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
