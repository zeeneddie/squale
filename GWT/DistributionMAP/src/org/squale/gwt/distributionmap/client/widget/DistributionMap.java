/**
 * 
 */
package org.squale.gwt.distributionmap.client.widget;

import java.util.ArrayList;

import org.squale.gwt.distributionmap.client.widget.data.Parent;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author fabrice
 */
public class DistributionMap
    extends Composite
{
    final AsyncCallback<ArrayList<Parent>> callback = new AsyncCallback<ArrayList<Parent>>()
    {
        public void onSuccess( ArrayList<Parent> result )
        {
            displayBoxes( result );
        }

        public void onFailure( Throwable caught )
        {
            mainPanel.clear();
            mainPanel.add( new HTML( caught.toString() ) );
        }
    };

    final private FlowPanel mainPanel = new FlowPanel();

    final private Widget loadingLabel = new HTML( "<div class='loading-label'></div>" );

    final private DecoratedPopupPanel detailPopup = new DecoratedPopupPanel( true );

    private String bigBoxDetailPopupMessage;

    private String smallBoxDetailPopupMessage;

    public DistributionMap()
    {
        detailPopup.setWidth( "150px" );

        initWidget( mainPanel );
    }

    public void startLoading()
    {
        mainPanel.clear();
        mainPanel.add( loadingLabel );
    }

    public AsyncCallback<ArrayList<Parent>> getCallback()
    {
        return callback;
    }

    private void displayBoxes( ArrayList<Parent> parents )
    {
        mainPanel.clear();
        for ( Parent parent : parents )
        {
            mainPanel.add( new BigBox( this, parent ) );
        }
    }

    void updateDetailPopup( int xPosition, int yPosition )
    {
        detailPopup.setPopupPosition( xPosition + 1, yPosition + 1 );
    }

    void hideDetailPopupForSmallBox()
    {
        detailPopup.setWidget( new HTML( bigBoxDetailPopupMessage ) );
    }

    void showDetailPopupForSmallBox( String message, int xPosition, int yPosition )
    {
        smallBoxDetailPopupMessage = message;
        detailPopup.setWidget( new HTML( smallBoxDetailPopupMessage ) );
        detailPopup.show();
        updateDetailPopup( xPosition, yPosition );
    }

    void hideDetailPopupForBigBox()
    {
        detailPopup.hide();
    }

    void showDetailPopupForBigBox( String message, int xPosition, int yPosition )
    {
        bigBoxDetailPopupMessage = message;
        detailPopup.setWidget( new HTML( bigBoxDetailPopupMessage ) );
        detailPopup.show();
        updateDetailPopup( xPosition, yPosition );
    }
}
