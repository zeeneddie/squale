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
 * Créé le 26 mai 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.formulaire;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.taglib.renderer.RendererFactory;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class FormulaireBottomTag
    extends BodyTagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 6129630059994913651L;

    /** la bottomvalue */
    private String bottomvalue;

    /** render */
    private static IFormulaireBottomRenderer render =
        (IFormulaireBottomRenderer) RendererFactory.getRenderer( RendererFactory.FORM_BOTTOM_BAR );

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        if ( getBodyContent() != null )
        {
            bottomvalue = getBodyContent().getString();
        }

        ResponseUtils.write( pageContext, render.drawTable( bottomvalue ) );

        return EVAL_PAGE;
    }
}