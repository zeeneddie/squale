//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\applicationcomponent\\ComponentApplicationComponentAccess.java

package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.display;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.facade.component.AuditFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.component.ComponentFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.component.ProjectFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.quality.MeasureFacade;

/**
 * @author m400841 (by Rose)
 * Classe permettant de récupérer les composants (fils, parent) et les audits (dernier ou
 * particulier)
 */
public class ComponentApplicationComponentAccess extends DefaultExecuteComponent {

    /**
     * Constructeur vide
     * @roseuid 42CBFBF70095
     */
    public ComponentApplicationComponentAccess() {
    }

    /**
     * Permet de recuperer un ComponentDTO avec un identifiant renseigné
     * @param pComponentDTO renseignant l'identifiant
     * @return ComponentDTO avec toutes les données
     * @throws JrafEnterpriseException exception JRAF
     */
    public ComponentDTO get(ComponentDTO pComponentDTO) throws JrafEnterpriseException {
        return ComponentFacade.get(pComponentDTO);
    }

    /**
     * @param pAuditId l'id de l'audit
     * @return l'audit d'id <code>pAuditId</code>
     * @throws JrafEnterpriseException si erreur
     */
    public AuditDTO getById(Long pAuditId) throws JrafEnterpriseException {
        return AuditFacade.getById(pAuditId);
    }

    /**
     * Récupère les valeurs permettant d'avoir l'historique relatif a des cles de tre et un composant
     * @param pComponentDTO un composant
     * @param pTreLabel label de tre
     * @param pTreKey cles de tre concerne par l'historique
     * @param pAuditDate date a partir de laquelle on souhaite recuperer tous les audits
     * @param pRuleId id de la règle qualité ou null s'il s'agit d'une mesure
     * @return La map correspondant aux points
     * @throws JrafEnterpriseException exception Jraf
     */
    public Map getHistoricMap(ComponentDTO pComponentDTO, String pTreKey, String pTreLabel, Date pAuditDate, Long pRuleId) throws JrafEnterpriseException {
        return MeasureFacade.getHistoricResults(pComponentDTO, pTreKey, pTreLabel, pAuditDate, pRuleId);
    }

    /**
     * Permet de récupérer tous les enfants d'un composant donné avec la possibilité 
     * de spécifier la clé du type de composant souhaité
     * @param pComponent composant parent renseignant l'identifiant
     * @param pType clé du type de composant, sinon <code>null</code> pour tous les composants
     * @param pAudit audit du composant associé, si <code>null</code>, on utilise le dernier
     * @param pFilter le filtre sur les noms des enfants
     * @return Collection de ComponentDTO
     * @throws JrafEnterpriseException exception JRAF
     */
    public Collection getChildren(ComponentDTO pComponent, String pType, AuditDTO pAudit, String pFilter) throws JrafEnterpriseException {

        // Initialisation de la facade
        Collection collection = new ArrayList(0);

        if (pAudit != null) {
            collection = ComponentFacade.getChildren(pComponent, pType, pAudit, pFilter);
        } else {
            AuditDTO lastAudit = AuditFacade.getLastAudit(pComponent, null);
            collection = ComponentFacade.getChildren(pComponent, pType, lastAudit, pFilter);
        }
        return collection;
    }

    /**
     * @param pComponent le composant
     * @return les enfants du composant
     * @throws JrafEnterpriseException si erreur
     */
    public Collection getChildren(ComponentDTO pComponent) throws JrafEnterpriseException {
        return ComponentFacade.getChildren(pComponent, null, null, null);
    }

    /**
     * Permet de compter tous les enfants d'un composant donné avec la possibilité 
     * de spécifier la clé du type de composant souhaité
     * @param pComponent composant parent renseignant l'identifiant
     * @param pType clé du type de composant, sinon <code>null</code> pour tous les composants
     * @param pAudit audit du composant associé, si <code>null</code>, on utilise le dernier
     * @param pFilter le filtre sur les noms des enfants
     * @return le nombre d'enfants
     * @throws JrafEnterpriseException exception JRAF
     */
    public Integer countChildren(ComponentDTO pComponent, String pType, AuditDTO pAudit, String pFilter) throws JrafEnterpriseException {

        // Initialisation de la facade
        Integer nbChildren = new Integer(0);

        if (pAudit != null) {
            nbChildren = ComponentFacade.countChildren(pComponent, pType, pAudit, pFilter);
        } else {
            AuditDTO lastAudit = AuditFacade.getLastAudit(pComponent, null);
            nbChildren = ComponentFacade.countChildren(pComponent, pType, lastAudit, pFilter);
        }
        return nbChildren;
    }

