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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.welcom.addons.access.excel.object.AccessKey;
import org.squale.welcom.addons.access.excel.object.Profile;
import org.squale.welcom.addons.access.excel.object.ProfileAccessKey;
import org.squale.welcom.addons.config.AddonsConfig;
import org.squale.welcom.outils.jdbc.WJdbc;
import org.squale.welcom.outils.jdbc.WStatement;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class BdAccessUpdate
{
    /** logger */
    private static Log log = LogFactory.getLog( BdAccessUpdate.class );

    /**
     * Mise a jour des profiles
     * 
     * @param jdbc : connecition
     * @param arrayFromFile : liste dans le fichie excel
     * @param arrayFromBd : liste dans la bd
     * @throws SQLException : probleme SQL
     * @return nombre de champs modifiés
     */
    public int updateProfile( final WJdbc jdbc, final ArrayList arrayFromFile, final ArrayList arrayFromBd )
        throws SQLException
    {

        int cpt = 0;
        final ArrayList noChange = new ArrayList();
        Iterator iter = arrayFromBd.iterator();

        while ( iter.hasNext() )
        {
            final Profile profileBd = (Profile) iter.next();
            if ( arrayFromFile.contains( profileBd ) )
            {
                noChange.add( profileBd );
            }
        }

        // Suppression des doubles
        arrayFromBd.removeAll( noChange );
        arrayFromFile.removeAll( noChange );

        iter = arrayFromBd.iterator();
        while ( iter.hasNext() )
        {
            final Profile profileBd = (Profile) iter.next();
            delete( jdbc, profileBd );
            cpt++;
        }

        iter = arrayFromFile.iterator();
        while ( iter.hasNext() )
        {
            final Profile profileFile = (Profile) iter.next();
            create( jdbc, profileFile );
            cpt++;
        }
        return cpt;

    }

    /**
     * Supprime un Profile
     * 
     * @param jdbc : connection
     * @param profile : profil
     * @throws SQLException : erreur SQL
     */
    public void delete( final WJdbc jdbc, final Profile profile )
        throws SQLException
    {
        log.info( "Delete : " + profile );

        final WStatement sta = jdbc.getWStatement();
        sta.add( "delete from " + AddonsConfig.WEL_PROFILE + " where " );
        sta.addParameter( "IDPROFILE=?", profile.getIdProfile() );

        sta.executeUpdate();
        sta.close();

    }

    /**
     * Insertion de Profile
     * 
     * @param jdbc : connection
     * @param profile : profil
     * @throws SQLException : Exception SQL
     */
    public void create( final WJdbc jdbc, final Profile profile )
        throws SQLException
    {

        log.info( "Insertion : " + profile );

        final WStatement sta = jdbc.getWStatement();
        sta.add( "insert into " + AddonsConfig.WEL_PROFILE + " (IDPROFILE,NAME)" );
        sta.add( "values (" );
        sta.addParameter( "?,", profile.getIdProfile() );
        sta.addParameter( "?)", profile.getName() );

        sta.executeUpdate();
        sta.close();
    }

    /**
     * Mise a jour des access Key
     * 
     * @param jdbc : connecition
     * @param arrayFromFile : liste dans le fichie excel
     * @param arrayFromBd : liste dans la bd
     * @throws SQLException : probleme SQL
     * @return Nombre de champs modifiés
     */
    public int updateAccessKey( final WJdbc jdbc, final ArrayList arrayFromFile, final ArrayList arrayFromBd )
        throws SQLException
    {

        int cpt = 0;
        final ArrayList noChange = new ArrayList();
        Iterator iter = arrayFromBd.iterator();

        while ( iter.hasNext() )
        {
            final AccessKey accessKeyBd = (AccessKey) iter.next();
            if ( arrayFromFile.contains( accessKeyBd ) )
            {
                noChange.add( accessKeyBd );
            }
        }

        // Suppression des doubles
        arrayFromBd.removeAll( noChange );
        arrayFromFile.removeAll( noChange );

        iter = arrayFromBd.iterator();
        while ( iter.hasNext() )
        {
            final AccessKey accessKeyBd = (AccessKey) iter.next();
            delete( jdbc, accessKeyBd );
            cpt++;
        }

        iter = arrayFromFile.iterator();
        while ( iter.hasNext() )
        {
            final AccessKey accessKeyFile = (AccessKey) iter.next();
            create( jdbc, accessKeyFile );
            cpt++;
        }
        return cpt;
    }

    /**
     * Supprime un accessKey
     * 
     * @param jdbc : connection
     * @param accessKey : clef d'accés
     * @throws SQLException : erreur SQL
     */
    public void delete( final WJdbc jdbc, final AccessKey accessKey )
        throws SQLException
    {
        log.info( "Delete : " + accessKey );

        final WStatement sta = jdbc.getWStatement();
        sta.add( "delete from " + AddonsConfig.WEL_ACCESSKEY + " where " );
        sta.addParameter( "ACCESSKEY=?", accessKey.getAccesskey() );

        sta.executeUpdate();
        sta.close();

    }

    /**
     * Insertion de accesKey
     * 
     * @param jdbc : connection
     * @param accessKey : accessKey
     * @throws SQLException : erreur SQL
     */
    public void create( final WJdbc jdbc, final AccessKey accessKey )
        throws SQLException
    {

        log.info( "Insertion : " + accessKey );

        final WStatement sta = jdbc.getWStatement();
        sta.add( "insert into " + AddonsConfig.WEL_ACCESSKEY + " (IDACCESSKEY,TAB,ACCESSKEY,LABEL,TYPE)" );
        sta.addParameter( "values (?,", accessKey.getIdAccessKey() );
        sta.addParameter( "?,", accessKey.getTab() );
        sta.addParameter( "?,", accessKey.getAccesskey() );
        sta.addParameter( "?,", accessKey.getLabel() );
        sta.addParameter( "?)", accessKey.getType() );

        sta.executeUpdate();
        sta.close();
    }

    /**
     * Mise a jour des profiles / droits d'accés
     * 
     * @param jdbc : connecition
     * @param arrayFromFile : liste dans le fichie excel
     * @param arrayFromBd : liste dans la bd
     * @throws SQLException : probleme SQL
     * @return nombre de champs modifiés
     */
    public int updateProfileAccessKey( final WJdbc jdbc, final ArrayList arrayFromFile, final ArrayList arrayFromBd )
        throws SQLException
    {

        int cpt = 0;
        final ArrayList noChange = new ArrayList();
        Iterator iter = arrayFromBd.iterator();

        while ( iter.hasNext() )
        {
            final ProfileAccessKey profileAccessKeyBd = (ProfileAccessKey) iter.next();
            if ( arrayFromFile.contains( profileAccessKeyBd ) )
            {
                noChange.add( profileAccessKeyBd );
            }
        }

        // Suppression des doubles
        arrayFromBd.removeAll( noChange );
        arrayFromFile.removeAll( noChange );

        iter = arrayFromBd.iterator();
        while ( iter.hasNext() )
        {
            final ProfileAccessKey profileAccessKeyBd = (ProfileAccessKey) iter.next();
            delete( jdbc, profileAccessKeyBd );
            cpt++;
        }

        iter = arrayFromFile.iterator();
        while ( iter.hasNext() )
        {
            final ProfileAccessKey profileAccessKeyFile = (ProfileAccessKey) iter.next();
            create( jdbc, profileAccessKeyFile );
            cpt++;
        }
        return cpt;

    }

    /**
     * Supprime un Profile
     * 
     * @param jdbc : connection
     * @param profileAccessKey : ProfilAccessKey
     * @throws SQLException : erreur SQL
     */
    public void delete( final WJdbc jdbc, final ProfileAccessKey profileAccessKey )
        throws SQLException
    {
        log.info( "Delete : " + profileAccessKey );

        final WStatement sta = jdbc.getWStatement();
        sta.add( "delete from " + AddonsConfig.WEL_PROFILE_ACCESSKEY_INT + " where " );
        sta.addParameter( "IDPROFILE=?", profileAccessKey.getIdProfile() );
        sta.addParameter( "and ACCESSKEY=?", profileAccessKey.getAccesskey() );

        sta.executeUpdate();
        sta.close();

    }

    /**
     * Insertion de Profile
     * 
     * @param jdbc : connection
     * @param profileAccessKey : ProfilAccessKey
     * @throws SQLException : erreur SQL
     */
    public void create( final WJdbc jdbc, final ProfileAccessKey profileAccessKey )
        throws SQLException
    {

        log.info( "Insertion : " + profileAccessKey );

        final WStatement sta = jdbc.getWStatement();
        sta.add( "insert into " + AddonsConfig.WEL_PROFILE_ACCESSKEY_INT + " (IDPROFILE,ACCESSKEY,VALUE)" );
        sta.add( "values (" );
        sta.addParameter( "?,", profileAccessKey.getIdProfile() );
        sta.addParameter( "?,", profileAccessKey.getAccesskey() );
        sta.addParameter( "?)", profileAccessKey.getValue() );

        sta.executeUpdate();
        sta.close();
    }

}
