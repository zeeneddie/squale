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
package org.squale.squaleexport.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.datatransfertobject.config.AdminParamsDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.AdminParamsBO;
import org.squale.squalecommon.util.mail.IMailerProvider;
import org.squale.squaleexport.core.ExporterFactory;
import org.squale.squaleexport.core.IExporter;

/**
 * 
 *
 */
public class Launch
{

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * 
     */
    public void exec()
    {

        ISession session = null;
        IMailerProvider mailer = null;

        // session = PERSISTENTPROVIDER.getSession();
        IExporter exporter = ExporterFactory.createExporter( PERSISTENTPROVIDER);
        HashMap<Long, Long> applicationsAudit = new HashMap<Long, Long>();
        applicationsAudit.put( 3182L, 511L );
        applicationsAudit.put( 1L, 664L);

        List<AdminParamsDTO> listMapping = new ArrayList<AdminParamsDTO>();
        AdminParamsDTO param = new AdminParamsDTO();
        String metricKey = AdminParamsBO.MAPPING+"/java/module/loc";
        param.setAdminParam( metricKey.toLowerCase(), "JavancssTask.ncss" );
        listMapping.add( param );

        param = new AdminParamsDTO();
        metricKey = AdminParamsBO.MAPPING+"/java/module/number-of-classes";
        param.setAdminParam( metricKey.toLowerCase(), "JavancssTask.numberOfClasses" );
        listMapping.add( param );

        param = new AdminParamsDTO();
        metricKey = AdminParamsBO.MAPPING+"/java/class/loc";
        param.setAdminParam( metricKey.toLowerCase(), "JavancssTask.ncss" );
        listMapping.add( param );

        param = new AdminParamsDTO();
        metricKey = AdminParamsBO.MAPPING+"/java/class/number-of-methods";
        param.setAdminParam( metricKey.toLowerCase(), "JavancssTask.methods" );
        listMapping.add( param );

        param = new AdminParamsDTO();
        metricKey = AdminParamsBO.MAPPING+"/java/method/loc";
        param.setAdminParam( metricKey.toLowerCase(), "JavancssTask.ncss" );
        listMapping.add( param );

        param = new AdminParamsDTO();
        metricKey = AdminParamsBO.MAPPING+"/java/method/vg";
        param.setAdminParam( metricKey.toLowerCase(), "JavancssTask.ccn" );
        listMapping.add( param );

        exporter.exportData( applicationsAudit, listMapping );

    }

}
