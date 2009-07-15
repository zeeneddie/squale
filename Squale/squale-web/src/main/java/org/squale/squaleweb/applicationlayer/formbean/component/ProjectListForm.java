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
package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Liste de projets
 * 
 * @author M400842
 */
public class ProjectListForm
    extends RootForm
{
    /**
     * Liste des projets
     */
    private List mList = new ArrayList();

    /**
     * @return la liste des projets
     */
    public List getList()
    {
        return mList;
    }

    /**
     * @param pList la liste des projets
     */
    public void setList( List pList )
    {
        mList = pList;
    }

}
