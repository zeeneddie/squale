package com.airfrance.squaleweb.applicationlayer.formbean.creation;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Contient une règle qualité.
 * 
 * @version 1.0
 * @author m400842
 */
public class QualityRuleForm extends RootForm {
    
    /**
     * Type de la règle (facteur, critere, pratique)
     */
    private String mType = "";
    /**
     * Nom du facteur
     */
    private String mName = "";
    
    /**
     * Constructeur par défaut
     *
     */
    public QualityRuleForm(){
    }
    
    /**
     * Constructeur complet.
     * 
     * @param pName le nom de la règle.
     * @param pType le type de la règle.
     */
    public QualityRuleForm(final String pName, final String pType){
        mName = pName;
        mType = pType;
    }
    
    /**
     * @return le nom de la règle
     */
    public String getName() {
        return mName;
    }

    /**
     * @return le type de la règle
     */
    public String getType() {
        return mType;
    }

    /**
     * @param pName le nom de la règle
     */
    public void setName(String pName) {
        mName = pName;
    }

    /**
     * @param pType le type de la règle
     */
    public void setType(String pType) {
        mType = pType;
    }

}
