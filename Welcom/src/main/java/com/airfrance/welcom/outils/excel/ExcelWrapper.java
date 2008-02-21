/*
 * Créé le 28 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableWorkbook;

/**
 *
 * @author Rémy BOuquet
 *
 */
public class ExcelWrapper implements ExcelGenerateur {

    /**
     * Document Excel 
     */
    protected WritableWorkbook workbook = null;

    /**
     * Flux ou l'on ecrit le resultat de la generation excel
     */
    protected OutputStream os = null;

    /**
     * 
     * @param fluxSortant Stream pour ecrire le flux
     */
    public ExcelWrapper(final OutputStream fluxSortant) {
        os = fluxSortant;
    }

    /**
     * Constreur vide
     *
     */
    public ExcelWrapper() {
    }

    /**
     * Initilisation du classeur Excel
     * @throws IOException Erreur a la creation du classeur
     */
    public void init() throws IOException {
        WorkbookSettings workbookSettings = new WorkbookSettings();
        workbookSettings.setLocale(Locale.FRENCH);
        workbook = Workbook.createWorkbook(os, workbookSettings);
    }

    /**
     * @see com.airfrance.welcom.outils.excel.ExcelGenerateur#writeExcel()
     */
    public void writeExcel() throws ExcelGenerateurException {
        if (workbook == null) {
            throw new ExcelGenerateurException("Le workbook est vide ou n'a pas été initialisé.");
        }

        if (os == null) {
            throw new ExcelGenerateurException("Le flux de sortie n'a pas été initialisé");
        }

        try {
            workbook.write();
            workbook.close();
        } catch (final Exception e) {
            throw new ExcelGenerateurException("Une erreur s'est produite lors de l'écriture de fichier excel",e);
        }
    }

    /**
     * Setter du flux de sortie.
     * @param fluxSortant : le flux de sortie.
     */
    public void setOutputStream(final OutputStream fluxSortant) {
        os = fluxSortant;
    }

    /**
     * @return le classeur Excel
     */
    public WritableWorkbook getWorkbook() {
        return workbook;
    }

    /**
     * @see com.airfrance.welcom.outils.excel.ExcelGenerateur#open(java.io.OutputStream)
     */
    public void open(final OutputStream pOs) throws ExcelGenerateurException {
        setOutputStream(pOs);
    }

}