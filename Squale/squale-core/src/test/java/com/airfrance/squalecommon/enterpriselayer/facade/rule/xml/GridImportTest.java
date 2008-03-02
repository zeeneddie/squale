package com.airfrance.squalecommon.enterpriselayer.facade.rule.xml;

import java.io.InputStream;
import java.util.Collection;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.ConditionFormulaBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.SimpleFormulaBO;
import com.airfrance.squalecommon.enterpriselayer.facade.rule.FormulaMeasureExtractor;

/**
 * Test d'importation au format XML de la grille Le test vérifie essentiellement que la mapping est correct au niveau du
 * digester en s'assurant que tous les attributs présents dans le fichier XML sont bien mappés dans le classes métier
 */
public class GridImportTest
    extends SqualeTestCase
{

    /**
     * Importation d'une grille Ce test vérifie la présence de chacun des attributs présents dans le fichier XML
     */
    public void testImportGrid()
    {
        StringBuffer errors = new StringBuffer();
        InputStream stream = getClass().getClassLoader().getResourceAsStream( "data/grid/grid_simple.xml" );
        Collection grids;
        grids = new GridImport().importGrid( stream, errors );
        assertEquals( 1, grids.size() );
        assertTrue( errors.length() == 0 );
        QualityGridBO grid = (QualityGridBO) grids.iterator().next();
        // Vérification des informations de la grille
        assertEquals( "grid1", grid.getName() );
        assertEquals( 1, grid.getFactors().size() );
        // Vérification des informations du facteur
        FactorRuleBO factor = (FactorRuleBO) grid.getFactors().first();
        assertEquals( "factor1", factor.getName() );
        assertEquals( 1, factor.getCriteria().size() );
        // Vérification des informations du critère
        CriteriumRuleBO criterium = (CriteriumRuleBO) factor.getCriteria().firstKey();
        assertEquals( "criterium1", criterium.getName() );
        assertEquals( new Float( 1.0 ), (Float) factor.getCriteria().get( criterium ) );
        assertEquals( 3, criterium.getPractices().size() );
        // Vérification des informations des pratiques
        PracticeRuleBO[] practices =
            (PracticeRuleBO[]) criterium.getPractices().keySet().toArray( new PracticeRuleBO[] {} );
        int index = 0;
        PracticeRuleBO practice;

        FormulaMeasureExtractor measureExtactor = new FormulaMeasureExtractor();
        // La première pratique ne contient pas de formule
        practice = practices[index++];
        assertEquals( "practice" + index, practice.getName() );
        assertNull( practice.getFormula() );
        assertEquals( "no measure", 0, measureExtactor.getUsedMeasures( practice.getFormula() ).length );
        // La première pratique contient la fonction de pondération par défaut
        assertEquals( PracticeRuleBO.WEIGHTING_FUNCTION, practice.getWeightFunction() );

        // La deuxième pratique contient une formule avec conditions
        practice = practices[index++];
        assertEquals( "practice" + index, practice.getName() );
        assertNotNull( practice.getFormula() );
        assertNotNull( measureExtactor.getUsedMeasures( practice.getFormula() ) );
        // Vérification de la formule
        assertTrue( practice.getFormula() instanceof ConditionFormulaBO );
        ConditionFormulaBO condFormula = (ConditionFormulaBO) practice.getFormula();
        assertEquals( "class", condFormula.getComponentLevel() );
        assertEquals( 1, condFormula.getMeasureKinds().size() );
        assertEquals( "mccabe", condFormula.getMeasureKinds().iterator().next() );
        assertEquals( "mccabe.wmc >= 8", condFormula.getTriggerCondition() );
        assertEquals( "mccabe.maxvg >= 0.5*mccabe.sumvg", condFormula.getMarkConditions().get( 0 ) );
        assertEquals( "mccabe.maxvg >= 0.4*mccabe.sumvg", condFormula.getMarkConditions().get( 1 ) );
        assertEquals( "mccabe.maxvg >= 0.3*mccabe.sumvg", condFormula.getMarkConditions().get( 2 ) );
        // La deuxième pratique contient la fonction de pondération par défaut
        assertEquals( PracticeRuleBO.WEIGHTING_FUNCTION, practice.getWeightFunction() );
        // elle a un effort de correction de 2
        assertEquals( 2, practice.getEffort() );

        // La troisème pratique contient une formule simple
        practice = practices[index++];
        assertEquals( "practice" + index, practice.getName() );
        assertNotNull( practice.getFormula() );
        assertNotNull( measureExtactor.getUsedMeasures( practice.getFormula() ) );
        // La troisième pratique contient une fonction de pondération
        assertEquals( "lambda x:9**-x", practice.getWeightFunction() );
        // elle a un effort de correction par défaut
        assertEquals( PracticeRuleBO.EFFORT, practice.getEffort() );
        // Vérification de la formule
        assertTrue( practice.getFormula() instanceof SimpleFormulaBO );
        SimpleFormulaBO simpleFormula = (SimpleFormulaBO) practice.getFormula();
        assertEquals( "class", simpleFormula.getComponentLevel() );
        assertEquals( 1, simpleFormula.getMeasureKinds().size() );
        assertEquals( "mccabe", simpleFormula.getMeasureKinds().iterator().next() );
        assertEquals( "", simpleFormula.getTriggerCondition() );
        assertEquals( "mccabe.maxvg / mccabe.sumvg", simpleFormula.getFormula() );
    }

}
