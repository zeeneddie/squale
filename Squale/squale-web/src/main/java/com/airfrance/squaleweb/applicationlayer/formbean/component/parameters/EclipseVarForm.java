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

/**
 * Bean pour les variables eclipse
 */
public class EclipseVarForm
    extends RootForm
{

    /** Le nom de la variable */
    private String mName;

    /** Le chemin vers la librairies */
    private String mLib;

    /**
     * @return la librarie associée à la variable (.jar ou .zip)
     */
    public String getLib()
    {
        return mLib;
    }

    /**
     * Constructeur par défaut
     */
    public EclipseVarForm()
    {
        this( "", "" );
    }

    /**
     * @param name le nom
     * @param lib la librairie
     */
    public EclipseVarForm( String name, String lib )
    {
        mName = name;
        mLib = lib;
    }

    /**
     * @return le nom de la variable
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @param pLib le chemin vers la librairie
     */
    public void setLib( String pLib )
    {
        mLib = pLib.trim();
    }

    /**
     * @param pName le nom de la variable
     */
    public void setName( String pName )
    {
        mName = pName.trim();
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
        if ( getLib().length() == 0 )
        {
            addError( "lib", new ActionError( "error.field.required" ) );
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
        setLib( "" );
    }

}
