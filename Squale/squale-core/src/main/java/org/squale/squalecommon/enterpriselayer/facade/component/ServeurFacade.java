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
package com.airfrance.squalecommon.enterpriselayer.facade.component;

import com.airfrance.squalecommon.daolayer.config.ServeurDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.transform.config.ServeurTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ServeurBO;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Façade du Serveur d'exécution de Squalix
 */
public class ServeurFacade
{
    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Retourne la liste des serveurs
     * 
     * @return la liste des serveurs
     * @throws JrafEnterpriseException si une exception survient
     */
    public static Collection listeServeurs()
        throws JrafEnterpriseException
    {
        ISession lSession = null;
        Collection lCollBo = new ArrayList();
        Collection lCollDto = new ArrayList();
        try
        {
            lSession = PERSISTENTPROVIDER.getSession();
            lCollBo = (Collection) ServeurDAOImpl.getInstance().listeServeurs( lSession );
            Iterator it = lCollBo.iterator();
            while ( it.hasNext() )
            {
                lCollDto.add( ServeurTransform.bo2dto( (ServeurBO) it.next() ) );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, null );
        }
        finally
        {
            FacadeHelper.closeSession( lSession, null );
        }

        return lCollDto;
    }
}