    /**
     * Récupère les composants exclus
     * @param pAudit l'audit 
     * @param pProject le projet auquel doivent appartenir les composants
     * @return les composants exclus du plan d'action
     * @throws JrafEnterpriseException exception JRAF
     */
    public Collection getExcluded(AuditDTO pAudit, ComponentDTO pProject) throws JrafEnterpriseException {
        Collection collection = new ArrayList(0);
        // Si il y a un audit, on le prend
        if (pAudit != null) {
            collection = ComponentFacade.getExcluded(pAudit);
            // Sinon on récupère le dernier
        } else {
            AuditDTO lastAudit = AuditFacade.getLastAudit(pProject, null);
            collection = ComponentFacade.getExcluded(lastAudit);
        }
        return collection;
    }

    /**
     * Permet de récupérer tous les enfants d'un sous-projet de n'importe quel niveau 
     * @param pProject composant parent renseignant l'identifiant
     * @param pType clé du type de composant
     * @param pAudit audit du composant associé, si <code>null</code>, on utilise le dernier
     * audit
     * @return Collection de ComponentDTO
     * @throws JrafEnterpriseException exception JRAF
     */
    public Collection getProjectChildren(ComponentDTO pProject, String pType, AuditDTO pAudit) throws JrafEnterpriseException {

        Collection children = null;

        if (pAudit != null) {
            children = ComponentFacade.getProjectChildren(pProject, pType, pAudit);
        } else {
            AuditDTO lastAudit = AuditFacade.getLastAudit(pProject, null);
            children = ComponentFacade.getProjectChildren(pProject, pType, lastAudit);

        }

        return children;

    }

    /**
     * Permet de récupérer les n derniers milestones d'un projet donné
     * @param pApplication ApplicationDTO renseignant l'ID du projet
     * @param pNbLignes nombre de lignes
     * @param pIndexDepart index de depart, si 0 utilisé, les derniers audits
     * @return Collection d'AuditDTO 
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42DB6BE100E5
     */
    public Collection getLastMilestones(ComponentDTO pApplication, Integer pNbLignes, Integer pIndexDepart) throws JrafEnterpriseException {
        Collection collection = AuditFacade.getLastAudits(pApplication, pNbLignes, pIndexDepart, AuditBO.MILESTONE, AuditBO.TERMINATED);
        return collection;
    }

    /**
     * Permet de récupérer les n derniers audits en échec
     * @param pApplication ApplicationDTO renseignant l'ID du projet
     * @param pNbLignes nombre de lignes
     * @param pIndexDepart index de depart, si 0 utilisé, les derniers audits
     * @return Collection d'AuditDTO 
     * @throws JrafEnterpriseException exception JRAF
     */
    public Collection getFailedAudits(ComponentDTO pApplication, Integer pNbLignes, Integer pIndexDepart) throws JrafEnterpriseException {
        Collection collection = AuditFacade.getFailedAudits(pApplication, pNbLignes, pIndexDepart);
        return collection;
    }

    /**
     * Retourne le dernier audit de suivi programmé d'une application.
     * (<code>null</code> si aucun audit de suivi n'est programmé pour l'application)
     * @param pApplicationDTO l'application
     * @return le dernier audit de suivi programmé
     * @throws JrafEnterpriseException si erreur
     */
    public AuditDTO getLastNotAttemptedAudit(ComponentDTO pApplicationDTO) throws JrafEnterpriseException {
        AuditDTO lAuditDTO = null;

        List lAuditsList = AuditFacade.getLastAudits(pApplicationDTO, new Integer(1), new Integer(0), AuditBO.NORMAL, AuditBO.NOT_ATTEMPTED);
        if (lAuditsList.size() != 0) {
            lAuditDTO = (AuditDTO) lAuditsList.get(0);
        }
        return lAuditDTO;
    }

