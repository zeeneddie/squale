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
package com.airfrance.squaleweb.applicationlayer.formbean.component.parameters;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;

/**
 * Bean pour les librairies utilisateur eclipse
 */
public class EclipseUserLibForm
    extends RootForm
{

    /** Le nom */
    private String mName = "";

    /** Les librairies associées */
    private String[] mLibs = new String[0];

    /**
     * Constructeur par défaut
     */
    public EclipseUserLibForm()
    {
        this( "", new String[0] );
    }

    /**
     * @param name le nom
     * @param libs les librairies
     */
    public EclipseUserLibForm( String name, String[] libs )
    {
        mName = name;
        mLibs = libs;
    }

    /**
     * @return les librairies associées
     */
    public String[] getLibs()
    {
        return mLibs;
    }

    /**
     * @return les librairies associées sous forme de chaine
     */
    public String getLibsStr()
    {
        String result = "";
        for ( int i = 0; i < getLibs().length; i++ )
        {
            result += mLibs[i] + ";";
        }
        return result;
    }

    /**
     * @return le nom
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @param pLibs les librairies associées
     */
    public void setLibs( String[] pLibs )
    {
        mLibs = pLibs;
    }

    /**
     * @param pName le nom
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * {@inheritdoc}
     * 
     * @see com.airfrance.welcom.struts.bean.WActionForm#wValidate(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    public void wValidate( ActionMapping mapping, HttpServletRequest request )
    {
        if ( getName().length() == 0 )
        {
            addError( "name", new ActionError( "error.field.required" ) );
        }
        setLibs( SqualeWebActionUtils.cleanValues( getLibs() ) );
        if ( getLibs().length == 0 )
        {
            addError( "libs", new ActionError( "error.field.required" ) );
        }
        // On ajoute le nom de la tâche à la requête pour indiquer au dropPanel
        // qu'il faut qu'il soit ouvert
        request.setAttribute( "tool", "JCompilingTask" );
    }

    /**
     * {@inheritdoc}
     * 
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.ServletRequest)
     */
    public void reset( ActionMapping arg0, ServletRequest arg1 )
    {
        setName( "" );
        setLibs( new String[0] );
    }

}
