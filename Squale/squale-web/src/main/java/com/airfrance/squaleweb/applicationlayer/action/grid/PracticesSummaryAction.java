package com.airfrance.squaleweb.applicationlayer.action.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.PracticeRuleDTO;
import com.airfrance.squaleweb.applicationlayer.action.ActionUtils;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.ReaderAction;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ResultForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ResultListForm;
import com.airfrance.squaleweb.applicationlayer.tracker.TrackerStructure;
import com.airfrance.squaleweb.comparator.ResultFormComparator;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.transformer.ProjectTransformer;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * 
 */
public class PracticesSummaryAction
    extends ReaderAction
{

    /**
     * Importation d'un fichier de grille
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward meteo( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                HttpServletResponse pResponse )
    {
        ActionErrors errors = new ActionErrors();
        ActionForward forward = pMapping.findForward( "noPracticesDefined" );
        ArrayList list = new ArrayList( 0 );

        try
        {
            // ici la liste des audits est forcément != null
            List auditsDTO = ActionUtils.getCurrentAuditsAsDTO( pRequest );
            ComponentDTO projectDTO =
                (ComponentDTO) WTransformerFactory.formToObj( ProjectTransformer.class,
                                                              ActionUtils.getCurrentProject( pRequest ) )[0];

            // On va récupérer la grille qualité correspondant au dernier audit
            IApplicationComponent acResult = AccessDelegateHelper.getInstance( "Results" );
            Collection practicesAndMarks =
                (Collection) acResult.execute( "getAllPractices", new Object[] { auditsDTO.get( 0 ), projectDTO } );
            // Audit précédent si défini
            Collection practicesAndMarksOld = null;
            if ( auditsDTO.size() > 1 )
            {
                practicesAndMarksOld =
                    (Collection) acResult.execute( "getAllPractices", new Object[] { auditsDTO.get( 1 ), projectDTO } );

            }
            // définition et initialisation à null des différents éléments
            // concernant les anciens résultats
            Collection practicesOld = null;
            Collection marksOld = null;
            // Si des anciens résultats existent, alors on initialise correctement
            // tous les éléments permettant de récupérer ces résultats
            if ( practicesAndMarksOld != null )
            {
                Iterator practicesAndOldMarksIt = practicesAndMarksOld.iterator();
                practicesOld = (Collection) practicesAndOldMarksIt.next();
                marksOld = (Collection) practicesAndOldMarksIt.next();
            }
            // collection de practiceRuleDto
            Iterator practicesAndMarksIt = practicesAndMarks.iterator();
            Collection practices = (Collection) practicesAndMarksIt.next();
            Iterator practicesIt = practices.iterator();
            Collection marks = (Collection) practicesAndMarksIt.next();
            Iterator marksIt = marks.iterator();
            while ( practicesIt.hasNext() && marksIt.hasNext() )
            {
                // La pratique et la note associée ont le meme index
                Float mark = (Float) marksIt.next();
                PracticeRuleDTO practice = (PracticeRuleDTO) practicesIt.next();
                String practiceName = practice.getName();
                ResultForm f = new ResultForm();
                // Récupère la note précédente pour cette pratique si elle existe,
                // sinon on ne mettra rien
                if ( practicesAndMarksOld != null )
                {
                    Float oldMark = getPreviousMark( practicesOld, marksOld, practice.getId() );
                    if ( oldMark != null )
                    {
                        f.setPredecessorMark( oldMark.toString() );
                        // Si il y a une note précédente alors les audits sont comparables, on l'indique
                        ( (ResultListForm) pForm ).setComparableAudits( true );
                    }
                }
                // Positionne les champs pour cette pratique et pour la note courante
                if ( mark != null )
                {
                    f.setId( "" + practice.getId() );
                    f.setName( practiceName );
                    f.setCurrentMark( mark.toString() );
                    // si on n'a pas pu remplir le champ note précédente, la tendance sera égale
                    // cf. TrendTag
                    list.add( f );
                }
            }
            Collections.sort( list, new ResultFormComparator() );
            ( (ResultListForm) pForm ).setList( list );
            // On est passé par un menu donc on réinitialise le traceur
            // On ajoute le lien de la page pour avoir le lien lors de la sélection d'une pratique
            updateHistTracker( WebMessages.getString( pRequest, "practices.meteo" ), "/squale/meteo.do?action=meteo",
                               TrackerStructure.TOP_VIEW, pRequest, true );
        }
        catch ( Exception e )
        {
            // Traitement des exceptions
            handleException( e, errors, pRequest );
            forward = pMapping.findForward( "total_failure" );
        }
        if ( !errors.isEmpty() )
        {
            saveMessages( pRequest, errors );
        }
        return forward;
    }

    /**
     * PRECONDITION: les 2 collections ne sont pas null et ont la meme taille, renvoie null sinon
     * 
     * @param pPractices les pratiques
     * @param pMarks les notes associés aux pratiques
     * @param pPracticeId l'id de la pratique dont on veut la note
     * @return la note de l'audit précédent pour la practice pPractice si elle existe, null sinon
     */
    private Float getPreviousMark( Collection pPractices, Collection pMarks, long pPracticeId )
    {
        Float result = null;
        // Vérifie que les 2 collections ont bien la meme taille car on incrémente
        // les 2 a chaque fois
        // on ne vérifie pas le fait qu'elles ne soient pas nulles
        if ( pPractices.size() == pMarks.size() )
        {
            Iterator practicesIt = pPractices.iterator();
            Iterator marksIt = pMarks.iterator();
            // Cherche dans l'ensemble des pratiques la pratique passé en paramètre,
            // et si elle existe renvoie sa note (peut etre null)
            while ( result == null && practicesIt.hasNext() )
            {
                PracticeRuleDTO practiceOld = (PracticeRuleDTO) practicesIt.next();
                Float previousMark = (Float) marksIt.next();
                if ( practiceOld.getId() == pPracticeId )
                {
                    result = previousMark;
                }
            }
        }
        return result;
    }

}
