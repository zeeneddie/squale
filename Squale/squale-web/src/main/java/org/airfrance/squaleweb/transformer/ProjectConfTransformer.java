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

import com.airfrance.squalecommon.datatransfertobject.component.ProjectConfDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.config.ProjectProfileDTO;
import com.airfrance.squalecommon.datatransfertobject.config.SourceManagementDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateProjectForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformer pour le bean CreateProjectForm
 */
public class ProjectConfTransformer
    implements WITransformer
{

    /**
     * @param pObject le tableau de ProjectDTO à transformer en formulaires.
     * @throws WTransformerException si un pb apparaît.
     * @return le formulaire associé
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        CreateProjectForm form = new CreateProjectForm();
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
        ProjectConfDTO projectConf = (ProjectConfDTO) pObject[0];
        CreateProjectForm form = (CreateProjectForm) pForm;
        MapParameterDTO params = projectConf.getParameters();
        /* Paramètres généraux */
        form.setProjectName( projectConf.getName() );
        form.setProjectId( "" + projectConf.getId() );
        form.setStatus( projectConf.getStatus() );
        // Place le nom (le nom est unique)
        form.setQualityGrid( projectConf.getQualityGrid().getName() );
        form.setProfile( projectConf.getProfile().getName() );
        form.setSourceManagement( projectConf.getSourceManager().getName() );
        /* Affectation des paramètres */
        form.setParameters( projectConf.getParameters() );
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparaît.
     * @return le formulaire associé
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        ProjectConfDTO projectConf = new ProjectConfDTO();
        Object obj[] = { projectConf };
        formToObj( pForm, obj );
        return obj;
    }

    /**
     * @param pForm le formulaire à lire.
     * @param pObject le tableau de ProjectDTO qui récupère les données du formulaire.
     * @throws WTransformerException si un pb apparaît.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {

        // Initialisation
        ProjectConfDTO project = (ProjectConfDTO) pObject[0];
        CreateProjectForm form = (CreateProjectForm) pForm;
        if ( form.getProjectId().equals( "" ) )
        {
            project.setId( -1 );
        }
        else
        {
            project.setId( new Long( form.getProjectId() ).longValue() );
        }
        project.setName( form.getProjectName() );
        project.setStatus( form.getStatus() );
        // Placement du profile
        ProjectProfileDTO profileDTO = new ProjectProfileDTO();
        profileDTO.setName( form.getProfile() );
        project.setProfile( profileDTO );
        // Placement du source manager
        SourceManagementDTO managerDTO = new SourceManagementDTO();
        managerDTO.setName( form.getSourceManagement() );
        project.setSourceManager( managerDTO );
        // Placement de la grille qualité
        QualityGridDTO gridDTO = new QualityGridDTO();
        gridDTO.setName( form.getQualityGrid() );
        project.setQualityGrid( gridDTO );
        project.setParameters( form.getParameters() );
    }
}
