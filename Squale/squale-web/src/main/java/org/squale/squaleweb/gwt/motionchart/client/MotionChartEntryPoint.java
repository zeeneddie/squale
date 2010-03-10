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
package org.squale.squaleweb.gwt.motionchart.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.MotionChart;
import com.google.gwt.visualization.client.visualizations.MotionChart.Options;

/**
 * Entry point for the Motion Chart displayed on the Homepage.
 * 
 * @author Fabrice BELLINGARD
 */
public class MotionChartEntryPoint
    implements EntryPoint
{
    final private Label label = new Label( "..." );

    final private DataServiceAsync motionChartService = GWT.create( DataService.class );

    final private AsyncCallback<String> callback = new AsyncCallback<String>()
    {

        public void onSuccess( String arg0 )
        {
            label.setText( new Date() + " - SUCCES ! " + arg0 );
        }

        public void onFailure( Throwable arg0 )
        {
            label.setText( new Date() + " - ECHEC ! " + arg0 );
        }
    };

    public void onModuleLoad()
    {
        Button b = new Button( "Launch request !" );
        b.addClickHandler( new ClickHandler()
        {
            public void onClick( ClickEvent arg0 )
            {
                motionChartService.getData( callback );
            }
        } );

        RootPanel.get( "testdiv" ).add( b );
        RootPanel.get( "testdiv" ).add( label );

        // Create a callback to be called when the visualization API
        // has been loaded.
        Runnable onLoadCallback = new Runnable()
        {
            public void run()
            {
                Panel panel = RootPanel.get( "motionchart" );
                MotionChart motionChart = new MotionChart( createTable(), createOptions() );
                panel.add( motionChart );
            }

        };
        VisualizationUtils.loadVisualizationApi( onLoadCallback, MotionChart.PACKAGE );
    }

    protected Options createOptions()
    {
        Options options = Options.create();
        Panel panel = RootPanel.get( "motionchart" );
        options.setWidth( panel.getOffsetWidth() );
        options.setHeight( panel.getOffsetHeight() );
        options.setOption( "showXScalePicker", false );
        options.setOption( "showYScalePicker", false );
        return options;
    }

    protected AbstractDataTable createTable()
    {
        DataTable data = DataTable.create();
        data.addColumn( ColumnType.STRING, "Applications" );
        data.addColumn( ColumnType.DATE, "Date" );
        data.addColumn( ColumnType.NUMBER, "Cyclomatic Complexity" ); // X by default
        data.addColumn( ColumnType.NUMBER, "Grade" ); // Y by default
        data.addColumn( ColumnType.NUMBER, "Lines of Code" );
        data.addColumn( ColumnType.STRING, "Requirements achievement" );
        data.addRows( 6 );
        // Apple
        data.setValue( 0, 0, "Gaia" );
        data.setValue( 0, 1, new Date( 1988, 0, 1 ) );
        data.setValue( 0, 2, 1057 );
        data.setValue( 0, 3, 2.2 );
        data.setValue( 0, 4, 256789 );
        data.setValue( 0, 5, "Accepted" );
        data.setValue( 1, 0, "Gaia" );
        data.setValue( 1, 1, new Date( 1989, 6, 1 ) );
        data.setValue( 1, 2, 1783 );
        data.setValue( 1, 3, 1.9 );
        data.setValue( 1, 4, 300789 );
        data.setValue( 1, 5, "Partially Accepted" );
        // Linux
        data.setValue( 2, 0, "eReca" );
        data.setValue( 2, 1, new Date( 1988, 0, 1 ) );
        data.setValue( 2, 2, 2300 );
        data.setValue( 2, 3, 0.8 );
        data.setValue( 2, 4, 567930 );
        data.setValue( 2, 5, "Refused" );
        data.setValue( 3, 0, "eReca" );
        data.setValue( 3, 1, new Date( 1989, 6, 1 ) );
        data.setValue( 3, 2, 2476 );
        data.setValue( 3, 3, 1.3 );
        data.setValue( 3, 4, 600087 );
        data.setValue( 3, 5, "Partially Accepted" );
        // None
        data.setValue( 4, 0, "-" );
        data.setValue( 4, 1, new Date( 1988, 0, 1 ) );
        data.setValue( 4, 2, 1 );
        data.setValue( 4, 3, 0 );
        data.setValue( 4, 4, 0 );
        data.setValue( 4, 5, "Refused" );
        data.setValue( 5, 0, "-" );
        data.setValue( 5, 1, new Date( 1989, 6, 1 ) );
        data.setValue( 5, 2, 1 );
        data.setValue( 5, 3, 3 );
        data.setValue( 5, 4, 0 );
        data.setValue( 5, 5, "Accepted" );
        return data;
    }

}
