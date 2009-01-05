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
 * Créé le 20 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.daolayer;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.util.database.DatabaseTypeFactory;

/**
 * @author M400843
 */
public class DAOUtils
{
    /**
     * le pattern de date utilisé
     */
    private final static String JAVADATEPATTERN = "dd/MM/yyyy HH:mm:ss";

    /**
     * @param pDate la date à convertir en chaine
     * @return la chaine de caractère permettant d'utiliser la date dans une requete
     */
    public static String makeQueryDate( final Date pDate )
    throws JrafDaoException
    {
        SimpleDateFormat sdf = new SimpleDateFormat( JAVADATEPATTERN );
        String dateString = sdf.format( pDate );
        String queryDate = DatabaseTypeFactory.getInstance().toDate(dateString);
        return queryDate;
    }
}
