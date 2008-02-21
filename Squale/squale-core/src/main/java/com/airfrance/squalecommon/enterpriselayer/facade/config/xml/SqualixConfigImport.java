package com.airfrance.squalecommon.enterpriselayer.facade.config.xml;

import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.AuditFrequencyBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.BubbleConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.VolumetryConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.SqualixConfigurationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.StopTimeBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalecommon.util.xml.XmlImport;

/**
 * Importation de la configuration Squalix à partir de son fichier de configuration
 */
public class SqualixConfigImport extends XmlImport {

    /** Log */
    private static Log LOG = LogFactory.getLog(SqualixConfigImport.class);

    /** Nom publique de la DTD */
    final static String PUBLIC_DTD = "-//Squale//DTD Squalix Configuration 1.0//EN";

    /** Localisation de la DTD */
    final static String DTD_LOCATION = "/com/airfrance/squalecommon/dtd/squalix-config-1.0.dtd";

    /** Nom du fichier xml contenant les règles */
    final static String RULES_FILE_NAME = "/com/airfrance/squalecommon/enterpriselayer/facade/config/xml/configRules.xml";

    /**
     * Constructeur
     *
     */
    public SqualixConfigImport() {
        super(LOG);
    }

    /**
     * Importation de la configuration squalix
     * @param pStream flux de configuration
     * @param pErrors erreurs de traitement ou vide si aucune erreur n'est rencontrée
     * @return la configuration importée sous la forme de SqualixConfigurationBO
     */
    public SqualixConfigurationBO importConfig(InputStream pStream, StringBuffer pErrors) {
        Digester configDigester = setupDigester(pErrors);
        SqualixConfigurationBO config = new SqualixConfigurationBO();
        configDigester.push(config);
        parse(configDigester, pStream, pErrors);
        return config;
    }

    /**
      * Configuration du digester
      * Le digester est utilisé pour le chargement du fichier XML de règles
      * @param pErrors erreurs de traitement
      * @return digester
      */
    private Digester setupDigester(StringBuffer pErrors) {
        Digester configDigester = preSetupDigester(PUBLIC_DTD, DTD_LOCATION, pErrors);

        // Traitement des paramètres généraux
        configDigester.addObjectCreate("configuration/stoptimes/stoptime", StopTimeBO.class);
        configDigester.addSetProperties("configuration/stoptimes/stoptime");
        configDigester.addSetNext("configuration/stoptimes/stoptime", "addStopTime");

        // Traitement de la fréquence max des audits en fonction des accès
        configDigester.addObjectCreate("configuration/frequencies/frequency", AuditFrequencyBO.class);
        configDigester.addSetProperties("configuration/frequencies/frequency");
        configDigester.addSetNext("configuration/frequencies/frequency", "addFrequency");

        // Traitement des tâches
        TaskFactory taskFactory = new TaskFactory();
        configDigester.addFactoryCreate("configuration/tasks/task", taskFactory);
        configDigester.addSetProperties("configuration/tasks/task", "class", "className");

        // Traitement de la récupération des sources
        SourceManagementFactory sourceManagementFactory = new SourceManagementFactory();
        TaskRefFactory taskRefFactory = new TaskRefFactory(taskFactory.getTasks());
        configDigester.addFactoryCreate("configuration/sourcemanagements/sourcemanagement", sourceManagementFactory);
        configDigester.addSetProperties("configuration/sourcemanagements/sourcemanagement");
        configDigester.addFactoryCreate("configuration/sourcemanagements/sourcemanagement/analysis/task-ref", taskRefFactory);
        configDigester.addSetNext("configuration/sourcemanagements/sourcemanagement/analysis/task-ref", "addAnalysisTask");
        registerParameterTag(configDigester, "configuration/sourcemanagements/sourcemanagement/analysis/task-ref");
        configDigester.addFactoryCreate("configuration/sourcemanagements/sourcemanagement/termination/task-ref", taskRefFactory);
        registerParameterTag(configDigester, "configuration/sourcemanagements/sourcemanagement/termination/task-ref");
        configDigester.addSetNext("configuration/sourcemanagements/sourcemanagement/termination/task-ref", "addTerminationTask");
        configDigester.addSetNext("configuration/sourcemanagements/sourcemanagement", "addSourceManagement");

        // Traitement des profils
        ProfileFactory profileFactory = new ProfileFactory();
        configDigester.addFactoryCreate("configuration/profiles/profile", profileFactory);
        configDigester.addSetProperties("configuration/profiles/profile");
        // Les grilles
        configDigester.addObjectCreate("configuration/profiles/profile/integrityConstraints/grids/grid", QualityGridBO.class);
        configDigester.addSetProperties("configuration/profiles/profile/integrityConstraints/grids/grid");
        configDigester.addSetNext("configuration/profiles/profile/integrityConstraints/grids/grid", "addGrid");
        // Les Configurations
        // Le scatterplot
        configDigester.addObjectCreate("configuration/profiles/profile/integrityConstraints/displayConfigurations/bubble", BubbleConfBO.class);
        String[] nameInXML = { "xtre", "ytre", "xPos", "yPos" };
        String[] nameInBO = { "XTre", "YTre", "horizontalAxisPos", "verticalAxisPos" };
        configDigester.addSetProperties("configuration/profiles/profile/integrityConstraints/displayConfigurations/bubble", nameInXML, nameInBO);
        configDigester.addSetNext("configuration/profiles/profile/integrityConstraints/displayConfigurations/bubble", "addProfileDisplayConf");
        // La volumétrie
        configDigester.addObjectCreate("configuration/profiles/profile/integrityConstraints/displayConfigurations/volumetries/volumetry", VolumetryConfBO.class);
        configDigester.addSetProperties("configuration/profiles/profile/integrityConstraints/displayConfigurations/volumetries/volumetry", "component", "componentType");
        registerTreTag(configDigester, "configuration/profiles/profile/integrityConstraints/displayConfigurations/volumetries/volumetry");
        configDigester.addSetNext("configuration/profiles/profile/integrityConstraints/displayConfigurations/volumetries/volumetry", "addProfileDisplayConf");
        // Les tâches
        configDigester.addFactoryCreate("configuration/profiles/profile/analysis/task-ref", taskRefFactory);
        registerParameterTag(configDigester, "configuration/profiles/profile/analysis/task-ref");
        configDigester.addSetNext("configuration/profiles/profile/analysis/task-ref", "addAnalysisTask");
        configDigester.addFactoryCreate("configuration/profiles/profile/termination/task-ref", taskRefFactory);
        registerParameterTag(configDigester, "configuration/profiles/profile/termination/task-ref");
        configDigester.addSetNext("configuration/profiles/profile/termination/task-ref", "addTerminationTask");

        configDigester.addSetNext("configuration/profiles/profile", "addProfile");

        return configDigester;
    }

    /**
     * Enregistrement d'un paramètre de tâche
     * @param pDigester digester
     * @param pPath chemin
     */
    private void registerParameterTag(Digester pDigester, String pPath) {
        String path = new StringBuffer(pPath).append("/parameter").toString();
        pDigester.addCallMethod(path, "addParameter", 2);
        pDigester.addCallParam(path, 0, "name");
        pDigester.addCallParam(path, 1, "value");
    }

    /**
     * Enregistrement d'un tre de configuration de volumétrie
     * @param pDigester digester
     * @param pPath chemin
     */
    private void registerTreTag(Digester pDigester, String pPath) {
        String path = new StringBuffer(pPath).append("/tre").toString();
        pDigester.addCallMethod(path, "addTre", 1);
        pDigester.addCallParam(path, 0, "name");
    }
}
