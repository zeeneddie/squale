package com.airfrance.squalix.tools.macker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionItemBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;
import com.airfrance.squalecommon.enterpriselayer.facade.macker.RuleFactory;
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskData;

/**
 * Test de la tâche Macker pour un projet J2ee
 */
public class J2eeMackerTaskTest
    extends SqualeTestCase
{

    /** répertoire des .class */
    public static final String CLASSES_DIR = "data/samples/bin_for_test";

    /** répertoire des .java */
    public static final String SOURCES_DIR = "data/samples/testWeb/JavaSource";

    /** répertoire des .class */
    public static final String JSP_CLASSES_DIR = "data/samples/testWeb/WebContent/jsp_classes";

    /** répertoire des .java */
    public static final String JSP_SOURCES_DIR = "data/samples/testWeb/WebContent/jsp";

    /**
     * Pseudo-chemin vers une vue.
     */
    private static final String VIEW_PATH = ".";

    /** chemin du fichier de configuration Macker */
    public static final String CONFIG_PATH = "data/samples/testWeb/macker.xml";

    /** le nombre .class dans le répertoire */
    public static final int NB_CLASSES = 1;

    /** le nombre d'erreur trouvées pour la classe */
    public static final int NB_ERRORS = 1;

    /** l'application */
    private ApplicationBO mAppli = new ApplicationBO();

    /** le projet à auditer */
    private ProjectBO mProject = new ProjectBO();

    /** l'audit */
    private AuditBO mAudit = new AuditBO();

    /** les tâches temporaires */
    private TaskData mData = new TaskData();

    /**
     * Constructeur pour MackerTaskTest.
     * 
     * @param arg0 nom
     */
    public J2eeMackerTaskTest( String arg0 )
    {
        super( arg0 );

    }

    /**
     * @see TestCase#setUp()
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        getSession().beginTransaction();
        mAppli = getComponentFactory().createApplication( getSession() );
        QualityGridBO grid = getComponentFactory().createGrid( getSession() );
        mProject = getComponentFactory().createProject( getSession(), mAppli, grid );
        // Enregistrement du ProjectBO dans la base
        ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
        // Les paramètres doivent contenir le chemin du fichier de configuration
        MapParameterBO params = new MapParameterBO();
        MapParameterBO mackerParams = new MapParameterBO();
        Map mackerMap = new HashMap();
        StringParameterBO filePath = new StringParameterBO();
        filePath.setValue( CONFIG_PATH );
        mackerMap.put( ParametersConstants.MACKER_CONFIGURATION, filePath );
        mackerParams.setParameters( mackerMap );
        params.getParameters().put( ParametersConstants.MACKER, mackerParams );
        ListParameterBO listParam = new ListParameterBO();
        List list = new ArrayList();
        StringParameterBO stringParam = new StringParameterBO();
        stringParam.setValue( SOURCES_DIR );
        list.add( stringParam );
        listParam.setParameters( list );
        params.getParameters().put( ParametersConstants.SOURCES, listParam );
        ListParameterBO listParam2 = new ListParameterBO();
        List list2 = new ArrayList();
        StringParameterBO stringParam2 = new StringParameterBO();
        stringParam2.setValue( JSP_SOURCES_DIR );
        list2.add( stringParam2 );
        listParam2.setParameters( list2 );
        params.getParameters().put( ParametersConstants.JSP, listParam2 );
        ProjectParameterDAOImpl.getInstance().create( getSession(), params );
        mProject.setParameters( params );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        mAudit = getComponentFactory().createAudit( this.getSession(), mProject );
        // On fait le commit pour permettre l'accès aux données dans une autre session
        getSession().commitTransactionWithoutClose();
        mData.putData( TaskData.JSP_CLASSES_DIR, JSP_CLASSES_DIR );
        List classesDirs = new ArrayList();
        classesDirs.add( CLASSES_DIR );
        mData.putData( TaskData.CLASSES_DIRS, classesDirs );
        File viewFile = new File( VIEW_PATH );
        mData.putData( TaskData.VIEW_PATH, viewFile.getCanonicalPath() );
    }

    /**
     * Vérifie la correcte exécution de la tâche Macker.
     * 
     * @throws JrafDaoException si erreur
     * @throws IOException si erreur
     */
    public void testRun()
        throws JrafDaoException, IOException
    {
        J2eeMackerTask task = new J2eeMackerTask();
        task.setData( mData );
        task.setAuditId( new Long( mAudit.getId() ) );
        task.setProjectId( new Long( mProject.getId() ) );
        task.setApplicationId( new Long( mAppli.getId() ) );
        task.setStatus( AbstractTask.NOT_ATTEMPTED );
        task.run();
        assertEquals( AbstractTask.TERMINATED, task.getStatus() );
        try
        {
            // On vérifie les résultats
            MeasureDAOImpl dao = MeasureDAOImpl.getInstance();
            assertEquals( 1, dao.count( getSession() ).intValue() );
            // On récupère la mesure sauvée
            Collection measures = dao.findAll( getSession() );
            assertEquals( 1, measures.size() );
            RuleCheckingTransgressionBO measure = (RuleCheckingTransgressionBO) measures.iterator().next();
            assertEquals( 1, measure.getMetrics().size() );
            int size = measure.getMetrics().size();
            measure.getMetrics().entrySet();
            Collection details = measure.getDetails();
            assertEquals( NB_ERRORS, details.size() );
            AbstractComponentBO c = null;
            for ( Iterator it = details.iterator(); it.hasNext(); )
            {
                RuleCheckingTransgressionItemBO item = (RuleCheckingTransgressionItemBO) it.next();
                RuleBO rule = item.getRule();
                String message = item.getMessage();
                assertEquals( RuleFactory.SEVERITY, rule.getSeverity() );
            }
            assertEquals( "architecture JRAF", measure.getRuleSet().getName() );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Vérifie la correcte exécution de la tâche Macker.
     * 
     * @throws JrafDaoException si erreur
     * @throws IOException si erreur
     */
    public void testRunWithExclusion()
        throws JrafDaoException, IOException
    {
        // On crée les exclusion
        getSession().beginTransaction();
        MapParameterBO params = mProject.getParameters();

        // Les patterns à exclure
        ListParameterBO excludedList = new ListParameterBO();
        ArrayList eList = new ArrayList();
        StringParameterBO stringExclude = new StringParameterBO();
        stringExclude.setValue( "**/page*/**" );
        eList.add( stringExclude );
        excludedList.setParameters( eList );
        params.getParameters().put( ParametersConstants.EXCLUDED_PATTERNS, excludedList );

        ProjectParameterDAOImpl.getInstance().create( getSession(), params );
        mProject.setParameters( params );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        getSession().commitTransactionWithoutClose();

        J2eeMackerTask task = new J2eeMackerTask();
        task.setData( mData );
        task.setAuditId( new Long( mAudit.getId() ) );
        task.setProjectId( new Long( mProject.getId() ) );
        task.setApplicationId( new Long( mAppli.getId() ) );
        task.setStatus( AbstractTask.NOT_ATTEMPTED );
        task.run();
        assertEquals( AbstractTask.TERMINATED, task.getStatus() );
        try
        {
            // On vérifie les résultats
            MeasureDAOImpl dao = MeasureDAOImpl.getInstance();
            assertEquals( 1, dao.count( getSession() ).intValue() );
            // On récupère la mesure sauvée
            Collection measures = dao.findAll( getSession() );
            assertEquals( 1, measures.size() );
            RuleCheckingTransgressionBO measure = (RuleCheckingTransgressionBO) measures.iterator().next();
            assertEquals( 1, measure.getMetrics().size() );
            int size = measure.getMetrics().size();
            measure.getMetrics().entrySet();
            Collection details = measure.getDetails();
            // Pas de résultats car on a exclu la seule classe qui générait des erreurs
            assertEquals( 0, details.size() );
            assertEquals( "architecture JRAF", measure.getRuleSet().getName() );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }
}
