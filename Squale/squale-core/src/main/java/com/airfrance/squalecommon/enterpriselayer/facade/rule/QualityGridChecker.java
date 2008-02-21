package com.airfrance.squalecommon.enterpriselayer.facade.rule;

import java.util.Iterator;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor;

/**
 * Vérification d'une grille qualité
 * La vérification se limite au test de compilation des
 * formules et fonctions rattachées à une grille
 */
public class QualityGridChecker implements QualityRuleBOVisitor {
    // Désactivation du cache python pour utilisation dans environnement
    // serveur J2EE dans lequel on n'a pas la main sur le répertoire
    // utilisé par jython pour la gestion du cache des packages
    static {
        System.setProperty("python.cachedir.skip","true");
    }

    /** Interpréteur de formule */
    private FormulaInterpreter mInterpreter;
    
    /**
     * Constructeur
     */
    public QualityGridChecker() {
        mInterpreter = new FormulaInterpreter();
    }
    
    /**
     * Vérification d'une grille
     * @param pGrid grille
     * @param pError erreurs sur cette grille
     */
    public void checkGrid(QualityGridBO pGrid, StringBuffer pError) {
        Iterator factors = pGrid.getFactors().iterator();
        while (factors.hasNext()) {
            ((FactorRuleBO)factors.next()).accept(this, pError);
        }
    }
    
    /** (non-Javadoc)
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO, java.lang.Object)
     */
    public Object visit(FactorRuleBO pFactorRule, Object pArgument) {
        Iterator criteria = pFactorRule.getCriteria().keySet().iterator();
        while (criteria.hasNext()) {
            ((CriteriumRuleBO)criteria.next()).accept(this, pArgument);
        }
        return null;
    }

    /** (non-Javadoc)
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO, java.lang.Object)
     */
    public Object visit(CriteriumRuleBO pCriteriumRule, Object pArgument) {
        Iterator practices = pCriteriumRule.getPractices().keySet().iterator();
        while (practices.hasNext()) {
            ((PracticeRuleBO)practices.next()).accept(this, pArgument);
        }
        return null;
    }

    /** (non-Javadoc)
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBOVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO, java.lang.Object)
     */
    public Object visit(PracticeRuleBO pPracticeRule, Object pArgument) {
        StringBuffer errorBuffer = (StringBuffer) pArgument;
        if (pPracticeRule.getFormula()!=null) {
            FormulaInterpreter interpreter = new FormulaInterpreter();
            WeightFunctionInterpreter functionInter = new WeightFunctionInterpreter(interpreter.getInterpreter());
            try {
                // Vérification de la syntaxe de la fonction de pondération
                functionInter.checkSyntax(pPracticeRule.getWeightFunction());
            } catch(WeightFunctionException wfe) {
                // Si une erreur se produit sur l'évaluation de la fonction
                // on note celle-ci dans le rapport des erreurs
                Object[] args = new Object[] {pPracticeRule.getWeightFunction(), pPracticeRule.getName()};
                errorBuffer.append(RuleMessages.getString("function.error", args));
                errorBuffer.append('\n');
                errorBuffer.append(wfe.getMessage());
            }
            try {
                // Vérification de la syntaxe de la formule
                // un exception est levée si elle est incorrecte
                interpreter.checkSyntax(pPracticeRule.getFormula());
            } catch (FormulaException e) {
                // Si une erreur se produit sur le calcul d'une formule
                // on note celle-ci dans le rapport des erreurs
                errorBuffer.append(RuleMessages.getString("formula.error", new Object[] { pPracticeRule.getName()}));
                errorBuffer.append('\n');
                errorBuffer.append(e.getMessage());
            }

        }
        return null;
    }

}
