/*
 * Créé le 28 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.ajax;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Rémy Bouquet Wrapper de construction de réponse xml pour un message de confirmation dynamique.
 */
public class WHttpConfirmationMessageResponse
    extends AjaxXmlResponseWrapper
{

    /**
     * constructeur
     * 
     * @param presponse la response HTTP
     */
    public WHttpConfirmationMessageResponse( final HttpServletResponse presponse )
    {
        super( presponse, "confirm" );
    }

    /**
     * Ajoute un message à la réponse XML
     * 
     * @param message message à ajouter
     */
    public void sendMessage( final String message )
    {
        addItem( "message", message );
    }

}
