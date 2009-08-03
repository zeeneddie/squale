package org.squale.squalecommon.util.mapping;

import org.squale.squalecommon.util.messages.BaseMessages;

/**
 * Implementation of the message for mapping
 */
public final class MappingMessages
    extends BaseMessages
{

    /** Create the singleton */
    private static MappingMessages instance = new MappingMessages();

    /**
     * Default constructor 
     */
    private MappingMessages()
    {
        super( "org.squale.squalecommon.util.mapping.mapping_messages" );
    }

    /**
     * Return the string link to the key.
     * 
     * @param pKey The key to search.
     * @return The associated string.
     */
    public static String getString( String pKey )
    {
        return instance.getBundleString( pKey );
    }

}
