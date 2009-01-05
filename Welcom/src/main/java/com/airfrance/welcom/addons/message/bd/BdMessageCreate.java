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
package com.airfrance.welcom.addons.message.bd;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.welcom.addons.config.AddonsConfig;
import com.airfrance.welcom.outils.jdbc.WJdbc;
import com.airfrance.welcom.outils.jdbc.WStatement;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class BdMessageCreate
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
        checkTable( jdbc, AddonsConfig.WEL_MSGLIBELLE );

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

        if ( com.airfrance.welcom.outils.Util.isEquals( pTable, AddonsConfig.WEL_MSGLIBELLE ) )
        {
            createTableWelLibelle( jdbc );
        }
    }

    /**
     * CREATE TABLE WEL_PROFILE_ACCESSKEY ( IDPROFILE VARCHAR2 (30), IDACCESSKEY NUMBER, ACCESSKEY VARCHAR2 (255), VALUE
     * VARCHAR2 (255), DATE DATETIME) Creation de la table WEL_PROFILE_ACCESSKEY
     * 
     * @param jdbc : Connexion jdbc
     * @throws SQLException Probleme SQL
     */
    private void createTableWelLibelle( final WJdbc jdbc )
        throws SQLException
    {
        final WStatement sta = jdbc.getWStatement();
        sta.add( "CREATE TABLE " );
        sta.add( AddonsConfig.WEL_MSGLIBELLE );
        sta.add( " (" );
        sta.add( "MESSAGEKEY          VARCHAR (255), " );
        sta.add( "MESSAGE         VARCHAR (255)," );
        sta.add( "LOCALE         VARCHAR (255))" );
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
        dropTable( jdbc, AddonsConfig.WEL_MSGLIBELLE );
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
