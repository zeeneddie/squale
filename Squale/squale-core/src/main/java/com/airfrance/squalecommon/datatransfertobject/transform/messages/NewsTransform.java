package com.airfrance.squalecommon.datatransfertobject.transform.messages;

import com.airfrance.squalecommon.datatransfertobject.message.NewsDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.message.NewsBO;

/**
 * 
 */
public class NewsTransform
{

    /**
     * Transform le dto en bo
     * 
     * @param pDTO le dto à transformer
     * @return le bo
     */
    public static NewsBO dto2Bo( NewsDTO pDTO )
    {
        NewsBO bo = new NewsBO();
        bo.setBeginningDate( pDTO.getBeginningDate() );
        bo.setEndDate( pDTO.getEndDate() );
        bo.setKey( pDTO.getKey() );
        bo.setId( pDTO.getId() );
        return bo;
    }

    /**
     * Transform le bo en dto
     * 
     * @param pBo le bo à transformer
     * @return le dto
     */
    public static NewsDTO bo2Dto( NewsBO pBo )
    {
        NewsDTO dto = new NewsDTO();
        dto.setBeginningDate( pBo.getBeginningDate() );
        dto.setEndDate( pBo.getEndDate() );
        dto.setKey( pBo.getKey() );
        dto.setId( pBo.getId() );
        return dto;
    }

}
