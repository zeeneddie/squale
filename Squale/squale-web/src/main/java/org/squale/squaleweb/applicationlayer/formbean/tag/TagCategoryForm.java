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
package org.squale.squaleweb.applicationlayer.formbean.tag;

import org.squale.squaleweb.applicationlayer.formbean.ActionIdFormSelectable;
import org.squale.welcom.struts.bean.WISelectable;

/**
 * Contient les données indispensables relatives à une application
 * 
 * @author M400842
 */
public class TagCategoryForm
    extends ActionIdFormSelectable implements WISelectable
{

    /**
     * The name of the tag
     */
    private String mName;

    /** 
     * The Tag Category description 
     */
    private String mDescription;
    
    /** 
     * the fact that the tag is selected 
     */
    private boolean mSelected = false;
    
    /**
     * Access method for the mName property.
     * 
     * @return the current value of the mName property
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Sets the value of the mName property.
     * 
     * @param pName the new value of the mName property
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
