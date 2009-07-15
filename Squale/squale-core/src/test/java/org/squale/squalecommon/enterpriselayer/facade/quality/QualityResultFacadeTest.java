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
 * Créé le 12 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.squalecommon.enterpriselayer.facade.quality;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.daolayer.result.MeasureDAOImpl;
import org.squale.squalecommon.daolayer.result.QualityResultDAOImpl;
import org.squale.squalecommon.daolayer.rule.QualityGridDAOImpl;
import org.squale.squalecommon.daolayer.rulechecking.CheckstyleRuleSetDAOImpl;
import org.squale.squalecommon.datatransfertobject.rule.QualityGridDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.FactorResultBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.misc.CommentsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.rulechecking.CheckstyleTransgressionBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleSetBO;
import org.squale.squalecommon.enterpriselayer.facade.rule.AuditComputing;
import org.squale.squalecommon.enterpriselayer.facade.rule.QualityGridImport;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class QualityResultFacadeTest
    extends SqualeTestCase
{

    /**
     * test getRuleChecking
     */

    public void testGetRuleChecking()
    {
        try
        {
            ISession session = getSession();
            CheckstyleRuleSetDAOImpl daoImpl = CheckstyleRuleSetDAOImpl.getInstance();
            // instanciation de la première VersionBO
            CheckstyleRuleSetBO v = new CheckstyleRuleSetBO();
            v.setValue( "n'import quoi".getBytes() );

            // instanciation de 3 règles
            CheckstyleRuleBO rule1 = new CheckstyleRuleBO();
            rule1.setCategory( "programmingstandard" );
            rule1.setCode( "COD01" );
            rule1.setSeverity( "warning" );
            CheckstyleRuleBO rule2 = new CheckstyleRuleBO();
            rule2.setCategory( "namingstandard" );
            rule2.setCode( "NOM03" );
            rule2.setSeverity( "error" );
            CheckstyleRuleBO rule3 = new CheckstyleRuleBO();
            rule3.setCategory( "formatingstandard" );
            rule3.setCode( "FOR04" );
            rule3.setSeverity( "info" );
            //
            rule1.setRuleSet( v );
            rule2.setRuleSet( v );
            rule3.setRuleSet( v );
            Map rules = new HashMap();
            rules.put( rule1.getCode(), rule1 );
            rules.put( rule2.getCode(), rule2 );
            rules.put( rule3.getCode(), rule3 );
            v.setRules( rules );

            v = daoImpl.createCheckstyleRuleSet( session, v );
            // Chargement de la grille
            InputStream stream = getClass().getClassLoader().getResourceAsStream( "data/grid/grid_rulechecking.xml" );
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
            // creation de la metrique commentaire utilise dans le calcul des partiques rulechecking
            CommentsBO commentMetrics = new CommentsBO();
            commentMetrics.setCloc( new Integer( 20 ) );
            commentMetrics.setSloc( new Integer( 1000 ) );
            commentMetrics.setAudit( audit );
            commentMetrics.setComponent( project );
            MeasureDAOImpl.getInstance().create( session, commentMetrics );

            CheckstyleTransgressionBO checkstyleMetrics = new CheckstyleTransgressionBO();
            checkstyleMetrics.setAudit( audit );
            checkstyleMetrics.setComponent( project );
            checkstyleMetrics.setRuleSet( v );
            IntegerMetricBO metric = new IntegerMetricBO();
            metric.setName( "COD01" );
            metric.setValue( 5 );
            metric.setMeasure( checkstyleMetrics );

            IntegerMetricBO metric2 = new IntegerMetricBO();
            metric2.setName( "NOM03" );
            metric2.setValue( 2 );
            metric2.setMeasure( checkstyleMetrics );
            IntegerMetricBO metric3 = new IntegerMetricBO();
            metric3.setName( "FOR04" );
            metric3.setValue( 3 );
            metric3.setMeasure( checkstyleMetrics );
            checkstyleMetrics.putMetric( metric );
            checkstyleMetrics.putMetric( metric2 );
            checkstyleMetrics.putMetric( metric3 );
            MeasureDAOImpl.getInstance().create( session, checkstyleMetrics );
            // Calcul de l'audit
            AuditComputing.computeAuditResult( session, project, audit );

            Collection factorResults =
                QualityResultDAOImpl.getInstance().findWhere( session, new Long( project.getId() ),
                                                              new Long( audit.getId() ) );
            assertEquals( 2, factorResults.size() );

            Float praticeProgramingMark = new Float( 3 * Math.pow( 2. / 3., -( 100 * 0 + 20 * 5 + 5 * 0 ) / 1000 ) );
            Float praticeNamingMark = new Float( 3 * Math.pow( 2. / 3., -( 100 * 2 + 20 * 0 + 5 * 0 ) / 1000 ) );
            Float praticeFormatingMark = new Float( 3 * Math.pow( 2. / 3., -( 100 * 0 + 20 * 0 + 5 * 3 ) / 1000 ) );

            float criteriumHomogeneity =
                ( praticeProgramingMark.floatValue() + praticeNamingMark.floatValue() + praticeFormatingMark.floatValue() ) / 3f;
            float facteurMaintenanbility = criteriumHomogeneity;
            assertEquals( facteurMaintenanbility, ( (FactorResultBO) factorResults.iterator().next() ).getMeanMark(),
                          0.1 );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }
    }

}
