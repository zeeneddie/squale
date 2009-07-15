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
//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\org\\squale\\squalecommon\\enterpriselayer\\applicationcomponent\\ApplicationAdminApplicationComponentAccess.java

package org.squale.squalecommon.enterpriselayer.applicationcomponent.administration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.datatransfertobject.tag.TagDTO;
import org.squale.squalecommon.enterpriselayer.facade.component.ApplicationFacade;
import org.squale.squalecommon.enterpriselayer.facade.component.ProjectFacade;

/**
 * <p>
 * Title : ApplicationAdminApplicationComponentAccess.java
 * </p>
 * <p>
 * Description : Application component projet Administration
 * </p>
 */
public class ProjectAdminProjectComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * log
     */
    private static final Log LOG = LogFactory.getLog( ProjectAdminProjectComponentAccess.class );

    /**
     * persistence provider
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Default construcor
     * 
     * @roseuid 42CBFC010285
     */
    public ProjectAdminProjectComponentAccess()
    {
    }
    
    /**
     * Adds a tag on the current project
     * 
     * @param pProjectId project id
     * @param pTag the tag to add
     * @throws JrafEnterpriseException if an error occurs
     */
    public void addTag ( Long pProjectId, TagDTO pTag )
        throws JrafEnterpriseException
    {
        try
        {
            ISession session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            ProjectFacade.addTag( session, pProjectId, pTag );
            session.commitTransaction();
        }
        catch ( JrafDaoException e )
        {
            LOG.fatal( e, e );
        }
    }
    
    /**
     * removes a tag from a project
     * 
     * @param pProjectId project accessed
     * @param pTag The tag that will be removed from the project
     * @throws JrafEnterpriseException if an error occurs
     */
    public void removeTag ( Long pProjectId, TagDTO pTag )
        throws JrafEnterpriseException
    {
        try
        {
            ISession session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            ProjectFacade.removeTag( session, pProjectId, pTag );
            session.commitTransaction();
        }
        catch ( JrafDaoException e )
        {
            LOG.fatal( e, e );
        }
    }
}
