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
public class TableNavigatorRendererV3001
    implements ITableNavigatorRenderer
{
    /** parametre du tag */
    private String imgFirst = "";

    /** parametre du tag */
    private String imgLast = "";

    /**
     * Constructeur
     */
    public TableNavigatorRendererV3001()
    {
        imgFirst = WelcomConfigurator.getMessageWithCfgChartePrefix( ".htmltable.images.path.first" );
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
            sb.append( "\n" + swapParams( getImgTag( srcImg ), labelImg, "&nbsp;", swap ) );
        }
        else
        {
            if ( GenericValidator.isBlankOrNull( formName ) )
            {
                sb.append( "\n<a href=\"" + link + "\">" );
                sb.append( "\n" + swapParams( getImgTag( srcImg ), labelImg, "&nbsp;", swap ) );
                sb.append( "\n</a>" );
            }
            else
            {
                if ( Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV3_LINK_SPAN_COMPATIBILITY ) ) )
                {
                    sb.append( "\n<span class=\"href\" style=\"text-decoration: underline;\" onclick=\"" + link + "\">" );
                    sb.append( "\n" + swapParams( getImgTag( srcImg ), labelImg, "&nbsp;", swap ) );
                    sb.append( "\n</span>" );
                }
                else
                {
                    sb.append( "\n<a href=\"#\" onclick=\"" + link + ";return false;\">" );
                    sb.append( "\n" + swapParams( getImgTag( srcImg ), labelImg, "&nbsp;", swap ) );
                    sb.append( "\n</a>" );
                }
            }
        }
        return sb.toString();
    }

    /**
     * Genere le tag de l'image
     * 
     * @param srcImg source de l'image
     * @return la chiane
     */
    private String getImgTag( String srcImg )
    {
        if ( srcImg != null )
        {
            return "<img src=\"" + srcImg + "\">";
        }
        return null;
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
            return ( ( arg0 != null ) ? arg0 : "" ) + ( ( arg1 != null ) ? arg1 : "" )
                + ( ( arg2 != null ) ? arg2 : "" );
        }
        else
        {
            return ( ( arg2 != null ) ? arg2 : "" ) + ( ( arg1 != null ) ? arg1 : "" )
                + ( ( arg0 != null ) ? arg0 : "" );
        }
    }

    /**
     * @see ITableNavigatorRenderer#drawBar(MessageResources, Locale, String, String, int, int, int, int, String)
     */
    public String drawBar( MessageResources resources, Locale localeRequest, String formName, String href, int pFrom,
                           int pVolume, int pLength, int ipagesPerNavBar, String tableName )
    {

        String txtPrev = resources.getMessage( localeRequest, WelcomConfigurator.WELCOM_INTERNAL_TABLE_PREV );

        if ( ( txtPrev == null ) || Util.isEquals( txtPrev, WelcomConfigurator.WELCOM_INTERNAL_TABLE_PREV ) )
        {
            txtPrev = WelcomConfigurator.getMessage( WelcomConfigurator.WELCOM_INTERNAL_TABLE_PREV );
        }

        String txtNext = resources.getMessage( localeRequest, WelcomConfigurator.WELCOM_INTERNAL_TABLE_NEXT );

        if ( ( txtNext == null ) || Util.isEquals( txtNext, WelcomConfigurator.WELCOM_INTERNAL_TABLE_NEXT ) )
        {
            txtNext = WelcomConfigurator.getMessage( WelcomConfigurator.WELCOM_INTERNAL_TABLE_NEXT );
        }

        final StringBuffer sb = new StringBuffer();

        // Cacule des indicateur necessaire a la navigation
        int ifrom = getIFrom( pFrom, pVolume, pLength, ipagesPerNavBar );
        int iprevious = getIPrevious( pFrom, pLength );
        int inext = getINext( pFrom, pVolume, pLength );
        int to = getITo( pVolume, pLength, ipagesPerNavBar, ifrom );
        int last = getLast( pVolume, pLength );

        sb.append( "<strong>" );

        // ajout les boutons de navigation precedent
        String urlFirst = getURLNavigation( formName, href, 0, tableName );
        String urlPrevisous = getURLNavigation( formName, href, iprevious, tableName );
        boolean hasPrevisousPage = iprevious >= 0;
        boolean isFirstPage = ( pFrom == 0 );

        sb.append( writeFirstPrevisousButton( formName, txtPrev, urlFirst, urlPrevisous, hasPrevisousPage, isFirstPage ) );

        // ajout les numeros de pages
        for ( int i = ifrom; i < to; i += pLength )
        {
            final int pageNumber = ( ( i + pLength / 2 ) / pLength ) + 1;
            if ( Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV3_LINK_SPAN_COMPATIBILITY ) )
                && !GenericValidator.isBlankOrNull( formName ) )
            {
                sb.append( writeNumberOfPageAsSpan( pFrom >= i && pFrom < i + pLength, getURLNavigation( formName,
                                                                                                         href, i,
                                                                                                         tableName ),
                                                    pageNumber ) );
            }
            else
            {
                sb.append( writeNumberOfPageAsHref( pFrom >= i && pFrom < i + pLength,
                                                    !GenericValidator.isBlankOrNull( formName ),
                                                    getURLNavigation( formName, href, i, tableName ), pageNumber ) );
            }

            if ( i < to - 1 )
            {
                sb.append( "&nbsp;" );
            }
        }

        // Ajout des boutons de navigations suivants
        String urlNext = getURLNavigation( formName, href, inext, tableName );
        String urlLast = getURLNavigation( formName, href, last, tableName );
        boolean hasNextPage = ( inext >= 0 ) && ( inext < pVolume );
        boolean isLastPage = !( pVolume > ( pFrom + pLength ) );

        sb.append( writeNextEndButton( formName, txtNext, urlNext, urlLast, hasNextPage, isLastPage ) );

        sb.append( "</strong>" );

        return sb.toString();

    }

    /**
     * Ecrit les boutons previous / first de la barre de navigation
     * 
     * @param formName : nom du formulaire
     * @param txtPrev : text pour le bouton precedent
     * @param urlFirst : url first
     * @param urlPrevisous : url previous
     * @param hasPrevisousPage : s'il possede une page precedent
     * @param isFirstPage : si c'est la premiere page
     * @return les boutons
     */
    private String writeFirstPrevisousButton( String formName, String txtPrev, String urlFirst, String urlPrevisous,
                                              boolean hasPrevisousPage, boolean isFirstPage )
    {
        StringBuffer sb = new StringBuffer();
        if ( isFirstPage )
        { // align=\"absmiddle\" border=\"0\"
            sb.append( writeButtonNav( formName, imgFirst, null, false ) );
            sb.append( writeButtonNav( formName, null, txtPrev, false ) );
        }
        else
        {

            sb.append( writeButtonNav( formName, imgFirst, null, urlFirst, false ) );

            if ( hasPrevisousPage )
            {
                sb.append( writeButtonNav( formName, null, txtPrev, urlPrevisous, false ) );
            }
            else
            {
                sb.append( writeButtonNav( formName, null, txtPrev, false ) );
            }
        }
        return sb.toString();
    }

    /**
     * Ecrit les boutons next / end de la barre de navigation
     * 
     * @param formName : nom du formulaire
     * @param txtNext : text pour le bouton suivant
     * @param urlNext : url next
     * @param urlLast : url last
     * @param hasNextPage : s'il possede une page suivante
     * @param isLastPage : si c'est la derniere page
     * @return les boutons
     */
    private String writeNextEndButton( String formName, String txtNext, String urlNext, String urlLast,
                                       boolean hasNextPage, boolean isLastPage )
    {
        StringBuffer sb = new StringBuffer();
        if ( !isLastPage )
        {
            if ( hasNextPage )
            {
                sb.append( writeButtonNav( formName, null, txtNext, urlNext, true ) );
            }
            else
            {
                sb.append( writeButtonNav( formName, null, txtNext, true ) );
            }

            sb.append( writeButtonNav( formName, imgLast, null, urlLast, true ) );
        }
        else
        {
            sb.append( writeButtonNav( formName, null, txtNext, true ) );
            sb.append( writeButtonNav( formName, imgLast, null, true ) );
        }
        return sb.toString();
    }

    /**
     * Ecrit le numero de page sous forme d'un span
     * 
     * @param isCurrentPage si c'est la page courante
     * @param url l'url
     * @param pageNumber la page en cours
     * @return le liens hypertext
     */
    private String writeNumberOfPageAsSpan( boolean isCurrentPage, String url, int pageNumber )
    {
        StringBuffer sb = new StringBuffer();
        sb.append( "<span  style=\"text-decoration: underline;\"" );
        if ( isCurrentPage )
        {
            sb.append( " onclick=\"" + url + "\"" );
            sb.append( " class=\"href exergue\">" );
            sb.append( "<big>" );
            sb.append( pageNumber );
            sb.append( "</big>" );
        }
        else
        {
            sb.append( " class=\"href\" onclick=\"" + url + "\" >" );
            sb.append( pageNumber );
        }
        sb.append( "</span>" );
        return sb.toString();
    }

    /**
     * Ecrit le numero de page sous forme de liens
     * 
     * @param isCurrentPage si c'est la page courante
     * @param isInFormulaire si on est dans un formulaire alors il faut soumission des valeurs
     * @param url l'url
     * @param pageNumber la page en cours
     * @return le liens hypertext
     */
    private String writeNumberOfPageAsHref( boolean isCurrentPage, boolean isInFormulaire, String url, int pageNumber )
    {
        StringBuffer sb = new StringBuffer();
        sb.append( "<a " );
        if ( !isInFormulaire )
        {
            sb.append( " href=\"" + url + "\"" );
        }
        else
        {
            sb.append( " href=\"#\"" );
            sb.append( " onclick=\"" + url + ";return false;\"" );
        }
        sb.append( " class=\"exergue\">" );
        if ( isCurrentPage )
        {
            sb.append( "<big>" );
        }
        sb.append( pageNumber );
        if ( isCurrentPage )
        {
            sb.append( "</big>" );
        }
        sb.append( "</a>" );

        return sb.toString();
    }

    /**
     * Retourne le dernier element
     * 
     * @param pVolume volume
     * @param pLength longueur
     * @return le dernier element
     */
    private int getLast( int pVolume, int pLength )
    {
        int last = ( pVolume / pLength ) * pLength;

        if ( last == pVolume )
        {
            last = pVolume - pLength;
        }
        return last;
    }

    /**
     * Calcule l'indice jusqu'ou il faut aller
     * 
     * @param pVolume volume
     * @param pLength longueur
     * @param ipagesPerNavBar saut de page en page
     * @param ifrom a partir de ..
     * @return l'indice jusqu'ou il faut aller
     */
    private int getITo( int pVolume, int pLength, int ipagesPerNavBar, int ifrom )
    {
        int to = ifrom + (int) ( ipagesPerNavBar * pLength );
        if ( to > pVolume )
        {
            to = pVolume;
        }
        return to;
    }

    /**
     * Calcule le Inext
     * 
     * @param pFrom a partir de quel postion
     * @param pVolume volume
     * @param pLength longueur de la page
     * @return le Inext
     */
    private int getINext( int pFrom, int pVolume, int pLength )
    {
        int inext = pFrom + pLength;

        if ( inext > pVolume )
        {
            inext = pVolume - pLength;
        }
        return inext;
    }

    /**
     * Calcule le IPrevious
     * 
     * @param pFrom a partir de quel postion
     * @param pLength longeur
     * @return le IPrevious
     */
    private int getIPrevious( int pFrom, int pLength )
    {
        int iprevious = pFrom - pLength;
        if ( iprevious < 0 )
        {
            iprevious = 0;
        }
        return iprevious;
    }

    /**
     * Calcule le ifrom
     * 
     * @param pFrom a partir de quel postion
     * @param pVolume colume total des element
     * @param pLength longeur
     * @param ipagesPerNavBar nombre d'element par page
     * @return le ifrom
     */
    private int getIFrom( int pFrom, int pVolume, int pLength, int ipagesPerNavBar )
    {
        int ifrom = pFrom - ( ipagesPerNavBar * pLength ) / 2;
        if ( ifrom > pVolume )
        {
            ifrom = pVolume - pLength;
        }
        if ( ifrom < 0 )
        {
            ifrom = 0;
        }
        return ifrom;
    }

}
