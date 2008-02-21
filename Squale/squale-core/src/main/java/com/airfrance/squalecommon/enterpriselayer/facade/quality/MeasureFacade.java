package com.airfrance.squalecommon.enterpriselayer.facade.quality;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.JFreeChart;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.enterpriselayer.IFacade;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.ApplicationDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditDisplayConfDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditGridDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MarkDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MetricDAOImpl;
import com.airfrance.squalecommon.daolayer.result.QualityResultDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.result.ResultsDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.AuditTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.result.MeasureTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditDisplayConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditGridBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.BubbleConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.DisplayConfConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.VolumetryConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.FactorResultBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MarkBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import com.airfrance.squalecommon.util.mapping.Mapping;

/**
 */
public class MeasureFacade implements IFacade {

    /**
     * Cle de tre relatif a l'historique
     */
    private static final String HISTORIC_TYPE = "tre.graph.histo";

    /** log */
    private static Log LOG = LogFactory.getLog(MeasureFacade.class);

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Permet de récupérer une valeur pour un type de résultat, un composant 
     * et un audit donné
     * Format de ResultsDTO : 1 ligne :
     * -- null en clé et liste contenant le resultat en valeur
     * @param pAudit AuditDTO contenant l'ID de l'audit
     * @param pComponent ComponentDTO contenant l'ID du composant
     * @param pKeyTRE Clé du type élémentaire de résultat
     * @return ResultsDTO correspondant à une valeur
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB4016D
     */
    public static ResultsDTO getMeasures(AuditDTO pAudit, ComponentDTO pComponent, String pKeyTRE) throws JrafEnterpriseException {
        ResultsDTO results = null;
        MeasureDAOImpl measureDAO = MeasureDAOImpl.getInstance();
        ISession session = null;

        try {
            session = PERSISTENTPROVIDER.getSession();

            Long idComponent = new Long(pComponent.getID());
            Long idAudit = new Long(pAudit.getID());

            MeasureBO measure = measureDAO.load(session, idComponent, idAudit, Mapping.getMetricClass(pKeyTRE));

            results = MeasureTransform.bo2dto(measure, pKeyTRE);

        } catch (JrafDaoException e) {
            FacadeHelper.convertException(e, MeasureFacade.class.getName() + ".getMeasures");
        } finally {
            FacadeHelper.closeSession(session, MeasureFacade.class.getName() + ".getMeasures");
        }

        return results;
    }

    /**
     * Permet de récupérer une liste de valeurs pour une liste de types de résultat, 
     * un type de composant et un audit donné
     * Format de ResultsDTO : 2 lignes :
     * -- null en clé et liste des clés des TREs en valeur
     * -- ComponentDTO en clé et liste des résultats associées en valeur
     * @param pAudit AuditDTO contenant l'ID de l'audit
     * @param pComponent ComponentDTO contenant l'ID du composant
     * @return ResultsDTO
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB40181
     */
    public static ResultsDTO getAllMeasures(AuditDTO pAudit, ComponentDTO pComponent) throws JrafEnterpriseException {
        // Déclaration du ResultsDTO à retourner
        ResultsDTO results = null;
        Collection trClasses = null;
        MeasureDAOImpl measureDAO = MeasureDAOImpl.getInstance();
        // Session Hibernate
        ISession session = null;

        try {
            // Récupération d'une session
            session = PERSISTENTPROVIDER.getSession();

            // Récupération des id de l'audit et du composant
            Long idComponent = new Long(pComponent.getID());
            Long idAudit = new Long(pAudit.getID());
            // Récupération des mesures souhaitées
            Collection measures = measureDAO.findWhere(session, idComponent, idAudit);
            // transformation de la liste de mesures en ResultsDTO
            results = MeasureTransform.bo2dtoByTRE(null, measures, pComponent);
            // Pour la 3.0, on conserve les mesures mcCabe concernant le nombre de méthodes et de classes sur un projet
            // On supprime (ATTENTION : seulement de l'affichage) donc les données RSM équivalentes pour ne pas faire doublon
            // Toutefois on garde le stockage des données en base
            // On récupère les index des clés pour pouvoir supprimer les valeurs aussi
            int indexClasses = ((List) (results.getResultMap().get(null))).indexOf("rsm.project.numberOfClasses");
            int indexMeth = ((List) (results.getResultMap().get(null))).indexOf("rsm.project.numberOfMethods");
            // Supprime les clés
             ((List) (results.getResultMap().get(null))).remove("rsm.project.numberOfClasses");
            ((List) (results.getResultMap().get(null))).remove("rsm.project.numberOfMethods");
            // Supprime les valeurs
            //On supprime d'abord le plus grand index pour éviter les décalages
            if (indexClasses != -1) {
                if (indexMeth != -1) {
                    if (indexClasses > indexMeth) {
                        ((List) (results.getResultMap().get(pComponent))).remove(indexClasses);
                        ((List) (results.getResultMap().get(pComponent))).remove(indexMeth);
                    } else {
                        ((List) (results.getResultMap().get(pComponent))).remove(indexMeth);
                        ((List) (results.getResultMap().get(pComponent))).remove(indexClasses);
                    }
                } else {
                    ((List) (results.getResultMap().get(pComponent))).remove(indexClasses);
                }
            } else {
                if (indexMeth != -1) {
                    ((List) (results.getResultMap().get(pComponent))).remove(indexMeth);
                }
            }

        } catch (JrafDaoException e) {
            FacadeHelper.convertException(e, MeasureFacade.class.getName() + ".getMeasuresByTRE");
        } finally {
            FacadeHelper.closeSession(session, MeasureFacade.class.getName() + ".getMeasuresByTRE");
        }
        return results;
    }

