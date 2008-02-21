package com.airfrance.squalecommon.datatransfertobject.transform.config;

import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.config.TaskDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskRefBO;

/**
 * Transformation d'une tâche Squalix
 */
public class TaskTransform {

    /**
     * Convertit un TaskBO en TaskDTO
     * 
     * @param pTaskRefBO objet à convertir
     * 
     * @return résultat de la conversion
     */
    public static TaskDTO bo2dto(TaskRefBO pTaskRefBO) {
        TaskDTO result = new TaskDTO();
        result.setId(pTaskRefBO.getId());
        result.setClassName(pTaskRefBO.getTask().getClassName());
        result.setName(pTaskRefBO.getTask().getName());
        result.setConfigurable(pTaskRefBO.getTask().isConfigurable());
        result.setStandard(pTaskRefBO.getTask().isStandard());
        result.setMandatory(pTaskRefBO.getTask().isMandatory());
        // Conversion des paramètres
        Iterator it = pTaskRefBO.getParameters().iterator();
        while (it.hasNext()) {
            result.addParameter(TaskParameterTransform.bo2dto((TaskParameterBO) it.next()));
        }
        return result;
    }

    /**
     * Convertit un TaskDTO en TaskRefBO
     * 
     * @param pTaskDTO objet à convertir
     * 
     * @return résultat de la conversion
     */
    public static Object dto2bo(TaskDTO pTaskDTO) {
        TaskRefBO result = new TaskRefBO();
        TaskBO task = new TaskBO();
        task.setId(pTaskDTO.getId());
        task.setClassName(pTaskDTO.getClassName());
        task.setName(pTaskDTO.getName());
        task.setConfigurable(pTaskDTO.isConfigurable());
        task.setMandatory(pTaskDTO.isMandatory());
        result.setTask(task);
        return result;
    }

}
