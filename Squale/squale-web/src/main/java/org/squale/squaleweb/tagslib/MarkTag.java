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
package org.squale.squaleweb.tagslib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import org.squale.squaleweb.util.SqualeWebActionUtils;

/**
 * Juste pour afficher la note sous une forme plus agréable avec un formatage Ce tag a été créé car l'algo de tendance
 * travaille sur les float complets
 */
public class MarkTag
    extends TagSupport
{

    /** le paramètre "nom" du tag */
    private String name;

    /** la note du tag */
    private String mark;

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag() {@inheritDoc} Méthode de lancement du tag
     */
    public int doStartTag()
        throws JspException
    {
        // Publie
        ResponseUtils.write( pageContext, format( (String) RequestUtils.lookup( pageContext, name, mark, null ) ) );
        return SKIP_BODY;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag() {@inheritDoc} Méthode de lancement du tag
     */
    public int doEndTag()
        throws JspException
    {
        return EVAL_PAGE;
    }

    /**
     * formate la chaine correspondant à la note
     * 
     * @param markToFormat la note à formater
     * @return la note formatée
     */
    private String format( String markToFormat )
    {
        // Si la note est nulle on affiche un "-"
        String result = "<img src=\"" + "images/pictos/na.gif" + "\" border=\"0\" />";
        String markResult = SqualeWebActionUtils.formatFloat( markToFormat );
        // Si on a pu récupérér une note correcte, on la renvoie sinon on affiche le "-"
        if ( !SqualeWebActionUtils.DASH.equals( markResult ) )
        {
            result = markResult;
        }
        return result;
    }

    /**
     * @return la note
     */
    public String getMark()
    {
        return mark;
    }

    /**
     * @return le nom
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param newMark la nouvelle note
     */
    public void setMark( String newMark )
    {
        mark = newMark;
    }

    /**
     * @param newName le nouveau nom
     */
    public void setName( String newName )
    {
        name = newName;
    }

}
