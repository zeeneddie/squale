/*
 * Créé le 29 juil. 05, par m400832.
 */
package com.airfrance.squalix.tools.compiling.java.beans;

import org.apache.tools.ant.BuildListener;

/**
 * @author m400832
 * @version 1.0
 */
public class JProject {

    /**
     * Chemin vers le projet.
     */
    private String mPath;
    
    /**
     * L'écouteur
     */
    private BuildListener listener;

    /**
     * Setter.
     * @param pPath Chemin vers le projet.
     * @since 1.0
     */
    public void setPath(String pPath) {
        mPath = pPath;
    }
    
    /**
     * Getter.
     * @return Chemin vers le projet.
     */
    public String getPath(){
        return mPath;
    }
    
    
    /**
     * @return le listener
     */
    public BuildListener getListener() {
        return listener;
    }

    /**
     * @param pListener le nouveau listener
     */
    public void setListener(BuildListener pListener) {
        listener = pListener;
    }

}
