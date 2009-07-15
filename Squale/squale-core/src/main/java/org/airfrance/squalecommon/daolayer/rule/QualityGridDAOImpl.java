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
package com.airfrance.squalecommon.daolayer.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.SortedSet;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * Persistance d'un grille qualité
 */
public class QualityGridDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Instance singleton
     */
    private static QualityGridDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new QualityGridDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private QualityGridDAOImpl()
    {
        initialize( QualityGridBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static QualityGridDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Création de l'objet métier persistent
     * 
     * @param pSession la session
     * @param pGrid la grille à persister
     * @return la grille créée si tout s'est bien passé
     * @throws JrafDaoException si une erreur à lieu
     */
    public QualityGridBO createGrid( ISession pSession, QualityGridBO pGrid )
        throws JrafDaoException
    {
        // il faut créer les pratiques et critères avant
        QualityRuleDAOImpl ruleDAO = QualityRuleDAOImpl.getInstance();
        SortedSet factors = pGrid.getFactors();
        for ( Iterator itF = factors.iterator(); itF.hasNext(); )
        {
            FactorRuleBO factor = (FactorRuleBO) itF.next();
            // Les critères
            SortedMap criteria = factor.getCriteria();
            for ( Iterator itC = criteria.keySet().iterator(); itC.hasNext(); )
            {
                CriteriumRuleBO criterium = (CriteriumRuleBO) itC.next();
                // Les pratiques
                SortedMap practices = criterium.getPractices();
                for ( Iterator itP = practices.keySet().iterator(); itP.hasNext(); )
                {
                    PracticeRuleBO practice = (PracticeRuleBO) itP.next();
                    ruleDAO.create( pSession, practice );
                }
                ruleDAO.create( pSession, criterium );
            }
        }

        super.create( pSession, pGrid );
        return pGrid;
    }

    /**
     * Obtention de la dernière grille correspondant à un nom
     * 
     * @param pSession session
     * @param pName nom de la grille
     * @return grille correspondante la plus récente ou null si elle n'existe pas
     * @throws JrafDaoException si erreur
     */
    public QualityGridBO findWhereName( ISession pSession, String pName )
        throws JrafDaoException
    {
        QualityGridBO result = null;
        String whereClause = "where ";
        whereClause += getAlias() + ".name = '" + pName + "'";
        whereClause +=
            " AND not exists (from QualityGridBO as oldgrid where " + getAlias()
                + ".name=oldgrid.name and oldgrid.dateOfUpdate > " + getAlias() + ".dateOfUpdate)";
        whereClause += "order by " + getAlias() + ".dateOfUpdate";
        Collection col = findWhere( pSession, whereClause );
        if ( col.size() > 0 )
        {
            // On prend le premier élément de la collection qui peut
            // en contenir plusieurs
            result = (QualityGridBO) col.iterator().next();
        }
        return result;
    }

    /**
     * Récupère les grilles associées à un profil
     * 
     * @param pSession la session
     * @param pProfileId l'id du profil
     * @return les grilles triées par ordre alphabétique
     * @throws JrafDaoException si erreur
     */
    public Collection findWhereProfile( ISession pSession, long pProfileId )
        throws JrafDaoException
    {
        Collection results = new ArrayList();
        String whereClause = "where ";
        whereClause += pProfileId + "in elements(" + getAlias() + ".profiles)";
        whereClause +=
            " AND not exists (from QualityGridBO as oldgrid where " + getAlias()
                + ".name=oldgrid.name and oldgrid.dateOfUpdate > " + getAlias() + ".dateOfUpdate)";
        whereClause += "order by " + getAlias() + ".name";
        return findWhere( pSession, whereClause );
    }

    /**
     * Obtention des grilles
     * 
     * @param pSession session
     * @param pLastVersions indique si seulement les dernières versions sont requises
     * @return grilles qualité triées par nom et par date
     * @throws JrafDaoException si erreur
     */
    public Collection findGrids( ISession pSession, boolean pLastVersions )
        throws JrafDaoException
    {
        String whereClause = "";
        if ( pLastVersions )
        {
            whereClause =
                "where not exists (from QualityGridBO as oldgrid where " + getAlias()
                    + ".name=oldgrid.name and oldgrid.dateOfUpdate > " + getAlias() + ".dateOfUpdate)";
        }
        whereClause += "order by " + getAlias() + ".name," + getAlias() + ".dateOfUpdate";
        Collection col = findWhere( pSession, whereClause );
        return col;
    }

    /**
     * Obtention des grilles sans profil
     * 
     * @param pSession session
     * @return grilles qualité triées par nom sans profil
     * @throws JrafDaoException si erreur
     */
    public Collection findGridsWithoutProfiles( ISession pSession )
        throws JrafDaoException
    {
        String whereClause =
            "where not exists (from QualityGridBO as oldgrid where " + getAlias()
                + ".name=oldgrid.name and oldgrid.dateOfUpdate > " + getAlias() + ".dateOfUpdate)";
        whereClause += " and " + getAlias() + ".profiles.size = 0";
        whereClause += "order by " + getAlias() + ".name";
        Collection col = findWhere( pSession, whereClause );
        return col;
    }

    /**
     * Destruction de grilles
     * 
     * @param pSession session
     * @param gridsId ids des grilles
     * @throws JrafDaoException si erreur
     */
    public void removeGrids( ISession pSession, ArrayList gridsId )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() );
        whereClause.append( ".id in(" );
        Iterator gridsIdIt = gridsId.iterator();
        boolean comma = false;
        // Pracours des ids de grille en vue de leur destruction
        while ( gridsIdIt.hasNext() )
        {
            if ( comma )
            {
                whereClause.append( ", " );
            }
            else
            {
                comma = true;
            }
            whereClause.append( gridsIdIt.next() );
        }
        whereClause.append( ")" );
        Iterator gridsIt = findWhere( pSession, whereClause.toString() ).iterator();
        // Suppression manuelle de chaque grille en attendant
        // le delete cascade en HQL prévu pour Hibernate 3.2.1
        while ( gridsIt.hasNext() )
        {
            remove( pSession, gridsIt.next() );
        }
    }

    /**
     * @param pSession session
     * @param pGridId l'id de la grid qu'on veut récupérer
     * @return la grille ou null si elle n'existe pas
     * @throws JrafDaoException si erreur
     */
    public QualityGridBO loadById( ISession pSession, long pGridId )
        throws JrafDaoException
    {
        QualityGridBO result = null;
        String whereClause = "where " + getAlias() + ".id=" + pGridId;
        Collection col = findWhere( pSession, whereClause );
        // Il ne peut y avoir qu'un seul élément, l'id étant la clé primaire
        if ( col.iterator().hasNext() )
        {
            result = (QualityGridBO) col.iterator().next();
        }
        return result;
    }

    /**
     * This method indicate if the grid associate to the id give in argument is linked to a profile
     * 
     * @param session hibernate session
     * @param gridId id of the grid
     * @return true if the grid associate to the id is linked to a profile
     * @throws JrafDaoException Exception happened during the search in the database
     */
    public boolean hasProfile( ISession session, long gridId )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() );
        whereClause.append( ".id= " );
        whereClause.append( gridId );
        whereClause.append( " AND "  );
        whereClause.append( getAlias() );
        whereClause.append( ".profiles.size > 0" );
        Collection col = findWhere( session, whereClause.toString() );
        return col.size() > 0;
    }

}
