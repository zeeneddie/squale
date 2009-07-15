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
package org.squale.squalix.tools.cpd;

/**
 * Fabrique de traitement Cpd
 */
public class CpdProcessingFactory
{

    /**
     * Obtention du traitement Cpd correspondant à un langage
     * 
     * @param pLanguage langage
     * @return traitement correspondant
     * @throws CpdFactoryException si le langage est inconnu
     */
    public AbstractCpdProcessing createCpdProcessing( String pLanguage )
        throws CpdFactoryException
    {
        AbstractCpdProcessing result;
        if ( pLanguage.equals( "java" ) )
        {
            result = new JavaCpdProcessing();
        }
        else if ( pLanguage.equals( "cpp" ) )
        {
            result = new CppCpdProcessing();
        }
        else if ( pLanguage.equals( "jsp" ) )
        {
            result = new JspCpdProcessing();
        }
        else if ( pLanguage.equals( "cobol" ) )
        {
            result = new CobolCpdProcessing();
        }
        else
        {
            throw new CpdFactoryException( CpdMessages.getString( "exception.unknown.language", pLanguage ) );
        }
        return result;
    }
}
