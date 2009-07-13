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
 * Créé le 15 avr. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.struts.lazyLoading;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.GenericValidator;
import org.squale.welcom.outils.Util;
import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.struts.lazyLoading.font.WFontSimulator;


/**
 * @author M327836 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WLazyUtil
{

    /**
     * recherche les parametres dans un tag
     * 
     * @param s chaine contenant le script du tag
     * @return une hashtable contenant les paramètres et leur valeur.
     */
    private static Hashtable searchParameter( final String s )
    {
        final Hashtable parameters = new Hashtable();

        final Pattern pattern =
            Pattern.compile( "(name|type|value|checked)\\s*=\\s*([\"\'])", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );
        final Matcher matcher = pattern.matcher( s );
        int pos = 0;

        while ( matcher.find( pos ) )
        {
            final String name = matcher.group( 1 );
            char charDelimiter = ' ';
            if ( !GenericValidator.isBlankOrNull( matcher.group( 2 ) ) )
            {
                charDelimiter = matcher.group( 2 ).charAt( 0 );
            }

            final int posStart = matcher.end( 2 );
            final int posEnd = s.indexOf( charDelimiter, posStart );

            final String value = s.substring( posStart, posEnd );

            parameters.put( name.toLowerCase(), charDelimiter + value + charDelimiter );

            pos = matcher.end( 2 );
        }

        return parameters;
    }

    /**
     * @param lazyTag le tag est en lazy
     * @return si le lazy loadin doit être appliqué
     */
    public static boolean isLazy( final boolean lazyTag )
    {
        final boolean lazyGlobal =
            new Boolean( WelcomConfigurator.getMessage( WelcomConfigurator.LAZYLOADING_KEY ) ).booleanValue();

        return lazyTag && lazyGlobal;
    }

    /**
     * converti un select en champ input classique
     * 
     * @param s chaine contenant le flux à modifier
     * @return le flux mis à jour
     */
    public static String convertSelectToLightInput( final String s )
    {
        final StringBuffer smallInput = new StringBuffer();
        smallInput.append( "<input " );

        // try {
        // Search name
        // RE reSelect=new RE("<\\s*select([^>]*)>",RE.MATCH_CASEINDEPENDENT);
        final Pattern reSelect = Pattern.compile( "<\\s*select([^>]*)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );

        // RE reOptionSelected = new RE ("<\\s*option[^>]+selected[^>]*>",RE.MATCH_CASEINDEPENDENT);
        final Pattern reOptionSelected =
            Pattern.compile( "<\\s*option[^>]+selected[^>]*>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );

        // RE reOption = new RE ("<\\s*option[^>]*>",RE.MATCH_CASEINDEPENDENT);
        final Pattern reOption = Pattern.compile( "<\\s*option[^>]*>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );

        final Matcher maSelect = reSelect.matcher( s );

        if ( maSelect.find() )
        {
            Hashtable parameters = searchParameter( s );

            if ( parameters.containsKey( "name" ) )
            {
                smallInput.append( "name=" );
                smallInput.append( parameters.get( "name" ) );
                smallInput.append( " " );
            }
            else
            {
                return s;
            }

            final Matcher maOptionSelected = reOptionSelected.matcher( s );

            if ( maOptionSelected.find() )
            {
                parameters = searchParameter( maOptionSelected.group( 0 ) );

                if ( parameters.containsKey( "value" ) )
                {
                    smallInput.append( "value=" );
                    smallInput.append( parameters.get( "value" ) );
                }
                else
                {
                    final int posStart = maOptionSelected.end( 0 );
                    final Pattern reOptionEnd =
                        Pattern.compile( "([^<]*)<\\s*\\/option\\s*>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );
                    final Matcher maOptionEnd = reOptionEnd.matcher( s );

                    // RE reOptionEnd= new RE ("([^<]*)<\\s*\\/option\\s*>");
                    if ( maOptionEnd.find( posStart ) )
                    {
                        final String value = maOptionEnd.group( 1 );
                        smallInput.append( "value=" );
                        smallInput.append( parameters.get( "value" ) );
                    }
                    else
                    {
                        return s;
                    }
                }
            }
            else
            {
                final Matcher maOption = reOption.matcher( s );

                if ( maOption.find() )
                {
                    parameters = searchParameter( maOption.group( 0 ) );

                    if ( parameters.containsKey( "value" ) )
                    {
                        smallInput.append( "value=" );
                        smallInput.append( parameters.get( "value" ) );
                    }
                    else
                    {
                        final int posStart = maOption.end( 0 );
                        final Pattern reOptionEnd =
                            Pattern.compile( "([^<]*)<\\s*\\/option\\s*>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );
                        final Matcher maOptionEnd = reOptionEnd.matcher( s );

                        // RE reOptionEnd= new RE ("([^<]*)<\\s*\\/option\\s*>");
                        if ( maOptionEnd.find( posStart ) )
                        {
                            final String value = maOptionEnd.group( 1 );
                            smallInput.append( "value=" );
                            smallInput.append( parameters.get( "value" ) );
                        }
                        else
                        {
                            return s;
                        }
                    }
                }
                else
                {
                    return s;
                }
            }
        }

        smallInput.append( ">" );

        return smallInput.toString();
    }

    /**
     * convertie un champs de type input text en champs input classique
     * 
     * @param s chaine contenant le flux à modifier
     * @return le flux mis à jour
     */
    public static String convertInputToLightInput( final String s )
    {
        final StringBuffer smallInput = new StringBuffer();

        final Hashtable parameters = searchParameter( s );
        final Enumeration enumeration = parameters.keys();

        smallInput.append( "<input " );

        while ( enumeration.hasMoreElements() )
        {
            final String key = (String) enumeration.nextElement();

            smallInput.append( key );
            smallInput.append( "=" );
            smallInput.append( parameters.get( key ) );
            smallInput.append( " " );
        }

        smallInput.append( ">" );

        return smallInput.toString();
    }

    /**
     * épure le corps d'une page
     * 
     * @param corps chaine contenant le flux à modifier
     * @return le flux modifié
     */
    public static String getLightBody( final String corps )
    {
        final StringBuffer buf = new StringBuffer();
        // try {
        // Cherche les inputs
        Pattern reg = Pattern.compile( "(<\\s*input[^>]*>)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );
        Matcher mat = reg.matcher( corps );

        // RE reg=new RE("(<\\s*input[^>]*>)",RE.MATCH_CASEINDEPENDENT);
        int pos = 0;

        while ( mat.find( pos ) )
        {
            buf.append( convertInputToLightInput( mat.group( 0 ) ) );
            pos = mat.end( 0 );
        }

        // Cherche les textarea
        reg =
            Pattern.compile( "(<\\s*textarea[^>]*>[^<]*<\\/\\s*textarea\\s*>)", Pattern.CASE_INSENSITIVE
                | Pattern.MULTILINE );
        mat = reg.matcher( corps );

        // reg=new RE("(<\\s*textarea[^>]*>[^<]*<\\/\\s*textarea\\s*>)",RE.MATCH_CASEINDEPENDENT);
        pos = 0;

        while ( mat.find( pos ) )
        {
            buf.append( mat.group( 0 ) );
            pos = mat.end( 0 );
        }

        // cherche les selects
        // RE regstart=new RE("(<select)",RE.MATCH_CASEINDEPENDENT);
        final Pattern regstart = Pattern.compile( "(<\\s*select)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );

        // RE regstop=new RE("(</select>)",RE.MATCH_CASEINDEPENDENT);
        final Pattern regstop = Pattern.compile( "(<\\s*/select>\\s*)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE );

        final Matcher mastart = regstart.matcher( corps );
        final Matcher mastop = regstop.matcher( corps );

        pos = 0;

        while ( mastart.find( pos ) )
        {
            final int posStart = mastart.start( 1 );
            int posStop = mastart.end( 1 );

            if ( mastop.find( posStart ) )
            {
                posStop = mastop.end( 1 );

                final String value = corps.substring( posStart, posStop );
                buf.append( convertSelectToLightInput( value ) );
            }

            pos = posStop;
        }
        return buf.toString();
    }

    /**
     * épure le corps d'une page
     * 
     * @param corps chaine contenant le flux à modifier
     * @return le flux modifié
     */
    public static String getSuperLightBody( final String corps )
    {
        final StringBuffer buf = new StringBuffer();
        // Cherche les chechbox
        final Pattern reg = Pattern.compile( "(<\\s*input[^>]*>)", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE );
        final Matcher mat = reg.matcher( corps );

        // RE reg=new RE("(<\\s*input[^>]*>)",RE.MATCH_CASEINDEPENDENT);
        int pos = 0;

        while ( mat.find( pos ) )
        {
            final Hashtable parameters = searchParameter( mat.group( 0 ) );

            if ( Util.isEqualsIgnoreCase( Util.removeQuotes( (String) parameters.get( "type" ) ), "checkbox" )
                && parameters.containsKey( "checked" ) )
            {

                buf.append( convertInputToLightInput( mat.group( 0 ) ) );
            }

            pos = mat.end( 0 );
        }

        return buf.toString();
    }

    /**
     * épure le corps d'un combo
     * 
     * @param s chaine contenant le flux à modifier
     * @return le flux modifié
     */
    public static String getLightCombo( final String s )
    {
        final StringBuffer options = new StringBuffer();

        // Search name
        final Pattern reOptionSelected =
            Pattern.compile( "<\\s*option[^>]+selected[^>]*>[^<]*<\\/\\s*option\\s*>", Pattern.CASE_INSENSITIVE
                | Pattern.MULTILINE );
        final Pattern reOption =
            Pattern.compile( "<\\s*option[^>]*>([^<]*)<\\/\\s*option\\s*>", Pattern.CASE_INSENSITIVE
                | Pattern.MULTILINE );
        final Matcher maOptionSelected = reOptionSelected.matcher( s );
        final Matcher maOption = reOption.matcher( s );

        // RE reOptionSelected = new RE
        // ("<\\s*option[^>]+selected[^>]*>[^<]*<\\/\\s*option\\s*>",RE.MATCH_CASEINDEPENDENT);
        // RE reOption = new RE ("<\\s*option[^>]*>([^<]*)<\\/\\s*option\\s*>",RE.MATCH_CASEINDEPENDENT);
        if ( maOptionSelected.find() )
        {
            options.append( maOptionSelected.group( 0 ) );
        }
        else
        {
            if ( maOption.find() )
            {
                options.append( maOption.group( 0 ) );
            }
            else
            {
                return s;
            }
        }

        int pos = 0;
        String longer = "";

        while ( maOption.find( pos ) )
        {
            if ( WFontSimulator.getSize( maOption.group( 1 ) ) > WFontSimulator.getSize( longer ) )
            {
                longer = maOption.group( 1 );
            }

            pos = maOption.end( 0 );
        }

        if ( options.toString().indexOf( longer ) < 0 )
        {
            options.append( "<option>" + longer + "</option>" );
        }
        return options.toString();
    }
}