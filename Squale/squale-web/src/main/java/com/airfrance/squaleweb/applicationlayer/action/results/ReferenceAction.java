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
package com.airfrance.squaleweb.applicationlayer.action.results;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.result.SqualeReferenceDTO;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.AdminAction;
import com.airfrance.squaleweb.applicationlayer.formbean.reference.ReferenceForm;
import com.airfrance.squaleweb.applicationlayer.formbean.reference.ReferenceGridForm;
import com.airfrance.squaleweb.applicationlayer.formbean.reference.ReferenceListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.reference.SetOfReferencesListForm;
import com.airfrance.squaleweb.transformer.ReferenceTransformer;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Actions sur le référentiel Les actions sur le référentiel permettent de consulter les facteurs et les métriques de
 * certaines applications mises en référence
 */
public class ReferenceAction
    extends AdminAction
{

    /**
     * Ajout ou retrait de projets du référentiel Les projets sélectionnés sont ajoutés ou retirés du référentiel, puis
     * les données affichées sont mise à jour
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward updateReferentiel( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                            HttpServletResponse pResponse )
    {
        // Initialisation
        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;
        ArrayList listOfReferences = new ArrayList();

        try
        {
            // On construit la liste des applications à ajouter/supprimer
            SetOfReferencesListForm referenceListForm = (SetOfReferencesListForm) pForm;
            // Parcours des references grille par grille
            for ( int i = 0; i < referenceListForm.getList().size(); i++ )
            {
                Iterator refsIt =
                    ( (ReferenceListForm) ( (ReferenceGridForm) referenceListForm.getList().get( i ) ).getReferenceListForm() ).getList().iterator();
                // Parcours de chaque reference dans une grille
                while ( refsIt.hasNext() )
                {
                    ReferenceForm refForm = (ReferenceForm) refsIt.next();
                    // Ajout des références sélectionnées
                    if ( refForm.isSelected() )
                    {
                        SqualeReferenceDTO refDTO =
                            (SqualeReferenceDTO) WTransformerFactory.formToObj( ReferenceTransformer.class, refForm )[0];
                        listOfReferences.add( refDTO );
                    }
                }
            }
            Object[] args = { listOfReferences };
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Validation" );
            Integer result = (Integer) ac.execute( "updateReferentiel", args );
            // On construit le message de confirmation
            ActionMessage message = new ActionMessage( "reference.updated", "", "" );
            errors.add( ActionMessages.GLOBAL_MESSAGE, message );
            // Mise à jour des données affichées
            // le forward renvoie vers l'action qui liste
            forward = pMapping.findForward( "success" );

            // Sauvegarde des messages pour la confirmation dans la réponse
            saveMessages( pRequest, errors );
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions et transfert vers la page d'erreur
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        return ( forward );
    }

}
