/*
 * Créé le 22 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.access.excel.filereader;

import java.util.ArrayList;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface IAccessKeyReader {


    /**
     * @return Retourne la liste des clefs des droits d'accés definit dans le fichier excel
     */
    public ArrayList getAccessKey();
    
    /**
     * @return Retourne la liste des profils du fichier excel
     */
    public ArrayList getProfile(); 
 

    /**
     * @return Retourne la liste des profils/accesskey du fichier excel
     */
    public ArrayList getProfileAccessKey(); 

}
