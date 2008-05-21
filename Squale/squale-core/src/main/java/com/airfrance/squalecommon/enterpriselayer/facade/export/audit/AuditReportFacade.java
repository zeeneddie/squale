package com.airfrance.squalecommon.enterpriselayer.facade.export.audit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditDisplayConfDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MarkDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MetricDAOImpl;
import com.airfrance.squalecommon.daolayer.result.QualityResultDAOImpl;
import com.airfrance.squalecommon.daolayer.result.rulechecking.RuleCheckingTransgressionItemDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.export.audit.CriteriumReportDTO;
import com.airfrance.squalecommon.datatransfertobject.export.audit.FactorReportDTO;
import com.airfrance.squalecommon.datatransfertobject.export.audit.PracticeReportDTO;
import com.airfrance.squalecommon.datatransfertobject.export.audit.PracticeReportDetailedDTO;
import com.airfrance.squalecommon.datatransfertobject.export.audit.ProjectReportDTO;
import com.airfrance.squalecommon.datatransfertobject.export.audit.QualityReportDTO;
import com.airfrance.squalecommon.datatransfertobject.result.QualityResultDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.AbstractFormulaDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.PracticeRuleDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.QualityRuleDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.ComponentTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.result.MeasureTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.result.QualityResultTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditDisplayConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.DisplayConfConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.VolumetryConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MarkBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.QualityResultBO;
import com.airfrance.squalecommon.enterpriselayer.facade.component.ComponentFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.quality.MeasureFacade;
import com.airfrance.squalecommon.util.ConstantRulesChecking;
import com.airfrance.squalecommon.util.mapping.Mapping;

/**
 * Results linked to generation of audit report
 */
public class AuditReportFacade
{

    /**
     * persistence provider
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Build list of projects for audit report
     * 
     * @param pAppliId application id
     * @param pCurAuditId current audit id
     * @param pPrevAuditId previous audit id
     * @param nbTop number of top to retrieve (maximum)
     * @param maxScore maximal score for score's components for top
     * @return list of project for audit report
     * @throws JrafEnterpriseException if error
     */
    public static List getProjectReports( Long pAppliId, Long pCurAuditId, Long pPrevAuditId, Integer nbTop,
                                          Float maxScore )
        throws JrafEnterpriseException
    {
        // Initialization
        // Method result
        List projectReports = new ArrayList();
        // DAO
        QualityResultDAOImpl resultDAO = QualityResultDAOImpl.getInstance();
        AbstractComponentDAOImpl componentDAO = AbstractComponentDAOImpl.getInstance();
        // Hibernate session
        ISession session = null;
        try
        {
            // Get session
            session = PERSISTENTPROVIDER.getSession();
            // Get projects for this application and this audit
            Collection projects = componentDAO.findChildrenWhere( session, pAppliId, pCurAuditId, null, null );
            // for each projet we get :
            // 1. factors results
            // 2. Number of lines
            // 3. volumetry measures
            // 4. scatterplot measures
            // 5. project name
            // 6. profile name
            for ( Iterator projectIt = projects.iterator(); projectIt.hasNext(); )
            {
                ProjectBO curProj = (ProjectBO) projectIt.next();
                ProjectReportDTO projectResult = new ProjectReportDTO();
                projectResult.setName( curProj.getName() );
                projectResult.setProfileName( curProj.getProfile().getName() );
                Long projectId = new Long( curProj.getId() );
                // set measures depend on audit configuration
                projectResult.setNbLines( getNumberOfLines( session, projectId, pCurAuditId, projectResult ) );
                // set project volumetry
                projectResult.setVolumetryMeasures( getProjectVol( session, projectId, pCurAuditId, projectResult ) );
                projectResult.setScatterplotMeasures( MeasureFacade.getProjectBubble( projectId, pCurAuditId ) );
                Collection factors = resultDAO.findWhere( session, projectId, pCurAuditId );
                // Transform bo to dto
                for ( Iterator factorsIt = factors.iterator(); factorsIt.hasNext(); )
                {
                    FactorReportDTO factorReport = buildFactorReport(session, (QualityResultBO)factorsIt.next(), projectId, pCurAuditId, pPrevAuditId, maxScore, nbTop);
                    setQualityReportPreviousScore( session, factorReport, new Long(factorReport.getRule().getId()), projectId, pPrevAuditId, resultDAO );
                    projectResult.addQualityResult( factorReport );
                }
                projectReports.add( projectResult );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, AuditReportFacade.class.getName() + ".getProjectReports" );
        }
        finally
        {
            FacadeHelper.closeSession( session, AuditReportFacade.class.getName() + ".getProjectReports" );
        }
        return projectReports;
    }

