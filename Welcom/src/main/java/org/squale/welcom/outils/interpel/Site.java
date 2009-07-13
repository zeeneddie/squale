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
 * Créé le 7 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.outils.interpel;

/**
 * Definit sur quel site doit etre effectué l'envoi<br>
 * <code>
 * TLS<br>
 * QVG<br>
 * QVI<br>
 * </code>
 * 
 * @author M327837
 * @deprecated
 */
public class Site
{

    /** Site TLS (AIRINTER) */
    public final static Site TLS = new Site( "AIRINTER" );

    /** Site QVG (QVGTCP) */
    public final static Site QVG = new Site( "QVGTCP" );

    /** Site QVI (VBLTCP) */
    public final static Site QVI = new Site( "VLBTCP" );

    /** Site QVI_RNIS (VLBSDF) */
    public final static Site QVI_RNIS = new Site( "VLBSDF" );

    /** Chaine du site */
    private String site = "";

    /**
     * Constucteur d'un Nouveau Stite Interpel
     * 
     * @param pSite : Non du sites
     */
    private Site( final String pSite )
    {
        this.site = pSite;
    }

    /**
     * Retourne le site en fonction d'une chaine de caractere;
     * 
     * @param site TLS/QVG/QVI ou AIRINTER/VLBTCP/QVGTCP
     * @return le Site : Retourne l'object site
     * @throws SiteException : Ne trouve pas le site correspondant
     */
    public static Site getSite( final String site )
        throws SiteException
    {
        if ( site == null )
        {
            throw new SiteException(
                                     "Site 'null' Non reconnu par interpel \n Utiliser (TLS,QVG,QVI,AIRINTER TLS,VLBTCP,QVGTCP)" );
        }

        if ( site.toUpperCase().equals( "TLS" ) || site.toUpperCase().equals( "AIRINTER" ) )
        {
            return TLS;
        }
        else if ( site.toUpperCase().equals( "QVG" ) || site.toUpperCase().equals( "QVGTCP" ) )
        {
            return QVG;
        }
        else if ( site.toUpperCase().equals( "QVI" ) || site.toUpperCase().equals( "VLBTCP" ) )
        {
            return QVI;
        }
        else
        {
            throw new SiteException( "Site '" + site
                + "' Non reconnu par interpel \n Utiliser (TLS,QVG,QVI,AIRINTER TLS,VLBTCP,QVGTCP)" );
        }
    }

    /**
     * @return retourne le site en chaine de caractere
     */
    public String toString()
    {
        return site;
    }
}