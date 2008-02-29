package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.administration;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.datatransfertobject.component.ApplicationConfDTO;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;

/**
 * @author M400841
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class PurgeApplicationComponentAccessTest extends SqualeTestCase {
    
    /** provider de persistence */
    private static IPersistenceProvider PERSISTENT_PROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * test sur la purge d'un composant de niveau application
     * @throws JrafEnterpriseException en cas d'échec du test
     */
    public void testPurgeApplicationComponentAccess() throws JrafEnterpriseException {
        // Teste si la construction de l'application component par AccessDelegateHelper
        IApplicationComponent appComponent;
        appComponent = AccessDelegateHelper.getInstance("Purge");
        assertNotNull(appComponent);

    }

    /**
     * test sur la purge d'une application
     * @throws JrafEnterpriseException en cas d'échec du test
     */
    public void testPurgeApplication() throws JrafEnterpriseException {
        try {
            ISession session = PERSISTENT_PROVIDER.getSession();
            ApplicationBO application = getComponentFactory().createApplication(session);
            FacadeHelper.closeSession(session, "");
            // Teste si la methode est accessible par AccessDelegateHelper
            // et si l'objet renvoyé n'est pas nul
            IApplicationComponent appComponent;
        
            // Initialisation des parametres de la methode
            ApplicationConfDTO applicationConf = new ApplicationConfDTO(); // à initialiser
            applicationConf.setId(application.getId());
            
            // Initialisation des parametres sous forme de tableaux d'objets
            Object[] paramIn = new Object[1];
            paramIn[0] = applicationConf;
        
            // Execution de la methode
            appComponent = AccessDelegateHelper.getInstance("Purge");
            appComponent.execute("purgeApplication", paramIn);
        } catch (Exception e) {
            e.printStackTrace();
            fail("unexpected error");
        }
    }


    /**
     * test sur la purge d'un audit
     * @throws JrafEnterpriseException en cas d'échec du test
     */
    public void testPurgeAudit() throws JrafEnterpriseException {
        // Teste si la methode est accessible par AccessDelegateHelper
        // et si l'objet renvoyé n'est pas nul
        IApplicationComponent appComponent;
        
        // Initialisation des parametres de la methode
        AuditDTO audit = new AuditDTO(); // à initialiser
        ComponentDTO application = new ComponentDTO(); // à initialiser
        audit.setApplicationId(application.getID());
        
        // Initialisation des parametres sous forme de tableaux d'objets
        Object[] paramIn = new Object[1];
        paramIn[0] = audit;
        
        // Execution de la methode
        appComponent = AccessDelegateHelper.getInstance("Purge");
        appComponent.execute("purgeAudit", paramIn);
    }

}
