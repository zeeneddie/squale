package com.airfrance.squalecommon.enterpriselayer.businessobject.config.web;

/** 
 * La configuration pour la création du scatterplot
 * 
 * @hibernate.subclass
 * lazy="true"
 * discriminator-value="bubble"
 *  
 */
public class BubbleConfBO extends AbstractDisplayConfBO {

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
    private long mHorizontalAxisPos;

    /**
     * la position de l'axe vertical
     */
    private long mVerticalAxisPos;
    
    /**
     * Constructeur par défaut
     */
    public BubbleConfBO() {
        this("", "", 0, 0);
    }
    
    /**
     * Constructeur
     * @param pXTre le tre des abssices au sens métier
     * @param pYTre le tre des ordonnées au sens métier
     * @param pHpos la position de l'axe horizontal
     * @param pVpos la position de l'axe vertical
     */
    public BubbleConfBO(String pXTre, String pYTre, long pHpos, long pVpos) {
        mXTre = pXTre;
        mYTre = pYTre;
        mHorizontalAxisPos = pHpos;
        mVerticalAxisPos = pVpos;
    }

    /**
     * @return le tre métier sur l'axe des v
     * 
     * @hibernate.property 
     * name="xTre" 
     * column="X_TRE" 
     * type="string" 
     * length="400"
     */
    public String getXTre() {
        return mXTre;
    }

    /**
     * @return le tre métier sur l'axe des y
     * 
     * @hibernate.property 
     * name="yTre" 
     * column="Y_TRE" 
     * type="string" 
     * length="400"
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
     * @return la position de l'axe horizontal
     * 
     * @hibernate.property 
     * name="xPos" 
     * column="X_POS" 
     * type="long" 
     * length="3" 
     */
    public long getHorizontalAxisPos() {
        return mHorizontalAxisPos;
    }

    /**
     * @return la position de l'axe vertical
     * 
     * @hibernate.property 
     * name="yPos" 
     * column="Y_POS" 
     * type="long" 
     * length="3"
     */
    public long getVerticalAxisPos() {
        return mVerticalAxisPos;
    }

    /**
     * @param pHorizontalPos la position de l'axe horizontal
     */
    public void setHorizontalAxisPos(long pHorizontalPos) {
        mHorizontalAxisPos = pHorizontalPos;
    }

    /**
     * @param pVerticalPos la position de l'axe vertical
     */
    public void setVerticalAxisPos(long pVerticalPos) {
        mVerticalAxisPos = pVerticalPos;
    }

    /** 
     * {@inheritDoc}
     * @param pVisitor {@inheritDoc}
     * @param pArgument {@inheritDoc}
     * @return {@inheritDoc}
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.AbstractDisplayConfBO#accept(com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.DisplayConfVisitor, java.lang.Object)
     */
    public Object accept(DisplayConfVisitor pVisitor, Object pArgument) {
        return pVisitor.visit(this, pArgument);
    }
    /** 
     * {@inheritDoc}
     * @param obj {@inheritDoc}
     * @return {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        boolean result = false;
        if(obj instanceof BubbleConfBO) {
            BubbleConfBO bubble = (BubbleConfBO) obj;
            result = bubble.getHorizontalAxisPos() == getHorizontalAxisPos();
            result &= bubble.getVerticalAxisPos() == getVerticalAxisPos();
            result &= bubble.getXTre().equals(getXTre());
            result &= bubble.getYTre().equals(getYTre());
        }
        return result;
    }
}
