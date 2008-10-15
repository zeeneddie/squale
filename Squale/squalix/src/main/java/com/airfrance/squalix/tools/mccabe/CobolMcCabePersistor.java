package com.airfrance.squalix.tools.mccabe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAMethodMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAProjectMetricsBO;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.util.csv.CSVParser;
import com.airfrance.squalix.util.parser.CobolParser;
import com.airfrance.squalix.util.repository.ComponentRepository;

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
        while ( it.hasNext() )
        {
            bo = it.next();
            bo.setAudit( mAudit );
            bo.setTaskName( mTaskName );
            // association des métriques avec les composants Cobol
            mAdaptator.adaptModuleResult( bo );
            // incrément du nombre de modules
            mNumberOfModules++;
        }

        // enregistrement en base des métriques McCabe
        LOGGER.info( McCabeMessages.getString( "logs.debug.report_parsing_database" ) );
        MeasureDAOImpl.getInstance().saveAll( mSession, moduleResults );
        mSession.commitTransactionWithoutClose();
        mSession.beginTransaction();
        LOGGER.info( McCabeMessages.getString( "logs.debug.report_parsing_end" ) );
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
