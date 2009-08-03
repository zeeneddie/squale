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
package org.squale.squalecommon.util.mapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.enterpriselayer.businessobject.component.ComponentType;

/**
 * Mapping Cette classe permet de faire le lien entre des noms textuels de composant, métrique ou mesures et les classes
 * correspondantes Par exemple, le composant de type classe a le nom component.class et une classe
 * org.squale.squalecommon.enterpriselayer.businessobject.component.ClassBO La mesure ckjm sur les classes porte le nom
 * ckjm.class et a pour classe org.squale.squalecommon.enterpriselayer.businessobject.result.ckjm.CkjmClassMetricsBO La
 * métrique McCabe profondeur d'héritage pour une classe porte le nom mccabe.class.dit et correspond à la classe
 * org.squale.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAClassMetricsBO
 */
public class Mapping
{
    /** log */
    private static Log LOG = LogFactory.getLog( Mapping.class );

    /** Ensemble des composants */
    private static MappingMap component = new MappingMap();

    // Traitement des composants
    static
    {
        component.put( ComponentType.CLASS,
                       org.squale.squalecommon.enterpriselayer.businessobject.component.ClassBO.class );
        component.put( ComponentType.PACKAGE,
                       org.squale.squalecommon.enterpriselayer.businessobject.component.PackageBO.class );
        component.put( ComponentType.METHOD,
                       org.squale.squalecommon.enterpriselayer.businessobject.component.MethodBO.class );
        component.put( ComponentType.PROJECT,
                       org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO.class );
        component.put( ComponentType.APPLICATION,
                       org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO.class );
        component.put( ComponentType.UML_MODEL,
                       org.squale.squalecommon.enterpriselayer.businessobject.component.UmlModelBO.class );
        component.put( ComponentType.UML_CLASS,
                       org.squale.squalecommon.enterpriselayer.businessobject.component.UmlClassBO.class );
        component.put( ComponentType.UML_INTERFACE,
                       org.squale.squalecommon.enterpriselayer.businessobject.component.UmlInterfaceBO.class );
        component.put( ComponentType.UML_PACKAGE,
                       org.squale.squalecommon.enterpriselayer.businessobject.component.UmlPackageBO.class );
        component.put( ComponentType.JSP, org.squale.squalecommon.enterpriselayer.businessobject.component.JspBO.class );
    }

    /** Ensemble des composants mappés avec hibernate */
    private static Map mappingComponent = new HashMap();

    // Traitement des composants
    static
    {
        mappingComponent.put( ComponentType.CLASS, "ClassBO" );
        mappingComponent.put( ComponentType.PACKAGE, "Package" );
        mappingComponent.put( ComponentType.METHOD, "Method" );
        mappingComponent.put( ComponentType.PROJECT, "Project" );
        mappingComponent.put( ComponentType.APPLICATION, "Application" );
        mappingComponent.put( ComponentType.UML_MODEL, "UmlModel" );
        mappingComponent.put( ComponentType.UML_CLASS, "UmlClass" );
        mappingComponent.put( ComponentType.UML_INTERFACE, "UmlInterface" );
        mappingComponent.put( ComponentType.UML_PACKAGE, "UmlPackageBO" );
        mappingComponent.put( ComponentType.JSP, "Jsp" );
    }

