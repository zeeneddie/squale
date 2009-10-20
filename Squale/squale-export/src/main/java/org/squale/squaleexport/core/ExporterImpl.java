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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
import org.squale.squalecommon.util.mail.IMailerProvider;
import org.squale.squaleexport.daolayer.AbstractComponentDAOImplEx;
import org.squale.squaleexport.daolayer.ApplicationDAOImplEx;
import org.squale.squaleexport.daolayer.AuditDAOImplEx;
import org.squale.squaleexport.daolayer.MetricDAOImplEx;
import org.squale.squaleexport.daolayer.QualityResultDAOImplEx;
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
 * For each application to export this class should :
 * <ul>
 * <li>Recover from the db the element (components and data) to export</li>
 * <li>Create all the objects needed to xstream to do the export</li>
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
    /** Status of the export */
    private boolean exportSuccessful;

    /** Buffer size (needed to create the zip) */
    static final int BUFFER = 2048;

    /** Persistence provider */
    private IPersistenceProvider persistenceProvider;

    /** Hibernate session */
    private ISession session;

    /** Mail provider */
    private IMailerProvider mailer;

    /** The current local */
    private Locale local;

    /** Id of the company */
    private String companyId;

    /** output path */
    private String outputDirectoryPath;

    /** Id of the last successful audit for the current application to export */
    private Long lastSuccessfulAuditId;

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
     * @param pMailer Mail provider
     * @param pLocal The current local
     */
    public ExporterImpl( IPersistenceProvider pPersistenceprovider, IMailerProvider pMailer, Locale pLocal )
    {

        persistenceProvider = pPersistenceprovider;
        mailer = pMailer;
        local = pLocal;
    }

    /**
     * {@inheritDoc}
     */
    public void exportData( List<Long> listApplicationId, List<AdminParamsDTO> mappingList )
    {
        exportSuccessful = false;

        // First we initiate the action.
        exportSuccessful = init( mappingList );

        if ( listApplicationId.size() > 0 )
        {

            // For each application to export we create an export file
            for ( Long applicationId : listApplicationId )
            {
                try
                {
                    session = persistenceProvider.getSession();
                    // First we create the companyEx object
                    CompanyEx company = createCompany();
                    // We create the applicationEx component
                    ApplicationEx application = createBasicApplication( applicationId );

                    // Fill the basic application
                    fillApplication( applicationId, application );

                    // We add the application to the companyEx object
                    company.addApplication( application );
                    // We create the export file
                    createFile( applicationId, company );
                }
                catch ( JrafPersistenceException e )
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finally
                {
                    if (session!=null)
                    {
                        try
                        {
                            session.closeSession();
                        }
                        catch ( JrafPersistenceException e )
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                
            }
            // We put all the export file in a zip
            zipExportFile();
            // We delete all the export file created (except the zip obviously)
            cleanExportFile();
        }
    }

    /**
     * The {@link ApplicationEx} given in argument has no {@link ModuleEx}. This method will create them and then add
     * them to the ApplicationEx
     * 
     * @param applicationId Id of the application to export
     * @param application The {@link ApplicationEx} object to fill
     */
    private void fillApplication( Long applicationId, ApplicationEx application )
    {
        AuditDAOImplEx auditDao = AuditDAOImplEx.getInstance();
        try
        {
            /*
             * We recover the last successful audit for the application. It's the results of this audit that we will
             * export. We don't care if the audit is a milestone or follow-up audit.
             */
            lastSuccessfulAuditId =
                Long.valueOf( ( auditDao.lastSuccesfullAudit( session, applicationId.longValue() ) ) );

            // If there is one successful audit
            if ( lastSuccessfulAuditId > -1 )
            {
                // We recover all the module implied in the audit
                AbstractComponentDAOImplEx compoDao = AbstractComponentDAOImplEx.getInstance();
                List<AbstractComponentBO> childrenModuleInvolvedInAudit =
                    compoDao.searchModuleByAudit( session, lastSuccessfulAuditId, applicationId );

                if ( childrenModuleInvolvedInAudit != null && !childrenModuleInvolvedInAudit.isEmpty() )
                {

                    // We recover and sort all the results for the current audit
                    recoverAllAuditResults();

                    // We recover all the components involved in the current audit
                    allComponentsInvolvedInAudit();

                    for ( AbstractComponentBO component : childrenModuleInvolvedInAudit )
                    {

                        ProjectBO module = (ProjectBO) component;
                        ProjectProfileBO profile = module.getProfile();
                        String language = profile.getLanguage();

                        // We create the moduleEx only if the module is a java module
                        if ( language.equals( Language.JAVA.toString().toLowerCase() ) )
                        {

                            ModuleEx modEx = createModule( component.getId() );
                            List<AbstractComponentBO> childCompo =
                                compoDao.searchChildrenByAuditAndParent( session, lastSuccessfulAuditId,
                                                                         Long.valueOf( component.getId() ) );
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
        }
        catch ( JrafDaoException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * This method recover
     * 
     * @throws JrafDaoException
     */
    private void allComponentsInvolvedInAudit()
        throws JrafDaoException
    {
        allComponentIdInvolvedInAudit = new ArrayList<Long>();
        AbstractComponentDAOImplEx compoDao = AbstractComponentDAOImplEx.getInstance();
        List<AbstractComponentBO> allCompo = compoDao.allComponentInvolvedInAudit( session, lastSuccessfulAuditId );
        for ( AbstractComponentBO compo : allCompo )
        {
            allComponentIdInvolvedInAudit.add( compo.getId() );
        }

    }

    /**
     * @throws JrafDaoException
     */
    private void recoverAllAuditResults()
        throws JrafDaoException
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
                List<MetricBO> resultList =
                    metricDao.findMetricByMetricName( session, lastSuccessfulAuditId, split[0], split[1] );
                for ( MetricBO metricRes : resultList )
                {
                    valueMap.put( metricRes.getMeasure().getComponent().getId(), metricRes.getValue() );
                }
                // 
                resultsByMetric.put( metric, valueMap );
            }
        }
    }

    /**
     * @return
     */
    private boolean init( List<AdminParamsDTO> mappingListFromSquale )
    {
        boolean isInitOk = false;
        HashMap<String, String> mappingMapFromSquale = new HashMap<String, String>();
        for ( AdminParamsDTO adminParams : mappingListFromSquale )
        {
            mappingMapFromSquale.put( adminParams.getParamKey(), adminParams.getParamValue() );
        }

        try
        {
            String tempDirPath = System.getProperty( "java.io.tmpdir" );
            File tempExportDir = new File( tempDirPath + File.separator + "expor_tmp" );
            outputDirectoryPath = tempExportDir.getCanonicalPath();
            tempExportDir.mkdir();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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

        return isInitOk;
    }

    /**
     * @return
     */
    private CompanyEx createCompany()
    {
        List<SegmentEx> segmentation = null;
        List<DataEx> datas = null;
        CompanyEx comp = new CompanyEx( companyId, segmentation, datas, new ArrayList<ApplicationEx>() );
        return comp;
    }

    /**
     * @param applicationId
     * @return
     */
    private ApplicationEx createBasicApplication( Long applicationId )
    {
        ApplicationEx appliToReturn = null;
        List<DataEx> datas = recoverAppData( applicationId );
        List<SegmentEx> segments = recoverAppSegment( applicationId );
        appliToReturn = new ApplicationEx( Long.toString( applicationId ), segments, datas, new ArrayList<ModuleEx>() );
        return appliToReturn;
    }

    /**
     * @param applicationId
     * @return
     */
    private List<SegmentEx> recoverAppSegment( Long applicationId )
    {
        List<SegmentEx> segments = new ArrayList<SegmentEx>();
        return segments;
    }

    /**
     * @param applicationId
     * @return
     */
    private List<DataEx> recoverAppData( Long applicationId )
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return datas;
    }

    /**
     * @param moduleId
     * @return
     */
    private ModuleEx createModule( Long moduleId )
    {
        ModuleEx modul = null;
        List<DataEx> datas = recoverModData( moduleId );
        List<SegmentEx> segments = recoverModSegment();
        modul = new ModuleEx( Long.toString( moduleId ), segments, datas, new ArrayList<ComponentEx>() );
        return modul;
    }

    /**
     * @return
     */
    private List<SegmentEx> recoverModSegment()
    {
        List<SegmentEx> segments = null;
        return segments;
    }

    /**
     * @param moduleId
     * @return
     */
    private List<DataEx> recoverModData( Long moduleId )
    {
        List<DataEx> datas = new ArrayList<DataEx>();
        QualityResultDAOImplEx resultDao = QualityResultDAOImplEx.getInstance();
        try
        {
            List<QualityResultBO> result = resultDao.findFactor( session, moduleId, lastSuccessfulAuditId );
            for ( QualityResultBO qualityResultBO : result )
            {
                QualityRuleBO ruleBo = qualityResultBO.getRule();
                String ruleName = ruleBo.getName();
                DataEx data =
                    new DataEx( DataType.FACTOR.toString(), ruleName, Float.toString( qualityResultBO.getMeanMark() ) );
                datas.add( data );
            }
            result = resultDao.findCriterium( session, moduleId, lastSuccessfulAuditId );
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return datas;
    }

    /**
     * @param compoBo
     * @param compoDao
     * @return
     * @throws JrafDaoException
     */
    private ComponentEx createComponent( AbstractComponentBO compoBo, AbstractComponentDAOImplEx compoDao )
        throws JrafDaoException
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
     * @param compoId
     * @param metricType
     * @return
     */
    private List<DataEx> searchData4Compo( Long compoId, String componentType )
    {
        List<DataEx> datas = new ArrayList<DataEx>();

        JavaComponentType definedComponentType = JavaComponentType.valueOf( componentType.toUpperCase() );

        Map<JavaMetric, String> metricMapping4level = metricMapping.get( definedComponentType );
        if ( metricMapping4level != null && !metricMapping4level.isEmpty() )
        {
            for ( JavaMetric metric : metricMapping4level.keySet() )
            {
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
     * @param appId
     * @param company
     */
    private void createFile( Long appId, CompanyEx company )
    {
        XStream xstream = new XStream();
        xstream.processAnnotations( CompanyEx.class );

        String path = null;
        FileWriter writer = null;
        try
        {
            path = new File( outputDirectoryPath + File.separator + appId + ".xml" ).getCanonicalPath();
            writer = new FileWriter( path );
            xstream.toXML( company, writer );
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            try
            {
                writer.close();
                exportFileList.add( path );
            }
            catch ( IOException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    /**
     * 
     */
    private void zipExportFile()
    {
        try
        {
            BufferedInputStream origin = null;
            Calendar cal = GregorianCalendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat( "yyyy_MM_dd" );
            String dateStr = format.format( cal.getTime() );
            String zipName = "export" + dateStr;
            String path = new File( outputDirectoryPath + File.separator + zipName + ".zip" ).getCanonicalPath();
            FileOutputStream dest = new FileOutputStream( path );
            ZipOutputStream out = new ZipOutputStream( new BufferedOutputStream( dest ) );
            // out.setMethod(ZipOutputStream.DEFLATED);
            byte data[] = new byte[BUFFER];
            // get a list of files from current directory
            // File f = new File( "." );
            // String files[] = f.list();

            for ( String file : exportFileList )
            {
                // System.out.println( "Adding: " + files[i] );
                String filename = zipName + File.separator + new File( file ).getName();
                FileInputStream fi = new FileInputStream( file );
                origin = new BufferedInputStream( fi, BUFFER );
                ZipEntry entry = new ZipEntry( filename );
                out.putNextEntry( entry );
                int count;
                while ( ( count = origin.read( data, 0, BUFFER ) ) != -1 )
                {
                    out.write( data, 0, count );
                }
                origin.close();
            }
            out.close();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

    }

    /**
     * 
     */
    private void cleanExportFile()
    {
        for ( String path : exportFileList )
        {
            File file = new File( path );
            file.delete();
        }
    }

}
