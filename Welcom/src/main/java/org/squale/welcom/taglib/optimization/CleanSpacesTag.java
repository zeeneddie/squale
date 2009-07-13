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
 * Créé le 14 avr. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.taglib.optimization;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.squale.welcom.outils.TrimStringBuffer;


/**
 * @author M327837 Suppresion Intelligente des espaces ....
 */
public class CleanSpacesTag
    extends BodyTagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = -2547959677796929093L;

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        String value = getBodyContent().getString();

        try
        {
            final TrimStringBuffer tsb = new TrimStringBuffer();
            tsb.append( value );
            value = tsb.toString();
            final JspWriter writer = pageContext.getOut();
            writer.print( value );
        }
        catch ( final Exception e )
        {
            throw new JspException( "Clean Spaces : " + e.getMessage() );
        }

        return EVAL_PAGE;
    }
}