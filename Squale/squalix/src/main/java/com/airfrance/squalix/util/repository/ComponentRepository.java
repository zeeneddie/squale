package com.airfrance.squalix.util.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import com.airfrance.squalecommon.daolayer.component.MethodDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComplexComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.JspBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlInterfaceBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlModelBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlPackageBO;

/**
 * Charger de faire persister les composants.
 */
public class ComponentRepository
{

    /**
     * Séparateur des champs de clés. Ne doit pas être un séparateur utilisé par McCabe pour qualifier entièrement un
     * nom.
     */
    private static final String KEY_SEPARATOR = "<=>";

    /**
     * Session de persistance.
     */
    private ISession mSession = null;

    /**
     * Contient la liste des classes du projet
     */
    private Map mClasses;

    /**
     * Contient la liste des méthodes du projet
     */
    private Map mMethods;

    /**
     * Liste des packages/namespaces du projet
     */
    private Map mPackages;

    /**
     * Liste des Modèles UML
     */
    private Map mUmlModels;

    /**
     * Liste des packages UML
     */
    private Map mUmlPackages;

    /**
     * Liste des Interfaces UML
     */

    private Map mUmlInterface;

    /**
     * Contient la liste des classes UML du modèle
     */
    private Map mUmlClasses;

    /**
     * Contient la liste des pages JSP
     */
    private Map mJSPs;

    /**
     * Projet sur lequel est réalisée l'analyse.
     */
    private ProjectBO mProject;

    /**
     * Constructeur
     * 
     * @param pProject le projet sur lequel est réalisée l'analyse.
     * @param pSession la session de persistance
     */
    public ComponentRepository( ProjectBO pProject, ISession pSession )
    {
        mProject = pProject;
        mSession = pSession;
        mClasses = new HashMap();
        mMethods = new HashMap();
        mPackages = new HashMap();
        mUmlInterface = new HashMap();
        mUmlModels = new HashMap();
        mUmlClasses = new HashMap();
        mUmlPackages = new HashMap();
        mJSPs = new HashMap();
        Collection children = mProject.getChildren();
        if ( null != children )
        {
            Iterator it = children.iterator();
            while ( it.hasNext() )
            {
                addToCollection( (AbstractComponentBO) it.next(), null );
            }
        }
    }

    /**
     * Crée la clé du composant pour être utilisée avec l'adaptateur et ajoute le composant et ses enfants dans les
     * collections locales associées.
     * 
     * @param pComponent le composant à ajouter.
     * @param pParentName le nom du parent si disponible.
     */
    private void addToCollection( AbstractComponentBO pComponent, String pParentName )
    {
        // Création de la clé
        String newParentName = pParentName;

        if ( null == newParentName )
        {
            newParentName = pComponent.getName();
        }
        else
        {
            newParentName += KEY_SEPARATOR + pComponent.getName();
        }
        Map map = getMapForComponent( pComponent );
        if ( pComponent instanceof AbstractComplexComponentBO )
        {
            map.put( newParentName, pComponent );
            Collection children = ( (AbstractComplexComponentBO) pComponent ).getChildren();
            if ( null != children )
            {
                // Si le composant est complexe, alors on va réappeler la méthode sur
                // chacun de ses enfants, de manière récursive pour parcourir tout
                // l'arbre des composants du projet
                Iterator it = children.iterator();
                while ( it.hasNext() )
                {
                    addToCollection( (AbstractComponentBO) it.next(), newParentName );
                }
            }
        }
        else
        {
            // Sinon, il s'agit d'une méthode, on l'ajoute simplement à sa map
            map.put( newParentName, pComponent );
        }
    }

    /**
     * Fait persister un composant ou le récupère si il existe déjà.
     * 
     * @param pComponent le composant à rechercher ou à faire persister.
     * @return le composant persistant.
     * @throws JrafDaoException si erreur
     */
    public AbstractComponentBO persisteComponent( AbstractComponentBO pComponent )
        throws JrafDaoException
    {
        // On crée la liste de ses parents + le composant lui-même
        List parents = getParents( pComponent );
        AbstractComponentBO persistentParent = mProject;
        int parentsSize = parents.size();
        for ( int i = parents.size() - 1; i >= 0; i-- )
        {
            persistentParent = persisteComponent( persistentParent, (AbstractComponentBO) parents.get( i ) );
        }
        return persistentParent;
    }

    /**
     * Construit l'arbre complet du composant.
     * 
     * @param pComponent le composant
     * @return la liste contenant les parents de pComponent et lui-même.
     */
    private List getParents( AbstractComponentBO pComponent )
    {
        List parents = new ArrayList();
        parents.add( pComponent );
        AbstractComplexComponentBO parent = pComponent.getParent();
        boolean hasCorrectParents = ( parent != null );
        for ( ; parent != null && hasCorrectParents; parent = parent.getParent() )
        {
            if ( parent instanceof ProjectBO )
            {
                hasCorrectParents = false;
            }
            else
            {
                parents.add( parent );
            }
        }
        return parents;
    }

