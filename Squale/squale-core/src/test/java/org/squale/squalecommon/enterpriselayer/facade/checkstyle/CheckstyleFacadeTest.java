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
package org.squale.squalecommon.enterpriselayer.facade.checkstyle;

import java.io.InputStream;
import java.util.Map;

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.daolayer.rulechecking.CheckstyleRuleSetDAOImpl;
import org.squale.squalecommon.datatransfertobject.rulechecking.CheckstyleDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleSetBO;

/**
 * @author E6400802 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CheckstyleFacadeTest
    extends SqualeTestCase
{

    /**
     * Parsing nominale d'un fichier de configuration checkstyle
     */
    public void testParseFileNew()
    {
        try
        {
            InputStream stream = getClass().getClassLoader().getResourceAsStream( "config/AFcheckStyle_parsing.xml" );
            CheckstyleDTO version = new CheckstyleDTO();
            // parsing du contenue du fichier
            StringBuffer errors = new StringBuffer();
            CheckstyleDTO versionRes = CheckstyleFacade.importCheckstyleConfFile( stream, errors );
            assertTrue( "Erreur pendant le parsing", errors.length() == 0 );
            assertNotNull( "echec de parsing", versionRes );
            CheckstyleRuleSetBO versionPer =
                (CheckstyleRuleSetBO) CheckstyleRuleSetDAOImpl.getInstance().load( getSession(),
                                                                                   new Long( versionRes.getId() ) );
            assertNotNull( "echec de load", versionPer );
            Map regles = versionPer.getRules();
            CheckstyleRuleBO rule = (CheckstyleRuleBO) regles.get( "COD01" );
            assertNotNull( "rule null", rule );
            assertNotNull( "rule null", rule.getSeverity() );
            assertEquals( "severity", rule.getSeverity(), "warning" );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }
    }

    /**
     * Parsing d'un mauvais fichier
     */
    public void testParseBadFileNew()
    {

        try
        {
            InputStream stream = getClass().getClassLoader().getResourceAsStream( "config/hibernate.cfg.xml" );
            CheckstyleDTO version = new CheckstyleDTO();
            // lecture en byte du contenue du fichier
            // parsing du contenue du fichier
            StringBuffer errors = new StringBuffer();
            CheckstyleDTO versionRes = CheckstyleFacade.importCheckstyleConfFile( stream, errors );
            assertTrue( "Erreur pendant le parsing", errors.length() > 0 );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }

    }

    /**
     * Parsing d'un fichier de configuration checkstyle qui contient un mauvais regroupement des mod
     */
    public void testParseBabRegroupingNew()
    {
        try
        {
            InputStream stream =
                getClass().getClassLoader().getResourceAsStream(
                                                                 "data/checkstyle/AFcheckStyle_parsing_bad_regrouping.xml" );
            CheckstyleDTO version = new CheckstyleDTO();
            // parsing du contenue du fichier
            StringBuffer errors = new StringBuffer();
            CheckstyleDTO versionRes = CheckstyleFacade.importCheckstyleConfFile( stream, errors );
            assertTrue( "Erreur pendant le parsing", errors.length() > 0 );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }

    }

    /**
     * Parsing d'un fichier de configuration checkstyle dans lequel on n'a pas enlevé l'ambiguté au niveau du module
     * com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck
     */
    public void testParseAmbigutyRiseNew()
    {
        try
        {
            InputStream stream =
                getClass().getClassLoader().getResourceAsStream(
                                                                 "data/checkstyle/AFcheckStyle_parsing_ambiguty_Rise.xml" );
            CheckstyleDTO version = new CheckstyleDTO();
            // parsing du contenue du fichier
            StringBuffer errors = new StringBuffer();
            CheckstyleDTO versionRes = CheckstyleFacade.importCheckstyleConfFile( stream, errors );
            assertTrue( "Erreur pendant le parsing", errors.length() > 0 );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }

    }

}
