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
package org.squale.squalix.tools.coberturatask;

import java.io.File;

import org.apache.commons.digester.Digester;
import org.junit.After;
import org.junit.Before;

import org.squale.squalix.tools.cobertura.CoberturaParser;

import junit.framework.TestCase;

/**
 * Test Class of the {@link CoberturaParserTest}
 */
public class CoberturaParserTest
    extends TestCase
{
    /* Switching check-style off to write tests */
    // CHECKSTYLE:OFF
    private Digester digester;
    
    private File configurationFile;
    
    private CoberturaParser parser;

    /** Preparing instance of needed objects */
    @Before
    public void setUp()
    {
        configurationFile = new File("myTestConfiguration.xml");
        parser = new CoberturaParser();
    }

    /** Cleaning values of objects */
    @After
    public void tearDown()
    {
    }

    /** Testing the xml configuration method */
    public void testXmlConfiguration()
    {
        //See with FB for private method testing
    }
}
