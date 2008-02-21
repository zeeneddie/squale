package com.airfrance.squalecommon.enterpriselayer.facade.roi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.daolayer.result.SimpleFormulaDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.result.RoiDTO;
import com.airfrance.squalecommon.datatransfertobject.result.RoiMeasureDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.roi.RoiMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.SimpleFormulaBO;
import com.airfrance.squalecommon.enterpriselayer.facade.rule.FormulaException;
import com.airfrance.squalecommon.enterpriselayer.facade.rule.FormulaInterpreter;
import com.airfrance.squalecommon.enterpriselayer.facade.rule.RuleMessages;

/**
 * Façade responsable de la gestion du ROI
 */
public class RoiFacade {

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Modifie la formule du ROI
     * @param pRoiDto le ROI
     * @param pErrors pour récupérer les erreurs
     * @return le ROI sous forme DTO modifié
     * @throws JrafEnterpriseException si erreur
     */
    public static RoiDTO updateFormula(RoiDTO pRoiDto, StringBuffer pErrors) throws JrafEnterpriseException {
        // Initialisation
        ISession session = null;
        String newFormulaStr = pRoiDto.getFormula();
        String oldFormulaStr = "";
        try {
            session = PERSISTENTPROVIDER.getSession();
            // Instanciation du DAO
            SimpleFormulaDAOImpl formulaDAO = SimpleFormulaDAOImpl.getInstance();
            // On récupère l'unique formule du ROI en base
            SimpleFormulaBO roiFormula = formulaDAO.getRoiFormula(session);
            if (null != roiFormula) {
                oldFormulaStr = roiFormula.getFormula();
            }
            SimpleFormulaBO newFormula = createRoiFormula(newFormulaStr);
            // Vérification de la formule
            FormulaInterpreter interpreter = new FormulaInterpreter();
            // Vérification de la syntaxe de la formule
            // un exception est levée si elle est incorrecte
            interpreter.checkSyntax(newFormula);
            // On calcule le nouveau ROI en appliquant la nouvelle formule
            double roi = calculateROI(pRoiDto.getNbCorrections(), newFormula);
            if (null != roiFormula) {
                // On update
                roiFormula.setFormula(newFormulaStr);
                formulaDAO.save(session, roiFormula);
            } else {
                // On crée
                formulaDAO.create(session, newFormula);
            }
            // On modifie le ROI si tout c'est bien passé
            // On calcule le ROI général
            getMeasures(session, pRoiDto);
        } catch (JrafDaoException jde) {
            // On remet l'ancienne formule
            pRoiDto.setFormula(oldFormulaStr);
            FacadeHelper.convertException(jde, RoiFacade.class.getName() + ".updateFormula");
        } catch (FormulaException fe) {
            // On remet l'ancienne formule
            pRoiDto.setFormula(oldFormulaStr);
            String message = RuleMessages.getString("bad.formula", new Object[] { newFormulaStr });
            pErrors.append(message);
            pErrors.append('\n');
            pErrors.append(fe.getMessage());
            pErrors.append('\n');
            session.rollbackTransactionWithoutClose();
        } finally {
            FacadeHelper.closeSession(session, RoiFacade.class.getName() + ".updateFormula");
        }
        return pRoiDto;
    }

    /**
     * Récupère les informations nécessaires au traitement du ROI
     * @param pApplicationId l'id de l'application dont on veut le ROI
     * -1 si le veut pour toutes les applications
     * @return le roi sous forme DTO
     * @throws JrafEnterpriseException si erreur
     */
    public static RoiDTO getROI(Long pApplicationId) throws JrafEnterpriseException {
        // Initialisation
        ISession session = null;
        RoiDTO roiDto = new RoiDTO();
        roiDto.setApplicationId(pApplicationId.longValue());
        try {
            session = PERSISTENTPROVIDER.getSession();
            // Instanciation des DAO
            SimpleFormulaDAOImpl formulaDAO = SimpleFormulaDAOImpl.getInstance();
            // On récupère l'unique formule du ROI en base
            SimpleFormulaBO roiFormula = formulaDAO.getRoiFormula(session);
            // On calcule la somme totale du nombre de corrections sur t-1an
            if (null != roiFormula) {
                roiDto.setFormula(roiFormula.getFormula());
                // On calcule le ROI général
                getMeasures(session, roiDto);
            }
        } catch (JrafDaoException jde) {
            FacadeHelper.convertException(jde, RoiFacade.class.getName() + ".updateFormula");
        } catch (FormulaException fe) {
            // La formule en base doit être correcte sinon il y a un problème en base
            FacadeHelper.convertException(fe, RoiFacade.class.getName() + ".updateFormula");
        } finally {
            FacadeHelper.closeSession(session, RoiFacade.class.getName() + ".updateFormula");
        }
        return roiDto;
    }

