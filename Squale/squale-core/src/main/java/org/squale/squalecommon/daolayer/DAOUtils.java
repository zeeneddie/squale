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

import java.util.Date;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.util.database.DatabaseTypeFactory;

/**
 * Utility class for the sql request
 */
public final class DAOUtils
{

    /**
     * Private constructor
     */
    private DAOUtils()
    {

    }

    /**
     * Utility method for transform date into a well formated string for a sql request
     * 
     * @param pDate The date to convert in string
     * @return The well formated string
     * @throws JrafDaoException Exception happened during the format
     */
    public static String makeQueryDate( final Date pDate )
        throws JrafDaoException
    {
        String queryDate = DatabaseTypeFactory.getInstance().toDate( pDate );
        return queryDate;
    }
}
