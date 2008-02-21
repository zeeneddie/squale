package com.airfrance.squalecommon.enterpriselayer.businessobject.config.web;

/**
 * Configurations pour l'affichage Web dépendants des outils utilisés pour le calcul
 * d'un audit
 * 
 * @hibernate.class 
 * table="displayConf"
 * mutable="true"
 * lazy="true"
 * discriminator-value="AbstractDisplayConfBO"
 * @hibernate.discriminator 
 * column="subclass"
 */
public abstract class AbstractDisplayConfBO {

    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId = -1;

    /**
     * Méthode d'accès pour mId
     * 
     * @return la l'identifiant (au sens technique) de l'objet
     * 
     * @hibernate.id generator-class="native"
     * type="long" 
     * column="ConfId" 
     * unsaved-value="-1" 
     * length="19"
     * @hibernate.generator-param name="sequence" value="displayconf_sequence" 
     * 
     */
    public long getId() {
        return mId;
    }

    /**
     * Change la valeur de mId
     * 
     * @param pId le nouvel identifiant
     */
    public void setId(long pId) {
        mId = pId;
    }

    /**
     * @param pVisitor visiteur
     * @param pArgument argument
     * @return objet
     */
    public abstract Object accept(DisplayConfVisitor pVisitor, Object pArgument);
}
