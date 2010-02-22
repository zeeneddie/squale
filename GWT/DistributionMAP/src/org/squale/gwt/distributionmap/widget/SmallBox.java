/**
 * 
 */
package org.squale.gwt.distributionmap.widget;

import org.squale.gwt.distributionmap.widget.bundle.DMCss;
import org.squale.gwt.distributionmap.widget.data.Child;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.user.client.ui.Anchor;
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

        HTML divElement = createBox();

        initWidget( divElement );
    }

    private HTML createBox()
    {
        DMCss css = DistributionMap.resources.css();

        HTML divElement = new HTML();
        divElement.setSize( "10px", "10px" );
        divElement.setStylePrimaryName( css.smallBox() );

        handleDetailLink( divElement );

        handleColor( divElement, css );

        return divElement;
    }

    private void handleDetailLink( HTML divElement )
    {
        String detailURL = getDistributionMap().getDetailURL();
        if ( detailURL.length() != 0 )
        {
            divElement.setHTML( "<a href=\"" + detailURL + childData.getId()
                + "\"><div style=\"width:100%;height:100%\"></div></a>" );
        }
    }

    private void handleColor( HTML divElement, DMCss css )
    {
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
    }

    public void onMouseOut( MouseOutEvent event )
    {
        getDistributionMap().hideDetailPopupForSmallBox();
    }

    public void onMouseOver( MouseOverEvent event )
    {
        getDistributionMap().showDetailPopupForSmallBox( childData.getName() + "<br/>Grade: " + childData.getGrade(),
                                                         event.getClientX(), event.getClientY() );
    }

}
