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
 * Créé le 29 juin 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.squalecommon.daolayer.rule;

import java.util.List;

import org.hibernate.Query;
import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.provider.persistence.hibernate.SessionImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBO;

/**
 * @author M400843
 */
public class QualityRuleDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static QualityRuleDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new QualityRuleDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private QualityRuleDAOImpl()
    {
        initialize( QualityRuleBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static QualityRuleDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Returns the level of the components that are used for the practice corresponding to the given ID
     * 
     * @param session the session
     * @param practiceId the id of the practice
     * @return the name of the component level, e.g. "method" or "class", as it is specified in the quality model
     * @throws JrafDaoException if it is not possible to get the component level
     */
    @SuppressWarnings( "unchecked" )
    public String getComponentLevelForPractice( ISession session, long practiceId )
        throws JrafDaoException
    {
        String componentLevel = "";

        String requete =
            "select formula.componentLevel from AbstractFormulaBO formula, QualityRuleBO rule where rule.formula=formula.id and rule.id="
                + practiceId;
        Query query = ( (SessionImpl) session ).getSession().createQuery( requete );
        List resultList = query.list();
        if ( resultList.size() != 1 )
        {
            throw new JrafDaoException( "Error while trying to identify the component level for the practice "
                + practiceId );
        }
        else
        {
            componentLevel = (String) resultList.get( 0 );
        }

        return componentLevel;
    }
}
