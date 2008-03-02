package com.airfrance.squaleweb.transformer;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformateurs DTO <-> Form pour les applications
 * 
 * @author M400842
 */
public class ApplicationTransformer
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
        ApplicationForm form = new ApplicationForm();
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
        ComponentDTO dto = (ComponentDTO) pObject[0];
        ApplicationForm form = (ApplicationForm) pForm;
        // dans le cas d'un ApplicationForm, l'information est redondante car elle se trouve
        // aussi dans la classe mère
        form.setId( dto.getID() );
        form.setApplicationId( "" + dto.getID() );
        form.setApplicationName( dto.getName() );
        form.setNumberOfChildren( "" + dto.getNumberOfChildren() );
        form.setExcludedFromActionPlan( dto.getExcludedFromActionPlan() );
        form.setJustification( dto.getJustification() );
        form.setHasResults( dto.getHasResults() );
        form.setLastUpdate( dto.getLastUpdate() );
        form.setLastUser( dto.getLastUser() );
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new ComponentDTO() };
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
        ApplicationForm form = (ApplicationForm) pForm;
        ComponentDTO dto = (ComponentDTO) pObject[0];
        dto.setID( form.getId() );
        dto.setName( form.getApplicationName() );
    }
}
