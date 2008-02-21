/*
 * Créé le 28 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.excel;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Interface à implémenter pour le formatage de la sortie des fichiers Excel.
 * @author Rémy Bouquet
 *
 */
public interface ExcelGenerateur {

    /**
     * 
     * @throws ExcelGenerateurException : Level une erreur sur la production du fichier
     */
    public abstract void writeExcel() throws ExcelGenerateurException;

    /**
     * 
     * @throws IOException : Probleme lors de l'ouverture des streams
     */
    public abstract void init() throws IOException;

    /**
     * 
     * assigne l'outputstream
     * @param os outputStream à setter
     * @throws ExcelGenerateurException exception pouvant etre levee
     */
    public void open(OutputStream os) throws ExcelGenerateurException;

}