/*
 * Créé le 27 juil. 05, par M400832.
 */
package com.airfrance.squalix.tools.compiling.java.beans;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author m400832
 * @version 1.0
 */
public class JWSADProject extends JProject {

    /** Permer de stocker les entrées du .classpath (autre que "lib") */
    private Map mClasspathEntries = new HashMap();

    /**
     * JVM version that should be used for compiling the project.
     * @since 1.0
     */
    private String mJavaVersion = "";

    /**
     * Project name.
     * @since 1.0
     */
    private String mName = "";

    /**
     * Source path.
     * @since 1.0
     */
    private String mSrcPath = "";

    /**
     * Destination path : folder where to store the compiled classes.
     * @since 1.0
     */
    private String mDestPath = "";

    /**
     * Classpath file name. Eclipse 2.x default classpath file name is 
     * <code>.classpath</code>. This is the default value of this attribute. 
     * @since 1.0
     */
    private String mClasspathExt = "";

    /**
     * Classpath value. In this attribute is stored the classpath value extracted 
     * from the classpath file parsed by the Parser class.
     * @since 1.0
     */
    private String mClasspath = "";

    /**
     * Object containing a list of projects on which another project depends on. 
     * @since 1.0
     */
    private List mDependsOnProjects = null;

    /** 
     * Contient la liste des projets exportés dont dépend le projet 
     * ou la liste des plugins ayant une visibility:=reexport
     */
    private List mExportedProjects = new ArrayList(0);

    /** 
     * Les librairies exportées par le projet 
     */
    private String mExportedLib = "";

    /** 
     * Les packages exportés par le projet 
     */
    private List mExportedPackages = new ArrayList(0);

    /**
     * Boolean value used to check if the project has already been compiled, or not.
     * @since 1.0
     */
    private boolean mIsCompiled = false;

    /**
     * Mémoire requise pour compiler.
     */
    private String mRequiredMemory = "";

    /**
     * Chemin vers le rt.jar de l'API java utilisé.
     */
    private String mBootClasspath = "";

    /**
     * Les répoirtoires exclus de la compilation
     */
    private List mExcludedDirs;

    /**
     * Chemin vers le fichier Manifest.mf du projet
     */
    private String mManifestPath = "";
    /**
     * Fichier du bundle eclipse nécessaire à la compilation
     */
    private File mBundleDir;

    /**
     * @return the classpath file name.
     * @since 1.0
     */
    public String getClasspathExt() {
        return mClasspathExt;
    }

    /**
     * @return the project name.
     * @since 1.0
     */
    public String getName() {
        return mName;
    }

    /**
     * @return the project sources path.
     * @since 1.0
     */
    public String getSrcPath() {
        return mSrcPath;
    }

    /**
     * @return the project destination path.
     * @since 1.0
     */
    public String getDestPath() {
        return mDestPath;
    }

    /**
     * @return the project classpath value.
     * @since 1.0
     */
    public String getClasspath() {
        return mClasspath;
    }

    /**
     * @return the project dependencies.
     * @since 1.0
     */
    public List getDependsOnProjects() {
        ArrayList dependencies = new ArrayList(mExportedProjects);
        if (null != mDependsOnProjects) {
            dependencies.addAll(mDependsOnProjects);
        }
        return dependencies;
    }

    /**
     * @return mJavaVersion the JDK version.
     * @since 1.0
     */
    public String getJavaVersion() {
        return mJavaVersion;
    }

    /**
     * This method sets the classpath file name value, <b>if not null or void</b>.
     * @param pClasspathExt the classpath file name.
     * @since 1.0
     */
    public void setClasspathExt(String pClasspathExt) {
        if (null != pClasspathExt && !"".equals(pClasspathExt)) {
            mClasspathExt = pClasspathExt;
        }
    }

    /**
     * @param pName the project name.
     * @since 1.0
     */
    public void setName(String pName) {
        mName = pName;
    }

    /**
     * @param pSrcPath the project path to java sources.
     * @since 1.0
     */
    public void setSrcPath(String pSrcPath) {
        mSrcPath = pSrcPath;
    }

    /**
     * @param pClasspath the project classpath.
     * @since 1.0
     */
    public void setClasspath(String pClasspath) {
        mClasspath = pClasspath;
    }

    /**
     * @param pDependsOnProjects the project dependencies.
     * @since 1.0
     */
    public void setDependsOnProjects(List pDependsOnProjects) {
        mDependsOnProjects = pDependsOnProjects;
    }

    /**
     * @param pDestPath the project destination path.
     * @since 1.0
     */
    public void setDestPath(String pDestPath) {
        mDestPath = pDestPath;
    }

    /**
     * @param pJavaVersion the JDK version.
     * @see org.apache.tools.ant.Project#JAVA_1_0
     * @see org.apache.tools.ant.Project#JAVA_1_1
     * @see org.apache.tools.ant.Project#JAVA_1_2
     * @see org.apache.tools.ant.Project#JAVA_1_3
     * @see org.apache.tools.ant.Project#JAVA_1_4
     * @since 1.0
     */
    public void setJavaVersion(String pJavaVersion) {
        if (null != pJavaVersion && !"".equals(pJavaVersion)) {
            mJavaVersion = pJavaVersion;
        }
    }

    /**
     * @param pIsCompiled set <code>true</code> if the project has already been 
     * compiled, <code>false</code> otherwise.
     * @since 1.0
     */
    public void setCompiled(boolean pIsCompiled) {
        mIsCompiled = pIsCompiled;
    }

