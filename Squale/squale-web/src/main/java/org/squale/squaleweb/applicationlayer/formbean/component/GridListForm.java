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
 * Liste des grilles qualité
 */
public class GridListForm
    extends RootForm
{
    /**
     * Liste des grilles
     */
    private List mGrids = new ArrayList();

    /**
     * Liste des noms des grilles qui ne sont liées à aucun profil et à aucun audit
     */
    private List mUnlinkedGrids = new ArrayList();

    /**
     * @return la liste des grilles
     */
    public List getGrids()
    {
        return mGrids;
    }

    /**
     * @return la liste des noms des grilles qui ne sont liées à aucun profil et à aucun audit
     */
    public List getUnlinkedGrids()
    {
        return mUnlinkedGrids;
    }

    /**
     * @param pGrids la liste des grilles
     */
    public void setGrids( List pGrids )
    {
        mGrids = pGrids;
    }

    /**
     * @param pUnlikedGrids la liste des noms des grilles qui ne sont liées à aucun profil et à aucun audit
     */
    public void setUnlinkedGrids( List pUnlikedGrids )
    {
        mUnlinkedGrids = pUnlikedGrids;
    }

}
