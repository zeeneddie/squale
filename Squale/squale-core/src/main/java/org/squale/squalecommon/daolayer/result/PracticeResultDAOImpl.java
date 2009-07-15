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
/*
 * Créé le 19 juil. 05
 *
 */
package org.squale.squalecommon.daolayer.result;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.rule.PracticeRuleAPDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.result.PracticeResultBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.AbstractFormulaBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;
import org.squale.squalecommon.util.mapping.Mapping;

/**
 * @author M400843
 */
public final class PracticeResultDAOImpl
    extends AbstractDAOImpl
{

    /** log */
    private static Log LOG = LogFactory.getLog( PracticeResultDAOImpl.class );

    /**
     * Instance singleton
     */
    private static PracticeResultDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new PracticeResultDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private PracticeResultDAOImpl()
    {
        initialize( PracticeResultBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static PracticeResultDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * @param pSession la session hibernate
     * @param pProjectId l'id du projet
     * @param pAuditId l'id de l'audit
     * @return les pratiques les plus faciles à corriger parmis les plus mauvaises pratiques du projet
     * @throws JrafDaoException si erreur
     */
    public Collection findPracticesForActionPlan( final ISession pSession, final String pProjectId,
                                                  final String pAuditId )
        throws JrafDaoException
    {
        /**
         * Class de comparaison de l'effort de correction pour un résulat de pratique
         */
        class WorstPratice
            implements Comparator
        {

            private HashMap mCache = new HashMap();

            /**
             * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
             */
            public int compare( Object o1, Object o2 )
            {
                int result = 0;
                try
                {
                    PracticeResultBO p1 = (PracticeResultBO) o1;
                    PracticeResultBO p2 = (PracticeResultBO) o2;
                    if ( (int) ( p1.getMeanMark() ) == (int) ( p2.getMeanMark() ) )
                    {
                        result = getCost( p1 ).compareTo( getCost( p2 ) );
                    }
                    else
                    {
                        result = new Float( p1.getMeanMark() ).compareTo( new Float( p2.getMeanMark() ) );
                    }
                }
                catch ( JrafDaoException e )
                {
                    LOG.error( e );
                }
                catch ( NumberFormatException e )
                {
                    LOG.error( e );
                }
                return result;
            }

            /**
             * @param p le résultat de la pratique
             * @throws JrafDaoException si erreur JRAF
             * @return le coût
             * @throws NumberFormatException si erreur de format
             */
            private Integer getCost( PracticeResultBO p )
                throws JrafDaoException, NumberFormatException
            {

                if ( !mCache.containsKey( p ) )
                {
                    MarkDAOImpl markDAO = MarkDAOImpl.getInstance();
                    MeasureDAOImpl measureDAO = MeasureDAOImpl.getInstance();
                    int nbcorrections = 0;
                    PracticeRuleBO rule =
                        (PracticeRuleBO) PracticeRuleAPDAOImpl.getInstance().load( pSession,
                                                                                   new Long( p.getRule().getId() ) );
                    // Rulechecking ?
                    if ( ( rule.getFormula() != null ) && rule.getFormula().getComponentLevel().equals( "project" ) )
                    {
                        // Récupération des types de mesure traités par la formule
                        AbstractFormulaBO formula = rule.getFormula();
                        // Pour chaque type de mesure, on va récupérer le nom de sa classe
                        String[] measureKinds = new String[formula.getMeasureKinds().size()];
                        formula.getMeasureKinds().toArray( measureKinds );
                        for ( int i = 0; i < measureKinds.length; i++ )
                        {
                            Class currentClass =
                                Mapping.getMeasureClass( measureKinds[i] + "." + formula.getComponentLevel() );
                            if ( currentClass.getSuperclass().equals( RuleCheckingTransgressionBO.class ) )
                            {
                                RuleCheckingTransgressionBO trans =
                                    (RuleCheckingTransgressionBO) measureDAO.load(
                                                                                   pSession,
                                                                                   new Long(
                                                                                             Long.parseLong( pProjectId ) ),
                                                                                   new Long( Long.parseLong( pAuditId ) ),
                                                                                   currentClass );
                                nbcorrections +=
                                    trans.getTotalInfoNumberForCategory( rule.getName() )
                                        + trans.getTotalWarningNumberForCategory( rule.getName() )
                                        + trans.getTotalErrorNumberForCategory( rule.getName() );
                            }
                        }
                    }
                    else
                    {
                        // On récupère les plus mauvais composants pour cette pratiques
                        /*
                         * We retrieve, for a given practice, the number of technical component which have a worst
                         * practice mark than the mark of the same practice at the project level
                         */
                        nbcorrections = markDAO.countWorstWhere( pSession, new Long( p.getId() ), p.getMeanMark() );
                    }
                    mCache.put( p, new Integer( nbcorrections * rule.getEffort() ) );
                }
                return (Integer) mCache.get( p );
            }
        }
        String where =
            "where " + getAlias() + " .meanMark < 3.0 and " + getAlias() + ".project.id=" + pProjectId + " and "
                + getAlias() + ".audit.id=" + pAuditId + " and " + getAlias() + ".meanMark != -1";
        List c = findWhere( pSession, where );

        Collections.sort( c, new WorstPratice() );
        return c;

    }

}
