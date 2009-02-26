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
package com.airfrance.squalecommon.enterpriselayer.businessobject.result.cobertura;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * Instance of this class stores values regarding the method level metrics (stored in a
 * {@link Collection}. <br />
 * </p>
 * 
 * @hibernate.subclass discriminator-value="CoberturaClassMetrics"
 */
public class CoberturaClassMetricsBO
extends AbstractCoberturaMetricsBO
{
    /** Collection to store packages informations */
    private List<AbstractCoberturaMetricsBO> methods;
    
    
    /** The default constructor */
    public CoberturaClassMetricsBO()
    {
        super();
        methods = new ArrayList<AbstractCoberturaMetricsBO>();
    }
    
    /**
     * This method adds an instance of {@link CoberturaMethodMetricsBO} to the {@link Collection}. Each time a
     * "method" closing tag is faced by the parser this method is called (please refer to the CoberturaParser class for
     * more information).
     * 
     * @param methodBO the method that has to be added to the Collection
     */
    public void addMethod( CoberturaMethodMetricsBO methodBO )
    {
        methods.add( methodBO );
    }
    
    /**
     * Getter to retrieve the collection of packages
     * 
     * @return the Collection of packages level results
     */
    public List<AbstractCoberturaMetricsBO> getMethods()
    {
        return methods;
    }
}
