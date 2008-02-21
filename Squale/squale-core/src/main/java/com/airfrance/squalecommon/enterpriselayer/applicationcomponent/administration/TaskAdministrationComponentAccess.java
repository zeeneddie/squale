package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.squalecommon.enterpriselayer.facade.component.TaskFacade;

/**
 */
public class TaskAdministrationComponentAccess extends DefaultExecuteComponent {

    
    /**
     * @param pProjectId l'id du projet
     * @return la liste des taches du projet dans leur ordre d'exécution
     * @throws JrafEnterpriseException en cas d'échec
     */
    public Collection getAllTasks(Long pProjectId) throws JrafEnterpriseException {
        return TaskFacade.getAllTasks(pProjectId);
    }
}
