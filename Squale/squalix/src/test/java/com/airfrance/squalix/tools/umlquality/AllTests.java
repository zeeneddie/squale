/*
 * Créé le 11 août 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalix.tools.umlquality;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author E6400802 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class AllTests
{

    /**
     * @return le test général pour UMLQuality
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite( "Test for com.airfrance.squalix.tools.umlquality" );
        // $JUnit-BEGIN$
        suite.addTest( new TestSuite( UMLQualityConfigurationTest.class ) );
        suite.addTest( new TestSuite( UMLQualityPersistorTest.class ) );
        suite.addTest( new TestSuite( UMLQualityTaskTest.class ) );
        // $JUnit-END$
        return suite;
    }
}
