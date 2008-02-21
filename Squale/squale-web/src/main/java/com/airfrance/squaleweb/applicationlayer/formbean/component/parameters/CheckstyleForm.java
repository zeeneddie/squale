
package com.airfrance.squaleweb.applicationlayer.formbean.component.parameters;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.transformer.component.parameters.CheckstyleProjectConfTransformer;



/**
 * Formulaire pour la configuration de la tâche Checkstyle
 */
public class CheckstyleForm extends AbstractParameterForm {
    
    /**
     * La version selectionnée;
     */
    private String mSelectedRuleSet;
    /**
     * Les versions disponibles
     */
    private String [] mRuleSets;

    /**
     * Constructeur 
     * 
     */
        
    public CheckstyleForm(){
        mSelectedRuleSet="";
        mRuleSets= new String[0];
    }
    
    /**
     * @return la version sélectionnée
     */
    public String getSelectedRuleSet() {
        return mSelectedRuleSet;
    }

    /**
     * @param pSelectedVersion la version
    */
    public void setSelectedRuleSet(String pSelectedVersion) {
        mSelectedRuleSet = pSelectedVersion;
    }

    /**
     * @return Les versions disponibles
     */
    public String [] getVersions() {
        return mRuleSets;
    }

    /**
     * @param pVersions les versions
     */
    public void setVersions(String []pVersions) {
        mRuleSets = pVersions;
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTransformer()
     * 
     * @return le transformer à utiliser
     */
    public Class getTransformer() {
        return CheckstyleProjectConfTransformer.class;
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getNameInSession()
     * 
     * @return le nom en session
     */
    public String getNameInSession() {
        return "checkstyleForm";
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getParametersConstants()
     * {@inheritDoc}
     */
    public String[] getParametersConstants() {
        return new String[]{ParametersConstants.CHECKSTYLE_RULESET_NAME};
    }
    /** 
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     * {@inheritDoc}
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        setSelectedRuleSet("");
        setVersions(new String[0]);
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTaskName()
     * {@inheritDoc}
     */
    public String getTaskName() {
        return "RulesCheckingTask";
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#validateConf(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     * {@inheritDoc}
     */
    protected void validateConf(ActionMapping pMapping, HttpServletRequest pRequest) {
        // Vérification de la version choisie
        setSelectedRuleSet(getSelectedRuleSet().trim());
        if (getSelectedRuleSet().length()==0) {
            addError("selectedRuleSet", new ActionError("error.field.required"));
        }
    }

}
