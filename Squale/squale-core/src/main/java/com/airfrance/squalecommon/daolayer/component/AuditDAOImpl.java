/*
 * Créé le 8 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.daolayer.component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.DAOMessages;
import com.airfrance.squalecommon.daolayer.DAOUtils;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;

/**
 * Cette classe est responsable de toutes les manipulations d'audits présents (ou a insérer) en base
 * @author M400843
 *
 */
public class AuditDAOImpl extends AbstractDAOImpl {
    /**
     * Instance singleton
     */
    private static AuditDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static {
        instance = new AuditDAOImpl();
    }

    /**
     * Constructeur prive
     * @throws JrafDaoException
     */
    private AuditDAOImpl() {
        initialize(AuditBO.class);
        if (null == LOG) {
            LOG = LogFactory.getLog(AuditDAOImpl.class);
        }
    }

    /**
     * Retourne un singleton du DAO
     * @return singleton du DAO
     */
    public static AuditDAOImpl getInstance() {
        return instance;
    }

    /**
     * Permet de supprimer logiquement un audit
     * @param pSession session Hibernate
     * @param pAudit objet à supprimer
     * @throws JrafDaoException exception DAO
     */
    public void remove(ISession pSession, AuditBO pAudit) throws JrafDaoException {
        /* Cette suppresion n'est que logique, il faudra ensuite :      
         *       Supprimer MarkBO --> PracticeResultBO --> AuditBO
         *       Supprimer QualityResultBO --> AuditBO
         *       Supprimer MeasureBO --> AuditBO
         *       Supprimer ErrorBO --> AuditBO
         *       Supprimer AuditBO
         */
        pAudit.setStatus(AuditBO.DELETED);
        save(pSession, pAudit);
        LOG.debug(DAOMessages.getString("dao.exit_method"));
    }

    /**
     * Permet de récupérer le dernier audit d'une application à partir de son identifiant
     * @param pSession session Hibernate
     * @param pIDApplication Identifiant de l'application
     * @param pType le type de l'audit (<code>null</code> pour n'importe quel type)
     * @param pStatus le statut de l'audit (valeur correspondant à "ALL_STATUS" pour n'importe quel status)
     * @return AuditBO objet metier d'audit
     * @throws JrafDaoException exception DAO
     */
    public AuditBO getLastAuditByApplication(ISession pSession, long pIDApplication, String pType, int pStatus) throws JrafDaoException {
        LOG.debug(DAOMessages.getString("dao.entry_method"));
        Integer nbLigne = new Integer(1);
        Integer indexDepart = new Integer(0);
        AuditBO audit = null;

        List list = findWhereApplication(pSession, pIDApplication, nbLigne, indexDepart, pType, pStatus);
        if (null != list) {
            if (list.size() > 0) {
                audit = (AuditBO) list.get(0);
            }
        }

        LOG.debug(DAOMessages.getString("dao.exit_method"));
        return audit;
    }

    /**
     * @param pSession la session
     * @param pComponentClass le type des composants
     * @param pType le type des audits
     * @param pStatus le status des audits
     * @return une liste de tableau d'objets à 3 éléments représentants les derniers audits des composants de 
     * type <pComponentType</code> de type <code>pType</code> dont le status est <code>pStatus</code>
     * Le tableau est de la forme {auditApplicationId, auditApplicationName, auditBO}
     * @throws JrafDaoException si erreur
     */
    public List findAllLastAudits(ISession pSession, Class pComponentClass, String pType, int pStatus) throws JrafDaoException {
        // Recupération du nom de classe et de l'alias pour le composant
        int index = pComponentClass.getName().lastIndexOf(".");
        String compClassName = pComponentClass.getName().substring(index + 1);
        // Le nom court de la classe du DAO
        index = getBusinessClass().getName().lastIndexOf(".");
        String className = getBusinessClass().getName().substring(index + 1);
        StringBuffer subQuery = new StringBuffer("select max(a2.date) from ");
        subQuery.append(className);
        subQuery.append(" as a2 where c.id in elements(a2.components)");
        StringBuffer query = new StringBuffer("select c.id, c.name, ");
        query.append(getAlias());
        // Si le composant est de type application , on récupère aussi le nom du serveur
        if(pComponentClass.equals(ApplicationBO.class)) {
            query.append(", c.serveurBO.name");
        }
        query.append(" from ");
        query.append(compClassName);
        query.append(" as c, ");
        query.append(className);
        query.append(" as ");
        query.append(getAlias());
        query.append(" where c.id in elements( ");
        query.append(getAlias());
        query.append(".components)");
        if(pType != AuditBO.ALL_TYPES) {
            query.append(" and ");
            query.append(getAlias());
            query.append(".type='");
            query.append(pType);
            query.append("'");
        }
        if(pStatus != AuditBO.ALL_STATUS) {
            query.append(" and ");
            query.append(getAlias());
            query.append(".status=");
            query.append(pStatus);
            subQuery.append(" and ");
            subQuery.append("a2.status=");
            subQuery.append(pStatus);
        } else {
            // On ne prend pas en compte les audits supprimés
            query.append(" and ");
            query.append(getAlias());
            query.append(".status !=");
            query.append(AuditBO.DELETED);
            subQuery.append(" and ");
            subQuery.append("a2.status !=");
            subQuery.append(AuditBO.DELETED);
        }
        query.append(" and ");
        query.append(getAlias());
        query.append(".date = (");
        query.append(subQuery);
        query.append(")");
        return find(pSession, query.toString());
    }

