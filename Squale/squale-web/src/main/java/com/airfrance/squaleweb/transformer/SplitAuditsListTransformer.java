package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.AuditForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.SplitAuditsListForm;
import com.airfrance.squaleweb.comparator.AuditComparator;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transforme les audits des applications publiques ou non
 */
public class SplitAuditsListTransformer implements WITransformer {

    /** 
     * {@inheritDoc}
     * @param pObject {@inheritDoc} - les listes des audits
     * @return le formulaire transformé
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        SplitAuditsListForm form = new SplitAuditsListForm();
        objToForm(pObject, form);
        return form;
    }

    /** 
     * {@inheritDoc}
     * @param pObject {@inheritDoc} - les listes des audits
     * @param pForm {@inheritDoc} - le splitAuditsListForm
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        SplitAuditsListForm form = (SplitAuditsListForm) pForm;
        // Les audits publiques
        List publicAudits = (List) pObject[0];
        // On transforme en formulaire et on affecte
        form.setPublicAudits(transformAuditsList(publicAudits));
        // Les audits des applications appartenant à l'utilisateur
        List audits = (List) pObject[1];
        // On transforme en formulaire et on affecte
        form.setAudits(transformAuditsList(audits));

    }

    /**
     * @param pAudits les auditDTO à transformer en formulaire
     * @return la collection transformée
     * @throws WTransformerException si erreur
     */
    private List transformAuditsList(List pAudits) throws WTransformerException {
        int size = pAudits.size();
        List listAuditForm = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            AuditDTO dto = (AuditDTO) pAudits.get(i);
            AuditForm auditForm = (AuditForm) WTransformerFactory.objToForm(AuditTransformer.class, dto);
            auditForm.setApplicationName(dto.getApplicationName());
            listAuditForm.add(auditForm);
        }
        Collections.sort(listAuditForm, new AuditComparator());
        Collections.reverse(listAuditForm);
        return listAuditForm;
    }

    /** 
     * {@inheritDoc}
     * @param form {@inheritDoc}
     * @return le tableau des objets transformés
     */
    public Object[] formToObj(WActionForm form) throws WTransformerException {
        throw new WTransformerException("Not Yet Implemented");
    }

    /** 
     * {@inheritDoc}
     * @param form {@inheritDoc}
     * @param object {@inheritDoc}
     */
    public void formToObj(WActionForm form, Object[] object) throws WTransformerException {
        throw new WTransformerException("Not Yet Implemented");
    }
}
