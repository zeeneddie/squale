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
package org.squale.squalecommon.util.initialisor;

import java.io.File;
import java.util.HashMap;

import org.squale.jraf.bootstrap.initializer.Initializer;
import org.squale.jraf.bootstrap.locator.ProviderLocator;
import org.squale.jraf.commons.exception.JrafDaoException;

/**
 * @author M400843 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class JRafConfigurator
{
    /**
     * Instance singleton
     */
    private static JRafConfigurator instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new JRafConfigurator();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private JRafConfigurator()
    {
        /** chemin racine repertoire - racine du classpath */
        File rootPath =
            new File(
                      Thread.currentThread().getContextClassLoader().getResource( "config/providers-config.xml" ).getPath() ).getParentFile();

        /** chemin du fichier de configuration relatif au classpath */
        String configFile = "/providers-config.xml";

        Initializer init = new Initializer( rootPath.getAbsolutePath(), configFile );
        ProviderLocator.setProviderLocator( new ProviderLocator( new HashMap() ) );
        init.initialize();
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static JRafConfigurator initialize()
    {
        return instance;
    }
}
