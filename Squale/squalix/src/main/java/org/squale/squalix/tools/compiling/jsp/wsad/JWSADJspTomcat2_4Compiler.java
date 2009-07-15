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
package org.squale.squalix.tools.compiling.jsp.wsad;

import java.io.File;

import org.apache.tools.ant.taskdefs.Java;

import org.squale.squalix.tools.compiling.jsp.bean.J2eeWSADProject;

/**
 * Compilation des JSPs avec Tomcat 5 pour les spécifications servlet 2.4 (Testé avec tomcat 5.5.25) Testé avec tomcat
 * 3.3.2, 4.1.36, 5.5.25 et 6.0.14
 */
public class JWSADJspTomcat2_4Compiler
    extends AbstractTomcatCompiler
{

    /**
     * @param pProject le projet
     */
    public JWSADJspTomcat2_4Compiler( J2eeWSADProject pProject )
    {
        super( pProject );
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.squalix.tools.compiling.jsp.wsad.AbstractTomcatCompiler#setJavaArgs(org.apache.tools.ant.taskdefs.Java,
     *      java.lang.String, java.lang.String, java.io.File)
     */
    protected void setJavaArgs( Java java, String packageName, File jspFile )
    {
        // Version des sources
        java.createArg().setValue( "-source" );
        java.createArg().setValue( mJ2eeProject.getJavaVersion() );
        // Version des sources générées
        java.createArg().setValue( "-target" );
        java.createArg().setValue( mJ2eeProject.getJavaVersion() );
        // Répertoire de destination
        java.createArg().setValue( "-d" );
        java.createArg().setValue( mJ2eeProject.getJspDestPath() );
        // Package : On indique un nom de package généré
        java.createArg().setValue( "-p" );
        java.createArg().setValue( packageName );
        // On indique le chemin racine vers l'application web
        java.createArg().setValue( "-webapp" );
        java.createArg().setValue( mJ2eeProject.getPath() );
        // La jsp à générer
        java.createArg().setValue( jspFile.getAbsolutePath() );
    }

}