    /**
     * Retourne tous les audits terminés de ce composant exécuté à partir de pDate
     * @param pSession la session hibernate
     * @param pIdComponent l'id du composant
     * @param pDate la date
     * @return une liste d'audits
     * @throws JrafDaoException si une erreur à lieu
     */
    public List findAfter(ISession pSession, long pIdComponent, Date pDate) throws JrafDaoException {
        LOG.debug(DAOMessages.getString("dao.entry_method"));
        String whereClause = "where ";
        whereClause += pIdComponent + " in elements(" + getAlias() + ".components)";

        whereClause += " AND ";
        whereClause += getAlias() + ".status = " + AuditBO.TERMINATED;
        whereClause += " AND (";
        whereClause += getAlias() + ".date > " + DAOUtils.makeQueryDate(pDate);
        whereClause += " OR ";
        whereClause += getAlias() + ".historicalDate > " + DAOUtils.makeQueryDate(pDate) + ")";

        whereClause += " order by nvl(" + getAlias() + ".historicalDate," + getAlias() + ".date) desc";

        LOG.debug(DAOMessages.getString("dao.exit_method"));
        return (List) findWhere(pSession, whereClause);
    }

    /**
     * Retourne tous les audits supprimées ou appartenant à une appli supprimée
     * et donc l'application n'a pas d'audit en cours
     * @param pSession la session hibernate
     * @param pSite le site de l'application
     * @param pForbiddenApplis les ids des applications à ne pas prendre en compte
     * @return une liste d'audits
     * @throws JrafDaoException si une erreur à lieu
     */
    public List findDeleted(ISession pSession, long pSite, Collection pForbiddenApplis) throws JrafDaoException {
        LOG.debug(DAOMessages.getString("dao.entry_method"));
        String whereClause = " where ";

        // Recupération du nom de classe et de l'alias pour l' application
        int index = ApplicationBO.class.getName().lastIndexOf(".");
        String classApplicationName = ApplicationBO.class.getName().substring(index + 1);
        String appAlias = classApplicationName.toLowerCase();

        index = getBusinessClass().getName().lastIndexOf(".");
        String className = getBusinessClass().getName().substring(index + 1);

            String requete = "select distinct " + getAlias() // uniquement les audits
        +" from " + classApplicationName + " as " + appAlias // avec jointure sur les Applications (site)
    +", " + className + " as " + getAlias() + " ";

        // pour les Applications  du site uniquement
        whereClause += "(" + getAlias() + ".status = " + AuditBO.DELETED;
        // ou pour les applications supprimées
        whereClause += " OR ";
        whereClause += appAlias + ".status = " + ApplicationBO.DELETED + ")";
        whereClause += " AND ";
        whereClause += appAlias + " in elements(" + getAlias() + ".components)";
        if(pForbiddenApplis.size() > 0) {
            // qui ne sont pas dans la liste des ids passés en paramétre
            Iterator itForbidden = pForbiddenApplis.iterator();
            whereClause += " AND ";
            whereClause += appAlias + " not in (" + ((Long)itForbidden.next()).longValue();
            while(itForbidden.hasNext()) {
                whereClause += ", " + ((Long)itForbidden.next()).longValue();
            }
            whereClause += ")";
        }
        whereClause += " AND ";
        whereClause += "lower(" + appAlias + ".serveurBO.serveurId) = '" + pSite + "'";

        LOG.info("requete = " + requete + whereClause);
        List result = find(pSession, requete + whereClause);
        LOG.info("find " + result.size() + " audits to delete");

        LOG.debug(DAOMessages.getString("dao.exit_method"));
        return result;
    }

