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
 * Créé le 1 févr. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.plugin;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.validator.GenericValidator;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WelcomContext
{

    /** Nom de context Welcom, resoud le pb de clonnage */
    private final static String DEFAULT_WELCOM_CONTEXT_NAME = "Welcom";

    /** Nombre d'initialisation du context */
    private static int nbInit = 0;

    /**
     * @return le nom du welcomContext (java:com/env) declaré sur le serveur
     */
    private static String lookupWelcomContextName()
    {
        InitialContext ic;
        String welcomContextName;
        try
        {
            ic = new InitialContext();
            welcomContextName = (String) ic.lookup( "java:comp/env/welcomContext" );
        }
        catch ( final NamingException e )
        {
            welcomContextName = DEFAULT_WELCOM_CONTEXT_NAME;
        }
        return welcomContextName;
    }

    /** Nom du contexte sur le threadLocal */
    private static ThreadLocal threadLocal = new ThreadLocal();

    /**
     * Initalise le WelcomContextName
     */
    static void initWelcomContextName()
    {
        nbInit++;
    }

    /**
     * Reinitialise le contexte de Welcom
     */
    public static void resetWelcomContextName()
    {
        synchronized ( threadLocal )
        {
            threadLocal.set( lookupWelcomContextName() );
        }
    }

    /**
     * Recuperele contextName de Welcom
     * 
     * @return le nom du contexte de welcom
     */
    public static String getWelcomContextName()
    {
        String welcomContextName = DEFAULT_WELCOM_CONTEXT_NAME;
        if ( nbInit > 1 )
        {
            welcomContextName = (String) threadLocal.get();
            if ( GenericValidator.isBlankOrNull( welcomContextName ) )
            {
                welcomContextName = lookupWelcomContextName();
                threadLocal.set( welcomContextName );
            }
        }
        return welcomContextName;
    }

}
