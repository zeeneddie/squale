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
package org.squale.gwt.distributionmap.widget;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Composite;

/**
 * @author fabrice
 */
abstract class AbstractBox
    extends Composite
    implements MouseMoveHandler, MouseOutHandler, MouseOverHandler
{

    final private DistributionMap distributionMap;

    private String name;

    public AbstractBox( DistributionMap dm, String name )
    {
        this.distributionMap = dm;
        this.name = name;

        setupMouseEvents();
    }

    private void setupMouseEvents()
    {
        addDomHandler( this, MouseMoveEvent.getType() );
        addDomHandler( this, MouseOverEvent.getType() );
        addDomHandler( this, MouseOutEvent.getType() );
    }

    protected DistributionMap getDistributionMap()
    {
        return distributionMap;
    }

    protected String getName()
    {
        return name;
    }

    public void onMouseMove( MouseMoveEvent event )
    {
        distributionMap.updateDetailPopup( event.getClientX(), event.getClientY() );
    }

    abstract public void onMouseOut( MouseOutEvent event );

    abstract public void onMouseOver( MouseOverEvent event );

}
