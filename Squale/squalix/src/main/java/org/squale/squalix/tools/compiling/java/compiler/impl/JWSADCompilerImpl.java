/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\org\\squale\\squalix\\tools\\compiling\\java\\compiler\\CompilerAdapterWSADImpl.java

package org.squale.squalix.tools.compiling.java.compiler.impl;

import java.util.List;

import org.squale.squalix.tools.compiling.java.adapter.JComponentAdapter;
import org.squale.squalix.tools.compiling.java.compiler.wsad.JWSADCompiler;

/**
 * Implémentation de l'adapteur pour le compilateur WSAD 5.x.
 * 
 * @author m400832 (by rose)
 * @version 1.0
 */
public class JWSADCompilerImpl
    extends JComponentAdapter
{

    /**
     * Compilateur pour WSAD.
     */
    private JWSADCompiler mWSADCompiler;

    /**
     * Méthode de lancement de la compilation.
     * 
     * @throws Exception exception.
     */
    public void execute()
        throws Exception
    {
        mWSADCompiler.runCompilation();
    }

    /**
     * Constructeur par défaut.
     * 
     * @param pProjectList liste des projets WSAD à compiler.
     */
    public JWSADCompilerImpl( List pProjectList )
    {
        mWSADCompiler = new JWSADCompiler( pProjectList );
    }
}
