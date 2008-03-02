/*
 * Créé le 22 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface ITableRenderer
{

    /**
     * Rendu : pour l'ecriture de l'enpied de table
     * 
     * @param bottomValue : valeur de l'enpied a inserer
     * @param width taille
     * @return rendu
     */
    public String drawTableBottom( String bottomValue, String width );

    /**
     * Rendu : titre de la tables
     * 
     * @param columns column
     * @return rendu
     */
    public String drawTableTitle( String columns );

    /**
     * Rendu : debut de la table
     * 
     * @param tableName nom de la table
     * @param width : taille
     * @return rendu
     */
    public String drawTableStart( String tableName, String width );

    /**
     * Rendu : fin de la table
     * 
     * @return rendu
     */
    public String drawTableEnd();

    /**
     * Rendu dessin le conteneur de la barre de navigation
     * 
     * @param navigation navigation
     * @param totalLabel libelle du total
     * @param width taille
     * @return rendu
     */
    public String drawNavigation( String totalLabel, String navigation, String width );

}