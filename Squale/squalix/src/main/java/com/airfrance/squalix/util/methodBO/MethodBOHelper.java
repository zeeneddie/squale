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
package com.airfrance.squalix.util.methodBO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import com.airfrance.squalix.util.repository.ComponentRepository;

/**
 * This class contains methods useful for handling methodBO
 */
public class MethodBOHelper
{

    /**
     * This method search the correspondence between an object methodBO and objects methodBO already persist. This
     * method do the search when there is not the full name for the arguments of the method to search. For example : the
     * name of the method search is : test(String, String, int) whereas the the good name is : test(java.lang.String,
     * java.lang.String, int) In this case this method will return all the object already persist which seems to : test(
     * *.String, *.String, int). Note that this method also take into account the longFileName of the object method for
     * do the selection. This method suppose that the search in the repository with the complete name
     * (repository.getComponent(...)) has already be done.
     * 
     * @param methBO The object MethodBO to search
     * @param allMethods The collection of object already persist
     * @param repository The repository of existent component
     * @return The list of existent MethodBO corresponding
     */
    public static ArrayList searchMethodBO( MethodBO methBO, Collection allMethods, ComponentRepository repository )
    {
        ArrayList methodMatch = new ArrayList();

        // We search our MethodBO in all the already existent MethodBO
        Iterator it = allMethods.iterator();
        while ( it.hasNext() )
        {
            // We do the comparison by splitting the method name
            MethodBO existentMethod = (MethodBO) it.next();
            {
                if ( repository.compare( methBO.getParent(), existentMethod.getParent() ) )
                {
                    Boolean isMatching = splitSearch( existentMethod, methBO );
                    if ( isMatching )
                    {
                        methodMatch.add( existentMethod );

                    }
                }
            }
        }
        return methodMatch;
    }

    /**
     * This method do the comparison between two MethodBO by splitting them.
     * 
     * @param existentMethod The method already existent
     * @param methBO The method we search
     * @return true if the two method match
     */
    private static boolean splitSearch( MethodBO existentMethod, MethodBO methBO )
    {
        ArrayList splitExist = splitMethod( existentMethod );
        ArrayList splitMeth = splitMethod( methBO );
        boolean isMatching = false;

        if ( ( (String) splitExist.get( 0 ) ).compareTo( ( (String) splitMeth.get( 0 ) ) ) == 0
            && splitExist.size() == splitMeth.size() )
        {
            // Begin from 1 because the first element is the name of the method and not an
            // argument of the method
            isMatching = true;
            int cpt = 1;
            while ( cpt < splitExist.size() && isMatching )
            {
                String methArg = (String) splitMeth.get( cpt );
                String existArg = (String) splitExist.get( cpt );

                if ( !( existArg ).endsWith( "." + methArg ) && existArg.compareTo( methArg ) != 0 )
                {
                    isMatching = false;
                }
                cpt++;
            }
        }
        return isMatching;
    }

    /**
     * This method split the attribute 'name' of a methodBO. The first element of the list return is the name of the
     * method. The other element of the list are the arguments of the method.
     * 
     * @param method The object MethodBO to transform
     * @return A list which contains the name of the method and the arguments of this method
     */
    public static ArrayList splitMethod( MethodBO method )
    {
        ArrayList splitMeth = new ArrayList();
        String methodName = method.getName();
        String tempMeth;
        int placeMeth = methodName.indexOf( "(" );
        // if there is no '( ' in the method name
        if ( placeMeth == -1 )
        {
            splitMeth.add( methodName );
        }
        else
        {
            tempMeth = methodName.substring( 0, placeMeth );
            splitMeth.add( tempMeth );
            tempMeth = methodName.substring( placeMeth + 1, methodName.lastIndexOf( ")" ) );
            String[] methArg = tempMeth.split( "," );
            if ( methArg[0] != null )
            {
                splitMeth.addAll( Arrays.asList( methArg ) );
            }
        }
        return splitMeth;
    }
}
