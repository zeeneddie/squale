package com.airfrance.squalecommon.datatransfertobject.transform.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.config.SourceManagementDTO;
import com.airfrance.squalecommon.datatransfertobject.config.TaskDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskRefBO;

/**
 * Transforme un récupérateur de source
 */
public class SourceManagementTransform
{

    /**
     * Convertit un SourceManagementBO en SourceManagementDTO
     * 
     * @param pManager l'objet à convertir
     * @return le résultat de la conversion
     */
    public static SourceManagementDTO bo2dto( SourceManagementBO pManager )
    {
        SourceManagementDTO result = new SourceManagementDTO();
        result.setId( pManager.getId() );
        result.setName( pManager.getName() );
        result.setMilestoneAudit( pManager.isMilestoneAudit() );
        result.setNormalAudit( pManager.isNormalAudit() );
        List analysisTasksDTO = new ArrayList();
        Iterator analysisTasks = pManager.getAnalysisTasks().iterator();
        while ( analysisTasks.hasNext() )
        {
            analysisTasksDTO.add( TaskTransform.bo2dto( (TaskRefBO) analysisTasks.next() ) );
        }
        result.setAnalysisTasks( analysisTasksDTO );
        List terminationTasksDTO = new ArrayList();
        Iterator terminationTasks = pManager.getTerminationTasks().iterator();
        while ( terminationTasks.hasNext() )
        {
            terminationTasksDTO.add( TaskTransform.bo2dto( (TaskRefBO) terminationTasks.next() ) );
        }
        result.setTerminationTasks( terminationTasksDTO );
        return result;
    }

    /**
     * Convertit une liste de SourceManagementBO en liste de SourceManagementDTO
     * 
     * @param pManagers la liste des récupérateurs de sources à convertir
     * @return le résultat de la conversion
     */
    public static Collection bo2dto( Collection pManagers )
    {
        Collection managersDTO = new ArrayList();
        SourceManagementDTO managerDTO;
        SourceManagementBO managerBO;
        Iterator it = pManagers.iterator();
        while ( it.hasNext() )
        {
            managerBO = (SourceManagementBO) it.next();
            managerDTO = bo2dto( managerBO );
            managersDTO.add( managerDTO );
        }
        return managersDTO;
    }

    /**
     * @param pManagementDTO le récupérateur de sources à convertir
     * @return le BO récupérateur de source
     */
    public static SourceManagementBO dto2bo( SourceManagementDTO pManagementDTO )
    {
        SourceManagementBO result = new SourceManagementBO();
        result.setId( pManagementDTO.getId() );
        result.setName( pManagementDTO.getName() );
        result.setMilestoneAudit( pManagementDTO.isMilestoneAudit() );
        result.setNormalAudit( pManagementDTO.isNormalAudit() );
        List analysisTasksBO = new ArrayList();
        Iterator analysisTasks = pManagementDTO.getAnalysisTasks().iterator();
        while ( analysisTasks.hasNext() )
        {
            analysisTasksBO.add( TaskTransform.dto2bo( (TaskDTO) analysisTasks.next() ) );
        }
        result.setAnalysisTasks( analysisTasksBO );
        List terminationTasksBO = new ArrayList();
        Iterator terminationTasks = pManagementDTO.getTerminationTasks().iterator();
        while ( terminationTasks.hasNext() )
        {
            terminationTasksBO.add( TaskTransform.dto2bo( (TaskDTO) terminationTasks.next() ) );
        }
        result.setTerminationTasks( terminationTasksBO );
        return result;
    }

}
