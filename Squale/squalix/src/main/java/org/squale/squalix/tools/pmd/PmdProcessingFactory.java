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
package com.airfrance.squalix.tools.pmd;

/**
 * Fabrique de traitement Pmd
 */
public class PmdProcessingFactory
{

    /**
     * Obtention du traitement Pmd correspondant à un langage
     * 
     * @param pLanguage langage
     * @return traitement correspondant
     * @throws PmdFactoryException si le langage est inconnu
     */
    public AbstractPmdProcessing createPmdProcessing( String pLanguage )
        throws PmdFactoryException
    {
        AbstractPmdProcessing result;
        if ( pLanguage.equals( "java" ) )
        {
            result = new JavaPmdProcessing();
        }
        else if ( pLanguage.equals( "jsp" ) )
        {
            result = new JspPmdProcessing();
        }
        else
        {
            throw new PmdFactoryException( PmdMessages.getString( "exception.unknown.language", pLanguage ) );
        }
        return result;
    }
}
