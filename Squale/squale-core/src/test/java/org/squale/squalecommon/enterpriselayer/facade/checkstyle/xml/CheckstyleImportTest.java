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
 * Créé le 12 juil. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.squalecommon.enterpriselayer.facade.checkstyle.xml;

import java.io.InputStream;

import java.util.Map;
import java.util.Set;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleModuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleSetBO;

/**
 * Test d'importation au format XML du fichier de configuration checkstyle Le test vérifie essentiellement que la
 * mapping est correct au niveau du digester en s'assurant que tous les attributs présents dans le fichier XML sont bien
 * mappés dans le classes métier
 */
public class CheckstyleImportTest
    extends SqualeTestCase
{

    /**
     * Parsing nominale d'un fichier de configuration checkstyle
     */
    public void testParseFile()
    {
        try
        {
            InputStream stream = getClass().getClassLoader().getResourceAsStream( "config/AFcheckStyle_parsing.xml" );
            StringBuffer errors = new StringBuffer();
            // parsing du contenue du fichier
            CheckstyleImport importch = new CheckstyleImport();
            CheckstyleRuleSetBO versionRes = importch.importCheckstyle( stream, errors );

            assertTrue( "Erreur pendant le parsing", errors.length() == 0 );
            assertNotNull( "echec de parsing", versionRes );
            assertEquals( "Version", versionRes.getName(), "default" );

            Map regles = versionRes.getRules();
            assertEquals( "nb règle", 23, regles.size() );
            CheckstyleRuleBO rule = (CheckstyleRuleBO) regles.get( "COD01" );

            assertNotNull( "rule null", rule );
            assertNotNull( "rule null", rule.getSeverity() );
            assertEquals( "severity", rule.getSeverity(), "warning" );
            assertEquals( "practise", rule.getCategory(), "programmingstandard" );
            Set moduleSet = rule.getModules();
            assertEquals( "nb règle", 4, moduleSet.size() );
            CheckstyleModuleBO module = new CheckstyleModuleBO();
            module.setName( "com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck" );
            assertTrue( "module contains", moduleSet.contains( module ) );

            module.setName( "com.puppycrawl.tools.checkstyle.checks.imports.IllegalImportCheck" );
            assertTrue( "module contains", moduleSet.contains( module ) );

            module.setName( "com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheck" );
            assertTrue( "module contains", moduleSet.contains( module ) );

            module.setName( "com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck" );
            assertTrue( "module contains", moduleSet.contains( module ) );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }
    }

    /**
     * Test d'importation avec mauvais regroupement Le fichier de test comporte une incohérence sur la sévérité entre un
     * module checkstyle et la règle checkstyle
     */
    public void testImportWithBadRegrouping()
    {
        try
        {
            InputStream stream =
                getClass().getClassLoader().getResourceAsStream(
                                                                 "data/checkstyle/AFcheckStyle_new_parsing_bad_regrouping.xml" );
            StringBuffer errors = new StringBuffer();
            // parsing du contenue du fichier
            CheckstyleImport importch = new CheckstyleImport();
            CheckstyleRuleSetBO versionRes = importch.importCheckstyle( stream, errors );
            assertTrue( "Erreurs attendues", errors.length() > 0 );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }
    }
}
