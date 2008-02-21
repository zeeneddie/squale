package com.airfrance.squalecommon.datatransfertobject.config;

/**
 * Date limite pour le lancement d'audit
 */
public class StopTimeDTO {
    
    /** L'identifiant (au sens technique) de l'objet */
    private long mId;
    
    /** Le jour de la semaine */
    private String mDay;
    
    /** L'heure du jour */
    private String mTime;

    /**
     * Constructeur par défaut
     */
    public StopTimeDTO() {
        mId = -1;
        mDay = "Monday";
        mTime = "4:00";
    }

    /**
     * Méthode d'accès à mId
     * 
     * @return l'identifiant de l'objet
     * 
     */
    public long getId() {
        return mId;
    }

    /**
     * Méthode d'accès à mDay
     * 
     * @return le jour de la semaine
     * 
     */
    public String getDay() {
        return mDay;
    }

    /**
     * Méthode d'accès à mTime
     * 
     * @return l'heure du jour
     * 
     */
    public String getTime() {
        return mTime;
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
     * Change la valeur de mDay
     * 
     * @param pDay le jour de la semaine
     */
    public void setDay(String pDay) {
        mDay = pDay;
    }

    /**
     * Change la valeur de mTime
     * 
     * @param pTime l'heure du jour
     */
    public void setTime(String pTime) {
        mTime = pTime;
    }

}

