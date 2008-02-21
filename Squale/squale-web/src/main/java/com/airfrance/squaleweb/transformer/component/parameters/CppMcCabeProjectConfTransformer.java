package com.airfrance.squaleweb.transformer.component.parameters;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.CppMcCabeForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation des paramètres McCabe pour le C++
 */
public class CppMcCabeProjectConfTransformer implements WITransformer {

    /**
     * {@inheritDoc}
     * @param arg0 {@inheritDoc}
     * @return le formulaire transformé
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     */
    public WActionForm objToForm(Object[] arg0) throws WTransformerException {
        CppMcCabeForm mcCabeForm = new CppMcCabeForm();
        objToForm(arg0, mcCabeForm);
        return mcCabeForm;
    }


    /** 
     * {@inheritDoc}
     * @param arg0 {@inheritDoc}
     * @return le tableau d'objets
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     */
    public Object[] formToObj(WActionForm arg0) throws WTransformerException {
        // Méthode non utilisée
        throw new WTransformerException("not yet implemented");
    }

    /** 
     * {@inheritDoc}
     * @param pObject {@inheritDoc}
     * @param pForm {@inheritDoc}
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[], com.airfrance.welcom.struts.bean.WActionForm)
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        // Récupération des paramètres
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        CppMcCabeForm mcCabeForm = (CppMcCabeForm) pForm;
        StringParameterDTO dialectParam = (StringParameterDTO) params.getParameters().get(ParametersConstants.DIALECT);
        if(dialectParam != null) {
            // On remplit le form
            mcCabeForm.setDialect(dialectParam.getValue());
        }
        MapParameterDTO cppParams = (MapParameterDTO) params.getParameters().get(ParametersConstants.CPP);
        if (cppParams != null) {
            // On remplit le form
            StringParameterDTO script = (StringParameterDTO) cppParams.getParameters().get(ParametersConstants.CPP_SCRIPTFILE);
            mcCabeForm.setCppScript(script.getValue());
        }
    }

    /**
     * {@inheritDoc}
     * @param pForm {@inheritDoc}
     * @param pObject {@inheritDoc}    
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm, java.lang.Object[])
     */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException {
        // Récupération des paramètres
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        CppMcCabeForm mcCabeForm = (CppMcCabeForm) pForm;
        MapParameterDTO cppParams = new MapParameterDTO();
        // Le script
        StringParameterDTO script = new StringParameterDTO();
        script.setValue(mcCabeForm.getCppScript());
        cppParams.getParameters().put(ParametersConstants.CPP_SCRIPTFILE, script);
        params.getParameters().put(ParametersConstants.CPP, cppParams);
        // Le dialect
        StringParameterDTO dialect = new StringParameterDTO();
        dialect.setValue(mcCabeForm.getDialect());
        params.getParameters().put(ParametersConstants.DIALECT, dialect);
    }

}
