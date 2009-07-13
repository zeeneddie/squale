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
package org.squale.welcom.struts.action;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ActionConfig;
import org.squale.welcom.struts.ajax.WHttpConfirmationMessageResponse;
import org.squale.welcom.struts.util.StrutsWatchedTask;
import org.squale.welcom.struts.util.WatchedTask;
import org.squale.welcom.struts.util.WatchedTaskManager;


public class WRunTaskAction
    extends WDispatchAction
{
    /**
     * Generate a unique task ID. The ID is used to update the progress of each task.
     */
    public ActionForward execSchedTask( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response )
        throws IOException, ServletException
    {

        String wAction = request.getParameter( "wWatchedAction" );
        String forwardExecMode = request.getParameter( "wModeForward" );

        try
        {
            ActionConfig cfg = mapping.getModuleConfig().findActionConfig( wAction );
            String taskClass = cfg.getType();

            Class classDesc = Class.forName( taskClass );
            WatchedTask batch = (WatchedTask) classDesc.newInstance();
            batch.init( form, request );

            Object taskId = WatchedTaskManager.getInstance( request ).regTask( batch );

            // Writes the response
            sendAjaxResponse( taskId.toString(), batch, response );

            WatchedTaskManager.getInstance( request ).getWorkQueue().execute( batch );

        }
        catch ( Exception e )
        {
            throw new ServletException( e );
        }

        return null;
    }

    /**
     * Enregistrement d'une tache en vue de son execution.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward registerTask( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response )
        throws IOException, ServletException
    {
        WatchedTask batch = new StrutsWatchedTask();
        batch.init( form, request );
        Object taskId = WatchedTaskManager.getInstance( request ).regTask( batch );

        sendAjaxResponse( taskId.toString(), batch, response );
        return null;
    }

    /**
     * Execution d'une action par forward.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward execTaskForward( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response, WatchedTask task )
        throws IOException, ServletException
    {

        request.getRequestDispatcher( ( (StrutsWatchedTask) task ).getActionURL() ).forward( request, response );

        return null;
    }

    /**
     * Lecture du pourcentage d'avancement de la tache.
     */
    public ActionForward checkProgress( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response, WatchedTask task )
        throws IOException, ServletException
    {

        // Retrieve inputs
        String oldValue = request.getParameter( "wOldProgressValue" );
        String taskId = request.getParameter( "wWatchedTaskId" );

        // Progress has changed => Sends new value
        response.setContentType( "text/xml" );
        response.setHeader( "Cache-Control", "no-cache" );

        sendAjaxResponse( taskId, task, response );

        return null;
    }

    /**
     * Affichage de la liste des actions avec progressbar en cours.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward showBatchList( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response )
        throws IOException, ServletException
    {

        response.setContentType( "text/html" );
        ServletOutputStream out = response.getOutputStream();
        out.println( "<html>" );
        out.println( "<link rel=\"stylesheet\" type=\"text/css\" href=\"theme/charte_v03_001/css/master.css\">" );
        out.println( "<link rel=\"stylesheet\" type=\"text/css\" href=\"theme/welcom-001.css\">" );
        out.println( "<body>" );
        out.println( "<H1>Server batch admin</H1>" );
        out.println( "Pool size : " + WatchedTaskManager.getInstance( request ).getWorkQueue().getPoolSize() + "<BR>" );
        out.println( "Waiting tasks : " + WatchedTaskManager.getInstance( request ).getWorkQueue().getWaitingTasks()
            + "<BR>" );

        out.println( "<table class=\"tblh\"><thead><tr><th>ID</th><th>impl</th><th>progress</th><th>status</th><th>errors</th><th>age(ms)</th></tr></thead>" );
        try
        {
            Collection colTasks = WatchedTaskManager.getInstance( request ).getAllTasks();

            synchronized ( colTasks )
            {

                long now = System.currentTimeMillis();
                Iterator iter = colTasks.iterator();
                final String myClassLignePaire = "clair";// WelcomConfigurator.getMessage(WelcomConfigurator.getCharte().getWelcomConfigFullPrefix()
                                                            // + ".cols.even");
                int i = 0;
                while ( iter.hasNext() )
                {
                    i++;

                    if ( i % 2 == 0 )
                    {
                        out.print( "<tr class=\"" + myClassLignePaire + "\">" );
                    }
                    else
                    {
                        out.print( "<tr class=\"\">" );
                    }

                    WatchedTask batch = (WatchedTask) iter.next();
                    Object taskId = WatchedTaskManager.getInstance( request ).getTaskId( batch );
                    long ageTache = now - batch.getProgress().getCreationDate();

                    out.print( "<td>" + taskId + "</td>" );
                    out.print( "<td>" + batch.getClass().getName() + "</td>" );
                    out.print( "<td>" + batch.getProgress().getPercentComplete() + "</td>" );
                    out.print( "<td>" + batch.getStatus() + "</td>" );
                    out.print( "<td>" + batch.getErrors() + "</td>" );
                    out.print( "<td>" + ageTache + "</td>" );
                    /*
                     * LIen pour la suppression de la tache out.print( "<td><a href=" + request.getContextPath() +
                     * request.getServletPath() + "?action=killBatch&taskId=" + taskId + ">kill</a>" + "</td>");
                     */
                    out.println( "</tr>" );
                }
                out.println( "</tfoot></table></body></html>" );
            }

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Suppression d'une action avec progressbar.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward killBatch( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response )
        throws IOException, ServletException
    {
        String id = request.getParameter( "taskId" );

        WatchedTaskManager.getInstance( request ).killTask( id );

        return mapping.findForward( "success" );
    }

    /**
     * Ecriture des informations sur une tache au format ajax.
     * 
     * @param taskId
     * @param task
     * @param response
     * @throws IOException
     */
    private void sendAjaxResponse( String taskId, WatchedTask task, HttpServletResponse response )
        throws IOException
    {

        WHttpConfirmationMessageResponse wresponse = new WHttpConfirmationMessageResponse( response );
        // wresponse.setRootTag("message");
        wresponse.addItem( "taskid", taskId );
        wresponse.addItem( "progress", task.getProgress().getPercentComplete() + "" );
        wresponse.addItem( "status", task.getStatus() );

        if ( task.getErrors() != null )
        {
            wresponse.addItem( "errors", task.getErrors().getClass().getName() + ":" + task.getErrors().getMessage() );

        }

        wresponse.close();

    }

}
