package com.airfrance.squaleweb.transformer;

import com.airfrance.squalecommon.datatransfertobject.config.ServeurDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.config.ServeurListForm;

import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Transformer de liste de serveur
 */
public class ServeurListTransformer
    extends AbstractListTransformer
{

    /**
     * {@inheritDoc}
     * 
     * @param object le tableau des objets nécessaires à la transformation
     * @return le formulaire transformé
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     */
    public WActionForm objToForm( Object[] object )
        throws WTransformerException
    {
        ServeurListForm form = new ServeurListForm();
        objToForm( object, form );
        return form;
    }

    /**
     * {@inheritDoc}
     * 
     * @param object {@inheritDoc} - la liste des serveurs sous forme DTO
     * @param form le formulaire de liste de serveur
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      com.airfrance.welcom.struts.bean.WActionForm)
     */
    public void objToForm( Object[] object, WActionForm form )
        throws WTransformerException
    {
        ArrayList lServeurListDTO = (ArrayList) object[0];
        ServeurListForm lServeurListForm = (ServeurListForm) form;
        ArrayList ar = new ArrayList();
        Iterator it = lServeurListDTO.iterator();
        while ( it.hasNext() )
        {
            ServeurDTO lServeurDTO = (ServeurDTO) it.next();
            ar.add( WTransformerFactory.objToForm( ServeurTransformer.class, lServeurDTO ) );
        }
        lServeurListForm.setServeurs( ar );
    }

    /**
     * Méthode non implémentée {@inheritDoc}
     * 
     * @param form {@inheritDoc}
     * @param object {@inheritDoc}
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm,
     *      java.lang.Object[])
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        // méthode non utilisée
    }

}
