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
package org.squale.squalecommon.util.mail.javamail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class JavaMailProviderSpringImpl
    extends JavaMailProviderImpl
    implements ApplicationContextAware
{

    /** logger */
    private static final Log LOG = LogFactory.getLog( JavaMailProviderSpringImpl.class );

    /** Spring context */
    private ApplicationContext applicationContext = null;

    /**
     * @return the spring context
     */
    public ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    /**
     * @param context the spring context
     */
    public void setApplicationContext( ApplicationContext context )
    {
        applicationContext = context;
    }

}
