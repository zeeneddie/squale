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
 * Cr�� le 25 juil. 05
 *
 * Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */
package org.squale.welcom.addons.config;

/**
 * @author M327837 Pour changer le mod�le de ce commentaire de type g�n�r�, allez � :
 *         Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */
public class AddonsException
    extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 1174187716606242032L;

    /**
     * Contructeur
     */
    public AddonsException()
    {
        super();
    }

    /**
     * @param s : message
     */
    public AddonsException( final String s )
    {
        super( s );
    }

    /**
     * @param message Message
     * @param cause Cause
     */
    public AddonsException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    /**
     * @param cause Cause
     */
    public AddonsException( final Throwable cause )
    {
        super( cause );
    }

}
