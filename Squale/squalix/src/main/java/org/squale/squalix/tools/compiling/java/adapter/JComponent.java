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
package org.squale.squalix.tools.compiling.java.adapter;

/**
 * Cette classe représente un "composant" de compilation JAVA, i.e. un parseur ou un compilateur. <br />
 * On utilise le pattern <a href=" http://labo-sun.com/index.jsp?actionId=32&docId=344&page=8" target="_new">Adapteur</a>.
 * 
 * @author m400832
 * @version 1.0
 */
public class JComponent
{

    /**
     * Adapteur des composants de compilation java.
     */
    private JComponentAdapter mComponentAdapter = null;

    /**
     * Constructeur.
     * 
     * @param pComponentAdapter un composant de compilation.
     */
    public JComponent( JComponentAdapter pComponentAdapter )
    {
        mComponentAdapter = pComponentAdapter;
    }

    /**
     * Getter.
     * 
     * @return un composant de compilation.
     */
    public JComponentAdapter getComponent()
    {
        return mComponentAdapter;
    }

}
