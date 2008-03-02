package com.airfrance.squalecommon.datatransfertobject.transform.component.parameters;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.ListParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;

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