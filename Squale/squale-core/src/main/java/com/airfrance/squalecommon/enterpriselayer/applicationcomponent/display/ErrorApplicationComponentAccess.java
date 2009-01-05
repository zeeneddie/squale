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
//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\applicationcomponent\\ErrorApplicationComponentAccess.java

package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.display;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.result.ErrorDTO;
import com.airfrance.squalecommon.enterpriselayer.applicationcomponent.ACMessages;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.facade.component.AuditFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.quality.ErrorFacade;
import com.airfrance.squalecommon.util.messages.CommonMessages;

/**
 * <p>
 * Title : ErrorApplicationComponentAccess.java
 * </p>
 * <p>
 * Description : Application component error
 * </p>
 * <p>
 * Copyright : Copyright (c) 2005
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 * Classe permettant de récupérer les erreurs en fonction des paramètres d'audits et de taches qui ont généré les
 * erreurs
 */
public class ErrorApplicationComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * Nombre d'audits a récupérer par defaut
     */
    private static final Integer NB_AUDITS = new Integer( CommonMessages.getInt( "audit.nombre" ) );

    /**
     * Index a partir duquel on souhaite récupérer les audits par defaut Les données sont récupérées du plus récent au
     * plus vieux
     */
    private static final Integer INDEX_DEPART_AUDIT = new Integer( CommonMessages.getInt( "audit.index" ) );

    /** log */
    private static Log LOG = LogFactory.getLog( ErrorApplicationComponentAccess.class );

    /**
     * Permet de récupérer les erreurs d'un projet relatifs à un audit et une tache donné
     * 
     * @param pError ErrorDTO renseignant l'ID de l'audit et du projet si AuditID < 0, recuperation des erreurs pour le
     *            dernier audit si Task = null, recuperation des erreurs pour toutes les taches
     * @param pNbLignes nombre de lignes
     * @param pIndexDepart index de depart
     * @return Collection de ErrorDTO
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFBF800B5
     */
    public Collection getErrors( ErrorDTO pError, Integer pNbLignes, Integer pIndexDepart )
        throws JrafEnterpriseException
    {

        // Initialisation
        Collection collection = null; // retour de l'AC
        ErrorDTO newError = pError;

        if ( newError != null )
        {

            // on verifie que les champs necessaires sont renseignes
            newError = validateErrorDTO( newError );
            if ( newError != null )
            {
                // Execution de la methode
                collection = ErrorFacade.getErrors( newError, pNbLignes, pIndexDepart );
            }

        }

        return collection;
    }

    /**
     * Permet de récupérer une liste d'erreurs pour un projet, un audit et plusieurs audits
     * 
     * @param pAudits liste des AuditDTO renseignant l'identifiant, sinon <code>null</code> si on souhaite les deux
     *            derniers audits de suivi
     * @param pError ErrorDTO avec un ID de composant renseigne au minimum
     * @param pNbLignes nombre de lignes
     * @param pIndexDepart index de depart
     * @return Collection de ErrorDTO
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFBF80105
     */
    public Collection getErrorsByAudit( List pAudits, ErrorDTO pError, Integer pNbLignes, Integer pIndexDepart )
        throws JrafEnterpriseException
    {

        // Initialsiation
        Collection collection = null; // retour de l'AC
        ComponentDTO project = null; // parametre pour AuditFacade
        List lastAudits = pAudits; // audits souhaites

        // Verifie si les audits sont renseignes
        if ( pError.getProjectId() > 0 )
        {
            if ( pAudits == null )
            {

                project = new ComponentDTO();
                project.setID( pError.getProjectId() );
                // recuperation des 2 derniers audits pour tous les audits
                lastAudits = AuditFacade.getLastAudits( project, NB_AUDITS, INDEX_DEPART_AUDIT, null, AuditBO.FAILED );
            }

            collection = ErrorFacade.getErrorsByAudit( lastAudits, pError, pNbLignes, pIndexDepart );

        }
        else
        {
            LOG.error( ACMessages.getString( "ac.exception.error.geterrorsbyaudit.negativeprojectid" ) );
        }

        return collection;
    }

    /**
     * Permet de récupérer des erreurs pour un projet, un audit et une liste de taches
     * 
     * @param pTaskKeys liste des cles de taches souhaitées, sinon <code>null</code> pour tooutes les taches
     * @param pError ErrorDTO avec identifiant du composant au minimum
     * @param pNbLignes nombre de lignes
     * @param pIndexDepart index de depart
     * @return Collection de ErrorDTO
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFBF8023B
     */
    public Collection getErrorsByTask( List pTaskKeys, ErrorDTO pError, Integer pNbLignes, Integer pIndexDepart )
        throws JrafEnterpriseException
    {

        // Initialisation
        Collection collection = null;

        ErrorDTO newError = validateErrorDTO( pError );

        if ( newError != null )
        {
            collection = ErrorFacade.getErrorsByTask( pTaskKeys, newError, pNbLignes, pIndexDepart );
        }

        return collection;
    }

    /**
     * Permet de récupérer des erreurs pour une liste de composants et une liste d'audits donnés
     * 
     * @param pAuditDTOs liste des AuditDTOs souhaités
     * @param pTaskKeys liste des clés des taches
     * @param pError ErrorDTO avec ID du composant renseigné
     * @param pNbLignes nombre de lignes
     * @param pIndexDepart index de depart
     * @return Collection de ErrorDTO
     * @throws JrafEnterpriseException Exception JRAF
     * @deprecated ne sera pas implemente
     * @roseuid 42CBFBF80336
     */
    /*
     * TODO BFR --> deprecated method to suppress public Collection getErrorsByAuditAndTask( List pAuditDTOs, List
     * pTaskKeys, ErrorDTO pError, Integer pNbLignes, Integer pIndexDepart ) throws JrafEnterpriseException { // Mise
     * des parametres a null pAuditDTOs = null; pTaskKeys = null; pError = null; pNbLignes = null; pIndexDepart = null;
     * Collection collection = null; // retour de l'AC return collection; }
     */
    /**
     * Permet de completer l'objet ErrorDTO si des données sont manquantes
     * 
     * @param pErrorDTO ErrorDTO
     * @return ErrorDTO avec la bonne initialisation
     * @throws JrafEnterpriseException exception JRAF
     */
    private ErrorDTO validateErrorDTO( ErrorDTO pErrorDTO )
        throws JrafEnterpriseException
    {

        // initialisation
        AuditDTO lastAudit = null; // dernier audit du projet
        ErrorDTO newErrorDTO = pErrorDTO;

        // teste si l'identifiant de l'audit est negatif
        if ( newErrorDTO.getAuditId() < 0 )
        {

            // initialisation du composant
            ComponentDTO project = new ComponentDTO(); // parametre de AuditFacade
            project.setID( newErrorDTO.getProjectId() );

            lastAudit = AuditFacade.getLastAudit( project, null );

            if ( lastAudit != null )
            {
                newErrorDTO.setAuditId( lastAudit.getID() );
            }
        }

        // teste si l'identifiant du projet ou de l'audit est negatif
        if ( newErrorDTO.getProjectId() < 0 || newErrorDTO.getAuditId() < 0 )
        {
            LOG.error( ACMessages.getString( "ac.exception.error.validateerrordto.negativeid" ) );
            newErrorDTO = null;
        }

        // aucun traitement si la tache est nul. Cas traite au niveau facade

        return newErrorDTO;
    }

    /**
     * @param pProjectId l'id du projet
     * @param pAuditId l'id de l'audit
     * @return les noms des tâches possédant des erreurs
     * @throws JrafEnterpriseException en cas d'échec
     */
    public List getAllTasks( Long pProjectId, String pAuditId )
        throws JrafEnterpriseException
    {
        List results = new ArrayList( 0 );
        // Liste vide si l'audit n'est pas renseigné
        if ( null != pAuditId )
        {
            results = ErrorFacade.getAllTasks( pProjectId, new Long( Long.parseLong( pAuditId ) ) );
        }
        return results;
    }

    /**
     * Get errors for a list of audits and a criticity level (facultative)
     * 
     * @param pAuditsDTO list of audits (currentt audit, previous audit)
     * @param pCriticity level of errors
     * @return list of map of errors for each audit (same order) like : (current audit map, previous audit map) key :
     *         project name value : List of ErrorDTO for this project and this audit
     * @throws JrafEnterpriseException if error
     */
    public List getAllErrors( List pAuditsDTO, String pCriticity )
        throws JrafEnterpriseException
    {
        return ErrorFacade.getAllErrors( pAuditsDTO, pCriticity );
    }

    /**
     * Constructeur par défaut
     * 
     * @roseuid 42CBFBF90034
     */
    public ErrorApplicationComponentAccess()
    {
    }
}
