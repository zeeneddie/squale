package com.airfrance.squalecommon.enterpriselayer.businessobject.config;

import java.io.Serializable;

/**
 * Date limite pour un lancement d'audit
 * 
 * @hibernate.class table="StopTimeBO" lazy="true"
 */
public class StopTimeBO
    implements Serializable
{

    /** L'identifiant (au sens technique) de l'objet */
    private long mId;

    /** Le jour de la semaine */
    private String mDay;

    /** L'heure du jour */
    private String mTime;

    /**
     * Constructeur par défaut
     */
    public StopTimeBO()
    {
        mId = -1;
        mDay = "Monday";
        mTime = "4:00";
    }

    /**
     * Méthode d'accès à mId
     * 
     * @return l'identifiant de l'objet
     * @hibernate.id generator-class="native" type="long" column="StopTimeId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="stoptime_sequence"
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Méthode d'accès à mDay
     * 
     * @return le jour de la semaine
     * @hibernate.property name="day" column="DayOfWeek" type="string" length="9" not-null="true" update="true"
     *                     insert="true"
     */
    public String getDay()
    {
        return mDay;
    }

    /**
     * Méthode d'accès à mTime
     * 
     * @return l'heure du jour
     * @hibernate.property name="time" column="TimeOfDay" type="string" length="5" not-null="true" update="true"
     *                     insert="true"
     */
    public String getTime()
    {
        return mTime;
    }

    /**
     * Change la valeur de mId
     * 
     * @param pId la nouvelle valeur de l'identifiant de l'objet
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Change la valeur de mDay
     * 
     * @param pDay le jour de la semaine
     */
    public void setDay( String pDay )
    {
        mDay = pDay;
    }

    /**
     * Change la valeur de mTime
     * 
     * @param pTime l'heure du jour
     */
    public void setTime( String pTime )
    {
        mTime = pTime;
    }

}
