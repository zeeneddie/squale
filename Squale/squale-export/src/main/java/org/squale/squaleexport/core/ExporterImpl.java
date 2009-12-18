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
package org.squale.squaleexport.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.datatransfertobject.config.AdminParamsDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComplexComponentBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.AdminParamsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.MetricBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.QualityResultBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBO;
import org.squale.squaleexport.daolayer.AbstractComponentDAOImplEx;
import org.squale.squaleexport.daolayer.ApplicationDAOImplEx;
import org.squale.squaleexport.daolayer.MetricDAOImplEx;
import org.squale.squaleexport.daolayer.QualityResultDAOImplEx;
import org.squale.squaleexport.exception.ExportException;
import org.squale.squaleexport.message.ExportMessages;
import org.squale.squaleexport.object.ApplicationEx;
import org.squale.squaleexport.object.CompanyEx;
import org.squale.squaleexport.object.ComponentEx;
import org.squale.squaleexport.object.DataEx;
import org.squale.squaleexport.object.ModuleEx;
import org.squale.squaleexport.object.SegmentEx;
import org.squale.squaleexport.util.DataType;
import org.squale.squaleexport.util.InfoApplication;
import org.squale.squaleexport.util.JavaComponentType;
import org.squale.squaleexport.util.JavaMetric;
import org.squale.squaleexport.util.Language;

import com.thoughtworks.xstream.XStream;

/**
 * <p>
 * This class is one implementation of the {@link IExporter} Interface. The method {@link #exportData(List)} is the
 * entry point to do the export. This implementation use xstream to do the export.
 * </p>
 * <p>
 * For each application to export, this class should :
 * <ul>
 * <li>Recover from the db the element (components and data) to export</li>
 * <li>Create all the objects necessary for xstream to do the export</li>
 * <li>Call xstream to create the xml export file</li>
 * </ul>
 * </p>
 * <p>
 * Then it should create a zip file with all the xml export file
 * </p>
 */
