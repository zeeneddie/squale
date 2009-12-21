package org.squale.squaleweb.tagslib.security;

import java.io.IOException;

import javax.servlet.jsp.JspException;

/**
 * This tags indicates if the current user has any of determined profiles for a given app. The tag can either output the
 * result to the page or expose it as a scripting variable set in the page scope.
 * 
 * @see NotHasProfileTag
 * @author gfouquet
 */
public class HasProfileTag
    extends AbstractProfileCheckerTag
{
    private static final long serialVersionUID = 3238314595800789525L;

    /**
     * The name of the page-scope attribute which should be set. If null, result is output to page instead.
     */
    private String var;

    public void setVar( String var )
    {
        this.var = var;
    }

    @Override
    public int doStartTag()
        throws JspException
    {
        boolean hasProfile = isUserHasAnySpecifiedProfile();

        if ( shouldExposeResultAsAScopedAttribute() )
        {
            exposeResult( hasProfile );
        }
        else
        {
            outputResult( hasProfile );
        }

        return SKIP_BODY;
    }

    /**
     * exposes the result of tag evaluation as a page-scoped attribute
     * 
     * @param hasProfile
     */
    private void exposeResult( boolean hasProfile )
    {
        pageContext.setAttribute( var, hasProfile );

    }

    /**
     * outputs the result of tag evaluation to the page
     * 
     * @param hasProfile
     * @throws JspException wraps any {@link IOException}
     */
    private void outputResult( boolean hasProfile )
        throws JspException
    {
        try
        {
            pageContext.getOut().write( Boolean.toString( hasProfile ) );
        }
        catch ( IOException e )
        {
            throw new JspException( e );
        }
    }

    /**
     * @return <code>true</code> if the result of tag evaluation should be exposed as a scoped attribute.
     */
    private boolean shouldExposeResultAsAScopedAttribute()
    {
        return var != null;
    }

}
