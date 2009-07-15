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

import java.util.TreeSet;

import org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComplexComponentBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;

/**
 * Parse les noms de type C++ et les remplace par les objets correspondants.
 */
public class CppParser
    implements LanguageParser
{
    /** Nom du package par défaut */
    private static final String DEFAULT_PACKAGE_NAME = "{C}";

    /** Nom de la classe par défaut */
    private static final String DEFAULT_CLASS_NAME = "{C}";

    /**
     * Pattern pour repérer une zone d'initialisation statique
     */
    private static final String STATIC_INITIALIZER_PATTERN = "^.*#StaticInitializer#[0-9]*$";

    /** Projet sur lequel est réalisé l'analyse */
    private ProjectBO mProject;

    /**
     * Ensemble des classes connues Celà permet de renseigner les noms de classe connues pour permettre une distinction
     * entre un namespace et un nom de classe. McCabe utilise l'opérateur de qualification :: aussi bien pour une classe
     * imbriquée que pour un namespace
     */
    private TreeSet mKnownClasses = new TreeSet();

    /**
     * Constructeur
     * 
     * @param pProject le projet sur lequel est réalisé l'analyse
     */
    public CppParser( ProjectBO pProject )
    {
        mProject = pProject;
    }

    /**
     * Ajout d'une classe connue
     * 
     * @param pClassName nom de la classe
     */
    public void addKnownClass( String pClassName )
    {
        StringBuffer context = new StringBuffer();
        String className = geRelativeElementName( pClassName, context );
        mKnownClasses.add( className );
    }

    /* ################ Décomposition et transformation en objet correspondant ################ */

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.squalix.util.parser.LanguageParser#getMethod(java.lang.String, java.lang.String)
     */
    public MethodBO getMethod( String pAbsoluteMethodName, String pFileName )
    {
        // On récupère le nom de la méthode
        MethodBO methodBO;
        try
        {
            String methodName = getMethodName( pAbsoluteMethodName );
            methodBO = new MethodBO( methodName );
            String nameWithoutParameters;
            // cas des méthodes 'main' C++ inscrite sans signature par McCabe
            if ( methodName.equals( "main" ) || methodName.matches( "main_#.*" ) )
            {
                nameWithoutParameters = methodName;
            }
            else
            {
                nameWithoutParameters = pAbsoluteMethodName.substring( 0, pAbsoluteMethodName.lastIndexOf( "(" ) );
            }
            int classIndex = nameWithoutParameters.lastIndexOf( "::" );
            String absoluteClassName;
            // On rattache les fonctions à une classe fictive
            if ( classIndex == -1 )
            {
                absoluteClassName = DEFAULT_CLASS_NAME;
            }
            else
            {
                absoluteClassName = nameWithoutParameters.substring( 0, nameWithoutParameters.lastIndexOf( "::" ) );
            }
            ClassBO parent = getClass( absoluteClassName );
            methodBO.setParent( parent );
            methodBO.setLongFileName( pFileName );
        }
        catch ( StringIndexOutOfBoundsException e )
        {
            // cas d'un nom de méthode mal formé - bug McCabe sur l'opérateur C++ ()
            methodBO = null;
        }
        return methodBO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.squalix.util.parser.LanguageParser#getClass(java.lang.String)
     */
    public ClassBO getClass( String pAbsoluteClassName )
    {
        // On récupère le nom de la classe
        StringBuffer context = new StringBuffer();
        String className = geRelativeElementName( pAbsoluteClassName, context );
        ClassBO classBO = new ClassBO( className );
        AbstractComplexComponentBO current = classBO;
        // Le contexte est soit un nom de classe, soit un nom de package
        // On le décompose entièrement
        while ( context.length() > 0 )
        {
            AbstractComplexComponentBO parent = null;
            String parentName = geRelativeElementName( context.toString(), context );
            // On doit maintenant deviner si le parentName est une classe ou un namespace
            if ( isClassName( parentName ) )
            {
                parent = new ClassBO( parentName );
            }
            else
            {
                parent = new PackageBO( parentName );
            }
            current.setParent( parent );
            current = parent;
        }
        AbstractComplexComponentBO parent = null;
        // On récupère le parent associé
        if ( current instanceof ClassBO )
        {
            parent = getPackage( DEFAULT_PACKAGE_NAME );
        }
        else
        {
            parent = mProject;
        }
        current.setParent( parent );
        return classBO;
    }

    /**
     * @param parentName nom de classe
     * @return true si le nom est considéré comme étant une classe
     */
    private boolean isClassName( String parentName )
    {
        // Un nom est suppsé être une classe s'il s'agit d'un template
        // ou s'il s'agit d'un nom de classe déjà enregistré
        return parentName.indexOf( '>' ) > 0 || mKnownClasses.contains( parentName );
    }

    /**
     * Crée le package désigné par les paramètres.
     * 
     * @param pPackageName le nom absolu du package
     * @return le PackageBO désigné par les paramètres.
     */
    private PackageBO getPackage( String pPackageName )
    {
        PackageBO packageBO = new PackageBO( getPackageName( pPackageName ) );
        String parentName = getParentName( pPackageName );
        if ( null == parentName )
        {
            packageBO.setParent( mProject );
        }
        else
        {
            PackageBO parent = getPackage( parentName );
            packageBO.setParent( parent );
        }
        return packageBO;
    }

    /**
     * Retourne la chaîne pAbsoluteName avant le dernier "::" ou null si il n'y a pas de "::"
     * 
     * @param pAbsoluteName le nom absolu du fils
     * @return le nom absolu du parent
     */
    public String getParentName( String pAbsoluteName )
    {
        String parent = null;
        int lastDot = pAbsoluteName.lastIndexOf( "::" );
        if ( lastDot != -1 )
        {
            parent = pAbsoluteName.substring( 0, lastDot );
        }
        return parent;
    }

    /**
     * Retourne le nom du package le plus à droite
     * 
     * @param pPackageName le nom absolu du package
     * @return le nom du dernier package
     */
    private String getPackageName( String pPackageName )
    {
        String[] splittingPackageName = pPackageName.split( "\\." );
        return splittingPackageName[splittingPackageName.length - 1];
    }

    /**
     * Décompose le nom entièrement qualifié d'une méthode afin de récupérer son nom relatif.
     * 
     * @param pAbsoluteMethodName le nom entièrement qualifié d'une méthode
     * @return le nom relatif de la méthode
     */
    protected String getMethodName( String pAbsoluteMethodName )
    {
        String relativeMethodName = "";
        if ( pAbsoluteMethodName.matches( STATIC_INITIALIZER_PATTERN ) )
        {
            // S'il s'agit d'une zone d'initialisation statique,
            // on la considère comme une méthode
            relativeMethodName = getStaticInitializerName( pAbsoluteMethodName );
        }
        else if ( pAbsoluteMethodName.equals( "main" ) || pAbsoluteMethodName.matches( "main_#.*" ) )
        {
            // cas des méthodes 'main' C++ inscrite sans signature par McCabe
            relativeMethodName = pAbsoluteMethodName;
        }
        else
        {
            StringBuffer context = new StringBuffer();
            relativeMethodName =
                geRelativeElementName( pAbsoluteMethodName.substring( 0, pAbsoluteMethodName.lastIndexOf( "(" ) ),
                                       context );
            relativeMethodName += pAbsoluteMethodName.substring( pAbsoluteMethodName.lastIndexOf( "(" ) );
        }
        return relativeMethodName;
    }

    /**
     * Décompose le nom entièrement qualifié d'une classe afin de récupérer son nom relatif.
     * 
     * @param pAbsoluteName le nom entièrement qualifié de la méthode
     * @param pContext contexte de définition de la classe (soit une classe englobante soit un namespace) (ou le nom
     *            entièrement qualifié de la méthode sans les paramètres)
     * @return le nom relatif de la classe (ou de la méthode sans ses paramètres)
     */
    protected String geRelativeElementName( String pAbsoluteName, StringBuffer pContext )
    {
        // normal pattern = .+ car il faut aussi prendre en compte les destructeurs
        // et la redéfinition des opérateurs
        String normalPattern = ".+$";
        String specialPattern = ".*<.*>.*$";
        String name = "";
        pContext.setLength( 0 );
        // On commence par tester sur le pattern des templates
        // car le pattern de class simple englobe tout
        // Cas d'une classe basée sur des templates
        if ( pAbsoluteName.matches( specialPattern ) )
        {
            // Cas complexe utilisant des templates
            int dbpIndex = pAbsoluteName.lastIndexOf( "::" );
            int gtIndex = pAbsoluteName.lastIndexOf( ">" );
            if ( dbpIndex > gtIndex )
            {
                // Si le dernier :: est après le dernier >
                // On récupère la chaîne après les ::
                name = pAbsoluteName.substring( dbpIndex + 2 );
                pContext.append( pAbsoluteName.substring( 0, dbpIndex ) );
            }
            else
            {
                // Sinon traitement spécial
                name = getSpecialCppName( pAbsoluteName, pContext );
            }
        }
        else
        {
            // Cas simple d'une classe qui n'utilise pas de template
            if ( pAbsoluteName.matches( normalPattern ) )
            {
                int index = pAbsoluteName.lastIndexOf( ":" );
                name = pAbsoluteName.substring( index + 1 );
                if ( index > 0 )
                {
                    pContext.append( pAbsoluteName.substring( 0, index - 1 ) );
                }
            }
            else
            { // Ne devrait jamais arriver
                name = DEFAULT_CLASS_NAME;
            }
        }
        // Enfin, on nettoie le nom complet
        return clearName( name );
    }

    /**
     * Traite les nom spéciaux, c'est-à-dire contenant des déclarations ou utilisation de templates.
     * 
     * @param pAbsoluteName le nom à traiter.
     * @param pContext contexte de définition de la classe (soit une classe englobante soit un namespace)
     * @return le nom simplement qualifié.
     */
    private String getSpecialCppName( String pAbsoluteName, StringBuffer pContext )
    {
        int count = 0;
        String className = pAbsoluteName;
        for ( int i = 0; i < pAbsoluteName.length(); i++ )
        {
            // On analyse chacun des caractères
            if ( count == 0 && pAbsoluteName.charAt( i ) == ':' && pAbsoluteName.charAt( ++i ) == ':' )
            {
                // Si on trouve :: en dehors d'un template, on récupère ce qu'il y a après les ::
                className = pAbsoluteName.substring( i + 1 );
                pContext.append( pAbsoluteName.substring( 0, i - 1 ) );
            }
            else if ( pAbsoluteName.charAt( i ) == '<' )
            {
                // On entre dans un template, donc on attend la fin du template
                count++;
            }
            else if ( pAbsoluteName.charAt( i ) == '>' )
            {
                count--;
            }
        }
        return className;
    }

    /**
     * Récupère le nom de la méthode s'il s'agit d'une zone d'initialisation.
     * 
     * @param pAbsoluteName le nom à traiter.
     * @return le nom simplement qualifié.
     */
    private String getStaticInitializerName( String pAbsoluteName )
    {
        int index = pAbsoluteName.lastIndexOf( "." );
        String result = pAbsoluteName.substring( ++index );
        return result;
    }

    /**
     * Enlève les caractères spéciaux à la fin d'un nom de classe, package, méthode.<br>
     * Les caractères spéciaux sont les séparateurs Java et C++ :
     * <ul>
     * <li>.</li>
     * <li>:</li>
     * <li>$</li>
     * </ul>
     * Ainsi que les caractères de chemins :
     * <ul>
     * <li>/</li>
     * <li>\</li>
     * </ul>
     * 
     * @param pName le nom à nettoyer
     * @return le nom sans caractère spécial en fin.
     */
    public static String clearName( String pName )
    {
        String newName = pName;
        String badEnd = ".*[/.:\\$#]$";
        while ( newName.matches( badEnd ) )
        {
            newName = newName.substring( 0, pName.length() - 1 );
        }
        return newName;
    }
}
