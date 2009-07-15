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
package org.squale.squalecommon.enterpriselayer.applicationcomponent.display;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import org.squale.squalecommon.daolayer.component.AuditDAOImpl;
import org.squale.squalecommon.daolayer.component.ProjectDAOImpl;
import org.squale.squalecommon.daolayer.rule.QualityGridDAOImpl;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.rule.QualityGridDTO;
import org.squale.squalecommon.datatransfertobject.transform.component.ComponentTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import org.squale.squalecommon.enterpriselayer.facade.rule.AuditComputing;
import org.squale.squalecommon.enterpriselayer.facade.rule.QualityGridImport;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ResultsApplicationComponentAccessTest
    extends SqualeTestCase
{

    /**
     * @throws JrafEnterpriseException si erreur
     * @throws JrafDaoException si erreur
     */
    public void testGetApplicationResults()
        throws JrafEnterpriseException, JrafDaoException
    {
        getSession().beginTransaction();
        ResultsApplicationComponentAccess resultAccess = new ResultsApplicationComponentAccess();
        // Chargement de la grille
        InputStream stream = getClass().getClassLoader().getResourceAsStream( "data/grid/grid_compute.xml" );
        StringBuffer errors = new StringBuffer();
        Collection grids;
        grids = QualityGridImport.createGrid( stream, errors );
        QualityGridBO grid =
            (QualityGridBO) QualityGridDAOImpl.getInstance().load(
                                                                   getSession(),
                                                                   new Long(
                                                                             ( (QualityGridDTO) grids.iterator().next() ).getId() ) );
        // Création de l'application
        ApplicationBO application = getComponentFactory().createApplication( getSession() );
        // Création du projet
        ProjectBO project = getComponentFactory().createProject( getSession(), application, grid );
        // création du profil du projet
        ProjectProfileBO profileBO = getComponentFactory().createProjectProfile( getSession() );
        project.setProfile( profileBO );
        ProjectDAOImpl.getInstance().save( getSession(), project );
        // Création de l'audit
        AuditBO audit = new AuditBO();
        audit.setStatus( AuditBO.TERMINATED );
        audit.setName( "audit1" );
        AuditDAOImpl.getInstance().create( getSession(), audit );
        application.addAudit( audit );
        AbstractComponentDAOImpl.getInstance().save( getSession(), application );
        project.addAudit( audit );
        AbstractComponentDAOImpl.getInstance().save( getSession(), project );
        // Création du package
        PackageBO pkg = getComponentFactory().createPackage( getSession(), project );
        pkg.addAudit( audit );
        AbstractComponentDAOImpl.getInstance().save( getSession(), pkg );
        // Création de la classe
        ClassBO cls = getComponentFactory().createClass( getSession(), pkg );
        cls.addAudit( audit );
        AbstractComponentDAOImpl.getInstance().save( getSession(), cls );
        // Création de la méthode
        MethodBO method = getComponentFactory().createMethod( getSession(), cls );
        method.addAudit( audit );
        AbstractComponentDAOImpl.getInstance().save( getSession(), cls );
        getComponentFactory().createMeasures( getSession(), audit, project, cls, method );
        // Calcul de l'audit
        AuditComputing.computeAuditResult( getSession(), project, audit );
        getSession().commitTransactionWithoutClose();
        ComponentDTO appliDTO = ComponentTransform.bo2Dto( application );
        List components = new ArrayList();
        components.add( appliDTO );
        List appliResults = resultAccess.getApplicationResults( components, null );
        // On doit avoir les résultats pour sloc et comments de rsm
        assertEquals( 1, appliResults.size() );
    }

}
