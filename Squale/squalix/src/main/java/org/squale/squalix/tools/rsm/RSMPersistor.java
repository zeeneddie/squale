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
package org.squale.squalix.tools.rsm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.result.MeasureDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.rsm.RSMClassMetricsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.rsm.RSMMethodMetricsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.rsm.RSMProjectMetricsBO;
import org.squale.squalix.core.TaskData;
import org.squale.squalix.util.csv.CSVParser;
import org.squale.squalix.util.parser.LanguageParser;
import org.squale.squalix.util.repository.ComponentRepository;

/**
 * Objet chargé de faire persister les résultats RSM
 */
public class RSMPersistor
{

    /** Le template pour le rapport de classe */
    private String mClassTemplate = "csv.template.class";

    /** Le template pour le rapport de classe */
    private String mMethodTemplate = "csv.template.method";

    /**
     * Nombre de méthodes
     */
    private int mNumberOfMethods = 0;

    /**
     * Nombre de classes
     */
    private int mNumberOfClasses = 0;

    /**
     * Nombre de lignes de commentaires sur le projet
     */
    private int mComments = 0;

    /**
     * Nombre de lignes de commentaires sur le projet
     */
    private int mSLOC = 0;

    /** le nom de la tache réel, pour différencier java et cpp */
    private String mTaskName;

    /**
     * Configuration
     */
    private RSMConfiguration mConfiguration;

    /**
     * Chemin du fichier à parser
     */
    private String mReportFileName = null;

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( RSMPersistor.class );

    /**
     * Audit durant lequel l'analyse est effectuée
     */
    private AuditBO mAudit = null;

    /**
     * Projet sur lequel est réalisée l'analyse.
     */
    private ProjectBO mProject = null;

    /**
     * Session Persistance
     */
    private ISession mSession = null;

    /** les paramètres temporaires */
    private TaskData mDatas;

    /** le parser CSV */
    private CSVParser mParser;

    /** l'adaptateur permettant de relier les résultats RSM au composants associés */
    private RSMAdaptator mAdaptator;

    /** pour faciliter le stockage */
    private ComponentRepository mRepository;

    /** Le marqueur pour préprocesser le rapport généré et en extraire les métriques de classes */
    private final static String CLASS_LOCATOR = "Class,";

    /** Le marqueur pour préprocesser le rapport généré et en extraire les métriques de méthodes */
    private final static String METHOD_LOCATOR = "Function,";

    /** Marqueur pour signaler la fin de la zone doublon */
    private final static String FILE_LOCATOR = "File,";

    /** Marqueur pour signaler le début de la zone doublon */
    private final static String STOP_WRITE = "ProjectFunctionMetrics";

    /** Marqueur pour signaler la fin de la zone doublon */
    private final static String RESTART_WRITE = "ProjectClassMetrics";
    
    /**
     * Chaîne contenant l'expression régulière permettant de effacer les paramètres 3 à 5 de la ligne méthode
     */
    private String mREGEXPREMOVEPARAMS = ",[^,]*\\([^,]*\\),[^,]*,[^,]*";
    
    /**
     * Chaîne contenant l'expression régulière permettant de effacer les paramètres function point attaché aux champs LOC, ELOC, LLOC
     */    
    private String mREGEXPREMOVEFUNCTIONPOINT = ",([^,]*)/[^,]*";

    /**
     * Constructeur.
     * 
     * @param pConfiguration configuration du framework.
     * @param pAudit audit encadrant l'exécution.
     * @param pSession la session de persistance utilisée par la tâche.
     * @param pDatas la liste des paramètres temporaires du projet
     * @param pTaskName le nom de la tache (pour différencier java et cpp)
     * @param pLanguageParser le parser de language pour persister les données
     * @throws JrafDaoException si une session de peristance ne peut être créée.
     */
    public RSMPersistor( final RSMConfiguration pConfiguration, final AuditBO pAudit, final ISession pSession,
                         TaskData pDatas, String pTaskName, LanguageParser pLanguageParser )
        throws JrafDaoException
    {
        mSession = pSession;
        mConfiguration = pConfiguration;
        mAudit = pAudit;
        mDatas = pDatas;
        mProject = pConfiguration.getProject();
        mRepository = new ComponentRepository( mProject, mSession );
        mTaskName = pTaskName;
        mAdaptator = new RSMAdaptator( pLanguageParser, mRepository );
    }

