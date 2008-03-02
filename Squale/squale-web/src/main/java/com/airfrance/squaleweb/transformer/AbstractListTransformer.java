package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;

import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation de liste en form Cette classe permet de factoriser le comportement des form qui contiennent une liste
 * sous form d'ArrayList
 */
public abstract class AbstractListTransformer
    implements WITransformer
{

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparaît.
     * @return le formulaire associé
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        ArrayList resultList = new ArrayList();
        Object obj[] = { resultList };
        formToObj( pForm, obj );
        return obj;
    }

}
