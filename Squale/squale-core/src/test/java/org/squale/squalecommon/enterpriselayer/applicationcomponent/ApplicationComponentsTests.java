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
package org.squale.squalecommon.enterpriselayer.applicationcomponent;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.squale.squalecommon.enterpriselayer.applicationcomponent.administration.ApplicationAdminApplicationComponentAccessTest;
import org.squale.squalecommon.enterpriselayer.applicationcomponent.administration.LoginApplicationComponentAccessTest;
import org.squale.squalecommon.enterpriselayer.applicationcomponent.administration.PurgeApplicationComponentAccessTest;
import org.squale.squalecommon.enterpriselayer.applicationcomponent.administration.ValidationApplicationComponentAccessTest;
import org.squale.squalecommon.enterpriselayer.applicationcomponent.display.ComponentApplicationComponentAccessTest;
import org.squale.squalecommon.enterpriselayer.applicationcomponent.display.ErrorApplicationComponentAccessTest;
import org.squale.squalecommon.enterpriselayer.applicationcomponent.display.ResultsApplicationComponentAccessTest;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ApplicationComponentsTests
{

    /**
     * Suite de tests JUnits pour les composants de niveau application
     * 
     * @return la suite de tests
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite( "Test for org.squale.squalecommon.enterpriselayer.applicationcomponent" );
        // $JUnit-BEGIN$
        suite.addTest( new TestSuite( LoginApplicationComponentAccessTest.class ) );
        suite.addTest( new TestSuite( ApplicationAdminApplicationComponentAccessTest.class ) );
        suite.addTest( new TestSuite( PurgeApplicationComponentAccessTest.class ) );
        suite.addTest( new TestSuite( ValidationApplicationComponentAccessTest.class ) );
        suite.addTest( new TestSuite( ComponentApplicationComponentAccessTest.class ) );
        suite.addTest( new TestSuite( ErrorApplicationComponentAccessTest.class ) );
        suite.addTest( new TestSuite( ResultsApplicationComponentAccessTest.class ) );
        // $JUnit-END$
        return suite;
    }
}
