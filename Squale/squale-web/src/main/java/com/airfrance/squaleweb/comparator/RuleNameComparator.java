package com.airfrance.squaleweb.comparator;

import java.util.Comparator;
import java.util.Locale;

import com.airfrance.squaleweb.resources.WebMessages;

/**
 * Comparateur pour les nom des règles internationalisés
 */
public class RuleNameComparator implements Comparator {
    
    /** La locale */
    private Locale mLocale;
    
    /**
     * Constructeur par défaut
     * @param pLocale la locale
     */
    public RuleNameComparator(Locale pLocale) {
        mLocale = pLocale;
    }

    /** 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * {@inheritDoc}
     */
    public int compare(Object ruleName1, Object ruleName2) {
        int result = 1;
        // Vérification des éléments
        String key1 = WebMessages.getString(mLocale, (String)ruleName1);
        String key2 = WebMessages.getString(mLocale, (String)ruleName2);
        if(null != key1 && null != key2) {
            result = key1.compareTo(key2);
        }
        return result;
    }

}
