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
package org.squale.squalecommon.enterpriselayer.facade.rule;

import org.python.core.PyException;
import org.python.core.PyFloat;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

/**
 * Interpréteur pour les fonctions de pondération de la grille qualité
 */
public class WeightFunctionInterpreter
{

    /** Interpreteur Jython */
    private PythonInterpreter mInterpreter;

    /**
     * Constructeur par défaut
     */
    public WeightFunctionInterpreter()
    {
        mInterpreter = new PythonInterpreter();
        mInterpreter.exec( "from math import *\n" );
    }

    /**
     * Constructeur
     * 
     * @param pInterpreter l'interpreteur Jython
     */
    public WeightFunctionInterpreter( PythonInterpreter pInterpreter )
    {
        if ( mInterpreter == null )
        {
            mInterpreter = new PythonInterpreter();
            mInterpreter.exec( "from math import *\n" );
        }
        else
        {
            mInterpreter = pInterpreter;
        }
    }

    /**
     * @param pFunction la fonction à vérifier
     * @throws WeightFunctionException si erreur de syntaxe
     */
    public void checkSyntax( String pFunction )
        throws WeightFunctionException
    {
        try
        {
            PyObject pyFunction = mInterpreter.eval( pFunction );
            // Le résultat de l'évaluation doit être un objet de type pyFunction
            if ( !( pyFunction instanceof PyFunction ) )
            {
                // On lance une exception
                String error =
                    RuleMessages.getString( "function.eval.badtype", new Object[] { pyFunction.getClass().getName() } );
                throw new WeightFunctionException( error );
            }
            // La fonction doit être à une variable
            // TODO??
        }
        catch ( PyException pe )
        {
            throw new WeightFunctionException( pe );
        }
    }

    /**
     * @param pFunction la fonction à appliquer
     * @param pMark la note
     * @return pFunction(pMark)
     * @throws WeightFunctionException si erreur Jython
     */
    public float exec( String pFunction, float pMark )
        throws WeightFunctionException
    {
        PyFunction pythonResult = (PyFunction) mInterpreter.eval( pFunction );
        float fMark = -1;
        try
        {
            PyFloat res = (PyFloat) pythonResult.__call__( new PyFloat( pMark ) );
            fMark = (float) res.getValue();
        }
        catch ( Throwable t )
        {
            // Renvoi d'une exception si la couche Jython rencontre une erreur
            throw new WeightFunctionException( t );
        }
        return fMark;
    }

    /**
     * @return l'interpréteur Jython
     */
    public PythonInterpreter getInterpreter()
    {
        return mInterpreter;
    }

}
