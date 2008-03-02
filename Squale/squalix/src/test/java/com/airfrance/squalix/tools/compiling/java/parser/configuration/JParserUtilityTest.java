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