class ExporterImpl
    implements IExporter
{

    /** Buffer size (needed to create the zip) */
    static final int BUFFER = 2048;

    /** Log */
    private static Log LOG;

    /** Persistence provider */
    private IPersistenceProvider persistenceProvider;

    /** Hibernate session */
    private ISession session;

    /** workspace path */
    private String workspaceDirectoryPath;

    /** Temporary diretory */
    private String tempDirectoryPath;

    /** Id of the company */
    private String companyId;

    /**
     * This map contains one map for each level of component ( project / class / method / ... ). Each "level" map
     * contains the mapping : metric generic name <==> tool name.metric name used in the Squale client
     */
    private Map<JavaComponentType, Map<JavaMetric, String>> metricMapping;

    /**
     * This map contains one map for each metric recover. Each "metric" map link component id (key ) to the metric value
     * for the component.
     */
    private Map<JavaMetric, Map<Long, Object>> resultsByMetric;

    /**
     * List of all the component of the application involved in the last successful audit
     */
    private List<Long> allComponentIdInvolvedInAudit;

    /**
     * List of all the xml file
     */
    private List<String> exportFileList = new ArrayList<String>();

    /**
     * Constructor
     * 
     * @param pPersistenceprovider Persistence provider
     */
    protected ExporterImpl( IPersistenceProvider pPersistenceprovider )
    {
        persistenceProvider = pPersistenceprovider;
    }

    /**
     * {@inheritDoc}
     */
    public boolean exportData( HashMap<Long, Long> mapAppAuditToExport, List<AdminParamsDTO> mappingList )
    {
        boolean exportSucessful = false;
        try
        {
            // First we initiate the action.
            init( mappingList );
            Iterator<Long> applicationIterator = mapAppAuditToExport.keySet().iterator();

            if ( applicationIterator.hasNext() )
            {
                // For each application to export we create an export file
                while ( applicationIterator.hasNext() )
                {
                    Long applicationId = applicationIterator.next();
                    try
                    {
                        try
                        {
                            session = persistenceProvider.getSession();
                            // First we create the companyEx object
                            CompanyEx company = createCompany();
                            // We create the applicationEx component
                            ApplicationEx application = createBasicApplication( applicationId );

                            // Fill the basic application
                            fillApplication( applicationId, mapAppAuditToExport.get( applicationId ), application );

                            // We add the application to the companyEx object
                            company.addApplication( application );
                            // We create the export file
                            createFile( applicationId, company );
                        }
                        finally
                        {
                            if ( session != null )
                            {
                                session.closeSession();
                            }
                        }
                    }
                    catch ( JrafPersistenceException e )
                    {
                        throw new ExportException( e );
                    }

                }
                // We put all the export file in a zip and we delete the old zip file
                zipExportFiles();
                exportSucessful=true;
            }
        }
        catch ( ExportException e )
        {
            exportSucessful=false;
            LOG.error( ExportMessages.getString( "export.failed" ), e );
        }
        return exportSucessful;
    }

    /**
     * The {@link ApplicationEx} given in argument has no {@link ModuleEx}. This method will create them and then add
     * them to the ApplicationEx
     * 
     * @param applicationId Id of the application to export
     * @param auditId Id of the audit to export
     * @param application The {@link ApplicationEx} object to fill
     * @throws ExportException Exception occurs during
     */
    private void fillApplication( Long applicationId, Long auditId, ApplicationEx application )
        throws ExportException
    {
        try
        {
            AbstractComponentDAOImplEx compoDao = AbstractComponentDAOImplEx.getInstance();
            // We recover all the module involved in the audit
            List<AbstractComponentBO> childrenModuleInvolvedInAudit =
                compoDao.searchModuleByAudit( session, auditId, applicationId );

            if ( childrenModuleInvolvedInAudit != null && !childrenModuleInvolvedInAudit.isEmpty() )
            {

                // We recover and sort all the results for the current audit
                recoverAllAuditResults( auditId );

                // We recover all the components involved in the current audit
                allComponentsInvolvedInAudit( auditId );

                for ( AbstractComponentBO component : childrenModuleInvolvedInAudit )
                {

                    ProjectBO module = (ProjectBO) component;
                    ProjectProfileBO profile = module.getProfile();
                    String language = profile.getLanguage();

                    // We create the moduleEx only if the module is a java module
                    if ( language.equals( Language.JAVA.toString().toLowerCase() ) )
                    {
                        ModuleEx modEx = createModule( component.getId(), auditId );
                        List<AbstractComponentBO> childCompo =
                            compoDao.searchChildrenByAuditAndParent( session, auditId, Long.valueOf( component.getId() ) );
                        for ( AbstractComponentBO abstractComponentBO : childCompo )
                        {
                            ComponentEx compo = createComponent( abstractComponentBO, compoDao );
                            modEx.addComponent( compo );
                        }
                        application.addModule( modEx );
                    }
                }
            }
        }
        catch ( JrafDaoException e )
        {
            throw new ExportException( e );
        }

    }

    /**
     * This method recover all the components involved in the audit.
     * 
     * @param auditId The audit id
     * @throws ExportException Errors occurs during the search of the components involved in the audit
     */
    private void allComponentsInvolvedInAudit( Long auditId )
        throws ExportException

    {
        try
        {
            allComponentIdInvolvedInAudit = new ArrayList<Long>();
            AbstractComponentDAOImplEx compoDao = AbstractComponentDAOImplEx.getInstance();
            List<AbstractComponentBO> allCompo = compoDao.allComponentInvolvedInAudit( session, auditId );
            for ( AbstractComponentBO compo : allCompo )
            {
                allComponentIdInvolvedInAudit.add( compo.getId() );
            }
        }
        catch ( JrafDaoException e )
        {
            throw new ExportException( e );
        }

    }

    /**
     * This method recover all the audit result link to audit given in argument
     * 
     * @param auditId The audit id
     * @throws ExportException Exception occurs during the search of the audit results
     */
    private void recoverAllAuditResults( Long auditId )
        throws ExportException

    {
        try
        {
            resultsByMetric = new HashMap<JavaMetric, Map<Long, Object>>();
            MetricDAOImplEx metricDao = MetricDAOImplEx.getInstance();
            Map<Long, Object> valueMap = null;
            // Recovery of the levels
            Set<JavaComponentType> levels = metricMapping.keySet();

            // For each level (project, class, method, ...)
            for ( JavaComponentType level : levels )
            {

                // For each metric we want to recover for the level
                Map<JavaMetric, String> metricMapping4level = metricMapping.get( level );
                for ( JavaMetric metric : metricMapping4level.keySet() )
                {
                    valueMap = new HashMap<Long, Object>();
                    String[] split = metricMapping4level.get( metric ).split( "\\." );
                    // We recover the results
                    List<MetricBO> resultList = metricDao.findMetricByMetricName( session, auditId, split[0], split[1] );
                    for ( MetricBO metricRes : resultList )
                    {
                        valueMap.put( metricRes.getMeasure().getComponent().getId(), metricRes.getValue() );
                    }
                    // 
                    resultsByMetric.put( metric, valueMap );
                }
            }
        }
        catch ( JrafDaoException e )
        {
            throw new ExportException( e );
        }
    }

    /**
     * This method initialize the export It creates (if not already exists) the export workspace and use the
     * configuration from Squalix to iniate the export
     * 
     * @param mappingListFromSquale The configuration from Squalix
     * @throws ExportException Exception occurs during the initialization of the export
     */
    private void init( List<AdminParamsDTO> mappingListFromSquale )
        throws ExportException
    {
        try
        {
            // Creation of the export workspace directory
            String squaleHome = System.getenv( "SQUALE_HOME" );
            String dirPath = squaleHome + "/Squalix/export";
            File exportDir = new File( dirPath );
            workspaceDirectoryPath = exportDir.getCanonicalPath();
            exportDir.mkdir();
            dirPath = workspaceDirectoryPath + "/tmp";
            File tempDir = new File( dirPath );
            tempDirectoryPath = tempDir.getCanonicalPath();
            tempDir.mkdir();

            // Create a Map from the list of adminParamsDTO (given by Squalix)
            HashMap<String, String> mappingMapFromSquale = new HashMap<String, String>();
            for ( AdminParamsDTO adminParams : mappingListFromSquale )
            {
                mappingMapFromSquale.put( adminParams.getParamKey(), adminParams.getParamValue() );
            }

            // The company Id
            companyId = mappingMapFromSquale.get( AdminParamsBO.ENTITY_ID );

            metricMapping = new HashMap<JavaComponentType, Map<JavaMetric, String>>();

            // Level project
            Map<JavaMetric, String> mapping = new HashMap<JavaMetric, String>();
            mapping.put( JavaMetric.LOC, mappingMapFromSquale.get( AdminParamsBO.MAPPING_JAVA_PROJECT_LOC ) );
            mapping.put( JavaMetric.NUMBER_OF_CLASSES,
                         mappingMapFromSquale.get( AdminParamsBO.MAPPING_JAVA_PROJECT_NB_CLASSES ) );
            metricMapping.put( JavaComponentType.PROJECT, mapping );

            // Level class
            mapping = new HashMap<JavaMetric, String>();
            mapping.put( JavaMetric.LOC, mappingMapFromSquale.get( AdminParamsBO.MAPPING_JAVA_CLASS_LOC ) );
            mapping.put( JavaMetric.NUMBER_OF_METHODS,
                         mappingMapFromSquale.get( AdminParamsBO.MAPPING_JAVA_CLASS_NB_METHODS ) );
            metricMapping.put( JavaComponentType.CLASS, mapping );

            // Level method
            mapping = new HashMap<JavaMetric, String>();
            mapping.put( JavaMetric.LOC, mappingMapFromSquale.get( AdminParamsBO.MAPPING_JAVA_METHOD_LOC ) );
            mapping.put( JavaMetric.VG, mappingMapFromSquale.get( AdminParamsBO.MAPPING_JAVA_METHOD_VG ) );
            metricMapping.put( JavaComponentType.METHOD, mapping );
        }
        catch ( IOException e )
        {
            throw new ExportException( e );
        }
    }

    /**
     * This method create an object CompanyEx which has only its name attribute completed
     * 
     * @return A basic CompanyEx object
     */
    private CompanyEx createCompany()
    {
        List<SegmentEx> segmentation = null;
        List<DataEx> datas = null;
        CompanyEx comp = new CompanyEx( companyId, segmentation, datas, new ArrayList<ApplicationEx>() );
        return comp;
    }

    /**
     * This method create an ApplicationEx object.
     * 
     * @param applicationId The id of the application
     * @return An ApplicationEx object
     * @throws ExportException Exception occurs during the creation of the ApplicationEx
     */
    private ApplicationEx createBasicApplication( Long applicationId )
        throws ExportException
    {
        ApplicationEx appliToReturn = null;
        List<DataEx> datas = recoverAppData( applicationId );
        List<SegmentEx> segments = recoverAppSegment( applicationId );
        appliToReturn = new ApplicationEx( Long.toString( applicationId ), segments, datas, new ArrayList<ModuleEx>() );
        return appliToReturn;
    }

    /**
     * This method recover all the segment linked
     * 
     * @param applicationId The id of the application
     * @return The list of segments linked to the the application
     */
    private List<SegmentEx> recoverAppSegment( Long applicationId )
    {
        List<SegmentEx> segments = new ArrayList<SegmentEx>();
        // TODO : To be completed when the segment will be implements
        return segments;
    }

    /**
     * This method recover the application data
     * 
     * @param applicationId The application id
     * @return The list of dataEx linked to the application given in argument
     * @throws ExportException Exception occurs during the search of the data
     */
    private List<DataEx> recoverAppData( Long applicationId )
        throws ExportException
    {
        ApplicationDAOImplEx appliDao = ApplicationDAOImplEx.getInstance();
        List<DataEx> datas = null;
        try
        {
            ApplicationBO application = (ApplicationBO) appliDao.load( session, applicationId );
            if ( application != null )
            {
                datas = new ArrayList<DataEx>();
                DataEx data =
                    new DataEx( DataType.INFO.toString(), InfoApplication.IS_IN_INITIAL_PHASE.toString(),
                                Boolean.toString( application.getInInitialDev() ) );
                datas.add( data );
                data =
                    new DataEx( DataType.INFO.toString(), InfoApplication.GLOBAL_COST.toString(),
                                Integer.toString( application.getGlobalCost() ) );
                datas.add( data );
                data =
                    new DataEx( DataType.INFO.toString(), InfoApplication.DEV_COST.toString(),
                                Integer.toString( application.getDevCost() ) );
                datas.add( data );
                data =
                    new DataEx( DataType.INFO.toString(), InfoApplication.QUALITY_APPROACH_AT_BEGINNING.toString(),
                                Boolean.toString( application.getQualityApproachOnStart() ) );
                datas.add( data );
            }

        }
        catch ( JrafDaoException e )
        {
            throw new ExportException( e );
        }
        return datas;
    }

    /**
     * This method create the moduleEx linked to the module given in argument with the data from the audit given in
     * argument
     * 
     * @param moduleId The module id
     * @param auditId The audit id
     * @return The moduleEx filled
     * @throws ExportException Exception occurs during the creation of the moduleEx
     */
    private ModuleEx createModule( Long moduleId, Long auditId )
        throws ExportException
    {
        ModuleEx modul = null;
        List<DataEx> datas = recoverModData( moduleId, auditId );
        List<SegmentEx> segments = recoverModSegment( moduleId );
        modul = new ModuleEx( Long.toString( moduleId ), segments, datas, new ArrayList<ComponentEx>() );
        return modul;
    }

    /**
     * This method recover the list of segment linked to the module given in argument
     * 
     * @param moduleId The module id
     * @return The list of segmentEx linked to the module given in argument
     */
    private List<SegmentEx> recoverModSegment( Long moduleId )
    {
        List<SegmentEx> segments = null;
        // TODO : To be completed when the segment will be implements
        return segments;
    }

    /**
     * This method create the list of dataEx linked to the module given in argument
     * 
     * @param moduleId The module id
     * @param auditId The id of the audit
     * @return list of Data linked to the module
     * @throws ExportException Exception occurs during the serach of the data
     */
    private List<DataEx> recoverModData( Long moduleId, Long auditId )
        throws ExportException
    {
        List<DataEx> datas = new ArrayList<DataEx>();
        QualityResultDAOImplEx resultDao = QualityResultDAOImplEx.getInstance();
        try
        {
            List<QualityResultBO> result = resultDao.findFactor( session, moduleId, auditId );
            for ( QualityResultBO qualityResultBO : result )
            {
                QualityRuleBO ruleBo = qualityResultBO.getRule();
                String ruleName = ruleBo.getName();
                DataEx data =
                    new DataEx( DataType.FACTOR.toString(), ruleName, Float.toString( qualityResultBO.getMeanMark() ) );
                datas.add( data );
            }
            result = resultDao.findCriterium( session, moduleId, auditId );
            for ( QualityResultBO qualityResultBO : result )
            {
                QualityRuleBO ruleBo = qualityResultBO.getRule();
                String ruleName = ruleBo.getName();
                DataEx data =
                    new DataEx( DataType.CRITERIUM.toString(), ruleName, Float.toString( qualityResultBO.getMeanMark() ) );
                datas.add( data );
            }

            Map<JavaMetric, String> metricMapping4level = metricMapping.get( JavaComponentType.PROJECT );
            if ( metricMapping4level != null && !metricMapping4level.isEmpty() )
            {
                for ( JavaMetric metric : metricMapping4level.keySet() )
                {
                    Map<Long, Object> metricMap = resultsByMetric.get( metric );
                    DataEx data =
                        new DataEx( DataType.METRIC.toString(), metric.toString(), metricMap.get( moduleId ).toString() );
                    datas.add( data );
                }
            }
        }
        catch ( JrafDaoException e )
        {
            throw new ExportException( e );
        }

        return datas;
    }

    /**
     * This method take the component BO and create recursively all the tree of component
     * 
     * @param compoBo The parent component BO
     * @param compoDao The dao
     * @return The componentEx with all its children full filled
     */
    private ComponentEx createComponent( AbstractComponentBO compoBo, AbstractComponentDAOImplEx compoDao )
    {
        // Type of the component (package, class, method)
        String compoType = compoBo.getType();
        String[] splitCompoType = compoType.split( "\\." );

        // Data for the component to create
        List<DataEx> datas = searchData4Compo( compoBo.getId(), splitCompoType[1] );

        // Creation of the component
        ComponentEx compo = new ComponentEx( splitCompoType[1], datas, new ArrayList<ComponentEx>() );

        if ( compoBo instanceof AbstractComplexComponentBO )
        {
            AbstractComplexComponentBO complexCompoBo = (AbstractComplexComponentBO) compoBo;
            List<AbstractComponentBO> childList = (List<AbstractComponentBO>) complexCompoBo.getChildren();
            for ( AbstractComponentBO abstractComponentBO : childList )
            {
                if ( allComponentIdInvolvedInAudit.contains( abstractComponentBO.getId() ) )
                {
                    ComponentEx childCompo = createComponent( abstractComponentBO, compoDao );
                    compo.addComponent( childCompo );
                }
            }
        }
        return compo;
    }

    /**
     * This method recover the data for the component given in argument
     * 
     * @param compoId The id of the component
     * @param componentType The type of the componet
     * @return The list of component data to export
     */
    private List<DataEx> searchData4Compo( Long compoId, String componentType )
    {
        List<DataEx> datas = new ArrayList<DataEx>();

        // we recover the type of the component
        JavaComponentType definedComponentType = JavaComponentType.valueOf( componentType.toUpperCase() );

        // The list of data/metric to recover according to component type
        Map<JavaMetric, String> metricMapping4level = metricMapping.get( definedComponentType );

        // If there is at least one data/metric to recover
        if ( metricMapping4level != null && !metricMapping4level.isEmpty() )
        {
            for ( JavaMetric metric : metricMapping4level.keySet() )
            {
                // For each data to recover, We create the DataEx element
                Map<Long, Object> metricMap = resultsByMetric.get( metric );
                Object value = metricMap.get( compoId );
                if ( value != null )
                {
                    DataEx data = new DataEx( DataType.METRIC.toString(), metric.toString(), value.toString() );
                    datas.add( data );
                }
            }
        }
        return datas;
    }

    /**
     * This method create the export file of the application given in argument. The CompanyEx object given in argument
     * contains all the information of the application to export
     * 
     * @param appId The application id
     * @param company The companyEx object (fully filled)
     * @throws ExportException Exception occurs during the creation of the application export file
     */
    private void createFile( Long appId, CompanyEx company )
        throws ExportException
    {
        XStream xstream = new XStream();
        xstream.processAnnotations( CompanyEx.class );

        String path = null;
        FileWriter writer = null;
        try
        {
            try
            {
                path = new File( tempDirectoryPath + File.separator + appId + ".xml" ).getCanonicalPath();
                writer = new FileWriter( path );
                xstream.toXML( company, writer );
            }
            finally
            {
                writer.close();

            }
        }
        catch ( IOException e )
        {
            throw new ExportException( e );
        }
        exportFileList.add( path );
    }

    /**
     * This method create the new zip file and delete the old zip file. This zip file contains an xml file for each
     * application exported
     * 
     * @throws ExportException Errors occurs the creation of the zip file
     */
    private void zipExportFiles()
        throws ExportException
    {
        try
        {

            // The name of the new zip file
            Calendar cal = GregorianCalendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat( "yyyy_MM_dd" );
            String dateStr = format.format( cal.getTime() );
            String zipName = "export" + dateStr;
            File resultFileTmp = new File( tempDirectoryPath + File.separator + zipName + ".zip" );

            // Creation of the zip (which contains all the application export file)
            FileOutputStream dest = new FileOutputStream( resultFileTmp.getCanonicalPath() );
            byte[] data = new byte[BUFFER];
            ZipOutputStream out = null;
            try
            {
                out = new ZipOutputStream( new BufferedOutputStream( dest ) );
                BufferedInputStream origin = null;
                for ( String file : exportFileList )
                {
                    String filename = zipName + File.separator + new File( file ).getName();
                    FileInputStream fi = new FileInputStream( file );
                    try
                    {
                        origin = new BufferedInputStream( fi, BUFFER );
                        ZipEntry entry = new ZipEntry( filename );
                        out.putNextEntry( entry );
                        int count;
                        while ( ( count = origin.read( data, 0, BUFFER ) ) != -1 )
                        {
                            out.write( data, 0, count );
                        }
                    }
                    finally
                    {
                        origin.close();
                    }
                }
            }
            finally
            {
                out.close();
            }
            cleaning( resultFileTmp );
        }
        catch ( IOException e )
        {
            throw new ExportException( e );
        }
    }

    /**
     * This method delete each application export file create during the export task
     */
    private void cleanExportFile()
    {
        File tempDirectory = new File( tempDirectoryPath );
        // Listing of the files of the temp export directory
        File[] listFile = tempDirectory.listFiles();
        // We delete each file of the temp export directory
        for ( int index = 0; index < listFile.length; index++ )
        {
            File file = listFile[index];
            file.delete();
        }
        // Finaly we delete the export temp directory
        tempDirectory.delete();
    }

    /**
     * This method delete the old zip file
     */
    private void cleanZipFile()
    {
        File workspaceDirectory = new File( workspaceDirectoryPath );

        File[] listFile = workspaceDirectory.listFiles();
        for ( int index = 0; index < listFile.length; index++ )
        {
            File file = listFile[index];
            if ( !file.isDirectory() )
            {
                file.delete();
            }
        }
    }

    /**
     * This method remove the previous zip file and put the new zip file at its place Then this method clean all the
     * temp files and directory created
     * 
     * @param resultFileTmp The new zip file
     */
    public void cleaning( File resultFileTmp )
    {

        /*
         * We verify that the new zip file exist. If yes we can delete the previous zip file and put the new one at its
         * place
         */
        if ( resultFileTmp.exists() )
        {
            // We delete the old zip file
            cleanZipFile();
            // We put the new one at its place
            File resultFile = new File( workspaceDirectoryPath + File.separator + resultFileTmp.getName());
            resultFileTmp.renameTo( resultFile );
        }
        // We delete all the export file created (except the zip obviously)
        cleanExportFile();
    }

}
