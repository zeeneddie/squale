package com.airfrance.squaleweb.applicationlayer.action.results;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.result.SqualeReferenceDTO;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.reference.ReferenceForm;
import com.airfrance.squaleweb.applicationlayer.formbean.reference.ReferenceGridForm;
import com.airfrance.squaleweb.applicationlayer.formbean.reference.SetOfReferencesListForm;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.transformer.ReferenceTransformer;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;
import com.airfrance.welcom.struts.util.WConstants;

/**
 */
public class ReferenceListAction
    extends DefaultAction
{

    /**
     * Affichage des applications du référentiel
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward list( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                               HttpServletResponse pResponse )
    {

        // Initialisation
        ActionForward forward; // return value
        // ReferenceListForm renseigné ou non
        SetOfReferencesListForm form = (SetOfReferencesListForm) pForm;

        try
        {
            // Renseigne le form
            populateForm( form, pRequest );
            // met un nom de grille avec la date afin de distinguer les différentes mises à jour
            // setDisplayedGridName(pRequest, form);
            // Provoquer le forward
            forward = pMapping.findForward( "success" );
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );

        }
        // Finish with
        return forward;

    }

    /**
     * Peuplement du form avec les liste des références Le form passé en paramètre est mis à jour pour contenir les
     * objets à afficher dans la JSP Les applications de l'utilisateur sont affichées si celui-ci n'est pas
     * administrateur du portail
     * 
     * @param pForm form
     * @param pRequest requête
     * @throws JrafEnterpriseException exception
     * @throws WTransformerException exception
     */
    public void populateForm( SetOfReferencesListForm pForm, HttpServletRequest pRequest )
        throws WTransformerException, JrafEnterpriseException
    {
        List applicationsList = new ArrayList( 0 );
        // Récupération de la liste des applications
        // Toutes si on est admin, les publiques et celles dont l'utilisateur est gestionnaire sinon
        if ( isUserAdmin( pRequest ) )
        {
            applicationsList = getUserAdminApplicationListAsDTO( pRequest );
        }
        else
        {
            applicationsList = getUserApplicationListAsDTO( pRequest );
        }

        // On vérifie que l'on récupère que les applications qui sont effectivement stockées en base et non pas que
        // celles
        // qui ont des résultats (cas pouvant arriver dans des conditions particulières)
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Validation" );
        Collection referencedApplis = (Collection) ac.execute( "listReferentiel" );
        // les applications de l'utilisateur qui sont également référencés
        List commonApplis = new ArrayList( 0 );
        for ( int i = 0; i < applicationsList.size(); i++ )
        {
            if ( referencedApplis.contains( ( (ComponentDTO) applicationsList.get( i ) ).getName() ) )
            {
                commonApplis.add( ( (ComponentDTO) applicationsList.get( i ) ).getName() );
            }
        }

        // Comme le form est en session, on le remet à jour
        pForm = new SetOfReferencesListForm();
        // Récupère les références et les structures
        getReferences( commonApplis, isUserAdmin( pRequest ), pRequest, pForm );
        // Remet à jour le form en session
        pRequest.getSession().removeAttribute( "setOfReferencesListForm" );
        pRequest.getSession().setAttribute( "setOfReferencesListForm", pForm );

    }

    /**
     * Permet de récupérer tous les projets qui se trouvent dans le référentiel Les résultats renvoyés sont rendus
     * anonymes en fonction du privilège administrateur, des projets connus par l'utilisateur et du caractère public du
     * référentiel
     * 
     * @param pApplicationsList liste des applications de l'utilisateur
     * @param pAdmin indique un privilège administrateur
     * @param pRequest la requete http
     * @param pSetForm le form général
     * @throws JrafEnterpriseException exception
     * @throws WTransformerException exception
     */
    private void getReferences( List pApplicationsList, boolean pAdmin, HttpServletRequest pRequest,
                                SetOfReferencesListForm pSetForm )
        throws JrafEnterpriseException, WTransformerException
    {
        // Obtention des résultats
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
        LogonBean sessionUser = (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY );
        Object[] paramIn = { null, null, new Boolean( pAdmin ), new Long( sessionUser.getId() ) };
        List references = ( (List) ac.execute( "getReference", paramIn ) );
        SqualeReferenceDTO squaleReference = null;
        ReferenceForm refForm = null;
        Iterator referencesIterator = references.iterator();
        while ( referencesIterator.hasNext() )
        {
            squaleReference = (SqualeReferenceDTO) referencesIterator.next();
            // Conversion welcom
            Object[] obj = { squaleReference };
            refForm = (ReferenceForm) WTransformerFactory.objToForm( ReferenceTransformer.class, obj );
            // On rend les projets anonymes si on n'est pas admin, qu'on a pas les droits de lecture
            // et s'il ne sont pas publics
            if ( !pAdmin && !refForm.isPublic() && !pApplicationsList.contains( refForm.getApplicationName() ) )
            {
                anonymize( refForm, pRequest );
            }
            // Met à jour le caractère masqué ou affiché
            if ( refForm.isHidden() )
            {
                refForm.setState( WebMessages.getString( pRequest, ReferenceForm.HIDDEN ) );
            }
            else
            {
                refForm.setState( WebMessages.getString( pRequest, ReferenceForm.DISPLAYED ) );
            }
            // On convertit la grille qualité associée
            ReferenceGridForm gridForm = new ReferenceGridForm();
            gridForm.setName( squaleReference.getGrid().getName() );
            gridForm.setUpdateDate( squaleReference.getGrid().getUpdateDate() );
            gridForm.setFormattedDate( SqualeWebActionUtils.getFormattedDate(
                                                                              squaleReference.getGrid().getUpdateDate(),
                                                                              pRequest.getLocale() ) );
            pSetForm.add( gridForm, refForm );
        }
    }

    /**
     * Anonymisation du form de référence Les projets qui ne font pas partie des projets de l'utilisateur sont rendus
     * anonymes en effaçant le nom de l'application et du projet
     * 
     * @param pReferenceForm form de la référence
     * @param pRequest la requete http
     */
    private void anonymize( ReferenceForm pReferenceForm, HttpServletRequest pRequest )
    {
        // Effacement des informations
        String textAnonym = WebMessages.getString( pRequest, "reference.project.anonyme" );
        pReferenceForm.setApplicationName( textAnonym );
        String projectName = textAnonym;
        pReferenceForm.setName( projectName );
    }
}
