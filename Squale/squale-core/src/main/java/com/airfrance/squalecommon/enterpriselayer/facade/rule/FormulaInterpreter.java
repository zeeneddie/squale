package com.airfrance.squalecommon.enterpriselayer.facade.rule;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.beanutils.BeanUtils;
import org.python.core.PyException;
import org.python.core.PyFloat;
import org.python.core.PyInteger;
import org.python.core.PyNone;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.AbstractFormulaBO;

/**
 * Interprète de formule
 */
public class FormulaInterpreter {
    /** Dernière formule compilée à des fins d'optimisation*/
    private long mLastFormulaComputed;

    /** Interpreteur python */
    private PythonInterpreter mInterpreter;

    /**
     * Obtention d'un interpréteur Jython
     * @return interpréteur
     */
    public PythonInterpreter getInterpreter() {
        if (mInterpreter == null) {
            mInterpreter = new PythonInterpreter();
            mInterpreter.exec("from math import *\n");
        }
        return mInterpreter;
    }

    /**
     * Vérification de la syntaxe d'une formule
     * @param pFormula formule
     * @throws FormulaException si erreur
     */
    public void checkSyntax(AbstractFormulaBO pFormula) throws FormulaException {
        FormulaConverter converter = new FormulaConverter();
        String result = converter.convertFormula(pFormula);
        PythonInterpreter inter = getInterpreter();
        try {
            inter.exec(result);
        } catch (PyException e) {
            throw new FormulaException(e);
        }
        // Test des paramètres utilisés
        MeasureBOFactory factory = new MeasureBOFactory();
        MeasureBO[] measures = new MeasureBO[pFormula.getMeasureKinds().size()];
        Iterator measureKinds = pFormula.getMeasureKinds().iterator();
        int i = 0;
        while (measureKinds.hasNext()) {
            measures[i] = factory.createMeasure(pFormula.getComponentLevel(), (String) measureKinds.next());
            i++;
        }
        checkParameters(pFormula, measures);
    }

    /**
     * Evaluation d'une formule
     * @param pFormula formule
     * @param pMeasures mesures
     * @throws FormulaException si erreur
     * @return note attribuée
     */
    public Number evaluate(AbstractFormulaBO pFormula, MeasureBO[] pMeasures) throws FormulaException {
        Number result = null;
        try {
            result = evaluateWithoutAdapt(pFormula, pMeasures);
            // Ajustement du résultat
            if (null != result) {
                double value = result.doubleValue();
                // Ajustement de la valeur dans l'intervale autorisé
                final int minValue = 0;
                final int maxValue = 3;
                if (value < minValue) {
                    value = minValue;
                } else if (value > maxValue) {
                    value = maxValue;
                }
                // Conversion de la valeur en type
                result = new Double(value);
            }
        } catch (Throwable t) {
            // Renvoi d'une exception si la couche Jython rencontre une erreur
            throw new FormulaException(t);
        }
        return result;
    }

    /**
     * @param pFormula la formule
     * @param pMeasures mesures
     * @return le résultat une fois la mesure appliquée
     * @throws FormulaException si erreur
     */
    public Number evaluateWithoutAdapt(AbstractFormulaBO pFormula, MeasureBO[] pMeasures) throws FormulaException {
        Number result = null;
        try {
            // Calcul
            PyObject r = calculate(pFormula, pMeasures);
            // Conversion du résultat
            result = convertResult(r);
        } catch (Throwable t) {
            // Renvoi d'une exception si la couche Jython rencontre une erreur
            throw new FormulaException(t);
        }
        return result;
    }

    /**
     * @param pFormula la formule
     * @param pMeasures mesures
     * @return le résulat en phyton une fois la formule appliquée
     * @throws FormulaException si erreur
     */
    private PyObject calculate(AbstractFormulaBO pFormula, MeasureBO[] pMeasures) throws FormulaException {
        // Obtention d'un interprète Python
        PythonInterpreter inter = getInterpreter();
        PyObject result = null;
        try {
            // Compilation de la formule correspondate
            compileFormula(pFormula);
            // Optimisation du traitement des chaînes
            StringBuffer function = new StringBuffer();
            function.append(FormulaConverter.FUNCTION_PREFIX);
            long formulaId = pFormula.getId();
            if (formulaId < 0) {
                formulaId = 0;
            }
            function.append(formulaId);
            function.append('(');
            // Traitement de chacun des paramètres
            for (int i = 0; i < pMeasures.length; i++) {
                // Chaque paramètre suit une règle de nommage
                String param = "measure" + i;
                inter.set(param, pMeasures[i]);
                // Cas de plusieurs paramètres
                if (i > 0) {
                    function.append(',');
                }
                function.append(param);
            }
            function.append(')');
            // Appel de la fonction
            result = inter.eval(function.toString());
        } catch (Throwable t) {
            // Renvoi d'une exception si la couche Jython rencontre une erreur
            throw new FormulaException(t);
        }
        return result;
    }

