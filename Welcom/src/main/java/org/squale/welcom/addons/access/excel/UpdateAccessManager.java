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
 * Créé le 22 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.addons.access.excel;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.squale.welcom.addons.access.WAddOnsAccessManager;
import org.squale.welcom.addons.access.excel.bd.BdAccessFactory;
import org.squale.welcom.addons.access.excel.filereader.AccessKeyReaderException;
import org.squale.welcom.addons.access.excel.filereader.AccessKeyReaderFactory;
import org.squale.welcom.addons.access.excel.filereader.IAccessKeyReader;
import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.outils.jdbc.WJdbc;
import org.squale.welcom.outils.jdbc.WJdbcMagic;
import org.squale.welcom.outils.jdbc.WStatement;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class UpdateAccessManager
{
    /** logger */
    private static Log logStartup = LogFactory.getLog( "Welcom" );

    /** Mon lecteur de fichier excel */
    private static IAccessKeyReader accessKeyReader = null;

    /** Un petit signleton */
    private static UpdateAccessManager updateAccessManager = null;

    /** Table pour la gestion des plugins */
    private final static String WEL_ADDONS = WelcomConfigurator.getMessage( WelcomConfigurator.ADDONS_TABLE_NAME );

    /**
     * Contructeur
     * 
     * @param excelAccessFile du fichier excel
     * @throws AccessKeyReaderException : Probleme
     */
    private UpdateAccessManager( final String excelAccessFile )
        throws AccessKeyReaderException
    {
        WJdbc jdbc = null;
        try
        {
            jdbc = new WJdbcMagic();
            // Verification et creation des tables
            BdAccessFactory.getBdCreate().checkAnCreateAllTable( jdbc );
        }
        catch ( final SQLException e )
        {
            logStartup.error( e );
        }
        finally
        {
            if ( jdbc != null )
            {
                jdbc.close();
            }
        }

        final URL url = getUrlAccessKeyFile( excelAccessFile );

        if ( !checkIfUpdateBDWithTheFile( url ) )
        {
            logStartup.info( "Mise a jour de la BD ... en cours" );
            updateBD( url );
        }
        else
        {
            logStartup.info( "Base de donnée ... Ok" );
        }

    }

    /**
     * Met a jour la base de donnée avec le fichier xls
     * 
     * @param url : Url...
     * @throws AccessKeyReaderException : Probleme
     */
    private void updateBD( final URL url )
        throws AccessKeyReaderException
    {
        // Lecture du fichier excel
        accessKeyReader = AccessKeyReaderFactory.read( url );

        WJdbc jdbc = null;
        int cpt = 0;
        try
        {
            jdbc = new WJdbcMagic();
            // Mise a jour de la BD : Table ACCESSKEY
            cpt +=
                BdAccessFactory.getBdUpdate().updateAccessKey( jdbc, accessKeyReader.getAccessKey(),
                                                               BdAccessFactory.getBdReader().getAccessKey( jdbc, false ) );

            // Mise a jour de la BD : Table PROFILE
            cpt +=
                BdAccessFactory.getBdUpdate().updateProfile( jdbc, accessKeyReader.getProfile(),
                                                             BdAccessFactory.getBdReader().getProfile( jdbc, false ) );

            // Mise a jour de la BD : Table PROFILE_ACCESSKEY_INT
            cpt +=
                BdAccessFactory.getBdUpdate().updateProfileAccessKey(
                                                                      jdbc,
                                                                      accessKeyReader.getProfileAccessKey(),
                                                                      BdAccessFactory.getBdReader().getProfileAccessKey(
                                                                                                                         jdbc,
                                                                                                                         false ) );

            // Mise a jour de la Date
            setTimeStampBD( jdbc, url );

            jdbc.commit();

        }
        catch ( final SQLException e )
        {
            logStartup.error( e, e );
        }
        finally
        {
            if ( jdbc != null )
            {
                jdbc.close();
            }
        }

    }

    /**
     * @param excelAccessFile Nom du fichier excel
     * @throws AccessKeyReaderException : Probleme
     */
    public static void update( final String excelAccessFile )
        throws AccessKeyReaderException
    {
        if ( updateAccessManager == null )
        {
            updateAccessManager = new UpdateAccessManager( excelAccessFile );
        }

    }

    /**
     * @param url : URL
     * @return La date de dernier emodification de l'url
     */
    private Date getTimeUrl( final URL url )
    {
        try
        {
            final URLConnection urlCon = url.openConnection();
            urlCon.setUseCaches( false );
            urlCon.connect();
            return new Date( urlCon.getLastModified() );
        }
        catch ( final IOException e )
        {
            logStartup.error( e, e );
        }
        return null;
    }

    /**
     * Retourne vrai si la bd est ok
     * 
     * @param url : URl du fichier
     * @return vrais si la base est a jour avec le fichier
     */
    private boolean checkIfUpdateBDWithTheFile( final URL url )
    {
        final Date excelFileDate = getTimeUrl( url );
        final Date bdDate = getTimeStampBD();

        if ( bdDate != null )
        {
            return ( excelFileDate.getTime() <= bdDate.getTime() );
        }

        return false;
    }

    /**
     * Met a jour la date de modification en base du fichier excel
     * 
     * @param jdbcMagic : Connexion jdbc
     * @param url : Url du fichier excel
     */
    private void setTimeStampBD( final WJdbc jdbcMagic, final URL url )
    {

        Date excelFileDate;
        try
        {
            excelFileDate = getTimeUrl( url );

            final WStatement sta = jdbcMagic.getWStatement();
            sta.add( "update " + WEL_ADDONS + " set" );
            sta.addParameter( "PARAMETERS=?", "" + excelFileDate.getTime() );

            sta.addParameter( "where NAME=?", WAddOnsAccessManager.ADDONS_ACCESSMANAGEMENT_NAME );

            sta.executeUpdate();
            sta.close();

        }
        catch ( final Exception e )
        {
            logStartup.error( e, e );
        }

    }

    /**
     * @return Derniere date de modification, null si la table n'existe pas
     */
    private Date getTimeStampBD()
    {
        Date bdDate = null;
        WJdbcMagic jdbcMagic = null;
        try
        {
            jdbcMagic = new WJdbcMagic();
            final WStatement sta = jdbcMagic.getWStatement();
            sta.add( "select * from WEL_ADDONS where" );
            sta.addParameter( "NAME=?", WAddOnsAccessManager.ADDONS_ACCESSMANAGEMENT_NAME );
            final ResultSet rs = sta.executeQuery();
            if ( ( rs != null ) && rs.next() )
            {
                bdDate = new Date( rs.getLong( "PARAMETERS" ) );
            }
            sta.close();

        }
        catch ( final SQLException e )
        {
            return null;
        }
        finally
        {
            if ( jdbcMagic != null )
            {
                jdbcMagic.close();
            }
        }
        return bdDate;
    }

    /**
     * @param fileName : Nom de la resource locale du projet.
     * @return Retourne l'url du fichier
     * @throws AccessKeyReaderException : Probleme sur la lecture du fichier
     */
    private URL getUrlAccessKeyFile( final String fileName )
        throws AccessKeyReaderException
    {

        final String extension = "xls";
        if ( GenericValidator.isBlankOrNull( fileName ) )
        {
            throw new AccessKeyReaderException(
                                                "Vous devez spécifier un fichier de gestion des droits d'accés, attribut 'excelAccessFile' du plugin" );
        }

        // Set up to load the property resource for this locale key, if we can
        String name = fileName.replace( '.', '/' );
        if ( name.endsWith( "/" + extension ) )
        {
            name = name.substring( 0, name.length() - extension.length() - 1 );
        }
        name += "." + extension;

        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        final URL url = classLoader.getResource( name );
        if ( url == null )
        {
            throw new AccessKeyReaderException( "Le fichier de gestion des droits d'accés est introuvable, '" + name
                + "'" );
        }
        return ( url );

    }

}
