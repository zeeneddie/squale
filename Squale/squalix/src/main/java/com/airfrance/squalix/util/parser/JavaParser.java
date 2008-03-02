package com.airfrance.squalix.util.parser;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComplexComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;

/**
 * Parse les noms de type java et les remplace par les objets correspondants.<br/> Cette classe est implémentée de
 * façon à fonctionner avec les nom spéciaux remontés par McCabe.<br/> On ne traite pas les cas McCabe directement dans
 * les classes McCabes car cela dépend du langage.
 */
public class JavaParser
    implements LanguageParser
{

    /** Début du nom donné par McCabe aux classes anonymes */
    public static final String MC_CABE_ANONYMOUS_CLASS_NAME = "__Anon_";

    /** Début du nom a donner aux classes anonymes */
    public static final String ANONYMOUS_CLASS_NAME = "AnonymousClass";

    /** Début de nom donné par RSM au premier package d'une Interface */
    public static final String RSM_INTERFACE_PACKAGE_NAME = "Interface-";

    /** Projet sur lequel est réalisé l'analyse */
    private ProjectBO mProject;

    /**
     * Ensemble des classes connues Celà permet de renseigner les noms de classe connues pour permettre une distinction
     * entre un package et un nom de classe. McCabe utilise l'opérateur de qualification "." aussi bien pour une classe
     * imbriquée que pour un package.
     */
    private TreeSet mKnownClasses = new TreeSet();

    /**
     * Ensemble des classes anonymes remontées par McCabe.<br/> On utilise une LinkedHashSet pour ne pas avoir de
     * doublons et pour l'ordre dans lequel sont insérés les noms soit conservé.<br/> Ce set va nous permettre de
     * numéroter les classes anonymes remontées par McCabe car on va sauvegarder ces noms de classes lorsqu'une méthode
     * définie dans une classe anonyme sera parsée.<br/> Le rpt de McCabe doit ordonner les méthodes par ordre
     * croissant de leur numéro de ligne (on suppose ainsi que le compilateur java défini l'identifiant d'une classe
     * anonyme par le même principe).
     */
    private LinkedHashSet mAnonymousClasses = new LinkedHashSet();

    /**
     * Constructeur
     * 
     * @param pProject le projet sur lequel est réalisé l'analyse
     */
    public JavaParser( ProjectBO pProject )
    {
        mProject = pProject;
    }

    /**
     * Ajout d'une classe connue
     * 
     * @param pClassName nom absolu de la classe
     */
    public void addKnownClass( String pClassName )
    {
        mKnownClasses.add( pClassName );
    }

    /* ################ Décomposition et transformation en objet correspondant ################ */

    /**
     * {@inheritDoc}
     * 
     * @param pAbsoluteMethodName {@inheritDoc}
     * @param pFileName {@inheritDoc}
     * @see com.airfrance.squalix.util.parser.LanguageParser#getMethod(java.lang.String, java.lang.String)
     */
    public MethodBO getMethod( String pAbsoluteMethodName, String pFileName )
    {
        MethodBO methodBO = null;
        // On vérifie que le nom de la méthode ne fait pas partie des bugs McCabe,
        // sinon on retourne null
        if ( !isMcCabeBug( pAbsoluteMethodName ) )
        {
            // Si la méthode est contenue dans classe anonyme remontée par McCabe, on l'enregistre
            // pour pouvoir déterminer l'identifiant de cette classe anonyme.
            if ( pAbsoluteMethodName.matches( "^.+\\$" + MC_CABE_ANONYMOUS_CLASS_NAME + "[0-9]+_.*" ) )
            {
                mAnonymousClasses.add( pAbsoluteMethodName );
            }
            // On nettoie le nom si celui-ci est remonté du rapport McCabe car
            // il peut peut ne pas convenir pour le parsing.
            String cleanMethodName = clearReportName( pAbsoluteMethodName );
            // On récupère le nom de la méthode
            String methodName = getName( cleanMethodName );
            methodBO = new MethodBO( methodName );
            methodBO.setLongFileName( pFileName );
            String absoluteClassName = getAbsoluteClassName( pAbsoluteMethodName );
            ClassBO parent = getClass( absoluteClassName );
            // On modifie le fichier de la classe car c'est le même que celui de la méthode
            parent.setFileName( pFileName );
            methodBO.setParent( parent );
        }
        return methodBO;
    }

    /**
     * @param classNameWithPackage le nom absolu de la classe
     * @param relativeFileName le nom du fichier relatif au projet
     * @return la classe persistée
     */
    public ClassBO getClass( String classNameWithPackage, String relativeFileName )
    {
        ClassBO classBO = getClass( classNameWithPackage );
        classBO.setFileName( relativeFileName );
        return classBO;
    }

    /**
     * {@inheritDoc}
     * 
     * @param pAbsoluteClassName {@inheritDoc}
     * @see com.airfrance.squalix.util.parser.LanguageParser#getClass(java.lang.String)
     */
    public ClassBO getClass( String pAbsoluteClassName )
    {
        int index = 0;
        ClassBO classBO = null;
        // On vérifie que le nom de la classe ne fait pas partie des bugs McCabe,
        // sinon on retourne null
        if ( !isMcCabeBug( pAbsoluteClassName ) )
        {
            AbstractComplexComponentBO parent = null;
            // Si la classe est une classe anonyme remontée par McCabe, on va recherche
            // dans le set l'index d'apparition de cette classe dans le rapport
            // des méthodes McCabe.
            if ( pAbsoluteClassName.matches( "^.+\\$" + MC_CABE_ANONYMOUS_CLASS_NAME + "[0-9]+_$" ) )
            {
                index = indexOfAnonymousClass( pAbsoluteClassName );
            }
            // On nettoie le nom si celui-ci est remonté du rapport McCabe car
            // il peut ne pas convenir pour le parsing.
            String cleanClassName = clearReportName( pAbsoluteClassName );
            // On récupère le nom de la classe
            String className = getName( cleanClassName );
            if ( 0 != index )
            {
                className += index;
            }
            classBO = new ClassBO( className );
            // Si il s'agit d'une classe interne (avec un $) on attache la classe
            // à la classe qui la contient non au package directement (pour les
            // cas ou deux classes internes porteraient le même nom dans des classes
            // ayant le même package
            int lastIndexOfDollard = cleanClassName.lastIndexOf( "$" );
            if ( lastIndexOfDollard > 0 )
            {
                parent = getClass( cleanClassName.substring( 0, lastIndexOfDollard ) );
            }
            else
            {
                // On récupère le package associé
                String packageName = getParentName( cleanClassName );
                // On attache la classe à un package
                // si la classe en a un sinon on l'attache au projet directement.
                if ( null != packageName && packageName.trim().length() > 0 )
                {
                    // On doit deviner si le nom du package est un nom de classe
                    // ou de package.
                    if ( mKnownClasses.contains( packageName ) )
                    {
                        parent = getClass( packageName );
                    }
                    else
                    {
                        parent = getPackage( packageName );
                    }
                }
                else
                {
                    parent = mProject;
                }
            }
            classBO.setParent( parent );
        }
        return classBO;
    }

    /**
     * Crée le package désigné par les paramètres.
     * 
     * @param pPackageName le nom absolu du package
     * @return le PackageBO désigné par les paramètres.
     */
    public PackageBO getPackage( String pPackageName )
    {
        // On récupère le nom relatif du package
        String relativePackageName = getPackageName( pPackageName );
        PackageBO packageBO = new PackageBO( relativePackageName );
        String parentName = getParentName( pPackageName );
        // On affecte son package parent ou le projet si il n'a
        // pas de package parent.
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
     * Retourne la chaîne pAbsoluteName avant le dernier "." ou null si il n'y a pas de "."
     * 
     * @param pAbsoluteName le nom absolu du fils
     * @return le nom absolu du parent
     */
    public String getParentName( String pAbsoluteName )
    {
        String parent = null;
        int lastDot = pAbsoluteName.lastIndexOf( "." );
        if ( lastDot != -1 )
        {
            parent = pAbsoluteName.substring( 0, lastDot );
        }
        return parent;
    }

    /**
     * Retourne le nom entièrement qualifié du package de la classe <code>pFullClassName</code><br/> Si la classe n'a
     * pas de package, retourne une chaine vide.
     * 
     * @param pFullClassName le nom entièrement qualifié de la classe
     * @return le nom entièrement qualifié du package de la classe.
     */
    public String getAbsolutePackage( String pFullClassName )
    {
        String absolutePackage = getParentName( pFullClassName );
        if ( null == absolutePackage )
        {
            absolutePackage = "";
        }
        return absolutePackage;
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
        // On supprime le nom rajouté par RSM en cas
        return splittingPackageName[splittingPackageName.length - 1].replaceFirst( RSM_INTERFACE_PACKAGE_NAME, "" );
    }

    /**
     * Décompose le nom entièrement qualifié d'une classe afin de récupérer son nom relatif.
     * 
     * @param pAbsoluteName le nom entièrement qualifié de la classe (ou le nom entièrement qualifié de la méthode)
     * @return le nom relatif de la classe (ou de la méthode sans ses paramètres)
     */
    private String getName( String pAbsoluteName )
    {
        // s'il y a une parenthèse (méthode) il ne faut pas chercher dans les arguments
        int par = pAbsoluteName.lastIndexOf( "(" );
        if ( par == -1 )
        {
            par = pAbsoluteName.length();
        }
        int firstSharp = pAbsoluteName.indexOf( "#" );
        int index = Math.max( pAbsoluteName.lastIndexOf( "$", par ), pAbsoluteName.lastIndexOf( ".", par ) );
        // Dans le cas d'un "#", il faut le garder dans le nom contrairement au "$" et"."
        if ( firstSharp > index )
        {
            index = firstSharp - 1;
        }
        String result = pAbsoluteName.substring( index + 1 );
        // On renomme éventuellement la classe si il s'agit d'une classe anonyme
        result = rename( result, pAbsoluteName );
        // On nettoie le nom dans le cas d'une classe interne définie dans un bloc
        // car le compilateur nomme le .class de cette façon : ClasseExterner$1ClassInterne.class
        // donc on peut avoir des chiffres en début de nom de classe.
        return clearName( result );
    }

    /**
     * Renomme une classe dans le cas des classes anonymes:<br/>
     * <ul>
     * <li>Le nom donné par le compilateur java à une classe anonyme est : nomClass$Id (Id est l'identifiant généré par
     * le compilateur).</li>
     * <li>Le nom donné par McCabe pour une classe anonyme est : __Anon_Id (Id pouvant être 001, 002,...).</li>
     * </ul>
     * 
     * @param pName le nom d'une classe à renommer
     * @param pAbsoluteName le nom abolu de la classe ou de la méthode.
     * @return le nom qu'il faut donner à une classe anonyme ou <code>pName</code> si il ne s'agit pas du nom d'une
     *         classe anonyme.
     */
    private String rename( String pName, String pAbsoluteName )
    {
        // Si le nom de la classe est un chiffre alors il s'agit d'une class anonyme, on la renomme
        // pour avoir une similitude avec McCabe.
        if ( pName.matches( "[0-9]+" ) )
        {
            pName = ANONYMOUS_CLASS_NAME + pName;
        }
        // Si le nom de la classe est le nom d'une classe anonyme remontée par McCabe, on la renomme
        // pour avoir une similitude avec les .class (l'identifiant sera donné au niveau de getClass()).
        if ( pName.startsWith( MC_CABE_ANONYMOUS_CLASS_NAME ) )
        {
            pName = ANONYMOUS_CLASS_NAME;
        }
        return pName;
    }

    /**
     * @param pAbsoluteName le nom absolu de la classe anonyme à chercher
     * @return l'index + 1 de <code>pAbsoluteName</code> dans le set qui contient toutes les classes anonymes
     *         récupérer lors du premier passage sur les méthodes de cette classe anonyme remontée par McCabe. -1 si le
     *         nom n'est pas présent dans le set.
     */
    private int indexOfAnonymousClass( String pAbsoluteName )
    {
        int index = -1;
        int currentIndex = 1;
        // On parcours le set ordonné jusqu'à trouvé le nom de la classe
        for ( Iterator it = mAnonymousClasses.iterator(); it.hasNext() && -1 == index; currentIndex++ )
        {
            String currentName = (String) it.next();
            if ( currentName.startsWith( pAbsoluteName ) )
            {
                index = currentIndex;
            }
        }
        return index;
    }

    /**
     * Indique si le nom <code>pName</code> correspond à un des bugs de nommage McCabe.<br/> Les nommages considérés
     * comme étant des bugs McCabe sont les suivants:<br/>
     * <ul>
     * <li>Le nom d'une class ou d'une méthode se termine par _#Id (Id étant un chiffre) (ex : methodName_#1). Cela
     * peut arriver lorsque deux classes portent le même nom dans un même projet.</li>
     * <li>Le nom d'un package est un chiffre (ex : test.1.blockInnerClass). Cela peut arriver lorsqu'une classe
     * interne est définie dans un bloc.</li>
     * </ul>
     * 
     * @param pName le nom d'une classe ou d'une méthode à vérifier
     * @return true si le nom remonté correspond à un bug McCabe
     */
    private boolean isMcCabeBug( String pName )
    {
        boolean result = false;
        // Cas d'une classe ou d'une méthode considérée comme dupliquée (ex : Name_#1)
        String duplicateModule = ".*_#1$";
        // Cas d'une classe interne définie dans un bloc (ex : test.1.blockInnerClass)
        String blockInnerClass = ".*\\.[0-9]\\..*";
        result = pName.matches( duplicateModule ) || pName.matches( blockInnerClass );
        return result;
    }

    /**
     * Nettoie le nom du composant remonté du rapport McCabe.<br>
     * <ul>
     * <li>Retire le nom de la classe externe dans le cas d'un nom de méthode (correspondant définie dans une classe
     * interne elle-même définie dans un bloc.</li>
     * <li>Retire le nom de la méthode qui contient la classe anonyme dont le nom correspond à <code>pName</code> (ou
     * <code>pName</code> correspond au nom de la méthode définie dans cette classe anonyme).</li>
     * </ul>
     * 
     * @param pName le nom a nettoyer.
     * @return le nom propre.
     */
    public String clearReportName( String pName )
    {
        // On supprime les noms des methodes qui contiennent des classes internes
        // remontées par McCabe (ex : classeExterne.nomMethodeCE`classeInterne.nomMethodeCI)
        pName = pName.replaceAll( "\\.[^\\.]+`", "." );
        // On nettoie le nom dans le cas d'une classe anonyme définie dans une
        // méthode (method) car McCabe remonte ce cas de cette façon :
        // Pour une classe : package.ClassName.method(args)$_Anon_001_
        // Pour une méthode : package.ClassName.method(args)$_Anon_001_.subMethod(args)
        // Or on veut une cohérence avec le compilateur java qui n'indique pas la méthode
        // dans laquelle la classe est définie.
        pName = pName.replaceAll( "\\.[^\\.]+\\(.*\\)\\$", "\\$" );
        return pName;
    }

    /**
     * Supprime les chiffres en début de nom.
     * 
     * @param pName le nom à nettoyer
     * @return le nom sans chiffre en début.
     */
    private String clearName( String pName )
    {
        String badBeginning = "^[0-9]+";
        return pName.replaceFirst( badBeginning, "" );
    }

    /**
     * Décompose le nom entièrement qualifié d'une méthode afin de récupèrer le nom absolu de la classe.
     * 
     * @param pAbsoluteMethodName le nom absolu de la méthode
     * @return le nom absolu de la classe
     */
    private String getAbsoluteClassName( String pAbsoluteMethodName )
    {
        // s'il y a une parenthese (méthode) il ne faut pas chercher dans les arguments
        int par = pAbsoluteMethodName.lastIndexOf( "(" );
        if ( par == -1 )
        {
            par = pAbsoluteMethodName.length();
        }
        int index = Math.max( pAbsoluteMethodName.lastIndexOf( ".", par ), pAbsoluteMethodName.indexOf( "#" ) );
        return pAbsoluteMethodName.substring( 0, index );
    }

    /**
     * @param pAbsoluteClassName le nom absolu de la classe
     * @return le package racine de la classe, null si la classe n'a pas de package
     */
    public String getFirstPackage( String pAbsoluteClassName )
    {
        String first = null;
        int firstDot = pAbsoluteClassName.indexOf( "." );
        if ( 0 > firstDot )
        {
            first = pAbsoluteClassName.substring( 0, firstDot );
        }
        return first;
    }
}
