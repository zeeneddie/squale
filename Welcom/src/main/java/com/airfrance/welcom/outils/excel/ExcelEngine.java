/*
 * Créé le 9 nov. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.excel;

/**
 * @author M327836
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ExcelEngine {
    
    /** Declration du moteur JEXCEL */    
    public final static ExcelEngine JEXCEL = new ExcelEngine("jexcel");
    
    /** nom du moteur */
    private String name="";
    
    /**
     * Constructeur
     * @param engineName engine excel
     */
    private ExcelEngine(final String engineName){
        name=engineName;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
       
        return name;
    }


}
