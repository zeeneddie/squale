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
package org.squale.squalecommon.enterpriselayer.facade.pmd;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.result.rulechecking.RuleCheckingTransgressionDAOImpl;
import org.squale.squalecommon.daolayer.rulechecking.PmdRuleSetDAOImpl;
import org.squale.squalecommon.datatransfertobject.rulechecking.PmdRuleSetDTO;
import org.squale.squalecommon.datatransfertobject.transform.rulechecking.PmdTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.pmd.PmdRuleSetBO;
import org.squale.squalecommon.util.file.FileUtility;

/**
 * Facade pour PMD
 */
public class PmdFacade
{

    /**
     * Provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /** Log */
    private static Log LOG = LogFactory.getLog( PmdFacade.class );

    /**
     * Parsing du fichier de configuration
     * 
     * @param pStream flux à lire
     * @param pErrors erreurs rencontrées
     * @return ruleset créé
     * @throws JrafEnterpriseException si erreur
     */
    public static PmdRuleSetDTO importPmdConfig( InputStream pStream, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        ISession session = null;
        PmdRuleSetDTO dto = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            PmdConfigParser parser = new PmdConfigParser();
            // Importation du fichier
            byte[] bytes = FileUtility.toByteArrayImpl( pStream );
            PmdRuleSetBO ruleset = parser.parseFile( new ByteArrayInputStream( bytes ), pErrors );
            if ( ruleset.getLanguage().equals( "jsp" ) )
            {
                // On ajoute une règle pour remonter les erreurs de parsing dû aux JSPs con conforme
                // au XHTML
                RuleBO xhtmlRule = new RuleBO();
                xhtmlRule.setRuleSet( ruleset );
                xhtmlRule.setCode( PmdRuleSetBO.XHTML_RULE_NAME );
                xhtmlRule.setCategory( PmdRuleSetBO.XHTML_RULE_CATEGORY );
                xhtmlRule.setSeverity( PmdRuleSetBO.XHTML_RULE_SEVERITY );
                ruleset.addRule( xhtmlRule );
            }
            if ( pErrors.length() == 0 )
            {
                // On lui positionne le numéro de version
                ruleset.setValue( bytes );
                ruleset = PmdRuleSetDAOImpl.getInstance().createPmdRuleSet( session, ruleset );
                // 
                dto = PmdTransform.bo2Dto( ruleset );
            }
        }
        catch ( JrafDaoException e )
        {
            LOG.error( e.getMessage(), e );
            FacadeHelper.convertException( e, PmdFacade.class.getName() + ".importPmdConfig" );
        }
        catch ( IOException e )
        {
            LOG.error( e.getMessage(), e );
            FacadeHelper.convertException( e, PmdFacade.class.getName() + ".importPmdConfig" );
        }
        finally
        {
            // Fermeture de la session
            FacadeHelper.closeSession( session, PmdFacade.class.getName() + ".importPmdConfig" );
        }
        return dto;
    }

    /**
     * Obtention de tous les rulesets Pmd
     * 
     * @return tous les rulesets PMD sous la forme de PmdRuleSetDTO
     * @throws JrafEnterpriseException si erreur
     */
    public static Collection getAllPmdConfigs()
        throws JrafEnterpriseException
    {
        Collection result = new ArrayList();
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            List rulesets = PmdRuleSetDAOImpl.getInstance().findAll( session );
            for ( Iterator it = rulesets.iterator(); it.hasNext(); )
            {
                result.add( PmdTransform.bo2Dto( (PmdRuleSetBO) it.next() ) );
            }
        }
        catch ( JrafDaoException e )
        {
            LOG.error( e.getMessage(), e );
            FacadeHelper.convertException( e, PmdFacade.class.getName() + ".getAllPmdConfigs" );
        }
        finally
        {
            // Fermeture de la session
            FacadeHelper.closeSession( session, PmdFacade.class.getName() + ".getAllPmdConfigs" );
        }
        return result;
    }

    /**
     * Suppression des configurations PMD
     * 
     * @param pRuleSets collection de rulesets PMD sous la forme de PmdRuleSetDTO
     * @return rulesets ne pouvant être détruits car utilisés
     * @throws JrafEnterpriseException si erreur
     */
    public static Collection deletePmdConfigs( Collection pRuleSets )
        throws JrafEnterpriseException
    {
        ISession session = null;
        Collection result = new ArrayList();
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            PmdRuleSetDAOImpl pmdRuleSetDAO = PmdRuleSetDAOImpl.getInstance();
            Iterator ruleSetsIt = pRuleSets.iterator();
            // Parcours des rulesets à détruire
            ArrayList rulesetsId = new ArrayList();
            RuleCheckingTransgressionDAOImpl ruleCheckingTransgressionDAO =
                RuleCheckingTransgressionDAOImpl.getInstance();
            while ( ruleSetsIt.hasNext() )
            {
                PmdRuleSetDTO checkstyleDTO = (PmdRuleSetDTO) ruleSetsIt.next();
                Long ruleSetId = new Long( checkstyleDTO.getId() );
                // On vérifie que le jeu de règles n'est pas utilisé
                // au niveau des mesures réalisées, pour les projets paramétrés mais non encore audités, on ne le gère
                // pas
                if ( ruleCheckingTransgressionDAO.isRuleSetUsed( session, ruleSetId ) )
                {
                    result.add( checkstyleDTO );
                }
                else
                {
                    // Ajout dans les rulesets à détruire
                    rulesetsId.add( ruleSetId );
                }
            }
            // Destruction des rulesets qui ne sont plus référencés
            if ( rulesetsId.size() > 0 )
            {
                pmdRuleSetDAO.removePmdRuleSets( session, rulesetsId );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, PmdFacade.class.getName() + ".get" );
        }
        finally
        {
            FacadeHelper.closeSession( session, PmdFacade.class.getName() + ".get" );
        }
        return result;
    }
}
