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
package com.airfrance.squaleweb.applicationlayer.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ProjectForm;
import com.airfrance.squaleweb.transformer.ApplicationTransformer;
import com.airfrance.squaleweb.transformer.LogonBeanTransformer;
import com.airfrance.squaleweb.transformer.ProjectTransformer;
import com.airfrance.squaleweb.transformer.UserTransformer;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;
import com.airfrance.welcom.struts.util.WConstants;

/**
 * Utilitaire pour les actions
 */
public class ActionUtils
{
    /**
     * Mise à jour du user en session Le user actuellement en session voit ses informations mises à jour après appel des
     * couches métier
     * 
     * @param pRequest requête
     * @throws JrafEnterpriseException si erreur
     * @throws WTransformerException si erreur
     */
    public static void refreshUser( HttpServletRequest pRequest )
        throws JrafEnterpriseException, WTransformerException
    {
        // On recharge les profils de l'utilisateur
        // On vérifie en base que le nom est disponible
        LogonBean sessionUser = (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY );
        UserDTO user = new UserDTO();
        user.setID( sessionUser.getId() );
        // Appel couche métier
        IApplicationComponent ac2 = AccessDelegateHelper.getInstance( "Login" );
        Object[] paramIn2 = { user, Boolean.valueOf( sessionUser.isAdmin() ) };
        user = (UserDTO) ac2.execute( "verifyUser", paramIn2 );
        // Placement dans la session des informations mises à jour
        if ( null != user )
        {
            boolean isAdmin = sessionUser.isAdmin();
            sessionUser = new LogonBean();
            WActionForm userForm = WTransformerFactory.objToForm( UserTransformer.class, user );
            WTransformerFactory.formToObj( LogonBeanTransformer.class, userForm, new Object[] { sessionUser,
                Boolean.valueOf( isAdmin ) } );
            pRequest.getSession().setAttribute( WConstants.USER_KEY, sessionUser );
        }
    }

    /**
     * Retourne l'application recherchée et stockée dans la liste.
     * 
     * @param pId l'id de l'application.
     * @param pApplications la liste des applications.
     * @return le nom de l'application.
     */
    public static ApplicationForm getComponent( long pId, final Collection pApplications )
    {
        ApplicationForm application = null;
        if ( null != pApplications )
        {
            // Parcours de chaque application
            Iterator it = pApplications.iterator();
            while ( it.hasNext() && null == application )
            {
                application = (ApplicationForm) it.next();
                // Le test se fait sur l'id de l'application
                if ( application.getId() != pId )
                {
                    application = null;
                }
            }
        }
        return application;
    }

    /**
     * Permet la récupération des audits en session de la requête paramètre.
     * 
     * @param pRequest la requête HTTP.
     * @return une liste des audits, ou null si il n'y en a pas.
     * @throws WTransformerException si un pb de transformation apparait.
     */
    public static List getCurrentAuditsAsDTO( final HttpServletRequest pRequest )
        throws WTransformerException
    {
        List audits = null;
        AuditDTO currentAudit = (AuditDTO) pRequest.getSession().getAttribute( BaseDispatchAction.CURRENT_AUDIT_DTO );
        AuditDTO previousAudit = (AuditDTO) pRequest.getSession().getAttribute( BaseDispatchAction.PREVIOUS_AUDIT_DTO );
        // On ajoute les audits que si ils ne sont pas nuls car sinon on a une erreur de taille
        // car ajouter un élément nul incréménte la taille
        if ( currentAudit != null )
        {
            audits = new ArrayList( 0 );
            audits.add( currentAudit );
            // De plus on ajoute (fort logiquement) le previous que si le courant existe
            if ( previousAudit != null )
            {
                audits.add( previousAudit );
            }
        }
        return audits;
    }

    /**
     * @param pRequest la requête
     * @return l'application courante en tant que form
     * @throws WTransformerException si un pb de transformation apparait.
     */
    public static ApplicationForm getCurrentApplication( final HttpServletRequest pRequest )
        throws WTransformerException
    {
        ApplicationForm application = null;
        ComponentDTO appli = (ComponentDTO) pRequest.getSession().getAttribute( BaseDispatchAction.APPLI_DTO );
        application =
            (ApplicationForm) WTransformerFactory.objToForm( ApplicationTransformer.class, new Object[] { appli } );
        return application;
    }

    /**
     * @param pRequest la requête HTTP.
     * @return le projet, ou null.
     * @throws WTransformerException si un pb de transformation apparait.
     */
    public static ProjectForm getCurrentProject( final HttpServletRequest pRequest )
        throws WTransformerException
    {
        ProjectForm result = null;
        ComponentDTO project = (ComponentDTO) pRequest.getSession().getAttribute( BaseDispatchAction.PROJECT_DTO );
        if ( project != null )
        {
            result = (ProjectForm) WTransformerFactory.objToForm( ProjectTransformer.class, new Object[] { project } );
        }
        return result;
    }

}