    /**
     * Parse le rapport pour obtenir tous les métriques
     * 
     * @param pFileName chemin du fichier rapport.
     * @param pDatas les données
     * @throws Exception si un problème de parsing apparait.
     * @return le nombre de métriques
     */
    public int parseReport( final String pFileName, TaskData pDatas )
        throws Exception
    {
        mReportFileName = pFileName;
        LOGGER.info( RSMMessages.getString( "logs.debug.report_parsing_class" ) + mReportFileName );
        mParser = new CSVParser( RSMMessages.getString( "csv.config.file" ) );

        // effecue les différents préprocessing
        managePreProcess( pFileName );

        int nbClassResults = parseReportForClassMetrics( mConfiguration.getClassReportPath() );
        int nbMethodsResults = parseMethodReport( mConfiguration.getMethodsReportPath(), pDatas );
        return nbClassResults + nbMethodsResults;
    }

    /**
     * Effectur les différents préprocess nécessaires
     * 
     * @param pFileName le nom du fichier généré par RSM
     * @throws IOException en cas de problemes lors du pré-processing
     */
    private void managePreProcess( String pFileName )
        throws IOException
    {
        // On effectue un préprocessing du fichier résultat car le format CSV renvoyé n'est pas vraiment du CSV,
        // Il y a des lignes supplémentaires et des informations génantes pour le parsing
        eraseDouble( pFileName, mConfiguration.getAuxFile() );
        preProcess( mConfiguration.getClassReportPath(), mConfiguration.getAuxFile(), CLASS_LOCATOR );
        preProcess( mConfiguration.getMethodsReportPath(), mConfiguration.getAuxFile(), METHOD_LOCATOR );
    }

    /**
     * @param pCSVOutputFile nom du fichier parsé pour le mettre au format CSV correct
     * @param pCSVOutputFileAux nom du fichier auxilliaire utilisé pour supprimer les doublons
     * @param pMarker la chaine repère
     * @throws IOException en cas de problemes avec le préprocessing du fichier
     */
    private void preProcess( String pCSVOutputFile, String pCSVOutputFileAux, String pMarker )
        throws IOException
    {
        // Ecrit a partir du deuxième fichier
        File f = new File( pCSVOutputFile );
        BufferedWriter bw = new BufferedWriter( new FileWriter( pCSVOutputFile ) );
        BufferedReader br = new BufferedReader( new FileReader( pCSVOutputFileAux ) );
        String line = "";
        List results = new ArrayList( 0 );
        while ( line != null )
        {
            line = br.readLine();
            // Null signifie fin de fichier
            if ( line != null )
            {
                // Si on a trouvé une ligne commencant par le repère passé en paramètre, on la récupère
                // Pour plus de sureté on vérifie également que la ligne contient une virgule
                // Traitement différent suivant les classes ou les fichiers, pour les classes on ne s'occupe
                // pas du nom du fichier, pour les méthodes oui
                if ( pMarker.equals( CLASS_LOCATOR ) )
                {
                    if ( isValidCSVLine( line, pMarker ) )
                    {
                        bw.write( line + "\n" );
                    }
                }
                else
                {
                    if ( isValidCSVLine( line, pMarker ) )
                    {
                        results.add( line );
                    }
                    else if ( isFileLine( line ) )
                    {
                        // On est sur la ligne indiquant le fichier dans lequel se trouve tous les éléments
                        // obtenus aux lignes précédentes
                        // on écrit les résultats précédents avec le nom du fichier en plus
                        StringTokenizer st = new StringTokenizer( line, "," );
                        String fileName = "";
                        int counter = 0;
                        while ( st.hasMoreElements() && counter < 2 )
                        {
                            fileName = st.nextToken().trim();
                            counter++;
                        }
                        // on écrit la ligne
                        for ( int i = 0; i < results.size(); i++ )
                        {
                        	// efface le retour à la ligne
                            String result = ( (String) ( results.get( i ) ) ).replaceAll( "\n", "" );

                            // efface paramètres 3 à 5 et function points
                            result = result.replaceFirst(mREGEXPREMOVEPARAMS, "").replaceAll(mREGEXPREMOVEFUNCTIONPOINT, ",$1");
                            
                            // Dans ce cas il faut rajouter un " " sinon le parser ne tient pas compte de la colonne
                            if ( result.charAt( result.length() - 1 ) != ' ' )
                            {                        
                                bw.write( result + " ," + fileName + "\n" );
                            }
                            else
                            {
                                bw.write( result + "," + fileName + "\n" );
                            }
                        }
                        // reset la liste des résultats
                        results = new ArrayList( 0 );
                    }
                }
            }
        }
        // ferme les buffers
        bw.close();
        br.close();

    }

    /**
     * @param pLine la ligne courante
     * @return true si la ligne commence par le marqueur de ligne fichier
     */
    private boolean isFileLine( String pLine )
    {
        return pLine.startsWith( FILE_LOCATOR );
    }

