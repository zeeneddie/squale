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
package com.airfrance.squalecommon.daolayer.rule;

import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.AbstractFormulaBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.SimpleFormulaBO;

/**
 * Tests de la couche DAO des règles qualité
 */
public class QualityRuleDAOImplTest
    extends SqualeTestCase
{

    /**
     * Test du findAll
     */
    public void testFindAll()
    {
        QualityRuleDAOImpl daoImpl = QualityRuleDAOImpl.getInstance();
        final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            assertEquals( 0, daoImpl.findAll( session ).size() );
            FacadeHelper.closeSession( session, "" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }
    }

    /**
     * Test du delete en cascade On vérifie qu'un facteur lorsqu'il est détruit provoque la destruction des données
     * rattachées
     */
    public void testDeleteCascade()
    {
        QualityRuleDAOImpl ruleDaoImpl = QualityRuleDAOImpl.getInstance();
        AbstractDAOImpl formulaDaoImpl = new AbstractDAOImpl()
        {
            {
                initialize( AbstractFormulaBO.class );
            }
        };
        final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            assertEquals( "condition initiale", 0, ruleDaoImpl.findAll( session ).size() );
            assertEquals( "condition initiale", 0, formulaDaoImpl.findAll( session ).size() );
            // On cree un facteur/critere/pratique/formule que l'on détruit
            // pour vérifier le delete cascade
            int nb = 0;
            SimpleFormulaBO formula = new SimpleFormulaBO();
            PracticeRuleBO practice = new PracticeRuleBO();
            practice.setFormula( formula );
            ruleDaoImpl.create( session, practice );
            nb++; // Objet créé
            CriteriumRuleBO criterium = new CriteriumRuleBO();
            criterium.addPractice( practice, new Float( 1 ) );
            ruleDaoImpl.create( session, criterium );
            nb++; // Objet créé
            FactorRuleBO factor = new FactorRuleBO();
            factor.addCriterium( criterium, new Float( 1 ) );
            ruleDaoImpl.create( session, factor );
            nb++; // Objet créé
            assertEquals( nb, ruleDaoImpl.count( session ).intValue() );
            ruleDaoImpl.remove( session, factor );
            assertEquals( "condition finale", 0, ruleDaoImpl.count( session ).intValue() );
            assertEquals( "condition finale", 0, formulaDaoImpl.findAll( session ).size() );
            FacadeHelper.closeSession( session, "" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }
    }

}