    /**
     * Fait persister un composant (ou le récupère si il existe déjà) en lui associant son parent déjà persistant.
     * 
     * @param pPersistentParent le parent du composant déjà persistant.
     * @param pComponent le composant à faire persister ou à rechercher
     * @return le composant persistant
     * @throws JrafDaoException si erreur
     */
    private AbstractComponentBO persisteComponent( AbstractComponentBO pPersistentParent, AbstractComponentBO pComponent )
        throws JrafDaoException
    {
        // On récupère la map du composant
        Map map = getMapForComponent( pComponent );
        // On crée la clé représentant l'objet dans la map
        String key = buildKey( pComponent );
        AbstractComponentBO mapComponent = (AbstractComponentBO) map.get( key );
        if ( null == mapComponent )
        {
            // il n'existe pas, on le crée et on sauvegarde l'objet en base
            AbstractComponentDAOImpl dao = AbstractComponentDAOImpl.getInstance();
            pComponent.setParent( (AbstractComplexComponentBO) pPersistentParent );
            pComponent.setProject( mProject );
            dao.save( mSession, pComponent );
            // On ajoute l' élément persistant à la map correspondante
            map.put( key, pComponent );
            mapComponent = pComponent;
        }
        else if ( pComponent instanceof ClassBO )
        {
            // cas particulier d'un classe qui peut être persistée sans son fichier
            ClassBO classPersistent = (ClassBO) mapComponent;
            ClassBO classArg = (ClassBO) pComponent;
            if ( null == classPersistent.getFileName() && null != classArg.getFileName() )
            {
                // On update la classe
                AbstractComponentDAOImpl dao = AbstractComponentDAOImpl.getInstance();
                classPersistent.setFileName( classArg.getFileName() );
                dao.save( mSession, classPersistent );
            }
        }

        return mapComponent;
    }

    /**
     * Retrouve la map en fonction du type du composant.
     * 
     * @param pComponent le composant
     * @return la map dans laquelle pComponent doit être
     */
    private Map getMapForComponent( AbstractComponentBO pComponent )
    {
        Map result = null;
        // Chaque type de composant à sa map associée,
        // on récupère donc le véritable type de l'objet
        // passé en paramètre et on retourne sa map associée
        if ( pComponent instanceof MethodBO )
        {
            // mMethods contient des MethodBO
            result = mMethods;
        }
        else if ( pComponent instanceof ClassBO )
        {
            // mClasses contient des ClassBO
            result = mClasses;
        }
        else if ( pComponent instanceof PackageBO )
        {
            // mPackages contient des PackageBO
            result = mPackages;
        }
        else if ( pComponent instanceof UmlInterfaceBO )
        {
            // mUmlInterface contient des UmlInterfaceBO
            result = mUmlInterface;
        }
        else if ( pComponent instanceof UmlModelBO )
        {
            // mUmlModels contient des UmlModelBO
            result = mUmlModels;
        }
        else if ( pComponent instanceof UmlClassBO )
        {
            // mUmlClass contient des UmlClasslBO
            result = mUmlClasses;
        }
        else if ( pComponent instanceof UmlPackageBO )
        {
            // mUmlPackage contient des UmlPackagelBO
            result = mUmlPackages;
        }
        else if ( pComponent instanceof JspBO )
        {
            // mJSPs contient des JspBO
            result = mJSPs;
        }
        return result;
    }

    /**
     * Construit la clé du composant qui correspond à la concaténation de ses parents séparée par KEY_SEPARATOR
     * 
     * @param pComponent le composant
     * @return la clé représentant le composant dans les maps
     */
    public String buildKey( AbstractComponentBO pComponent )
    {
        String key = pComponent.getName();
        AbstractComponentBO parent = pComponent.getParent();
        boolean hasCorrectParents = ( parent != null );
        while ( hasCorrectParents )
        {
            if ( parent instanceof ProjectBO )
            {
                hasCorrectParents = false;
            }
            else
            {
                key = parent.getName() + KEY_SEPARATOR + key;
                parent = parent.getParent();
                hasCorrectParents = ( parent != null );
            }
        }
        return key;
    }

    /**
     * @return la session
     */
    public ISession getSession()
    {
        return mSession;
    }

    /**
     * @param pMethodName le nom de la méthode à récupérer
     * @param pFileName le nom du fichier dans lequel se trouve la méthode
     * @param pAuditId l'id de l'audit
     * @return la bonne méthodBO
     * @throws JrafDaoException en cas d'échec
     */
    public Collection getSimilarMethods( String pMethodName, String pFileName, long pAuditId )
        throws JrafDaoException
    {
        AbstractComponentDAOImpl compDao = AbstractComponentDAOImpl.getInstance();
        Collection result = new ArrayList( 0 );
        MethodDAOImpl dao = MethodDAOImpl.getInstance();
        result = dao.findMethodeByName( mSession, pMethodName, pFileName, pAuditId );
        return result;
    }

    /**
     * This method search the component give in arguments in the list of all componentBO already existent
     * 
     * @param component Component to search
     * @return The componentBO associate if it is found or null if it is not found
     */
    public AbstractComponentBO getComponent( AbstractComponentBO component )
    {
        Map map = getMapForComponent( component );
        String key = buildKey( component );
        AbstractComponentBO persistentComponent = (AbstractComponentBO) map.get( key );
        return persistentComponent;
    }

    /**
     * This method give access to the attribute mClasses
     * 
     * @return the list of classBO already persist
     */
    public Map getClasses()
    {
        return mClasses;
    }

    /**
     * This method give access to the attribute mMethods
     * 
     * @return the list of methodBo already persist
     */
    public Map getMethods()
    {
        return mMethods;
    }

    /**
     * Compare two component by there key. This method use the buildKey() method of the ComponentRepository class to
     * build the key
     * 
     * @param pComponent First component to compare
     * @param pComponentToCompare Second component to compare
     * @return true if there key are the same
     */
    public boolean compare( AbstractComponentBO pComponent, AbstractComponentBO pComponentToCompare )
    {
        boolean bool = false;
        if ( buildKey( pComponent ).compareTo( buildKey( pComponentToCompare ) ) == 0 )
        {
            bool = true;
        }
        return bool;
    }

}
