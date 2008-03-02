package com.airfrance.squaleweb.transformer.message;

import com.airfrance.squalecommon.datatransfertobject.message.MessageDTO;
import com.airfrance.squalecommon.datatransfertobject.message.NewsDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.messages.MessageForm;
import com.airfrance.squaleweb.applicationlayer.formbean.messages.NewsForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * 
 */
public class NewsTranformer
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
        NewsForm form = new NewsForm();
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
        NewsDTO newsDTO = (NewsDTO) pObject[0];
        NewsForm form = (NewsForm) pForm;
        form.setLangSet( newsDTO.getLangSet() );
        form.setBeginningDate( newsDTO.getBeginningDate() );
        form.setEndDate( newsDTO.getEndDate() );
        form.setKey( newsDTO.getKey() );
        form.setId( newsDTO.getId() );
        MessageForm messForm =
            (MessageForm) WTransformerFactory.objToForm( MessTransformer.class, newsDTO.getMessage() );
        form.setMessage( messForm );
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new NewsDTO() };
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
        NewsForm newsForm = (NewsForm) pForm;
        NewsDTO dto = (NewsDTO) pObject[0];
        MessageDTO messDto =
            (MessageDTO) WTransformerFactory.formToObj( MessTransformer.class, newsForm.getMessage() )[0];
        dto.setLangSet( newsForm.getLangSet() );
        dto.setMessage( messDto );
        dto.setId( newsForm.getId() );
        dto.setBeginningDate( newsForm.getBeginningDate() );
        dto.setEndDate( newsForm.getEndDate() );
        dto.setKey( newsForm.getKey() );
    }

}
