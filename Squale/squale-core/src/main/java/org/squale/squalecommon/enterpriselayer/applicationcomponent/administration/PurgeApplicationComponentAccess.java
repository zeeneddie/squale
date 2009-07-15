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
//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\org\\squale\\squalecommon\\enterpriselayer\\applicationcomponent\\PurgeApplicationComponentAccess.java

package org.squale.squalecommon.enterpriselayer.applicationcomponent.administration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.component.ApplicationConfDTO;
import org.squale.squalecommon.enterpriselayer.applicationcomponent.ACMessages;
import org.squale.squalecommon.enterpriselayer.facade.component.AuditFacade;
import org.squale.squalecommon.enterpriselayer.facade.component.ApplicationFacade;
import org.squale.squalecommon.enterpriselayer.facade.quality.SqualeReferenceFacade;

/**
 * <p>
 * Title : PurgeApplicationComponentAccess.java
 * </p>
 * <p>
 * Description : Application component pour la purge projet et audit
 * </p>
 */
public class PurgeApplicationComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * log
     */
    private static final Log LOG = LogFactory.getLog( PurgeApplicationComponentAccess.class );

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Constructeur par défaut
     * 
     * @roseuid 42CBF81602AA
     */
    public PurgeApplicationComponentAccess()
    {
    }

    /**
     * Permet de supprimer une application du portail SQUALE
     * 
     * @param pApplicationConf ApplicationConfDTO renseignant l'ID de l'application
     * @return Integer : 0 pour la réussite de la methode sinon 1
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBF8160323
     */
    public Integer purgeApplication( ApplicationConfDTO pApplicationConf )
        throws JrafEnterpriseException
    {
        Integer status = new Integer( 0 );
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Verification si un jalon a ete posé sur l'application
            if ( !ApplicationFacade.existsMilestone( pApplicationConf, session ) )
            {
                // Si aucun jalon n'a ete posé, on insert le dernier audit dans le referentiel
                ComponentDTO project = new ComponentDTO();
                project.setID( pApplicationConf.getId() );
                // Récupération du dernier audit
                AuditDTO audit = AuditFacade.getLastAudit( project, null, session );
                if ( audit != null )
                {
                    // On insert l'audit récupéré précédemment
                    SqualeReferenceFacade.insertAudit( audit, session );
                }
            }
            // On efface l'application et toutes ses relations associées
            ApplicationFacade.deleteAll( pApplicationConf, session );

            session.commitTransaction();
        }
        catch ( Exception e )
        {
            // on rollback la transaction si nécessaire
            String tab[] = { String.valueOf( pApplicationConf.getId() ) };
            String message = ACMessages.getString( "ac.exception.purge.purgeapplication", tab );
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
     * permet de purger un audit particulier
     * 
     * @param pAudit AuditDTO renseignant l'ID de l'audit
     * @return Integer : 0 pour la réussite de la methode sinon 1
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBF81603D7
     */
    public Integer purgeAudit( AuditDTO pAudit )
        throws JrafEnterpriseException
    {
        Integer status = new Integer( 0 );
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Suppression d'un audit via AuditFacade
            AuditFacade.delete( pAudit, session );
            session.commitTransaction();
        }
        catch ( Exception e )
        {
            // on rollback la transaction si nécessaire
            String tab[] = { String.valueOf( pAudit.getID() ) };
            String message = ACMessages.getString( "ac.exception.purge.purgeaudit", tab );
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
     * Hide an application for not admin users without delete it physically: - remove users - if it's public, becomes
     * private - disactive audits - delete current not attempted audits - rename application like
     * applicationName(year)(month)(day)(hour)(minute)
     * 
     * @param pApplicationConf application to hide
     * @return 1 if success 0 else
     * @throws JrafEnterpriseException if error
     */
    public Integer hideApplication( ApplicationConfDTO pApplicationConf )
        throws JrafEnterpriseException
    {
        Integer status = new Integer( 0 );
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Hide application with ApplicationFacade
            ApplicationFacade.hideApplication( pApplicationConf, session );
            session.commitTransaction();
        }
        catch ( Exception e )
        {
            // rollback
            String tab[] = { String.valueOf( pApplicationConf.getId() ) };
            String message = ACMessages.getString( "ac.exception.purge.purgeapplication", tab );
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

}
