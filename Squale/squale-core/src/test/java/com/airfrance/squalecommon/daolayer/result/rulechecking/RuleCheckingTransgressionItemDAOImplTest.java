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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.daolayer.rulechecking.ProjectRuleSetDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionItemBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.ProjectRuleSetBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;

/**
 * Test pour RuleCheckingTransgressionItemDAOImpl
 */
public class RuleCheckingTransgressionItemDAOImplTest
    extends SqualeTestCase
{

    /**
     * Test la récupération d'items en fonction d'une mesure et d'une règle
     * 
     * @throws JrafDaoException si erreur
     */
    public void testFindWhereMeasureAndRule()
        throws JrafDaoException
    {
        ApplicationBO appli = getComponentFactory().createApplication( getSession() );
        QualityGridBO grid = getComponentFactory().createGrid( getSession() );
        ProjectBO projectBO = getComponentFactory().createProject( getSession(), appli, grid );
        AuditBO auditBO = getComponentFactory().createAudit( getSession(), projectBO );
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
        RuleCheckingTransgressionItemDAOImpl dao = RuleCheckingTransgressionItemDAOImpl.getInstance();
        // Création des items
        ArrayList items = new ArrayList();
        RuleCheckingTransgressionItemBO item1 = new RuleCheckingTransgressionItemBO();
        item1.setComponent( packageBO );
        item1.setMessage( "A retourner" );
        item1.setRule( ruleBO1 );
        items.add( item1 );
        RuleCheckingTransgressionItemBO item2 = new RuleCheckingTransgressionItemBO();
        item2.setComponent( packageBO );
        item2.setMessage( "A ne pas retourner" );
        item2.setRule( ruleBO2 );
        items.add( item2 );
        // Création de mesures
        RuleCheckingTransgressionBO transgression = new RuleCheckingTransgressionBO();
        transgression.setAudit( auditBO );
        transgression.setComponent( projectBO );
        transgression.setDetails( items );
        MeasureDAOImpl.getInstance().create( getSession(), transgression );
        Collection results =
            dao.findWhereMeasureAndRule( getSession(), new Long( transgression.getId() ), new Long( ruleBO1.getId() ) );
        assertEquals( 1, results.size() );
    }

}
