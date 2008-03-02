package com.airfrance.squaleweb.transformer;

import com.airfrance.squalecommon.datatransfertobject.config.SourceManagementDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.config.SourceManagementForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transforme SourceManagementForm <-> SourceManagementDTO
 */
public class SourceManagementTransformer
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
        SourceManagementForm form = new SourceManagementForm();
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
        SourceManagementDTO managerDTO = (SourceManagementDTO) pObject[0];
        SourceManagementForm form = (SourceManagementForm) pForm;
        form.setId( managerDTO.getId() );
        form.setName( managerDTO.getName() );
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new SourceManagementDTO() };
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
        SourceManagementForm managerForm = (SourceManagementForm) pForm;
        SourceManagementDTO dto = (SourceManagementDTO) pObject[0];
        dto.setId( Long.parseLong( managerForm.getId() ) );
        dto.setName( managerForm.getName() );
    }
}
