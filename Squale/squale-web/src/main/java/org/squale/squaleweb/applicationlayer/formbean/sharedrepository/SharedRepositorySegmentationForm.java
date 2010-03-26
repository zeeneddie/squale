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

import org.squale.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Form for the shared repository segmentation
 */
public class SharedRepositorySegmentationForm
    extends RootForm
{

    /**
     * UID
     */
    private static final long serialVersionUID = -8653757830336388017L;

    /**
     * The selected app
     */
    private String appSelected;

    /**
     * The selected module
     */
    private String moduleSelected;

    /**
     * The parent application of the selected module
     */
    private String parentModule;

    /**
     * The list of applications and their linked module
     */
    private List<ApplicationLightForm> appList = new ArrayList<ApplicationLightForm>();

    /**
     * The list of segment and selected segment for the application / module chosen
     */
    private List<SegmentCategoryForm> categoryList = new ArrayList<SegmentCategoryForm>();

    /**
     * Constructor
     */
    public SharedRepositorySegmentationForm()
    {

    }

    public List<ApplicationLightForm> getAppList()
    {
        return appList;
    }

    public void setAppList( List<ApplicationLightForm> pAppList )
    {
        appList = pAppList;
    }

    public List<SegmentCategoryForm> getCategoryList()
    {
        return categoryList;
    }

    public void setCategoryList( List<SegmentCategoryForm> pCategoryList )
    {
        categoryList = pCategoryList;
    }

    public String getAppSelected()
    {
        return appSelected;
    }

    public void setAppSelected( String pAppSelected )
    {
        appSelected = pAppSelected;
    }

    public String getModuleSelected()
    {
        return moduleSelected;
    }

    public void setModuleSelected( String pModuleSelected )
    {
        moduleSelected = pModuleSelected;
    }

    public void setParentModule( String pParentModule )
    {
        parentModule = pParentModule;
    }

    public String getParentModule()
    {
        return parentModule;
    }

    /**
     * This method returns the identifier of the element selected
     * 
     * @return the identifier of the element selected
     */
    public String getElementSelected()
    {
        String elementSelected;
        if ( moduleSelected != null )
        {
            elementSelected = moduleSelected;
        }
        else
        {
            elementSelected = appSelected;
        }
        return elementSelected;
    }

    /**
     * This method initializes the property appSelected and moduleSelected
     */
    public void initElementsSelected()
    {
        appSelected = null;
        moduleSelected = null;
        parentModule = null;
    }

}
