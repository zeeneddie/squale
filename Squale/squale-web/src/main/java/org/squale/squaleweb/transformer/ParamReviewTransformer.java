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

import org.squale.squaleweb.applicationlayer.formbean.results.ParamReviewForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transformation des paramètres d'historique
 */
public class ParamReviewTransformer
    implements WITransformer
{

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] arg0 )
        throws WTransformerException
    {
        ParamReviewForm form = new ParamReviewForm();
        objToForm( arg0, form );
        return form;
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      org.squale.welcom.struts.bean.WActionForm) {@inheritDoc}
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        ParamReviewForm form = (ParamReviewForm) pForm;
        form.setTre( (String) pObject[0] );
        form.setRuleId( (String) pObject[1] );
        form.setComponentId( (String) pObject[2] );
        form.setIsManualMark( (Boolean) pObject[3] );
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm)
     *      {@inheritDoc}
     */
    public Object[] formToObj( WActionForm arg0 )
        throws WTransformerException
    {
        final int size = 4;
        Object[] result = new Object[size];
        formToObj( arg0, result );
        return result;
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm,
     *      java.lang.Object[]) {@inheritDoc}
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        ParamReviewForm form = (ParamReviewForm) pForm;
        int index = 0;
        pObject[index++] = new Integer( form.getNbDays() );
        pObject[index++] = form.getTre();
        pObject[index++] = form.getRuleId();
        pObject[index++] = form.getComponentId();
        pObject[index++] = form.isManualMark();
    }

}