    /**
     * Permet de récupérer une liste des "tops" composant / measure pour 
     * un audit et un tre donnés
     * Format de ResultsDTO : 2 lignes :
     * -- null en clé et liste des ComponentDTOs 
     * -- AuditDTO en clé et liste des résultats associées en valeur
     * @param pProject projet 
     * @param pComponentType Type de composant (Method ou Class)
     * @param pAudit Audit
     * @param pTre Tre à retrouver et trié
     * @param pMax nombre maxi de resultat retouné
     * @return ResultsDTO trié
     * @throws JrafEnterpriseException exception JRAF
     */
    public static ResultsDTO getTop(ComponentDTO pProject, String pComponentType, AuditDTO pAudit, String pTre, Integer pMax) throws JrafEnterpriseException {
        ResultsDTO results = null;
        MeasureDAOImpl measureDAO = MeasureDAOImpl.getInstance();
        // Session Hibernate
        ISession session = null;
        try {
            session = PERSISTENTPROVIDER.getSession();
            List component_measures = (List) measureDAO.findTop(session, pProject.getID(), Mapping.getComponentClass(pComponentType), pAudit.getID(), pTre, pMax);
            if (component_measures.size() > 0) {
                // Transformation de la liste de mesures en ResultDTO
                results = MeasureTransform.bo2dtoByAuditOrComponent(null, component_measures, pAudit);
            }
        } catch (JrafDaoException e) {
            FacadeHelper.convertException(e, MeasureFacade.class.getName() + ".getMeasuresByTRE");
        } finally {
            FacadeHelper.closeSession(session, MeasureFacade.class.getName() + ".getMeasuresByTRE");
        }
        return results;
    }

