package com.airfrance.squaleweb.transformer.stats;

import com.airfrance.squalecommon.datatransfertobject.stats.ProfilStatsDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.stats.ProfilStatsForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 */
public class ProfileStatsTransformer implements WITransformer {

    /**
     * @param pObject le tableau d'objet contenant l'objet à transformer
     * @return le form résultat de la transformation
     * @throws WTransformerException en cas d'échec
     */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        ProfilStatsForm form = new ProfilStatsForm();
        objToForm(pObject, form);
        return form;
    }

    /**
     * @param pObject le tableau d'objet contenant l'objet à transformer
     * @param pForm le form résultat
     * @throws WTransformerException en cas d'échec
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        ProfilStatsForm form = (ProfilStatsForm) pForm;
        ProfilStatsDTO dto = (ProfilStatsDTO) pObject[0];
        form.setLoc(dto.getLoc());
        form.setNbProjects(dto.getNbProjects());
        form.setProfileName(dto.getProfileName());
    }

    /**
     * @deprecated n'a pas de sens
     * @param pForm le formulaire
     * @throws WTransformerException si un pb apparait.
     * @return rien mais lance systématiquement une exception
     */
    public Object[] formToObj(WActionForm pForm) throws WTransformerException {
        throw new WTransformerException("deprecated");
    }

    /**
     * @deprecated n'a pas de sens
     * @param pForm le formulaire
     * @param pTab les paramètres
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj(WActionForm pForm, Object[] pTab) throws WTransformerException {
        throw new WTransformerException("deprecated");
    }

}
