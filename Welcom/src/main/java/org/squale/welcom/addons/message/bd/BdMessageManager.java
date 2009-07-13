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
 * Créé le 5 août 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.addons.message.bd;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.welcom.outils.jdbc.WJdbc;
import org.squale.welcom.outils.jdbc.WJdbcMagic;


/**
 *
 */
public class BdMessageManager
{
    /** logger */
    private static Log log = LogFactory.getLog( BdMessageManager.class );

    /** singleton */
    private static BdMessageManager instance = null;

    /** Instance du bd create */
    private static BdMessageCreate bdCreate = null;

    /**
     * Constructeur private pour le singleton
     */
    private BdMessageManager()
    {

    }

    /**
     * @return l'instance de BdMessageManager
     */
    public static BdMessageManager getInstance()
    {
        if ( instance == null )
        {
            instance = new BdMessageManager();
        }
        return instance;
    }

    /**
     * initialisation
     */
    public void init()
    {
        WJdbc jdbc = null;
        try
        {

            jdbc = new WJdbcMagic();

            // Verification et creation des tables
            getBdCreate().checkAnCreateAllTable( jdbc );

            jdbc.commit();

        }
        catch ( final SQLException e )
        {
            log.error( e, e );
        }
        finally
        {
            if ( jdbc != null )
            {
                jdbc.close();
            }
        }

    }

    /**
     * Recupere l'instance du BD create
     * 
     * @return Creation de BD
     */
    public BdMessageCreate getBdCreate()
    {
        if ( bdCreate == null )
        {
            bdCreate = new BdMessageCreate();
        }
        return bdCreate;
    }

}
