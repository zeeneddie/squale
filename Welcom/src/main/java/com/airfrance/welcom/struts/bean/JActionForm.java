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
 * Créé le 26 mars 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.bean;

import java.sql.SQLException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.jdbc.WJdbc;
import com.airfrance.welcom.outils.jdbc.WMJdbc;
import com.airfrance.welcom.struts.util.ServletUtils;
import com.airfrance.welcom.struts.util.WConstants;

/**
 * Class WForm
 */
public class JActionForm
    extends ActionForm
{
    /**
     * 
     */
    private static final long serialVersionUID = -7142581724684808252L;

    /** logger */
    private static Log log = LogFactory.getLog( JActionForm.class );

    /** le userName */
    private String userName = "";

    /** jdbc */
    private WJdbc jdbc = null;

    /** Le message ressource */
    private MessageResources resources = null;

    /** l'actionError */
    private ActionErrors errors = null;

    /** le WILogonBean */
    private WILogonBean iLogonBean = null;

    /** la locale */
    private Locale localeRequest = null;

    /** le resultAction */
    private String resultAction = "";

    /** la request ... j'ai le droit car la classe est instancié plusieur fois */
    private HttpServletRequest request;

    /**
     * @deprecated Utiliser wValidate().
     * @param mapping l'actionMapping
     * @param pRequest la request
     * @param pJdbc le WJdbc
     */
    public final void validate( final ActionMapping mapping, final HttpServletRequest pRequest, final WJdbc pJdbc )
    {
    }

    /**
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    public final ActionErrors validate( final ActionMapping mapping, final HttpServletRequest pRequest )
    {
        // si on utilise le WRequestProcessor, c'est lui qui reconcatene date et heure
        if ( !mapping.getModuleConfig().getControllerConfig().getProcessorClass().equals(
                                                                                          "com.airfrance.welcom.struts.action.WRequestProcessor" ) )
        {
            // Search Date/Heure
            ServletUtils.welcomPersistanceUtil( this, request );
        }
        // sauve la request ...*/
        this.request = pRequest;

        // Initilise les erreurs;
        errors = new ActionErrors();

        // remet à blanc le resultAction
        resultAction = "";

        // Recupere la locale de la page
        localeRequest = (Locale) request.getSession().getAttribute( Globals.LOCALE_KEY );

        // Recupere le user
        if ( ( request.getSession().getAttribute( WConstants.USER_KEY ) ) != null )
        {
            iLogonBean = ( (WILogonBean) ( request.getSession().getAttribute( WConstants.USER_KEY ) ) );

            if ( GenericValidator.isBlankOrNull( userName ) )
            {
                userName = iLogonBean.getUserName();
            }
        }

        // Recuperer le fichier des Bundle
        resources = (MessageResources) servlet.getServletContext().getAttribute( Globals.MESSAGES_KEY );

        final String useMode = ( (String) servlet.getServletContext().getAttribute( WConstants.WELCOM_USE_MODE ) );

        if ( Util.isEquals( useMode, WConstants.MODE_JDBC ) || Util.isEquals( useMode, WConstants.MODE_MJDBC ) )
        {
            // Teste si l'on utilise l'avec jdbc embarqué
            try
            {
                // Initialise la connection jdbc
                if ( Util.isEquals( useMode, WConstants.MODE_JDBC ) )
                {
                    jdbc = new WJdbc( getUserName() );
                }
                else
                {
                    // appel de la fonction pour connaitre quel JDBC a utiliser
                    final String jdbcName = dispatchJdbc();
                    if ( jdbcName == null )
                    {
                        dispatchJdbc( request );
                    }
                    jdbc = new WMJdbc( getUserName(), jdbcName );
                }

                // Appel de l'ancien validate pour compatibilité
                final ActionErrors ae = validateOld( mapping, request, jdbc );

                if ( ae != null )
                {
                    errors.add( ae );
                }

                wValidate( mapping, request, jdbc );
            }
            catch ( final SQLException ex )
            {
                log.error( "2009-critical-Database--" + ex.getMessage() );
            }
            finally
            {
                // Fermeture de tout ce qui a été ouvert en connection JDBC
                if ( jdbc != null )
                {
                    jdbc.close();
                }

                jdbc = null;

                // Runtime.getRuntime().gc();
            }
        }
        else
        {
            wValidate( mapping, request );
        }

        if ( !GenericValidator.isBlankOrNull( resultAction ) )
        {
            String message =
                resources.getMessage( localeRequest, "welcom.internal.error.resultaction.error" ) + "\\n\\n"
                    + resultAction + "\\n"
                    + resources.getMessage( localeRequest, "welcom.internal.error.resultaction.explanation" );

            WResultAction.saveMessage( request, message );
        }

        return errors;
    }

    /**
     * @deprecated Fonction permettant de definir le quel jdbc on desire utiliser en retournant le nom definit dans le
     *             struts-config
     * @return : le nom du dataSource a Utiliser
     */
    public String dispatchJdbc()
    {
        return null;
    }

    /**
     * Fonction permettant de definir le quel jdbc on desire utiliser en retournant le nom definit dans le struts-config
     * 
     * @param pRequest : Requete Http
     * @return : le nom du dataSource a Utiliser
     */
    public String dispatchJdbc( final HttpServletRequest pRequest )
    {
        return null;
    }

    /**
     * @return le userName
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * @return le MessageRessources
     */
    protected MessageResources getMessageRessources()
    {
        return resources;
    }

    /**
     * Methode Validate de Welcom avec Support des addError()
     * 
     * @param mapping l'actionMapping
     * @param pRequest la request
     */
    public void wValidate( final ActionMapping mapping, final HttpServletRequest pRequest )
    {
        // return null;
    }

    /**
     * Methode Validate de Welcom avec Support des addError()
     * 
     * @param mapping l'actionMapping
     * @param pRequest la request
     * @param pJdbc le WJdbc
     * @throws SQLException exception
     */
    public void wValidate( final ActionMapping mapping, final HttpServletRequest pRequest, final WJdbc pJdbc )
        throws SQLException
    {
        // return null;
    }

    /**
     * Methode Validate de struts
     * 
     * @deprecated
     * @param mapping l'actionMapping
     * @param pRequest la request
     * @param pJdbc le WJdbc
     * @return les erreurs
     * @throws SQLException exception
     */
    public ActionErrors validateOld( final ActionMapping mapping, final HttpServletRequest pRequest, final WJdbc pJdbc )
        throws SQLException
    {
        return null;
    }

    /**
     * @return la locale
     */
    protected Locale getLocale()
    {
        return localeRequest;
    }

    /**
     * @return le iLogonBean
     */
    protected WILogonBean getWILogonBean()
    {
        return iLogonBean;
    }

    /**
     * @return le messageRessource
     */
    protected MessageResources getResources()
    {
        return resources;
    }

    /**
     * @return les erreurs
     */
    protected ActionErrors getErrors()
    {
        return errors;
    }

    /**
     * @deprecated
     * @return le jdbc
     */
    protected WJdbc getJdbc()
    {
        return jdbc;
    }

    /**
     * @param pErrors les erreurs
     */
    protected void setErrors( final ActionErrors pErrors )
    {
        errors = pErrors;
    }

    /**
     * setting du resultAction
     * 
     * @param pResultAction le resultAction
     */
    public void setResultAction( final String pResultAction )
    {
        WResultAction.saveMessage( request, pResultAction );
    }

    /**
     * ajout d'une erreur lors de la validation des attributs spécifiques ...
     * 
     * @param property la property de l'attribut
     * @param actionError l'erreur message à afficher dans la popup
     */
    public void addError( final String property, final ActionError actionError )
    {
        addError( property, actionError.getKey(), actionError.getValues() );
    }

    /**
     * ajout d'une erreur lors de la validation des attributs spécifiques ...
     * 
     * @param property la property de l'attribut
     * @param actionErrorKey la key de l'erreur à afficher dans la popup
     */
    public void addError( final String property, final String actionErrorKey )
    {
        addError( property, actionErrorKey, (Object[]) null );
    }

    /**
     * ajout d'une erreur lors de la validation des attributs spécifiques ...
     * 
     * @param property la property de l'attribut
     * @param actionErrorKey le message à afficher dans la popup
     * @param arg0 parametre {0}
     */
    public void addError( final String property, final String actionErrorKey, final String arg0 )
    {
        final Object o[] = new Object[1];
        o[0] = arg0;
        addError( property, actionErrorKey, o );
    }

    /**
     * ajout d'une erreur lors de la validation des attributs spécifiques ...
     * 
     * @param property la property de l'attribut
     * @param actionErrorKey le message à afficher dans la popup
     * @param arg0 parametre {0}
     * @param arg1 parametre {1}
     */
    public void addError( final String property, final String actionErrorKey, final String arg0, final String arg1 )
    {
        final Object o[] = new Object[2];
        o[0] = arg0;
        o[1] = arg1;
        addError( property, actionErrorKey, o );
    }

    /**
     * ajout d'une erreur lors de la validation des attributs spécifiques ...
     * 
     * @param property la property de l'attribut
     * @param actionErrorKey le message à afficher dans la popup
     * @param arg0 parametre {0}
     * @param arg1 parametre {1}
     * @param arg2 parametre {2}
     */
    public void addError( final String property, final String actionErrorKey, final String arg0, final String arg1,
                          final String arg2 )
    {
        final Object o[] = { arg0, arg1, arg2 };
        addError( property, actionErrorKey, o );
    }

    /**
     * ajout d'une erreur lors de la validation des attributs spécifiques ...
     * 
     * @param property la property de l'attribut
     * @param actionErrorKey le message à afficher dans la popup
     * @param arg0 parametre {0}
     * @param arg1 parametre {1}
     * @param arg2 parametre {2}
     * @param arg3 parametre {3}
     */
    public void addError( final String property, final String actionErrorKey, final String arg0, final String arg1,
                          final String arg2, final String arg3 )
    {
        final Object o[] = { arg0, arg1, arg2, arg3 };
        addError( property, actionErrorKey, o );
    }

    /**
     * ajout d'une erreur lors de la validation des attributs spécifiques ...
     * 
     * @param property la property de l'attribut
     * @param actionErrorKey le message à afficher dans la popup
     * @param arg0 parametre {0}
     * @param arg1 parametre {1}
     * @param arg2 parametre {2}
     * @param arg3 parametre {3}
     * @param arg4 parametre {4}
     */
    public void addError( final String property, final String actionErrorKey, final String arg0, final String arg1,
                          final String arg2, final String arg3, final String arg4 )
    {
        final Object o[] = { arg0, arg1, arg2, arg3, arg4 };
        addError( property, actionErrorKey, o );
    }

    /**
     * ajout d'une erreur lors de la validation des attributs spécifiques ...
     * 
     * @param property la property de l'attribut
     * @param actionErrorKey le message à afficher dans la popup
     * @param valuesArg le tableau remplacant les parametres
     */
    public void addError( final String property, final String actionErrorKey, final Object valuesArg[] )
    {
        errors.add( property, new ActionError( "welcom.internal.error.info" ) );

        String msg = resources.getMessage( localeRequest, actionErrorKey, valuesArg );

        if ( GenericValidator.isBlankOrNull( msg ) )
        {
            msg = actionErrorKey;
        }

        addResultAction( msg + "\\n" );
    }

    /**
     * adding du resultAction
     * 
     * @param str la chaine à ajouter
     */
    public void addResultAction( final String str )
    {
        if ( resultAction == null )
        {
            resultAction = " - " + str;
        }
        else
        {
            resultAction += ( " - " + str );
        }
    }
}