    /**
     * @param pSession la session
     * @param pRoi le ROI général contenant l'id de l'application et la formule
     * récupère les résulats des ROI de chaque Audit sur t-1an sous forme date->valeur
     * et remplit par la même occasion la valeur du ROI global
     * @throws JrafDaoException si erreur
     * @throws FormulaException si formule incorrecte
     */
    private static void getMeasures(ISession pSession, RoiDTO pRoi) throws JrafDaoException, FormulaException {
        List results = new ArrayList();
        SimpleFormulaBO formula = createRoiFormula(pRoi.getFormula());
        GregorianCalendar cal = new GregorianCalendar();
        Date today = cal.getTime();
        // On enlève 1 an
        cal.add(GregorianCalendar.YEAR, -1);
        Date start = cal.getTime();
        /* On récupère les mesures du ROI pour tous les audits datés de start à aujourd'hui */
        // Initialisation du DAO
        MeasureDAOImpl measureDAO = MeasureDAOImpl.getInstance();
        Collection measures = measureDAO.getRoiMeasures(pSession, pRoi.getApplicationId(), start);
        double roiValue = 0;
        int nbCorrections = 0;
        Date auditDate;
        double total = 0;
        // On va toujours mettre la date du jour et la date de J-1 an pour avoir
        // le même intervalle de temps à chaque fois.
        // Debut de l'intervalle de temps
        results.add(new RoiMeasureDTO("", start, 0));
        for (Iterator it = measures.iterator(); it.hasNext();) {
            RoiMetricsBO measure = (RoiMetricsBO) it.next();
            nbCorrections += measure.getNbCorrections().intValue();
            roiValue = calculateROI(measure.getNbCorrections().intValue(), formula);
            // On fait un new Date car on récupère un objet de type Timestamp sinon
            auditDate = new Date(measure.getAudit().getRealDate().getTime());
            String appliName = measure.getComponent().getName();
            results.add(new RoiMeasureDTO(appliName, auditDate, roiValue));
            total += roiValue;
        }
        // Fin de l'intervalle de temps
        results.add(new RoiMeasureDTO("", today, 0));
        pRoi.setNbCorrections(nbCorrections);
        pRoi.setTotal(total);
        pRoi.setMeasures(results);
    }

    /**
     * @param pFormula la formule sous forme de String
     * @return la formule sous forme SimpleformulaBO
     */
    private static SimpleFormulaBO createRoiFormula(String pFormula) {
        SimpleFormulaBO formula = new SimpleFormulaBO();
        formula.setComponentLevel("application");
        formula.setFormula(pFormula);
        formula.addMeasureKind("roi");
        return formula;
    }

    /**
     * @param pNbCorrections le nombre de corrections
     * @param pFormula la formule à appliquer
     * @return le roi calculé
     * @throws FormulaException si erreur dans la formule
     */
    public static float calculateROI(int pNbCorrections, SimpleFormulaBO pFormula) throws FormulaException {
        FormulaInterpreter interpreter = new FormulaInterpreter();
        RoiMetricsBO roiTotalMeasure = new RoiMetricsBO();
        roiTotalMeasure.setNbCorrections(pNbCorrections);
        MeasureBO[] measure = { roiTotalMeasure };
        // On évalue la formule
        Number total = interpreter.evaluateWithoutAdapt(pFormula, measure);
        return total.floatValue();
    }

    /**
     * Récupère l'histogramme représentant le roi sur une année glissante
     * @param pRoi le roi général
     * @throws JrafEnterpriseException Exception JRAF
     * @return le graphe représentant le ROI
     */
    public static Map getRoiGraph(RoiDTO pRoi) throws JrafEnterpriseException {
        // Initialisation
        Map values = new TreeMap();
        try {
            List measures = pRoi.getMeasures();
            Double roiValue;
            String roiDate;
            String appliName;
            RoiMeasureDTO current;
            for (int i = 0; i < measures.size(); i++) {
                current = (RoiMeasureDTO) measures.get(i);
                roiValue = new Double(current.getValue());
                // On fabrique la map des valeurs (date = valeur ROI)
                values.put(current.getAuditDate(), roiValue);
            }
            // On ajoute les valeurs au graphe
        } catch (Exception e) {
            FacadeHelper.convertException(e, RoiFacade.class.getName() + ".getRoiGraph");
        }
        return values;
    }
}
