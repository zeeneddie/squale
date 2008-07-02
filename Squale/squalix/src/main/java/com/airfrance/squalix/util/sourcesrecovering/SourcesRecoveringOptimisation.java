package com.airfrance.squalix.util.sourcesrecovering;

import java.util.HashSet;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;

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
