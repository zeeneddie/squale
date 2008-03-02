package com.airfrance.squalecommon.enterpriselayer.businessobject.config;

import java.util.HashSet;
import java.util.Set;

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.AbstractDisplayConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * Profile d'un projet
 * 
 * @hibernate.subclass lazy="true" discriminator-value="Profile"
 */
public class ProjectProfileBO
    extends AbstractTasksUserBO
{

    /** les configurations d'affichage */
    private Set mProfileDisplayConfs = new HashSet();

    /** les grilles associées */
    private Set mGrids = new HashSet();

    /** true si l'export IDE est possible avec ce profil */
    private boolean mExportIDE = true;

    /**
     * Récupère les configurations d'affichage liées à ce profil
     * 
     * @return les configurations
     * @hibernate.set table="Profiles_DisplayConfs" lazy="true" cascade="all" inverse="false"
     * @hibernate.collection-key column="ProfileId"
     * @hibernate.collection-one-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.config.Profile_DisplayConfBO"
     */
    public Set getProfileDisplayConfs()
    {
        return mProfileDisplayConfs;
    }

    /**
     * Récupère les grilles liées à ce profil
     * 
     * @return les grilles
     * @hibernate.set table="Profiles_Grids" lazy="true"
     * @hibernate.collection-key column="ProfileId"
     * @hibernate.collection-many-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO"
     *                                    column="GridId"
     */
    public Set getGrids()
    {
        return mGrids;
    }

    /**
     * @hibernate.property name="exportIDE" column="export_IDE" type="boolean" unique="false"
     * @return true si le profil permet l'export IDE
     */
    public boolean getExportIDE()
    {
        return mExportIDE;
    }

    /**
     * @param pExport true si le profil permet l'export IDE
     */
    public void setExportIDE( boolean pExport )
    {
        mExportIDE = pExport;
    }

    /**
     * @param pProfileDisplayConfs les configurations d'affichage liées à ce profil
     */
    public void setProfileDisplayConfs( Set pProfileDisplayConfs )
    {
        mProfileDisplayConfs = pProfileDisplayConfs;
    }

    /**
     * @param pDisplayConf la configuration à ajouter
     */
    public void addProfileDisplayConf( AbstractDisplayConfBO pDisplayConf )
    {
        Profile_DisplayConfBO confLink = new Profile_DisplayConfBO();
        confLink.setDisplayConf( pDisplayConf );
        confLink.setProfile( this );
        mProfileDisplayConfs.add( confLink );
    }

    /**
     * @param pGrids les grilles disponibles pour ce profil
     */
    public void setGrids( Set pGrids )
    {
        mGrids = pGrids;
    }

    /**
     * @param pGrid la grille à ajouter
     */
    public void addGrid( QualityGridBO pGrid )
    {
        mGrids.add( pGrid );
    }
}