    /**
     * Permet de récupérer un nombre d'audits définis à partir de l'identifiant du composant. <br />
     * Les Audits sont triés du plus récents au plus anciens
     * @param pSession session Hibernate
     * @param pIDComponent identifiant du composant
     * @param pNbLignes nombre d'audits, si <code>null</code> topus les audits seront remontés
     * @param pIndexDepart index de départ
     * @param pType type d'audits retourné (<code>null</code> pour n'importe quel type)
     * @param pStatus le statut désiré (valeur correspondant à <b>ALL_STATUS</b> pour n'importe quel status)
     * @return Collection de AuditDTO
     * @throws JrafDaoException exception DAO
     */
    public List findWhereComponent(ISession pSession, long pIDComponent, Integer pNbLignes, Integer pIndexDepart, String pType, int pStatus) throws JrafDaoException {
        LOG.debug(DAOMessages.getString("dao.entry_method"));
        // Initialisation
        List result = new ArrayList();
        // Création de la clause where :
        String whereClause = "where ";
        // choix des audits du composant concerné
        whereClause += pIDComponent + " in elements(" + getAlias() + ".components)";
        if (null != pType && pType != AuditBO.ALL_TYPES) {
            // si un type d'audit a été spécifié on l'insére dans la clause
            whereClause += " AND ";
            whereClause += getAlias() + ".type = '" + pType + "'";
        }
        if (pStatus == AuditBO.ALL_STATUS) {
            // on passe ici pour la page d'accueil
            // on affiche tous les audits dans la page d'accueil sauf ceux à effacer
            whereClause += " AND " + getAlias() + ".status <> " + AuditBO.DELETED;
        } else {
            // ici c'est pour les autres cas
            whereClause += " AND " + getAlias() + ".status = " + pStatus;
        }
        // tri par date (date historique prioritaire)
        whereClause += " order by nvl(" + getAlias() + ".historicalDate, " + getAlias() + ".date) desc";
        LOG.info("whereClause = " + whereClause);
        int start = 0;
        int nbLines = 0;
        //
        if (pNbLignes != null) {
            nbLines = pNbLignes.intValue();
            if (pIndexDepart != null) {
                start = pIndexDepart.intValue();
            }
            result = (List) findWhereScrollable(pSession, whereClause, nbLines, start, false);
        } else {
            // si le nombre de ligne ou l'index de départ n'est pas spécifié, 
            // on retourne tous les audits concernés
            result = (List) findWhere(pSession, whereClause);
        }
        LOG.debug(DAOMessages.getString("dao.exit_method"));
        return result;
    }

    /**
     * Permet de récupérer un nombre d'audits exécutés (ie. ni supprimé, ni en attente d'exécution)
     * définis à partir de l'identifiant du composant. <br />
     * Les Audits sont triés du plus récents au plus anciens
     * @param pSession session Hibernate
     * @param pIDComponent identifiant du composant
     * @param pNbLignes nombre d'audits, si <code>null</code> topus les audits seront remontés
     * @param pIndexDepart index de départ
     * @param pType type d'audits retourné (<code>null</code> pour n'importe quel type)
     * @return Collection de AuditDTO
     * @throws JrafDaoException exception DAO
     */
    public List findExecutedWhereComponent(ISession pSession, long pIDComponent, Integer pNbLignes, Integer pIndexDepart, String pType) throws JrafDaoException {
        LOG.debug(DAOMessages.getString("dao.entry_method"));
        // Initialisation
        List result = new ArrayList();
        // Création de la clause where :
        String whereClause = "where ";
        // choix des audits du composant concerné
        whereClause += pIDComponent + " in elements(" + getAlias() + ".components)";
        if (null != pType && pType != AuditBO.ALL_TYPES) {
            // si un type d'audit a été spécifié on l'insére dans la clause
            whereClause += " AND ";
            whereClause += getAlias() + ".type = '" + pType + "'";
        }
        // On ne veut que les audits avec des résultats donc on ne prend pas les audits supprimés,
        // en attente ou en cours
        whereClause += " AND " + getAlias() + ".status <> " + AuditBO.DELETED;
        whereClause += " AND " + getAlias() + ".status <> " + AuditBO.NOT_ATTEMPTED;
        whereClause += " AND " + getAlias() + ".status <> " + AuditBO.RUNNING;
        // tri par date (date historique prioritaire)
        whereClause += " order by nvl(" + getAlias() + ".historicalDate, " + getAlias() + ".date) desc";
        LOG.info("whereClause = " + whereClause);
        int start = 0;
        int nbLines = 0;
        //
        if (pNbLignes != null) {
            nbLines = pNbLignes.intValue();
            if (pIndexDepart != null) {
                start = pIndexDepart.intValue();
            }
            result = (List) findWhereScrollable(pSession, whereClause, nbLines, start, false);
        } else {
            // si le nombre de ligne ou l'index de départ n'est pas spécifié, 
            // on retourne tous les audits concernés
            result = (List) findWhere(pSession, whereClause);
        }
        LOG.debug(DAOMessages.getString("dao.exit_method"));
        return result;
    }

