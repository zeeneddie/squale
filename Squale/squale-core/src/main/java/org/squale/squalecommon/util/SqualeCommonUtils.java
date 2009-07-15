/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.squale.squalecommon.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squalecommon.enterpriselayer.applicationcomponent.administration.LoginApplicationComponentAccess;
import org.squale.squalecommon.util.mail.IMailerProvider;
import org.squale.squalecommon.util.mail.MailException;
import org.squale.squalecommon.util.messages.CommonMessages;

/**
 * 
 */
public class SqualeCommonUtils
{

    /**
     * Logger
     */
    private static Log log = LogFactory.getLog( SqualeCommonUtils.class );

    /** champ permettant d'accéder aux éléments */
    private static LoginApplicationComponentAccess access = new LoginApplicationComponentAccess();

    /**
     * @param pProvider le fournisser de service mail
     * @param pDestList la liste des destinataires
     * @param pApplicationId l'id de l'application concernée
     * @param pObject l'objet du mail
     * @param pContent le contenu du mail
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return true si le mail a bein été envoyé
     */
    public static boolean notifyByEmail( IMailerProvider pProvider, String pDestList, Long pApplicationId,
                                         String pObject, String pContent, boolean pUnsubscribed )
    {
        return notifyByEmail( pProvider, null, pDestList, pApplicationId, pObject, pContent, pUnsubscribed );

    }

    /**
     * @param pProvider le fournisser de service mail
     * @param pDestList la liste des destinataires
     * @param pApplicationId l'id de l'application concernée
     * @param pObject l'objet du mail
     * @param pContent le contenu du mail
     * @param pSender le nom de l'expéditeur
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return true si le mail a été envoyé
     */
    public static boolean notifyByEmail( IMailerProvider pProvider, String pSender, String pDestList,
                                         Long pApplicationId, String pObject, String pContent, boolean pUnsubscribed )
    {
        boolean sent = true;
        Collection destColl = getDestEmails( pDestList, pApplicationId, pUnsubscribed );
        // transformation le liste en tableau de String
        String[] recipients = new String[destColl.size()];
        recipients = (String[]) destColl.toArray( recipients );
        // si on a réussi à identifier des destinataires
        if ( recipients.length != 0 )
        {
            // Envoi du mail
            try
            {
                pProvider.sendMail( pSender, recipients, pObject, pContent );
            }
            catch ( MailException e )
            {
                log.error( CommonMessages.getString( "exception.sendmail" ), e );
                sent = false;
            }
        }
        return sent;
    }

    /**
     * @param pDestList indique quels utilisateurs sont concernés (constante définie dans SqualeCommonConstants)
     * @param pApplicationId l'id de l'application concernée
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return la liste des destinataires
     */
    private static Collection getDestEmails( String pDestList, Long pApplicationId, boolean pUnsubscribed )
    {
        Collection destColl = new ArrayList();
        if ( pDestList.equals( SqualeCommonConstants.ONLY_ADMINS ) )
        {
            // mail seulement pour les admins du portail
            destColl = getAdminsEmails( pUnsubscribed );
        }
        else
        {
            if ( pDestList.equals( SqualeCommonConstants.ONLY_MANAGERS ) )
            {
                // mail seulement pour les managers de l'application
                destColl = getManagersEmails( pApplicationId, pUnsubscribed );
            }
            else
            {
                if ( pDestList.equals( SqualeCommonConstants.MANAGERS_AND_ADMINS ) )
                {
                    // mail destiné à la fois aux admins du portail
                    // ainsi qu'aux gestionnaires de l'application
                    destColl = getAdminsAndManagersEmails( pApplicationId, pUnsubscribed );
                }
                else
                {
                    if ( pDestList.equals( SqualeCommonConstants.MANAGERS_AND_READERS ) )
                    {
                        // mail destiné à la fois aux gestionnaires de l'application
                        // et aux différents utilisateurs qui ont le droit de lecture
                        // sur les résultats de l'application
                        destColl = getManagersAndReadersEmails( pApplicationId, pUnsubscribed );
                    }
                    else
                    {
                        destColl.add( pDestList );
                    }
                }
            }
        }
        return destColl;
    }

