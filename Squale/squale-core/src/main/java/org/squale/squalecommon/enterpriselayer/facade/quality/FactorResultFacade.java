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
package org.squale.squalecommon.enterpriselayer.facade.quality;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.enterpriselayer.IFacade;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.result.FactorResultDAOImpl;

/**
 * Facade used to get info about factors
 */
public final class FactorResultFacade
    implements IFacade
{

    /** log */
    private static Log LOG = LogFactory.getLog( FactorResultFacade.class );

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Returns the names of all existing factors registered in the database.
     * 
     * @param session The hibernate session
     * @return a list of String, which are the identifiers of the factors in the database
     * @throws JrafDaoException if the method fails to retrieve the factor names
     */
    public static List<String> findFactorNames()
        throws JrafEnterpriseException
    {
        ISession session = null;
        List<String> result = new ArrayList<String>();
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            result = FactorResultDAOImpl.getInstance().findFactorNames( session );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, FactorResultFacade.class.getName() + ".findFactorNames" );
        }
        finally
        {
            FacadeHelper.closeSession( session, FactorResultFacade.class.getName() + ".findFactorNames" );
        }

        return result;
    }

    /**
     * Returns raw data that will be used by the Motion Chart. <br>
     * 
     * @param applicationId the id of the application that must be displayed in the Motion Chart
     * @return a MotionChartApplicationData object that can be used to iterate through the data
     * @throws JrafEnterpriseException if the method fails to get the data
     */
    public static MotionChartApplicationFactorData findFactorsForMotionChart( long applicationId )
        throws JrafEnterpriseException
    {
        ISession session = null;
        List<Object[]> result = new ArrayList<Object[]>();
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            result = FactorResultDAOImpl.getInstance().findFactorsForMotionChart( session, applicationId );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, FactorResultFacade.class.getName() + ".findFactorsForMotionChart" );
        }
        finally
        {
            FacadeHelper.closeSession( session, FactorResultFacade.class.getName() + ".findFactorsForMotionChart" );
        }

        return new MotionChartApplicationFactorData( result );
    }

    /**
     * Convenience class to iterate through the results used for the Motion Chart
     */
    public static class MotionChartApplicationFactorData
    {
        private List<Object[]> applicationData;

        MotionChartApplicationFactorData( List<Object[]> data )
        {
            this.applicationData = data;
        }

        public Iterator<Object[]> iterator()
        {
            return applicationData.iterator();
        }

        public long getProjectId( Object[] currentData )
        {
            return (Long) currentData[0];
        }

        public String getProjectName( Object[] currentData )
        {
            return (String) currentData[1];
        }

        public long getAuditId( Object[] currentData )
        {
            return (Long) currentData[2];
        }

        public String getFactorName( Object[] currentData )
        {
            return (String) currentData[3];
        }

        public float getFactorValue( Object[] currentData )
        {
            return (Float) currentData[4];
        }
    }

}
