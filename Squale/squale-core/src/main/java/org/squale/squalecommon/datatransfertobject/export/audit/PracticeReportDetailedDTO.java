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
package com.airfrance.squalecommon.datatransfertobject.export.audit;

import java.util.Map;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;

/**
 * Detail of a practice
 */
public class PracticeReportDetailedDTO
{
    /**
     * Component involved
     */
    private ComponentDTO component;

    /**
     * score of the component for this practice
     */
    private float score;

    /**
     * Metrics scores for this practice and this component
     */
    private Map metrics;

    /**
     * Getter for the component
     * 
     * @return the component
     */
    public ComponentDTO getComponent()
    {
        return component;
    }

    /**
     * Modify the component
     * 
     * @param pComponent new component
     */
    public void setComponent( ComponentDTO pComponent )
    {
        this.component = pComponent;
    }

    /**
     * Getter for score
     * 
     * @return the score
     */
    public float getScore()
    {
        return score;
    }

    /**
     * Modify the score
     * 
     * @param pScore new score
     */
    public void setScore( float pScore )
    {
        this.score = pScore;
    }

    /**
     * Getter for the metrics map
     * key : tre of the metric
     * value : score for this metric
     * 
     * @return metrics
     */
    public Map getMetrics()
    {
        return metrics;
    }

    /**
     * Modify metrics
     * 
     * @param pMetrics new metrics
     */
    public void setMetrics( Map pMetrics )
    {
        this.metrics = pMetrics;
    }

}
