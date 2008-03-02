/*
 * Créé le 2 août 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalix.tools.umlquality;

import java.util.Collection;

import junit.framework.TestCase;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlInterfaceBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlModelBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * Teste la peristance des résultats issus de UMLQuality.
 */
public class UMLQualityPersistorTest
    extends SqualeTestCase
{

    /** l'application */
    private ApplicationBO mAppli = new ApplicationBO();

    /** le projet à auditer */
    private ProjectBO mProject = new ProjectBO();

    /** l'audit */
    private AuditBO mAudit = new AuditBO();

    /**
     * Constructeur pour UMLQualityPersistorTest
     * 
     * @param pParam nom
     */
    public UMLQualityPersistorTest( String pParam )
    {

        super( pParam );

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
        mAudit = getComponentFactory().createAudit( getSession(), mProject );
        // Enregistrement du ProjectBO dans la base
        ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
        MapParameterBO params = new MapParameterBO();
        ProjectParameterDAOImpl.getInstance().create( getSession(), params );
    }

    /**
     * Test de parsing d'un rapport de model uml
     */
    public void testParseReports()
    {
        try
        {

            UMLQualityPersistor persistor = new UMLQualityPersistor( mProject, mAudit, getSession() );
            persistor.parseComponentReport( "data/umlquality/sample/m_Model.csv", "model" );
            persistor.parseComponentReport( "data/umlquality/sample/m_Package.csv", "package" );
            persistor.parseComponentReport( "data/umlquality/sample/m_Interface.csv", "interface" );
            persistor.parseComponentReport( "data/umlquality/sample/m_Class.csv", "class" );
            Collection coll =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit,
                                                                            UmlModelBO.class );
            Collection coll2 =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit,
                                                                            UmlInterfaceBO.class );
            Collection coll3 =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit,
                                                                            UmlClassBO.class );
            // Vérification des objets créés
            assertEquals( 1, coll.size() );
            assertEquals( 3, coll2.size() );
            assertEquals( 27, coll3.size() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

}