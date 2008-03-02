package com.airfrance.squalix.tools.umlquality;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlInterfaceBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlModelBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.umlquality.UMLQualityClassMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.umlquality.UMLQualityInterfaceMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.umlquality.UMLQualityModelMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskData;

/**
 * @author E6400802 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class UMLQualityTaskTest
    extends SqualeTestCase
{

    /**
     * Pseudo-chemin vers une vue.
     */
    private static final String VIEW_PATH = "";

    /** l'application */
    private ApplicationBO mAppli = new ApplicationBO();

    /** Le projet à auditer */
    private ProjectBO mProject = null;

    /** L'audit */
    private AuditBO mAudit = null;

    /** Les paramètres temporaires du projet */
    private TaskData mData = new TaskData();

    /**
     * @see TestCase#setUp()
     * @throws Exception si erreur
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

        MapParameterBO parameter = new MapParameterBO();
        Map projectParams = new HashMap();
        StringParameterBO params = new StringParameterBO();
        MapParameterBO umlqualityMap = new MapParameterBO();
        Map umlqualityParams = new HashMap();
        params.setValue( new File( ".", "data/models/Circulation2.xmi" ).getAbsolutePath() );
        umlqualityParams.put( ParametersConstants.UMLQUALITY_SOURCE_XMI, params );
        umlqualityMap.setParameters( umlqualityParams );

        // Ajout des filtres de classe
        ListParameterBO excludeClasses = new ListParameterBO();
        List listPatterns = new ArrayList();
        StringParameterBO pattern1 = new StringParameterBO();
        pattern1.setValue( "#.java" );
        listPatterns.add( pattern1 );
        StringParameterBO pattern2 = new StringParameterBO();
        pattern2.setValue( "#.javax" );
        listPatterns.add( pattern2 );
        excludeClasses.setParameters( listPatterns );
        // Ajout des filtres aux paramètres d'UMLQuality
        umlqualityParams.put( ParametersConstants.MODEL_EXCLUDED_CLASSES, excludeClasses );
        umlqualityMap.setParameters( umlqualityParams );
        // Ajout des paramètres d'UMLQuality aux paramètres du projet
        projectParams.put( ParametersConstants.UMLQUALITY, umlqualityMap );
        parameter.setParameters( projectParams );

        ProjectParameterDAOImpl.getInstance().create( getSession(), parameter );
        mProject.setParameters( parameter );

        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        mAudit = getComponentFactory().createAudit( this.getSession(), mProject );

        // On fait le commit pour permettre l'accès aux données dans une autre session
        getSession().commitTransactionWithoutClose();
        mData.putData( TaskData.VIEW_PATH, VIEW_PATH );
    }

    /**
     * Vérifie la correcte exécution de la tâche UMLQuality.
     * 
     * @throws JrafDaoException si erreur
     */
    public void testRun()
        throws JrafDaoException
    {
        UMLQualityTask task = new UMLQualityTask();
        task.setData( mData );
        task.setAuditId( new Long( mAudit.getId() ) );
        task.setProjectId( new Long( mProject.getId() ) );
        task.setApplicationId( new Long( mAppli.getId() ) );
        task.setStatus( AbstractTask.NOT_ATTEMPTED );
        task.run();
        assertEquals( AbstractTask.TERMINATED, task.getStatus() );

        try
        {
            // On charge le modèle analysé:
            Collection model =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit,
                                                                            UmlModelBO.class );
            assertEquals( 1, model.size() );
            // On récupère les mesures pour le modèle:
            Iterator modelIt = model.iterator();
            UmlModelBO aModel = (UmlModelBO) modelIt.next();
            Collection modelMeasures =
                MeasureDAOImpl.getInstance().findWhere( getSession(), new Long( aModel.getId() ), task.getAuditId() );
            assertEquals( 1, modelMeasures.size() );
            // on vérifie le contenu du tableau de Strings
            MapParameterBO umlqualityParams =
                (MapParameterBO) mProject.getParameters().getParameters().get( ParametersConstants.UMLQUALITY );
            List filterList =
                ( (ListParameterBO) umlqualityParams.getParameters().get( ParametersConstants.MODEL_EXCLUDED_CLASSES ) ).getParameters();

            assertEquals( "#.java", ( (StringParameterBO) filterList.get( 0 ) ).getValue() );
            assertEquals( "#.javax", ( (StringParameterBO) filterList.get( 1 ) ).getValue() );
            UMLQualityModelMetricsBO modelMetric = (UMLQualityModelMetricsBO) modelMeasures.iterator().next();
            assertEquals( 2, modelMetric.getMCC().intValue() );
            assertEquals( 4, modelMetric.getMD().intValue() );
            // On charge les classes analysées:
            Collection children =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit,
                                                                            UmlClassBO.class );
            assertEquals( 24, children.size() );
            // On récupère les mesures pour la première classe:
            Iterator childrenIt = children.iterator();
            UmlClassBO aClass = (UmlClassBO) childrenIt.next();
            Collection measures =
                MeasureDAOImpl.getInstance().findWhere( getSession(), new Long( aClass.getId() ), task.getAuditId() );
            assertEquals( 1, measures.size() );
            UMLQualityClassMetricsBO metric = (UMLQualityClassMetricsBO) measures.iterator().next();
            assertEquals( 7, metric.getRFC().intValue() ); // mesure du rfc
            assertEquals( 7, metric.getNumAttr().intValue() ); // mesure du nb d'attributs
            assertEquals( 0, metric.getNOBD().intValue() ); // mesure du dit
            assertEquals( 1, metric.getNOBM().intValue() );
            assertEquals( 1, metric.getNOC().intValue() );
            assertEquals( 1, metric.getNOP().intValue() );
            assertEquals( 0, metric.getNVM().intValue() );
            assertEquals( 0, metric.getNII().intValue() );
            assertEquals( 2, metric.getDIT().intValue() );
            assertEquals( 6, metric.getOpsInh().intValue() );
            assertEquals( 0, metric.getDepClient().intValue() );
            assertEquals( 0, metric.getDepSupp().intValue() );
            assertEquals( 1, metric.getNumOps().intValue() ); // mesure du nb d'opérations
            // On charge les interfaces analysées:
            Collection interfaces =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit,
                                                                            UmlInterfaceBO.class );
            assertEquals( 3, interfaces.size() );
            // On récupère les mesures pour la première interface:
            Iterator interfaceIt = interfaces.iterator();
            UmlInterfaceBO aInterface = (UmlInterfaceBO) interfaceIt.next();
            Collection interfaceMeasures =
                MeasureDAOImpl.getInstance().findWhere( getSession(), new Long( aInterface.getId() ),
                                                        new Long( mAudit.getId() ) );
            assertEquals( 1, interfaceMeasures.size() );
            UMLQualityInterfaceMetricsBO interfaceMetric =
                (UMLQualityInterfaceMetricsBO) interfaceMeasures.iterator().next();
            assertEquals( 3, interfaceMetric.getNumOps().intValue() );
            assertEquals( 1, interfaceMetric.getNumClients().intValue() );
            assertEquals( 0, interfaceMetric.getNumAnc().intValue() );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }
}
