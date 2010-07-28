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
package org.squale.squalecommon.enterpriselayer.facade.rule.xml;

import java.io.InputStream;
import java.util.Collection;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.enterpriselayer.businessobject.rule.ConditionFormulaBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.SimpleFormulaBO;
import org.squale.squalecommon.util.xml.XmlImport;

/**
 * Importation de grille qualité à partir d'un fichier XML
 */
public class GridImport
    extends XmlImport
{
    /** Log */
    private static Log LOG = LogFactory.getLog( GridImport.class );

    /** Nom publique de la DTD */
    static final String PUBLIC_DTD = "-//Squale//DTD Grid Configuration 1.3//EN";

    /** Localisation de la DTD */
    static final String DTD_LOCATION = "/org/squale/squalecommon/dtd/grid-1.3.dtd";

    /**
     * Constructeur
     */
    public GridImport()
    {
        super( LOG );
    }

    /**
     * Importation d'une grille
     * 
     * @param pStream flux de grille
     * @param pErrors erreurs de traitement ou vide si aucune erreur n'est rencontrée
     * @return collection de grilles importées sous la forme de QualityGridBO
     */
    public Collection importGrid( InputStream pStream, StringBuffer pErrors )
    {
        GridFactory gridFactory = new GridFactory();
        Digester configDigester = setupDigester( gridFactory, pErrors );
        parse( configDigester, pStream, pErrors );
        return gridFactory.getGrids().values();
    }

    /**
     * Configuration du digester Le digester est utilisé pour le chargement du fichier XML de règles
     * 
     * @param pGridFactory factory
     * @param pErrors erreurs de traitement
     * @return digester
     */
    protected Digester setupDigester( GridFactory pGridFactory, StringBuffer pErrors )
    {
        Digester configDigester = preSetupDigester( PUBLIC_DTD, DTD_LOCATION, pErrors );
        // Traitement des pratiques
        PracticeFactory practiceFactory = new PracticeFactory();
        configDigester.addFactoryCreate( "squale/practiceset/practice", practiceFactory );
        configDigester.addSetProperties( "squale/practiceset/practice" );
        configDigester.addBeanPropertySetter( "squale/practiceset/practice/weight/", "weightFunction" );
        
        // Processing of the time limitation
        configDigester.addCallMethod( "squale/practiceset/practice/timeLimitation", "setTimeLimitationFromXmlParse", 2, new Class[]{String.class, String.class} );
        configDigester.addCallParam( "squale/practiceset/practice/timeLimitation", 0 );
        configDigester.addCallParam( "squale/practiceset/practice/timeLimitation", 1, "unit" );
        
        // Traitement des formules
        // Formule avec condition
        configDigester.addObjectCreate( "squale/practiceset/practice/conditionformula", ConditionFormulaBO.class );
        configDigester.addBeanPropertySetter( "squale/practiceset/practice/conditionformula/level", "componentLevel" );
        configDigester.addBeanPropertySetter( "squale/practiceset/practice/conditionformula/trigger",
                                              "triggerCondition" );
        configDigester.addCallMethod( "squale/practiceset/practice/conditionformula/conditions/condition",
                                      "addMarkCondition", 1 );
        configDigester.addCallParam( "squale/practiceset/practice/conditionformula/conditions/condition", 0 );
        configDigester.addCallMethod( "squale/practiceset/practice/conditionformula/measures/measure",
                                      "addMeasureKind", 1 );
        configDigester.addCallParam( "squale/practiceset/practice/conditionformula/measures/measure", 0 );
        configDigester.addSetNext( "squale/practiceset/practice/conditionformula", "setFormula" );
        // Formule simple
        configDigester.addObjectCreate( "squale/practiceset/practice/simpleformula", SimpleFormulaBO.class );
        configDigester.addBeanPropertySetter( "squale/practiceset/practice/simpleformula/level", "componentLevel" );
        configDigester.addBeanPropertySetter( "squale/practiceset/practice/simpleformula/trigger", "triggerCondition" );
        configDigester.addBeanPropertySetter( "squale/practiceset/practice/simpleformula/formula", "formula" );
        configDigester.addCallMethod( "squale/practiceset/practice/simpleformula/measures/measure", "addMeasureKind", 1 );
        configDigester.addCallParam( "squale/practiceset/practice/simpleformula/measures/measure", 0 );
        configDigester.addSetNext( "squale/practiceset/practice/simpleformula", "setFormula" );

        // Traitement des critères
        CriteriumFactory criteriumFactory = new CriteriumFactory();
        PracticeRefFactory practiceRefFactory = new PracticeRefFactory( practiceFactory.getPractices() );
        configDigester.addFactoryCreate( "squale/criteriumset/criterium", criteriumFactory );
        configDigester.addSetProperties( "squale/criteriumset/criterium" );
        configDigester.addCallMethod( "squale/criteriumset/criterium/practice-ref", "addPractice", 2, new Class[] {
            PracticeRuleBO.class, Float.class } );

        configDigester.addFactoryCreate( "squale/criteriumset/criterium/practice-ref", practiceRefFactory );
        configDigester.addCallParam( "squale/criteriumset/criterium/practice-ref", 0, true );
        configDigester.addCallParam( "squale/criteriumset/criterium/practice-ref", 1, "weight" );

        // Traitement des facteurs
        FactorFactory factorFactory = new FactorFactory();
        CriteriumRefFactory criteriumRefFactory = new CriteriumRefFactory( criteriumFactory.getCriteria() );
        configDigester.addFactoryCreate( "squale/factorset/factor", factorFactory );
        configDigester.addSetProperties( "squale/factorset/factor" );
        configDigester.addCallMethod( "squale/factorset/factor/criterium-ref", "addCriterium", 2, new Class[] {
            CriteriumRuleBO.class, Float.class } );

        configDigester.addFactoryCreate( "squale/factorset/factor/criterium-ref", criteriumRefFactory );
        configDigester.addCallParam( "squale/factorset/factor/criterium-ref", 0, true );
        configDigester.addCallParam( "squale/factorset/factor/criterium-ref", 1, "weight" );

        // Traitement des grilles
        FactorRefFactory factorRefFactory = new FactorRefFactory( factorFactory.getFactors() );
        configDigester.addFactoryCreate( "squale/gridset/grid", pGridFactory );
        configDigester.addSetProperties( "squale/gridset/grid" );
        configDigester.addFactoryCreate( "squale/gridset/grid/factor-ref", factorRefFactory );
        configDigester.addSetNext( "squale/gridset/grid/factor-ref", "addFactor" );
        return configDigester;
    }

}
