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
package com.airfrance.squalecommon.daolayer.rulechecking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleSetBO;

/**
 * @author henix
 */
public class CheckstyleRuleSetDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Instance singleton
     */
    private static CheckstyleRuleSetDAOImpl instance = null;

    /**
     * initialisation du singleton
     */
    static
    {
        instance = new CheckstyleRuleSetDAOImpl();
    }

    /**
     * Constructeur privé
     * 
     * @throws JrafDaoException
     */
    private CheckstyleRuleSetDAOImpl()
    {
        initialize( CheckstyleRuleSetBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static CheckstyleRuleSetDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Enregistre une versions du fichier checkstyle dans la base
     * 
     * @param pSession ISession
     * @param pVersionBO VersionBO
     * @return ruleset créé
     * @throws JrafDaoException exception JRAF
     */
    public CheckstyleRuleSetBO createCheckstyleRuleSet( ISession pSession, CheckstyleRuleSetBO pVersionBO )
        throws JrafDaoException
    {
        create( pSession, pVersionBO );
        return pVersionBO;
    }

    /**
     * Récupère la dernière version du fichier de configuration checkstyle
     * 
     * @param pSession la session
     * @param pName nom du ruleset
     * @return la version
     * @throws JrafDaoException exception JRAF
     */

    public CheckstyleRuleSetBO getLastVersion( ISession pSession, String pName )
        throws JrafDaoException
    {

        CheckstyleRuleSetBO version = null;
        CheckstyleRuleSetBO lastVersion = null;
        StringBuffer whereClause = new StringBuffer();
        whereClause.append( "where " ).append( getAlias() ).append( ".name='" ).append( pName ).append( '\'' );
        whereClause.append( " order by " ).append( getAlias() ).append( ".dateOfUpdate DESC" );
        Collection lesVersion = findWhere( pSession, whereClause.toString() );
        if ( lesVersion.size() > 0 )
        {
            lastVersion = (CheckstyleRuleSetBO) lesVersion.iterator().next();
        }
        return lastVersion;
    }

    /**
     * Destruction de rulesets
     * 
     * @param pSession session
     * @param pRuleSetsId ids des rulesets
     * @throws JrafDaoException si erreur
     */
    public void removeCheckstyleRuleSets( ISession pSession, ArrayList pRuleSetsId )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() );
        whereClause.append( ".id in(" );
        Iterator ruleSetsIdIt = pRuleSetsId.iterator();
        boolean comma = false;
        // Parcours des ids de ruleset pour construire la liste dans la requête
        while ( ruleSetsIdIt.hasNext() )
        {
            if ( comma )
            {
                whereClause.append( ", " );
            }
            else
            {
                comma = true;
            }
            whereClause.append( ruleSetsIdIt.next() );
        }
        whereClause.append( ")" );
        Iterator ruleSetsIt = findWhere( pSession, whereClause.toString() ).iterator();
        // Suppression de chaque jeu de règles
        while ( ruleSetsIt.hasNext() )
        {
            remove( pSession, ruleSetsIt.next() );
        }
    }

    /**
     * @param pSession session
     * @return liste triée par nom et par date des rulesets
     * @throws JrafDaoException si erreur
     */
    public Collection findAllSorted( ISession pSession )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer();
        whereClause.append( "order by " ).append( getAlias() ).append( ".name ASC, " ).append( getAlias() ).append(
                                                                                                                    ".dateOfUpdate DESC" );
        return findWhere( pSession, whereClause.toString() );
    }
}
