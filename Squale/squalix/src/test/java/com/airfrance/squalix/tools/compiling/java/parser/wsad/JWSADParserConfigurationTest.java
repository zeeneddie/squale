/*
 * Créé le 3 août 05, par M400832.
 */
package com.airfrance.squalix.tools.compiling.java.parser.wsad;

import com.airfrance.squalecommon.SqualeTestCase;

/**
 * Cette classe teste la validité de la configuration de la tâche de compilation JAVA.
 */
public class JWSADParserConfigurationTest
    extends SqualeTestCase
{

    /**
     * Instance de configuration pour un parser WSAD.
     */
    private JWSADParserConfiguration mConf = null;

    /** test sur la clé d'ancrage du classpath */
    public void testClasspathAnchor()
    {
        assertEquals( mConf.getClasspathAnchor(), "classpath" );
    }

    /** test sur la clé d'entrée du classpath */
    public void testClasspathentry()
    {
        assertEquals( mConf.getClasspathentry(), "classpathentry" );
    }

    /** test sur la clé décrivant le type */
    public void testKind()
    {
        assertEquals( mConf.getKind(), "kind" );
    }

    /** test sur la clé path */
    public void testPath()
    {
        assertEquals( mConf.getPath(), "path" );
    }

    /** test sur la clé exported */
    public void testExported()
    {
        assertEquals( mConf.getExported(), "exported" );
    }

    /** test sur la clé lib */
    public void testLib()
    {
        assertEquals( mConf.getLib(), "lib" );
    }

    /** test sur la clé src */
    public void testSrc()
    {
        assertEquals( mConf.getSrc(), "src" );
    }

    /** test sur la clé var */
    public void testVar()
    {
        assertEquals( mConf.getVar(), "var" );
    }

    /**
     * test sur la clé décrivant le nom du fichier à partir duquel on récupère les informations
     */
    public void testFilename()
    {
        assertEquals( mConf.getFilename(), ".classpath" );
    }

    /**
     * Tear-down.
     * 
     * @throws Exception en cas de problèmes
     */
    protected void tearDown()
        throws Exception
    {
        super.tearDown();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws Exception {@inheritDoc}
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        mConf = new JWSADParserConfiguration();
    }

}
