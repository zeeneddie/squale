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
package org.squale.squalecommon.enterpriselayer.businessobject.result.umlquality;

import org.squale.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;

/**
 * @hibernate.subclass discriminator-value="UMLQualityInterfaceMetrics"
 */
public class UMLQualityInterfaceMetricsBO
    extends UMLQualityMetricsBO
{

    /** Number of Ancestors: le nombre d'ancêtres de l'Interface */
    private final static String NUMANC = "numAnc";

    /** Number of Clients: le nombre de classes qui implémentent l'Interface. */
    private final static String NUMCLIENTS = "numClients";

    /** Number of Operations: le nombre d'opérations dans l'Interface. */
    private final static String NUMOPS = "numOps";

    /**
     * Access method for the NumAnc property.
     * 
     * @return the current value of the NumAnc property
     */
    public Integer getNumAnc()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NUMANC ) ).getValue();
    }

    /**
     * Sets the value of the NumAnc property.
     * 
     * @param pNumAnc the new value of the NumAnc property
     */
    public void setNumAnc( Integer pNumAnc )
    {
        ( (IntegerMetricBO) getMetrics().get( NUMANC ) ).setValue( pNumAnc );
    }

    /**
     * Access method for the NumClients property.
     * 
     * @return the current value of the NumClients property
     */
    public Integer getNumClients()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NUMCLIENTS ) ).getValue();
    }

    /**
     * Sets the value of the NumClients property.
     * 
     * @param pNumClients the new value of the NumClients property
     */
    public void setNumClients( Integer pNumClients )
    {
        ( (IntegerMetricBO) getMetrics().get( NUMCLIENTS ) ).setValue( pNumClients );
    }

    /**
     * Access method for the NumOps property.
     * 
     * @return the current value of the NumOps property
     */
    public Integer getNumOps()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NUMOPS ) ).getValue();
    }

    /**
     * Sets the value of the NumOps property.
     * 
     * @param pNumOps the new value of the NumOps property
     */
    public void setNumOps( Integer pNumOps )
    {
        ( (IntegerMetricBO) getMetrics().get( NUMOPS ) ).setValue( pNumOps );
    }

    /**
     * Constructeur par défaut.
     */
    public UMLQualityInterfaceMetricsBO()
    {
        super();
        getMetrics().put( NUMANC, new IntegerMetricBO() );
        getMetrics().put( NUMCLIENTS, new IntegerMetricBO() );
        getMetrics().put( NUMOPS, new IntegerMetricBO() );

    }
}
