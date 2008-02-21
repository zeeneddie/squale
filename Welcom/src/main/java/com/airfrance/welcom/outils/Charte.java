package com.airfrance.welcom.outils;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class Charte {

    /** charte v2.001 */
    public static final Charte V2_001 = new Charte("v2", "001");
    /** charte v2.002 */
    public static final Charte V2_002 = new Charte("v2", "002");
    
    /** charte v3.001 */
    public static final Charte V3_001 = new Charte("v3", "001");
    

    /** version major */
    private String versionMajor;

    /** version minor */
    private String versionMinor;

    /**
     * Contructeur
     * @param pVersionMajor Version majeur
     * @param pVersionMinor Version Mineur
     */
    private Charte(final String pVersionMajor, final String pVersionMinor) {
        this.versionMajor = pVersionMajor;
        this.versionMinor = pVersionMinor;
    }

    /** 
     * Version major 
     * @return Version Major
     * */
    public String getVersionMajor() {
        return versionMajor;
    }

    /**
     *  Version minor 
     * @return Version Minor
     * */
    public String getVersionMinor() {
        return versionMinor;
    }

    /**
     * Concatenation de "charte"+versionMajor
     * @return "charte"+versionMajor
     */
    public String getWelcomConfigPrefix() {
        return "charte" + versionMajor;
    }

    /**
     * Concatenation de "charte"+versionMajor+"."+versionMinor
     * @return "charte"+versionMajor+"."+versionMinor
     */
    public String getWelcomConfigFullPrefix() {
        return getWelcomConfigPrefix() + "." + versionMinor;
    }

    /**
     * Retourn vrais on on est configurer en charte V2
     * @return charte V2
     */
    public boolean isV2() {
        return this == V2_001 || this == V2_002;
    }

    /**
     * Retourne vrais si on est en charte V3
     * @return true si charte V3
     */
    public boolean isV3() {
        return this == V3_001;
    }

    /**
     *  To string
     * @return le numero de version  
     * */
    public String toString() {
        return "Version Major : " + versionMajor + ", Version Minor :" + versionMinor;
    }

}