    /**
     * Permet de récupérer des valeurs pour une liste de types de résultat, une liste 
     * de types de composant et un audit donnés
     * Format de ResultsDTO : 2 lignes :
     * -- null en clé et liste des clés de TREs en valeur
     * -- ComponentDTO en clé et liste des résultats associées en valeur ( n fois )
     * @param pAudit AuditDTO contenant l'ID de l'audit
     * @param pTreKey l'id du tre correspondant à la pratique
     * @param pTREKeys liste des IDs des types de résultats souhaités
     * @param pComponents liste de ComponentDTO souhaités
     * @return ResultsDTO
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB401C0
     */
    public static ResultsDTO getMeasuresByTREAndComponent(Long pTreKey, List pTREKeys, List pComponents, AuditDTO pAudit) throws JrafEnterpriseException {
        ResultsDTO results = null;
        Collection trClasses = null;
        MeasureDAOImpl measureDAO = MeasureDAOImpl.getInstance();
        // Session Hibernate
        ISession session = null;

        try {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            // Récupération de la collection de classes de TREs
            trClasses = getTREClasses(pTREKeys);

            if (trClasses.size() > 0) {
                // initialisation du ResultsDTO et ajout des clés
                results = new ResultsDTO();
                results.put(null, pTREKeys);
                Iterator itComp = pComponents.iterator();
                while (itComp.hasNext()) {
                    // Pour chaque composant :
                    ComponentDTO component = (ComponentDTO) itComp.next();
                    Long idComponent = new Long(component.getID());
                    Long idAudit = new Long(pAudit.getID());
                    // Récupération des mesures souhaitées
                    Collection measures = measureDAO.findWhere(session, idComponent, idAudit, trClasses);
                    if (measures.size() > 0) {
                        // ajout au Résult DTO des mesures souhaitées
                        results = MeasureTransform.bo2dtoByTRE(results, measures, pTREKeys, component);
                        // On récupère aussi la note de la pratique associée à chaque composant
                        // que l'on place en début de liste
                        MarkBO mark = MarkDAOImpl.getInstance().load(session, new Long(component.getID()), new Long(pAudit.getID()), pTreKey);
                        ((List) results.getResultMap().get(component)).add(0, new Float(mark.getValue()));
                    }
                }
            }
        } catch (JrafDaoException e) {
            LOG.error(MeasureFacade.class.getName() + ".getMeasuresByTREAndComponent", e);
        } finally {
            FacadeHelper.closeSession(session, MeasureFacade.class.getName() + ".getMeasuresByTREAndComponent");
        }
        return results;
    }

    /**
     * Récupère les classes de TRE à partir de la liste de TREs
     * @param pTREKeys liste de clés de TREs
     * @return une collection de classe de TRE
     */
    private static Collection getTREClasses(List pTREKeys) {
        Set trClasses = new HashSet();
        Iterator it = pTREKeys.iterator();
        while (it.hasNext()) {
            // pour chaque clé de type de résultat
            String treKey = (String) it.next();
            // on ajoute la classe correspondante à la liste de classe de TRE
            trClasses.add(Mapping.getMetricClass(treKey));
        }

        return trClasses;
    }

    /**
     * Constructeur vide
     * @roseuid 42CBFFB40203
     */
    private MeasureFacade() {
    }

    /**
     * Creation d'une mesure de type Kiviat pour une application
     * @param pAuditId  Id de l'audit
     * @throws JrafEnterpriseException  en cas de pb Hibernate
     * @return les données nécessaires à la conception du Kiviat via une Applet soit : 
     * Map dont la clé contient les projets et la valeur contient une map (facteurs, notes)
     */
    public static Map getApplicationKiviat(Long pAuditId) throws JrafEnterpriseException {
        MeasureDAOImpl measureDAO = MeasureDAOImpl.getInstance();
        // Session Hibernate
        ISession session = null;
        Map result = new HashMap();
        try {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            ApplicationBO application = (ApplicationBO) ApplicationDAOImpl.getInstance().loadByAuditId(session, pAuditId);
            Iterator itp = application.getChildren().iterator();
            while (itp.hasNext()) {
                // On ajoute les notes de chaque projets sur le kiviat
                ProjectBO project = (ProjectBO) itp.next();
                SortedMap values = new TreeMap();
                Long idProject = new Long(project.getId());
                // recupere les facteurs du projet
                Collection factorResults = QualityResultDAOImpl.getInstance().findWhere(session, idProject, pAuditId);
                // et cree le map nom => note correspondant
                Iterator itf = factorResults.iterator();
                while (itf.hasNext()) {
                    FactorResultBO factor = (FactorResultBO) itf.next();
                    // le -1 est traité directement par le kiviatMaker
                    Float value = new Float(factor.getMeanMark());
                    // le nom internationalisé est géré dans le kiviatMaker
                    String name = factor.getRule().getName();
                    values.put(name, value);
                }
                result.put(project.getName(), values);
            }

        } catch (Exception e) {
            FacadeHelper.convertException(e, MeasureFacade.class.getName() + ".getMeasures");
        } finally {
            FacadeHelper.closeSession(session, MeasureFacade.class.getName() + ".getMeasures");
        }
        return result;
    }

