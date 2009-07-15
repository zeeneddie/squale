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
package org.squale.squalix.tools.mccabe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import org.squale.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import org.squale.squalecommon.daolayer.config.ProjectProfileDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.JspBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import org.squale.squalix.core.AbstractTask;
import org.squale.squalix.core.TaskData;

/**
 * Test pour la tâche McCabe sur un projet java UNIT_KO : Le fichier CVS ne correspond pas à l'environnement de test. Il
 * faut modifier METHODES.CVS pour qu'il corresponde à l'environnement.
 */
public class JavaMcCabeTaskTest
    extends SqualeTestCase
{

    /** l'application */
    private ApplicationBO mAppli = new ApplicationBO();

    /** Le projet à auditer */
    private ProjectBO mProject = null;

    /** L'audit */
    private AuditBO mAudit = null;

    /** Les tâches temporaires du projet */
    private TaskData mData = new TaskData();

    /**
     * Constructeur.
     * 
     * @param arg0 nom
     */
    public JavaMcCabeTaskTest( String arg0 )
    {
        super( arg0 );
    }

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
        MapParameterBO parameters = getProjectParameters();
        ProjectParameterDAOImpl.getInstance().save( getSession(), parameters );
        ProjectProfileBO profileBO = getComponentFactory().createProjectProfile( getSession() );
        profileBO.setName( "j2ee" );
        ProjectProfileDAOImpl.getInstance().save( getSession(), profileBO );
        SourceManagementBO managerBO = getComponentFactory().createSourceManagement( getSession() );
        mProject = getComponentFactory().createProject( getSession(), mAppli, grid, profileBO, managerBO, parameters );
        mAudit = getComponentFactory().createAudit( getSession(), mProject );
        // On fait le commit pour permettre l'accès aux données dans une autre session
        getSession().commitTransactionWithoutClose();
        String classpath = "./data/samples/bin";
        mData.putData( TaskData.CLASSPATH, classpath );
        mData.putData( TaskData.VIEW_PATH, new File( "./data/samples/testWeb/" ).getCanonicalPath().replaceAll( "\\\\",
                                                                                                                "/" ) );
    }

    /**
     * @return les paramètres du projet
     */
    private MapParameterBO getProjectParameters()
    {
        MapParameterBO parameters = new MapParameterBO();
        // Le dialect
        StringParameterBO dialect = new StringParameterBO();
        dialect.setValue( ParametersConstants.JAVA1_4 );
        parameters.getParameters().put( ParametersConstants.DIALECT, dialect );
        // Liste des chemin vers les sources
        ArrayList srcPath = new ArrayList();
        StringParameterBO value = new StringParameterBO();
        value.setValue( "JavaSource" );
        srcPath.add( value );
        ListParameterBO srcs = new ListParameterBO();
        srcs.setParameters( srcPath );
        parameters.getParameters().put( ParametersConstants.SOURCES, srcs );
        // Liste des chemins vers les JSPs
        ArrayList jspPath = new ArrayList();
        StringParameterBO jspValue = new StringParameterBO();
        jspValue.setValue( "WebContent/jsp" );
        jspPath.add( jspValue );
        ListParameterBO jsps = new ListParameterBO();
        jsps.setParameters( jspPath );
        parameters.getParameters().put( ParametersConstants.JSP, jsps );
        // Exclusion de la classe BinaryMetricsBO
        ListParameterBO patterns = new ListParameterBO();
        StringParameterBO pattern = new StringParameterBO();
        pattern.setValue( "**Binary.**" );
        ArrayList patternsList = new ArrayList();
        patternsList.add( pattern );
        patterns.setParameters( patternsList );
        parameters.getParameters().put( ParametersConstants.EXCLUDED_PATTERNS, patterns );
        return parameters;
    }

    /**
     * Vérifie la correcte exécution de la tâche McCabe sur un projet j2ee.
     * 
     * @throws Exception si erreur
     */
    public void testRunJ2EE()
        throws Exception
    {
        JavaMcCabeTaskStub taskStub = new JavaMcCabeTaskStub();
        taskStub.setData( mData );
        taskStub.setAuditId( new Long( mAudit.getId() ) );
        taskStub.setProjectId( new Long( mProject.getId() ) );
        taskStub.setApplicationId( new Long( mAppli.getId() ) );
        taskStub.setStatus( AbstractTask.NOT_ATTEMPTED );
        taskStub.run();
        assertEquals( AbstractTask.TERMINATED, taskStub.getStatus() );
        try
        {
            // On charge les classe analysées:
            Collection children =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit, JspBO.class );
            assertEquals( "Un composant JSP doit être crée", 1, children.size() );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /** Classe interne pour les tests */
    class JavaMcCabeTaskStub
        extends JavaMcCabeTask
    {
        /**
         * (non-Javadoc)
         * 
         * @see org.squale.squalix.tools.mccabe.AbstractMcCabeTask#createProjectConfigurationFile(org.squale.squalix.tools.mccabe.McCabePCFFile)
         */
        protected void createProjectConfigurationFile( McCabePCFFile pFile )
            throws Exception
        {
        }

        /**
         * (non-Javadoc)
         * 
         * @see org.squale.squalix.tools.mccabe.AbstractMcCabeTask#createReport(java.lang.String)
         */
        protected void createReport( String pReport )
            throws Exception
        {
        }

        /**
         * (non-Javadoc)
         * 
         * @see org.squale.squalix.tools.mccabe.AbstractMcCabeTask#parseSource()
         */
        protected int parseSource()
            throws Exception
        {
            return 0;
        }

        /**
         * (non-Javadoc)
         * 
         * @see org.squale.squalix.tools.mccabe.AbstractMcCabeTask#computeReportFileName(java.lang.String)
         */
        protected String computeReportFileName( String pReport )
            throws IOException
        {
            String result = null;
            if ( pReport.startsWith( "CLASSES" ) )
            {
                result = "data/mccabe/j2ee/CLASSES.CSV";
            }
            else if ( pReport.startsWith( "METHODES" ) )
            {
                result = "data/mccabe/j2ee/METHODES.CSV";
            }
            return result;
        }
    }

}
