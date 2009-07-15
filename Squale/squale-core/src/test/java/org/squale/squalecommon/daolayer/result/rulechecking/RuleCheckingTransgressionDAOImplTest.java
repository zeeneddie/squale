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
package org.squale.squalecommon.daolayer.result.rulechecking;

import java.util.HashMap;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.daolayer.result.MeasureDAOImpl;
import org.squale.squalecommon.daolayer.rulechecking.ProjectRuleSetDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.ProjectRuleSetBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;

/**
 * Teste les opérations du la table des transgressions
 */
public class RuleCheckingTransgressionDAOImplTest
    extends SqualeTestCase
{

    /** L'audit */
    private AuditBO auditBO;

    /**
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp()
        throws Exception
    {
        super.setUp();
        getSession().beginTransaction();
        ApplicationBO appli = getComponentFactory().createApplication( getSession() );
        QualityGridBO grid = getComponentFactory().createGrid( getSession() );
        ProjectBO projectBO = getComponentFactory().createProject( getSession(), appli, grid );
        auditBO = getComponentFactory().createAudit( getSession(), projectBO );
        PackageBO packageBO = getComponentFactory().createPackage( getSession(), projectBO );
        ProjectRuleSetBO ruleSetBO = new ProjectRuleSetBO();
        ruleSetBO.setName( "test" );
        ruleSetBO.setProject( projectBO );
        ProjectRuleSetDAOImpl ruleSetDao = ProjectRuleSetDAOImpl.getInstance();
        ruleSetDao.create( getSession(), ruleSetBO );
        HashMap rules = new HashMap();
        RuleBO ruleBO1 = new RuleBO();
        ruleBO1.setCode( "Code1" );
        ruleBO1.setCategory( "layering" );
        ruleBO1.setSeverity( "error" );
        ruleBO1.setRuleSet( ruleSetBO );
        rules.put( "Code1", ruleBO1 );
        RuleBO ruleBO2 = new RuleBO();
        ruleBO2.setCode( "Code2" );
        ruleBO2.setCategory( "layering" );
        ruleBO2.setSeverity( "error" );
        ruleBO2.setRuleSet( ruleSetBO );
        rules.put( "Code2", ruleBO2 );
        ruleSetBO.setRules( rules );
        ruleSetDao.save( getSession(), ruleSetBO );
        // Création de mesures
        RuleCheckingTransgressionBO transgression = new RuleCheckingTransgressionBO();
        transgression.setAudit( auditBO );
        transgression.setComponent( projectBO );
        transgression.setRuleSet( ruleSetBO );
        MeasureDAOImpl.getInstance().create( getSession(), transgression );
        getSession().commitTransactionWithoutClose();
    }

    /**
     * Teste la récupération des corrections de type rulechecking
     * 
     * @throws JrafDaoException si erreur
     */
    public void testFindNbErrorAndWarning()
        throws JrafDaoException
    {
        RuleCheckingTransgressionDAOImpl dao = RuleCheckingTransgressionDAOImpl.getInstance();
        int nbErrors = dao.findNbErrorAndWarning( getSession(), new Long( auditBO.getId() ) );
        assertEquals( 2, nbErrors );
    }

}
