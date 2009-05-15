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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentType;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAMethodMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAClassMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAProjectMetricsBO;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.util.csv.CSVParser;
import com.airfrance.squalix.util.parser.CobolParser;
import com.airfrance.squalix.util.repository.ComponentRepository;
import com.airfrance.squalecommon.util.mapping.Mapping;

/**
 * Objet chargé de faire persister les métriques McCabe pour les projets Cobol.
 */
public class CobolMcCabePersistor
    extends McCabePersistor
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( CobolMcCabePersistor.class );

    /**
     * Adaptateur.
     */
    private CobolMcCabeAdaptator mAdaptator;

    /**
     * Le parser.
     */
    private CobolParser mParser;

    /**
     * Nombre de module du projet.
     */
    private int mNumberOfModules;

    /**
     * Nombre de programmes.
     */
    private int mNumberOfPrograms;

    /**
     * Nombre total de ligne du projet.
     */
    private int mTotalNl;

    /**
     * Constructeur.
     * 
     * @param pSession la session de persistence utilisée par la tâche.
     * @param pDatas la liste des paramètres temporaires du projet
     * @param pTaskName le nom de la tache
     * @param pAudit l'audit en cours
     * @param pConfiguration la configuration
     * @param pCobolParser le parser Cobol
     */
    public CobolMcCabePersistor( final ISession pSession, final TaskData pDatas, final String pTaskName,
                                 final AuditBO pAudit, final McCabeConfiguration pConfiguration,
                                 final CobolParser pCobolParser )
    {
        super( pSession, pDatas, pTaskName, pAudit, pConfiguration );
        mParser = pCobolParser;
        ComponentRepository repository = new ComponentRepository( mProject, mSession );
        mAdaptator = new CobolMcCabeAdaptator( repository, mParser );
    }

    /**
     * Analyse le rapport des métriques McCabe et enregistre les composants et métriques associées.
     * 
     * @param pFilename le nom du fichier des métriques rapportées par McCabe.
     * @throws Exception si exception
     */
    public void parseCobolReport( final String pFilename )
        throws Exception
    {
        LOGGER.info( McCabeMessages.getString( "logs.debug.report_parsing_module" ) + pFilename );
        // chargement du modèle csv de rapport McCabe pour le Cobol
        CSVParser csvparser = new CSVParser( McCabeMessages.getString( "csv.config.file" ) );
        // analyse du rapport des métriques et création des objets métier des métriques
        Collection<McCabeQAMethodMetricsBO> moduleResults = new ArrayList<McCabeQAMethodMetricsBO>();
        moduleResults = csvparser.parse( McCabeMessages.getString( "csv.template.cobol" ), pFilename );
        // association des métriques avec le composant Cobol avant enregistrement en base
        McCabeQAMethodMetricsBO bo = null;
        Iterator<McCabeQAMethodMetricsBO> it = moduleResults.iterator();
        Set<String> lSetOfProgramNames = new TreeSet<String>();
        while ( it.hasNext() )
        {
            bo = it.next();
            bo.setAudit( mAudit );
            bo.setTaskName( mTaskName );
            // association des métriques avec les composants Cobol
            String lPrgName = mAdaptator.adaptModuleResult( bo );
            // ajout du nom du programme dans l'ensemble (si non présent)
            lSetOfProgramNames.add( lPrgName );
            // incrément du nombre de modules
            mNumberOfModules++;
            // incrément du nombre de lignes
            mTotalNl += bo.getNl();
        }
        // calcul du nombre final de programmes analysés
        mNumberOfPrograms = lSetOfProgramNames.size();

        // enregistrement en base des métriques McCabe
        LOGGER.info( McCabeMessages.getString( "logs.debug.report_parsing_database" ) );
        MeasureDAOImpl.getInstance().saveAll( mSession, moduleResults );
        mSession.commitTransactionWithoutClose();
        mSession.beginTransaction();
        LOGGER.info( McCabeMessages.getString( "logs.debug.report_parsing_end" ) );
    }

    /**
     * Calcule les métriques au niveau Programme à partir de l'arbre des composants
     */
    public void calculateCobolProgramMetrics()
        throws Exception
    {
        LOGGER.info( McCabeMessages.getString( "logs.debug.program_processing" ) );
        // Récupération du Package Root du projet COBOL
        ProjectBO lProject = mParser.getProject();
        Collection lListRootPackage =
            AbstractComponentDAOImpl.getInstance().findAllChildrenWhere( mSession, lProject.getId(), mAudit.getId(),
                                                                         "Package" );

        if ( lListRootPackage.size() != 1 )
        {
            // Erreur car un projet COBOL ne peut contenir qu'un root package SOURCE par audit
            LOGGER.error( "Zero or more than one Root Package detected. Impossible to calculate COBOL Program Metric." );
        }
        else
        {
            PackageBO lRootPackage = (PackageBO) lListRootPackage.toArray()[0];
            Collection lListProgram =
                AbstractComponentDAOImpl.getInstance().findAllChildrenWhere( mSession, lRootPackage.getId(),
                                                                             mAudit.getId(), "Class" );
            generateProgramMetrics( lListProgram );
        }
    }

    /**
     * Génération des métriques au niveau Programme
     * 
     * @param listProgram liste des programmes
     * @return true si les métriques ont été générés
     */
    private void generateProgramMetrics( Collection listProgram )
        throws Exception
    {
        LOGGER.info( McCabeMessages.getString( "logs.debug.program_calculating" ) );
        Collection<McCabeQAClassMetricsBO> programResults = new ArrayList<McCabeQAClassMetricsBO>();
        Iterator itProgram = listProgram.iterator();
        while ( itProgram.hasNext() )
        {
            ClassBO program = (ClassBO) itProgram.next();
            // Récupération des métriques des modules du programme
            Collection<McCabeQAMethodMetricsBO> programMeasureList =
                MeasureDAOImpl.getInstance().findWhereParent( mSession, program.getId(), mAudit.getId(),
                                                              "MethodMetrics" );
            // Si ce programme n'existe plus pour cet audit, pas de mesures retournées
            if ( programMeasureList.size() > 0 )
            {
                // Init des métriques Programmes qu'on va calculer
                McCabeQAClassMetricsBO programMetric = new McCabeQAClassMetricsBO();
                programMetric.setTaskName( mTaskName );
                programMetric.setAudit( mAudit );
                programMetric.setComponent( program );
                programMetric.setComponentName( program.getName() );
                // en COBOL, on a 1 mesure / composant
                programMetric.setWmc( programMeasureList.size() );
                int sumNbLoc = 0;
                int sumNbLoCom = 0;
                boolean deadM = false;
                int sumVg = 0;
                int maxVg = 0;
                // Itération sur les mesures des modules
                Iterator itMesure = programMeasureList.iterator();
                while ( itMesure.hasNext() )
                {
                    McCabeQAMethodMetricsBO mesure = (McCabeQAMethodMetricsBO) itMesure.next();
                    sumNbLoc += mesure.getNsloc();
                    sumNbLoCom += mesure.getNcloc();
                    sumVg += mesure.getVg();
                    maxVg = Math.max( maxVg, mesure.getVg() );
                    if ( deadM == false && mesure.getDeadCode() == 1 )
                    {
                        deadM = true;
                    }
                }
                // Calcul pour le programme puis ajout dans la collection
                programMetric.setSloc( sumNbLoc );
                programMetric.setCloc( sumNbLoCom );
                programMetric.setSumvg( sumVg );
                programMetric.setDeadModule( deadM );
                programMetric.setMaxvg( maxVg );
                programResults.add( programMetric );
            }
        }
        // Persistance de la collection obtenue
        LOGGER.info( McCabeMessages.getString( "logs.debug.program_processing_database" ) );
        MeasureDAOImpl.getInstance().saveAll( mSession, programResults );
        mSession.commitTransactionWithoutClose();
        mSession.beginTransaction();
        LOGGER.info( McCabeMessages.getString( "logs.debug.program_processing_end" ) );
    }

    /**
     * Crée les résultats de niveau projet et les enregistre en base.
     */
    public void persistProjectResult()
    {
        LOGGER.info( McCabeMessages.getString( "logs.debug.project_database" ) );
        McCabeQAProjectMetricsBO metrics = new McCabeQAProjectMetricsBO();
        // Création des métriques de niveau projet
        metrics.setComponent( mProject );
        metrics.setAudit( mAudit );
        metrics.setTaskName( mTaskName );
        metrics.setNumberOfClasses( new Integer( mNumberOfPrograms ) );
        metrics.setNumberOfMethods( new Integer( mNumberOfModules ) );
        metrics.setTotalNl( mTotalNl );
        try
        {
            MeasureDAOImpl.getInstance().create( mSession, metrics );
        }
        catch ( JrafDaoException e )
        {
            LOGGER.error( e, e );
        }

    }
}
