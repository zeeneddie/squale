package com.airfrance.squalecommon.datatransfertobject.transform.rule;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.AbstractFormulaBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO.MeasureExtractor;

/**
 * Transformation d'une grille qualité en métriques Cette transformation est utilisée pour la construction du menu top
 * de façon dynamique
 */
public class GridMetricsTransform
    implements QualityRuleBOVisitor
{
    /** Extracteur de mesure */
    private MeasureExtractor mMeasureExtractor;

    /**
     * Constructeur
     * 
     * @param pMeasureExtractor extracteur de mesure
     */
    public GridMetricsTransform( PracticeRuleBO.MeasureExtractor pMeasureExtractor )
    {
        mMeasureExtractor = pMeasureExtractor;
    }

    /**
     * Transformation d'une grille qualité en métriques
     * 
     * @param pGrid grille qualité
     * @param pDto métriques sous la forme de map avec en clef
     * @param pMeasureExtractor extracteur de mesure le type de composant et en valer la liste des métriques
     */
    static public void bo2dto( QualityGridBO pGrid, Map pDto, PracticeRuleBO.MeasureExtractor pMeasureExtractor )
    {
        GridMetricsTransform tr = new GridMetricsTransform( pMeasureExtractor );
        // Transformation de chaque facteur
        Iterator factorIt = pGrid.getFactors().iterator();
        while ( factorIt.hasNext() )
        {
            FactorRuleBO factor = (FactorRuleBO) factorIt.next();
            factor.accept( tr, pDto );
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO,
     *      java.lang.Object)
     */
    public Object visit( FactorRuleBO pFactorRule, Object pArgument )
    {
        Iterator criteriumIt = pFactorRule.getCriteria().keySet().iterator();
        while ( criteriumIt.hasNext() )
        {
            ( (CriteriumRuleBO) criteriumIt.next() ).accept( this, pArgument );
        }
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO,
     *      java.lang.Object)
     */
    public Object visit( CriteriumRuleBO pCriteriumRule, Object pArgument )
    {
        Iterator practiceIt = pCriteriumRule.getPractices().keySet().iterator();
        while ( practiceIt.hasNext() )
        {
            ( (PracticeRuleBO) practiceIt.next() ).accept( this, pArgument );
        }
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO,
     *      java.lang.Object)
     */
    public Object visit( PracticeRuleBO pPracticeRule, Object pArgument )
    {
        AbstractFormulaBO formula = pPracticeRule.getFormula();
        if ( formula != null && !formula.getComponentLevel().equals( "project" ) )
        {
            Map map = (Map) pArgument;
            String level = "component." + formula.getComponentLevel();
            TreeSet metrics = (TreeSet) map.get( level );
            if ( metrics == null )
            {
                metrics = new TreeSet();
                map.put( level, metrics );
            }
            String[] used = mMeasureExtractor.getUsedMeasures( formula );
            for ( int i = 0; i < used.length; i++ )
            {
                metrics.add( used[i] );
            }
        }
        return null;
    }
}
