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
package com.airfrance.squalecommon.enterpriselayer.facade.macker;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.rulechecking.ProjectRuleSetDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.ProjectRuleSetDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.rulechecking.ProjectRuleSetTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.ProjectRuleSetBO;

/**
 * Facade pour Macker
 */
public class MackerFacade
{

    /**
     * Provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /** Log */
    private static Log LOG = LogFactory.getLog( MackerFacade.class );

    /**
     * Parsing du fichier de configuration
     * 
     * @param pProject le project associé
     * @param pStream flux à lire
     * @param pErrors erreurs rencontrées
     * @return ruleset créé
     * @throws JrafEnterpriseException si erreur
     */
    public static ProjectRuleSetDTO importMackerConfig( ProjectBO pProject, InputStream pStream, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        ISession session = null;
        ProjectRuleSetDTO dto = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            // Importation du fichier
            MackerConfigParser parser = new MackerConfigParser();
            ProjectRuleSetBO ruleset = parser.parseFile( pStream, pErrors );
            // Si le parsing ne provoque pas d'erreur, on tente la sauvegarde dans la base
            if ( pErrors.length() == 0 )
            {
                ruleset.setProject( pProject );
                ProjectRuleSetDAOImpl dao = ProjectRuleSetDAOImpl.getInstance();
                dao.create( session, ruleset );
                dto = ProjectRuleSetTransform.bo2Dto( ruleset );
            }
        }
        catch ( JrafDaoException e )
        {
            LOG.error( e.getMessage(), e );
            FacadeHelper.convertException( e, MackerFacade.class.getName() + ".get" );
        }
        finally
        {
            // Fermeture de la session
            FacadeHelper.closeSession( session, MackerFacade.class.getName() + ".get" );
        }
        return dto;
    }
}
