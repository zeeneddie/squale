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
package com.airfrance.welcom.addons.spell.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.airfrance.welcom.addons.spell.bean.WSpellCheckerBean;
import com.airfrance.welcom.addons.spell.bean.WSpellField;
import com.airfrance.welcom.addons.spell.bean.WSpellMistake;
import com.airfrance.welcom.addons.spell.engine.SpellingHelper;
import com.airfrance.welcom.addons.spell.engine.WSpellChecker;
import com.swabunga.spell.engine.Configuration;
import com.swabunga.spell.engine.Word;
import com.swabunga.spell.event.SpellCheckEvent;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;
import com.swabunga.spell.event.XMLWordFinder;

/*
 * Créé le 25 mai 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WSpellCheckerAction
    extends DispatchAction
{

    /**
     * @see org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward unspecified( final ActionMapping mapping, final ActionForm form,
                                      final HttpServletRequest request, final HttpServletResponse response )
        throws Exception
    {
        try
        {
            // SpellChecker spellChecker =
            // (SpellChecker)request.getSession().getServletContext().getAttribute("welcom.spellCheck");

            final SpellChecker spellChecker = WSpellChecker.getSpellChecker( getLocale( request ).getLanguage() );

            final SpellingHelper spellingHelper = new SpellingHelper();
            spellChecker.addSpellCheckListener( spellingHelper );

            final Configuration configuration = spellChecker.getConfiguration();

            boolean keepGoing = true;
            int element = -1;

            // WSpellCheckerBean wsp = (WSpellCheckerBean)request.getAttribute("wSpellCheckerBean");
            final WSpellCheckerBean wsp = (WSpellCheckerBean) form;

            while ( keepGoing && ( wsp.getFields().size() > element + 1 ) )
            {
                element++;
                final WSpellField field = ( (WSpellField) ( wsp.getFields().get( element ) ) );
                final String value = field.getValue();
                final String formElement = field.getName();
                if ( ( value == null ) || ( formElement == null ) )
                {
                    keepGoing = false;
                    continue;
                }

                spellChecker.checkSpelling( new StringWordTokenizer( new XMLWordFinder( value ) ) );
                final List spellCheckEvents = spellingHelper.getSpellCheckEvents();
                spellingHelper.reset();

                final Iterator iterCheckEvents = spellCheckEvents.iterator();
                while ( iterCheckEvents.hasNext() )
                {
                    final SpellCheckEvent spellCheckEvent = (SpellCheckEvent) iterCheckEvents.next();

                    final WSpellMistake sm = new WSpellMistake();
                    sm.setWordContextPosition( spellCheckEvent.getWordContextPosition() );
                    sm.setInvalidWord( spellCheckEvent.getInvalidWord() );

                    final Iterator iterSuggestion = spellCheckEvent.getSuggestions().iterator();
                    while ( iterSuggestion.hasNext() )
                    {
                        final Word word = (Word) iterSuggestion.next();
                        sm.addSuggestions( word.toString() );
                    }

                    field.addMistake( sm );
                }
            }
        }
        catch ( final Exception e )
        {
            e.printStackTrace();
        }

        return mapping.findForward( "success" );
    }

}
