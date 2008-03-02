/*
 * Créé le 22 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.access.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.addons.config.AddonsConfig;
import com.airfrance.welcom.outils.jdbc.WJdbc;
import com.airfrance.welcom.outils.jdbc.WJdbcMagic;
import com.airfrance.welcom.outils.jdbc.WStatement;
import com.airfrance.welcom.outils.jdbc.wrapper.ResultSetUtils;
import com.airfrance.welcom.struts.bean.WActionForm;

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
