//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\util\\csv\\CSVConfiguration.java

package com.airfrance.squalix.util.csv;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Spécifie pour chaque type de fichier susceptible d'être parsé :
 * <ul>
 * <li>le nom du template</li>
 * <li>la classe concrète entièrement spécifiée du CSVBean correspondant</li>
 * <li>et pour chaque colonne du fichier csv, le nom de l'attribut de la classe 
 * concrète lui correspondant, ainsi que son 
 * type objet entièrement spécifié (et pas simple)</li>
 * </ul>
 * @author m400842
 * @version 1.0
 */
public class CSVConfiguration {
    
    /**
     * Nombre de lignes d'en-tête à ne pas prendre en compte
     */
    private int mHeaderSize;
    
    /**
     * Nombre de lignes de pied de page à ne pas prendre en compte
     */
    private int mFooterSize;
    
    /**
     * Nom du modèle de mapping utilisé
     */
    private String mTemplateName;
    
    /**
     * Nom de la classe des objets à mapper
     */
    private String mCSVBean;
    
    /**
     * Contient les couples "numéro de colonne" => "methode"
     */
    private HashMap mMethods;
    
    /**
     * Instancie une nouvelle configuration avec le nom du modèle
     * 
     * @param pTemplateName le nom du modèle
     * @roseuid 42B2BE430148
     */
    public CSVConfiguration(final String pTemplateName) {
        mTemplateName = pTemplateName;
    }
    
    /**
     * Access method for the mTemplateName property.
     * 
     * @return   the current value of the mTemplateName property
     * @roseuid 42CE6C6E0081
     */
    public String getTemplateName() {
        return mTemplateName;
    }
    
    /**
     * Access method for the mCSVBean property.
     * 
     * @return   the current value of the mCSVBean property
     * @roseuid 42CE6C6E0082
     */
    public String getCSVBean() {
        return mCSVBean;
    }
    
    /**
     * Access method for the mMethods property.
     * 
     * @return   the current value of the mMethods property
     * @roseuid 42CE6C6E0083
     */
    public HashMap getMethods() {
        return mMethods;
    }
    
    /**
     * Access method for the mHeaderSize property.
     * 
     * @return   the current value of the mHeaderSize property
     * @roseuid 42CE6C6E0090
     */
    public int getHeaderSize() {
        return mHeaderSize;
    }
    
    /**
     * Retourne le couple de mapping correspondant à la colonne passée en paramètre.
     * 
     * @param pColumn Le numéro de la colonne dans le fichier CSV
     * @return Le setter correspondant à la colonne.
     * @roseuid 42CE745D023E
     */
    public Method getMappingData(int pColumn) {
        Integer column = new Integer(pColumn);
        Method method = null;
        if (mMethods.containsKey(column)) {
            method = (Method) mMethods.get(column);
        }
        return method;
    }
    
    /**
     * Sets the value of the mHeaderSize property.
     * 
     * @param pHeaderSize the new value of the mHeaderSize property
     * @roseuid 42CE749B0146
     */
    public void setHeaderSize(int pHeaderSize) {
        mHeaderSize = pHeaderSize;
    }
    
    /**
     * Access method for the mFooterSize property.
     * 
     * @return   the current value of the mFooterSize property
     * @roseuid 42CE749B0156
     */
    public int getFooterSize() {
        return mFooterSize;
    }
    
    /**
     * Sets the value of the mFooterSize property.
     * 
     * @param pFooterSize the new value of the mFooterSize property
     * @roseuid 42CE749B0164
     */
    public void setFooterSize(int pFooterSize) {
        mFooterSize = pFooterSize;
    }
    
    /**
     * Sets the value of the mCSVBean property.
     * 
     * @param pCSVBean the new value of the mCSVBean property
     * @roseuid 42DFB34601C5
     */
    public void setCSVBean(String pCSVBean) {
        mCSVBean = pCSVBean;
    }
    
    /**
     * Sets the value of the mMethods property.
     * 
     * @param pMethods the new value of the mMethods property
     * @roseuid 42DFB34601D4
     */
    public void setMethods(HashMap pMethods) {
        mMethods = pMethods;
    }
}