    /**
     * Build factor's results for audit report
     * 
     * @param session hibernate session
     * @param resultBO quality result to transform
     * @param projectId projec's id
     * @param pCurAuditId current audit id
     * @param pPrevAuditId previous audit id
     * @param nbTop number of top to retrieve (maximum)
     * @param maxScore maximal score for score's components for top
     * @return factor report
     * @throws JrafDaoException if hibernate error
     * @throws JrafEnterpriseException if jraf error
     */
    private static FactorReportDTO buildFactorReport( ISession session, QualityResultBO resultBO, Long projectId, Long pCurAuditId,
                                  Long pPrevAuditId, Float maxScore, Integer nbTop )
        throws JrafDaoException, JrafEnterpriseException
    {
        // DAO
        QualityResultDAOImpl resultDAO = QualityResultDAOImpl.getInstance();
        FactorReportDTO factorReport =
            new FactorReportDTO( (QualityResultDTO) QualityResultTransform.bo2Dto( resultBO ) );
        // Get criteria for this factor
        Collection criteria = getChildrenResults( session, new Long( factorReport.getRule().getId() ), projectId, pCurAuditId, resultDAO );
        if ( null != criteria )
        {
            for ( Iterator criteriaIt = criteria.iterator(); criteriaIt.hasNext(); )
            {
                CriteriumReportDTO criteriumReport =
                    new CriteriumReportDTO(
                                            (QualityResultDTO) QualityResultTransform.bo2Dto( (QualityResultBO) criteriaIt.next() ) );
                Long criteriumId = new Long( criteriumReport.getRule().getId() );
                // Get practices for this criterium
                Collection practices = getChildrenResults( session, criteriumId, projectId, pCurAuditId, resultDAO );
                if ( null != practices )
                {
                    for ( Iterator practicesIt = practices.iterator(); practicesIt.hasNext(); )
                    {
                        QualityResultBO practiceBO = (QualityResultBO) practicesIt.next();
                        PracticeReportDTO practiceReport =
                            new PracticeReportDTO( (QualityResultDTO) QualityResultTransform.bo2Dto( practiceBO ) );
                        Long practiceId = new Long( practiceReport.getRule().getId() );
                        // get Worst detailed components
                        if ( practiceReport.isRulechecking() )
                        {
                            // get worst transgressions (error - warning - info)
                            practiceReport.setWorstResults( getWorstTransgressions(
                                                                                    session,
                                                                                    projectId,
                                                                                    pCurAuditId,
                                                                                    nbTop.intValue(),
                                                                                    (PracticeRuleDTO) practiceReport.getRule(),
                                                                                    practiceBO.getRule().getName() ) );
                        }
                        else
                        {
                            // get worst components
                            practiceReport.setWorstResults( getWorstComponents( session, pCurAuditId, nbTop, maxScore,
                                                                                new Long( practiceBO.getId() ),
                                                                                new Long( practiceBO.getRule().getId() ) ) );
                        }
                        setQualityReportPreviousScore( session, practiceReport, practiceId, projectId, pPrevAuditId,
                                                       resultDAO );
                        criteriumReport.addResult( practiceReport );
                    }
                }
                setQualityReportPreviousScore( session, criteriumReport, criteriumId, projectId, pPrevAuditId,
                                               resultDAO );
                factorReport.addResult( criteriumReport );
            }
        }
        return factorReport;
    }
    
