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
package com.airfrance.squalecommon.enterpriselayer.businessobject.result.javancss;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;

/**
 * Implementation of measureBO of the method level result for javancss
 * 
 * @hibernate.subclass discriminator-value="JavancssMethodMetrics"
 */
public class JavancssMethodMetricsBO
    extends JavancssMetricsBO

{

    /**
     * The Cyclomatic Complexity Number
     */
    private static final String CCN = "ccn";

    /**
     * Default constructor
     */
    public JavancssMethodMetricsBO()
    {
        super();
        getMetrics().put( CCN, new IntegerMetricBO() );
    }

    /**
     * Get the Cyclomatic Complexity Number for the component
     * 
     * @return The Cyclomatic Complexity Number
     */
    public Integer getCcn()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( CCN ) ).getValue();
    }

    /**
     * Set Cyclomatic Complexity Number for the component
     * 
     * @param pCcn The Cyclomatic Complexity Number
     */
    public void setCcn( int pCcn )
    {
        ( (IntegerMetricBO) getMetrics().get( CCN ) ).setValue( pCcn );
    }

}
