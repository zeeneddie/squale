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
package org.squale.squaleexport.exception;

/**
 * This class represent the exceptions specific to the export functionality
 */
public class ExportException
    extends Exception
{

    /**
     * UID
     */
    private static final long serialVersionUID = -4753327144840613155L;

    /**
     * Constructor. Create a new ExportException with the cause given in argument
     * 
     * @param cause The exception cause
     */
    public ExportException( Throwable cause )
    {
        super( cause );
    }

    /**
     * Constructor. Create a new ExportException with the cause and message given in argument
     * 
     * @param message The exception message
     * @param cause The exception cause
     */
    public ExportException( String message, Throwable cause )
    {
        super( message, cause );
    }

    /**
     * Constructor. Create a new ExportException with the message given in argument
     * 
     * @param pMessage The exception message
     */
    public ExportException( String pMessage )
    {
        super( pMessage );
    }

}
