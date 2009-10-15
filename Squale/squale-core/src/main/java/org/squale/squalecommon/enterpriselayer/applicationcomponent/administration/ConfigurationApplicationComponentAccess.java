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
package org.squale.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.squalecommon.datatransfertobject.config.AdminParamsDTO;
import org.squale.squalecommon.datatransfertobject.config.SqualixConfigurationDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.AdminParamsBO;
import org.squale.squalecommon.enterpriselayer.facade.config.ConfigurationImport;
import org.squale.squalecommon.enterpriselayer.facade.config.SqualixConfigFacade;

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

    /**
     * This method search all the admin-params useful for the mapping in the export function
     * 
     * @return The list of admin-params useful for the mapping
     * @throws JrafEnterpriseException If an error occurs during the search
     */
    public Collection<AdminParamsDTO> getExportAdminParams()
        throws JrafEnterpriseException
    {
        Collection<AdminParamsDTO> adminParamsList;
        adminParamsList = SqualixConfigFacade.getAdminParamsStartWith( AdminParamsBO.MAPPING );
        List<AdminParamsDTO> entityId =
            (List<AdminParamsDTO>) SqualixConfigFacade.getAdminParamsStartWith( AdminParamsBO.ENTITY_ID );
        if ( entityId.size() == 1 )
        {
            adminParamsList.add( entityId.get( 0 ) );
        }
        else if ( entityId.size() == 0 )
        {
            String message = "";
            throw new JrafEnterpriseException( message );
        }
        else
        {
            String message = "";
            throw new JrafEnterpriseException( message );
        }

        return adminParamsList;
    }

}
