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
package org.squale.squaleweb.util;

import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.datatransfertobject.config.AdminParamsDTO;
import org.squale.squalecommon.util.SqualeCommonConstants;
import org.squale.squalecommon.util.SqualeCommonUtils;
import org.squale.squalecommon.util.mail.IMailerProvider;
import org.squale.squalecommon.util.mail.MailerHelper;
import org.squale.squaleexport.core.ExporterFactory;
import org.squale.squaleexport.core.IExporter;
import org.squale.squaleweb.resources.WebMessages;

/**
 * This class launch an export of application for the shared repository. This launch is done in a new thread. For that
 * the class implements the interface {@link Runnable}
 */
public class ExportThread
    implements Runnable
{

    /** Logger */
    private static final Log LOGGER = LogFactory.getLog( ExportThread.class );

    /**
     * Persistence provider
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * The current local
     */
    private Locale local;

    /**
     * The current servlet of the application
     */
    private ActionServlet servlet;

    /**
     * The list of id of the application to export
     */
    private List<Long> applicationToExport;

    /**
     * List of parameter of the local squale needed for the export
     */
    private List<AdminParamsDTO> configMappingList;

    /**
     * Full constructor
     * 
     * @param applicationlist The list of id of the application to export
     * @param configInfo The list of parameter needed for the export
     * @param currentServlet The servlet of the application
     * @param pLocal The current Locale
     */
    public ExportThread( List<Long> applicationlist, List<AdminParamsDTO> configInfo, ActionServlet currentServlet,
                         Locale pLocal )
    {
        applicationToExport = applicationlist;
        configMappingList = configInfo;
        servlet = currentServlet;
        local = pLocal;
    }

    /**
     * {@inheritDoc}
     */
    public void run()
    {

        ISession session;
        IMailerProvider mailer;
        mailer = MailerHelper.getMailerProvider();
        try
        {
            session = PERSISTENTPROVIDER.getSession();

            IExporter exporter = ExporterFactory.createExporter( session, mailer, local );

            // Launch of the export
            exporter.exportData( applicationToExport, configMappingList );

        }
        catch ( JrafPersistenceException e )
        {
            LOGGER.error( e );
            String object = WebMessages.getString( local, "shared_repository.export.thread.mail.failed.object" );
            String content = WebMessages.getString( local, "shared_repository.export.thread.mail.failed.content" );
            SqualeCommonUtils.notifyByEmail( mailer, null, SqualeCommonConstants.ONLY_ADMINS, null, object, content,
                                             false );
        }
        finally
        {
            // When the export is done the "export_in_progress" attribute is set to false
            ServletContext ctx = servlet.getServletContext();
            ctx.setAttribute( "export_in_progress", "false" );
        }

    }

}