    /**
     * Creation d'une mesure de type Kiviat pour un projet
     * @param pProjectId Id du projet
     * @param pAuditId  Id de l'audit
     * @param pAllFactors tous les facteurs (= "true") ou seulement ceux ayant une note ?
     * @throws JrafEnterpriseException  en cas de pb Hibernate
     * @return tableau d'objets : la map des données + le booléen pour affichage de la case à cocher tous les facteurs 
     */
    public static Object[] getProjectKiviat(Long pProjectId, Long pAuditId, String pAllFactors) throws JrafEnterpriseException {
        Map result = new HashMap();
        JFreeChart projectKiviat = null;
        MeasureDAOImpl measureDAO = MeasureDAOImpl.getInstance();
        // Session Hibernate
        ISession session = null;
        // Booléen conditonnanant l'affichage de la case à cocher "tous les facteurs" dans la page Jsp
        boolean displayCheckBoxFactors = true;
        try {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            // On ajoute les notes de chaque projets sur le kiviat
            ProjectBO project = (ProjectBO) ProjectDAOImpl.getInstance().load(session, pProjectId);
            SortedMap values = new TreeMap();
            // recupere les facteurs du projet
            Collection factorResults = QualityResultDAOImpl.getInstance().findWhere(session, pProjectId, pAuditId);
            // et cree le map nom => note correspondant
            Iterator it = factorResults.iterator();
            ArrayList nullValuesList = new ArrayList();
            while (it.hasNext()) {
                FactorResultBO factor = (FactorResultBO) it.next();
                // le -1 est traité directement par le kiviatMaker
                Float value = new Float(factor.getMeanMark());
                // ajoute la note dans le titre
                // TODO prendre le véritable nom du facteur                 
                String name = factor.getRule().getName();
                if (value.floatValue() >= 0) {
                    // avec 1 seul chiffre aprés la virgule
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMinimumFractionDigits(1);
                    nf.setMaximumFractionDigits(1);
                    name = name + " (" + nf.format(value) + ")";
                } else {
                    // Mémorisation temporaire des facteurs pour lesquels les notes sont nulles : sera utile si l'option
                    // "Tous les facteurs" est cochée pour afficher uniquement les facteurs ayant une note.
                    nullValuesList.add(name);
                }
                values.put(name, value);
            }
            final int FACTORS_MIN = 3;
            if (nullValuesList.size() <= 0 || values.size() <= FACTORS_MIN) {
                displayCheckBoxFactors = false;
            }
            // Seulement les facteurs ayant une note ? ==> suppression des facteurs ayant une note nulle.
            // Mais trois facteurs doivent au moins s'afficher (nuls ou pas !)       
            values = deleteFactors(values, nullValuesList, pAllFactors, FACTORS_MIN);

            // recupère le nom de l'audit 
            String name = null;
            AuditBO audit = (AuditBO) AuditDAOImpl.getInstance().load(session, pAuditId);
            if (audit.getType().compareTo(AuditBO.MILESTONE) == 0) {
                name = audit.getName();
            }
            if (null == name) {
                DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
                name = df.format(audit.getDate());
            }
            result.put(name, values);

        } catch (Exception e) {
            FacadeHelper.convertException(e, MeasureFacade.class.getName() + ".getMeasures");
        } finally {
            FacadeHelper.closeSession(session, MeasureFacade.class.getName() + ".getMeasures");
        }
        Object[] kiviatObject = { result, new Boolean(displayCheckBoxFactors)};
        return kiviatObject;
    }

    /**
     * Affichage des facteurs du Kiviat : tous les facteurs ou seulement ceux ayant une note
     * @param pValues les données à afficher : facteur + note
     * @param pNullValuesList la liste des facteurs dont la note est nulle
     * @param pAllFactors tous les facteurs (="true") ou seulement les facteurs ayant une note
     * @param pFactorsMin nombre de facteurs minimal que l'on doit afficher sur le Kiviat
     * @return values les données réellement à afficher : facteur + note
     */
    private static SortedMap deleteFactors(SortedMap pValues, ArrayList pNullValuesList, String pAllFactors, int pFactorsMin) {
        SortedMap values = new TreeMap();
        // Seulement les facteurs ayant une note ? ==> suppression des facteurs ayant une note nulle.
        // Mais trois facteurs doivent au moins s'afficher (nuls ou pas !)    
        if (pValues.size() > pFactorsMin && !pAllFactors.equals("true")) {
            // Nombre de suppressions possible             
            int nbTotalDeletions = pValues.size() - pFactorsMin;
            // Nombre de suppressions effectué
            int nbCurrentDeletions = 0;
            // Parcours de la liste des facteurs avec une note nulle, pour les supprimer de l'affichage
            Iterator itList = pNullValuesList.iterator();
            while (itList.hasNext() && nbCurrentDeletions < nbTotalDeletions) {
                String keyListe = (String) itList.next();
                pValues.remove(keyListe);
                nbCurrentDeletions += 1;
            }
        }
        values.putAll(pValues);
        return values;
    }

