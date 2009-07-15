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
package com.airfrance.squaleweb.applicationlayer.formbean.results;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * List of components which have some values for some tres
 */
public class ComponentResultListForm
    extends RootForm
{

    /**
     * Serial id
     */
    private static final long serialVersionUID = 1L;

    /**
     * list of components
     */
    private ComponentListForm componentListForm = new ComponentListForm();

    /** keys for tre */
    private String[] treKeys = new String[0];

    /** values of tres */
    private String[] treValues = new String[0];

    /**
     * Getter for components list
     * 
     * @return list of components
     */
    public ComponentListForm getComponentListForm()
    {
        return componentListForm;
    }

    /**
     * Setter for components list
     * 
     * @param pComponentListForm new components
     */
    public void setComponentListForm( ComponentListForm pComponentListForm )
    {
        componentListForm = pComponentListForm;
    }

    /**
     * Getter for keys
     * 
     * @return keys
     */
    public String[] getTreKeys()
    {
        return treKeys;
    }

    /**
     * Setter for keys
     * 
     * @param pTreKeys new keys
     */
    public void setTreKeys( String[] pTreKeys )
    {
        this.treKeys = pTreKeys;
    }

    /**
     * Getter for values
     * 
     * @return values of tres
     */
    public String[] getTreValues()
    {
        return treValues;
    }

    /**
     * Setter for values
     * 
     * @param pTreValues new values
     */
    public void setTreValues( String[] pTreValues )
    {
        this.treValues = pTreValues;
    }

}
