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
 * @hibernate.subclass discriminator-value="UMLQualityClassMetrics"
 */

public class UMLQualityClassMetricsBO
    extends UMLQualityMetricsBO
{

    /** Dependencies as Client: le nombre de dépendances dans lesquelles la classe est cliente. */
    private final static String DEPCLIENT = "depClient";

    /** Dependencies as Supplier: le nombre de dépendances dans lesquelles la classe est fournisseuse. */
    private final static String DEPSUPP = "depSupp";

    /** Depth in the Inheritance Tree: la profondeur de la classe dans la structure d'héritage du modèle. */
    private final static String DIT = "DIT";

    /** Number of Implemented Interfaces: nombre d'Integererfaces implémentées par la classe. */
    private final static String NII = "NII";

    /** Number Of puBlic fileDs: nombre d'attributs publics dans la classe. */
    private final static String NOBD = "NOBD";

    /** Number Of puBlic Methods: nombre de méthodes publiques dans la classe. */
    private final static String NOBM = "NOBM";

    /** Number Of Children: nombre de descendants directs (classes-filles) de la classe. */
    private final static String NOC = "NOC";

    /** Number of Parents: nombre de d'ancêtres directs (classes-mères) de la classe. */
    private final static String NOP = "NOP";

    /** Number of Attributes: nombre d'attributs dans la classe. */
    private final static String NUMATTR = "numAttr";

    /** Number of Operations: nombre d'opérations dans la classe. */
    private final static String NUMOPS = "numOps";

    /** Number of oVeriding Methods: nombre d'opérations de la classe qui redéfinissent une opération héritée. */
    private final static String NVM = "NVM";

    /** Operations Inherited: nombre d'opérations héritées dans la classe. */
    private final static String OPSINH = "opsInh";

    /**
     * Response For Class: nombre d'opérations qui peuvent être invoquées en réaction à un message reçu par une instance
     * de cete classe.
     */
    private final static String RFC = "RFC";

    /**
     * Access method for the mDepClient property.
     * 
     * @return the current value of the mDepClient property
     */
    public Integer getDepClient()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( DEPCLIENT ) ).getValue();
    }

    /**
     * Sets the value of the mDepClient property.
     * 
     * @param pDepClient the new value of the mDepClient property
     */
    public void setDepClient( Integer pDepClient )
    {
        ( (IntegerMetricBO) getMetrics().get( DEPCLIENT ) ).setValue( pDepClient );
    }

    /**
     * Access method for the mDepSupp property.
     * 
     * @return the current value of the mDepSupp property
     */
    public Integer getDepSupp()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( DEPSUPP ) ).getValue();
    }

    /**
     * Sets the value of the mDepSupp property.
     * 
     * @param pDepSupp the new value of the mDepSupp property
     */
    public void setDepSupp( Integer pDepSupp )
    {
        ( (IntegerMetricBO) getMetrics().get( DEPSUPP ) ).setValue( pDepSupp );
    }

    /**
     * Access method for the mDepSupp property.
     * 
     * @return the current value of the mDepSupp property
     */

    public Integer getDIT()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( DIT ) ).getValue();
    }

    /**
     * Sets the value of the DIT property.
     * 
     * @param pDit the new value of the DIT property
     */
    public void setDIT( Integer pDit )
    {
        ( (IntegerMetricBO) getMetrics().get( DIT ) ).setValue( pDit );
    }

    /**
     * Access method for the mNII property.
     * 
     * @return the current value of the mNII property
     */
    public Integer getNII()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NII ) ).getValue();
    }

    /**
     * Sets the value of the mNII property.
     * 
     * @param pNii the new value of the mNII property
     */
    public void setNII( Integer pNii )
    {
        ( (IntegerMetricBO) getMetrics().get( NII ) ).setValue( pNii );
    }

    /**
     * Access method for the mNOBD property.
     * 
     * @return the current value of the mNOBD property
     */
    public Integer getNOBD()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NOBD ) ).getValue();
    }

    /**
     * Sets the value of the mNOBD property.
     * 
     * @param pNobd the new value of the mNOBD property
     */
    public void setNOBD( Integer pNobd )
    {
        ( (IntegerMetricBO) getMetrics().get( NOBD ) ).setValue( pNobd );
    }

    /**
     * Access method for the mNOBM property.
     * 
     * @return the current value of the mNOBM property
     */
    public Integer getNOBM()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NOBM ) ).getValue();
    }

    /**
     * Sets the value of the mNOBM property.
     * 
     * @param pNobm the new value of the mNOBM property
     */
    public void setNOBM( Integer pNobm )
    {
        ( (IntegerMetricBO) getMetrics().get( NOBM ) ).setValue( pNobm );
    }

    /**
     * Access method for the mNOC property.
     * 
     * @return the current value of the mNOC property
     */
    public Integer getNOC()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NOC ) ).getValue();
    }

    /**
     * Sets the value of the mNOC property.
     * 
     * @param pNoc the new value of the mNOC property
     */
    public void setNOC( Integer pNoc )
    {
        ( (IntegerMetricBO) getMetrics().get( NOC ) ).setValue( pNoc );
    }

    /**
     * Access method for the mNOP property.
     * 
     * @return the current value of the mNOP property
     */
    public Integer getNOP()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NOP ) ).getValue();
    }

    /**
     * Sets the value of the mNOP property.
     * 
     * @param pNop the new value of the mNOP property
     */
    public void setNOP( Integer pNop )
    {
        ( (IntegerMetricBO) getMetrics().get( NOP ) ).setValue( pNop );
    }

    /**
     * Access method for the mNumAttr property.
     * 
     * @return the current value of the mNumAttr property
     */
    public Integer getNumAttr()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NUMATTR ) ).getValue();
    }

    /**
     * Sets the value of the mNumAttr property.
     * 
     * @param pNumAttr the new value of the mNumAttr property
     */
    public void setNumAttr( Integer pNumAttr )
    {
        ( (IntegerMetricBO) getMetrics().get( NUMATTR ) ).setValue( pNumAttr );
    }

    /**
     * Access method for the mNumOps property.
     * 
     * @return the current value of the mNumOps property
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
     * Access method for the mNVM property.
     * 
     * @return the current value of the mNVM property
     */
    public Integer getNVM()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NVM ) ).getValue();
    }

    /**
     * Sets the value of the mNVM property.
     * 
     * @param pNvm the new value of the mNVM property
     */
    public void setNVM( Integer pNvm )
    {
        ( (IntegerMetricBO) getMetrics().get( NVM ) ).setValue( pNvm );
    }

    /**
     * Access method for the mOpsInh property.
     * 
     * @return the current value of the mOpsInh property
     */
    public Integer getOpsInh()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( OPSINH ) ).getValue();
    }

    /**
     * Sets the value of the mOpsInh property.
     * 
     * @param pOpsInh the new value of the mOpsInh property
     */
    public void setOpsInh( Integer pOpsInh )
    {
        ( (IntegerMetricBO) getMetrics().get( OPSINH ) ).setValue( pOpsInh );
    }

    /**
     * Access method for the mRFC property.
     * 
     * @return the current value of the mRFC property
     */
    public Integer getRFC()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( RFC ) ).getValue();
    }

    /**
     * Sets the value of the mRFC property.
     * 
     * @param pRfc the new value of the mRFC property
     */
    public void setRFC( Integer pRfc )
    {
        ( (IntegerMetricBO) getMetrics().get( RFC ) ).setValue( pRfc );
    }

    /**
     * Constructeur par défaut.
     */
    public UMLQualityClassMetricsBO()
    {
        super();
        getMetrics().put( DEPCLIENT, new IntegerMetricBO() );
        getMetrics().put( DEPSUPP, new IntegerMetricBO() );
        getMetrics().put( DIT, new IntegerMetricBO() );
        getMetrics().put( NII, new IntegerMetricBO() );
        getMetrics().put( NOBD, new IntegerMetricBO() );
        getMetrics().put( NOBM, new IntegerMetricBO() );
        getMetrics().put( NOC, new IntegerMetricBO() );
        getMetrics().put( NOP, new IntegerMetricBO() );
        getMetrics().put( NUMATTR, new IntegerMetricBO() );
        getMetrics().put( NUMOPS, new IntegerMetricBO() );
        getMetrics().put( NVM, new IntegerMetricBO() );
        getMetrics().put( OPSINH, new IntegerMetricBO() );
        getMetrics().put( RFC, new IntegerMetricBO() );
    }
}
