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
package org.squale.squalix.util.parser;

import org.squale.squalecommon.enterpriselayer.businessobject.component.JspBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalix.tools.compiling.jsp.configuration.JspCompilingConfiguration;

/**
 * Parse les noms de type j2ee et les remplace par les objets correspondants.<br/>
 */
public class J2EEParser
    extends JavaParser
{

    /** Nom du package racine contenant les JSPs */
    public static final String ROOT_PACKAGE = "{jsp}";

    /**
     * Constructeur
     * 
     * @param pProject le projet � auditer
     */
    public J2EEParser( ProjectBO pProject )
    {
        super( pProject );
    }

    /**
     * Cr�e le composant JSP d�sign� par les param�tres.
     * 
     * @param pJspName le nom de la classe JSP
     * @param pFileName le nom absolu du fichier � partir du projet
     * @param pDirectoryPath le nom absolu du r�pertoire racine contenant les pages JSP
     * @param pId l'index du r�pertoire source des JSPs dans les param�tres du projet
     * @return le JspBO d�sign� par les param�tres.
     */
    public JspBO getJsp( String pJspName, String pFileName, String pDirectoryPath, int pId )
    {
        JspBO jsp = new JspBO( pJspName );
        jsp.setFileName( pFileName );
        String packageName = convertIntoPackageName( pFileName, pDirectoryPath );
        // On va d�composer le nom du fichier pour cr�er une hierarchie de package
        // dont la racine est un package par d�faut pour les JSP + le r�pertoire indiquant
        // l'index du r�pertoire source
        String absolutePackageName = ROOT_PACKAGE + "." + JspCompilingConfiguration.FIRST_PACKAGE;
        if ( pId > 0 )
        {
            absolutePackageName += pId;
        }
        if ( packageName.length() > 0 )
        {
            absolutePackageName += "." + packageName;
        }
        PackageBO packageBO = getPackage( absolutePackageName );
        // on ne v�rifie pas que packageBO est nul puisqu'il aura toujours le package par d�faut
        jsp.setParent( packageBO );
        return jsp;
    }

    /**
     * @param pFileName le nom du fichier � transformer
     * @param pDirectoryPath le nom absolu du r�pertoire racine � partir duquel le nom du package doit �tre construit
     * @return le nom du fichier transform� en nom de package
     */
    private String convertIntoPackageName( String pFileName, String pDirectoryPath )
    {
        String packageName = "";
        // On coupe le nom du fichier
        packageName = pFileName.replaceFirst( "[/\\\\]+[^/\\\\]+.jsp$", "" );
        // On coupe le chemin du fichier jusqu'au r�pertoire racine
        packageName = packageName.replaceFirst( pDirectoryPath + "[/\\\\]*", "" );
        // On remplace les "/" par des "."
        packageName = packageName.replaceAll( "/", "." );
        return packageName;
    }

}
