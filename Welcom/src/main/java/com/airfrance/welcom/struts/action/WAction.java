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
package com.airfrance.welcom.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.airfrance.welcom.struts.util.WatchedTask;
import com.airfrance.welcom.struts.util.WatchedTaskManager;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public abstract class WAction
    extends MAction
{
    /** logger */
    private static Log log = LogFactory.getLog( WAction.class );

    /** Est-ce que l'on verifie le timout */
    protected boolean disabledTimeOut = false;

    /**
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward execute( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
                                  final HttpServletResponse response )
        throws Exception
    {

        ActionForward af = null;

        try
        {

            // Force la locale en FR (Historique)
            // java.util.Locale.setDefault(java.util.Locale.FRENCH);

            if ( !isDisabledTimeOut() )
            {
                WActionUtil.checkSessionTimeout( request );
            }

            // Appele le traitement
            // La seule manière de savoir si on est dans la progressbar est de
            // vérifier l'existence du paramètre wWatchedTaskId
            if ( !GenericValidator.isBlankOrNull( request.getParameter( "wWatchedTaskId" ) ) )
            {
                String taskID = request.getParameter( "wWatchedTaskId" );
                WatchedTask task = WatchedTaskManager.getInstance( request ).getTask( taskID );

                if ( task != null )
                {
                    af = wExecute( mapping, form, request, response, task );
                }
            }
            else
            {
                af = wExecute( mapping, form, request, response );
            }

        }
        catch ( final Exception e )
        {
            if ( !( e instanceof TimeOutException ) )
            {
                log.error( e, e );
            }
            String theMessage = null;
            try
            {
                theMessage = getResources( request ).getMessage( getLocale( request ), e.getMessage() );
            }
            catch ( final Exception e2 )
            {
                throw e;
            }
            if ( !GenericValidator.isBlankOrNull( theMessage ) )
            {
                throw new Exception( theMessage );
            }
            else
            {
                throw e;
            }
        }

        return af;
    }

    /**
     * Fonction sans JDBC embarqué
     * 
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward wExecute( final ActionMapping mapping, final ActionForm form,
                                   final HttpServletRequest request, final HttpServletResponse response )
        throws Exception
    {
        return null;
    }

    /**
     * Fonction avec WatchTask (pour la progressbar)
     * 
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward wExecute( final ActionMapping mapping, final ActionForm form,
                                   final HttpServletRequest request, final HttpServletResponse response,
                                   final WatchedTask task )
        throws Exception
    {
        return null;
    }

    /**
     * @return si le timeout est activé
     */
    public boolean isDisabledTimeOut()
    {
        return disabledTimeOut;
    }

    /**
     * @param b si le timeout est activé
     */
    public void setDisabledTimeOut( final boolean b )
    {
        disabledTimeOut = b;
    }
}