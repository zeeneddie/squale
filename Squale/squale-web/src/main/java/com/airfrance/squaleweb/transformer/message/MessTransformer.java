package com.airfrance.squaleweb.transformer.message;

import com.airfrance.squalecommon.datatransfertobject.message.MessageDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.messages.MessageForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * 
 */
public class MessTransformer implements WITransformer{

    /**
        * @param pObject l'objet à transformer
        * @throws WTransformerException si un pb apparait.
        * @return le formulaire.
        */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        MessageForm form = new MessageForm();
        objToForm(pObject, form);
        return form;
    }

    /**
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        MessageDTO messageDTO = (MessageDTO) pObject[0];
        MessageForm form = (MessageForm) pForm;
        form.setKey(messageDTO.getKey());
        form.setLanguage(messageDTO.getLang());
        form.setText(messageDTO.getText());
        form.setTitle(messageDTO.getTitle());

    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj(WActionForm pForm) throws WTransformerException {
        Object[] obj = { new MessageDTO()};
        formToObj(pForm, obj);
        return obj;
    }

    /**
     * @param pObject l'objet à remplir
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException {
        MessageForm newsForm = (MessageForm) pForm;
        MessageDTO dto = (MessageDTO) pObject[0];
        dto.setKey(newsForm.getKey());
        dto.setLang(newsForm.getLanguage());
        dto.setText(newsForm.getText());
        dto.setTitle(newsForm.getTitle());
    }

}
