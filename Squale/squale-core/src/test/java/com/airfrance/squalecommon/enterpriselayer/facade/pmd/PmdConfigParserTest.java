package com.airfrance.squalecommon.enterpriselayer.facade.pmd;

import java.io.InputStream;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.pmd.PmdRuleSetBO;

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
