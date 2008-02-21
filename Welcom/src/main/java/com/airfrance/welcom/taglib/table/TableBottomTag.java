/*
 * Créé le 15 avr. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class TableBottomTag extends BodyTagSupport {
    /**
     * 
     */
    private static final long serialVersionUID = -811401005085569582L;

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        //Recherche si un parent est du bon type
        Tag curParent = null;

        for (curParent = getParent();(curParent != null) && !(curParent instanceof TableTag);) {
            curParent = curParent.getParent();
        }

        final TableTag tableTag = (TableTag) curParent;

        if (tableTag == null) {
            throw new JspException("TableButtonsTag  must be used between Table Tag.");
        }

        tableTag.setBottomValue(getBodyContent().getString());

        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {
        return EVAL_PAGE;
    }
}