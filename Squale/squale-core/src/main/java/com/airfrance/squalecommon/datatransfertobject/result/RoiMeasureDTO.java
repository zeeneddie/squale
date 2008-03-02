package com.airfrance.squalecommon.datatransfertobject.result;

import java.util.Date;

/**
 * Représente une mesure de ROI avec le nom de l'application concerné la date de l'audit et son résultat
 */
public class RoiMeasureDTO
    implements java.lang.Comparable
{

    /** Le nom de l'application concernée par la mesure */
    private String mAppliName;

    /** La date de l'audit */
    private Date mAuditDate;

    /** La valeur du ROI pour cette application et cet audit */
    private double mValue;

    /**
     * @param pAppliName la nom de l'application
     * @param pAuditDate la date de l'audit
     * @param pValue la valeur du ROI
     */
    public RoiMeasureDTO( String pAppliName, Date pAuditDate, double pValue )
    {
        mAppliName = pAppliName;
        mAuditDate = pAuditDate;
        mValue = pValue;
    }

    /**
     * @return le nom de l'application
     */
    public String getAppliName()
    {
        return mAppliName;
    }

    /**
     * @return la date de l'audit
     */
    public Date getAuditDate()
    {
        return mAuditDate;
    }

    /**
     * @param pAppliName le nom de l'application
     */
    public void setAppliName( String pAppliName )
    {
        mAppliName = pAppliName;
    }

    /**
     * @param pDate la date de l'audit
     */
    public void setAuditDate( Date pDate )
    {
        mAuditDate = pDate;
    }

    /**
     * Comparaison sur le nom de l'appli puis la date si égalité
     * 
     * @param pRoiMeasure la mesure du ROI
     * @return le résultat de la comparaison
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo( Object pRoiMeasure )
    {
        int result = 0;
        if ( pRoiMeasure instanceof RoiMeasureDTO )
        {
            RoiMeasureDTO roiMeasure = (RoiMeasureDTO) pRoiMeasure;
            if ( roiMeasure.mAppliName != null )
            {
                result = getAppliName().compareTo( roiMeasure.mAppliName );
                if ( ( 0 == result ) && ( getAuditDate() != null ) )
                {
                    result = getAuditDate().compareTo( roiMeasure.getAuditDate() );
                }
            }
        }
        return result;
    }

    /**
     * @return la valeur du ROI
     */
    public double getValue()
    {
        return mValue;
    }

    /**
     * @param pValue la valeur du ROI
     */
    public void setValue( double pValue )
    {
        mValue = pValue;
    }

}