    /**
     * Permet de récupérer les n derniers audits terminés
     * @param pApplication ApplicationDTO renseignant l'ID du projet
     * @param pNbLignes nombre de lignes
     * @param pIndexDepart index de depart, si 0 utilisé, les derniers audits
     * @return Collection d'AuditDTO 
     * @throws JrafEnterpriseException exception JRAF
     */
    public Collection getTerminatedAudits(ComponentDTO pApplication, Integer pNbLignes, Integer pIndexDepart) throws JrafEnterpriseException {
        Collection collection = AuditFacade.getTerminatedAudits(pApplication, pNbLignes, pIndexDepart);
        return collection;
    }

    /**
     * Permet de récupérer les n derniers audits de suivi d'un projet donné
     * @param pApplication ApplicationDTO renseignant l'ID du projet
     * @param pNbLignes nombre de lignes
     * @param pIndexDepart index de depart, si 0 utilisé, les derniers audits
     * @return Collection d'AuditDTO 
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42DB6BE10121
     */
    public Collection getLastPeriodicAudits(ComponentDTO pApplication, Integer pNbLignes, Integer pIndexDepart) throws JrafEnterpriseException {
        Collection collection = AuditFacade.getLastAudits(pApplication, pNbLignes, pIndexDepart, AuditBO.NORMAL, AuditBO.TERMINATED);
        return collection;
    }

    /**
     * Permet de récupérer les n derniers audits (tous confondus) d'un projet donné
     * @param pApplication ApplicationDTO renseignant l'ID du projet
     * @param pNbLignes nombre de lignes
     * @param pIndexDepart index de depart, si 0 utilisé, les derniers audits
     * @param pStatus le status de l'audit
     * @return Collection d'AuditDTO 
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42DB6BE10153
     */
    public Collection getLastAllAudits(ComponentDTO pApplication, Integer pNbLignes, Integer pIndexDepart, Integer pStatus) throws JrafEnterpriseException {
        Collection collection = AuditFacade.getLastAudits(pApplication, pNbLignes, pIndexDepart, null, pStatus.intValue());
        return collection;
    }

    /**
     * Permet de récupérer les n derniers audits partiels d'un projet donné
     * @param pApplication ApplicationDTO renseignant l'ID du projet
     * @param pNbLignes nombre de lignes
     * @param pIndexDepart index de depart, si 0 utilisé, les derniers audits
     * @return Collection d'AuditDTO 
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42DB6BE10153
     */
    public Collection getPartialAudits(ComponentDTO pApplication, Integer pNbLignes, Integer pIndexDepart) throws JrafEnterpriseException {
        Collection collection = AuditFacade.getPartialAudits(pApplication, pNbLignes, pIndexDepart);
        return collection;
    }

    /**
     * @param pApplications les applications
     * @param pDate la date
     * @param pWithFailedAudits indique si les audits en échec doivent également être collectés
     * @return les audits pour les applications de pApplications dont l'exécution s'est effectué
     * après pDate (si ce paramètre n'est pas nul)
     * @throws JrafEnterpriseException si erreur
     */
    public Collection getAllAuditsAfterDate(Collection pApplications, Date pDate, Boolean pWithFailedAudits) throws JrafEnterpriseException {
        return AuditFacade.getAllAuditsAfterDate(pApplications, pDate, pWithFailedAudits.booleanValue());
    }

    /**
     * @param pApplications les applications
     * @param pDate la date
     * @param pExcludedStatus les statuts à exclure
     * @param pExcludedApplications les ids des applications à ne pas prendre en compte
     * @param pNbAuditsPerAppli le nombre max d'audits par application
     * @return les audits pour les applications de pApplications dont l'exécution s'est effectué
     * après pDate (si ce paramètre n'est pas nul)
     * @throws JrafEnterpriseException si erreur
     */
    public Collection getAuditsForPortlet(Collection pApplications, Date pDate, Integer[] pExcludedStatus, String[] pExcludedApplications, Integer pNbAuditsPerAppli) throws JrafEnterpriseException {
        return AuditFacade.getAuditsForPortlet(pApplications, pDate, pExcludedStatus, pExcludedApplications, pNbAuditsPerAppli);
    }