    /**
     * Permet de récupérer l'audit précédent terminé
     * @param pSession session Hibernate
     * @param pAuditID ID de l'audit 
     * @param pAuditHistoricalDate la date historique de l'audit
     * @param pComponentID ID de l'applie concernée 
     * @param pType type d'audits retourné (<code>null</code> pour n'importe quel type)
     * @return AuditBO si retrouver (null sinon)
     * @throws JrafDaoException exception DAO
     */
    public AuditBO findPreviousAudit(ISession pSession, long pAuditID, Date pAuditHistoricalDate, long pComponentID, String pType) throws JrafDaoException {
        LOG.debug(DAOMessages.getString("dao.entry_method"));
        AuditBO result = null;
        // On va sélectionner la date de réalisation de l'audit courant
        // ou sa date historique si celle-ci n'est pas nulle
        String date = "date";
        if (null != pAuditHistoricalDate) {
            date = "historicalDate";
        }
        // Création de la clause where :
        String whereClause = "where ";
        // choix des audits du composant concerné
        whereClause += pComponentID + " in elements(" + getAlias() + ".components)";
        // pour les audits antérieurs
        whereClause += " and (";
        whereClause += "(" + getAlias() + ".date < (select " + date + " from AuditBO where id=" + pAuditID + ")";
        // en prenant en compte la date historique des audits de jalon
        whereClause += " and " + getAlias() + ".historicalDate is null)";
        whereClause += " or (";
        whereClause += getAlias() + ".historicalDate is not null";
        whereClause += " and " + getAlias() + ".historicalDate < (select " + date + " from AuditBO where id=" + pAuditID + ")";
        whereClause += ")";
        whereClause += ")";
        if (null != pType) {
            // si un type d'audit a été spécifié on l'insére dans la clause
            whereClause += " AND ";
            whereClause += getAlias() + ".type = '" + pType + "'";
        }
        // seulement les audits réussis
        whereClause += " and " + getAlias() + ".status =  " + AuditBO.TERMINATED;

        // tri par date (date historique prioritaire)
        // on utilise la fonction nvl d'oracle pour prendre en compte la date d'exécutio
        // quand la date historique est nulle
        whereClause += " order by nvl(" + getAlias() + ".historicalDate, " + getAlias() + ".date) desc";

        LOG.info("whereClause = " + whereClause);
        List r = (List) findWhereScrollable(pSession, whereClause, 1, 0, false);
        // ne recupere que le 1er audit
        if (r.size() > 0) {
            result = (AuditBO) r.get(0);
        }
        LOG.debug(DAOMessages.getString("dao.exit_method"));
        return result;
    }

    /**
     * Permet de récupérer un nombre d'audits définis à partir de l'identifiant de l'application. <br />
     * Les Audits sont triés du plus récents au plus anciens
     * @param pSession session Hibernate
     * @param pIDApplication identifiant de l'application
     * @param pNbLignes nombre d'audits, si <code>null</code> tous les audits seront remontés
     * @param pIndexDepart index de départ
     * @param pType type d'audits retourné (<code>null</code> pour n'importe quel type)
     * @param pStatus le statut désiré (valeur correspondant à <b>ALL_STATUS</b> pour n'importe quel status)
     * @return Collection de AuditDTO
     * @throws JrafDaoException exception DAO
     * @deprecated utiliser {@link #findWhereComponent(ISession, long, Integer, Integer, String, int)}
     */
    public List findWhereApplication(ISession pSession, long pIDApplication, Integer pNbLignes, Integer pIndexDepart, String pType, int pStatus) throws JrafDaoException {
        return findWhereComponent(pSession, pIDApplication, pNbLignes, pIndexDepart, pType, pStatus);
    }

