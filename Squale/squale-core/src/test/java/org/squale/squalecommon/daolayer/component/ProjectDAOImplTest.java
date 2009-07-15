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

import java.util.Collection;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.transform.component.ComponentTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import org.squale.squalecommon.enterpriselayer.businessobject.profile.UserBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * @author M400843
 */
public class ProjectDAOImplTest
    extends SqualeTestCase
{

    /**
     * Test pour void remove(ISession, ProjectBO)
     * 
     * @throws JrafDaoException en cas de problème avec la base
     */
    final public void testRemoveISessionProjectBO()
        throws JrafDaoException
    {
        ISession session = getSession();
        ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();

        session.beginTransaction();
        ApplicationBO appli = getComponentFactory().createApplication( session );
        ProjectBO project = getComponentFactory().createProject( session, appli, null );

        session.commitTransactionWithoutClose();

        assertNotNull( project );

        session.beginTransaction();
        projectDAO.remove( session, project );
        session.commitTransactionWithoutClose();

        session.beginTransaction();
        project = (ProjectBO) projectDAO.load( session, new Long( project.getId() ) );
        session.commitTransactionWithoutClose();

        assertNull( project );
        /*
         * Pour bien tester il faudrait vérifier que les composant fils ainsi que les résultats liés n'éxistent plus..
         */
    }

    /**
     * test la récupération de tous les projets d'un audit
     * 
     * @throws JrafDaoException en cas d'échec
     */
    public void testFindAllProjects()
        throws JrafDaoException
    {
        ApplicationDAOImpl applicationDAO = ApplicationDAOImpl.getInstance();
        ISession session = getSession();
        // on utilise une transaction pour créer l'application
        session.beginTransaction();
        ApplicationBO application = getComponentFactory().createApplication( session );

        // on crée 1 projet fils
        ProjectBO projet1 = getComponentFactory().createProject( getSession(), application, null );
        session.commitTransactionWithoutClose();

        // on vérifie qu'on le récupère bien
        Collection col = ProjectDAOImpl.getInstance().findAllProjects( getSession(), new Long( application.getId() ) );
        assertEquals( col.size(), 1 );
    }

    /**
     * Test la récupération des projets en fonction de leur nom, du nom de leur application et de la liste des
     * applications visibles par l'utilisateur
     * 
     * @throws JrafDaoException en cas d'échec
     */
    public void testfindProjects()
        throws JrafDaoException
    {
        ProjectDAOImpl dao = ProjectDAOImpl.getInstance();
        UserBO user = getComponentFactory().createUser( getSession() );
        ApplicationBO application = getComponentFactory().createApplication( getSession() );
        QualityGridBO grid = getComponentFactory().createGrid( getSession() );
        ProjectBO project = getComponentFactory().createProject( getSession(), application, grid );
        ComponentDTO appliDTO = ComponentTransform.bo2Dto( application );
        long[] userAppli = new long[] { appliDTO.getID() };
        Collection projects = dao.findProjects( getSession(), userAppli, "", "proj", null );
        assertEquals( 1, projects.size() );
        projects = dao.findProjects( getSession(), userAppli, "test", "proj", null );
        assertEquals( 0, projects.size() );
        projects = dao.findProjects( getSession(), userAppli, "", "test", null );
        assertEquals( 0, projects.size() );

    }

    /**
     * Teste la récupération des projets en fonction de leur statut et du site de leur application
     * 
     * @throws JrafDaoException si erreur
     */
    public void testFindWhereStatusAndSite()
        throws JrafDaoException
    {
        ProjectDAOImpl dao = ProjectDAOImpl.getInstance();
        ApplicationBO application = getComponentFactory().createApplicationWithSite( getSession(), "qvi" );
        QualityGridBO grid = getComponentFactory().createGrid( getSession() );
        ProjectBO project = getComponentFactory().createProject( getSession(), application, grid );
        ApplicationBO applicationWithoutSite = getComponentFactory().createApplication( getSession() );
        ProjectBO projectDeleted = getComponentFactory().createProject( getSession(), applicationWithoutSite, grid );
        projectDeleted.setStatus( ProjectBO.DELETED );
        dao.save( getSession(), projectDeleted );
        Collection projects =
            dao.findWhereStatusAndSite( getSession(), ProjectBO.DELETED,
                                        applicationWithoutSite.getServeurBO().getServeurId() );
        assertEquals( 1, projects.size() );
    }

    /**
     * Teste la récupération des projets en fonction de leur statut et du site de leur application
     * 
     * @throws JrafDaoException si erreur
     */
    public void testCountBySiteAndProfil()
        throws JrafDaoException
    {
        getSession().beginTransaction();
        ProjectDAOImpl dao = ProjectDAOImpl.getInstance();
        ApplicationBO application = getComponentFactory().createApplicationWithSite( getSession(), "qvi" );
        QualityGridBO grid = getComponentFactory().createGrid( getSession() );
        ProjectProfileBO profil = getComponentFactory().createProjectProfile( getSession() );
        profil.setName( "java" );
        ProjectBO project = getComponentFactory().createProject( getSession(), application, grid );
        project.setProfile( profil );
        int nbProjects = dao.countBySiteAndProfil( getSession(), application.getServeurBO().getServeurId(), "java" );
        assertEquals( 1, nbProjects );
        getSession().commitTransactionWithoutClose();
    }

}