    /**
     * Permet de récupérer les n derniers audits (tous confondus) d'un projet donné
     * @param pAudit l'auditDto permettant d'obtenir
     * @return un AuditDTO 
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42DB6BE10153
     */
    public Collection getLastTwoAuditsByAuditId(AuditDTO pAudit) throws JrafEnterpriseException {
        Collection collection = new ArrayList(0);
        collection.add(AuditFacade.get(pAudit));
        collection.add(AuditFacade.getPreviousAudit(pAudit, null));
        return collection;
    }

    /**
     * Permet de retourner la liste des parents relatif a un composant
     * @param pComponent ComponentDTO renseignant l'identifiant 
     * @param pNbParents nombre de parents qu'on souhaite retourner, si <code>null</code>,
     * tous les parents sont retournés
     * @return List de ComponentDTO parents du composant
     * @throws JrafEnterpriseException exception Jraf
     */
    public List getParentsComponent(ComponentDTO pComponent, Integer pNbParents) throws JrafEnterpriseException {

        List parentList = null;

        if (null != pComponent) {
            if (pComponent.getID() >= 0) {

                parentList = ComponentFacade.getParentsComponent(pComponent, pNbParents);

            }
        }

        return parentList;

    }

    /**
     * Méthode pour mettre à jour un composant après que l'on ait modifié sa justification
     * ou son status par rapport au plan d'action
     * @param pComponent le composant
     * @throws JrafEnterpriseException en cas d'échec
     */
    public void updateComponent(ComponentDTO pComponent) throws JrafEnterpriseException {
        ComponentFacade.updateComponent(pComponent);
    }

    /**
     * Retourne le liste des projets dont le nom commence par <code>mProjectName</code>, dont
     * l'application commence par <code>mAppliName</code> et qui appartient à la liste
     * <code>pUserAppli</code> associé à son dernier audits (peut-être nul)
     * 
     * @param pUserAppli la liste des applications de l'utilisateur courant
     * @param mAppliName le début du nom de l'application associée au projet
     * @param mProjectName le début du nom du projet à chercher
     * @throws JrafEnterpriseException si erreurs
     * 
     * @return les projets trouvés avec leur dernier audit si il existe
     */
    public Map getProjectsWithLastAudit(Collection pUserAppli, String mAppliName, String mProjectName) throws JrafEnterpriseException {
        return ComponentFacade.getProjectsWithLastAudit(pUserAppli, mAppliName, mProjectName);
    }

    /**
     * @param pType le type d'audit
     * @param pStatus le status de l'audit
     * @return les audits dont le statut est <code>pStatus</code> de type <code>pType</code>
     * @throws JrafEnterpriseException exception Jraf
     */
    public Collection getAudits(String pType, Integer pStatus) throws JrafEnterpriseException {
        return AuditFacade.getAudits(pType, pStatus.intValue());
    }
    
    /**
     * @return le dernier audit terminé de chaque application
     * @throws JrafEnterpriseException si erreur
     */
    public Collection getLastTerminatedAudits() throws JrafEnterpriseException {
        return AuditFacade.getLastTerminatedAudits();
    }

    /**
     * @param pAudits les audits dont la date est à mettre à jour
     * @return le nombre d'audits mis à jour
     * @throws JrafEnterpriseException exception Jraf
     */
    public Integer updateAuditsDateOrStatus(Collection pAudits) throws JrafEnterpriseException {
        return AuditFacade.updateAuditsDateOrStatus(pAudits);
    } /**
          * Renvoie la liste des projets attachés à la grille qualité avec le nom en paramètre
          * @param pQualityGridName le nom de la grille qualité
          * @return la liste
          * @throws JrafEnterpriseException en cas d'échec
          */
    public Collection findWhereQualityGrid(String pQualityGridName) throws JrafEnterpriseException {
        return ProjectFacade.findWhereQualityGrid(pQualityGridName);
    }

    /**
     * @param pProjectId l'id du projet
     * @return true si l'export IDE est possible pour ce projet
     * @throws JrafEnterpriseException si erreur
     */
    public Boolean canBeExportedToIDE(Long pProjectId) throws JrafEnterpriseException {
        return ProjectFacade.canBeExportedToIDE(pProjectId);
    }

    /**
     * @param pProjectId l'id du projet
     * @return le workspace du projet si il existe, null sinon
     * @throws JrafEnterpriseException si erreur
     */
    public String getProjectWorkspace(Long pProjectId) throws JrafEnterpriseException {
        return ProjectFacade.getProjectWorkspace(pProjectId);
    }

}
