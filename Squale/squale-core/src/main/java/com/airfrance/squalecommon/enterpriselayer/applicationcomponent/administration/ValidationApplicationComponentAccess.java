//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\applicationcomponent\\ValidationApplicationComponentAccess.java

package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.applicationcomponent.ACMessages;
import com.airfrance.squalecommon.enterpriselayer.facade.component.ApplicationFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.quality.SqualeReferenceFacade;

/**
 * <p>Title : ValidationApplicationComponentAccess.java</p>
 * <p>Description : Application component de validation de creation d'applications </p>
 * <p>Copyright : Copyright (c) 2005</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ValidationApplicationComponentAccess extends DefaultExecuteComponent {

    /**
     * log
     */
    private static final Log LOG = LogFactory.getLog(ValidationApplicationComponentAccess.class);

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Constructeur par défaut
     * @roseuid 42CBFC030151
     */
    public ValidationApplicationComponentAccess() {
    }

    /**
     * liste le référentiel
     * @return les différentes références
     * @throws JrafEnterpriseException en cas de problème de récupération des donnés
     * @throws JrafPersistenceException en cas de problème de persistence
     */
    public Collection listReferentiel() throws JrafEnterpriseException, JrafPersistenceException {
        Collection result = null;
        result = SqualeReferenceFacade.listReferentiel();
        return result;
    }

    /**
     * Permet de supprimer une collection d'applications créés non valides
     * @param pApplicationsToRemove applications à supprimer
     * @return Integer : 0 pour la réussite de la methode sinon 1 
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFC030179
     */
    public Integer removeApplicationsCreation(Collection pApplicationsToRemove) throws JrafEnterpriseException {
        Integer status = new Integer(0);
        ISession session = null;
        try {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Suppression d'applications via la facade ApplicationFacade
            ApplicationFacade.deleteApplicationConfList(pApplicationsToRemove, session);
            session.commitTransaction();
        } catch (Exception e) {
            // on rollback la transaction si nécessaire
            String message = ACMessages.getString("ac.exception.validation.removeapplicationscreation");
            status = new Integer(1);
            if (session != null) {
                session.rollbackTransaction();
            }
            LOG.fatal(message, e);
            throw new JrafEnterpriseException(message, e);
        }
        return status;
    }

    /**
     * Permet de valider une collection d'applications créés
     * @param pApplicationsToValidate collection d'applications à valider
     * @return null si il y a eu un problème, l'ensemble des applications non validées
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFC0301A1
     */
    public List validateApplicationsCreation(Collection pApplicationsToValidate) throws JrafEnterpriseException {
        List notValidated = new ArrayList();
        ISession session = null;
        try {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Validation de l'application via la facade ApplicationFacade
            notValidated = ApplicationFacade.validateApplicationConfList(pApplicationsToValidate, session);
            session.commitTransaction();
        } catch (Exception e) {
            // on rollback la transaction si nécessaire
            String message = ACMessages.getString("ac.exception.validation.validateapplicationscreation");
            if (session != null) {
                session.rollbackTransaction();
            }
            LOG.fatal(message, e);
            throw new JrafEnterpriseException(message, e);
        }
        return notValidated;
    }

    /**
     * Permet de récupérer la liste des applications ayant été créés à vérifier et valider
     * @return Collection de ApplicationConfDTO à vérifier
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFC0301B5
     */
    public Collection getApplicationsCreated() throws JrafEnterpriseException {
        Collection collection = ApplicationFacade.getApplicationConfList();
        return collection;
    }

    /**
     * Permet de supprimer une collection de résultats d'une application du référentiel de comparaison
     * @param pAuditsToRemove collection de SqualeReferenceDTO à supprimer
     * @return Integer : 0 pour la réussite de la methode sinon 1 
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFC0301DE
     */
    public Integer removeApplicationsReference(Collection pAuditsToRemove) throws JrafEnterpriseException {
        Integer status = new Integer(0);
        ISession session = null;
        try {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Suppression de references via SqualeReferenceFacade
            SqualeReferenceFacade.deleteAuditList(pAuditsToRemove, session);
            session.commitTransaction();
        } catch (Exception e) {
            // on rollback la transaction si nécessaire
            String message = ACMessages.getString("ac.exception.validation.removeapplicationsreference");
            status = new Integer(1);
            if (session != null) {
                session.rollbackTransaction();
            }
            LOG.fatal(message, e);
            throw new JrafEnterpriseException(message, e);
        }
        return status;
    }

    /**
     * Permet mettre à jour le status des applications du référentiel indiquant si 
     * les projets sont affichés ou pas dans le référentiel
     * @param pReferences collection de SqualeReferenceDTO à valider
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFC0301F1
     */
    public void updateReferentiel(Collection pReferences) throws JrafEnterpriseException {
        ISession session = null;
        try {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Validation de references via la facade SqualeReferenceFacade

            SqualeReferenceFacade.updateReferentiel(pReferences, session);
            session.commitTransaction();
        } catch (Exception e) {
            // on rollback la transaction si nécessaire
            String message = ACMessages.getString("ac.exception.validation.validateapplicationsreference");
            if (session != null) {
                session.rollbackTransaction();
            }
            LOG.fatal(message, e);
            throw new JrafEnterpriseException(message, e);
        }
    }
}