    /**
     * @param pId l'application dont on veut les managers
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return une collection contenant l'ensemble des admins du portail ainsi que les managers de l'application.
     */
    private static Collection getAdminsAndManagersEmails( Long pId, boolean pUnsubscribed )
    {
        Collection managers = getManagersEmails( pId, pUnsubscribed );
        Collection admins = getAdminsEmails( pUnsubscribed );
        return merge( managers, admins );
    }

    /**
     * @param pId l'application dont on veut les managers
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return une collection contenant l'ensemble des admins du portail ainsi que les managers de l'application.
     */
    private static Collection getManagersAndReadersEmails( Long pId, boolean pUnsubscribed )
    {
        Collection managers = getManagersEmails( pId, pUnsubscribed );
        Collection readers = getReadersEmails( pId, pUnsubscribed );
        return merge( managers, readers );
    }

    /**
     * Fusionne 2 collections d'emails en évitant les doublons
     * 
     * @param pColl1 la première collection d'emails
     * @param pColl2 la deuxième collection d'emails
     * @return une collection résultat de la fusion sans doublons
     */
    private static Collection merge( Collection pColl1, Collection pColl2 )
    {
        ArrayList result = new ArrayList( 0 );
        result.addAll( pColl1 );
        Iterator it = pColl2.iterator();
        while ( it.hasNext() )
        {
            String currentMail = (String) it.next();
            // on n'utilise pas la méthode contains car on veut juste vérifier les emails
            boolean needToAdd = true;
            for ( int i = 0; needToAdd && i < result.size(); i++ )
            {
                String comparedEmail = ( (String) result.get( i ) );
                if ( currentMail.equals( comparedEmail ) )
                {
                    needToAdd = false;
                }
            }
            if ( needToAdd )
            {
                result.add( currentMail );
            }
        }
        return result;
    }

    /**
     * @param pId l'id de l'application confirmée
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return la liste des emails des managers de l'application
     */
    private static Collection getManagersEmails( Long pId, boolean pUnsubscribed )
    {
        // On cherche les utilisateurs avec le profil gestionnaire de l'application
        IApplicationComponent ac;
        Collection coll = new ArrayList( 0 );
        try
        {
            // Obtention de la liste des administrateurs
            coll = access.getManagersEmails( pId, new Boolean( pUnsubscribed ) );
        }
        catch ( JrafEnterpriseException e )
        {
            log.error( CommonMessages.getString( "exception.sendmail" ), e );
        }
        return coll;
    }

    /**
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return la liste des emails des administrateurs
     */
    private static Collection getAdminsEmails( boolean pUnsubscribed )
    {
        // On cherche les utilisateurs avec le profil administrateur
        IApplicationComponent ac;
        String[] recipients = null;
        Collection coll = new ArrayList( 0 );
        try
        {
            // Obtention de la liste des administrateurs
            coll = access.getAdminsEmails( new Boolean( pUnsubscribed ) );
        }
        catch ( JrafEnterpriseException e )
        {
            log.error( CommonMessages.getString( "exception.sendmail" ), e );
        }
        return coll;
    }

    /**
     * @param pId l'id de l'application confirmée
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return la liste des emails des utilisateurs ayant un droit de lecture sur l'application
     */
    private static Collection getReadersEmails( Long pId, boolean pUnsubscribed )
    {
        // On cherche les utilisateurs avec le profil administrateur
        IApplicationComponent ac;
        String[] recipients = null;
        Collection coll = new ArrayList( 0 );
        try
        {
            // Obtention de la liste des administrateurs
            coll = access.getReadersEmails( pId, new Boolean( pUnsubscribed ) );
        }
        catch ( JrafEnterpriseException e )
        {
            log.error( CommonMessages.getString( "exception.sendmail" ), e );
        }
        return coll;
    }
}
