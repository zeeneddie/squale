/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Créé le 25 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.taglib.optimization;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.validator.GenericValidator;
import org.squale.welcom.outils.WelcomConfigurator;


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