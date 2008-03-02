package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.airfrance.squalecommon.datatransfertobject.component.ProfileDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.ActionIdFormSelectable;

/**
 * Représente un utilisateur de l'application
 */
public class UserForm
    extends ActionIdFormSelectable
{

    /**
     * Liste des applications administrables et en cours de création (ApplicationForm).
     */
    private List mOnlyAdminApplicationsList = new LinkedList();

    /**
     * Liste des applications administrables (ApplicationForm).
     */
    private List mApplicationsList = new LinkedList();

    /**
     * Liste des applications publiques ayant des résulats (ApplicationForm).
     */
    private List mPublicApplicationsList = new LinkedList();

    /**
     * Liste des applications ayant des résulats (ApplicationForm).
     */
    private List mApplicationsWithResults = new LinkedList();

    /**
     * Liste des accès par défaut
     */
    private ProfileDTO mDefaultAccess = null;

    /**
     * Le nom complet de l'utilisateur
     */
    private String mName = "";

    /**
     * L'adresse e-mail de l'utilisateur
     */
    private String mEmail = "";

    /**
     * Indique si l'utilisateur ne souhaite pas recevoir d'emails automatiques
     */
    private boolean mUnsubscribed;

    /**
     * Le matricule
     */
    private String mMatricule = "";

    /**
     * La liste des applications accessibles à l'utilisateur. La clé est l'application (ApplicationForm).
     */
    private Map mProfiles = new HashMap();

    /**
     * @return le nom complet de l'utilisateur
     */
    public String getName()
    {
        return mName;
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
     * @return la liste des applications (ApplicationForm).
     */
    public List getApplicationsList()
    {
        return mApplicationsList;
    }

    /**
     * @return la liste des applications publiques (ApplicationForm).
     */
    public List getPublicApplicationsList()
    {
        return mPublicApplicationsList;
    }

    /**
     * @return la liste des applications ayant des résultats (ApplicationForm).
     */
    public List getApplicationsWithResults()
    {
        return mApplicationsWithResults;
    }

    /**
     * @param pList la liste des applications ayant des résultats (ApplicationForm).
     */
    public void setApplicationsWithResults( List pList )
    {
        mApplicationsWithResults = pList;
    }

    /**
     * @param pDefaultAccess la nouvelle liste droits par défaut.
     */
    public void setDefaultAccess( ProfileDTO pDefaultAccess )
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
     * @param pProfiles la nouvelle collection des profils.
     */
    public void setProfiles( Map pProfiles )
    {
        mProfiles = pProfiles;
    }

    /**
     * @param pApplicationsList le nouvelle liste des applications.
     */
    public void setApplicationsList( final List pApplicationsList )
    {
        mApplicationsList = pApplicationsList;
    }

    /**
     * @param pPublicList le nouvelle liste des applications publiques.
     */
    public void setPublicApplicationsList( final List pPublicList )
    {
        mPublicApplicationsList = pPublicList;

    }

    /**
     * @return la collection des profils.
     */
    public Map getProfiles()
    {
        return mProfiles;
    }

    /**
     * @return la liste des droits par défaut.
     */
    public ProfileDTO getDefaultAccess()
    {
        return mDefaultAccess;
    }

    /**
     * @return la liste des applications administrables et en cours de création
     */
    public List getOnlyAdminApplicationsList()
    {
        return mOnlyAdminApplicationsList;
    }

    /**
     * @param pOnlyAdminApplicationsList la liste des applications administrables et en cours de création
     */
    public void setOnlyAdminApplicationsList( List pOnlyAdminApplicationsList )
    {
        mOnlyAdminApplicationsList = pOnlyAdminApplicationsList;
    }

    /**
     * @return true si l'utilisateur s'est désabonné de l'envoi d'email
     */
    public boolean getUnsubscribed()
    {
        return mUnsubscribed;
    }

    /**
     * @param pUnsubscribed true si l'utilisateur s'est désabonné de l'envoi d'email
     */
    public void setUnsubscribed( boolean pUnsubscribed )
    {
        mUnsubscribed = pUnsubscribed;
    }
}
