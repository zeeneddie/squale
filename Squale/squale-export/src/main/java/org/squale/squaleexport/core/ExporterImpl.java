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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.sharedrepository.export.ApplicationEx;
import org.squale.sharedrepository.export.AuditEx;
import org.squale.sharedrepository.export.CompanyEx;
import org.squale.sharedrepository.export.ComponentEx;
import org.squale.sharedrepository.export.DataEx;
import org.squale.sharedrepository.export.ExporterEx;
import org.squale.sharedrepository.export.ModuleEx;
import org.squale.sharedrepository.segment.SegmentEx;
import org.squale.squalecommon.datatransfertobject.config.AdminParamsDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComplexComponentBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.AdminParamsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.MetricBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.QualityResultBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBO;
import org.squale.squaleexport.daolayer.AbstractComponentDAOImplEx;
import org.squale.squaleexport.daolayer.ApplicationDAOImplEx;
import org.squale.squaleexport.daolayer.AuditDAOImplEx;
import org.squale.squaleexport.daolayer.MetricDAOImplEx;
import org.squale.squaleexport.daolayer.QualityResultDAOImplEx;
import org.squale.squaleexport.exception.ExportException;
import org.squale.squaleexport.message.ExportMessages;
import org.squale.squaleexport.model.Model;
import org.squale.squalemodel.definition.DataType;
import org.squale.squalemodel.definition.ElementType;
import org.squale.squalemodel.definition.InfoApplication;
import org.squale.squalemodel.definition.Language;
import org.squale.squalemodel.definition.Metric;

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
    private static final int BUFFER = 2048;

    /** Log */
    private static Log LOG = LogFactory.getLog( ExporterImpl.class );

    /** Persistence provider */
    private IPersistenceProvider persistenceProvider;

    /** Hibernate session */
    private ISession session;

    /** workspace path */
    private String workspaceDirectoryPath;

    /** Temporary diretory */
    private String tempDirectoryPath;

    /**
     * This map contains one map for each level of component ( project / class / method / ... ). Each "level" map
     * contains the mapping : metric generic name <==> tool name.metric name used in the Squale client
     */
    private HashMap<Language, HashMap<ElementType, ArrayList<Metric>>> mapLanguageComponentMetric;

    /**
     * Map adminParamsBO <=> value based on the adminParamsBO from squale
     */
    private HashMap<String, String> adminParamBoFromSquale;

    /**
     * This map contains one map for each metric recover. Each "metric" map link component id (key ) to the metric value
     * for the component.
     */
    private Map<String, Map<Long, Object>> resultsByMetricForAudit;

    /**
     * List of all the component of the application involved in the last successful audit
     */
    private List<Long> allComponentIdInvolvedInAudit;

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
                List<String> exportFileList = new ArrayList<String>();
                // For each application to export we create an export file
                while ( applicationIterator.hasNext() )
                {
                    Long applicationId = applicationIterator.next();
                    Long auditId = mapAppAuditToExport.get( applicationId );
                    exportFileList.add( exportApplication( applicationId, auditId ));
                }
                // We put all the export file in a zip and we delete the old zip file
                zipExportFiles( exportFileList );
                exportSucessful = true;
            }
        }
        catch ( ExportException e )
        {
            exportSucessful = false;
            LOG.error( ExportMessages.getString( "export.failed" ), e );
        }
        return exportSucessful;
    }

    /**
     * This application do the export for application and audit given in argument
     * 
     * @param applicationId The id of the application to export 
     * @param auditId The id of the audit to export 
     * @return The path of the export file 
     * @throws ExportException exception occurs during the application export
     */
    private String exportApplication(Long applicationId, Long auditId) throws ExportException
    {
        String filePath = null;
        try
        {
            try
            {
                session = persistenceProvider.getSession();
                // First we create the companyEx object
                CompanyEx company = createCompany();
                // We create the applicationEx component

                ApplicationEx application = createBasicApplication( applicationId, auditId );

                // Fill the basic application
                fillApplication( applicationId, auditId, application );

                // We add the application to the companyEx object
                company.setApplication( application );
                // We create the ExporterEx element

                Properties prop = new Properties();
                InputStream is = getClass().getResourceAsStream( "/squale-export.properties" );
                try
                {
                    prop.load( is );
                }
                catch ( IOException e )
                {
                    throw new ExportException(
                                               ExportMessages.getString( "squale.export.properties.notfound" ),
                                               e );
                }

                String squaleExportVersion = prop.get( "squale.export.version" ).toString();

                ExporterEx exporter = new ExporterEx( squaleExportVersion, company );
                // We create the export file
                filePath = createFile( applicationId, exporter );
                //exportFileList.add( filePath );
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
        return filePath;
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
                        ModuleEx modEx = createModule( component.getId(), auditId, language );
                        List<AbstractComponentBO> childCompo =
                            compoDao.searchChildrenByAuditAndParent( session, auditId, Long.valueOf( component.getId() ) );
                        for ( AbstractComponentBO abstractComponentBO : childCompo )
                        {
                            ComponentEx compo = createComponent( abstractComponentBO, compoDao, language );
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
            resultsByMetricForAudit = new HashMap<String, Map<Long, Object>>();
            MetricDAOImplEx metricDao = MetricDAOImplEx.getInstance();

            Map<Long, Object> valueMap = null;
            Set<Language> languageSet = mapLanguageComponentMetric.keySet();
            for ( Language language : languageSet )
            {
                HashMap<ElementType, ArrayList<Metric>> componentTypesForCurrentLanguage =
                    mapLanguageComponentMetric.get( language );
                Set<ElementType> componentTypeSet = componentTypesForCurrentLanguage.keySet();
                for ( ElementType componentType : componentTypeSet )
                {
                    ArrayList<Metric> metricListForLanguageAndType =
                        componentTypesForCurrentLanguage.get( componentType );
                    for ( Metric metric : metricListForLanguageAndType )
                    {
                        // The key for the metric in squale-config.xml file
                        String metricKey =
                            language.toString() + "/" + componentType.getXmlTag() + "/" + metric.getXmlTag();
                        metricKey = metricKey.toLowerCase();
                        valueMap = new HashMap<Long, Object>();
                        // We retrieve the good tool metric name and split it by "."
                        String metricFromTool = adminParamBoFromSquale.get( metricKey );
                        if ( metricFromTool != null )
                        {
                            String[] split = metricFromTool.split( "\\." );
                            // We recover the results
                            List<MetricBO> resultList =
                                metricDao.findMetricByMetricName( session, auditId, split[0], split[1] );
                            for ( MetricBO metricRes : resultList )
                            {
                                valueMap.put( metricRes.getMeasure().getComponent().getId(), metricRes.getValue() );
                            }
                            // 
                            resultsByMetricForAudit.put( metricKey, valueMap );
                        }
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
     * This method initialize the export It creates (if not already exists) the export workspace and use the
     * configuration from Squalix to initiate the export
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
            // String squaleHome = System.getProperty( "squale.home" );
            String dirPath = squaleHome + "/Squalix/export";
            File exportDir = new File( dirPath );
            workspaceDirectoryPath = exportDir.getCanonicalPath();
            exportDir.mkdir();
            dirPath = workspaceDirectoryPath + "/tmp";
            File tempDir = new File( dirPath );
            tempDirectoryPath = tempDir.getCanonicalPath();
            tempDir.mkdir();

            // Create a Map from the list of adminParamsDTO (given by Squalix)
            adminParamBoFromSquale = new HashMap<String, String>();
            for ( AdminParamsDTO adminParams : mappingListFromSquale )
            {
                String paramKey = adminParams.getParamKey();
                if ( paramKey.startsWith( AdminParamsBO.MAPPING ) )
                {
                    paramKey = paramKey.split( AdminParamsBO.MAPPING + "/" )[1];
                }
                adminParamBoFromSquale.put( paramKey.toLowerCase(), adminParams.getParamValue() );
            }

            // Retrieve the language mapping
            Model model = new Model();
            mapLanguageComponentMetric = model.getLanguageMapping();
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
        // The company Id
        String companyId = adminParamBoFromSquale.get( AdminParamsBO.ENTITY_ID.toLowerCase() );
        CompanyEx comp = new CompanyEx( companyId, new ArrayList<SegmentEx>(), new ArrayList<DataEx>() );
        return comp;
    }

    /**
     * This method create an ApplicationEx object.
     * 
     * @param applicationId The id of the application
     * @param auditId The id of the exported audit
     * @return An ApplicationEx object
     * @throws ExportException Exception occurs during the creation of the ApplicationEx
     */
    private ApplicationEx createBasicApplication( Long applicationId, Long auditId )
        throws ExportException
    {
        ApplicationEx appliToReturn = null;
        AuditEx audit = createAuditEx( auditId );
        List<DataEx> datas = recoverAppData( applicationId );
        List<SegmentEx> segments = recoverAppSegment( applicationId );
        appliToReturn =
            new ApplicationEx( Long.toString( applicationId ), segments, audit, datas, new ArrayList<ModuleEx>() );
        return appliToReturn;
    }

    /**
     * This method create the AuditEx element for the current export
     * 
     * @param auditId The id of the audit exported
     * @return The AuditEx corresponding to the current export
     * @throws ExportException Exception occurs during the search in the database
     */
    private AuditEx createAuditEx( Long auditId )
        throws ExportException
    {
        AuditEx audiEx = null;
        try
        {
            AuditDAOImplEx dao = AuditDAOImplEx.getInstance();
            AuditBO audit = (AuditBO) dao.get( session, auditId );
            audiEx = new AuditEx( audit.getRealDate(), "-1", auditId.toString() );
        }
        catch ( JrafDaoException e )
        {
            throw new ExportException( e );
        }
        return audiEx;
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
                    DataEx.createData( DataType.INFO.toString(), InfoApplication.IS_IN_INITIAL_PHASE.toString(),
                                       Boolean.toString( application.getInInitialDev() ) );
                datas.add( data );
                data =
                    DataEx.createData( DataType.INFO.toString(), InfoApplication.GLOBAL_COST.toString(),
                                       Integer.toString( application.getGlobalCost() ) );
                datas.add( data );
                data =
                    DataEx.createData( DataType.INFO.toString(), InfoApplication.DEV_COST.toString(),
                                       Integer.toString( application.getDevCost() ) );
                datas.add( data );
                data =
                    DataEx.createData( DataType.INFO.toString(),
                                       InfoApplication.QUALITY_APPROACH_AT_BEGINNING.toString(),
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
     * @param language The language
     * @param auditId The audit id
     * @return The moduleEx filled
     * @throws ExportException Exception occurs during the creation of the moduleEx
     */
    private ModuleEx createModule( Long moduleId, Long auditId, String language )
        throws ExportException
    {
        ModuleEx modul = null;
        List<DataEx> datas = recoverModData( moduleId, auditId, language );
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
     * @param language The module language
     * @return list of Data linked to the module
     * @throws ExportException Exception occurs during the serach of the data
     */
    private List<DataEx> recoverModData( Long moduleId, Long auditId, String language )
        throws ExportException
    {
        List<DataEx> datas = new ArrayList<DataEx>();
        QualityResultDAOImplEx resultDao = QualityResultDAOImplEx.getInstance();
        try
        {
            
            List<QualityResultBO> result = resultDao.findFactor( session, moduleId, auditId );
            datas.addAll( createDatas(DataType.FACTOR, result));
            
            result = resultDao.findCriterium( session, moduleId, auditId );
            datas.addAll( createDatas(DataType.CRITERIUM, result));

            Map<ElementType, ArrayList<Metric>> componentTypesForLanguage =
                mapLanguageComponentMetric.get( Language.valueOf( language.toUpperCase() ) );

            ArrayList<Metric> metricMapping4level = componentTypesForLanguage.get( ElementType.MODULE );
            if ( metricMapping4level != null && !metricMapping4level.isEmpty() )
            {
                for ( Metric metric : metricMapping4level )
                {
                    String metricKey =
                        language.toString() + "/" + ElementType.MODULE.getXmlTag() + "/" + metric.getXmlTag();
                    Map<Long, Object> metricMap = resultsByMetricForAudit.get( metricKey.toLowerCase() );
                    Object value = metricMap.get( moduleId );
                    if ( value != null )
                    {
                        DataEx data =
                            DataEx.createMetric( DataType.METRIC.toString(), metric.toString(), value.toString(),
                                                 language.toUpperCase() );
                        datas.add( data );
                    }
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
     * This method creates the list of data to export
     * 
     * @param dataType The dataType
     * @param result The list of result to export
     * @return The list of dataEx
     */
    private List<DataEx> createDatas( DataType dataType, List<QualityResultBO> result )
    {
        List<DataEx> datas = new ArrayList<DataEx>();
        for ( QualityResultBO qualityResultBO : result )
        {
            QualityRuleBO ruleBo = qualityResultBO.getRule();
            String ruleName = ruleBo.getName();
            DataEx data =
                DataEx.createData( dataType.toString(), ruleName,
                                   Float.toString( qualityResultBO.getMeanMark() ) );
            datas.add( data );
        }
        return datas;
    }

    /**
     * This method take the component BO and create recursively all the tree of component
     * 
     * @param compoBo The parent component BO
     * @param compoDao The dao
     * @param language The current language
     * @return The componentEx with all its children full filled
     */
    private ComponentEx createComponent( AbstractComponentBO compoBo, AbstractComponentDAOImplEx compoDao,
                                         String language )
    {
        // Type of the component (package, class, method)
        String compoType = compoBo.getType();
        String[] splitCompoType = compoType.split( "\\." );
        ElementType definedComponentType = ElementType.valueOf( splitCompoType[1].toUpperCase() );
        // Data for the component to create
        List<DataEx> datas = searchData4Compo( compoBo.getId(), definedComponentType, language );

        // Creation of the component
        ComponentEx compo =
            new ComponentEx( Long.toString( compoBo.getId() ), definedComponentType.toString(), language.toUpperCase(),
                             datas, new ArrayList<ComponentEx>() );

        if ( compoBo instanceof AbstractComplexComponentBO )
        {
            AbstractComplexComponentBO complexCompoBo = (AbstractComplexComponentBO) compoBo;
            List<AbstractComponentBO> childList = (List<AbstractComponentBO>) complexCompoBo.getChildren();
            for ( AbstractComponentBO abstractComponentBO : childList )
            {
                if ( allComponentIdInvolvedInAudit.contains( abstractComponentBO.getId() ) )
                {
                    ComponentEx childCompo = createComponent( abstractComponentBO, compoDao, language );
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
     * @param componentType The type of the component
     * @param language The language of the module
     * @return The list of component data to export
     */
    private List<DataEx> searchData4Compo( Long compoId, ElementType componentType, String language )
    {
        List<DataEx> datas = new ArrayList<DataEx>();
        Map<ElementType, ArrayList<Metric>> componentTypesForLanguage =
            mapLanguageComponentMetric.get( Language.valueOf( language.toUpperCase() ) );

        ArrayList<Metric> metricMapping4level = componentTypesForLanguage.get( componentType );
        if ( metricMapping4level != null && !metricMapping4level.isEmpty() )
        {
            for ( Metric metric : metricMapping4level )
            {
                String metricKey = language.toString() + "/" + componentType.getXmlTag() + "/" + metric.getXmlTag();
                Map<Long, Object> metricMap = resultsByMetricForAudit.get( metricKey.toLowerCase() );
                if ( metricMap != null )
                {
                    Object value = metricMap.get( compoId );
                    if ( value != null )
                    {
                        DataEx data =
                            DataEx.createMetric( DataType.METRIC.toString(), metric.toString(), value.toString(),
                                                 language.toUpperCase() );
                        datas.add( data );
                    }
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
     * @param exporter The ExporterEx object (fully filled)
     * @return The path of the file created
     * @throws ExportException Exception occurs during the creation of the application export file
     */
    private String createFile( Long appId, ExporterEx exporter )
        throws ExportException
    {
        String path = null;
        FileWriter writer = null;
        try
        {
            try
            {
                // The path of the file to create
                path = new File( tempDirectoryPath + File.separator + appId + ".xml" ).getCanonicalPath();
                writer = new FileWriter( path );
                // Creation of the xml file
                XStream xstream = new XStream();
                xstream.processAnnotations( ExporterEx.class );
                xstream.toXML( exporter, writer );
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
        return path;
    }

    /**
     * This method create the new zip file and delete the old zip file. This zip file contains an xml file for each
     * application exported
     * 
     * @param exportFileList List of file to insert in the zip file
     * @throws ExportException Errors occurs the creation of the zip file
     */
    private void zipExportFiles( List<String> exportFileList )
        throws ExportException
    {
        try
        {

            // The name of the new zip file
            Calendar cal = GregorianCalendar.getInstance();
            String zipName = "export" + cal.getTimeInMillis();
            File resultFileTmp = new File( tempDirectoryPath + File.separator + zipName + ".zip" );

            // Create the outputstrem of the zip (which will contain all the application export file)
            FileOutputStream dest = new FileOutputStream( resultFileTmp.getCanonicalPath() );
            byte[] data = new byte[BUFFER];
            ZipOutputStream out = null;
            try
            {
                out = new ZipOutputStream( new BufferedOutputStream( dest ) );
                BufferedInputStream origin = null;

                for ( String file : exportFileList )
                {
                    // We will add each xml export file to the zip
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
    private void cleaning( File resultFileTmp )
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
            File resultFile = new File( workspaceDirectoryPath + File.separator + resultFileTmp.getName() );
            resultFileTmp.renameTo( resultFile );
        }
        // We delete all the export file created (except the zip obviously)
        cleanExportFile();
    }

}
