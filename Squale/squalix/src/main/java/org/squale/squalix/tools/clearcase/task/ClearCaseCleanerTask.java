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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\org\\squale\\squalix\\tools\\clearcase\\ClearCaseCleanerTask.java

package org.squale.squalix.tools.clearcase.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalix.core.AbstractSourceTerminationTask;
import org.squale.squalix.core.TaskException;
import org.squale.squalix.tools.clearcase.configuration.ClearCaseConfiguration;
import org.squale.squalix.tools.clearcase.configuration.ClearCaseMessages;
import org.squale.squalix.util.process.ProcessErrorHandler;
import org.squale.squalix.util.process.ProcessManager;

/**
 * Nettoie l'environnement de travail après avoir l'analyse complète d'un projet.<br>
 * Démonte la vue ClearCase notamment.<br>
 * Elle est la dernière tâche exécutée sur un projet avant le calcul des résultats qualité.
 * 
 * @author m400832
 * @version 2.1
 */
public class ClearCaseCleanerTask
    extends AbstractSourceTerminationTask
    implements ProcessErrorHandler
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( ClearCaseCleanerTask.class );

    /**
     * Instance de configuration.
     */
    protected ClearCaseConfiguration mConfiguration;

    /**
     * Constructeur.
     * 
     * @roseuid 42B97E86009E
     */
    public ClearCaseCleanerTask()
    {
        mName = "ClearCaseCleanerTask";
    }

    // Cette tache n'a pas d'influence dans le calcul de la taille max du file system

    /**
     * Méthode de lancement de la tâche de démontage.
     * 
     * @throws TaskException en cas d'échec
     * @roseuid 42AE95AB03C8
     */
    public void execute()
        throws TaskException
    {
        /* récupère la configuration clearcase */
        try
        {
            mConfiguration = new ClearCaseConfiguration( mProject, mAudit );
            /* démontage */
            umount();
        }
        catch ( Exception e )
        {
            throw new TaskException( e );
        }
    }

    /**
     * Méthode créée pour éviter une pieuvre.
     * 
     * @see #cleanView()
     * @throws Exception exception.
     */
    private void umount()
        throws Exception
    {
        /* suppression de la vue */
        if ( cleanView() )
        {
            LOGGER.info( ClearCaseMessages.getString( "logs.view.umounted" ) );

            /* si la vue n'a pu être démontée */
        }
        else
        {
            /* exception lancée */
            throw new Exception( ClearCaseMessages.getString( "exception.view.umount_failed" ) );
        }
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
        /* instanciation du process et exécution */
        ProcessManager mgr = new ProcessManager( mConfiguration.getUmountViewCommand(), null, null );

        /* si le processus se termine correctement */
        return ( mgr.startProcess( this ) == 0 );
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.squale.squalix.util.process.ProcessErrorHandler#processError(java.lang.String)
     */
    public void processError( String pErrorMessage )
    {
        initError( pErrorMessage );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean doOptimization()
    {
        boolean doOptimization = false;
        if ( ClearCaseMessages.getString( "properties.task.optimization" ).equals( "true" ) )
        {
            doOptimization = true;
        }
        return doOptimization;
    }
}
