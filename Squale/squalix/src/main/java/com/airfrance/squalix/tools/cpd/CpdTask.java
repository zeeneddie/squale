package com.airfrance.squalix.tools.cpd;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.ErrorBO;
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.core.TaskException;
import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * Tâche de détection de copier/coller
 * La tâche est configurée par un ou plusieurs paramètres nommés 'language',
 * chaque paramètre donne lieu au lancement du de la détection du copy/paste
 * correspondant
 */
public class CpdTask extends AbstractTask {
    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog(CpdTask.class);

    /**
     * Constructeur
     */
    public CpdTask() {
        mName = "CpdTask";
    }

    /** 
     * {@inheritDoc}
     * @see com.airfrance.squalix.core.Task#execute()
     */
    public void execute() throws TaskException {
        try {
            // Création du persistor
            CpdPersistor persistor = new CpdPersistor(getProject(), getAudit(), (String) getData().getData(TaskData.VIEW_PATH));
            // Factory
            CpdProcessingFactory factory = new CpdProcessingFactory();
            // Pracours sur les langages
            boolean langFound = false;
            for (Iterator it = getTaskParameters().iterator(); it.hasNext();) {
                TaskParameterBO param = (TaskParameterBO) it.next();
                if (param.getName().equals("language")) {
                    LOGGER.info(CpdMessages.getString("cpd.processing.language", param.getValue()));
                    langFound = true;
                    AbstractCpdProcessing processing = factory.createCpdProcessing(param.getValue());
                    Iterator matches = processing.process(getData(), getProject().getParameters());
                    // Stockage du langage
                    persistor.addResult(param.getValue(), matches);
                } else {
                    throw new ConfigurationException(CpdMessages.getString("exception.parameter.invalid", new Object[] { param.getName(), param.getValue()}));
                }
            }
            // Vérification qu'il existe au moins un language défini
            if (!langFound) {
                throw new ConfigurationException(CpdMessages.getString("exception.parameter.missing"));
            }
            // On fait persister les données
            persistor.storeResults(getSession());
        } catch (Exception e) {
            // Traitement spécial pour les erreurs remontées quand le code analysé n'est pas conforme
            // à la norme XHTML, on remonte ces erreurs juste avec un niveau warning
            if (e.getMessage().indexOf("net.sourceforge.pmd.jsp.ast.TokenMgrError: Lexical error") != -1) {
                ErrorBO error = new ErrorBO();
                error.setInitialMessage(e.getMessage());
                error.setMessage(CpdMessages.getString("warning.code.no.conform.XHTML"));
                error.setLevel(ErrorBO.CRITICITY_WARNING);
                mErrors.add(error);
            } else {
                throw new TaskException(e);
            }
        }
    }
}
