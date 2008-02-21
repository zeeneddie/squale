package com.airfrance.squaleweb.transformer.component.parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.ListParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.ClearCaseForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation des paramètres ClearCase
 */
public class ClearCaseConfTransformer implements WITransformer {

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     * 
     * @param pObject le tableau des objets
     * @return le formulaire transformé
     * @throws WTransformerException si erreur
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        ClearCaseForm clearCaseForm = new ClearCaseForm();
        objToForm(pObject, clearCaseForm);
        return clearCaseForm;
    }

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[], com.airfrance.welcom.struts.bean.WActionForm)
     * 
     * @param pObject le tableau des objets
     * @param pForm le formulaire
     * @throws WTransformerException si erreur
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        MapParameterDTO projectParams = (MapParameterDTO) pObject[0];
        MapParameterDTO params = (MapParameterDTO) projectParams.getParameters().get(ParametersConstants.CLEARCASE);
        if (params != null) {
            // On remplit le form
            ClearCaseForm clearCaseForm = (ClearCaseForm) pForm;
            Map ccParams = params.getParameters();
            StringParameterDTO appli = (StringParameterDTO) ccParams.get(ParametersConstants.APPLI);
            clearCaseForm.setAppli(appli.getValue());
            StringParameterDTO branch = (StringParameterDTO) ccParams.get(ParametersConstants.BRANCH);
            clearCaseForm.setLabelAudited(branch.getValue());
            ListParameterDTO vobsList = (ListParameterDTO) ccParams.get(ParametersConstants.VOBS);
            List vobs = vobsList.getParameters();
            Iterator it = vobs.iterator();
            String[] locations = new String[vobs.size()];
            int index = 0;
            while (it.hasNext()) {
                StringParameterDTO vob = (StringParameterDTO) it.next();
                locations[index] = vob.getValue();
                index++;
            }
            clearCaseForm.setLocation(locations);
        }
        
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     * 
     * @param pForm le formulaire
     * @return le tableau des objets transformés
     * @throws WTransformerException si erreur
     */
    public Object[] formToObj(WActionForm pForm) throws WTransformerException {
        Object[] obj = {new MapParameterDTO()};
        formToObj(pForm, obj);
        return obj;
    }

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm, java.lang.Object[])
     * 
     * @param pForm le formulaire
     * @param pObject le tableau des objets
     * @throws WTransformerException si erreur
     */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException {
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        ClearCaseForm clearCaseForm = (ClearCaseForm) pForm;
        // On ajoute les paramètres clearcase aux paramètres
        // généraux du projet
        Map clearCaseParams = new HashMap();
        // appli:
        StringParameterDTO appli = new StringParameterDTO();
        appli.setValue(clearCaseForm.getAppli());
        clearCaseParams.put(ParametersConstants.APPLI, appli);
        // branch:
        StringParameterDTO branch = new StringParameterDTO();
        branch.setValue(clearCaseForm.getLabelAudited());
        clearCaseParams.put(ParametersConstants.BRANCH, branch);
        // vobs
        ListParameterDTO vobs = new ListParameterDTO();
        ArrayList vobsList = new ArrayList();
        String[] location = clearCaseForm.getLocation();
        for (int i = 0; i < location.length; i++) {
            StringParameterDTO strParam = new StringParameterDTO();
            strParam.setValue(location[i]);
            vobsList.add(strParam);
        }
        vobs.setParameters(vobsList);
        clearCaseParams.put(ParametersConstants.VOBS, vobs);
        MapParameterDTO ccMap = new MapParameterDTO();
        ccMap.setParameters(clearCaseParams);
        params.getParameters().put(ParametersConstants.CLEARCASE, ccMap);
    }

}
