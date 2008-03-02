package com.airfrance.squaleweb.transformer.rulechecking;

import com.airfrance.squalecommon.datatransfertobject.rulechecking.RuleSetDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.rulechecking.AbstractRuleSetForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation de formbean sur un ruleset
 */
public abstract class AbstractRuleSetTransformer
    implements WITransformer
{

    /**
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        RuleSetDTO rulesetDTO = (RuleSetDTO) pObject[0];
        AbstractRuleSetForm form = (AbstractRuleSetForm) pForm;
        form.setId( rulesetDTO.getId() );
        form.setName( rulesetDTO.getName() );
        form.setDateOfUpdate( rulesetDTO.getDateOfUpdate() );
    }

    /**
     * @param pObject l'objet à remplir
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        AbstractRuleSetForm form = (AbstractRuleSetForm) pForm;
        RuleSetDTO dto = (RuleSetDTO) pObject[0];
        dto.setId( form.getId() );
        // Le nom et la date de mise à jour sont non modifiables mais on le place pour l'affichage
        dto.setDateOfUpdate( form.getDateOfUpdate() );
        dto.setName( form.getName() );
    }
}
