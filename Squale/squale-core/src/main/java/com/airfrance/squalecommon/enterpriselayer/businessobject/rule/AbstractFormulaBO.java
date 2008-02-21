package com.airfrance.squalecommon.enterpriselayer.businessobject.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Formule de calcul
 * Une formule de calcul est associée à une ou plusieurs pratiques.
 * Une formule s'applique à un seul type de composant : une classe ou
 * une méthode par exemple.
 * Elle décrit la façon dont une note est attribuée en fonction de
 * mesure(s). Plusieurs mesures peuvent être utilisées dans la formule,
 * par exemple une mesure mccabe et une mesure sonarj.
 * Une condition d'activation permet de calculer ou non une formule.
 * La syntaxe de la condition est du type Jython et ressemble à celà :
 * mccabe.wmc >= 8
 * Dans cet exemple, la mesure de type mccabe nommée wmc est utilisée.
 * 
 * @hibernate.class 
 * table="Formula"
 * discriminator-value="AbstractFormula" // theoriquement pas besoin car classe abstraite
 * mutable="true"
 * 
 * @hibernate.discriminator 
 * column="subclass"
 * type="string"
 * not-null="true"
 * 
 */
public abstract class AbstractFormulaBO implements Serializable {

    /** Le type de formule SIMPLE */
    public final static String TYPE_SIMPLE = "Simple";

    /** Le type de formule SIMPLE */
    public final static String TYPE_CONDITION = "Condition";

    /**
     * Contient le niveau de la formule (Classe, méthode, fichier, package ou projet) 
     */
    private String mComponentLevel;
    /**
     * Mesures composant la formules
     * Par exemple une mesure de type McCabe et une mesure de type SonarJ
     */
    private Collection mMeasureKinds = new ArrayList();
    /**
     * @return niveau du composant associé
     * 
     * @hibernate.property 
     * name="componentLevel" 
     * column="ComponentLevel" 
     * type="string" 
     * not-null="false"
     * unique="false"
     */
    public String getComponentLevel() {
        return mComponentLevel;

    }
    /**
     * Affecte le niveau du composant
     * @param pComponentLevel level
     */
    public void setComponentLevel(String pComponentLevel) {
        mComponentLevel = pComponentLevel;
    }
    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId = -1;
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
     * column="FormulaId" 
     * unsaved-value="-1" 
     * length="19"
     * @hibernate.generator-param name="sequence" value="formula_sequence" 
     * 
     */
    public long getId() {
        return mId;
    }
    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     */
    public void setId(long pId) {
        mId = pId;
    }
    /** Condition d'activation de la formule */
    private String mTriggerCondition;

    /**
     * @return trigger
     * 
     * @hibernate.property 
     * name="triggerCondition" 
     * column="TriggerCondition" 
     * type="string" 
     * length="4000"
     * not-null="false"
     * unique="false"
     */
    public String getTriggerCondition() {
        return mTriggerCondition;
    }

    /**
     * @param pString trigger
     */
    public void setTriggerCondition(String pString) {
        mTriggerCondition = pString;
    }

    /**
     * @return mesures
     * 
     * @hibernate.bag 
     * table="Formula_Measures" 
     * lazy="false" 
     * 
     * @hibernate.collection-key 
     * column="FormulaId"
     * 
     * @hibernate.collection-element
     * column="Measure" 
     * type="string"
     */
    public Collection getMeasureKinds() {
        return mMeasureKinds;
    }

    /**
     * @param pStrings mesures
     */
    public void setMeasureKinds(Collection pStrings) {
        mMeasureKinds = pStrings;
    }

    /**
     * Ajout d'un type de mesure
     * @param pMeasureKind mesure
     */
    public void addMeasureKind(String pMeasureKind) {
        mMeasureKinds.add(pMeasureKind);
    }

    /**
     * 
     * @param pVisitor visiteur
     * @param pArgument argument
     * @return objet
     */
    public abstract Object accept(FormulaVisitor pVisitor, Object pArgument);

    /**
     * 
     * @return le type
     */
    public abstract String getType();
}
