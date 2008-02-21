package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.io.InputStream;
import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.PmdRuleSetDTO;
import com.airfrance.squalecommon.enterpriselayer.facade.pmd.PmdFacade;

/**
 * Application Component de PMD
 */
public class PmdAdminComponentAccess extends DefaultExecuteComponent {

    /**
     * 
     * @param pStream flux
     * @param pErrors détail des erreurs rencontrées
     * @return configuration importée
     * @throws JrafEnterpriseException si erreur
     */
    public PmdRuleSetDTO importConfiguration(InputStream pStream, StringBuffer pErrors) throws JrafEnterpriseException {
        return PmdFacade.importPmdConfig(pStream, pErrors);
    }

    /**
     * Obtention de toutes les configurations CppTest
     * @return collection de CppTestRuleSetDTO
     * @throws JrafEnterpriseException exception JRAF
     */
    public Collection getAllConfigurations() throws JrafEnterpriseException{
        
        Collection configurations=PmdFacade.getAllPmdConfigs();
              
        return configurations;
    }

    /**
     * Destruction des jeu de règles inutilisés
     * @param pRuleSets rulesets devant être détruits
     * @return rulesets utilisés et ne pouvant donc pas être détruits
     * @throws JrafEnterpriseException si erreur
     */
    public Collection deleteRuleSets(Collection pRuleSets) throws JrafEnterpriseException {
        return PmdFacade.deletePmdConfigs(pRuleSets);
    }
}
