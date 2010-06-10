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
import java.util.List;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.squalecommon.datatransfertobject.component.ApplicationLightDTO;
import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.component.UserDTO;
import org.squale.squalecommon.datatransfertobject.result.QualityResultDTO;
import org.squale.squalecommon.enterpriselayer.facade.component.ApplicationFacade;
import org.squale.squalecommon.enterpriselayer.facade.component.AuditFacade;
import org.squale.squalecommon.enterpriselayer.facade.component.UserFacade;
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
     * This method try authenticate the user given in argument. If the authentication succeed the method returns a
     * UserDTO which corresponding to the authenticated user. Else the method returns an empty UserDTO object
     * 
     * @param pUser : the user to authenticate
     * @return A UserDTO object
     * @throws JrafEnterpriseException exception happened during the search in the data base
     */
    public UserDTO userAuthentication( UserDTO pUser )
        throws JrafEnterpriseException
    {
        UserDTO userDTO = null;
        userDTO = UserFacade.getUserByMatriculeAndPassword( pUser );
        return userDTO;
    }

    /**
     * This methods retrieves the applications which has at least one successful audit and are visible for the current
     * user
     * 
     * @param userDto The authenticated user
     * @return The applications visible by the user
     * @throws JrafEnterpriseException Exception ocuurs during the search
     */
    public List<ApplicationLightDTO> visibleApplication( UserDTO userDto )
        throws JrafEnterpriseException
    {
        List<ApplicationLightDTO> listApp = new ArrayList<ApplicationLightDTO>();
        listApp = ApplicationFacade.visibleApplication( userDto );
        return listApp;
    }

    /**
     * This methods retrieves the audits available for the application given in argument
     * 
     * @param appId The application id
     * @return The list of audit available for the current application
     * @throws JrafEnterpriseException Exception occurs during the search of the audit
     */
    public List<AuditDTO> availableAudits( Long appId )
        throws JrafEnterpriseException
    {
        List<AuditDTO> listAudit = AuditFacade.getAllSuccessfulAudits( appId );
        return listAudit;
    }

    /**
     * This methods retrieves the factors linked to current audit. The factor are order by module and by factor name
     * 
     * @param applicationId The application id
     * @param auditId The audit id
     * @return the list of factor for the current audit
     * @throws JrafEnterpriseException The exception occurs during the search of the factors
     */
    public List<QualityResultDTO> factorList( Long applicationId, Long auditId )
        throws JrafEnterpriseException
    {
        List<QualityResultDTO> listFactor = QualityResultFacade.getFactor( auditId, applicationId );
        return listFactor;
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
