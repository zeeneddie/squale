//Source file: D:\\cc_views\\squale_v0_0_act_M400843\\squale\\src\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\businessobject\\profile\\UserBO.java

package com.airfrance.squalecommon.enterpriselayer.businessobject.profile;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Données d'un utilisateur
 * 
 * @author m400842
 * @hibernate.class table="UserBO" mutable="true"
 */
public class UserBO
    implements Serializable
{

    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;

    /**
     * Nom complet de l'utilisateur
     */
    private String mFullName;

    /**
     * Matricule de l'utilisateur
     */
    private String mMatricule;

    /**
     * Mot de passe crypté de l'utilisateur
     */
    private String mPassword;

    /**
     * Adresse e-mail de l'utilisateur
     */

    private String mEmail;

    /**
     * Indique si l'utilisateur ne souhaite pas recevoir d'emails automatiques
     */
    private boolean mUnsubscribed;

    /**
     * Contient les profils liés aux projets
     */
    private Map mRights;

    /**
     * Profil par défaut (sur les projets sur lequel n'est pas inscrit)
     */
    private ProfileBO mDefaultProfile;

    /**
     * Access method for the mFullName property.
     * 
     * @return the current value of the mFullName property
     * @hibernate.property name="fullName" column="FullName" type="string" // * length="" not-null="false"
     *                     unique="false"
     * @roseuid 42BACED8021E
     */
    public String getFullName()
    {
        return mFullName;
    }

    /**
     * Sets the value of the mFullName property.
     * 
     * @param pFullName the new value of the mFullName property
     * @roseuid 42BACED8021F
     */
    public void setFullName( String pFullName )
    {
        mFullName = pFullName;
    }

    /**
     * Access method for the mMatricule property.
     * 
     * @return the current value of the mMatricule property
     * @hibernate.property name="matricule" column="Matricule" type="string" // * length="" not-null="true"
     *                     unique="true"
     * @roseuid 42BACED8022A
     */
    public String getMatricule()
    {
        return mMatricule;
    }

    /**
     * Sets the value of the mMatricule property.
     * 
     * @param pMatricule the new value of the mMatricule property
     * @roseuid 42BACED8022B
     */
    public void setMatricule( String pMatricule )
    {
        mMatricule = pMatricule;
    }

    /**
     * Access method for the mPassword property.
     * 
     * @return the current value of the mPassword property
     * @hibernate.property name="password" column="Password" type="string" // * length="" not-null="false"
     *                     unique="false"
     * @roseuid 42BACED80239
     */
    public String getPassword()
    {
        return mPassword;
    }

    /**
     * Sets the value of the mPassword property.
     * 
     * @param pPassword the new value of the mPassword property
     * @roseuid 42BACED80248
     */
    public void setPassword( String pPassword )
    {
        mPassword = pPassword;
    }

    /**
     * Access method for the mEmail property.
     * 
     * @return the current value of the mEmail property
     * @hibernate.property name="email" column="Email" type="string" // * length="" not-null="false" unique="false"
     * @roseuid 42BACED8024A
     */
    public String getEmail()
    {
        return mEmail;
    }

    /**
     * Sets the value of the mEmail property.
     * 
     * @param pEmail the new value of the mEmail property
     * @roseuid 42BACED80258
     */
    public void setEmail( String pEmail )
    {
        mEmail = pEmail;
    }

    /**
     * Access method for the mRights property.
     * 
     * @return the current value of the mRights property
     * @hibernate.map name="rights" table="User_Rights" lazy="true" cascade="none"
     * @hibernate.index-many-to-many column="ApplicationId"
     *                               class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO"
     * @hibernate.collection-key column="UserId"
     * @hibernate.collection-many-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO"
     *                                    column="ProfileId"
     * @roseuid 42BACED8025A
     */
    public Map getRights()
    {
        return mRights;
    }

    /**
     * Sets the value of the mRights property.
     * 
     * @param pRights the new value of the mRights property
     * @roseuid 42BACED80267
     */
    public void setRights( Map pRights )
    {
        mRights = pRights;
    }

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property Note: unsaved-value An identifier property value that indicates
     *         that an instance is newly instantiated (unsaved), distinguishing it from transient instances that were
     *         saved or loaded in a previous session. If not specified you will get an exception like this: another
     *         object associated with the session has the same identifier
     * @hibernate.id generator-class="native" type="long" column="UserId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="user_sequence"
     * @roseuid 42BFF24000BD
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     * @roseuid 42BFF24000CC
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Constructeur par défaut.
     * 
     * @roseuid 42CB8EC30051
     */
    public UserBO()
    {
        mId = -1;
        mRights = new HashMap();
    }

    /**
     * Constructeur complet.
     * 
     * @param pFullName nom complet de l'utilisateur
     * @param pMatricule le matricule de l'utilisateur
     * @param pPassword le mot de passe crypté de l'utilisateur
     * @param pEmail l'eamil de l'utilisateur
     * @param pRights les droits
     * @roseuid 42CB8EC3012C
     */
    public UserBO( String pFullName, String pMatricule, String pPassword, String pEmail, Map pRights )
    {
        mId = -1;
        mFullName = pFullName;
        mMatricule = pMatricule;
        mPassword = pPassword;
        mEmail = pEmail;
        mRights = pRights;
    }

    /**
     * Récupère le profil par défaut de l'utilisateur
     * 
     * @return le profil par défaut
     * @hibernate.many-to-one column="ProfileId"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO"
     *                        cascade="none" not-null="true"
     * @roseuid 42D268A801AC
     */
    public ProfileBO getDefaultProfile()
    {
        return mDefaultProfile;
    }

    /**
     * Affecte le profil par défaut de l'utilisateur
     * 
     * @param pProfileBO le profil par défaut de l'utilisateur
     * @roseuid 42D268A80267
     */
    public void setDefaultProfile( ProfileBO pProfileBO )
    {
        mDefaultProfile = pProfileBO;
    }

    /**
     * @return true si l'utilisateur s'est désabonné de l'envoi d'email
     * @hibernate.property name="unsubscribed" column="unsubscribed" type="boolean" unique="false"
     */
    public boolean isUnsubscribed()
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
