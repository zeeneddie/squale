package com.airfrance.squalecommon.datatransfertobject.transform.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.result.MarkDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.ComponentTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MarkBO;

/**
 * bo <--> dto des notes
 */
public class MarkTransform
{

    /**
     * @param pMarks les notes à transformer
     * @return les notes sous forme dto
     */
    public static Collection bo2Dto( Collection pMarks )
    {
        Collection dtos = new ArrayList();
        for ( Iterator it = pMarks.iterator(); it.hasNext(); )
        {
            dtos.add( bo2Dto( (MarkBO) it.next() ) );
        }
        return dtos;
    }

    /**
     * Le composant associé à la note est transformé avec son nom entier
     * 
     * @param pMark la note
     * @return la note sous forme DTO
     */
    public static MarkDTO bo2Dto( MarkBO pMark )
    {
        MarkDTO markDTO = new MarkDTO();
        markDTO.setComponent( ComponentTransform.bo2DtoWithFullName( pMark.getComponent() ) );
        markDTO.setValue( pMark.getValue() );
        return markDTO;
    }
}
