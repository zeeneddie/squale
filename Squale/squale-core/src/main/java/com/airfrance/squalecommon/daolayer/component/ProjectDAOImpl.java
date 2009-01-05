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
 * Créé le 8 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.daolayer.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.DAOMessages;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ProjectParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * @author M400843
 */
public class ProjectDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static ProjectDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static
    {
        instance = new ProjectDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private ProjectDAOImpl()
    {
        initialize( ProjectBO.class );
        if ( null == LOG )
        {
            LOG = LogFactory.getLog( ProjectDAOImpl.class );
        }
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static ProjectDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Retourne les projets en relation avec l'audit
     * 
     * @param pSession une session hibernate
     * @param pAuditId l'id de l'audit
     * @return une collection de projets
     * @throws JrafDaoException si une erreur à lieu
     */
    public Collection findWhere( ISession pSession, Long pAuditId )
        throws JrafDaoException
    {
        LOG.debug( DAOMessages.getString( "dao.entry_method" ) );
        String whereClause = "where ";
        whereClause += pAuditId + " in elements(" + getAlias() + ".audits)";

        Collection ret = findWhere( pSession, whereClause );
        LOG.debug( DAOMessages.getString( "dao.exit_method" ) );
        return ret;
    }

    /**
     * Création ou sauvegarde de l'objet en fonction du paramètrage défini dans le fichier de mapping Vérifie l'unicité
     * du nom du projet pour l'application
     * 
     * @param pSession la session
     * @param pProject le projet à persister ou à mettre à jour
     * @return le projet créé si tout s'est bien passé
     * @throws JrafDaoException si une erreur à lieu ou si le nom du projet existe déjà pour l'application correspondant
     */
    public ProjectBO save( ISession pSession, ProjectBO pProject )
        throws JrafDaoException
    {
        LOG.debug( DAOMessages.getString( "dao.entry_method" ) );
        Long idProject = new Long( pProject.getId() );
        ProjectBO newProject = null;

        // si le projet n'existe pas, il se peut qu'un autre projet existe deja avec le meme nom
        if ( ( null != load( pSession, idProject ) ) || ( 0 == countWhereParentName( pSession, pProject ) ) )
        {
            super.save( pSession, pProject );
            newProject = pProject;
        }
        LOG.debug( DAOMessages.getString( "dao.exit_method" ) );
        return newProject;
    }

    /**
     * Création de l'objet métier persistent, <b>Attention : </b> la relation avec le projet doit être à jour
     * 
     * @param pSession la session
     * @param pProject le projet à persister
     * @return le projet créé si tout s'est bien passé
     * @throws JrafDaoException si une erreur à lieu ou si le nom du projet existe déjà pour l'application
     *             correspondante
     */
    public ProjectBO create( ISession pSession, ProjectBO pProject )
        throws JrafDaoException
    {
        LOG.debug( DAOMessages.getString( "dao.entry_method" ) );
        int existantProject = countWhereParentName( pSession, pProject );
        ProjectBO newProject = null;

        if ( 0 == existantProject )
        {
            super.create( pSession, pProject );
            newProject = pProject;
        }

        LOG.debug( DAOMessages.getString( "dao.exit_method" ) );
        return newProject;
    }

    /**
     * Récupère la liste des projets de cette application
     * 
     * @param pSession la session
     * @param pApplicationId l'id de l'application dont on veut les projets
     * @return la liste des projets fils
     * @throws JrafDaoException en cas d'échec
     */
    public Collection findAllProjects( ISession pSession, Long pApplicationId )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".parent=" + pApplicationId.longValue();

        Collection col = findWhere( pSession, whereClause );
        return col;
    }

    /**
     * Retourne le nombre de projet du nom de pProject et de meme application que pProject
     * 
     * @param pSession la session
     * @param pProject le projet
     * @return le nombre de projets trouvé (théoriquement 0 ou 1)
     * @throws JrafDaoException si une erreur à lieu
     */
    private int countWhereParentName( ISession pSession, ProjectBO pProject )
        throws JrafDaoException
    {
        LOG.debug( DAOMessages.getString( "dao.entry_method" ) );
        String whereClause = "where ";
        whereClause += getAlias() + ".parent.id = " + pProject.getParent().getId();
        whereClause += " AND ";
        whereClause += getAlias() + ".name = '" + pProject.getName() + "'";
        // Il peut en exister d'autre de même nom mais avec un status "à supprimer"
        whereClause += " AND ";
        whereClause += getAlias() + ".status != " + ProjectBO.DELETED;

        int ret = countWhere( pSession, whereClause ).intValue();
        LOG.debug( DAOMessages.getString( "dao.exit_method" ) );
        return ret;
    }

    /**
     * @see com.airfrance.jraf.spi.persistence.IPersistenceDAO#remove(com.airfrance.jraf.spi.persistence.ISession,
     *      java.lang.Object)
     */
    public void remove( ISession pSession, Object pObj )
        throws JrafDaoException
    {
        super.remove( pSession, pObj );
    }

    /**
     * Mise à jour des grilles qualité
     * 
     * @param pSession session
     * @param pQualityGrid grille qualité
     * @throws JrafDaoException si erreur
     */
    public void updateQualityGrid( ISession pSession, QualityGridBO pQualityGrid )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".qualityGrid.id != " + pQualityGrid.getId();
        whereClause += " AND ";
        whereClause += getAlias() + ".qualityGrid.name = '" + pQualityGrid.getName() + "'";
        Iterator projectsIt = findWhere( pSession, whereClause ).iterator();
        // Parcours de la collection
        while ( projectsIt.hasNext() )
        {
            // Mise à jour des grilles qualité
            ProjectBO project = (ProjectBO) projectsIt.next();
            project.setQualityGrid( pQualityGrid );
            save( pSession, project );
        }
    }

    /**
     * Retourne la liste des projets utilisant cette grille qualité Sert notamment pour la notification aux
     * gestionnaires des applications lors d'un chargement de nouvelle grille
     * 
     * @param pSession session
     * @param pQualityGridName grille qualité
     * @return l'ensemble des projets utilisant cette grille
     * @throws JrafDaoException si erreur
     */
    public Collection findWhereQualityGrid( ISession pSession, String pQualityGridName )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".qualityGrid.name = '" + pQualityGridName + "'";
        return findWhere( pSession, whereClause );
    }

    /**
     * Test d'utilisation de la grille qualité
     * 
     * @param pSession session
     * @param pQualityGridId id de grille qualité
     * @return true si la grille est utilisée par au moins un projet
     * @throws JrafDaoException si erreur
     */
    public boolean isGridUsed( ISession pSession, Long pQualityGridId )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".qualityGrid.id = " + pQualityGridId;
        Collection projects = findWhere( pSession, whereClause );
        return projects.size() > 0;
    }

    /**
     * Retourne les projets dont le source manager est psourceManagerId
     * 
     * @param pSession session
     * @param pSourceManagerId l'id du source manager
     * @throws JrafDaoException si erreur
     * @return une liste de ProjectBO
     */
    public Collection findWhereSourceManager( ISession pSession, Long pSourceManagerId )
        throws JrafDaoException
    {
        SourceManagementBO managerBO = null;
        String whereClause = "where ";
        whereClause += getAlias() + ".sourceManager = " + pSourceManagerId;
        Collection projects = findWhere( pSession, whereClause );
        return projects;
    }

    /**
     * Retourne les projets dont le profil est pProfileId
     * 
     * @param pSession session
     * @param pProfileId l'id du profil
     * @throws JrafDaoException si erreur
     * @return une liste de ProjectBO
     */
    public Collection findWhereProfile( ISession pSession, Long pProfileId )
        throws JrafDaoException
    {
        ProjectProfileBO profileBO = null;
        String whereClause = "where ";
        whereClause += getAlias() + ".profile = " + pProfileId;
        Collection projects = findWhere( pSession, whereClause );
        return projects;
    }

    /**
     * Retourne le liste des projets dont le nom commence par <code>mProjectName</code>, dont l'application commence
     * par <code>mAppliName</code> et qui appartient à la liste <code>pUserAppli</code>
     * 
     * @param pSession session
     * @param pAppliIds les ids des applications de l'utilisateur
     * @param pAppliName le début du nom de l'application
     * @param pProjectName le début du nom du projet
     * @throws JrafDaoException si erreur
     * @return la liste des projets trouvés
     */
    public Collection findProjects( ISession pSession, long[] pAppliIds, String pAppliName, String pProjectName )
        throws JrafDaoException
    {
        Collection projects = null;
        int nbAppli = pAppliIds.length;
        // On vérifie qu'il y a des applications pour éviter
        // une requete inutile
        if ( nbAppli == 0 )
        {
            projects = new ArrayList();
        }
        else
        {
            // On construit la clause where afin que les comparaisons
            // de string ne prennent pas compte de la casse.
            String whereClause = "where ";
            whereClause += "lower(" + getAlias() + ".name) like '" + pProjectName.toLowerCase() + "%'";
            whereClause += " and ";
            whereClause += "lower(" + getAlias() + ".parent.name) like '" + pAppliName.toLowerCase() + "%'";
            whereClause += " and ";
            whereClause += getAlias() + ".parent.id in (";
            // On prend le premier élément sans mettre de virgule,
            // comme ça on est sûr que la clause sera correctement formée.
            whereClause += pAppliIds[0];
            for ( int i = 1; i < nbAppli; i++ )
            {
                whereClause += ", " + pAppliIds[i];
            }
            whereClause += ")";
            projects = findWhere( pSession, whereClause );
        }
        return projects;
    }

    /**
     * @param pSession la session hibernate
     * @param pSiteId l'id du site
     * @param pProfileName le nom du profil
     * @return le nombre de projets pour ce site et ce profil
     * @throws JrafDaoException en cas d'échec
     */
    public int countBySiteAndProfil( ISession pSession, long pSiteId, String pProfileName )
        throws JrafDaoException
    {
        int result = 0;
        // On construit la clause where afin que les comparaisons
        // de string ne prennent pas compte de la casse.
        String whereClause =
            "where " + getAlias() + ".parent.serveurBO.serveurId='" + pSiteId + "' AND " + getAlias()
                + ".profile.name='" + pProfileName + "'";
        result = countWhere( pSession, whereClause ).intValue();
        return result;
    }

    /**
     * Retourne tous les projets ayant le statut <code>pStatus</code> et rattaché à une application <code>pSite</code>
     * 
     * @param pSession la session
     * @param pStatus le status du projet
     * @param pSite le site de l'application
     * @return les projets concernés
     * @throws JrafDaoException si erreur DAO
     */
    public Collection findWhereStatusAndSite( ISession pSession, int pStatus, long pSite )
        throws JrafDaoException
    {
        String whereClause = "where ";
        // le statut
        whereClause += getAlias() + ".status=" + pStatus;
        // le site de l'application
        whereClause += " and (";
        whereClause += getAlias() + ".parent.serveurBO.serveurId='" + pSite + "'";
        whereClause += " or ";
        whereClause += getAlias() + ".parent.serveurBO is null)";
        return findWhere( pSession, whereClause );

    }

    /**
     * @param pSession la session
     * @param pProjectId l'id du projet
     * @param pKey la clé du paramètre à rechercher
     * @return le paramètre du projet de premier niveau de clé <code>pKey</code>
     * @throws JrafDaoException si erreur
     */
    public ProjectParameterBO getParameterWhere( ISession pSession, Long pProjectId, String pKey )
        throws JrafDaoException
    {
        ProjectParameterBO result = null;
        StringBuffer query = new StringBuffer( "select " );
        query.append( "param from ProjectBO as " );
        query.append( getAlias() );
        query.append( " join " );
        query.append( getAlias() );
        query.append( ".parameters.parameters as param " );
        query.append( " where " );
        query.append( getAlias() );
        query.append( ".id=" );
        query.append( pProjectId );
        query.append( " and index(param) = '" );
        query.append( pKey );
        query.append( "'" );
        List results = find( pSession, query.toString() );
        LOG.warn( query.toString() );
        if ( results.size() == 1 )
        { // Il ne peut y avoir qu'un paramètre trouvé
            result = (ProjectParameterBO) results.get( 0 );
        }
        return result;
    }
}
