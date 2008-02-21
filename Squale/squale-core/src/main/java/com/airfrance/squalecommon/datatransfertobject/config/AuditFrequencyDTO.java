package com.airfrance.squalecommon.datatransfertobject.config;

/**
 * DTO pour la fréquence max des audits
 */
public class AuditFrequencyDTO {

    /** L'identifiant (au sens technique) de l'objet */
    private long mId;

    /** Durée depuis le dernier accès (en nombre de jours) */
    private int mDays;

    /** Fréquence max d'audit (en nombre de jours) */
    private int mFrequency;

    /**
     * Méthode d'accès à mId
     * 
     * @return l'identifiant de l'objet
     */
    public long getId() {
        return mId;
    }

    /**
     * @return la durée le depuis dernier accès
     */
    public int getDays() {
        return mDays;
    }

    /**
     * @return la fréquence max d'audit
     */
    public int getFrequency() {
        return mFrequency;
    }

    /**
     * Change la valeur de mId
     * 
     * @param pId la nouvelle valeur de l'identifiant de l'objet
     */
    public void setId(long pId) {
        mId = pId;
    }

    /**
     * @param pDays la durée le depuis dernier accès
     */
    public void setDays(int pDays) {
        mDays = pDays;
    }

    /**
     * @param pFrequency la fréquence max d'audit
     */
    public void setFrequency(int pFrequency) {
        mFrequency = pFrequency;
    }
}
