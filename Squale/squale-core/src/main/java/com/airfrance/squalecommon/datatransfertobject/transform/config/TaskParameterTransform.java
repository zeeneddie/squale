package com.airfrance.squalecommon.datatransfertobject.transform.config;

import com.airfrance.squalecommon.datatransfertobject.config.TaskParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskParameterBO;

/**
 * Conversion des paramètres de tâche
 */
public class TaskParameterTransform {

    /**
     * Transformation BO - DTO
     * @param pTaskParameter paramètre de tâche
     * @return paramètre converti
     */
    static public TaskParameterDTO bo2dto(TaskParameterBO pTaskParameter) {
        TaskParameterDTO result = new TaskParameterDTO();
        result.setName(pTaskParameter.getName());
        result.setValue(pTaskParameter.getValue());
        return result;
    }
    
    // Le dto2bo est inutile car on ne modifie pas les paramètres dans le web
}
