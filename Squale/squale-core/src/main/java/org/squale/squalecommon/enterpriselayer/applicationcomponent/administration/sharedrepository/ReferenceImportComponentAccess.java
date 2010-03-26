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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.commons.exception.JrafException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.sharedrepository.export.SegmentEx;
import org.squale.sharedrepository.segmentref.Label;
import org.squale.sharedrepository.segmentref.SegmentCategoryRef;
import org.squale.sharedrepository.segmentref.SegmentRef;
import org.squale.sharedrepository.segmentref.SegmentsReference;
import org.squale.sharedrepository.stat.ComponentStat;
import org.squale.sharedrepository.stat.DataStat;
import org.squale.sharedrepository.stat.ModuleStat;
import org.squale.sharedrepository.stat.RepositoryStat;
import org.squale.sharedrepository.stat.SegmentationStat;
import org.squale.sharedrepository.stat.Statistic;
import org.squale.squalecommon.datatransfertobject.config.SqualeParamsDTO;
import org.squale.squalecommon.datatransfertobject.message.MessageDTO;
import org.squale.squalecommon.datatransfertobject.sharedrepository.SegmentationDTO;
import org.squale.squalecommon.datatransfertobject.sharedrepository.SharedRepoStatsDTO;
import org.squale.squalecommon.datatransfertobject.sharedrepository.segment.SegmentCategoryDTO;
import org.squale.squalecommon.datatransfertobject.sharedrepository.segment.SegmentDTO;
import org.squale.squalecommon.enterpriselayer.applicationcomponent.ACMessages;
import org.squale.squalecommon.enterpriselayer.businessobject.config.SqualeParamsBO.SqualeParams;
import org.squale.squalecommon.enterpriselayer.facade.config.SqualeParamsFacade;
import org.squale.squalecommon.enterpriselayer.facade.message.MessageFacade;
import org.squale.squalecommon.enterpriselayer.facade.sharedrepository.SegmentationFacade;
import org.squale.squalecommon.enterpriselayer.facade.sharedrepository.SharedRepoStatsFacade;
import org.squale.squalecommon.enterpriselayer.facade.sharedrepository.segment.SegmentCategoryFacade;
import org.squale.squalecommon.enterpriselayer.facade.sharedrepository.segment.SegmentFacade;
import org.squale.squalemodel.definition.ElementType;
import org.squale.squalemodel.definition.StatisticType;

import com.thoughtworks.xstream.XStream;

/**
 * This class is the component access for all works around the reference import
 */
