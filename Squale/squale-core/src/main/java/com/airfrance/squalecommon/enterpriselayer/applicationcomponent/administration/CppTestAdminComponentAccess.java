package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.io.InputStream;
import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.CppTestRuleSetDTO;
import com.airfrance.squalecommon.enterpriselayer.facade.cpptest.CppTestFacade;

/**
 * Application component de l'outil CppTest
 */
public class CppTestAdminComponentAccess  extends DefaultExecuteComponent {

    
    /**
     * Obtention de toutes les configurations CppTest
     * @return collection de CppTestRuleSetDTO
     * @throws JrafEnterpriseException exception JRAF
     */
    public Collection getAllConfigurations() throws JrafEnterpriseException{
        
        Collection configurations=CppTestFacade.getCppTestConfigurations();
              
        return configurations;
    }
    
    /**
     * Importation d'une configuration CppTest
     * @param pStream flux contenant les données
     * @param pErrors les erreurs générés pendant le parsing. 
     * @return un VersionDTO Complet
     * @throws JrafEnterpriseException exception JRAF
     */
    public CppTestRuleSetDTO importConfiguration(InputStream pStream,StringBuffer pErrors) throws JrafEnterpriseException{
        
        CppTestRuleSetDTO result;
        result = CppTestFacade.importCppTestConfig(pStream,pErrors);          
        return result;
    }

    /**
     * Destruction des jeu de règles inutilisés
     * @param pRuleSets rulesets devant être détruits
     * @return rulesets utilisés et ne pouvant donc pas être détruits
     * @throws JrafEnterpriseException si erreur
     */
    public Collection deleteRuleSets(Collection pRuleSets) throws JrafEnterpriseException {
        return CppTestFacade.deleteRuleSets(pRuleSets);
    }
}
