/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.squale.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.io.InputStream;
import java.util.Collection;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.squalecommon.datatransfertobject.rulechecking.PmdRuleSetDTO;
import org.squale.squalecommon.enterpriselayer.facade.pmd.PmdFacade;

/**
 * Application Component de PMD
 */
public class PmdAdminComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * @param pStream flux
     * @param pErrors détail des erreurs rencontrées
     * @return configuration importée
     * @throws JrafEnterpriseException si erreur
     */
    public PmdRuleSetDTO importConfiguration( InputStream pStream, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        return PmdFacade.importPmdConfig( pStream, pErrors );
    }

    /**
     * Obtention de toutes les configurations CppTest
     * 
     * @return collection de CppTestRuleSetDTO
     * @throws JrafEnterpriseException exception JRAF
     */
    public Collection getAllConfigurations()
        throws JrafEnterpriseException
    {

        Collection configurations = PmdFacade.getAllPmdConfigs();

        return configurations;
    }

    /**
     * Destruction des jeu de règles inutilisés
     * 
     * @param pRuleSets rulesets devant être détruits
     * @return rulesets utilisés et ne pouvant donc pas être détruits
     * @throws JrafEnterpriseException si erreur
     */
    public Collection deleteRuleSets( Collection pRuleSets )
        throws JrafEnterpriseException
    {
        return PmdFacade.deletePmdConfigs( pRuleSets );
    }
}
