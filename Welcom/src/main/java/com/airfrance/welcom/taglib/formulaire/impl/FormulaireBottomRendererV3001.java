package com.airfrance.welcom.taglib.formulaire.impl;

import com.airfrance.welcom.outils.TrimStringBuffer;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.taglib.button.ButtonBarTag;
import com.airfrance.welcom.taglib.formulaire.IFormulaireBottomRenderer;

/**
 * 
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class FormulaireBottomRendererV3001 implements IFormulaireBottomRenderer {

    /**
     * @see com.airfrance.welcom.taglib.formulaire.IFormulaireBottomRenderer#drawTable(java.lang.String)
     */
    public String drawTable(String bottomvalue) {

        final TrimStringBuffer buf = new TrimStringBuffer();

        // buf.append("<!-- Génération de la barre de boutons -->\n");
        buf.append("\t<table class=\"formulaire\">\n");
        buf.append("<tfoot><tr><td>");
        if (Util.isTrimNonVide(ButtonBarTag.purgeBodyHidden(bottomvalue))) {
            buf.append(bottomvalue);
        }
        buf.append("</td></tr></tfoot>");
        // buf.append("<!-- Fin de la Génération de la barre de boutons -->\n");
        buf.append("\t</table>\n");

        return buf.toString();

    }

}
