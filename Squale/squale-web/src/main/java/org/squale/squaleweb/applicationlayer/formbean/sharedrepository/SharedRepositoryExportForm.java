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

import org.squale.squaleweb.applicationlayer.formbean.RootForm;

/**
 * This form includes a list of application {@link SharedRepositoryExportApplicationForm}. All the applications in the
 * list are exportable : that means they have at least one successfull audit Each application is selectable, so that the
 * user can choose the application he wants to export
 */
public class SharedRepositoryExportForm
    extends RootForm
{
    /**
     * UID
     */
    private static final long serialVersionUID = -7877154729526494713L;

    /**
     * The list of selectable applications
     */
    private ArrayList<SharedRepositoryExportApplicationForm> listApp;

    /**
     * Default constructor
     */
    public SharedRepositoryExportForm()
    {
        super();
    }

    /**
     * Getter for the list of selectable applications
     * 
     * @return The list of selectable applications
     */
    public ArrayList<SharedRepositoryExportApplicationForm> getListApp()
    {
        return listApp;
    }

    /**
     * Setter for the list of selecable applications
     * 
     * @param pListApp The new list of selectable application
     */
    public void setListApp( ArrayList<SharedRepositoryExportApplicationForm> pListApp )
    {
        listApp = pListApp;
    }

}
