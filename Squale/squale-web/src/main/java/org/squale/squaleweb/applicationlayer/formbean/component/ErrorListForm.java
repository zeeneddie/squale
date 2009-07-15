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
package org.squale.squaleweb.applicationlayer.formbean.component;

import java.util.ArrayList;
import java.util.List;

import org.squale.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Contient les listes d'erreurs pour une tache donnée
 * 
 * @author M400842
 */
public class ErrorListForm
    extends RootForm
{

    /**
     * Liste des erreurs
     */
    private List mList = new ArrayList();

    /**
     * le nom de la tache
     */
    private String mTaskName;

    /**
     * le niveau maximal d'erreur pour cette tache Sert notamment pour mettre l'icone de niveau de criticité sur la page
     * Web
     */
    private String mMaxErrorLevel;

    /**
     * @return la liste des erreurs
     */
    public List getList()
    {
        return mList;
    }

    /**
     * @param pList la liste des erreurs
     */
    public void setList( List pList )
    {
        mList = pList;
    }

    /**
     * @return le nom de la tache pour laquelle on a cette liste d'erreurs
     */
    public String getTaskName()
    {
        return mTaskName;
    }

    /**
     * @param pTaskName le nouveau nom de la tache
     */
    public void setTaskName( String pTaskName )
    {
        mTaskName = pTaskName;
    }

    /**
     * @return le niveau d'erreur max
     */
    public String getMaxErrorLevel()
    {
        return mMaxErrorLevel;
    }

    /**
     * @param pMaxErrorLevel le nouveau niveau d'erreur max
     */
    public void setMaxErrorLevel( String pMaxErrorLevel )
    {
        mMaxErrorLevel = pMaxErrorLevel;
    }

}
