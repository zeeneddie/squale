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

import org.squale.gwt.distributionmap.widget.bundle.DMCss;
import org.squale.gwt.distributionmap.widget.data.Child;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.HTML;

/**
 * Box that displays an element in the DMap.
 * 
 * @author Fabrice BELLINGARD
 */
class SmallBox
    extends AbstractBox
{
    /**
     * Formatter for the grade to display
     */
    private static final NumberFormat decimalFormat = NumberFormat.getFormat( "#.#" );

    /**
     * The child element displayed by this small box
     */
    private Child childData;

    /**
     * The grade of the element correctly formatted
     */
    private String displayableGrade;

    /**
     * Default constructor.
     * 
     * @param dm the DMap
     * @param child the child element to display
     */
    public SmallBox( DistributionMap dm, Child child )
    {
        super( dm, child.getName() );
        this.childData = child;
        displayableGrade = decimalFormat.format( child.getGrade() );

        HTML divElement = createBox();

        initWidget( divElement );
    }

    /**
     * Creates the small box with the correct CSS and URL link if needed.
     * 
     * @return the HTML element
     */
    private HTML createBox()
    {
        DMCss css = DistributionMap.resources.css();

        HTML divElement = new HTML();
        divElement.setStylePrimaryName( css.smallBox() );

        handleDetailLink( divElement );

        handleColor( divElement, css );

        return divElement;
    }

    /**
     * Creates the link for the small box (if needed)
     * 
     * @param divElement the small box
     */
    private void handleDetailLink( HTML divElement )
    {
        String detailURL = getDistributionMap().getDetailURL();
        if ( detailURL.length() != 0 )
        {
            divElement.setHTML( "<a href=\"" + detailURL + childData.getId()
                + "\"><div style=\"width:100%;height:100%\"></div></a>" );
        }
    }

    /**
     * Sets the correct style for the small box according to the value of the grade of the element
     * 
     * @param divElement the small box
     * @param css the CSS resource
     */
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

    /**
     * @see MouseOutHandler#onMouseOut(MouseOutEvent)
     */
    public void onMouseOut( MouseOutEvent event )
    {
        getDistributionMap().hideDetailPopupForSmallBox();
    }

    /**
     * @see MouseOverHandler#onMouseOver(MouseOverEvent)
     */
    public void onMouseOver( MouseOverEvent event )
    {
        getDistributionMap().showDetailPopupForSmallBox(
                                                         childData.getName() + "<br/>"
                                                             + DistributionMap.messages.grade() + ": "
                                                             + displayableGrade, event.getClientX(), event.getClientY() );
    }

}
