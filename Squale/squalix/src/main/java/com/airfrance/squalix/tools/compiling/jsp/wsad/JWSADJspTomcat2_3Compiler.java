package com.airfrance.squalix.tools.compiling.jsp.wsad;

import java.io.File;

import org.apache.tools.ant.taskdefs.Java;

import com.airfrance.squalix.tools.compiling.jsp.bean.J2eeWSADProject;

/**
 * 
 */
public class JWSADJspTomcat2_3Compiler extends AbstractTomcatCompiler {

    /**
     * @param pProject le projet
     */
    public JWSADJspTomcat2_3Compiler(J2eeWSADProject pProject) {
        super(pProject);
    }

    /** 
     * {@inheritDoc}
     * @see com.airfrance.squalix.tools.compiling.jsp.wsad.AbstractTomcatCompiler#setJavaArgs(org.apache.tools.ant.taskdefs.Java, java.lang.String, java.lang.String, java.io.File)
     */
    protected void setJavaArgs(Java java, String packageName, String className, File jspFile) {
        // Répertoire de destination
        java.createArg().setValue("-d");
        java.createArg().setValue(mJ2eeProject.getJspDestPath() + File.separator + packageName.replace('.', File.separatorChar));
        // Package : On indique un nom de package généré
        java.createArg().setValue("-p");
        java.createArg().setValue(packageName);
        // Package : On indique un nom de la classe générée
        java.createArg().setValue("-c");
        java.createArg().setValue(className);
        // On indique le chemin racine vers l'application web
        java.createArg().setValue("-uriroot");
        java.createArg().setValue(mJ2eeProject.getPath());
        // La jsp à générer
        java.createArg().setValue(jspFile.getAbsolutePath());
    }

}

