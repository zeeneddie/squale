package com.airfrance.squaleweb.taskconfig;

/**
 * Classe définissant les infos générique nécessaire pour les champs d'une JSP
 */
public abstract class AbstractInfoConfig
{

    /** La clé du label */
    private String mKey;

    /** Le nom du champ */
    private String mProperty;

    /**
     * @return renvoi la clé pour le label
     */
    public String getKey()
    {
        return mKey;
    }

    /**
     * @return renvoi le nom du champ
     */
    public String getProperty()
    {
        return mProperty;
    }

    /**
     * @param pKey insert la clé pour le field
     */
    public void setKey( String pKey )
    {
        mKey = pKey;
    }

    /**
     * @param pProperty insert le nom du champ
     */
    public void setProperty( String pProperty )
    {
        mProperty = pProperty;
    }

    /**
     * constructeur
     * 
     * @param pKey Clé pour le label
     * @param pProperty Nom du champ
     */
    public AbstractInfoConfig( String pKey, String pProperty )
    {
        mKey = pKey;
        mProperty = pProperty;
    }

}
