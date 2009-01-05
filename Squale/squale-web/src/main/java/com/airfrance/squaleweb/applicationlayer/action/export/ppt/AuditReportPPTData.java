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
package com.airfrance.squaleweb.applicationlayer.action.export.ppt;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hslf.model.MasterSheet;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.Table;
import org.apache.poi.hslf.model.TableCell;
import org.jfree.chart.JFreeChart;

import com.airfrance.squalecommon.datatransfertobject.export.audit.PracticeReportDTO;
import com.airfrance.squalecommon.datatransfertobject.export.audit.PracticeReportDetailedDTO;
import com.airfrance.squalecommon.datatransfertobject.export.audit.ProjectReportDTO;
import com.airfrance.squalecommon.datatransfertobject.export.audit.QualityReportDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionItemBO;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction;
import com.airfrance.squaleweb.applicationlayer.formbean.export.AuditReportParamForm;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;
import com.airfrance.squaleweb.util.graph.BubbleMaker;
import com.airfrance.squaleweb.util.graph.KiviatMaker;
import com.airfrance.squaleweb.util.graph.PieChartMaker;

/**
 * Data needed to create a return audit report
 */
public class AuditReportPPTData
    extends PPTData
{

    /** List of projects data needed to create ppt */
    protected List projectReports;
    
    /**
     * Logger
     */
    private static Log log = LogFactory.getLog( AuditReportPPTData.class );

    public AuditReportPPTData(HttpServletRequest pRequest, File prez, File model, File mapping, List pProjectReports ) throws PPTGeneratorException {
        projectReports = pProjectReports;
        this.request = pRequest;
        try
        {
        	setPresentation(new FileInputStream(prez));
        	setModel(new FileInputStream(model));
        	setMapping(new FileInputStream(mapping));
        } 
        catch ( FileNotFoundException e )
        {
            throw new PPTGeneratorException( e.getMessage() );
        }
   }
    /**
     * Constructor
     * 
     * @param pRequest request
     * @param params bean containing information
     * @param pProjectReports list of results
     * @throws PPTGeneratorException if error
     */
    public AuditReportPPTData( HttpServletRequest pRequest, AuditReportParamForm params, List pProjectReports )
        throws PPTGeneratorException
    {
        projectReports = pProjectReports;
        this.request = pRequest;

        if ( ! GraphicsEnvironment.isHeadless() ) {
        	log.warn("java.awt.headless is not activated on WAS JVM. Graphic generation will fail !");
        }
        
        try
        {
            setPresentation( params.getPresentation().getInputStream() );
            setModel( params.getModel().getInputStream() );
            setMapping( params.getMapping().getInputStream() );
        }
        catch ( FileNotFoundException e )
        {
            throw new PPTGeneratorException( e.getMessage() );
        }
        catch ( IOException e )
        {
            throw new PPTGeneratorException( e.getMessage() );
        }
    }

    /**
     * Add pie chart by profile
     * 
     * @param slideToSet slide to modify
     * @param where place to add shape
     * @throws IOException if error
     */
    public void setProfilePieChart( Slide slideToSet, Rectangle where )
        throws IOException
    {
    	log.info("AuditReturn - setProfilePieChart");
    	// Build volumetry map by profile
        HashMap profileVolMap = new HashMap();
        for ( int i = 0; i < projectReports.size(); i++ )
        {
        	log.info("AuditReturn - setProfilePieChart Project n°" + i);
            ProjectReportDTO curProject = (ProjectReportDTO) projectReports.get( i );
            String profileName = curProject.getProfileName();
            Long nbLinesByProfile = (Long) profileVolMap.get( profileName );
            if ( nbLinesByProfile == null )
            {
                nbLinesByProfile = new Long( 0 );
            }
            profileVolMap.put( profileName, new Long( curProject.getNbLines() + nbLinesByProfile.intValue() ) );
        }
        // Create profilePieChart
        PieChartMaker pieMaker = new PieChartMaker( null, null, null );
        pieMaker.setValues( profileVolMap );
        JFreeChart pieChart = pieMaker.getChart( new HashMap(), request );
        // Image of pieChart
        addJFreeChart( slideToSet, pieChart, where );
    	log.info("AuditReturn - setProfilePieChart done");
    }

    /**
     * Add pie chart for the application
     * 
     * @param slideToSet slide to modify
     * @param where place to add shape
     * @throws IOException if error
     */
    public void setPieChart( Slide slideToSet, Rectangle where )
        throws IOException
    {
    	log.info("AuditReturn - setPieChart");
        // Build volumetry map
        HashMap volMap = new HashMap();
        for ( int i = 0; i < projectReports.size(); i++ )
        {
        	log.info("AuditReturn - setPieChart Project n°" + i);
            ProjectReportDTO curProject = (ProjectReportDTO) projectReports.get( i );
            Long nbLinesByProfile = (Long) volMap.get( curProject.getName() );
            if ( nbLinesByProfile == null )
            {
                nbLinesByProfile = new Long( 0 );
            }
            volMap.put( curProject.getName(), new Long( curProject.getNbLines() + nbLinesByProfile.intValue() ) );
        }
        // Create profilePieChart
        PieChartMaker pieMaker = new PieChartMaker( null, null, null );
        pieMaker.setValues( volMap );
        JFreeChart pieChart = pieMaker.getChart( new HashMap(), request );
        // add graph in slide
        addJFreeChart( slideToSet, pieChart, where );
    	log.info("AuditReturn - setPieChart done");
    }

    /**
     * Add pie chart for the application
     * 
     * @param slideToSet slide to modify
     * @param where place to add shape
     * @throws IOException if error
     */
    public void setKiviatChart( Slide slideToSet, Rectangle where )
        throws IOException
    {
    	log.info("AuditReturn - setKiviatChart");
       // Build factors results map
        KiviatMaker kiviatMaker = new KiviatMaker();
        for ( int i = 0; i < projectReports.size(); i++ )
        {
        	log.info("AuditReturn - setKiviatChart Project n°" + i);
            ProjectReportDTO curProject = (ProjectReportDTO) projectReports.get( i );
            kiviatMaker.addValues( curProject.getName(), buildSortedMapForKiviat( curProject.getQualityResults() ),
                                   request );
        }
        JFreeChart kiviatChart = kiviatMaker.getChart();
        // add kiviat
        addJFreeChart( slideToSet, kiviatChart, where );
    	log.info("AuditReturn - setKiviatChart done");
    }

    /**
     * Build factors sorted map : key = factorName value = result for current audit
     * 
     * @param qualityResults quality results
     * @return the sorted map for kiviat graph
     */
    private SortedMap buildSortedMapForKiviat( List qualityResults )
    {
        SortedMap results = new TreeMap();
        for ( int i = 0; i < qualityResults.size(); i++ )
        {
            QualityReportDTO factor = (QualityReportDTO) qualityResults.get( i );
            results.put( factor.getRule().getName().replaceFirst( "rule\\.", "" ), new Float( factor.getMeanMark() ) );
        }
        return results;
    }

    /**
     * Add an array for the volumetry of projects by profile. The array contains: columns: {Profile, projectName1,
     * projectName2,..., projectNameN, Total, Comments} rows: {profile1, tre1OfProfile1, ..., treNOfProfileN, ...,
     * profileN, tre1OfprofileN, ..., treNOfProfileN}
     * 
     * @param slideToSet slide to modify
     * @param where place to add shape
     * @throws IOException if error
     * @throws PPTGeneratorException 
     */
    public void setProfileVolTab( Slide slideToSet, Rectangle where )
        throws IOException, PPTGeneratorException
    {
       	log.info("AuditReturn - setProfileVolTab");
       // Create a map of map of lists in order to create the volumetry profile array
        // key: profileName; value: mapTre
        // mapTre --> key: treName; value: List of String
        final int nbPredefinedCols = 2;
        final int nbCols = nbPredefinedCols + projectReports.size();
        TreeMap globalMap = new TreeMap();
        List titles = new ArrayList( nbCols );
        // col 1: profile
        titles.add( WebMessages.getString( request, "export.audit_report.set.volByProfile.profile_col.title" ) );
        for ( int i = 0; i < projectReports.size(); i++ )
        {
            ProjectReportDTO curProject = (ProjectReportDTO) projectReports.get( i );
        	log.info("AuditReturn - setProfileVolTab Project n°" + i + " : " + curProject.getName() );
            // add project name on titles line
            titles.add( curProject.getName() );
            TreeMap tresMap = (TreeMap) globalMap.get( curProject.getProfileName() );
            if ( null == tresMap )
            {
                tresMap = new TreeMap();
            }
            for ( Iterator tresIt = curProject.getVolumetryMeasures().keySet().iterator(); tresIt.hasNext(); )
            {
                String curTre = (String) tresIt.next();
            	log.debug("AuditReturn - setProfileVolTab Measure : " + curTre );
                // add line in tab for this tre
                ArrayList valuesForTre = (ArrayList) tresMap.get( curTre );
                if ( null == valuesForTre )
                {
                    valuesForTre = createEmptyList( projectReports.size() );
                }
                // set value for project cell for this tre
                valuesForTre.set( i, curProject.getVolumetryMeasures().get( curTre ).toString() );
                tresMap.put( curTre, valuesForTre );
            }
        	log.debug("AuditReturn - setProfileVolTab Measure iteration done "  );
            globalMap.put( curProject.getProfileName(), tresMap );
        }
        // add the two last titles
        titles.add( WebMessages.getString( request, "export.audit_report.set.volByProfile.total_col.title" ) );
        // create table with map
        // addPicture( slideToSet, htmlToImage( createVolByProfileTable( where, titles, globalMap ).toString() ), where
        // );
    	log.debug("AuditReturn - setProfileVolTab end processing "  );
        addHtmlPicture( slideToSet, createVolByProfileTable( where, titles, globalMap ).toString(), where.x, where.y );
       	log.info("AuditReturn - setProfileVolTab done");
    }

    /**
     * Create table of volumetry by profile
     * 
     * @param where place to add table
     * @param titles columns title
     * @param globalMap map getting informations to create table
     * @return table representing volumetry by profile
     */
    private StringBuffer createVolByProfileTable( Rectangle where, List titles, TreeMap globalMap )
    {
        StringBuffer tableHtml = new StringBuffer( "<html><body><table border='1'><tr>" );
        // first line: titles
        for ( int i = 0; i < titles.size(); i++ )
        {
            tableHtml.append( "<td>" + (String) titles.get( i ) + "</td>" );
        }
        tableHtml.append( "</tr>" );
        // volumetry by profile
        for ( Iterator profileIt = globalMap.keySet().iterator(); profileIt.hasNext(); )
        {
            String curProfile = (String) profileIt.next();
            // add cyan line for profile
            tableHtml.append( "<tr bgcolor=\"#00FFFF\">" );
            tableHtml.append( "<td colspan='" + titles.size() + "'>" + curProfile.toUpperCase() + "</td>" );
            tableHtml.append( "</tr>" );
            // get tres
            TreeMap curTres = (TreeMap) globalMap.get( curProfile );
            for ( Iterator tresIt = curTres.keySet().iterator(); tresIt.hasNext(); )
            {
                tableHtml.append( "<tr>" );
                String curTre = (String) tresIt.next();
                tableHtml.append( "<td>" + WebMessages.getString( request, curTre ) + "</td>" );
                // get values for this tre
                ArrayList treLine = (ArrayList) curTres.get( curTre );
                int total = 0;
                for ( int i = 0; i < treLine.size(); i++ )
                {
                    tableHtml.append( "<td>" + (String) treLine.get( i ) + "</td>" );
                    try
                    {
                        total += Integer.parseInt( (String) treLine.get( i ) );
                    }
                    catch ( NumberFormatException nfe )
                    {
                        // do nothing
                    }
                }
                tableHtml.append( "<td>" + total + "</td>" );
                tableHtml.append( "</tr>" );
            }
        }
        tableHtml.append( "</table></body></html>" );
        return tableHtml;
    }

    /**
     * Create a list of string which are initialized with an empty string
     * 
     * @param size size of list
     * @return a list of empty String
     */
    private ArrayList createEmptyList( int size )
    {
        ArrayList emptyList = new ArrayList( size );
        for ( int i = 0; i < size; i++ )
        {
            emptyList.add( "" );
        }
        return emptyList;
    }

    /**
     * Add array of results for the application in a slide
     * 
     * @param slideToSet slide to set
     * @param where place to add results
     * @throws IOException if error
     * @throws PPTGeneratorException 
     */
    public void setApplicationQualityResults( Slide slideToSet, Rectangle where )
        throws IOException, PPTGeneratorException
    {
       	log.info("AuditReturn - setApplicationQualityResults");
        // Create map for fill table
        TreeMap factorsMap = new TreeMap();
        for ( int i = 0; i < projectReports.size(); i++ )
        {
        	log.info("AuditReturn - setApplicationQualityResults Project n°" + i);
            ProjectReportDTO curProject = (ProjectReportDTO) projectReports.get( i );
            for ( int j = 0; j < curProject.getQualityResults().size(); j++ )
            {
                QualityReportDTO factor = (QualityReportDTO) curProject.getQualityResults().get( j );
                String factorName = WebMessages.getString( request, factor.getRule().getName() );
                TreeMap results = (TreeMap) factorsMap.get( factorName );
                if ( results == null )
                {
                    results = new TreeMap();
                }
                results.put( curProject.getName(), new Float[] { new Float( factor.getMeanMark() ),
                    new Float( factor.getPreviousScore() ) } );
                factorsMap.put( factorName, results );
            }
        }
        // create table and add it to the slide in function of the map previously created
        createApplicationResultsTable( slideToSet, where, factorsMap );
       	log.info("AuditReturn - setApplicationQualityResults done");
    }

    /**
     * Create and add the array of results for the application in a slide
     * 
     * @param slideToSet slide to set
     * @param where place to add results
     * @param factorsMap information about cells
     * @throws IOException if error
     * @throws PPTGeneratorException 
     */
    private void createApplicationResultsTable( Slide slideToSet, Rectangle where, TreeMap factorsMap )
        throws IOException, PPTGeneratorException
    {
        StringBuffer html = new StringBuffer( "<html><body><table border='1'>" );
        StringBuffer title = new StringBuffer( "<tr bgcolor=\"#00FFFF\">" );
        List resultsBuffers = new ArrayList();
        // title of first column
        title.append( "<td><b>" + WebMessages.getString( request, "component.project" ) + "</b></td>" );
        for ( Iterator it = factorsMap.keySet().iterator(); it.hasNext(); )
        {
            String factorName = (String) it.next();
            // factor title
            title.append( "<td><b>" + factorName + "</b></td>" );
            Map results = (TreeMap) factorsMap.get( factorName );
            int rowProj = 1;
            for ( Iterator projIt = results.keySet().iterator(); projIt.hasNext(); rowProj++ )
            {
                String projName = (String) projIt.next();
                StringBuffer projectResults = null;
                if ( rowProj > resultsBuffers.size() )
                {
                    projectResults = new StringBuffer( "<td>" + projName + "</td>" );
                    resultsBuffers.add( projectResults );
                }
                else
                {
                    projectResults = (StringBuffer) resultsBuffers.get( rowProj - 1 );
                }
                // project title
                // fill factor column
                appendFactorResultToProjectRow( projectResults, (Float[]) results.get( projName ) );
                projectResults.append( "</td>" );
            }
        }
        html.append( title + "</tr>" );
        for ( int i = 0; i < resultsBuffers.size(); i++ )
        {
            html.append( "<tr>" );
            html.append( (StringBuffer) resultsBuffers.get( i ) );
            html.append( "</tr>" );
        }
        html.append( "</table></body></html>" );
        addHtmlPicture( slideToSet, html.toString(), where.x, where.y );
    }

    /**
     * Append score, evolution and image representation to the buffer stands for results table.
     * 
     * @param projectResults buffer to complete
     * @param scores factor scores
     */
    private void appendFactorResultToProjectRow( StringBuffer projectResults, Float[] scores )
    {

        String score = SqualeWebActionUtils.formatFloat( scores[0] );
        // score
        projectResults.append( "<td>" );
        projectResults.append( score );
        if ( scores[0].floatValue() != -1 && scores[1].floatValue() != -1 )
        {
            // arrow
            URL arrowSrc =
                this.getClass().getResource(
                                             "/"
                                                 + SqualeWebActionUtils.getImageForTrend( scores[0].toString(),
                                                                                          scores[1].toString() ) );
            if ( arrowSrc != null )
            {
                projectResults.append( "<img src=\"" + arrowSrc.toString() + "\"></img>" );

            }
        }
        if ( scores[0].floatValue() != -1 )
        {
            // picto
            URL pictoSrc =
                this.getClass().getResource(
                                             "/"
                                                 + SqualeWebActionUtils.IMG[SqualeWebActionUtils.generatePicto( score )] );
            if ( pictoSrc != null )
            {
                projectResults.append( "<img src=\"" + pictoSrc.toString() + "\" border=\"0\"></img>" );
            }
        }

    }

    /**
     * Add slide for each project containing : - bubble graph - ratio
     * 
     * @param model model to use
     * @throws IOException is error while creating slide
     */
    public void addScatterplotSlides( MasterSheet model )
        throws IOException
    {
       	log.info("AuditReturn - addScatterplotSlides");
        // for each project we create a new slide containing:
        // scatterplot graph and ratio
        for ( int i = 0; i < projectReports.size(); i++ )
        {
           log.info("AuditReturn - addScatterplotSlides Project n°" + i);
           ProjectReportDTO curProject = (ProjectReportDTO) projectReports.get( i );
            if ( null != curProject.getScatterplotMeasures() )
            {
                // create slide
                Slide slide = getPresentation().createSlide();
                // set model
                slide.setMasterSheet( model );
                // set title
                addTitle( slide, WebMessages.getString( request,
                                                        "export.audit_report.presentation.add.slide.scatterplot.title" ) );

                // add bulleted list
                int sumOfLines = getTotalNbLines();
                if ( sumOfLines == 0 )
                {
                    sumOfLines = 1; // in order to not have a division by zero
                }
                final int firstBulletTextSize = 24;
                final int firstBulletX = 30;
                final int firstBulletY = 100;
                final int firstBulletWidth = 300;
                final int firstBulletHeight = 350;
                String textToAdd =
                    curProject.getName()
                        + "\r"
                        + (int)((double)( (double)curProject.getNbLines() / (double)sumOfLines )
                        * 100)
                        + "%\r"
                        + WebMessages.getString( request,
                                                 "export.audit_report.presentation.add.slide.scatterplot.bullet2" );
                addTextBox( slide, textToAdd, firstBulletTextSize,
                            new Rectangle( firstBulletX, firstBulletY, firstBulletWidth, firstBulletHeight ), true );

                // add footer comment
                final int footerSize = 14;
                final int footerX = 12;
                final int footerY = 480;
                final int footerWidth = 500;
                final int footerHeight = 30;
                textToAdd =
                    WebMessages.getString( request, "export.audit_report.presentation.add.slide.scatterplot.footer" );
                addTextBox( slide, textToAdd, footerSize, new Rectangle( footerX, footerY, footerWidth, footerHeight ),
                            false );

                // add bubble graph
                final int bubbleX = 280;
                final int bubbleY = 90;
                final int bubbleWidth = 415;
                final int bubbleHeight = 380;
                addJFreeChart( slide, getBubbleChart( curProject ), new Rectangle( bubbleX, bubbleY, bubbleWidth,
                                                                                   bubbleHeight ) );
            }
        }
       	log.info("AuditReturn - addScatterplotSlides done");
    }

    /**
     * Get buuble chart for a project PRECONDITION: bubble chart measures are not null
     * 
     * @param curProject project
     * @return bubble chart
     */
    private JFreeChart getBubbleChart( ProjectReportDTO curProject )
    {
        int indexInParam = 0;
        // create scatterplot
        BubbleMaker bubbleMaker =
            new BubbleMaker( request.getLocale(), (Long) curProject.getScatterplotMeasures()[indexInParam++],
                             (Long) curProject.getScatterplotMeasures()[indexInParam++] );
        bubbleMaker.addSerie( (String) curProject.getScatterplotMeasures()[indexInParam++],
                              (double[]) curProject.getScatterplotMeasures()[indexInParam++],
                              (double[]) curProject.getScatterplotMeasures()[indexInParam++],
                              (double[]) curProject.getScatterplotMeasures()[indexInParam++] );
        JFreeChart chartBubble =
            bubbleMaker.getChart( null, null, null, (double[]) curProject.getScatterplotMeasures()[indexInParam++],
                                  (double[]) curProject.getScatterplotMeasures()[indexInParam++],
                                  (double[]) curProject.getScatterplotMeasures()[indexInParam++],
                                  (String[]) curProject.getScatterplotMeasures()[indexInParam] );
        return chartBubble;
    }

    /**
     * Give number of lines in the application
     * 
     * @return number of lines in the application
     */
    private int getTotalNbLines()
    {
        int sum = 0;
        for ( int i = 0; i < projectReports.size(); i++ )
        {
            ProjectReportDTO curProject = (ProjectReportDTO) projectReports.get( i );
            sum += curProject.getNbLines();
        }
        return sum;
    }

    /**
     * Add all projects results For each project and each factor we add a slide containing an array of quality results
     * (>=0) for each practices add slide with the "ten" (can be change by a configuration key:
     * export.audit_report.nb_top) worst components (or transgression) for each practice worst component have a score
     * inferior to 2 (configurable with key: export.audit_report.max_score_top)
     * 
     * @param model model to applied
     * @throws IOException if error
     * @throws PPTGeneratorException 
     */
    public void addAllAuditResultsDetailed( MasterSheet model )
        throws IOException, PPTGeneratorException
    {
       	log.info("AuditReturn - addAllAuditResultsDetailed");
        final Rectangle pictAnchor = new Rectangle( 30, 150, 600, 200 );
        for ( int i = 0; i < projectReports.size(); i++ )
        {
            log.info("AuditReturn - addAllAuditResultsDetailed Project n°" + i);
            ProjectReportDTO curProject = (ProjectReportDTO) projectReports.get( i );
            List practiceTopId = new ArrayList();
            // iterate on its results
            for ( int f = 0; f < curProject.getQualityResults().size(); f++ )
            {
                Slide slide = createFactorResultsSlide( model, curProject.getName() );
                // build factor table
                int rowspan = 0;
                QualityReportDTO factor = (QualityReportDTO) curProject.getQualityResults().get( f );
                StringBuffer crietriaAndPracticeTable = new StringBuffer();
                for ( int c = 0; c < factor.getQualityResults().size(); c++ )
                {
                    QualityReportDTO criterium = (QualityReportDTO) factor.getQualityResults().get( c );
                    // Add criterium name and its score
                    if ( c > 0 )
                    {
                        crietriaAndPracticeTable.append( "<tr>" );
                    }
                    appendQualityResultsDetail( crietriaAndPracticeTable, criterium );
                    rowspan += criterium.getQualityResults().size();
                    for ( int p = 0; p < criterium.getQualityResults().size(); p++ )
                    {
                        QualityReportDTO practice = (QualityReportDTO) criterium.getQualityResults().get( p );
                        if ( p > 0 )
                        {
                            crietriaAndPracticeTable.append( "<tr>" );
                        }
                        appendQualityResultsDetail( crietriaAndPracticeTable, practice );
                        crietriaAndPracticeTable.append( "</tr>" );
                        // add slide for each practice
                        addTopForPractice( (PracticeReportDTO) criterium.getQualityResults().get( p ), model,
                                           practiceTopId );
                    }
                }

                // add table for this factor
                StringBuffer factorTable = new StringBuffer( "<html><body><table border='1'>" );
                // add titles
                appendFactorResultTitles( factorTable );
                factorTable.append( "<tr>" );
                // Add factor name and its score
                appendQualityResultsDetail( factorTable, factor, rowspan );
                // append criteria and practice table
                factorTable.append( crietriaAndPracticeTable );
                // add image to slide
                factorTable.append( "</td></table></tr></table></body></html>" );
                addPicture( slide, htmlToImage( factorTable.toString() ), pictAnchor );
                addTextBoxInFactorResultSlide( slide, WebMessages.getString( request, factor.getRule().getName() ),
                                               factor.getMeanMark() );
            }
        }
       	log.info("AuditReturn - addAllAuditResultsDetailed done");
    }

    /**
     * Create factor results slide
     * 
     * @param model model to use
     * @param projectName name of project
     * @return the slide
     * @throws IOException if error
     */
    private Slide createFactorResultsSlide( MasterSheet model, String projectName )
        throws IOException
    {
        Slide slide = getPresentation().createSlide();
        slide.setMasterSheet( model );
        final int titleSize = 22;
        // title
        addTitle( slide, WebMessages.getString( request,
                                                "export.audit_report.presentation.add.slide.factor_results.title" )
            + "\r"
            + (String) WebMessages.getString( request.getLocale(),
                                              "export.audit_report.presentation.add.slide.factor_results.subtitle",
                                              new String[] { projectName } ), titleSize );
        return slide;
    }

    /**
     * Add titles to the html table
     * 
     * @param factorTable buffer for html table
     */
    private void appendFactorResultTitles( StringBuffer factorTable )
    {
        factorTable.append( "<tr bgcolor=\"#C0C0C0\" color=\"#FFFFFF\">" );
        final int nbTitles = 6;
        for ( int t = 1; t < nbTitles + 1; t++ )
        {
            factorTable.append( "<td>"
                + WebMessages.getString( request,
                                         "export.audit_report.presentation.add.slide.factor_results.table.title" + t )
                + "</td>" );
        }
        factorTable.append( "</tr>" );
    }

    /**
     * Add text box with information about factor
     * 
     * @param slide slide to set
     * @param factorName name of factor
     * @param score score of factor
     */
    private void addTextBoxInFactorResultSlide( Slide slide, String factorName, float score )
    {
        final int fontSize = 18;
        final Rectangle anchor = new Rectangle( 20, 85, 635, 430 );
        String status = WebMessages.getString( request, "mark.less." + ( (int) score + 1 ) );
        if ( null == status )
        {
            status =
                WebMessages.getString( request, "export.audit_report.presentation.add.slide.factor_results.not_noted" );
        }
        String textToAdd =
            (String) WebMessages.getString( request.getLocale(),
                                            "export.audit_report.presentation.add.slide.factor_results.bullet1",
                                            new String[] { factorName, status } )
                + "\r\r\r\r\r\r\r\r\r\r\r\r"
                + WebMessages.getString( request, "export.audit_report.presentation.add.slide.factor_results.bullet2" );
        addTextBox( slide, textToAdd, fontSize, anchor, true );
    }

    /**
     * Append name and score of a result for quality results table
     * 
     * @param factorTable buffer stands for table
     * @param result quality result
     * @param rowspan rowspan for td tag
     */
    private void appendQualityResultsDetail( StringBuffer factorTable, QualityReportDTO result, int rowspan )
    {
        factorTable.append( "<td rowspan='" + rowspan + "'>"
            + WebMessages.getString( request, result.getRule().getName() ) + "</td>" );
        // and its score for this audit
        factorTable.append( "<td rowspan='" + rowspan + "'>"
            + SqualeWebActionUtils.formatFloat( new Float( result.getMeanMark() ) ) );
        if ( result.getMeanMark() != -1 && result.getPreviousScore() != -1 )
        {
            // arrow
            URL arrowSrc =
                this.getClass().getResource(
                                             "/"
                                                 + SqualeWebActionUtils.getImageForTrend( "" + result.getMeanMark(), ""
                                                     + result.getPreviousScore() ) );
            if ( arrowSrc != null )
            {
                factorTable.append( "<img src=\"" + arrowSrc.toString() + "\"></img>" );

            }
        }
        factorTable.append( "</td>" );
    }

    /**
     * Append name and score of a result for quality results table
     * 
     * @param factorTable buffer stands for table
     * @param result quality result
     */
    private void appendQualityResultsDetail( StringBuffer factorTable, QualityReportDTO result )
    {
        appendQualityResultsDetail( factorTable, result, result.getQualityResults().size() );
    }

    /**
     * Add top slide for a practice
     * 
     * @param practiceReportDTO the practice
     * @param model model to use
     * @param practicesId id of practices which have already a top slide
     * @throws IOException if error
     */
    private void addTopForPractice( PracticeReportDTO practiceReportDTO, MasterSheet model, List practicesId )
        throws IOException
    {
       	log.info("AuditReturn - addTopForPractice " + practiceReportDTO.getRule().getName());
        Long practiceId = new Long( practiceReportDTO.getRule().getId() );
        if ( practiceReportDTO.getWorstResults().size() > 0 && !practicesId.contains( practiceId ) )
        {
            practicesId.add( practiceId );
            Slide newSlide = getPresentation().createSlide();
            newSlide.setMasterSheet( model );
            // Add title
            String practiceName = WebMessages.getString( request, practiceReportDTO.getRule().getName() );
            addTitle(
                      newSlide,
                      (String) WebMessages.getString(
                                                      request.getLocale(),
                                                      "export.audit_report.presentation.add.slide.practice.details.title",
                                                      new String[] { practiceName } ) );
            // Add practice info
            String practiceDesc =
                WebMessages.getString( request, practiceReportDTO.getRule().getHelpKey() + ".description" );
            if ( null != practiceDesc )
            {
                final int infoSize = 10;
                final Rectangle infoAnchor = new Rectangle( 0, 90, getPresentation().getPageSize().width, 300 );
                addTextBox(
                            newSlide,
                            (String) WebMessages.getString(
                                                            request.getLocale(),
                                                            "export.audit_report.presentation.add.slide.practice.details.info",
                                                            new String[] { practiceDesc } ), infoSize, infoAnchor,
                            false );
            }
            if ( practiceReportDTO.isRulechecking() )
            {
                addTopTransgressionsSlide( newSlide, practiceReportDTO );
            }
            else
            {
                addTopComponentSlide( newSlide, practiceReportDTO );
            }
        }
    }

    /**
     * Add slide for listing worst components for this practice PRECONDITION: at least one top exists
     * 
     * @param newSlide slide to set
     * @param practiceReportDTO the practice
     */
    private void addTopComponentSlide( Slide newSlide, PracticeReportDTO practiceReportDTO )
    {
        final int columnWidth = 300;
        // Get metrics for title
        Map metrics = ( (PracticeReportDetailedDTO) practiceReportDTO.getWorstResults().get( 0 ) ).getMetrics();
        // Create list for titles and reference
        List metricsList = new ArrayList( metrics.keySet() );
        Table table = createTableWithBorder( practiceReportDTO.getWorstResults().size() + 1, metrics.size() + 2 );
        // Add titles
        int titleId = 0;
        addWorstComponentsTitles( table, titleId++, WebMessages.getString( request, "component.name" ) );
        addWorstComponentsTitles( table, titleId++, WebMessages.getString( request, "project.result.practice.value" ) );
        for ( int i = 0; i < metricsList.size(); i++ )
        {
            String shortTre = (String) metricsList.get( i );
            addWorstComponentsTitles( table, titleId++, WebMessages.getString( request, shortTre ) );
        }
        for ( int i = 0; i < practiceReportDTO.getWorstResults().size(); i++ )
        {
            PracticeReportDetailedDTO detail = (PracticeReportDetailedDTO) practiceReportDTO.getWorstResults().get( i );
            TableCell cell = table.getCell( i + 1, 0 );
            setTabCell( cell, detail.getComponent().getFullName() );
            cell = table.getCell( i + 1, 1 );
            setTabCell( cell, SqualeWebActionUtils.formatFloat( new Float( detail.getScore() ) ) );
            for ( int m = 0; m < metricsList.size(); m++ )
            {
                cell = table.getCell( i + 1, m + 2 );
                setTabCell(
                            cell,
                            SqualeWebActionUtils.formatFloat( detail.getMetrics().get( (String) metricsList.get( m ) ).toString() ) );
            }

        }
        // set column width
        table.setColumnWidth( 0, columnWidth );
        newSlide.addShape( table );
    }

    /**
     * Add a title for the column <code>col</code>
     * 
     * @param table table to set
     * @param col column index
     * @param title title for this column
     */
    private void addWorstComponentsTitles( Table table, int col, String title )
    {
        final int titleSize = 8;
        TableCell cell = table.getCell( 0, col );
        setTabCell( cell, title, DEFAULT_FONT_NAME, titleSize, Color.CYAN, true, Color.BLACK );
    }

    /**
     * Add the table of top for a rulechecking practice
     * 
     * @param newSlide slide to set
     * @param practiceReportDTO practice
     */
    private void addTopTransgressionsSlide( Slide newSlide, PracticeReportDTO practiceReportDTO )
    {
        final int borderWidth = 50;
        final int columnWidth = getPresentation().getPageSize().width - borderWidth;
        Table table = createTableWithBorder( practiceReportDTO.getWorstResults().size(), 1 );
        for ( int i = 0; i < practiceReportDTO.getWorstResults().size(); i++ )
        {
            TableCell cell = table.getCell( i, 0 );
            setTabCell(
                        cell,
                        ( (RuleCheckingTransgressionItemBO) practiceReportDTO.getWorstResults().get( i ) ).getMessage().replace(
                                                                                                                                 '\n',
                                                                                                                                 '\r' ) );
        }
        // set column width and height
        table.setColumnWidth( 0, columnWidth );
        newSlide.addShape( table );
    }
}