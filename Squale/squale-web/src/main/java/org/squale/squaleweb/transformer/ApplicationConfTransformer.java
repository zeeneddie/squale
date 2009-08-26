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
import java.util.HashMap;
import java.util.Iterator;

import org.squale.squalecommon.datatransfertobject.component.ApplicationConfDTO;
import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.component.ProjectConfDTO;
import org.squale.squalecommon.datatransfertobject.config.ServeurDTO;
import org.squale.squaleweb.applicationlayer.formbean.access.AccessListForm;
import org.squale.squaleweb.applicationlayer.formbean.component.AuditForm;
import org.squale.squaleweb.applicationlayer.formbean.config.ServeurForm;
import org.squale.squaleweb.applicationlayer.formbean.creation.CreateApplicationForm;
import org.squale.squaleweb.applicationlayer.formbean.creation.CreateProjectForm;
import org.squale.squaleweb.transformer.access.AccessListTransformer;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformation de la configuration d'une application
 */
public class ApplicationConfTransformer
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
        CreateApplicationForm form = new CreateApplicationForm();
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
        ApplicationConfDTO dto = (ApplicationConfDTO) pObject[0];
        CreateApplicationForm form = (CreateApplicationForm) pForm;
        form.setApplicationId( "" + dto.getId() );
        form.setApplicationName( dto.getName() );
        form.setStatus( dto.getStatus() );
        form.setLastUpdate( dto.getLastUpdate() );
        form.setLastUser( dto.getLastUser() );
        // Le dto ne contient que la fréquence d'audit
        // alors que le form contient un booléen et la fréquence
        if ( dto.getAuditFrequency() > 0 )
        {
            form.setAuditFrequency( dto.getAuditFrequency() );
            form.setMilestone( false );
        }
        else
        {
            form.setMilestone( true );
        }
        form.setPurgeDelay( dto.getResultsStorageOptions() );
        if ( dto.getServeurDTO() != null )
        {
            form.setServeurForm( (ServeurForm) WTransformerFactory.objToForm( ServeurTransformer.class,
                                                                              dto.getServeurDTO() ) );
        }
        if ( dto.getAccesses() != null )
        {
            form.setAccessListForm( (AccessListForm) WTransformerFactory.objToForm( AccessListTransformer.class,
                                                                                    dto.getAccesses() ) );
        }
        form.setPublic( dto.getPublic() );
        HashMap hm = new HashMap();
        form.setIsInProduction( dto.getInProduction() );
        form.setExternalDev( dto.getExternalDev() );
        form.setInInitialDev( dto.getInInitialDev() );
        form.setQualityApproachOnStart( dto.getQualityApproachOnStart() );
        if (dto.getInInitialDev())
        {
            form.setGlobalCostInitial( dto.getGlobalCost() );
            form.setDevCost( dto.getDevCost() );
        }
        else
        {
            form.setGlobalCostMaintenance( dto.getGlobalCost() );
            form.setDevCost( 0 );
        }
        

        // Prise en compte des utilisateurs déclarés sur l'application
        if ( null != dto.getUsers() )
        {
            hm.putAll( dto.getUsers() );
        }
        form.setRights( hm );
        // Transformation des projets
        ArrayList projects = new ArrayList();
        if ( dto.getProjectConfList() != null )
        {
            Iterator it = dto.getProjectConfList().iterator();
            ProjectConfDTO project = null;
            Object[] paramIn = { project };
            // Traitement de chaque projet
            while ( it.hasNext() )
            {
                project = (ProjectConfDTO) it.next();
                paramIn[0] = project;
                // Conversion du projet
                CreateProjectForm projectForm =
                    (CreateProjectForm) WTransformerFactory.objToForm( ProjectConfTransformer.class, paramIn );
                // on remplit les informations concernant l'application parent
                projectForm.setApplicationId( form.getApplicationId() );
                projectForm.setApplicationName( form.getApplicationName() );
                // ajoute à la liste
                projects.add( projectForm );
            }
        }
        form.setProjects( projects );
        if ( pObject.length == 2 && null != pObject[1] )
        {
            // On affecte l'audit de jalon
            AuditDTO audit = (AuditDTO) pObject[1];
            AuditForm auditForm =
                (AuditForm) WTransformerFactory.objToForm( AuditTransformer.class, new Object[] { audit } );
            form.setMilestoneAudit( auditForm );
        }
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new ApplicationConfDTO() };
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
        CreateApplicationForm form = (CreateApplicationForm) pForm;
        ApplicationConfDTO dto = (ApplicationConfDTO) pObject[0];
        dto.setId( new Long( form.getApplicationId() ).longValue() );
        dto.setName( form.getApplicationName() );
        dto.setResultsStorageOptions( form.getPurgeDelay() );
        if ( form.getServeurForm() != null )
        {
            Object[] lServeurDTOs = WTransformerFactory.formToObj( ServeurTransformer.class, form.getServeurForm() );
            dto.setServeurDTO( (ServeurDTO) lServeurDTOs[0] );
        }

        dto.setStatus( form.getStatus() );
        if ( form.isMilestone() )
        {
            dto.setAuditFrequency( -1 );
        }
        else
        {
            dto.setAuditFrequency( form.getAuditFrequency() );
        }
        dto.setPublic( form.isPublic() );
        dto.setInProduction( form.getIsInProduction() );
        dto.setExternalDev( form.getExternalDev() );
        dto.setInInitialDev( form.getInInitialDev() );
        dto.setQualityApproachOnStart( form.getQualityApproachOnStart() );
        if (form.getInInitialDev())
        {
            dto.setGlobalCost( form.getGlobalCostInitial() );
            dto.setDevCost( form.getDevCost() );
        }
        else
        {
            dto.setGlobalCost( form.getGlobalCostMaintenance() );
            dto.setDevCost( 0 );
        }
        
        
        HashMap hm = new HashMap();
        if ( null != form.getRights() )
        {
            hm.putAll( form.getRights() );
        }
        dto.setUsers( hm );
    }

}
