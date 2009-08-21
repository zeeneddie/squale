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
package org.squale.squaleweb.transformer;

import org.squale.squalecommon.datatransfertobject.result.QualityResultDTO;
import org.squale.squaleweb.applicationlayer.formbean.results.ParamReviewCommentsForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transformer class Object <=> form for ParamReviewComments
 */
public class ParamReviewCommentsTransform
    implements WITransformer
{
    
    /**
     * {@inheritDoc}
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        // TODO Auto-generated method stub
        
    }

    /**
     * {@inheritDoc}
     */
    public WActionForm objToForm( Object[] object )
        throws WTransformerException
    {
        ParamReviewCommentsForm form = new ParamReviewCommentsForm();
        objToForm( object, form );
        return form;
    }

    /**
     * {@inheritDoc}
     */
    public void objToForm( Object[] object, WActionForm form )
        throws WTransformerException
    {
        QualityResultDTO dto = (QualityResultDTO) object[0];
        ParamReviewCommentsForm currentForm = (ParamReviewCommentsForm) form;
        currentForm.setValue( String.valueOf( dto.getMeanMark() ) );
        currentForm.setManualMarkDate( dto.getCreationDate() );
        //on insère le commentaire si celui-ci existe
        if ( dto.getManualMarkComment() != null )
        {
            currentForm.setComments( dto.getManualMarkComment().getComments() );
        }
        else
        {
            currentForm.setComments( "" );
        }
    }

}
