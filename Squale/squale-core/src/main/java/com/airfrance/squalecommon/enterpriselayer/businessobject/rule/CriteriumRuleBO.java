package com.airfrance.squalecommon.enterpriselayer.businessobject.rule;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Règle de calcul d'un critère
 * 
 * @hibernate.subclass 
 * name="CriteriumRule" 
 * discriminator-value="CriteriumRule"
 * lazy="false"
 */
public class CriteriumRuleBO extends QualityRuleBO {

    /**
     * Liste des pratiques avec leur poids associé
     * permettant le calcul du critère
     */
    protected SortedMap mPractices;

    /**
     * Constructeur par défaut
     */
    public CriteriumRuleBO() {
        super();
        mPractices = new TreeMap();
    }

    /**
     * Access method for the mPractices property.
     * 
     * @return   the current value of the mPractices property
     * 
     * @hibernate.map 
     * name="practices" 
     * table="CriteriumPractice_Rule" 
     * lazy="true" 
     * cascade="all"
    
     * sort="natural"
     * 
     * @hibernate.index-many-to-many 
     * column="PracticeRuleId" 
     * class="com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO" 
     * 
     * @hibernate.collection-key 
     * column="CriteriumRuleId"
     * 
     * @hibernate.collection-element 
     * column="Weight" 
     * type="float" 
     * not-null="true"
     * 
     */
    public SortedMap getPractices() {
        return mPractices;
    }

    /**
     * Sets the value of the mPractices property.
     * 
     * @param pPractices the new value of the mPractices property
     */
    public void setPractices(SortedMap pPractices) {
        mPractices = pPractices;
    }

    /**
     * ajoute une regle de pratique
     * @param pPractice une regle de pratique
     * @param pWeight le poids de la pratique
     */
    public void addPractice(PracticeRuleBO pPractice, Float pWeight) {
        if (null == mPractices) {
            mPractices = new TreeMap();
        }
        mPractices.put(pPractice, pWeight);
    }

    /** (non-Javadoc)
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBO#accept(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor, java.lang.Object)
     */
    public Object accept(QualityRuleBOVisitor pVisitor, Object pArgument) {
        return pVisitor.visit(this, pArgument);
    }
}
