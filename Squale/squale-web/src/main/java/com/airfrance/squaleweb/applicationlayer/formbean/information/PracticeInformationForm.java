package com.airfrance.squaleweb.applicationlayer.formbean.information;

import java.util.Collection;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Classe permettant d'afficher des informations sur une pratique
 */
public class PracticeInformationForm extends RootForm {

    /** le trigger */
    private String trigger = "";
    
    /** 
     * Les mesures extraites de la formule
     * sous la forme ckjm.classe.cbo (i.e outil.typeComposant.attribut)
     */
    private Collection usedTres;
    
    /** la formule utilisée pour le calcul */
    private String formula = "";
    
    /**
     * Dans le cas de formule conditionnelle
     */
    private String[] formulaCondition;
    
    /** la correction à apporter */
    private String correction = "";
    
    /** la description de la pratique */
    private String description = "";
    
    /** le nom de la pratique */
    private String name = "";

    /**
     * @return la correction
     */
    public String getCorrection() {
        return correction;
    }

    /**
     * @return la description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return la formule
     */
    public String getFormula() {
        return formula;
    }

    /**
     * @return le nom
     */
    public String getName() {
        return name;
    }

    /**
     * @return le trigger
     */
    public String getTrigger() {
        return trigger;
    }

    /**
     * @param pCorrection la correction
     */
    public void setCorrection(String pCorrection) {
        correction = pCorrection;
    }

    /**
     * @param pDescription la description
     */
    public void setDescription(String pDescription) {
        description = pDescription;
    }

    /**
     * @param pFormula la formule
     */
    public void setFormula(String pFormula) {
        formula = pFormula;
    }

    /**
     * @param pName le nom
     */
    public void setName(String pName) {
        name = pName;
    }

    /**
     * @param pTrigger le trigger
     */
    public void setTrigger(String pTrigger) {
        trigger = pTrigger;
    }

    /**
     * @return les conditions de la formule
     */
    public String[] getFormulaCondition() {
        return formulaCondition;
    }

    /**
     * @param strings les conditions de la formule
     */
    public void setFormulaCondition(String[] strings) {
        formulaCondition = strings;
    }

    /**
     * @return les mesures utilisées dans la formule
     */
    public Collection getUsedTres() {
        return usedTres;
    }

    /**
     * @param pTres les mesures utilisées dans la formule
     */
    public void setUsedTres(Collection pTres) {
        usedTres = pTres;
    }

}