    /**
     * Creation d'une mesure de type Bubble pour un projet
     * @param pProjectId Id du projet
     * @param pAuditId  Id de l'audit
     * @throws JrafEnterpriseException  en cas de pb Hibernate
     * @return chart Scatterplot de l'Audit
     * @throws JrafEnterpriseException si pb Hibernate
     */
    public static Object[] getProjectBubble(Long pProjectId, Long pAuditId) throws JrafEnterpriseException {
        // Récupération des valeurs    
        int i = 0;
        Collection measures = new ArrayList(0);
        MeasureDAOImpl measureDAO = MeasureDAOImpl.getInstance();
        // Session Hibernate
        ISession session = null;
        Object[] result = null;
        try {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();

            // On récupère la configuration du scatterplot pour ce projet et cet audit
            AuditDisplayConfBO auditConf = AuditDisplayConfDAOImpl.getInstance().findConfigurationWhere(session, pProjectId, pAuditId, "bubble");
            if (null != auditConf) {
                BubbleConfBO bubble = (BubbleConfBO) auditConf.getDisplayConf();

                String[] tre = { bubble.getXTre(), bubble.getYTre()};
                // récupération des mesures distinctes rattachées à l'audit et au projet
                measures = MeasureDAOImpl.getInstance().findDistinct(session, pProjectId.longValue(), pAuditId.longValue(), tre);

                // recuperation des 2 tableaux evgs, vgs contenant les mesures          
                double[] xMeasures = new double[measures.size()];
                double[] yMeasures = new double[measures.size()];
                double[] total = new double[measures.size()];
                long[] components = new long[measures.size()];
                Iterator it = measures.iterator();
                // Constantes pour la récupérations des valeurs dans le tableau des résultats remontés de la base
                final int VG_ID = 0;
                final int EVG_ID = 1;
                final int TOTAL_ID = 2;
                final int COMPONENT_ID = 3;
                while (it.hasNext()) {
                    Object[] res = (Object[]) it.next();
                    // on ajoute ses valeurs            
                    Integer vg = (Integer) res[VG_ID];
                    yMeasures[i] = vg.doubleValue();
                    Integer evg = (Integer) res[EVG_ID];
                    xMeasures[i] = evg.doubleValue();
                    Integer tt = (Integer) res[TOTAL_ID];
                    total[i] = tt.doubleValue();
                    Long cmp = (Long) res[COMPONENT_ID];
                    components[i] = cmp.longValue();
                    i++;
                }
                // Positions des axes
                Long horizontal = new Long(bubble.getHorizontalAxisPos());
                Long vertical = new Long(bubble.getVerticalAxisPos());
                // construction des paramètres de la série
                String displayedXTre = bubble.getXTre().substring(bubble.getXTre().lastIndexOf(".") + 1);
                String displayedYTre = bubble.getXTre().substring(bubble.getXTre().lastIndexOf(".") + 1);
                String measuresKinds = "(" + displayedXTre + "," + displayedYTre + ",total)";
                result = new Object[] { horizontal, vertical, measuresKinds, yMeasures, xMeasures, total, components, yMeasures, xMeasures, total };
            }
        } catch (Exception e) {
            FacadeHelper.convertException(e, MeasureFacade.class.getName() + ".getMeasures");
        } finally {
            FacadeHelper.closeSession(session, MeasureFacade.class.getName() + ".getMeasures");
        }
        return result;
    }

