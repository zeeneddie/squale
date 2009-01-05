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
package com.airfrance.squaleweb.transformer.component.parameters;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.ListParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.GeneralParametersForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation des paramètres généraux
 */
public class GeneralParametersTransformer
    implements WITransformer
{

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        GeneralParametersForm generalForm = new GeneralParametersForm();
        objToForm( pObject, generalForm );
        return generalForm;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      com.airfrance.welcom.struts.bean.WActionForm) {@inheritDoc}
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        String profile = (String) pObject[1];
        GeneralParametersForm generalForm = (GeneralParametersForm) pForm;
        // On remplit le form
        // sources
        ListParameterDTO sourcesDTO = (ListParameterDTO) params.getParameters().get( ParametersConstants.SOURCES );
        if ( sourcesDTO != null )
        {
            generalForm.setSources( convertList( sourcesDTO ) );
        }
        // excludedPatterns
        ListParameterDTO excludedList =
            (ListParameterDTO) params.getParameters().get( ParametersConstants.EXCLUDED_PATTERNS );
        if ( excludedList != null )
        {
            generalForm.setExcludePatterns( getPatternsTab( excludedList ) );
        }
        // includedPatterns
        ListParameterDTO includedList =
            (ListParameterDTO) params.getParameters().get( ParametersConstants.INCLUDED_PATTERNS );
        if ( includedList != null )
        {
            generalForm.setIncludePatterns( getPatternsTab( includedList ) );
        }
        // FIXME (line from AF code base) this test is ugly: should find another way to decide wether to set this parameter...
        if ( profile.matches( ".*(j2ee|jee|web).*" ) )
        {
            generalForm.setJspSourcesRequired( true );
            // sources JSP
            ListParameterDTO jspsDTO = (ListParameterDTO) params.getParameters().get( ParametersConstants.JSP );
            if ( jspsDTO != null )
            {
                generalForm.setJspSources( convertList( jspsDTO ) );
            }
        }
    }

    /**
     * Conversion d'une liste de sources
     * 
     * @param pSourcesList liste de sources
     * @return liste des sources
     */
    private String[] convertList( ListParameterDTO pSourcesList )
    {
        List sourcesParam = pSourcesList.getParameters();
        String[] sources = new String[sourcesParam.size()];
        for ( int i = 0; i < sourcesParam.size(); i++ )
        {
            StringParameterDTO source = (StringParameterDTO) sourcesParam.get( i );
            sources[i] = source.getValue().trim();

        }
        return sources;
    }

    /**
     * @param pPatterns la liste des patterns
     * @return les patterns sous forme de tableau de String
     */
    private String[] getPatternsTab( ListParameterDTO pPatterns )
    {
        List params = pPatterns.getParameters();
        String[] patterns = new String[params.size()];
        // Conversion des patterns
        for ( int i = 0; i < params.size(); i++ )
        {
            StringParameterDTO currClass = (StringParameterDTO) params.get( i );
            patterns[i] = currClass.getValue();
        }
        return patterns;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     *      {@inheritDoc}
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new MapParameterDTO() };
        formToObj( pForm, obj );
        return obj;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm,
     *      java.lang.Object[]) {@inheritDoc}
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        GeneralParametersForm generalForm = (GeneralParametersForm) pForm;
        // Insertion des paramètres dans la map
        // sources:
        ListParameterDTO sources = new ListParameterDTO();
        fillSourcesList( sources, generalForm.getSources() );
        params.getParameters().put( ParametersConstants.SOURCES, sources );
        // excludedPatterns si non vide:
        setPatternsList( ParametersConstants.EXCLUDED_PATTERNS, params, generalForm.getExcludePatterns() );
        // includedPatterns si non vide:
        setPatternsList( ParametersConstants.INCLUDED_PATTERNS, params, generalForm.getIncludePatterns() );
        // sources JSP
        if ( generalForm.isJspSourcesRequired() )
        {
            ListParameterDTO jsps = new ListParameterDTO();
            fillSourcesList( jsps, generalForm.getJspSources() );
            params.getParameters().put( ParametersConstants.JSP, jsps );
        }
    }

    /**
     * Remplissage d'une liste de sources
     * 
     * @param pSourcesList liste à remplir
     * @param pSourcesTab source à y insérer
     */
    private void fillSourcesList( ListParameterDTO pSourcesList, String[] pSourcesTab )
    {
        ArrayList sourcesList = new ArrayList();
        for ( int i = 0; i < pSourcesTab.length; i++ )
        {
            StringParameterDTO strParamSource = new StringParameterDTO();
            strParamSource.setValue( pSourcesTab[i] );
            sourcesList.add( strParamSource );
        }
        pSourcesList.setParameters( sourcesList );
    }

    /**
     * @param pConstant la constante du paramètre à modifier
     * @param pParams les paramètres
     * @param pPatternsTab les patterns sous forme d'un tableau de String
     */
    private void setPatternsList( String pConstant, MapParameterDTO pParams, String[] pPatternsTab )
    {
        if ( pPatternsTab.length == 0 )
        {
            // On supprime ce paramètre
            pParams.getParameters().remove( pConstant );
        }
        else
        {
            ListParameterDTO patterns = new ListParameterDTO();
            ArrayList paramsList = new ArrayList();
            for ( int i = 0; i < pPatternsTab.length; i++ )
            {
                StringParameterDTO strParam = new StringParameterDTO();
                strParam.setValue( pPatternsTab[i] );
                paramsList.add( strParam );
            }
            patterns.setParameters( paramsList );
            pParams.getParameters().put( pConstant, patterns );
        }
    }
}
