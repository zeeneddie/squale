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

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.AuditDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * Test de la facade d'audit
 */
public class AuditFacadeTest
    extends SqualeTestCase
{

    /**
     * Test pour void delete(AuditDTO)
     */
    public void testInsertAuditDTO()
    {
        // Insertion de l'application a manipuler
        ISession session;
        try
        {
            session = getSession();
            session.beginTransaction();
            ApplicationBO application = getComponentFactory().createApplication( session );
            QualityGridBO grid = getComponentFactory().createGrid( session );
            ProjectBO project = getComponentFactory().createProject( session, application, grid );
            session.commitTransactionWithoutClose();
            // Insertion d'un audit
            AuditDTO audit = new AuditDTO();
            audit.setApplicationId( application.getId() );
            audit.setStatus( AuditBO.NOT_ATTEMPTED );
            audit.setType( AuditBO.NORMAL );
            audit = AuditFacade.insertAudit( audit );

            assertEquals( AuditBO.NOT_ATTEMPTED, audit.getStatus() );
            assertEquals( AuditBO.NORMAL, audit.getType() );

            session.beginTransaction();
            assertEquals( 1, AuditDAOImpl.getInstance().count( session ).intValue() );
            AuditBO auditBO = (AuditBO) AuditDAOImpl.getInstance().load( session, audit.getID() );
            session.commitTransactionWithoutClose();

            assertEquals( AuditBO.NOT_ATTEMPTED, auditBO.getStatus() );
            assertEquals( AuditBO.NORMAL, auditBO.getType() );

        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test pour void delete(AuditDTO)
     */
    public void testDeleteAuditDTO()
    {
        // Insertion de l'application a manipuler
        ISession session;
        try
        {
            session = getSession();
            ApplicationBO application = getComponentFactory().createApplication( session );
            QualityGridBO grid = getComponentFactory().createGrid( session );
            ProjectBO project = getComponentFactory().createProject( session, application, grid );
            // Insertion d'un audit
            AuditDTO audit = new AuditDTO();
            audit.setApplicationId( application.getId() );
            audit = AuditFacade.insertAudit( audit );
            assertEquals( 1, AuditDAOImpl.getInstance().count( session ).intValue() );
            AuditFacade.delete( audit );
            AuditBO auditBO = (AuditBO) AuditDAOImpl.getInstance().load( session, audit.getID() );
            assertEquals( AuditBO.DELETED, auditBO.getStatus() );
            assertEquals( 1, AuditDAOImpl.getInstance().count( session ).intValue() );
            session.closeSession();
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

}