    /**
     * Creation du Piechart d'une application 
     * @param pAuditId id de l'audit
     * @throws JrafEnterpriseException si pb Hibernate
     * @return les données nécessaires à la conception du PieChart via une Applet soit :
     * Map dont la clé contient les projets et la valeur le nombre de lignes du projet
     */
    public static Object[] getApplicationPieChart(Long pAuditId) throws JrafEnterpriseException {
        // Récupération des v(g) et des ev(g)        
        Collection measures;
        MeasureDAOImpl measureDAO = MeasureDAOImpl.getInstance();
        MetricDAOImpl metricDAO = MetricDAOImpl.getInstance();
        AuditDisplayConfDAOImpl auditConfDao = AuditDisplayConfDAOImpl.getInstance();
        // Session Hibernate
        ISession session = null;
        Object[] result = new Object[2];

        try {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();

            ApplicationBO application = (ApplicationBO) ApplicationDAOImpl.getInstance().loadByAuditId(session, pAuditId);
            // On retrouve les ids de tous les projets de l'application :
            Set projects = new HashSet();
            Iterator itChildren = application.getChildren().iterator();
            // Pour chaque enfant du projet :
            while (itChildren.hasNext()) {
                // Une application ne contient que des projets
                ProjectBO projectBO = (ProjectBO) itChildren.next();
                // On ajoute le projet seulement si celui-ci a été audité
                // (cas par exemple d'une désactivation)
                if (projectBO.containsAuditById(pAuditId.longValue())) {
                    projects.add(projectBO);
                }
            }

            // récupération du nombre de ligne de code de chaque projet
            Map volum = new HashMap();
            Map url = new HashMap();
            // volumétrie par grille (ne sert que pour l'export PDF)
            Map volumByGrid = new HashMap();
            Iterator itProjects = projects.iterator();
            int i = 0;
            while (itProjects.hasNext()) {
                int accLines = 0;
                Long nbLignes = new Long(0);
                ProjectBO project = (ProjectBO) itProjects.next();
                Long idProject = new Long(project.getId());
                // Récupération des mesures correspondant à l'audit et
                // au projet concerné à l'aide de la configuration de la volumétrie pour une application
                AuditDisplayConfBO auditConf =
                    (AuditDisplayConfBO) auditConfDao.findConfigurationWhere(session, idProject, pAuditId, DisplayConfConstants.VOLUMETRY_SUBCLASS, DisplayConfConstants.VOLUMETRY_APPLICATION_TYPE);
                if (null != auditConf) {
                    VolumetryConfBO volumConf = (VolumetryConfBO) auditConf.getDisplayConf();
                    // On ajoute les volumétries trouvées en fonction des tres
                    for (Iterator it = volumConf.getTres().iterator(); it.hasNext();) {
                        IntegerMetricBO metric = metricDAO.findIntegerMetricWhere(session, idProject.longValue(), pAuditId.longValue(), (String)it.next());
                        if(null != metric) {
                            accLines += ((Integer)metric.getValue()).intValue();
                        }
                    }
                    nbLignes = new Long(accLines);
                    // On ajoute la valeur au nb de ligne pour la grille du projet
                    setApplicationVolumetryByGrid(session, idProject, pAuditId, volumByGrid, nbLignes);
                } // si il n'y a pas de configuration, c'est qu'il y a un problème en base

                // Placement du nombre de lignes en regard du nom de projet si le nombre de lignes
                // et > 0
                if(0 < nbLignes.longValue()) {
                    volum.put(project.getName(), nbLignes);
                    url.put(project.getName(), idProject);
                }

                i++;
            }
            result = new Object[] { volum, url, volumByGrid };
        } catch (Exception e) {
            FacadeHelper.convertException(e, MeasureFacade.class.getName() + ".getMeasures");
        } finally {
            FacadeHelper.closeSession(session, MeasureFacade.class.getName() + ".getMeasures");
        }
        return result;
    }

