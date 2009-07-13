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
package org.squale.welcom.taglib.button;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.squale.welcom.taglib.formulaire.FormulaireBottomTag;
import org.squale.welcom.taglib.onglet.JSOngletBottomTag;
import org.squale.welcom.taglib.onglet.JSOngletItemTag;
import org.squale.welcom.taglib.onglet.JSOngletTag;
import org.squale.welcom.taglib.table.TableBottomTag;
import org.squale.welcom.taglib.table.TableTag;


/**
 * ButtonBarTag Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ButtonBarTag
    extends BodyTagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = -4874608576214137078L;

    /** le bodyTagSupport */
    private BodyTagSupport tag = null;

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        // Recherche si un parent est du bon type
        Tag curParent = null;

        for ( curParent = getParent(); ( curParent != null )
            && !( ( curParent instanceof JSOngletTag ) || ( curParent instanceof JSOngletItemTag ) || ( curParent instanceof TableTag ) ); curParent =
            curParent.getParent() )
        {
            ;
        }

        // INstancie le tage en fonction
        if ( curParent instanceof JSOngletTag )
        {
            tag = new JSOngletBottomTag();
        }
        else if ( curParent instanceof TableTag )
        {
            tag = new TableBottomTag();
        }
        else
        {
            tag = new FormulaireBottomTag();
        }

        tag.setParent( getParent() );

        tag.setPageContext( pageContext );

        return tag.doStartTag();
    }

    /**
     * @param s chaine a parser
     * @return supperssion de <input type=hidden ....>
     */
    public static String purgeBodyHidden( final String s )
    {

        final Pattern reg = Pattern.compile( "(<\\s*input[^>]*>)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );
        final Matcher matcher = reg.matcher( s );
        final StringBuffer sb = new StringBuffer();
        boolean result = matcher.find();
        while ( result )
        {
            if ( matcher.group( 0 ).toLowerCase().indexOf( "hidden" ) > -1 )
            {
                matcher.appendReplacement( sb, "" );
            }
            else
            {
                matcher.appendReplacement( sb, matcher.group( 0 ) );
            }
            result = matcher.find();
        }
        matcher.appendTail( sb );
        return sb.toString();

    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        tag.setBodyContent( getBodyContent() );

        return tag.doEndTag();
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#release()
     */
    public void release()
    {
        super.release();
        tag = null;
    }

}