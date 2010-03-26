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
package org.squale.squaleweb.applicationlayer.formbean.sharedrepository;

import java.util.ArrayList;
import java.util.List;

import org.squale.welcom.struts.bean.WActionForm;

/**
 * Struts form for the application 
 */
public class ApplicationLightForm
    extends WActionForm
{

    /**
     * UID
     */
    private static final long serialVersionUID = 1619249170685483744L;

    /**
     * The technical id of the application
     */
    private String technicalId;

    /**
     * The name of the application
     */
    private String name;

    /**
     * The list of module linked to the application
     */
    private List<ModuleLightForm> moduleList = new ArrayList<ModuleLightForm>();

    /**
     * Constructor
     */
    public ApplicationLightForm()
    {
    }

    /**
     * Constructor
     * 
     * @param pTechnicalId The technical id of the application
     * @param pName The name of the application
     */
    public ApplicationLightForm( String pTechnicalId, String pName )
    {
        technicalId = pTechnicalId;
        name = pName;
    }

    /**
     * Getter method for the attribute technicalId
     * 
     * @return The technical id of the application
     */
    public String getTechnicalId()
    {
        return technicalId;
    }

    /**
     * Setter method for the attribute technicalId
     * 
     * @param pTechnicalId The technical id of the application
     */
    public void setTechnicalId( String pTechnicalId )
    {
        technicalId = pTechnicalId;
    }

    /**
     * Getter method for the attribute name
     * 
     * @return The name of the attribute
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter method for the attribute name
     * 
     * @param pName The name of the application
     */
    public void setName( String pName )
    {
        name = pName;
    }

    /**
     * Getter method for the attribute moduleList
     * 
     * @return The list of module linked to the application
     */
    public List<ModuleLightForm> getModuleList()
    {
        return moduleList;
    }

    /**
     * Add a module to the list of module
     * 
     * @param module the list of module
     */
    public void addModule( ModuleLightForm module )
    {
        moduleList.add( module );
    }

}
