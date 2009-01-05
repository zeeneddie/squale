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
package com.airfrance.squaleweb.applicationlayer.action.component.parameters;

import java.io.InputStream;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.config.TaskDTO;
import com.airfrance.squalecommon.datatransfertobject.config.TaskParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.PmdRuleSetDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.UserBO;
import com.airfrance.squalecommon.enterpriselayer.facade.pmd.PmdFacade;
import com.airfrance.squaleweb.SqualeWebTestCase;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.PmdForm;
import com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateProjectForm;

/**
 * Test de l'action de paramétrage pmd d'un projet
 */
public class CreatePmdParametersActionTest
    extends SqualeWebTestCase
{

    /**
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        // On crée une application
        ApplicationBO appli = getComponentFactory().createApplication( getJRAFSession() );
        UserBO user = getComponentFactory().createUser( getJRAFSession() );
        setupLogonBean( user.getMatricule(), true );
        addRequestParameter( "applicationId", "" + appli.getId() );
    }

    /**
     * Création d'un ruleset
     * 
     * @param pResourceName nom de la ressource à charger
     * @return ruleset correspondant
     * @throws JrafEnterpriseException si exception
     */
    protected PmdRuleSetDTO createRuleSet( String pResourceName )
        throws JrafEnterpriseException
    {
        InputStream stream = getClass().getClassLoader().getResourceAsStream( pResourceName );
        // Parsing du contenu du fichier
        StringBuffer errors = new StringBuffer();
        PmdRuleSetDTO versionRes = PmdFacade.importPmdConfig( stream, errors );
        return versionRes;
    }

    /**
     * Création du DTO de la tâche
     * 
     * @param pLanguages langages à inclure
     * @return tâche correspondante
     */
    protected TaskDTO createTaskDTO( String[] pLanguages )
    {
        TaskDTO task = new TaskDTO();
        task.setName( "PmdTask" );
        for ( int i = 0; i < pLanguages.length; i++ )
        {
            TaskParameterDTO taskParam = new TaskParameterDTO();
            taskParam.setName( "language" );
            taskParam.setValue( pLanguages[i] );
            task.addParameter( taskParam );
        }
        return task;
    }

    /**
     * Test de remplissage du formulaire de configuration Pmd en configuration java On charge plusieurs rulesets pmd de
     * même nom et on positionne les paramètres du projet sur le nom du ruleset Le formulaire en sortie doit contenir un
     * seul ruleset et ne doit pas contenir de données jsp
     */
    public void testFillJava()
    {
        try
        {
            // Chargement du ruleset
            PmdRuleSetDTO ruleSet = createRuleSet( "config/pmd.xml" );
            // on charge une deuxième fois le ruleset
            ruleSet = createRuleSet( "config/pmd.xml" );

            // Positionnement des paramètres pour l'action
            StringParameterDTO dialect = new StringParameterDTO();
            dialect.setValue( ParametersConstants.JAVA1_4 );
            MapParameterDTO pmdParams = new MapParameterDTO();
            StringParameterDTO param = new StringParameterDTO();
            param.setValue( ruleSet.getName() );
            pmdParams.getParameters().put( ParametersConstants.PMD_JAVA_RULESET_NAME, param );
            CreateProjectForm form = new CreateProjectForm();
            form.getParameters().getParameters().put( ParametersConstants.DIALECT, dialect );
            form.getParameters().getParameters().put( ParametersConstants.PMD, pmdParams );
            form.getAdvancedTasks().add( createTaskDTO( new String[] { "java" } ) );
            addRequestParameter( "action", "fill" );
            getSession().setAttribute( "createProjectForm", form );

            setRequestPathInfo( "/PmdTask.do" );
            actionPerform();

            // Vérification des informations
            PmdForm pmdForm = (PmdForm) getSession().getAttribute( "pmdForm" );
            assertEquals( "un seul ruleset filtré par son nom", 1, pmdForm.getJavaRuleSets().length );
            assertEquals( "le ruleset sélectionné", ruleSet.getName(), pmdForm.getSelectedJavaRuleSet() );
            assertFalse( "masque jsp désactivé", pmdForm.isJspSourcesRequired() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de remplissage du formulaire de configuration Pmd en configuration java et jsp On charge plusieurs rulesets
     * pmd de même nom et on positionne les paramètres du projet sur le nom du ruleset Le formulaire en sortie doit
     * contenir un seul ruleset java et un seul ruleset jsp
     */
    public void testFillJsp()
    {
        try
        {
            // Chargement du ruleset java
            PmdRuleSetDTO javaRuleSet = createRuleSet( "config/pmd.xml" );
            // on charge une deuxième fois le ruleset
            javaRuleSet = createRuleSet( "config/pmd.xml" );
            // Chargement du ruleset jsp
            PmdRuleSetDTO jspRuleSet = createRuleSet( "config/pmd_jsp.xml" );
            // on charge une deuxième fois le ruleset
            jspRuleSet = createRuleSet( "config/pmd_jsp.xml" );

            // Positionnement des paramètres pour l'action
            StringParameterDTO dialect = new StringParameterDTO();
            dialect.setValue( ParametersConstants.JAVA1_4 );
            MapParameterDTO pmdParams = new MapParameterDTO();
            StringParameterDTO param = new StringParameterDTO();
            param.setValue( javaRuleSet.getName() );
            pmdParams.getParameters().put( ParametersConstants.PMD_JAVA_RULESET_NAME, param );
            param = new StringParameterDTO();
            param.setValue( jspRuleSet.getName() );
            pmdParams.getParameters().put( ParametersConstants.PMD_JSP_RULESET_NAME, param );
            CreateProjectForm form = new CreateProjectForm();
            form.getParameters().getParameters().put( ParametersConstants.DIALECT, dialect );
            form.getParameters().getParameters().put( ParametersConstants.PMD, pmdParams );
            form.getAdvancedTasks().add( createTaskDTO( new String[] { "java", "jsp" } ) );
            addRequestParameter( "action", "fill" );
            getSession().setAttribute( "createProjectForm", form );

            setRequestPathInfo( "/PmdTask.do" );
            actionPerform();

            // Vérification des informations
            PmdForm pmdForm = (PmdForm) getSession().getAttribute( "pmdForm" );
            assertEquals( "un seul ruleset java filtré par son nom", 1, pmdForm.getJavaRuleSets().length );
            assertEquals( "le ruleset sélectionné", javaRuleSet.getName(), pmdForm.getSelectedJavaRuleSet() );
            assertTrue( "masque jsp activé", pmdForm.isJspSourcesRequired() );
            assertEquals( "un seul ruleset jsp filtré par son nom", 1, pmdForm.getJspRuleSets().length );
            assertEquals( "le ruleset sélectionné", jspRuleSet.getName(), pmdForm.getSelectedJspRuleSet() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test d'ajout de la configuration Pmd dans un contexte java On positionne les paramètres avec les sources et le
     * ruleset pmd/java En sortie on doit obtenir les paramètres du projet en conséquence
     */
    public void testAddProjectPmdJava()
    {
        try
        {
            // On positionne les paramètres de la la session
            String javaRuleSetName = "javaruleset";
            PmdForm pmdForm = new PmdForm();
            pmdForm.setSelectedJavaRuleSet( javaRuleSetName );
            getSession().setAttribute( "pmdForm", pmdForm );

            addRequestParameter( "action", "addParameters" );
            CreateProjectForm form = new CreateProjectForm();
            getSession().setAttribute( "createProjectForm", form );
            setRequestPathInfo( "/add_project_pmd_config.do" );

            actionPerform();
            verifyForward( "configure" );

            // Vérification des informations positionnées sur le projet
            MapParameterDTO params = form.getParameters();
            MapParameterDTO pmdParams = (MapParameterDTO) params.getParameters().get( ParametersConstants.PMD );
            assertNotNull( "map PMD devrait être renseignée", pmdParams );
            StringParameterDTO javaParam =
                (StringParameterDTO) pmdParams.getParameters().get( ParametersConstants.PMD_JAVA_RULESET_NAME );
            assertNotNull( "ruleset renseigné", javaParam );
            assertEquals( "ruleset", javaRuleSetName, javaParam.getValue() );
            StringParameterDTO jspParam =
                (StringParameterDTO) pmdParams.getParameters().get( ParametersConstants.PMD_JSP_RULESET_NAME );
            assertNull( "le paramètre jsp ne devrait pas être renseigné", jspParam );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test d'ajout de la configuration Pmd dans un contexte java/jsp On positionne les paramètres avec les sources et
     * le ruleset pmd/jsp et java En sortie on doit obtenir les paramètres du projet en conséquence
     */
    public void testAddProjectJspJava()
    {
        try
        {
            // On positionne les paramètres de la session
            String javaRuleSetName = "javaruleset";
            String jspRuleSetName = "jspruleset";
            PmdForm pmdForm = new PmdForm();
            // On force le masque jsp
            pmdForm.setJspSourcesRequired( true );
            pmdForm.setSelectedJavaRuleSet( javaRuleSetName );
            pmdForm.setSelectedJspRuleSet( jspRuleSetName );
            getSession().setAttribute( "pmdForm", pmdForm );
            addRequestParameter( "action", "addParameters" );
            CreateProjectForm form = new CreateProjectForm();
            getSession().setAttribute( "createProjectForm", form );
            setRequestPathInfo( "/add_project_pmd_config.do" );

            actionPerform();
            verifyForward( "configure" );

            // Vérification des informations positionnées sur le projet
            MapParameterDTO params = form.getParameters();
            MapParameterDTO pmdParams = (MapParameterDTO) params.getParameters().get( ParametersConstants.PMD );
            assertNotNull( "map PMD devrait être renseignée", pmdParams );
            StringParameterDTO javaParam =
                (StringParameterDTO) pmdParams.getParameters().get( ParametersConstants.PMD_JAVA_RULESET_NAME );
            assertNotNull( "ruleset java renseigné", javaParam );
            assertEquals( "ruleset java", javaRuleSetName, javaParam.getValue() );
            StringParameterDTO jspParam =
                (StringParameterDTO) pmdParams.getParameters().get( ParametersConstants.PMD_JSP_RULESET_NAME );
            assertNotNull( "ruleset jsp renseigné", jspParam );
            assertEquals( "ruleset jsp", jspRuleSetName, jspParam.getValue() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }
}
