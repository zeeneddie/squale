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

import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class StyleSheetTag
    extends TagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = -4919447656470690628L;

    /** Prefix */
    protected String prefix = WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_COMPRESSION_PREFIX_CSS );

    /** src */
    protected String src = "";

    /** debug */
    protected String debug = "false";

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        final JspWriter writer = pageContext.getOut();

        try
        {
            writer.print( "<link rel=\"stylesheet\" type=\"text/css\" href=\"" );
            /*
             * if ((src.indexOf("cmsintranet.airfrance.fr") == -1) ||
             * !(WelcomConfigurator.getMessage(WelcomConfigurator.OPTIFLUX_REDIRECT_CMSINTRANET).equals("false"))) {
             * writer.print(prefix); }
             */
            writer.print( src );
            writer.print( "\">" );
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
}