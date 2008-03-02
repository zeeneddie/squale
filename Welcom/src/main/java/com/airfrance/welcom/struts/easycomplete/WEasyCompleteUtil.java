/*
 * Créé le 16 juin 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.easycomplete;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author M327836 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WEasyCompleteUtil
{
    /**
     * Filtre une chaine de caractère pour échapper tous les carractère de commande d'expressions régulières
     * 
     * @param ch la chaine à filtrer
     * @return Chaine échappé
     */
    public static String filter( String ch )
    {
        final Pattern regexp = Pattern.compile( "(\\p{Punct})" );
        final Matcher m = regexp.matcher( ch );
        final StringBuffer buf = new StringBuffer();
        int start = 0;
        int end = 0;

        while ( m.find() )
        {
            end = m.start();
            buf.append( ch.substring( start, end ) );
            buf.append( "\\" );
            buf.append( m.group() );
            start = m.end();
        }

        buf.append( ch.substring( start ) );
        ch = buf.toString();

        return ch;
    }
}