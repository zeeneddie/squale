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
package org.squale.squaleexport.core;

import java.util.Locale;

import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.util.mail.IMailerProvider;

/**
 * Factory for create the exporter implementation. Implementation which implements the {@link IExport} interface
 */
public final class ExporterFactory
{

    /**
     * Private constructor
     */
    private ExporterFactory()
    {

    }

    /**
     * This method instanciate the exporter implementation and return this instance
     * 
     * @param session The Hibernate session
     * @param mailer The mail provider
     * @return An instance of the exporter implementation
     */
    public static IExporter createExporter( ISession session,IMailerProvider mailer, Locale local )
    {
        IExporter exporter = new ExporterImpl( session,mailer,local );
        return exporter;
    }

}
