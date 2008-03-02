/*
 * Créé le 24 janv. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.spell.engine;

import java.util.LinkedList;
import java.util.List;

import com.swabunga.spell.event.SpellCheckEvent;
import com.swabunga.spell.event.SpellCheckListener;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class SpellingHelper
    implements SpellCheckListener
{
    /** List of SpellCheckEvents. */
    private List spellCheckEvents = new LinkedList();

    /**
     * Called by the Spell Checker.
     * 
     * @param spellCheckEvent Event
     */
    public void spellingError( final SpellCheckEvent spellCheckEvent )
    {
        spellCheckEvents.add( spellCheckEvent );
    }

    /**
     * An easy way to get the events.
     * 
     * @return List des events
     */
    public List getSpellCheckEvents()
    {
        return spellCheckEvents;
    }

    /**
     * Reset the list of events between calls to SpellChecker.checkSpelling(...).
     */
    public void reset()
    {
        spellCheckEvents = new LinkedList();
    }
}
