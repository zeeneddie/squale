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

import java.util.Collection;
import java.util.Iterator;

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.ConditionFormulaBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.SimpleFormulaBO;

/**
 * Test de conversion de formules
 */
public class FormulaConverterTest
    extends SqualeTestCase
{
    /** Identificateur de formule */
    private final int mFormulaId = 12345;

    /**
     * Test d'extraction des paramètres
     */
    public void testExtractParametersCondition()
    {
        // Test d'une formule avec conditions
        ConditionFormulaBO formula = new ConditionFormulaBO();
        formula.setTriggerCondition( "mccabe.wmc >= 8 | sonarj.vg >7" );
        formula.addMarkCondition( "mccabe.maxvg >= 0.5 * mccabe.sumvg" );
        formula.addMarkCondition( "mccabe.maxvg >= 0.5 * mccabe.sumvg" );
        formula.addMarkCondition( "mccabe.maxvg >= 0.3 * mccabe.sumvg" );
        formula.addMeasureKind( "mccabe" );
        formula.addMeasureKind( "sonarj" );
        formula.setId( mFormulaId );
        ParameterExtraction extracter = new ParameterExtraction();
        Collection params = extracter.getFormulaParameters( formula );
        // Nombre de prametres attendus
        final int paramsCount = 4;
        assertEquals( paramsCount, params.size() );
        FormulaParameter parameter = new FormulaParameter();
        parameter.setMeasureKind( "mccabe" );
        parameter.setMeasureAttribute( "maxvg" );
        assertTrue( params.contains( parameter ) );
        parameter.setMeasureAttribute( "sumvg" );
        assertTrue( params.contains( parameter ) );
        parameter.setMeasureAttribute( "wmc" );
        assertTrue( params.contains( parameter ) );
        parameter.setMeasureKind( "sonarj" );
        parameter.setMeasureAttribute( "vg" );
        assertTrue( params.contains( parameter ) );
    }

    /**
     * Test d'extraction des paramètres
     */
    public void testExtractParametersSimple()
    {
        // Test d'une formule avec conditions
        SimpleFormulaBO formula = new SimpleFormulaBO();
        formula.setTriggerCondition( "mccabe.wmc >= 8 | sonarj.vg >7" );
        formula.setFormula( "mccabe.maxvg >= 0.5 * mccabe.sumvg" );
        formula.addMeasureKind( "mccabe" );
        formula.addMeasureKind( "sonarj" );
        formula.setId( mFormulaId );
        ParameterExtraction extracter = new ParameterExtraction();
        Collection params = extracter.getFormulaParameters( formula );
        // Nombre de prametres attendus
        final int paramsCount = 4;
        assertEquals( paramsCount, params.size() );
        FormulaParameter parameter = new FormulaParameter();
        parameter.setMeasureKind( "mccabe" );
        parameter.setMeasureAttribute( "maxvg" );
        assertTrue( params.contains( parameter ) );
        parameter.setMeasureAttribute( "sumvg" );
        assertTrue( params.contains( parameter ) );
        parameter.setMeasureAttribute( "wmc" );
        assertTrue( params.contains( parameter ) );
        parameter.setMeasureKind( "sonarj" );
        parameter.setMeasureAttribute( "vg" );
        assertTrue( params.contains( parameter ) );
    }

    /**
     * Test de conversion des formules avec condition
     */
    public void testConvertConditionFormula()
    {
        ConditionFormulaBO formula = new ConditionFormulaBO();
        formula.setId( mFormulaId );
        formula.addMarkCondition( "condition0" );
        formula.addMarkCondition( "condition1" );
        formula.addMarkCondition( "condition2" );
        formula.addMeasureKind( "kind1" );
        formula.addMeasureKind( "kind2" );
        formula.setTriggerCondition( "trigger" );
        FormulaConverter converter = new FormulaConverter();
        String result = converter.convertFormula( formula );
        // Vérification de l'id de la formule
        assertTrue( result.indexOf( formula.getId() + "" ) > 0 );
        // Vérification de la présence du trigger
        assertTrue( result.indexOf( formula.getTriggerCondition() ) > 0 );
        // Vérification des conditions
        Iterator conditions = formula.getMarkConditions().iterator();
        while ( conditions.hasNext() )
        {
            assertTrue( result.indexOf( (String) conditions.next() ) > 0 );
        }
        // Vérification des types de mesure
        Iterator measureKinds = formula.getMeasureKinds().iterator();
        while ( measureKinds.hasNext() )
        {
            assertTrue( result.indexOf( (String) measureKinds.next() ) > 0 );
        }
    }

    /**
     * Test de conversion des formules simples
     */
    public void testConvertSimpleFormula()
    {
        SimpleFormulaBO formula = new SimpleFormulaBO();
        formula.setId( mFormulaId );
        formula.setFormula( "formula0" );
        formula.addMeasureKind( "kind1" );
        formula.addMeasureKind( "kind2" );
        formula.setTriggerCondition( "trigger" );
        FormulaConverter converter = new FormulaConverter();
        String result = converter.convertFormula( formula );
        // Vérification de l'id de la formule
        assertTrue( result.indexOf( formula.getId() + "" ) > 0 );
        // Vérification de la présence du trigger
        assertTrue( result.indexOf( formula.getTriggerCondition() ) > 0 );
        // Vérification de la formule
        assertTrue( result.indexOf( formula.getFormula() ) > 0 );
        // Vérification des types de mesure
        Iterator measureKinds = formula.getMeasureKinds().iterator();
        while ( measureKinds.hasNext() )
        {
            assertTrue( result.indexOf( (String) measureKinds.next() ) > 0 );
        }
    }
}
