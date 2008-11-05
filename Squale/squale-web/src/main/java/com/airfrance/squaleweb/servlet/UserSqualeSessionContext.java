package com.airfrance.squaleweb.servlet;

import javax.servlet.http.HttpSession;

/**
 * Utility class for set variable in an http session
 */
public class UserSqualeSessionContext
{

    /**
     * The name
     */
    public static final String USER_SQUALE_SESSION_CONTEXT = "userSqualeSessionContext";

    // Here you will find the list of variable you want put in session
    /**
     * The Squale's administrator mailing list
     */
    private String squaleAdminsMailingList;

    /**
     * This method recover the object UserSqualeSessionContext put in session. If there is not, this method creates one
     * 
     * @param session The http session
     * @return The object UserSqualeSessionContext existent in the http session
     */
    public static UserSqualeSessionContext getContext( HttpSession session )
    {
        UserSqualeSessionContext sessionContext = null;
        sessionContext = (UserSqualeSessionContext) session.getAttribute( USER_SQUALE_SESSION_CONTEXT );

        // If it is not already in session, we put it.
        if ( sessionContext == null )
        {
            sessionContext = new UserSqualeSessionContext();
            setContext( session, sessionContext );
        }

        return sessionContext;
    }

    /**
     * This method set the object UserSqualeSessionContext in the http session. This object will be set in session under
     * the name : UserSqualeSessionContext.USER_SQUALE_SESSION_CONTEXT
     * 
     * @param session The http session
     * @param sessionContext The object UserSqualeSessionContext to set in session
     */
    public static void setContext( HttpSession session, UserSqualeSessionContext sessionContext )
    {
        session.setAttribute( USER_SQUALE_SESSION_CONTEXT, sessionContext );
    }

    /**
     * Getter method for the parameter squaleAdminsMailingList
     * 
     * @return The
     */
    public String getSqualeAdminsMailingList()
    {
        return squaleAdminsMailingList;
    }

    /**
     * Setter method for the parameter squaleAdminsMailingList
     * 
     * @param pSqualeAdminsMailingList The new value of the parameter squaleAdminsMailingList
     */
    public void setSqualeAdminsMailingList( String pSqualeAdminsMailingList )
    {
        squaleAdminsMailingList = pSqualeAdminsMailingList;
    }
}
