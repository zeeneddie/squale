/*
 * Créé le 5 août 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.enterpriselayer.businessobject.rule;

import org.apache.commons.lang.exception.NestableException;

/**
 * @author M400843
 */
public class ComputeException
    extends NestableException
{
    /**
     * Construit une ComputeException avec le message spécifié
     * 
     * @param pMessage Le message
     */
    public ComputeException( String pMessage )
    {
        super( pMessage );
    }

    /**
     * Construit une ComputeException avec la cause spécifiée
     * 
     * @param pException La cause de l'exception
     */
    public ComputeException( Throwable pException )
    {
        super( pException );
    }
}
