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
package org.squale.welcom.addons.access.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.squale.welcom.addons.config.AddonsConfig;
import org.squale.welcom.outils.jdbc.WJdbc;
import org.squale.welcom.outils.jdbc.WJdbcMagic;
import org.squale.welcom.outils.jdbc.WStatement;
import org.squale.welcom.outils.jdbc.wrapper.ResultSetUtils;
import org.squale.welcom.struts.bean.WActionForm;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ProfileBean
    extends WActionForm
    implements Serializable
{

    /** ID pour la serialization */
    static final long serialVersionUID = -5713952017034438217L;

    /** logger */
    private static Log log = LogFactory.getLog( ProfileBean.class );

    /** Id du profil */
    private String idProfile = "";

    /** Name */
    private String name = "";

    /** Liste des droits d'accés */
    private ArrayList accessList = new ArrayList();

    /**
     * @return Name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param string Name
     */
    public void setName( final String string )
    {
        name = string;
    }

    /**
     * @return IdProfile
     */
    public String getIdProfile()
    {
        return idProfile;
    }

    /**
     * @param string IdProfile
     */
    public void setIdProfile( final String string )
    {
        idProfile = string;
    }

    /**
     * @return AccessList
     */
    public ArrayList getAccessList()
    {
        return accessList;
    }

    /**
     * reload recharge a partir de la BD
     */
    public void reloadAccessList()
    {
        try
        {
            final ProfileBean p = ProfileBean.getProfileBean( this.getIdProfile() );
            accessList = p.getAccessList();
        }
        catch ( final ServletException e )
        {
            log.error( e, e );
        }
    }

    /**
     * @param list AccessList
     */
    public void setAccessList( final ArrayList list )
    {
        accessList = list;
    }

    /**
     * Remize a Zero
     */
    public void setNull()
    {
        idProfile = "";
        name = "";
        accessList = new ArrayList();
    }

    /**
     * Créé un Profile en fonction de idProfile
     * 
     * @param pIdProfil : Probile
     * @throws ServletException : Probleme SQL
     * @return le bean profil
     */
    public static ProfileBean getProfileBean( final String pIdProfil )
        throws ServletException
    {

        final ProfileBean profileBean = new ProfileBean();
        profileBean.setIdProfile( pIdProfil );
        getProfileBean( profileBean );
        return profileBean;
    }

    /**
     * Met a jour un Profile en fonction de idProfile
     * 
     * @param profil : Profile
     * @throws ServletException : Probleme SQL
     */
    public static void getProfileBean( final ProfileBean profil )
        throws ServletException
    {
        WStatement sta = null;
        java.sql.ResultSet rs = null;
        WJdbc jdbc = null;
        try
        {
            jdbc = new WJdbcMagic();

            if ( !GenericValidator.isBlankOrNull( profil.getIdProfile() ) )
            {
                // Chargement du profil
                sta = jdbc.getWStatement();
                sta.add( "select * from" );
                sta.add( AddonsConfig.WEL_PROFILE );
                sta.addParameter( "where idprofile=?", profil.getIdProfile() );
                rs = sta.executeQuery();
                if ( ( rs != null ) && rs.next() )
                {
                    ResultSetUtils.populate( profil, rs );
                }
                else
                {
                    new ServletException( "idProfile not found" );
                }
                sta.close();

                // Liste de reference
                final ArrayList listAccessInt = new ArrayList();
                final HashMap hashAccessInt = new HashMap();

                sta = jdbc.getWStatement();
                sta.add( "select * from " );
                sta.add( AddonsConfig.WEL_PROFILE_ACCESSKEY_INT + " A," );
                sta.add( AddonsConfig.WEL_ACCESSKEY + " B where" );
                sta.add( "A.accesskey=B.accesskey and " );
                sta.addParameter( "A.idprofile=?", profil.getIdProfile() );
                sta.add( "order by B.idAccessKey " );
                rs = sta.executeQuery();
                if ( rs != null )
                {
                    while ( rs.next() )
                    {
                        final AccessBean droits = new AccessBean();
                        ResultSetUtils.populate( droits, rs );
                        hashAccessInt.put( droits.getAccesskey(), droits );
                        listAccessInt.add( droits );
                    }
                }
                sta.close();

                // Recupere son profil modifié via l'IHM de l'appli
                sta = jdbc.getWStatement();
                sta.add( "select * from " );
                sta.add( AddonsConfig.WEL_PROFILE_ACCESSKEY );
                sta.add( "where" );
                sta.addParameter( "idprofile=?", profil.getIdProfile() );
                rs = sta.executeQuery();
                if ( rs != null )
                {
                    while ( rs.next() )
                    {
                        final AccessBean droits = new AccessBean();
                        ResultSetUtils.populate( droits, rs );
                        if ( hashAccessInt.containsKey( droits.getAccesskey() ) )
                        {
                            final AccessBean dr = (AccessBean) hashAccessInt.get( droits.getAccesskey() );
                            dr.setValue( droits.getValue() );
                        }
                    }
                }
                profil.setAccessList( listAccessInt );
                sta.close();
            }

        }
        catch ( final SQLException e )
        {
            throw new ServletException( e.getMessage() );
        }
        finally
        {
            if ( jdbc != null )
            {
                jdbc.close();
            }
        }

    }

}
