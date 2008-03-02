package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.io.InputStream;
import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.squalecommon.datatransfertobject.config.SqualixConfigurationDTO;
import com.airfrance.squalecommon.enterpriselayer.facade.config.ConfigurationImport;
import com.airfrance.squalecommon.enterpriselayer.facade.config.SqualixConfigFacade;

/**
 * Manipulation de la configuration Squalix
 */
public class ConfigurationApplicationComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * Obtention de la configuration
     * 
     * @return la configuration Squalix actuelle
     * @throws JrafEnterpriseException si une erreur survient
     */
    public SqualixConfigurationDTO getConfiguration()
        throws JrafEnterpriseException
    {
        return SqualixConfigFacade.getConfig();
    }

    /**
     * Obtention des récupérateurs de sources
     * 
     * @return les récupérateurs de sources actuels
     * @throws JrafEnterpriseException si une erreur survient
     */
    public Collection getSourceManagements()
        throws JrafEnterpriseException
    {
        return SqualixConfigFacade.getSourceManagements();
    }

    /**
     * Obtention des profiles squalix
     * 
     * @return les profiles Squalix actuels
     * @throws JrafEnterpriseException si une erreur survient
     */
    public Collection getProfiles()
        throws JrafEnterpriseException
    {
        return SqualixConfigFacade.getProfiles();
    }

    /**
     * Création de la configuration Squalix
     * 
     * @param pStream flux associé
     * @param pErrors erreur rencontrées
     * @return la configuration créee
     * @throws JrafEnterpriseException si erreur
     */
    public SqualixConfigurationDTO createConfig( InputStream pStream, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        return ConfigurationImport.createConfig( pStream, pErrors );
    }

    /**
     * Importation de la configuration Squalix
     * 
     * @param pStream flux associé
     * @param pErrors erreur rencontrées
     * @return la configuration importée
     * @throws JrafEnterpriseException si erreur
     */
    public SqualixConfigurationDTO importConfig( InputStream pStream, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        return ConfigurationImport.importConfig( pStream, pErrors );
    }

    /**
     * Obtention des tâches configurables d'un profile ou d'un source manager
     * 
     * @param pProfileName le nom du profile
     * @param pManagerName le nom du source manager
     * @return les tâches configurables
     * @throws JrafEnterpriseException si erreur
     */
    public Collection getConfigurableTasks( String pManagerName, String pProfileName )
        throws JrafEnterpriseException
    {
        return SqualixConfigFacade.getConfigurableTasks( pManagerName, pProfileName );
    }

    /**
     * Constructeur par défaut
     */
    public ConfigurationApplicationComponentAccess()
    {
    }

}
