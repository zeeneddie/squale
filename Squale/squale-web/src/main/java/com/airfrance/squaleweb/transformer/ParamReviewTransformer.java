package com.airfrance.squaleweb.transformer;

import com.airfrance.squaleweb.applicationlayer.formbean.results.ParamReviewForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation des paramètres d'historique
 */
public class ParamReviewTransformer
    implements WITransformer
{

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] arg0 )
        throws WTransformerException
    {
        ParamReviewForm form = new ParamReviewForm();
        objToForm( arg0, form );
        return form;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      com.airfrance.welcom.struts.bean.WActionForm) {@inheritDoc}
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        ParamReviewForm form = (ParamReviewForm) pForm;
        form.setTre( (String) pObject[0] );
        form.setRuleId( (String) pObject[1] );
        form.setComponentId( (String) pObject[2] );
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     *      {@inheritDoc}
     */
    public Object[] formToObj( WActionForm arg0 )
        throws WTransformerException
    {
        final int size = 4;
        Object[] result = new Object[size];
        formToObj( arg0, result );
        return result;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm,
     *      java.lang.Object[]) {@inheritDoc}
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        ParamReviewForm form = (ParamReviewForm) pForm;
        int index = 0;
        pObject[index++] = new Integer( form.getNbDays() );
        pObject[index++] = form.getTre();
        pObject[index++] = form.getRuleId();
        pObject[index++] = form.getComponentId();
    }

}
