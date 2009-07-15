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
package org.squale.squalix.util.sourcesrecovering;

import java.util.HashSet;

import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;

/**
 * This class is used for doing the optimization of the source code recovering. Its aim is to verify if the sources have
 * already been recovered. For that we use the path of the source to recovering and we compare to the path of the
 * sources already recovered
 */
public final class SourcesRecoveringOptimisation
{

    /**
     * List of all the path of source code already recovered. Attribute necessary for the optimization of the source code
     * recovering
     */
    private static HashSet<String> pathSourceCodeList;

    /**
     * Default constructor
     */
    private SourcesRecoveringOptimisation()
    {

    }

    /**
     * This method return true if the path has already been recovered
     * 
     * @param path The path for recovering the source code
     * @param appli The application audited
     * @return true if the path has already been recovered
     */
    public static boolean pathAlreadyRecovered( String path, ApplicationBO appli )
    {
        boolean isPresent;
        isPresent = pathSourceCodeList.contains( path );
        return isPresent;
    }

    /**
     * Method for insert a new entry in the list of path of source code already recovered
     * 
     * @param path The path used for recovering the source code
     * @param appli The application audited
     */
    public static void addToPathRecovered( String path, ApplicationBO appli )
    {

        pathSourceCodeList.add( path );

    }

    /**
     * This method put the pathSourceCodeList to null
     */
    public static void reinit()
    {

        pathSourceCodeList = new HashSet<String>();

    }

}
