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
/*
 * Créé le 27 juil. 05, par M400832.
 */
package com.airfrance.squalix.tools.compiling.java.parser.configuration;

import com.airfrance.squalix.tools.compiling.java.configuration.JCompilingConfiguration;

/**
 * Configuration générique des parseurs.
 * 
 * @author m400832
 * @version 1.0
 */
public class JParserConfiguration
    extends JCompilingConfiguration
{

    /**
     * Extension des fichiers <code>class</code>.
     */
    public static final String EXT_CLASS = "class";

    /**
     * Extension des fichiers <code>zip</code>.
     */
    public static final String EXT_ZIP = "zip";

    /**
     * Extension des fichiers <code>jar</code>.
     */
    public static final String EXT_JAR = "jar";

    /**
     * Nom du fichier de classpath à parser.
     */
    private String mFilename = "";

    /**
     * Constructeur par défaut.
     * 
     * @throws Exception exception.
     */
    public JParserConfiguration()
        throws Exception
    {
        super();
    }

    /**
     * Getter
     * 
     * @return le nom du fichier de classpath à parser.
     */
    public String getFilename()
    {
        return mFilename;
    }

    /**
     * Setter.
     * 
     * @param pFilename le nom du fichier de classpath à parser.
     */
    public void setFilename( String pFilename )
    {
        mFilename = pFilename;
    }
}
