package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.rulechecking.RuleCheckingItemDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.results.RuleCheckingItemForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.RuleCheckingItemsListForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transforme un dto en form un RuleCheckingItemsList.
 */
public class RuleCheckingItemsListTransformer
    implements WITransformer
{

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        RuleCheckingItemsListForm items = new RuleCheckingItemsListForm();
        objToForm( pObject, items );
        return items;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      com.airfrance.welcom.struts.bean.WActionForm) {@inheritDoc}
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        // Récupération des paramètres
        String ruleName = (String) pObject[0];
        Collection details = (Collection) pObject[1];
        RuleCheckingItemsListForm form = (RuleCheckingItemsListForm) pForm;
        RuleCheckingItemTransformer transformer = new RuleCheckingItemTransformer();
        ArrayList detailsForm = new ArrayList();
        boolean hasComponentLink = false;
        for ( Iterator it = details.iterator(); it.hasNext(); )
        {
            RuleCheckingItemDTO itemDTO = (RuleCheckingItemDTO) it.next();
            RuleCheckingItemForm itemForm = (RuleCheckingItemForm) transformer.objToForm( new Object[] { itemDTO } );
            detailsForm.add( itemForm );
            // On teste met le booléen à true dès qu'un item possède un lien vers un composant
            if ( -1 != itemForm.getComponentId() || -1 != itemForm.getComponentInvolvedId() )
            {
                hasComponentLink = true;
            }
        }
        form.setComponentLink( hasComponentLink );
        form.setDetails( detailsForm );
        form.setRuleName( ruleName );
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     *      {@inheritDoc}
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        throw new WTransformerException( "not yet implemented" );
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm,
     *      java.lang.Object[]) {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        throw new WTransformerException( "not yet implemented" );
    }
}
