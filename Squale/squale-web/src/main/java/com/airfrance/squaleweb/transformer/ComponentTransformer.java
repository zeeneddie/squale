package com.airfrance.squaleweb.transformer;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ComponentForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformer ComponentDTO <-> ComponentForm
 * 
 * @author M400842
 */
public class ComponentTransformer
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
        ComponentForm form = new ComponentForm();
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
        ComponentForm form = (ComponentForm) pForm;
        form.setId( dto.getID() );
        form.setName( dto.getName() );
        form.setParentId( dto.getIDParent() );
        form.setType( dto.getType() );
        form.setFileName( dto.getFileName() );
        form.setExcludedFromActionPlan( dto.getExcludedFromActionPlan() );
        form.setJustification( dto.getJustification() );
        form.setFullName( dto.getFullName() );
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
        ComponentForm form = (ComponentForm) pForm;
        ComponentDTO dto = (ComponentDTO) pObject[0];
        dto.setID( form.getId() );
        dto.setIDParent( form.getParentId() );
        dto.setType( form.getType() );
        dto.setName( form.getName() );
        dto.setIDParent( form.getParentId() );
        dto.setFileName( form.getFileName() );
        dto.setExcludedFromActionPlan( form.getExcludedFromActionPlan() );
        dto.setJustification( form.getJustification() );
    }

}
