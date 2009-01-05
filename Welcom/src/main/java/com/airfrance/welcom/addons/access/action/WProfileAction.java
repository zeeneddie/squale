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
package com.airfrance.welcom.addons.access.action;

import java.sql.SQLException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.airfrance.welcom.addons.access.bean.AccessBean;
import com.airfrance.welcom.addons.access.bean.ProfileBean;
import com.airfrance.welcom.addons.access.bean.WLogonBeanAddOnAccessManager;
import com.airfrance.welcom.addons.config.AddonsConfig;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.jdbc.WJdbc;
import com.airfrance.welcom.outils.jdbc.WJdbcMagic;
import com.airfrance.welcom.outils.jdbc.WStatement;
import com.airfrance.welcom.struts.action.WDispatchAction;
import com.airfrance.welcom.struts.util.WConstants;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WProfileAction
    extends WDispatchAction
{
    /** logger */
    private static Log log = LogFactory.getLog( WProfileAction.class );

    /**
     * Affiche la liste de profil d'une personne
     * 
     * @see org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward unspecified( final ActionMapping mapping, final ActionForm form,
                                      final HttpServletRequest request, final HttpServletResponse response )
        throws Exception
    {

        final ProfileBean profil = (ProfileBean) form;

        ProfileBean.getProfileBean( profil );

        return mapping.findForward( "success" );
    }

    /**
     * Modifier les profils
     * 
     * @see WDispatchAction
     */
    public ActionForward modifier( final ActionMapping mapping, final ActionForm form,
                                   final HttpServletRequest request, final HttpServletResponse response )
        throws Exception
    {

        // Recuperation du logon bean
        final Object o = request.getSession().getAttribute( WConstants.USER_KEY );
        if ( ( o == null ) || !( o instanceof WLogonBeanAddOnAccessManager ) )
        {
            throw new ServletException( "Le bean USER_KEY doit etre du type WLogonBeanAddOnAccessManagement" );
        }

        final WLogonBeanAddOnAccessManager logonBean = (WLogonBeanAddOnAccessManager) o;
        final ProfileBean profil = (ProfileBean) form;
        WJdbc jdbc = null;

        try
        {
            jdbc = new WJdbcMagic();
            WStatement sta = null;

            // Recherche les elements modifiés et met a jour uniquement ceux la.
            for ( final Iterator iterator = profil.getAccessList().iterator(); iterator.hasNext(); )
            {

                final AccessBean droit = (AccessBean) iterator.next();

                if ( ( droit.getValueNew() != null ) && Util.isNonEquals( droit.getValue(), droit.getValueNew() ) )
                {
                    sta = jdbc.getWStatement();
                    sta.add( "delete from " + AddonsConfig.WEL_PROFILE_ACCESSKEY + " where" );
                    sta.addParameter( "IDPROFILE=?", profil.getIdProfile() );
                    sta.addParameter( "and ACCESSKEY=?", droit.getAccesskey() );
                    sta.executeUpdate();
                    sta.close();

                    sta = jdbc.getWStatement();
                    sta.add( "insert into " + AddonsConfig.WEL_PROFILE_ACCESSKEY
                        + " (IDPROFILE,ACCESSKEY,VALUE,USERNAME,DATES) values (" );
                    sta.addParameter( "?,", profil.getIdProfile() );
                    sta.addParameter( "?,", droit.getAccesskey() );
                    sta.addParameter( "?,", droit.getValueNew() );
                    sta.addParameter( "?,", logonBean.getUserName() );
                    sta.add( sta.now() + ")" );
                    sta.executeUpdate();
                    sta.close();
                }
            }

            // affiche un popup de confirmation
            setMessagePopup( request, "welcom.addons.access.profile.msg.ok" );

            // Commit
            jdbc.commit();
        }
        catch ( final SQLException e )
        {
            final ActionErrors errors = new ActionErrors();
            errors.add( Globals.ERROR_KEY, new ActionError( e.getMessage() ) );
            saveErrors( request, errors );
            return mapping.findForward( "error" );
        }
        finally
        {
            if ( jdbc != null )
            {
                jdbc.close();
            }
        }

        log.debug( "Profile en cours :" + profil.getIdProfile() );
        log.debug( "Mon profile:" + ( (ProfileBean) logonBean.getProfiles().get( 0 ) ).getIdProfile() );

        // Met a jour la liste de profil si elle est touchée. On suppose qu'on a un seul profil
        if ( Util.isEquals( profil.getIdProfile(), ( (ProfileBean) logonBean.getProfiles().get( 0 ) ).getIdProfile() ) )
        {
            logonBean.reloadAccess();
            log.debug( "reload" );
        }

        return unspecified( mapping, form, request, response );
    }

}
