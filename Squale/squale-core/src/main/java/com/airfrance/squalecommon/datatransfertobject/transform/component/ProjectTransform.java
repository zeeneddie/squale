package com.airfrance.squalecommon.datatransfertobject.transform.component;

import java.io.Serializable;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentType;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;

/**
 * @author M400841
 * @deprecated ComponentTransform doit etre utilisé a la place de ProjectTransform
 */
public class ProjectTransform
    implements Serializable
{

    /**
     * Constructeur prive
     */
    private ProjectTransform()
    {
    }

    /**
     * ProjectDTO -> ProjectBO
     * 
     * @param pProjectDTO ProjectDTO à transformer
     * @return ProjectBO
     */
    public static ProjectBO dto2Bo( ComponentDTO pProjectDTO )
    {
        // Initialisation du retour
        ProjectBO projectBO = new ProjectBO();

        projectBO.setId( pProjectDTO.getID() );
        projectBO.setName( pProjectDTO.getName() );

        projectBO.setExcludedFromActionPlan( pProjectDTO.getExcludedFromActionPlan() );
        projectBO.setJustification( pProjectDTO.getJustification() );
        return projectBO;
    }

    /**
     * ProjectBO -> ProjectDTO
     * 
     * @param pProjectBO ProjectBO
     * @return ProjectDTO
     */
    public static ComponentDTO bo2Dto( ProjectBO pProjectBO )
    {
        // Initialisation du retour
        ComponentDTO projectDTO = new ComponentDTO();

        projectDTO.setID( pProjectBO.getId() );
        projectDTO.setName( pProjectBO.getName() );
        projectDTO.setType( ComponentType.PROJECT );
        projectDTO.setTechnology( pProjectBO.getProfile().getName() );
        projectDTO.setExcludedFromActionPlan( pProjectBO.getExcludedFromActionPlan() );
        projectDTO.setJustification( pProjectBO.getJustification() );

        return projectDTO;
    }
}
