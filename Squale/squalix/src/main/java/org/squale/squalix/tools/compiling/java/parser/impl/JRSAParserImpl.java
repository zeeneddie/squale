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
package org.squale.squalix.tools.compiling.java.parser.impl;

import java.util.List;

import org.squale.squalix.tools.compiling.java.adapter.JComponentAdapter;
import org.squale.squalix.tools.compiling.java.parser.rsa.JRSAParser;

/**
 * Implémentation de l'adapteur du parser pour RSA7
 */
public class JRSAParserImpl
    extends JComponentAdapter
{

    /**
     * @param pProjectList la liste des projets
     * @throws Exception erreur
     */
    public JRSAParserImpl( List pProjectList )
        throws Exception
    {
        mRSAParser = new JRSAParser( pProjectList );
    }

    /**
     * Parser à utiliser.
     */
    private JRSAParser mRSAParser;

    /**
     * Méthode de lancement du parsing.
     * 
     * @throws Exception exception lancée en cas d'erreur du parsing.
     */
    public void execute()
        throws Exception
    {
        mRSAParser.execute();
    }

    /**
     * Getter.
     * 
     * @return la liste des projets WSAD.
     */
    public List getProjectList()
    {
        return mRSAParser.getProjectList();
    }
}
