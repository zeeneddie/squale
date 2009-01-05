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
package com.airfrance.welcom.addons.message;

import org.apache.struts.util.PropertyMessageResourcesFactory;

/**
 * Inspired by code coming from Dirk Bartkowiak, MessageResourcesFactory.java, Jul 21, 2003
 */
public class MessageResourcesFactoryAddons
    extends PropertyMessageResourcesFactory
{

    /**
     * 
     */
    private static final long serialVersionUID = 7092695078186643904L;

    /**
     * @see org.apache.struts.util.MessageResourcesFactory#createResources(String) Factory to provide access to
     *      MessageResource implementation
     */
    public org.apache.struts.util.MessageResources createResources( final String configuration )
    {
        return new MessageResourcesAddons( this, configuration, getReturnNull() );
    }

}