package com.airfrance.squaleweb.applicationlayer.formbean.component.parameters;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.transformer.component.parameters.JavaMockCompilingTransformer;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;

/**
 * Bean pour la tâche de compilation dans les cas où le projet
 * est déjà compilé
 */
public class JavaMockCompilingForm extends AbstractParameterForm {
    
    /** Chemin vers les sources compilées */
    private String[] mCompiledSources;
    
    /** Chemin vers le classpath du projet */
    private String mClasspath;
    
    /**
     * Version de java
     */
    private String mDialect;
    
    /**
     * Constructeur par défaut
     */
    public JavaMockCompilingForm() {
        mClasspath = "";
        mDialect = "";
        mCompiledSources = new String[0];
    }
    
    /**
     * @return le chemin vers le classpath du projet
     */
    public String getClasspath() {
        return mClasspath;
    }
    
    /**
     * Modifie le chemin vers le classpath du projet
     * @param pClasspath le chemin vers le classpath du projet
     */
    public void setClasspath(String pClasspath) {
        mClasspath = pClasspath;
    }

    /**
     * @return la version de java
     */
    public String getDialect() {
        return mDialect;
    }

    /**
     * @param pDialect la version de java
     */
    public void setDialect(String pDialect) {
        mDialect = pDialect;
    }

    /**
     * @return les chemins vers les sources compilées
     */
    public String[] getCompiledSources() {
        return mCompiledSources;
    }

    /**
     * @param pSrcs les chemins vers les sources compilées
     */
    public void setCompiledSources(String[] pSrcs) {
        mCompiledSources = pSrcs;
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTransformer()
     * {@inheritDoc}
     */
    public Class getTransformer() {
        return JavaMockCompilingTransformer.class;
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getNameInSession()
     * {@inheritDoc}
     */
    public String getNameInSession() {
        return "javaMockCompilingForm";
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getParametersConstants()
     * {@inheritDoc}
     */
    public String[] getParametersConstants() {
        return new String[]{ParametersConstants.COMPILED, ParametersConstants.DIALECT};
    }

    /** 
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     * {@inheritDoc}
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        setClasspath("");
        setCompiledSources(new String[0]);
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTaskName()
     * {@inheritDoc}
     */
    public String getTaskName() {
        return "JavaMockCompilingTask";
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#validateConf(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     * {@inheritDoc}
     */
    protected void validateConf(ActionMapping pMapping, HttpServletRequest pRequest) {
        // Les sources compilées
        setCompiledSources(SqualeWebActionUtils.cleanValues(getCompiledSources()));
        if(getCompiledSources().length == 0) {
            addError("compiledSources", new ActionError("error.field.required"));
        }
        setClasspath(getClasspath().trim());
        // Le classpath est un champ obligatoire tout comme le chemin vers les sources compilées
        if(getClasspath().length() == 0) {
            addError("classpath", new ActionError("error.field.required"));
        }
        // Le dialect est aussi obligatoire
        if(getDialect().length() == 0) {
            addError("dialect", new ActionError("error.field.required"));
        }
    }

}
