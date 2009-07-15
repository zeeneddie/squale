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
package com.airfrance.squaleweb.applicationlayer.formbean.tag;

import com.airfrance.squaleweb.applicationlayer.formbean.ActionIdFormSelectable;
import com.airfrance.welcom.struts.bean.WISelectable;

/**
 * Contient les données indispensables relatives à une application
 * 
 * @author M400842
 */
public class TagForm
    extends ActionIdFormSelectable implements WISelectable
{

    /** the name of the Tag */
    private String mName;

    /** the description of the tag */
    private String mDescription;
    
    /** the Tag Category */
    private TagCategoryForm mCategoryForm = new TagCategoryForm();
    
    /** the fact that the tag is selected */
    private boolean mSelected = false;

    /**
     * Redefinition of the hashCode method
     * {@inheritDoc} 
     * @return return the hash number of the object
     */
    public int hashCode()
    {
        return super.hashCode();
    }

    /**
     * Compares two tags
     * @see java.lang.Object#equals(java.lang.Object)
     * @param obj l'objet à comparer
     * @return true si obj=this, false sinon
     */
    public boolean equals( Object obj )
    {
        boolean result = false;
        if ( obj instanceof TagForm )
        {
            TagForm compare = (TagForm) obj;
            if ( null != this.getName() )
            {
                result = this.getName().equals( compare.getName() );
            }
        }
        return result;
    }

    /**
     * Access method for the mName property.
     * @return the name of the Tag
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Sets the value of the mName property.
     * @param pName the name given to the tag
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Access method for the mDescription property.
     * @return le nom raccourcis
     */
    public String getDescription()
    {
        return mDescription;
    }

    /**
     * Sets the value of the mDescription property.
     * @param pDescription le nom raccourcis
     */
    public void setDescription( String pDescription )
    {
        mDescription = pDescription;
    }
    
    /**
     * Access method for the mCategoryForm property.
     * @return the category of the Tag
     */
    public TagCategoryForm getCategoryForm()
    {
        return mCategoryForm;
    }

    /**
     * Sets the value of the mCategoryForm property.
     * @param pCategoryForm the Category given to the tag
     */
    public void setCategoryForm( TagCategoryForm pCategoryForm )
    {
        mCategoryForm = pCategoryForm;
    }

    /**
     * Access method for the mSelected property
     * @return wether the checkbox is selected or not 
     */
    public boolean isSelected()
    {
        return mSelected;
    }

    /**
     * Sets the value of the checkbox
     * @param pSelected wether the checkbox need to be checked or not
     */
    public void setSelected( boolean pSelected )
    {
        mSelected = pSelected;
    }
}
