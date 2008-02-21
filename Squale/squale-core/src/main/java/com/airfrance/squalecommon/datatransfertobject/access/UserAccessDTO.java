package com.airfrance.squalecommon.datatransfertobject.access;

import java.util.Date;

/**
 * 
 */
public class UserAccessDTO {

    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;
    
    /** Le matricule de l'utilisateur concerné */
    private String mMatricule;
    
    /** Date à laquelle l'utilisateur a accédé à l'application */
    private Date mDate;

    /**
     * @return l'id de l'objet
     */
    public long getId() {
        return mId;
    }

    /**
     * @param pId l'id de l'objet
     */
    public void setId(long pId) {
        mId = pId;
    }

    /**
     * @return la date d'accès
     */
    public Date getDate() {
        return mDate;
    }

    /**
     * @return le matricule de l'utilisateur
     */
    public String getMatricule() {
        return mMatricule;
    }

    /**
     * @param pDate la date d'accès
     */
    public void setDate(Date pDate) {
        mDate = pDate;
    }

    /**
     * @param pMatricule le matricule utilisateur
     */
    public void setMatricule(String pMatricule) {
        mMatricule = pMatricule;
    }

}
