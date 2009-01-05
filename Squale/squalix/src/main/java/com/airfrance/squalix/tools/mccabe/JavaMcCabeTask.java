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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalix.util.csv.CSVParser;
import com.airfrance.squalix.util.parser.J2EEParser;

/**
 * Exécute l'analyse McCabe sur un projet java. L'environnement McCabe doit être correctement initialisé avant le
 * lancement de la tâche, ou la commande "cli" appelée doit pointer vers un script qui le met en place.<br>
 * <br>
 * Il est important que la génération des rapports débute par celle du niveau méthode, afin de pouvoir correctement
 * créer les composants ClassBO si nécessaire.<br>
 * L'ordre des rapports dans le configuration est respécté.
 */
public class JavaMcCabeTask
    extends OOMcCabeTask
    implements CSVParser.CSVHandler
{

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( JavaMcCabeTask.class );

    /**
     * Constructeur
     */
    public JavaMcCabeTask()
    {
        mName = "JavaMcCabeTask";
    }

    /**
     * {@inheritDoc} On doit utiliser un parser java
     * 
     * @see com.airfrance.squalix.tools.mccabe.AbstractMcCabeTask#setParser()
     */
    public void setParser()
    {
        mParser = new J2EEParser( mProject );
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.mccabe.AbstractMcCabeTask#setClassTemplate()
     */
    public void setClassTemplate()
    {
        mClassTemplate = "csv.java.template.class";
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.mccabe.AbstractMcCabeTask#createReport(java.lang.String)
     */
    protected void createReport( String pReport )
        throws Exception
    {
        // Génération du rapport
        super.createReport( pReport );
        // Dans le cas d'un rapport de type classe
        // on va lire ce rapport pour extraire les noms de classe
        if ( pReport.startsWith( McCabeMessages.getString( "reports.profile.class" ) ) )
        {
            String fileName = computeReportFileName( pReport );
            LOGGER.info( McCabeMessages.getString( "logs.java.class.preprocess", fileName ) );
            // Lecture du contenu de ce fichier pour en extraire les noms de
            // classes et les stocker dans le JavaParser
            CSVParser parser = new CSVParser( McCabeMessages.getString( "csv.config.file" ) );
            parser.parseLines( McCabeMessages.getString( mClassTemplate ), fileName, this );
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.util.csv.CSVParser.CSVHandler#processLine(java.util.ArrayList)
     */
    public void processLine( List pLine )
    {
        ( (J2EEParser) mParser ).addKnownClass( (String) pLine.get( 0 ) );
    }
}
