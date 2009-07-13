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
package org.squale.welcom.taglib.aide;

import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ResponseUtils;
import org.squale.welcom.outils.Util;
import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.taglib.button.ButtonTag;


/**
 * Generated tag class. Ce tag n'est pas renseigné dans la TLD de welcom
 */
public class AideTag
    extends BodyTagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = -6893823684557475693L;

    /** property declaration for tag attribute: key. */
    private String key;

    /** property declaration for tag attribute: name. */
    private String name = "aide";

    /** property declaration for tag attribute: type. */
    private String type = "";

    /** property declaration for tag attribute: divOngletName. */
    private String divOngletName = "";

    /** property declaration for tag attribute: vue. */
    private String vue = "";

    /** Message resource */
    private MessageResources resources;

    /** locale */
    private Locale locale;

    /**
     * Constructeur
     */
    public AideTag()
    {
        super();
    }

    /**
     * . This method is called when the JSP engine encounters the start tag, after the attributes are processed.
     * Scripting variables (if any) have their values set here.
     * 
     * @return EVAL_BODY_INCLUDE if the JSP engine should evaluate the tag body, otherwise return SKIP_BODY. This method
     *         is automatically generated. Do not modify this method. Instead, modify the methods that this method
     *         calls.
     * @throws JspException exception pouvant etre levee
     */
    public int doStartTag()
        throws JspException
    {
        if ( !Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.AIDE_ACTIVE ) ) )
        {
            return SKIP_BODY;
        }
        resources = (MessageResources) pageContext.getServletContext().getAttribute( Globals.MESSAGES_KEY );
        // Recupere la locale
        locale = (Locale) pageContext.getSession().getAttribute( Globals.LOCALE_KEY );
        // Si la locale est nulle alors la force en francais
        if ( locale == null )
        {
            locale = Locale.getDefault();
        }
        // pageContext.getServletContext().getRealPath(Aide.getUrlAide(key, locale))
        StringBuffer results = new StringBuffer();
        try
        {
            writeButton( results );
        }
        catch ( final AideException ae )
        {
            results = new StringBuffer();
        }
        // Publie
        ResponseUtils.write( pageContext, results.toString() );
        return SKIP_BODY;
    }

    /**
     * Ecrit le boutton
     * 
     * @param results resultat
     * @throws AideException probleme sur l'aide
     * @throws JspException exception
     */
    private void writeButton( StringBuffer results )
        throws AideException, JspException
    {
        if ( type == null )
        {
            // Genere le tag de l'aide
            writeDefaultButton( results );
        }
        else
        {
            if ( type.equals( "ongletHiddenHyperlink" ) )
            {
                // Genere le tag de l'aide
                results.append( "<input type=\"" );
                results.append( "hidden" );
                results.append( "\" name=\"" );
                results.append( name );
                results.append( "\" value=\"" );
                results.append( Aide.getUrlAide( key, locale ) );
                results.append( "\">" );
            }
            else if ( type.equals( "ongletTitreHyperlink" ) )
            {
                // Genere le tag de l'aide
                results.append( "<a styleId=\"lien\" " );
                results.append( "href=\"javascript:openHelp(calculeURL(\'" );
                results.append( divOngletName );
                results.append( "\'));\">" );
                results.append( resources.getMessage( locale, "aide" ) );
                results.append( "</a>" );

                if ( GenericValidator.isBlankOrNull( divOngletName ) )
                {
                    throw new JspException(
                                            "Aide Erreur : Type ongletTitreyperlink doit renseigner le propriété 'divOngletName'" );
                }
            }
            else if ( type.equals( "FormHyperlink" ) )
            {
                // Genere le tag de l'aide
                results.append( "<a  onclick=\"this.blur()\" class=\"mymenu\" href=\"javascript:openHelp(\'" );
                results.append( Aide.getUrlAide( key, locale ) );
                results.append( "\');\">" );
                results.append( "<img src=\"images/form/fr/bouton_aide.gif\" border=\"0\">" );
                results.append( "</a>" );
            }
            else if ( type.equals( "MenuHyperlink" ) )
            {
                // Genere le tag de l'aide
                final ButtonTag bt = new ButtonTag();
                bt.setPageContext( pageContext );
                bt.setParent( getParent() );
                bt.setName( "aide" );
                bt.setOnclick( "javascript:openHelp(\'" + Aide.getUrlAide( key, locale ) + "\')" );
                bt.doStartTag();
                bt.doEndTag();
            }
            else if ( type.equals( "MenuHyperlinkOnglet" ) )
            {
                // Genere le tag de l'aide
                final ButtonTag bt = new ButtonTag();
                bt.setPageContext( pageContext );
                bt.setParent( getParent() );
                bt.setName( "aide" );
                bt.setOnclick( "javascript:openHelp(calculeURL(\'" + divOngletName + "\'));" );
                bt.doStartTag();
                bt.doEndTag();

                if ( GenericValidator.isBlankOrNull( divOngletName ) )
                {
                    throw new JspException(
                                            "Aide Erreur : Type MenuHyperlinkOnglet doit renseigner le propriété 'divOngletName'" );
                }
            }
            else
            {
                writeDefaultButton( results );
            }
        }
    }

    /**
     * Bouton par defaut
     * 
     * @param results stringbuffer
     * @throws AideException aide
     */
    private void writeDefaultButton( StringBuffer results )
        throws AideException
    {
        // Genere le tag de l'aide
        results.append( "<a styleId=\"lien\" " );
        results.append( "href=\"javascript:openHelp(\'" );
        results.append( Aide.getUrlAide( key, locale ) );
        results.append( "\');\">" );
        results.append( resources.getMessage( locale, "aide" ) );
        results.append( "</a>" );

    }

    /**
     * Accesseur
     * 
     * @return la key
     */
    public String getKey()
    {
        return key;
    }

    /**
     * Accesseur
     * 
     * @param value la nouvelle valeur de key
     */
    public void setKey( final String value )
    {
        key = value;
    }

    /**
     * Accesseur
     * 
     * @return le name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Accesseur
     * 
     * @param value la nouvelle valeur de name
     */
    public void setName( final String value )
    {
        name = value;
    }

    /**
     * Gets the type
     * 
     * @return Returns a String
     */
    public String getType()
    {
        return type;
    }

    /**
     * Sets the type
     * 
     * @param pType The type to set
     */
    public void setType( final String pType )
    {
        type = pType;
    }

    /**
     * Gets the divOngletName
     * 
     * @return Returns a String
     */
    public String getDivOngletName()
    {
        return divOngletName;
    }

    /**
     * Sets the divOngletName
     * 
     * @param pDivOngletName The divOngletName to set
     */
    public void setDivOngletName( final String pDivOngletName )
    {
        divOngletName = pDivOngletName;
    }

    /**
     * Gets the vue
     * 
     * @return Returns a String
     */
    public String getVue()
    {
        return vue;
    }

    /**
     * Sets the vue
     * 
     * @param pVue The vue to set
     */
    public void setVue( final String pVue )
    {
        vue = pVue;
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#release()
     */
    public void release()
    {
        super.release();
        key = "";
        name = "aide";
        type = "";
        divOngletName = "";
        vue = "";
    }

}