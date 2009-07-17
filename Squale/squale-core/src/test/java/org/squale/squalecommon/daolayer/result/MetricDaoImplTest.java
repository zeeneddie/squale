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
package org.squale.squalecommon.daolayer.result;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.ComponentFactory;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.daolayer.component.AuditDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;

/**
 */
public class MetricDaoImplTest
    extends SqualeTestCase
{

    /**
     * @throws JrafDaoException si erreur
     */
    public void testGetVolumetry()
        throws JrafDaoException
    {
        int volumetry = -1;
        int volumetryBySite = -1;
        ISession session = getSession();
        MetricDAOImpl metricDAO = MetricDAOImpl.getInstance();
        session.beginTransaction();
        ApplicationBO appli = getComponentFactory().createApplicationWithSite( getSession(), "qvi" );
        ProjectProfileBO profil = getComponentFactory().createProjectProfileWithName( getSession(), "java" );
        ProjectBO proj = getComponentFactory().createProject( getSession(), appli, null, profil, null, null );
        PackageBO packageBO = getComponentFactory().createPackage( session, proj );
        ClassBO classBO = getComponentFactory().createClass( session, packageBO );
        MethodBO method = getComponentFactory().createMethod( session, classBO );
        AuditBO audit = getComponentFactory().createAudit( getSession(), proj );
        audit.setStatus( AuditBO.TERMINATED );
        AuditDAOImpl.getInstance().save( getSession(), audit );
        // On créer des mesures
        getComponentFactory().createMeasures( session, audit, proj, classBO, method );
        session.commitTransactionWithoutClose();
        session.beginTransaction();
        volumetry = metricDAO.getVolumetry( session, new Long( proj.getId() ) );
        volumetryBySite = metricDAO.getVolumetryBySite( session, appli.getServeurBO().getServeurId() );
        session.commitTransactionWithoutClose();
        // on doit avoir la meme volumétrie que celle qui est crée par
        // cf getComponentFactory().createComments pour la valeur
        assertTrue( volumetry == ComponentFactory.SLOC );
        assertTrue( volumetryBySite == ComponentFactory.SLOC );
    }

}
