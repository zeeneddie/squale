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
/*
 * Créé le 2 août 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.squalix.tools.umlquality;

import java.util.Collection;

import junit.framework.TestCase;

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import org.squale.squalecommon.daolayer.component.ProjectDAOImpl;
import org.squale.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.UmlClassBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.UmlInterfaceBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.UmlModelBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

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