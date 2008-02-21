package com.airfrance.squaleweb.applicationlayer.formbean.component.parameters;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.transformer.component.parameters.CppTestProjectConfTransformer;

/**
 * Formulaire pour la configuration de la tâche CppTest
 */
public class CppTestForm extends AbstractParameterForm {
    /** Script de génération */
    private String mScript;
    /** Jeu de règles appliqué au sens Clearcase */
    private String mSelectedRuleSet;
    /** Jeux de règles disponibles */
    private Collection mRuleSets;

    /**
     * Constructeur
     *
     */
    public CppTestForm() {
        mScript = "";
        mSelectedRuleSet = "";
    }

    /**
     * @return RuleSet au sens CppTest
     */
    public String getSelectedRuleSet() {
        return mSelectedRuleSet;
    }

    /**
     * @param pSelectedRuleSet nom du jeu de règles
     */
    public void setSelectedRuleSet(String pSelectedRuleSet) {
        mSelectedRuleSet = pSelectedRuleSet;
    }
    /**
     * @return script
     */
    public String getScript() {
        return mScript;
    }

    /**
     * @param pScript script
     */
    public void setScript(String pScript) {
        mScript = pScript;
    }

    /**
     * @return rulesets définis
     */
    public Collection getRuleSets() {
        return mRuleSets;
    }

    /**
     * @param pRuleSets rulesets définis
     */
    public void setRuleSets(Collection pRuleSets) {
        mRuleSets = pRuleSets;
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTransformer()
     * {@inheritDoc}
     */
    public Class getTransformer() {
        return CppTestProjectConfTransformer.class;
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getNameInSession()
     * {@inheritDoc}
     */
    public String getNameInSession() {
        return "cppTestForm";
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getParametersConstants()
     * {@inheritDoc}
     */
    public String[] getParametersConstants() {
        return new String[]{ParametersConstants.CPPTEST};
    }

    /** 
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     * {@inheritDoc}
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        setScript("");
        setSelectedRuleSet("");
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTaskName()
     * {@inheritDoc}
     */
    public String getTaskName() {
        return "CppTestTask";
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#validateConf(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     * {@inheritDoc}
     */
    protected void validateConf(ActionMapping pMapping, HttpServletRequest pRequest) {
        // Traitement du champ ruleSetId
        setSelectedRuleSet(getSelectedRuleSet().trim());
        if (getSelectedRuleSet().length() == 0) {
            addError("selectedRuleSet", new ActionError("error.field.required"));
        }
        // Traitement du champ script
        setScript(getScript().trim());
        if (getScript().length() == 0) {
            addError("script", new ActionError("error.field.required"));
        }
    }
}
