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
package com.airfrance.squalecommon.enterpriselayer.facade.config.web;

import java.util.List;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.enterpriselayer.IFacade;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.config.web.HomepageComponentDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.config.web.HomepageComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.config.web.HomepageComponentTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.HomepageComponentBO;

/**
 * Facade for the HomepageComponent
 */
public final class HomepageComponentFacade
    implements IFacade
{
    
    /**
     * Constructor
     */
    private HomepageComponentFacade()
    {
        
    }

    /**
     * Persistent provider
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * This method recover the list of all HomepageComponentDTO which are link to the user id give in arguments
     * 
     * @param userId id of the user link to the HomepageComponents
     * @return The list of hiomepageComponent link to the userId
     * @throws JrafEnterpriseException Exception occur during the search in the database
     */
    public static List<HomepageComponentDTO> findComponent( long userId )
        throws JrafEnterpriseException
    {
        ISession session = null;
        List<HomepageComponentDTO> listComponentDTO = null;

        try
        {
            session = PERSISTENTPROVIDER.getSession();
            HomepageComponentDAOImpl dao = HomepageComponentDAOImpl.getInstance();
            List<HomepageComponentBO> listComponentBO = (List<HomepageComponentBO>) dao.findByUserId( session, userId );
            listComponentDTO = HomepageComponentTransform.bo2dto( listComponentBO );

        }
        catch ( JrafPersistenceException e )
        {
            FacadeHelper.convertException( e, HomepageComponentFacade.class.getName() + ".findComponent" );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, HomepageComponentFacade.class.getName() + ".findComponent" );
        }
        finally
        {
            FacadeHelper.closeSession( session, HomepageComponentFacade.class.getName() + ".findComponent" );
        }
        return listComponentDTO;
    }

    /**
     * This method create or update the list of HomepageComponentDTO give in argument
     * 
     * @param componentList List of HomepageComponentDTO to save
     * @throws JrafEnterpriseException Exception happen during the work in the database
     */
    public static void saveOrUpdate( List<HomepageComponentDTO> componentList )
        throws JrafEnterpriseException
    {
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            HomepageComponentDAOImpl dao = HomepageComponentDAOImpl.getInstance();
            HomepageComponentBO componentBO;
            
            // We create or update each HomepageComponent of the list
            for ( HomepageComponentDTO componentDTO : componentList )
            {
                componentBO = HomepageComponentTransform.dto2bo( componentDTO );
                dao.save( session, componentBO );
            }

        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, HomepageComponentFacade.class.getName() + ".saveOrUpdate" );
        }
        finally
        {
            FacadeHelper.closeSession( session, HomepageComponentFacade.class.getName() + ".saveOrUpdate" );
        }
    }
}
