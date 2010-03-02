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
package org.squale.squalecommon.enterpriselayer.applicationcomponent.administration.sharedrepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.datatransfertobject.sharedrepository.SharedRepoStatsDTO;
import org.squale.squalecommon.datatransfertobject.tag.TagDTO;
import org.squale.squalecommon.enterpriselayer.applicationcomponent.ACMessages;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.SharedRepoStatsBO;
import org.squale.squalecommon.enterpriselayer.facade.sharedrepository.SharedRepoStatsFacade;
import org.squale.squalemodel.definition.DataType;

/**
 * This class is the component access for all the works around the {@link SharedRepoStatsBO}
 */
public class SharedRepoStatsComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * UID
     */
    private static final long serialVersionUID = 2689954495029853110L;

    /**
     * log
     */
    private static final Log LOG = LogFactory.getLog( ReferenceImportComponentAccess.class );

    /**
     * Persistence provider
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * <p>
     * This method recovers the statistics for a given segmentation (which corresponding to the list of tag given in
     * argument)and data type.
     * </p>
     * <p>
     * This method return a map of statistics for one segmentation (the one given in argument). This map has for keys
     * data types and for values the list of statistics linked to the data type for the current segmentation.
     * </p>
     * 
     * @param tagList The list of tag which represents the segmentation
     * @param dataType The type of data
     * @return A map : data type <=> statistics linked to the data type for the segmentation given in argument
     * @throws JrafEnterpriseException Exception occurs during the retrieve operation
     */
    public Map<String, SharedRepoStatsDTO> retrieveStatsByDataType( List<TagDTO> tagList, DataType dataType )
        throws JrafEnterpriseException
    {
        ISession session;
        Map<String, SharedRepoStatsDTO> statsMap = new HashMap<String, SharedRepoStatsDTO>();
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            // we retrieve the all the statistics for the current segmentation
            List<SharedRepoStatsDTO> retrievedStatsList = SharedRepoStatsFacade.retrieveStats( session, tagList );
            // We create the map to return : data type <=> statistics linked to the data type
            for ( SharedRepoStatsDTO sharedRepoStatsDTO : retrievedStatsList )
            {
                if ( sharedRepoStatsDTO.getDataType().equals( dataType.toString() ) )
                {
                    statsMap.put( sharedRepoStatsDTO.getDataName(), sharedRepoStatsDTO );
                }
            }
        }
        catch ( JrafPersistenceException e )
        {
            String message = ACMessages.getString( "ac.exception.generic.retrieveHibernateSession" );
            LOG.error( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return statsMap;
    }
}
