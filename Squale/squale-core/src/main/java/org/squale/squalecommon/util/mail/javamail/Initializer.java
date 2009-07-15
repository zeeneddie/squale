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

import java.util.Map;

import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.spi.initializer.IInitializable;
import org.squale.jraf.spi.initializer.IInitializableBean;
import org.squale.jraf.spi.provider.IProvider;

/**
 * Initializer for the javamail provider
 */
public class Initializer
    implements IInitializable, IInitializableBean
{

    /**
     * Constructor
     */
    public Initializer()
    {
        super();
    }

    /**
     * This method return an instance of the javamail provider
     * 
     * @param objectInitialize List of parameter (key-value)
     * @return The return an instance of JavaMailProviderImpl
     * @throws JrafConfigException Exception happened during the initialization
     */
    public IProvider initialize( Map objectInitialize )
        throws JrafConfigException
    {

        return initialize();
    }

    /**
     * This method return an instance of the javamail provider
     * 
     * @return The return an instance of JavaMailProviderImpl
     * @throws JrafConfigException Exception happened during the initialization
     */
    public IProvider initialize()
        throws JrafConfigException
    {
        IProvider mailer = new JavaMailProviderImpl();
        return mailer;
    }

    /**
     * Method used for control the good initialization
     */
    public void afterPropertiesSet()
    {

    }

}
