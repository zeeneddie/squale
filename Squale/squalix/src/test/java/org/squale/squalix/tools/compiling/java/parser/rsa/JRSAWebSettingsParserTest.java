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
package org.squale.squalix.tools.compiling.java.parser.rsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalix.core.exception.ConfigurationException;

/**
 * Test pour le parsing du fichier .settings/org.eclipse.wst.common.component afin de récupérer le nom du répertoire web
 * content
 */
public class JRSAWebSettingsParserTest
    extends SqualeTestCase
{

    /**
     * Teste Le parsing du fichier.
     * 
     * @throws FileNotFoundException si erreur de fichier
     * @throws ConfigurationException si erreur de configuration
     */
    public void testParse()
        throws FileNotFoundException, ConfigurationException
    {
        JRSAWebSettingsParser parser = new JRSAWebSettingsParser();
        InputStream input =
            new FileInputStream( new File( "./data/QuickTestRSA/testWeb/.settings/org.eclipse.wst.common.component" ) );
        parser.parse( input );
        assertEquals( "/WebContent", parser.getWebContentFolder() );
    }

}
