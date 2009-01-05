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
package com.airfrance.welcom.taglib.onglet;

import java.util.Hashtable;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.taglib.renderer.RendererFactory;

/**
 * @author user To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class JSOngletTag
    extends BodyTagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 1470492852260054467L;

    /** parametre du tag */
    protected String styleClass = "";

    /** parametre du tag */
    protected String name = JSOnglet.DEFAULT_NAME;

    /** parametre du tag */
    protected String bottomValue = "";

    /** parametre du tag */
    protected String selectedOnglet = null;

    /** parametre du tag */
    protected boolean isFirstOngletSelected = false;

    /** le JSOnglet */
    protected JSOnglet onglets = new JSOnglet();

    /** render */
    private static IJSOngletRenderer render = (IJSOngletRenderer) RendererFactory.getRenderer( RendererFactory.ONGLET );

    /**
     * @param v la bottomValue
     */
    public void addBottomValue( final String v )
    {
        bottomValue = v;
    }

    /**
     * Ajoute un onglet
     * 
     * @param o l'onglet a ajouter
     */
    public void addOnglet( final JSOngletItem o )
    {
        if ( onglets == null )
        {
            onglets = new JSOnglet();
            onglets.setName( name );
        }

        onglets.addOnglet( o );
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        // récupère l'onglet sélectionné dans la session
        final Hashtable selOnglets = (Hashtable) pageContext.getSession().getAttribute( JSOnglet.SELECTED_TABS );

        if ( selOnglets != null )
        {
            selectedOnglet = (String) selOnglets.get( name + ".selectedtab" );
        }

        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        if ( onglets == null )
        {
            onglets = new JSOnglet();
            onglets.setName( name );
        }

        // vérifie qu'un onglet est sélectionné sinon sélectionne le premier
        final String selname = onglets.checkSelected( this );

        // vérifie que l'onglet sélectionné est celui enregistré dans la session. sinon on remplce celui de la session
        if ( ( selname != null ) && ( selname.equals( selectedOnglet ) == false ) )
        {
            final Hashtable selOnglets = (Hashtable) pageContext.getSession().getAttribute( JSOnglet.SELECTED_TABS );

            if ( selOnglets != null )
            {
                selOnglets.put( name + ".selectedtab", selname );
            }
        }

        // onglets.
        // Affiche le menu que seulement s'il a des items
        if ( onglets.hasChild() )
        {
            final String header = doPrintHeader();
            final String bodyTitle = doPrintBodyTitle();
            ResponseUtils.write( pageContext, header + bodyTitle );
            doPrintBodyCorps(); // S'imprime dedans

            final String footer = doPrintFooter();
            ResponseUtils.write( pageContext, footer );
        }
        else
        {
            ResponseUtils.write( pageContext, "<!-- Pas d'onglets -->" );
        }
        release();
        return EVAL_PAGE;
    }

    /**
     * @return le bodyTitle
     */
    public String doPrintBodyTitle()
    {
        final StringBuffer buf = new StringBuffer();
        buf.append( "<input type=\"hidden\" name=\"" + name + ".selectedtab\" value=\"\">" );
        buf.append( render.drawTitleBar( onglets.doPrintTitle() ) );

        return buf.toString();
    }

    /**
     * appel doPrintCorps de l'onglet
     * 
     * @throws JspException exception pouvant etre levee
     */
    public void doPrintBodyCorps()
        throws JspException
    {
        onglets.doPrintCorps();
    }

    /**
     * @return le header
     */
    public String doPrintHeader()
    {
        final StringBuffer buf = new StringBuffer();
        buf.append( "\t<DIV id=\"" + name + "\">" );

        return buf.toString();
    }

    /**
     * @return le footer
     */
    public String doPrintFooter()
    {
        final StringBuffer buf = new StringBuffer();
        buf.append( "\t</DIV>" );
        buf.append( render.drawTableBottom( bottomValue ) );

        // Ajout du codeOnglet en champs Hidden
        // buf.append("<!-- Fin de la generation des onglets -->\n");
        return buf.toString();
    }

    /**
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param string le name
     */
    public void setName( final String string )
    {
        name = string;
    }

    /**
     * @return selectedOnglet
     */
    public String getSelectedOnglet()
    {
        return selectedOnglet;
    }

    /**
     * @return isFirstOngletSelected
     */
    public boolean isFirstOngletSelected()
    {
        return isFirstOngletSelected;
    }

    /**
     * @param b le isFirstOngletSelected
     */
    public void setFirstOngletSelected( final boolean b )
    {
        isFirstOngletSelected = b;
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#release()
     */
    public void release()
    {
        super.release();
        styleClass = "";
        name = JSOnglet.DEFAULT_NAME;
        bottomValue = "";
        selectedOnglet = null;
        isFirstOngletSelected = false;
        onglets = new JSOnglet();

    }

}