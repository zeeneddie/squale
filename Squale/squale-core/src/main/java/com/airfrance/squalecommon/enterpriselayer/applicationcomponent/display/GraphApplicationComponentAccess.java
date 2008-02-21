package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.display;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.result.RoiDTO;
import com.airfrance.squalecommon.enterpriselayer.facade.quality.MeasureFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.roi.RoiFacade;
import com.airfrance.squalecommon.util.messages.CommonMessages;

/**
 * <p>Title : GraphApplicationComponentAccess.java</p>
 * <p>Description : Application component graph </p>
 * <p>Copyright : Copyright (c) 2005</p>
 * <p>Company : AIRFRANCE</p>
 * Classe permettant de recuperer des objets de type GraphDTO stockés en base 
 * ou encore généré à la volée
 */
public class GraphApplicationComponentAccess extends DefaultExecuteComponent {

    /**
     * Date a partir de laquelle on recupere les audits
     */
    private static final Integer AUDIT_TIME = new Integer(CommonMessages.getInt("audit.historic.nombre.jour"));

    

    /**
     * Permet de récupérer l'historique pour un composant donné, une date et une collection de tres
     * @param pComponent ComponentDTO avec identifiant renseigné
     * @param pTreLabel label du tre
     * @param pTreKey tre sur lequel se basera l'historique
     * @param pAuditTime Date en nombre de jours sur lesquels aura lieu l'historique
     * @param pRuleId id de la règle ou nul s'il s'agit d'une métrique. Dans le
     * cas d'une règle, pTreKey donne le nom qui figurera sur le graphique
     * @return Collection de GraphDTO
     * @throws JrafEnterpriseException Exception JRAF
     */
    public Object[] getHistoricGraph(ComponentDTO pComponent, String pTreKey, String pTreLabel, Date pAuditTime, Long pRuleId) throws JrafEnterpriseException {

        if (!(pAuditTime instanceof Date)) {
            GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
            gc.add(GregorianCalendar.DAY_OF_MONTH, -AUDIT_TIME.intValue());
            pAuditTime = gc.getTime();
        }

        // Initialisation
        Object[] result = null; // retour de la methode

        // Recuperation dans la facade du GraphDTO
        if (pComponent != null) {
            result = MeasureFacade.getHistoricGraph(pComponent, pTreKey, pTreLabel, pAuditTime, pRuleId);
        }

        return result;
    }

    /**
     * Récupère l'histogramme représentant le roi sur une année glissante
     * @param pRoi le roi général
     * @throws JrafEnterpriseException Exception JRAF
     * @return le graphe représentant le ROI
     */
    public Map getRoiGraph(RoiDTO pRoi) throws JrafEnterpriseException {
        return RoiFacade.getRoiGraph(pRoi);
    }
    
    /**
     * Récupère le Kiviat d'une application
     * @param pAuditId l'audit
     * @throws JrafEnterpriseException Exception JRAF
     * @return graphe de type Kiviat de l'application auditée
     */
    public Map getApplicationKiviatGraph(Long pAuditId) throws JrafEnterpriseException {
        return MeasureFacade.getApplicationKiviat(pAuditId);
    }
    
    /**
     * Récupère le Kiviat d'un projet
     * @param pProjectId le projet
     * @param pAuditId l'audit
     * @param pAllFactors tous les facteurs (= "true") ou bien seulement ceux ayant une note
     * @throws JrafEnterpriseException Exception JRAF
     * @return tableau d'objets : la map des données Kiviat du projet audité + le booléen pour affichage de la case à cocher tous les facteurs 
     */
    public Object[] getProjectKiviatGraph(Long  pProjectId, Long pAuditId, String pAllFactors) throws JrafEnterpriseException {
        return MeasureFacade.getProjectKiviat(pProjectId, pAuditId, pAllFactors);
    }              
    
    /**
     * Récupère le PieChart d'une application
     * @param pAuditId l'audit
     * @throws JrafEnterpriseException Exception JRAF
     * @return graphe de type PieChart pour l'application auditée
     */
    public Object[] getApplicationPieChartGraph(Long pAuditId) throws JrafEnterpriseException {
        return MeasureFacade.getApplicationPieChart(pAuditId);
    }
    
    /**
     * Récupère le Scatterplot d'un projet
     * @param pProjectId le projet
     * @param pAuditId l'audit
     * @throws JrafEnterpriseException Exception JRAF
     * @return Map contenant les données formatées, nécessaires à la constitution du graphe
     */
    public Object[] getProjectBubbleGraph(Long  pProjectId, Long pAuditId) throws JrafEnterpriseException {
        return MeasureFacade.getProjectBubble(pProjectId,pAuditId);     
    }         

    /**
     * Constructeur par défaut
     * @roseuid 42CBFBFA0216
     */
    public GraphApplicationComponentAccess() {
    }
}
