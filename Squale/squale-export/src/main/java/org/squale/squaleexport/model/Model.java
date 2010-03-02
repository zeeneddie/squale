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
package org.squale.squaleexport.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.squale.squalemodel.definition.ElementType;
import org.squale.squalemodel.definition.Language;
import org.squale.squalemodel.definition.Metric;

/**
 * <p>
 * This class represents the mapping language / element type / metric.
 * </p>
 * <p>
 * For each language it defines which elements type can be used for this language. And for each element type of a
 * language the list of metrics.
 * </p>
 */
public class Model
{
    /**
     * The language mapping
     */
    private HashMap<Language, HashMap<ElementType, ArrayList<Metric>>> languageMapping;

    /**
     * This method create the mapping
     */
    private void initModel()
    {

        languageMapping = new HashMap<Language, HashMap<ElementType, ArrayList<Metric>>>();
        HashMap<ElementType, ArrayList<Metric>> mappingByComponent = javaMapping();
        languageMapping.put( Language.JAVA, mappingByComponent );

    }

    /**
     * This method defines the mapping element type (key) <=> list of metrics (value) for the java language
     * 
     * @return the mapping element type <=> metrics for the java language
     */
    private HashMap<ElementType, ArrayList<Metric>> javaMapping()
    {
        HashMap<ElementType, ArrayList<Metric>> mappingByComponent = new HashMap<ElementType, ArrayList<Metric>>();

        // Level project
        ArrayList<Metric> metricList = new ArrayList<Metric>();
        metricList.add( Metric.LOC );
        metricList.add( Metric.NUMBER_OF_CLASSES );
        mappingByComponent.put( ElementType.MODULE, metricList );

        // Level class
        metricList = new ArrayList<Metric>();
        metricList.add( Metric.LOC );
        metricList.add( Metric.NUMBER_OF_METHODS );
        mappingByComponent.put( ElementType.CLASS, metricList );

        // Level method
        metricList = new ArrayList<Metric>();
        metricList.add( Metric.LOC );
        metricList.add( Metric.VG );
        mappingByComponent.put( ElementType.METHOD, metricList );

        // Level package
        metricList = new ArrayList<Metric>();
        mappingByComponent.put( ElementType.PACKAGE, metricList );

        return mappingByComponent;
    }

    /**
     * This method returns the language mapping
     * 
     * @return The language mapping
     */
    public HashMap<Language, HashMap<ElementType, ArrayList<Metric>>> getLanguageMapping()
    {
        initModel();
        return languageMapping;
    }

}
