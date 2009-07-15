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
import java.util.Iterator;

import org.squale.squalecommon.datatransfertobject.config.ProjectProfileDTO;
import org.squale.squalecommon.datatransfertobject.config.SourceManagementDTO;
import org.squale.squaleweb.applicationlayer.formbean.config.ConfigPortailForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * Transforme ConfigPortailForm <-> ProjectProfileDTO et SourceManagementDTO
 */
public class ConfigPortailTransformer
    extends AbstractListTransformer
{

    /**
     * @param pObject l'objet SqualixConfigurationDTO à transformer en formulaire.
     * @throws WTransformerException si un pb apparaît.
     * @return le formulaire associé
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        ConfigPortailForm form = new ConfigPortailForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pObject l'objet SqualixConfigurationDTO à transformer en formulaire.
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparaît.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        Collection listProfilesDTO = (Collection) pObject[0];
        Collection listManagersDTO = (Collection) pObject[1];
        ConfigPortailForm configForm = (ConfigPortailForm) pForm;
        ArrayList listProfilesForm = new ArrayList();
        ArrayList listManagersForm = new ArrayList();
        Iterator itProfiles = listProfilesDTO.iterator();
        ProjectProfileDTO profileDTO = null;
        while ( itProfiles.hasNext() )
        {
            profileDTO = (ProjectProfileDTO) itProfiles.next();
            listProfilesForm.add( WTransformerFactory.objToForm( ProjectProfileTransformer.class, profileDTO ) );

        }
        configForm.setProfiles( listProfilesForm );
        Iterator itManagers = listManagersDTO.iterator();
        SourceManagementDTO managerDTO = null;
        while ( itManagers.hasNext() )
        {
            managerDTO = (SourceManagementDTO) itManagers.next();
            listManagersForm.add( WTransformerFactory.objToForm( SourceManagementTransformer.class, managerDTO ) );
        }
        configForm.setSourceManagements( listManagersForm );
    }

    /**
     * Méthode générée
     * 
     * @param pForm le formulaire à transformer.
     * @param pObject l'objet à remplir
     * @throws WTransformerException si un pb apparaît.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
    }
}
