package com.airfrance.squalecommon.datatransfertobject.transform.messages;

import com.airfrance.squalecommon.datatransfertobject.message.MessageDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.message.MessageBO;

/**
 * 
 */
public class MessageTransform {

    /**
     * Transform le dto en bo
     * @param pDTO le dto à transformer
     * @return le bo
     */
    public static MessageBO dto2Bo(MessageDTO pDTO) {
        MessageBO bo = new MessageBO();
        bo.setText(pDTO.getText());
        bo.setLang(pDTO.getLang());
        bo.setTitle(pDTO.getTitle());
        bo.setKey(pDTO.getKey());
        return bo;
    }

    /**
     * Transform le bo en dto
     * @param pBo le bo à transformer
     * @return le dto
     */
    public static MessageDTO bo2Dto(MessageBO pBo) {
        MessageDTO dto = new MessageDTO();
        dto.setText(pBo.getText());
        dto.setLang(pBo.getLang());
        dto.setTitle(pBo.getTitle());
        dto.setKey(pBo.getKey());
        return dto;
    }

}
