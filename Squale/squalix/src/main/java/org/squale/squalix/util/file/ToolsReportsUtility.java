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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\org\\squale\\squalix\\tools\\mccabe\\McCabeUtility.java

package org.squale.squalix.util.file;

import java.util.Iterator;
import java.util.List;

import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;

/**
 * Classe contenant les méthodes outils utilisées par les différents outils générant des rapports.<br>
 * Fournit des méthodes permettant de manipuler les noms des méthodes, classes et packages.
 */
public class ToolsReportsUtility
{

    /**
     * Vérifie si le nom de la classe correspond aux patterns séparés par des point-virgules.
     * 
     * @param pClassname le nom de la classe.
     * @param pPattern les patterns sous forme de liste de StringParameterBO.
     * @return le résultat de la correspondance.
     */
    public static boolean isMatchingClass( final String pClassname, final List pPattern )
    {
        boolean result = false;
        if ( null != pClassname && null != pPattern )
        {
            // On parcours la liste des patterns
            Iterator it = pPattern.iterator();
            StringParameterBO pattern;
            while ( !result && it.hasNext() )
            {
                pattern = (StringParameterBO) it.next();
                // On vérifie pour chaque pattern que celui-ci ne correspond pas à la classe
                result = pClassname.matches( pattern.getValue() );
            }
        }
        return result;
    }

    /**
     * Nettoie le nom du composant si celui-ci a été mal remonté du rapport.<br>
     * Retire les guillemets et la virgule éventuelle.
     * 
     * @param pName le nom a nettoyer.
     * @return le nom propre.
     */
    public static String clearReportName( String pName )
    {
        String newName = pName;
        if ( newName.matches( "\".*\"," ) )
        {
            newName = newName.substring( 1, pName.length() - 2 );
        }
        return newName;
    }

    /**
     * @param pComponentName le nom du composant JSP
     * @return le nom du composant sans le "_jsp" que McCabe place en fin du nom
     */
    public static String clearJspName( String pComponentName )
    {
        String result = pComponentName;
        int id = pComponentName.lastIndexOf( "_jsp" );
        if ( id > 0 )
        {
            result = pComponentName.substring( 0, id );
        }
        return result;
    }
}
