package com.airfrance.squalecommon.enterpriselayer.businessobject.roi;

import java.util.Date;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;

/**
 * Représente un retour sur investissement
 */
public class RoiBO
{

    /** L'application concerné par le ROI */
    private ApplicationBO mApplication;

    /** Date de calcul du ROI */
    private Date mDate;

    /**
     * @return l'application
     */
    public ApplicationBO getApplication()
    {
        return mApplication;
    }

    /**
     * @return la date de calcul du ROI
     */
    public Date getDate()
    {
        return mDate;
    }

    /**
     * @param pApplicationBO l'application
     */
    public void setApplication( ApplicationBO pApplicationBO )
    {
        mApplication = pApplicationBO;
    }

    /**
     * @param pDate la date de calcul du ROI
     */
    public void setDate( Date pDate )
    {
        mDate = pDate;
    }

}
