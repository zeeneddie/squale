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

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAClassMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.ConditionFormulaBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.SimpleFormulaBO;
import com.airfrance.squalecommon.util.initialisor.JRafConfigurator;

/**
 * Test de l'interpréteur de formule L'interpréteur de formule est testé pour la vérification de syntaxe ainsi que pour
 * la vérification de l'évaluation des formules
 */
public class FormulaInterpreterTest
    extends SqualeTestCase
{

    /**
     * Constructor for FormulaInterpreterTest.
     * 
     * @param arg0 nom
     */
    public FormulaInterpreterTest( String arg0 )
    {
        super( arg0 );
        JRafConfigurator.initialize();
    }

    /**
     * Test de vérification de la syntaxe
     */
    public void testCheckSyntax()
    {
        ConditionFormulaBO formula = new ConditionFormulaBO();
        formula.setId( 1 );
        formula.addMarkCondition( "mccabe.sumvg == 3" );
        formula.addMarkCondition( "mccabe.sumvg == 30" );
        formula.addMarkCondition( "mccabe.sumvg == 32" );
        formula.setTriggerCondition( "mccabe.sumvg == 12" );
        formula.setComponentLevel( "class" );
        formula.addMeasureKind( "mccabe" );
        formula.addMeasureKind( "ckjm" );
        FormulaInterpreter inter = new FormulaInterpreter();
        try
        {
            inter.checkSyntax( formula );
            assertTrue( "Formule correcte", true );
        }
        catch ( FormulaException e )
        {
            e.printStackTrace();
            fail( "Unexpected exception" );
        }
        formula.setTriggerCondition( "trigger.0" );
        try
        {
            inter.checkSyntax( formula );
            fail( "Expected exception" );
        }
        catch ( FormulaException e )
        {
            assertTrue( "Formule incorrecte", true );
        }
        formula.setTriggerCondition( "mccabe.unknown == 12" );
        try
        {
            inter.checkSyntax( formula );
            fail( "Expected exception" );
        }
        catch ( FormulaException e )
        {
            assertTrue( "Formule incorrecte", true );
        }
    }

    /**
     * Test d'évaluation d'expression
     */
    public void testEvaluateCondition()
    {
        ConditionFormulaBO formula = new ConditionFormulaBO();
        formula.setTriggerCondition( "mccabe.wmc >= 8" );
        formula.addMarkCondition( "mccabe.maxvg >= 0.5 * mccabe.sumvg" );
        formula.addMarkCondition( "mccabe.maxvg >= 0.4 * mccabe.sumvg" );
        formula.addMarkCondition( "mccabe.maxvg >= 0.3 * mccabe.sumvg" );
        formula.addMeasureKind( "mccabe" );
        formula.setId( 1 );
        McCabeQAClassMetricsBO measure = new McCabeQAClassMetricsBO();
        MeasureBO[] measures = new MeasureBO[1];
        measures[0] = measure;
        measure.setMaxvg( new Integer( 9 ) );
        measure.setSumvg( new Integer( 31 ) );
        measure.setWmc( new Integer( 10 ) );
        FormulaInterpreter inter = new FormulaInterpreter();
        Number val;
        try
        {
            // Test des valeurs dans l'intervalle 0..3
            val = inter.evaluate( formula, measures );
            assertEquals( 3, val.intValue() );
            measure.setMaxvg( new Integer( 10 ) );
            val = inter.evaluate( formula, measures );
            assertEquals( 2, val.intValue() );
            measure.setMaxvg( new Integer( 13 ) );
            val = inter.evaluate( formula, measures );
            assertEquals( 1, val.intValue() );
            measure.setMaxvg( new Integer( 16 ) );
            val = inter.evaluate( formula, measures );
            assertEquals( 0, val.intValue() );
            // Test avec trigger non vérifié
            measure.setWmc( new Integer( 7 ) );
            val = inter.evaluate( formula, measures );
            assertNull( val );
        }
        catch ( FormulaException e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }
    }

    /**
     * Test d'évaluation d'expression simple
     */
    public void testEvaluateSimple()
    {
        SimpleFormulaBO formula = new SimpleFormulaBO();
        formula.setTriggerCondition( "mccabe.wmc >= 8" );
        formula.setFormula( "mccabe.sumvg / mccabe.maxvg" );
        formula.addMeasureKind( "mccabe" );
        formula.setId( 1 );
        McCabeQAClassMetricsBO measure = new McCabeQAClassMetricsBO();
        MeasureBO[] measures = new MeasureBO[1];
        measures[0] = measure;
        measure.setMaxvg( new Integer( 9 ) );
        measure.setSumvg( new Integer( 31 ) );
        measure.setWmc( new Integer( 10 ) );
        FormulaInterpreter inter = new FormulaInterpreter();
        Number val;
        try
        {
            // Test des valeurs dans l'intervalle 0..3
            val = inter.evaluate( formula, measures );
            assertEquals( 3, val.intValue() );
            measure.setMaxvg( new Integer( 11 ) );
            val = inter.evaluate( formula, measures );
            assertEquals( 2, val.intValue() );
            measure.setMaxvg( new Integer( 16 ) );
            val = inter.evaluate( formula, measures );
            assertEquals( 1, val.intValue() );
            measure.setMaxvg( new Integer( 32 ) );
            val = inter.evaluate( formula, measures );
            assertEquals( 0, val.intValue() );
            // Test de valeur > 3, la note est ramenée à 3
            measure.setMaxvg( new Integer( 5 ) );
            val = inter.evaluate( formula, measures );
            assertEquals( 3, val.intValue() );
            // Test de valeur < 0, la note est ramenée à 0
            measure.setMaxvg( new Integer( -5 ) );
            val = inter.evaluate( formula, measures );
            assertEquals( 0, val.intValue() );
            // Test avec trigger non vérifié
            measure.setWmc( new Integer( 7 ) );
            val = inter.evaluate( formula, measures );
            assertNull( val );
        }
        catch ( FormulaException e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }
    }

}
