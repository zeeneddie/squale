package com.airfrance.squalecommon.enterpriselayer.facade.macker;

import java.io.InputStream;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.ProjectRuleSetBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;

/**
 * Test de l'importation de fichier de configuration Macker
 */
public class MackerConfigParserTest
    extends SqualeTestCase
{

    /**
     * Test nominal On importe un fichier dont le contenu est connu, on vérifie que les données du fichier sont
     * restituées
     */
    public void testImportNominal()
    {
        StringBuffer errors = new StringBuffer();
        MackerConfigParser parser = new MackerConfigParser();
        // Parsing d'un fichier de test
        InputStream stream = getClass().getClassLoader().getResourceAsStream( "data/macker/macker.xml" );
        ProjectRuleSetBO ruleset = parser.parseFile( stream, errors );
        assertEquals( "pas d'erreur de parsing", 0, errors.length() );
        // Vérification des données chargées
        assertNotNull( ruleset );
        assertEquals( 1, ruleset.getRules().size() );
        assertEquals( "valeur à vérifier dans le fichier", "Simple example", ruleset.getName() );
        // Vérification des attributs de la règle
        RuleBO rule = (RuleBO) ruleset.getRules().values().iterator().next();
        assertEquals( "valeur à vérifier dans le fichier", "layerrespect", rule.getCategory() );
        assertEquals( "valeur à vérifier dans le fichier", "Les références ne sont pas correctes", rule.getCode() );
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
            MackerConfigParser parser = new MackerConfigParser();
            ProjectRuleSetBO ruleset = parser.parseFile( stream, errors );
            assertEquals( 0, ruleset.getRules().size() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }
}
