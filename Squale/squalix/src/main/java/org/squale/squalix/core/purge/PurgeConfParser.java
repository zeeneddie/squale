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
package org.squale.squalix.core.purge;

import org.squale.squalecommon.util.xml.XmlImport;
import org.squale.squalix.core.exception.ConfigurationException;
import org.squale.squalix.tools.compiling.CompilingMessages;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.InputStream;

/**
 * @author E6392453 Analyseur de fichier de configuration de la purge d'audits.
 */
public class PurgeConfParser
    extends XmlImport
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( PurgeConfParser.class );

    /**
     * Nombre maximum d'audits à purger par serveur et par exécution du batch.
     */
    private Integer mMaxAuditsToDelete = new Integer( 0 );

    /**
     * Nombre minimum d'audits de suivi obsolètes à conserver pour historique.
     */
    private Integer mMinObsoleteAuditsToKeep = new Integer( 0 );

    /**
     * Constructeur sans argument.
     */
    public PurgeConfParser()
    {
        super( LOGGER );
    }

    /**
     * @return le nombre max d'audits.
     */
    public Integer getMaxAuditsToDelete()
    {
        return mMaxAuditsToDelete;
    }

    /**
     * @return le nombre min d'audits.
     */
    public Integer getMinObsoleteAuditsToKeep()
    {
        return mMinObsoleteAuditsToKeep;
    }

    /**
     * @param i le nombre max d'audits.
     */
    public void setMaxAuditsToDelete( Integer i )
    {
        mMaxAuditsToDelete = i;
    }

    /**
     * @param i le nombre min d'audits.
     */
    public void setMinObsoleteAuditsToKeep( Integer i )
    {
        mMinObsoleteAuditsToKeep = i;
    }

    /**
     * Analyse le fichier de configuration.
     * 
     * @param pStream fichier de configuration.
     * @throws ConfigurationException si erreur d'analyse.
     */
    public void parse( InputStream pStream )
        throws ConfigurationException
    {
        StringBuffer errors = new StringBuffer();
        Digester digester = preSetupDigester( null, null, errors );
        digester.addCallMethod( "PurgeConfiguration/MaxAuditsToDelete", "setMaxAuditsToDelete", 1,
                                new Class[] { Integer.class } );
        digester.addCallParam( "PurgeConfiguration/MaxAuditsToDelete", 0 );
        digester.addCallMethod( "PurgeConfiguration/MinObsoleteAuditsToKeep", "setMinObsoleteAuditsToKeep", 1,
                                new Class[] { Integer.class } );
        digester.addCallParam( "PurgeConfiguration/MinObsoleteAuditsToKeep", 0 );
        digester.push( this );
        parse( digester, pStream, errors );
        if ( errors.length() > 0 )
        {
            throw new ConfigurationException( CompilingMessages.getString( "exception.configuration",
                                                                           new Object[] { errors.toString() } ) );
        }
    }

}
