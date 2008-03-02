package com.airfrance.squalecommon.enterpriselayer.businessobject.rule;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;

/**
 * Grille qualité Une grille qualité est composée d'un ensemble de facteurs.
 * 
 * @hibernate.class table="QualityGrid" mutable="true" lazy="true"
 */
public class QualityGridBO
    implements Serializable
{
    /**
     * Identifiant (au sens technique) de l'objet
     */
    private long mId;

    /**
     * Liste des critères permettant de calculer le facteur
     */
    private SortedSet mFactors;

    /**
     * Date de la creation de la regle de calcul d'un résultat qualité
     */
    private Date mDateOfUpdate;

    /**
     * Nom de la grille
     */
    private String mName;

    /** les profils associés */
    private Set mProfiles = new HashSet();

    /**
     * Constructeur
     */
    public QualityGridBO()
    {
        mFactors = new TreeSet();
        mId = -1;
        Calendar cal = new GregorianCalendar();
        setDateOfUpdate( cal.getTime() );
    }

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property Note: unsaved-value An identifier property value that indicates
     *         that an instance is newly instantiated (unsaved), distinguishing it from transient instances that were
     *         saved or loaded in a previous session. If not specified you will get an exception like this: another
     *         object associated with the session has the same identifier
     * @hibernate.id generator-class="native" type="long" column="QualityGridId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="qualitygrid_sequence"
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @hibernate.property name="name" column="Name" type="string" not-null="true" unique="false"
     * @return nom
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @param pName nom
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Récupère les profils liés à cette grille
     * 
     * @return les profils
     * @hibernate.set table="Profiles_Grids" lazy="true" inverse="true"
     * @hibernate.collection-key column="GridId"
     * @hibernate.collection-many-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO"
     *                                    column="ProfileId"
     */
    public Set getProfiles()
    {
        return mProfiles;
    }

    /**
     * Récupère la date de mise à jour
     * 
     * @return la date de mise à jour
     * @hibernate.property name="dateOfUpdate" column="DateOfUpdate" type="timestamp" not-null="true" unique="false"
     */
    public Date getDateOfUpdate()
    {
        return mDateOfUpdate;
    }

    /**
     * Affecte la date de mise à jour
     * 
     * @param pDate la date de mise à jour
     */
    private void setDateOfUpdate( Date pDate )
    {
        mDateOfUpdate = pDate;
    }

    /**
     * Access method for the mFactors property.
     * 
     * @return the current value of the mFactors property
     * @hibernate.set table="GridFactor_Rule" lazy="true" cascade="all" inverse="false" sort="natural"
     * @hibernate.collection-key column="QualityGridId"
     * @hibernate.collection-many-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO"
     *                                    column="FactorRuleId"
     */
    public SortedSet getFactors()
    {
        return mFactors;
    }

    /**
     * Affectation des facteurs
     * 
     * @param pFactors facteurs
     */
    public void setFactors( SortedSet pFactors )
    {
        mFactors = pFactors;
    }

    /**
     * Ajout d'un facteur
     * 
     * @param pFactor facteur
     */
    public void addFactor( FactorRuleBO pFactor )
    {
        mFactors.add( pFactor );
    }

    /**
     * @param pProfiles les profils disponibles pour cette grille
     */
    public void setProfiles( Set pProfiles )
    {
        mProfiles = pProfiles;
    }

    /**
     * @param pProfile le profil à ajouter
     */
    public void addProfile( ProjectProfileBO pProfile )
    {
        mProfiles.add( pProfile );
    }

}
