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
package com.airfrance.squalix.tools.mccabe;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAClassMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAJspMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAMethodMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAProjectMetricsBO;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.util.buildpath.BuildProjectPath;
import com.airfrance.squalix.util.csv.CSVParser;
import com.airfrance.squalix.util.file.FileUtility;
import com.airfrance.squalix.util.file.ToolsReportsUtility;
import com.airfrance.squalix.util.parser.LanguageParser;
import com.airfrance.squalix.util.repository.ComponentRepository;

/**
 * Objet de persistence des métriques McCabe pour les langages par objet (Java, C++, etc.).
 */
public class OOMcCabePersistor extends McCabePersistor
{

    /** L'extension d'une page jsp */
    public static final String JSP_EXTENSION = ".jsp";

    /** Signature McCabe de la méthode d'une JSP */
    public static final String JSP_METHOD_REGEXP = "._jspService\\(HttpServletRequest,HttpServletResponse\\)";

    /**
     * Metriques des JSP
     */
    private Map mJspResults = new HashMap();

    /**
     * Nombre de méthodes
     */
    private int mNumberOfMethods = 0;

    /**
     * Nombre de classes
     */
    private int mNumberOfClasses = 0;

    /**
     * Adaptateur
     */
    private OOMcCabeAdaptator mAdaptator;

    /**
     * Chemin du fichier à parser
     */
    private String mReportFileName = null;

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( McCabePersistor.class );

    /** le parser */
    private LanguageParser mParser;

    /** Le template pour le rapport de classe */
    private String mClassTemplate;

    /** Les chemins vers les JSPs si il y en a */
    private List mJspPaths;

    /**
     * Constructeur.
     * 
     * @param pParser le parser.
     * @param pConfiguration configuration du framework.
     * @param pAudit audit encadrant l'exécution.
     * @param pSession la session de persistance utilisée par la tâche.
     * @param pDatas la liste des paramètres temporaires du projet
     * @param pTaskName le nom de la tache (pour différencier java et cpp)
     * @param pClassTemplate le template à utiliser
     * @throws JrafDaoException si une session de peristance ne peut être créée.
     */
    public OOMcCabePersistor( LanguageParser pParser, final McCabeConfiguration pConfiguration, final AuditBO pAudit,
                            final ISession pSession, TaskData pDatas, String pTaskName, String pClassTemplate )
        throws JrafDaoException
    {
        super(pSession,pDatas,pTaskName,pAudit,pConfiguration);
        mParser = pParser;
        ComponentRepository repository = new ComponentRepository( mProject, mSession );
        mAdaptator = new OOMcCabeAdaptator( pParser, repository );
        mClassTemplate = pClassTemplate;
        ListParameterBO jsps = (ListParameterBO) mProject.getParameter( ParametersConstants.JSP );
        if ( null != jsps )
        { // Il s'agit d'un projet J2EE
            mJspPaths =
                BuildProjectPath.buildProjectPath( (String) pDatas.getData( TaskData.VIEW_PATH ), jsps.getParameters() );
        }
    }

