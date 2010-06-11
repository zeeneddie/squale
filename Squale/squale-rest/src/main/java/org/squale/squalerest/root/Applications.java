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

import java.util.ArrayList;
import java.util.List;

import org.squale.squalerest.model.ApplicationRest;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Root tag for the response to the rest /applications call
 * 
 * @author bfranchet
 */
@XStreamAlias( "data" )
public class Applications
    implements IRootObject
{
    /**
     * List of applications
     */
    @XStreamImplicit
    private List<ApplicationRest> applications;

    /**
     * Constructor
     */
    public Applications()
    {
        applications = new ArrayList<ApplicationRest>();

    }

    /**
     * Add an application to the list of application
     * 
     * @param application The application to add
     */
    public void addApplication( ApplicationRest application )
    {
        applications.add( application );
    }

    /**
     * Getter method for the attribute applications
     * 
     * @return The list of application
     */
    public List<ApplicationRest> getApplication()
    {
        return applications;
    }

}
