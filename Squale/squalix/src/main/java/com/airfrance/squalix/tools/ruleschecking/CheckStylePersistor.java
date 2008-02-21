package com.airfrance.squalix.tools.ruleschecking;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.CheckstyleTransgressionBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionItemBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleModuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleSetBO;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * Persistance des résultats générés par CheckStyle
 */
public class CheckStylePersistor implements ReportHandler {
    /** Logger */
    private static final Log LOGGER = LogFactory.getLog(CheckStylePersistor.class);
    /** Transgression */
    private CheckstyleTransgressionBO mTransgression;
    /** RuleSet */
    private CheckstyleRuleSetBO mRuleSet;
    /** Liste des codes avec leur nombre d'occurence */
    private HashMap mCode;
    /** La liste des modules */
    private HashSet mModules;
    /** La map contenant toutes les règles */
    private Map mRules;

    /**
     * Constructeur
     * @param pRuleSet jeu de règles
     */
    public CheckStylePersistor(CheckstyleRuleSetBO pRuleSet) {
        mRuleSet = pRuleSet;
        mRules = pRuleSet.getRules();
        // Création de la transgression
        mTransgression = new CheckstyleTransgressionBO();
        mTransgression.setRuleSet(pRuleSet);

        Collection col = mRules.values();
        Iterator it = col.iterator();
        // Parcours des règles pour stocker dans une map le nombre de violations
        // On utilise une autre Map pour y stocker les modules
        mCode = new HashMap();
        mModules = new HashSet();
        while (it.hasNext()) {
            CheckstyleRuleBO rule = (CheckstyleRuleBO) it.next();
            mCode.put(rule.getCode(), new IntegerWrapper());
            mModules.addAll(rule.getModules());
        }
    }

    /**
     * Fabrication de la transgression
     * Les données collectées pendant l'analyse du rapport checkstyle sont placées
     * dans la transgression
     * @return transgression
     */
    public CheckstyleTransgressionBO computeTransgression() {
        Collection col = mCode.keySet();
        Iterator it = col.iterator();

        while (it.hasNext()) {
            String code = (String) it.next();
            IntegerWrapper nbOccur = (IntegerWrapper) mCode.get(code);

            IntegerMetricBO metric = new IntegerMetricBO();
            metric.setName(code);
            metric.setValue(nbOccur.getValue());
            metric.setMeasure(mTransgression);
            mTransgression.putMetric(metric);
        }
        return mTransgression;
    }

    /** 
     * {@inheritDoc}
     * @see com.airfrance.squalix.tools.ruleschecking.ReportHandler#processError(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void processError(String pFileName, String pLine, String pColumn, String pSeverity, String pMessage, String pSource) {
        final int MAX_LENGTH = 3000;
        if (!SeverityLevel.IGNORE.getName().equals(pSeverity)) {
            // recupère le code de l'erreur
            CheckstyleRuleBO rule = getRuleForModule(pSource, pMessage);
            if (rule != null) {
                IntegerWrapper nbOccur = (IntegerWrapper) mCode.get(rule.getCode());
                nbOccur.increment();
                // Les messages trop longs bloquent le Commit, ils sont donc tronqués
                StringBuffer detail = new StringBuffer();
                detail.append(
                    RulesCheckingMessages.getString("transgression.detail", new Object[] { RuleCheckingTransgressionItemBO.PATH_KEY, RuleCheckingTransgressionItemBO.LINE_KEY, pColumn, pMessage }));
                if (detail.length() > MAX_LENGTH) {
                    detail.setLength(MAX_LENGTH - 1);
                }
                // Ajout du détail de la transgression
                RuleCheckingTransgressionItemBO item = new RuleCheckingTransgressionItemBO();
                item.setMessage(detail.toString());
                item.setRule(rule);
                item.setComponentFile(pFileName);
                item.setLine(Integer.parseInt(pLine));
                // On ajoute l'item seulement si son nombre d'occurences <= 100
                if (nbOccur.mValue <= RuleCheckingTransgressionBO.MAX_DETAILS) {
                    mTransgression.getDetails().add(item);
                }
            }
        }
    }

    /**
     * Determine le nom du code associé à l'erreur
     * 
     * @param pModule le nom du module(rule)
     * @param pMessage message de l'erreur
     * @return la règle associée au module 
     */

    private CheckstyleRuleBO getRuleForModule(String pModule, String pMessage) {
        CheckstyleRuleBO result = null;
        Set modules = getCorrespondingModules(pModule);
        CheckstyleModuleBO module = null;
        if (modules.size() > 0) {
            if (modules.size() > 1) { //cas de plusieurs modules avec
                module = raiseAmbiguity(modules, pMessage);
                if (module != null) {
                    result = (CheckstyleRuleBO) module.getRule();
                } else {
                    LOGGER.error(RulesCheckingMessages.getString("exception.rulesChecking.module.notfound", new Object[] { pModule, pMessage }));
                }
            } else {
                Iterator it = modules.iterator();
                module = (CheckstyleModuleBO) it.next();
                result = (CheckstyleRuleBO) module.getRule();
            }
        }
        return result;
    }

    /**
     * Trouve les modules du référentiel ayant le même nom que le nom de la transgression renvoyée par checkstyle 
     * @param pModule nom de la transgression renvoyée par Checkstyle
     * @return une liste de modules du référentiel
     */
    private Set getCorrespondingModules(String pModule) {
        HashSet modules = new HashSet();
        CheckstyleModuleBO module = null;
        Iterator it = mModules.iterator();
        while (it.hasNext()) {
            module = (CheckstyleModuleBO) it.next();
            if (pModule.equals(module.getName())) {
                modules.add(module);
            }
        }
        return modules;
    }

    /**
     * Lève l'ambigüité pour une trangression
     * @param pModules Liste des modules du référentiel
     * @param pMessage Le message de la transgression donné par checkstyle
     * @return Le module correspondant au message
     */
    private CheckstyleModuleBO raiseAmbiguity(Set pModules, String pMessage) {
        boolean status = true;
        CheckstyleModuleBO module = null;
        CheckstyleModuleBO bufferModule = null;
        Iterator it = pModules.iterator();
        while (status && it.hasNext()) {
            bufferModule = (CheckstyleModuleBO) it.next();
            if (bufferModule.getMessage().matches(pMessage)) {
                module = bufferModule;
                status = false;
            }
        }
        return module;
    }

    /**
     * Encapsulation d'un entier
     *
     */
    static class IntegerWrapper {
        /** Valeur */
        private int mValue = 0;
        /**
         * @return valeur
         */
        public int getValue() {
            return mValue;
        }
        /**
         * Incrément
         *
         */
        public void increment() {
            mValue++;
        }
    }
}