    /**
     * Compilation d'une formule
     * Une optimisation est faite pour éviter de recompiler plusieurs fois
     * la même formule
     * @param pFormula formule à compiler
     */
    private void compileFormula(AbstractFormulaBO pFormula) {
        if (mLastFormulaComputed != pFormula.getId()) {
            FormulaConverter converter = new FormulaConverter();
            String formula = converter.convertFormula(pFormula);
            PythonInterpreter inter = getInterpreter();
            inter.exec(formula);
            mLastFormulaComputed = pFormula.getId();
        }
    }

    /**
     * Conversion d'un résultat Python
     * La conversion s'opère sur les objets de type PyFloat ou PyInteger,
     * un autre objet génère une erreur
     * @param pResult objet à convertir
     * @return valeur correspondante ou null si la conversion ne peut se faire
     * @throws FormulaException si erreur dans le type
     */
    public Double convertResult(PyObject pResult) throws FormulaException {
        Double result;
        // Les fonctions peuvent renvoyer null comme type PYTHON
        // on ne traite donc que les objets de type numérique
        // Attention en Jython, isNumbeType renvoie toujours true
        if (pResult.isNumberType()) {
            double value;
            // Traitement du cas entier
            if (pResult instanceof PyInteger) {
                value = ((PyInteger) pResult).getValue();
            } else if (pResult instanceof PyFloat) {
                value = ((PyFloat) pResult).getValue();
            } else {
                // Le type attendue n'est pas numérique
                throw new FormulaException(RuleMessages.getString("result.badtype", new Object[] { pResult }));
            }
            // Conversion de la valeur en type
            result = new Double(value);
        } else if (pResult instanceof PyNone) {
            // Un résultat vide est généré dans le cas d'un trigger non activé par exemple
            result = null;
        } else {
            // Pour le cas où isNumberType renvoie false ce qui est peu probable
            // pour la version actuelle
            // Le type attendue n'est pas numérique
            throw new FormulaException(RuleMessages.getString("result.badtype", new Object[] { pResult }));
        }
        return result;
    }

    /**
     * Vérification des paramètres
     * @param pFormula formule
     * @param pMeasures mesure
     * @throws FormulaException si erreur
     * @return true si les paramètres sont corrects
     */
    private boolean checkParameters(AbstractFormulaBO pFormula, MeasureBO[] pMeasures) throws FormulaException {
        boolean result = true;
        // Construction d'une map avec les types de mesure et les mesures
        HashMap map = new HashMap();
        Iterator measureKinds = pFormula.getMeasureKinds().iterator();
        int i = 0;
        // Parcours des mesures
        while (measureKinds.hasNext()) {
            map.put(measureKinds.next(), pMeasures[i]);
            i++;
        }
        // Récupération des attributs utilisés par la formule
        ParameterExtraction extracter = new ParameterExtraction();
        Iterator attributes = extracter.getFormulaParameters(pFormula).iterator();
        // On vérifie que les paramètres utilisés dans la formule correspondent bien
        // à des métriques existantes
        while (attributes.hasNext()) {
            FormulaParameter parameter = (FormulaParameter) attributes.next();
            Object measure = map.get(parameter.getMeasureKind());
            // Si la mesure n'existe pas on lève une exception
            if (measure == null) {
                // La métrique est inconnue, on indique ce qui manque
                throw new FormulaException(RuleMessages.getString("measure.unknown", new Object[] { parameter.getMeasureKind()}));
            } else {
                // On vérifie que la mesure contient bien une propriété avec ce nom
                if (getProperty(measure, parameter.getMeasureAttribute()) == null) {
                    result = false;
                }
            }
        }
        return result;
    }

    /**
     * Obtention d'une propriété
     * @param pMeasure mesure
     * @param pAttribute attribut
     * @return propriété si elle existe
     * @throws FormulaException si erreur
     */
    private String getProperty(Object pMeasure, String pAttribute) throws FormulaException {
        String value = null;
        // On pourrait aussi intercepter le type Exception et factoriser
        // le message d'erreur
        try {
            // On essaye d'obtenir la propriété correspondante
            value = BeanUtils.getProperty(pMeasure, pAttribute);
        } catch (IllegalAccessException e) {
            // Renvoi d'une exception encapulée
            throw new FormulaException(RuleMessages.getString("parameter.unknown", new Object[] { pAttribute }), e);
        } catch (InvocationTargetException e) {
            // Renvoi d'une exception encapulée
            throw new FormulaException(RuleMessages.getString("parameter.unknown", new Object[] { pAttribute }), e);
        } catch (NoSuchMethodException e) {
            // Renvoi d'une exception encapulée
            throw new FormulaException(RuleMessages.getString("parameter.unknown", new Object[] { pAttribute }), e);
        }
        return value;
    }
}