    /**
     * Parse le rapport des métriques de classe.
     * 
     * @param pFilename chemin du fichier rapport.
     * @throws Exception si un problème de parsing apparait.
     * @return le nombre de résultats de niveau classe
     * @roseuid 42B976100269
     */
    public int parseClassReport( final String pFilename )
        throws Exception
    {
        mReportFileName = pFilename;
        LOGGER.info( McCabeMessages.getString( "logs.debug.report_parsing_class" ) + mReportFileName );
        // Récupérer les beans issus du rapport de classes McCabe
        CSVParser csvparser = new CSVParser( McCabeMessages.getString( "csv.config.file" ) );
        Collection classResults = csvparser.parse( McCabeMessages.getString( mClassTemplate ), pFilename );
        // Ajout de la volumétrie dans les beans
        Iterator it = classResults.iterator();
        McCabeQAClassMetricsBO bo = null;
        while ( it.hasNext() )
        {
            // On adapte chaque bean issu du rapport
            bo = (McCabeQAClassMetricsBO) it.next();
            bo.setAudit( mAudit );
            bo.setTaskName( mTaskName );
            // On nettoie le nom du composant si celui-ci a été mal remonté du rapport,
            // en effet, quand le séparateur de valeur est la virgule et qu'une valeur contient
            // une virgule, alors cette valeur est placée entre guillemets
            bo.setComponentName( ToolsReportsUtility.clearReportName( bo.getComponentName() ) );
            // Le bean est adapté, c'est-à-dire qu'on le lie à son composant
            // Si il s'agit d'une page JSP, on a stocker une partie de
            // ses résulats dans la map mJspResults
            McCabeQAJspMetricsBO jspMetrics = (McCabeQAJspMetricsBO) mJspResults.get( bo.getComponentName() );
            boolean isJSP = ( null != jspMetrics );
            if ( isJSP )
            {
                // On ne fait pas persister le bo en tant que Classe
                it.remove();
                jspMetrics.setClassMetrics( bo );
                if ( null != jspMetrics.getComponent() )
                {
                    mJspResults.put( bo.getComponentName(), jspMetrics );
                }
            }
            else
            {
                // Sinon on l'adapte à une classe
                mNumberOfClasses++;
                mAdaptator.adaptClassResult( bo );
            }
            if ( null == bo.getComponent() && !isJSP )
            {
                // La classe n'a pu persister, on ne fait pas persister
                // le bo et on lance un warning
                it.remove();
                LOGGER.warn( McCabeMessages.getString( "logs.warn.incorrect_class_name", bo.getComponentName() ) );
            }
        }
        LOGGER.info( McCabeMessages.getString( "logs.debug.report_parsing_database" ) );
        // On sauvegarde le mesures sur les classes
        MeasureDAOImpl.getInstance().saveAll( mSession, classResults );
        // On sauvegarde le mesures sur les JSP si il y en a
        MeasureDAOImpl.getInstance().saveAll( mSession, mJspResults.values() );
        mSession.commitTransactionWithoutClose();
        mSession.beginTransaction();
        LOGGER.info( McCabeMessages.getString( "logs.debug.report_parsing_end" ) );
        return classResults.size();
    }

