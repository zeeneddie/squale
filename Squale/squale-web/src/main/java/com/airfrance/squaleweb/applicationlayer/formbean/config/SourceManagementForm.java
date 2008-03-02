package com.airfrance.squaleweb.applicationlayer.formbean.config;

import com.airfrance.squalecommon.datatransfertobject.config.SourceManagementDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Contient les données des récupérateurs de sources
 */
public class SourceManagementForm
    extends RootForm
{

    /** Id du récupérateur de sources en String pour le récupérer dans l'option */
    private String mId;

    /** Nom du récupérateur de source */
    private String mName;

    /**
     * Constructeur par défaut
     */
    public SourceManagementForm()
    {
        super();
    }

    /**
     * Constructeur
     * 
     * @param pSourceManagement le DTO
     */
    public SourceManagementForm( SourceManagementDTO pSourceManagement )
    {
        mId = Long.toString( pSourceManagement.getId() );
        mName = pSourceManagement.getName();
    }

    /**
     * Méthode d'accès à mId
     * 
     * @return l'identifiant du récupérateur de source
     */
    public String getId()
    {
        return mId;
    }

    /**
     * Méthode d'accès à mName
     * 
     * @return le nom du récupérateur de source
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Change la valeur de mId
     * 
     * @param pId le nouvel identifiant
     */
    public void setId( String pId )
    {
        mId = pId;
    }

    /**
     * Change la valeur de mId
     * 
     * @param pId le nouvel identifiant
     */
    public void setId( long pId )
    {
        mId = Long.toString( pId );
    }

    /**
     * Change la valeur de mName
     * 
     * @param pName le nouveau nom
     */
    public void setName( String pName )
    {
        mName = pName;
    }

}