    /** Ensemble des mesures */
    private static MappingMap measures = new MappingMap();
    // Traitement des mesures
    static
    {
        // Ckjm
        measures.put( "ckjm.class",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.ckjm.CkjmClassMetricsBO.class );
        // Jdepend
        measures.put(
                      "jdepend.package",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.jdepend.JDependPackageMetricsBO.class );
        // McCabe ################
        measures.put( "mccabe.class",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAClassMetricsBO.class );
        measures.put(
                      "mccabe.method",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAMethodMetricsBO.class );
        measures.put(
                      "mccabe.project",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAProjectMetricsBO.class );
        measures.put( "mccabe.jsp",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAJspMetricsBO.class );
        // RSM
        measures.put( "rsm.class",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.rsm.RSMClassMetricsBO.class );
        measures.put( "rsm.method",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.rsm.RSMMethodMetricsBO.class );
        measures.put( "rsm.project",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.rsm.RSMProjectMetricsBO.class );
        // JSPVolumetry
        measures.put(
                      "jspvolumetry.project",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.jspvolumetry.JSPVolumetryProjectBO.class );
        // Checkstyle
        measures.put(
                      "checkstyle.project",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.rulechecking.CheckstyleTransgressionBO.class );
        // CppTest
        measures.put(
                      "cpptest.project",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.rulechecking.CppTestTransgressionBO.class );
        // Macker
        measures.put(
                      "macker.project",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.rulechecking.MackerTransgressionBO.class );
        // UMLQuality
        measures.put(
                      "umlquality.umlclass",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.umlquality.UMLQualityClassMetricsBO.class );
        measures.put(
                      "umlquality.umlinterface",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.umlquality.UMLQualityInterfaceMetricsBO.class );
        measures.put(
                      "umlquality.umlpackage",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.umlquality.UMLQualityPackageMetricsBO.class );
        measures.put(
                      "umlquality.umlmodel",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.umlquality.UMLQualityModelMetricsBO.class );
        // Cpd
        measures.put(
                      "copypaste.project",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.rulechecking.CpdTransgressionBO.class );
        // Pmd pour Java
        measures.put(
                      "javapmd.project",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.rulechecking.JavaPmdTransgressionBO.class );
        // Pmd pour Jsp
        measures.put(
                      "jsppmd.project",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.rulechecking.JspPmdTransgressionBO.class );
        // Roi
        measures.put( "roi.application",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.roi.RoiMetricsBO.class );
        // PureComments (pour les projets audités avant le lot 3.0)
        measures.put( "purecomments.project",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.misc.CommentsBO.class );
        // BugTracking QC
        measures.put(
                      "extbugtrackingqc.project",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.external.bugtracking.ExtBugTrackingMetricsBO.class );
        // Test manager QC
        measures.put(
                      "testmanagerqc.project",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.external.bugtracking.qc.ExtTestManagerQCMetricsBO.class );
        // Javancss
        measures.put(
                      "javancss.project",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.javancss.JavancssProjectMetricsBO.class );
        measures.put(
                      "javancss.package",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.javancss.JavancssPackageMetricsBO.class );
        measures.put(
                      "javancss.class",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.javancss.JavancssClassMetricsBO.class );
        measures.put(
                      "javancss.method",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.javancss.JavancssMethodMetricsBO.class );

        // Cobertura Code Coverage
        measures.put(
                      "cobertura.project",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.cobertura.CoberturaProjectMetricsBO.class );
        measures.put(
                      "cobertura.package",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.cobertura.CoberturaPackageMetricsBO.class );
        measures.put(
                      "cobertura.class",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.cobertura.CoberturaClassMetricsBO.class );
        measures.put(
                      "cobertura.method",
                      org.squale.squalecommon.enterpriselayer.businessobject.result.cobertura.CoberturaMethodMetricsBO.class );
    }

    /** volumetry name for number of code lines */
    public static final String VOLUMETRY_NB_CODES_LINES = "volumetry_nb_codes_lines";
    
    /** volumetry name for number of code lines for JSP*/
    public static final String VOLUMETRY_NB_CODES_LINES_JSP = "volumetry_nb_codes_lines_JSP";

    /** Volumetry name for methods */
    public static final String VOLUMETRY_METHODS = "volumetry_methods";

    /** Volumetry name for classes */
    public static final String VOLUMETRY_CLASSES = "volumetry_classes";

    /**
     * Map metrics name (key) - volumetry name (value). This map links the metrics name of the tool to a generic metric
     * name (volumetry name)
     */
    private static Properties VOLUMETRY = new Properties();

    /**
     * Fill the map VOLUMETRY with the information from the file :
     * org/squale/squalecommon/util/mapping/mapping_volumetry_metrics.properties
     */
    static
    {
        try
        {
            InputStream stream = Mapping.class.getResourceAsStream( "mapping_volumetry_metrics.properties" );
            VOLUMETRY.load( stream );
        }
        catch ( IOException e )
        {
            LOG.fatal( MappingMessages.getString( "exception.fatal.loading_file" ) );
        }
    }

