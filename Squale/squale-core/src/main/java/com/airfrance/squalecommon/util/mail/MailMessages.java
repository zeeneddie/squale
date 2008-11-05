package com.airfrance.squalecommon.util.mail;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * Messages for the mail
 */
public final class MailMessages
    extends BaseMessages
{

    /**
     * Instance
     */
    private static MailMessages mInstance = new MailMessages();

    /**
     * Private constructor
     */
    private MailMessages()
    {
        super( "com.airfrance.squalecommon.util.mail.mail_messages" );
    }

    /**
     * Return the message associate to the key
     * 
     * @param pKey The key name
     * @return the message associate to the key
     */
    public static String getString( String pKey )
    {
        return mInstance.getBundleString( pKey );
    }

}