    /**
     * Charger un audit à partir de son identifiant sous forme de type simple
     * @param pSession session Hibernate
     * @param pID sous forme de type simple
     * @return Object AuditBO
     * @throws JrafDaoException exception DAO
     */
    public Object load(ISession pSession, long pID) throws JrafDaoException {
        LOG.debug(DAOMessages.getString("dao.entry_method"));
        // Initialisation de la variable temporaire
        Long projectID = new Long(pID);
        // Appel à la méthode load
        Object ret = load(pSession, projectID);
        LOG.debug(DAOMessages.getString("dao.exit_method"));
        return ret;
    }

    /**
     * Recupère les audits dont la date est antèrieure à la date passée en paramètre
     * @param pSession la session
     * @param pSite le site de l'application
     * @param pStatus le status de l'audit
     * @param pDate la date 
     * @return une collection d'audit
     * @throws JrafDaoException exception DAO
     */
    public Collection findBeforeBySiteAndStatus(ISession pSession, long pSite, int pStatus, Date pDate) throws JrafDaoException {
        LOG.debug(DAOMessages.getString("dao.entry_method"));
        String whereClause = "where ";
        Collection result = null;

        // Recupération du nom de classe et de l'alias pour le Application
        int index = ApplicationBO.class.getName().lastIndexOf(".");
        String classApplicationName = ApplicationBO.class.getName().substring(index + 1);
        String appAlias = classApplicationName.toLowerCase();

        index = getBusinessClass().getName().lastIndexOf(".");
        String className = getBusinessClass().getName().substring(index + 1);

        // Fabrication de la requete (clauses select et from)
            String requete = "select " + getAlias() // uniquement les audits
        +" from " + classApplicationName + " as " + appAlias // avec jointure sur les Applications (site)
    +", " + className + " as " + getAlias() + " ";

        whereClause += "(" + getAlias() + ".date < " + DAOUtils.makeQueryDate(pDate) + " or " + getAlias() + ".date is null )";

        // si on ne doit recuperer que certains status de l'audit
        if (pStatus != AuditBO.ALL_STATUS) {
            whereClause += " AND ";
            whereClause += getAlias() + ".status = " + "'" + pStatus + "'";
        }
        // pour les Applications  du site uniquement (validée ou non)
        whereClause += " AND ";
        whereClause += appAlias + ".id in elements(" + getAlias() + ".components)";
        // uniquement pour les applications validées
        whereClause += " AND NOT ";
        whereClause += appAlias + ".status = " + ApplicationBO.DELETED;
        whereClause += " AND ";
        whereClause += "lower(" + appAlias + ".serveurBO.serveurId) = '" + pSite + "'";

        // On doit trié par ordre de dernière date d'accès.
        // Attention, les applications qui n'ont pas eu d'accès, ne seront pas prises en compte!
        // On appelle donc une fois en triant par accès, une autre pour les applications sans accès et dans ce
        // cas on trie par date d'exécution.
        String orderClause = " order by " + appAlias + ".userAccesses[minindex(" + appAlias + ".userAccesses)].date desc";

        LOG.info("requete = " + requete + whereClause + orderClause);
        result = find(pSession, requete + whereClause + orderClause);

        orderClause = " and size(" + appAlias + ".userAccesses)=0 order by " + getAlias() + ".date";
        LOG.info("requete = " + requete + whereClause + orderClause);
        result.addAll(find(pSession, requete + whereClause + orderClause));

        LOG.info("find " + result.size() + " audits");

        LOG.debug(DAOMessages.getString("dao.exit_method"));
        return result;
    }

    /**
     * Permet de compter le nombre d'audit d'un type sur une application donné
     * @param pSession session Hibernate
     * @param pApplicationBO objet metier Application
     * @param pType type d'audits retourné (<code>null</code> pour n'importe quel type)
     * @param pStatus le statut désiré (valeur correspondant à <b>ALL_STATUS</b> pour n'importe quel status)
     * @return int nombre d'audits d'un type
     * @throws JrafDaoException exception DAO
     */
    public int countWhereType(ISession pSession, ApplicationBO pApplicationBO, String pType, int pStatus) throws JrafDaoException {
        String whereClause = "where ";
        whereClause += pApplicationBO.getId() + " in elements(" + getAlias() + ".components)";
        if (null != pType) {
            whereClause += " AND ";
            whereClause += getAlias() + ".type = '" + pType + "'";
        }
        if (pStatus != AuditBO.ALL_STATUS) {
            whereClause += " AND ";
            whereClause += getAlias() + ".status = " + pStatus;

        }

        int ret = countWhere(pSession, whereClause).intValue();
        return ret;
    }

