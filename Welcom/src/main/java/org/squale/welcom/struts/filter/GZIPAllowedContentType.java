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
 * Créé le 14 déc. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.struts.filter;

import java.util.StringTokenizer;
import java.util.Vector;

import org.squale.welcom.outils.WelcomConfigurator;


/**
 * @author M327836 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class GZIPAllowedContentType
{

    /** Singleton */
    private static GZIPAllowedContentType instance = null;

    /** Type interne a ignoré */
    private Vector internalTypes;

    /** Contructeur */
    private GZIPAllowedContentType()
    {
        internalTypes = new Vector();

        final StringTokenizer st =
            new StringTokenizer(
                                 WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_GZIPFILTER_ALLOWEDCONTENTTYPE ),
                                 ";" );

        while ( st.hasMoreTokens() )
        {
            internalTypes.add( st.nextToken() );
        }
    }

    /**
     * Recupere le singleton
     * 
     * @return singleton
     */
    private static Vector getTypes()
    {
        if ( instance == null )
        {
            instance = new GZIPAllowedContentType();
        }

        return instance.getInternalTypes();
    }

    /**
     * @return Liste des type interne
     */
    public Vector getInternalTypes()
    {
        return internalTypes;
    }

    /**
     * @param vector liste des type interne
     */
    public void setInternalTypes( final Vector vector )
    {
        internalTypes = vector;
    }

    /**
     * Retourne si ce type de type mime doit etre zippé
     * 
     * @param arg0 Content type full ou small
     */
    public static boolean isAllowZipType( final String arg0 )
    {
        String smallType;
        if ( ( arg0 == null ) || ( arg0.indexOf( ";" ) < 0 ) )
        {
            smallType = arg0;
        }
        else
        {
            smallType = arg0.substring( 0, arg0.indexOf( ";" ) );
        }

        return getTypes().contains( smallType );
    }
}