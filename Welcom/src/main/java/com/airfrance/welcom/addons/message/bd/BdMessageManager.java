/*
 * Créé le 5 août 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.message.bd;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.welcom.outils.jdbc.WJdbc;
import com.airfrance.welcom.outils.jdbc.WJdbcMagic;

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
