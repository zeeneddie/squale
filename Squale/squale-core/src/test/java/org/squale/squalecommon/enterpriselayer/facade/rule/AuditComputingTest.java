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
package org.squale.squalecommon.enterpriselayer.facade.rule;

import java.io.InputStream;
import java.util.Collection;

import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import org.squale.squalecommon.daolayer.result.MarkDAOImpl;
import org.squale.squalecommon.daolayer.result.MeasureDAOImpl;
import org.squale.squalecommon.daolayer.result.QualityResultDAOImpl;
import org.squale.squalecommon.daolayer.rule.QualityGridDAOImpl;
import org.squale.squalecommon.datatransfertobject.rule.QualityGridDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.FactorResultBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.MarkBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAClassMetricsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * Test de calcul d'audit
 */
public class AuditComputingTest
    extends SqualeTestCase
{
    /**
     * Test de calcul d'audit Un audit est créé artificiellement avec une grille qualité de base, les métriques sont
     * elles aussi créées en dur dans le test. Une phase d'audit est ensuite réalisée avec une vérification des
     * résultats obtenus.
     */
    public void testComputeAuditResult()
    {
        ISession session;
        try
        {
            session = getSession();
            // Chargement de la grille
            InputStream stream = getClass().getClassLoader().getResourceAsStream( "data/grid/grid_compute.xml" );
            StringBuffer errors = new StringBuffer();
            Collection grids;
            grids = QualityGridImport.createGrid( stream, errors );
            QualityGridBO grid =
                (QualityGridBO) QualityGridDAOImpl.getInstance().load(
                                                                       session,
                                                                       new Long(
                                                                                 ( (QualityGridDTO) grids.iterator().next() ).getId() ) );
            // Création de l'application
            ApplicationBO application = getComponentFactory().createApplication( session );
            // Création du projet
            ProjectBO project = getComponentFactory().createProject( session, application, grid );
            // Création de l'audit
            AuditBO audit = getComponentFactory().createAudit( session, project );
            // Création du package
            PackageBO pkg = getComponentFactory().createPackage( session, project );
            pkg.addAudit( audit );
            AbstractComponentDAOImpl.getInstance().save( session, pkg );
            // Création de la classe
            ClassBO cls = getComponentFactory().createClass( session, pkg );
            cls.addAudit( audit );
            AbstractComponentDAOImpl.getInstance().save( session, cls );
            // Création de la classe2
            ClassBO cls2 = getComponentFactory().createClass( session, pkg );
            cls2.addAudit( audit );
            AbstractComponentDAOImpl.getInstance().save( session, cls2 );
            // Création de la méthode
            MethodBO method = getComponentFactory().createMethod( session, cls );
            method.addAudit( audit );
            AbstractComponentDAOImpl.getInstance().save( session, cls );
            /*
             * McCabe : - cls : Maxvg = 2; Sumvg = 2; wmc = 8; - method : nsloc = 2; Checkstyle : - project : totalError =
             * 20;
             */

            getComponentFactory().createMeasures( session, audit, project, cls, method );
            // Nouvelle mesure pour la classe 2
            McCabeQAClassMetricsBO classMetrics = new McCabeQAClassMetricsBO();
            classMetrics.setAudit( audit );
            classMetrics.setComponent( cls2 );
            // On veut une note à 2 pour le composant
            classMetrics.setMaxvg( new Integer( 350 ) );
            classMetrics.setSumvg( new Integer( 1000 ) );
            classMetrics.setWmc( new Integer( 8 ) );
            MeasureDAOImpl.getInstance().create( getSession(), classMetrics );

            // Calcul de l'audit
            AuditComputing.computeAuditResult( session, project, audit );
            // Vérification des calculs
            /*
             * D'après la grille "grid_compute.xml" on doit avoir : practice 1 class1 = 0 car 2(mccabe.maxvg) >=
             * 0.5*2(mccabe.sumvg) et 8(mccabe.wmc) >= 8 (classe) practice 1 class2 = 2 car 350(mccabe.maxvg) >=
             * 0.3*1000(mccabe.sumvg) et 8(mccabe.wmc) >= 8 (classe) --> practice 1 = 1.0 car f(x)=x est la fonction de
             * pondération donc f^-1(1/2*(0+2)=1 practice 2 = 3*(2.0/3.0)**(100.0*20/1000) = 1.33334 (projet) practice 3 =
             * 3 car aucune condition n'est remplie (mccabe.nsloc = 2 < 40) (methode)
             */
            // Note sur la classe
            final float expectedClassMark = 0f;
            Collection classMarks =
                MarkDAOImpl.getInstance().findWhere( session, new Long( cls.getId() ), new Long( audit.getId() ) );
            assertEquals( 1, classMarks.size() );
            assertEquals( new Float( expectedClassMark ),
                          new Float( ( (MarkBO) classMarks.iterator().next() ).getValue() ) );
            // Note sur la classe2
            final float expectedClassMark2 = 2f;
            Collection classMarks2 =
                MarkDAOImpl.getInstance().findWhere( session, new Long( cls2.getId() ), new Long( audit.getId() ) );
            assertEquals( 1, classMarks2.size() );
            assertEquals( new Float( expectedClassMark2 ),
                          new Float( ( (MarkBO) classMarks2.iterator().next() ).getValue() ) );
            // Résultat pour practice1
            float expectedPractice1Mark = 1f;
            // Note sur la méthode
            final float expectedMethodMark = 3f;
            Collection methodMarks =
                MarkDAOImpl.getInstance().findWhere( session, new Long( method.getId() ), new Long( audit.getId() ) );
            assertEquals( 1, methodMarks.size() );
            assertEquals( new Float( expectedMethodMark ),
                          new Float( ( (MarkBO) methodMarks.iterator().next() ).getValue() ) );
            // Note sur le projet
            final float expectedProjectMark =
                new Float( 3 * Math.pow( ( 2.0 / 3.0 ), 100.0 * 20 / 1000 ) ).floatValue();
            Collection factorResults =
                QualityResultDAOImpl.getInstance().findWhere( session, new Long( project.getId() ),
                                                              new Long( audit.getId() ) );
            assertEquals( 2, factorResults.size() );
            // La pondération sur les pratiques est identique
            Collection factorResults2 =
                QualityResultDAOImpl.getInstance().findWhere( session, new Long( project.getId() ),
                                                              new Long( audit.getId() ) );
            assertEquals( ( expectedPractice1Mark + expectedMethodMark + expectedProjectMark ) / 3f,
                          ( (FactorResultBO) factorResults2.iterator().next() ).getMeanMark(), 0.1 );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

}
