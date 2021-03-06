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
//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\org\\squale\\squalecommon\\enterpriselayer\\applicationcomponent\\ValidationApplicationComponentAccess.java

package org.squale.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.applicationcomponent.ACMessages;
import org.squale.squalecommon.enterpriselayer.facade.component.ApplicationFacade;
import org.squale.squalecommon.enterpriselayer.facade.quality.SqualeReferenceFacade;

/**
 * <p>
 * Title : ValidationApplicationComponentAccess.java
 * </p>
 * <p>
 * Description : Application component de validation de creation d'applications
 * </p>
 */
public class ValidationApplicationComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * log
     */
    private static final Log LOG = LogFactory.getLog( ValidationApplicationComponentAccess.class );

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Constructeur par d�faut
     * 
     * @roseuid 42CBFC030151
     */
    public ValidationApplicationComponentAccess()
    {
    }

    /**
     * liste le r�f�rentiel
     * 
     * @return les diff�rentes r�f�rences
     * @throws JrafEnterpriseException en cas de probl�me de r�cup�ration des donn�s
     * @throws JrafPersistenceException en cas de probl�me de persistence
     */
    public Collection listReferentiel()
        throws JrafEnterpriseException, JrafPersistenceException
    {
        Collection result = null;
        result = SqualeReferenceFacade.listReferentiel();
        return result;
    }

    /**
     * Permet de supprimer une collection d'applications cr��s non valides
     * 
     * @param pApplicationsToRemove applications � supprimer
     * @return Integer : 0 pour la r�ussite de la methode sinon 1
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFC030179
     */
    public Integer removeApplicationsCreation( Collection pApplicationsToRemove )
        throws JrafEnterpriseException
    {
        Integer status = new Integer( 0 );
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Suppression d'applications via la facade ApplicationFacade
            ApplicationFacade.deleteApplicationConfList( pApplicationsToRemove, session );
            session.commitTransaction();
        }
        catch ( Exception e )
        {
            // on rollback la transaction si n�cessaire
            String message = ACMessages.getString( "ac.exception.validation.removeapplicationscreation" );
            status = new Integer( 1 );
            if ( session != null )
            {
                session.rollbackTransaction();
            }
            LOG.fatal( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return status;
    }

    /**
     * Permet de valider une collection d'applications cr��s
     * 
     * @param pApplicationsToValidate collection d'applications � valider
     * @return null si il y a eu un probl�me, l'ensemble des applications non valid�es
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFC0301A1
     */
    public List validateApplicationsCreation( Collection pApplicationsToValidate )
        throws JrafEnterpriseException
    {
        List notValidated = new ArrayList();
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Validation de l'application via la facade ApplicationFacade
            notValidated = ApplicationFacade.validateApplicationConfList( pApplicationsToValidate, session );
            session.commitTransaction();
        }
        catch ( Exception e )
        {
            // on rollback la transaction si n�cessaire
            String message = ACMessages.getString( "ac.exception.validation.validateapplicationscreation" );
            if ( session != null )
            {
                session.rollbackTransaction();
            }
            LOG.fatal( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return notValidated;
    }

    /**
     * Permet de r�cup�rer la liste des applications ayant �t� cr��s � v�rifier et valider
     * 
     * @return Collection de ApplicationConfDTO � v�rifier
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFC0301B5
     */
    public Collection getApplicationsCreated()
        throws JrafEnterpriseException
    {
        Collection collection = ApplicationFacade.getApplicationConfList();
        return collection;
    }

    /**
     * Permet de supprimer une collection de r�sultats d'une application du r�f�rentiel de comparaison
     * 
     * @param pAuditsToRemove collection de SqualeReferenceDTO � supprimer
     * @return Integer : 0 pour la r�ussite de la methode sinon 1
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFC0301DE
     */
    public Integer removeApplicationsReference( Collection pAuditsToRemove )
        throws JrafEnterpriseException
    {
        Integer status = new Integer( 0 );
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Suppression de references via SqualeReferenceFacade
            SqualeReferenceFacade.deleteAuditList( pAuditsToRemove, session );
            session.commitTransaction();
        }
        catch ( Exception e )
        {
            // on rollback la transaction si n�cessaire
            String message = ACMessages.getString( "ac.exception.validation.removeapplicationsreference" );
            status = new Integer( 1 );
            if ( session != null )
            {
                session.rollbackTransaction();
            }
            LOG.fatal( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return status;
    }

    /**
     * Permet mettre � jour le status des applications du r�f�rentiel indiquant si les projets sont affich�s ou pas dans
     * le r�f�rentiel
     * 
     * @param pReferences collection de SqualeReferenceDTO � valider
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFC0301F1
     */
    public void updateReferentiel( Collection pReferences )
        throws JrafEnterpriseException
    {
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Validation de references via la facade SqualeReferenceFacade

            SqualeReferenceFacade.updateReferentiel( pReferences, session );
            session.commitTransaction();
        }
        catch ( Exception e )
        {
            // on rollback la transaction si n�cessaire
            String message = ACMessages.getString( "ac.exception.validation.validateapplicationsreference" );
            if ( session != null )
            {
                session.rollbackTransaction();
            }
            LOG.fatal( message, e );
            throw new JrafEnterpriseException( message, e );
        }
    }
}
