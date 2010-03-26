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
package org.squale.squalecommon.datatransfertobject.component;

import java.util.ArrayList;
import java.util.List;

import org.squale.jraf.spi.dto.IDTO;

/**
 * This class is a light DTO for the application. It contains the name and the technical id of the application. It also
 * contains the list of module linked to the application
 */
public class ApplicationLightDTO
    implements IDTO, Comparable<ApplicationLightDTO>
{

    /**
     * The technical id of the application
     */
    private long technicalId;

    /**
     * The name of the application
     */
    private String name;

    /**
     * The list of module linked to the application
     */
    private List<ModuleLightDTO> moduleList = new ArrayList<ModuleLightDTO>();

    /**
     * Constructor
     */
    public ApplicationLightDTO()
    {
    }

    /**
     * Constructor
     * 
     * @param pTechnicalId The technical id of the application
     * @param pName The name of the application
     */
    public ApplicationLightDTO( long pTechnicalId, String pName )
    {
        technicalId = pTechnicalId;
        name = pName;
    }

    /**
     * Getter method for the attribute technicalId
     * 
     * @return The technical id of the application
     */
    public long getTechnicalId()
    {
        return technicalId;
    }

    /**
     * Setter method for the attribute technicalId
     * 
     * @param pTechnicalId The technical id of the application
     */
    public void setTechnicalId( long pTechnicalId )
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
    public List<ModuleLightDTO> getModuleList()
    {
        return moduleList;
    }

    /**
     * Add a module to the list of module
     * 
     * @param module the list of module
     */
    public void addModule( ModuleLightDTO module )
    {
        moduleList.add( module );
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo( ApplicationLightDTO o )
    {
        return this.name.compareToIgnoreCase( o.name );
    }

}
