package com.airfrance.squalix.util.parser;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.JspBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalix.tools.compiling.jsp.configuration.JspCompilingConfiguration;

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
     * @param pProject le projet à auditer
     */
    public J2EEParser( ProjectBO pProject )
    {
        super( pProject );
    }

    /**
     * Crée le composant JSP désigné par les paramètres.
     * 
     * @param pJspName le nom de la classe JSP
     * @param pFileName le nom absolu du fichier à partir du projet
     * @param pDirectoryPath le nom absolu du répertoire racine contenant les pages JSP
     * @param pId l'index du répertoire source des JSPs dans les paramètres du projet
     * @return le JspBO désigné par les paramètres.
     */
    public JspBO getJsp( String pJspName, String pFileName, String pDirectoryPath, int pId )
    {
        JspBO jsp = new JspBO( pJspName );
        jsp.setFileName( pFileName );
        String packageName = convertIntoPackageName( pFileName, pDirectoryPath );
        // On va décomposer le nom du fichier pour créer une hierarchie de package
        // dont la racine est un package par défaut pour les JSP + le répertoire indiquant
        // l'index du répertoire source
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
        // on ne vérifie pas que packageBO est nul puisqu'il aura toujours le package par défaut
        jsp.setParent( packageBO );
        return jsp;
    }

    /**
     * @param pFileName le nom du fichier à transformer
     * @param pDirectoryPath le nom absolu du répertoire racine à partir duquel le nom du package doit être construit
     * @return le nom du fichier transformé en nom de package
     */
    private String convertIntoPackageName( String pFileName, String pDirectoryPath )
    {
        String packageName = "";
        // On coupe le nom du fichier
        packageName = pFileName.replaceFirst( "[/\\\\]+[^/\\\\]+.jsp$", "" );
        // On coupe le chemin du fichier jusqu'au répertoire racine
        packageName = packageName.replaceFirst( pDirectoryPath + "[/\\\\]*", "" );
        // On remplace les "/" par des "."
        packageName = packageName.replaceAll( "/", "." );
        return packageName;
    }

}
