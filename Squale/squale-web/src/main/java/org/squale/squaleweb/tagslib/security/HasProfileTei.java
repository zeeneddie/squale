package org.squale.squaleweb.tagslib.security;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * Exposes the page-scoped attribute set by a {@link HasProfileTag} or a {@link NotHasProfileTag} as a scripting
 * variable.
 * 
 * @author gfouquet
 */
public class HasProfileTei
    extends TagExtraInfo
{
    private static final VariableInfo[] NO_INFO = {};

    /**
     * Returns information about the scripting variable to be created.
     */
    public VariableInfo[] getVariableInfo( TagData data )
    {

        String var = (String) data.getAttribute( "var" );

        if ( var == null )
        {
            return NO_INFO;
        }

        return new VariableInfo[] { new VariableInfo( var, "java.lang.Boolean", true, VariableInfo.AT_END ) };

    }

}
