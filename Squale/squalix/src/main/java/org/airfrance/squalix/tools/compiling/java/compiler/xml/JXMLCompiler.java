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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\tools\\compiling\\java\\compiler\\xml\\XMLCompiler.java

package com.airfrance.squalix.tools.compiling.java.compiler.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.types.Path;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.tools.compiling.CompilingMessages;
import com.airfrance.squalix.tools.compiling.java.JCompilingTask;
import com.airfrance.squalix.tools.compiling.java.beans.JXMLProject;
import com.airfrance.squalix.tools.compiling.java.configuration.JCompilingConfiguration;

/**
 * Ant Compilation
 * 
 * @author m400832 (by rose)
 * @version 1.0
 */
public class JXMLCompiler
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( JXMLCompiler.class );

    /**
     * Projet à compiler.
     */
    private JXMLProject mProject;

    /**
     * Constructeur.
     * 
     * @param pProject projet à compiler.
     */
    public JXMLCompiler( JXMLProject pProject )
    {
        mProject = pProject;
    }

    /**
     * Cette méthode lance la procédure de compilation.
     * 
     * @throws Exception en cas d'erreur lors de l'exécution du script ANT.
     * @see #doCompilation()
     */
    public void execute()
        throws Exception
    {
        LOGGER.trace( CompilingMessages.getString( "logs.task.entering_method" ) );
        doCompilation();
    }

    /**
     * Cette méthode lance effectivement la compilation.
     * 
     * @throws Exception exception lors de la compilation par ANT.
     */
    private void doCompilation()
        throws Exception
    {
        LOGGER.trace( CompilingMessages.getString( "logs.task.entering_method" ) );

        /*
         * Création d'un descripteur de fichier avec un chemin UNIX.
         */
        File buildFile =
            new File ( mProject.getPath() );

        /*
         * La procédure n'est lancée que si le fichier XML est trouvé.
         */
        if ( buildFile.exists() && buildFile.isFile() )
        {
            /*
             * Initialisation du projet ANT.
             */
            Project project = new Project();
            project.setCoreLoader( null );
            project.init();

            /*
             * Propriétés du projet ANT.
             */
            project.setUserProperty( CompilingMessages.getString( "java.compiling.ant.key.ant.version" ),
                                     CompilingMessages.getString( "java.compiling.ant.value.ant.version" ) );
            project.setUserProperty( CompilingMessages.getString( "java.compiling.ant.key.ant.file" ),
                                     buildFile.getAbsolutePath() );

            /* mode keep-going à false */
            project.setKeepGoingMode( false );

            // Ajout du listener pour les traces
            project.addBuildListener( mProject.getListener() );
            /*
             * Parsing du fichier xml.
             */
            ProjectHelper helper = ProjectHelper.getProjectHelper();
            project.addReference( CompilingMessages.getString( "java.compiling.ant.key.ant.project_helper" ), helper );
            helper.parse( project, buildFile );

            /*
             * Ajout de la tâche à lancer.
             */
            Vector targets = new Vector();
            /* si une target a été définie dans le JXMLProject */
            if ( null != mProject.getTarget() && !"".equals( mProject.getTarget() ) )
            {
                targets.addElement( (String) mProject.getTarget() );

                /* sinon on ajoute la target par défaut du projet ANT */
            }
            else
            {
                targets.addElement( project.getDefaultTarget() );
            }

            /*
             * Exécution de la tâche ANT.
             */
            project.executeTargets( targets );

            /*
             * Récupération du classpath dans le fichier build.xml et du classes_dir.
             */
            antClasspath( project );

            /* ménage */
            project = null;
            targets = null;
            helper = null;

        }
        else
        {
            /* exception lancée si le fichier xml n'est pas trouvé */
            throw new FileNotFoundException(
                                             CompilingMessages.getString( "java.exception.compiling.ant.build_file_not_found" ) );
        }

    }

    /**
     * Getter.
     * 
     * @return un projet JXMLProject.
     */
    public JXMLProject getXMLProject()
    {
        return mProject;
    }

    /**
     * Méthode qui permet de recuperer le classpath et de redefinir précisément l'emplacement des fichiers compilés.
     * Pour que cette méthode marche, il faut que l'utilisateur ait défini dans le fichier build.xml : Une propriété
     * nommée SqualeClassesDir qui indique l'emplacement des fichiers compilés et une référence générant le classpath
     * portant l'identifiant SqualeClasspath
     * 
     * @param antProject projet ant dans lequel on effectue la recherche
     */
    private void antClasspath( Project antProject )
    {
        JCompilingTask compilingTask = (JCompilingTask) mProject.getListener();
        String classesDir = antProject.getProperty( "SqualeClassesDir" );
        File baseDir = antProject.getBaseDir();
        File test = new File( classesDir );
        if ( !test.isAbsolute() )
        {
            // if(!classesDir.startsWith("/")){
            classesDir = baseDir.getAbsolutePath() + "/" + classesDir;
        }
        List classesDirs = new ArrayList();
        classesDirs.add( classesDir );
        compilingTask.getData().putData( TaskData.CLASSES_DIRS, classesDirs );
        Path antClassPath = (Path) antProject.getReference( "SqualeClasspath" );
        String classpath = antClassPath.toString();
        String[] allpath = classpath.split( ":" );
        for ( int i = 0; i < allpath.length; i++ )
        {
            int index = allpath[i].indexOf( "${" );
            if ( index != -1 )
            {
                LOGGER.warn( CompilingMessages.getString( "java.exception.compiling.ant.classpath_warning" )
                    + allpath[i] );
            }
        }
        classpath = classpath.replace( ':', ';' );
        classpath = classpath + ';' + classesDir;
        compilingTask.getData().putData( TaskData.CLASSPATH, classpath );
    }
}