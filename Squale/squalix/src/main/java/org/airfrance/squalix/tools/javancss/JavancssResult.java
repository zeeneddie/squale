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
package com.airfrance.squalix.tools.javancss;

import java.util.ArrayList;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.javancss.JavancssClassMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.javancss.JavancssMethodMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.javancss.JavancssPackageMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.javancss.JavancssProjectMetricsBO;

/**
 * Class use in the xml result file parsing for get back the result.
 */
public class JavancssResult
{

    /** List of package Level results */
    private ArrayList packageResult;

    /** List of class level results */
    private ArrayList classesResult;

    /** List of method level results */
    private ArrayList methodsResult;

    /** The project level results */
    private JavancssProjectMetricsBO projectMetrics;

    /**
     * Default constructor
     */
    public JavancssResult()
    {
        packageResult = new ArrayList();
        classesResult = new ArrayList();
        methodsResult = new ArrayList();
        projectMetrics = new JavancssProjectMetricsBO();
    }

    /**
     * Method use for get back the class level results during the parsing of the xml result file
     * 
     * @param name Name of the class
     * @param ncss Value of ncss for the class
     * @param meth Number of method in the class
     * @param clazz Number of class in the class
     * @param jdocs Numbers of javadocs in the class
     */
    public void addClass( String name, Integer ncss, Integer meth, Integer clazz, Integer jdocs )
    {
        JavancssClassMetricsBO classMeasure = new JavancssClassMetricsBO();
        classMeasure.setComponentName( name );
        classMeasure.setNcss( ncss.intValue() );
        classMeasure.setMethods( meth.intValue() );
        classMeasure.setClasses( clazz.intValue() );
        classMeasure.setJavadocs( jdocs.intValue() );
        classesResult.add( classMeasure );
    }

    /**
     * Method use for get back the method level results during the parsing of the xml result file
     * 
     * @param name Name of the method
     * @param ncss Value of the ncss of the method
     * @param ccn Value of the cyclomatic complexity number of the method
     * @param jdocs Number of javadocs of the method
     */
    public void addMethod( String name, Integer ncss, Integer ccn, Integer jdocs )
    {
        JavancssMethodMetricsBO methodsMetrics = new JavancssMethodMetricsBO();
        methodsMetrics.setComponentName( name );
        methodsMetrics.setNcss( ncss.intValue() );
        methodsMetrics.setCcn( ccn.intValue() );
        methodsMetrics.setJavadocs( jdocs.intValue() );
        methodsResult.add( methodsMetrics );

    }

    /**
     * Method use for get back the package level results during the parsing of the xml result file
     * 
     * @param name Name of the package
     * @param clazz Number of class in the package
     * @param meth Number of method in the package
     * @param ncss Value of ncss for the package
     * @param jdocs Number of javadocs in the package
     * @param jdocLines Number of line of javadoc in the package
     * @param singleCommentLines Number of single comments lines in the package
     * @param multiCommentLines Number of multi comments lines in the package
     */
    public void addPackage( String name, Integer clazz, Integer meth, Integer ncss, Integer jdocs, Integer jdocLines,
                            Integer singleCommentLines, Integer multiCommentLines )
    {
        JavancssPackageMetricsBO packageMetrics = new JavancssPackageMetricsBO();
        packageMetrics.setComponentName( name );
        packageMetrics.setClasses( clazz.intValue() );
        packageMetrics.setMethods( meth.intValue() );
        packageMetrics.setNcss( ncss.intValue() );
        packageMetrics.setJavadocs( jdocs.intValue() );
        packageMetrics.setJavadocsLines( jdocLines.intValue() );
        packageMetrics.setSingleCommentsLines( singleCommentLines.intValue() );
        packageMetrics.setMultiCommentsLines( multiCommentLines.intValue() );
        packageResult.add( packageMetrics );
    }

    /**
     * Method use for get back the project level results during the parsing of the xml result file
     * 
     * @param clazz Number of class in the project
     * @param meth Number of method in the project
     * @param ncss Value of ncss for the project
     * @param jdocs Number of javadoc in the project
     * @param jdocLines Number of line of javadoc in the project
     * @param singleCommentLines Number of single comments lines in the project
     * @param multiCommentLines Number of multi comments lines in the project
     */
    public void addProject( Integer clazz, Integer meth, Integer ncss, Integer jdocs, Integer jdocLines,
                            Integer singleCommentLines, Integer multiCommentLines )
    {
        projectMetrics.setClasses( clazz.intValue() );
        projectMetrics.setMethods( meth.intValue() );
        projectMetrics.setNcss( ncss.intValue() );
        projectMetrics.setJavadocs( jdocs.intValue() );
        projectMetrics.setJavadocsLines( jdocLines.intValue() );
        projectMetrics.setSingleCommentsLines( singleCommentLines.intValue() );
        projectMetrics.setMultiCommentsLines( multiCommentLines.intValue() );
    }

    /**
     * Getter for the packageResult attribute
     * 
     * @return The packageResult attribute
     */
    public ArrayList getPackageResult()
    {
        return packageResult;
    }

    /**
     * Setter for the packageResult attribute
     * 
     * @param pPackageResult object to put in packageResult
     */
    public void setPackageResult( ArrayList pPackageResult )
    {
        packageResult = pPackageResult;
    }

    /**
     * Getter for the classesResult attribute
     * 
     * @return The classesResult attribute
     */
    public ArrayList getClassesResult()
    {
        return classesResult;
    }

    /**
     * Setter for the classesResult attribute
     * 
     * @param pClassesResult object to put in classesResult
     */
    public void setClassesResult( ArrayList pClassesResult )
    {
        classesResult = pClassesResult;
    }

    /**
     * Getter for the methodsResult attribute
     * 
     * @return The methodsResult attribute
     */
    public ArrayList getMethodsResult()
    {
        return methodsResult;
    }

    /**
     * Setter for the methodsResult attribute
     * @param pMethodsResult object to put in methodsResult
     */
    public void setMethodsResult( ArrayList pMethodsResult )
    {
        methodsResult = pMethodsResult;
    }

    /**
     * Getter for the projectMetrics attribute 
     * @return The projetcsMetric attribute
     */
    public JavancssProjectMetricsBO getProjectMetrics()
    {
        return projectMetrics;
    }

    /**
     * Setter for the projetcMetrcis attribute
     * @param pProjectMetrics Object to put in projectsMetrics
     */
    public void setProjectMetrics( JavancssProjectMetricsBO pProjectMetrics )
    {
        projectMetrics = pProjectMetrics;
    }
}