    /**
     * @return isCompiled <code>true</code> if the project has already been compiled, 
     * <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean isCompiled() {
        return mIsCompiled;
    }

    /**
     * Getter.
     * @return la mémoire requise pour compiler.
     */
    public String getRequiredMemory() {
        return mRequiredMemory;
    }

    /**
     * Setter.
     * @param pRequiredMemory la mémoire requise pour compiler.
     */
    public void setRequiredMemory(String pRequiredMemory) {
        mRequiredMemory = pRequiredMemory;
    }

    /**
     * This method adds a dependency for the current project. <br /><br />
     * For example : <br /><br />
     * <code>project1.addProjectDependency(project2)</code><br /><br />
     * means that <code>project1</code> depends on <code>project2</code> to be 
     * successfully compiled.
     * <br /><br />
     * @param pProj JWSADProject on which depends the current project.
     * @since 1.0
     */
    public void addProjectDependency(JWSADProject pProj) {
        if (null == mDependsOnProjects) {
            mDependsOnProjects = new ArrayList();
        }
        mDependsOnProjects.add(pProj);
    }

    /**
     * This method adds a dependency for the current project. <br /><br />
     * For example : <br /><br />
     * <code>project1.addProjectDependency(project2)</code><br /><br />
     * means that <code>project1</code> depends on <code>project2</code> to be 
     * successfully compiled.
     * <br /><br />
     * @param pProj JWSADProject on which depends the current project.
     * @since 1.0
     */
    public void addExportedProject(JWSADProject pProj) {
        mExportedProjects.add(pProj);
    }

    /**
     * This method tests if a project has dependencies, or not.
     * @return <code>true</code> if it has, <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean hasDependency() {
        boolean has = false;
        if (null != mDependsOnProjects && mDependsOnProjects.size() > 0 || mExportedProjects.size() > 0) {
            has = true;
        }
        return has;
    }

    /**
     * Indique si le projet est bien un projet WSAD
     * @return false si aucun fichier .project n'est trouvé
     */
    public boolean isWSAD() {
        boolean result = true;
        String path = getPath();
        File projectFile = new File(path, ".project");
        // dès qu'il y en a un qui n'existe pas on sort de la boucle
        result = projectFile.exists();
        // vrai si il y a au moins un path de défini
        // et que le projet associé existe
        return result;
    }

    /**
     * @return les réperoires exclus
     */
    public List getExcludedDirs() {
        return mExcludedDirs;
    }

    /**
     * @param pDirs les réperoires exclus
     */
    public void setExcludedDirs(List pDirs) {
        mExcludedDirs = pDirs;
    }

    /**
     * @return le chemin vers le manifest
     */
    public String getManifestPath() {
        return mManifestPath;
    }

    /**
     * @param pManifestPath le chemin vers le manifest
     */
    public void setManifestPath(String pManifestPath) {
        mManifestPath = pManifestPath;
    }

    /**
     * Ajoute un répertoire exclu à la liste des répertoires exclus
     * @param pDir le répertoire exclu
     */
    public void addToExcludeDirs(String pDir) {
        if (null == mExcludedDirs) {
            mExcludedDirs = new ArrayList();
        }
        mExcludedDirs.add(pDir);
    }

    /**
     * @return le bundle eclipse
     */
    public File getBundleDir() {
        return mBundleDir;
    }

    /**
     * @param pBundlePath le chemin ver le bundle eclipse
     */
    public void setBundleDir(String pBundlePath) {
        mBundleDir = new File(pBundlePath);
    }
    /**
     * @return les librairies exportées sous la forme du classpath
     */
    public String getExportedLib() {
        return mExportedLib;
    }

    /**
     * @return la liste des projets exportés
     */
    public List getExportedProjects() {
        return mExportedProjects;
    }

    /**
     * @param pExportedLib le nouveau "classpath" des libraries exportées
     */
    public void setExportedLib(String pExportedLib) {
        mExportedLib = pExportedLib;
    }

    /**
     * @return les packages exportés
     */
    public List getExportedPackages() {
        return mExportedPackages;
    }

    /**
     * Ajoute un package exporté
     * @param pPackage le package à ajouter
     */
    public void addExportedPackage(String pPackage) {
        mExportedPackages.add(pPackage);
    }

    /**
     * @return les entrées du .classpath à traiter après
     * les entrées dont la clé est "lib"
     */
    public Map getClasspathEntries() {
        return mClasspathEntries;
    }
    
    /**
     * Ajoute pValue à la liste identifiée par la clé pKey dans la map des entrées du .classpath
     * @param pKey la clé correspondant à une entrée du classpath (src, con,...)
     * @param pValue la valeur correspondant à l'entrée du classpath (/NomProjet, ...)
     */
    public void addClasspathEntrie(String pKey, String pValue) {
        Collection values = (Collection)getClasspathEntries().get(pKey);
        if(null == values) {
            // On crée une liste vide
            values = new ArrayList();
        }
        // On rajoute la valeur à la liste
        values.add(pValue);
        // On remplace l'ancienne valeur par la nouvelle
        getClasspathEntries().put(pKey, values);
    }

    /**
     * @param pMap les entrées du .classpath à traiter après
     * les entrées dont la clé est "lib"
     */
    public void setClasspathEntries(Map pMap) {
        mClasspathEntries = pMap;
    }

    /**
     * @return le chemin pour l'option -bootclasspath de javac
     */
    public String getBootClasspath() {
        return mBootClasspath;
    }

    /**
     * @param pBootClasspath le chemin pour l'option -bootclasspath de javac
     */
    public void setBootClasspath(String pBootClasspath) {
        mBootClasspath = pBootClasspath;
    }

}
