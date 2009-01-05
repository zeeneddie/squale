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
package com.airfrance.squalecommon.daolayer.result.rulechecking;

import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionBO;
import com.airfrance.squalecommon.util.ConstantRulesChecking;

/**
 * Classe DAO pour l'insertion des transgressions
 */
public class RuleCheckingTransgressionDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static RuleCheckingTransgressionDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new RuleCheckingTransgressionDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private RuleCheckingTransgressionDAOImpl()
    {
        initialize( RuleCheckingTransgressionBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static RuleCheckingTransgressionDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Permet de récupérer les transgressions en fonction d'un audit
     * 
     * @param pSession session Hiebrnate
     * @param pComponentID identifiant du composant
     * @param pAuditID identifiant de l'audit
     * @return une collection de transgressions
     * @throws JrafDaoException exception DAO
     */
    public Collection load( ISession pSession, Long pComponentID, Long pAuditID )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".component.id = '" + pComponentID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".audit.id = '" + pAuditID + "'";
        Collection measures = findWhere( pSession, whereClause );
        return measures;
    }

    /**
     * Test d'utilisation d'un ruleset
     * 
     * @param pSession session
     * @param pRuleSetId id du ruleset
     * @return true si le ruleset est utilisé dans une mesure
     * @throws JrafDaoException si erreur
     */
    public boolean isRuleSetUsed( ISession pSession, Long pRuleSetId )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".ruleSet.id = " + pRuleSetId;
        Collection measures = findWhere( pSession, whereClause );
        return measures.size() > 0;
    }

    /**
     * @param pSession session
     * @param pAuditId l'id de l'audit
     * @return le nombre d'erreur et de warning
     * @throws JrafDaoException si erreur
     */
    public int findNbErrorAndWarning( ISession pSession, Long pAuditId )
        throws JrafDaoException
    {
        String requete = "select count(m.id) from RuleCheckingTransgressionBO as m, RuleBO as r";
        String whereClause = " where ";
        whereClause += "m.audit.id = '" + pAuditId + "'";
        whereClause += " and ";
        whereClause += " r.ruleSet.id=m.ruleSet.id";
        whereClause += " and ";
        whereClause += " (r.severity='" + ConstantRulesChecking.ERROR_LABEL + "'";
        whereClause += " or r.severity='" + ConstantRulesChecking.WARNING_LABEL + "')";
        Collection result = find( pSession, requete + whereClause );
        return ( (Integer) result.iterator().next() ).intValue();
    }
}
