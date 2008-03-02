package com.airfrance.squaleweb.transformer.message;

import com.airfrance.squalecommon.datatransfertobject.message.MessagesDTO;
import com.airfrance.squaleweb.messages.MessageProvider;

/**
 * Transformation des messages DTO
 */
public class MessagesDTOTransformer
{
    /**
     * Transformation des messages DTO
     * 
     * @param pMessage messages à transformer (de type MessagesDTO)
     * @return interface vers les messagesDTO
     */
    static public MessageProvider transform( Object pMessage )
    {
        final MessagesDTO messages = (MessagesDTO) pMessage;
        return new MessageProvider()
        {
            public String getMessage( String pLang, String pKey )
            {
                return messages.getMessage( pLang, pKey );
            }
        };
    }
}
