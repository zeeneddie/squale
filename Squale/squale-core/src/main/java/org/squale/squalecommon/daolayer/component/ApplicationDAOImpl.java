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
package org.squale.squalecommon.daolayer.component;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.provider.persistence.hibernate.SessionImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.DAOMessages;
import org.squale.squalecommon.daolayer.DAOUtils;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;

/**
 * DAO pour les applications
 */
public class ApplicationDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static ApplicationDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static
    {
        instance = new ApplicationDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private ApplicationDAOImpl()
    {
        initialize( ApplicationBO.class );
        if ( null == LOG )
        {
            LOG = LogFactory.getLog( ApplicationDAOImpl.class );
        }
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static ApplicationDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Récupère toutes les applications non supprimées triées par ordre alphabétique insensible à la casse
     * 
     * @param pSession la session
     * @return la liste d'applications
     * @throws JrafDaoException si erreur hibernate
     */
    public List findNotDeleted( ISession pSession )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        // On ne récupère pas les applications supprimées
        whereClause.append( getAlias() );
        whereClause.append( ".status !=" );
        whereClause.append( ApplicationBO.DELETED );
        // On beut le tri insensible à la casse
        whereClause.append( " order by LOWER(" );
        whereClause.append( getAlias() );
        whereClause.append( ".name)" );
        return findWhere( pSession, whereClause.toString() );
    }

    /**
     * Permet de connaitre le nombre d'applications ayant comme nom pName
     * 
     * @param pSession la session
     * @param pName un nom
     * @return un nombre d'applications
     * @throws JrafDaoException si une erreur à lieu
     */
    public int countWhereName( ISession pSession, String pName )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".name = '" + pName + "'";

        int ret = countWhere( pSession, whereClause ).intValue();
        return ret;
    }

    /**
     * Permet de connaitre le nombre d'applications (validées ou à valider) pour un site
     * 
     * @param pSession la session
     * @param pSiteId l'id du site
     * @return le nombre d'applications de ce site
     * @throws JrafDaoException si une erreur à lieu
     */
    public int countWhereSite( ISession pSession, long pSiteId )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() );
        whereClause.append( ".serveurBO.serveurId = '" );
        whereClause.append( pSiteId );
        whereClause.append( "'" );
        // On ne compte pas les applications supprimées
        whereClause.append( " and " );
        whereClause.append( getAlias() );
        whereClause.append( ".status !=" );
        whereClause.append( ApplicationBO.DELETED );
        int ret = countWhere( pSession, whereClause.toString() ).intValue();
        return ret;
    }

    /**
     * Permet de connaitre le nombre d'applications ayant eu un audit exécuté depuis une date donné
     * 
     * @param pSession la session
     * @param pSiteId l'id du site auquel doit appartenir l'application
     * @param pStatus le status des audits (null si on ne tient pas compte du status)
     * @param pBeginningDate une date. On comptabilise les audits dont la date d'exécution est postérieure à celle ci
     * @return le nombre d'applications de ce site
     * @throws JrafDaoException si une erreur à lieu
     */
    public int countWhereHaveAuditByStatus( ISession pSession, long pSiteId, Integer pStatus, Date pBeginningDate )
        throws JrafDaoException
    {
        // Recupération du nom de classe et de l'alias pour le Application
        int index = ApplicationBO.class.getName().lastIndexOf( "." );
        String classAuditName = AuditBO.class.getName().substring( index + 1 );
        String auditAlias = classAuditName.toLowerCase();
        index = getBusinessClass().getName().lastIndexOf( "." );
        String className = getBusinessClass().getName().substring( index + 1 );

        // On fait un distinct sur les applications pour ne pas compter plusieurs fois
        // celles qui ont plusieurs audits et on fait une jointure sur les audits
        String query =
            "select distinct " + getAlias() + ".id " + " from " + classAuditName + " as " + auditAlias + ", "
                + className + " as " + getAlias() + " ";

        int result = -1;
        String whereClause = "where ";
        whereClause += getAlias() + " in elements(" + auditAlias + ".components) AND ";
        whereClause += getAlias() + ".serveurBO.serveurId = '" + pSiteId + "'";
        // la date peut etre null dans le cas d'un test unitaire par exemple car
        // on ne peut pas tester sur la date, to_date est réservé à Oracle
        if ( pBeginningDate != null )
        {
            whereClause += " AND " + auditAlias + ".date >= " + DAOUtils.makeQueryDate( pBeginningDate );
        }
        int intStatus = pStatus.intValue();
        if ( intStatus != AuditBO.ALL_STATUS )
        {
            whereClause += " AND ";
            whereClause += auditAlias + ".status = '" + intStatus + "'";
        }
        Collection coll = find( pSession, query + whereClause );
        return coll.size();
    }

    /**
     * Retourne les projets taggés par un tag donné
     * 
     * @param pSession une session hibernate
     * @param ptagIds tableau d'ids des Tags demandés
     * @return une collection de projets
     * @throws JrafDaoException si une erreur à lieu
     */
    public Collection findtagged( ISession pSession, Long[] ptagIds )
        throws JrafDaoException
    {
        String whereClause = "where ";
        if ( ptagIds.length > 1 )
        {
            whereClause += ptagIds[0] + " in elements(" + getAlias() + ".tags)";
            for ( int i = 1; i < ptagIds.length; i++ )
            {
                whereClause += " and " + ptagIds[i] + " in elements(" + getAlias() + ".tags)";
            }
        }
        else
        {
            whereClause += ptagIds[0] + " in elements(" + getAlias() + ".tags)";
        }

        Collection ret = findWhere( pSession, whereClause );
        return ret;
    }

    /**
     * Permet de connaitre les applications pour un site
     * 
     * @param pSession la session
     * @param pSiteId l'id du site
     * @return les applications de ce site
     * @throws JrafDaoException si une erreur à lieu
     */
    public Collection findWhereSite( ISession pSession, long pSiteId )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".serveurBO.serveurId = '" + pSiteId + "'";
        Collection ret = findWhere( pSession, whereClause );
        return ret;
    }

    /**
     * Suppression de tout les objets projets correspondant aux pIdApplications
     * 
     * @param pSession la session
     * @param pIdApplications une collection d'ids (Long) de projets
     * @throws JrafDaoException si une erreur à lieu
     */
    public void removeAll( ISession pSession, Collection pIdApplications )
        throws JrafDaoException
    {
        Iterator itIdApplication = pIdApplications.iterator();
        while ( itIdApplication.hasNext() )
        {
            Long idApplication = (Long) itIdApplication.next();
            ApplicationBO project = (ApplicationBO) load( pSession, idApplication );
            remove( pSession, project );
        }
    }

    /**
     * Permet de recuperer tous les projets validés ou non
     * 
     * @param pSession session Hibernate
     * @param pStatus champ de status d'un projet
     * @return une liste d'applications ayant comme statut pStatus
     * @throws JrafDaoException si une erreur à lieu
     */
    public List findWhereStatus( ISession pSession, int pStatus )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".status = " + pStatus;
        whereClause += " order by lower(" + getAlias() + ".name)";
        LOG.debug( "query(findWhereStatus) = " + whereClause );
        List ret = (List) findWhere( pSession, whereClause );
        return ret;
    }

    /**
     * Permet de recuperer tous les projets ayant comme status pStatusArray
     * 
     * @param pSession session Hibernate
     * @param pStatusArray array de status d'un projet
     * @return une liste d'applications ayant comme statut pStatusArray
     * @throws JrafDaoException si une erreur à lieu
     */
    public List findWhereStatus( ISession pSession, int[] pStatusArray )
        throws JrafDaoException
    {
        if ( ( pStatusArray == null ) || ( pStatusArray.length == 0 ) )
        {
            LOG.error( "pStatusArray est vide!" );
        }
        StringBuffer inClause = new StringBuffer();
        for ( int i = 0; i < pStatusArray.length; i++ )
        {
            if ( i > 0 )
            {
                inClause.append( ", " );
            }
            inClause.append( pStatusArray[i] );
        }
        String whereClause = "where ";
        whereClause += getAlias() + ".status in (" + inClause.toString() + ")";
        whereClause += " order by lower(" + getAlias() + ".name)";
        LOG.info( "query(findWhereStatus) = " + whereClause );
        List ret = (List) findWhere( pSession, whereClause );
        return ret;
    }

    /**
     * Permet de recuperer tous les projets ayant un caractère public
     * 
     * @param pSession session Hibernate
     * @param pAppliIds la liste des ids des applications à ne pas prendre en compte
     * @return une liste d'applications ayant comme statut pStatus
     * @throws JrafDaoException si une erreur à lieu
     */
    public List findPublic( ISession pSession, String pAppliIds )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".public = true and " + getAlias() + ".status = " + ApplicationBO.VALIDATED;
        // Si il n'y a pas d'ids d'application, pas la peine de faire
        if ( pAppliIds.length() > 0 )
        {
            whereClause += " and ";
            whereClause += getAlias() + ".id not in (" + pAppliIds + ")";
        }
        whereClause += " order by lower(" + getAlias() + ".name)";
        List ret = (List) findWhere( pSession, whereClause );
        return ret;
    }

    /**
     * Création ou sauvegarde de l'objet en fonction du paramètrage défini dans le fichier de mapping Vérifie l'unicité
     * du nom du projet
     * 
     * @param pSession la session
     * @param pApplication le projet à persister ou à mettre à jour
     * @return le projet créé ou mis à jour si tout s'est bien passé
     * @throws JrafDaoException si une erreur à lieu
     */
    public ApplicationBO save( ISession pSession, ApplicationBO pApplication )
        throws JrafDaoException
    {
        Long idApplication = new Long( pApplication.getId() );
        ApplicationBO newApplication = null;

        // si le projet n'existe pas, il se peut qu'un autre projet existe deja avec le meme nom
        if ( ( null != get( pSession, idApplication ) ) || ( 0 == countWhereName( pSession, pApplication ) ) )
        {
            super.save( pSession, pApplication );
            newApplication = pApplication;
        }
        return newApplication;
    }

    /**
     * Création de l'objet métier persistent
     * 
     * @param pSession la session
     * @param pApplication le sous-projet à persister
     * @return le projet créé si tout s'est bien passé
     * @throws JrafDaoException si une erreur à lieu
     */
    public ApplicationBO create( ISession pSession, ApplicationBO pApplication )
        throws JrafDaoException
    {
        ApplicationBO newApplication = null;

        // Verification que le nom de l'application n'existe pas déja
        int existantApplication = countWhereName( pSession, pApplication );
        if ( 0 == existantApplication )
        {
            super.create( pSession, pApplication );
            newApplication = pApplication;
        }
        return newApplication;
    }

    /**
     * Retourne le nombre d'application du nom de pApplication et qui n'est pas à supprimer
     * 
     * @param pSession la session
     * @param pApplication l'application
     * @return le nombre d'applications trouvées (théoriquement 0 ou 1)
     * @throws JrafDaoException si une erreur à lieu
     */
    public int countWhereName( ISession pSession, ApplicationBO pApplication )
        throws JrafDaoException
    {
        String whereClause = "where ";
        // porte le même nom
        whereClause += getAlias() + ".name = '" + pApplication.getName() + "'";
        whereClause += " and ";
        // et qui n'est pas à supprimer
        whereClause += getAlias() + ".status != '" + ApplicationBO.DELETED + "'";

        int ret = countWhere( pSession, whereClause ).intValue();
        return ret;
    }

    /**
     * Supprime pApplication ainsi que tous ses fils, leurs mesures, leurs erreurs, leurs résultats et leurs notes
     * 
     * @param pSession la session
     * @param pApplication le composant
     * @throws JrafDaoException si une erreur à lieu
     */
    public void remove( ISession pSession, ApplicationBO pApplication )
        throws JrafDaoException
    {
        // Supprime "logiquement" les audits du projet
        AuditDAOImpl auditDAO = AuditDAOImpl.getInstance();
        if ( null != pApplication.getAudits() )
        {
            Iterator itAudits = pApplication.getAudits().iterator();
            while ( itAudits.hasNext() )
            {
                AuditBO audit = (AuditBO) itAudits.next();
                auditDAO.remove( pSession, audit );
            }
        }
        // Supprime "logiquement" le projet lui-même
        pApplication.setStatus( ApplicationBO.DELETED );
        super.save( pSession, pApplication );
        // il faut faire (en batch) la suppresion physique !
    }

    /**
     * @param pSession la session
     * @param pName le nom du projet recherché
     * @return le projet ayant pour nom pName
     * @throws JrafDaoException si une erreur à lieu
     */
    public ApplicationBO loadByName( ISession pSession, String pName )
        throws JrafDaoException
    {
        ApplicationBO project = null;
        String whereClause = "where ";
        whereClause += getAlias() + ".name = '" + pName + "'";

        // pour un nom d'applications, il ne doit exister 0 ou 1 projet
        Collection col = findWhere( pSession, whereClause );
        if ( col.size() == 1 )
        {
            project = (ApplicationBO) col.iterator().next();
        }
        else if ( col.size() > 1 )
        {
            String tab[] = { pName };
            LOG.warn( DAOMessages.getString( "application.many.name", tab ) );
        }
        return project;
    }

    /**
     * Retourne l'application en relation avec l'audit
     * 
     * @param pSession une session hibernate
     * @param pAuditId l'id de l'audit
     * @return un projet
     * @throws JrafDaoException si une erreur à lieu
     */
    public ApplicationBO loadByAuditId( ISession pSession, Long pAuditId )
        throws JrafDaoException
    {
        ApplicationBO app = null;
        SessionImpl sessionHibernate = (SessionImpl) pSession;
        try
        {
            String fullquery =
                "SELECT app_comp.ComponentId" + ", app_comp.Excluded" + ", app_comp.Justification" + ", app_comp.Name"
                    + ", app_comp.Parent" + ", app_comp.ProjectId" + ", app_comp.AuditFrequency"
                    + ", app_comp.ResultsStorageOptions" + ", app_comp.Status" + ", app_comp.PublicApplication"
                    + ", app_comp.LastUpdate" + ", app_comp.EXTERNAL_DEV" + ", app_comp.IN_PRODUCTION"
                    + ", app_comp.lastUser" + ", app_comp.Serveur" + ", app_comp.QualityApproachOnStart"
                    + ", app_comp.InInitialDev" + ", app_comp.GlobalCost" + ", app_comp.DevCost"
                    + " FROM Component app_comp";
            fullquery +=
                " WHERE app_comp.subclass = 'Application'" + " AND EXISTS "
                    + "( SELECT 'X' FROM Components_Audits compaudits"
                    + " WHERE app_comp.ComponentId = compaudits.ComponentId AND compaudits.AuditId = ?)";
            Query q = sessionHibernate.getSession().createSQLQuery( fullquery ).addEntity( ApplicationBO.class );
            q.setLong( 0, pAuditId );
            app = (ApplicationBO) q.uniqueResult();
        }
        catch ( HibernateException e )
        {
            throw new JrafDaoException( e );
        }
        return app;
    }

    /**
     * @param pSession la session hibernate
     * @param pSiteId le nom du site (si null sur tous les sites)
     * @return le nombre d'applications validées n'ayant aucun audits (ou que des audits programmés) pour ce site
     * @throws JrafDaoException en cas d'échec
     */
    public int countWhereHaveNoAudits( ISession pSession, long pSiteId )
        throws JrafDaoException
    {
        // Recupération du nom de classe et de l'alias pour le Application
        int index = ApplicationBO.class.getName().lastIndexOf( "." );
        String classAuditName = AuditBO.class.getName().substring( index + 1 );
        String auditAlias = classAuditName.toLowerCase();
        index = getBusinessClass().getName().lastIndexOf( "." );
        String className = getBusinessClass().getName().substring( index + 1 );

        // La clause where: Application validée mais sans audit réussi ou en échec
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() );
        whereClause.append( ".status=1" );
        if ( pSiteId != 0 )
        {
            whereClause.append( " and " );
            whereClause.append( getAlias() );
            whereClause.append( ".serveurBO.serveurId='" );
            whereClause.append( pSiteId );
            whereClause.append( "'" );
        }

        // Clause pour savoir si il n'y a que des audits en attente --> il n'y a pas
        // eu d'exécution d'audits sur cette application
        whereClause.append( " and not exists( select " );
        whereClause.append( auditAlias );
        whereClause.append( ".status from " );
        whereClause.append( classAuditName );
        whereClause.append( " as " );
        whereClause.append( auditAlias );
        whereClause.append( " where " );
        whereClause.append( auditAlias );
        whereClause.append( " in elements (" );
        whereClause.append( getAlias() );
        whereClause.append( ".audits)" );
        whereClause.append( " and " );
        whereClause.append( auditAlias );
        whereClause.append( ".status!=" );
        whereClause.append( AuditBO.NOT_ATTEMPTED );
        whereClause.append( ")" );
        return countWhere( pSession, whereClause.toString() ).intValue();
    }

    /**
     * @param pSession la session hibernate
     * @param pSiteId l'id du site (si null sur tous les sites)
     * @return le nombre d'applications à valider pour ce site
     * @throws JrafDaoException en cas d'échec
     */
    public int countNotValidate( ISession pSession, long pSiteId )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".status = '" + ApplicationBO.IN_CREATION + "'";
        if ( pSiteId != 0 )
        {
            whereClause += " And " + getAlias() + ".serveurBO.serveurId='" + pSiteId + "'";
        }
        int ret = countWhere( pSession, whereClause ).intValue();
        return ret;
    }

    /**
     * Permet de connaitre le nombre d'applications par site n'ayant que des audits de type <code>pStatus</code>
     * 
     * @param pSession la session
     * @param pSiteId l'id du site auquel doit appartenir l'application
     * @return le nombre d'applications correspondant
     * @throws JrafDaoException si une erreur à lieu
     */
    public int countWhereHaveOnlyFailedAudit( ISession pSession, long pSiteId )
        throws JrafDaoException
    {
        // Recupération du nom de classe et de l'alias pour le Application
        int index = AuditBO.class.getName().lastIndexOf( "." );
        String classAuditName = AuditBO.class.getName().substring( index + 1 );
        String auditAlias = classAuditName.toLowerCase();
        index = getBusinessClass().getName().lastIndexOf( "." );
        String className = getBusinessClass().getName().substring( index + 1 );

        // Requête de jointure avec les audits du composants
        StringBuffer selectWhereAuditIn = new StringBuffer( " select " );
        selectWhereAuditIn.append( auditAlias );
        selectWhereAuditIn.append( ".status from " );
        selectWhereAuditIn.append( classAuditName );
        selectWhereAuditIn.append( " as " );
        selectWhereAuditIn.append( auditAlias );
        selectWhereAuditIn.append( " where " );
        selectWhereAuditIn.append( auditAlias );
        selectWhereAuditIn.append( " in elements (" );
        selectWhereAuditIn.append( getAlias() );
        selectWhereAuditIn.append( ".audits)" );

        // La clause where: il y a pas d'audits terminés mais au moins un audit en échec ou partiel
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() );
        whereClause.append( ".serveurBO.serveurId='" );
        whereClause.append( pSiteId );
        whereClause.append( "' and " );
        whereClause.append( getAlias() );
        whereClause.append( ".status=1 and " );

        // Clause pour savoir si il n'y a pas d'audit réussi
        whereClause.append( "not exists(" );
        whereClause.append( selectWhereAuditIn );
        whereClause.append( " and " );
        whereClause.append( auditAlias );
        whereClause.append( ".status=" );
        whereClause.append( AuditBO.TERMINATED );

        // Clause pour savoir si on a au moins un audit partiel ou en échec
        whereClause.append( ") and exists(" );
        whereClause.append( selectWhereAuditIn );
        whereClause.append( " and (" );
        whereClause.append( auditAlias );
        whereClause.append( ".status=" );
        whereClause.append( AuditBO.FAILED );
        whereClause.append( " or " );
        whereClause.append( auditAlias );
        whereClause.append( ".status=" );
        whereClause.append( AuditBO.PARTIAL );
        whereClause.append( "))" );
        LOG.debug( "countWhereHaveOnlyFailedAudit: " + whereClause.toString() );
        return countWhere( pSession, whereClause.toString() ).intValue();
    }

    /**
     * Récupère les applications du site <code>pSiteId</code> qui ont un audit de programmé et dont la fréquence peut
     * être changée (--> >0)
     * 
     * @param pSession la session
     * @param pSiteId l'id du site
     * @return les applications ayant les critères
     * @throws JrafDaoException si erreur
     */
    public Collection findWhereHaveNotAttemptedAuditBySite( ISession pSession, long pSiteId )
        throws JrafDaoException
    {
        // Recupération du nom de classe et de l'alias pour la jointure avec les audits
        int index = AuditBO.class.getName().lastIndexOf( "." );
        String classAuditName = AuditBO.class.getName().substring( index + 1 );
        String auditAlias = classAuditName.toLowerCase();

        StringBuffer whereClause = new StringBuffer( " where " );
        // Le serveur
        whereClause.append( getAlias() );
        whereClause.append( ".serveurBO.serveurId=" );
        whereClause.append( pSiteId );
        whereClause.append( " and " );
        whereClause.append( getAlias() );
        // c'est une application qui a une fréquence pour des audits de suivi (--> >0)
        whereClause.append( ".auditFrequency>0" );
        whereClause.append( " and " );
        // A un audit programmé

        // Requête de jointure avec les audits du composants
        StringBuffer selectWhereAuditIn = new StringBuffer( "(select " );
        selectWhereAuditIn.append( auditAlias );
        selectWhereAuditIn.append( ".status from " );
        selectWhereAuditIn.append( classAuditName );
        selectWhereAuditIn.append( " as " );
        selectWhereAuditIn.append( auditAlias );
        selectWhereAuditIn.append( " where " );
        selectWhereAuditIn.append( auditAlias );
        selectWhereAuditIn.append( " in elements (" );
        selectWhereAuditIn.append( getAlias() );
        selectWhereAuditIn.append( ".audits))" );

        whereClause.append( AuditBO.NOT_ATTEMPTED );
        whereClause.append( " in " );
        whereClause.append( selectWhereAuditIn );
        return findWhere( pSession, whereClause.toString() );
    }

    /**
     * This method retrieves each application available for the shared repository. That means that these applications
     * have at least one successfull audit and are not hide. This method return a list of array. Each array represent
     * one available application
     * 
     * @param session The hibernate session
     * @return A list of array [application technical id (Long), application name (String)]
     * @throws JrafDaoException Exception occurs during the search in the database
     */
    public List<Object[]> getAvailableForSharedRepository( ISession session )
        throws JrafDaoException
    {
        List<Object[]> listToReturn = null;
        StringBuffer request = new StringBuffer( "Select app.id, app.name from ApplicationBO app where " );
        request.append( "app.id in (select app2.id from ApplicationBO app2, AuditBO audit where audit.components.id = app2.id and audit.status = " );
        request.append( AuditBO.TERMINATED );
        request.append( " ) and app.userList is not empty and app.status = " );
        request.append( ApplicationBO.VALIDATED );
        listToReturn = find( session, request.toString() );
        return listToReturn;
    }

    /**
     * This method retrieve all the aplications which has at least one succesful audit and is public or the user given
     * in arguments has rights on it
     * 
     * @param session The hibernate session
     * @param userId The user id
     * @return the list of applications visible for the user given in argument
     * @throws JrafDaoException Exceptions occurs during the search
     */
    public List<Object[]> getVisibleApplicationForUser( ISession session, long userId )
        throws JrafDaoException
    {
        List<Object[]> listToReturn = null;
        StringBuffer request = new StringBuffer( "Select app.id, app.name from ApplicationBO app where " );
        request.append( "(app.id in (select app2.id from ApplicationBO app2, AuditBO audit where audit.components.id = app2.id and audit.status = " );
        request.append( AuditBO.TERMINATED );
        request.append( " and ( app.status = ");
        request.append( ApplicationBO.VALIDATED );
        request.append( " ) and ( app.userList.id = " );
        request.append( userId );
        request.append( " or ( app.public = true and app.userList is not empty )))) " );
        listToReturn = find( session, request.toString() );
        return listToReturn;
    }
    
    /**
     * This method establish if the application given in argument is public 
     * 
     * @param session The hibernate session
     * @param applicationId The application thecnical Id
     * @return True if the application is public
     * @throws JrafDaoException exception accurs during the search
     */
    public boolean isPublic( ISession session, long applicationId )
        throws JrafDaoException
    {
        StringBuffer request = new StringBuffer( "Select app.public from ApplicationBO app where " );
        request.append( " app.id = " );
        request.append( applicationId );
        List<Boolean> listToReturn = find( session, request.toString() );
        return listToReturn.get( 0 );   
    }

}