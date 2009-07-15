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
 * Bean pour la présentation d'un audit en échec
 */
public class ApplicationErrorForm
    extends RootForm
{

    /**
     * L'id de l'audit concerné
     */
    private long mAuditId;

    /**
     * Liste des ProjectErrorForm
     */
    private List mList = new ArrayList();

    /**
     * @return la liste des applications
     */
    public List getList()
    {
        return mList;
    }

    /**
     * @param pList la liste des applications
     */
    public void setList( List pList )
    {
        mList = pList;
    }

    /**
     * @return l'id de l'audit
     */
    public long getAuditId()
    {
        return mAuditId;
    }

    /**
     * @param pAuditId l'id de laudit
     */
    public void setAuditId( long pAuditId )
    {
        mAuditId = pAuditId;
    }

}
