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
package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.config.web.HomepageComponentDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.homepagemanagement.HomepageManagementForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * This class do the transformation between the HomepageManagementForm and the list of HomepageComponentDTO
 */
public class HomepageManagementTransformer
    implements WITransformer
{

    /**
     * {@inheritDoc}
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        Object[] obj = new Object[1];
        formToObj( form, obj );
        return obj;
    }

    /**
     * {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {

        HomepageManagementForm currentForm = (HomepageManagementForm) form;
        ArrayList<HomepageComponentDTO> list = (ArrayList<HomepageComponentDTO>) object[0];
        HomepageComponentDTO component;

        List<String> elementNotChecked = new ArrayList<String>();
        String[] elementChecked = new String[HomepageComponentDTO.ELEMENT];

        // Form to object for introduction part
        ftoIntro( list, currentForm, elementNotChecked, elementChecked );
        
        // form to object for news part
        ftoNews( list, currentForm, elementNotChecked, elementChecked );
        
        //Form to object for audit part
        ftoAudit( list, currentForm, elementNotChecked, elementChecked );
        
        // Form to object for result part
        ftoResult( list, currentForm, elementNotChecked, elementChecked );
        
        // Form to object for statistics part
        ftoStat( list, currentForm, elementNotChecked, elementChecked );

        currentForm.setJspOrder( doList( elementNotChecked, elementChecked ) );

    }

    /**
     * {@inheritDoc}
     */
    public WActionForm objToForm( Object[] object )
        throws WTransformerException
    {
        WActionForm form = new WActionForm();
        objToForm( object, form );
        return form;
    }

    /**
     * {@inheritDoc}
     */
    public void objToForm( Object[] object, WActionForm form )
        throws WTransformerException
    {
        HomepageManagementForm currentForm = (HomepageManagementForm) form;
        HashMap<String, HomepageComponentDTO> element = new HashMap<String, HomepageComponentDTO>();
        ArrayList<HomepageComponentDTO> list = (ArrayList<HomepageComponentDTO>) object[0];
        Iterator<HomepageComponentDTO> it = list.iterator();

        while ( it.hasNext() )
        {
            HomepageComponentDTO component = it.next();
            element.put( component.getComponentName(), component );
        }

        List<String> elementNotChecked = new ArrayList<String>();
        String[] elementChecked = new String[HomepageComponentDTO.ELEMENT];

        // object to form for introduction part
        otfIntro( element, currentForm, elementNotChecked, elementChecked );
        
        // object to form for news part
        otfNews( element, currentForm, elementNotChecked, elementChecked );
        
        // object to form for audit part
        otfAudit( element, currentForm, elementNotChecked, elementChecked );
        
        // object to form for result part
        otfResult( element, currentForm, elementNotChecked, elementChecked );
        
        // object to form for statistics part
        otfStat( element, currentForm, elementNotChecked, elementChecked );

        currentForm.setJspOrder( doList( elementNotChecked, elementChecked ) );

    }

    /**
     * This method give the name of the jsp linked to the name given in argument. This name should be one of the static
     * variable define in the HomepageComponentDTO for the five displayable component
     * 
     * @param name Name corresponding to the HomepageComponent (
     * @return The name of the jsp
     */
    private String jspName( String name )
    {
        StringBuffer jspName = new StringBuffer( "hmngt_" );
        jspName.append( name );
        jspName.append( ".jsp" );
        return jspName.toString();
    }

    /**
     * Do the ordered list of jsp
     * 
     * @param elementNotChecked The list of element not checked
     * @param elementChecked The list of element checked
     * @return the list of jsp name
     */
    private List<String> doList( List<String> elementNotChecked, String[] elementChecked )
    {
        List<String> jspList = new ArrayList<String>();
        // We push of one rank
        jspList.add( "" );
        for ( int index = 0; index < elementChecked.length; index = index + 1 )
        {
            if ( elementChecked[index] != null )
            {
                jspList.add( elementChecked[index] );
            }
        }

        jspList.addAll( elementNotChecked );
        return jspList;
    }

    /**
     * Fill the form for the audit bloc
     * 
     * @param element The map of element
     * @param currentForm The form to fill
     * @param elementNotChecked The list of element not checked
     * @param elementChecked The list of element checked
     */
    private void otfAudit( HashMap<String, HomepageComponentDTO> element, HomepageManagementForm currentForm,
                           List<String> elementNotChecked, String[] elementChecked )
    {
        // Elements which depends of audit
        HomepageComponentDTO component;

        component = element.get( HomepageComponentDTO.AUDIT );
        String jspName = jspName( HomepageComponentDTO.AUDIT );
        if ( component != null && component.getComponentValue().equals( "true" ) )
        {
            currentForm.setAuditCheck( true );
            currentForm.setPositionAudit( component.getComponentPosition() );
            elementChecked[component.getComponentPosition() - 1] = jspName;
        }
        else
        {
            currentForm.setAuditCheck( false );
            elementNotChecked.add( jspName );
        }

        otfAuditDone( element, currentForm );

        component = element.get( HomepageComponentDTO.AUDIT_SCHEDULED );
        if ( component != null && component.getComponentValue().equals( "true" ) )
        {
            currentForm.setAuditScheduledCheck( true );
        }
        else
        {
            currentForm.setAuditScheduledCheck( false );
        }

        component = element.get( HomepageComponentDTO.AUDIT_SHOW_SEPARETELY );
        if ( component != null && component.getComponentValue().equals( "true" ) )
        {
            currentForm.setAuditShowSeparatelyCheck( true );
        }
        else
        {
            currentForm.setAuditShowSeparatelyCheck( false );
        }
        component = element.get( HomepageComponentDTO.AUDIT_NB_JOURS );
        if ( component != null && component.getComponentValue() != null )
        {
            currentForm.setAuditNbJours( component.getComponentValue() );
        }
        else
        {
            currentForm.setAuditNbJours( HomepageComponentDTO.DEFAULT_AUDIT_NB_JOURS );
        }
    }

    /**
     * Fill the form for the audit done bloc
     * 
     * @param element The map of element
     * @param currentForm The form to fill
     */
    private void otfAuditDone( HashMap<String, HomepageComponentDTO> element, HomepageManagementForm currentForm )
    {
        HomepageComponentDTO component;

        component = element.get( HomepageComponentDTO.AUDIT_DONE );
        if ( component != null && component.getComponentValue().equals( "true" ) )
        {
            currentForm.setAuditDoneCheck( true );
        }
        else
        {
            currentForm.setAuditDoneCheck( false );
        }

        // Elements which depends of auditDone
        component = element.get( HomepageComponentDTO.AUDIT_SUCCESSFUL );
        if ( component != null && component.getComponentValue().equals( "true" ) )
        {
            currentForm.setAuditSuccessfullCheck( true );
        }
        else
        {
            currentForm.setAuditSuccessfullCheck( false );
        }

        component = element.get( HomepageComponentDTO.AUDIT_PARTIAL );
        if ( component != null && component.getComponentValue().equals( "true" ) )
        {
            currentForm.setAuditPartialCheck( true );
        }
        else
        {
            currentForm.setAuditPartialCheck( false );
        }

        component = element.get( HomepageComponentDTO.AUDIT_FAILED );
        if ( component != null && component.getComponentValue().equals( "true" ) )
        {
            currentForm.setAuditFailedCheck( true );
        }
        else
        {
            currentForm.setAuditFailedCheck( false );
        }
    }

    /**
     * Fill the form for the result bloc
     * 
     * @param element The map of element
     * @param currentForm The form to fill
     * @param elementNotChecked The list of element not checked
     * @param elementChecked The list of element checked
     */
    private void otfResult( HashMap<String, HomepageComponentDTO> element, HomepageManagementForm currentForm,
                            List<String> elementNotChecked, String[] elementChecked )
    {
        HomepageComponentDTO component;

        component = element.get( HomepageComponentDTO.RESULT );
        String jspName = jspName( HomepageComponentDTO.RESULT );
        if ( component != null && component.getComponentValue().equals( "true" ) )
        {
            currentForm.setResultCheck( true );
            currentForm.setPositionResult( component.getComponentPosition() );
            elementChecked[component.getComponentPosition() - 1] = jspName;
        }
        else
        {
            currentForm.setResultCheck( false );
            elementNotChecked.add( jspName );
        }

        component = element.get( HomepageComponentDTO.RESULT_BY_GRID );
        if ( component != null && component.getComponentValue().equals( "true" ) )
        {
            currentForm.setResultByGridCheck( true );
        }
        else
        {
            currentForm.setResultByGridCheck( false );
        }

        component = element.get( HomepageComponentDTO.RESULT_KIVIAT );
        if ( component != null && component.getComponentValue().equals( "true" ) )
        {
            currentForm.setResultKiviatCheck( true );
        }
        else
        {
            currentForm.setResultKiviatCheck( false );
        }

        component = element.get( HomepageComponentDTO.KIVIAT_WIDTH );
        if ( component != null && component.getComponentValue() != null )
        {
            currentForm.setKiviatWidth(component.getComponentValue());
        }
        else
        {
            currentForm.setKiviatWidth( HomepageComponentDTO.DEFAULT_KIVIAT_WIDTH );
        }
    }

    /**
     * Fill the form for the introduction bloc
     * 
     * @param element The map of element
     * @param currentForm The form to fill
     * @param elementNotChecked The list of element not checked
     * @param elementChecked The list of element checked
     */
    private void otfIntro( HashMap<String, HomepageComponentDTO> element, HomepageManagementForm currentForm,
                           List<String> elementNotChecked, String[] elementChecked )
    {
        HomepageComponentDTO component;
        component = element.get( HomepageComponentDTO.INTRO );
        String jspName = jspName( HomepageComponentDTO.INTRO );
        if ( component != null && component.getComponentValue().equals( "true" ) )
        {
            currentForm.setIntroCheck( true );
            currentForm.setPositionIntro( component.getComponentPosition() );
            elementChecked[component.getComponentPosition() - 1] = jspName;
        }
        else
        {
            currentForm.setIntroCheck( false );
            elementNotChecked.add( jspName );
        }
    }

    /**
     * Fill the form for the news bloc
     * 
     * @param element The map of element
     * @param currentForm The form to fill
     * @param elementNotChecked The list of element not checked
     * @param elementChecked The list of element checked
     */
    private void otfNews( HashMap<String, HomepageComponentDTO> element, HomepageManagementForm currentForm,
                          List<String> elementNotChecked, String[] elementChecked )
    {
        HomepageComponentDTO component;
        component = element.get( HomepageComponentDTO.NEWS );
        String jspName = jspName( HomepageComponentDTO.NEWS );
        if ( component != null && component.getComponentValue().equals( "true" ) )
        {
            currentForm.setNewsCheck( true );
            currentForm.setPositionNews( component.getComponentPosition() );
            elementChecked[component.getComponentPosition() - 1] = jspName;
        }
        else
        {
            currentForm.setNewsCheck( false );
            elementNotChecked.add( jspName );
        }
    }

    /**
     * Fill the form for the statistics bloc
     * 
     * @param element The map of element
     * @param currentForm The form to fill
     * @param elementNotChecked The list of element not checked
     * @param elementChecked The list of element checked
     */
    private void otfStat( HashMap<String, HomepageComponentDTO> element, HomepageManagementForm currentForm,
                          List<String> elementNotChecked, String[] elementChecked )
    {
        HomepageComponentDTO component;
        component = element.get( HomepageComponentDTO.STAT );
        String jspName = jspName( HomepageComponentDTO.STAT );
        if ( component != null && component.getComponentValue().equals( "true" ) )
        {
            currentForm.setStatisticCheck( true );
            currentForm.setPositionStat( component.getComponentPosition() );
            elementChecked[component.getComponentPosition() - 1] = jspName;
        }
        else
        {
            currentForm.setStatisticCheck( false );
            elementNotChecked.add( jspName );
        }
    }

    /**
     * Transform the information from the introduction bloc of the form in a list of homepgeComponentDTO
     * 
     * @param list The list of HomepageComponentDTO to save in the database
     * @param currentForm The form used for recover the information
     * @param elementNotChecked The list of element not checked
     * @param elementChecked The list of element checked
     */
    private void ftoIntro( ArrayList<HomepageComponentDTO> list, HomepageManagementForm currentForm,
                           List<String> elementNotChecked, String[] elementChecked )
    {
        HomepageComponentDTO component;
        component = new HomepageComponentDTO( HomepageComponentDTO.INTRO, "false" );
        String jspName = jspName( HomepageComponentDTO.INTRO );
        if ( currentForm.isIntroCheck() )
        {
            component.setComponentValue( "true" );
            component.setComponentPosition( currentForm.getPositionIntro() );
            elementChecked[component.getComponentPosition() - 1] = jspName;
        }
        else
        {
            elementNotChecked.add( jspName );
        }
        list.add( component );
    }

    /**
     * Transform the information from the news bloc of the form in a list of homepgeComponentDTO
     * 
     * @param list The list of HomepageComponentDTO to save in the database
     * @param currentForm The form used for recover the information
     * @param elementNotChecked The list of element not checked
     * @param elementChecked The list of element checked
     */
    private void ftoNews( ArrayList<HomepageComponentDTO> list, HomepageManagementForm currentForm,
                          List<String> elementNotChecked, String[] elementChecked )
    {
        HomepageComponentDTO component;
        component = new HomepageComponentDTO( HomepageComponentDTO.NEWS, "false" );
        String jspName = jspName( HomepageComponentDTO.NEWS );
        if ( currentForm.isNewsCheck() )
        {
            component.setComponentValue( "true" );
            component.setComponentPosition( currentForm.getPositionNews() );
            elementChecked[component.getComponentPosition() - 1] = jspName;
        }
        else
        {
            elementNotChecked.add( jspName );
        }
        list.add( component );
    }

    /**
     * Transform the information from the audit bloc of the form in a list of homepgeComponentDTO
     * 
     * @param list The list of HomepageComponentDTO to save in the database
     * @param currentForm The form used for recover the information
     * @param elementNotChecked The list of element not checked
     * @param elementChecked The list of element checked
     */
    private void ftoAudit( ArrayList<HomepageComponentDTO> list, HomepageManagementForm currentForm,
                           List<String> elementNotChecked, String[] elementChecked )
    {
        HomepageComponentDTO component;
        component = new HomepageComponentDTO( HomepageComponentDTO.AUDIT, "false" );
        String jspName = jspName( HomepageComponentDTO.AUDIT );
        if ( currentForm.isAuditCheck() )
        {
            component.setComponentValue( "true" );
            component.setComponentPosition( currentForm.getPositionAudit() );
            elementChecked[component.getComponentPosition() - 1] = jspName;
        }
        else
        {
            elementNotChecked.add( jspName );
        }
        list.add( component );

        ftoAuditdone( list, currentForm );

        component = new HomepageComponentDTO( HomepageComponentDTO.AUDIT_SCHEDULED, "false" );
        if ( currentForm.isAuditScheduledCheck() )
        {
            component.setComponentValue( "true" );
        }
        list.add( component );

        component = new HomepageComponentDTO( HomepageComponentDTO.AUDIT_SHOW_SEPARETELY, "false" );
        if ( currentForm.isAuditShowSeparatelyCheck() )
        {
            component.setComponentValue( "true" );
        }
        list.add( component );

        component = new HomepageComponentDTO( HomepageComponentDTO.AUDIT_NB_JOURS, HomepageComponentDTO.DEFAULT_AUDIT_NB_JOURS );
        if ( currentForm.getAuditNbJours() != null )
        {
            component.setComponentValue( currentForm.getAuditNbJours() );
        }
        list.add( component );

    }

    /**
     * Transform the information of the audits done from the form in a list of homepgeComponentDTO
     * 
     * @param list The list of HomepageComponentDTO to save in the database
     * @param currentForm The form used for recover the information
     */
    private void ftoAuditdone( ArrayList<HomepageComponentDTO> list, HomepageManagementForm currentForm )
    {
        HomepageComponentDTO component = new HomepageComponentDTO( HomepageComponentDTO.AUDIT_DONE, "false" );
        if ( currentForm.isAuditDoneCheck() )
        {
            component.setComponentValue( "true" );
        }
        list.add( component );

        component = new HomepageComponentDTO( HomepageComponentDTO.AUDIT_SUCCESSFUL, "false" );
        if ( currentForm.isAuditSuccessfullCheck() )
        {
            component.setComponentValue( "true" );
        }
        list.add( component );

        component = new HomepageComponentDTO( HomepageComponentDTO.AUDIT_PARTIAL, "false" );
        if ( currentForm.isAuditPartialCheck() )
        {
            component.setComponentValue( "true" );
        }
        list.add( component );

        component = new HomepageComponentDTO( HomepageComponentDTO.AUDIT_FAILED, "false" );
        if ( currentForm.isAuditFailedCheck() )
        {
            component.setComponentValue( "true" );
        }
        list.add( component );

    }

    /**
     * Transform the information from the result bloc of the form in a list of homepgeComponentDTO
     * 
     * @param list The list of HomepageComponentDTO to save in the database
     * @param currentForm The form used for recover the information
     * @param elementNotChecked The list of element not checked
     * @param elementChecked The list of element checked
     */
    private void ftoResult( ArrayList<HomepageComponentDTO> list, HomepageManagementForm currentForm,
                            List<String> elementNotChecked, String[] elementChecked )
    {
        HomepageComponentDTO component;
        component = new HomepageComponentDTO( HomepageComponentDTO.RESULT, "false" );
        String jspName = jspName( HomepageComponentDTO.RESULT );
        if ( currentForm.isResultCheck() )
        {
            component.setComponentValue( "true" );
            component.setComponentPosition( currentForm.getPositionResult() );
            elementChecked[component.getComponentPosition() - 1] = jspName;
        }
        else
        {
            elementNotChecked.add( jspName );
        }
        list.add( component );

        component = new HomepageComponentDTO( HomepageComponentDTO.RESULT_BY_GRID, "false" );
        if ( currentForm.isResultByGridCheck() )
        {
            component.setComponentValue( "true" );
        }
        list.add( component );

        component = new HomepageComponentDTO( HomepageComponentDTO.RESULT_KIVIAT, "false" );
        if ( currentForm.isResultKiviatCheck() )
        {
            component.setComponentValue( "true" );
        }
        list.add( component );
        
        component = new HomepageComponentDTO( HomepageComponentDTO.KIVIAT_WIDTH, HomepageComponentDTO.DEFAULT_KIVIAT_WIDTH );
        if ( currentForm.getKiviatWidth() != null )
        {
            component.setComponentValue( currentForm.getKiviatWidth() );
        }
        list.add( component );

        
    }

    /**
     * Transform the information from the statistics bloc of the form in a list of homepgeComponentDTO
     * 
     * @param list The list of HomepageComponentDTO to save in the database
     * @param currentForm The form used for recover the information
     * @param elementNotChecked The list of element not checked
     * @param elementChecked The list of element checked
     */
    private void ftoStat( ArrayList<HomepageComponentDTO> list, HomepageManagementForm currentForm,
                          List<String> elementNotChecked, String[] elementChecked )
    {
        HomepageComponentDTO component;
        component = new HomepageComponentDTO( HomepageComponentDTO.STAT, "false" );
        String jspName = jspName( HomepageComponentDTO.STAT );
        if ( currentForm.isStatisticCheck() )
        {
            component.setComponentValue( "true" );
            component.setComponentPosition( currentForm.getPositionStat() );
            elementChecked[component.getComponentPosition() - 1] = jspName;
        }
        else
        {
            elementNotChecked.add( jspName );
        }
        list.add( component );
    }
}
