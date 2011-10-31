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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\org\\squale\\squalix\\tools\\clearcase\\ClearCaseTask.java

package org.squale.squalix.tools.clearcase.task;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ProjectParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import org.squale.squalix.core.AbstractTask;
import org.squale.squalix.core.TaskData;
import org.squale.squalix.core.TaskException;
import org.squale.squalix.tools.clearcase.configuration.ClearCaseConfiguration;
import org.squale.squalix.tools.clearcase.configuration.ClearCaseMessages;
import org.squale.squalix.util.process.ProcessErrorHandler;
import org.squale.squalix.util.process.ProcessManager;

/**
 * Cette classe permet de monter une vue ClearCase snapshot.<br />
 * Il s'agit d'une tâche SQUALIX. En conséquence, cette classe hérite de la classe <code>Task</code>.
 * 
 * @author m400832 (by rose)
 * @version 2.0
 * @see org.squale.squalix.core.Task
 */
public class ClearCaseTask
    extends AbstractTask
    implements ProcessErrorHandler
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( ClearCaseTask.class );

    /**
     * Instance de configuration.
     */
    protected ClearCaseConfiguration mConfiguration;

    /**
     * {@inheritDoc} Constructeur. Initialisation des premiers paramètres. <br />
     * Les autres paramètres sont initialisés dans la méthode <code>init()</code>.
     * 
     * @see ClearCaseTask#init()
     */
    public ClearCaseTask()
    {
        mName = "ClearCaseTask";
    }

    /**
     * @param projectParam le paramètre qu'on va éventuellement essayer d'ajuster
     */
    private void adjustParameters( ProjectParameterBO projectParam )
    {
        if ( projectParam instanceof StringParameterBO )
        {
            // On ajuste le paramètre
            String oldParam = ( (StringParameterBO) projectParam ).getValue();
            // on commence par remplacer tous les \ par des /
            String newValue = oldParam.replaceAll( "\\\\", "/" );
            // et si la chaine n'est pas préfixée par /vobs/, on le rajoute
            if ( !newValue.startsWith( "/vobs/" ) )
            {
                // si ya déjà un /, on ne rajout que /vobs sinon on rajoute /vobs/
                if ( newValue.startsWith( "/" ) )
                {
                    newValue = "/vobs" + newValue;
                }
                else
                {
                    newValue = "/vobs/" + newValue;
                }
            }
            ( (StringParameterBO) projectParam ).setValue( newValue );
        }
        else
        {
            if ( projectParam instanceof MapParameterBO )
            {
                // On ajuste tous les paramètres de la map
                Map map = ( (MapParameterBO) projectParam ).getParameters();
                Set keys = map.keySet();
                Iterator it = keys.iterator();
                // On parcours donc tous les paramètres et on les ajuste en fonction
                // de leur type
                while ( it.hasNext() )
                {
                    adjustParameters( (ProjectParameterBO) it.next() );
                }
            }
            else
            {
                if ( projectParam instanceof ListParameterBO )
                {
                    // On ajuste tous les paramètres de la liste
                    List list = ( (ListParameterBO) projectParam ).getParameters();
                    Iterator it = list.iterator();
                    // On parcours donc tous les paramètres et on les ajuste en fonction
                    // de leur type
                    while ( it.hasNext() )
                    {
                        adjustParameters( (ProjectParameterBO) it.next() );
                    }
                }
            }
        }

    }

    /** Ajuste les différents paramètres */
    private void adjustParamsForClearcase()
    {
        // On reformate les différentes paramètres du projet car l'utilisateur a pu entré
        // des '/' ou des '\' comme séparateur et si le fichier défini n'existe pas on essaie de le reconstruire
        adjustParameters( mProject.getParameter( ParametersConstants.ANT_BUILD_FILE ) );
        adjustParameters( mProject.getParameter( ParametersConstants.BRANCH ) );
        adjustParameters( mProject.getParameter( ParametersConstants.CLASSPATH ) );
        adjustParameters( mProject.getParameter( ParametersConstants.CPP_SCRIPTFILE ) );
        adjustParameters( mProject.getParameter( ParametersConstants.CPPTEST_SCRIPT ) );
        adjustParameters( mProject.getParameter( ParametersConstants.EXCLUDED_DIRS ) );
        adjustParameters( mProject.getParameter( ParametersConstants.JSP ) );
        adjustParameters( mProject.getParameter( ParametersConstants.MACKER_CONFIGURATION ) );
        adjustParameters( mProject.getParameter( ParametersConstants.PATH ) );
        adjustParameters( mProject.getParameter( ParametersConstants.PMD_JAVA_RULESET_NAME ) );
        adjustParameters( mProject.getParameter( ParametersConstants.PMD_JSP_RULESET_NAME ) );
        adjustParameters( mProject.getParameter( ParametersConstants.SOURCES ) );
        adjustParameters( mProject.getParameter( ParametersConstants.VOBS ) );
        adjustParameters( mProject.getParameter( ParametersConstants.UMLQUALITY_SOURCE_XMI ) );
    }

    /**
     * {@inheritDoc} Créé la configuration clearcase et charge l'application et le projet en base
     * 
     * @see org.squale.squalix.core.Task#execute()
     */
    public void execute()
        throws TaskException
    {
        adjustParamsForClearcase();
        try
        {
            mConfiguration = new ClearCaseConfiguration( mProject, mAudit );
            LOGGER.info( ClearCaseMessages.getString( "logs.task.initialized" ) + mProject.getName() );
            /* si la vue existe, on essaye de la démonter. */
            if ( checkViewOrStorageDirectoryExistence() )
            {
                LOGGER.info( ClearCaseMessages.getString( "logs.view.umounting" ) );

                /* si elle ne se démonte pas, on lance une exception. */
                if ( !cleanView() )
                {
                    throw new TaskException( ClearCaseMessages.getString( "exception.view.umount_failed" ) );
                }
                LOGGER.info( ClearCaseMessages.getString( "logs.view.umounted" ) );
            }

            /* on monte la vue. */
            if ( mountView() )
            {
                LOGGER.info( ClearCaseMessages.getString( "logs.view.mounted" ) );

                /* si la vue ne se monte pas */
            }
            else
            {
                /* on lance l'exception en rapport */
                throw new TaskException( ClearCaseMessages.getString( "exception.view.mount_failed" ) );
            }

            /* on modifie les paramètres temporaires . */
            modifyTempMap();

            // positionne les données sur la taille du file System
            affectFileSystemSize( mConfiguration.getWriteDirectory(), true );

        }
        catch ( Exception e )
        {
            throw new TaskException( e );
        }
    }

    /**
     * Cette méthode modifie le paramètre <code>view_path</code> de la <code>
     * HashMap</code> des paramètres
     * temporaires de l'objet <code>
     * ProjectBO</code>.
     * 
     * @throws Exception exception lors du traitement.
     */
    private void modifyTempMap()
        throws Exception
    {
        /*
         * on récupère le chemin du dossier dans lequel ont été mis les fichiers de la vue, et on met cette valeur dans
         * la hashmap de paramètres.
         */
        StringBuffer buf = new StringBuffer( mConfiguration.getUmountViewCommand().trim() );
        int length = buf.length();
        String viewPath = buf.substring( buf.lastIndexOf( ClearCaseConfiguration.SPACE ) + 1, length );
        getData().putData( TaskData.VIEW_PATH, viewPath );
    }

    /**
     * Cette méthode vérifie si une vue est actuellement montée sur la machine et/ou si le répertoire de stockage de la
     * vue existe déjà.
     * 
     * @throws Exception si le processus ne peut être lancé.
     * @return <code>true</code> si elle est actuellement montée et/ou si le répertoire de stockage de la vue existe
     *         déjà, <code>false</code> sinon.
     */
    protected boolean checkViewOrStorageDirectoryExistence()
        throws Exception
    {
        /* initialisation et lancement du processus */
        ProcessManager mgr = new ProcessManager( mConfiguration.getVerifyViewExistenceCommand(), null, new File( "." ) );
        String removeCommand = mConfiguration.getRemoveDirectoryCommand();
        return ( mgr.startProcess( this ) == 0 || new File( removeCommand.substring( removeCommand.indexOf( "/" ),
                                                                                     removeCommand.length() ) ).exists() );
    }

    /**
     * Cette méthode monte la vue ClearCase.
     * 
     * @return <code>true</code> en cas de succès, <code>false</code> sinon.
     * @throws Exception si le script de montage de la vue a produit une ou plusieurs erreurs.
     */
    protected boolean mountView()
        throws Exception
    {
        String cmdLine;

        /* Vue de travail, i.e. audit de suivi. */
        if ( mAudit.getType().equals( AuditBO.NORMAL ) )
        {
            /* on recupere la commande */
            cmdLine = mConfiguration.getMountWorkViewCommand();

            /* Vue de consultation, i.e. audit de jalon. */
        }
        else if ( mAudit.getType().equals( AuditBO.MILESTONE ) )
        {
            cmdLine = mConfiguration.getMountConsultationViewCommand();

            /* Pas de type d'audit trouvé. On ne monte pas de vue. */
        }
        else
        {
            /* et on lance l'exception en rapport */
            throw new Exception( ClearCaseMessages.getString( "exception.audit.missing_type" ) );
        }

        LOGGER.info( cmdLine );
        ProcessManager mgr = new ProcessManager( cmdLine, null, new File( "." ) );
        return mgr.startProcess( this ) == 0;
    }

    /**
     * Supprime / démonte la vue ClearCase
     * 
     * @throws Exception si la commande UNIX produit des erreurs.
     * @return <code>true</code> si la vue est correctement démontée, <code>
     * false</code> sinon.
     */
    protected boolean cleanView()
        throws Exception
    {
        boolean cleaningOk = false;
        /* initialisation et lancement du processus */
        ProcessManager mgr = new ProcessManager( mConfiguration.getUmountViewCommand(), null, new File( "." ) );
        cleaningOk = mgr.startProcess( this ) == 0;
        // Si on a pu supprimer la vue avec la commande normale, c'est bon
        // sinon 2 cas: soit le .vws n'existe plus, alors on doit supprimer récursivement le répertoire
        // soit c'est le répertoire qui n'existe plus, mais la vue existe et il faut supprimer le .vws
        if ( !cleaningOk )
        {
            // Commande de suppression du répertoire
            String removeDircommand = mConfiguration.getRemoveDirectoryCommand();
            // Teste l'existence du répertoire, on ne tente de le supprimeer que si il existe sinon il faut supprimer
            // le .vws
            File f =
                new File( removeDircommand.substring( removeDircommand.indexOf( "/" ), removeDircommand.length() ) );
            if ( f.exists() )
            {
                mgr = new ProcessManager( removeDircommand, null, new File( "." ) );
                LOGGER.info( "Remove dir command:" + removeDircommand );
                cleaningOk = mgr.startProcess( this ) == 0;
            }
            if ( !cleaningOk )
            { // sinon c'est le .vws à supprimer
                mgr = new ProcessManager( mConfiguration.getAuxUmountViewCommand(), null, new File( "." ) );
                LOGGER.info( "Remove .view command:" + mConfiguration.getAuxUmountViewCommand() );
                cleaningOk = mgr.startProcess( this ) == 0;
            }
        }
        return cleaningOk;
    }

    /**
     * Getter. Pour des considérations de test unitaire uniquement.
     * 
     * @return mConfiguration la configuration de la tâche.
     */
    public ClearCaseConfiguration getConfiguration()
    {
        return mConfiguration;
    }

    /**
     * Setter. Pour des considérations de test unitaire uniquement.
     * 
     * @param pConfiguration la configuration de la tâche.
     */
    public void setConfiguration( ClearCaseConfiguration pConfiguration )
    {
        mConfiguration = pConfiguration;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.squalix.util.process.ProcessErrorHandler#processError(java.lang.String)
     */
    public void processError( String pErrorMessage )
    {
        // on filtre les erreurs
        // les 2 messages ci-dessous ne sont pas pris en compte
        if ( pErrorMessage.toLowerCase().startsWith( "cleartool: " )
            && !pErrorMessage.toLowerCase().startsWith( "cleartool: error: no matching entries found for view tag" ) )
        {
            initError( pErrorMessage );
        }
    }
}
