/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.squale.squalix.tools.cpptest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.squalecommon.datatransfertobject.rulechecking.CppTestRuleSetDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import org.squale.squalecommon.enterpriselayer.facade.cpptest.CppTestFacade;
import org.squale.squalix.core.AbstractTask;
import org.squale.squalix.core.TaskData;
import org.squale.squalix.core.TaskException;
import org.squale.squalix.core.exception.ConfigurationException;
import org.squale.squalix.util.file.FileUtility;
import org.squale.squalix.util.process.ProcessErrorHandler;
import org.squale.squalix.util.process.ProcessManager;
import org.squale.squalix.util.process.ProcessOutputHandler;

/**
 * Tâche CppTest Cette tâche réalise l'interface avec l'outil CppTest pour collecter les données concernant les règles
 * de codage non respectées. Les données de configuration indiquent la liste des scripts de compilation à lancer
 */
public class CppTestTask
    extends AbstractTask
    implements ProcessErrorHandler, ProcessOutputHandler
{
    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( CppTestTask.class );

    /** Filtre d'erreur */
    private ErrorFilter mErrorFilter;

    /**
     * Constructeur
     */
    public CppTestTask()
    {
        mName = "CppTestTask";
        mErrorFilter = new ErrorFilter();
    }

    /**
     * Exécution de la tâche CppTest
     * 
     * @throws TaskException en cas d'échec
     */
    public void execute()
        throws TaskException
    {
        try
        {
            // Lecture de la configuration du projet
            CppTestConfiguration conf = new CppTestConfiguration();
            conf.parse( new FileInputStream( "config/cpptest-config.xml" ) );
            // Les données du projet doivent fournir le nom du fichier
            // de configuration
            MapParameterBO cppTestMap = (MapParameterBO) getProject().getParameter( ParametersConstants.CPPTEST );
            if ( cppTestMap == null )
            {
                String message =
                    CppTestMessages.getString( "exception.variable.not_found", ParametersConstants.CPPTEST );
                LOGGER.error( message );
                // Renvoi d'une exception de configuration
                throw new ConfigurationException( message );
            }
            // Obtention du ruleset
            CppTestRuleSetDTO ruleset = getRuleSet( cppTestMap );

            // Espace de travail pour CppTest
            CppTestWorkSpace workspace =
                new CppTestWorkSpace( new File( conf.getReportDirectory(), "project" + getProjectId() + "_audit"
                    + getAuditId() ) );

            // Lancement de la commande externe de génération du rapport
            generateReport( workspace, ruleset, cppTestMap, conf.getLogger() );

            // Exploitation des données du rapport
            parseReport( workspace, ruleset );

            // positionne les données sur la taille du file System
            affectFileSystemSize( conf.getReportDirectory(), true );

            // Nettoyage du workspace
            workspace.cleanup();
        }
        catch ( Exception e )
        {
            throw new TaskException( e );
        }
    }

    /**
     * Génération du rapport XML Le rapport XML est généré en une seule étape par le lancement d'une commande cpptest
     * qui réalise l acréation du projet et le lancement de l'audit sur les règles
     * 
     * @param pWorkSpace espace de travail CppTest
     * @param pCppTestMap paramètres de la tâche
     * @param pRuleSet ruleset utilisé
     * @param pLogger le fichier de log
     * @throws Exception si erreur
     */
    protected void generateReport( CppTestWorkSpace pWorkSpace, CppTestRuleSetDTO pRuleSet, MapParameterBO pCppTestMap,
                                   String pLogger )
        throws Exception
    {
        // Récupération des informations de configuration du projet
        String ruleSet = pRuleSet.getCppTestName();
        File script;
        StringParameterBO scriptConf =
            (StringParameterBO) pCppTestMap.getParameters().get( ParametersConstants.CPPTEST_SCRIPT );
        if ( scriptConf == null )
        {
            String message =
                CppTestMessages.getString( "exception.variable.not_found", ParametersConstants.CPPTEST_SCRIPT );
            LOGGER.error( message );
            // Renvoi d'une exception de configuration
            throw new ConfigurationException( message );
        }
        else
        {
            script = new File( scriptConf.getValue() );
        }
        // Détermination du script à exécuter
        script = computeScriptLocation( script );
        // Lancement de la commande
        String viewPath = (String) getData().getData( TaskData.VIEW_PATH );
        String[] command =
            { script.getAbsolutePath(), viewPath, ruleSet, pWorkSpace.getProjectFile().getAbsolutePath(),
                pWorkSpace.getReportDirectory().getAbsolutePath() };
        LOGGER.info( CppTestMessages.getString( "report.command", command ) );
        executeCommand( command, pLogger );
    }

    /**
     * Obtention du DTO du ruleset
     * 
     * @param pCppTestMap paramètres de la tâche
     * @return nom du ruleset CppTest
     * @throws ConfigurationException si erreur
     * @throws JrafEnterpriseException si erreur
     */
    private CppTestRuleSetDTO getRuleSet( MapParameterBO pCppTestMap )
        throws ConfigurationException, JrafEnterpriseException
    {
        // Récupération des informations de configuration du projet
        StringParameterBO ruleSetName =
            (StringParameterBO) pCppTestMap.getParameters().get( ParametersConstants.CPPTEST_RULESET_NAME );
        if ( ruleSetName == null )
        {
            String message =
                CppTestMessages.getString( "exception.variable.not_found", ParametersConstants.CPPTEST_RULESET_NAME );
            LOGGER.error( message );
            // Renvoi d'une exception de configuration
            throw new ConfigurationException( message );
        }
        // Obtention du ruleset dans la base
        CppTestRuleSetDTO dto = CppTestFacade.getCppTestConfiguration( ruleSetName.getValue() );
        if ( dto == null )
        {
            String message = CppTestMessages.getString( "exception.ruleset.not_found", ruleSetName.getValue() );
            LOGGER.error( message );
            // Renvoi d'une exception de configuration
            throw new ConfigurationException( message );
        }
        return dto;
    }

    /**
     * Localisation du script
     * 
     * @param pScript script à tester
     * @return script à lancer
     * @throws ConfigurationException si erreur
     */
    private File computeScriptLocation( File pScript )
        throws ConfigurationException
    {
        File result;
        // Si le script a un nom absolue et existe, on prend celui-ci
        if ( pScript.isAbsolute() && pScript.exists() )
        {
            result = pScript;
        }
        else
        {
            // Le script est supposé être relatif à la vue
            String viewPath = (String) getData().getData( TaskData.VIEW_PATH );
            if ( viewPath == null )
            {
                String message = CppTestMessages.getString( "exception.variable.not_found", TaskData.VIEW_PATH );
                LOGGER.error( message );
                // Renvoi d'une exception de configuration
                throw new ConfigurationException( message );
            }
            result = new File( viewPath, pScript.getPath() );
            if ( !result.exists() )
            {
                String message = CppTestMessages.getString( "error.script.not_found", result.getAbsolutePath() );
                LOGGER.error( message );
                // Renvoi d'une exception de configuration
                throw new ConfigurationException( message );
            }
        }
        return result;
    }

    /**
     * Exécution de la commande
     * 
     * @param command commande à lancer
     * @param pLogger le fichier de log
     * @throws IOException si erreur
     * @throws InterruptedException si erreur
     */
    protected void executeCommand( String[] command, String pLogger )
        throws IOException, InterruptedException
    {
        // la sortie va être redirigée vers un fichier de log et non vers squalix.txt
        ProcessManager mgr = new ProcessManager( command, null, null, FileUtility.getLogFile( pLogger ) );
        mgr.setOutputHandler( this );
        int result = mgr.startProcess( this );
        if ( result != 0 )
        {
            String message = CppTestMessages.getString( "exception.command", result + "" );
            LOGGER.error( message );
            throw new IOException( message );
        }
    }

    /**
     * Lecture du rapport CppTest Le rapport est lu dans le répertoire contenant celui-ci, puis les données associées au
     * rapport sont enregistrées dans la base
     * 
     * @param pWorkSpace espace de travail CppTest
     * @param pRuleSet ruleset
     * @throws Exception si erreur
     */
    protected void parseReport( CppTestWorkSpace pWorkSpace, CppTestRuleSetDTO pRuleSet )
        throws Exception
    {
        // Recherche des rapports
        Collection files = pWorkSpace.getReportFiles();
        if ( files.size() == 0 )
        {
            String message = CppTestMessages.getString( "error.noreport", pWorkSpace.getReportDirectory() );
            LOGGER.error( message );
            // Aucun rapport n'a été généré, levée d'une exception
            throw new Exception( message );
        }
        // Traitement des rapports
        Iterator it = files.iterator();
        ReportParser rp = new ReportParser();
        HashMap rules = new HashMap();
        while ( it.hasNext() )
        {
            File file = (File) it.next();
            Map result = rp.parse( new FileInputStream( file ), (String) getData().getData( TaskData.VIEW_PATH ) );
            // On fusionne les résultats avec ceux obtenus jusqu'alors
            mergeResults( rules, result );
        }

        // Sauvegarde des données dans la base
        CppTestPersistor persistor = new CppTestPersistor();
        persistor.storeResults( getSession(), getProject(), getAudit(), rules, pRuleSet );
    }

    /**
     * Fusion des résultats dans la MAP
     * 
     * @param pResult résultat de la fusion
     * @param pMerged données à fusionner
     */
    protected void mergeResults( Map pResult, Map pMerged )
    {
        Iterator entries = pMerged.entrySet().iterator();
        while ( entries.hasNext() )
        {
            Map.Entry entry = (Entry) entries.next();
            Collection val = (Collection) pResult.get( entry.getKey() );
            if ( val == null )
            {
                val = (Collection) entry.getValue();
                pResult.put( entry.getKey(), val );
            }
            else
            {
                val.addAll( (Collection) entry.getValue() );
            }
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.squale.squalix.util.process.ProcessErrorHandler#processError(java.lang.String)
     */
    public void processError( String pErrorMessage )
    {
        initError( pErrorMessage );
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.squale.squalix.util.process.ProcessOutputHandler#processOutput(java.lang.String)
     */
    public void processOutput( String pOutputLine )
    {
        // CPPTEST ecrit ses erreurs sur le flux stdout !
        // Le script est supposé écrit comme ayant le paramètre -Zoe
        // censé limiter les traces
        mErrorFilter.processLine( pOutputLine );
        if ( mErrorFilter.errorAvailable() )
        {
            initError( mErrorFilter.consumeError() );
        }
    }

}
