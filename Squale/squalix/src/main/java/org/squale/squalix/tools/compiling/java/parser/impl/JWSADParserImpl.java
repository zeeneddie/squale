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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\tools\\compiling\\java\\parser\\ParserAdapterWSADImpl.java

package com.airfrance.squalix.tools.compiling.java.parser.impl;

import java.util.List;

import com.airfrance.squalix.tools.compiling.java.adapter.JComponentAdapter;
import com.airfrance.squalix.tools.compiling.java.parser.wsad.JWSADParser;

/**
 * Implémentation de l'adapteur du parser pour WSAD 5.x.
 * 
 * @author m400832
 * @version 1.0
 */
public class JWSADParserImpl
    extends JComponentAdapter
{

    /**
     * Parser à utiliser.
     */
    private JWSADParser mWSADParser;

    /**
     * Méthode de lancement du parsing.
     * 
     * @throws Exception exception lancée en cas d'erreur du parsing.
     */
    public void execute()
        throws Exception
    {
        mWSADParser.execute();
        // On modifie la liste des erreurs
        mErrors.addAll( mWSADParser.getErrors() );
    }

    /**
     * Constructeur par défaut.
     * 
     * @param pProjectList liste des projets WSAD à parser.
     * @throws Exception exception en cas d'erreur.
     */
    public JWSADParserImpl( List pProjectList )
        throws Exception
    {
        mWSADParser = new JWSADParser( pProjectList );
    }

    /**
     * Getter.
     * 
     * @return la liste des projets WSAD.
     */
    public List getProjectList()
    {
        return mWSADParser.getProjectList();
    }
}