    /**
     * @param pFilename le nom du fichier généré par rsm
     * @param pOutputFileAux nom du fichier auxilliaire utilisé pour supprimer les doublons
     * @throws IOException en cas d'échec de lecture
     */
    private void eraseDouble( String pFilename, String pOutputFileAux )
        throws IOException
    {
        BufferedWriter bw = new BufferedWriter( new FileWriter( pOutputFileAux ) );
        BufferedReader br = new BufferedReader( new FileReader( pFilename ) );
        String line = "";
        // Supprime les lignes redondantes du à l'utilisation (nécessaire)
        // de deux options donnant en partie le meme résultat
        boolean write = true;
        while ( line != null )
        {
            line = br.readLine();
            // Null signifie fin de fichier
            if ( line != null )
            {
                // on enlève tous les espaces (il n'est pas censé y avoir d'espaces dans un fichier CSV)
                // et dans le fichier renvoyé par RSM il y en a un peu partout
                // Toutefois on enlève pas l'espace qui est entre deux virgules sinon le parser ne compte pas la colonne
                String properLine = "";
                for ( int i = 0; i < line.length(); i++ )
                {
                    if ( line.charAt( i ) != ' '
                        || ( line.charAt( i ) == ' ' && i > 0 && i < ( line.length() - 1 )
                            && line.charAt( i - 1 ) == ',' && line.charAt( i + 1 ) == ',' ) )
                    {
                        properLine += line.charAt( i );
                    }
                }
                // Si on a trouvé une ligne commencant par le repère passé en paramètre, on la récupère
                // Pour plus de sureté on vérifie également que la ligne contient une virgule
                if ( properLine.startsWith( STOP_WRITE ) )
                {
                    write = false;
                }
                else
                {
                    if ( properLine.startsWith( RESTART_WRITE ) )
                    {
                        write = true;
                    }
                }
                if ( write && isValidCSVLine( properLine ) )
                {
                    bw.write( properLine + "\n" );
                }
            }
        }
        // ferme les buffers
        bw.close();
        br.close();
    }

    /**
     * Effectue un test plus général sans marqueur Sert pour l'étape 1 du pré-processing
     * 
     * @param pLine la ligne à tester
     * @return true si la ligne est valide pour préprocessing CSV
     */
    private boolean isValidCSVLine( String pLine )
    {
        // On vérifie que la ligne comporte les éléments nécessaire à l'analyse CSV
        // Il faut une virgule
        return commonValidCVSLine( pLine )
            && ( pLine.startsWith( CLASS_LOCATOR ) || pLine.startsWith( FILE_LOCATOR ) || pLine.startsWith( METHOD_LOCATOR ) );
    }

    /**
     * Effectue les tests de préprocessing pour récupérer les données qui interressent en fonction du marqueur Sert pour
     * l'étape 2 du pré-processing
     * 
     * @param pLine la ligne à tester
     * @param pMarker le repère
     * @return true si la ligne est valide pour analyse CSV
     */
    private boolean isValidCSVLine( String pLine, String pMarker )
    {
        // On vérifie que la ligne comporte les éléments nécessaire à l'analyse CSV
        // Il faut une virgule
        return commonValidCVSLine( pLine )
        // Les contraintes sur la taille
            && pLine.length() > pMarker.length() && pMarker.equals( pLine.substring( 0, pMarker.length() ) );
    }

    /**
     * Permet de traiter les conditions communes qu'une ligne doit avoir pour être analysée par CSV
     * 
     * @param pLine la ligne à vérifier
     * @return true si la ligne est partiellement valide pour préprocessing CSV
     */
    private boolean commonValidCVSLine( String pLine )
    {
        // On vérifie que la ligne comporte les éléments nécessaire à l'analyse CSV
        // Il faut une virgule
        return pLine.indexOf( "," ) != -1
            // Les mots clés interdits
            && pLine.indexOf( "Average" ) == -1 && pLine.indexOf( "Total" ) == -1 && pLine.indexOf( "Maximum" ) == -1
            && pLine.indexOf( "Minimum" ) == -1;
    }