    /**
     * Build top for audit report
     * @param session hibernate session
     * @param curAuditId current audit id
     * @param nbTop number of top
     * @param maxScore highter score for components in top
     * @param practiceId id of the practice
     * @param ruleId rule of the id
     * @return list of components in top
     * @throws JrafEnterpriseException if error
     * @throws JrafDaoException if error
     */
    private static List getWorstComponents( ISession session, Long curAuditId, Integer nbTop, Float maxScore,
                                            Long practiceId, Long ruleId )
        throws JrafEnterpriseException, JrafDaoException
    {
        ArrayList worstComp = new ArrayList();
        MarkDAOImpl markDAO = MarkDAOImpl.getInstance();
        MeasureDAOImpl measureDAO = MeasureDAOImpl.getInstance();
        // Get metrics
        List treKeys = new ArrayList( ComponentFacade.getTREChildren( ruleId ) );
        Collection treClasses = MeasureFacade.getTREClasses( treKeys );
        // Get worst components for this practice
        Collection marks = markDAO.findWorstWhere( session, practiceId, maxScore.floatValue(), nbTop );
        for ( Iterator it = marks.iterator(); it.hasNext(); )
        {
            PracticeReportDetailedDTO detail = new PracticeReportDetailedDTO();
            MarkBO markBO = (MarkBO) it.next();
            detail.setComponent( ComponentTransform.bo2DtoWithFullName( markBO.getComponent() ) );
            detail.setScore( markBO.getValue() );
            // build metrics result
            Collection measures =
                measureDAO.findWhere( session, new Long( detail.getComponent().getID() ), curAuditId, treClasses );
            detail.setMetrics( MeasureTransform.bo2dtoForMetric( measures, treKeys ) );
            worstComp.add( detail );
        }
        return worstComp;
    }
    
    /**
     * Build transgression list for practice's top
     * 
     * @param pSession hibernate session
     * @param pProjectId project id
     * @param pAuditId audit id
     * @param nbTop nuber of top
     * @param rule rule of the practice
     * @param ruleName name of the rule
     * @return list of items for the top
     * @throws JrafDaoException if error
     */
    private static List getWorstTransgressions( ISession pSession, Long pProjectId, Long pAuditId, int nbTop,
                                                PracticeRuleDTO rule, String ruleName )
        throws JrafDaoException
    {
        int cpt = nbTop;
        RuleCheckingTransgressionItemDAOImpl itemDAO = RuleCheckingTransgressionItemDAOImpl.getInstance();
        // Get kind of measures for this formula
        AbstractFormulaDTO formula = rule.getFormula();
        // Get class name for each measure
        String[] measureKinds = new String[formula.getMeasureKinds().size()];
        formula.getMeasureKinds().toArray( measureKinds );
        String[] measures = new String[measureKinds.length];
        for ( int i = 0; i < measureKinds.length; i++ )
        {
            Class pTreClass = Mapping.getMeasureClass( measureKinds[i] + "." + formula.getComponentLevel() );
            measures[i] = pTreClass.getName();
        }
        ArrayList items = new ArrayList();
        Collection itemsFound = new ArrayList();
        // Order by severity (error then warning then info)
        for ( int i = 0; i < ConstantRulesChecking.SEVERITIES.length && cpt > 0; i++ )
        {
            itemsFound =
                itemDAO.findWhereMeasureClass( pSession, pProjectId.toString(), pAuditId.toString(), measures,
                                               ConstantRulesChecking.SEVERITIES[i], ruleName, new Integer( cpt ) );
            items.addAll( itemsFound );
            cpt -= itemsFound.size();
        }
        return items;
    }

    /**
     * Build volumetry of a project for an audit
     * 
     * @param session hibernate session
     * @param projectId project id
     * @param curAuditId audit id
     * @param projectResult information of a project for audit report
     * @return volumetry for this project
     * @throws JrafDaoException if error
     */
    private static Map getProjectVol( ISession session, Long projectId, Long curAuditId, ProjectReportDTO projectResult )
        throws JrafDaoException
    {
        // Initialization
        HashMap vol = new HashMap();
        MetricDAOImpl metricDAO = MetricDAOImpl.getInstance();

        // Get configuration for this project and this audit
        AuditDisplayConfBO auditConf =
            AuditDisplayConfDAOImpl.getInstance().findConfigurationWhere( session, projectId, curAuditId,
                                                                          DisplayConfConstants.VOLUMETRY_SUBCLASS,
                                                                          DisplayConfConstants.VOLUMETRY_PROJECT_TYPE );
        if ( null != auditConf )
        {
            VolumetryConfBO volumConf = (VolumetryConfBO) auditConf.getDisplayConf();
            // For each tre, get its value and add it in the map
            for ( Iterator it = volumConf.getTres().iterator(); it.hasNext(); )
            {
                String treName = (String) it.next();
                IntegerMetricBO metric =
                    metricDAO.findIntegerMetricWhere( session, projectId.longValue(), curAuditId.longValue(), treName );
                if ( null != metric )
                {
                    // Add result
                    vol.put( treName, metric.getValue() );
                }
            }
        }
        return vol;
    }