    /**
     * @param pSession la session
     * @param pApplicationId l'id de l'application de l'audit
     * @return l'audit de jalon programmé pour l'application
     * @throws JrafDaoException si erreur
     */
    public AuditBO findMilestoneAudit(ISession pSession, long pApplicationId) throws JrafDaoException {
        AuditBO result = null;
        String whereClause = "where ";
        whereClause += pApplicationId + " in elements(" + getAlias() + ".components)";
        whereClause += " and ";
        whereClause += getAlias() + ".type='" + AuditBO.MILESTONE + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".status=" + AuditBO.NOT_ATTEMPTED;
        List found = findWhere(pSession, whereClause);
        if (found.size() > 0) {
            // Il ne doit y avoir qu'un audit de jalon programmé
            result = (AuditBO) found.get(0);
        }
        return result;
    }

    /**
     * Supprime l'audit d'id <code>pId</code>
     * @param pSession la session 
     * @param pId l'id de l'audit
     * @throws JrafDaoException si erreur
     */
    public void removeWhereId(ISession pSession, long pId) throws JrafDaoException {
        String whereClause = "where ";
        whereClause += getAlias() + ".id=" + pId;
        removeWhere(pSession, whereClause);
    }

    /**
     * @param pSession la session
     * @param pStatus le satus de l'audit
     * @return les audits dont le status est <code>pStatus</code>
     * @throws JrafDaoException si erreur
     */
    public List findWhereStatus(ISession pSession, int pStatus) throws JrafDaoException {
        String whereClause = " where ";
        whereClause += getAlias() + ".status=" + pStatus;
        whereClause += " order by " + getAlias() + ".date";
        List audits = findWhere(pSession, whereClause);
        return audits;
    }

    /**
     * @param pSession la session
     * @param pType le type des audits
     * @param pStatus le satus des audits
     * @return les audits dont le status est <code>pStatus</code> de type <code>pType</code>
     * @throws JrafDaoException si erreur
     */
    public List findWhereStatusAndType(ISession pSession, String pType, int pStatus) throws JrafDaoException {
        List audits = new ArrayList(0);
        if (AuditBO.ALL_TYPES.equals(pType)) {
            audits = findWhereStatus(pSession, pStatus);
        } else {
            String whereClause = " where ";
            whereClause += getAlias() + ".status=" + pStatus;
            whereClause += " and ";
            whereClause += getAlias() + ".type='" + pType + "'";
            whereClause += " order by " + getAlias() + ".date";
            audits = findWhere(pSession, whereClause);
        }
        return audits;
    }

    /**
     * @param pSession la session
     * @param pSiteId l'id du site de l'application
     * @param pAuditStatus le satus des audits
     * @return les audits dont le status est <code>pStatus</code> de type <code>pType</code>
     * @throws JrafDaoException si erreur
     */
    public int countWhereStatusAndSite(ISession pSession, long pSiteId, int pAuditStatus) throws JrafDaoException {
        // Recupération du nom de classe et de l'alias pour le Application
        int index = ApplicationBO.class.getName().lastIndexOf(".");
        String classApplicationName = ApplicationBO.class.getName().substring(index + 1);
        String appAlias = classApplicationName.toLowerCase();

        index = getBusinessClass().getName().lastIndexOf(".");
        String className = getBusinessClass().getName().substring(index + 1);

        // Fabrication de la requete (clauses select et from)
            String selectClause = "select " + getAlias() // uniquement les audits
        +" from " + classApplicationName + " as " + appAlias // avec jointure sur les Applications (site)
    +", " + className + " as " + getAlias() + " ";

        String whereClause = " where ";

        // si on ne doit recuperer que certains status de l'audit
        if (pAuditStatus != AuditBO.ALL_STATUS) {
            whereClause += getAlias() + ".status = " + "'" + pAuditStatus + "' AND ";
        }
        // pour les Applications  du site uniquement
        whereClause += appAlias + ".id in elements(" + getAlias() + ".components)";
        whereClause += " AND ";
        whereClause += appAlias + ".serveurBO.serveurId='" + pSiteId + "'";

        return find(pSession, selectClause + whereClause).size();
    }

