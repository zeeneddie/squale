package com.airfrance.squalix.tools.compiling.jsp.bean;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.tools.ant.BuildListener;

import com.airfrance.squalix.tools.compiling.java.beans.JWSADProject;
import com.airfrance.squalix.util.file.ExtensionFileFilter;
import com.airfrance.squalix.util.file.FileUtility;

/**
 * Projet J2EE sous WSAD
 */
public class J2eeWSADProject extends JWSADProject {
    
    /** Extension des fichiers jars */
    public static final String JAR_FILE_EXTENSION = ".jar";

    /** Le nom par défaut du répertoire destiné à recevoir les .java des JSPs compilées */
    public static final String JAVA_DEST = "jspToJava";

    /** Le nom par défaut du répertoire contenant les .class des jsps */
    public static final String CLASSES_DEST = JAVA_DEST + "/jsp_classes";
    
    /** nombre d'index de niveau 2 dans le tableau des chemins vers les jsps */
    public static final int NB_ID = 2;
    /** index dans le tableau des chemins vers les jsps représentant le chemin vers le répertoire source */
    public static final int DIR_ID = 0;
    /** index dans le tableau des chemins vers les jsps représentant la liste des jsps à compiler */
    public static final int JSP_LIST_ID = 1;

    /** Le classpath dont le séparateur est ";" */
    private String mClasspath;

    /** 
     * Chemins vers les JSPs de la forme
     * mJspPaths[i][0] -> chemin absolu du répertoire source jsp d'index i dans les paramétres de configuration
     * mJspPaths[i][1] -> liste des .jsp à compiler du répertoire mJspPaths[i][0]
     */
    private Object[][] mJspPaths;

    /** Le répertoire detiné à recevoir les .java des JSPs compilées */
    private String mJspDestPath;

    /** L'écouteur */
    private BuildListener mListener;

    /** 
     * Constructeur par défaut
     */
    public J2eeWSADProject() {
        mJspPaths = new Object[0][0];
        mJspDestPath = JAVA_DEST;
    }

    /**
     * @return les chemins vers les JSPs
     */
    public Object[][] getJspPaths() {
        return mJspPaths;
    }

    /**
     * @param pJspPaths les chemins vers les JSPs
     */
    public void setJspPaths(Object[][] pJspPaths) {
        mJspPaths = pJspPaths;
    }

    /**
     * @return le répertoire de destination
     */
    public String getJspDestPath() {
        return mJspDestPath;
    }

    /**
     * @param pJspDestPath le répertoire de destination
     */
    public void setJspDestPath(String pJspDestPath) {
        mJspDestPath = pJspDestPath;
    }

    /**
     * @return le classpath du projet
     */
    public String getClasspath() {
        return mClasspath;
    }

    /**
     * @param pClasspath le classpath du projet
     */
    public void setClasspath(String pClasspath) {
        mClasspath = pClasspath;
    }

    /**
     * @return l'écouteur
     */
    public BuildListener getListener() {
        return mListener;
    }

    /**
     * @param pListener l'écouteur
     */
    public void setListener(BuildListener pListener) {
        mListener = pListener;
    }

    /** 
     * Ajoute les jars contenus dans le répertoire en début de classpath
     * <code>pDir</code>
     * @param pDir le répertoire contenant les jars
     */
    public void addJarDirToClasspath(File pDir) {
        HashSet jars = new HashSet();
        FileUtility.createRecursiveListOfFiles(pDir, new ExtensionFileFilter(JAR_FILE_EXTENSION), jars);
        for (Iterator it = jars.iterator(); it.hasNext();) {
            String jar = (String) it.next();
            mClasspath =  jar + File.pathSeparator + mClasspath;
        }
    }

}
