/*
 * Créé le 25 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.optimization;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * @author M327837
 */
public class ScriptTag
    extends TagSupport
{

    /**
     * 
     */
    private static final long serialVersionUID = -6961407675934903756L;

    /** Prefix js */
    protected String prefix = WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_COMPRESSION_PREFIX_JS );

    /** src */
    protected String src = "";

    /** mode debug */
    protected String debug = "false";

    /** charset */
    protected String charset = null;

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        final JspWriter writer = pageContext.getOut();

        try
        {
            writer.print( "<script type=\"text/JavaScript\" src=\"" );
            /*
             * if ((src.indexOf("cmsintranet.airfrance.fr") == -1) ||
             * !(WelcomConfigurator.getMessage(WelcomConfigurator.OPTIFLUX_REDIRECT_CMSINTRANET).equals("false"))) {
             * writer.print(prefix); }
             */
            writer.print( src + "\"" );
            if ( !GenericValidator.isBlankOrNull( charset ) )
            {
                writer.print( " charset=\"" + charset + "\"" );
            }
            writer.print( ">" );
            writer.print( "</script>" );
        }
        catch ( final IOException e )
        {
            throw new JspException( e.getMessage() );
        }

        return EVAL_PAGE;
    }

    /**
     * @return accesseur
     */
    public String getDebug()
    {
        return debug;
    }

    /**
     * @return accesseur
     */
    public String getSrc()
    {
        return src;
    }

    /**
     * @param string accesseur
     */
    public void setDebug( final String string )
    {
        debug = string;
    }

    /**
     * @param string accesseur
     */
    public void setSrc( final String string )
    {
        src = string;
    }

    /**
     * @return accesseur
     */
    public String getCharset()
    {
        return charset;
    }

    /**
     * @param string accesseur
     */
    public void setCharset( String string )
    {
        charset = string;
    }

}