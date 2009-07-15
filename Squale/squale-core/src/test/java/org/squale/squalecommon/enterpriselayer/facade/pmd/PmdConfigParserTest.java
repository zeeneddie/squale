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
package org.squale.squalecommon.enterpriselayer.facade.pmd;

import java.io.InputStream;

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.pmd.PmdRuleSetBO;

/**
 * Test du parser de configuration PMD
 */
public class PmdConfigParserTest
    extends SqualeTestCase
{

    /**
     * Test nominal On importe un fichier dont le contenu est connu, on vérifie que les données du fichier sont
     * restituées
     */
    public void testImportNominal()
    {
        StringBuffer errors = new StringBuffer();
        PmdConfigParser parser = new PmdConfigParser();
        // Parsing d'un fichier de test
        InputStream stream = getClass().getClassLoader().getResourceAsStream( "data/pmd/pmd.xml" );
        PmdRuleSetBO ruleset = parser.parseFile( stream, errors );
        assertEquals( "pas d'erreur de parsing", 0, errors.length() );
        // Vérification des données chargées
        assertNotNull( ruleset );
        assertEquals( "valeur à vérifier dans le fichier", "default", ruleset.getName() );
        assertEquals( "valeur à vérifier dans le fichier", "java", ruleset.getLanguage() );
        // Vérification du nombre de règles lues
        assertEquals( "valeur à vérifier dans le fichier", 2, ruleset.getRules().size() );
        // Vérification des attributs de la règle CyclomaticComplexity
        RuleBO rule = (RuleBO) ruleset.getRules().get( "CyclomaticComplexity" );
        assertEquals( "valeur à vérifier dans le fichier", "coding", rule.getCategory() );
        assertEquals( "valeur à vérifier dans le fichier", "CyclomaticComplexity", rule.getCode() );
        assertEquals( "valeur à vérifier dans le fichier", "error", rule.getSeverity() );
        // Vérification des attributs de la règle ExcessiveMethodLength
        rule = (RuleBO) ruleset.getRules().get( "ExcessiveMethodLength" );
        assertEquals( "valeur à vérifier dans le fichier", "format", rule.getCategory() );
        assertEquals( "valeur à vérifier dans le fichier", "ExcessiveMethodLength", rule.getCode() );
        assertEquals( "valeur à vérifier dans le fichier", "warning", rule.getSeverity() );
    }
}
