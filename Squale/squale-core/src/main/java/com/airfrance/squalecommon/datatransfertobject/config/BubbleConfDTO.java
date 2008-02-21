package com.airfrance.squalecommon.datatransfertobject.config;

/**
 */
public class BubbleConfDTO {

    /** l'identifiant au sens technique de l'objet */
    private long mId;

    /**
     * le tre des abssices au sens métier
     */
    private String mXTre;

    /**
     * le tre des ordonnées au sens métier
     */
    private String mYTre;
    
    /**
     * La position de l'axe horizontal
     */
    private Long mHorizontalAxisPos;
    
    /**
     * la position de l'axe vertical
     */
    private Long mVerticalAxisPos;

    /**
     * @return le tre métier sur l'axe des x
     */
    public String getXTre() {
        return mXTre;
    }

    /**
      * @return le tre métier sur l'axe des y
     */
    public String getYTre() {
        return mYTre;
    }

    /**
     * @param pXtre la nouvelle valeur du tre au sens métier
     */
    public void setXTre(String pXtre) {
        mXTre = pXtre;
    }

    /**
     * @param pYtre la nouvelle valeur du tre au sens métier
     */
    public void setYTre(String pYtre) {
        mYTre = pYtre;
    }

    /**
     * @return l'id de la conf
     */
    public long getId() {
        return mId;
    }

    /**
     * @param pId le nouvel id
     */
    public void setId(long pId) {
        mId = pId;
    }

    /**
     * @return la position de l'axe horizontal
     */
    public Long getHorizontalAxisPos() {
        return mHorizontalAxisPos;
    }

    /**
     * @return la position de l'axe vertical
     */
    public Long getVerticalAxisPos() {
        return mVerticalAxisPos;
    }

    /**
     * @param pHorizontalPos la position de l'axe horizontal
     */
    public void setHorizontalAxisPos(Long pHorizontalPos) {
        mHorizontalAxisPos = pHorizontalPos;
    }

    /**
     * @param pVerticalPos la position de l'axe vertical
     */
    public void setVerticalAxisPos(Long pVerticalPos) {
        mVerticalAxisPos = pVerticalPos;
    }

}
