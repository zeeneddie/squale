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
package org.squale.squalecommon.daolayer.stats;

import java.util.Collection;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.enterpriselayer.businessobject.config.ServeurBO;
import org.squale.squalecommon.enterpriselayer.businessobject.stats.SiteAndProfilStatsDICTBO;

/**
 */
public class SiteAndProfilStatsDICTDAOImplTest
    extends SqualeTestCase
{

    /** le bo */
    private SiteAndProfilStatsDICTBO siteAndProfil;

    /** le nom du profil */
    private final static String PROFIL = "JAVA";

    /** nombre de lignes de codes */
    private final static int LINES = 1200;

    /** nombre de projets */
    private static int NB_PROJECTS = 2;

    /** Le site */
    private ServeurBO mServer;

    /**
     * Méthode mettant en place l'environnement de test
     * 
     * @throws Exception en cas d'échec
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        mServer = getComponentFactory().createServer( getSession() );
        siteAndProfil = new SiteAndProfilStatsDICTBO( mServer.getServeurId(), PROFIL, LINES, NB_PROJECTS );
    }

    /** teste la récupération par site et profil */
    public void testFindBySiteAndProfil()
    {
        SiteAndProfilStatsDICTDAOImpl dao = SiteAndProfilStatsDICTDAOImpl.getInstance();
        try
        {
            // on enregistre le dao qu'on vient de créer
            getSession().beginTransaction();
            dao.create( getSession(), siteAndProfil );
            getSession().commitTransactionWithoutClose();
            // on vérifie qu'on peut le récupérer par la méthode findAll
            getSession().beginTransaction();
            Collection result = dao.findAll( getSession() );
            getSession().commitTransactionWithoutClose();
            assertTrue( result.size() == 1 );
            // on vérifie qu'on peut le récupérer par la méthode findBySite
            getSession().beginTransaction();
            result = dao.findBySiteAndProfil( getSession(), mServer.getServeurId(), PROFIL );
            getSession().commitTransactionWithoutClose();
            assertTrue( result.size() == 1 );
            siteAndProfil = (SiteAndProfilStatsDICTBO) result.iterator().next();
            // Teste la bonne récupération des valeurs
            assertEquals( mServer.getServeurId(), siteAndProfil.getServeurBO().getServeurId() );
            assertEquals( siteAndProfil.getProfil(), PROFIL );
            assertEquals( siteAndProfil.getNbOfCodesLines(), LINES );
            assertEquals( siteAndProfil.getNbProjects(), NB_PROJECTS );
            // on vérifie qu'on ne récupère que celui enregistré
            getSession().beginTransaction();
            result = dao.findBySiteAndProfil( getSession(), mServer.getServeurId(), "cpp" );
            getSession().commitTransactionWithoutClose();
            assertTrue( result.size() == 0 );
            // on vérifie qu'on ne récupère que celui enregistré
            getSession().beginTransaction();
            ServeurBO qvg = getComponentFactory().createServer( getSession(), "qvg" );
            result = dao.findBySiteAndProfil( getSession(), qvg.getServeurId(), "java" );
            getSession().commitTransactionWithoutClose();
            assertTrue( result.size() == 0 );
        }
        catch ( JrafDaoException e )
        {
            fail( "unexpected Exception" );
            e.printStackTrace();
        }
    }

}
