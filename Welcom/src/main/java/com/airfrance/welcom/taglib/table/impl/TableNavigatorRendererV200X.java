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
package com.airfrance.welcom.taglib.table.impl;

import java.util.Locale;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.MessageResources;

import com.airfrance.welcom.outils.Charte;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.table.ITableNavigatorRenderer;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class TableNavigatorRendererV200X
    implements ITableNavigatorRenderer
{

    /** parametre du tag */
    private String imgFirst = "";

    /** parametre du tag */
    private String imgPrev = "";

    /** parametre du tag */
    private String imgNext = "";

    /** parametre du tag */
    private String imgLast = "";

    /**
     * Constructeur
     */
    public TableNavigatorRendererV200X()
    {
        imgFirst = WelcomConfigurator.getMessageWithCfgChartePrefix( ".htmltable.images.path.first" );
        imgPrev = WelcomConfigurator.getMessageWithCfgChartePrefix( ".htmltable.images.path.prev" );
        imgNext = WelcomConfigurator.getMessageWithCfgChartePrefix( ".htmltable.images.path.next" );
        imgLast = WelcomConfigurator.getMessageWithCfgChartePrefix( ".htmltable.images.path.last" );
    }

    /**
     * @param formName : id de depart pur l'affichage
     * @param href : lien hypertexte
     * @param from : from
     * @param tableName : nom de la table
     * @return l'urlNavigation
     */
    protected String getURLNavigation( String formName, String href, final int from, String tableName )
    {

        if ( GenericValidator.isBlankOrNull( formName ) )
        {
            return getHref( href, from, tableName );
        }
        else
        {
            return getFormLink( formName, href, from, tableName );
        }
    }

    /**
     * @param pFrom link avec submit
     * @param formName nom du formulaire
     * @param href lien
     * @param tableName nom de la ptable
     * @return le formLink
     */
    protected String getFormLink( String formName, String href, final int pFrom, String tableName )
    {
        final StringBuffer sb = new StringBuffer();
        sb.append( "javascript:tableForward('" + formName + "','" + getHref( href, pFrom, tableName ) + "')" );

        return sb.toString();
    }

    /**
     * Gere le lien pour un id donnée
     * 
     * @param href lien
     * @param tableName nom de la table
     * @param pFrom : a partie de...
     * @return : liens
     */
    protected String getHref( String href, final int pFrom, String tableName )
    {

        return href + "&" + tableName + ".from" + "=" + pFrom;

    }

    /**
     * Style de la classe
     * 
     * @return Style de la classe
     */
    public String getLinkStyleClass()
    {
        if ( WelcomConfigurator.getCharte() == Charte.V2_002 )
        {
            return "gras";
        }
        else
        {
            return "blueAFlientitre";
        }
    }

    /**
     * Ecrire un bouton de navigation
     * 
     * @param srcImg url de l'image
     * @param labelImg libellé
     * @param swap Swap si necessaire ...
     * @param formName nom du fomulaire
     * @return flux html
     */
    private String writeButtonNav( String formName, final String srcImg, final String labelImg, final boolean swap )
    {
        return writeButtonNav( formName, srcImg, labelImg, null, swap );
    }

    /**
     * Ecrire un bouton de navigation
     * 
     * @param srcImg url de l'image
     * @param labelImg libellé
     * @param link liens de navigation
     * @param swap Swap si necessaire ...
     * @param formName nom du formulaire
     * @return flux html
     */
    private String writeButtonNav( String formName, final String srcImg, final String labelImg, final String link,
                                   final boolean swap )
    {
        final StringBuffer sb = new StringBuffer();
        if ( GenericValidator.isBlankOrNull( link ) )
        {
            sb.append( "\n" + swapParams( "<img class=\"page\" src=\"" + srcImg + "\">", labelImg, "&nbsp;", swap ) );
        }
        else
        {
            if ( GenericValidator.isBlankOrNull( formName ) )
            {
                sb.append( "\n<a class=\"" + getLinkStyleClass() + "\" href=\"" + link + "\">" );
                sb.append( "\n" + swapParams( "<img class=\"page\" src=\"" + srcImg + "\">", labelImg, "&nbsp;", swap ) );
                sb.append( "\n</a>" );
            }
            else
            {
                sb.append( "\n<span class=\"hrefred " + getLinkStyleClass() + "\" onclick=\"" + link + "\">" );
                sb.append( "\n" + swapParams( "<img class=\"page\" src=\"" + srcImg + "\">", labelImg, "&nbsp;", swap ) );
                sb.append( "\n</span>" );
            }
        }
        return sb.toString();
    }

    /**
     * Swap les 3 parametres
     * 
     * @param arg0 : arg0
     * @param arg1 : arg1
     * @param arg2 : arg2
     * @param swap : si on swappe
     * @return la concatenation swappé
     */
    private String swapParams( final String arg0, final String arg1, final String arg2, boolean swap )
    {
        if ( !swap )
        {
            return arg0 + arg1 + arg2;
        }
        else
        {
            return arg2 + arg1 + arg0;
        }
    }

    /**
     * @see ITableNavigatorRenderer#drawBar(MessageResources, Locale, String, String, int, int, int, int, String)
     */
    public String drawBar( MessageResources resources, Locale localeRequest, String formName, String href, int pFrom,
                           int pVolume, int pLength, int ipagesPerNavBar, String tableName )
    {

        String txtFirst = getTxtFirst( resources, localeRequest );

        String txtPrev = getTxtPrev( resources, localeRequest );

        String txtNext = getTxtNext( resources, localeRequest );

        String txtLast = getTxtLast( resources, localeRequest );

        final StringBuffer sb = new StringBuffer();

        final int ifrom = ( pFrom / ( ipagesPerNavBar * ( pLength - 0 ) ) ) * ( ipagesPerNavBar * pLength );
        final int iprevious = ifrom - ( ipagesPerNavBar * pLength );
        int inext = ifrom + ( ipagesPerNavBar * pLength );

        if ( inext > ( ( pVolume / pLength ) * pLength ) )
        {
            inext = -1;
        }

        if ( inext > pVolume )
        {
            inext = pVolume - 1;
        }

        int to = ifrom + ( ipagesPerNavBar * pLength );

        if ( to > pVolume )
        {
            to = pVolume;
        }

        int last = ( pVolume / pLength ) * pLength;

        if ( last == pVolume )
        {
            last = pVolume - pLength;
        }

        sb.append( "page&nbsp;" );

        if ( ipagesPerNavBar > 1 )
        {
            sb.append( "<select onchange=\"document.location.href=this.value\"> " );

            for ( int i = ifrom; i < to; i += pLength )
            {
                final int pageNumber = ( i / pLength ) + 1;

                if ( pFrom == i )
                {
                    sb.append( "<option value=\"" + getURLNavigation( formName, href, i, tableName ) + "\" selected>"
                        + pageNumber );
                }
                else
                {
                    sb.append( "<option value=\"" + getURLNavigation( formName, href, i, tableName ) + "\">"
                        + pageNumber );
                }
            }

            sb.append( "</select>\n" );
        }
        else
        {
            sb.append( "" + ( ( ifrom / pLength ) + 1 ) );
        }

        if ( ( pVolume % pLength ) == 0 )
        {
            sb.append( "/" + ( pVolume / pLength ) + "&nbsp;" );
        }
        else
        {
            sb.append( "/" + ( ( pVolume / pLength ) + 1 ) + "&nbsp;" );
        }

        // ajout les boutons de navigation precedent
        String urlFirst = getURLNavigation( formName, href, 0, tableName );
        String urlPrevisous = getURLNavigation( formName, href, iprevious, tableName );
        boolean hasPrevisousPage = iprevious >= 0;
        boolean isFirstPage = ( pFrom == 0 );

        sb.append( writeFirstPreviousButton( formName, txtFirst, txtPrev, urlFirst, urlPrevisous, hasPrevisousPage,
                                             isFirstPage ) );

        sb.append( "." );

        // ajout les boutons de navigation suivant
        boolean isLastPage = !( pVolume > ( pFrom + pLength ) );
        boolean hasNextPage = ( inext >= 0 ) && ( inext < pVolume );
        String urlNext = getURLNavigation( formName, href, inext, tableName );
        String urlLast = getURLNavigation( formName, href, last, tableName );

        sb.append( writeNextLastButton( formName, txtNext, txtLast, isLastPage, hasNextPage, urlNext, urlLast ) );

        return sb.toString();

    }

    /**
     * Message pour le bouton last
     * 
     * @param resources resource
     * @param localeRequest locale
     * @return message
     */
    private String getTxtLast( MessageResources resources, Locale localeRequest )
    {
        String txtLast = resources.getMessage( localeRequest, WelcomConfigurator.WELCOM_INTERNAL_TABLE_LAST );

        if ( ( txtLast == null ) || Util.isEquals( txtLast, WelcomConfigurator.WELCOM_INTERNAL_TABLE_LAST ) )
        {
            txtLast = WelcomConfigurator.getMessage( WelcomConfigurator.WELCOM_INTERNAL_TABLE_LAST );
        }
        return txtLast;
    }

    /**
     * Message pour le bouton next
     * 
     * @param resources resource
     * @param localeRequest locale
     * @return message
     */
    private String getTxtNext( MessageResources resources, Locale localeRequest )
    {
        String txtNext = resources.getMessage( localeRequest, WelcomConfigurator.WELCOM_INTERNAL_TABLE_NEXT );

        if ( ( txtNext == null ) || Util.isEquals( txtNext, WelcomConfigurator.WELCOM_INTERNAL_TABLE_NEXT ) )
        {
            txtNext = WelcomConfigurator.getMessage( WelcomConfigurator.WELCOM_INTERNAL_TABLE_NEXT );
        }
        return txtNext;
    }

    /**
     * Message pour le bouton previsous
     * 
     * @param resources resource
     * @param localeRequest locale
     * @return message
     */
    private String getTxtPrev( MessageResources resources, Locale localeRequest )
    {
        String txtPrev = resources.getMessage( localeRequest, WelcomConfigurator.WELCOM_INTERNAL_TABLE_PREV );

        if ( ( txtPrev == null ) || Util.isEquals( txtPrev, WelcomConfigurator.WELCOM_INTERNAL_TABLE_PREV ) )
        {
            txtPrev = WelcomConfigurator.getMessage( WelcomConfigurator.WELCOM_INTERNAL_TABLE_PREV );
        }
        return txtPrev;
    }

    /**
     * Message pour le bouton first
     * 
     * @param resources resource
     * @param localeRequest locale
     * @return message
     */
    private String getTxtFirst( MessageResources resources, Locale localeRequest )
    {
        String txtFirst = resources.getMessage( localeRequest, WelcomConfigurator.WELCOM_INTERNAL_TABLE_FIRST );

        if ( ( txtFirst == null ) || Util.isEquals( txtFirst, WelcomConfigurator.WELCOM_INTERNAL_TABLE_FIRST ) )
        {
            txtFirst = WelcomConfigurator.getMessage( WelcomConfigurator.WELCOM_INTERNAL_TABLE_FIRST );
        }
        return txtFirst;
    }

    /**
     * Ecrit les boutons next / end de la barre de navigation
     * 
     * @param formName : nom du formulaire
     * @param txtFirst : texte pour le premier
     * @param txtPrev : texte pour le bouton precedent
     * @param urlPrevisous : url previsou
     * @param urlFirst : url first
     * @param hasPrevisousPage : s'il possede une page precedente
     * @param isFirstPage : si c'est la premiere page
     * @return les boutons
     */
    private String writeFirstPreviousButton( String formName, String txtFirst, String txtPrev, String urlFirst,
                                             String urlPrevisous, boolean hasPrevisousPage, boolean isFirstPage )
    {
        StringBuffer sb = new StringBuffer();
        if ( isFirstPage )
        { // align=\"absmiddle\" border=\"0\"
            sb.append( writeButtonNav( formName, imgFirst, txtFirst, false ) );
            sb.append( writeButtonNav( formName, imgPrev, txtPrev, false ) );
        }
        else
        {

            sb.append( writeButtonNav( formName, imgFirst, txtFirst, urlFirst, false ) );

            if ( hasPrevisousPage )
            {
                sb.append( writeButtonNav( formName, imgPrev, txtPrev, urlPrevisous, false ) );
            }
            else
            {
                sb.append( writeButtonNav( formName, imgPrev, txtPrev, false ) );
            }
        }
        return sb.toString();
    }

    /**
     * Ecrit les boutons next / end de la barre de navigation
     * 
     * @param formName : nom du formulaire
     * @param txtNext : text pour le bouton suivant
     * @param txtLast : dernier texte
     * @param urlNext : url next
     * @param urlLast : url last
     * @param hasNextPage : s'il possede une page suivante
     * @param isLastPage : si c'est la derniere page
     * @return les boutons
     */
    private String writeNextLastButton( String formName, String txtNext, String txtLast, boolean isLastPage,
                                        boolean hasNextPage, String urlNext, String urlLast )
    {
        StringBuffer sb = new StringBuffer();
        if ( !isLastPage )
        {
            if ( hasNextPage )
            {
                sb.append( writeButtonNav( formName, imgNext, txtNext, urlNext, true ) );
            }
            else
            {
                sb.append( writeButtonNav( formName, imgNext, txtNext, true ) );
            }

            sb.append( writeButtonNav( formName, imgLast, txtLast, urlLast, true ) );
        }
        else
        {
            sb.append( writeButtonNav( formName, imgNext, txtNext, true ) );
            sb.append( writeButtonNav( formName, imgLast, txtLast, true ) );
        }
        return sb.toString();
    }

}
