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
/*
 * Créé le 11 août 05, par M400832.
 */
package com.airfrance.squaleweb.applicationlayer.formbean.creation;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * @author M400832
 * @version 1.0
 */
public class CRightListForm
    extends RootForm
{

    /**
     * Liste de droits.
     */
    private List mRightList = new ArrayList();

    /**
     * @return une liste de droits.
     */
    public List getRightList()
    {
        return mRightList;
    }

    /**
     * @param pRightList une liste de droits.
     */
    public void setRightList( List pRightList )
    {
        mRightList = pRightList;
    }

}