    /**
     * Permet de récupérer les audits pour la page d'acceuil de squaleWeb.
     * Récupère tous les audits qui ne sont pas supprimés en relation avec pComponentId
     * et qui ont été éxécuté après pDate ou qui sont en attente d'exécution.
     * @param pSession la session
     * @param pComponentId l'id du composant
     * @param pDate la date à partir de laquelle les audits doivent être pris, nulle si on ne prend
     * @param pWithFailedAudits indique si les audits en échec doivent également être collectés
     * pas ce critère en compte
     * @return les audits correspondants aux critères
     * @throws JrafDaoException si erreur
     */
    public List findAfterDateWhereComponent(ISession pSession, long pComponentId, Date pDate, boolean pWithFailedAudits) throws JrafDaoException {
        // Création de la clause where :
        String whereClause = "where ";
        // choix des audits du composant concerné
        whereClause += pComponentId + " in elements(" + getAlias() + ".components)";
        // On ne prend pas les audits supprimés
        whereClause += " and " + getAlias() + ".status <> " + AuditBO.DELETED;
        // collection des audits en échec 
        if (pWithFailedAudits) {
            whereClause += " and " + getAlias() + ".status <> " + AuditBO.FAILED;
        }
        if (null != pDate) {
            whereClause += " and ";
            whereClause += "(" + getAlias() + ".date > " + DAOUtils.makeQueryDate(pDate) + " or " + getAlias() + ".date is null )";
        }
        List result = (List) findWhere(pSession, whereClause);
        return result;
    }

    /**
     * Permet de récupérer les n derniers (par date d'éxécution) audits dont le statut n'est pas exclu
     * @param pSession la session
     * @param pComponentsId l'id des composants (PRE-CONDITION : la liste doit être > 1)
     * @param pDate la date à partir de laquelle les audits doivent être pris, nulle si on ne prend
     * pas ce critère en compte
     * @param pNbAudits le nombre max d'audits
     * @param pExcludedStatus les statuts à exclure (peut être nul)
     * @return les audits correspondants aux critères
     * @throws JrafDaoException si erreur
     */
    public List findAfterDateWhereComponents(ISession pSession, List pComponentsId, Date pDate, Integer[] pExcludedStatus, Integer pNbAudits) throws JrafDaoException {
        // Initialisation de la valeur de retour
        List result = new ArrayList(0);

        // Création de la clause where :
        StringBuffer whereClause = new StringBuffer("where (");
        // choix des audits des composants concernés
        for (int i = 0; i < pComponentsId.size() - 1; i++) {
            whereClause.append(pComponentsId.get(i));
            whereClause.append(" in elements(");
            whereClause.append(getAlias());
            whereClause.append(".components) or ");
        }
        // Le dernier sans le "or"
        whereClause.append(pComponentsId.get(pComponentsId.size() - 1));
        whereClause.append(" in elements(");
        whereClause.append(getAlias());
        whereClause.append(".components)");
        whereClause.append(")");
        // On ne prend pas les audits dont le statut est dans pStatus
        if (null != pExcludedStatus) {
            for (int i = 0; i < pExcludedStatus.length; i++) {
                whereClause.append(" and ");
                whereClause.append(getAlias());
                whereClause.append(".status <> ");
                whereClause.append(pExcludedStatus[i]);
            }
        }
        // A partir de pDate si pDate n'est pas nulle
        if (null != pDate) {
            whereClause.append(" and ");
            whereClause.append(getAlias());
            whereClause.append(".date > ");
            whereClause.append(DAOUtils.makeQueryDate(pDate));
        }
        // Tri par date
        whereClause.append(" order by ");
        whereClause.append(getAlias());
        whereClause.append(".date desc");

        if (null == pNbAudits) {
            result = (List) findWhere(pSession, whereClause.toString());
        } else {
            result = (List) findWhereScrollable(pSession, whereClause.toString(), pNbAudits.intValue(), 0, false);
        }
        return result;
    }

    /**
     * @param pSession la session hibernate
     * @return l'audit de rotation
     * @throws JrafDaoException en cas d'échec de récupération de l'audit
     */
    public Collection findRotationAudit(ISession pSession) throws JrafDaoException {
        // Création de la clause where :
        String whereClause = "where " + getAlias() + ".name='" + ROTATION_AUDIT_NAME + "'";
        Collection result = findWhere(pSession, whereClause);
        return result;
    }

    /** le nombre de semaines prévues entre 2 rotations */
    public final static int ROTATION_DELAY_IN_WEEKS = 12;

