/*
 * Créé le 12 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.enterpriselayer.facade;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.airfrance.squalecommon.enterpriselayer.facade.component.ApplicationFacadeTest;
import com.airfrance.squalecommon.enterpriselayer.facade.component.AuditFacadeTest;
import com.airfrance.squalecommon.enterpriselayer.facade.component.ComponentFacadeTest;
import com.airfrance.squalecommon.enterpriselayer.facade.component.ProjectFacadeTest;
import com.airfrance.squalecommon.enterpriselayer.facade.component.UserFacadeTest;
import com.airfrance.squalecommon.enterpriselayer.facade.quality.ErrorFacadeTest;
import com.airfrance.squalecommon.enterpriselayer.facade.quality.MarkFacadeTest;
import com.airfrance.squalecommon.enterpriselayer.facade.quality.PracticeRuleFacadeTest;
import com.airfrance.squalecommon.enterpriselayer.facade.quality.QualityResultFacadeTest;
import com.airfrance.squalecommon.enterpriselayer.facade.quality.SqualeReferenceFacadeTest;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class FacadesTests
{

    /**
     * Suite de tests JUnit pour les facades
     * 
     * @return la suite de tests
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite( "Test for com.airfrance.squalecommon.enterpriselayer.facade.component" );
        // $JUnit-BEGIN$
        suite.addTest( new TestSuite( AuditFacadeTest.class ) );
        suite.addTest( new TestSuite( ComponentFacadeTest.class ) );
        suite.addTest( new TestSuite( ApplicationFacadeTest.class ) );
        suite.addTest( new TestSuite( ProjectFacadeTest.class ) );
        suite.addTest( new TestSuite( UserFacadeTest.class ) );
        suite.addTest( new TestSuite( ErrorFacadeTest.class ) );
        suite.addTest( new TestSuite( MarkFacadeTest.class ) );
        suite.addTest( new TestSuite( PracticeRuleFacadeTest.class ) );
        suite.addTest( new TestSuite( QualityResultFacadeTest.class ) );
        suite.addTest( new TestSuite( SqualeReferenceFacadeTest.class ) );

        // $JUnit-END$
        return suite;
    }
}
