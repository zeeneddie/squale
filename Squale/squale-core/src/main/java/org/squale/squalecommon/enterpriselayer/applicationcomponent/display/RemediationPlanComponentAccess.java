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
package org.squale.squalecommon.enterpriselayer.applicationcomponent.display;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.datatransfertobject.remediation.ComponentCriticalityDTO;
import org.squale.squalecommon.enterpriselayer.facade.quality.QualityResultFacade;

/**
 * Access component for the remediation plan
 * 
 * @author bfranchet
 */
public class RemediationPlanComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * UID
     */
    private static final long serialVersionUID = -620387937757730607L;

    /**
     * LOG
     */
    private static Log LOG = LogFactory.getLog( RemediationPlanComponentAccess.class );

    /**
     * Persistence provider
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * This method recover all the component involved in the current audit for the current module and which are not
     * "excluded from action plan"
     * 
     * @param auditId The id of the audit
     * @param projectId The id of the module
     * @return The list of component ordered by their criticality
     * @throws JrafEnterpriseException Exception occurs during the process
     */
    public List<ComponentCriticalityDTO> remediationByRisk( String auditId, String projectId )
        throws JrafEnterpriseException
    {

        ISession session;
        List<ComponentCriticalityDTO> compoList = new ArrayList<ComponentCriticalityDTO>();
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            compoList =
                QualityResultFacade.getRemediationByCriticality( session, Long.parseLong( auditId ),
                                                                 Long.parseLong( projectId ) );
        }
        catch ( JrafPersistenceException e )
        {
            LOG.error( e.getMessage() );
            throw new JrafEnterpriseException( e );
        }

        return compoList;
    }

}
