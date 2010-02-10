/**
 * 
 */
package org.squale.gwt.distributionmap.client.widget;

import org.squale.gwt.distributionmap.client.widget.data.Child;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.user.client.ui.HTML;

/**
 * @author fabrice
 */
class SmallBox
    extends AbstractBox
{

    private Child childData;

    public SmallBox( DistributionMap dm, Child child )
    {
        super( dm, child.getName() );
        this.childData = child;

        HTML divElement = new HTML();
        divElement.setSize( "10px", "10px" );
        divElement.setStylePrimaryName( "small-box" );

        float grade = childData.getGrade();
        if ( grade >= 2 )
        {
            divElement.addStyleName( "top-grade" );
        }
        else if ( grade >= 1 )
        {
            divElement.addStyleName( "mid-grade" );
        }
        else
        {
            divElement.addStyleName( "low-grade" );
        }

        initWidget( divElement );
    }

    public void onMouseOut( MouseOutEvent event )
    {
        getDistributionMap().hideDetailPopupForSmallBox();
    }

    public void onMouseOver( MouseOverEvent event )
    {
        getDistributionMap().showDetailPopupForSmallBox(
                                                         "Element " + childData.getName() + "<br/>Grade: "
                                                             + childData.getGrade(), event.getClientX(),
                                                         event.getClientY() );
    }

}
