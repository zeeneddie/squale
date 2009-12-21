package org.squale.squaleweb.applicationlayer.formbean;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import org.squale.welcom.outils.Access;

/**
 * Test d'exploration des fonctionnalités de LogonBean
 * 
 * @author gfouquet
 */
public class LogonBeanTryoutTest
{
    private LogonBean testedObject;
    
    @Test
    public void aManagerShouldHaveWriteAccessOnManagerRight() {
        // given
        aManagerIsLogged();

        // when
        String access = testedObject.getAccess( "manager" );

        // then
        assertThat( access, is( Access.READWRITE ) );
    }

    private void aManagerIsLogged()
    {
        testedObject = new LogonBean();

        testedObject.setCurrentAccess( ProfileBO.MANAGER_PROFILE_NAME );
    }

    @Test
    public void aManagerShouldHaveWriteAccessOnReaderRight()
    {
        // given
        aManagerIsLogged();

        // when
        String access = testedObject.getAccess( "reader" );

        // then
        // ce à quoi on pourrait s'attendre
        // assertThat( access, is( Access.READWRITE ) );

        // mais LoginBean répond systématiquement "readonly" pour le droit "reader"
        assertThat( access, is( Access.READONLY ) );
    }

}
