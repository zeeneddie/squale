//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\core\\TaskUtility.java

package com.airfrance.squalix.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskRefBO;
import com.airfrance.squalix.messages.Messages;

/**
 * Cette classe fournit un certin nombre de méthodes "utilitaires" utilisables par les
 * toutes les tâches et le moteur.
 * 
 * @author m400842 (by rose)
 * @version 1.0
 */
public class TaskUtility {

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog(TaskUtility.class);

    /**
     * Retourne une instance de la classe de la classe dont le nom
     * est passé en paramètre.
     * 
     * @param pClassName le nom entièrement spécifié de la classe.
     * @return l'instance d'une tâche.
     * @throws Exception si un problème d'instanciation apparaît.
     * @roseuid 42CAA2A1013B
     */
    private static AbstractTask instanciate(String pClassName) throws Exception {
        AbstractTask task = null;
        try {
            Class theClass = Class.forName(pClassName);
            task = (AbstractTask) theClass.newInstance();
        } catch (Exception e) {
            LOGGER.error(e, e);
            LOGGER.error(Messages.getString("exception.task_not_found") + pClassName);
            task = null;
        }
        return task;
    }

    /**
     * Crée un arbre de tâches à partir des descripteurs d'exécution.<br />
     * Pour chaque tâche, une instance est créée, et ses enfants lui sont attribués.<br />
     * Cette action est fait de manière récursive sur toutes les tâches sous la racine du
     * descripteur d'exécution. 
     * 
     * @param pTaskRef le nom de la classe de la tache
     * @param pProjectId l'id du projet analysé. Peut-être null.
     * @param pAuditId l'id de l'audit pour lequel est exécuté la tâche. Peut-être null.
     * @param pApplicationId l'id de l'application contenant ce projet
     * @param pData les paramètres temporaires
     * @return la tâche racine.
     * @roseuid 42CD40730176
     */
    public static AbstractTask createTask(TaskRefBO pTaskRef, long pProjectId, long pApplicationId, long pAuditId, TaskData pData) {
        AbstractTask task = null;
        try {
            // Crée une nouvelle instance de tâche
            task = instanciate(pTaskRef.getTask().getClassName());
            if (null != task) {
                // On attribue à la tâche les valeurs de ses attributs
                // pattern IOC
                task.setProjectId(new Long(pProjectId));
                task.setAuditId(new Long(pAuditId));
                task.setApplicationId(new Long(pApplicationId));
                task.setStatus(AbstractTask.NOT_ATTEMPTED);
                task.setMandatoryTask(pTaskRef.getTask().isMandatory());
                task.setData(pData);
                task.setTaskParameters(pTaskRef.getParameters());
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return task;
    }

    /**
     * Annule la tache qui a échoué et celles qui suivaient .<br />
     * Le statut qui leur est attribué est CANCELLED.
     * 
     * @param pTask tâche à annuler
     * @roseuid 42D22EF8027A
     */
    public static void stopTask(AbstractTask pTask) {
        if (pTask.getStatus() != AbstractTask.RUNNING) {
            pTask.setStatus(AbstractTask.CANCELLED);
        }
    }

}
