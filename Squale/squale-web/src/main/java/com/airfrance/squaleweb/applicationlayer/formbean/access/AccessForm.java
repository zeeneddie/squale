package com.airfrance.squaleweb.applicationlayer.formbean.access;

import java.util.Date;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Bean pour les accès utilisateur
 */
public class AccessForm
    extends RootForm
{

    /** Le matricule de l'utilisateur concerné */
    private String mMatricule;

    /** Date à laquelle l'utilisateur a accédé à l'application */
    private Date mDate;

    /**
     * @return la date d'accès
     */
    public Date getDate()
    {
        return mDate;
    }

    /**
     * @return le matricule de l'utilisateur
     */
    public String getMatricule()
    {
        return mMatricule;
    }

    /**
     * @param pDate la date d'accès
     */
    public void setDate( Date pDate )
    {
        mDate = pDate;
    }

    /**
     * @param pMatricule le matricule utilisateur
     */
    public void setMatricule( String pMatricule )
    {
        mMatricule = pMatricule;
    }

}
