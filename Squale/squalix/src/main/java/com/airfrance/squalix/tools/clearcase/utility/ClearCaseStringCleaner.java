/*
 * Créé le 6 sept. 05, par m400832.
 */
package com.airfrance.squalix.tools.clearcase.utility;

import com.airfrance.squalix.tools.clearcase.configuration.ClearCaseConfiguration;


/**
 * @author m400832
 * @version 1.0
 */
public class ClearCaseStringCleaner {

    /**
     * Cette méthode retourne une chaîne en minuscules, sans caractères spéciaux ; 
     * i.e. uniquement des lettres, des chiffres et des "<code>_</code>" 
     * (qui est utilisé comme caractère de remplacement).<br />
     * Typiquement :<br /><pre>
     * a0é~&èïëöüüä1245cacahuèes ' hihi#{((ldkqsmiopnncwx,ncxcwx)\\|-||
     * </pre> deviendra : <br /><pre>
     * a0__________1245cacahu_es___hihi____ldkqsmiopnncwx_ncxcwx______
     * </pre>
     * @param pStringToBeCleaned chaîne à nettoyer.
     * @return la chaîne nettoyée.
     */
    public static String getCleanedStringFrom(String pStringToBeCleaned) {
        StringBuffer tmp = new StringBuffer();
        char car;
        
        int i=0;
        /* tant que l'on est pas arrivé au bout de la chaîne */
        while(i < pStringToBeCleaned.length()){
            
            /* on récupère le caractère à traiter */
            car = pStringToBeCleaned.charAt(i);
            
            /* s'il s'agit d'un caractère spécial et/ou accentué */
            if(Character.isJavaIdentifierPart(car) && 
                Character.getNumericValue(car) >= 0) {
                    
                tmp.append(car);
            /* sinon */
            } else { 
                tmp.append(ClearCaseConfiguration.UNDERSCORE);
            }
            i++;
        }
        
        /* on retourne la chaîne nettoyée */
        return tmp.toString().toLowerCase();
    }
}
