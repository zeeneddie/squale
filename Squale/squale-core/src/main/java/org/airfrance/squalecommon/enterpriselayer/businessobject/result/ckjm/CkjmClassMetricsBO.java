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
package com.airfrance.squalecommon.enterpriselayer.businessobject.result.ckjm;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MetricBO;

/**
 * Business object of the metric of CKJM
 * @hibernate.subclass discriminator-value="CkjmClassMetrics"
 */
public class CkjmClassMetricsBO
    extends MeasureBO
{
    /**
     * Weighted methods per class
     */
    private static final  String WMC = "wmc";
    
    /**
     * Depth of Inheritance Tree
     */
    private static final String DIT = "dit";
    
    /**
     * Number of Children
     */
    private static final String NOC = "noc";
    
    /**
     *  Coupling between objects 
     */
    private static final String CBO = "cbo";
    
    
    /**
     * Response for a Class
     */    
    private static final String RFC = "rfc";
    
    /**
     * Lack of cohesion in methods
     */
    private static final String LCOM = "lcom";

    /**
     * Afferent couplings
     */
    private static final String CA = "ca";
    
    /**
     * Number of Public Methods
     */
    private static final String NPM = "npm";

    
    /**
     * Default constructor
     */
    public CkjmClassMetricsBO()
    {
        super();
        getMetrics().put( WMC, new IntegerMetricBO() );
        getMetrics().put( DIT, new IntegerMetricBO() );
        getMetrics().put( NOC, new IntegerMetricBO() );
        getMetrics().put( CBO, new IntegerMetricBO() );
        getMetrics().put( RFC, new IntegerMetricBO() );
        getMetrics().put( LCOM, new IntegerMetricBO() );
        getMetrics().put( CA, new IntegerMetricBO() );
        getMetrics().put( NPM, new IntegerMetricBO() );
    }

    

    
    
    /**
     * Getter method for the WMC metric
     * 
     * @return The value of WMC
     */
    public Integer getWmc()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( WMC ) ).getValue();
    }

    /**
     * Setter method for the WMC metric
     * 
     * @param pWmc The new WMC value
     */
    public void setWmc( int pWmc )
    {
        ( (IntegerMetricBO) getMetrics().get( WMC ) ).setValue( new Integer( pWmc ) );
    }
    
    /**
     * Getter method for the DIT metric
     * 
     * @return The value of DIT
     */
    public Integer getDit()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( DIT ) ).getValue();
    }

    /**
     * Setter method for the DIT metric
     * 
     * @param pDit The new value of DIT
     */
    public void setDit( int pDit )
    {
        ( (IntegerMetricBO) getMetrics().get( DIT ) ).setValue( new Integer( pDit ) );
    }
    
    /**
     * Getter method for the NOC metric
     * 
     * @return The value of NOC
     */
    public Integer getNoc()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NOC ) ).getValue();
    }

    /**
     * Setter method for the NOC metric
     * 
     * @param pNoc The new value of NOC
     */
    public void setNoc( int pNoc )
    {
        ( (IntegerMetricBO) getMetrics().get( NOC ) ).setValue( new Integer( pNoc ) );
    }
    
    /**
     * Getter method for the CBO metric
     * 
     * @return The value of CBO
     */
    public Integer getCbo()
    {
        return (Integer) ( (MetricBO) getMetrics().get( CBO ) ).getValue();
    }

    /**
     * Setter method for the CBO metric
     * 
     * @param pCbo The new value of CBO
     */
    public void setCbo( int pCbo )
    {
        ( (IntegerMetricBO) getMetrics().get( CBO ) ).setValue( new Integer( pCbo ) );
    }
    
    /**
     * Getter method for the RFC metric
     * 
     * @return The value of RFC
     */
    public Integer getRfc()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( RFC ) ).getValue();
    }

    /**
     * Setter method for the RFC metric
     * 
     * @param pRfc The new value of RFC
     */
    public void setRfc( int pRfc )
    {
        ( (IntegerMetricBO) getMetrics().get( RFC ) ).setValue( new Integer( pRfc ) );
    }
    
    /**
     * Getter method for the LCOM metrics
     * 
     * @return The value of LCOM
     */
    public Integer getLcom()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( LCOM ) ).getValue();
    }

    /**
     * Setter method for the LCOM metrics
     * 
     * @param pLcom The new value of LCOM
     */
    public void setLcom( int pLcom )
    {
        ( (IntegerMetricBO) getMetrics().get( LCOM ) ).setValue( new Integer( pLcom ) );
    }
    
    /**
     * Getter method for the CA metric
     * 
     * @return The value of CA
     */
    public Integer getCa()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( CA ) ).getValue();
    }

    /**
     * Setter method for the CA metric
     * 
     * @param pCa The new value of CA
     */
    public void setCa( int pCa )
    {
        ( (IntegerMetricBO) getMetrics().get( CA ) ).setValue( new Integer( pCa ) );
    }
    
    /**
     * Getter method for the NPM metric
     * 
     * @return The value of NPM
     */
    public Integer getNpm()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NPM ) ).getValue();
    }

    /**
     * Setter method for the NPM metric
     * 
     * @param pNpm The new value of NPM
     */
    public void setNpm( int pNpm )
    {
        ( (IntegerMetricBO) getMetrics().get( NPM ) ).setValue( new Integer( pNpm ) );
    }
    
}
