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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.config.AdminParamsDAOImpl;
import org.squale.squalecommon.daolayer.sharedrepository.ApplicationExportDAOImpl;
import org.squale.squalecommon.datatransfertobject.config.AdminParamsDTO;
import org.squale.squalecommon.datatransfertobject.transform.config.AdminParamsTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.config.AdminParamsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.ApplicationExportBO;
import org.squale.squaleexport.core.ExporterFactory;
import org.squale.squaleexport.core.IExporter;
import org.squale.squalix.messages.Messages;

/**
 * This class launch an export of application for the shared repository. This launch is done in a new thread. For that
 * the class implements the interface {@link Runnable}
 */
public class ExportThread
    implements Runnable
{

    /**
     * Persistence provider
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( Export.class );

    /** set of audit id to purge */
    private HashMap<Long, Long> mapAppAuditToExport;

    /**
     * Default constructor
     * 
     * @param pMapAppAuditToExport List of application id and audit id to export
     */
    public ExportThread( HashMap<Long, Long> pMapAppAuditToExport )
    {
        mapAppAuditToExport = pMapAppAuditToExport;
    }

    /**
     * {@inheritDoc}
     */
    public void run()
    {
        IExporter exporter = ExporterFactory.createExporter( PERSISTENTPROVIDER );
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();

            ArrayList<AdminParamsDTO> adminParamsDTOList = searchAdminParams( session );

            // Launch of the export
            boolean exportSuccessful = false;
            try
            {
                LOGGER.info( Messages.getString( "export.job.start" ) );
                exportSuccessful = exporter.exportData( mapAppAuditToExport, adminParamsDTOList );
            }
            finally
            {
                try
                {
                    if ( exportSuccessful )
                    {
                        ExportUtils.setJobAsSuccessful( session );
                        setExportDate( session );
                        LOGGER.info( Messages.getString( "export.job.successful" ) );
                    }
                    else
                    {
                        ExportUtils.setJobAsFailed( session );
                        LOGGER.error( Messages.getString( "export.job.failed" ));
                    }
                }
                finally
                {
                    LOGGER.info( Messages.getString( "export.job.finish" ) );
                    session.closeSession();
                }
            }
        }
        catch ( JrafPersistenceException e )
        {
            String message = Messages.getString( "export.job.failed" );
            LOGGER.error( message, e );
        }
        catch ( JrafEnterpriseException e )
        {
            String message = Messages.getString( "export.job.failed" );
            LOGGER.error( message, e );
        }
    }

    /**
     * This method recover the list of admin params which concerning the export
     * 
     * @param session the hibernate session
     * @return The list of admin params concerning the export
     * @throws JrafEnterpriseException Exception occurs during the search
     */
    private ArrayList<AdminParamsDTO> searchAdminParams( ISession session )
        throws JrafEnterpriseException
    {
        ArrayList<AdminParamsDTO> adminParamsDTOList = null;
        try
        {
            AdminParamsDAOImpl dao = AdminParamsDAOImpl.getInstance();
            List<AdminParamsBO> adminParamsBOList = dao.findByKeyLike( session, AdminParamsBO.MAPPING );
            adminParamsDTOList = (ArrayList<AdminParamsDTO>) AdminParamsTransform.bo2dto( adminParamsBOList );
            adminParamsBOList = dao.findByKeyLike( session, AdminParamsBO.ENTITY_ID );
            ArrayList<AdminParamsDTO> adminParamsEntityList =
                (ArrayList<AdminParamsDTO>) AdminParamsTransform.bo2dto( adminParamsBOList );
            if ( adminParamsEntityList.size() == 1 )
            {
                adminParamsDTOList.add( adminParamsEntityList.get( 0 ) );
            }
            else if ( adminParamsEntityList.size() == 0 )
            {
                String message = Messages.getString( "export.adminParam.search.entity.empty" );
                throw new JrafEnterpriseException( message );
            }
            else
            {
                String message = Messages.getString( "export.adminParam.search.entity.many" );
                throw new JrafEnterpriseException( message );
            }
        }
        catch ( JrafDaoException e )
        {
            String message = Messages.getString( "export.adminParam.search" );
            throw new JrafEnterpriseException( message, e );
        }
        return adminParamsDTOList;
    }

    /**
     * This method update all the ApplicationExportBO for the application export with the date
     * 
     * @param session The hibernate session
     * @throws JrafEnterpriseException Exception occurs during the update
     */
    private void setExportDate( ISession session )
        throws JrafEnterpriseException
    {
        try
        {
            Iterator<Long> applicationIterator = mapAppAuditToExport.keySet().iterator();
            // For each application to export we create an export file
            Date currentDate = new Date();
            while ( applicationIterator.hasNext() )
            {
                long appId = applicationIterator.next();
                ApplicationExportDAOImpl dao = ApplicationExportDAOImpl.getInstance();
                ArrayList<ApplicationExportBO> listApp =
                    (ArrayList<ApplicationExportBO>) dao.getLastExportByApplication( session, appId );
                if ( listApp.size() == 1 )
                {
                    ApplicationExportBO bo = listApp.get( 0 );
                    bo.setLastExportDate( currentDate );
                    dao.save( session, bo );
                }
                else
                {
                    String message = Messages.getString( "export.lastexportdate.failed" );
                    LOGGER.warn( message );
                }
            }
        }
        catch ( JrafDaoException e )
        {
            throw new JrafEnterpriseException( e );
        }
    }
}
