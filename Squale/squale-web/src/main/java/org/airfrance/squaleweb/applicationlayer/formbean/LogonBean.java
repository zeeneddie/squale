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
package com.airfrance.squaleweb.applicationlayer.formbean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.Serializable;

import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import com.airfrance.welcom.outils.Access;
import com.airfrance.welcom.struts.bean.WILogonBeanSecurity;
import com.airfrance.welcom.struts.bean.WResultAction;

/**
 * Bean de l'utilisateur connecté
 */
public class LogonBean
    extends WResultAction
    implements WILogonBeanSecurity, Serializable
{
    /**
     * Résultat d'action utilisé lors de la validation des formulaires
     */
    private String mResultAction;

    /**
     * Le user est administrateur.
     */
    private boolean mIsAdmin;

    /**
     * Liste des applications accessibles (ApplicationDTO).
     */
    private List mApplicationsList = null;

    /**
     * Liste des applications administrables (ApplicationDTO).
     */
    private List mAdminList = null;

    /**
     * Liste des applications consultables (ApplicationDTO).
     */
    private List mReadOnlyList = null;

    /**
     * Liste des applications publiques (ApplicationDTO).
     */
    private List mPublicList = null;

    /**
     * Liste des accès courants (liés à l'application)
     */
    private String mCurrentAccess = null;

    /**
     * Liste des accès par défaut
     */
    private String mDefaultAccess = null;

    /**
     * Le nom complet de l'utilisateur
     */
    private String mName = "";

    /**
     * L'adresse e-mail de l'utilisateur
     */
    private String mEmail = "";

    /**
     * Le matricule
     */
    private String mMatricule = null;

    /**
     * La liste des applications accessibles à l'utilisateur. La clé est l' id de l'application (Long), la valeur le nom
     * du profil associé (String).
     */
    private Map mProfiles = null;

    /**
     * Id de l'utilisateur
     */
    private long mId = -1;

    /**
     * Constructeur
     */
    public LogonBean()
    {
    }

    /**
     * Charge les droits liés à l'application.<br>
     * 
     * @param pApplicationId le nom de l'application dont on veut les droits
     * @return le profil de l'utilisateur pour cette application
     */
    public String getApplicationRight( Long pApplicationId )
    {
        return (String) mProfiles.get( pApplicationId );
    }

    /**
     * @return le nom complet de l'utilisateur
     */
    public String getUserName()
    {
        return mName;
    }

    /**
     * @return le nom complet de l'utilisateur
     */
    public String getUsername()
    {
        return getUserName();
    }

    /**
     * @return l'adresse e-mail.
     */
    public String getEmail()
    {
        return mEmail;
    }

    /**
     * @return le matricule.
     */
    public String getMatricule()
    {
        return mMatricule;
    }

    /**
     * @return la liste des applications (ProjectDTO).
     */
    public List getApplicationsList()
    {
        return mApplicationsList;
    }

    /**
     * @return l'ID de l'utilisateur
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @param pId le nouvel ID
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @param pDefaultAccess la nouvelle liste droits par défaut.
     */
    public void setDefaultAccess( String pDefaultAccess )
    {
        mDefaultAccess = pDefaultAccess;
    }

    /**
     * @param pEmail le nouvel email.
     */
    public void setEmail( String pEmail )
    {
        mEmail = pEmail;
    }

    /**
     * @param pMatricule le nouveau matricule.
     */
    public void setMatricule( String pMatricule )
    {
        mMatricule = pMatricule;
    }

    /**
     * @param pName le nouveau nom.
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * @return true s'il s'agit d'un administrateur
     */
    public boolean isAdmin()
    {
        return mIsAdmin;
    }

    /**
     * @return la liste des applications administrable
     */
    public List getAdminList()
    {
        return mAdminList;
    }

    /**
     * @return la liste des applications consultables
     */
    public List getReadOnlyList()
    {
        return mReadOnlyList;
    }

    /**
     * @return la liste des applications publiques dont les résultats sont consutables
     */
    public List getPublicList()
    {
        return mPublicList;
    }

    /**
     * @return la liste des applications non publiques consutables
     */
    public List getNotPublicList()
    {
        List results = new ArrayList( mApplicationsList );
        results.removeAll( mPublicList );
        return results;
    }

    /**
     * @see com.airfrance.welcom.struts.bean.WIResultAction#setResultAction(java.lang.String) {@inheritDoc}
     */
    public void setResultAction( String pResultAction )
    {
        mResultAction = pResultAction;

    }

    /**
     * @see com.airfrance.welcom.struts.bean.WIResultAction#getResultAction() {@inheritDoc}
     */
    public String getResultAction()
    {
        return mResultAction;
    }

    /**
     * @param pIsAdmin admin
     */
    public void setAdmin( boolean pIsAdmin )
    {
        mIsAdmin = pIsAdmin;
    }

    /**
     * @param pProfiles profils sous la forme d'une map avec l'id d'application et le nom du profil
     */
    public void setProfiles( Map pProfiles )
    {
        mProfiles = pProfiles;
    }

    /**
     * @param pList list des applications avec un privilège admin
     */
    public void setAdminList( List pList )
    {
        mAdminList = pList;
    }

    /**
     * @param pList list des applications avec un privilège lecteur
     */
    public void setReadOnlyList( List pList )
    {
        mReadOnlyList = pList;
    }

    /**
     * @param pList list des applications publiques ayant des résultats
     */
    public void setPublicList( List pList )
    {
        mPublicList = pList;
    }

    /**
     * @param pList liste des applications sous la forme de ApplicationForm
     */
    public void setApplicationsList( List pList )
    {
        mApplicationsList = pList;
    }

    /**
     * @param pCurrentAccess accès courant
     */
    public void setCurrentAccess( String pCurrentAccess )
    {
        mCurrentAccess = pCurrentAccess;
    }

    /**
     * Retourne l'accès associé.<br>
     * Les droits liés à l'application sont prioritaires par rapport aux droits par défaut.<br>
     * Si le droit n'est pas paramétré, le droit le plus bas est retourné.
     * 
     * @param pKey clé de droit atomique recherchée.
     * @return l'action associée au droit.
     */
    public String getAccess( String pKey )
    {
        // Par défaut : aucun accès possible
        String result = Access.NO;
        if ( isAdmin() )
        {
            // Un admin a tous les droits
            result = Access.READWRITE;
        }
        else if ( pKey.equals( "manager" ) && mCurrentAccess.equals( ProfileBO.MANAGER_PROFILE_NAME ) )
        {
            // un manager a tous les droits sur ses applications
            result = Access.READWRITE;
        }
        else if ( pKey.equals( "reader" ) )
        {
            // un lecteur n'a que les droits de lecture
            result = Access.READONLY;
        }
        else if ( pKey.equals( "default" ) )
        {
            // tous les droits si default.
            result = Access.READWRITE;
        }
        return result;
    }

    /**
     * @param pAppliId l'id de l'application sous forme de String
     * @return le profil de l'utilisateur lié à l'application d'id <code>pAppliId</code>
     */
    public Object getProfile( String pAppliId )
    {
        Long key = new Long( Long.parseLong( pAppliId ) );
        return mProfiles.get( key );
    }

    /**
     * Construit les groupes d'applications pour le menu header PRECONDITION : les applications sont triées par ordre
     * alphabétique
     * 
     * @param applications les applications
     * @param key la clé de configuration présentant (ex : E,R qui signifie grouper les applications dont la première
     *            lettre < E puis entre F et R et enfin celles après R)
     * @param minApplis le nombre minimum d'applications dans le menu pour que le regroupement soit effectué
     * @return les applications groupées alphabétiquement : ex : key : A - E (le message du menu) value : Appli1,
     *         Appli2,... (les applications dont la première lettre du nom est entre A et E)
     */
    public static TreeMap getApplicationMenu( List applications, String key, int minApplis )
    {
        TreeMap result = null;
        if ( null != key )
        {
            result = new TreeMap();
            List groupedApplis = new ArrayList();
            int nbApplis = 0;
            // On récupère les lettres pour le regroupement
            // la clé de configuration est de la forme E,R (i.e. on ne met pas la première et dernière lettre
            // de l'alphabet car c'est implicite)
            String[] split = key.split( " *, *" );
            // Le premier groupe commence toujours par A
            char firstLetter = 'A';
            char lastLetter = split[0].toUpperCase().charAt( 0 );
            // Le message qui va apparaître dans le menu
            String messageKey = firstLetter + " - " + lastLetter;
            int cptForLetter = 1;
            char appliFirstLetter;
            for ( int i = 0; i < applications.size(); i++ )
            {
                ApplicationForm curAppli = (ApplicationForm) applications.get( i );
                // On ne regarde que les applications ayant des résultats
                if ( curAppli.getHasResults() )
                {
                    appliFirstLetter = curAppli.getApplicationName().toUpperCase().charAt( 0 );
                    if ( appliFirstLetter > lastLetter )
                    {
                        // On ajoute le groupe et on commence à grouper les autres applications
                        result.put( messageKey, groupedApplis );
                        // On passe à la lettre suivant la dernière de l'ancien groupe
                        // pour commencer le nouveau groupe même si ce nouveau groupe commence par une
                        // application qui commence par une lettre supérieure
                        firstLetter = (char) ( lastLetter + 1 );
                        cptForLetter = getNextLastLetter( cptForLetter, appliFirstLetter, split );
                        if ( split.length == cptForLetter )
                        {
                            // il n'y a plus d'intervalles, on prend donc le reste de l'alphabet
                            lastLetter = 'Z';
                        }
                        else
                        {
                            lastLetter = split[cptForLetter++].toUpperCase().charAt( 0 );
                        }
                        messageKey = firstLetter + " - " + lastLetter;
                        groupedApplis = new ArrayList();
                    }
                    groupedApplis.add( curAppli );
                    nbApplis++;
                }
            }
            if ( groupedApplis.size() > 0 )
            {
                result.put( messageKey, groupedApplis );
            }
            if ( nbApplis <= minApplis )
            {
                // On ne regroupe pas
                result = null;
            }
        }
        return result;
    }

    /**
     * Cette méthode permet de ne pas se retrouver avec un groupe vide lors de la construction du menu 'Applications'
     * 
     * @param cptForLetter l'index courant du tableau
     * @param appliFirstLetter la première lettre de l'application
     * @param split le tableau des lettres
     * @return la dernière lettre du nouveau groupe
     */
    private static int getNextLastLetter( int cptForLetter, char appliFirstLetter, String[] split )
    {
        int size = split.length;
        int currentIndex = cptForLetter;
        int index = size;
        for ( ; currentIndex < size && index == size; currentIndex++ )
        {
            // Si l'application rentre bien dans ce nouveau groupe on prend cette dernière
            // lettre sinon on avance pour trouver le bon groupe
            if ( split[currentIndex].toUpperCase().charAt( 0 ) >= appliFirstLetter )
            {
                index = currentIndex;
            }
        }
        return index;
    }

}