    /**
     * Ajoute le nombre de lignes d'un projet pour une grille donnée
     * @param session la session
     * @param idProject l'id du projet
     * @param pAuditId l'id de l'audit
     * @param volumByGrid la map du nombre de lignes par grille
     * @param nbLines le nombre de ligne à ajouter pour la grille
     * @throws JrafDaoException si erreur
     */
    private static void setApplicationVolumetryByGrid(ISession session, Long idProject, Long pAuditId, Map volumByGrid, Long nbLines) throws JrafDaoException {
        // Récupération des grilles
        AuditGridDAOImpl auditGridDao = AuditGridDAOImpl.getInstance();
        // On récupère la grille associée au projet pour cet audit
        AuditGridBO auditGrid = auditGridDao.findWhere(session, idProject, pAuditId);
        // on ajoute la valeur au nb de ligne pour la grille du projet
        Long nbLinesforGrid = (Long) volumByGrid.get(auditGrid.getGrid().getName());
        // Si on a déjà eu un projet associé à cette grille, on fait la somme des lignes
        if (null != nbLinesforGrid) {
            volumByGrid.put(auditGrid.getGrid().getName(), new Long(nbLinesforGrid.longValue() + nbLines.longValue()));
        } else {
            volumByGrid.put(auditGrid.getGrid().getName(), nbLines);
        }

    }

    /**
     * Récupère les valeurs permettant d'avoir l'historique relatif a des cles de tre et un composant
     * @param pComponent un composant
     * @param pTreLabel label de tre
     * @param pTreKey cles de tre concerne par l'historique
     * @param pAuditDate date a partir de laquelle on souhaite recuperer tous les audits
     * @param pRuleId id de la règle qualité ou null s'il s'agit d'une mesure
     * @return La map correspondant aux points
     * @throws JrafEnterpriseException exception Jraf
     */
    public static Map getHistoricResults(ComponentDTO pComponent, String pTreKey, String pTreLabel, Date pAuditDate, Long pRuleId) throws JrafEnterpriseException {
        //Session Hibernate
        ISession session = null;
        // Initialisation des differentes Daos
        AuditDAOImpl auditDao = AuditDAOImpl.getInstance();
        List currentAudits = null; // liste des audits relatifs au composant
        Map values = null;
        try {
            session = PERSISTENTPROVIDER.getSession();
            // recuperation des audits relatifs a un composant triés par date réelle
            currentAudits = auditDao.findAfter(session, pComponent.getID(), pAuditDate);
            // Initialisation de la Map
            values = new HashMap();
            Iterator auditIterator = currentAudits.iterator();
            AuditBO currentAuditBO = null; // AuditBO courant
            Long currentAuditId = null;
            AuditDTO currentAudit = null; // audit parcouru
            ResultsDTO currentResults = null; // ResultsDTO courant recupere
            while (auditIterator.hasNext()) {
                // recuperation de l'audit parcouru
                currentAuditBO = (AuditBO) auditIterator.next();
                currentAuditId = new Long(currentAuditBO.getId());
                // recuperation de l'application associée a l'audit
                ApplicationBO currentApplication = (ApplicationBO) ApplicationDAOImpl.getInstance().loadByAuditId(session, currentAuditId);
                currentAudit = AuditTransform.bo2Dto(currentAuditBO, currentApplication.getId());
                if (pRuleId == null) {
                    currentResults = MeasureFacade.getMeasures(currentAudit, pComponent, pTreKey);
                } else {
                    currentResults = QualityResultFacade.getQResults(currentAudit, pComponent, pRuleId);
                }
                // recuperation de la valeur associee du ResultsDTO
                Object objectValue = ((ArrayList) currentResults.getResultMap().get(null)).get(0);
                // On ajoute la valeur à la table (en fonction du type de l'objet)
                addHistoricValues(values, currentAudit.getRealDate(), objectValue);
            }
        } catch (JrafDaoException e) {
            FacadeHelper.convertException(e, MeasureFacade.class.getName() + ".getHistoricResults");
        } finally {
            FacadeHelper.closeSession(session, MeasureFacade.class.getName() + ".getHistoricResults");
        }
        return values;
    }

