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
package org.squale.squalix.messages;

import java.util.TreeMap;
import java.util.Iterator;
import java.util.LinkedList;
import org.squale.squalix.messages.Messages;

public class MessageMailManager
{
    /**
     * Corps du message
     */
    private TreeMap<String, String> mBody;

    /**
     * Validité des parties du message par langue
     */
    private TreeMap<String, Boolean> mValidity;

    /**
     * Liste des langues
     */
    private String[] mLanguages;

    /**
     * Historique des clés de message demandées
     */
    private LinkedList<TreeMap<String, String[]>> mHistory;

    /**
     * Constructeur public
     */
    public MessageMailManager()
    {
        getLanguages();
        mValidity = new TreeMap<String, Boolean>();
        mBody = new TreeMap<String, String>();
        mHistory = new LinkedList<TreeMap<String, String[]>>();
        initValidity();
    }

    /**
     * Initialise la liste des langues
     */
    private void getLanguages()
    {
        // Récupération de la liste des langues
        String param = Messages.getString( "mail.squalix.language" );
        mLanguages = param.split( "," );
    }

    /**
     * Initialise le tableau de validité par langue
     */
    private void initValidity()
    {
        // boucle sur les différents langues
        for ( int i = 0; i < mLanguages.length; i++ )
        {
            mValidity.put( mLanguages[i], true );
        }
    }

    /**
     * Rajoute du contenu (séparé par langue) identifiée par la clé
     * @param pKey la clé identifiant le contenu à rajouter
     * @param pValues les éventuelles valeurs à suppléer dans le contenu
     */
    public void addContent( String pKey, String[] pValues )
    {
        TreeMap<String, String[]> historique = new TreeMap<String, String[]>();
        historique.put( pKey, pValues );
        mHistory.add( historique );
        for ( int i = 0; i < mLanguages.length; i++ )
        {
            // Si la clé existe, on va le rajouter sinon on indique l'invalidité pour le langage
            if ( Messages.existString( pKey + "." + mLanguages[i] ).booleanValue() )
            {
                // Récupération de la clé pour le langue
                addData( mLanguages[i], Messages.getMessage( pKey + "." + mLanguages[i], pValues ) );
            }
            else
            {
                mValidity.put( mLanguages[i], false );
            }
        }
    }

    /**
     * Rajoute des données dans le Body suivant les langues
     * 
     * @param pLanguage la langue
     * @param pData les données à rajouter
     */
    private void addData( String pLanguage, String pData )
    {
        if ( mBody.containsKey( pLanguage ) )
        {
            mBody.put( pLanguage, mBody.get( pLanguage ) + pData );
        }
        else
        {
            mBody.put( pLanguage, pData );
        }
    }

    /**
     * Construit le contenu du mail en fonction de la validité
     * 
     * @return le contenu du mail
     */
    public String getContent()
    {
        // S'il manque une valeur pour un langage, on construit le mail classique
        if ( mValidity.containsValue( false ) )
        {
            return buildMixedContent();
        }
        else
        {
            return buildSeparateContent();
        }
    }

    /**
     * Construit le contenu du mail avec un mélange FR/EN
     * @return le contenu du mail
     */
    private String buildMixedContent()
    {
        String result = "";
        Iterator<TreeMap<String, String[]>> it = mHistory.iterator();
        while ( it.hasNext() )
        {
            TreeMap<String, String[]> reference = it.next();
            result += Messages.getMessage( reference.firstKey(), reference.get( reference.firstKey() ) );
        }
        return result;
    }

    /**
     * Construit le contenu du mail par langue avec un séparateur
     * @return le contenu du mail formaté par langue
     */
    private String buildSeparateContent()
    {
        String result = "";
        String separator = Messages.getString( "mail.squalix.language.separator" );
        for ( int i = 0; i < mLanguages.length; i++ )
        {
            result += mBody.get( mLanguages[i] ).trim();
            if ( i != ( mLanguages.length - 1 ) )
            {
                result += separator;
            }
        }
        return result;
    }

}