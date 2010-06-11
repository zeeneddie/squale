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
package org.squale.squalerest.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Application class used for the export
 */
@XStreamAlias( "application" )
public class ApplicationRest
{

    /**
     * Id of the application
     */
    @XStreamAsAttribute
    private String id;

    /**
     * Name of the application
     */
    @XStreamAsAttribute
    private String name;

    /**
     * The audit exported
     */
    private AuditRest audit;

    /**
     * List of modules ({@link ModuleRest}) linked to the application
     */
    @XStreamImplicit
    private List<ModuleRest> modules;

    /**
     * Constructor
     */
    public ApplicationRest()
    {
        modules = new ArrayList<ModuleRest>();
    }

    /**
     * Full constructor
     * 
     * @param pId Id of the application
     * @param pName Name of the application
     * @param pAudit The audit {@link AuditRest}
     */
    public ApplicationRest( String pId, String pName, AuditRest pAudit )
    {
        id = pId;
        name = pName;
        audit = pAudit;
        modules = new ArrayList<ModuleRest>();
    }

    /**
     * Getter method for the attribute id
     * 
     * @return The id of the application
     */
    public String getId()
    {
        return id;
    }

    /**
     * Setter method for the attribute id
     * 
     * @param pId The new id of the application
     */
    public void setId( String pId )
    {
        id = pId;
    }

    /**
     * Add a new module({@link ModuleRest} to the application
     * 
     * @param module The new module to add
     */
    public void addModule( ModuleRest module )
    {
        modules.add( module );
    }

    /**
     * Getter method for the attribute name
     * 
     * @return The application name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter method for the attribute name
     * 
     * @param pName The new name of the application
     */
    public void setName( String pName )
    {
        name = pName;
    }

    /**
     * Getter method for the attribute modules
     * 
     * @return The list of moduleEx
     */
    public List<ModuleRest> getModules()
    {
        return modules;
    }

    /**
     * Setter method for the attribute modules
     * 
     * @param pModules The new list of moduleEx
     */
    public void setModules( List<ModuleRest> pModules )
    {
        modules = pModules;
    }

    /**
     * Getter method for the attribute audit
     * 
     * @return The audit
     */
    public AuditRest getAudit()
    {
        return audit;
    }

    /**
     * Setter method for the attribute audit
     * 
     * @param pAudit The audit to add
     */
    public void setAudit( AuditRest pAudit )
    {
        audit = pAudit;
    }

}