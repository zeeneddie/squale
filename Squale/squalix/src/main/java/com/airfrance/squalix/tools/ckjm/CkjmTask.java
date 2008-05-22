package com.airfrance.squalix.tools.ckjm;

import gr.spinellis.ckjm.ClassMetrics;
import gr.spinellis.ckjm.ClassMetricsContainer;
import gr.spinellis.ckjm.MetricsFilter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.ckjm.CkjmClassMetricsBO;
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.core.TaskException;
import com.airfrance.squalix.core.exception.ConfigurationException;
import com.airfrance.squalix.util.buildpath.BuildProjectPath;
import com.airfrance.squalix.util.file.ExtensionFileFilter;
import com.airfrance.squalix.util.file.FileUtility;
import com.airfrance.squalix.util.parser.JavaParser;
import com.airfrance.squalix.util.repository.ComponentRepository;

/**
 * Tâche Ckjm.<br/> Calcule le CBO (couplage entre les classes) des classes du projet à auditer.<br/> La tâche de
 * compilation java doit avoir été éxécutée avant afin que le chemin du répertoire contenant les ".class" (CLASSES_DIRS)
 * soit présent dans les paramètres temporaires.<br/> La tâche du source manager associé au projet doit avoir été
 * éxécutée avant afin que le chemin de la vue (VIEW_PATH) soit présent dans les paramètres temporaires.<br/> No path
 * in CLASSES_DIRS parameter must contains spaces else ckjm won't work correctly because ckjm considers a classe name
 * with space as a jar file following by the classe name to analyze (see ckjm documentation :
 * http://www.spinellis.gr/sw/ckjm/doc/oper.html)
 */
