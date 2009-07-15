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

import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;

import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squaleweb.applicationlayer.formbean.component.ProjectForm;
import org.squale.squaleweb.applicationlayer.formbean.component.ProjectListForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * Transforme les listes de projets
 * 
 * @author M400842
 */
public class ProjectListTransformer
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
        ProjectListForm form = new ProjectListForm();
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
        ArrayList listDTO = (ArrayList) pObject[0];
        int nbParams = pObject.length;
        ArrayList result = new ArrayList();
        ListIterator it = listDTO.listIterator();
        Object[] params = new Object[nbParams];
        if ( nbParams == 2 )
        { // On a aussi la liste des applications --> on veut que les projets
            // aient le nom de leur application.
            params[1] = (Collection) pObject[1];
        }
        while ( it.hasNext() )
        {
            params[0] = (ComponentDTO) it.next();
            result.add( WTransformerFactory.objToForm( ProjectTransformer.class, params ) );
        }
        ProjectListForm form = (ProjectListForm) pForm;
        form.setList( result );
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
        if ( null != ( (ProjectListForm) pForm ).getList() )
        {
            ListIterator it = ( (ProjectListForm) pForm ).getList().listIterator();
            while ( it.hasNext() )
            {
                listObject.add( WTransformerFactory.formToObj( ProjectTransformer.class, (ProjectForm) it.next() )[0] );
            }
        }
    }
}
