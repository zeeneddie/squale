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
 * Créé le 14 avr. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.canvas;

import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.struts.Globals;

import com.airfrance.welcom.outils.Charte;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.optimization.ScriptTag;
import com.airfrance.welcom.taglib.optimization.StyleSheetTag;

/**
 * @author M327836 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CanvasUtil
{
    /**
     * Ajout des CSS et des JS
     * 
     * @param parent le tag parent
     * @param pageContext le page context
     * @throws JspException exception pouvant etre levee lors d'un ajout
     */
    public static void appendCssJs( final Tag parent, final PageContext pageContext )
        throws JspException
    {
        final Charte vcharte = WelcomConfigurator.getCharte();

        // Mastes CSS
        addCss( WelcomConfigurator.getMessageWithFullCfgChartePrefix( ".header.master.css.path" ), parent, pageContext );

        // welcom css
        addCss( WelcomConfigurator.getMessage( WelcomConfigurator.HEADER_LOCALCSS_PATH_KEY ) + "welcom-"
            + vcharte.getVersionMinor() + ".css", parent, pageContext );

        // com calendrier css
        // addCss(WelcomConfigurator.getMessage(HEADER_LOCALCSS_PATH_KEY) + "comCalendrierPopup.css", parent,
        // pageContext);

        // behaviour.js
        addJs( WelcomConfigurator.getMessage( WelcomConfigurator.HEADER_LOCALJS_PATH_KEY ) + "behaviour-1.1.js",
               parent, pageContext );

        // welcom.js
        addJs( WelcomConfigurator.getMessage( WelcomConfigurator.HEADER_LOCALJS_PATH_KEY ) + "welcom.js", parent,
               pageContext );

        // Tarduction des libelles
        addJs( WelcomConfigurator.getMessage( WelcomConfigurator.HEADER_LOCALJS_PATH_KEY ) + "lang/"
            + ( (Locale) pageContext.getSession().getAttribute( Globals.LOCALE_KEY ) ).getLanguage() + ".js", parent,
               pageContext );

        // comCalendrierPopup.js
        String charset = null;
        if ( WelcomConfigurator.getCharte().isV3() )
        {
            charset = "utf-8";
        }
        addJs( WelcomConfigurator.getMessageWithCfgChartePrefix( ".calendrier.jsurl" ), parent, pageContext, charset );
    }

    /**
     * Ajout de la css localisee a l'url specifiee en parametre
     * 
     * @param url adresse de la css
     * @param parent le tag parent
     * @param pageContext le page context
     * @throws JspException exception pouvant etre levee lors d'un ajout
     */
    public static void addCss( final String url, final Tag parent, final PageContext pageContext )
        throws JspException
    {
        final StyleSheetTag sstag = new StyleSheetTag();
        sstag.setPageContext( pageContext );
        sstag.setParent( parent );
        sstag.setSrc( url );
        sstag.doStartTag();
        sstag.doEndTag();
    }

    /**
     * Ajout du js localisee a l'url specifiee en parametre
     * 
     * @param url adresse de la js
     * @param parent le tag parent
     * @param pageContext le page context
     * @param charset charset
     * @throws JspException exception pouvant etre levee lors d'un ajout
     */
    public static void addJs( final String url, final Tag parent, final PageContext pageContext, final String charset )
        throws JspException
    {
        final ScriptTag stag = new ScriptTag();
        stag.setPageContext( pageContext );
        stag.setParent( parent );
        stag.setSrc( url );
        stag.setCharset( charset );
        stag.doStartTag();
        stag.doEndTag();
    }

    /**
     * Ajout du js localisee a l'url specifiee en parametre
     * 
     * @param url adresse de la js
     * @param parent le tag parent
     * @param pageContext le page context
     * @throws JspException exception pouvant etre levee lors d'un ajout
     */
    public static void addJs( final String url, final Tag parent, final PageContext pageContext )
        throws JspException
    {
        addJs( url, parent, pageContext, null );
    }
}