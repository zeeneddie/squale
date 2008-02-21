package com.airfrance.welcom.taglib.formulaire.impl;

import com.airfrance.welcom.outils.TrimStringBuffer;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.button.ButtonBarTag;
import com.airfrance.welcom.taglib.formulaire.IFormulaireBottomRenderer;

/**
 * 
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class FormulaireBottomRendererV200X implements IFormulaireBottomRenderer{

    /** le pixel gris*/
    private final String imgPixGrey = WelcomConfigurator.getMessage(WelcomConfigurator.CHARTEV2_PIX_GREY_GIF);

    /**
     * @see com.airfrance.welcom.taglib.formulaire.IFormulaireBottomRenderer#drawTable(java.lang.String)
     */
    public String drawTable(String bottomvalue) {

           final TrimStringBuffer buf = new TrimStringBuffer();

            if (Util.isTrimNonVide(ButtonBarTag.purgeBodyHidden(bottomvalue))) {
                //buf.append("<!-- Génération de la barre de boutons -->\n");  
                buf.append("\t<table class=\"wide\">\n");

                buf.append("\t\t<tr class=trtete align=\"center\" height=\"25\"> \n");

                buf.append(bottomvalue);

                buf.append("\t\t</tr>\n");

                //buf.append("<!-- Fin de la Génération de la barre de boutons -->\n");  
                buf.append("\t</table>\n");
            } else {
                buf.append("\t<table class=\"wide\">\n");
                buf.append("\t\t<tr>\n");
                buf.append("<td height=\"1\"><img src=\"" + imgPixGrey + "\" height=\"1\" width=\"100%\"></td> \n");
                buf.append("\t\t</tr>\n");
                buf.append("\t</table>\n");
            }
            
            return buf.toString();
        
    }


}
