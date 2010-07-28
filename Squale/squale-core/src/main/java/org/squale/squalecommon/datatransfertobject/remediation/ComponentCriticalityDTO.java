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
package org.squale.squalecommon.datatransfertobject.remediation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Dto for the component criticality. Create for the feature remediation by risk.
 * 
 * @author bfranchet
 */
public class ComponentCriticalityDTO
{

    /**
     * The technical id of the component
     */
    private long id;

    /**
     * The name of the component
     */
    private String name;

    /**
     * The full name of the component
     */
    private String fullname;

    /**
     * The type of the component
     */
    private String type;

    /**
     * The criticality of the component
     */
    private float criticality;

    /**
     * Flag to indicate if the component has at least one practice with a mark under 2.0
     */
    private boolean hasBadMark;

    /**
     * List of the practice linked to the component
     */
    private List<PracticeCriticalityDTO> practiceList;

    /**
     * Default constructor
     */
    public ComponentCriticalityDTO()
    {
        practiceList = new ArrayList<PracticeCriticalityDTO>();
    }

    /**
     * Constructor
     * 
     * @param pId The technical id of the component
     * @param pName The name of the component
     * @param pFullname The fullname (application.module.package. ... ) of the component
     * @param pType The type of the component
     */
    public ComponentCriticalityDTO( long pId, String pName, String pFullname, String pType )
    {
        id = pId;
        name = pName;
        fullname = pFullname;
        practiceList = new ArrayList<PracticeCriticalityDTO>();
        type = pType;
    }

    /**
     * Getter method for the attribute id
     * 
     * @return The technical id of the component
     */
    public long getId()
    {
        return id;
    }

    /**
     * Setter method for the attribute id
     * 
     * @param pId The new technical id of the component
     */
    public void setId( long pId )
    {
        id = pId;
    }

    /**
     * Getter method for the attribute name
     * 
     * @return the name of the component
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter method for the attribute name
     * 
     * @param pName The new name of the component
     */
    public void setName( String pName )
    {
        name = pName;
    }

    /**
     * Getter method for the attribute fullname
     * 
     * @return The full name of the component
     */
    public String getFullname()
    {
        return fullname;
    }

    /**
     * Setter method for the attribute fullname
     * 
     * @param pFullname The new full name of the component
     */
    public void setFullname( String pFullname )
    {
        fullname = pFullname;
    }

    /**
     * Getter method for the attribute criticality
     * 
     * @return the criticality of the component
     */
    public float getCriticality()
    {
        return criticality;
    }

    /**
     * Setter method for the attribute criticality
     * 
     * @param pCriticality The new criticality of the component
     */
    public void setCriticality( float pCriticality )
    {
        criticality = pCriticality;
    }

    /**
     * Getter method for the attribute practiceList
     * 
     * @return The list of practice
     */
    public List<PracticeCriticalityDTO> getPracticeList()
    {
        return practiceList;
    }

    /**
     * Add a practice to the list of practice
     * 
     * @param practiceCriticity the practice to add
     */
    public void addPracticeList( PracticeCriticalityDTO practiceCriticity )
    {
        practiceList.add( practiceCriticity );
        if ( practiceCriticity.getMark() < 2.0f )
        {
            hasBadMark = true;
        }
    }

    /**
     * This method returns true if the component has at least one practice with a mark under 2.0
     * 
     * @return true if the component has at least one practice with a mark under 2.0
     */
    public boolean isHasBadMark()
    {
        return hasBadMark;
    }

    /**
     * Getter method for the attribute type
     * 
     * @return The type of the component
     */
    public String getType()
    {
        return type;
    }

    /**
     * Setter method for the attribute type
     * 
     * @param pType the new type of the component
     */
    public void setType( String pType )
    {
        type = pType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append( fullname ).toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( !( obj instanceof ComponentCriticalityDTO ) )
        {
            return false;
        }
        if ( this == obj )
        {
            return true;
        }
        ComponentCriticalityDTO other = (ComponentCriticalityDTO) obj;
        return new EqualsBuilder().append( id, other.id ).isEquals();
    }

}
