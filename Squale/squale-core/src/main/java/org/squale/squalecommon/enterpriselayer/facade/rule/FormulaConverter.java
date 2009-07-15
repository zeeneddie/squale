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
package com.airfrance.squalecommon.enterpriselayer.facade.rule;

import java.util.Iterator;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.AbstractFormulaBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.ConditionFormulaBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FormulaVisitor;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.SimpleFormulaBO;

/**
 * Conversion de formule Une formule qui est composée de paramètres doit être convertie avant de pouvoir être évaluée.
 */
public class FormulaConverter
    implements FormulaVisitor
{

    /** Préfixe de function utilisée */
    public final static String FUNCTION_PREFIX = "factor_";

    /**
     * Conversion de formule La formule passée en paramètre est convertie sous sa forme Python
     * 
     * @param pFormula formule
     * @return formule convertie
     */
    public String convertFormula( AbstractFormulaBO pFormula )
    {

        // TODO : Renvoyer tuple contenant le nom de la fonction et la String que l'on renvoie actuellement
        // pour pouvoir récupérer le nom de la fonction et ainsi éviter une duplication du code.

        // Utilisation de StringBuffer pour les performances
        StringBuffer buf = new StringBuffer();
        // Définition de la fonction
        buf.append( "def " + FUNCTION_PREFIX );
        // L'id de la fonction est ajouté pour assurer l'unicité
        long id = pFormula.getId();
        // Traitement des formules transientes
        if ( id < 0 )
        {
            id = 0;
        }
        buf.append( id );
        // Placement des paramètres, un par type de mesure
        buf.append( '(' );
        Iterator measureKinds = pFormula.getMeasureKinds().iterator();
        int i = 0;
        while ( measureKinds.hasNext() )
        {
            // Placement de la virgule après le premier paramètre
            if ( i > 0 )
            {
                buf.append( ", " );
            }
            buf.append( (String) measureKinds.next() );
            i++;
        }
        buf.append( "):\n" );
        // Vérification de l'existence de la condition de trigger
        if ( ( pFormula.getTriggerCondition() != null ) && ( pFormula.getTriggerCondition().trim().length() > 0 ) )
        {
            // Ajout d'un if pour traiter le cas du trigger
            buf.append( "  if " );
            buf.append( pFormula.getTriggerCondition() );
            buf.append( ":\n" );
        }
        // Partie variable en fonction du type de formule
        // utilisation du visiteur
        pFormula.accept( this, buf );
        return buf.toString();
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.practice.FormulaVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.practice.ConditionFormulaBO,
     *      java.lang.Object)
     */
    public Object visit( ConditionFormulaBO pConditionFormula, Object pArgument )
    {
        StringBuffer buffer = (StringBuffer) pArgument;
        final String indentation = "    ";
        // Traitement des conditions
        Iterator conditions = pConditionFormula.getMarkConditions().iterator();
        int i = 0;
        while ( conditions.hasNext() )
        {
            String condition = (String) conditions.next();
            buffer.append( indentation );
            if ( i == 0 )
            {
                buffer.append( "if " );
            }
            else
            {
                buffer.append( "elif " );
            }
            buffer.append( condition );
            buffer.append( ":\n" );
            buffer.append( indentation );
            buffer.append( "    return " );
            buffer.append( i );
            buffer.append( '\n' );
            i++;
        }
        // Traitement du else
        buffer.append( indentation );
        buffer.append( "else:\n" );
        buffer.append( indentation );
        buffer.append( "    return " );
        buffer.append( i );
        buffer.append( '\n' );
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.practice.FormulaVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.practice.SimpleFormulaBO,
     *      java.lang.Object)
     */
    public Object visit( SimpleFormulaBO pSimpleFormula, Object pArgument )
    {
        StringBuffer buffer = (StringBuffer) pArgument;
        final String indentation = "    ";
        buffer.append( indentation );
        buffer.append( "return " );
        buffer.append( pSimpleFormula.getFormula() );
        buffer.append( '\n' );
        return null;
    }
}
