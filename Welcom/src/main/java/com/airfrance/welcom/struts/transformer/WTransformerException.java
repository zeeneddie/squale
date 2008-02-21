/*
 * Créé le 25 mai 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.transformer;

import javax.servlet.ServletException;

/**
 * @author M325379
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WTransformerException extends ServletException {

    /**
     * 
     */
    private static final long serialVersionUID = -2151208544577413416L;

    /**
     * Probleme sur la conversion du BO/DTO en AcftionForm (struts) ou inverssement
     */
    public WTransformerException() {
        super();
    }

    /**
     * Probleme sur la conversion du BO/DTO en AcftionForm (struts) ou inverssement
     * @param arg0 : Message
     */
    public WTransformerException(final String arg0) {
        super(arg0);
    }

    /**
     * Probleme sur la conversion du BO/DTO en AcftionForm (struts) ou inverssement
     * @param arg0 Message 
     * @param arg1 throwable
     */
    public WTransformerException(final String arg0, final Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * Probleme sur la conversion du BO/DTO en AcftionForm (struts) ou inverssement
     * @param arg0 throwable
     */
    public WTransformerException(final Throwable arg0) {
        super(arg0);
    }
}