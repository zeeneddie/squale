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

import org.squale.gwt.distributionmap.widget.data.Child;
import org.squale.gwt.distributionmap.widget.data.Parent;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Box that displays the parent of an element in the DMap.
 * 
 * @author Fabrice BELLINGARD
 */
class BigBox
    extends AbstractBox
{

    /**
     * The parent element displayed in the DMap
     */
    private Parent parentData;

    /**
     * Comparator to sort children by grade
     */
    final private Comparator<Child> childComparator = new Comparator<Child>()
    {
        public int compare( Child c1, Child c2 )
        {
            return Math.round( c1.getGrade() * 1000 - c2.getGrade() * 1000 );
        }
    };

    /**
     * The default constructor.
     * 
     * @param dm the DMap
     * @param parent the parent element
     */
    public BigBox( DistributionMap dm, Parent parent )
    {
        super( dm, parent.getName() );
        this.parentData = parent;

        FlowPanel mainPanel = new FlowPanel();
        ArrayList<Child> children = new ArrayList<Child>( parentData.getChildren() );
        Collections.sort( children, childComparator );
        for ( Child child : children )
        {
            mainPanel.add( new SmallBox( dm, child ) );
        }
        initWidget( mainPanel );

        setWidth( "100px" );
        setStylePrimaryName( DistributionMap.resources.css().bigBox() );
    }

    /**
     * @see MouseOutHandler#onMouseOut(MouseOutEvent)
     */
    public void onMouseOut( MouseOutEvent event )
    {
        getDistributionMap().hideDetailPopupForBigBox();
    }

    /**
     * @see MouseOverHandler#onMouseOver(MouseOverEvent)
     */
    public void onMouseOver( MouseOverEvent event )
    {
        getDistributionMap().showDetailPopupForBigBox( getName(), event.getClientX(), event.getClientY() );
    }

}
