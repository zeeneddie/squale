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

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.ErrorBO;
import com.airfrance.squalix.util.csv.CSVParser;
import com.airfrance.squalix.util.parser.CobolParser;

/**
 * Tâche McCabe pour les projets Cobol.
 */
public class CobolMcCabeTask
    extends AbstractMcCabeTask
    implements CSVParser.CSVHandler
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( AbstractMcCabeTask.class );

    /** Le parser */
    protected CobolParser mParser;

    /**
     * Instance du persisteur McCabe.
     */
    private CobolMcCabePersistor mPersistor;

    /**
     * Constructeur.
     */
    public CobolMcCabeTask()
    {
        mName = "CobolMcCabeTask";
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public void setParser()
    {
        mParser = new CobolParser( mProject );
    }

    /*
     * {@inheritDoc}
     */
    public void processLine( List line )
    {
        // aucun traitement particulier
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public void setPersistor()
        throws Exception
    {
        mPersistor = new CobolMcCabePersistor( getSession(), getData(), mName, mAudit, mConfiguration, mParser );
    }

    /*
     * {@inheritDoc}
     */
    @Override
    protected void parseReport( final String pReport )
        throws Exception
    {
        String lReportFileName = computeReportFileName( pReport );
        mPersistor.parseCobolReport( lReportFileName );
        // la tâche est terminée avec succès si le rapport est correctement analysé
        mStatus = TERMINATED;
        // conversion de toutes les erreurs 'fatales' en avertissements
        convertFatalToWarningErrors();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persistProjectResult()
    {
        mPersistor.persistProjectResult();
    }

    /**
     * Convertit les erreurs de sevérité 'Fatale' en erreurs de sevérité 'Avertissement'.
     */
    private void convertFatalToWarningErrors()
    {
        // toutes les erreurs fatales sont transformées en avertissement
        Iterator<ErrorBO> it = mErrors.iterator();
        ErrorBO lErrorBO = null;
        while ( it.hasNext() )
        {
            lErrorBO = it.next();
            if ( lErrorBO.getLevel().equals( ErrorBO.CRITICITY_FATAL ) )
            {
                lErrorBO.setLevel( ErrorBO.CRITICITY_WARNING );
            }
        }

    }
}