    /**
     * Obtention de la classe correspondant à une mesure
     * 
     * @param pMeasure mesure (par exemple ckjm.class)
     * @return classe correspondante ou null si non trouvée
     */
    static public Class getMeasureClass( String pMeasure )
    {
        Class result = measures.getClassNameForName( pMeasure );
        if ( result == null )
        {
            LOG.error( "Measure '" + pMeasure + "' unknown" );
        }
        return result;
    }

    /**
     * Obtention du nom de la mesure pour une classe de mesure
     * 
     * @param pMeasureClass classe de mesure
     * @return nom de la mesure
     */
    static public String getMeasureName( Class pMeasureClass )
    {
        String result = measures.getNameForClass( pMeasureClass );
        if ( result == null )
        {
            LOG.error( "Measure class name '" + pMeasureClass.getName() + "' is unknown" );
        }
        return result;
    }

    /**
     * Obtention de la classe pour un composant
     * 
     * @param pComponent composant
     * @return classe correspondante
     */
    static public Class getComponentClass( String pComponent )
    {
        Class result = component.getClassNameForName( pComponent );
        if ( result == null )
        {
            LOG.error( "Component type '" + pComponent + "' unknown" );
        }
        return result;
    }

    /**
     * Obtention du nom symbolique d'un composant
     * 
     * @param pComponentClass classe du composant
     * @return nom symbolique de composant
     */
    static public String getComponentName( Class pComponentClass )
    {
        String result = component.getNameForClass( pComponentClass );
        if ( result == null )
        {
            LOG.error( "Component class name '" + pComponentClass + "' is unknown" );
        }
        return result;
    }

    /**
     * Obtention de la classe de la mesure pour une métrique
     * 
     * @param pMetric métrique (par exemple mccabe.class.dit)
     * @return classe correspondante
     * @throws IllegalArgumentException si pMetric mal formé
     */
    public static Class getMetricClass( String pMetric )
        throws IllegalArgumentException
    {
        int index = pMetric.lastIndexOf( '.' );
        if ( index < 0 )
        {
            throw new IllegalArgumentException( "Metric name should be named tool.component.name" );
        }
        // Inutile de faire un test supplémentaire
        return getMeasureClass( pMetric.substring( 0, index ) );
    }

    /**
     * Obtention du getter d'une métrique La méthode est recherchée dans la classe correspondante, la méthode est du
     * type getXxxx
     * 
     * @param pMetric métrique (par exemple mccabe.class.dit)
     * @return méthode d'accès à cette métrique (par exemple McCabeQAClassMetricsBO.getDit)
     * @throws IllegalArgumentException si pMetric mal formé
     */
    public static Method getMetricGetter( String pMetric )
        throws IllegalArgumentException
    {
        Method result = null;
        int index = pMetric.lastIndexOf( '.' );
        if ( index < 0 )
        {
            throw new IllegalArgumentException( "Metric name should be named tool.component.name" );
        }
        String measure = pMetric.substring( 0, index );
        String metric = pMetric.substring( index + 1 );
        // Obtention de la classe
        Class cl = getMeasureClass( measure );
        // Un log d'erreur aura été fait par la méthode
        // auparavant, inutile d'engorger le log
        if ( cl != null )
        {
            try
            {
                // On forme le nom du getter
                StringBuffer methodName = new StringBuffer( metric );
                // Respect de la norme javabean pour le caractère en majuscules
                methodName.setCharAt( 0, Character.toTitleCase( methodName.charAt( 0 ) ) );
                methodName.insert( 0, "get" );
                result = cl.getMethod( methodName.toString(), null );
            }
            catch ( SecurityException e )
            {
                LOG.error( "Getter not found " + pMetric, e );
            }
            catch ( NoSuchMethodException e )
            {
                LOG.error( "Getter not found " + pMetric, e );
            }
        }
        return result;
    }

    /**
     * @param pType type du composant recherché
     * @return le nom du mapping hibernate correspondant à la classe du composant
     */
    public static String getComponentMappingName( String pType )
    {
        return (String) mappingComponent.get( pType );
    }

    /**
     * This method return the generic metric name linked to the tool metric name passed in argument. The method return
     * null if there is no match.
     * 
     * @param treName The tool metric name
     * @return The generic metric name linked to the tool metric name
     */
    public static String getVolumetryType( String treName )
    {
        String type = VOLUMETRY.getProperty( treName, "" );
        return type;
    }

}
