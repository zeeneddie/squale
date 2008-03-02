package com.airfrance.welcom.struts.bean;

/*
 * Créé le 6 mai 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface WILogonBeanSecurity
    extends WILogonBean
{
    /**
     * Fonction retounant l'accés a la page
     * 
     * @param accessKey l'accessKey
     * @return : READWRITE : page en lecture / ecriture READONLY : page en lecture seule NONE : Aucun accés a la page
     *         YES : Accessible NO : Non accéssible
     */
    public String getAccess( String accessKey );
}