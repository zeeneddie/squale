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
package org.squale.squaleweb.gwt.distributionmap.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.squale.gwt.distributionmap.widget.data.Child;
import org.squale.gwt.distributionmap.widget.data.Parent;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.persistence.hibernate.SessionImpl;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.rule.QualityRuleDAOImpl;
import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.result.MarkDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ComponentType;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBO;
import org.squale.squalecommon.enterpriselayer.facade.component.ComponentFacade;
import org.squale.squalecommon.enterpriselayer.facade.quality.QualityResultFacade;
import org.squale.squaleweb.gwt.distributionmap.client.DataService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings( "serial" )
public class DataServiceImpl
    extends RemoteServiceServlet
    implements DataService
{

    private Date initialDate;

    @SuppressWarnings( "unchecked" )
    public ArrayList<Parent> getData( long auditId, long projectId, long practiceId )
    {
        handleTime();

        ArrayList<Parent> parentList = new ArrayList<Parent>();
        Map<Long, Parent> parentMap = new HashMap<Long, Parent>();

        AuditDTO audit = new AuditDTO();
        audit.setID( auditId );
        ComponentDTO project = new ComponentDTO();
        project.setID( projectId );

        try
        {
            IPersistenceProvider persistenceProvider = PersistenceHelper.getPersistenceProvider();
            ISession session = persistenceProvider.getSession();

            QualityRuleBO ruleBO = (QualityRuleBO) QualityRuleDAOImpl.getInstance().load( session, practiceId );

            Session hibernateSession = ( (SessionImpl) session ).getSession();

            String requete =
                "select formula.componentLevel from AbstractFormulaBO formula, QualityRuleBO rule where rule.formula=formula.id and rule.id="
                    + practiceId;
            Query query = hibernateSession.createQuery( requete );
            List resultList = query.list();
            String componentLevel = (String) resultList.get( 0 );

            Collection<ComponentDTO> components =
                ComponentFacade.getProjectChildren( project, "component." + componentLevel, audit );
            for ( ComponentDTO component : components )
            {
                MarkDTO mark =
                    QualityResultFacade.getPracticeByAuditRuleProject( component.getID(), auditId, practiceId );
                Child child =
                    new Child( component.getID(), component.getName(), ( mark == null ? -1 : mark.getValue() ) );
                attachToParent( parentMap, child, component.getIDParent() );
            }

        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        handleTime();

        parentList.addAll( parentMap.values() );
        return parentList;
    }

    private void attachToParent( Map<Long, Parent> parentMap, Child child, long idParent )
    {
        try
        {
            Parent parent = parentMap.get( idParent );
            if ( parent == null )
            {
                ComponentDTO comp = new ComponentDTO();
                comp.setID( idParent );
                comp = ComponentFacade.get( comp );
                parent = new Parent( comp.getName() );
                parentMap.put( idParent, parent );
            }
            parent.addChild( child );
        }
        catch ( JrafEnterpriseException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void handleTime()
    {
        if ( initialDate == null )
        {
            initialDate = new Date();
        }
        else
        {
            Date current = new Date();
            System.out.println( "Temps d'ex�cution : " + ( current.getTime() - initialDate.getTime() ) );
            initialDate = null;
        }
    }
}
