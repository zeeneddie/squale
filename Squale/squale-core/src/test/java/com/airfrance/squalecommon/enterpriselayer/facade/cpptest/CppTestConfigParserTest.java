package com.airfrance.squalecommon.enterpriselayer.facade.cpptest;

import java.io.InputStream;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.cpptest.CppTestRuleSetBO;

/**
 * Test de l'importation de fichier de configuration
 */
public class CppTestConfigParserTest
    extends SqualeTestCase
{

    /**
     * Test nominal On importe un fichier dont le contenu est connu, on vérifie que les données du fichier sont
     * restituées
     */
    public void testImportNominal()
    {
        StringBuffer errors = new StringBuffer();
        CppTestConfigParser parser = new CppTestConfigParser();
        // Parsing d'un fichier de test
        InputStream stream = getClass().getClassLoader().getResourceAsStream( "data/cpptest/cpptest.xml" );
        CppTestRuleSetBO ruleset = parser.parseFile( stream, errors );
        assertEquals( "pas d'erreur de parsing", 0, errors.length() );
        // Vérification des données chargées
        assertNotNull( ruleset );
        assertEquals( 1, ruleset.getRules().size() );
        assertEquals( "valeur à vérifier dans le fichier", "default", ruleset.getName() );
        assertEquals( "valeur à vérifier dans le fichier", "builtin://MustHaveRules", ruleset.getCppTestName() );
        // Vérification des attributs de la règle
        RuleBO rule = (RuleBO) ruleset.getRules().values().iterator().next();
        assertEquals( "valeur à vérifier dans le fichier", "coding", rule.getCategory() );
        assertEquals( "valeur à vérifier dans le fichier", "DeleteNonPointer.rule", rule.getCode() );
        assertEquals( "valeur à vérifier dans le fichier", "error", rule.getSeverity() );
    }

    /**
     * Importation d'un mauvais fichier XML
     */
    public void testImportBadFile()
    {
        StringBuffer errors = new StringBuffer();
        InputStream stream = getClass().getClassLoader().getResourceAsStream( "config/hibernate.cfg.xml" );
        try
        {
            CppTestConfigParser parser = new CppTestConfigParser();
            CppTestRuleSetBO ruleset = parser.parseFile( stream, errors );
            assertEquals( 0, ruleset.getRules().size() );
            assertTrue( errors.length() > 0 );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }
}
