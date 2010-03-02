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
package org.squale.squalecommon.enterpriselayer.facade.config;

import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.enterpriselayer.IFacade;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.config.SqualeParamsDAOImpl;
import org.squale.squalecommon.datatransfertobject.config.SqualeParamsDTO;
import org.squale.squalecommon.datatransfertobject.transform.config.SqualeParamsTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.config.SqualeParamsBO;

/**
 * This class represents the facade for the {@link SqualeParamsBO}
 */
public final class SqualeParamsFacade
    implements IFacade
{

    /**
     * Private constructor
     */
    private SqualeParamsFacade()
    {

    }

    /**
     * <p>
     * This method searches the SqualeParamsBO which has the paramKey given in argument
     * </p>
     * <p>
     * <ul>
     * <li>If a parameter is found, then this method returns it</li>
     * <li>If not, this method returns null</li>
     * </ul>
     * </p>
     * 
     * @param paramKey The key of the parameter that needs to be found
     * @param session The hibernate session
     * @return This method returns the SqualeParamsDTO linked to the paramKey or null if no SqualeParamsBO is found
     * @throws JrafEnterpriseException This exception could be launched if :
     *             <ul>
     *             <li>Many SqualeParams were found for the paramKey given in arguments</li>
     *             <li>A problem with the database occurs</li>
     *             </ul>
     */
    public static SqualeParamsDTO getSqualeParams( String paramKey, ISession session )
        throws JrafEnterpriseException
    {
        SqualeParamsDTO dtoFound = null;
        try
        {
            SqualeParamsDAOImpl dao = SqualeParamsDAOImpl.getInstance();
            List<SqualeParamsBO> resultList = dao.findByKey( session, paramKey );
            if ( resultList.size() > 1 )
            {
                throw new JrafEnterpriseException( "Many SqualeParams have been found for the paramKey : " + paramKey );
            }
            else
            {
                if ( resultList.size() == 1 )
                {
                    SqualeParamsBO boFound = resultList.get( 0 );
                    dtoFound = SqualeParamsTransform.bo2dto( boFound );
                }
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, "getSqualeParams" );
        }
        finally
        {
            FacadeHelper.closeSession( session, "getSqualeParams" );
        }
        return dtoFound;
    }

    /**
     * <p>
     * This method create or update the squaleParamsDto given in argument
     * </p>
     * <p>
     * First the method search the squaleParams in the database. If several results have been found then the method
     * launch an exception. Else the squaleParams is create or update
     * </p>
     * 
     * @param squaleParamDto The squaleParams to create or update
     * @param session The hibernate session
     * @throws JrafEnterpriseException This exception could be launched if :
     *             <ul>
     *             <li>Many SqualeParams were found for the paramKey given in arguments</li>
     *             <li>A problem with the database occurs</li>
     *             </ul>
     */
    public static void createOrUpdate( SqualeParamsDTO squaleParamDto, ISession session )
        throws JrafEnterpriseException
    {
        SqualeParamsBO squaleParamsBo = SqualeParamsTransform.dto2bo( squaleParamDto );
        SqualeParamsDAOImpl dao = SqualeParamsDAOImpl.getInstance();
        try
        {
            // We search record in the database with the same paramKey
            List<SqualeParamsBO> resultFind = dao.findByKey( session, squaleParamsBo.getParamKey() );
            if ( resultFind.size() > 1 )
            {
                // only one record with the same paramKey should exist
                throw new JrafEnterpriseException( "Several squaleParams have been found for teh paramKey : "
                    + squaleParamsBo.getParamKey() );
            }
            else
            {
                if ( resultFind.size() == 1 )
                {
                    // If one record already exist we update it
                    squaleParamsBo = (SqualeParamsBO) resultFind.get( 0 );
                    squaleParamsBo.setParamValue( squaleParamDto.getParamValue() );
                }
                dao.save( session, squaleParamsBo );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, "createOrUpdate" );
        }
        finally
        {
            FacadeHelper.closeSession( session, "createOrUpdate" );
        }
    }
}
