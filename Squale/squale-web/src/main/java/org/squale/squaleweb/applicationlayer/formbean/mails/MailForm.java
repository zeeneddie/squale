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
package org.squale.squaleweb.applicationlayer.formbean.mails;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import org.squale.squaleweb.applicationlayer.formbean.RootForm;
import org.squale.squaleweb.applicationlayer.formbean.component.ApplicationListForm;

/**
 * 
 */
public class MailForm
    extends RootForm
{

    /** Constante pour indiquer qu'on sélectionne toutes les applications */
    public static final String ALL_APPLICATIONS = "Toutes les applications";

    /** la liste des appli concernée pour le choix */
    private ApplicationListForm mApplicationFormsList = new ApplicationListForm();

    /** l'application qui a été choisie */
    private String mAppliName = "";

    /** l'objet du mail */
    private String mObject = "";

    /** le contenu du mail */
    private String mContent = "";

    /**
     * @return le contenu du mail
     */
    public String getContent()
    {
        return mContent;
    }

    /**
     * @return l'objet du mail
     */
    public String getObject()
    {
        return mObject;
    }

    /**
     * @param pContent le nouveau contenu du mail
     */
    public void setContent( String pContent )
    {
        mContent = pContent;
    }

    /**
     * @param pObject le nouvel objet du mail
     */
    public void setObject( String pObject )
    {
        mObject = pObject;
    }

    /**
     * @return la liste des applications
     */
    public ApplicationListForm getApplicationFormsList()
    {
        return mApplicationFormsList;
    }

    /**
     * @param collection la nouvelle collection d'appli
     */
    public void setApplicationFormsList( ApplicationListForm collection )
    {
        mApplicationFormsList = collection;
    }

    /**
     * @return l'application sélectionnée
     */
    public String getAppliName()
    {
        return mAppliName;
    }

    /**
     * @param pAppliName la nouvelle appli sélectionnée
     */
    public void setAppliName( String pAppliName )
    {
        mAppliName = pAppliName;
    }

    /**
     * @see org.squale.welcom.struts.bean.WActionForm#wValidate(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void wValidate( ActionMapping mapping, HttpServletRequest request )
    {
        super.wValidate( mapping, request );
        // on vérifie que si on arrive depuis le menu, on ne doit pas vérifier que
        // les champs sont bien remplis car il y a une étape
        if ( request.getParameter( "fromMenu" ) == null )
        {
            // L'objet du mail
            setObject( getObject().trim() );
            if ( getObject().length() == 0 )
            {
                addError( "object", new ActionError( "error.field.required" ) );
            }
            // Le message
            setContent( getContent().trim() );
            if ( getContent().length() == 0 )
            {
                addError( "content", new ActionError( "error.field.required" ) );
            }
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    public void reset( ActionMapping mapping, HttpServletRequest request )
    {
        setContent( "" );
        setObject( "" );
        setAppliName( "" );
    }

}
