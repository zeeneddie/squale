package com.airfrance.squaleweb.transformer;

import com.airfrance.squalecommon.datatransfertobject.result.RoiDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.roi.RoiForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformeur pour le ROI
 */
public class RoiTransformer
    implements WITransformer
{

    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        RoiForm form = new RoiForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        RoiDTO dto = (RoiDTO) pObject[0];
        RoiForm form = (RoiForm) pForm;
        form.setFormula( dto.getFormula() );
        form.setRoiApplicationId( dto.getApplicationId() );
        form.setTotal( dto.getTotal() );
        form.setMeasures( dto.getMeasures() );
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new RoiDTO() };
        formToObj( pForm, obj );
        return obj;
    }

    /**
     * @param pObject l'objet à remplir
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        RoiForm form = (RoiForm) pForm;
        RoiDTO dto = (RoiDTO) pObject[0];
        dto.setFormula( form.getFormula() );
        dto.setApplicationId( form.getRoiApplicationId() );
        dto.setMeasures( form.getMeasures() );
    }
}
