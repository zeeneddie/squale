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
 * Créé le 18 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.taglib.button.impl;

import java.text.MessageFormat;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.validator.GenericValidator;
import org.squale.welcom.outils.Util;
import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.taglib.button.ButtonBarTag;
import org.squale.welcom.taglib.button.IButtonRenderer;
import org.squale.welcom.taglib.formulaire.FormulaireBottomTag;
import org.squale.welcom.taglib.onglet.JSOngletBottomTag;
import org.squale.welcom.taglib.table.TableBottomTag;


/**
 * ButtonSkinv2 : boutons dynamiques (charte v002)
 */
public class ButtonRendererv3001
    extends AbstractButtonRederer
    implements IButtonRenderer
{

    /** le picto par defaut */
    private final static String DEFAULT_PICTO =
        WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV3_BOUTON_ICONE );

    /** le blueAF */
    private final static String COULEUR_BLUE_AF =
        WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV3_BOUTON_BLUEAF );

    /** le blanc */
    private final static String COULEUR_WHITE =
        WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV3_BOUTON_WHITE );

    /**
     * retourne la direction en fonction du type
     * 
     * @param type le type du bouton
     * @param name nom du bouton
     * @return la direction
     */
    private String getDirection( final String name, final String type )
    {
        if ( Util.isEquals( "form", type ) )
        {
            String direction = WelcomConfigurator.getMessage( "chartev3.bouton." + type + "." + name + ".alignement" );

            if ( GenericValidator.isBlankOrNull( direction ) )
            {
                direction = WelcomConfigurator.getMessage( "chartev3.bouton." + name + ".alignement" );
            }

            if ( !GenericValidator.isBlankOrNull( direction ) )
            {
                if ( "g".equals( direction ) )
                {
                    return "right";
                }
            }
        }

        return "left";
    }

    /**
     * retourne le path du picto
     * 
     * @param type le type du bouton
     * @param name nom du bouton
     * @param parentTag nom du tag parent
     * @return le pathpicto
     */
    private String getPathPicto( final Tag parentTag, final String name, final String type )
    {
        String couleur = COULEUR_BLUE_AF;

        if ( ( parentTag instanceof TableBottomTag ) || ( parentTag instanceof JSOngletBottomTag )
            || ( parentTag instanceof ButtonBarTag ) || ( parentTag instanceof FormulaireBottomTag ) )
        {
            couleur = COULEUR_WHITE;
        }

        String picto = WelcomConfigurator.getMessage( "chartev3.bouton." + type + "." + name + ".icone", couleur );

        if ( !GenericValidator.isBlankOrNull( picto ) )
        {
            return picto;
        }
        else
        {
            picto = WelcomConfigurator.getMessage( "chartev3.bouton." + name + ".icone", couleur );

            if ( !GenericValidator.isBlankOrNull( picto ) )
            {
                return picto;
            }
            else
            {
                Object[] o = new Object[1];
                o[0] = couleur;
                return MessageFormat.format( DEFAULT_PICTO, o );
            }
        }
    }

    /**
     * retourn le class du form
     * 
     * @param type le type du bouton
     * @param name nom du bouton
     * @param parentTag nom du tag parent
     * @return le class du form
     */
    private String getFormClass( final Tag parentTag, final String name, final String type )
    {
        if ( ( parentTag instanceof TableBottomTag ) || ( parentTag instanceof JSOngletBottomTag )
            || ( parentTag instanceof ButtonBarTag ) || ( parentTag instanceof FormulaireBottomTag ) )
        {
            return "btn " + getDirection( name, type );
        }
        else
        {
            return "btn " + getDirection( name, type ) + " white ";
        }
    }

    /**
     * @see org.squale.welcom.taglib.button.IButtonRenderer#drawRenderHRefTag(StringBuffer results)
     */
    public String drawRenderHRefTag( final PageContext pageContext, final Tag parentTag, final String name,
                                     final String rawValue, final String target, final String href,
                                     final String onclick, final String toolTip, final String styleId )
    {
        StringBuffer results = new StringBuffer();
        results.append( "<a href=\"" + href );
        results.append( "\" " );

        if ( !GenericValidator.isBlankOrNull( onclick ) )
        {
            results.append( "onClick=\"" + onclick + "\" " );
        }

        if ( !GenericValidator.isBlankOrNull( toolTip ) )
        {
            results.append( toolTip );
        }

        if ( !GenericValidator.isBlankOrNull( styleId ) )
        {
            results.append( " id=\"" + styleId + "\"" );
        }

        results.append( " class=\"" + getFormClass( parentTag, name, "form" ) + "\"" );
        results.append( " style=\"BACKGROUND-IMAGE: url(" + getPathPicto( parentTag, name, "form" ) + ")\"" );
        results.append( " >" );
        results.append( getLabel( pageContext, name, rawValue, "form" ) );
        results.append( "</a>" );
        return results.toString();
    }

    /**
     * @see org.squale.welcom.taglib.button.IButtonRenderer#drawRenderMenuHRefTag(StringBuffer results)
     */
    public String drawRenderMenuHRefTag( final PageContext pageContext, final Tag parentTag, final String name,
                                         final String rawValue, final String target, final String href,
                                         final String onclick, final String toolTip, final String styleId )
    {
        StringBuffer results = new StringBuffer();
        // div.menuAction
        results.append( "<li><a href=\"" + href );
        results.append( "\" " );

        if ( !GenericValidator.isBlankOrNull( onclick ) )
        {
            results.append( "onClick=\"" + onclick + "\" " );
        }

        if ( !GenericValidator.isBlankOrNull( toolTip ) )
        {
            results.append( toolTip );
        }

        if ( !GenericValidator.isBlankOrNull( styleId ) )
        {
            results.append( " id=\"" + styleId + "\"" );
        }

        if ( !GenericValidator.isBlankOrNull( target ) )
        {
            results.append( " target=\"" + target + "\" " );
        }

        results.append( " style=\"BACKGROUND-IMAGE:url(" + getPathPicto( parentTag, name, "menu" ) + ")\"" );
        results.append( " >" );
        results.append( getLabel( pageContext, name, rawValue, "menu" ) );
        results.append( "</a></li>" );
        return results.toString();
    }

    /**
     * @see org.squale.welcom.taglib.button.IButtonRenderer#drawRenderFormHRefTag(StringBuffer results)
     */
    public String drawRenderFormHRefTag( final PageContext pageContext, final Tag parentTag, final String name,
                                         final String rawValue, final String target, final String onclick,
                                         final String toolTip, final String styleId )
    {
        StringBuffer results = new StringBuffer();
        results.append( "<input" );

        if ( !GenericValidator.isBlankOrNull( toolTip ) )
        {
            results.append( toolTip );
        }

        if ( !GenericValidator.isBlankOrNull( styleId ) )
        {
            results.append( " id=\"" + styleId + "\"" );
        }

        results.append( " class=\"" + getFormClass( parentTag, name, "form" ) + "\" type=\"button\" name=\"" + name
            + "\" " );

        results.append( " style=\"BACKGROUND-IMAGE: url(" + getPathPicto( parentTag, name, "form" ) + ")\"" );

        // results.append(" onMouseOver=\"rollover"+typebtn+"(true,this)\"
        // onMouseOut=\"rollover"+typebtn+"(false,this)\"");
        results.append( " onclick=\"" + onclick + "\"" );
        results.append( " value=\"" + getLabel( pageContext, name, rawValue, "form" ) + "\"" );
        results.append( " />" );
        return results.toString();
    }

    /**
     * @see org.squale.welcom.taglib.button.IButtonRenderer#drawRenderFormInputTag(StringBuffer results)
     */
    public String drawRenderFormInputTag( final PageContext pageContext, final Tag parentTag, final String name,
                                          final String rawValue, final String target, final String onclick,
                                          final String toolTip, final String styleId )
    {
        StringBuffer results = new StringBuffer();

        // Si c'est un bouton mineur, c'est un Href
        if ( ( parentTag instanceof TableBottomTag ) || ( parentTag instanceof JSOngletBottomTag )
            || ( parentTag instanceof ButtonBarTag ) || ( parentTag instanceof FormulaireBottomTag ) )
        {
            results.append( "<input" );

            if ( !GenericValidator.isBlankOrNull( toolTip ) )
            {
                results.append( toolTip );
            }

            if ( !GenericValidator.isBlankOrNull( styleId ) )
            {
                results.append( " id=\"" + styleId + "\"" );
            }

            results.append( " class=\"" + getFormClass( parentTag, name, "form" ) + "\" type=\"submit\" name=\"" + name
                + "\" " );

            results.append( " style=\"BACKGROUND-IMAGE: url(" + getPathPicto( parentTag, name, "form" ) + ")\"" );

            // results.append(" onMouseOver=\"rollover(true,this)\" onMouseOut=\"rollover(false,this)\"");
            results.append( " onclick=\"" + onclick + "\"" );
            results.append( " value=\"" + getLabel( pageContext, name, rawValue, "form" ) + "\"" );
            results.append( " />" );
        }
        else
        {
            results.append( drawRenderFormHRefTag( pageContext, parentTag, name, rawValue, target, onclick, toolTip,
                                                   styleId ) );
        }
        return results.toString();
    }
}