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

import org.squale.welcom.struts.bean.WActionForm;

/**
 * Light forms for module
 */
public class ModuleLightForm
    extends WActionForm
{

    /**
     * UID
     */
    private static final long serialVersionUID = 631718026625550991L;

    /**
     * The module technical id
     */
    private String technicalId;

    /**
     * The module name
     */
    private String name;

    /**
     * Constructor
     */
    public ModuleLightForm()
    {

    }

    /**
     * Constructor
     * 
     * @param pTechnicalId The technical id of the module
     * @param pName The name of the module
     */
    public ModuleLightForm( String pTechnicalId, String pName )
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
     * @param pTechnicalId The new technical id of the module
     */
    public void setTechnicalId( String pTechnicalId )
    {
        technicalId = pTechnicalId;
    }

    /**
     * Getter method for the attribute name
     * 
     * @return The name of the application
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter method for the attribute
     * 
     * @param pName The new name of the application
     */
    public void setName( String pName )
    {
        name = pName;
    }

}
