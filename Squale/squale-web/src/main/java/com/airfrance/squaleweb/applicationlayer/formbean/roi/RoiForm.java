package com.airfrance.squaleweb.applicationlayer.formbean.roi;

import java.text.DecimalFormat;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Bean pour le ROI
 */
public class RoiForm
    extends RootForm
{

    /** L'id de l'application. Si l'id est -1 on prend toutes les applications */
    private long mRoiApplicationId = -1;

    /** Le ROI total en jours/homme */
    private double mTotal;

    /** La valeur en KEuros pour un jours/homme */
    private double mKEuros;

    /** Formule appliquée pour calculer le ROI à partir du nombre de corrections */
    private String mFormula;

    /**
     * La liste des mesures
     */
    private List mMeasures;

    /**
     * @return l'id de l'application
     */
    public long getRoiApplicationId()
    {
        return mRoiApplicationId;
    }

    /**
     * @return la valeur du jour/homme en K€
     */
    public double getKEuros()
    {
        return mKEuros;
    }

    /**
     * @return la valeur total du ROI
     */
    public double getTotal()
    {
        return mTotal;
    }

    /**
     * @param pApplicationId l'id de l'application
     */
    public void setRoiApplicationId( long pApplicationId )
    {
        mRoiApplicationId = pApplicationId;
    }

    /**
     * @param pKEuros la valeur du jour/homme en K€
     */
    public void setKEuros( double pKEuros )
    {
        mKEuros = pKEuros;
    }

    /**
     * @param pTotal la valeur total du ROI
     */
    public void setTotal( double pTotal )
    {
        mTotal = pTotal;
    }

    /**
     * @return la valeur du ROI en K€
     */
    public double getTotalInKEuros()
    {
        double result = mTotal * mKEuros;
        DecimalFormat df = new DecimalFormat( "########.0" );
        String totalStr = df.format( result );
        result = Double.parseDouble( totalStr );
        return result;
    }

    /**
     * @return la formule
     */
    public String getFormula()
    {
        return mFormula;
    }

    /**
     * @param pFormula la formule
     */
    public void setFormula( String pFormula )
    {
        mFormula = pFormula;
    }

    /**
     * @return les mesures
     */
    public List getMeasures()
    {
        return mMeasures;
    }

    /**
     * @param pMeasures les mesures
     */
    public void setMeasures( List pMeasures )
    {
        mMeasures = pMeasures;
    }

}
