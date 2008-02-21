package com.airfrance.squaleweb.transformer.component.parameters.external;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.externalTask.BugTrackingQCForm;
import com.airfrance.squaleweb.taskconfig.qc.ExtBugTrackingQCTaskConfig;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;


/**
 * Transformation des paramètres de bug tracking QC 
 */
public class BugTrackingQCConfTransformer implements WITransformer {



    /** (non-Javadoc)
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     */
    public WActionForm objToForm(Object[] object) throws WTransformerException {
        BugTrackingQCForm form = new BugTrackingQCForm();
        objToForm(object,form);
        return form;
    }

    /** (non-Javadoc)
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[], com.airfrance.welcom.struts.bean.WActionForm)
     */
    public void objToForm(Object[] object, WActionForm form) throws WTransformerException {
        BugTrackingQCForm btqcForm = (BugTrackingQCForm) form;
        MapParameterDTO projectParams = (MapParameterDTO) object[0];
        MapParameterDTO btqcParams = (MapParameterDTO)projectParams.getParameters().get(ExtBugTrackingQCTaskConfig.TASK_NAME);
        if(null != btqcParams){
            StringParameterDTO btqcLoginParam = (StringParameterDTO)btqcParams.getParameters().get(ExtBugTrackingQCTaskConfig.BUGTRACKINGQC_LOGIN);
            String btqcLogin = btqcLoginParam.getValue();
            btqcForm.setQCLogin(btqcLogin);
            StringParameterDTO btqcPwdParam = (StringParameterDTO) btqcParams.getParameters().get(ExtBugTrackingQCTaskConfig.BUGTRACKINGQC_PWD);
            String btqcPwd = btqcPwdParam.getValue();
            btqcForm.setQCPassword(btqcPwd);
            StringParameterDTO btqcUrlParam = (StringParameterDTO) btqcParams.getParameters().get(ExtBugTrackingQCTaskConfig.BUGTRACKINGQC_URL);
            String btqcUrl = btqcUrlParam.getValue();
            btqcForm.setQCUrl(btqcUrl);
            StringParameterDTO btqcTraceParam = (StringParameterDTO) btqcParams.getParameters().get(ExtBugTrackingQCTaskConfig.BUGTRACKINGQC_TRACE);
            String btqcTrace = btqcTraceParam.getValue();
            btqcForm.setQCTrace(btqcTrace);
        }
    }

    /** (non-Javadoc)
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     */
    public Object[] formToObj(WActionForm form) throws WTransformerException {
        Object[] objet = {new MapParameterDTO()};
        formToObj(form, objet);
        return objet;
    }

    /** (non-Javadoc)
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm, java.lang.Object[])
     */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException {
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        BugTrackingQCForm btqcForm = (BugTrackingQCForm) pForm;
        MapParameterDTO btqcParams = new MapParameterDTO();
        
        StringParameterDTO btqcLoginParam = new StringParameterDTO();        
        btqcLoginParam.setValue(btqcForm.getQCLogin());
        btqcParams.getParameters().put(ExtBugTrackingQCTaskConfig.BUGTRACKINGQC_LOGIN,btqcLoginParam);
        
        StringParameterDTO btqcPwdParam = new StringParameterDTO();
        btqcPwdParam.setValue(btqcForm.getQCPassword());
        btqcParams.getParameters().put(ExtBugTrackingQCTaskConfig.BUGTRACKINGQC_PWD,btqcPwdParam);
        
        StringParameterDTO btqcUrlParam = new StringParameterDTO();
        btqcUrlParam.setValue(btqcForm.getQCUrl());     
        btqcParams.getParameters().put(ExtBugTrackingQCTaskConfig.BUGTRACKINGQC_URL,btqcUrlParam);
        
        StringParameterDTO btqcTraceParam = new StringParameterDTO();
        btqcTraceParam.setValue(btqcForm.getQCTrace());
        btqcParams.getParameters().put(ExtBugTrackingQCTaskConfig.BUGTRACKINGQC_TRACE,btqcTraceParam);
           
        params.getParameters().put(ExtBugTrackingQCTaskConfig.TASK_NAME,btqcParams);
        
    }

}
