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
package org.squale.squalecommon.enterpriselayer.businessobject.result.roi;

import org.squale.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.MetricBO;

/**
 * Représente un retour sur investissement
 * 
 * @hibernate.subclass discriminator-value="Roi"
 */
public class RoiMetricsBO
    extends MeasureBO
{

    /** Le nom de la tâche ROI */
    public static final String ROI_TASKNAME = "RoiTask";

    /**
     * Le nombre de corrections
     */
    private final static String NB_CORRECTIONS = "roi";

    /**
     * Constructeur par défaut.
     */
    public RoiMetricsBO()
    {
        super();
        getMetrics().put( NB_CORRECTIONS, new IntegerMetricBO() );
        mTaskName = ROI_TASKNAME;
    }

    /**
     * @return le nombre de corrections
     */
    public Integer getNbCorrections()
    {
        return (Integer) ( (MetricBO) getMetrics().get( NB_CORRECTIONS ) ).getValue();
    }

    /**
     * @param pNbCorrections le nombre de corrections
     */
    public void setNbCorrections( int pNbCorrections )
    {
        ( (IntegerMetricBO) getMetrics().get( NB_CORRECTIONS ) ).setValue( new Integer( pNbCorrections ) );
    }
}
