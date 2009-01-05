/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
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
