//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\tools\\compiling\\java\\compiler\\CompilerAdapterXMLImpl.java

package com.airfrance.squalix.tools.compiling.java.compiler.impl;

import com.airfrance.squalix.tools.compiling.java.adapter.JComponentAdapter;
import com.airfrance.squalix.tools.compiling.java.beans.JXMLProject;
import com.airfrance.squalix.tools.compiling.java.compiler.xml.JXMLCompiler;

/**
 * Implémentation de l'adapteur pour le compilateur de fichiers type <code>build.xml</code>.
 * 
 * @author m400832
 * @version 1.0
 */
public class JXMLCompilerImpl
    extends JComponentAdapter
{

    /**
     * Compilateur de fichiers <code>build.xml</code>.
     */
    private JXMLCompiler mXMLCompiler;

    /**
     * Méthode d'exécution.
     * 
     * @throws Exception exception
     */
    public void execute()
        throws Exception
    {
        mXMLCompiler.execute();
    }

    /**
     * Constructeur.
     * 
     * @param pProject project XML.
     */
    public JXMLCompilerImpl( JXMLProject pProject )
    {
        mXMLCompiler = new JXMLCompiler( pProject );
    }

    /**
     * Getter.
     * 
     * @return un projet JXMLProject.
     */
    public JXMLProject getXMLProject()
    {
        return mXMLCompiler.getXMLProject();
    }
}
