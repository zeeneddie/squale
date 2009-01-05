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
package com.airfrance.squaleweb.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.airfrance.squalecommon.util.ConstantRulesChecking;
import com.airfrance.squaleweb.resources.WebMessages;

/**
 * Utilitaire pour l'export PDF avec jasperReport
 */
public class JasperReportUtil
{

    /** Chemin racine de l'application */
    private static final String ROOT = "/squale";

    /**
     * @param pLocale la locale
     * @param pTreNames les noms des métriques
     * @param pValues les valeurs des métriques
     * @return les métriques avec les valeurs associées sous forme de chaîne pour l'afficher dans le PDF
     */
    public static String getMetrics( Locale pLocale, List pTreNames, List pValues )
    {
        List valuesStr = SqualeWebActionUtils.getAsStringsList( pValues );
        // La note de la pratique
        String result = WebMessages.getString( pLocale, "export.pdf.mark" );
        String mark = SqualeWebActionUtils.formatFloat( (String) valuesStr.get( 0 ) );
        if ( mark == null )
        {
            mark = "";
        }
        result += " : " + mark + "\n";
        // Les métriques
        int i = 1;
        for ( Iterator it = pTreNames.iterator(); it.hasNext(); i++ )
        {
            result += WebMessages.getString( pLocale, (String) it.next() ) + "\t:";
            String curValue = (String) valuesStr.get( i );
            // curValue peut être nul
            if ( null == curValue )
            {
                curValue = "-";
            }
            if ( curValue.indexOf( "\n" ) > 0 )
            { // On passe le bloc à la ligne
                result += "\n";
            }
            else
            {
                result += "\t";
            }
            result += curValue + "\n";
        }
        return result;
    }

    /**
     * Récupère le pictogramme associé à la sévérité de la règle
     * 
     * @param pSeverity la sévérité.
     * @return le chemin de l'image
     */
    public static String generateRulePicto( String pSeverity )
    {
        String picto = "";
        // Images en fonction de la sévérité (error, warning, info)
        final HashMap severities = new HashMap();
        severities.put( ConstantRulesChecking.ERROR_LABEL, "/images/pictos/error.png" );
        severities.put( ConstantRulesChecking.WARNING_LABEL, "/images/pictos/warning.png" );
        severities.put( ConstantRulesChecking.INFO_LABEL, "/images/pictos/info.png" );
        // Vérification simpliste de la sévérité pour éviter des exceptions
        // lors de sa conversion
        if ( pSeverity != null )
        {
            picto = (String) severities.get( pSeverity );
            if ( null == picto )
            {
                picto = "/images/pictos/na.gif";
            }
        }
        return picto;
    }

    /**
     * @param pNote la note
     * @return le chemin (adapté aux rapports JASPER) vers le pictogramme associé à la note
     */
    public static String generatePicto( String pNote )
    {
        return SqualeWebActionUtils.IMG[SqualeWebActionUtils.generatePicto( pNote )].replaceAll( ROOT, "" );
    }

    /**
     * @param currentMark la note courante
     * @param predecessorMark la note précédente
     * @return l'image représentant l'évolution entre currentMark et predecessorMark
     */
    public static String getImageForTrend( String currentMark, String predecessorMark )
    {
        return SqualeWebActionUtils.getImageForTrend( currentMark, predecessorMark ).replaceAll( ROOT, "" );
    }

    /**
     * @param pText le texte à formater
     * @return le texte sans les balises html
     */
    public static String formatHTML( String pText )
    {
        String result = "";
        if ( null != pText )
        {
            // on remplace les saut de ligne
            result = pText.replaceAll( "<br/?>", "\n" );
            // les puces
            result = result.replaceAll( "<li>", "\n- " );
            result = result.replaceAll( "</li>", "\n" );
            // On supprime les autres balises
            result = result.replaceAll( "<.+>", "" );
        }
        return result;
    }
}
