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
package com.airfrance.squalix.tools.umlquality;

import java.lang.reflect.Constructor;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;

/**
 * Classe contenant les méthodes outils utilisées par UMLQuality.<br>
 * Fournit des méthodes permettant de manipuler le noms, des composants, des rapports ...etc.
 * 
 * @author sportorico
 */
public class UMLQualityUtility
{
    /**
     * Instancie un composant
     * 
     * @param pClassName le nom de la classe
     * @param pConstructorParam le constructeur de la classe
     * @return le composant
     * @throws Exception si le composant ne paut pas être instancier
     */
    public static AbstractComponentBO newInstance( String pClassName, String pConstructorParam )
        throws Exception
    {
        Class cl = Class.forName( pClassName );
        Class[] classOfParameter = { String.class };
        Constructor cons = cl.getConstructor( classOfParameter );
        Object[] parameter = { pConstructorParam };
        Object ob = cons.newInstance( parameter );
        return (AbstractComponentBO) ob;
    }

    /**
     * Retourne le nom des composants uml(classe, transition, ..etc)<br>
     * grâce au nom des composants metrics calculé par UMLQuality
     * 
     * @param pUmlMetricClassName le nom de la classe
     * @return le nom du composant uml
     */
    public static String getUmlComponentName( String pUmlMetricClassName )
    {
        StringBuffer str = new StringBuffer( getName( pUmlMetricClassName ) );
        int start = UMLQualityMessages.getString( "uml.metric.prefix.name" ).length();
        int end = UMLQualityMessages.getString( "uml.metric.sufix.name" ).length();
        return str.substring( start, str.length() - end );
    }

    /**
     * Retourne le nom propre à un composant grâce à son nom Absolu(complet)
     * 
     * @param pFullName le nom complet
     * @return le nom
     */
    public static String getName( String pFullName )
    {
        String res = null;
        StringBuffer str = new StringBuffer( pFullName );
        int index = str.lastIndexOf( "." );

        if ( index > 0 )
        {
            res = str.substring( index + 1 );
        }
        else
        {
            res = pFullName;
        }
        return res.equals( "" ) ? null : res;
    }

    /**
     * Retourne le nom(absolu) du parent d'un composant
     * 
     * @param pFullName le nom compplet
     * @return le nom du parent
     */
    public static String getParentName( String pFullName )
    {
        StringBuffer str = new StringBuffer( pFullName );
        int index = str.lastIndexOf( "." );

        return ( index > 0 ) ? str.substring( 0, index ) : null;
    }

    /**
     * Détermine le type de rapport( générer par UMLQuality pour un composant uml donné).
     * 
     * @param pReportEndName suffixe des nom de rapport
     * @return le type de rapport
     */
    public static String typeReport( String pReportEndName )
    {
        StringBuffer str = new StringBuffer( pReportEndName );
        int index = str.lastIndexOf( "." );

        return ( str.substring( 1, index ) ).toLowerCase();
    }

}
