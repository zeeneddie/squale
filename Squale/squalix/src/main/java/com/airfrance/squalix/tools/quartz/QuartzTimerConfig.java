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
package com.airfrance.squalix.tools.quartz;

import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.airfrance.squalecommon.util.xml.XmlImport;
import com.airfrance.squalix.configurationmanager.ConfigUtility;
import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * This class get back the configuration for the Javancss task. This task is done by reading an xml file :
 * <code>javancss-config.xml</code>
 */
public class QuartzTimerConfig
    extends XmlImport
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( QuartzTimerConfig.class );

    /** Path for the result file */
    private String quartzKey;

    /**
     * Default Constructor
     */
    public QuartzTimerConfig()
    {
        super( LOGGER );
    }

    /**
     * Reading of the configuration
     * 
     * @param pStream stream
     * @throws ConfigurationException exception if an error occurs
     */
    public void parse( InputStream pStream )
        throws ConfigurationException
    {
        StringBuffer errors = new StringBuffer();
        Digester digester = preSetupDigester( "-//Quartz Configuration DTD //EN", "/config/quartz-timer-config.dtd", errors );
        // Root directory treatment
        digester.addCallMethod( "quartz-timer-configuration/quartzKey", "setQuartzKey", 1, new Class[] { String.class } );
        digester.addCallParam( "quartz-timer-configuration/quartzKey", 0 );
        digester.push( this );
        // Parser call
        parse( digester, pStream, errors );
        if ( errors.length() > 0 )
        {
            throw new ConfigurationException( QuartzMessages.getString( "quartz.exception.configuration.parse",
                                                                        new Object[] { errors.toString() } ) );
        }
    }

    /**
     * Getter for the pathResultFile attribute
     * 
     * @return the path of the result file
     */
    public String getQuartzKey()
    {
        return quartzKey;
    }

    /**
     * Setter for the pathResultFile attribute
     * 
     * @param pQuartzKey The path of the result file
     */
    public void setQuartzKey( String pQuartzKey )
    {
        quartzKey = ConfigUtility.filterStringWithSystemProps( pQuartzKey );
    }

}
