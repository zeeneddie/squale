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
package org.squale.squalix.tools.macker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.innig.macker.Macker;
import net.innig.macker.event.ListenerException;
import net.innig.macker.event.MackerIsMadException;
import net.innig.macker.rule.RulesException;
import net.innig.macker.structure.ClassParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.squalecommon.daolayer.result.MeasureDAOImpl;
import org.squale.squalecommon.daolayer.rulechecking.ProjectRuleSetDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.rulechecking.MackerTransgressionBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionItemBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.ProjectRuleSetBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;
import org.squale.squalecommon.enterpriselayer.facade.macker.MackerConfigParser;
import org.squale.squalix.core.AbstractTask;
import org.squale.squalix.core.TaskData;
import org.squale.squalix.core.TaskException;
import org.squale.squalix.core.exception.ConfigurationException;
import org.squale.squalix.util.buildpath.BuildProjectPath;
import org.squale.squalix.util.file.FileUtility;

/**
 * Tâche Macker.<br/> Vérifie l'architecture du projet à auditer.<br/> La tâche de compilation java doit avoir été
 * éxécutée avant afin que le chemin du répertoire contenant les ".class" (CLASSES_DIR) soit présent dans les paramètres
 * temporaires.
 */
public class MackerTask
    extends AbstractTask
{

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( MackerTask.class );

    /** Configuration de la tâche Macker */
    protected MackerConfiguration mConfiguration;

    /** L'écouteur pour les classes java */
    protected JavaStorageListener mListener;

    /**
     * Constructeur par defaut
     */
    public MackerTask()
    {
        super();
        mName = "MackerTask";
        mConfiguration = new MackerConfiguration();
    }

    // Cette tache n'a pas d'influence dans le calcul de la taille du file system

    /**
     * Analyse les fichiers compilés afin de relever pour chacun les transgressions des règles concernant
     * l'architecture.
     * 
     * @throws TaskException si erreur
     */
    public void execute()
        throws TaskException
    {
        if ( init() )
        {
            try
            {
                analyze();
                // On fait persister les mesures
                persisteMeasures( mListener.getNbOcc(), mListener.getDetails(), mConfiguration.getRuleSet() );
            }
            catch ( Exception e )
            {
                throw new TaskException( e );
            }
        }
    }

    /**
     * Analyse les fichiers
     * 
     * @throws ClassParseException si erreur
     * @throws IOException si erreur
     * @throws RulesException si erreur
     * @throws ListenerException si erreur
     * @throws MackerIsMadException si erreur
     */
    protected void analyze()
        throws ClassParseException, IOException, RulesException, ListenerException, MackerIsMadException
    {
        Macker macker = configMacker( mConfiguration.getFilesToAnalyze(), mConfiguration.getConfigFile() );
        // On modifie le classpath pour exécuter Macker afin d'avoir le build classpath pour la résolution
        // de l'ensemble des classes.
        // macker.setClassLoader(new URLClassLoader(getClasspathURLs()));
        /* On lance l'analyse en attachant notre événement à Macker: */
        mListener = new JavaStorageListener( getSession(), mProject, mConfiguration );
        macker.addListener( mListener );
        macker.checkRaw();

    }

    /**
     * Construit le tableau des urls à ajouter au classpath pour exécuter Macker à partir du classpath temporaire
     * 
     * @return les URLs
     */
    protected URL[] getClasspathURLs()
    {
        ArrayList urlsList = new ArrayList();
        // On découpe le classpath des paramètres temporaires
        String[] paths = ( (String) mData.getData( TaskData.CLASSPATH ) ).split( ";" );
        for ( int i = 0; i < paths.length; i++ )
        {
            try
            {
                urlsList.add( new File( paths[i] ).toURL() );
            }
            catch ( MalformedURLException e )
            {
                // On log juste l'erreur
                LOGGER.warn( "Error in temp classpath, malformed url: " + paths[i] );
            }
        }
        return (URL[]) urlsList.toArray( new URL[urlsList.size()] );
    }

    /**
     * Initialise la tâche Macker
     * 
     * @throws TaskException si erreur
     * @return true si la tâche n'est pas annulée
     */
    protected boolean init()
        throws TaskException
    {
        // Les données du projet doivent fournir le nom du fichier
        // de configuration
        MapParameterBO mackerMap = (MapParameterBO) getProject().getParameter( ParametersConstants.MACKER );
        if ( mackerMap == null )
        {
            String message = MackerMessages.getString( "macker.exception.no_configuration" );
            // On affiche un warning sans lancer d'exception, la tâche ne sera pas exécutée.
            initError( message );
            LOGGER.warn( message );
            // Les paramètres sont mal configurés, on annule la tâche
            mStatus = CANCELLED;
        }
        else
        {
            // On récupère le view_path crée par la tâche du source manager en ajoutant un séparateur
            // Unix en bout au cas où.
            String root = (String) mData.getData( TaskData.VIEW_PATH );
            if ( null == root )
            {
                String message =
                    MackerMessages.getString( "macker.exception.view_path_not_found" ) + TaskData.VIEW_PATH;
                LOGGER.error( message );
                // Lance une exception de configuration
                throw new TaskException( message );
            }
            root += "/";
            mConfiguration.setRoot( root );

            // On récupère les chemins relatifs des répertoires contenant les .java du projet
            ListParameterBO sources = (ListParameterBO) getProject().getParameter( ParametersConstants.SOURCES );
            if ( sources == null )
            {
                String message = MackerMessages.getString( "macker.exception.sources_not_found" );
                LOGGER.error( message );
                // Lance une exception de configuration
                throw new TaskException( message );
            }
            mConfiguration.setSources( BuildProjectPath.buildProjectPath( root, sources.getParameters() ) );

            // On récupère les répertoires contenant les .class du projet à analyser
            // crée par la tâche de compilation java
            List classesDirs = (List) this.getData().getData( TaskData.CLASSES_DIRS );
            if ( classesDirs == null )
            {
                String message = MackerMessages.getString( "macker.exception.class_dir_not_found" );
                LOGGER.error( message );
                // Lance une exception de configuration
                throw new TaskException( message );
            }
            // On génère la liste des fichiers compilés à analyser
            HashSet classes = new HashSet();
            for ( int i = 0; i < classesDirs.size(); i++ )
            {
                classes.addAll( mConfiguration.getFilesToAnalyze( (String) classesDirs.get( i ) ) );
            }
            mConfiguration.setFilesToAnalyze( classes );
            // Les nom des fichiers qui peuvent être persistés
            List includedFileNames =
                FileUtility.getIncludedFiles(
                                              root,
                                              mConfiguration.getSources(),
                                              (ListParameterBO) mProject.getParameter( ParametersConstants.INCLUDED_PATTERNS ),
                                              (ListParameterBO) mProject.getParameter( ParametersConstants.EXCLUDED_PATTERNS ),
                                              null, new String[] { ".java" } );
            mConfiguration.setIncludedFiles( includedFileNames );

            try
            {
                /* On configure Macker */
                // On récupère le fichier de configuration
                File configFile = getConfigFile( mackerMap, root );
                // Si le fichier de configuration n'existe pas
                if ( !configFile.exists() )
                {
                    String message =
                        MackerMessages.getString( "macker.exception.configurationfile_not_found" )
                            + configFile.toString();
                    LOGGER.error( message );
                    // Lance une exception de configuration
                    throw new TaskException( message );
                }
                mConfiguration.setConfigFile( configFile );
                // On récupère la liste des règles en parsant le fichier de configuration
                ProjectRuleSetBO projectRuleSet = importMackerConfig( configFile );
                mConfiguration.setRuleSet( projectRuleSet );
            }
            catch ( Exception e )
            {
                throw new TaskException( e );
            }
        }
        return mStatus != CANCELLED;
    }

    /**
     * @param pConfigFile le fichier de configuration Macker
     * @throws ConfigurationException si erreur
     * @throws FileNotFoundException si erreur
     * @return la liste des règles Macker
     */
    private ProjectRuleSetBO importMackerConfig( File pConfigFile )
        throws ConfigurationException, FileNotFoundException
    {
        // Importation du fichier
        MackerConfigParser parser = new MackerConfigParser();
        InputStream stream = new FileInputStream( pConfigFile );
        StringBuffer errors = new StringBuffer();
        ProjectRuleSetBO ruleset = parser.parseFile( stream, errors );
        // Si le parsing s'est mal passé on lance une erreur de configuration
        if ( errors.length() > 0 )
        {
            String message =
                MackerMessages.getString( "macker.exception.configurationfile_parsing_error" ) + "("
                    + pConfigFile.getAbsolutePath() + ")" + errors;
            LOGGER.error( message );
            // Lance une exception de configuration
            throw new ConfigurationException( message );
        }
        return ruleset;
    }

    /**
     * Fait persister les mesures
     * 
     * @param pNbOcc les résultats obtenus par Macker
     * @param pDetails le détail des transgressions
     * @param pRuleset les règles
     * @throws IOException si erreur
     * @throws JrafDaoException si erreur
     */
    protected void persisteMeasures( HashMap pNbOcc, HashMap pDetails, ProjectRuleSetBO pRuleset )
        throws IOException, JrafDaoException
    {
        // On sauvegarde le ruleset dans la base
        pRuleset.setProject( mProject );
        ProjectRuleSetDAOImpl.getInstance().create( getSession(), pRuleset );
        // Création de la transgression
        MackerTransgressionBO transgression = new MackerTransgressionBO();
        transgression.setAudit( mAudit );
        transgression.setComponent( mProject );
        transgression.setRuleSet( pRuleset );
        transgression.setTaskName( mName );
        // On parcourt les règles connues dans le ruleset
        Map rules = pRuleset.getRules();
        Iterator ruleCodes = rules.keySet().iterator();
        while ( ruleCodes.hasNext() )
        {
            String ruleCode = (String) ruleCodes.next();
            // On récupère les détails liés à cette règle
            ArrayList items = (ArrayList) pDetails.get( ruleCode );
            // si il y en a on modifie la transgression en conséquence
            if ( null != items )
            {
                RuleBO rule = (RuleBO) rules.get( ruleCode );
                for ( int i = 0; i < items.size(); i++ )
                {
                    RuleCheckingTransgressionItemBO item = (RuleCheckingTransgressionItemBO) items.get( i );
                    item.setRule( rule );
                    transgression.getDetails().add( item );
                }
            }
            Integer value = (Integer) pNbOcc.get( ruleCode );
            int nbOcc = 0;
            // Si le parsing n'a pas donné de résultat, on place 0 comme
            // nombre de transgression
            if ( null != value )
            {
                nbOcc = value.intValue();
            }
            // On ajoute une métrique de type Integer pour chaque règle transgressée
            // avec 0 comme valeur par défaut
            IntegerMetricBO metric = new IntegerMetricBO();
            metric.setName( ruleCode );
            metric.setValue( nbOcc );
            metric.setMeasure( transgression );
            transgression.putMetric( metric );
        }
        // Sauvegarde des données dans la base
        MeasureDAOImpl.getInstance().create( getSession(), transgression );
    }

    /**
     * Configure Macker en lui indiquant le fichier des règles et les classes qu'il doit analyser
     * 
     * @param pFilesToAnalyse les fichiers compilés à analyser
     * @param pConfigFile le fichier de configuration Macker
     * @throws ClassParseException si erreur
     * @throws RulesException si erreur
     * @throws IOException si erreur
     * @return Macker le macker configuré
     */
    protected Macker configMacker( HashSet pFilesToAnalyse, File pConfigFile )
        throws RulesException, ClassParseException, IOException
    {
        Macker macker = new Macker();
        Iterator filesIt = pFilesToAnalyse.iterator();
        while ( filesIt.hasNext() )
        {
            try
            {
                macker.addClass( new File( (String) filesIt.next() ) );
            }
            catch ( IllegalStateException ise )
            {
                // On log juste un warning. Cette erreur peut survenir lorsque
                // l'utilisateur a par exemple laisser des .class dans le répertoire
                // de génération des .class
                LOGGER.warn( ise.getMessage() );
                initError( ise.getMessage() );
            }
        }
        // On indique le fichier de configuration
        macker.addRulesFile( pConfigFile );
        return macker;
    }

    /**
     * Récupère le fichier de configuration Macker
     * 
     * @param pMackerMap les paramètres Macker
     * @param pViewPath la vue
     * @return le fichier de configuration
     * @throws ConfigurationException si erreur
     */
    private File getConfigFile( MapParameterBO pMackerMap, String pViewPath )
        throws ConfigurationException
    {
        File result = new File( "" );
        Map params = pMackerMap.getParameters();
        StringParameterBO filePath = (StringParameterBO) params.get( ParametersConstants.MACKER_CONFIGURATION );
        if ( filePath == null )
        {
            String message = MackerMessages.getString( "macker.exception.no_configuration" );
            LOGGER.warn( message );
            // si le fichier n'est pas renseigné, on annule la tâche.
            mStatus = CANCELLED;
        }
        else
        {
            File configFile = new File( filePath.getValue() );
            // Si le fichier de configuration a un nom absolue et existe, on prend celui-ci
            if ( configFile.isAbsolute() && configFile.exists() )
            {
                result = configFile;
            }
            else
            {
                // Le fichier de configuration est supposé être relatif à la vue
                result = new File( pViewPath, configFile.getPath() );
            }
        }
        return result;
    }
}
