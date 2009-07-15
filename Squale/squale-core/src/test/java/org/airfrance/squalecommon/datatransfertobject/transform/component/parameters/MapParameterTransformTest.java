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
package com.airfrance.squalecommon.datatransfertobject.transform.component.parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.ListParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;

/**
 * Test pour MapParameterTransform
 */
public class MapParameterTransformTest
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
        MapParameterDTO mapDTO = new MapParameterDTO();
        Map map = new HashMap();
        StringParameterDTO strParamDTO = new StringParameterDTO();
        strParamDTO.setValue( "Test" );
        map.put( "testStr", strParamDTO );
        ListParameterDTO listParamDTO = new ListParameterDTO();
        List list = new ArrayList();
        list.add( strParamDTO );
        listParamDTO.setParameters( list );
        map.put( "testList", listParamDTO );
        MapParameterDTO mapParamDTO = new MapParameterDTO();
        Map map2 = new HashMap();
        map2.put( "testStr2", strParamDTO );
        mapParamDTO.setParameters( map2 );
        map.put( "testMap", mapParamDTO );
        mapDTO.setParameters( map );
        MapParameterBO mapBO = (MapParameterBO) MapParameterTransform.dto2Bo( mapDTO );
        assertEquals( 3, mapBO.getParameters().size() );
    }
}
