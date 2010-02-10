package org.squale.gwt.distributionmap.client;

import org.squale.gwt.distributionmap.client.widget.DistributionMap;

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
