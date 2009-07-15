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
package org.squale.squalecommon.datatransfertobject.transform.component.parameters;

import java.util.ArrayList;
import java.util.List;

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.datatransfertobject.component.parameters.ListParameterDTO;
import org.squale.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;

/**
 * Test pour ListParameterTransform
 */
public class ListParameterTransformTest
    extends SqualeTestCase
{

    /**
     * Test la transformation dto -> bo
     * 
     * @throws Exception si erreur
     */
    public void testDto2Bo()
        throws Exception
    {
        ListParameterDTO listDTO = new ListParameterDTO();
        List list = new ArrayList();
        StringParameterDTO strParamDTO = new StringParameterDTO();
        strParamDTO.setValue( "Test" );
        list.add( strParamDTO );
        listDTO.setParameters( list );
        ListParameterBO listBO = (ListParameterBO) ListParameterTransform.dto2Bo( listDTO );
        assertEquals( 1, listBO.getParameters().size() );
    }
}