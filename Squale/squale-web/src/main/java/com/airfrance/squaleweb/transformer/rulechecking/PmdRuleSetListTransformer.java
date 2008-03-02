package com.airfrance.squaleweb.transformer.rulechecking;

import com.airfrance.squaleweb.applicationlayer.formbean.rulechecking.PmdRuleSetListForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation d'une liste de ruleset PMD
 */
public class PmdRuleSetListTransformer
    extends AbstractRuleSetListTransformer
{

    /**
     * Constructeur
     */
    public PmdRuleSetListTransformer()
    {
        super( PmdRuleSetTransformer.class );
    }

    /**
     * @param pObject le tableau de CheckstyleDTO à transformer en formulaires.
     * @throws WTransformerException si un pb apparaît.
     * @return le formulaire associé
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        PmdRuleSetListForm form = new PmdRuleSetListForm();
        objToForm( pObject, form );
        return form;
    }
}
