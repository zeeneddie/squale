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
 * Créé le 1 août 05, par m400832.
 */
package com.airfrance.squalix.tools.compiling.java.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Pattern <a href=" http://labo-sun.com/index.jsp?actionId=32&docId=344&page=8" target="_new">Adapteur</a>.
 * 
 * @author m400832
 * @version 1.0
 */
public abstract class JComponentAdapter
{

    /** la liste des erreurs */
    protected List mErrors = new ArrayList();

    /**
     * Méthode commune à tous les composants de compilation JAVA. Elle lance l'action sur ce composant (parsing,
     * compiling).
     * 
     * @throws Exception exception en cas d'erreur.
     */
    public abstract void execute()
        throws Exception;

    /**
     * @return la liste des erreurs
     */
    public List getErrors()
    {
        return mErrors;
    }

    /**
     * @param pList la liste des erreurs
     */
    public void setErrors( List pList )
    {
        mErrors = pList;
    }

}
