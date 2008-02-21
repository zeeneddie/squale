package com.airfrance.squaleweb.applicationlayer.formbean.component.parameters;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.transformer.component.parameters.CppMcCabeProjectConfTransformer;

/**
 * Formulaire de configuration McCabe pour le C++
 */
public class CppMcCabeForm extends AbstractParameterForm {
    /** Script de compilation du C++ */
    private String mCppScript = "";
    /** Dialect */
    private String mDialect = "";
    /**
     * @return le script de compilation
     */
    public String getCppScript() {
        return mCppScript;
    }
    /**
     * @param pCppScript le script de compilation
     */
    public void setCppScript(String pCppScript) {
        mCppScript = pCppScript;
    }

    /**
     * @return le dialect
     */
    public String getDialect() {
        return mDialect;
    }

    /**
     * @param pDialect le dialect
     */
    public void setDialect(String pDialect) {
        mDialect = pDialect;
    }
    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTransformer()
     * 
     * @return le transformer à utiliser
     */
    public Class getTransformer() {
        return CppMcCabeProjectConfTransformer.class;
    }
    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getNameInSession()
     * 
     * @return le nom utilisé en session
     */
    public String getNameInSession() {
        return "cppMcCabeForm";
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getParametersConstants()
     * {@inheritDoc}
     */
    public String[] getParametersConstants() {
        return new String[]{ParametersConstants.CPP, ParametersConstants.DIALECT};
    }

    /** 
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     * {@inheritDoc}
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        setDialect("");
        setCppScript("");
    }
    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTaskName()
     * {@inheritDoc}
     */
    public String getTaskName() {
        return "CppMcCabeTask";
    }
    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#validateConf(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     * {@inheritDoc}
     */
    protected void validateConf(ActionMapping pMapping, HttpServletRequest pRequest) {
        // Traitement du champ cppScript
        setCppScript(getCppScript().trim());
        if (getCppScript().length() == 0) {
            addError("cppScript", new ActionError("error.field.required"));
        }
        // Traitement du dialect
        if (getDialect().length() == 0) {
            addError("dialect", new ActionError("error.field.required"));
        }
    }
}
