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
package org.squale.squalecommon.daolayer.component;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * @author M400843
 */
public class AuditDAOImplTest
    extends SqualeTestCase
{
    /** Logger */
    private static final Log LOGGER = LogFactory.getLog( AuditDAOImplTest.class );

    /** le projet */
    private ProjectBO p1;

    /** Le premier audit */
    private AuditBO a1;

    /** Le deuxième audit */
    private AuditBO a2;

    /** Le troisième audit */
    private AuditBO a3;

    /** Le quatrième audit avec un status en cours */
    private AuditBO a4;

    /** Le dao */
    private AuditDAOImpl dao = AuditDAOImpl.getInstance();

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        getSession().beginTransaction();
        // On va créer 2 audits de suivis et un de jalon
        // dont la date sera la plus ancienne
        GregorianCalendar cal = new GregorianCalendar();
        ApplicationBO ap1 = getComponentFactory().createApplicationWithSite( getSession(), "qvi" );
        ap1.setStatus( ApplicationBO.VALIDATED );
        QualityGridBO grid = getComponentFactory().createGrid( getSession() );
        p1 = getComponentFactory().createProject( getSession(), ap1, grid );
        a1 = getComponentFactory().createAudit( getSession(), p1 );

        a1.setType( AuditBO.MILESTONE );
        a1.setStatus( AuditBO.TERMINATED );

        // date réelle = aujourd'hui
        a1.setHistoricalDate( cal.getTime() );
        a2 = getComponentFactory().createAudit( getSession(), p1 );
        a2.setStatus( AuditBO.TERMINATED );
        a3 = getComponentFactory().createAudit( getSession(), p1 );
        a3.setStatus( AuditBO.TERMINATED );

        a4 = getComponentFactory().createAuditWithStatus( getSession(), p1, new Integer( AuditBO.RUNNING ) );
        final int ten_days = 10;
        // date a2 = dans 10 jours
        cal.add( GregorianCalendar.DATE, ten_days );
        a2.setDate( cal.getTime() );
        // date de réalisation de a1 = dans 20 jours
        cal.add( GregorianCalendar.DATE, ten_days );
        a1.setDate( cal.getTime() );
        // date a3 = dans 30 jours
        cal.add( GregorianCalendar.DATE, ten_days );
        a3.setDate( cal.getTime() );
        // date a4 = dans 40 jours
        cal.add( GregorianCalendar.DATE, ten_days );
        a4.setDate( cal.getTime() );
        LOGGER.warn( "Date de a1 = " + a1.getDate() + " date historique = " + a1.getHistoricalDate() );
        LOGGER.warn( "Date de a2 = " + a2.getDate() );
        LOGGER.warn( "Date de a3 = " + a3.getDate() );
        LOGGER.warn( "Date de a4 = " + a4.getDate() );
        dao.save( getSession(), a1 );
        dao.save( getSession(), a2 );
        dao.save( getSession(), a3 );
        dao.save( getSession(), a4 );
        getSession().commitTransactionWithoutClose();
    }

    /**
     * Teste la récupération des audits à supprimer
     * 
     * @throws JrafDaoException en cas de problémes
     */
    public void testFindDeleted()
        throws JrafDaoException
    {
        Collection coll = new ArrayList( 0 );
        getSession().beginTransaction();
        assertTrue( dao.findDeleted( getSession(), ( (ApplicationBO) p1.getParent() ).getServeurBO().getServeurId(),
                                     new ArrayList() ).size() == 0 );
        getSession().commitTransactionWithoutClose();
        a1.setStatus( AuditBO.DELETED );
        getSession().beginTransaction();
        dao.save( getSession(), a1 );
        getSession().commitTransactionWithoutClose();
        assertEquals( dao.findDeleted( getSession(), ( (ApplicationBO) p1.getParent() ).getServeurBO().getServeurId(),
                                       new ArrayList() ).size(), 1 );
        ArrayList applis = new ArrayList( 1 );
        applis.add( new Long( 1 ) );
        assertEquals( dao.findDeleted( getSession(), ( (ApplicationBO) p1.getParent() ).getServeurBO().getServeurId(),
                                       applis ).size(), 0 );
    }

    /**
     * Teste la méthode qui compte le nombre d'audits encore en cours sur un site donné
     * 
     * @throws JrafDaoException en cas d'échec
     */
    public void testCountWhereStatusAndSite()
        throws JrafDaoException
    {
        getSession().beginTransaction();
        int result =
            dao.countWhereStatusAndSite( getSession(),
                                         ( (ApplicationBO) p1.getParent() ).getServeurBO().getServeurId(),
                                         AuditBO.RUNNING );
        getSession().commitTransactionWithoutClose();
        assertEquals( result, 1 );
    }

    /**
     * Teste la récupération du dernier audit réussi pour toutes les applications
     * 
     * @throws JrafDaoException si erreur
     */
    public void testFindAllLastAudits()
        throws JrafDaoException
    {
        List audits = dao.findAllLastAudits( getSession(), ApplicationBO.class, AuditBO.ALL_TYPES, AuditBO.TERMINATED );
        assertEquals( 1, audits.size() );
    }

}
