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
 * Créé le 25 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.addons.access.excel.bd;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.welcom.addons.config.AddonsConfig;
import org.squale.welcom.outils.jdbc.WJdbc;
import org.squale.welcom.outils.jdbc.WStatement;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class BdAccessCreate
{
    /** logger */
    private static Log logStartup = LogFactory.getLog( "Welcom" );

    /** Table not found */
    private final static int SQL_CODE_TABLE_NOT_FOUND = 942;

    /**
     * Verifie l'existance des tables é les crée si necessaire
     * 
     * @param jdbc Connection jdbc
     * @throws SQLException : Probleme SQL
     */
    public void checkAnCreateAllTable( final WJdbc jdbc )
        throws SQLException
    {

        // dropTableAll(jdbc);

        // Verification de la BD
        checkTable( jdbc, AddonsConfig.WEL_ACCESSKEY );
        checkTable( jdbc, AddonsConfig.WEL_PROFILE );
        checkTable( jdbc, AddonsConfig.WEL_PROFILE_ACCESSKEY_INT );
        checkTable( jdbc, AddonsConfig.WEL_PROFILE_ACCESSKEY );
        checkVersion( jdbc );
    }

    /**
     * Verifie que la version est correcte
     * 
     * @param jdbc : Connexion JDBC
     * @throws SQLException : Probleme sur la recuperation de la version
     */
    private void checkVersion( final WJdbc jdbc )
        throws SQLException
    {
        // Les tables existent, on verifie qu'on est en version 2.3. (sinon on renomme les colonnes)
        WStatement sta = jdbc.getWStatement();
        sta.add( "select VERSION from " + AddonsConfig.WEL_ADDONS + " where NAME='ADDONS_ACCESSMANAGEMENT'" );
        final ResultSet resultSet = sta.executeQuery();
        String version = "";
        while ( resultSet.next() )
        {
            version = resultSet.getString( "VERSION" );
        }
        sta.close();
        // Si la version est 2.2, on met version 2.3
        if ( version.equals( "2.2" ) )
        {
            try
            {
                logStartup.info( "On passe l'addons d'acces de la version 2.2 à la version 2.3" );
                // on change KEY en ACCESSKEY pour WEL_ACCESSKEY
                sta = jdbc.getWStatement();
                sta.add( "ALTER TABLE " + AddonsConfig.WEL_ACCESSKEY + " RENAME COLUMN KEY to ACCESSKEY" );
                sta.executeUpdate();
                sta.close();
                logStartup.info( "Table " + AddonsConfig.WEL_ACCESSKEY + " : colonne KEY renommee en ACCESSKEY" );

                // on change KEY en ACCESSKEY pour WEL_PROFILE_ACCESSKEY_INT
                sta = jdbc.getWStatement();
                sta.add( "ALTER TABLE " + AddonsConfig.WEL_PROFILE_ACCESSKEY_INT + " RENAME COLUMN KEY to ACCESSKEY" );
                sta.executeUpdate();
                sta.close();
                logStartup.info( "Table " + AddonsConfig.WEL_PROFILE_ACCESSKEY_INT
                    + " : colonne KEY renommee en ACCESSKEY" );

                // on change KEY en ACCESSKEY pour WEL_PROFILE_ACCESSKEY
                sta = jdbc.getWStatement();
                sta.add( "ALTER TABLE " + AddonsConfig.WEL_PROFILE_ACCESSKEY + " RENAME COLUMN KEY to ACCESSKEY" );
                sta.executeUpdate();
                sta.close();
                logStartup.info( "Table " + AddonsConfig.WEL_PROFILE_ACCESSKEY + " : colonne KEY renommee en ACCESSKEY" );

                // on met à jour la version
                sta = jdbc.getWStatement();
                sta.add( "update " + AddonsConfig.WEL_ADDONS
                    + " set VERSION='2.3' where NAME='ADDONS_ACCESSMANAGEMENT'" );
                sta.executeUpdate();
                sta.close();
                jdbc.commit();
                logStartup.info( "Version mise à jour : " );
            }
            catch ( final SQLException sqle )
            {
                logStartup.error( "Erreur dans la montée de version de l'addons (2.2 à 2.3)" );
                throw sqle;
            }
        }

    }

    /**
     * Verifie si le tables est presente
     * 
     * @param pTable Nom de la table
     * @param jdbc Connection jdbc
     * @throws SQLException Probleme SQL
     */
    private void checkTable( final WJdbc jdbc, final String pTable )
        throws SQLException
    {
        try
        {
            final WStatement sta = jdbc.getWStatement();
            sta.add( "select * from " + pTable );
            sta.executeQuery();
            sta.close();
            logStartup.info( "Table '" + pTable + "' ... Ok" );
        }
        catch ( final SQLException sqle )
        {
            // 1146 : MYSQL , 20000 : DERBY
            if ( ( sqle.getErrorCode() == SQL_CODE_TABLE_NOT_FOUND ) || ( sqle.getErrorCode() == 1146 )
                || ( sqle.getErrorCode() == 20000 ) )
            {
                logStartup.info( "Table '" + pTable + "' not found" );
                createTable( jdbc, pTable );
            }
            else
            {
                logStartup.error( sqle, sqle );
            }
        }
    }

    /**
     * Dispatche la creation des tables
     * 
     * @param jdbc : Connexion jdbc
     * @param pTable Nom de la table
     * @throws SQLException Probleme SQL
     */
    private void createTable( final WJdbc jdbc, final String pTable )
        throws SQLException
    {
        logStartup.info( "Create Table : '" + pTable + "'" );

        if ( org.squale.welcom.outils.Util.isEquals( pTable, AddonsConfig.WEL_ACCESSKEY ) )
        {
            createTableWelAccessKey( jdbc );
        }
        else if ( org.squale.welcom.outils.Util.isEquals( pTable, AddonsConfig.WEL_PROFILE ) )
        {
            createTableWelProfile( jdbc );
        }
        else if ( org.squale.welcom.outils.Util.isEquals( pTable, AddonsConfig.WEL_PROFILE_ACCESSKEY_INT ) )
        {
            createTableWelProfileAccessKeyInternal( jdbc );
        }
        else if ( org.squale.welcom.outils.Util.isEquals( pTable, AddonsConfig.WEL_PROFILE_ACCESSKEY ) )
        {
            createTableWelProfileAccessKey( jdbc );
        }
    }

    /**
     * CREATE TABLE WEL_ACCESSKEY ( IDACCESSKEY NUMBER, TAB NUMBER, ACCESSKEY VARCHAR2 (255), LABEL VARCHAR2 (255), TYPE
     * VARCHAR2 (25), VERSION VARCHAR2 (6), DATE DATETIME) ; Creation de la table WEL_ACCESSKEY
     * 
     * @param jdbc : Connexion jdbc
     * @throws SQLException Probleme SQL
     */
    private void createTableWelAccessKey( final WJdbc jdbc )
        throws SQLException
    {
        final WStatement sta = jdbc.getWStatement();
        sta.add( "CREATE TABLE " + AddonsConfig.WEL_ACCESSKEY + " (" );
        sta.add( "IDACCESSKEY   INTEGER," );
        sta.add( "TAB   INTEGER," );
        sta.add( "ACCESSKEY     VARCHAR (255)," );
        sta.add( "LABEL         VARCHAR (255)," );
        sta.add( "TYPE          VARCHAR (25))" );
        sta.executeUpdate();
        sta.close();
    }

    /**
     * CREATE TABLE WEL_PROFILE ( IDPROFILE VARCHAR2 (50), NAME VARCHAR2 (255)) Creation de la table WEL_PROFILE
     * 
     * @param jdbc : Connexion jdbc
     * @throws SQLException Probleme SQL
     */
    private void createTableWelProfile( final WJdbc jdbc )
        throws SQLException
    {
        final WStatement sta = jdbc.getWStatement();
        sta.add( "CREATE TABLE " + AddonsConfig.WEL_PROFILE + " (" );
        sta.add( "IDPROFILE   VARCHAR (50)," );
        sta.add( "NAME    VARCHAR (255))" );
        sta.executeUpdate();
        sta.close();
    }

    /**
     * CREATE TABLE WEL_PROFILE_ACCESSKEY_INTERNAL ( IDPROFILE VARCHAR2 (50), IDACCESSKEY NUMBER, ACCESSKEY VARCHAR2
     * (255), VALUE VARCHAR2 (255)) Creation de la table WEL_PROFILE_ACCESSKEY_INTERNAL
     * 
     * @param jdbc : Connexion jdbc
     * @throws SQLException Probleme SQL
     */
    private void createTableWelProfileAccessKeyInternal( final WJdbc jdbc )
        throws SQLException
    {
        final WStatement sta = jdbc.getWStatement();
        sta.add( "CREATE TABLE " + AddonsConfig.WEL_PROFILE_ACCESSKEY_INT + " (" );
        sta.add( "IDPROFILE   VARCHAR (50)," );
        sta.add( "ACCESSKEY          VARCHAR (255), " );
        sta.add( "VALUE         VARCHAR (255))" );
        sta.executeUpdate();
        sta.close();
    }

    /**
     * CREATE TABLE WEL_PROFILE_ACCESSKEY ( IDPROFILE VARCHAR2 (50), IDACCESSKEY NUMBER, ACCESSKEY VARCHAR2 (255), VALUE
     * VARCHAR2 (255), DATE DATETIME) Creation de la table WEL_PROFILE_ACCESSKEY
     * 
     * @param jdbc : Connexion jdbc
     * @throws SQLException Probleme SQL
     */
    private void createTableWelProfileAccessKey( final WJdbc jdbc )
        throws SQLException
    {
        final WStatement sta = jdbc.getWStatement();
        sta.add( "CREATE TABLE " + AddonsConfig.WEL_PROFILE_ACCESSKEY + " (" );
        sta.add( "IDPROFILE   VARCHAR (50)," );
        sta.add( "ACCESSKEY          VARCHAR (255), " );
        sta.add( "VALUE         VARCHAR (255)," );
        sta.add( "USERNAME         VARCHAR (255)," );
        sta.add( "DATES         DATE)" );
        sta.executeUpdate();
        sta.close();
    }

    /**
     * Supprime toute les tables
     * 
     * @param jdbc : Connexion jdbc
     * @throws SQLException Probleme SQL
     */
    protected void dropTableAll( final WJdbc jdbc )
        throws SQLException
    {
        // Verification de la BD
        dropTable( jdbc, AddonsConfig.WEL_ACCESSKEY );
        dropTable( jdbc, AddonsConfig.WEL_PROFILE );
        dropTable( jdbc, AddonsConfig.WEL_PROFILE_ACCESSKEY_INT );
        dropTable( jdbc, AddonsConfig.WEL_PROFILE_ACCESSKEY );
    }

    /**
     * Supprime la table
     * 
     * @param jdbc : Connexion jdbc
     * @param pTable Nom de la table
     * @throws SQLException Probleme SQL
     */
    private void dropTable( final WJdbc jdbc, final String pTable )
        throws SQLException
    {
        final WStatement sta = jdbc.getWStatement();
        sta.add( "DROP TABLE " + pTable );
        sta.executeUpdate();
        sta.close();
    }

}
