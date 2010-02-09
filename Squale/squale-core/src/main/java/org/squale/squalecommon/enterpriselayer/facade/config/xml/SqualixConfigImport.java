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
package org.squale.squalecommon.enterpriselayer.facade.config.xml;

import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.enterpriselayer.businessobject.config.AuditFrequencyBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.web.BubbleConfBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.web.VolumetryConfBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.AdminParamsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.SqualixConfigurationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.StopTimeBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import org.squale.squalecommon.util.xml.XmlImport;

/**
 * Importation de la configuration Squalix à partir de son fichier de configuration
 */
public class SqualixConfigImport
    extends XmlImport
{

    /** Log */
    private static Log LOG = LogFactory.getLog( SqualixConfigImport.class );

    /** Nom publique de la DTD */
    static final String PUBLIC_DTD = "-//Squale//DTD Squalix Configuration 1.2//EN";

    /** Localisation de la DTD */
    static final String DTD_LOCATION = "/org/squale/squalecommon/dtd/squalix-config-1.2.dtd";

    /** Nom du fichier xml contenant les règles */
    static final String RULES_FILE_NAME =
        "/org/squale/squalecommon/enterpriselayer/facade/config/xml/configRules.xml";

    /**
     * Constructeur
     */
    public SqualixConfigImport()
    {
        super( LOG );
    }

    /**
     * Importation de la configuration squalix
     * 
     * @param pStream flux de configuration
     * @param pErrors erreurs de traitement ou vide si aucune erreur n'est rencontrée
     * @return la configuration importée sous la forme de SqualixConfigurationBO
     */
    public SqualixConfigurationBO importConfig( InputStream pStream, StringBuffer pErrors )
    {
        Digester configDigester = setupDigester( pErrors );
        SqualixConfigurationBO config = new SqualixConfigurationBO();
        configDigester.push( config );
        parse( configDigester, pStream, pErrors );
        return config;
    }

    /**
     * Configuration du digester Le digester est utilisé pour le chargement du fichier XML de règles
     * 
     * @param pErrors erreurs de traitement
     * @return digester
     */
    private Digester setupDigester( StringBuffer pErrors )
    {
        Digester configDigester = preSetupDigester( PUBLIC_DTD, DTD_LOCATION, pErrors );

        // Traitement des paramètres généraux
        configDigester.addObjectCreate( "configuration/stoptimes/stoptime", StopTimeBO.class );
        configDigester.addSetProperties( "configuration/stoptimes/stoptime" );
        configDigester.addSetNext( "configuration/stoptimes/stoptime", "addStopTime" );

        // Traitement de la fréquence max des audits en fonction des accès
        configDigester.addObjectCreate( "configuration/frequencies/frequency", AuditFrequencyBO.class );
        configDigester.addSetProperties( "configuration/frequencies/frequency" );
        configDigester.addSetNext( "configuration/frequencies/frequency", "addFrequency" );

        // Traitement des tâches
        TaskFactory taskFactory = new TaskFactory();
        configDigester.addFactoryCreate( "configuration/tasks/task", taskFactory );
        configDigester.addSetProperties( "configuration/tasks/task", "class", "className" );

        // Traitement de la récupération des sources
        SourceManagementFactory sourceManagementFactory = new SourceManagementFactory();
        TaskRefFactory taskRefFactory = new TaskRefFactory( taskFactory.getTasks() );
        configDigester.addFactoryCreate( "configuration/sourcemanagements/sourcemanagement", sourceManagementFactory );
        configDigester.addSetProperties( "configuration/sourcemanagements/sourcemanagement" );
        configDigester.addFactoryCreate( "configuration/sourcemanagements/sourcemanagement/analysis/task-ref",
                                         taskRefFactory );
        configDigester.addSetNext( "configuration/sourcemanagements/sourcemanagement/analysis/task-ref",
                                   "addAnalysisTask" );
        registerParameterTag( configDigester, "configuration/sourcemanagements/sourcemanagement/analysis/task-ref" );
        configDigester.addFactoryCreate( "configuration/sourcemanagements/sourcemanagement/termination/task-ref",
                                         taskRefFactory );
        registerParameterTag( configDigester, "configuration/sourcemanagements/sourcemanagement/termination/task-ref" );
        configDigester.addSetNext( "configuration/sourcemanagements/sourcemanagement/termination/task-ref",
                                   "addTerminationTask" );
        configDigester.addSetNext( "configuration/sourcemanagements/sourcemanagement", "addSourceManagement" );

        // Traitement des profils
        ProfileFactory profileFactory = new ProfileFactory();
        configDigester.addFactoryCreate( "configuration/profiles/profile", profileFactory );
        configDigester.addSetProperties( "configuration/profiles/profile" );
        // Les grilles
        configDigester.addObjectCreate( "configuration/profiles/profile/integrityConstraints/grids/grid",
                                        QualityGridBO.class );
        configDigester.addSetProperties( "configuration/profiles/profile/integrityConstraints/grids/grid" );
        configDigester.addSetNext( "configuration/profiles/profile/integrityConstraints/grids/grid", "addGrid" );
        // Les Configurations
        // Le scatterplot
        configDigester.addObjectCreate(
                                        "configuration/profiles/profile/integrityConstraints/displayConfigurations/bubble",
                                        BubbleConfBO.class );
        String[] nameInXML = { "xtre", "ytre", "xPos", "yPos" };
        String[] nameInBO = { "XTre", "YTre", "horizontalAxisPos", "verticalAxisPos" };
        configDigester.addSetProperties(
                                         "configuration/profiles/profile/integrityConstraints/displayConfigurations/bubble",
                                         nameInXML, nameInBO );
        configDigester.addSetNext( "configuration/profiles/profile/integrityConstraints/displayConfigurations/bubble",
                                   "addProfileDisplayConf" );
        // La volumétrie
        configDigester.addObjectCreate(
                                        "configuration/profiles/profile/integrityConstraints/displayConfigurations/volumetries/volumetry",
                                        VolumetryConfBO.class );
        configDigester.addSetProperties(
                                         "configuration/profiles/profile/integrityConstraints/displayConfigurations/volumetries/volumetry",
                                         "component", "componentType" );
        registerTreTag( configDigester,
                        "configuration/profiles/profile/integrityConstraints/displayConfigurations/volumetries/volumetry" );
        configDigester.addSetNext(
                                   "configuration/profiles/profile/integrityConstraints/displayConfigurations/volumetries/volumetry",
                                   "addProfileDisplayConf" );
        // Les tâches
        configDigester.addFactoryCreate( "configuration/profiles/profile/analysis/task-ref", taskRefFactory );
        registerParameterTag( configDigester, "configuration/profiles/profile/analysis/task-ref" );
        configDigester.addSetNext( "configuration/profiles/profile/analysis/task-ref", "addAnalysisTask" );
        configDigester.addFactoryCreate( "configuration/profiles/profile/termination/task-ref", taskRefFactory );
        registerParameterTag( configDigester, "configuration/profiles/profile/termination/task-ref" );
        configDigester.addSetNext( "configuration/profiles/profile/termination/task-ref", "addTerminationTask" );

        configDigester.addSetNext( "configuration/profiles/profile", "addProfile" );

        // two-to-compute - value
        configDigester.addObjectCreate( "configuration/admin-params/two-to-compute", AdminParamsBO.class );
        configDigester.addCallMethod( "configuration/admin-params/two-to-compute", "setAdminParam", 2, 
                                      new Class[] { String.class, String.class } );
        configDigester.addCallParamPath( "configuration/admin-params/two-to-compute", 0 );
        configDigester.addCallParam( "configuration/admin-params/two-to-compute", 1 );
        configDigester.addSetNext( "configuration/admin-params/two-to-compute", "addAdminParam" );
        
        // mail - smtp server
        configDigester.addObjectCreate( "configuration/admin-params/mail/smtp-server", AdminParamsBO.class );
        configDigester.addCallMethod( "configuration/admin-params/mail/smtp-server", "setAdminParam", 2, new Class[] {
            String.class, String.class } );
        configDigester.addCallParamPath( "configuration/admin-params/mail/smtp-server", 0 );
        configDigester.addCallParam( "configuration/admin-params/mail/smtp-server", 1 );
        configDigester.addSetNext( "configuration/admin-params/mail/smtp-server", "addAdminParam" );

        // mail - default sender
        configDigester.addObjectCreate( "configuration/admin-params/mail/sender-address", AdminParamsBO.class );
        configDigester.addCallMethod( "configuration/admin-params/mail/sender-address", "setAdminParam", 2,
                                      new Class[] { String.class, String.class } );
        configDigester.addCallParamPath( "configuration/admin-params/mail/sender-address", 0 );
        configDigester.addCallParam( "configuration/admin-params/mail/sender-address", 1 );
        configDigester.addSetNext( "configuration/admin-params/mail/sender-address", "addAdminParam" );

        // mail - squale's administrators mailing list
        configDigester.addObjectCreate( "configuration/admin-params/mail/admin-mailing-list", AdminParamsBO.class );
        configDigester.addCallMethod( "configuration/admin-params/mail/admin-mailing-list", "setAdminParam", 2,
                                      new Class[] { String.class, String.class } );
        configDigester.addCallParamPath( "configuration/admin-params/mail/admin-mailing-list", 0 );
        configDigester.addCallParam( "configuration/admin-params/mail/admin-mailing-list", 1 );
        configDigester.addSetNext( "configuration/admin-params/mail/admin-mailing-list", "addAdminParam" );

        // mail - smtp authentication needed ?
        configDigester.addObjectCreate( "configuration/admin-params/mail/smtp-authent-needed", AdminParamsBO.class );
        configDigester.addCallMethod( "configuration/admin-params/mail/smtp-authent-needed", "setAdminParam", 2,
                                      new Class[] { String.class, String.class } );
        configDigester.addCallParamPath( "configuration/admin-params/mail/smtp-authent-needed", 0 );
        configDigester.addCallParam( "configuration/admin-params/mail/smtp-authent-needed", 1 );
        configDigester.addSetNext( "configuration/admin-params/mail/smtp-authent-needed", "addAdminParam" );

        // mail - smtp authentication : user name
        configDigester.addObjectCreate( "configuration/admin-params/mail/smtp-username", AdminParamsBO.class );
        configDigester.addCallMethod( "configuration/admin-params/mail/smtp-username", "setAdminParam", 2, new Class[] {
            String.class, String.class } );
        configDigester.addCallParamPath( "configuration/admin-params/mail/smtp-username", 0 );
        configDigester.addCallParam( "configuration/admin-params/mail/smtp-username", 1 );
        configDigester.addSetNext( "configuration/admin-params/mail/smtp-username", "addAdminParam" );

        // mail - smtp authentication : password
        configDigester.addObjectCreate( "configuration/admin-params/mail/smtp-password", AdminParamsBO.class );
        configDigester.addCallMethod( "configuration/admin-params/mail/smtp-password", "setAdminParam", 2, new Class[] {
            String.class, String.class } );
        configDigester.addCallParamPath( "configuration/admin-params/mail/smtp-password", 0 );
        configDigester.addCallParam( "configuration/admin-params/mail/smtp-password", 1 );
        configDigester.addSetNext( "configuration/admin-params/mail/smtp-password", "addAdminParam" );

        // shared repository - mapping java - project - loc
        configDigester.addObjectCreate( "configuration/admin-params/shared-repository-export/mapping/java/module/loc", AdminParamsBO.class );
        configDigester.addCallMethod( "configuration/admin-params/shared-repository-export/mapping/java/module/loc", "setAdminParam", 2, new Class[] {
            String.class, String.class } );
        configDigester.addCallParamPath( "configuration/admin-params/shared-repository-export/mapping/java/module/loc", 0 );
        configDigester.addCallParam( "configuration/admin-params/shared-repository-export/mapping/java/module/loc", 1 );
        configDigester.addSetNext( "configuration/admin-params/shared-repository-export/mapping/java/module/loc", "addAdminParam" );
        
        // shared repository - mapping java - project - number of classes
        configDigester.addObjectCreate( "configuration/admin-params/shared-repository-export/mapping/java/module/number-of-classes", AdminParamsBO.class );
        configDigester.addCallMethod( "configuration/admin-params/shared-repository-export/mapping/java/module/number-of-classes", "setAdminParam", 2, new Class[] {
            String.class, String.class } );
        configDigester.addCallParamPath( "configuration/admin-params/shared-repository-export/mapping/java/module/number-of-classes", 0 );
        configDigester.addCallParam( "configuration/admin-params/shared-repository-export/mapping/java/module/number-of-classes", 1 );
        configDigester.addSetNext( "configuration/admin-params/shared-repository-export/mapping/java/module/number-of-classes", "addAdminParam" );
        
        // shared repository - mapping java - class - loc
        configDigester.addObjectCreate( "configuration/admin-params/shared-repository-export/mapping/java/class/loc", AdminParamsBO.class );
        configDigester.addCallMethod( "configuration/admin-params/shared-repository-export/mapping/java/class/loc", "setAdminParam", 2, new Class[] {
            String.class, String.class } );
        configDigester.addCallParamPath( "configuration/admin-params/shared-repository-export/mapping/java/class/loc", 0 );
        configDigester.addCallParam( "configuration/admin-params/shared-repository-export/mapping/java/class/loc", 1 );
        configDigester.addSetNext( "configuration/admin-params/shared-repository-export/mapping/java/class/loc", "addAdminParam" );
        
        // shared repository - mapping java - class - number of methods
        configDigester.addObjectCreate( "configuration/admin-params/shared-repository-export/mapping/java/class/number-of-methods", AdminParamsBO.class );
        configDigester.addCallMethod( "configuration/admin-params/shared-repository-export/mapping/java/class/number-of-methods", "setAdminParam", 2, new Class[] {
            String.class, String.class } );
        configDigester.addCallParamPath( "configuration/admin-params/shared-repository-export/mapping/java/class/number-of-methods", 0 );
        configDigester.addCallParam( "configuration/admin-params/shared-repository-export/mapping/java/class/number-of-methods", 1 );
        configDigester.addSetNext( "configuration/admin-params/shared-repository-export/mapping/java/class/number-of-methods", "addAdminParam" );
        
        // shared repository - mapping java - method - loc
        configDigester.addObjectCreate( "configuration/admin-params/shared-repository-export/mapping/java/method/loc", AdminParamsBO.class );
        configDigester.addCallMethod( "configuration/admin-params/shared-repository-export/mapping/java/method/loc", "setAdminParam", 2, new Class[] {
            String.class, String.class } );
        configDigester.addCallParamPath( "configuration/admin-params/shared-repository-export/mapping/java/method/loc", 0 );
        configDigester.addCallParam( "configuration/admin-params/shared-repository-export/mapping/java/method/loc", 1 );
        configDigester.addSetNext( "configuration/admin-params/shared-repository-export/mapping/java/method/loc", "addAdminParam" );
        
        // shared repository - mapping java - method - vg
        configDigester.addObjectCreate( "configuration/admin-params/shared-repository-export/mapping/java/method/vg", AdminParamsBO.class );
        configDigester.addCallMethod( "configuration/admin-params/shared-repository-export/mapping/java/method/vg", "setAdminParam", 2, new Class[] {
            String.class, String.class } );
        configDigester.addCallParamPath( "configuration/admin-params/shared-repository-export/mapping/java/method/vg", 0 );
        configDigester.addCallParam( "configuration/admin-params/shared-repository-export/mapping/java/method/vg", 1 );
        configDigester.addSetNext( "configuration/admin-params/shared-repository-export/mapping/java/method/vg", "addAdminParam" );
        
        // shared repository - squalix server name which will done the export
        configDigester.addObjectCreate( "configuration/admin-params/shared-repository-export/squalix-server/name", AdminParamsBO.class );
        configDigester.addCallMethod( "configuration/admin-params/shared-repository-export/squalix-server/name", "setAdminParam", 2, new Class[] {
            String.class, String.class } );
        configDigester.addCallParamPath( "configuration/admin-params/shared-repository-export/squalix-server/name", 0 );
        configDigester.addCallParam( "configuration/admin-params/shared-repository-export/squalix-server/name", 1 );
        configDigester.addSetNext( "configuration/admin-params/shared-repository-export/squalix-server/name", "addAdminParam" );
        
        return configDigester;
    }

    /**
     * Enregistrement d'un paramètre de tâche
     * 
     * @param pDigester digester
     * @param pPath chemin
     */
    private void registerParameterTag( Digester pDigester, String pPath )
    {
        String path = new StringBuffer( pPath ).append( "/parameter" ).toString();
        pDigester.addCallMethod( path, "addParameter", 2 );
        pDigester.addCallParam( path, 0, "name" );
        pDigester.addCallParam( path, 1, "value" );
    }

    /**
     * Enregistrement d'un tre de configuration de volumétrie
     * 
     * @param pDigester digester
     * @param pPath chemin
     */
    private void registerTreTag( Digester pDigester, String pPath )
    {
        String path = new StringBuffer( pPath ).append( "/tre" ).toString();
        pDigester.addCallMethod( path, "addTre", 1 );
        pDigester.addCallParam( path, 0, "name" );
    }
}
