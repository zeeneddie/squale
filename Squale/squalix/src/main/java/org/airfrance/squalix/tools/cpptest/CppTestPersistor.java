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
package com.airfrance.squalix.tools.cpptest;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.daolayer.rulechecking.CppTestRuleSetDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.CppTestRuleSetDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.CppTestTransgressionBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionItemBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.cpptest.CppTestRuleSetBO;

/**
 * Persistance des résultats CppTest
 */
public class CppTestPersistor
{

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( CppTestPersistor.class );

    /**
     * Sauvegarde des résultats
     * 
     * @param pSession session
     * @param pProjectBO projet
     * @param pAuditBO audit
     * @param pResults résultats
     * @param pRuleSet ruleset
     * @throws JrafDaoException si erreur
     */
    public void storeResults( ISession pSession, ProjectBO pProjectBO, AuditBO pAuditBO, Map pResults,
                              CppTestRuleSetDTO pRuleSet )
        throws JrafDaoException
    {
        // On récupère le jeu de règles associé au projet
        CppTestRuleSetBO ruleset =
            (CppTestRuleSetBO) CppTestRuleSetDAOImpl.getInstance().get( pSession, new Long( pRuleSet.getId() ) );
        // Création de la transgression
        CppTestTransgressionBO transgression = new CppTestTransgressionBO();
        transgression.setAudit( pAuditBO );
        transgression.setComponent( pProjectBO );
        transgression.setRuleSet( ruleset );
        transgression.setTaskName( "task.cpptest.name" );
        // On parcourt les règles connues dans le ruleset
        Iterator ruleCodes = ruleset.getRules().values().iterator();
        while ( ruleCodes.hasNext() )
        {
            RuleBO rule = (RuleBO) ruleCodes.next();
            Collection details = (Collection) pResults.get( rule.getCode() );
            int nbOcc = 0;
            // Si le parsing n'a pas donné de résultat, on place 0 comme
            // nombre de transgression
            if ( details != null )
            {
                nbOcc = details.size();
                // On parcourt le détail des transgressions
                int cpt = RuleCheckingTransgressionBO.MAX_DETAILS;
                for ( Iterator detailIt = details.iterator(); detailIt.hasNext() && cpt > 0; cpt-- )
                {
                    RuleCheckingTransgressionItemBO item = (RuleCheckingTransgressionItemBO) detailIt.next();
                    item.setRule( rule );
                    transgression.getDetails().add( item );
                }
            }
            // On ajoute une métrique de type Integer pour chaque règle transgressée
            // avec 0 comme valeur par défaut
            IntegerMetricBO metric = new IntegerMetricBO();
            metric.setName( rule.getCode() );
            metric.setValue( nbOcc );
            metric.setMeasure( transgression );
            transgression.putMetric( metric );
        }
        // On parcourt les règles non trouvées dans le ruleset
        ruleCodes = pResults.keySet().iterator();
        while ( ruleCodes.hasNext() )
        {
            String ruleCode = (String) ruleCodes.next();
            // Chaque règle non trouvée est signalée comme
            // manquante
            if ( false == ruleset.getRules().containsKey( ruleCode ) )
            {
                LOGGER.warn( CppTestMessages.getString( "rule.ignored", ruleCode ) );
            }
        }
        // Sauvegarde des données dans la base
        MeasureDAOImpl.getInstance().create( pSession, transgression );
    }

}
