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
package com.airfrance.squalecommon.enterpriselayer.businessobject.result.jspvolumetry;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;

/**
 * @hibernate.subclass discriminator-value="JSPVolumetryProject"
 */
public class JSPVolumetryProjectBO
    extends MeasureBO
{

    /**
     * Nombre lignes de code jsp
     */
    private final static String NUMBER_OF_JSP_CODE_LINES = "numberOfJSPCodeLines";

    /**
     * Nombre de jsp dans le projet
     */
    private final static String NUMBER_OF_JSP = "numberOfJSP";

    /**
     * Constructeur par défaut.
     */
    public JSPVolumetryProjectBO()
    {
        super();
        getMetrics().put( NUMBER_OF_JSP_CODE_LINES, new IntegerMetricBO() );
        getMetrics().put( NUMBER_OF_JSP, new IntegerMetricBO() );
    }

    /**
     * @return the current value of the NUMBER_OF_JSP property
     */
    public Integer getNumberOfJSP()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NUMBER_OF_JSP ) ).getValue();
    }

    /**
     * @param pNumberOfJSPs the new value of the NUMBER_OF_JSP property
     */
    public void setNumberOfJSPs( Integer pNumberOfJSPs )
    {
        ( (IntegerMetricBO) getMetrics().get( NUMBER_OF_JSP ) ).setValue( pNumberOfJSPs );
    }

    /**
     * @return the current value of the NUMBER_OF_JSP_CODE_LINES property
     */
    public Integer getNumberOfJSPCodeLines()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( NUMBER_OF_JSP_CODE_LINES ) ).getValue();
    }

    /**
     * @param pJSPsLOC the new value of the NUMBER_OF_JSP_CODE_LINES property
     */
    public void setJSPsLOC( Integer pJSPsLOC )
    {
        ( (IntegerMetricBO) getMetrics().get( NUMBER_OF_JSP_CODE_LINES ) ).setValue( pJSPsLOC );
    }

}
