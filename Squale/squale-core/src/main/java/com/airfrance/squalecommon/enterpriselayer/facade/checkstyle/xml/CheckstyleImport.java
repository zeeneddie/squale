package com.airfrance.squalecommon.enterpriselayer.facade.checkstyle.xml;

import java.io.InputStream;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleSetBO;
import com.airfrance.squalecommon.util.xml.XmlImport;

/**
 * Parser de configuration de Checkstyle
 */
public class CheckstyleImport
    extends XmlImport
{

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( CheckstyleImport.class );

    /** The public ID for version 1_1 of the configuration dtd */
    private static final String DTD_PUBLIC_ID_1_1 = "-//Puppy Crawl//DTD Check Configuration 1.1//EN";

    /** The resource for version 1_1 of the configuration dtd */
    private static final String DTD_RESOURCE_NAME_1_1 = "/com/puppycrawl/tools/checkstyle/configuration_1_1.dtd";

    /**
     * Constructeur
     */
    public CheckstyleImport()
    {
        super( LOGGER );
    }

    /**
     * Importation d'un fichier de configuration checkstyle
     * 
     * @param pStream flux du fichier de configuration checkstyle
     * @param pErrors erreurs de traitement ou vide si aucune erreur n'est rencontrée
     * @return collection de Rules checkstyle importéés sous la forme de CheckstyleRuleSetBO
     */
    public CheckstyleRuleSetBO importCheckstyle( InputStream pStream, StringBuffer pErrors )
    {

        CheckstyleRuleSetFactory ruleSetFactory = new CheckstyleRuleSetFactory();
        Digester configDigester = setupDigester( ruleSetFactory, pErrors );
        // lancement du parsing
        parse( configDigester, pStream, pErrors );

        return ruleSetFactory.getCheckstyleRuleSets();

    }

    /**
     * Configuration du digester Le digester est utilisé pour le chargement du fichier XML de règles
     * 
     * @param pRuleSetFactory factory
     * @param pErrors erreurs de traitement
     * @return digester
     */
    protected Digester setupDigester( CheckstyleRuleSetFactory pRuleSetFactory, StringBuffer pErrors )
    {
        Digester configDigester = preSetupDigester( DTD_PUBLIC_ID_1_1, DTD_RESOURCE_NAME_1_1, pErrors );
        // Traitement des RuleSet
        configDigester.addFactoryCreate( "module/metadata", pRuleSetFactory );

        // Initialisation des caractéristiques de la règle, à récupérer dans la seconde balise "metadata"
        CheckstyleRulePractice checkstyleRulePractice = new CheckstyleRulePractice();
        configDigester.addFactoryCreate( "module/module/metadata", checkstyleRulePractice );

        // Traitement des Modules (rules)
        CheckstyleModuleFactory checkstyleModuleFactory = new CheckstyleModuleFactory();

        configDigester.addFactoryCreate( "module/module/module", checkstyleModuleFactory );
        // Traitement des propriétés du Module
        configDigester.addCallMethod( "module/module/module/property", "addProperty", 2, new Class[] { String.class,
            String.class } );

        configDigester.addCallParam( "module/module/module/property", 0, "name" );
        configDigester.addCallParam( "module/module/module/property", 1, "value" );

        // Traitement des Rules
        CheckstyleRuleFactory checkstyleRuleFactory =
            new CheckstyleRuleFactory( pRuleSetFactory, checkstyleRulePractice );
        configDigester.addFactoryCreate( "module/module/module/metadata", checkstyleRuleFactory );

        configDigester.addSetTop( "module/module/module/metadata", "addModule" );

        return configDigester;
    }

}
