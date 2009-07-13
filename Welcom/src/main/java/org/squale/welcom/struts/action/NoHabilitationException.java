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
package org.squale.welcom.struts.action;

/**
 * @author M327837 classe qui assure la gestion des l'exception d'un erreur sur la verification des habilitations
 */
public class NoHabilitationException
    extends Exception
{

    /**
     * serial version
     */
    private static final long serialVersionUID = 1918261844678079975L;

    /**
     * Contructeur herités
     */
    public NoHabilitationException()
    {
    }

    /**
     * Contructeur herités
     * 
     * @param message message
     */
    public NoHabilitationException( String message )
    {
        super( message );
    }

    /**
     * Contructeur herités
     * 
     * @param cause cause
     */
    public NoHabilitationException( Throwable cause )
    {
        super( cause );
    }

    /**
     * Contructeur herités
     * 
     * @param message message
     * @param cause cause
     */
    public NoHabilitationException( String message, Throwable cause )
    {
        super( message, cause );
    }

}
