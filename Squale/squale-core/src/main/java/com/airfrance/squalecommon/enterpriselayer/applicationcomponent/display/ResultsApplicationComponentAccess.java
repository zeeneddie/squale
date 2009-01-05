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
package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.display;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.AuditGridDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.result.ResultsDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.PracticeRuleDTO;
import com.airfrance.squalecommon.enterpriselayer.applicationcomponent.ACMessages;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.facade.component.AuditFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.component.ComponentFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.export.audit.AuditReportFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.quality.ErrorFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.quality.MeasureFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.quality.QualityResultFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.quality.SqualeReferenceFacade;
import com.airfrance.squalecommon.util.messages.CommonMessages;

/**
 * Classe permetant d'effectuer la récupération des résultats
 * <p>
 * Title : ResultsApplicationComponentAccess.java
 * </p>
 * <p>
 * Description : Application component d'affichage de resultats
 * </p>
 * <p>
 * Copyright : Copyright (c) 2005
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 * 
 * @author m400841
 */
public class ResultsApplicationComponentAccess
    extends DefaultExecuteComponent
{

    /** log */
    private static Log LOG = LogFactory.getLog( ResultsApplicationComponentAccess.class );

    /**
     * Nombre d'audits a récupérer par defaut
     */
    private static final Integer NB_AUDITS = new Integer( CommonMessages.getInt( "audit.nombre" ) );

    /**
     * Index a partir duquel on souhaite récupérer les audits par defaut Les données sont récupérées du plus récent au
     * plus vieux
     */
    private static final Integer INDEX_DEPART_AUDIT = new Integer( CommonMessages.getInt( "audit.index" ) );

    /**
     * Constructeur par défaut
     * 
     * @roseuid 42CBFBFC02AF
     */
    public ResultsApplicationComponentAccess()
    {
    }

    /**
     * Permet de récupérer une liste de valeurs pour une liste de types de résultat, un type de composant et un audit
     * donné
     * 
     * @param pAudit AuditDTO renseignant l'ID de l'audit sinon <code>null</code> pour le dernier audit de suivi
     * @param pComponent ComponentDTO renseignant l'ID du composant
     * @param pMetrics indique si l'on veut les métriques ou les résultats qualité
     * @return ResultsDTO <br/> Format de ResultsDTO : 2 lignes : <br/> -- Clé : null -- Valeur : liste des clés des
     *         TREs<br/> -- Clé : ComponentDTO -- Valeur : liste des résultats associées en valeur
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFBFD0008
     */
    public ResultsDTO getComponentResults( AuditDTO pAudit, ComponentDTO pComponent, Boolean pMetrics )
        throws JrafEnterpriseException
    {
        // Initialisation
        ResultsDTO results = null; // retour de la methode
        ComponentDTO currentComponent = validateComponent( pComponent ); // parametre de la facade
        AuditDTO currentAudit = initAudit( pAudit, pComponent ); // parametre de la facade

        ResultsDTO measuresResults = null;
        ResultsDTO qresultsResults = null;

        if ( currentAudit != null && currentComponent != null )
        {

            // Envoi des collections aux facades et recuperation des resultats
            if ( pMetrics.booleanValue() )
            {
                measuresResults = MeasureFacade.getAllMeasures( currentAudit, currentComponent );
                results = measuresResults;
            }
            else
            {
                qresultsResults = QualityResultFacade.getAllQResults( currentAudit, currentComponent );
                results = qresultsResults;
            }
        }
        else
        {
            LOG.error( ACMessages.getString( "ac.exception.results.getbytre.auditorcomponentnull" ) );
        }

        return results;
    }

    /**
     * @param pAudit l'audit concerné
     * @param pProject le projet
     * @return la liste des pratiques
     * @throws JrafEnterpriseException en cas d'échec
     */
    public Collection getAllPractices( AuditDTO pAudit, ComponentDTO pProject )
        throws JrafEnterpriseException
    {
        return QualityResultFacade.findPracticesWhere( new Long( pProject.getID() ), new Long( pAudit.getID() ) );
    }

    /**
     * Récupère les noms de toutes les pratiques liées à un audit et à un projet
     * 
     * @param pAudit l'audit concerné
     * @param pProject le projet
     * @return la liste des noms des pratiques
     * @throws JrafEnterpriseException en cas d'échec
     */
    public Collection getAllPracticeNames( AuditDTO pAudit, ComponentDTO pProject )
        throws JrafEnterpriseException
    {
        return QualityResultFacade.findPracticeNameWhere( new Long( pProject.getID() ), new Long( pAudit.getID() ) );
    }

    /**
     * Permet de récupérer des valeurs pour un type de résultat, un type de composant et plusieurs audits donnés
     * 
     * @param pProject ComponentDTO renseignant l'ID du composant
     * @param pRuleId Clé du TRE
     * @param pAuditDTOs liste des IDs des audits. Si <code>null</code> alors recuperation des deux derniers audits de
     *            suivi
     * @return ResultsDTO <br/> Format de ResultsDTO : 2 lignes : <br/> -- Clé : null -- Valeur : liste des AuditDTOs<br/> --
     *         Clé : ComponentDTO -- Valeur : liste des résultats associées<br/>
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFBFD01E9
     */
    public ResultsDTO getByAudit( List pAuditDTOs, ComponentDTO pProject, Long pRuleId )
        throws JrafEnterpriseException
    {

        // Initialisation
        ResultsDTO results = null; // retour de l'AC
        ComponentDTO currentComponent = validateComponent( pProject ); // parametre de la facade
        List currentAudits = initAuditList( pAuditDTOs, pProject ); // parametre de la facade

        if ( currentAudits != null && currentComponent != null )
        {
            results = QualityResultFacade.getQResultsByAudit( currentAudits, pRuleId, pProject );
        }
        else
        {
            LOG.error( ACMessages.getString( "ac.exception.results.getbyaudit.notvalidparameter" ) );
        }

        return results;
    }

    /**
     * Permet de récupérer des valeurs pour une liste de types de résultat, une liste de types de composant et un audit
     * donnés
     * 
     * @param pAudit AuditDTO renseignant l'ID de l'audit. Si <code>null</code> passé en parametre, alors recuperation
     *            des deux derniers audits de suivi.
     * @param pComponentDTOs liste des IDs des composants
     * @return ResultsDTO <br/> Format de ResultsDTO : n+1 lignes : <br/> -- Clé : null -- Valeur : liste des clés de
     *         TREs <br/> -- Clé : ComponentDTO -- Valeur : liste des résultats associées ( n fois ) <br/>
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFBFD0289
     */
    private ResultsDTO getByFactorAndProject( AuditDTO pAudit, List pComponentDTOs )
        throws JrafEnterpriseException
    {
        // Initialisation
        ResultsDTO results = null; // retour de la facade
        List currentComponents = validateComponents( pComponentDTOs ); // parametre pour la facade
        AuditDTO currentAudit = pAudit;
        if ( currentComponents != null )
        {
            currentAudit = initAudit( pAudit, (ComponentDTO) currentComponents.iterator().next() );
            // parametre de la facade
        }

        if ( currentComponents != null && currentAudit != null )
        {
            results = QualityResultFacade.getQResultsByFactorAndProject( currentComponents, currentAudit );
        }

        return results;
    }

    /**
     * Permet de récupérer des valeurs pour une liste de types de résultat, un type de composant et une liste d'audits
     * donnés
     * 
     * @param pProject ComponentDTO renseignant l'ID du composant
     * @param pAuditDTOs liste des IDs des audits. Si <code>null</code> passé en parametre, alors recuperation des
     *            deux derniers audits de suivi.
     * @return ResultsDTO <br/> Format de ResultsDTO : n+1 lignes : <br/> -- Clé : null -- Valeur : liste des clés de
     *         TREs <br/> -- Clé : AuditDTO -- Valeur : liste des résultats associées en valeur ( n fois )
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFBFD030B
     */
    public ResultsDTO getProjectResults( List pAuditDTOs, ComponentDTO pProject )
        throws JrafEnterpriseException
    {

        // Initialisation
        ResultsDTO results = null; // retour de l'AC
        List currentAudits = initAuditList( pAuditDTOs, pProject ); // parametre de la facade

        if ( currentAudits != null && pProject != null )
        {
            // Envoi des collections aux facades et recuperation des resultats
            results = QualityResultFacade.getQResultsByTREAndAudit( currentAudits, pProject );
        }

        return results;
    }

    /**
     * Obtention de la grille d'audit correspondant à un projet
     * 
     * @param pProject projet
     * @param pAudit audit
     * @return grille d'audit
     * @throws JrafEnterpriseException si erreur
     */
    public AuditGridDTO getProjectAuditGrid( ComponentDTO pProject, AuditDTO pAudit )
        throws JrafEnterpriseException
    {
        return QualityResultFacade.getAuditGrid( pProject, pAudit );
    }

    /**
     * Permet de récupérer la volumétrie d'un projet
     * 
     * @param pProject ComponentDTO renseignant l'ID du composant
     * @param pAuditId l'ID de l'audit.
     * @return ResultsDTO <br/> Format de ResultsDTO : n+1 lignes : <br/> -- Clé : null -- Valeur : liste des clés de
     *         TREs <br/> -- Clé : ComponentDTO -- Valeur : liste des résultats associées en valeur ( n fois )
     * @throws JrafEnterpriseException Exception JRAF
     */
    public ResultsDTO getProjectVolumetry( Long pAuditId, ComponentDTO pProject )
        throws JrafEnterpriseException
    {
        // Appel méthode de la façade
        return MeasureFacade.getProjectVolumetry( pAuditId, pProject );
    }

    /**
     * Permet de récupérer les résultats de tous les projets dans le référentiel
     * 
     * @param pNbLignes nombre de lignes, sinon <code>null</code> pour tous les projets avec le parametre pIndexDepart
     *            à <code>null</code>
     * @param pIndexDepart index de depart, sinon <code>null</code> pour tous les projets
     * @param pIsAdmin booléen permettant de savoir si on doit récupérer les projets masqués ou non
     * @param pUserId l'id de l'utilisateur
     * @return Collection de SqualeReferenceDTO
     * @throws JrafEnterpriseException Exception JRAF
     */
    public Collection getReference( Integer pNbLignes, Integer pIndexDepart, Boolean pIsAdmin, Long pUserId )
        throws JrafEnterpriseException
    {
        Collection collection = null;
        collection =
            SqualeReferenceFacade.getProjectResults( pNbLignes, pIndexDepart, pIsAdmin.booleanValue(), pUserId );
        return collection;
    }

    /**
     * Obtention des métriques d'applications
     * 
     * @param pComponentDTOs collection d'applications
     * @return métriques associés au projet
     * @throws JrafEnterpriseException si erreur
     */
    public List getApplicationResults( Collection pComponentDTOs )
        throws JrafEnterpriseException
    {
        List result = new ArrayList();
        // Initialisation de la recuperation de projets
        Iterator componentIterator = pComponentDTOs.iterator(); // iterateur
        while ( componentIterator.hasNext() )
        {
            // Obtention du composant
            ComponentDTO currentComponent = (ComponentDTO) componentIterator.next();

            // Recuperation des composants fils
            Iterator children =
                new ArrayList( ComponentFacade.getChildren( currentComponent, null, null, null ) ).iterator();
            // Obtention du dernier audit
            AuditDTO lastAudit = initAudit( null, currentComponent );
            // Ce cas se présente sur une nouvelle application ou bien
            // en cas de purge d'audit
            if ( lastAudit != null )
            {
                while ( children.hasNext() )
                {
                    ComponentDTO currentChild = (ComponentDTO) children.next();
                    // Obtention des mesures liées à cet audit pour chacun des fils
                    ResultsDTO results = MeasureFacade.getAllMeasures( lastAudit, currentChild );
                    result.add( results );
                }
            }
        }
        return result;
    }

    /**
     * Permet de récupérer les résultats relatifs aux applications
     * 
     * @param pComponentDTOs Collection de ComponentDTOs renseignant l'ID du composant
     * @param pAudits liste des audits souhaités, sinon <code>null</code> pour le cas ou on souhaite les deux derniers
     *            audits (paramétré dans le fichier de propriétés)
     * @return liste de liste de resultDTOs triés dans le meme ordre que les pComponentDTOs<br/> Format de la liste :
     *         <br/> liste de liste - le nombre d'élément étant égal au nombre d'éléments de pComponentDTOs {ResultsDTO
     *         du dernier audit, ResultsDTO de l'avant-dernier audit, etc. }<br/><br/> Format du ResultsDTO :<br/> --
     *         Clé : null -- Valeur : liste des TREs disponibles<br/> -- Clé : ComponentDTO relatif au projet -- Valeur :
     *         liste de valeurs associés (n fois) (n fois avec n le nombre total de projets appartenant a l'utilisateur)
     * @throws JrafEnterpriseException Exception JRAF
     * @roseuid 42CBFBFE0154
     */
    public List getApplicationResults( Collection pComponentDTOs, List pAudits )
        throws JrafEnterpriseException
    {

        // Initialisation
        List resultDTOs = new ArrayList(); // retour de la fonction
        List children = new ArrayList(); // retour de la facade ComponentFacade
        List currentAudits = null; // parametre des facades de resultats

        // Validation des composants
        List componentTemps = new ArrayList( pComponentDTOs );
        List currentComponents = validateComponents( componentTemps );

        // Initialisation de la recuperation de projets
        Iterator componentIterator = pComponentDTOs.iterator(); // iterateur
        ComponentDTO currentComponent = null;
        // ApplicationDTO sous forme de ComponentDTO

        // Initialisation du retour de la methode
        ResultsDTO resultsTemp = null;

        if ( currentComponents != null )
        {
            // ajout de tous les projets concernés
            while ( componentIterator.hasNext() )
            {

                // Obtention du composant
                currentComponent = (ComponentDTO) componentIterator.next();

                // Recuperation des composants fils
                children = new ArrayList( ComponentFacade.getChildren( currentComponent, null, null, null ) );

                // Initialisation des audits
                currentAudits = initAuditList( pAudits, currentComponent );

                List currentResults = new ArrayList();
                // Initialisation du retour de la methode
                if ( currentAudits != null )
                {
                    // Construction de la liste des résultats intermédiaires
                    for ( int i = 0; i < currentAudits.size(); i++ )
                    {
                        resultsTemp = new ResultsDTO();
                        currentResults.add( resultsTemp );
                    }
                    // Obtention des résultats
                    currentResults = getProjectResultList( currentResults, currentAudits, children );
                }
                // Ajout des résultats vides pour les applications non encore audités
                if ( currentResults == null || currentResults.size() == 0 )
                {
                    currentResults = new ArrayList();
                    currentResults.add( createEmptyResults( children ) );
                }
                // Ajout des résultats
                resultDTOs.add( currentResults );
            }
        }
        else
        {
            resultDTOs = null;
        }

        return resultDTOs;
    }

    /**
     * Création de résultats nuls pour des projets
     * 
     * @param pChildren applications
     * @return résultats vides respectant le format suivant : -- Clé : null -- Valeur : liste des TREs demandés<br/> --
     *         Clé : ComponentDTO relatif à chaque sous-projet -- Valeur : liste de valeurs nulles
     */
    private ResultsDTO createEmptyResults( List pChildren )
    {
        ResultsDTO result = new ResultsDTO();
        if ( result != null )
        {
            return result;
        }
        // TODO extraire la liste des facteurs associés
        // et renvoyer celle-ci
        return result;
    }

    /**
     * @param pAuditId l'id de l'audit
     * @param pProjectId l'id du projet
     * @return un booléen indiquant si le projet a provoqué des erreurs
     * @throws JrafEnterpriseException en cas d'échec
     */
    public Boolean getHaveErrors( Long pAuditId, Long pProjectId )
        throws JrafEnterpriseException
    {
        return new Boolean( getNumberOfErrors( pAuditId, pProjectId ).intValue() > 0 );
    }

    /**
     * @param pAuditId l'id de l'audit
     * @param pProjectId l'id du projet
     * @return le nombre d'erreurs provoqué par le projet
     * @throws JrafEnterpriseException en cas d'échec
     */
    public Integer getNumberOfErrors( Long pAuditId, Long pProjectId )
        throws JrafEnterpriseException
    {
        return new Integer( ErrorFacade.getNumberOfErrors( pAuditId, pProjectId ) );
    }

    /**
     * @param pAuditId l'id de l'audit
     * @param pProjectId l'id du projet
     * @return le nombre d'erreurs provoqué par le projet par niveau
     * @throws JrafEnterpriseException en cas d'échec
     */
    public Integer[] getErrorsRepartition( Long pAuditId, Long pProjectId )
        throws JrafEnterpriseException
    {
        return ErrorFacade.getErrorsRepartition( pAuditId, pProjectId );
    }

    /**
     * @param pAuditId l'id de l'audit
     * @param pProjectId l'id du projet
     * @return les noms des tâches en échec
     * @throws JrafEnterpriseException en cas d'échec
     */
    public List getFailedTasks( Long pAuditId, Long pProjectId )
        throws JrafEnterpriseException
    {
        return ErrorFacade.getFailedTasks( pAuditId, pProjectId );
    }

    /**
     * Ajoute à pResultDTOs les ResultDTO correspondant.Recupere pour tous les audits les ResultsDTO au format composant
     * en fonction des tres, et les ajoute a la liste de ResultsDTO à renvoyer à l'utilisateur.
     * 
     * @param pResultDTOs la liste de ResultDTO donnant les projets
     * @param pCurrentAudits liste des AuditDTO concernés
     * @param pChildren enfants dont on veut les résultats parametres des facades MeasureFacade et QResultFacade
     * @return une liste de ResultDTO au même format que dans la methode getApplicationResults()
     * @throws JrafEnterpriseException Exception JRAF
     */
    private List getProjectResultList( List pResultDTOs, List pCurrentAudits, List pChildren )
        throws JrafEnterpriseException
    {
        ListIterator auditIterator = pCurrentAudits.listIterator();
        AuditDTO currentAudit = null;
        // audit sur lequel on requiert des resultats
        ResultsDTO currentResults = null;
        // ResultsDTO pour les projets et les TREs
        List newResultDTOs = pResultDTOs;
        while ( auditIterator.hasNext() && newResultDTOs != null )
        {

            // Initialisation de l'audit courant et chargement de resultats projets / TREs
            currentAudit = (AuditDTO) auditIterator.next();
            currentResults = getByFactorAndProject( currentAudit, pChildren );

            // Ajout a des resultats des projets / TREs a la collection de ResultsDTO pour un audit donné
            // L'indice dans la liste de ResultsDTO correspond a l'indice dans la liste d'audits
            /*
             * TODO tester que la premiere ligne de resultsDTO pour chaque projet est la meme SINON throw une exception
             */
            if ( currentResults != null )
            {
                // Ajout des résultats sur le RésultDTO de pResultDTOs correspondant au bon audit
                ResultsDTO resultDTO = ( (ResultsDTO) newResultDTOs.get( auditIterator.nextIndex() - 1 ) );
                resultDTO.getResultMap().putAll( currentResults.getResultMap() );
            }
            else
            {
                newResultDTOs = null;
            }
        }
        return newResultDTOs;
    }

    /**
     * Permet d'effectuer la navigation par qualite
     * 
     * @param pComponent composant concerné par la navigation qualite (projet)
     * @param pTreParent tre parent dont on souahite les fils
     * @param pAudits audits concernes
     * @return ResultsDTO du format suivant :<br /> -- Clé : null -- Valeur : liste des clés de TREs<br /> -- Clé :
     *         AuditDTO -- Valeur : liste des résultats associées ( n fois )<br />
     * @throws JrafEnterpriseException exception Jraf
     */
    public ResultsDTO getTreChildrenResults( ComponentDTO pComponent, Long pTreParent, List pAudits )
        throws JrafEnterpriseException
    {

        // Initialisation
        ResultsDTO resultsDTO = null; // retour de l'AC
        Collection treChildren = null; // liste des TREs enfants

        // Validation du composant (id et test de nullité)
        ComponentDTO currentComponent = validateComponent( pComponent ); // parametre de la facade

        if ( currentComponent != null )
        {
            // récupère les audits si la liste d'audits est nulles
            List currentAudits = initAuditList( pAudits, currentComponent );

            try
            {

                // récupération des tre enfants
                treChildren = ComponentFacade.getTREChildren( pTreParent );

                if ( treChildren != null )
                {
                    List treChildrenList = new ArrayList( treChildren );
                    // récupération des résultats correspondant aux TREs enfants, aux audits et au composant concerné
                    resultsDTO = QualityResultFacade.getQResults( currentAudits, currentComponent, treChildrenList );
                }

            }
            catch ( JrafEnterpriseException e )
            {
                String obj[] = { String.valueOf( currentComponent.getID() ) };
                LOG.error( ACMessages.getString( "ac.exception.results.gettrechildrenresults", obj ), e );
            }

        }

        return resultsDTO;

    }

    /**
     * Permet de recuperer les resultats d'une pratique ainsi que sa repartition
     * 
     * @param pAudits liste des AuditDTOs ou <code>null</code> pour récupérer les derniers audits
     * @param pProject ComponentDTO relatif a un projet
     * @param pPractice pratique
     * @return ResultsDTO avec les deux Map renseignes ResultsMap : <br/> -- Clé : null -- Valeur : Liste d'AuditDTO
     *         <br/> -- Clé : ComponentDTO -- Valuer : valeurs associé au tre <br/> <br/> PracticeMap : n lignes de la
     *         forme suivante :<br/> -- Clé : AuditDTO -- Valeur : tableau de Integer correspondant a la repartition
     * @throws JrafEnterpriseException exception Jraf
     */
    public ResultsDTO getRepartitionResults( List pAudits, ComponentDTO pProject, PracticeRuleDTO pPractice )
        throws JrafEnterpriseException
    {

        // Initialisation
        ResultsDTO repartitionResults = null; // retour de la methode
        ComponentDTO currentComponent = validateComponent( pProject ); // composant testé
        List currentAudits = null; // audits initialises
        if ( currentComponent != null )
        {
            currentAudits = initAuditList( pAudits, pProject );
        }

        if ( currentAudits != null )
        {
            repartitionResults = QualityResultFacade.getRepartition( currentAudits, currentComponent, pPractice );
        }

        return repartitionResults;

    }

    /**
     * Obtention des résultats de rulecheking sur un projet
     * 
     * @param pAudits liste des AuditDTOs ou <code>null</code> pour récupérer les derniers audits
     * @param pProject ComponentDTO relatif a un projet
     * @param pPractice pratique
     * @return ResultsDTO avec les deux Map renseignes ResultsMap : <br/> -- Clé : null -- Valeur : Liste d'AuditDTO
     *         <br/> -- Clé : ComponentDTO -- Valeurs : notes associées aux audits dans le même ordre que la liste
     *         d'audit de type java.lang.Float<br/> <br/> PracticeMap : n lignes de la forme suivante :<br/> -- Clé :
     *         AuditDTO -- Valeur : map de <RuleCheckingDTO,java.lang.Integer>
     * @throws JrafEnterpriseException exception Jraf
     */
    public ResultsDTO getRuleCheckingResults( List pAudits, ComponentDTO pProject, PracticeRuleDTO pPractice )
        throws JrafEnterpriseException
    {
        // Initialisation
        ResultsDTO repartitionResults = null; // retour de la methode
        ComponentDTO currentComponent = validateComponent( pProject ); // composant testé
        List currentAudits = null; // audits initialises
        if ( currentComponent != null )
        {
            currentAudits = initAuditList( pAudits, pProject );
        }

        if ( currentAudits != null )
        {
            repartitionResults = QualityResultFacade.getRuleChecking( currentAudits, currentComponent, pPractice );
        }

        return repartitionResults;
    }

    /**
     * Récupère les détails d'une transgressions pour une règle donnée.
     * 
     * @param pMeasureID l'id de la mesure
     * @param pRuleID l'id de la règle
     * @return les détails de la mesure concernant la règle d'id <code>pRuleID</code>
     * @throws JrafEnterpriseException si erreur
     */
    public Collection getRuleCheckingItemResults( Long pMeasureID, Long pRuleID )
        throws JrafEnterpriseException
    {
        return QualityResultFacade.getRuleCheckingItems( pMeasureID, pRuleID );
    }

    /**
     * Permet de recuperer les composants et leurs resultats associés a une valeur donnée d'une note et un audit
     * 
     * @param pAudit AuditDTO renseignant l'identifiant. Ne peut etre <code>null</code>
     * @param pProject ComponentDTO relatif au projet
     * @param pTreKey clé de tre relatif a une pratique
     * @param pValue Integer representant l'index de répartition
     * @param pMax Nombre maximum de composants retourné
     * @return ResultsDTO de la forme suivante :<br/> -- Clé : null -- Valeur : Liste de clés de tre relatifs a une
     *         pratique -- Clé : ComponentDTO -- Valeur : Liste de valeur associée
     * @throws JrafEnterpriseException exception Jraf
     */
    public ResultsDTO getValueResults( AuditDTO pAudit, ComponentDTO pProject, Long pTreKey, Integer pValue,
                                       Integer pMax )
        throws JrafEnterpriseException
    {

        // Initialisation
        ResultsDTO results = null; // retour de la methode

        Collection components = null; // composants ayant la valeur donnée
        List componentList = null; // liste associee aux composants recuperes
        Collection treChildren = null; // liste des tres fils
        List treKeyList = null; // liste associee aux tres recuperes

        AuditDTO currentAudit = pAudit;
        ComponentDTO currentProject = validateComponent( pProject );
        if ( currentProject != null )
        {
            currentAudit = initAudit( currentAudit, currentProject );
        }

        // récupération des composants souhaités et des tres de mesures
        if ( pValue != null )
        {

            components = ComponentFacade.get( pAudit, currentProject, pTreKey, pValue, pMax );
            treChildren = ComponentFacade.getTREChildren( pTreKey );

            // Initialisation des listes
            if ( components != null && treChildren != null )
            {

                componentList = new ArrayList( components );
                treKeyList = new ArrayList( treChildren );

                /* recuperation des resultats */
                results = MeasureFacade.getMeasuresByTREAndComponent( pTreKey, treKeyList, componentList, pAudit );

            }
        }
        return results;
    }

    /**
     * Permet de recuperer les composants et leurs resultats associés a une valeur donnée d'une note et un audit
     * 
     * @param pAudit AuditDTO renseignant l'identifiant. Ne peut etre <code>null</code>
     * @param pProject ComponentDTO relatif au projet
     * @param pTreKey clé de tre relatif a une pratique
     * @param pMinValue Integer representant la note min de l'interval
     * @param pMaxValue Integer representant la note max de l'interval
     * @param pMax Nombre maximum de composants retourné
     * @return ResultsDTO de la forme suivante :<br/> -- Clé : null -- Valeur : Liste de clés de tre relatifs a une
     *         pratique -- Clé : ComponentDTO -- Valeur : Liste de valeur associée
     * @throws JrafEnterpriseException exception Jraf
     */
    public ResultsDTO getValueResultsForInterval( AuditDTO pAudit, ComponentDTO pProject, Long pTreKey,
                                                  Double pMinValue, Double pMaxValue, Integer pMax )
        throws JrafEnterpriseException
    {

        // Initialisation
        ResultsDTO results = null; // retour de la methode

        Collection components = null; // composants ayant la valeur donnée
        List componentList = null; // liste associee aux composants recuperes
        Collection treChildren = null; // liste des tres fils
        List treKeyList = null; // liste associee aux tres recuperes

        AuditDTO currentAudit = pAudit;
        ComponentDTO currentProject = validateComponent( pProject );
        if ( currentProject != null )
        {
            currentAudit = initAudit( currentAudit, currentProject );
        }

        // récupération des composants souhaités et des tres de mesures
        if ( pMinValue != null && pMaxValue != null )
        {

            components =
                ComponentFacade.getComponentsInInterval( pAudit, currentProject, pTreKey, pMinValue, pMaxValue, pMax );
            treChildren = ComponentFacade.getTREChildren( pTreKey );

            // Initialisation des listes
            if ( components != null && treChildren != null )
            {

                componentList = new ArrayList( components );
                treKeyList = new ArrayList( treChildren );

                /* recuperation des resultats */
                results = MeasureFacade.getMeasuresByTREAndComponent( pTreKey, treKeyList, componentList, pAudit );

            }
        }
        return results;
    }

    /**
     * @param pAudit audit courant
     * @return l'audit précédent
     * @throws JrafEnterpriseException si pb hibernate
     */
    public AuditDTO getPreviousAudit( AuditDTO pAudit )
        throws JrafEnterpriseException
    {
        return AuditFacade.getPreviousAudit( pAudit, null );
    }

    /**
     * Permet de récupérer les notes de tous les composants d'un type donné relatifs a un projet
     * 
     * @param pProject ComponentDTO de type projet
     * @param pComponentType type du composant fils souhaité
     * @param pAudit AuditDTO
     * @param pTre Tre dont on souhaite connaître le classment
     * @param pMax nombre maximum de top remonté
     * @return ResultsDTO du meme format que dans getByComponent()
     * @throws JrafEnterpriseException exception Jraf
     */
    public ResultsDTO getTopResults( ComponentDTO pProject, String pComponentType, AuditDTO pAudit, String pTre,
                                     Integer pMax )
        throws JrafEnterpriseException
    {

        // Initiailisation
        ResultsDTO results = null; // retour de la methode
        ComponentDTO currentProject = null; // sous-projet apres verification
        AuditDTO currentAudit = null; // audit apres verification
        Collection children = null; // fils du sous-projet d'un type donné

        currentProject = validateComponent( pProject );
        if ( currentProject != null )
        {
            currentAudit = initAudit( pAudit, currentProject );
        }

        if ( currentAudit != null )
        {
            results = MeasureFacade.getTop( currentProject, pComponentType, currentAudit, pTre, pMax );
        }

        return results;

    }

    /**
     * Retrieves components which have a precise score for some tres
     * 
     * @param pProjectId project id
     * @param pAuditId Audit id
     * @param pTreKeys keys of tres to find
     * @param pTreValues value of tres
     * @param pMax number of components max to return
     * @return list of components
     * @throws JrafEnterpriseException if error
     */
    public List<ComponentDTO> getComponentsWhereTres( Long pProjectId, Long pAuditId, String[] pTreKeys, String[] pTreValues,
                                               Integer pMax )
        throws JrafEnterpriseException
    {
        return MeasureFacade.getComponentsWhereTres( pProjectId, pAuditId, pTreKeys, pTreValues, pMax );
    }

    /**
     * Initialise l'audit si celui-ci n'est pas renseigne (null ou identifiant negatif)
     * 
     * @param pAudit AuditDTO à valider
     * @param pComponent ComponentDTO associé à l'audit
     * @return AuditDTO renseignant un identifiant valide
     * @throws JrafEnterpriseException exception Jraf
     */
    private AuditDTO initAudit( AuditDTO pAudit, ComponentDTO pComponent )
        throws JrafEnterpriseException
    {

        // Initialisation
        AuditDTO audit = pAudit; // retour de la methode

        if ( audit == null || audit.getID() < 0 )
        {
            audit = AuditFacade.getLastAudit( pComponent, null );
        }

        // Test si le composant possede bien un audit pour loguer l'erreur
        if ( audit == null )
        {
            String obj[] = { String.valueOf( pComponent.getID() ) };
            LOG.error( ACMessages.getString( "ac.exception.results.initAudit.noauditforcomponent", obj ) );
        }

        return audit;
    }

    /**
     * Initialise les audits si ceux-ci ne sont pas renseignes
     * 
     * @param pAudits liste des AuditsDTO a renseigner
     * @param pComponent composant associe
     * @return liste de AuditDTO renseignant un identifiant valide
     * @throws JrafEnterpriseException exception Jraf
     */
    private List initAuditList( List pAudits, ComponentDTO pComponent )
        throws JrafEnterpriseException
    {

        // Initialisation
        List audits = pAudits; // retour de la methode

        if ( audits == null )
        {
            audits = AuditFacade.getLastAudits( pComponent, NB_AUDITS, INDEX_DEPART_AUDIT, null, AuditBO.TERMINATED );
        }
        else
        {
            /* TODO : lancer une methode d'integrite des audits */
        }

        return audits;

    }

    /**
     * Verifie que le composant n'est pas null et que son identifiant est >= 0
     * 
     * @param pComponent ComponentDTO
     * @return ComponentDTO si valide, sinon <code>null</code>
     */
    private ComponentDTO validateComponent( ComponentDTO pComponent )
    {
        ComponentDTO newComponent = pComponent;
        if ( newComponent != null )
        {
            if ( newComponent.getID() < 0 )
            {
                newComponent = null;
                LOG.error( ACMessages.getString( "ac.exception.results.validatecomponent.idnegative" ) );
            }
        }
        else
        {
            LOG.error( ACMessages.getString( "ac.exception.results.validatecomponent.componentnull" ) );
        }
        return newComponent;
    }

    /**
     * Verifie que la liste de composants est valide
     * 
     * @param pComponents Liste de ComponentDTO
     * @return Liste de ComponentDTO avec les composants uniquement valides
     */
    private List validateComponents( List pComponents )
    {

        // Initialisation
        List components = pComponents; // retour de la methode
        // On parcourt chaque composant pour
        // les valider un par un
        if ( pComponents != null )
        {
            Object currentObject = null;
            Iterator componentIterator = pComponents.iterator();
            // Verification de l'integrite de chaque composant
            while ( ( componentIterator.hasNext() ) && ( components != null ) )
            {
                currentObject = componentIterator.next();
                // TODO voir si on doit faire ce test ou supposer que
                // la collection est bien composée exclusivement de componentDTO
                // code defensif ?
                if ( currentObject instanceof ComponentDTO )
                {
                    ComponentDTO componentTemp = validateComponent( (ComponentDTO) currentObject );
                    if ( componentTemp == null )
                    {
                        components = null;
                    }
                }
                else
                { // objets differents de ComponentDTO
                    components = null;
                }
            }
        }
        else
        {
            LOG.error( ACMessages.getString( "ac.exception.results.validatecomponents.componentlistnull" ) );
        }

        return components;

    }

    /**
     * @param firstAudit le premier audit
     * @param secondAudit le second pour comparer
     * @param project le projet
     * @param filter le filtre à utiliser
     * @param pLimit le nombre max de résultats à récupérer
     * @return les changements entre le premier et le second audit
     * @throws JrafEnterpriseException si erreur
     */
    public Collection getChangedComponentResults( AuditDTO firstAudit, AuditDTO secondAudit, ComponentDTO project,
                                                  Object[] filter, Integer pLimit )
        throws JrafEnterpriseException
    {
        // On récupère la liste des nouveaux composants, des composants qui ont été supprimés
        // et ceux dont la note sur une ou plusieurs pratiques a changé.
        Collection components =
            QualityResultFacade.getChangedComponents( firstAudit, secondAudit, project, filter, pLimit );
        return components;
    }

    /**
     * Récupère les pratiques à corriger pour le plan d'action (sous forme ActionPlanDTO)
     * 
     * @param pAuditId l'id de l'audit
     * @param pProjectId l'id du projet
     * @param pHasLimit indique si il y a une limite du nombre de corrections à remonter
     * @return les 100 (au maximum) composants ou transgressions "à corriger" (si <code>pHasLimit</code>) triés par
     *         pratique (de la pratique la plus facile à corriger à la moins facile). Une pratique est plus facile à
     *         corriger si l'effort * nombre de corrections à faire est bas.
     * @throws JrafEnterpriseException si erreur
     */
    public Collection getWorstPractices( String pAuditId, String pProjectId, Boolean pHasLimit )
        throws JrafEnterpriseException
    {
        return QualityResultFacade.getWorstPractices( pAuditId, pProjectId, pHasLimit.booleanValue() );
    }
    
    /**
     * Build list of projects for audit report
     * 
     * @param pAppliId application id
     * @param pCurAuditId current audit id
     * @param pPrevAuditId previous audit id
     * @param nbTop number of top to retrieve (maximum)
     * @param maxScore maximal score for score's components for top
     * @return list of project for audit report
     * @throws JrafEnterpriseException if error
     */
    public List getProjectReports(Long pAppliId, Long pCurAuditId, Long pPrevAuditId, Integer nbTop, Float maxScore) throws JrafEnterpriseException {
        return AuditReportFacade.getProjectReports( pAppliId, pCurAuditId, pPrevAuditId, nbTop, maxScore );
    }

}
