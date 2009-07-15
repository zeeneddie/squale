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
package org.squale.squalix.tools.umlquality;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.umlquality.connector.QualimetryData;
import com.umlquality.connector.QualimetryDataListener;
import com.umlquality.connector.QualimetryMessageListener;

/**
 * @author sportorico
 */
public class UMLQualityListner
    implements QualimetryDataListener, QualimetryMessageListener
{

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( UMLQualityListner.class );

    /**
     * la tache umlquality
     */
    private UMLQualityTask mUmlqualityTask;

    /**
     * Constructeur par defaut
     * 
     * @param pUmlqualityTask la tache umlquality
     */
    public UMLQualityListner( UMLQualityTask pUmlqualityTask )
    {
        mUmlqualityTask = pUmlqualityTask;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.umlquality.connector.QualimetryDataListener#incomingData(com.umlquality.connector.QualimetryData)
     */
    public void incomingData( QualimetryData pArg0 )
    {

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.umlquality.connector.QualimetryMessageListener#connectorMessage(java.lang.String, java.lang.String, int)
     */
    public void connectorMessage( String pArg0, String pArg1, int pArg2 )
    {

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.umlquality.connector.QualimetryMessageListener#connectorError(java.lang.String, java.lang.String, int)
     */
    public void connectorError( String pArg0, String pArg1, int pArg2 )
    {

        LOGGER.error( UMLQualityMessages.getString( "logs.error.tools_error" ) + "  " + pArg0 + "  " + pArg1 + "  "
            + pArg2 );
        mUmlqualityTask.initError( "" + pArg0 + "  " + pArg1 + "  " + pArg2 );
    }
}
