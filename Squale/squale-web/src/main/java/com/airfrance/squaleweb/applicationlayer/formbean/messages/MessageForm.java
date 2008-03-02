package com.airfrance.squaleweb.applicationlayer.formbean.messages;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 */
public class MessageForm
    extends RootForm
{

    /** le text du message */
    private String text = "";

    /** le langage dans lequel est écrit le message */
    private String language = "";

    /** la clé définissant le message */
    private String key = "";

    /** le titre du message */
    private String title = "";

    /** pour identifier les messages par un id de type long */
    private long messageIdSequ;

    /**
     * @return l'id du message
     */
    public long getMessageIdSequ()
    {
        return messageIdSequ;
    }

    /**
     * @param newMessageSequenceId le nouvel id du message
     */
    public void setMessageIdSequ( long newMessageSequenceId )
    {
        messageIdSequ = newMessageSequenceId;
    }

    /**
     * @return la clé
     */
    public String getKey()
    {
        return key;
    }

    /**
     * @return la langue dans lequel est écrit le message
     */
    public String getLanguage()
    {
        return language;
    }

    /**
     * @return le text du message
     */
    public String getText()
    {
        return text;
    }

    /**
     * @param newKey la nouvelle valeur de la clé
     */
    public void setKey( String newKey )
    {
        key = newKey;
    }

    /**
     * @param newLang la nouvelle langue du message
     */
    public void setLanguage( String newLang )
    {
        language = newLang;
    }

    /**
     * @param newText le nouveau text du message
     */
    public void setText( String newText )
    {
        text = newText;
    }

    /**
     * @return le titre
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param newTitle le nouveau titre
     */
    public void setTitle( String newTitle )
    {
        title = newTitle;
    }

}
