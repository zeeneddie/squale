package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridConfDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.QualityRuleDTO;
import com.airfrance.squalecommon.enterpriselayer.facade.rule.QualityGridFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.rule.QualityGridImport;

/**
 * Manipulation de grille qualité
 */
public class QualityGridApplicationComponentAccess extends DefaultExecuteComponent {
    /**
     * Destruction des grilles qualité
     * @param pGrids grilles à détruire
     * @return grilles qualité utilisées et ne pouvant donc pas être détruites
     * @throws JrafEnterpriseException si erreur
     */
    public Collection deleteGrids(Collection pGrids) throws JrafEnterpriseException {
        return QualityGridFacade.deleteGrids(pGrids);
    }
    
    /**
     * Met à jour une grille qualité suite à des changements sur le web
     * @param pGrid la grille
     * @throws JrafEnterpriseException si erreur
     */
    public void updateGrid(QualityGridConfDTO pGrid) throws JrafEnterpriseException {
        QualityGridFacade.updateGrid(pGrid);
    }
    
    /**
     * Obtention des grilles qualité
     * @param pLastVersionsOnly indique si seulement les dernières versions
     * @return grilles qualité existantes
     * @throws JrafEnterpriseException si erreur
     */
    public Collection getGrids(Boolean pLastVersionsOnly) throws JrafEnterpriseException {
        return QualityGridFacade.getGrids(pLastVersionsOnly.booleanValue());
    }
    
    /**
     * Obtention des grilles qualité sans profil ni audit
     * @return grilles qualité existantes sans profil ni audit
     * @throws JrafEnterpriseException si erreur
     */
    public Collection getUnlinkedGrids() throws JrafEnterpriseException {
        return QualityGridFacade.getUnlinkedGrids();
    }
    
    
    /**
     * Permet de récupérer une grille par son identifiant
     * @param pGridId l'identifiant
     * @return la grille
     * @throws JrafEnterpriseException en cas d'échec
     */
    public  QualityGridDTO loadGridById(Long pGridId) throws JrafEnterpriseException {
        return QualityGridFacade.loadGridById(pGridId);   
    }
    
    /**
     * Création d'une grille qualité
     * @param pStream flux associé
     * @param pErrors erreur rencontrées
     * @return grilles créees
     * @throws JrafEnterpriseException si erreur
     */
    public Collection createGrid(InputStream pStream, StringBuffer pErrors) throws JrafEnterpriseException {
        return QualityGridImport.createGrid(pStream, pErrors);
    }
    
    /**
     * Importation d'une grille qualité
     * @param pStream flux associé
     * @param pErrors erreur rencontrées
     * @return grilles présentes dans le fichier
     * @throws JrafEnterpriseException si erreur
     */
    public Collection importGrid(InputStream pStream, StringBuffer pErrors) throws JrafEnterpriseException {
        return QualityGridImport.importGrid(pStream, pErrors);
    }
    
    /**
     * Obtention d'une grille
     * @param pQualityGrid grille
     * @return grille correspondante
     * @throws JrafEnterpriseException si erreur
     */
    public QualityGridConfDTO getGrid(QualityGridDTO pQualityGrid) throws JrafEnterpriseException {
        return QualityGridFacade.get(pQualityGrid);
    }
    /**
     * Obtention d'une règle qualité
     * @param pQualityRule règle qualité (seul l'id est utilisé)
     * @param deepTransformation le booléen indiquant si on veut également
     * une transformation de la formule ou pas.
     * @return règle qualité correspondante
     * @throws JrafEnterpriseException si erreur
     */
    public QualityRuleDTO getQualityRule(QualityRuleDTO pQualityRule,Boolean deepTransformation) throws JrafEnterpriseException {
        return QualityGridFacade.getQualityRule(pQualityRule,deepTransformation.booleanValue());
    }
    
    /**
     * Obtention d'une règle qualité et des mesures utilisées
     * @param pQualityRule règle qualité
     * @param deepTransformation un booléen indiquant si on veut aussi transformer
     * les formules ou pas.
     * @return un tableau à deux entrées contenant la règle qualité et ses mesures extraites
     * @throws JrafEnterpriseException si erreur
     */
    public Object[] getQualityRuleAndUsedTres(QualityRuleDTO pQualityRule, Boolean deepTransformation) throws JrafEnterpriseException {
        return QualityGridFacade.getQualityRuleAndUsedTres(pQualityRule,deepTransformation.booleanValue());
    }
    
    /**
     * Obtention des métriques d'une grille
     * @param pQualityGrid grille
     * @return liste des métriques sous la forme de map, chaque entrée donnant le type
     * de composant et la liste des métriques sur ce type de composant
     * @throws JrafEnterpriseException si erreur
     */
    public Map getGridMetrics(QualityGridDTO pQualityGrid) throws JrafEnterpriseException {
        return QualityGridFacade.getGridMetrics(pQualityGrid);
    }
    
    /**
     * Met à jour une grille qualité suite à des changements sur le web
     * @param pGrid la grille
     * @param pErrors erreur rencontrées
     * @throws JrafEnterpriseException si erreur
     */
    public void updateGrid(QualityGridConfDTO pGrid, StringBuffer pErrors) throws JrafEnterpriseException {
        QualityGridFacade.updateGrid(pGrid, pErrors);
    }
    
    /**
     * Constructeur par défaut
     */
    public QualityGridApplicationComponentAccess() {
    }
}
