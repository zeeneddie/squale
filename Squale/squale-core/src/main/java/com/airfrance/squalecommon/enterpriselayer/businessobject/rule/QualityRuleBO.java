package com.airfrance.squalecommon.enterpriselayer.businessobject.rule;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Règle de calcul d'un résultat qualité, c'est-à-dire donnant lieu à une note.
 * 
 * @hibernate.class 
 * table="QualityRule"
 * discriminator-value="QualityRule" // theoriquement pas besoin car classe abstraite
 * mutable="true"
 * lazy="false"
 * 
 * @hibernate.discriminator 
 * column="subclass"
 * type="string"
 * not-null="true"
 * 
 */
public abstract class QualityRuleBO implements Serializable, Comparable {

    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;

    /**
     * Contient le nombre de répartition des notes possibles pour chaque QualityRule 
     * 5 pour :<br/>
     * <ul>
     *  <li> -1, 0, 1, 2, 3 dans le cas d'une ConditionFormula</li>
     *  <li> -1, [0,1[, [1,2[, [2,3[, 3 dans le cas d'une SimpleFormula</li>
     * </ul> 
     */
    public static final int NUMBER_OF_MARKS = 5;

    /**
     * Contient le nombre de répartition des notes possibles pour chaque QualityRule 
     * 30 pour tous les intervalles de pas = 0,1 entre 0 et 3
     * + 2 (un pour les composants ayant 3 pile et un pour les composants non notés)
     */
    public static final int NUMBER_OF_FLOAT_INTERVALS = 32;

    /**
     * Date de la creation de la regle de calcul d'un résultat qualité
     */
    protected Date mDateOfCreation;

    /** Nom de la règle */
    protected String mName = "";

    /** 
     * la clé permettant de récupérer l'aide associée à la pratique
     */
    private String helpKey;

    /**
     * @return la clé de l'aide
     * 
     * @hibernate.property 
     * name="helpKey" 
     * column="Name" 
     * type="string" 
     * unique="false"
     * column="Help_Key" 
     * cascade="all"
     */
    public String getHelpKey() {
        return (helpKey == null) ? mName : helpKey;
    }

    /**
     * @param newKey la nouvelle clé
     */
    public void setHelpKey(String newKey) {
        helpKey = newKey;
    }

    /**
     * Constructeur par défaut
     * @roseuid 42C13FBF012F
     */
    public QualityRuleBO() {
        mId = -1;
        Calendar cal = new GregorianCalendar();
        setDateOfCreation(cal.getTime());
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     * @roseuid 42C13FC70249
     */
    public void setId(long pId) {
        mId = pId;
    }

    /**
     * Access method for the mId property.
     * 
     * @return   the current value of the mId property
     * 
     * Note: unsaved-value An identifier property value that indicates that an instance 
     * is newly instantiated (unsaved), distinguishing it from transient instances that 
     * were saved or loaded in a previous session.  If not specified you will get an exception like this:
     * another object associated with the session has the same identifier
     * 
     * @hibernate.id generator-class="native"
     * type="long" 
     * column="QualityRuleId" 
     * unsaved-value="-1" 
     * length="19"
     * @hibernate.generator-param name="sequence" value="qualityrule_sequence" 
     * 
     * @roseuid 42C3FC3700A9
     */
    public long getId() {
        return mId;
    }

    /**
     * 
     * @param pName nom
     */
    public void setName(String pName) {
        mName = pName;
    }

    /**
     * @hibernate.property 
     * name="name" 
     * column="Name" 
     * type="string" 
     * not-null="true"
     * unique="false"
     * @return nom
     */
    public String getName() {
        return mName;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @roseuid 42C3FC3700B8
     */
    public boolean equals(Object pObj) {
        boolean ret = false;
        if (getClass().isInstance(pObj)) {
            ret = getName().equals(((QualityRuleBO) pObj).getName());
        }
        return ret;
    }

    /**
     * @see java.lang.Object#hashCode()
     * @roseuid 42C3FC3700BA
     */
    public int hashCode() {
        return getName().hashCode();
    }

    /**
     * @see java.lang.Object#toString()
     * @roseuid 42C3FC3700BB
     */
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        return toStringBuilder.append("ID", mId).append("date de création", mDateOfCreation).toString();

    }

    /**
     * Récupère la date de création
     * @return la date de création
     * 
     * @hibernate.property 
     * name="dateOfCreation" 
     * column="DateOfCreation" 
     * type="timestamp" 
     * not-null="true"
     * unique="false"
     * //     * lenght=""
     * 
     * @roseuid 42C405930155
     */
    public Date getDateOfCreation() {
        return mDateOfCreation;
    }

    /**
     * Affecte la date de création
     * @param pDate la date de création
     * @roseuid 42C51CFC01AC
     */
    private void setDateOfCreation(Date pDate) {
        mDateOfCreation = pDate;
    }
    /** (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        int result = 0;
        if (o instanceof QualityRuleBO) {
            QualityRuleBO rule = (QualityRuleBO) o;
            if ((rule.getName() != null) && (getName() != null)) {
                result = getName().compareTo(rule.getName());
            }
        }
        return result;
    }

    /**
     * Traitement du visiteur
     * @param pVisitor visiteur
     * @param pArgument argument
     * @return objet
     */
    abstract public Object accept(QualityRuleBOVisitor pVisitor, Object pArgument);
}
