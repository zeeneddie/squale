/*
 * Créé le 3 févr. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.spell.engine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.welcom.addons.spell.exception.WSpellCheckerNotFound;
import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellChecker;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WSpellChecker
{

    /** logger */
    private static Log logStartup = LogFactory.getLog( "Welcom" );

    /** Instance du WSpellChecker */
    private static WSpellChecker instance = null;

    /** Map des dictionnaire en fonction de la langue */
    private final HashMap spellCheckerLocale = new HashMap();

    /**
     * Contructeur cahcé, cf pattern singleton.
     */
    private WSpellChecker()
    {

    }

    /**
     * Recherche si le spellChecker est chargé dans la map
     * 
     * @param locale : locale
     * @return : Le spell checker
     * @throws WSpellCheckerNotFound Le dictionnaire n'est pas disponible
     */
    private SpellChecker findSpellChecker( final String locale )
        throws WSpellCheckerNotFound
    {

        synchronized ( spellCheckerLocale )
        {
            if ( !spellCheckerLocale.containsKey( locale ) )
            {
                spellCheckerLocale.put( locale, loadSpellChecker( locale ) );
            }
            return (SpellChecker) spellCheckerLocale.get( locale );
        }
    }

    /**
     * Chargement du spell checker pour une locale
     * 
     * @param locale : locale
     * @return : SpelleChecker
     * @throws WSpellCheckerNotFound Le dictionnaire n'est pas disponible
     */
    private SpellChecker loadSpellChecker( final String locale )
        throws WSpellCheckerNotFound
    {

        final long startTime = System.currentTimeMillis();

        logStartup.info( "Chargement du  dictionnaire '" + locale + "' en cours " );

        final SpellChecker spellCheck = new SpellChecker();

        final ClassLoader myLoader = this.getClass().getClassLoader();

        try
        {

            final String dictFile = "dico/" + locale + "/word.0";
            final String phonetFile = "dico/" + locale + "/phonet.0";

            logStartup.debug( "Dico File : " + dictFile + ", " + myLoader.getResourceAsStream( dictFile ) );
            logStartup.debug( "Phonet File : " + phonetFile + ", " + myLoader.getResourceAsStream( phonetFile ) );

            spellCheck.addDictionary( new SpellDictionaryHashMap(
                                                                  new java.io.InputStreamReader(
                                                                                                 myLoader.getResourceAsStream( dictFile ) ),
                                                                  new java.io.InputStreamReader(
                                                                                                 myLoader.getResourceAsStream( phonetFile ) ) ) );

        }
        catch ( final FileNotFoundException e )
        {
            throw new WSpellCheckerNotFound( e );
        }
        catch ( final IOException e )
        {
            throw new WSpellCheckerNotFound( e );
        }

        logStartup.info( "Chargement du  dictionnaire '" + locale + "' terminé ("
            + ( System.currentTimeMillis() - startTime ) + "ms)" );

        return spellCheck;
    }

    /**
     * Retourne un dictionnaire
     * 
     * @param locale en fonction de la locale
     * @return Le dicionnaire
     * @throws WSpellCheckerNotFound Le dictionnaire n'est pas disponible
     */
    public static SpellChecker getSpellChecker( final String locale )
        throws WSpellCheckerNotFound
    {
        if ( instance == null )
        {
            instance = new WSpellChecker();
        }
        return instance.findSpellChecker( locale );
    }

}
