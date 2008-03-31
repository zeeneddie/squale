package com.airfrance.squalix.tools.compiling.java.compiler.impl;

import java.util.List;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalix.tools.compiling.java.adapter.JComponentAdapter;
import com.airfrance.squalix.tools.compiling.java.compiler.eclipse.EclipseCompiler;

/**
 * Classe d'implémentation pour le compilater Eclipse
 */
public class EclipseCompilerImpl
    extends JComponentAdapter
{

    /**
     * Compilateur pour Eclipse.
     */
    private EclipseCompiler eclipseCompiler;

    /**
     * Méthode de lancement de la compilation.
     * 
     * @throws Exception exception.
     */
    public void execute()
        throws Exception
    {
        eclipseCompiler.runCompilation();
        // On modifie les erreurs
        setErrors( eclipseCompiler.getErrors() );
    }

    /**
     * Constructeur par défaut.
     * 
     * @param pProjectList liste des projets WSAD à compiler.
     * @param pViewPath le view path
     * @param eclipseParams eclipse parameters
     */
    public EclipseCompilerImpl( List pProjectList, String pViewPath, MapParameterBO eclipseParams )
    {
        eclipseCompiler = new EclipseCompiler( pProjectList, pViewPath, eclipseParams );
    }

    /**
     * @return le compileur
     */
    public EclipseCompiler getEclipseCompiler()
    {
        return eclipseCompiler;
    }

}
