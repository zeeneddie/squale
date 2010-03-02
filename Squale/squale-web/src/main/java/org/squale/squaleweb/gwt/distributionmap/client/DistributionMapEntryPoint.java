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
package org.squale.squaleweb.gwt.distributionmap.client;

import org.squale.gwt.distributionmap.widget.DistributionMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author Fabrice BELLINGARD
 */
public class DistributionMapEntryPoint
    implements EntryPoint
{

    final private DataServiceAsync dataService = GWT.create( DataService.class );

    final private Button sendButton = new Button( "Load distribution map!" );

    final private DistributionMap dmWidget = new DistributionMap();

    private long practiceId;

    private long projectId;

    private long auditId;

    private String previousAuditId;

    public void onModuleLoad()
    {
        initButton();
        RootPanel.get( "mainButton" ).add( sendButton );

        initDistributionMap();
        RootPanel.get( "distributionmap" ).add( dmWidget );
    }

    private void initButton()
    {
        sendButton.addClickHandler( new ClickHandler()
        {
            public void onClick( ClickEvent event )
            {
                dmWidget.startLoading();
                dataService.getData( auditId, projectId, practiceId, dmWidget.getCallback() );
            }
        } );
    }

    private void initDistributionMap()
    {
        retrieveParamsFromURL();
        // build the URL that is needed to display the component details
        StringBuffer detailURL = new StringBuffer( "project_component.do?action=component&projectId=" );
        detailURL.append( projectId );
        detailURL.append( "&currentAuditId=" );
        detailURL.append( auditId );
        detailURL.append( "&previousAuditId=" );
        detailURL.append( previousAuditId );
        detailURL.append( "&fromMarkPage=true&component=" );
        // and set this URL to the DM widget
        dmWidget.setDetailURL( detailURL.toString() );
    }

    private void retrieveParamsFromURL()
    {
        String stringAuditId = Window.Location.getParameter( "currentAuditId" );
        String stringProjectId = Window.Location.getParameter( "projectId" );
        String stringPracticeId = Window.Location.getParameter( "which" );
        if ( stringAuditId == null || stringAuditId.length() == 0 || stringProjectId == null
            || stringProjectId.length() == 0 || stringPracticeId == null || stringPracticeId.length() == 0 )
        {
            // TODO : handle this case

        }
        else
        {
            auditId = Long.parseLong( stringAuditId );
            projectId = Long.parseLong( stringProjectId );
            practiceId = Long.parseLong( stringPracticeId );
        }

        // optional parameter that does not need to be translated into a long
        previousAuditId = ""; // default value
        previousAuditId = Window.Location.getParameter( "previousAuditId" ); // correct value if it exists
    }

}
