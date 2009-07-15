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
package com.airfrance.squalix.tools.pmd;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sourceforge.pmd.FileDataSource;
import net.sourceforge.pmd.Language;
import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RuleSet;
import net.sourceforge.pmd.RuleSetFactory;
import net.sourceforge.pmd.RuleSetNotFoundException;
import net.sourceforge.pmd.RuleSets;
import net.sourceforge.pmd.SourceType;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.core.exception.ConfigurationException;
import com.airfrance.squalix.util.buildpath.BuildProjectPath;
import com.airfrance.squalix.util.file.FileUtility;

/**
 * Traitement PMD
 */
public abstract class AbstractPmdProcessing
{
    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( AbstractPmdProcessing.class );

    /**
     * Le dialect des sources
     */
    protected String mDialect;

    /**
     * Obtention du langage
     * 
     * @return langage
     */
    protected abstract String getLanguage();

    /**
     * Obtention sur type de source
     * 
     * @return type de source
     */
    protected abstract SourceType getSourceType();

    /**
     * Obtention de l'extension
     * 
     * @return extension à traiter
     */
    protected abstract String[] getExtensions();

    /**
     * Construction des répertoires à analyser
     * 
     * @param pData données de la tâche
     * @param pProjectParams paramètres du projet
     * @return liste des fichiers à traiter
     * @throws ConfigurationException si erreur
     * @throws IOException si erreur
     */
    protected List buildFilesToProcess( TaskData pData, MapParameterBO pProjectParams )
        throws ConfigurationException, IOException
    {

        // On prend le view path
        String viewPath = (String) pData.getData( TaskData.VIEW_PATH );
        if ( viewPath == null )
        {
            String message = PmdMessages.getString( "exception.variable.not_found", TaskData.VIEW_PATH );
            LOGGER.error( message );
            // Renvoi d'une exception de configuration
            throw new ConfigurationException( message );
        }
        // Pour chaque répertoire source on ajoute celui-ci
        // On récupère les chemins relatifs des répertoires contenant les .java du projet
        ListParameterBO sources = getSourcesDirs( pProjectParams );
        if ( sources == null )
        {
            String message = PmdMessages.getString( "exception.sources.notset", getLanguage() );
            LOGGER.error( message );
            // Renvoi d'une exception de configuration
            throw new ConfigurationException( message );
        }

        // Prise en compte des patterns d'exclusion et d'inclusion
        ListParameterBO included =
            (ListParameterBO) pProjectParams.getParameters().get( ParametersConstants.INCLUDED_PATTERNS );
        ListParameterBO excluded =
            (ListParameterBO) pProjectParams.getParameters().get( ParametersConstants.EXCLUDED_PATTERNS );
        List srcs = BuildProjectPath.buildProjectPath( viewPath, sources.getParameters() );
        List includedFileNames =
            FileUtility.getIncludedFiles( viewPath, srcs, included, excluded, getExcludedDirs( pProjectParams ),
                                          getExtensions() );

        // Conversion en fichiers
        ArrayList result = new ArrayList();
        for ( Iterator it = includedFileNames.iterator(); it.hasNext(); )
        {
            String name = (String) it.next();
            result.add( new FileDataSource( new File( name ) ) );
        }
        return result;
    }

    /**
     * Obtention des sources
     * 
     * @param pProjectParams paramètres du projet
     * @return sources sous la forme de ListParameterBO(StringParameterBO)
     */
    protected ListParameterBO getSourcesDirs( MapParameterBO pProjectParams )
    {
        return (ListParameterBO) pProjectParams.getParameters().get( ParametersConstants.SOURCES );
    }

    /**
     * Obtention des répertoires exclus de la compilation
     * 
     * @param pProjectParams paramètres du projet
     * @return répertories exclus sous la forme de ListParameterBO(StringParameterBO)
     */
    protected ListParameterBO getExcludedDirs( MapParameterBO pProjectParams )
    {
        return (ListParameterBO) pProjectParams.getParameters().get( ParametersConstants.EXCLUDED_DIRS );
    }

    /**
     * Obtention du dialect
     * 
     * @param pProjectParams paramètres du projet
     * @return dialect sous la forme de StringParameterBO
     */
    protected StringParameterBO getDialect( MapParameterBO pProjectParams )
    {
        return (StringParameterBO) pProjectParams.getParameters().get( ParametersConstants.DIALECT );
    }

    /**
     * Détection des copier-coller
     * 
     * @param pData données de tâche
     * @param pProjectParams paramètres de projet
     * @param pRuleSetFile jeu de règles à appliquer
     * @return rapport d'exécution
     * @throws ConfigurationException si erreur
     * @throws IOException si erreur
     */
    public Report process( TaskData pData, MapParameterBO pProjectParams, File pRuleSetFile )
        throws IOException, ConfigurationException
    {
        RuleContext ctx = new RuleContext();
        Report report = new Report();
        ctx.setReport( report );
        report.start();
        RuleSetFactory ruleSetFactory = new RuleSetFactory();
        // On crée le ruleset
        RuleSets rulesets;
        try
        {
            rulesets = new RuleSetFactory().createRuleSets( pRuleSetFile.getAbsolutePath() );
            for ( Iterator it = rulesets.getRuleSetsIterator(); it.hasNext(); )
            {
                ( (RuleSet) it.next() ).setLanguage( Language.getByName( getLanguage() ) );
            }
        }
        catch ( RuleSetNotFoundException e )
        {
            throw new ConfigurationException( PmdMessages.getString( "exception.ruleset.notfound",
                                                                     pRuleSetFile.getAbsolutePath() ), e );
        }
        // On récupère le dialect
        StringParameterBO dialect = getDialect( pProjectParams );
        if ( dialect == null )
        {
            String message = PmdMessages.getString( "exception.dialect.notset", getLanguage() );
            LOGGER.error( message );
            // Renvoi d'une exception de configuration
            throw new ConfigurationException( message );
        }
        mDialect = dialect.getValue();
        PMD pmd = new PMD();
        SourceType type = getSourceType();
        if ( type == null )
        {
            throw new ConfigurationException( PmdMessages.getString( "exception.dialect.notfound", mDialect ) );
        }
        pmd.setJavaVersion( type );
        pmd.setExcludeMarker( PMD.EXCLUDE_MARKER );

        // Traitement de PMD
        pmd.processFiles( buildFilesToProcess( pData, pProjectParams ), ctx, rulesets,
                          false, // mode debug
                          true, // simplification des noms de fichier avec le préfixe suivant
                          (String) pData.getData( TaskData.VIEW_PATH ),
                          new InputStreamReader( System.in ).getEncoding() );
        report.end();
        return report;
    }
}
