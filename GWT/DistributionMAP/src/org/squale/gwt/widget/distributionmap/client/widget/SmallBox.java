/**
 * 
 */
package org.squale.gwt.widget.distributionmap.client.widget;

import org.squale.gwt.widget.distributionmap.client.bundle.DMCss;
import org.squale.gwt.widget.distributionmap.client.widget.data.Child;

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

        DMCss css = DistributionMap.resources.css();

        HTML divElement = new HTML();
        divElement.setSize( "10px", "10px" );
        divElement.setStylePrimaryName( css.smallBox() );

        float grade = childData.getGrade();
        if ( grade >= 2 )
        {
            divElement.addStyleName( css.topGrade() );
        }
        else if ( grade >= 1 )
        {
            divElement.addStyleName( css.midGrade() );
        }
        else
        {
            divElement.addStyleName( css.lowGrade() );
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
