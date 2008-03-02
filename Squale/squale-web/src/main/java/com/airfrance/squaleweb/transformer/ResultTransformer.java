package com.airfrance.squaleweb.transformer;

import com.airfrance.squalecommon.datatransfertobject.result.QualityResultDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ResultForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transforme un résultat qualité
 */
public class ResultTransformer
    implements WITransformer
{

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        ResultForm form = new ResultForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      com.airfrance.welcom.struts.bean.WActionForm) {@inheritDoc}
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        QualityResultDTO resultDTO = (QualityResultDTO) pObject[0];
        ResultForm resultForm = (ResultForm) pForm;
        resultForm.setName( resultDTO.getRule().getName() );
        resultForm.setId( "" + resultDTO.getRule().getId() );
        resultForm.setCurrentMark( "" + resultDTO.getMeanMark() );
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     *      {@inheritDoc}
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        throw new WTransformerException( "Not Yet Implemented" );
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm,
     *      java.lang.Object[]) {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        throw new WTransformerException( "Not Yet Implemented" );
    }

}
