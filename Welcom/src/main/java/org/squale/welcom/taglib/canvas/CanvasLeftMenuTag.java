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
 * Créé le 14 mai 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.taglib.canvas;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.util.ResponseUtils;
import org.squale.welcom.taglib.renderer.RendererFactory;


/**
 * CanvasLeftMenu Tag
 */
public class CanvasLeftMenuTag
    extends BodyTagSupport
{

    /**
     * 
     */
    private static final long serialVersionUID = -8253987493672223902L;

    /** Clef de stockage */
    public static final String KEY_CANVASLEFT = "org.squale.welcom.CanvasLeft";

    /** Constante la largeur par defaut du canvas */
    private static final int DEFAULT_WIDTH = 140;

    /** la largeur du canvas */
    private int width = DEFAULT_WIDTH;

    /** vrai si le canvas contient des boutons */
    private boolean containsBoutons = false;

    /** vrai si le canvas contient des menus */
    private boolean containsMenu = false;

    /** le body */
    private String body = "";

    /** render */
    private ICanvasLeftMenuTagRenderer render =
        (ICanvasLeftMenuTagRenderer) RendererFactory.getRenderer( RendererFactory.CANVAS_LEFT );

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {

        pageContext.getRequest().setAttribute( KEY_CANVASLEFT, this );

        final String startContent = render.drawStart();
        if ( startContent != null )
        {
            ResponseUtils.write( pageContext, startContent );
        }

        return super.doStartTag();
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        if ( getBodyContent() != null )
        {
            body = getBodyContent().getString();
        }

        ResponseUtils.write( pageContext, render.drawEnd( body, width, containsMenu, isContainsBoutons() ) );

        return EVAL_PAGE;
    }

    /**
     * Accesseur
     * 
     * @return la largeur
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Accesseur
     * 
     * @param i la largeur
     */
    public void setWidth( final int i )
    {
        width = i;
    }

    /**
     * Accesseur
     * 
     * @return vrai si le canvas contient des boutons
     */
    public boolean isContainsBoutons()
    {
        return containsBoutons;
    }

    /**
     * Accesseur
     * 
     * @param b la nouvelle valeur
     */
    public void setContainsBoutons( final boolean b )
    {
        containsBoutons = b;
    }

    /**
     * Accesseur
     * 
     * @return vrai si le canvas contient des menus
     */
    public boolean isContainsMenu()
    {
        return containsMenu;
    }

    /**
     * Accesseur
     * 
     * @param b la nouvelle valeur
     */
    public void setContainsMenu( final boolean b )
    {
        containsMenu = b;
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#release()
     */
    public void release()
    {
        super.release();
        width = DEFAULT_WIDTH;
        containsBoutons = false;
        containsMenu = false;
        body = "";
    }

}