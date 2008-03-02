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
