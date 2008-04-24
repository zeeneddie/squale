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
     * method take into account the case when there is not the full name for the arguments of the method to search. For
     * example : the name of the method search is : test(String, String, int) whereas the the good name is :
     * test(java.lang.String, java.lang.String, int) In this case this method will return all the object already persist
     * which seems to : test( *.String, *.String, int). Note that this method also take into account the longFuileName
     * of the object method for do the selection.
     * 
     * @param methBO The object MethodBO to search
     * @param allMethods The collection of object already persist
     * @return The list of existent MethodBO corresponding
     */
    public static ArrayList searchMethodBO( MethodBO methBO, Collection allMethods, ComponentRepository repository )
    {
        ArrayList methodMatch = new ArrayList();
        Iterator it = allMethods.iterator();
        while ( it.hasNext() )
        {
            MethodBO existentMethod = (MethodBO) it.next();
            if (repository.compare( methBO, existentMethod ))  
//methBO.getLongFileName().compareTo( existentMethod.getLongFileName() ) == 0 )
            {
                ArrayList splitExist = splitMethod( existentMethod );
                ArrayList splitMeth = splitMethod( methBO );

                if ( ( (String) splitExist.get( 0 ) ).compareTo( ( (String) splitMeth.get( 0 ) ) ) == 0 )
                {

                    if ( splitExist.size() == splitMeth.size() )
                    {
                        // Begin from 1 because the first element is the name of the method and not an
                        // argument of the method
                        int cpt = 1;
                        boolean isOk = true;
                        while ( cpt < splitExist.size() && isOk )
                        {
                            String methArg = (String) splitMeth.get( cpt );
                            String existArg = (String) splitExist.get( cpt );

                            if ( !( existArg ).endsWith( "." + methArg ) && existArg.compareTo( methArg ) != 0 )
                            {
                                isOk = false;
                            }
                            cpt++;
                        }
                        if ( isOk )
                        {
                            methodMatch.add( existentMethod );
                        }
                    }
                }
            }
        }
        return methodMatch;
    }

    /**
     * This method split the attribute name of a methodBO. The first element of the list return is the name of the
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
