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
 * Instance of this class stores values regarding the package level metrics (stored in a
 * {@link Collection}. <br />
 * </p>
 * 
 * @hibernate.subclass discriminator-value="CoberturaPackageMetrics"
 */
public class CoberturaPackageMetricsBO
    extends AbstractCoberturaMetricsBO
{
    /** Collection to store packages informations */
    private List<AbstractCoberturaMetricsBO> classes;

    /** The default constructor */
    public CoberturaPackageMetricsBO()
    {
        super();
        classes = new ArrayList<AbstractCoberturaMetricsBO>();
    }
    /**
     * This method adds an instance of {@link CoberturaClassMetricsBO} to the {@link Collection}. Each time a
     * "class" closing tag is faced by the parser this method is called (please refer to the CoberturaParser class for
     * more information).
     * 
     * @param classBO the class that has to be added to the Collection
     */
    public void addClass( CoberturaClassMetricsBO classBO )
    {
        classes.add( classBO );
    }
    
    /**
     * Getter to retrieve the collection of classes
     * 
     * @return the Collection of classes level results
     */
    public List<AbstractCoberturaMetricsBO> getClasses()
    {
        return classes;
    }
}
