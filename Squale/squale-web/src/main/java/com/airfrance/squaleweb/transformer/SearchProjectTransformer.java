package com.airfrance.squaleweb.transformer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.search.SearchProjectForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * transformer pour la recherche d'un projet
 */
public class SearchProjectTransformer implements WITransformer {

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     * {@inheritDoc}
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        SearchProjectForm form = new SearchProjectForm();
        objToForm(pObject, form);
        return form;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[], com.airfrance.welcom.struts.bean.WActionForm)
     * {@inheritDoc}
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        SearchProjectForm form = (SearchProjectForm) pForm;
        Map projectsWithAudit = new HashMap();
        Map projectsDto = (Map) pObject[0];
        List applications = (List) pObject[1];
        for (Iterator it = projectsDto.keySet().iterator(); it.hasNext();) {
            ComponentDTO dto = (ComponentDTO) it.next();
            AuditDTO auditDTO = (AuditDTO) projectsDto.get(dto);
            if(null != auditDTO) {
            projectsWithAudit.put(
                WTransformerFactory.objToForm(ProjectTransformer.class, new Object[] { dto, applications }),
                WTransformerFactory.objToForm(AuditTransformer.class, new Object[] { auditDTO }));
            } else {
                projectsWithAudit.put(
                    WTransformerFactory.objToForm(ProjectTransformer.class, new Object[] { dto, applications }),
                    null);
            }
        }
        form.setProjectForms(projectsWithAudit);

    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     * {@inheritDoc}
     */
    public Object[] formToObj(WActionForm pForm) throws WTransformerException {
        Object[] obj = new Object[2];
        formToObj(pForm, obj);
        return obj;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm, java.lang.Object[])
     * {@inheritDoc}
     */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException {
        SearchProjectForm form = (SearchProjectForm) pForm;
        pObject[0] = form.getApplicationBeginningName();
        pObject[1] = form.getProjectBeginningName();
    }

}
