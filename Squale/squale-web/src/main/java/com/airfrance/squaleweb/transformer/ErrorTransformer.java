package com.airfrance.squaleweb.transformer;

import com.airfrance.squalecommon.datatransfertobject.result.ErrorDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ErrorForm;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformer ErrorDTO <-> ErrorForm
 * 
 * @author M400842
 */
public class ErrorTransformer
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
        ErrorForm form = new ErrorForm();
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
        ErrorDTO dto = (ErrorDTO) pObject[0];
        ErrorForm form = (ErrorForm) pForm;
        form.setLevel( dto.getLevel() );
        // Code défensif dans le cas où le message serait nul.
        if ( null == dto.getMessageKey() )
        {
            dto.setMessageKey( "No message available for this error" );
        }
        form.setMessage( dto.getMessageKey().replaceAll( "<", "&lt;" ).replaceAll( "\n", "<br/>" ) );
        form.setErrorProjectId( dto.getProjectId() );
        form.setAuditId( dto.getAuditId() );
        form.setTaskName( WebMessages.getString( dto.getTaskName() + ".name" ) );
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new ErrorDTO() };
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
        ErrorForm form = (ErrorForm) pForm;
        ErrorDTO dto = (ErrorDTO) pObject[0];
        dto.setAuditId( form.getAuditId() );
        dto.setProjectId( form.getErrorProjectId() );
        dto.setTaskName( form.getTaskName() );
    }
}