public class CkjmTask
    extends AbstractTask
{

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( CkjmTask.class );

    /** L'extension d'un fichier java */
    public static final String JAVA_FILE_EXTENSION = ".java";

    /**
     * L'extension d'un fichier java compilé
     */
    public static final String COMPILED_JAVA_FILE_EXTENSION = ".class";

    /** Parser java */
    private JavaParser mParser;

    /** Aide à la création de composants */
    private ComponentRepository mRepository;

    /** La liste des .class à analyser */
    private HashSet mFilesToAnalize;

    /** Le chemin vers la vue du projet */
    private String mViewPath;

    /** Les chemins absolus des sources à auditer */
    private List mSourcesPaths;

    /** Les noms des fichiers qui peuvent être persistés */
    private List mIncludedFileNames;

    // Cette tache n'intervient pas dans le calcul de la taille max du file system

    /**
     * Constructeur par defaut
     */
    public CkjmTask()
    {
        mName = "CkjmTask";
        mFilesToAnalize = new HashSet();
        mIncludedFileNames = new ArrayList();
    }

    /**
     * Analyse les fichiers compilés afin de calculer pour chacun le CBO associé.
     * 
     * @throws TaskException si erreur
     */
    public void execute()
        throws TaskException
    {
        mParser = new JavaParser( mProject );
        mRepository = new ComponentRepository( mProject, getSession() );
        // On initialise les attributs de la tâche
        try
        {
            initialize();
            // On analyse les fichiers compilés
            ClassMetricsContainer cm = new ClassMetricsContainer();
            Iterator filesIt = mFilesToAnalize.iterator();
            while ( filesIt.hasNext() )
            {
                MetricsFilter.processClass( cm, (String) filesIt.next() );
            }
            // On fait persister les mesures pour les classes visitées et qui n'appartiennent pas
            // à l'un des répertoires exclus de la compilation.
            persisteMeasures( cm );
        }
        catch ( Exception e )
        {
            throw new TaskException( e );
        }
    }

    /**
     * Initialise les attributs de la tâche.
     * 
     * @throws ConfigurationException si erreur
     */
    private void initialize()
        throws ConfigurationException
    {
        // On récupère les répertoires contenant les .class du projet à analyser
        // crée par la tâche de compilation java
        List classesDirs = (List) this.getData().getData( TaskData.CLASSES_DIRS );
        if ( classesDirs == null )
        {
            String message = CkjmMessages.getString( "ckjm.exception.classdir.not_found" ) + TaskData.CLASSES_DIRS;
            LOGGER.error( message );
            // Lance une exception de configuration
            throw new ConfigurationException( message );
        }
        // On récupère les chemins relatifs des répertoires contenant les .java du projet
        ListParameterBO sources = (ListParameterBO) mProject.getParameter( ParametersConstants.SOURCES );
        if ( sources == null )
        {
            String message = CkjmMessages.getString( "ckjm.exception.sources.not_found" ) + ParametersConstants.SOURCES;
            LOGGER.error( message );
            // Lance une exception de configuration
            throw new ConfigurationException( message );
        }
        // On récupère le view_path crée par la tâche du source manager en ajoutant un séparateur
        // Unix en bout au cas où.
        mViewPath = (String) mData.getData( TaskData.VIEW_PATH );
        if ( null == mViewPath )
        {
            String message = CkjmMessages.getString( "ckjm.exception.viewpath.not_found" ) + TaskData.VIEW_PATH;
            LOGGER.error( message );
            // Lance une exception de configuration
            throw new ConfigurationException( message );
        }
        // On construit la liste des chemins absolus vers les sources.
        mSourcesPaths = BuildProjectPath.buildProjectPath( mViewPath, sources.getParameters() );
        // On génère la liste des fichiers compilés à analyser
        ExtensionFileFilter filter = new ExtensionFileFilter( COMPILED_JAVA_FILE_EXTENSION );
        for ( int i = 0; i < classesDirs.size(); i++ )
        {
            FileUtility.createRecursiveListOfFiles( new File( (String) classesDirs.get( i ) ), filter, mFilesToAnalize );
        }
        // Les nom des fichiers qui peuvent être persistés
        mIncludedFileNames =
            FileUtility.getIncludedFiles(
                                          mViewPath,
                                          mSourcesPaths,
                                          (ListParameterBO) mProject.getParameter( ParametersConstants.INCLUDED_PATTERNS ),
                                          (ListParameterBO) mProject.getParameter( ParametersConstants.EXCLUDED_PATTERNS ),
                                          null, new String[] { ".java" } );
        // on a pas besoin des répertoires exclus de la compilation car on analyse les .class
    }

    /**
     * Fait persister les mesures.
     * 
     * @param pContainer le containeur des mesures ckjm
     * @throws IOException si erreur de flux
     * @throws JrafDaoException si erreur de persistance
     */
    private void persisteMeasures( ClassMetricsContainer pContainer )
        throws IOException, JrafDaoException
    {
        // On parcours l'ensemble des métriques calculées
        Set entries = pContainer.getEntries();
        CkjmClassMetricsBO ckjmMetric;
        for ( Iterator i = entries.iterator(); i.hasNext(); )
        {
            Map.Entry e = (Map.Entry) i.next();
            ClassMetrics classMetrics = (ClassMetrics) e.getValue();
            if ( classMetrics.isVisited() && ( MetricsFilter.includeAll() || classMetrics.isPublic() ) )
            {
                ckjmMetric = new CkjmClassMetricsBO();
                ckjmMetric.setTaskName( this.getName() );
                ckjmMetric.setAudit( this.getAudit() );
                // Le nom de la classe entièrement qualifié:
                String classNameWithPackage = (String) e.getKey();
                // On récupère le nom relatif du fichier source si le fichier
                // doit être analyser.
                String relativeFileName = isInclude( classNameWithPackage );
                if ( null != relativeFileName )
                { // La classe appartient bien au projet à auditer
                    // On récupère la classe
                    ClassBO classBO = mParser.getClass( classNameWithPackage, relativeFileName );
                    // On fait persister la classe et on construit la mesure ckjm associée
                    ckjmMetric.setComponent( mRepository.persisteComponent( classBO ) );
                    ckjmMetric.setWmc( pContainer.getMetrics( classNameWithPackage ).getWmc() );
                    ckjmMetric.setDit( pContainer.getMetrics( classNameWithPackage ).getDit() );
                    ckjmMetric.setNoc( pContainer.getMetrics( classNameWithPackage ).getNoc() );
                    ckjmMetric.setCbo( pContainer.getMetrics( classNameWithPackage ).getCbo() );
                    ckjmMetric.setRfc( pContainer.getMetrics( classNameWithPackage ).getRfc() );
                    ckjmMetric.setLcom( pContainer.getMetrics( classNameWithPackage ).getLcom() );
                    ckjmMetric.setCa( pContainer.getMetrics( classNameWithPackage ).getCa() );
                    ckjmMetric.setNpm( pContainer.getMetrics( classNameWithPackage ).getNpm() );
                    // On fait persister la mesure
                    MeasureDAOImpl.getInstance().create( getSession(), ckjmMetric );
                    getSession().commitTransactionWithoutClose();
                    getSession().beginTransaction();
                }
            }
        }
    }

    /**
     * Retourne le nom relatif du fichier source de la classe de nom absolu <code>pAbsoluteClassName</code> si la
     * classe fait partie des sources à analyser.
     * 
     * @param pAbsoluteClassName le nom absolu de la classe
     * @return le nom relatif du fichier source de la classe, null si la classe ne doit pas être auditer.
     * @throws IOException si erreur
     */
    private String isInclude( String pAbsoluteClassName )
        throws IOException
    {
        String relativeFileName = null;
        // On vérifie que la classe fait partie des fichiers
        String packageName = mParser.getAbsolutePackage( pAbsoluteClassName );
        // On récupère le nom absolu du fichier compilé associé à la classe
        // NB: On ne vérifie pas si le nom retourné est nul car c'est impossible
        String classFileName = FileUtility.getClassFileName( mFilesToAnalize, pAbsoluteClassName );
        // Puis le nom du fichier source dans lequel se trouve la définition de la classe
        String fileName = FileUtility.getFileName( classFileName );
        // On construit le chemin relatif du fichier source de la classe
        String endOfFileName = FileUtility.buildEndOfFileName( packageName, fileName );
        // On récupère le nom absolu du fichier source si il est présent
        // dans les paramètres.
        String absoluteFileName = FileUtility.getAbsoluteFileName( mSourcesPaths, endOfFileName );
        if ( mIncludedFileNames.contains( absoluteFileName ) )
        {
            relativeFileName = FileUtility.getRelativeFileName( absoluteFileName, mViewPath );
        }
        return relativeFileName;
    }
}
