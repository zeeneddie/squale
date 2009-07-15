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
package org.squale.squalecommon.enterpriselayer.businessobject.result.jdepend;

import org.squale.squalecommon.enterpriselayer.businessobject.result.BooleanMetricBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.FloatMetricBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.StringMetricBO;

/**
 * @hibernate.subclass discriminator-value="JDependPackageMetrics"
 */
public class JDependPackageMetricsBO
    extends MeasureBO
{

    /** le nombre de classes et d'interfaces */
    private final static String NUMBER = "numberOfClassesAndInterfaces";

    /** couplage afferent */
    private final static String CA = "ca";

    /** couplage efferent */
    private final static String CE = "ce";

    /** niveau d'abstraction */
    private final static String ABSTRACTNESS = "abstractness";

    /** instabilité */
    private final static String INSTABILITY = "instability";

    /** indique la présence de cycles */
    private final static String HAVE_CYCLES = "haveCycles";

    /** le cycle eventuellement remonté par JDepend */
    private final static String CYCLE = "cycle";

    /**
     * Distance. Notion indiquant l'équilibre entre le niveau d'abstraction et l'instabilité
     */
    private final static String DISTANCE = "distance";

    /**
     * Constructeur par défaut.
     */
    public JDependPackageMetricsBO()
    {
        super();
        getMetrics().put( NUMBER, new IntegerMetricBO() );
        getMetrics().put( CA, new IntegerMetricBO() );
        getMetrics().put( CE, new IntegerMetricBO() );
        getMetrics().put( ABSTRACTNESS, new FloatMetricBO() );
        getMetrics().put( INSTABILITY, new FloatMetricBO() );
        getMetrics().put( DISTANCE, new FloatMetricBO() );
        getMetrics().put( HAVE_CYCLES, new BooleanMetricBO() );
        // on n'ajoute la clé cycle dans la table que si il y a un cycle
        // cf la méthode setCycles
    }

    /**
     * @return le couplage afférent
     */
    public Integer getCa()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( CA ) ).getValue();
    }

    /**
     * @return le couplage efferent
     */
    public Integer getCe()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( CE ) ).getValue();
    }

    /**
     * @return la distance
     */
    public Float getDistance()
    {
        return (Float) ( (FloatMetricBO) getMetrics().get( DISTANCE ) ).getValue();
    }

    /**
     * @return l'instabilité
     */
    public Float getInstability()
    {
        return (Float) ( (FloatMetricBO) getMetrics().get( INSTABILITY ) ).getValue();
    }

    /**
     * @return le nombre de classes
     */
    public Integer getNumberOfClassesAndInterfaces()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NUMBER ) ).getValue();
    }

    /**
     * @return le niveau d'abstraction
     */
    public Float getAbstractness()
    {
        return (Float) ( (FloatMetricBO) getMetrics().get( ABSTRACTNESS ) ).getValue();
    }

    /**
     * @return le niveau d'abstraction
     */
    public Boolean getHaveCycles()
    {
        return (Boolean) ( (BooleanMetricBO) getMetrics().get( HAVE_CYCLES ) ).getValue();
    }

    /**
     * @return le cycle
     */
    public String getCycle()
    {
        String result = null;
        StringMetricBO cycleMetric = (StringMetricBO) getMetrics().get( CYCLE );
        if ( null != cycleMetric )
        {
            result = (String) cycleMetric.getValue();
        }
        return result;
    }

    /**
     * @param pCa la nouvelle valeur du couplage afférent
     */
    public void setCa( int pCa )
    {
        ( (IntegerMetricBO) getMetrics().get( CA ) ).setValue( new Integer( pCa ) );
    }

    /**
     * @param pCe la nouvelle valeur du couplage afférent
     */
    public void setCe( int pCe )
    {
        ( (IntegerMetricBO) getMetrics().get( CE ) ).setValue( new Integer( pCe ) );
    }

    /**
     * @param pDistance la nouvelle valeur de la distance
     */
    public void setDistance( float pDistance )
    {
        ( (FloatMetricBO) getMetrics().get( DISTANCE ) ).setValue( new Float( pDistance ) );
    }

    /**
     * @param pInstability la nouvelle valeur de l'instabilité
     */
    public void setInstability( float pInstability )
    {
        ( (FloatMetricBO) getMetrics().get( INSTABILITY ) ).setValue( new Float( pInstability ) );
    }

    /**
     * @param pNumber le nouveau nombre de classes et d'interfaces
     */
    public void setNumberOfClassesAndInterfaces( int pNumber )
    {
        ( (IntegerMetricBO) getMetrics().get( NUMBER ) ).setValue( new Integer( pNumber ) );
    }

    /**
     * @param pAbstractness la nouvelle valeur du niveau d'abstraction
     */
    public void setAbstractness( float pAbstractness )
    {
        ( (FloatMetricBO) getMetrics().get( ABSTRACTNESS ) ).setValue( new Float( pAbstractness ) );
    }

    /**
     * @param pCycles la nouvelle valeur indiquant la présence de cycles
     */
    public void setHaveCycles( boolean pCycles )
    {
        ( (BooleanMetricBO) getMetrics().get( HAVE_CYCLES ) ).setValue( new Boolean( pCycles ) );
    }

    /**
     * @param pCycles la nouvelle valeur du cycle on n'ajoute la clé cycle dans la table que si il y a un cycle
     */
    public void setCycle( String pCycles )
    {
        StringMetricBO stringMetric = new StringMetricBO();
        stringMetric.setValue( pCycles );
        getMetrics().put( CYCLE, stringMetric );
    }

}