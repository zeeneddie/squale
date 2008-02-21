/*
 * Créé le 11 août 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import javax.servlet.jsp.JspException;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface ICol {

    /**
      * Definit le rendu de la colonne ... si spécifique
      * @param bean : Bean
      * @param position la position de la colonne
      * @param idIndex Index
      * @param style Style
      * @param styleSelect Style selectionne
      * @return le html généré
      */
    public String getCurrentValue(int position,Object bean,int idIndex,String style,String styleSelect, int pageLength) throws JspException;
  

}
