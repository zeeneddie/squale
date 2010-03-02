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
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.HTML;

/**
 * @author fabrice
 */
class SmallBox
    extends AbstractBox
{

    private static final NumberFormat decimalFormat = NumberFormat.getFormat( "#.#" );

    private Child childData;

    private String displayableGrade;

    public SmallBox( DistributionMap dm, Child child )
    {
        super( dm, child.getName() );
        this.childData = child;
        //displayableGrade = child.getGrade()+"";
        displayableGrade = decimalFormat.format( child.getGrade() );

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
        getDistributionMap().showDetailPopupForSmallBox( childData.getName() + "<br/>Grade: " + displayableGrade,
                                                         event.getClientX(), event.getClientY() );
    }

}
