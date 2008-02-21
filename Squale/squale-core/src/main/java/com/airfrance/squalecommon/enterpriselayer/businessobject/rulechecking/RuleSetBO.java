package com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Jeu de règles
 * Un ensemble de règles est lié à un outil, il définit les règles
 * qui sont vérifiées par cet outil.
 * @hibernate.class 
 * table="RuleSet"
 * mutable="true"
 * @hibernate.discriminator 
 *   column="subclass"
 * 
 **/

public abstract class RuleSetBO implements Serializable {
    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId = -1;

    /**
     * Nom du jeu de règles
     */
    protected String mName;
    
    /**
     * Date de la creation de la regle de calcul d'un résultat qualité
     */
    private Date mDateOfUpdate;

    /**
     * Liste des règles
     */
    protected Map mRules = new HashMap();

    /**
     * Constucteur par défaut
     *
     */
    public RuleSetBO() {
        mId = -1;
        setDateOfUpdate(new GregorianCalendar().getTime());

    }
    /**
     * Access method for the mId property.
     * @return   the current value of the mId property
     * 
     * Note: unsaved-value An identifier property value that indicates that an instance 
     * is newly instantiated (unsaved), distinguishing it from transient instances that 
     * were saved or loaded in a previous session.  If not specified you will get an exception like this:
     * another object associated with the session has the same identifier
     * 
     * @hibernate.id generator-class="native" 
     * type="long" 
     * column="RuleSetId" 
     * unsaved-value="-1" 
     * length="19"
     * @hibernate.generator-param name="sequence" value="RuleSet_sequence" 
     */
    public long getId() {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     **/

    public void setId(long pId) {
        mId = pId;
    }

    /**
     * Ajout d'une règle
     * @param pRule règle
     */
    public void addRule(RuleBO pRule) {
        getRules().put(pRule.getCode(), pRule);
    }

    /**
     * Access method for the mRules property.
     * 
     * @return   the current value of the mRules property
     * 
     * @hibernate.map 
     * name="rules" 
     * lazy="true" 
     * cascade="all"
     * @hibernate.collection-index 
     * column="Code" 
     * type="string" 
     * @hibernate.collection-key 
     * column="RuleSetId"
     * @hibernate.collection-one-to-many 
     * class="com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO" 
     */

    public Map getRules() {
        return mRules;
    }
    /**
     * Sets the value of the mRules property.
     * 
     * @param pRules the new value of the mRules property
     **/
    public void setRules(Map pRules) {
        mRules = pRules;
    }
    /**
     * Access method for the mVersion property.
     * 
     * @return   the current value of the Version property
     * 
     * @hibernate.property 
     * name="name" 
     * column="Name" 
     * not-null="false" 
     * type="string" 
     * unique="false"
     **/
    public String getName() {
        return mName;
    }

    /**
     * Sets the value of the mName property.
     * 
     * @param pName the new value of the mVersion property
     **/
    public void setName(String pName) {
        mName = pName;
    }

    /**
     * Récupère la date de mise à jour
     * @return la date de mise à jour
     * 
     * @hibernate.property 
     * name="dateOfUpdate" 
     * column="DateOfUpdate" 
     * type="timestamp" 
     * not-null="true"
     * unique="false"
     * 
     */
    public Date getDateOfUpdate() {
        return mDateOfUpdate;
    }
    
    /**
     * Affecte la date de mise à jour
     * @param pDate la date de mise à jour
     */
    private void setDateOfUpdate(Date pDate) {
        mDateOfUpdate = pDate;
    }
}
