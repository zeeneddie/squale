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
package org.squale.squaleweb.tagslib.security;

import java.util.StringTokenizer;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import org.squale.squaleweb.applicationlayer.formbean.LogonBean;
import org.squale.welcom.struts.util.WConstants;

/**
 * Superclass of tags which check the current user's profile for a given app against a list of profiles.
 * 
 * @author gfouquet
 */
public class AbstractProfileCheckerTag
    extends TagSupport
{

    private static final long serialVersionUID = -7329193681271289937L;
    /**
     * Comma-separated list of {@link ProfileBO} names for which the tag renders its body content.
     */
    protected String profiles;
    /**
     * String representation of id of application for which profile is checked.
     */
    private String applicationId;

    public final void setProfiles( String profiles )
    {
        this.profiles = profiles;
    }

    public final void setApplicationId( String applicationId )
    {
        this.applicationId = applicationId;
    }

    /**
     * @return the current user's profile for the application {@link #applicationId}
     */
    private String getUserProfileForApplication()
    {
        LogonBean user = (LogonBean) pageContext.getAttribute( WConstants.USER_KEY, PageContext.SESSION_SCOPE );
    
        return (String) user.getProfile( applicationId );
    }

    /**
     * @return <code>true</code> if user's profile for {@link #applicationId} is one of {@link #profiles}
     */
    protected final boolean isUserHasAnySpecifiedProfile()
    {
        boolean hasProfile = false;
    
        String userProfileForApp = getUserProfileForApplication();
        StringTokenizer tokenizer = new StringTokenizer( profiles, "," );
    
        while ( tokenizer.hasMoreTokens() )
        {
            String requiredProfile = tokenizer.nextToken().trim();
    
            if ( StringUtils.equals( userProfileForApp, requiredProfile ) )
            {
                hasProfile = true;
                break;
            }
        }

        return hasProfile;
    }

}