package com.airfrance.squaleweb.transformer.rulechecking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import com.airfrance.squaleweb.applicationlayer.formbean.rulechecking.AbstractRuleSetForm;
import com.airfrance.squaleweb.applicationlayer.formbean.rulechecking.AbstractRuleSetListForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformation d'une liste de ruleset
 */
public abstract class AbstractRuleSetListTransformer
    implements WITransformer
{

    /**
     * Transformer pour les ruleset
     */
    private Class mRuleSetTransformerClass;

    /**
     * Constructeur
     * 
     * @param pRuleSetTransformerClass class pour transformer chaque ruleset
     */
    protected AbstractRuleSetListTransformer( Class pRuleSetTransformerClass )
    {
        mRuleSetTransformerClass = pRuleSetTransformerClass;
    }

    /**
     * @param pObject le tableau de CheckstyleDTO à transformer en formulaires.
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparaît.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        Collection listcheckstyleRuleSetsDTO = (Collection) pObject[0];
        AbstractRuleSetListForm checkStyleRuleSetListForm = (AbstractRuleSetListForm) pForm;
        ArrayList listRuleSetForm = new ArrayList();
        Iterator it = listcheckstyleRuleSetsDTO.iterator();
        while ( it.hasNext() )
        {
            listRuleSetForm.add( WTransformerFactory.objToForm( mRuleSetTransformerClass, it.next() ) );
        }
        checkStyleRuleSetListForm.setRuleSets( listRuleSetForm );
    }

    /**
     * @param pForm le formulaire à lire.
     * @param pObject le tableau de CheckstyleDTO qui récupère les données du formulaire.
     * @throws WTransformerException si un pb apparaît.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        Collection listObject = (Collection) pObject[0];
        ListIterator it = ( (AbstractRuleSetListForm) pForm ).getRuleSets().listIterator();
        while ( it.hasNext() )
        {
            AbstractRuleSetForm ruleSetForm = (AbstractRuleSetForm) it.next();
            if ( ruleSetForm.isSelected() )
            {
                listObject.add( WTransformerFactory.formToObj( mRuleSetTransformerClass, ruleSetForm )[0] );
            }
        }
    }

    /**
     * @param pForm le formulaire
     * @return le formulaire transformé
     * @throws WTransformerException si erreur
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        ArrayList list = new ArrayList();
        Object[] result = { list };
        formToObj( pForm, result );
        return result;
    }

}
