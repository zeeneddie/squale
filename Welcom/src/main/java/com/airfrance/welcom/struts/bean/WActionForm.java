/*
 * Créé le 26 mars 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.bean;

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

import com.airfrance.welcom.struts.util.ServletUtils;
import com.airfrance.welcom.struts.util.WConstants;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WActionForm
    extends ActionForm
{

    /**
     * 
     */
    private static final long serialVersionUID = -208874349319343398L;

    /** le userName */
    private String userName = "";

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

    /** logger */
    private final static Log logger = LogFactory.getLog( ActionForm.class );

    /** Retourne si on a été initialisé */
    private boolean initialized = false;

    /**
     * @deprecated
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    public final ActionErrors validate( final ActionMapping mapping, final HttpServletRequest request )
    {
        // ReTourne comme quoi on a étét initalisé
        initialized = true;

        // si on utilise le WRequestProcessor, c'est lui qui reconcatene date et heure
        if ( !mapping.getModuleConfig().getControllerConfig().getProcessorClass().equals(
                                                                                          "com.airfrance.welcom.struts.action.WRequestProcessor" ) )
        {
            // Search Date/Heure
            ServletUtils.welcomPersistanceUtil( this, request );
        }
        // sauve la request ...*/
        this.request = request;

        // Initilise les erreurs;
        errors = new ActionErrors();

        // remet à blanc le resultAction
        resultAction = "";

        // Recupere la locale de la page
        localeRequest = (Locale) request.getSession().getAttribute( Globals.LOCALE_KEY );

        // Recupere le user
        if ( ( ( request.getSession().getAttribute( WConstants.USER_KEY ) ) != null )
            && ( request.getSession().getAttribute( WConstants.USER_KEY ) instanceof WILogonBean ) )
        {
            iLogonBean = ( (WILogonBean) ( request.getSession().getAttribute( WConstants.USER_KEY ) ) );

            if ( GenericValidator.isBlankOrNull( userName ) )
            {
                userName = iLogonBean.getUserName();
            }
        }

        // Recuperer le fichier des Bundle
        resources = (MessageResources) servlet.getServletContext().getAttribute( Globals.MESSAGES_KEY );

        wValidate( mapping, request );

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
     * @return le userName
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * @deprecated sur getResources
     * @return le MessageRessources
     */
    protected MessageResources getMessageRessources()
    {
        checkInitialized();
        return resources;
    }

    /**
     * Methode Validate de Welcom avec Support des addError()
     * 
     * @param mapping l'actionMapping
     * @param request la request
     */
    public void wValidate( final ActionMapping mapping, final HttpServletRequest request )
    {
        // return null;
    }

    /**
     * @return la locale
     */
    protected Locale getLocale()
    {
        checkInitialized();
        return localeRequest;
    }

    /**
     * @return le iLogonBean
     */
    protected WILogonBean getWILogonBean()
    {
        checkInitialized();
        return iLogonBean;
    }

    /**
     * @return le messageRessource
     */
    protected MessageResources getResources()
    {
        checkInitialized();
        return resources;
    }

    /**
     * @return les erreurs
     */
    protected ActionErrors getErrors()
    {
        checkInitialized();
        return errors;
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
        if ( checkInitialized() )
        {
            WResultAction.saveMessage( request, pResultAction );
        }
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
        if ( checkInitialized() )
        {
            errors.add( property, new ActionError( "welcom.internal.error.info" ) );

            String msg = resources.getMessage( localeRequest, actionErrorKey, valuesArg );

            if ( GenericValidator.isBlankOrNull( msg ) )
            {
                msg = actionErrorKey;
            }

            addResultAction( msg + "\\n" );

        }

    }

    /**
     * adding du resultAction
     * 
     * @param str la chaine à ajouter
     */
    public void addResultAction( final String str )
    {
        if ( checkInitialized() )
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

    /**
     * Trace si on a pas ete initialise par la methode validate et retourne la valeur
     * 
     * @return si on a ete initilisé
     */
    private boolean checkInitialized()
    {
        if ( initialized == false )
        {

            logger.fatal( "Vous devez appeler la methode Validate avant d'appeler cette methode" );
        }
        return initialized;
    }
}