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
package com.airfrance.squalix.tools.mccabe;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalix.util.parser.LanguageParser;

/**
 * Tâche McCabe pour les projet de langage par objet (Java, C++, etc.).
 */
public abstract class OOMcCabeTask extends AbstractMcCabeTask
{
    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( AbstractMcCabeTask.class );

    /** Le parser */
    protected LanguageParser mParser;

    /**
     * Instance du persisteur McCabe.
     */
    private OOMcCabePersistor mPersistor = null;

    /** Le template de la classe à utiliser. */
    protected String mClassTemplate = "";

    /**
     * Modifie le template niveau classe.
     */
    public abstract void setClassTemplate();

    protected void initialize() throws Exception {
        setClassTemplate();
        super.initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPersistor() throws Exception {
        mPersistor =
            new OOMcCabePersistor( mParser, mConfiguration, mAudit, getSession(), getData(), mName, mClassTemplate );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void parseReport( final String pReport )
    throws Exception
    {
    String reportFileName = computeReportFileName( pReport );
    // Parser le rapport généré
    // Il y a deux méthodes de parsing des rapports, une pour les rapports de méthodes
    // et l'autre pour les rapports de classe. Ainsi, il suffit que le nom du rapport
    // débute par METHOD ou CLASS pour que la bonne méthode soit sélectionné, le reste
    // est laissé à l'appréciation de l'utilisateur. Ceci permet de versionner, dater
    // les noms de rapports.

    if ( pReport.startsWith( McCabeMessages.getString( "reports.profile.class" ) ) )
    {
        mPersistor.parseClassReport( reportFileName );
    }
    else if ( pReport.startsWith( McCabeMessages.getString( "reports.profile.module" ) ) )
    {
        mPersistor.parseMethodReport( reportFileName, mData );
    }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void persistProjectResult() {
        mPersistor.persistProjectResult();
    }
    
}
