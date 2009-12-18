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
package org.squale.squalix.core.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.component.AuditDAOImpl;
import org.squale.squalecommon.daolayer.config.AdminParamsDAOImpl;
import org.squale.squalecommon.daolayer.config.ServeurDAOImpl;
import org.squale.squalecommon.daolayer.sharedrepository.ApplicationExportDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.AdminParamsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.ServeurBO;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.ApplicationExportBO;
import org.squale.squalix.messages.Messages;

/**
 * This class prepare and launch the export Thread
 */
public class Export
{

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( Export.class );

    /**
     * This method launch the thread for the creation of the export file for the shared repository
     * 
     * @param siteId The siteId
     * @return The thread created to execute the export
     */
    public Thread launchExport( long siteId )
    {

        Thread exportThread = null;
        ISession session;
        try
        {
            session = PersistenceHelper.getPersistenceProvider().getSession();
            try
            {

                // We search whether or not the current server is the one authorized to
                boolean goodServer = isGoodServer( session, siteId );
                // We search whether or not a job is scheduled
                boolean jobScheduled = ExportUtils.isJobScheduled( session );
                if ( goodServer && jobScheduled )
                {
                    // This list contain the id of all the audit marked as to be purged or obsoleted. So we will exclude
                    // them from the export
                    HashSet<Long> setAuditIdToPurge = excludedAudit( session, siteId );

                    // We search now all the audit to export
                    HashMap<Long, Long> mapAppAuditToExport = auditToExport( session, setAuditIdToPurge );

                    ExportThread export = new ExportThread( mapAppAuditToExport );
                    exportThread = new Thread( export );
                    exportThread.start();
                }
            }
            catch ( JrafEnterpriseException e )
            {
                String message;
                try
                {
                    ExportUtils.setJobAsFailed( session );
                }
                catch ( JrafEnterpriseException e1 )
                {
                    LOGGER.error( e1 );
                }
                message = Messages.getString( "export.launch.failed" );
                LOGGER.error( message, e );
            }
            finally
            {
                if(session!=null)
                {
                    session.closeSession();
                }
            }
        }
        catch ( JrafPersistenceException e )
        {
            String  message = Messages.getString( "export.launch.failed" );
            LOGGER.error( message, e );
        }        
        return exportThread;
    }

    /**
     * This method determines if the current server is the one authorized to launch audit
     * 
     * @param session The hibernate session
     * @param siteId The id of the site
     * @return true if the current server is one that should launch the audit
     * @throws JrafEnterpriseException Exception occurs
     */
    private boolean isGoodServer( ISession session, long siteId )
        throws JrafEnterpriseException

    {
        boolean goodServer = false;
        ServeurDAOImpl daoSrv = ServeurDAOImpl.getInstance();
        ServeurBO srv;
        try
        {
            srv = daoSrv.findWhereId( session, siteId );

            AdminParamsDAOImpl daoParam = AdminParamsDAOImpl.getInstance();
            ArrayList<AdminParamsBO> paramList =
                (ArrayList<AdminParamsBO>) daoParam.findByKey( session, AdminParamsBO.SQUALIX_SERVER_NAME );
            if ( paramList.size() == 1 )
            {
                AdminParamsBO param = paramList.get( 0 );
                if ( srv.getName().equals( param.getParamValue() ) )
                {
                    goodServer = true;
                }
            }
            else
            {
                LOGGER.warn( Messages.getString( "export.config.server" ) );
            }
        }
        catch ( JrafDaoException e )
        {
            String message = Messages.getString( "export.init.goodServer.error" );
            throw new JrafEnterpriseException( message, e );
        }
        return goodServer;
    }

    /**
     * This method list all the audit which are marked as to be purged or obsoleted
     * 
     * @param session The hibernate session
     * @param siteId The id of the Squalix site
     * @return The list of audit id to exclude
     * @throws JrafEnterpriseException Exception occurs during the search of the excluded audit for the export
     */
    private HashSet<Long> excludedAudit( ISession session, long siteId )
        throws JrafEnterpriseException

    {
        // Set of Audit Id that will be exclude from the export
        HashSet<Long> setAuditIdToPurge;
        try
        {
            // Audit marked to purge
            List<AuditBO> listAuditsToPurge =
                (List<AuditBO>) AuditDAOImpl.getInstance().findDeleted( session, siteId, new ArrayList() );
            // Obsoleted audit
            listAuditsToPurge.addAll( AuditDAOImpl.getInstance().findObsoleteAuditsToDelete( session, siteId,
                                                                                             new ArrayList() ) );

            setAuditIdToPurge = new HashSet<Long>();
            for ( AuditBO audit : listAuditsToPurge )
            {
                setAuditIdToPurge.add( audit.getId() );
            }
        }
        catch ( JrafDaoException e )
        {
            String message = Messages.getString( "export.init.excludedAudit.error" );
            throw new JrafEnterpriseException( message, e );
        }
        return setAuditIdToPurge;
    }

    /**
     * This method recover the list of audit to export
     * 
     * @param session The hibernate session
     * @param setAuditIdToPurge The list of audit id to exclude
     * @return the list of audit/application to export
     * @throws JrafEnterpriseException Exception occurs during the search
     */
    private HashMap<Long, Long> auditToExport( ISession session, HashSet<Long> setAuditIdToPurge )
        throws JrafEnterpriseException
    {
        HashMap<Long, Long> mapAppAuditToExport;
        try
        {
            ApplicationExportBO exampleBo = new ApplicationExportBO();
            exampleBo.setToExport( true );
            ApplicationExportDAOImpl dao = ApplicationExportDAOImpl.getInstance();
            ArrayList<ApplicationExportBO> listAppToExport =
                (ArrayList<ApplicationExportBO>) dao.findByExample( session, exampleBo );

            // All the application marked as to be export should be reinit
            for ( ApplicationExportBO applicationExportBO : listAppToExport )
            {
                applicationExportBO.setToExport( false );
                dao.save( session, applicationExportBO );
            }
            AuditDAOImpl auditDao = AuditDAOImpl.getInstance();
            mapAppAuditToExport = new HashMap<Long, Long>();
            for ( ApplicationExportBO applicationExportBO : listAppToExport )
            {
                // We search all the audit successful for the application ordered by date
                List<AuditBO> allSuccessfulAudit =
                    auditDao.lastSuccesfullAudit( session, applicationExportBO.getApplication().getId() );
                // If there is at least one successful audit then we check if this audit is in the list of audit to
                // purge
                if ( allSuccessfulAudit != null && allSuccessfulAudit.size() > 0 )
                {
                    boolean valid = false;
                    int index = 0;
                    while ( !valid )
                    {
                        AuditBO audit = allSuccessfulAudit.get( index );
                        if ( !setAuditIdToPurge.contains( audit.getId() ) )
                        {
                            valid = true;
                            mapAppAuditToExport.put( applicationExportBO.getApplication().getId(), audit.getId() );
                        }
                        index++;
                    }
                }
            }
        }
        catch ( JrafDaoException e )
        {
            String message = "export.init.auditToExport.error";
            throw new JrafEnterpriseException( message, e );
        }
        return mapAppAuditToExport;
    }

}
