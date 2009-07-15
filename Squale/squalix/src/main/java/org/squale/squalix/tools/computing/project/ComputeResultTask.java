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
/*
 * Créé le 18 août 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.squalix.tools.computing.project;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.enterpriselayer.businessobject.result.ErrorBO;
import org.squale.squalecommon.enterpriselayer.facade.rule.AuditComputing;
import org.squale.squalix.core.AbstractTask;
import org.squale.squalix.tools.computing.ComputingMessages;

/**
 * @author M400843
 */
public class ComputeResultTask
    extends AbstractTask
{

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( ComputeResultTask.class );

    /**
     * Constructeur
     * 
     * @roseuid 430452570082
     */
    public ComputeResultTask()
    {
        mName = "ComputeResultTask";
    }

    // Cette tache n'a pas d'influence dans le calcul de la taille max du file system

    /**
     * @see java.lang.Runnable#run()
     * @roseuid 430452570083
     */
    public void execute()
    {
        String[] tab = { mProject.getName(), mProject.getParent().getName() };
        LOGGER.debug( ComputingMessages.getString( "logs.result.debug.running_task", tab ) );
        // Traitements pour les calculs
        analyze();
    }

    /**
     * Execute la méthode compute() sur toute les regles qualités du projet
     * 
     * @roseuid 430452570092
     */
    private void analyze()
    {
        try
        {
            boolean warning = AuditComputing.computeAuditResult( getSession(), mProject, mAudit );
            if (warning)
            {    
                initError( ComputingMessages.getString("logs.compute.mark_out_of_date") );
            }
        }
        catch ( Exception e )
        {
            String[] tab = { mProject.getName(), mProject.getParent().getName(), "" }; // TODO changer le message
            ErrorBO error =
                new ErrorBO( e.getMessage(), ComputingMessages.getString( "error.cant_compute.proj_proj_rule", tab ),
                             ErrorBO.CRITICITY_FATAL, "task.result.name", mAudit, mProject );
            mErrors.add( error );
            LOGGER.error( e, e );
        }
    }

}
