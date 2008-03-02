/*
 * Créé le 23 nov. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.message;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.PropertyMessageResourcesFactory;

/**
 * @author M325379 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WPropertyMessageResourcesFactory
    extends PropertyMessageResourcesFactory
{

    /**
     * 
     */
    private static final long serialVersionUID = -4202316858713721860L;

    /**
     * @see org.apache.struts.util.MessageResourcesFactory#createResources(java.lang.String)
     */
    public MessageResources createResources( final String configuration )
    {
        return new WPropertyMessageResources( this, configuration, getReturnNull() );
    }

}
