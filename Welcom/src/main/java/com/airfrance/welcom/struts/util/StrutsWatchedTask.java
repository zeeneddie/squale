package com.airfrance.welcom.struts.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

public class StrutsWatchedTask
    extends WatchedTask
{

    private String actionURL;

    public void init( ActionForm form, HttpServletRequest request )
    {
        actionURL = request.getParameter( "wWatchedAction" );
    }

    public void run()
    {
        // Nothing todo
    }

    public String getActionURL()
    {
        return actionURL;
    }

}
