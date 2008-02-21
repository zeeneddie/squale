/*
 * Créé le 22 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.access.excel.filereader;

import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.addons.access.excel.object.AccessKey;
import com.airfrance.welcom.addons.access.excel.object.Profile;
import com.airfrance.welcom.addons.access.excel.object.ProfileAccessKey;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class AccessKeyReader10 implements IAccessKeyReader {

    /** Workbood du fichier d'accés */
    private Workbook workbook = null;

    /** Feuille qui m'interresse */
    private Sheet sheet = null;

    /** Cache AccessKey */
    private ArrayList cacheAccessKeyList = new ArrayList();

    /** Cache Profile */
    private ArrayList cacheProfileList = new ArrayList();

    /** Cache Profile / AccessKey*/
    private ArrayList cacheProfileAccessKeyList = new ArrayList();
    
    /** Constante pour les cellule nommées */
    private static final String PROFILE = "PROFILE";

    /** Constante pour les cellule nommées */
    private static final String PROFILEID = "PROFILEID";

    /** Constante pour les cellule nommées */
    private static final String ACCESSKEYTAB = "ACCESSKEYTAB";

    /** Constante pour les cellule nommées */
    private static final String ACCESSKEY = "ACCESSKEY";
 
    /** Constante pour les cellule nommées */
    private static final String ACCESSKEYLABEL = "ACCESSKEYLABEL";

    /** Constante pour les cellule nommées */
    private static final String ACCESSKEYTYPE = "ACCESSKEYTYPE";
    

    /**
     * Contruction d'un read de fichier de droits d'accés
     * @param  pWorkbook , classeeur Excel
     * @throws AccessKeyReaderException : probleme de lecture
     */
    public AccessKeyReader10(final Workbook pWorkbook) throws AccessKeyReaderException {
        this.workbook = pWorkbook;
        this.sheet = pWorkbook.getSheet(0);

        // Verifie que le cellules nommées sont bien presente dans le fichier
        checkAllIntegrity();
        // Lecture des droits d'acces
        readAccessKey();
        // Lecture des Profils
        readProfile();
        // Lecture des Profils / droits d'accés
        readProfileAccessKey();
    }


    /**
     * Verifie que toute les cellules nommées sont bien presente dans le fichier
     * @throws AccessKeyReaderException : Ne trouve pas au moins une cellule nommé
     */
    public void checkAllIntegrity() throws AccessKeyReaderException {
        checkIntegrity(PROFILE);
        checkIntegrity(PROFILEID);
        checkIntegrity(ACCESSKEYTAB);
        checkIntegrity(ACCESSKEY);
        checkIntegrity(ACCESSKEYLABEL);
        checkIntegrity(ACCESSKEYTYPE);
    }

    /**
     * Verifie que la cellule nommée est bien presente dans le fichier
     * @param cellName : Nom de la cellule a tester
     * @throws AccessKeyReaderException : Ne ptrouve pas la cellule nommé
     */
    public void checkIntegrity(final String cellName) throws AccessKeyReaderException {
        try{
            workbook.findCellByName(cellName);
        } catch (final java.lang.ArrayIndexOutOfBoundsException e){
            throw new AccessKeyReaderException("La cellule nommé '" + cellName +"' a disparut du fichier xls, verifier avec le fichier d'origine !");
        }
    }



    /**
     * @return Retourne la liste des clefs des droits d'accés definit dans le fichier excel
     */
    public ArrayList getAccessKey() {
        return cacheAccessKeyList;
    }

    /**
     * @return Retourne la liste des profils du fichier excel
     */
    public ArrayList getProfile() {
        return cacheProfileList;
    }

    /**
     * @return Retourne la liste des profils/accesskey du fichier excel
     */
    public ArrayList getProfileAccessKey() {
        return cacheProfileAccessKeyList;
    }

    /** 
     * Lecture des droits de la pages
     * @throws AccessKeyReaderException : probleme de lecture
     */
    public void readProfile() throws AccessKeyReaderException {
        final ArrayList arrayList = new ArrayList();
        final Cell cProfile = workbook.findCellByName("PROFILE");
        final Cell cProfileID = workbook.findCellByName("PROFILEID");

        // Parse en execption si on ne trouve pas de cellule contenant PROFILE / PROFILEID  
        if ((cProfile == null) || (cProfileID == null)) {
            throw new AccessKeyReaderException("La cellule de profil est introuvable");
        }

        // Recupere l'ID de demarrage
        final int idStart = cProfile.getColumn();

        // Pour chaque cellule recupere l'id ...
        for (int i = idStart; i < sheet.getColumns(); i++) {
            
            // Si la cellule n'est pas vide
            if (!GenericValidator.isBlankOrNull(sheet.getCell(i, cProfileID.getRow()).getContents()) && !GenericValidator.isBlankOrNull(sheet.getCell(i, cProfile.getRow()).getContents())) {
                final Profile profiles = new Profile();
                profiles.setIdProfile(sheet.getCell(i, cProfileID.getRow()).getContents().toUpperCase());
                profiles.setName(sheet.getCell(i, cProfile.getRow()).getContents());

                arrayList.add(profiles);

            }
        }

        // Stocke en cache
        cacheProfileList = arrayList;
    }

    /** 
     * Lecture des droits de la pages
     * @throws AccessKeyReaderException : probleme de lecture
     */
    public void readAccessKey() throws AccessKeyReaderException {

        final ArrayList arrayList = new ArrayList();
        final Cell cTab = workbook.findCellByName("ACCESSKEYTAB");
        final Cell cKey = workbook.findCellByName("ACCESSKEY");
        final Cell cLabel = workbook.findCellByName("ACCESSKEYLABEL");
        final Cell cType = workbook.findCellByName("ACCESSKEYTYPE");

        if ((cKey.getRow() != cLabel.getRow()) || (cLabel.getRow() != cTab.getRow()) || (cTab.getRow() != cType.getRow()) || (cType.getRow() != cKey.getRow())) {
            throw new AccessKeyReaderException("Les colonnes pour les droits d'accés ne sont pas aux memes niveaux");
        }

        final int idStart = cKey.getRow() + 1;

        final ArrayList checkUnicityKey = new ArrayList();

        int id = 0;
        for (int i = idStart; i < sheet.getRows(); i++) {

            if (!GenericValidator.isBlankOrNull(sheet.getCell(cKey.getColumn(), i).getContents())) {

                final AccessKey accessKey = new AccessKey();
                final String key = sheet.getCell(cKey.getColumn(), i).getContents().toUpperCase().trim();
                if (checkUnicityKey.contains(key)) {
                    throw new AccessKeyReaderException("La clef '" + key + "' not unique");
                }
                checkUnicityKey.add(key);
                accessKey.setIdAccessKey(id++);
                accessKey.setAccesskey(key);
                accessKey.setLabel(sheet.getCell(cLabel.getColumn(), i).getContents());
                accessKey.setTab(sheet.getCell(cTab.getColumn(), i).getContents());
                accessKey.setType(sheet.getCell(cType.getColumn(), i).getContents().toUpperCase());

                arrayList.add(accessKey);

            }
        }

        // Stocke en cache
        cacheAccessKeyList = arrayList;
    }

    /** 
     * Lecture des droits de la pages avec les profils
     * @throws AccessKeyReaderException : probleme de lecture
     */
    public void readProfileAccessKey() throws AccessKeyReaderException {

        final ArrayList arrayList = new ArrayList();
        final Cell cKey = workbook.findCellByName("ACCESSKEY");
        final Cell cProfileID = workbook.findCellByName("PROFILEID");

        final int iStart = cKey.getRow() + 1;
        final int jStart = cProfileID.getColumn();

        for (int j = jStart; j < sheet.getColumns(); j++) {
            for (int i = iStart; i < sheet.getRows(); i++) {
                final String key = sheet.getCell(cKey.getColumn(), i).getContents();
                final String profil = sheet.getCell(j, cProfileID.getRow()).getContents();
                final String value = sheet.getCell(j, i).getContents();
                if (!GenericValidator.isBlankOrNull(key) && !GenericValidator.isBlankOrNull(profil)) {
                    final ProfileAccessKey profilesAccessKey = new ProfileAccessKey();
                    profilesAccessKey.setAccesskey(key);
                    profilesAccessKey.setIdProfile(profil);
                    profilesAccessKey.setValue(value);

                    arrayList.add(profilesAccessKey);
                }
            }
        }

        // Stocke en cache
        cacheProfileAccessKeyList = arrayList;
    }

}
