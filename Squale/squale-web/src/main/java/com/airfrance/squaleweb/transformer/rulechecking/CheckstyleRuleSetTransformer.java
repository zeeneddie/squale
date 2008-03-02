package com.airfrance.squaleweb.transformer.rulechecking;

import com.airfrance.squalecommon.datatransfertobject.rulechecking.CheckstyleDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.rulechecking.CheckstyleRuleSetForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * @author E6400802 Transformation d'un jeu de règles checkstyle
 */
public class CheckstyleRuleSetTransformer
    extends AbstractRuleSetTransformer
{
    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        CheckstyleRuleSetForm form = new CheckstyleRuleSetForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new CheckstyleDTO() };
        formToObj( pForm, obj );
        return obj;
    }

}
