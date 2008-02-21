package com.airfrance.squalix.tools.umlquality;

import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.umlquality.UMLQualityMetricsBO;

import com.airfrance.squalix.util.csv.CSVParser;
import com.airfrance.squalix.util.repository.ComponentRepository;
/**
 * Objet chargé de faire persister les composants identifiés par UMLQuality (les composants) 
 * @author sportorico
 *
 */
public class UMLQualityPersistor {
 
    /**
     * Audit durant lequel l'analyse est effectuée
     */
    private AuditBO mAudit = null;
    /**
     * Session Persistance
     */
    private ISession mSession = null;
    /**
     * Chemin du fichier à parser
     */
    private String mReportFileName = null;
    
    /**
     * Adaptateur
     */
    private UMLQualityBeanAdaptator mAdaptator;
    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog(UMLQualityPersistor.class);
    /**
     * Constructeur.
     * @param pProject projet.
     * @param pAudit audit encadrant l'exécution.
     * @param pSession la session de perisistance utilisée par la tâche.
     * @throws JrafDaoException si une session de peristance ne peut être créée.
     * 
     */
    public UMLQualityPersistor(final ProjectBO pProject, final AuditBO pAudit, final ISession pSession) throws JrafDaoException {
    
        mSession = pSession;
        mAudit = pAudit;
        ComponentRepository repository = new ComponentRepository(pProject, mSession);
        mAdaptator= new UMLQualityBeanAdaptator(pProject,repository);
    }
        
    /**
     * Parse le rapport des métriques des différents composant uml et<br>
     * fait persister les resultats(metrique ou mesures)issus du parsage tout<br>
     * en éliminant tout ce qui ne sont pas conforme.
     * @param pFilename chemin du fichier rapport.
     * @param pComponentName nom du composant UML(model, class, package, interface) .
     * @throws Exception si un problème de parsing apparait.
     * @return le nombre de résultats de niveau resultats metrique
     *
     */
    public int parseComponentReport(final String pFilename,final String pComponentName) throws Exception {
        
        mReportFileName = pFilename;
        LOGGER.debug(UMLQualityMessages.getString("logs.debug.report_parsing_"+pComponentName)
                    + mReportFileName);
               
        
        CSVParser csvparser = new CSVParser(UMLQualityMessages.getString("csv.config.file"));
        //  Récupérer les beans issus du nom de rapport mReportFileName 
        Collection classResults = csvparser.parse( 
                  UMLQualityMessages.getString("csv.template."+pComponentName)
                  , mReportFileName);
        Object [] tab=classResults.toArray();
        Arrays.sort(tab);// trie les beans selon le nom du composant. ceci nous permet de 
                         // traiter un composant parent avant ses fils
                                           
             
        UMLQualityMetricsBO bo = null; //un resultat metrique
        Collection col=new Vector();
     
        for(int i=0;i<tab.length;i++) {//parcours de liste des beans(résultats métriques) triés
        
            bo = (UMLQualityMetricsBO) tab[i];
            bo.setAudit(mAudit);
            bo.setTaskName("UMLQualityTask");
           
           if(mAdaptator.adaptComponentResult(bo)){
                col.add(bo);
            }
        }
        
        MeasureDAOImpl.getInstance().saveAll(mSession, col);//enregistre les beans dans la session
        mSession.commitTransactionWithoutClose();
        mSession.beginTransaction();
        return classResults.size();
    }
}
