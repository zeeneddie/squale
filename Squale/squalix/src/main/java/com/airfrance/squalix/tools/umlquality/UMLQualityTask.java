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
package com.airfrance.squalix.tools.umlquality;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.ErrorBO;
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.core.TaskException;
import com.airfrance.squalix.core.exception.ConfigurationException;
import com.umlquality.connector.Project;
import com.umlquality.connector.QualimetryConnectorException;
import com.umlquality.connector.UQFacade;

/**
 * Exécute l'analyse UMQuality sur un sous-projet grace à la façade UMQuality <code>UQFacade<code>.<br>
 * L'environnement UMLQuality doit être correctement initialisé avant le lancement de la tâche.<br>
 * Il est important que le parsing des rapports débute par celui du niveau le plus élevé(Model,Package...),<br> 
 * vers le moin élevé(...State,Transition) afin
 * de pouvoir créer correctement les composants UML si nécessaire.<br>
 *
 * @author sportorico
 */
public class UMLQualityTask
    extends AbstractTask
{
    /**
     * Configuration de l'outil d'analyse
     */
    private UMLQualityConfiguration mConfiguration;

    /**
     * Instance du persisteur Umlquality
     */
    private UMLQualityPersistor mPersistor = null;

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( UMLQualityTask.class );

    /**
     * Espace de travail pour UMLQuality
     */
    private UMLQualityWorkSpace mWorkspace;

    /**
     * Constructeur par defaut
     */
    public UMLQualityTask()
    {
        mName = "UMLQualityTask";

    }

    /**
     * L'analyse complète consiste en :
     * <ul>
     * <li>Création des rapports par l'analyse des sources fait par UMLQuality</li>
     * <li>Création des beans à partir des rapports</li>
     * <li>Transformation des beans en beans persistants</li>
     * <li>Persistance des beans</li>
     * </ul>
     * 
     * @throws TaskException Si un problème d'exécution apparaît.
     */
    public void execute()
        throws TaskException
    {
        try
        {
            LOGGER.info( UMLQualityMessages.getString( "logs.analyzing" ) + mProject.getParent().getName() + " - "
                + mProject.getName() );

            mConfiguration = new UMLQualityConfiguration();
            // Si la tâche n'est pas configurée
            // Les données du projet doivent fournir les paramètres de la tâche UMLQuality
            MapParameterBO umlqualityMap = (MapParameterBO) mProject.getParameter( ( ParametersConstants.UMLQUALITY ) );
            if ( umlqualityMap == null )
            {
                // Affichage d'un warning et annulation de la tâche
                String message = UMLQualityMessages.getString( "umlquality.exception.task.not_configured" );
                LOGGER.warn( message );
                mStatus = CANCELLED;
                initError( message, ErrorBO.CRITICITY_WARNING );
            }
            else
            {
                // la configuration existe
                // On va utiliser de préférence un InputStream issu du classpath,
                InputStream is =
                    Thread.currentThread().getContextClassLoader().getResourceAsStream( "config/umlquality-config.xml" );
                if ( null == is )
                {
                    // mais si celui-ci n'exista pas, alors on le récupère en relatif
                    is = new FileInputStream( "config/umlquality-config.xml" );
                }
                mConfiguration.parse( is );
                // On crée le répertoire de travail en cas
                File reportDir =
                    new File( mConfiguration.getReportDirectory(), "project" + getProjectId() + "_audit" + getAuditId()
                        + "_" + System.currentTimeMillis() );
                reportDir.getParentFile().mkdirs();
                mPersistor = new UMLQualityPersistor( mProject, mAudit, getSession() );

                // Espace de travail pour UMLQuality
                mWorkspace = new UMLQualityWorkSpace( reportDir );

                createReports( umlqualityMap ); // creation des rapports

                parseReports(); // parsing des rapports

                // positionne les données sur la taille du file System
                affectFileSystemSize( mWorkspace.getReportDirectory(), false );

                // Suppression du répertoire de travail
                mWorkspace.cleanup();
            }
        }
        catch ( Exception e )
        {
            throw new TaskException( e );
        }
    }

    /**
     * Géneration des rapports: UMLQuality audit le fichier xmi et génere le rapport(fichier) en csv.
     * 
     * @param pUmlqualityMap les paramètres du projet concernant uml quality
     * @throws QualimetryConnectorException execption remontée par umlquality si un problème se produit lors de l'audit
     *             du fichier xmi.
     * @throws ConfigurationException erreur de configuration
     * @throws IOException erreur de flux
     */
    private void createReports( MapParameterBO pUmlqualityMap )
        throws QualimetryConnectorException, ConfigurationException, IOException
    {

        UQFacade mUQFacade = null;
        // Projet au sens d'UMLQuality
        Project umlQualityProject = null;
        UMLQualityListner listener = null;

        // exclusion de certains éléments de l'analyse
        String[] filterTable = null;
        if ( pUmlqualityMap.getParameters().get( ParametersConstants.MODEL_EXCLUDED_CLASSES ) != null )
        {
            // On récupère la liste des patterns d'exclusions des classes et on rempli le tableau de filtre d'UQ
            List filterList =
                ( (ListParameterBO) pUmlqualityMap.getParameters().get( ParametersConstants.MODEL_EXCLUDED_CLASSES ) ).getParameters();
            // il faut parcourir tous les StringParameterBo pour en extraire les patterns
            Iterator it = filterList.iterator();
            List listPatterns = new ArrayList();
            while ( it.hasNext() )
            {
                listPatterns.add( ( (StringParameterBO) ( it.next() ) ).getValue() );
            }
            // On rempli le tableau à passer en paramètres à UQ
            filterTable = new String[listPatterns.size()];
            listPatterns.toArray( filterTable );
        }
        // le nom du rapport (fichier resultat)
        File directory = mWorkspace.getReportDirectory();

        String outputfilename = UMLQualityMessages.getString( "project.parameter.outputfilename" );
        outputfilename = directory.getAbsolutePath() + File.separator + outputfilename;

        mUQFacade = UQFacade.getInstance(); // récuperer l'instance de la façade UMLQuality
        umlQualityProject = mUQFacade.getProject(); // récuperer un projet UMLQuality
        listener = new UMLQualityListner( this ); // intanciation du listener de UMLQuality
        mUQFacade.addMessageListener( listener );
        mUQFacade.addDataListener( listener );

        if ( null != filterTable )
        {
            umlQualityProject.setFilters( filterTable );
        }
        umlQualityProject.setOutputFormat( "csv" );
        umlQualityProject.setOneFile( false );

        // On va maintenant créer le rapport pour chaque fichier (xmi)
        File file = getModelFile( pUmlqualityMap ); // on recupère l'entrée(s) de UMLQuality(fichier(s) xmi)
        if ( null != file && file.canRead() )
        {
            LOGGER.info( UMLQualityMessages.getString( "logs.debug.xmi.file.path" ) + file.getAbsolutePath() );
            umlQualityProject.setSourceFile( file.getCanonicalPath() );
            umlQualityProject.setOutputFile( outputfilename );
            // lancer l'export par la façade de UMLQuality
            mUQFacade.export( umlQualityProject );

        }
        else
        {
            // on ne trouve pas de fichier xmi à analyser
            String message = UMLQualityMessages.getString( "umlquality.exception.xmi_file.not_found" );
            LOGGER.error( message );
            // On remonte une exception de configuration
            throw new ConfigurationException( message );
        }
    }

    /**
     * Parse les différents fichiers csv générés par UMLQuality
     * 
     * @throws Exception si le parsing des rapports échoué
     */
    private void parseReports()
        throws Exception
    {

        // recupérer les raports crées

        Collection reports = mWorkspace.getReportFiles();

        // recupère une liste ordonnée des type de rapports
        String[] endFilesName = ( UMLQualityMessages.getString( "reports.end.file.name" ) ).split( "," );

        Iterator it = null;
        String reportName = null;
        String type = null;

        if ( endFilesName != null )
        { // on a une liste ordonnée des extensions de rapports

            for ( int i = 0; i < endFilesName.length; i++ )
            { // parcours de liste ordonées
                it = reports.iterator();
                while ( it.hasNext() )
                { // parcours des différents rapports générés
                    reportName = (String) it.next(); // recupère le nom du rapport

                    if ( reportName.endsWith( endFilesName[i] ) )
                    { // test le type de rapport
                        type = UMLQualityUtility.typeReport( endFilesName[i] ); // recupère le type de rapport
                        if ( type != null )
                        {
                            mPersistor.parseComponentReport( reportName, type ); // parse le rapport
                        }
                    }
                }
            }
        }

    }

    /**
     * Access method for the mName property.
     * 
     * @return le nom de la tâche mName
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @param pConfiguration la configuration du projet
     */
    public void setConfiguration( UMLQualityConfiguration pConfiguration )
    {
        mConfiguration = pConfiguration;
    }

    /**
     * Récupère le ou les fichier(s) xmi à analyser
     * 
     * @param pUmlQualityMap les paramètres UMLQuality
     * @return le fichier du modèle
     * @throws ConfigurationException si erreur
     */
    private File getModelFile( MapParameterBO pUmlQualityMap )
        throws ConfigurationException
    {
        File result = null;
        Map params = pUmlQualityMap.getParameters();
        StringParameterBO filePath = (StringParameterBO) params.get( ParametersConstants.UMLQUALITY_SOURCE_XMI );

        if ( filePath == null )
        {
            // Renvoi d'une exception de configuration
            throw new ConfigurationException( UMLQualityMessages.getString( "umlquality.exception.variable.not_found" )
                + ParametersConstants.UMLQUALITY_SOURCE_XMI );
        }
        else
        {
            String xmiPath = (String) mData.getData( TaskData.VIEW_PATH ) + filePath.getValue();
            File xmiFile = new File( xmiPath );
            // Si le fichier xmi a un nom absolu et existe, on prend celui-ci
            if ( xmiFile.isAbsolute() && xmiFile.exists() )
            {
                result = xmiFile;
            }
            else
            {
                // Renvoi d'une exception de configuration
                throw new ConfigurationException(
                                                  UMLQualityMessages.getString( "umlquality.exception.xmipath.not.valid" ) );
            }
        }
        return result;
    }
}
