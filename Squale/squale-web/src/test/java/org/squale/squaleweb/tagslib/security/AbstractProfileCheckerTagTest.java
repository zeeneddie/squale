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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.jsp.PageContext;

import org.junit.Before;
import org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import org.squale.squaleweb.applicationlayer.formbean.LogonBean;
import org.squale.welcom.struts.util.WConstants;

public abstract class AbstractProfileCheckerTagTest
{
    /**
     * stubbed page context
     */
    protected final PageContext pageContext = mock( PageContext.class );

    /**
     * stubbed logon bean / user
     */
    private final LogonBean logonBean = mock( LogonBean.class );

    /**
     * id of application for which we're checking the rights.
     */
    protected final String currentApplicationId = "10";

    @Before
    public void setUpPageContext()
    {
        when( pageContext.getAttribute( WConstants.USER_KEY, PageContext.SESSION_SCOPE ) ).thenReturn( logonBean );
    }

    protected void aReaderForCurrentApplicationIsLogged()
    {
        when( logonBean.getProfile( currentApplicationId ) ).thenReturn( ProfileBO.READER_PROFILE_NAME );
    }

    protected void managerProfileIsRequired()
    {
        getTestedObject().setProfiles( ProfileBO.MANAGER_PROFILE_NAME );
    }

    protected void aManagerForCurrentApplicationIsLogged()
    {
        when( logonBean.getProfile( currentApplicationId ) ).thenReturn( ProfileBO.MANAGER_PROFILE_NAME );
    }

    protected void managerOrAdminProfileIsRequired()
    {
        getTestedObject().setProfiles( ProfileBO.MANAGER_PROFILE_NAME + ',' + ProfileBO.ADMIN_PROFILE_NAME );

    }

    protected abstract AbstractProfileCheckerTag getTestedObject();

}