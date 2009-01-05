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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\tools\\compiling\\java\\compiler\\wsad\\WSADCompiler.java

package com.airfrance.squalix.tools.compiling.java.compiler.wsad;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalix.tools.compiling.CompilingMessages;
import com.airfrance.squalix.tools.compiling.java.beans.JWSADProject;
import com.airfrance.squalix.tools.compiling.java.parser.wsad.JWSADParser;

/**
 * Compilation d'une série de projets WSAD Cette classe permet de lancer la compilation en fonction des dépendances
 * éventuelles entre les projets. Le processus de compilation est stoppé à la première anomalie.
 * 
 * @author m400832 (by rose)
 * @version 1.3
 */
public class JWSADCompiler
{
    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( JWSADCompiler.class );

    /**
     * Liste des projets en cours de traitement pour détecter des cycles
     */
    private ArrayList mProjectProcessed = new ArrayList();

    /**
     * Liste des projets WSAD.
     */
    private List mProjectList = null;

    /**
     * Constructeur.
     * 
     * @param pProjectList liste des projets WSAD à compiler.
     */
    public JWSADCompiler( List pProjectList )
    {
        mProjectList = pProjectList;
    }

    /**
     * Cette méthode lance la procédure de compilation.
     * 
     * @see #doRecursive()
     * @throws Exception exception lors de la compilation du projet WSAD 5.x.
     */
    public void runCompilation()
        throws Exception
    {
        JWSADProject projet = null;
        try
        {
            /* si la liste de projets n'est pas vide */
            if ( null != mProjectList )
            {
                Iterator it = mProjectList.iterator();
                /* si l'itérateur a des éléments */
                if ( null != it && it.hasNext() )
                {
                    /* tant qu'il y a des projets à compiler */
                    while ( it.hasNext() )
                    {
                        projet = (JWSADProject) it.next();
                        /*
                         * on appelle la méthode qui lance effectivement la compilation
                         */
                        doRecursive( projet );
                    }
                }
                it = null;
            }
            /* exception en provenance de doRecursive() */
        }
        catch ( Exception e )
        {
            LOGGER.fatal( e, e );
            /* on lance une nouvelle exception */
            throw new Exception( CompilingMessages.getString( "java.exception.task.not_compiled" ) + projet.getName()
                + "\n" + e );
        }
    }

    /**
     * This methods is actually the one that launches the project compilation. Firstly, one checks that the parser
     * didn't find any dependencies with another projet : <br />
     * <br />
     * <code>if(null != mProj.getMDependsOnProjects() && 
     * mProj.getMDependsOnProjects().size()>0)</code><br />
     * <br />
     * If one or more dependencies are found, then the compiling method ( <code>doCompilation()</code>) is
     * recursively called on each dependency.<br />
     * Otherwise, the comiling method is only called for the current projet.
     * 
     * @param pProject projet to compile
     * @see com.airfrance.squalix.tools.compiling.java.parser.wsad.JWSADParser#processSrc(String)
     * @see JWSADProject
     * @see #doCompilation()
     * @since 1.0
     * @throws Exception exception lors de la compilation.
     */
    private void doRecursive( JWSADProject pProject )
        throws Exception
    {
        // On crée un parser WSAD pour pouvoir appeler la méthode "addExportedPackagesToClasspath"
        JWSADParser parser = new JWSADParser( new ArrayList() );
        if ( pProject.isCompiled() == false )
        {
            // Détection de cycle
            if ( mProjectProcessed.contains( pProject ) )
            {
                throw new Exception( CompilingMessages.getString( "java.exception.task.circularity" )
                    + pProject.getName() + "\n" );
            }
            else
            {
                mProjectProcessed.add( pProject );
            }
            /*
             * si la compilation du projet ne peut se faire que si d'autres projets ont été compilés
             */
            if ( pProject.hasDependency() )
            {
                Iterator it = pProject.getDependsOnProjects().iterator();
                /* tant que l'itérateur a des éléments */
                if ( null != it && it.hasNext() )
                {
                    /*
                     * on crée les instances utilisées par la boucle ci dessous
                     */
                    JWSADCompiler comp = null;
                    JWSADProject pTemp = null;
                    /* tant qu'il y a des projets en dépendance */
                    while ( it.hasNext() )
                    {
                        /* on appelle récursivement doRecursive() */
                        pTemp = (JWSADProject) it.next();
                        doRecursive( pTemp );
                        // On ajoute les .class si il y a des packages exportés dans le cas
                        // d'une compilation RCP
                        if ( pTemp.getExportedPackages().size() > 0 )
                        {
                            parser.addExportedPackagesToClasspath( pProject, pTemp, new File( pTemp.getDestPath() ) );
                        }
                    }
                    pTemp = null;
                }
                it = null;
            }
            /* Sinon on compile le projet */
            JWSADAntCompiler compiler = new JWSADAntCompiler( pProject );
            compiler.doCompilation();
        }
    }

}
