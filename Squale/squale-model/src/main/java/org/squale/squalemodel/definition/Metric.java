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
package org.squale.squalemodel.definition;

/**
 * This {@link Enum} list all the metric, for java module, we want recover in the export
 */
public enum Metric
{

    /**
     * The number of code lines
     */
    LOC( "loc" ),

    /**
     * The cyclomatic complexity
     */
    VG( "vg" ),
    /**
     * The line rate
     */
    LINE_RATE( "line-rate" ),

    /**
     * The branch rate
     */
    BRANCH_RATE( "branch-rate" ),

    /**
     * The number of methods
     */
    NUMBER_OF_METHODS( "number-of-methods" ),

    /**
     * The number of classes
     */
    NUMBER_OF_CLASSES( "number-of-classes" );

    /**
     * The corresponding xml tag
     */
    private String xmlTag;

    /**
     * The corresponding metric to recover for the currennt instance
     */
    //private String toolMetric;

    /**
     * Constructor
     * 
     * @param pXmlTag The corresponding xml tag (squale-config.xml)
     */
    private Metric( String pXmlTag )
    {
        xmlTag = pXmlTag;
    }

    /**
     * Return the current metric to recover
     * 
     * @return The metric to recover
     */
    /*public String getToolMetric()
    {
        return toolMetric;
    }*/

    /**
     * Set the metric to recover for this element in this current instance
     * 
     * @param pToolMetric The metric to recover
     */
    /*public void setToolMetric( String pToolMetric )
    {
        toolMetric = pToolMetric;
    }*/

    /**
     * Return the corresponding tag for this generic-metric in the squale-config.xml file (see squalix-config.dtd)
     * 
     * @return The corresponding tag
     */
    public String getXmlTag()
    {
        return xmlTag;
    }

}
