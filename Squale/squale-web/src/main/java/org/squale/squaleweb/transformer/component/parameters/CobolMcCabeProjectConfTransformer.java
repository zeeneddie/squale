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
package org.squale.squaleweb.transformer.component.parameters;

import org.squale.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import org.squale.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squaleweb.applicationlayer.formbean.component.parameters.CobolMcCabeForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transformation des paramètres McCabe pour le Cobol.
 */
public class CobolMcCabeProjectConfTransformer
    implements WITransformer
{

    /**
     * {@inheritDoc}
     * 
     * @param arg0 {@inheritDoc}
     * @return le formulaire transformé
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     */
    public WActionForm objToForm( Object[] arg0 )
        throws WTransformerException
    {
        CobolMcCabeForm mcCabeForm = new CobolMcCabeForm();
        objToForm( arg0, mcCabeForm );
        return mcCabeForm;
    }

    /**
     * {@inheritDoc}
     * 
     * @param arg0 {@inheritDoc}
     * @return le tableau d'objets
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm)
     */
    public Object[] formToObj( WActionForm arg0 )
        throws WTransformerException
    {
        // Méthode non utilisée
        throw new WTransformerException( "not yet implemented" );
    }

    /**
     * {@inheritDoc}
     * 
     * @param pObject {@inheritDoc}
     * @param pForm {@inheritDoc}
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      org.squale.welcom.struts.bean.WActionForm)
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        // Récupération des paramètres
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        CobolMcCabeForm mcCabeForm = (CobolMcCabeForm) pForm;
        StringParameterDTO dialectParam = (StringParameterDTO) params.getParameters().get( ParametersConstants.DIALECT );
        if ( dialectParam != null )
        {
            // On remplit le form
            mcCabeForm.setDialect( dialectParam.getValue() );
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @param pForm {@inheritDoc}
     * @param pObject {@inheritDoc}
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm,
     *      java.lang.Object[])
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        // Récupération des paramètres
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        CobolMcCabeForm mcCabeForm = (CobolMcCabeForm) pForm;
        // Le dialect
        StringParameterDTO dialect = new StringParameterDTO();
        dialect.setValue( mcCabeForm.getDialect() );
        params.getParameters().put( ParametersConstants.DIALECT, dialect );

    }

}
