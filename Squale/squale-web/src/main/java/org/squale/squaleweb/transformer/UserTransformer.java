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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ProfileDTO;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.UserForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformateur UserDTO <-> UserForm
 * 
 * @author M400842
 */
public class UserTransformer
    implements WITransformer
{

    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        UserForm form = new UserForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        UserDTO dto = (UserDTO) pObject[0];
        UserForm form = (UserForm) pForm;

        form.setId( dto.getID() );
        form.setMatricule( dto.getMatricule() );
        if ( dto.getEmail() != null )
        {
            form.setEmail( dto.getEmail() );
        }
        form.setUnsubscribed( dto.isUnsubscribed() );
        form.setName( dto.getFullName() );
        form.setDefaultAccess( dto.getDefaultProfile() );
        // Transformation des collections
        // Liste des applications
        ArrayList applications = new ArrayList();
        applications.addAll( dto.getProfiles().keySet() );
        ApplicationListForm applicationListForm =
            (ApplicationListForm) WTransformerFactory.objToForm( ApplicationListTransformer.class, applications );
        form.setApplicationsList( applicationListForm.getList() );
        // Liste des applications publiques
        ApplicationListForm publicListForm =
            (ApplicationListForm) WTransformerFactory.objToForm( ApplicationListTransformer.class,
                                                                 dto.getPublicApplications() );
        form.setPublicApplicationsList( publicListForm.getList() );
        // On va séparer les applications validées de celles en cours de création
        // Liste des profils
        ApplicationListForm inCreationListForm = new ApplicationListForm();
        Map profiles = dto.getProfiles();
        Map newProfiles = new HashMap();
        if ( null != profiles )
        {
            Iterator it = profiles.keySet().iterator();
            ComponentDTO applicationDTO = null;
            ApplicationForm applicationForm = null;
            List allApplications = new ArrayList( applicationListForm.getList() );
            allApplications.addAll( publicListForm.getList() );
            while ( it.hasNext() )
            {
                applicationDTO = (ComponentDTO) it.next();
                applicationForm = getForm( applicationDTO.getID(), allApplications );
                newProfiles.put( applicationForm, WTransformerFactory.objToForm( ProfileTransformer.class,
                                                                                 profiles.get( applicationDTO ) ) );
                if ( dto.getApplicationsInCreation().contains( applicationDTO ) )
                {
                    // les applications non validées sont écartées de la liste pour les lecteurs uniquement
                    inCreationListForm.getList().add( applicationForm );
                    ProfileDTO lProfileDTO = ( (ProfileDTO) profiles.get( applicationDTO ) );
                    if ( !lProfileDTO.getName().equals( ProfileBO.MANAGER_PROFILE_NAME ) )
                    {
                        form.getApplicationsList().remove( applicationForm );
                    }
                }
            }
        }
        form.setProfiles( newProfiles );
        form.setOnlyAdminApplicationsList( inCreationListForm.getList() );
    }

    /**
     * Retourne l'instance de ApplicationForm avec l'id correspondant.
     * 
     * @param pId l'id recherché.
     * @param pApplicationList la liste dans laquelle l'application est recherchée.
     * @return l'instance de ApplicationForm ou null si non trouvé.
     */
    private ApplicationForm getForm( long pId, List pApplicationList )
    {
        ApplicationForm application = null;
        ListIterator it = pApplicationList.listIterator();
        while ( it.hasNext() && null == application )
        {
            application = (ApplicationForm) it.next();
            if ( pId != application.getId() )
            {
                application = null;
            }
        }
        return application;
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new UserDTO() };
        formToObj( pForm, obj );
        return obj;
    }

    /**
     * @param pObject l'objet à remplir
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        UserForm form = (UserForm) pForm;
        UserDTO dto = (UserDTO) pObject[0];

        dto.setID( form.getId() );
        dto.setMatricule( form.getMatricule() );
        dto.setEmail( form.getEmail() );
        dto.setUnsubscribed( form.getUnsubscribed() );
        dto.setFullName( form.getName() );
    }

}
