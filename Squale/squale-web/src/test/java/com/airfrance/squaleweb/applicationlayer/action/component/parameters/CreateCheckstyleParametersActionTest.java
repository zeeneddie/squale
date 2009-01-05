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

import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.CheckstyleDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.UserBO;
import com.airfrance.squalecommon.enterpriselayer.facade.checkstyle.CheckstyleFacade;
import com.airfrance.squaleweb.SqualeWebTestCase;
import com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.CheckstyleForm;
import com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateProjectForm;

/**
 * Test de l'action de paramétrage checkstyle d'un projet
 */
public class CreateCheckstyleParametersActionTest
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
     * Test de remplissage du formulaire de configuration Checkstyle On charge plusieurs rulesets checkstyle de même nom
     * et on positionne les paramètres du projet sur le nom du ruleset Le formulaire en sortie doit contenir un seul
     * ruleset
     */
    public void testFill()
    {
        try
        {
            // Chargement du ruleset checkstyle
            InputStream stream = getClass().getClassLoader().getResourceAsStream( "config/AFcheckStyle_parsing.xml" );
            // Parsing du contenu du fichier
            StringBuffer errors = new StringBuffer();
            CheckstyleDTO versionRes = CheckstyleFacade.importCheckstyleConfFile( stream, errors );
            // on charge une deuxième fois le ruleset
            stream = getClass().getClassLoader().getResourceAsStream( "config/AFcheckStyle_parsing.xml" );
            versionRes = CheckstyleFacade.importCheckstyleConfFile( stream, errors );
            StringParameterDTO param = new StringParameterDTO();
            param.setValue( versionRes.getName() );

            addRequestParameter( "action", "fill" );
            CreateProjectForm form = new CreateProjectForm();
            form.getParameters().getParameters().put( ParametersConstants.CHECKSTYLE_RULESET_NAME, param );
            getSession().setAttribute( "createProjectForm", form );

            setRequestPathInfo( "/RulesCheckingTask.do" );

            actionPerform();
            // Vérification des informations
            CheckstyleForm checkstyleForm = (CheckstyleForm) getSession().getAttribute( "checkstyleForm" );
            assertEquals( "un seul ruleset filtré par son nom", 1, checkstyleForm.getVersions().length );
            assertEquals( "le ruleset sélectionné", checkstyleForm.getSelectedRuleSet(), versionRes.getName() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test d'ajout de la configuration checkstyle On positionne les paramètres avec les sources et le ruleset chekstyle
     * En sortie on doit obtenir les paramètres du projet en conséquence
     */
    public void testAddProjectCheckstyle()
    {
        try
        {
            // On positionne les paramètres de la requête
            String ruleSetName = "ruleset";
            CheckstyleForm theForm = new CheckstyleForm();
            theForm.setSelectedRuleSet( ruleSetName );
            getSession().setAttribute( "checkstyleForm", theForm );

            addRequestParameter( "action", "addParameters" );
            CreateProjectForm form = new CreateProjectForm();
            getSession().setAttribute( "createProjectForm", form );
            setRequestPathInfo( "/add_project_checkstyle_config.do" );

            actionPerform();
            verifyForward( "configure" );

            // Vérification des informations positionnées sur le projet
            MapParameterDTO params = form.getParameters();
            StringParameterDTO param =
                (StringParameterDTO) params.getParameters().get( ParametersConstants.CHECKSTYLE_RULESET_NAME );
            assertNotNull( "ruleset renseigné", param );
            assertEquals( "ruleset", ruleSetName, param.getValue() );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

}
