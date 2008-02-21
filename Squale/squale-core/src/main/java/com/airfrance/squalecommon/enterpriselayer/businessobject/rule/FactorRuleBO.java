package com.airfrance.squalecommon.enterpriselayer.businessobject.rule;

import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Règle de calcul d'un facteur
 * @author m400842
 * 
 * @hibernate.subclass 
 * name="FactorRule" 
 * discriminator-value="FactorRule"
 * lazy="false"
 */
public class FactorRuleBO extends QualityRuleBO {

    /**
     * Liste des critères avec leur poids associé permettant de calculer le facteur
     */
    protected SortedMap mCriteria;

    /**
     * Access method for the mCriteria property.
     * 
     * @return   the current value of the mCriteria property
     * 
     * @hibernate.map 
     * name="factors" 
     * table="FactorCriterium_Rule" 
     * lazy="true" 
     * cascade="all"

     * sort="natural"
     * 
     * @hibernate.index-many-to-many 
     * column="CriteriumRuleId" 
     * class="com.airfrance.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO" 
     * 
     * @hibernate.collection-key 
     * column="FactorRuleId"
     * 
     * @hibernate.collection-element 
     * column="Weight" 
     * type="float" 
     * not-null="true"
     */
    public SortedMap getCriteria() {
        return mCriteria;
    }

    /**
     * Sets the value of the mCriteria property.
     * 
     * @param pCriteria the new value of the mCriteria property
     */
    public void setCriteria(SortedMap pCriteria) {
        mCriteria = pCriteria;
    }

    /**
     * Ajoute une regle de critere
     * @param pCriterium une regle de critère
     * @param pWeight le poids du critère
     */
    public void addCriterium(CriteriumRuleBO pCriterium, Float pWeight) {
        if (null == mCriteria) {
            mCriteria = new TreeMap();
        }
        mCriteria.put(pCriterium, pWeight);
    }

    /**
     * @see java.lang.Object#toString()
     * @roseuid 42C51CF80035
     */
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        return toStringBuilder.append("ID", mId).toString();

    }

    /**
     * Constructeur par défaut
     * @roseuid 42C520EC0197
     */
    public FactorRuleBO() {
        super();
    }


    /** (non-Javadoc)
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBO#accept(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor, java.lang.Object)
     */
    public Object accept(QualityRuleBOVisitor pVisitor, Object pArgument) {
        return pVisitor.visit(this, pArgument);
    }
}
