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
package com.airfrance.squalecommon.enterpriselayer.businessobject.result.umlquality;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;

/**
 * @hibernate.subclass discriminator-value="UMLQualityModelMetrics"
 */
public class UMLQualityModelMetricsBO
    extends UMLQualityMetricsBO
{

    /** Average Depth of Inheritance: la profondeur moyenne de l'arbre d'héritage des classes. */
    private final static String ADI = "ADI";

    /**
     * Maximum Depth of Inheritance: la plus grande profondeur de l'arbre d'héritage, i.e. le plus grand nombre de
     * niveaux existant entre une feuille et la racine la plus loInteger aine de sa hiérarchie.
     */
    private final static String MD = "MD";

    /** Maximum Class Coupling: plus grande valeur de couplage pour une classe du modèle. */
    private final static String MCC = "MCC";

    /**
     * Constructeur
     */
    public UMLQualityModelMetricsBO()
    {
        super();
        getMetrics().put( ADI, new IntegerMetricBO() );
        getMetrics().put( MD, new IntegerMetricBO() );
        getMetrics().put( MCC, new IntegerMetricBO() );
    }

    /**
     * Access method for the mADI property.
     * 
     * @return the current value of the mADI property
     */
    public Integer getADI()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( ADI ) ).getValue();
    }

    /**
     * Sets the value of the mADI property.
     * 
     * @param pAdi the new value of the mADI property
     */
    public void setADI( Integer pAdi )
    {
        ( (IntegerMetricBO) getMetrics().get( ADI ) ).setValue( pAdi );
    }

    /**
     * Access method for the MCC property.
     * 
     * @return the current value of the MCC property
     */
    public Integer getMCC()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( MCC ) ).getValue();
    }

    /**
     * Sets the value of the MCC property.
     * 
     * @param pMcc the new value of the MCC property
     */
    public void setMCC( Integer pMcc )
    {
        ( (IntegerMetricBO) getMetrics().get( MCC ) ).setValue( pMcc );
    }

    /**
     * Access method for the MD property.
     * 
     * @return the current value of the MD property
     */
    public Integer getMD()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( MD ) ).getValue();
    }

    /**
     * Sets the value of the MD property.
     * 
     * @param pMd the new value of the MD property
     */
    public void setMD( Integer pMd )
    {
        ( (IntegerMetricBO) getMetrics().get( MD ) ).setValue( pMd );
    }

}
