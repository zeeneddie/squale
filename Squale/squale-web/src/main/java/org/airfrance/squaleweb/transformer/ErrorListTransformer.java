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
package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import com.airfrance.squalecommon.datatransfertobject.result.ErrorDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ErrorForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ErrorListForm;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformArrayList;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transforme des listes d'erreurs Form <-> DTO
 * 
 * @author M400842
 */
public class ErrorListTransformer
    extends AbstractListTransformer
{
    /**
     * @param pObject le tableau de ProjectDTO à transformer en formulaires.
     * @throws WTransformerException si un pb apparaît.
     * @return le formulaire associé
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        ErrorListForm form = new ErrorListForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pObject le tableau de ProjectDTO à transformer en formulaires.
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparaît.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        Collection dto = (Collection) pObject[1];
        HashMap map = new HashMap();
        ErrorListForm form = (ErrorListForm) pForm;
        if ( null != dto )
        {
            Iterator it = dto.iterator();
            while ( it.hasNext() )
            {
                ErrorForm error = null;
                if (pObject.length == 4){
                    Object[] objects = new Object[]{ (ErrorDTO) it.next(), (Locale) pObject[3] };
                    error = (ErrorForm) WTransformerFactory.objToForm( ErrorTransformer.class, objects );
                } else {
                    error = (ErrorForm) WTransformerFactory.objToForm( ErrorTransformer.class, (ErrorDTO) it.next() );
                }
                // On insère dans la map avec le message en clé
                if ( map.get( error.getMessage() ) != null )
                {
                    error = (ErrorForm) map.get( error.getMessage() );
                    error.setNbOcc( error.getNbOcc() + 1 );
                }
                map.put( error.getMessage(), error );
            }
        }
        form.setList( new ArrayList( map.values() ) );
        if (pObject.length == 4){
            form.setTaskName( WebMessages.getString( (Locale) pObject[3], (String) pObject[0] + ".name" ) );
        } else {
            form.setTaskName( WebMessages.getString( (String) pObject[0] + ".name" ) );
        }
        form.setMaxErrorLevel( (String) pObject[2] );
    }

    /**
     * @param pForm le formulaire à lire.
     * @param pObject le tableau de ProjectDTO qui récupère les données du formulaire.
     * @throws WTransformerException si un pb apparaît.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        ArrayList listObject = (ArrayList) pObject[0];
        ArrayList listForm = (ArrayList) ( (ErrorListForm) pForm ).getList();
        WITransformer auditTransformer = WTransformerFactory.getSingleTransformer( AuditTransformer.class );
        WTransformArrayList.formToObj( listForm, listObject, auditTransformer );
    }
}
