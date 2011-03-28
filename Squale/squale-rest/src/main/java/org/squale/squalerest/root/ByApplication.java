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
package org.squale.squalerest.root;

import org.squale.squalerest.model.ApplicationRest;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Root XStream object for the response to the rest /application/id call
 * 
 * @author bfranchet
 */
@XStreamAlias( "data" )
public class ByApplication
    implements IRootObject
{

    /**
     * The application
     */
    @XStreamAlias("application")
    private ApplicationRest application;
    
    /**
     * Constructor
     */
    public ByApplication()
    {
        
    }

    /**
     * Setter method for the attribute application
     * 
     * @param pApplication The application
     */
    public void setApplication( ApplicationRest pApplication )
    {
        application = pApplication;
    }

    /**
     * Getter method for the attribute application
     * 
     * @return The application
     */
    public ApplicationRest getApplication()
    {
        return application;
    }
   
}
