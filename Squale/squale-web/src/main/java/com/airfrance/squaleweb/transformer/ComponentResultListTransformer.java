package com.airfrance.squaleweb.transformer;

import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.results.ComponentListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ComponentResultListForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transforms a list of "result components"
 */
public class ComponentResultListTransformer
    implements WITransformer
{

    /**
     * {@inheritDoc}
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        ComponentResultListForm form = new ComponentResultListForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * {@inheritDoc}
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        ComponentResultListForm componentsResultList = (ComponentResultListForm) pForm;
        int index = 0;
        List components = (List) pObject[index++];
        String[] keys = (String[]) pObject[index++];
        String[] values = (String[]) pObject[index++];
        componentsResultList.setComponentListForm( (ComponentListForm)WTransformerFactory.objToForm( ComponentListTransformer.class,
                                       new Object[] { components } ));
        componentsResultList.setTreKeys( keys );
        componentsResultList.setTreValues( values );
    }

    /**
     * {@inheritDoc}
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        throw new WTransformerException( "not yet implemented" );
    }

    /**
     * {@inheritDoc}
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        throw new WTransformerException( "not yet implemented" );
    }

}
