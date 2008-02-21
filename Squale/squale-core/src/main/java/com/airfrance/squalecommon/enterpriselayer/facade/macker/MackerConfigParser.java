package com.airfrance.squalecommon.enterpriselayer.facade.macker;

import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.ProjectRuleSetBO;
import com.airfrance.squalecommon.util.xml.XmlImport;

/**
 * Parser de configuration Macker
 */
public class MackerConfigParser extends XmlImport {

    /** Log */
    private static Log LOG = LogFactory.getLog(MackerConfigParser.class);

    /**
     * Constructeur par défaut
     */
    public MackerConfigParser() {
        super(LOG);
    }

    /**
     * Parsing du fichier de configuration Macker
     * @param pStream flux
     * @param pErrors erreurs rencontrées
     * @return données lues
     */
    public ProjectRuleSetBO parseFile(InputStream pStream, StringBuffer pErrors) {
        // Résultat
        ProjectRuleSetBO result = new ProjectRuleSetBO();

        Digester configDigester = preSetupDigester(null, null, pErrors);
        configDigester.push(result);
        // On récupère le nom du ruleSet
        configDigester.addSetProperties("macker/ruleset");
        // On crée une règle
        RuleFactory rule = new RuleFactory();
        configDigester.addFactoryCreate("macker/ruleset/access-rule", rule);
        configDigester.addSetProperties("macker/ruleset/access-rule");
        configDigester.addBeanPropertySetter("macker/ruleset/access-rule/message", "code");
        configDigester.addCallMethod("macker/ruleset/access-rule", "setRuleSet", 1, new Class[] { ProjectRuleSetBO.class });
        configDigester.addCallParam("macker/ruleset/access-rule", 0, 1);
        // On ajoute la règle au ruleSet
        configDigester.addSetNext("macker/ruleset/access-rule", "addRule");
        parse(configDigester, pStream, pErrors);
        return result;
    }

}
