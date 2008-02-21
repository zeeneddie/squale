package com.airfrance.squaleweb.applicationlayer.formbean.component.parameters;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.transformer.component.parameters.MackerConfTransformer;

/**
 * Formulaire pour la configuration de la tâche Macker
 */
public class MackerForm extends AbstractParameterForm {
    
    /** Chemin relatif ou absolu vers le fichier de configuration Macker */
    private String mConfigFile;

    /**
     * Constructeur par défaut
     */
    public MackerForm() {
        mConfigFile = "";
    }

    /**
     * @return le chemin du fichier de configuration
     */
    public String getConfigFile() {
        return mConfigFile;
    }

    /**
     * Modifie le chemin du fichier de configuration
     * 
     * @param pConfigFile le nouveau chemin du fichier de configuration
     */
    public void setConfigFile(String pConfigFile) {
        mConfigFile = pConfigFile;
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTransformer()
     * {@inheritDoc}
     */
    public Class getTransformer() {
        return MackerConfTransformer.class;
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getNameInSession()
     * {@inheritDoc}
     */
    public String getNameInSession() {
        return "mackerForm";
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getParametersConstants()
     * {@inheritDoc}
     */
    public String[] getParametersConstants() {
        return new String[]{ParametersConstants.MACKER};
    }

    /** 
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     * {@inheritDoc}
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        setConfigFile("");
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTaskName()
     * {@inheritDoc}
     */
    public String getTaskName() {
        return "MackerTask";
    }

    /** 
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#validateConf(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     * {@inheritDoc}
     */
    protected void validateConf(ActionMapping pMapping, HttpServletRequest pRequest) {
        // Vérification du fichier de configuration
        setConfigFile(getConfigFile().trim());
        if (getConfigFile().length()==0) {
            addError("configFile", new ActionError("error.field.required"));
        }
    }
}
