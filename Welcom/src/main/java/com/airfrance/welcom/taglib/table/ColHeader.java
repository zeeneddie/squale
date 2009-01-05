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
 * Créé le 28 févr. 07
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import java.util.Locale;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.MessageResources;

import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ColHeader
{

    /** Colonne */
    private Col col;

    /** critére de trie de la colonne */
    private ColSort colSort;

    /** locale de l'utlisateur */
    private Locale localeRequest;

    /** message resource */
    private MessageResources resources;

    /** Constante */
    private final static String IMG_NONE =
        WelcomConfigurator.getMessageWithCfgChartePrefix( ".htmltable.images.sort.none" );

    /** Constante */
    private final static String IMG_ASC =
        WelcomConfigurator.getMessageWithCfgChartePrefix( ".htmltable.images.sort.asc" );

    /** Constante */
    private final static String IMG_DESC =
        WelcomConfigurator.getMessageWithCfgChartePrefix( ".htmltable.images.sort.desc" );

    /**
     * Contructeur d'un col header
     * 
     * @param pCol colonne
     * @param pColSort critere de trie
     * @param pLocaleRequest locale de l'utilisateur
     * @param pResource resourcebundle, contient touts les massge de l'appli
     */
    public ColHeader( MessageResources pResource, Locale pLocaleRequest, Col pCol, ColSort pColSort )
    {
        this.col = pCol;
        this.colSort = pColSort;
        this.resources = pResource;
        this.localeRequest = pLocaleRequest;
    }

    /**
     * Retourne le libellé de l'entete de la colonne cela peux etre de ll'HTMl si c'est un spécialcontent a été definit
     * 
     * @return Retourne le libellé de l'entete de la colonne
     */
    public String getLibelle()
    {
        String libelle = "";
        if ( col.isSpecialHeader() )
        {
            libelle = col.getSpecialHeaderContent();
        }
        else
        {
            libelle = resources.getMessage( localeRequest, col.getKey() );
        }

        if ( libelle == null )
        {
            libelle = col.getKey();
        }

        return libelle;

    }

    /**
     * Retourne le libelle trucated si necessaire
     * 
     * @return le libelle trucated si necessaire
     */
    public String getTuncatedIfNecessaryLibelle()
    {
        // Truncque le fichier si necessaire
        return InternalTableUtil.getTruncatedString( getLibelle(), col.getHeaderTruncate(), null );
    }

    /**
     * Retourne l'url pour le trie
     * 
     * @param servletName nom de la servlet
     * @param tableTag nom du tag de la table
     * @param from pour quel indice de la table ?
     * @return l'url pour le trie
     */
    public String getSortUrl( TableTag tableTag, String servletName, int from )
    {

        String frwd = null;

        if ( !GenericValidator.isBlankOrNull( tableTag.getPageForward() ) )
        {
            frwd = "&wforward=" + tableTag.getPageForward();
        }
        else
        {
            frwd = "&requestURI=" + tableTag.getRequestURI();
        }

        final StringBuffer sbUrl = new StringBuffer();
        sbUrl.append( servletName + "from=" + from + frwd + "&colonne=" + col.getProperty() );
        sbUrl.append( "&table=" + ListColumnSort.getCle( tableTag.getName(), tableTag.getProperty() ) );
        sbUrl.append( "&sens=" + SortOrder.next( colSort.getSort() ) + "" );

        if ( !GenericValidator.isBlankOrNull( col.getType() ) )
        {
            sbUrl.append( "&type=" + col.getType() + "" );
        }

        if ( !GenericValidator.isBlankOrNull( col.getDateFormatKey() ) )
        {
            final String format = resources.getMessage( localeRequest, col.getDateFormatKey() );
            sbUrl.append( "&dateformat=" + format + "" );
        }
        else if ( !GenericValidator.isBlankOrNull( col.getDateFormat() ) )
        {
            sbUrl.append( "&dateformat=" + col.getDateFormat() + "" );
        }

        return sbUrl.toString();
    }

    /**
     * retourn l'image du trie
     * 
     * @return le chemin de l'image correspondant au type de trie defini dans le WelcomResources
     */
    public String getSrcImgOfSort()
    {
        String src = null;
        if ( colSort.getSort() == SortOrder.NONE )
        {
            src = IMG_NONE;
        }
        else if ( colSort.getSort() == SortOrder.ASC )
        {
            src = IMG_DESC;
        }
        else if ( colSort.getSort() == SortOrder.DESC )
        {
            src = IMG_ASC;
        }
        return src;
    }

    /**
     * Retourne si on autorise les espaces
     * 
     * @return si on autorise les espaces
     */
    public boolean isNoWrap()
    {
        return col.isHeaderNoWrap();
    }

    /**
     * @return la colonne
     */
    public Col getCol()
    {
        return col;
    }

}
