package com.airfrance.squalecommon.enterpriselayer.businessobject.stats;

import java.io.Serializable;

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ServeurBO;

/**
 * La table regroupant les stats par profil et par site
 * 
 * @hibernate.class table="Stats_squale_dict_annexe"
 */
public class SiteAndProfilStatsDICTBO
    implements Serializable
{

    /**
     * Constructeur vide
     */
    public SiteAndProfilStatsDICTBO()
    {
    }

    /**
     * Constructeur
     * 
     * @param pId l'id du serveur
     * @param pProfil le profil
     * @param pNbOfCodesLines le nombre de lignes de code
     * @param pNbProjects le nombre de projets disponibles pour ce site
     */
    public SiteAndProfilStatsDICTBO( long pId, String pProfil, int pNbOfCodesLines, int pNbProjects )
    {
        // initialisation l'id à la valeur unsaved_value
        // pour permettre à hibernate de prendre la valeur de la séquence
        id = -1;
        mServeurBO = new ServeurBO();
        mServeurBO.setServeurId( pId );
        profil = pProfil;
        nbOfCodesLines = pNbOfCodesLines;
        nbProjects = pNbProjects;
    }

    /** l'identifiant en base */
    private long id;

    /** le nombre de projets pour ce site */
    private int nbProjects;

    /** le nom du site */
    private ServeurBO mServeurBO;

    /** le profil */
    private String profil;

    /** le nombre de lignes de codes pour ce site et ce profil */
    private int nbOfCodesLines;

    /**
     * @return le nombre de lignes de code pour ce site et ce profil
     * @hibernate.property name="nbOfCodesLines" column="NB_LIGNES" type="integer" update="true" insert="true"
     */
    public int getNbOfCodesLines()
    {
        return nbOfCodesLines;
    }

    /**
     * @return le profil
     * @hibernate.property name="Profil" column="Profil" type="string" length="50" update="true" insert="true"
     */
    public String getProfil()
    {
        return profil;
    }

    /**
     * @return le nombre de projets disponibles
     * @hibernate.property name="nbProjects" column="NB_PROJETS" type="integer" update="true" insert="true"
     */
    public int getNbProjects()
    {
        return nbProjects;
    }

    /**
     * @param newNumber le nouveau nombre de lignes de code
     */
    public void setNbOfCodesLines( int newNumber )
    {
        nbOfCodesLines = newNumber;
    }

    /**
     * @param newProfil le nouveau profil
     */
    public void setProfil( String newProfil )
    {
        profil = newProfil;
    }

    /**
     * @return l'id de l'objet
     * @hibernate.id generator-class="native" type="long" column="Id" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="stats_annexe_sequence"
     */
    public long getId()
    {
        return id;
    }

    /**
     * Setter pour l'id
     * 
     * @param newId la nouvelle valeur de l'id
     */
    public void setId( long newId )
    {
        id = newId;
    }

    /**
     * @param pNbProjects le nombre de projets disponibles
     */
    public void setNbProjects( int pNbProjects )
    {
        nbProjects = pNbProjects;
    }

    /**
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.config.ServeurBO"
     *                        column="Serveur" not-null="false" update="true" insert="true" cascade="none"
     *                        outer-join="auto"
     * @return le serveur
     */
    public ServeurBO getServeurBO()
    {
        return mServeurBO;
    }

    /**
     * @param pServeurBO le serveur
     */
    public void setServeurBO( ServeurBO pServeurBO )
    {
        mServeurBO = pServeurBO;
    }

}