    /**
     * Parse le rapport des métriques de méthodes.
     * 
     * @param pFilename chemin du fichier rapport.
     * @param pDatas les données temporaires
     * @throws Exception si un problème de parsing apparaît.
     * @return le nombre de résultats de niveau méthode récupérés.
     * @roseuid 42B9761F015F
     */
    public int parseMethodReport( final String pFilename, TaskData pDatas )
        throws Exception
    {
        mReportFileName = pFilename;
        LOGGER.info( McCabeMessages.getString( "logs.debug.report_parsing_method" ) + mReportFileName );
        // Récupérer les beans issus du rapport de méthodes McCabe
        CSVParser csvparser = new CSVParser( McCabeMessages.getString( "csv.config.file" ) );
        Collection methodResults = csvparser.parse( McCabeMessages.getString( "csv.template.method" ), pFilename );

        McCabeQAMethodMetricsBO bo = null;
        String name = null;
        int count = 0;
        // On crée une nouvelle collection contenant les beans à faire effectivement persister
        List resultsToPersist = new ArrayList();
        // Adaptation des beans
        Iterator it = methodResults.iterator();
        while ( it.hasNext() )
        {
            bo = (McCabeQAMethodMetricsBO) it.next();
            it.remove();
            name = bo.getComponentName();
            // On nettoie le nom du composant si celui-ci a été mal remonté du rapport,
            // en effet, quand le séparateur de valeur est la virgule et qu'une valeur contient
            // une virgule, alors cette valeur est placée entre guillemets
            name = ToolsReportsUtility.clearReportName( name );
            bo.setComponentName( name );
            String className = mParser.getParentName( name );
            // On vérifie que la méthode n'est pas contenue dans les classes à exclure
            // Et que la classe n'est pas nulle (sinon c'est signe d'une défaillance McCabe)
            bo.setAudit( mAudit );
            String absoluteFileName = bo.getFilename();
            // Le nom du fichier est mis en relatif par rapport à la racine du projet
            String viewPath = (String) pDatas.getData( TaskData.VIEW_PATH );
            bo.setFilename( FileUtility.getRelativeFileName( absoluteFileName, viewPath ) );
            bo.setTaskName( mTaskName );
            // Le bean est adapté, c'est-à-dire qu'on le lie à son composant
            // A un composant JSP si le nom du fichier se termine par .jsp
            if ( bo.getFilename().endsWith( JSP_EXTENSION ) && null != mJspPaths )
            {
                String componentName = bo.getComponentName().replaceFirst( JSP_METHOD_REGEXP, "" );
                // On nettoie le nom pour enlever le "_jsp"
                bo.setComponentName( ToolsReportsUtility.clearJspName( componentName ) );
                McCabeQAJspMetricsBO jspMetrics = new McCabeQAJspMetricsBO( bo );
                // On récupère le répertoire racine contenant les JSP
                File rootDir = null;
                String relativeRootDir = null;
                int id = 0;
                for ( int i = 0; i < mJspPaths.size() && null == relativeRootDir; i++ )
                {
                    rootDir = new File( (String) mJspPaths.get( i ) );
                    if ( null != FileUtility.findFileWithPathSuffix( rootDir, absoluteFileName ) )
                    {
                        relativeRootDir = FileUtility.getRelativeFileName( rootDir.getAbsolutePath(), viewPath );
                        id = i;
                    }
                }
                if ( null != relativeRootDir )
                {
                    mAdaptator.adaptJspResult( jspMetrics, relativeRootDir, id );
                }
                if ( null == jspMetrics.getComponent() )
                {
                    // On lance un warning car le composant n'a pu être parsé
                    LOGGER.warn( McCabeMessages.getString( "logs.warn.incorrect_jsp_method_name",
                                                           jspMetrics.getComponentName() ) );
                }
                else
                {
                    // On enregistre le nom du composant avant la suppression du "_jsp"
                    // pour pouvoir comparer correctement.
                    mJspResults.put( componentName, jspMetrics );
                }
            }
            else
            {
                // A une méthode java
                mAdaptator.adaptMethodResult( bo );
                if ( null == bo.getComponent() )
                {
                    // On lance un warning car le composant n'a pu être parsé
                    LOGGER.warn( McCabeMessages.getString( "logs.warn.incorrect_method_name", bo.getComponentName() ) );
                }
                else
                {
                    mNumberOfMethods++;
                    count++;
                    resultsToPersist.add( bo );
                }
            }
        } // La collection des résultats de méthodes est persistée
        LOGGER.info( McCabeMessages.getString( "logs.debug.report_parsing_database" ) );
        MeasureDAOImpl.getInstance().saveAll( mSession, resultsToPersist );
        mSession.commitTransactionWithoutClose();
        mSession.beginTransaction();
        LOGGER.info( McCabeMessages.getString( "logs.debug.report_parsing_end" ) );
        return methodResults.size();
    }

    /**
     * Crée et fait persister les résultats de niveau projet.
     * 
     * @roseuid 42E09E5201AB
     */
    public void persistProjectResult()
    {
        LOGGER.info( McCabeMessages.getString( "logs.debug.project_database" ) );
        McCabeQAProjectMetricsBO metrics = new McCabeQAProjectMetricsBO();
        // Création des métriques de niveau projet
        metrics.setComponent( mProject );
        metrics.setAudit( mAudit );
        metrics.setTaskName( mTaskName );
        metrics.setNumberOfClasses( new Integer( mNumberOfClasses ) );
        metrics.setNumberOfMethods( new Integer( mNumberOfMethods ) );
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
