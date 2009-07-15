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
package com.airfrance.squalix.tools.compiling.java.parser.configuration;

import java.io.FileNotFoundException;

import org.w3c.dom.Node;

import com.airfrance.squalecommon.SqualeTestCase;

/**
 *
 */
public class JParserUtilityTest
    extends SqualeTestCase
{

    /**
     * test de l'obtention d'un rootnode connu
     */
    public void testGetRootNodeKnown()
    {
        try
        {
            Node node = JParserUtility.getRootNode( ".classpath", "classpath" );
            assertNotNull( node );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Unexpected exception" );
        }
    }

    /**
     * test de l'obtention d'un rootnode inconnu
     */
    public void testGetRootNodeUnknownAttribute()
    {
        try
        {
            Node node = JParserUtility.getRootNode( ".classpath", "unknown" );
            assertNull( node );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Unexpected exception" );
        }
    }

    /**
     * test de l'obtention d'un rootnode inconnu
     */
    public void testGetRootNodeUnknownFile()
    {
        try
        {
            Node node = JParserUtility.getRootNode( ".classpath.unknown", "unknown" );
            fail( "Expected exception" );
        }
        catch ( Exception e )
        {
            assertTrue( "Expected exception", e instanceof FileNotFoundException );
        }
    }

    /**
     * Test de l'obtention d'un noeud fils
     */
    public void testGetNodeByTagName()
    {
        try
        {
            Node node = JParserUtility.getRootNode( ".classpath", "classpath" );
            assertNotNull( node );
            Node child = JParserUtility.getNodeByTagName( node, "unknown" );
            assertNull( "Le tag devrait être inconnu", child );
            child = JParserUtility.getNodeByTagName( node, "classpathentry" );
            assertNotNull( "Le tag devrait être connu", child );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Unexpected exception" );
        }
    }
}
