package org.squale.squaleweb.applicationlayer.action.accessRights;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import org.squale.squaleweb.applicationlayer.formbean.LogonBean;

public class ReaderActionTest
{
    private ReaderAction testedObject = new ReaderAction();

    private final long checkedApplicationId = 10L;

    private final LogonBean stubUser = mock( LogonBean.class );

    @Test
    public void readersShouldHaveAccess()
    {
        // given
        userProfileForCheckedAppIs( ProfileBO.READER_PROFILE_NAME );

        // when
        boolean accessible = testedObject.checkRights( stubUser, checkedApplicationId );

        // then
        assertThat( accessible, is( true ) );
    }


    private void userProfileForCheckedAppIs( String profile )
    {
        when( stubUser.getApplicationRight( checkedApplicationId ) ).thenReturn( profile );
    }

    @Test
    public void managersShouldHaveAccess()
    {
        // given
        userProfileForCheckedAppIs( ProfileBO.MANAGER_PROFILE_NAME );

        // when
        boolean accessible = testedObject.checkRights( stubUser, checkedApplicationId );

        // then
        assertThat( accessible, is( true ) );
    }

    @Test
    public void auditorsShouldHaveAccess()
    {
        // given
        userProfileForCheckedAppIs( ProfileBO.AUDITOR_PROFILE_NAME );

        // when
        boolean accessible = testedObject.checkRights( stubUser, checkedApplicationId );

        // then
        assertThat( accessible, is( true ) );
    }

    @Test
    public void randomUsersShouldNotHaveAccess()
    {
        // given
        userProfileForCheckedAppIs( "some random profile" );

        // when
        boolean accessible = testedObject.checkRights( stubUser, checkedApplicationId );

        // then
        assertThat( accessible, is( false ) );
    }

    @Test
    public void adminUsersShouldHaveAccess()
    {
        // given
        userIsAnAdmin();

        // when
        boolean accessible = testedObject.checkRights( stubUser, checkedApplicationId );

        // then
        assertThat( accessible, is( true ) );
    }

    private void userIsAnAdmin()
    {
        // admin rights are checked using this application-independant method
        when( stubUser.isAdmin() ).thenReturn( true );
    }

}
