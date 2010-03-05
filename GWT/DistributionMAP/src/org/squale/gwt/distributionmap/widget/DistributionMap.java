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
/**
 * 
 */
package org.squale.gwt.distributionmap.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.squale.gwt.distributionmap.widget.bundle.DMResources;
import org.squale.gwt.distributionmap.widget.data.Parent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * <p>
 * This is the Distribution Map widget class.
 * </p>
 * <p>
 * The aim of a DMap is to show the distribution of a property of an element across its parents. <br>
 * A DMap displays a collection of these elements as little boxes, grouped into big boxes which are the parents of these
 * elements. <br>
 * Each little box has a color which depends on the value of the property that the DMap shows.
 * </p>
 * <p>
 * When building data to give to the DMap widget, the elements are referred as children (Child class) and the theirs
 * parents as parent (Parent class).
 * </p>
 * 
 * @author Fabrice BELLINGARD
 */
public class DistributionMap
    extends Composite
{
    /**
     * Callback of the DMap used when receiving data from the server
     */
    final private AsyncCallback<ArrayList<Parent>> callback = new AsyncCallback<ArrayList<Parent>>()
    {
        public void onSuccess( ArrayList<Parent> result )
        {
            Collections.sort( result, parentComparator );
            displayBoxes( result );
        }

        public void onFailure( Throwable caught )
        {
            mainPanel.clear();
            mainPanel.add( new HTML( caught.toString() ) );
        }
    };

    /**
     * Comparator to sort parents by size, in order to print them properly in HTML
     */
    final private Comparator<Parent> parentComparator = new Comparator<Parent>()
    {
        public int compare( Parent p1, Parent p2 )
        {
            return p1.getChildren().size() - p2.getChildren().size();
        }
    };

    /**
     * Various resources used by the DMap, like images or CSS style sheets
     */
    final public static DMResources resources = GWT.create( DMResources.class );

    /**
     * Main panel where the boxes will be placed
     */
    final private FlowPanel mainPanel = new FlowPanel();

    /**
     * A temporary widget that shows a turning wheel to indicate that the widget is waiting for the servers's response
     */
    final private Widget loadingLabel = new HTML();

    /**
     * The popup panel used to display the details of each element box
     */
    final private PopupPanel detailPopup = new PopupPanel( true );

    /**
     * The message to display for big boxes (parents)
     */
    private String bigBoxDetailPopupMessage;

    /**
     * The message to display for small boxes (children)
     */
    private String smallBoxDetailPopupMessage;

    /**
     * The optional URL used to create a link from small boxes to a resources that gives details about the elements
     */
    private String detailURL = "";

    /**
     * Default constructor. <br>
     * The principle is to create the widget with this constructor, then to call {@link #startLoading()}, and finally to
     * call the RPC service that will generate the data (and give it the DMap callback).
     */
    public DistributionMap()
    {
        resources.css().ensureInjected();

        loadingLabel.setStylePrimaryName( resources.css().loadingLabel() );

        mainPanel.setStylePrimaryName( resources.css().distributionMap() );

        initWidget( mainPanel );
    }

    /**
     * Display the turning wheel in the DMap widget
     */
    public void startLoading()
    {
        mainPanel.clear();
        mainPanel.add( loadingLabel );
    }

    /**
     * Gives the callback used by the DMap to display the data
     * 
     * @return the callback
     */
    public AsyncCallback<ArrayList<Parent>> getCallback()
    {
        return callback;
    }

    /**
     * Displays the data returned from the server into a distribution map. It removes any loading label if necessary.
     * 
     * @param parents the Parent-Child tree to display as a DMap
     */
    private void displayBoxes( ArrayList<Parent> parents )
    {
        mainPanel.clear();
        for ( Parent parent : parents )
        {
            mainPanel.add( new BigBox( this, parent ) );
        }
    }

    /**
     * Moves the current detail popup to the specified position.
     * 
     * @param xPosition X position
     * @param yPosition Y position
     */
    void updateDetailPopup( int xPosition, int yPosition )
    {
        detailPopup.setPopupPosition( xPosition + 1 + Window.getScrollLeft(), yPosition + 1 + Window.getScrollTop() );
    }

    /**
     * Hides the small box detail popup.
     */
    void hideDetailPopupForSmallBox()
    {
        detailPopup.setWidget( new HTML( bigBoxDetailPopupMessage ) );
    }

    /**
     * Shows the small box detail popup with the given message and moves it to the given position.
     * 
     * @param message the message to display
     * @param xPosition X position
     * @param yPosition Y position
     */
    void showDetailPopupForSmallBox( String message, int xPosition, int yPosition )
    {
        smallBoxDetailPopupMessage = message;
        detailPopup.setWidget( new HTML( smallBoxDetailPopupMessage ) );
        detailPopup.show();
        updateDetailPopup( xPosition, yPosition );
    }

    /**
     * Hides the big box detail popup.
     */
    void hideDetailPopupForBigBox()
    {
        detailPopup.hide();
    }

    /**
     * Shows the bi box detail popup with the given message and moves it to the given position.
     * 
     * @param message the message to display
     * @param xPosition X position
     * @param yPosition Y position
     */
    void showDetailPopupForBigBox( String message, int xPosition, int yPosition )
    {
        bigBoxDetailPopupMessage = message;
        detailPopup.setWidget( new HTML( bigBoxDetailPopupMessage ) );
        detailPopup.show();
        updateDetailPopup( xPosition, yPosition );
    }

    /**
     * Returns the URL used to display details about elements. This URL may be an empty string if nothing was
     * configured.
     * 
     * @return the detail URL
     */
    public String getDetailURL()
    {
        return detailURL;
    }

    /**
     * Sets the URL used to display details about elements.
     * 
     * @param detailURL the detail URL to set
     */
    public void setDetailURL( String detailURL )
    {
        this.detailURL = detailURL;
    }
}
