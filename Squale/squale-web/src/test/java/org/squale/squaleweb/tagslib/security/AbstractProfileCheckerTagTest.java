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