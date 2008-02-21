package com.airfrance.squalecommon.datatransfertobject.message;

/**
 * 
 */
public class MessageDTO {

    /** le titre du message, dans le cas d'une news */
    private String title;

    /** le texte du message */
    private String text;

    /** la langue du message */
    private String lang;

    /** la clé de message */
    private String key;

    /**
     * @return la clé
     */
    public String getKey() {
        return key;
    }

    /**
     * @return la langue
     */
    public String getLang() {
        return lang;
    }

    /**
     * @return le texte
     */
    public String getText() {
        return text;
    }

    /**
     * @return le titre
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param newKey la nouvelle valeur de la clé
     */
    public void setKey(String newKey) {
        key = newKey;
    }

    /**
     * @param newLang la nouvelle valeur de la langue
     */
    public void setLang(String newLang) {
        lang = newLang;
    }

    /**
     * @param newText la nouvelle valeur du texte
     */
    public void setText(String newText) {
        text = newText;
    }

    /**
     * @param newTitle la nouvelle valeur du titre
     */
    public void setTitle(String newTitle) {
        title = newTitle;
    }

}