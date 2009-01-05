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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\businessobject\\result\\mccabe\\McCabeClassMetricsBO.java

package com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.BooleanMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;

/**
 * @author m400842 (by rose)
 * @version 1.0
 * @hibernate.subclass discriminator-value="McCabeQAClassMetrics"
 */
public final class McCabeQAClassMetricsBO
    extends McCabeQAMetricsBO
{
    /**
     * Contient le nombre de ligne de commentaires
     */
    private final static String CLOC = "cloc";

    /**
     * Contient le nombre de ligne de code
     */
    private final static String SLOC = "sloc";

    /**
     * Maximum de V(g) sur la classe
     */
    private final static String MAXVG = "maxvg";

    /**
     * Nombre de méthodes propres et de méthodes héritées
     */
    private final static String RFC = "rfc";

    /**
     * Nombre de méthodes implémentées dans la classe
     */
    private final static String WMC = "wmc";

    /**
     * Profondeur d'héritage
     */
    private final static String DIT = "dit";

    /**
     * Couplage
     */
    private final static String CBO = "cbo";

    /**
     * Manque de cohésion (pourcentage)
     */
    private final static String LOCM = "locm";

    /**
     * Nombre de classes directement dérivées de celle-ci
     */
    private final static String NOC = "noc";

    /**
     * Somme des v(g) des méthodes de la classe
     */
    private final static String SUMVG = "sumvg";

    /**
     * Somme des v(g) des méthodes de la classe
     */
    private final static String SUMIVG = "sumivg";

    /**
     * Définit si la classe dépend de ses enfants.
     */
    private final static String DEPENDSUPONCHILD = "dependsUponChild";

    /**
     * Access method for the mMaxvg property.
     * 
     * @return the current value of the mMaxvg property
     */
    public Integer getMaxvg()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( MAXVG ) ).getValue();
    }

    /**
     * Sets the value of the mMaxvg property.
     * 
     * @param pMaxvg the new value of the mMaxvg property
     * @roseuid 42C416B20089
     */
    public void setMaxvg( Integer pMaxvg )
    {
        ( (IntegerMetricBO) getMetrics().get( MAXVG ) ).setValue( pMaxvg );
    }

    /**
     * Access method for the mRfc property.
     * 
     * @return the current value of the mRfc property
     */
    public Integer getRfc()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( RFC ) ).getValue();
    }

    /**
     * Sets the value of the mRfc property.
     * 
     * @param pRfc the new value of the mRfc property
     * @roseuid 42C416B20145
     */
    public void setRfc( Integer pRfc )
    {
        ( (IntegerMetricBO) getMetrics().get( RFC ) ).setValue( pRfc );
    }

    /**
     * Access method for the mWmc property.
     * 
     * @return the current value of the mWmc property
     */
    public Integer getWmc()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( WMC ) ).getValue();
    }

    /**
     * Sets the value of the mWmc property.
     * 
     * @param pWmc the new value of the mWmc property
     * @roseuid 42C416B20200
     */
    public void setWmc( Integer pWmc )
    {
        ( (IntegerMetricBO) getMetrics().get( WMC ) ).setValue( pWmc );
    }

    /**
     * Access method for the mDit property.
     * 
     * @return the current value of the mDit property
     */
    public Integer getDit()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( DIT ) ).getValue();
    }

    /**
     * Sets the value of the mDit property.
     * 
     * @param pDit the new value of the mDit property
     * @roseuid 42C416B202BC
     */
    public void setDit( Integer pDit )
    {
        ( (IntegerMetricBO) getMetrics().get( DIT ) ).setValue( pDit );
    }

    /**
     * Access method for the mCbo property.
     * 
     * @return the current value of the mCbo property
     */
    public Integer getCbo()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( CBO ) ).getValue();
    }

    /**
     * Sets the value of the mCbo property.
     * 
     * @param pCbo the new value of the mCbo property
     */
    public void setCbo( Integer pCbo )
    {
        ( (IntegerMetricBO) getMetrics().get( CBO ) ).setValue( pCbo );
    }

    /**
     * Access method for the mLocm property.
     * 
     * @return the current value of the mLocm property
     * @roseuid 42C416B302FA
     */
    public Integer getLocm()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( LOCM ) ).getValue();
    }

    /**
     * Sets the value of the mLocm property.
     * 
     * @param pLocm the new value of the mLocm property
     * @roseuid 42C416B3030A
     */
    public void setLocm( Integer pLocm )
    {
        ( (IntegerMetricBO) getMetrics().get( LOCM ) ).setValue( pLocm );
    }

    /**
     * Access method for the mNoc property.
     * 
     * @return the current value of the mNoc property
     * @roseuid 42C416B303A6
     */
    public Integer getNoc()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NOC ) ).getValue();
    }

    /**
     * Sets the value of the mNoc property.
     * 
     * @param pNoc the new value of the mNoc property
     * @roseuid 42C416B400A8
     */
    public void setNoc( Integer pNoc )
    {
        ( (IntegerMetricBO) getMetrics().get( NOC ) ).setValue( pNoc );
    }

    /**
     * Access method for the mSumvg property.
     * 
     * @return the current value of the mSumvg property
     * @roseuid 42C416B40135
     */
    public Integer getSumvg()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( SUMVG ) ).getValue();
    }

    /**
     * Sets the value of the mSumvg property.
     * 
     * @param pSumvg the new value of the mSumvg property
     * @roseuid 42C416B40164
     */
    public void setSumvg( Integer pSumvg )
    {
        ( (IntegerMetricBO) getMetrics().get( SUMVG ) ).setValue( pSumvg );
    }

    /**
     * Access method for the mSumivg property.
     * 
     * @return the current value of the mSumivg property
     * @roseuid 42C416B40135
     */
    public Integer getSumivg()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( SUMIVG ) ).getValue();
    }

    /**
     * Sets the value of the mSumivg property.
     * 
     * @param pSumivg the new value of the mSumivg property
     * @roseuid 42C416B40164
     */
    public void setSumivg( Integer pSumivg )
    {
        ( (IntegerMetricBO) getMetrics().get( SUMIVG ) ).setValue( pSumivg );
    }

    /**
     * Constructeur par défaut.
     * 
     * @roseuid 42CA76660217
     */
    public McCabeQAClassMetricsBO()
    {
        super();
        getMetrics().put( CLOC, new IntegerMetricBO() );
        getMetrics().put( SLOC, new IntegerMetricBO() );
        getMetrics().put( MAXVG, new IntegerMetricBO() );
        getMetrics().put( RFC, new IntegerMetricBO() );
        getMetrics().put( WMC, new IntegerMetricBO() );
        getMetrics().put( DIT, new IntegerMetricBO() );
        getMetrics().put( CBO, new IntegerMetricBO() );
        getMetrics().put( LOCM, new IntegerMetricBO() );
        getMetrics().put( NOC, new IntegerMetricBO() );
        getMetrics().put( SUMVG, new IntegerMetricBO() );
        getMetrics().put( SUMIVG, new IntegerMetricBO() );
        getMetrics().put( DEPENDSUPONCHILD, new BooleanMetricBO() );
        ;
    }

    /**
     * Access method for the mDependsUponChild property.
     * 
     * @return the current value of the mDependsUponChild property
     * @roseuid 42E621E80185
     */
    public Boolean getDependsUponChild()
    {
        return (Boolean) ( (BooleanMetricBO) getMetrics().get( DEPENDSUPONCHILD ) ).getValue();
    }

    /**
     * Sets the value of the mDependsUponChild property.
     * 
     * @param pDependsUponChild the new value of the mDependsUponChild property
     * @roseuid 42E621E80194
     */
    public void setDependsUponChild( Boolean pDependsUponChild )
    {
        ( (BooleanMetricBO) getMetrics().get( DEPENDSUPONCHILD ) ).setValue( pDependsUponChild );
    }

}
