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
package org.squale.squalecommon.enterpriselayer.facade.sharedrepository;

import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.enterpriselayer.IFacade;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.sharedrepository.ApplicationExportDAOImpl;
import org.squale.squalecommon.datatransfertobject.sharedrepository.ApplicationExportDTO;
import org.squale.squalecommon.datatransfertobject.transform.sharedrepository.ApplicationExportTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.ApplicationExportBO;
import org.squale.squalecommon.enterpriselayer.facade.config.SqualixConfigFacade;

/**
 * Facade for the ApplicationExportBO business object
 *
 */
public final class ApplicationExportFacade
    implements IFacade
{

    /**
     * Private constructor
     */
    private ApplicationExportFacade()
    {

    }
    
    /**
     * This method search the {@link ApplicationExportBO } link to the application given in argument 
     * 
     * @param session The hibernate session
     * @param appId The id of the application
     * @return The {@link ApplicationExportBO}linked to application given in argument
     * @throws JrafEnterpriseException Exception occurs during the search in the database
     */
    public static ApplicationExportDTO getApplicationExport(ISession session, Long appId) throws JrafEnterpriseException
    {
        ApplicationExportDTO appliDto = null;
        try
        {
            ApplicationExportDAOImpl dao = ApplicationExportDAOImpl.getInstance();
            List<ApplicationExportBO>  listAppBo = (List<ApplicationExportBO>) dao.getLastExportByApplication( session, appId );   //findByExample( session, exampleBo );
            if (listAppBo.size()>0)
            {
                ApplicationExportBO appExport = listAppBo.get( 0 );
                appliDto = ApplicationExportTransform.bo2dto( appExport );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, SqualixConfigFacade.class.getName() + ".getApplicationExport" );
        }
        finally
        {
            FacadeHelper.closeSession( session, SqualixConfigFacade.class.getName() + ".getApplicationExport" );
        }
        return appliDto;
    }

    /**
     * This method save or update a list of {@link ApplicationExportBO}
     * 
     * @param session The persistence session
     * @param listAppliToExportDTO List of ApplicationExportDTO to save or update
     * @throws JrafEnterpriseException Exception happened during the persistence work
     */
    public static void saveOrUpdateList( ISession session, List<ApplicationExportDTO> listAppliToExportDTO )
        throws JrafEnterpriseException
    {
        try
        {
            ApplicationExportDAOImpl dao = ApplicationExportDAOImpl.getInstance();
            for ( ApplicationExportDTO dto : listAppliToExportDTO )
            {
                ApplicationExportBO appliToExportBO = ApplicationExportTransform.dto2bo( dto ) ;
                dao.save( session, appliToExportBO );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, SqualixConfigFacade.class.getName() + ".saveOrUpdateList" );
        }
        finally
        {
            FacadeHelper.closeSession( session, SqualixConfigFacade.class.getName() + ".saveOrUpdateList" );
        }
    }
}
