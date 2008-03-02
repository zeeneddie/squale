package com.airfrance.squaleweb.transformer;

import com.airfrance.squalecommon.datatransfertobject.rule.FactorRuleDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectFactorForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation d'un facteur de projet
 */
public class ProjectFactorTransformer
    implements WITransformer
{

    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        ProjectFactorForm form = new ProjectFactorForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        ProjectFactorForm form = (ProjectFactorForm) pForm;
        // Placement des attributs
        FactorRuleDTO factor = (FactorRuleDTO) pObject[0];
        form.setId( factor.getId() );
        form.setName( factor.getName() );
        // Vérification de la présence d'une note
        if ( pObject.length > 1 )
        {
            form.setCurrentMark( (String) pObject[1] );
            // Vérification de la présence d'une tendance
            if ( pObject.length > 2 )
            {
                form.setPredecessorMark( (String) pObject[2] );
            }
        }
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        return null;
    }

    /**
     * @param pObject l'objet à remplir
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
    }
}
