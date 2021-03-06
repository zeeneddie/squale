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
package org.squale.squaleweb.applicationlayer.action.rulechecking;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squalecommon.datatransfertobject.rulechecking.RuleSetDTO;
import org.squale.squaleweb.applicationlayer.action.accessRights.AdminAction;
import org.squale.squaleweb.resources.WebMessages;
import org.squale.squaleweb.util.SqualeWebActionUtils;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * Action abstraite sur les rulesets Les actions sur les rulesets permettent de lister ceux-ci ou de purger certains
 * d'entre eux. Cette classe factorise le comportement identique sur ces aspects
 */
public abstract class AbstractRuleSetAction
    extends AdminAction
{
    /**
     * Obtention du transformer de liste de ruleset
     * 
     * @return transformer welcom pour la liste des rulesets
     */
    protected abstract Class getRuleSetListTransformer();

    /**
     * Obtention du nom de l'access component
     * 
     * @return nom de l'access component li� � l'action
     */
    protected abstract String getAccessComponentName();

    /**
     * Affichage de la liste des configurations checkstyle
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire � lire.
     * @param pRequest la requ�te HTTP.
     * @param pResponse la r�ponse de la servlet.
     * @return l'action � r�aliser.
     */
    public ActionForward list( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                               HttpServletResponse pResponse )
    {

        ActionErrors errors = new ActionErrors();
        ActionForward forward = pMapping.findForward( "total_failure" );
        try
        {
            IApplicationComponent ac = AccessDelegateHelper.getInstance( getAccessComponentName() );
            Collection rulesets = (Collection) ac.execute( "getAllConfigurations" );
            WTransformerFactory.objToForm( getRuleSetListTransformer(), (WActionForm) pForm, rulesets );
            forward = pMapping.findForward( "list" );
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            saveMessages( pRequest, errors );
        }
        // On est pass� par un menu donc on r�initialise le traceur
        resetTracker( pRequest );
        return ( forward );
    }

    /**
     * Purge des jeux de r�gles inutilis�s
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire � lire.
     * @param pRequest la requ�te HTTP.
     * @param pResponse la r�ponse de la servlet.
     * @return l'action � r�aliser.
     */
    public ActionForward purge( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                HttpServletResponse pResponse )
    {

        ActionErrors errors = new ActionErrors();
        ActionForward forward = pMapping.findForward( "total_failure" );
        try
        {
            // Transformation en DTO de chaque jeu de r�gles s�lectionn�
            Collection ruleSets =
                (Collection) WTransformerFactory.formToObj( getRuleSetListTransformer(), (WActionForm) pForm )[0];
            if ( ruleSets.isEmpty() )
            {
                // Affichage d'une erreur sur le message
                ActionMessage error = new ActionError( "error.invalidSelection" );
                errors.add( ActionErrors.GLOBAL_ERROR, error );
            }
            else
            {
                IApplicationComponent ac = AccessDelegateHelper.getInstance( getAccessComponentName() );
                Collection usedRuleSets = (Collection) ac.execute( "deleteRuleSets", new Object[] { ruleSets } );
                String confirmation = "";
                // On construit le message de confirmation
                if ( usedRuleSets.size() > 0 )
                {
                    ActionMessage error =
                        new ActionError( "rulechecking.rulesetused", buildListStr( pRequest.getLocale(), usedRuleSets ) );
                    errors.add( ActionErrors.GLOBAL_ERROR, error );
                }
                // On r�cup�re les rulesets supprim�s
                ruleSets.removeAll( usedRuleSets );
                if ( ruleSets.size() > 0 )
                {
                    ActionMessage error =
                        new ActionError( "rulechecking.rulesetdeleted", buildListStr( pRequest.getLocale(), ruleSets ) );
                    errors.add( ActionErrors.GLOBAL_ERROR, error );
                }
                // On remet � jour la liste des jeux de r�gles
                Collection newRuleSets = (Collection) ac.execute( "getAllConfigurations" );
                WTransformerFactory.objToForm( getRuleSetListTransformer(), (WActionForm) pForm, newRuleSets );
            }
            forward = pMapping.findForward( "list" );
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            saveMessages( pRequest, errors );
        }
        // On est pass� par un menu donc on r�initialise le traceur
        resetTracker( pRequest );
        return ( forward );
    }

    /**
     * @param pLocale la locale
     * @param pRuleSets les items
     * @return le paragraphe � afficher dans le message de confirmation
     */
    private String buildListStr( Locale pLocale, Collection pRuleSets )
    {
        String message = "<ul>";
        for ( Iterator it = pRuleSets.iterator(); it.hasNext(); )
        {
            RuleSetDTO dto = (RuleSetDTO) it.next();
            String formattedDate =
                SqualeWebActionUtils.getFormattedDate( pLocale, dto.getDateOfUpdate(), "datetime.format.simple" );
            message +=
                "<li>"
                    + WebMessages.getString( pLocale, "rulechecking.confirmation.item", new String[] { dto.getName(),
                        formattedDate } );
        }
        message += "</ul>";
        return message;
    }

}
