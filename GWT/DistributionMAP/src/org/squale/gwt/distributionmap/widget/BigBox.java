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
