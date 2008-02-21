/*
 * Créé le 10 juin 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.field;

import com.airfrance.welcom.taglib.util.WTagSupport;

/**
 * EventHandlerTag
 */
public class EventHandlerTag extends WTagSupport {
    /**
     * 
     */
    private static final long serialVersionUID = 3385392117341931614L;
    /** parametre du tag */
    private String tabindex;
    /** parametre du tag */
    private String onclick;
    /** parametre du tag */
    private String ondblclick;
    /** parametre du tag */
    private String onmouseover;
    /** parametre du tag */
    private String onmouseout;
    /** parametre du tag */
    private String onmousemove;
    /** parametre du tag */
    private String onmousedown;
    /** parametre du tag */
    private String onmouseup;
    /** parametre du tag */
    private String onkeydown;
    /** parametre du tag */
    private String onkeyup;
    /** parametre du tag */
    private String onkeypress;
    /** parametre du tag */
    private String onselect;
    /** parametre du tag */
    private String onchange;
    /** parametre du tag */
    private String onblur;
    /** parametre du tag */
    private String onfocus;
    /** parametre du tag */
    private String onitemselection;

    /**
     * Constructeur
     */
    public EventHandlerTag() {
        tabindex = null;
        onclick = null;
        ondblclick = null;
        onmouseover = null;
        onmouseout = null;
        onmousemove = null;
        onmousedown = null;
        onmouseup = null;
        onkeydown = null;
        onkeyup = null;
        onkeypress = null;
        onselect = null;
        onchange = null;
        onblur = null;
        onfocus = null;
    }

    /**
     * 
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release() {
        super.release();
        tabindex = null;
        onclick = null;
        ondblclick = null;
        onmouseover = null;
        onmouseout = null;
        onmousemove = null;
        onmousedown = null;
        onmouseup = null;
        onkeydown = null;
        onkeyup = null;
        onkeypress = null;
        onselect = null;
        onchange = null;
        onblur = null;
        onfocus = null;
    }

    /**
     * prepare les proprietes liees aux evenements(souris, clavier, text et focus)
     * @return le html genere
     */
    protected String prepareEventHandlers() {
        final StringBuffer handlers = new StringBuffer();
        prepareMouseEvents(handlers);
        prepareKeyEvents(handlers);
        prepareTextEvents(handlers);
        prepareFocusEvents(handlers);

        return handlers.toString();
    }

    /**
     * prepare les proprietes liees aux evenements souris 
     * @param handlers le stringbuffer
     */
    protected void prepareMouseEvents(final StringBuffer handlers) {
        
        addParam(handlers, "onclick", onclick);
        addParam(handlers, "ondblclick", ondblclick);
        addParam(handlers, "onmouseover", onmouseover);
        addParam(handlers, "onmouseout", onmouseout);
        addParam(handlers, "onmousemove", onmousemove);
        addParam(handlers, "onmousedown", onmousedown);
        addParam(handlers, "onmouseup", onmouseup);

    }

    /**
     * prepare les proprietes liees aux evenements clavier 
     * @param handlers le buffer
     */
    protected void prepareKeyEvents(final StringBuffer handlers) {

        addParam(handlers, "onkeydown", onkeydown);
        addParam(handlers, "onkeyup", onkeyup);
        addParam(handlers, "onkeypress", onkeypress);

    }

    /**
     * prepareles proprietes liees aux evenements de texte
     * @param handlers le stringbuffer
     */
    protected void prepareTextEvents(final StringBuffer handlers) {
        addParam(handlers, "onselect", onselect);
        addParam(handlers, "onchange", onchange);
    }

    /**
     * prepare les proprietes liees au focus
     * @param handlers le stringbuffer
     */
    protected void prepareFocusEvents(final StringBuffer handlers) {
        addParam(handlers, "onblur", onblur);
        addParam(handlers, "onfocus", onfocus);
    }

    /**
     * @return onblur
     */
    public String getOnblur() {
        return onblur;
    }

    /**
     * @return onchange
     */
    public String getOnchange() {
        return onchange;
    }

    /**
     * @return onclick
     */
    public String getOnclick() {
        return onclick;
    }

    /**
     * @return ondblclick
     */
    public String getOndblclick() {
        return ondblclick;
    }

    /**
     * @return onfocus
     */
    public String getOnfocus() {
        return onfocus;
    }

    /**
     * @return onkeydown
     */
    public String getOnkeydown() {
        return onkeydown;
    }

    /**
     * @return onkeypress
     */
    public String getOnkeypress() {
        return onkeypress;
    }

    /**
     * @return onkeyup
     */
    public String getOnkeyup() {
        return onkeyup;
    }

    /**
     * @return onmousedown
     */
    public String getOnmousedown() {
        return onmousedown;
    }

    /**
     * @return onmousemove
     */
    public String getOnmousemove() {
        return onmousemove;
    }

    /**
     * @return onmouseout
     */
    public String getOnmouseout() {
        return onmouseout;
    }

    /**
     * @return onmouseover
     */
    public String getOnmouseover() {
        return onmouseover;
    }

    /**
     * @return onmouseup
     */
    public String getOnmouseup() {
        return onmouseup;
    }

    /**
     * @return onselect
     */
    public String getOnselect() {
        return onselect;
    }

    /**
     * @return tabindex
     */
    public String getTabindex() {
        return tabindex;
    }

    /**
     * @param string le onblur
     */
    public void setOnblur(final String string) {
        onblur = string;
    }

    /**
     * @param string le onchange
     */
    public void setOnchange(final String string) {
        onchange = string;
    }

    /**
     * @param string le onclick
     */
    public void setOnclick(final String string) {
        onclick = string;
    }

    /**
     * @param string le ondblclick
     */
    public void setOndblclick(final String string) {
        ondblclick = string;
    }

    /**
     * @param string le onfocus
     */
    public void setOnfocus(final String string) {
        onfocus = string;
    }

    /**
     * @param string le onkeydown
     */
    public void setOnkeydown(final String string) {
        onkeydown = string;
    }

    /**
     * @param string le onkeypress
     */
    public void setOnkeypress(final String string) {
        onkeypress = string;
    }

    /**
     * @param string le onkeyup
     */
    public void setOnkeyup(final String string) {
        onkeyup = string;
    }

    /**
     * @param string le onmousedown
     */
    public void setOnmousedown(final String string) {
        onmousedown = string;
    }

    /**
     * @param string le onmousemove
     */
    public void setOnmousemove(final String string) {
        onmousemove = string;
    }

    /**
     * @param string le onmouseoust
     */
    public void setOnmouseout(final String string) {
        onmouseout = string;
    }

    /**
     * @param string le onmouseover
     */
    public void setOnmouseover(final String string) {
        onmouseover = string;
    }

    /**
     * @param string le onmouseup
     */
    public void setOnmouseup(final String string) {
        onmouseup = string;
    }

    /**
     * @param string le onselect
     */
    public void setOnselect(final String string) {
        onselect = string;
    }

    /**
     * @param string le tabindex
     */
    public void setTabindex(final String string) {
        tabindex = string;
    }
    /**
     * @return onitemselection
     */
    public String getOnitemselection() {
        return onitemselection;
    }

    /**
     * @param string onitemselection
     */
    public void setOnitemselection(final String string) {
        onitemselection = string;
    }

}