    /**
     * Parse le rapport pour obtenir les métriques de classe.
     * 
     * @param pFilename chemin du fichier rapport.
     * @throws Exception si un problème de parsing apparait.
     * @return le nombre de résultats de niveau classe
     * @roseuid 42B976100269
     */
    private int parseReportForClassMetrics( final String pFilename )
        throws Exception
    {
        // Récupérer les beans issus du rapport de classes RSM
        Collection classResults = mParser.parse( RSMMessages.getString( mClassTemplate ), pFilename );
        // On ne compte que les classes qui ont une logique
        Collection classToPersist = new ArrayList( 0 );
        // Ajout de la volumétrie dans les beans
        Iterator it = classResults.iterator();
        RSMClassMetricsBO bo = null;
        while ( it.hasNext() )
        {
            // On adapte chaque bean issu du rapport
            bo = (RSMClassMetricsBO) it.next();
            // On ne compte que les classes qui ont une logique, c'est à dire les classes ayant des méthodes
            if ( ( bo.getPublicData().intValue() + bo.getProtectedData().intValue() + bo.getPrivateData().intValue() ) > 0 )
            {
                // rattache le bo rsm à la classe associée
                mAdaptator.adaptClassResult( bo );
                // on ajoute pour les données projets les données concernant la classe courante
                // au niveau des commentaires et du nombre de lignes de code
                mSLOC += bo.getSloc().intValue();
                mComments += bo.getComments().intValue();
                bo.setAudit( mAudit );
                mNumberOfClasses++;
                bo.setTaskName( mTaskName );
                classToPersist.add( bo );
            }
        }
        LOGGER.info( RSMMessages.getString( "logs.debug.report_parsing_database" ) );
        // On sauvegarde le mesures sur les classes
        MeasureDAOImpl.getInstance().saveAll( mSession, classToPersist );
        mSession.commitTransactionWithoutClose();
        mSession.beginTransaction();
        LOGGER.info( RSMMessages.getString( "logs.debug.report_parsing_end" ) );
        return classResults.size();
    }

    /**
     * Parse le rapport pour obtenir les métriques de méthodes
     * 
     * @param pFilename chemin du fichier rapport.
     * @param pDatas les données temporaires
     * @throws Exception si un problème de parsing apparaît.
     * @return le nombre de résultats de niveau méthode récupérés.
     * @roseuid 42B9761F015F
     */
    private int parseMethodReport( final String pFilename, TaskData pDatas )
        throws Exception
    {
        // Récupérer les beans issus du rapport de méthodes RSM
        Collection methodResults = mParser.parse( RSMMessages.getString( mMethodTemplate ), pFilename );
        RSMMethodMetricsBO bo = null;
        String name = null;
        // On crée une nouvelle collection contenant les beans à faire effectivement persister
        List resultsToPersist = new ArrayList();
        // Adaptation des beans
        Iterator it = methodResults.iterator();
        while ( it.hasNext() )
        {
            bo = (RSMMethodMetricsBO) it.next();
            // rattache le bo rsm à la classe associée
            bo.setAudit( mAudit );
            // Le nom du fichier est mis en relatif par rapport à la racine du projet
            String completFileName = bo.getFileName();
            if (completFileName.indexOf( "vobs" ) != -1) {
            	String fileName = completFileName.substring( completFileName.indexOf( "vobs" ), completFileName.length() );
            	bo.setFileName( fileName );
        	} else {
        		bo.setFileName( completFileName );
        	}
            bo.setTaskName( mTaskName );
            mNumberOfMethods++;
            // Problème RSM avec le polymorphisme, si plusieurs méthodes de meme nom présentes
            // dans le fichier on ne les sauvegarde pas
            boolean canAdd = mAdaptator.adaptMethodResult( bo );
            if ( canAdd )
            {
                resultsToPersist.add( bo );
            }
        } // La collection des résultats de méthodes est persistée
        LOGGER.info( RSMMessages.getString( "logs.debug.report_parsing_database" ) );
        MeasureDAOImpl.getInstance().saveAll( mSession, resultsToPersist );
        mSession.commitTransactionWithoutClose();
        mSession.beginTransaction();
        LOGGER.info( RSMMessages.getString( "logs.debug.report_parsing_end" ) );
        return methodResults.size();
    }

    /**
     * Crée et fait persister les résultats de niveau projet.
     * 
     * @roseuid 42E09E5201AB
     */
    public void persistProjectResult()
    {
        LOGGER.info( RSMMessages.getString( "logs.debug.project_database" ) );
        RSMProjectMetricsBO metrics = new RSMProjectMetricsBO();
        // Création des métriques de niveau projet
        metrics.setComponent( mProject );
        metrics.setAudit( mAudit );
        metrics.setTaskName( mTaskName );
        metrics.setNumberOfClasses( new Integer( mNumberOfClasses ) );
        metrics.setNumberOfMethods( new Integer( mNumberOfMethods ) );
        metrics.setSloc( new Integer( mSLOC ) );
        metrics.setComments( new Integer( mComments ) );
        try
        {
            MeasureDAOImpl.getInstance().create( mSession, metrics );
        }
        catch ( JrafDaoException e )
        {
            LOGGER.error( e, e );
        }
    }
}