/*
 * Créé le 18 août 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalix.tools.computing.project;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.ErrorBO;
import com.airfrance.squalecommon.enterpriselayer.facade.rule.AuditComputing;
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.tools.computing.ComputingMessages;

/**
 * @author M400843
 * 
 */
public class ComputeResultTask extends AbstractTask {

    
    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog(ComputeResultTask.class);

    /**
     * Constructeur
     * @roseuid 430452570082
     */
    public ComputeResultTask() {
        mName = "ComputeResultTask";
    }

    // Cette tache n'a pas d'influence dans le calcul de la taille max du file system

    /**
     * @see java.lang.Runnable#run()
     * @roseuid 430452570083
     */
    public void execute() {
        String[] tab = { mProject.getName(), mProject.getParent().getName()};
        LOGGER.debug(ComputingMessages.getString("logs.result.debug.running_task", tab));
        // Traitements pour les calculs
        analyze();
    }

    /**
     * Execute la méthode compute() sur toute les regles qualités du projet
     * @roseuid 430452570092
     */
    private void analyze() {
        try {
            AuditComputing.computeAuditResult(getSession(), mProject, mAudit);
        } catch (Exception e) {
            String[] tab = { mProject.getName(), mProject.getParent().getName(), "" }; // TODO changer le message
            ErrorBO error = new ErrorBO(e.getMessage(), ComputingMessages.getString("error.cant_compute.proj_proj_rule", tab), ErrorBO.CRITICITY_FATAL, "task.result.name", mAudit, mProject);
            mErrors.add(error);
            LOGGER.error(e,e);
        }
    }

}