    /**
     * Ajoute une entrée à la table des valeurs du graphe historique
     * @param values la table des valeurs
     * @param key la clé
     * @param value la valeur
     */
    private static void addHistoricValues(Map values, Date key, Object value) {
        if (value != null) {
            // Petit test à faire sur le type de la note
            // Dans le cas ou c'est un booléen, on met alors 1 si le booléen
            // est à true, 0 sinon
            // Dans le cas d'une note, on met la valeur
            // Initialisation à 0 par défaut
            Number numberToAdd = new Integer("0");
            if (value instanceof Boolean) {
                // booleén valant "true"
                if (((Boolean) value).booleanValue()) {
                    numberToAdd = new Integer("1");
                }
                //else booléen valant false, rien à faire car valeur par défaut
            } else if(value.toString().matches("[0-9]+(\\.[0-9]+)?")){ // Récupère directement le nombre
                numberToAdd = (Number) value;
            }
            if (numberToAdd.intValue() != -1) {
                values.put(key, numberToAdd);
            }
        }
    }

    /**
     * Permet de dessiner l'historique relatif a des cles de tre et un composant
     * @param pComponent un composant
     * @param pTreLabel label de tre
     * @param pTreKey cles de tre concerne par l'historique
     * @param pAuditDate date a partir de laquelle on souhaite recuperer tous les audits
     * @param pRuleId id de la règle qualité ou null s'il s'agit d'une mesure
     * @return GraphDTO
     * @throws JrafEnterpriseException exception Jraf
     */
    public static Object[] getHistoricGraph(ComponentDTO pComponent, String pTreKey, String pTreLabel, Date pAuditDate, Long pRuleId) throws JrafEnterpriseException {
        Object[] result = new Object[2];
        // Session Hibernate
        ISession session = null;
        // Initialisation des differentes Daos
        AuditDAOImpl auditDao = AuditDAOImpl.getInstance();
        List currentAudits = null; // liste des audits relatifs au composant
        Map values = MeasureFacade.getHistoricResults(pComponent, pTreKey, pTreLabel, pAuditDate, pRuleId);
        result = new Object[] { pTreLabel, values, HISTORIC_TYPE };
        return result;

    }

    /**
     * Retourne les mesures associées à la volumétrie d'un projet d'après sa configuration en base
     * pour un audit donné sous la forme de ResultsDTO :
     * ResultsDTO.getResultMap.get(null) --> les noms des tres
     * ResultsDTO.getResultMap.get(pProject) --> les valeurs associées
     * @param pAuditId l'id de l'audit
     * @param pProject le projet
     * @return l'ensemble des mesures de la volumétrie du projet
     * @throws JrafEnterpriseException si erreur
     */
    public static ResultsDTO getProjectVolumetry(Long pAuditId, ComponentDTO pProject) throws JrafEnterpriseException {
        // Déclaration du ResultsDTO à retourner
        ResultsDTO results = new ResultsDTO();
        MetricDAOImpl metricDAO = MetricDAOImpl.getInstance();
        // Session Hibernate
        ISession session = null;

        try {
            // Récupération d'une session
            session = PERSISTENTPROVIDER.getSession();

            // Récupération de la configuration de la volumétrie pour ce projet et cet audit
            AuditDisplayConfBO auditConf =
                AuditDisplayConfDAOImpl.getInstance().findConfigurationWhere(
                    session,
                    new Long(pProject.getID()),
                    pAuditId,
                    DisplayConfConstants.VOLUMETRY_SUBCLASS,
                    DisplayConfConstants.VOLUMETRY_PROJECT_TYPE);
            if (null != auditConf) {
                VolumetryConfBO volumConf = (VolumetryConfBO) auditConf.getDisplayConf();
                // Pour chaque nom de tre, on récupère la valeur de la métrique associée
                // et on construit ainsi les résultats à retourner
                List tres = new ArrayList();
                List values = new ArrayList();
                for (Iterator it = volumConf.getTres().iterator(); it.hasNext();) {
                    String treName = (String) it.next();
                    IntegerMetricBO metric = metricDAO.findIntegerMetricWhere(session, pProject.getID(), pAuditId.longValue(), treName);
                    if (null != metric) {
                        // On ajoute à la liste des résultats
                        tres.add(treName);
                        values.add(metric.getValue());
                    }
                }
                results.getResultMap().put(null, tres);
                results.getResultMap().put(pProject, values);
            } // Sinon il y a un problème en base
        } catch (JrafDaoException e) {
            FacadeHelper.convertException(e, MeasureFacade.class.getName() + ".getMeasuresByTRE");
        } finally {
            FacadeHelper.closeSession(session, MeasureFacade.class.getName() + ".getMeasuresByTRE");
        }
        return results;
    }
}
