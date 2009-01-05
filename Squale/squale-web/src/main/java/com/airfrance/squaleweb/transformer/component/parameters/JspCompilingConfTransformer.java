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
import java.util.Iterator;
import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.ListParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.JspCompilingForm;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformeur pour les paramétres de compilation JSP
 */
public class JspCompilingConfTransformer
    implements WITransformer
{

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        JspCompilingForm jspForm = new JspCompilingForm();
        objToForm( pObject, jspForm );
        return jspForm;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      com.airfrance.welcom.struts.bean.WActionForm) {@inheritDoc}
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        MapParameterDTO params = (MapParameterDTO) pObject[0];
        JspCompilingForm jspCompilingForm = (JspCompilingForm) pForm;
        // On remplit le form
        // Le chemin vers le répertoire d'application Web
        StringParameterDTO webApp = (StringParameterDTO) params.getParameters().get( ParametersConstants.WEB_APP );
        if ( webApp != null )
        {
            jspCompilingForm.setWebAppPath( webApp.getValue() );
        }
        // La version des servlets
        StringParameterDTO j2eeVers =
            (StringParameterDTO) params.getParameters().get( ParametersConstants.J2EE_VERSION );
        if ( j2eeVers != null )
        {
            jspCompilingForm.setJ2eeVersion( j2eeVers.getValue() );
        }
        // excludedDirs
        jspCompilingForm.setExcludeJspDir( new String[0] );
        ListParameterDTO dirsList =
            (ListParameterDTO) params.getParameters().get( ParametersConstants.JSP_EXCLUDED_DIRS );
        if ( dirsList != null )
        {
            List dirs = dirsList.getParameters();
            Iterator it = dirs.iterator();
            String[] excludedDirs = new String[dirs.size()];
            int index = 0;
            // On parcours la liste des répertoires exclus afin de remplir
            // le tableau concerné du form
            while ( it.hasNext() )
            {
                StringParameterDTO dir = (StringParameterDTO) it.next();
                excludedDirs[index] = dir.getValue();
                index++;
            }
            jspCompilingForm.setExcludeJspDir( excludedDirs );
        }
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
        MapParameterDTO mapParameter = (MapParameterDTO) pObject[0];
        JspCompilingForm jspCompilingForm = (JspCompilingForm) pForm;
        // Insertion des paramètres dans la map
        // chemin vers le répertoire d'application web
        StringParameterDTO webApp = new StringParameterDTO();
        webApp.setValue( jspCompilingForm.getWebAppPath() );
        mapParameter.getParameters().put( ParametersConstants.WEB_APP, webApp );
        // dialect
        StringParameterDTO dialect = new StringParameterDTO();
        dialect.setValue( jspCompilingForm.getJ2eeVersion() );
        mapParameter.getParameters().put( ParametersConstants.J2EE_VERSION, dialect );
        // excludedDirs
        // Nettoyage des noms des répertoires à exclure
        String[] excludedDirsTab = SqualeWebActionUtils.cleanValues( jspCompilingForm.getExcludeJspDir() );
        ListParameterDTO excludedDirsList = new ListParameterDTO();
        ArrayList paramsList = new ArrayList();
        // On remplit la liste avec les données du tableau
        for ( int i = 0; i < excludedDirsTab.length; i++ )
        {
            StringParameterDTO strParam = new StringParameterDTO();
            strParam.setValue( excludedDirsTab[i] );
            paramsList.add( strParam );
        }
        excludedDirsList.setParameters( paramsList );
        mapParameter.getParameters().put( ParametersConstants.JSP_EXCLUDED_DIRS, excludedDirsList );
        // si il n'y a plus de répertoires exclus, on supprime le paramètre correspondant
        if ( excludedDirsTab.length == 0 )
        {
            mapParameter.getParameters().remove( ParametersConstants.JSP_EXCLUDED_DIRS );
        }
    }
}
