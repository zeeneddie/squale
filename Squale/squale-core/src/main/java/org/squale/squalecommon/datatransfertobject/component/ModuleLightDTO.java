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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.squale.jraf.spi.dto.IDTO;
import org.squale.squalecommon.datatransfertobject.result.QualityResultDTO;
import org.squale.squalecommon.datatransfertobject.tag.TagDTO;

/**
 * This class is a light dto for the module (ProjectBO). This dto contains the name and the technical id of the module
 */
public class ModuleLightDTO
    implements IDTO, Comparable<ModuleLightDTO>
{

    /**
     * The module technical id
     */
    private long technicalId;

    /**
     * The module name
     */
    private String name;

    /**
     * The name of the grid linked to the module
     */
    private String gridName;
    
    /**
     * The list of tags linked to the module
     */
    private List<TagDTO> tags;

    /**
     * The list of factor results linked to the module
     */
    private List<QualityResultDTO> factors;

    /**
     * The list of volumetry informations linked to the module
     */
    private Map<String, Integer> volumetry;

    /**
     * Constructor
     */
    public ModuleLightDTO()
    {
        factors = new ArrayList<QualityResultDTO>();
        tags = new ArrayList<TagDTO>();
        volumetry = new HashMap<String, Integer>();
    }

    /**
     * Constructor
     * 
     * @param pTechnicalId The technical id of the module
     * @param pName The name of the module
     */
    public ModuleLightDTO( long pTechnicalId, String pName )
    {
        technicalId = pTechnicalId;
        name = pName;
        factors = new ArrayList<QualityResultDTO>();
        tags = new ArrayList<TagDTO>();
        volumetry = new HashMap<String, Integer>();
    }
    
    /**
     * Constructor
     * 
     * @param pTechnicalId The technical id of the module
     * @param pName The name of the module
     * @param pGridName The grid linked to the module
     */
    public ModuleLightDTO( long pTechnicalId, String pName, String pGridName )
    {
        technicalId = pTechnicalId;
        name = pName;
        gridName = pGridName;
        factors = new ArrayList<QualityResultDTO>();
        tags = new ArrayList<TagDTO>();
        volumetry = new HashMap<String, Integer>();
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
     * @param pTechnicalId The new technical id of the module
     */
    public void setTechnicalId( long pTechnicalId )
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
     * Setter method for the attribute name
     * 
     * @param pName The new name of the application
     */
    public void setName( String pName )
    {
        name = pName;
    }

    /**
     * Getter method for the attribute grid 
     * 
     * @return The grid linked to the module
     */
    public String getGridName()
    {
        return gridName;
    }

    /**
     * Setter method for the attribute grid
     * 
     * @param pGridName The new grid linked to the module
     */
    public void setGridName( String pGridName )
    {
        this.gridName = pGridName;
    }

    /**
     * Getter method for the attribute tags
     * 
     * @return the list of tags linked to the module
     */
    public List<TagDTO> getTags()
    {
        return tags;
    }

    /**
     * Setter method for the attribute
     * 
     * @param pTags The new list of tags linked to the module
     */
    public void setTags( List<TagDTO> pTags )
    {
        tags = pTags;
    }

    /**
     * Getter method for the attribute factors
     * 
     * @return The list of factor results linked to the module
     */
    public List<QualityResultDTO> getFactor()
    {
        return factors;
    }

    /**
     * This method adds the factor given in argument to the list of factor
     * 
     * @param factor Add a new factor results to the list of factors
     */
    public void addFactor( QualityResultDTO factor )
    {
        factors.add( factor );
    }

    /**
     * Getter method for the attribute volumetry
     * 
     * @return the volumetry map
     */
    public Map<String, Integer> getVolumetry()
    {
        return volumetry;
    }

    /**
     * This method add a new volumetry informartion to the volumetry map
     * 
     * @param key The volumetry information name
     * @param value The volumetry information value
     */
    public void putVolumetry( String key, Integer value )
    {
        volumetry.put( key, value );
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo( ModuleLightDTO o )
    {
        return this.name.compareToIgnoreCase( o.name );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof ModuleLightDTO ) )
        {
            return false;
        }
        if ( this == obj )
        {
            return true;
        }
        ModuleLightDTO other = (ModuleLightDTO) obj;
        return new EqualsBuilder().append( technicalId, other.technicalId ).isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append( technicalId ).append( name ).toHashCode();
    }

}
