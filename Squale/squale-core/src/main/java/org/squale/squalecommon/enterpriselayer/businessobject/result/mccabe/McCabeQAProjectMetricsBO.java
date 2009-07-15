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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squaleCommon\\src\\org\\squale\\squalecommon\\enterpriselayer\\businessobject\\result\\mccabe\\McCabeProjectMetricsBO.java

package org.squale.squalecommon.enterpriselayer.businessobject.result.mccabe;

import org.squale.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;

/**
 * @author m400842 (by rose)
 * @version 1.0
 * @hibernate.subclass discriminator-value="ProjectMetrics"
 */
public final class McCabeQAProjectMetricsBO
    extends McCabeQAMetricsBO
{

    /**
     * Nombre de classes dans le sous-projet
     */
    private final static String NUMBEROFCLASSES = "numberOfClasses";

    /**
     * Nombre de méthodes dans le sous-projet.
     */
    private final static String NUMBEROFMETHODS = "numberOfMethods";

    /**
     * Nombre total de lignes du projet.
     */
    private final static String PROJECTNL = "projectnl";

    /**
     * Constructeur
     * 
     * @roseuid 42B9751A0293
     */
    public McCabeQAProjectMetricsBO()
    {
        super();
        getMetrics().put( NUMBEROFCLASSES, new IntegerMetricBO() );
        getMetrics().put( NUMBEROFMETHODS, new IntegerMetricBO() );
        getMetrics().put( PROJECTNL, new IntegerMetricBO() );
    }

    /**
     * Access method for the mNumberOfClasses property.
     * 
     * @return the current value of the mNumberOfClasses property
     * @roseuid 42C416B702BC
     */
    public Integer getNumberOfClasses()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NUMBEROFCLASSES ) ).getValue();
    }

    /**
     * Sets the value of the mNumberOfClasses property.
     * 
     * @param pNumberOfClasses the new value of the mNumberOfClasses property
     * @roseuid 42C416B702DB
     */
    public void setNumberOfClasses( Integer pNumberOfClasses )
    {
        ( (IntegerMetricBO) getMetrics().get( NUMBEROFCLASSES ) ).setValue( pNumberOfClasses );
    }

    /**
     * Access method for the mNumberOfMethods property.
     * 
     * @return the current value of the mNumberOfMethods property
     * @roseuid 42C416B70339
     */
    public Integer getNumberOfMethods()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NUMBEROFMETHODS ) ).getValue();
    }

    /**
     * Sets the value of the mNumberOfMethods property.
     * 
     * @param pNumberOfMethods the new value of the mNumberOfMethods property
     * @roseuid 42C416B70367
     */
    public void setNumberOfMethods( Integer pNumberOfMethods )
    {
        ( (IntegerMetricBO) getMetrics().get( NUMBEROFMETHODS ) ).setValue( pNumberOfMethods );
    }

    /**
     * Retourne le nombre total de lignes du projet.
     * 
     * @return le nombre total de lignes du projet.
     */
    public Integer getProjectsloc()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( PROJECTNL ) ).getValue();
    }

    /**
     * Positionne le nombre total de lignes du projet.
     * 
     * @param pTotalSLoc le nombre total de lignes du projet.
     */
    public void setTotalNl( Integer pTotalNl )
    {
        ( (IntegerMetricBO) getMetrics().get( PROJECTNL ) ).setValue( pTotalNl );
    }
    
}
