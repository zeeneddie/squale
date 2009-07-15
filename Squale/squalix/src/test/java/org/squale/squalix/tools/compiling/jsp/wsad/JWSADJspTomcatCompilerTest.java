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
package com.airfrance.squalix.tools.compiling.jsp.wsad;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildListener;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalix.tools.compiling.jsp.bean.J2eeWSADProject;
import com.airfrance.squalix.tools.compiling.utility.FileManager;

/**
 * 
 */
public class JWSADJspTomcatCompilerTest
    extends SqualeTestCase
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( JWSADJspTomcatCompilerTest.class );

    /**
     * Compilation avec Jspc Tomcat - version J2EE 1.4 (web.xml dtd 2.4)
     * 
     * @throws BuildException si erreur lors de la compilation
     * @throws IOException si erreur de flux
     */
    public void testJspTomcatCompilerWithComor()
        throws BuildException, IOException
    {
        J2eeWSADProject project = new J2eeWSADProject();
        project.setJavaVersion( "1.4" );
        project.setListener( new MyListener() );
        project.setClasspath( "" );
        project.addJarDirToClasspath( new File( "../squalix/lib/jspcompiling/1_4/" ) );
        project.addJarDirToClasspath( new File( "data/Project4JspCompilingTest/J2ee1_4/WebContent/WEB-INF/lib" ) );
        JWSADJspTomcat2_4Compiler compiler = new JWSADJspTomcat2_4Compiler( project );
        project.setJspDestPath( "data/Project4JspCompilingTest/J2ee1_4/jspToJava" );
        project.setPath( new File( "data/Project4JspCompilingTest/J2ee1_4/WebContent" ).getAbsolutePath() );
        final int NB_JSP_DIRS = 3;
        Object[][] jspSrcs = new Object[NB_JSP_DIRS][2];
        jspSrcs[0][0] = new File( "data/Project4JspCompilingTest/J2ee1_4/WebContent/WEB-INF/jsp1" ).getAbsolutePath();
        jspSrcs[0][1] =
            FileManager.checkFileNumber( "data/Project4JspCompilingTest/J2ee1_4/WebContent/WEB-INF/jsp1", ".jsp" );
        jspSrcs[1][0] = new File( "data/Project4JspCompilingTest/J2ee1_4/WebContent/WEB-INF/jsp2" ).getAbsolutePath();
        jspSrcs[1][1] =
            FileManager.checkFileNumber( "data/Project4JspCompilingTest/J2ee1_4/WebContent/WEB-INF/jsp2", ".jsp" );
        jspSrcs[2][0] = new File( "data/Project4JspCompilingTest/J2ee1_4/jspExt" ).getAbsolutePath();
        jspSrcs[2][1] = FileManager.checkFileNumber( "data/Project4JspCompilingTest/J2ee1_4/jspExt", ".jsp" );
        project.setJspPaths( jspSrcs );
        compiler.compileJsp();
        int nbGeneratedClasses = FileManager.checkFileNumber( project.getJspDestPath(), ".java" ).size();
        // Le nombre de .jasp = au nombre de .java généré
        assertEquals( nbGeneratedClasses, ( (ArrayList) jspSrcs[0][1] ).size() + ( (ArrayList) jspSrcs[1][1] ).size()
            + ( (ArrayList) jspSrcs[2][1] ).size() );
    }

    /** Ecouteur pour le test */
    class MyListener
        implements BuildListener
    {

        /**
         * @see org.apache.tools.ant.BuildListener#buildStarted(org.apache.tools.ant.BuildEvent)
         */
        public void buildStarted( BuildEvent event )
        {
            LOGGER.warn( event.getMessage() );
        }

        /**
         * @see org.apache.tools.ant.BuildListener#buildFinished(org.apache.tools.ant.BuildEvent)
         */
        public void buildFinished( BuildEvent event )
        {
            LOGGER.warn( event.getMessage() );
        }

        /**
         * @see org.apache.tools.ant.BuildListener#targetStarted(org.apache.tools.ant.BuildEvent)
         */
        public void targetStarted( BuildEvent event )
        {
            LOGGER.warn( event.getMessage() );
        }

        /**
         * @see org.apache.tools.ant.BuildListener#targetFinished(org.apache.tools.ant.BuildEvent)
         */
        public void targetFinished( BuildEvent event )
        {
            LOGGER.warn( event.getMessage() );
        }

        /**
         * @see org.apache.tools.ant.BuildListener#taskStarted(org.apache.tools.ant.BuildEvent)
         */
        public void taskStarted( BuildEvent event )
        {
            LOGGER.warn( event.getMessage() );
        }

        /**
         * @see org.apache.tools.ant.BuildListener#taskFinished(org.apache.tools.ant.BuildEvent)
         */
        public void taskFinished( BuildEvent event )
        {
            LOGGER.warn( event.getMessage() );
        }

        /**
         * @see org.apache.tools.ant.BuildListener#messageLogged(org.apache.tools.ant.BuildEvent)
         */
        public void messageLogged( BuildEvent event )
        {
            LOGGER.warn( event.getMessage() );
        }

    }
}
