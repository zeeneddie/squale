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
package org.squale.squaleweb.servlet;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

/**
 * Utility class for set variable in an http session
 */
public class UserSqualeSessionContext
    implements Serializable
{

    /**
     * UID
     */
    private static final long serialVersionUID = 1729256194480504259L;

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
     * Indicate if the shared reposiroy is configured
     */
    private String sharedRepositoryConfigured = "false";

    /**
     * This attribute indicate if a reference file has been import or no
     */
    private Integer importReferenceVersion;

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
     * @return The value of the parameter squaleAdminsMailingList
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

    /**
     * Getter method for the parameter sharedRepositoryConfigured
     * 
     * @return "true" if the Shared repository is configured
     */
    public String getSharedRepositoryConfigured()
    {
        return sharedRepositoryConfigured;
    }

    /**
     * Setter method for the parameter sharedRepositoryConfigured. The attribute could take the value "true" or false"
     * 
     * @param pSharedRepositoryConfigured The new state of the shared repository
     */
    public void setSharedRepositoryConfigured( String pSharedRepositoryConfigured )
    {
        sharedRepositoryConfigured = pSharedRepositoryConfigured;
    }

    /**
     * Getter method for the attribute importReferenceVersion
     * 
     * @return The version of the reference imported or null if no reference has been imported 
     */ 
    public Integer getImportReferenceVersion()
    {
        return importReferenceVersion;
    }

    /**
     * Setter method for the attribute importReferenceVersion
     * 
     * @param pImportReferenceVersion The new reference version 
     */
    public void setImportReferenceVersion( Integer pImportReferenceVersion )
    {
        importReferenceVersion = pImportReferenceVersion;
    }
}
