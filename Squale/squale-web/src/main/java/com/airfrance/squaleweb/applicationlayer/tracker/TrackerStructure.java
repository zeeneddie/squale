package com.airfrance.squaleweb.applicationlayer.tracker;

/**
 * Classe qui définit un objet utile seulement pour le traceur
 * @author M403988
 */
public class TrackerStructure {

    /** 
     * La liste des types possibles
     */
    /**
     * Cette valeur est pour quand on est passé par une vue Facteur
     */
    public final static int FACTOR_VIEW = 0;
    /**
     * Cette valeur est pour quand on est passé par une vue Composant
     */
    public final static int COMPONENT_VIEW = 1;
    /**
     * Cette valeur est pour quand on est passé par une vue Top
     */
    public final static int TOP_VIEW = 2;
    /**
     * Valeur par défault
     */
    public final static int UNDEFINED = 3;
    
    /**
     * Le nom du lien tel qu'il est affiché
     */
    private String mDisplayName;
    /**
     * La valeur du lien
     */
    private String mLink;
    /**
     * Le type de l'élément
     */
    private int mType;
    
    /**
     * Constructeur sans parametres
     *
     */
    public TrackerStructure() {
        mDisplayName = "";
        mLink = "";
        mType = UNDEFINED;
    }
    
    /**
     * Constructeur
     * @param pDisplayName le nouveau nom à afficher 
     * @param pLink le nouveau lien
     * @param pType le nouveau type
     */
    public TrackerStructure(String pDisplayName,String pLink, int pType) {
        mDisplayName = pDisplayName;
        mLink = pLink;
        mType = pType;
    }
    
    /**
     * @return le nom du lien tel qu'affiché
     */
    public String getDisplayName() {
        return mDisplayName;
    }

    /**
     * @return la valeur du lien
     */
    public String getLink() {
        return mLink;
    }

    /**
     * @return le type de l'élément
     */
    public int getType() {
        return mType;
    }

    /**
     * @param pDisplayName le nouveau nom 
     */
    public void setDisplayName(String pDisplayName) {
        mDisplayName = pDisplayName;
    }

    /**
     * @param pLink la nouvelle valeur du lien
     */
    public void setLink(String pLink) {
        mLink = pLink;
    }

    /**
     * @param pType le nouveau type de l'élément
     */
    public void setType(int pType) {
        mType = pType;
    }

}