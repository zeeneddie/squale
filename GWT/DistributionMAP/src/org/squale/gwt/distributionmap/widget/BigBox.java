/**
 * 
 */
package org.squale.gwt.distributionmap.widget;

import org.squale.gwt.distributionmap.widget.data.Child;
import org.squale.gwt.distributionmap.widget.data.Parent;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * @author fabrice
 */
class BigBox
    extends AbstractBox
{

    private Parent parentData;

    public BigBox( DistributionMap dm, Parent parent )
    {
        super( dm, parent.getName() );
        this.parentData = parent;

        FlowPanel mainPanel = new FlowPanel();
        for ( Child child : parentData.getChildren() )
        {
            mainPanel.add( new SmallBox( dm, child ) );
        }
        initWidget( mainPanel );

        setWidth( "100px" );
        setStylePrimaryName( DistributionMap.resources.css().bigBox() );
    }

    public void onMouseOut( MouseOutEvent event )
    {
        getDistributionMap().hideDetailPopupForBigBox();
    }

    public void onMouseOver( MouseOverEvent event )
    {
        getDistributionMap().showDetailPopupForBigBox( getName(), event.getClientX(), event.getClientY() );
    }

}
