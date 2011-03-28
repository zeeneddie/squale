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
package org.squale.squalecommon.enterpriselayer.applicationcomponent.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.component.ModuleLightDTO;
import org.squale.squalecommon.datatransfertobject.component.UserDTO;
import org.squale.squalecommon.datatransfertobject.result.QualityResultDTO;
import org.squale.squalecommon.datatransfertobject.result.ResultsDTO;
import org.squale.squalecommon.datatransfertobject.tag.TagDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.facade.component.ApplicationFacade;
import org.squale.squalecommon.enterpriselayer.facade.component.AuditFacade;
import org.squale.squalecommon.enterpriselayer.facade.quality.MeasureFacade;
import org.squale.squalecommon.enterpriselayer.facade.quality.QualityResultFacade;

/**
 * Application component for the rest interface
 * 
 * @author bfranchet
 */
public class RestComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * UID
     */
    private static final long serialVersionUID = 6684975460091150006L;

    /**
     * Default constructor
     */
    public RestComponentAccess()
    {

    }
    
    /**
     * This methods retrieves the applications which has at least one successful audit and are visible for the current
     * user
     * 
     * @param userDto The authenticated user
     * @return The applications visible by the user
     * @throws JrafEnterpriseException Exception occurs during the search
     */
    public List<ComponentDTO> visibleApplication( UserDTO userDto )
        throws JrafEnterpriseException
    {
        List<ComponentDTO> listApp = new ArrayList<ComponentDTO>();
        listApp = ApplicationFacade.visibleApplication( userDto );
        return listApp;
    }

    /**
     * This methods retrieves the successful audits available for the application given in argument
     * 
     * @param appId The application id
     * @return The list of audit available for the current application
     * @throws JrafEnterpriseException Exception occurs during the search of the audit
     */
    public List<AuditDTO> availableAudits( Long appId )
        throws JrafEnterpriseException
    {
        ComponentDTO compo = new ComponentDTO();
        compo.setID( appId );
        List<AuditDTO> listAudit = AuditFacade.getAudits( compo, null, null, null, AuditBO.TERMINATED );
        return listAudit;
    }
    
    /**
     * This method retrieves the failed audits available for the application given in argument
     * 
     * @param appId The application id
     * @return The list of failed audits available for the current application
     * @throws JrafEnterpriseException Exception occurs during the search of the audit
     */
    public List<AuditDTO> failedAudits( Long appId )
        throws JrafEnterpriseException
    {
        ComponentDTO compo = new ComponentDTO();
        compo.setID( appId );
        List<AuditDTO> listAudit = AuditFacade.getAudits( compo, null, null, null, AuditBO.FAILED );
        return listAudit;
    }
    
    /**
     * This method retrieves the partial audits available for the application given in argument
     * 
     * @param appId The application id
     * @return The list of partial audits available for the current application
     * @throws JrafEnterpriseException Exception occurs during the search of the audit
     */
    public List<AuditDTO> partialAudits( Long appId )
        throws JrafEnterpriseException
    {
        ComponentDTO compo = new ComponentDTO();
        compo.setID( appId );
        List<AuditDTO> listAudit = AuditFacade.getAudits( compo, null, null, null, AuditBO.PARTIAL );
        return listAudit;
    }

    /**
     * This method retrieves for the current audit the list of modules involved in the audit. For each module the method
     * retrieves its factor for the current audit, its tags, and its volumetry information for the current audit.
     * 
     * @param applicationId The application id
     * @param auditId The audit id
     * @return The list of module fully filled for the current audit
     * @throws JrafEnterpriseException The exception occurs during the search of the factors
     */
    public List<ModuleLightDTO> moduleList( Long applicationId, Long auditId )
        throws JrafEnterpriseException
    {
        List<ModuleLightDTO> listModule = new ArrayList<ModuleLightDTO>();

        // We retrieve the factors for the current audit and applications
        List<QualityResultDTO> listFactor = QualityResultFacade.getFactor( auditId, applicationId );
        ModuleLightDTO module;
        Map<Long, ModuleLightDTO> moduleMap = new HashMap<Long, ModuleLightDTO>();
        /*
         * For each factor, if its related module doesn't exist then we create it and we retrieve its tags and volumetry
         * informations else we only add the new factor to the list of factor of the module
         */
        for ( QualityResultDTO factor : listFactor )
        {
            ComponentDTO compo = factor.getProject();
            module = moduleMap.get( Long.valueOf( compo.getID() ) );
            if ( module == null )
            {
                AuditDTO audit = new AuditDTO();
                audit.setID( auditId );
                String gridName = QualityResultFacade.getGridName( compo, audit );
                module = new ModuleLightDTO( compo.getID(), compo.getName(), gridName );
                
                module.setTags( new ArrayList<TagDTO>( compo.getTags() ) );
                module.addFactor( factor );
                ResultsDTO res = MeasureFacade.getProjectVolumetry( auditId, compo );
                Map volumetryData = res.getResultMap();
                List<String> measureKeys = (List<String>) volumetryData.get( null );
                List<Integer> measureValues = (List<Integer>) volumetryData.get( compo );
                for ( int index = 0; index < measureKeys.size(); index++ )
                {
                    module.putVolumetry( measureKeys.get( index ), measureValues.get( index ) );
                }
                listModule.add( module );
                moduleMap.put( Long.valueOf( compo.getID() ), module );
            }
            else
            {
                module.addFactor( factor );
            }
        }
        return listModule;
    }

    /**
     * This method loads the audit linked to the auditId given in argument
     * 
     * @param auditId The audit id
     * @return The audit object
     * @throws JrafEnterpriseException Exception occurs
     */
    public AuditDTO audit( Long auditId )
        throws JrafEnterpriseException
    {
        AuditDTO audit = AuditFacade.getById( auditId );
        if ( audit != null )
        {
            ComponentDTO compo = AuditFacade.getLinkedApplication( auditId );
            audit.setApplicationId( compo.getID() );
            audit.setApplicationName( compo.getName() );
        }
        return audit;
    }

}
