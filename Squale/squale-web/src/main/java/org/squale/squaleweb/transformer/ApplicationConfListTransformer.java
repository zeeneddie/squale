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
import java.util.Iterator;

import org.squale.squaleweb.applicationlayer.formbean.component.ApplicationListForm;
import org.squale.squaleweb.applicationlayer.formbean.creation.CreateApplicationForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformArrayList;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformer des listes d'applications.
 * 
 * @author M400842
 */
public class ApplicationConfListTransformer
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
        ApplicationListForm form = new ApplicationListForm();
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
        ApplicationListForm listForm = (ApplicationListForm) pForm;
        WITransformer transformer = WTransformerFactory.getSingleTransformer( ApplicationConfTransformer.class );
        ArrayList list = WTransformArrayList.objToForm( listDTO, transformer );
        listForm.setList( list );
    }

    /**
     * @param pForm le formulaire à lire.
     * @param pObject le tableau de ProjectDTO qui récupère les données du formulaire.
     * @throws WTransformerException si un pb apparaît.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        // Extraction des paramètres
        ArrayList listObject = (ArrayList) pObject[0];
        boolean selectedOnly = pObject.length > 1;
        if ( null != ( (ApplicationListForm) pForm ).getList() )
        {
            Iterator it = ( (ApplicationListForm) pForm ).getList().iterator();
            // On récupère soit toutes les applications soit
            // seulement celles qui sont sélectionénes en fonction du contexte
            while ( it.hasNext() )
            {
                CreateApplicationForm form = (CreateApplicationForm) it.next();
                if ( ( !selectedOnly ) || ( form.isSelected() ) )
                {
                    listObject.add( WTransformerFactory.formToObj( ApplicationConfTransformer.class, form )[0] );
                }
            }
        }
    }

}
