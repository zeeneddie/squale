package com.airfrance.squalix.tools.pmd;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.pmd.Report;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.daolayer.rulechecking.PmdRuleSetDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.pmd.PmdRuleSetBO;
import com.airfrance.squalecommon.util.file.FileUtility;
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskException;
import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * Tâche de détection de copier/coller La tâche est configurée par un ou plusieurs paramètres nommés 'language', chaque
 * paramètre donne lieu au lancement du de la détection du copy/paste correspondant
 */
public class PmdTask
    extends AbstractTask
{
    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( PmdTask.class );

    /**
     * Constructeur
     */
    public PmdTask()
    {
        mName = "PmdTask";
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.core.Task#execute()
     */
    public void execute()
        throws TaskException
    {
        try
        {
            // Factory
            PmdProcessingFactory factory = new PmdProcessingFactory();
            // Parcours sur les langages
            boolean oneLangAtLeast = false;
            for ( Iterator it = getTaskParameters().iterator(); it.hasNext(); )
            {
                TaskParameterBO param = (TaskParameterBO) it.next();
                if ( param.getName().equals( "language" ) )
                {
                    LOGGER.info( PmdMessages.getString( "pmd.processing.language", param.getValue() ) );
                    oneLangAtLeast = true;
                    AbstractPmdProcessing processing = factory.createPmdProcessing( param.getValue() ); // Récupération
                                                                                                        // du fichier de
                                                                                                        // ruleset
                    PmdRuleSetBO ruleset = getRuleSet( param.getValue() );
                    // La tâche peut avoir été annulée
                    if ( mStatus != CANCELLED )
                    {
                        Report report =
                            processing.process( getData(), getProject().getParameters(),
                                                FileUtility.byteToFile( ruleset.getValue() ) );
                        // Création du persistor
                        PmdPersistor persistor = new PmdPersistor( getProject(), getAudit(), ruleset, param.getValue() );
                        persistor.storeResults( getSession(), report );
                    }
                }
                else
                {
                    throw new ConfigurationException( PmdMessages.getString( "exception.parameter.invalid",
                                                                             new Object[] { param.getName(),
                                                                                 param.getValue() } ) );
                }
            }
            // Vérification qu'il existe au moins un language défini
            if ( !oneLangAtLeast )
            {
                throw new ConfigurationException( PmdMessages.getString( "exception.parameter.missing" ) );
            }
        }
        catch ( Exception e )
        {
            throw new TaskException( e );
        }
    }

    /**
     * @param pLanguage langage
     * @return fichier de ruleset
     * @throws JrafDaoException si erreur
     * @throws ConfigurationException si erreur
     */
    protected PmdRuleSetBO getRuleSet( String pLanguage )
        throws JrafDaoException, ConfigurationException
    {
        PmdRuleSetBO ruleset = null;
        // On récupère les paramètres du projet
        MapParameterBO params = (MapParameterBO) getProject().getParameter( ParametersConstants.PMD );
        if ( params == null )
        {
            String message = PmdMessages.getString( "warning.task.not_set" );
            // On affiche un warning sans lancer d'exception, la tâche ne sera pas exécutée.
            initError( message );
            LOGGER.warn( message );
            // Les paramètres ne sont pas configurés, on annule la tâche
            mStatus = CANCELLED;
        }
        else
        {
            StringParameterBO param = null;
            if ( pLanguage.equals( "java" ) )
            {
                param = (StringParameterBO) params.getParameters().get( ParametersConstants.PMD_JAVA_RULESET_NAME );
            }
            else if ( pLanguage.equals( "jsp" ) )
            {
                param = (StringParameterBO) params.getParameters().get( ParametersConstants.PMD_JSP_RULESET_NAME );
            }
            else
            {
                throw new ConfigurationException( PmdMessages.getString( "exception.unknown.language", pLanguage ) );
            }
            if ( param == null )
            {
                String message = PmdMessages.getString( "exception.ruleset.missing" );
                LOGGER.error( message );
                // Renvoi d'une exception de configuration
                throw new ConfigurationException( message );
            }
            ruleset = PmdRuleSetDAOImpl.getInstance().findRuleSet( getSession(), param.getValue(), pLanguage );

        }
        return ruleset;
    }
}
