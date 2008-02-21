package com.airfrance.squaleweb.transformer;

import java.util.List;
import java.util.Map;

import com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectFactorsForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectSummaryForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ResultListForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Conversion de la synthèse d'un projet
 */
public class ProjectSummaryTransformer implements WITransformer {

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     * {@inheritDoc}
     */
    public WActionForm objToForm(Object[] arg0) throws WTransformerException {
        return null;
    }

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[], com.airfrance.welcom.struts.bean.WForm)
     * {@inheritDoc}
     */
    public void objToForm(Object[] arg0, WActionForm arg1) throws WTransformerException {
        ProjectSummaryForm form = (ProjectSummaryForm) arg1;
        // Positionnement des facteurs
        ProjectFactorsForm factors = (ProjectFactorsForm) WTransformerFactory.objToForm(ProjectFactorsTransformer.class, new Object[]{arg0[0], arg0[1]});
        form.setFactors(factors);
        form.setComparableAudits(factors.getComparableAudits());
        
        // Positionnement de la volumétrie
        Map volumetryData = (Map) arg0[2];
        // Récupération des clefs
        List measureKeys = (List) volumetryData.get(null);
        List measureValues = (List) volumetryData.get(arg0[0]);
        if (measureValues!=null) {
            Object obj[] = { measureKeys, measureValues };
            ResultListForm volumetry = (ResultListForm) WTransformerFactory.objToForm(ResultListTransformer.class, obj);
            form.setVolumetry(volumetry);
        }
        final int errors_id = 3;
        form.setHaveErrors((Boolean) arg0[errors_id]);
        final int ide_id = 4;
        form.setExportIDE((Boolean) arg0[ide_id]);
    }

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WForm)
     * {@inheritDoc}
     */
    public Object[] formToObj(WActionForm arg0) throws WTransformerException {
        return null;
    }

    /** 
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WForm, java.lang.Object[])
     * {@inheritDoc}
     */
    public void formToObj(WActionForm arg0, Object[] arg1) throws WTransformerException {
    }

}