    /**
     * Count number of lines for a project
     * 
     * @param session hibernate session
     * @param projectId project id
     * @param curAuditId audit id
     * @param projectResult information of a project for audit report
     * @return number of lines
     * @throws JrafDaoException if error
     */
    private static int getNumberOfLines( ISession session, Long projectId, Long curAuditId,
                                         ProjectReportDTO projectResult )
        throws JrafDaoException
    {
        // Initialization
        int accLines = 0;
        MetricDAOImpl metricDAO = MetricDAOImpl.getInstance();
        // Get configuration
        AuditDisplayConfBO auditConf =
            (AuditDisplayConfBO) AuditDisplayConfDAOImpl.getInstance().findConfigurationWhere(
                                                                                               session,
                                                                                               projectId,
                                                                                               curAuditId,
                                                                                               DisplayConfConstants.VOLUMETRY_SUBCLASS,
                                                                                               DisplayConfConstants.VOLUMETRY_APPLICATION_TYPE );
        if ( null != auditConf )
        {
            VolumetryConfBO volumConf = (VolumetryConfBO) auditConf.getDisplayConf();
            // We add value of tres
            for ( Iterator it = volumConf.getTres().iterator(); it.hasNext(); )
            {
                IntegerMetricBO metric =
                    metricDAO.findIntegerMetricWhere( session, projectId.longValue(), curAuditId.longValue(),
                                                      (String) it.next() );
                if ( null != metric )
                {
                    accLines += ( (Integer) metric.getValue() ).intValue();
                }
            }
        }
        return accLines;
    }
    
    /**
     * Retrieve children results for a rule, a project and an audit
     * 
     * @param  session hibernate session
     * @param pRuleId id of rule
     * @param projectId project id
     * @param auditId audit id
     * @param resultDAO dao
     * @return children of this quality result
     * @throws JrafDaoException if error
     * @throws JrafEnterpriseException if error
     */
    private static Collection getChildrenResults( ISession session, Long pRuleId, Long projectId, Long auditId,
                                                  QualityResultDAOImpl resultDAO )
        throws JrafDaoException, JrafEnterpriseException
    {
        // Initialization
        Collection qualityResults = null;
        // Get TRE children
        Collection tres = ComponentFacade.getTREChildren( pRuleId );
        if ( null != tres )
        {
            // RuleDTO to Id
            ArrayList ruleIds = new ArrayList();
            for ( Iterator rulesIt = tres.iterator(); rulesIt.hasNext(); )
            {
                QualityRuleDTO ruleDTO = (QualityRuleDTO) rulesIt.next();
                ruleIds.add( new Long( ruleDTO.getId() ) );
            }
            // Get results
            qualityResults = resultDAO.findWhere( session, projectId, auditId, ruleIds );
        }
        return qualityResults;
    }
    
    /**
     * Set previous score for a quality result
     * 
     * @param session hibernate session
     * @param qualityReport a quality result
     * @param ruleId id of rule
     * @param projectId project id
     * @param pPrevAuditId previous audit id
     * @param resultDAO dao
     * @throws JrafDaoException if error
     */
    private static void setQualityReportPreviousScore( ISession session, QualityReportDTO qualityReport, Long ruleId,
                                                       Long projectId, Long pPrevAuditId, QualityResultDAOImpl resultDAO )
        throws JrafDaoException
    {
        if ( pPrevAuditId != null )
        {
            // We have to get previous score
            QualityResultBO prevResult = resultDAO.load( session, projectId, pPrevAuditId, ruleId );
            if ( prevResult != null )
            {
                qualityReport.setPreviousScore( prevResult.getMeanMark() );
            }
        }
    }
}
