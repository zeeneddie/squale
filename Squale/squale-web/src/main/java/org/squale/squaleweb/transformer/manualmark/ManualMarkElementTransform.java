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
package org.squale.squaleweb.transformer.manualmark;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.squalecommon.datatransfertobject.result.QualityResultCommentDTO;
import org.squale.squalecommon.datatransfertobject.result.QualityResultDTO;
import org.squale.squalecommon.datatransfertobject.rule.PracticeRuleDTO;
import org.squale.squalecommon.datatransfertobject.rule.QualityRuleDTO;
import org.squale.squaleweb.applicationlayer.formbean.manualmark.ManualMarkElementForm;
import org.squale.squaleweb.util.TimelimitationUtil;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transformer class Object <=> form for ManualMarkElement
 */
public class ManualMarkElementTransform
    implements WITransformer
{
    
    /** Logger */
    private static final Log LOGGER = LogFactory.getLog( ManualMarkElementTransform.class );

    /**
     * {@inheritDoc}
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        Object[] object = { new ManualMarkElementForm() };
        formToObj( form, object );
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        ManualMarkElementForm currentForm = (ManualMarkElementForm) form;
        QualityResultDTO dto = new QualityResultDTO();
        //création du commentaire
        QualityResultCommentDTO commentDto = new QualityResultCommentDTO();
        commentDto.setComments( currentForm.getComments() );
        dto.setManualMarkComment( commentDto );
        dto.setId( currentForm.getResultId() );
        dto.setMeanMark( Float.parseFloat( currentForm.getValue() ) );
        dto.setCreationDate( currentForm.getCreationDate() );
        QualityRuleDTO rule = new QualityRuleDTO();
        rule.setId( currentForm.getRuleId() );
        dto.setRule( rule );
        object[0] = dto;
    }

    /**
     * {@inheritDoc}
     */
    public WActionForm objToForm( Object[] object )
        throws WTransformerException
    {
        ManualMarkElementForm form = new ManualMarkElementForm();
        objToForm( object, form );
        return form;
    }

    /**
     * {@inheritDoc}
     */
    public void objToForm( Object[] object, WActionForm form )
        throws WTransformerException
    {
        Locale local = (Locale) object[1];
        QualityResultDTO dto = (QualityResultDTO) object[0];
        ManualMarkElementForm currentForm = (ManualMarkElementForm) form;
        PracticeRuleDTO ruleDTO = (PracticeRuleDTO) dto.getRule();
        currentForm.setName( local, ruleDTO.getName() );
        currentForm.setValue( String.valueOf( dto.getMeanMark() ) );
        currentForm.setCreationDate( dto.getCreationDate() );
        currentForm.setTimelimitation( ruleDTO.getTimeLimitation() );
        // set the comments
        if ( dto.getManualMarkComment()!= null )
        {
            currentForm.setComments( dto.getManualMarkComment().getComments() );
        }
        else 
        {
            currentForm.setComments( "" );
        }
        String limitation = TimelimitationUtil.parseStringWithUnitTranslate( ruleDTO.getTimeLimitation(), local );
        currentForm.setTimeLimitationParse( limitation );
        if ( dto.getCreationDate() != null )
        {
            currentForm.setTimeLeft( TimelimitationUtil.timeleft( ruleDTO.getTimeLimitation(), dto.getCreationDate(),
                                                                  local ) );
        }
        currentForm.setRuleId( ruleDTO.getId() );
    }

}
