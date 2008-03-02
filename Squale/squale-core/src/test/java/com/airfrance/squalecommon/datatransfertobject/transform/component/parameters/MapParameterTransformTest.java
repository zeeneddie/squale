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
