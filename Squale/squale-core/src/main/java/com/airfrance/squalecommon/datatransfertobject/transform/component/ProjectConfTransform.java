package com.airfrance.squalecommon.datatransfertobject.transform.component;

import java.io.Serializable;

import com.airfrance.squalecommon.datatransfertobject.component.ProjectConfDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.parameters.MapParameterTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.config.ProjectProfileTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.config.SourceManagementTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.rule.QualityGridTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ProjectConfTransform
    implements Serializable
{
    /**
     * ProjectConfDTO --> ProjectBO
     * 
     * @param pProjectConfDTO ProjectDTO à transformer
     * @return ProjectBO
     */
    public static ProjectBO dto2Bo( ProjectConfDTO pProjectConfDTO )
    {
        // Initialisation du retour
        ProjectBO projectBO = new ProjectBO();

        // Initialisation des champs suivants
        projectBO.setId( pProjectConfDTO.getId() );
        projectBO.setName( pProjectConfDTO.getName() );
        projectBO.setParameters( MapParameterTransform.dto2Bo( pProjectConfDTO.getParameters() ) );
        projectBO.setProfile( ProjectProfileTransform.dto2bo( pProjectConfDTO.getProfile() ) );
        projectBO.setSourceManager( SourceManagementTransform.dto2bo( pProjectConfDTO.getSourceManager() ) );

        return projectBO;
    }

    /**
     * ProjectBO -> ProjectConfDTO
     * 
     * @param pProjectBO ProjectBO
     * @return ProjectConfDTO
     */
    public static ProjectConfDTO bo2Dto( ProjectBO pProjectBO )
    {
        // Initialisation du retour
        ProjectConfDTO projectConfDTO = new ProjectConfDTO();

        // Initialisation des valeurs
        projectConfDTO.setId( pProjectBO.getId() );
        projectConfDTO.setParameters( MapParameterTransform.bo2Dto( pProjectBO.getParameters() ) );
        projectConfDTO.setName( pProjectBO.getName() );
        projectConfDTO.setProfile( ProjectProfileTransform.bo2dto( pProjectBO.getProfile() ) );
        projectConfDTO.setSourceManager( SourceManagementTransform.bo2dto( pProjectBO.getSourceManager() ) );
        projectConfDTO.setQualityGrid( QualityGridTransform.bo2Dto( pProjectBO.getQualityGrid() ) );
        projectConfDTO.setParameters( MapParameterTransform.bo2Dto( pProjectBO.getParameters() ) );
        projectConfDTO.setStatus( pProjectBO.getStatus() );
        return projectConfDTO;
    }

    /**
     * Permet de modifier les valeurs souhaitées dans applicationDTO sans ecraser les relations
     * 
     * @param pProjectConfDTO ProjectConfDTO
     * @param pProjectBO ProjectBO
     */
    public static void dto2Bo( ProjectConfDTO pProjectConfDTO, ProjectBO pProjectBO )
    {

        // Initialisation des champs suivants
        pProjectBO.setId( pProjectConfDTO.getId() );
        pProjectBO.setName( pProjectConfDTO.getName() );
    }

}
