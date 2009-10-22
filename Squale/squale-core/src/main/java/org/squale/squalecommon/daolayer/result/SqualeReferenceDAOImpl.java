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
 */
package org.squale.squalecommon.daolayer.result;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.DAOMessages;
import org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.SqualeReferenceBO;

/**
 * @author M400843
 */
public class SqualeReferenceDAOImpl
    extends AbstractDAOImpl
{

    /** log */
    private static Log LOG;

    /**
     * Instance singleton
     */
    private static SqualeReferenceDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new SqualeReferenceDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private SqualeReferenceDAOImpl()
    {
        initialize( SqualeReferenceBO.class );
        if ( null == LOG )
        {
            LOG = LogFactory.getLog( SqualeReferenceDAOImpl.class );
        }
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static SqualeReferenceDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Permet de récupérer un nombre de SqualeReference définis
     * 
     * @param pSession session Hibernate
     * @param pNbLignes nombre d'audits, si <code>null</code> toutes les references seront remontés
     * @param pIndexDepart index de départ
     * @param pIsAdmin un booléen indiquant si l'utilisateur est admin et donc si on doit récupérer tout le référentiel
     *            sans prendre en compte l'attribut masqué
     * @param pUserId l'id de l'utilisateur
     * @return Collection de SqualeReference
     * @throws JrafDaoException exception DAO
     */
    public Collection findWhereScrollable( ISession pSession, Integer pNbLignes, Integer pIndexDepart,
                                           boolean pIsAdmin, Long pUserId )
        throws JrafDaoException
    {
        Collection result = null;
        StringBuffer whereClause = new StringBuffer();
        // si l'utilisateur est admin on récupère tout le référentiel sans
        // prendre en compte le champ masqué, sinon on récupère que les non-masqués
        // ou les masquées mais dont l'utilisateur est admin
        if ( !pIsAdmin )
        {
            whereClause.append( "where " );
            whereClause.append( getAlias() );
            whereClause.append( ".hidden=0" );
            whereClause.append( " or (" );
            whereClause.append( getAlias() );
            whereClause.append( ".hidden=1 and " );
            whereClause.append( getAlias() );
            whereClause.append( ".applicationName in (" );

            // select pour récupérer les noms des applications pour lesquelles l'utilisateur a les
            // droits de lecture
            StringBuffer selectIn = new StringBuffer( "select a.name from ApplicationBO a, UserBO u where u.id=" );
            selectIn.append( pUserId );
            selectIn.append( " and u.rights[a.id] is not null and u.rights[a.id].name='" );
            selectIn.append( ProfileBO.MANAGER_PROFILE_NAME );

            whereClause.append( selectIn );
            whereClause.append( "'))" );
        }
        if ( ( null == pNbLignes ) || ( null == pIndexDepart ) )
        {
            result = findWhere( pSession, whereClause.toString() );
        }
        else
        {
            result =
                (Collection) super.findWhereScrollable( pSession, whereClause.toString(), pNbLignes.intValue(),
                                                        pIndexDepart.intValue(), false );
        }
        return result;
    }

    /**
     * Renvoie la ligne du referentiel correspondant à application/projet (par nom)
     * 
     * @param pSession la session
     * @param pAppName le nom de l'application recherché
     * @param pProjName le nom du projet recherché
     * @return la ligne de referentiel du projet correspondant
     * @throws JrafDaoException si une erreur à lieu
     */
    public SqualeReferenceBO loadByName( ISession pSession, String pAppName, String pProjName )
        throws JrafDaoException
    {
        SqualeReferenceBO ref = null;
        String whereClause = "where ";
        whereClause += getAlias() + ".applicationName = '" + pAppName + "'";
        whereClause += " and " + getAlias() + ".projectName = '" + pProjName + "'";

        // pour un nom d'application,projet, recupere le SqualeReferenceBO
        Collection col = findWhere( pSession, whereClause );
        // qui doit etre unique !
        if ( col.size() == 1 )
        {
            ref = (SqualeReferenceBO) col.iterator().next();
        }
        else if ( col.size() > 1 )
        {
            throw new JrafDaoException( "Too many line in referentiel" );
        }
        return ref;
    }

    /**
     * @param pSession la session
     * @param pAppliName le nom de l'application recherchée
     * @return l'objet référence correspondant au projet si il existe, null sinon il ne peut pas y avoir de doublons
     * @throws JrafDaoException en cas d'échec
     */
    public SqualeReferenceBO findReferenceByAppliName( ISession pSession, String pAppliName )
        throws JrafDaoException
    {
        SqualeReferenceBO result = null;
        Collection coll = findWhere( pSession, getByAppliNameClause(pAppliName));
        // il peut y avoir plusieurs résultats, en fait un par projet de l'application
        // mais on ne veut récupérer que des informations communes à tous les projets
        // type de l'audit, date de l'audit...
        // donc on n'en renvoie qu'un seul
        if ( coll != null && coll.size() >= 1 )
        {
            result = (SqualeReferenceBO) coll.iterator().next();
        }
        return result;
    }
    /**
     * @param pSession la session
     * @param pAppliName le nom de l'application recherchée
     * @return les objets référence correspondant à l'application si elle existe, null sinon
     * @throws JrafDaoException en cas d'échec
     */
    public Collection findReferencesByAppliName(ISession pSession, String pAppliName) throws JrafDaoException {
        return findWhere(pSession, getByAppliNameClause(pAppliName));
    }
    
    /**
     * @param pAppliName application's name
     * @return where clause with application's name condition
     */
    private String getByAppliNameClause(String pAppliName) {
        StringBuffer whereClause = new StringBuffer("where ");
        whereClause.append(getAlias());
        whereClause.append(".applicationName = '");
        whereClause.append(pAppliName);
        whereClause.append("'");
        return whereClause.toString();
    }


    /**
     * Remonte toutes les applications distinctes stockées dans le référentiel
     * 
     * @param pSession la session
     * @return la liste des noms
     * @throws JrafDaoException en cas d'échec
     */
    public Collection findAllDistinctAppliName( ISession pSession )
        throws JrafDaoException
    {
        Collection result = new ArrayList( 0 );
        // Recupération du nom de classe et de l'alias pour le Application
        int index = SqualeReferenceBO.class.getName().lastIndexOf( "." );
        String className = getBusinessClass().getName().substring( index + 1 );
        // On fait un distinct sur les applications pour ne pas compter plusieurs fois
        // celles qui ont plusieurs projets
        String query =
            "select distinct " + getAlias() + ".applicationName " + " from " + className + " as " + getAlias();
        result = find( pSession, query );
        return result;
    }

}
