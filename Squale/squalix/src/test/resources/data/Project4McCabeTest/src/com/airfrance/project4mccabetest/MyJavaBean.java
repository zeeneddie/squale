package com.airfrance.project4mccabetest;

import sun.security.provider.MD5;

/**
 * Classe de test permettant de tester l'exécution de la tâche McCabe
 * sous Unix.
 * 
 * @author M400842
 */
public class MyJavaBean {

    /**
     * Nombre de lignes dans le fichier lu
     */
    private int mLineNumber;
    
    /**
     * Nom du fichier lu
     */
    private String mFileName = null;
    
    /**
     * Durée de lecture en ms.
     */
    private long mReadingTime;
    
    
    /**
     * @return le nom du fichier lu.
     */
    public String getFileName() {
        return mFileName;
    }

    /**
     * @return le nombre de lignes lues.
     */
    public int getLineNumber() {
        return mLineNumber;
    }

    /**
     * @return la durée de lecture.
     */
    public long getReadingTime() {
        return mReadingTime;
    }

    /**
     * @param pFileName le nom du fichier lu.
     */
    public void setFileName(String pFileName) {
        mFileName = pFileName;
    }

    /**
     * @param pLineNumber le nombre de lignes lues.
     */
    public void setLineNumber(int pLineNumber) {
        mLineNumber = pLineNumber;
    }

    /**
     * @param pReadingTime la durée de lecture.
     */
    public void setReadingTime(long pReadingTime) {
        mReadingTime = pReadingTime;
    }
    
    /**
     * @return l'objet sous forme de chaîne.
     */
    public String toString() {
        String string = System.getProperty("line.separator");
        string += "Nom du fichier : " + mFileName + System.getProperty("line.separator");
        string += "Nombre de lignes lues : " + mLineNumber + System.getProperty("line.separator");
        string += "Durée de lecture : " + mReadingTime + " ms." + System.getProperty("line.separator");
        return string;
    }


}
