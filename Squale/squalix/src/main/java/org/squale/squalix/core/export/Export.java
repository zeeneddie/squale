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
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.component.AuditDAOImpl;
import org.squale.squalecommon.daolayer.component.ProjectDAOImpl;
import org.squale.squalecommon.daolayer.config.AdminParamsDAOImpl;
import org.squale.squalecommon.daolayer.config.ServeurDAOImpl;
import org.squale.squalecommon.daolayer.sharedrepository.ApplicationExportDAOImpl;
import org.squale.squalecommon.daolayer.sharedrepository.segment.SegmentDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.AdminParamsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.ServeurBO;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.ApplicationExportBO;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment.SegmentBO;
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
                    // We search now all the audit to export
                    HashMap<Long, Long> mapAppAuditToExport = auditToExport( session );

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
                if ( session != null )
                {
                    session.closeSession();
                }
            }
        }
        catch ( JrafPersistenceException e )
        {
            String message = Messages.getString( "export.launch.failed" );
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
        }
        catch ( JrafDaoException e )
        {
            String message = Messages.getString( "export.init.goodServer.error" );
            throw new JrafEnterpriseException( message, e );
        }
        return goodServer;
    }

    /**
     * This method recover the list of audit to export
     * 
     * @param session The hibernate session
     * @return the list of audit (technical id)/application (technical id) to export
     * @throws JrafEnterpriseException Exception occurs during the search
     */
    private HashMap<Long, Long> auditToExport( ISession session )
        throws JrafEnterpriseException
    {
        HashMap<Long, Long> mapAppAuditToExport;
        try
        {
            // We retrieve all the application to export
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
            mapAppAuditToExport = new HashMap<Long, Long>();
            List<String> notExported = new ArrayList<String>();
            for ( ApplicationExportBO applicationExportBO : listAppToExport )
            {
                ApplicationBO application = applicationExportBO.getApplication();
                auditForApplication( session, application, mapAppAuditToExport, notExported );

            }
            if ( notExported.size() > 0 )
            {
                ExportUtils.applicationNotExported( notExported );
            }
        }
        catch ( JrafDaoException e )
        {
            String message = "export.init.auditToExport.error";
            throw new JrafEnterpriseException( message, e );
        }
        return mapAppAuditToExport;
    }

    /**
     * This method searches for the application given in argument the audit that will be exported. If one valid audit is
     * found then it is add to the map of application to export. else the application name is add to the list of
     * application will not be exported.
     * 
     * @param session The hibernate session
     * @param application the application for which we search the audit
     * @param mapAppAuditToExport The map of audit to export
     * @param notExported The list of application that will not be exported
     * @throws JrafDaoException Exception occurs during the audit search
     */
    private void auditForApplication( ISession session, ApplicationBO application,
                                      HashMap<Long, Long> mapAppAuditToExport, List<String> notExported )
        throws JrafDaoException
    {
        // We search all the audit successful for the application ordered by date
        AuditDAOImpl auditDao = AuditDAOImpl.getInstance();
        List<AuditBO> allSuccessfulAudit = auditDao.succesfullAudit( session, application.getId() );

        // If there is at least one successful audit then we check if this audit is in the list of audit to
        // purge
        if ( allSuccessfulAudit != null && allSuccessfulAudit.size() > 0 )
        {
            boolean isValid = false;
            int auditIndex = 0;
            while ( !isValid && allSuccessfulAudit.size() > auditIndex )
            {
                AuditBO audit = allSuccessfulAudit.get( auditIndex );

                if ( !application.isAuditObsolete( audit ) )
                {
                    isValid = true;
                    boolean hasSegment = isSegmented( session, audit );
                    if ( hasSegment )
                    {
                        mapAppAuditToExport.put( application.getId(), audit.getId() );
                    }
                    else
                    {
                        isValid = false;
                    }
                }
                auditIndex++;
            }
            if ( !isValid )
            {
                notExported.add( application.getName() );
            }
        }
    }

    /**
     * This method verifies that all the modules linked to the audit are segmented
     * 
     * @param session The hibernate session
     * @param audit The audit
     * @return True if all the module linked to the audit has segment
     * @throws JrafDaoException exception occurs during the search
     */
    private boolean isSegmented( ISession session, AuditBO audit )
        throws JrafDaoException
    {
        // Retrieve of the module linked to the audit
        ProjectDAOImpl componentDao = ProjectDAOImpl.getInstance();
        List<ProjectBO> modulesList = componentDao.getModuleslinkedToAudit( session, audit );
        SegmentDAOImpl segDao = SegmentDAOImpl.getInstance();
        // Each module should had segment
        int moduleIndex = 0;
        boolean hasSegment = true;
        while ( hasSegment && moduleIndex < modulesList.size() )
        {
            ProjectBO module = modulesList.get( moduleIndex );
            List<SegmentBO> allLinkedSegment = segDao.findModuleSegments( session, module.getId() );
            if ( allLinkedSegment.size() == 0 )
            {
                hasSegment = false;
            }
            moduleIndex++;
        }
        return hasSegment;
    }

}
