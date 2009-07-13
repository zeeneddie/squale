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
 * Créé le 24 janv. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.addons.spell.engine;

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
