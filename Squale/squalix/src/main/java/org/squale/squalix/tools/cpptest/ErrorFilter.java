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
package org.squale.squalix.tools.cpptest;

/**
 * Buffer d'erreur Les erreurs provenant de l'exécution de CppTest sont remontées sous la forme de lignes noyées dans
 * des traces. Cette classe permet de filtrer ces traces et d'intercepter les messages d'erreur
 */
public class ErrorFilter
{
    /** Erreur courante */
    private StringBuffer mCurrentError;

    /** Indicateur d'état */
    private boolean mInsideError;

    /**
     * Constructeur
     */
    public ErrorFilter()
    {
        mInsideError = false;
        mCurrentError = new StringBuffer();
    }

    /**
     * Traitement d'une ligne
     * 
     * @param pLine ligne traitée
     */
    public void processLine( String pLine )
    {
        if ( mInsideError )
        {
            // Pattern de fin d'erreur
            if ( pLine.indexOf( '^' ) >= 0 )
            {
                mInsideError = false;
            }
            else
            {
                appendError( pLine );
            }
        }
        else if ( pLine.indexOf( "error:" ) >= 0 )
        { // Détection du pattern
            mInsideError = true;
            appendError( pLine );
        }
    }

    /**
     * Ajout d'une ligne d'erreur
     * 
     * @param pLine ligne
     */
    private void appendError( String pLine )
    {
        mCurrentError.append( pLine );
        mCurrentError.append( '\n' );
    }

    /**
     * Erreur disponible
     * 
     * @return true si une erreur est prête à être consommée
     */
    public boolean errorAvailable()
    {
        return ( mInsideError == false ) && mCurrentError.length() > 0;
    }

    /**
     * Consommation d'une erreur
     * 
     * @return erreur courante
     */
    public String consumeError()
    {
        String result = mCurrentError.toString();
        mCurrentError.setLength( 0 );
        return result;
    }
}
