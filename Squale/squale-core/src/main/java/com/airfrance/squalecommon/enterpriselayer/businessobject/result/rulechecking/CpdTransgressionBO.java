package com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;

/**
 * Résultat de transgression de Cpd
 * Une transgression de copy/paste contient le nombre total de lignes dupliquées,
 * et pour chaque langage le détail des transgressions ainsi que le nombre total
 * de lignes dupliquées pour un langage.
 * @hibernate.subclass
 * discriminator-value="CpdTransgression"
 */
public class CpdTransgressionBO extends RuleCheckingTransgressionBO {
    /**
     * Nombre de lignes dupliquées au total
     */
    private final static String DUPLICATE_LINES_NUMBER="dupLinesNumber";

    /**
     * Constructeur
     * @roseuid 42B9751A0293
     */
    public CpdTransgressionBO() {
        super();
        IntegerMetricBO metric = new IntegerMetricBO();
        metric.setValue(new Integer(0));
        metric.setMeasure(this);
        getMetrics().put(DUPLICATE_LINES_NUMBER, metric);
    }

    /**
     * Access method for the mLinesNumber property.
     * 
     * @return   the current value of the mLinesNumber property
     * 
     */
    public Integer getDuplicateLinesNumber() {
        return (Integer) ((IntegerMetricBO) getMetrics().get(DUPLICATE_LINES_NUMBER)).getValue();
    }

    /**
     * Sets the value of the mLinesNumber property.
     * 
     * @param pLinesNumber the new value of the mLinesNumber property
     */
    public void setDuplicateLinesNumber(Integer pLinesNumber) {
        ((IntegerMetricBO) getMetrics().get(DUPLICATE_LINES_NUMBER)).setValue(pLinesNumber);
    }
    
    /**
     * Incrément du nombre de lignes dupliquées
     * @param pLineNumber nombre de lignes à ajouter
     */
    public void incrementDuplicateLinesNumber(int pLineNumber) {
        setDuplicateLinesNumber(new Integer(pLineNumber+getDuplicateLinesNumber().intValue()));
    }
    
    /**
     * Création lazy de la métrique
     * Les métriques de lignes de code dupliquées sont dépendantes du langage, elles sont donc
     * crées de façon dynamique suivant le besoin
     * @param pLanguage langage
     * @return métrique asscociée
     */
    private IntegerMetricBO getMetricForLanguage(String pLanguage) {
        String pMetricKey = pLanguage+".nb";
        IntegerMetricBO result = (IntegerMetricBO) getMetrics().get(pMetricKey);
        if (result==null) {
            result = new IntegerMetricBO();
            getMetrics().put(pMetricKey, result);
        }
        return result;
    }
    
    /**
     * 
     * @param pLanguage langage
     * @return nombre de lignes dupliquées pour le langage
     */
    public Integer getDuplicateLinesForLanguage(String pLanguage) {
        return (Integer) getMetricForLanguage(pLanguage).getValue();
    }

    /**
     * 
     * @param pLanguage langage
     * @param pValue valeur
     */
    public void setDuplicateLinesForLanguage(String pLanguage, Integer pValue) {
        getMetricForLanguage(pLanguage).setValue(pValue);
    }
}