public class ReferenceImportComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * UID
     */
    private static final long serialVersionUID = 3489364847165011361L;

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
     * This method is the entry point to import a new reference file in Squale.
     * </p>
     * <p>
     * If the new reference xml file content in the stream given in argument is newer than the one already loaded, or if
     * there is no reference already load then this method returns the version of the new reference inserted and the new
     * xml file is loaded. <br/>
     * Else this method returns null and the reference version previously loaded is keeping.
     * </p>
     * 
     * @param is The stream to download
     * @param currentReferenceVersion The version of the current reference inserted (null if there is no reference
     *            already inserted)
     * @return the version number of the reference file if this one is newer than the one already loaded, else it
     *         returns null
     * @throws JrafEnterpriseException Exception occurs during the import
     */
    public Integer importReference( InputStream is, Integer currentReferenceVersion )
        throws JrafEnterpriseException
    {
        RepositoryStat reference = xmlToObj( is );
        Integer newRefrenceVersion = null;
        if ( currentReferenceVersion == null || currentReferenceVersion.intValue() < reference.getVersion() )
        {
            // Update the segment reference
            updateSegmentsReference( reference );
            // Remove previous stats reference
            removeReference();
            // Insert the new stat reference
            addReference( reference );
            // Update the reference version number
            updateVersion( reference.getVersion() );
            newRefrenceVersion = Integer.valueOf( reference.getVersion() );
        }
        return newRefrenceVersion;
    }

    /**
     * This method create or update the segments reference
     * 
     * @param reference the new reference
     * @throws JrafEnterpriseException Exception occurs during the update
     */
    private void updateSegmentsReference( RepositoryStat reference )
        throws JrafEnterpriseException
    {
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            HashMap<Long, Long> mapCategory = createMapCategory( session );
            SegmentsReference ref = reference.getSegmentReference();

            List<MessageDTO> messageList = new ArrayList<MessageDTO>();
            // for each segment category of the reference file
            for ( SegmentCategoryRef catRef : ref.getCategoryList() )
            {
                boolean newCategory = true;
                SegmentCategoryDTO category =
                    new SegmentCategoryDTO( catRef.getKeyName(), catRef.getCatId(), catRef.getType(),
                                            catRef.isDeprecated() );
                messageList.addAll( messageToAdd( category.getFullKey(), catRef.getLabelList() ) );
                // We search if the segment category already exist in the database. If yes the method updates it, else
                // the method creates it

                if ( mapCategory.containsKey( catRef.getCatId() ) )
                {
                    category.setTechnicalId( mapCategory.get( catRef.getCatId() ) );
                    newCategory = false;

                }
                // The method saves or updates the category
                SegmentCategoryFacade.saveOrUpdate( session, category );
                HashMap<Long, Long> mapSegment = new HashMap<Long, Long>();
                if ( !newCategory )
                {
                    mapSegment = createMapSegment( session, category );
                }

                // For each segment of the category the method tests if the segment already exist. If yes, we update it
                // else we create it
                List<SegmentDTO> segmentDtoToUpdate = new ArrayList<SegmentDTO>();
                for ( SegmentRef segRef : catRef.getSegmentList() )
                {
                    SegmentDTO seg =
                        new SegmentDTO( segRef.getKeyName(), segRef.getSegmentId(), segRef.isDeprecated(), category );
                    messageList.addAll( messageToAdd( seg.getFullKey(), segRef.getLabelList() ) );
                    if ( mapSegment.containsKey( segRef.getSegmentId() ) )
                    {
                        seg.setTechnicalId( mapSegment.get( segRef.getSegmentId() ) );
                    }
                    segmentDtoToUpdate.add( seg );
                }
                // The method execute the update
                SegmentFacade.saveOrUpdateForACategory( session, segmentDtoToUpdate );
            }
            MessageFacade.importSegmentMessage( session, messageList );
            session.commitTransaction();
        }
        catch ( JrafPersistenceException e )
        {
            String message = ACMessages.getString( "ac.exception.generic.retrieveHibernateSession" );
            LOG.error( message, e );
            throw new JrafEnterpriseException( message, e );
        }

    }

    /**
     * This methods creates a list of MessageDTO for each label of a category
     * 
     * @param keyName The key name
     * @param labelList The list of label ( one per language ) for the category
     * @return The list of messageDTO for the category
     * @throws JrafEnterpriseException Exception launched when labeList is empty
     */
    private List<MessageDTO> messageToAdd( String keyName, List<Label> labelList )
        throws JrafEnterpriseException
    {
        List<MessageDTO> messageList = new ArrayList<MessageDTO>();
        MessageDTO message;
        if ( labelList != null && labelList.size() > 0 )
        {
            for ( Label label : labelList )
            {
                message = new MessageDTO();
                message.setKey( keyName );
                message.setLang( label.getLang() );
                message.setText( label.getLabel() );
                messageList.add( message );
            }
        }
        else
        {
            String msg = ACMessages.getString( "ac.exception.reference.import.segment.nolabel" );
            LOG.error( msg );
            throw new JrafEnterpriseException( msg );

        }
        return messageList;
    }

    /**
     * This method create a map. Each entry of the map represents a segment category. The key is the identifier of the
     * category and the value is the technical id of the segment category
     * 
     * @param session The hibernate session
     * @return A map category identifier <=> category technical id
     * @throws JrafEnterpriseException Exception occurs during the creation of the map
     */
    private HashMap<Long, Long> createMapCategory( ISession session )
        throws JrafEnterpriseException
    {
        HashMap<Long, Long> mapCatIdentifierTechnicalId = new HashMap<Long, Long>();
        mapCatIdentifierTechnicalId = SegmentCategoryFacade.getAllIdentifier( session );
        return mapCatIdentifierTechnicalId;
    }

    /**
     * This method create a map. This map contains the list of segment for a category. Each entry of the map represents
     * a segment. The key is the identifier of the segment and the value is the technical id of the segment
     * 
     * @param session The hibernate session
     * @param category The parent category
     * @return The segment identifier <=> segment technical id for a category
     * @throws JrafEnterpriseException exception occurs during the search
     */
    private HashMap<Long, Long> createMapSegment( ISession session, SegmentCategoryDTO category )
        throws JrafEnterpriseException
    {
        HashMap<Long, Long> mapSegIdentifierTechnicalId = new HashMap<Long, Long>();
        mapSegIdentifierTechnicalId =
            SegmentFacade.getSegmentIdentifierTechnicalIdForACategory( session, category.getTechnicalId() );
        return mapSegIdentifierTechnicalId;
    }

    /**
     * This method parses an xml file (InpuStream) and 'transform' the data into java objects
     * 
     * @param is The stream which contains the xml file to parse
     * @return Return a tree object which represents the xml file
     * @throws JrafEnterpriseException Exception launch during the close of the InputStream
     */
    private RepositoryStat xmlToObj( InputStream is )
        throws JrafEnterpriseException

    {
        RepositoryStat reference = null;
        try
        {
            XStream xstream = new XStream();
            xstream.processAnnotations( RepositoryStat.class );
            reference = (RepositoryStat) xstream.fromXML( is );
            is.close();
        }
        catch ( IOException e )
        {
            String message = ACMessages.getString( "ac.exception.reference.import.fileinputstream" );
            throw new JrafEnterpriseException( message, e );
        }
        return reference;
    }

    /**
     * This method removes the reference already load in Squale
     * 
     * @throws JrafEnterpriseException A problem occurs during the work with the database
     */
    private void removeReference()
        throws JrafEnterpriseException
    {
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            /*
             * This method remove all the segmentation. By cascade the stats and the segmentation_tag linked to the
             * segmentation are also remove
             */
            SegmentationFacade.removeAll( session );
        }
        catch ( JrafPersistenceException e )
        {
            String message = ACMessages.getString( "ac.exception.generic.retrieveHibernateSession" );
            LOG.error( message, e );
            throw new JrafEnterpriseException( message, e );
        }

    }

    /**
     * This method insert a new reference in the database
     * 
     * @param reference The new reference to insert
     * @throws JrafEnterpriseException Exception occurs during the insert of the new reference
     */
    private void addReference( RepositoryStat reference )
        throws JrafEnterpriseException
    {
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            List<SegmentationStat> segmentationList = reference.getSegmentationList();
            for ( SegmentationStat segmentationStat : segmentationList )
            {
                // For each segmentation of the reference we create the segmentation in the database.
                SegmentationDTO segmentation = new SegmentationDTO();
                for ( SegmentEx segmentEx : segmentationStat.getSegmentIdList() )
                {
                    SegmentDTO segmentDto = new SegmentDTO( segmentEx.getSegmentId() );
                    segmentation.addSegment( segmentDto );
                }

                SegmentationFacade.create( session, segmentation );

                // For this segmentation we create the statistic at the module Level
                ModuleStat moduleStat = segmentationStat.getModule();
                String elementType = ElementType.MODULE.toString();

                List<SharedRepoStatsDTO> statsList = createStatList( moduleStat.getDatas(), elementType, segmentation );

                // For this segmentation we create the statistic at the component level (class, method, ...)
                List<ComponentStat> componentStatList = segmentationStat.getComponents();
                for ( ComponentStat componentStat : componentStatList )
                {
                    elementType = ElementType.valueOf( componentStat.getType() ).toString();
                    statsList.addAll( createStatList( componentStat.getDatas(), elementType, segmentation ) );
                }
                SharedRepoStatsFacade.saveAll( session, statsList );
            }
            session.commitTransaction();
        }
        catch ( JrafException e )
        {
            if ( session != null )
            {
                session.rollbackTransaction();
            }
            String message = ACMessages.getString( "ac.exception.generic.retrieveHibernateSession" );
            LOG.error( message, e );
            throw new JrafEnterpriseException( message, e );
        }
    }

    /**
     * This method create the list of statistic for one element type (module, class, method, ...)
     * 
     * @param dataList The list of statistic for the current element type
     * @param elementType The type of element
     * @param segmentation The current segmentation
     * @return a list of stats to add to the segmentation
     */
    private List<SharedRepoStatsDTO> createStatList( List<DataStat> dataList, String elementType,
                                                     SegmentationDTO segmentation )
    {
        List<SharedRepoStatsDTO> statsDtoList = new ArrayList<SharedRepoStatsDTO>();
        for ( DataStat dataStat : dataList )
        {
            // For each data type we create its stats list
            SharedRepoStatsDTO statDto =
                new SharedRepoStatsDTO( elementType, dataStat.getType(), dataStat.getGenericName(),
                                        dataStat.getLanguage(), segmentation );
            List<Statistic> statsList = dataStat.getStatList();
            for ( Statistic statistic : statsList )
            {
                addStat( statDto, statistic );
            }
            statsDtoList.add( statDto );
        }
        return statsDtoList;
    }

    /**
     * This method fills the statistic dto object given in argument with the statistic informations provide by the
     * statistic object The statistic object come from the parse of the xml reference file
     * 
     * @param statDto The statistic dto object to fill
     * @param statistic The statistic object which provide the statistic informations
     */
    private void addStat( SharedRepoStatsDTO statDto, Statistic statistic )
    {
        // According to the statistic type, the method fills the right fields in the statistics dto
        if ( statistic.getStatName().equals( StatisticType.MEAN.toString() ) )
        {
            float value = Float.parseFloat( statistic.getStatValue() );
            statDto.setMean( value );
        }
        else if ( statistic.getStatName().equals( StatisticType.MAX.toString() ) )
        {
            float value = Float.parseFloat( statistic.getStatValue() );
            statDto.setMax( value );
        }
        else if ( statistic.getStatName().equals( StatisticType.MIN.toString() ) )
        {
            float value = Float.parseFloat( statistic.getStatValue() );
            statDto.setMin( value );
        }
        else if ( statistic.getStatName().equals( StatisticType.DEVIATION.toString() ) )
        {
            float value = Float.parseFloat( statistic.getStatValue() );
            statDto.setDeviation( value );
        }
        else if ( statistic.getStatName().equals( StatisticType.ELEMENTS.toString() ) )
        {
            int value = Integer.parseInt( statistic.getStatValue() );
            statDto.setElements( value );
        }
    }

    /**
     * This method updates the reference version inserted in the database. The version parameters is updated with the
     * version number given in argument
     * 
     * @param version The new version
     * @throws JrafEnterpriseException Problem occurs with the database
     */
    private void updateVersion( int version )
        throws JrafEnterpriseException
    {
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            SqualeParamsDTO squaleParamDto = new SqualeParamsDTO();
            // The method tries to retrieve the squale params for the reference version
            squaleParamDto.setSqualeParam( SqualeParams.referenceVersion.toString(), Integer.toString( version ) );
            SqualeParamsFacade.createOrUpdate( squaleParamDto, session );
        }
        catch ( JrafPersistenceException e )
        {
            String message = ACMessages.getString( "ac.exception.generic.retrieveHibernateSession" );
            LOG.error( message, e );
            throw new JrafEnterpriseException( message, e );
        }

    }

    /**
     * This method returns the current version of the reference inserted.
     * 
     * @return the version number of the reference inserted. It returns null if there is no reference already inserted
     * @throws JrafEnterpriseException Problems occurs during the retrieve of the version number of the reference
     *             inserted
     */
    public Integer currentReferenceVersion()
        throws JrafEnterpriseException
    {
        Integer recordVersion = null;
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();

            SqualeParamsDTO dto =
                SqualeParamsFacade.getSqualeParams( SqualeParams.referenceVersion.toString(), session );
            if ( dto != null && dto.getParamValue() != null )
            {
                recordVersion = Integer.valueOf( dto.getParamValue() );
            }
        }
        catch ( JrafPersistenceException e )
        {
            String message = ACMessages.getString( "ac.exception.generic.retrieveHibernateSession" );
            LOG.error( message, e );
            throw new JrafEnterpriseException( message, e );
        }

        return recordVersion;
    }

}