    /** le nom donné à l'audit de rotation */
    public final static String ROTATION_AUDIT_NAME = "###_ROTATION_###";

    /**
     * Décale l'audit de rotation des partitions à la prochaine date prévue
     * Le délai prévu entre 2 rotations est de 12 semaines
     * @param pSession la session
     * @throws JrafDaoException en cas d'échec
     */
    public void reportRotationAudit(ISession pSession) throws JrafDaoException {
        // Récupère l'audit de rotation
        Collection result = findRotationAudit(pSession);
        if (result != null && result.size() != 0) {
            // il ne doit y avoir qu'un seul élément
            Iterator it = result.iterator();
            AuditBO rotationAudit = (AuditBO) it.next();
            Date currentDate = rotationAudit.getDate();
            // ajout le délai prévu
            GregorianCalendar currentCal = new GregorianCalendar();
            currentCal.setTime(currentDate);
            currentCal.add(Calendar.WEEK_OF_YEAR, ROTATION_DELAY_IN_WEEKS);
            rotationAudit.setDate(currentCal.getTime());
            // et mise à jour en base
            save(pSession, rotationAudit);
        }
    }
    
    /** Recherche les audits obsolètes au regard de la fréquence de purge 
     * de l'application. <br />
     * Pour les audits de jalon, seuls les audits obsolètes en échec 
     * sont pris en compte. <br />
     * Pour les audits de suivi, les derniers audits obsolètes ne sont pas pris 
     * en compte, pour conservation d'un historique (selon configuration). <br />
     * Les audits supprimés, en cours ou programmés ne sont pas pris en compte.
     * @param pSession session de persistence.
     * @param pSiteId id du serveur du batch.
     * @param pForbiddenApplis les ids des applications à ne pas prendre en compte
     * @return la collection d'audits obsolètes à supprimer.
     * @throws JrafDaoException si exception de persistence.
     */
    public List findObsoleteAuditsToDelete(ISession pSession, long pSiteId, Collection pForbiddenApplis) throws JrafDaoException {

        int index = ApplicationBO.class.getName().lastIndexOf(".");
        String lApplicationClassName = ApplicationBO.class.getName().substring(index + 1);
        String lApplicationAlias = lApplicationClassName.toLowerCase();

        index = getBusinessClass().getName().lastIndexOf(".");
        String lClassName = getBusinessClass().getName().substring(index + 1);

        // partie select
        String selectClause = "select " + getAlias() + ", " + lApplicationAlias
        + " from " + lClassName + " as " + getAlias()
        + ", " + lApplicationClassName + " as " + lApplicationAlias;

        // clause where
        StringBuffer whereClause = new StringBuffer (" where ");
        // selection des composants de type ApplicationBO
        whereClause.append(lApplicationAlias + ".id in elements(" + getAlias() + ".components)");
        if(pForbiddenApplis.size() > 0) {
            // qui ne sont pas dans la liste des ids passés en paramétre
            Iterator itForbidden = pForbiddenApplis.iterator();
            whereClause.append(" AND ");
            whereClause.append(lApplicationAlias + " not in (" + ((Long)itForbidden.next()).longValue());
            while(itForbidden.hasNext()) {
                whereClause.append(", " + ((Long)itForbidden.next()).longValue());
            }
            whereClause.append(")");
        }
        // selection du serveur
        whereClause.append(" and " + lApplicationAlias + ".serveurBO.serveurId = '" + pSiteId + "'");
        // selection des audits obsolètes
        whereClause.append(" and " + getAlias() + ".realBeginningDate + " + lApplicationAlias + ".resultsStorageOptions" + " < " + DAOUtils.makeQueryDate(new Date()));     
        // selection suivant le type d'audit
        whereClause.append(" and ((");        whereClause.append(getAlias() + ".type = '" + AuditBO.MILESTONE + "'");
        whereClause.append(" and " + getAlias() + ".status = '" + AuditBO.FAILED + "'");
        whereClause.append(") or (");
        whereClause.append(getAlias() + ".type = '" + AuditBO.NORMAL + "'");
        whereClause.append(" and " + getAlias() + ".status in ");
        whereClause.append("('" + AuditBO.FAILED + "', '" + AuditBO.PARTIAL + "', '" + AuditBO.TERMINATED + "')");
        whereClause.append("))");

        // clause order
        String orderClause = " order by " + getAlias() + ".realBeginningDate desc";

        return find(pSession, selectClause + whereClause.toString() + orderClause);
    }
    
}
