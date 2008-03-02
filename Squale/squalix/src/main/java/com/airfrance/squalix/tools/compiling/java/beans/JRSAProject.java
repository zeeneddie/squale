package com.airfrance.squalix.tools.compiling.java.beans;

/**
 * Projet RSA7
 */
public class JRSAProject
    extends JWSADProject
{

    /**
     * @param myProject le projet WSAD dont on copie les valeur
     */
    public JRSAProject( JWSADProject myProject )
    {
        setClasspath( myProject.getClasspath() );
        setClasspathExt( myProject.getClasspathExt() );
        setCompiled( myProject.isCompiled() );
        setDependsOnProjects( myProject.getDependsOnProjects() );
        setDestPath( myProject.getDestPath() );
        setExcludedDirs( myProject.getExcludedDirs() );
        setJavaVersion( myProject.getJavaVersion() );
        setListener( myProject.getListener() );
        setName( myProject.getName() );
        setPath( myProject.getPath() );
        setRequiredMemory( myProject.getRequiredMemory() );
        setSrcPath( myProject.getSrcPath() );
        setBundleDir( myProject.getBundleDir().getAbsolutePath() );
        setBootClasspath( myProject.getBootClasspath() );
    }

    /** Le nom du projet EAR associé */
    private String mEARProjectName = "";

    /**
     * @return le nom du projet EAR associé
     */
    public String getEARProjectName()
    {
        return mEARProjectName;
    }

    /**
     * @param pEARName le nom du projet EAR associé
     */
    public void setEARProjectName( String pEARName )
    {
        if ( pEARName != null )
        {
            mEARProjectName = pEARName;
        }
    }
}
