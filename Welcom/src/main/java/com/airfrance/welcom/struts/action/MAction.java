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
/*
 * Créé le 6 nov. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.Action;
import org.apache.struts.util.MessageResources;

import com.airfrance.welcom.outils.Access;
import com.airfrance.welcom.struts.bean.WILogonBean;
import com.airfrance.welcom.struts.bean.WILogonBeanSecurity;
import com.airfrance.welcom.struts.bean.WResultAction;
import com.airfrance.welcom.struts.util.WConstants;

/**
 * Ajoute la couche de gestion des messages
 * 
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class MAction
    extends Action
{

    /** logger */
    private static Log log = LogFactory.getLog( MAction.class );

    /**
     * Recupere le WILogonBean
     * 
     * @param request : le request
     * @return WILogonBean
     */
    public WILogonBean getWILogonBean( final HttpServletRequest request )
    {
        if ( request.getSession().getAttribute( WConstants.USER_KEY ) instanceof WILogonBean )
        {
            return (WILogonBean) request.getSession().getAttribute( WConstants.USER_KEY );
        }
        else
        {
            return null;
        }
    }

    /**
     * Stocke un message
     * 
     * @deprecated
     * @param request : Request
     * @param msgRessource : message
     * @throws ServletException : Le logonBean n'implemente pas l'interface WIResultAction pour la gestion des popup de
     *             resultat de l'action
     */
    public void setResultAction( final HttpServletRequest request, final String msgRessource )
        throws ServletException
    {
        setMessagePopup( request, msgRessource, (Object[]) null );
    }

    /**
     * Stocke un message
     * 
     * @deprecated
     * @param request : Request
     * @param msgRessource : message
     * @param arg0 : le 1er argument du message
     * @throws ServletException : Le logonBean n'implemente pas l'interface WIResultAction pour la gestion des popup de
     *             resultat de l'action
     */
    public void setResultAction( final HttpServletRequest request, final String msgRessource, final String arg0 )
        throws ServletException
    {
        final Object[] args = { arg0 };
        setMessagePopup( request, msgRessource, args );
    }

    /**
     * Stocke un message
     * 
     * @param request : Request
     * @param msgRessource : message
     * @param arg0 : le 1er argument du message
     * @param arg1 : le 2eme argument du message
     * @throws ServletException : Le logonBean n'implemente pas l'interface WIResultAction pour la gestion des popup de
     *             resultat de l'action
     * @deprecated
     */
    public void setResultAction( final HttpServletRequest request, final String msgRessource, final String arg0,
                                 final String arg1 )
        throws ServletException
    {
        final Object[] args = { arg0, arg1 };
        setMessagePopup( request, msgRessource, args );
    }

    /**
     * Stocke un message
     * 
     * @param request : Request
     * @param msgRessource : message
     * @param arg0 : le 1er argument du message
     * @param arg1 : le 2eme argument du message
     * @param arg2 : le 3eme argument du message
     * @throws ServletException : Le logonBean n'implemente pas l'interface WIResultAction pour la gestion des popup de
     *             resultat de l'action
     * @deprecated
     */
    public void setResultAction( final HttpServletRequest request, final String msgRessource, final String arg0,
                                 final String arg1, final String arg2 )
        throws ServletException
    {
        final Object[] args = { arg0, arg1, arg2 };
        setMessagePopup( request, msgRessource, args );
    }

    /**
     * Stocke un message
     * 
     * @param request : Request
     * @param msgRessource : message
     * @param arg0 : le 1er argument du message
     * @param arg1 : le 2eme argument du message
     * @param arg2 : le 3eme argument du message
     * @param arg3 : le 4eme argument du message
     * @throws ServletException : Le logonBean n'implemente pas l'interface WIResultAction pour la gestion des popup de
     *             resultat de l'action
     * @deprecated
     */
    public void setResultAction( final HttpServletRequest request, final String msgRessource, final String arg0,
                                 final String arg1, final String arg2, final String arg3 )
        throws ServletException
    {
        final Object[] args = { arg0, arg1, arg2, arg3 };
        setMessagePopup( request, msgRessource, args );
    }

    /**
     * Stocke un message
     * 
     * @param request : Request
     * @param msgRessource : message
     * @param args : le tableau d'arguments
     * @throws ServletException : Le logonBean n'implemente pas l'interface WIResultAction pour la gestion des popup de
     *             resultat de l'action
     * @deprecated
     */
    public void setResultAction( final HttpServletRequest request, final String msgRessource, final Object[] args )
        throws ServletException
    {
        setMessagePopup( request, msgRessource, args );
    }

    /**
     * Stocke un message
     * 
     * @param request : Request
     * @param msgRessource : message
     * @throws ServletException : Le logonBean n'implemente pas l'interface WIResultAction pour la gestion des popup de
     *             resultat de l'action
     */
    public void setMessagePopup( final HttpServletRequest request, final String msgRessource )
        throws ServletException
    {
        setMessagePopup( request, msgRessource, (Object[]) null );
    }

    /**
     * Stocke un message
     * 
     * @param request : Request
     * @param msgRessource : message
     * @param arg0 : le 1er argument du message
     * @throws ServletException : Le logonBean n'implemente pas l'interface WIResultAction pour la gestion des popup de
     *             resultat de l'action
     */
    public void setMessagePopup( final HttpServletRequest request, final String msgRessource, final String arg0 )
        throws ServletException
    {
        final Object[] args = { arg0 };
        setMessagePopup( request, msgRessource, args );
    }

    /**
     * Stocke un message
     * 
     * @param request : Request
     * @param msgRessource : message
     * @param arg0 : le 1er argument du message
     * @param arg1 : le 2eme argument du message
     * @throws ServletException : Le logonBean n'implemente pas l'interface WIResultAction pour la gestion des popup de
     *             resultat de l'action
     */
    public void setMessagePopup( final HttpServletRequest request, final String msgRessource, final String arg0,
                                 final String arg1 )
        throws ServletException
    {
        final Object[] args = { arg0, arg1 };
        setMessagePopup( request, msgRessource, args );
    }

    /**
     * Stocke un message
     * 
     * @param request : Request
     * @param msgRessource : message
     * @param arg0 : le 1er argument du message
     * @param arg1 : le 2eme argument du message
     * @param arg2 : le 3eme argument du message
     * @throws ServletException : Le logonBean n'implemente pas l'interface WIResultAction pour la gestion des popup de
     *             resultat de l'action
     */
    public void setMessagePopup( final HttpServletRequest request, final String msgRessource, final String arg0,
                                 final String arg1, final String arg2 )
        throws ServletException
    {
        final Object[] args = { arg0, arg1, arg2 };
        setMessagePopup( request, msgRessource, args );
    }

    /**
     * Stocke un message
     * 
     * @param request : Request
     * @param msgRessource : message
     * @param arg0 : le 1er argument du message
     * @param arg1 : le 2eme argument du message
     * @param arg2 : le 3eme argument du message
     * @param arg3 : le 4eme argument du message
     * @throws ServletException : Le logonBean n'implemente pas l'interface WIResultAction pour la gestion des popup de
     *             resultat de l'action
     */
    public void setMessagePopup( final HttpServletRequest request, final String msgRessource, final String arg0,
                                 final String arg1, final String arg2, final String arg3 )
        throws ServletException
    {
        final Object[] args = { arg0, arg1, arg2, arg3 };
        setMessagePopup( request, msgRessource, args );
    }

    /**
     * Stocke un message
     * 
     * @param request : Request
     * @param msgRessource : message
     * @param args : le tableau d'arguments
     * @throws ServletException : Le logonBean n'implemente pas l'interface WIResultAction pour la gestion des popup de
     *             resultat de l'action
     */
    public void setMessagePopup( final HttpServletRequest request, final String msgRessource, final Object[] args )
        throws ServletException
    {
        final MessageResources resources = getResources( request );
        String result = resources.getMessage( getLocale( request ), msgRessource, args );

        if ( GenericValidator.isBlankOrNull( result ) )
        {
            result = msgRessource;
        }
        WResultAction.saveMessage( request, result );
    }

    /**
     * Verifie l'habillitation d'une personne pour executer la methode
     * 
     * @param request Requette
     * @param accessKey droit d'accées de la personne
     * @param minimalAccess niveau d'accées minimal, NONE, NO, READONLY, YES, READWRITE
     * @throws NoHabilitationException Probleme pour retouver le niveau d'habilitation
     * @since 2.4.3
     */
    public void checkHabilitation( final HttpServletRequest request, String accessKey, String minimalAccess )
        throws NoHabilitationException
    {
        if ( GenericValidator.isBlankOrNull( accessKey ) )
        {
            throw new NoHabilitationException( "Votre clef d'accés ne peux être nulle" );
        }
        // Recupere le logon bean pour effectuer les tests
        WILogonBean logonBean = getWILogonBean( request );
        if ( logonBean != null )
        {
            if ( !( logonBean instanceof WILogonBeanSecurity ) )
            {
                throw new NoHabilitationException(
                                                   "Votre Logon Bean n'herite par de WILogonBeanSecurity, Le gestion des droits d'accés n'est pas activé" );
            }
            String access = ( (WILogonBeanSecurity) logonBean ).getAccess( accessKey );
            if ( GenericValidator.isBlankOrNull( access ) )
            {
                throw new NoHabilitationException( "La valeur de la clef " + accessKey
                    + " ne retourne pas de droit valide (null)" );
            }

            String max = Access.getMaxAccess( access, minimalAccess );

            if ( !access.equals( max ) )
            {
                throw new NoHabilitationException(
                                                   "Vous ne possédez pas les priviléges necessaire pour executer cette methode. Le niveau minimal requis est "
                                                       + minimalAccess + ", vous possédez seulement le niveau " + max );
            }
        }

    }

    /**
     * Verfie le niveau d'habilitation, si le droit retourne au moins un accée en READONLY / READWRITE / YES
     * 
     * @param request requette
     * @param accessKey droit d'accées
     * @throws NoHabilitationException Probleme pour retouver le niveau d'habilitation
     * @since 2.4.3
     */
    public void checkHabilitation( final HttpServletRequest request, String accessKey )
        throws NoHabilitationException
    {
        checkHabilitation( request, accessKey, Access.READONLY );
    }
}
