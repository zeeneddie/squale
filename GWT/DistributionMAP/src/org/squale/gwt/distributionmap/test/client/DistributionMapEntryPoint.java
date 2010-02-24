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
package org.squale.gwt.distributionmap.test.client;

import org.squale.gwt.distributionmap.widget.DistributionMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class DistributionMapEntryPoint
    implements EntryPoint
{

    final private DataServiceAsync dataService = GWT.create( DataService.class );

    final Button sendButton = new Button( "Load distribution map!" );

    final DistributionMap dmWidget = new DistributionMap();

    public void onModuleLoad()
    {
        dmWidget.setDetailURL( "/testURL?id=" );
        initButton();
        RootPanel.get( "main" ).add( sendButton );
        RootPanel.get( "distributionmap" ).add( dmWidget );
    }

    private void initButton()
    {
        sendButton.addClickHandler( new ClickHandler()
        {
            public void onClick( ClickEvent event )
            {
                dmWidget.startLoading();
                dataService.getData( dmWidget.getCallback() );
            }
        } );
    }

}
