package com.airfrance.squalecommon.enterpriselayer.businessobject.config;

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.AbstractDisplayConfBO;

/**
 * @hibernate.class 
 * table="Profile_DisplayConfBO"
 * lazy="true"
 */
public class Profile_DisplayConfBO {
    
    /** L'id de l'objet */
    protected long mId = -1;
    
    /** le profil (lien inverse) */
    protected ProjectProfileBO mProfile;
    
    /** La configuration d'affichage associée au profil */
    protected  AbstractDisplayConfBO mDisplayConf;

    /**
     * @return la configuration d'affichage
     * 
     * @hibernate.many-to-one 
     * name="displayConf" 
     * column="Profile_ConfId" 
     * type="com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.AbstractDisplayConfBO" 
     * not-null="false"
     * cascade="none"
     */
    public AbstractDisplayConfBO getDisplayConf() {
        return mDisplayConf;
    }

    /**
     * @return l'id
     * 
     * @hibernate.id generator-class="native"
     * type="long" 
     * column="ConfId" 
     * unsaved-value="-1" 
     * length="19"
     * @hibernate.generator-param name="sequence" value="profiledisplayconf_sequence" 
     */
    public long getId() {
        return mId;
    }

    /**
     * @return le profil
     * 
     * @hibernate.many-to-one 
     * name="displayConf" 
     * column="ProfileId" 
     * type="com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO" 
     * not-null="false"
     * cascade="none"
     */
    public ProjectProfileBO getProfile() {
        return mProfile;
    }

    /**
     * @param pConfBO la configuration d'affichage
     */
    public void setDisplayConf(AbstractDisplayConfBO pConfBO) {
        mDisplayConf = pConfBO;
    }

    /**
     * @param pId l'id
     */
    public void setId(long pId) {
        mId = pId;
    }

    /**
     * @param pProfileBO le profil
     */
    public void setProfile(ProjectProfileBO pProfileBO) {
        mProfile = pProfileBO;
    }

}
