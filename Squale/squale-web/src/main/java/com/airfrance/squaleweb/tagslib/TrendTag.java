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
package com.airfrance.squaleweb.tagslib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.squaleweb.util.SqualeWebActionUtils;

/**
 */
public class TrendTag
    extends TagSupport
{

    /** le paramètre "nom" du tag */
    private String name;

    /** le paramètre "current" du tag qui désigne la note actuelle */
    private String current;

    /** le paramètre "predecessor" du tag qui désigne la note précédente */
    private String predecessor;

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag() {@inheritDoc} Méthode de lancement du tag
     */
    public int doStartTag()
        throws JspException
    {
        // Publie
        ResponseUtils.write( pageContext, getImageForTrend( (String) RequestUtils.lookup( pageContext, name, current,
                                                                                          null ),
                                                            (String) RequestUtils.lookup( pageContext, name,
                                                                                          predecessor, null ) ) );
        return SKIP_BODY;
    }

    /**
     * Obtention de l'image associée à une tendance
     * 
     * @param pCurrentMark la note actuelle
     * @param pPreviousMark l'ancienne note
     * @return l'url avec l'image correspondant à l'évolution
     */
    private String getImageForTrend( String pCurrentMark, String pPreviousMark )
    {
        String result = SqualeWebActionUtils.getImageForTrend( pCurrentMark, pPreviousMark );
        return "<img src=\"" + result + "\" border=\"0\" />";
    }

    /**
     * @return la note courante
     */
    public String getCurrent()
    {
        return current;
    }

    /**
     * @return le nom
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return la note précédente
     */
    public String getPredecessor()
    {
        return predecessor;
    }

    /**
     * change la note courante
     * 
     * @param newCurrent la nouvelle note
     */
    public void setCurrent( String newCurrent )
    {
        current = newCurrent;
    }

    /**
     * change le nom
     * 
     * @param newName la nouvelle note
     */
    public void setName( String newName )
    {
        name = newName;
    }

    /**
     * change la note courante
     * 
     * @param newPredecessor la nouvelle note
     */
    public void setPredecessor( String newPredecessor )
    {
        predecessor = newPredecessor;
    }

}
