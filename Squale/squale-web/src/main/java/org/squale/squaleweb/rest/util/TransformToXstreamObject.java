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
package org.squale.squaleweb.rest.util;

import java.util.List;
import java.util.Locale;

import org.squale.squalecommon.datatransfertobject.component.ApplicationLightDTO;
import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.result.QualityResultDTO;
import org.squale.squalerest.model.ApplicationRest;
import org.squale.squalerest.model.AuditRest;
import org.squale.squalerest.model.FactorRest;
import org.squale.squalerest.model.ModuleRest;
import org.squale.squalerest.root.Applications;
import org.squale.squalerest.root.ByApplication;
import org.squale.squalerest.root.ByAudit;
import org.squale.squaleweb.resources.WebMessages;

/**
 * Utility class for transform data to xstream object
 * 
 * @author bfranchet
 */
public final class TransformToXstreamObject
{

    /**
     * Private constructor
     */
    private TransformToXstreamObject()
    {

    }

    /**
     * Transform data to xstream object for the rest call /applications
     * 
     * @param appList The data to transform
     * @return The Xstream object
     */
    public static Applications applications( List<ApplicationLightDTO> appList )
    {
        Applications dataToReturn = new Applications();
        ApplicationRest application = null;
        for ( ApplicationLightDTO applicationLightDTO : appList )
        {
            application =
                new ApplicationRest( Long.toString( applicationLightDTO.getTechnicalId() ),
                                     applicationLightDTO.getName(), null );
            dataToReturn.addApplication( application );
        }

        return dataToReturn;
    }

    /**
     * Transform data to xstream object for the rest call /application/id
     * 
     * @param app The application
     * @param auditList The list of audit
     * @param factorList The list of factor
     * @param locale current locale
     * @return The Xstream object
     */
    public static ByApplication byApplication( ApplicationLightDTO app, List<AuditDTO> auditList,
                                               List<QualityResultDTO> factorList, Locale locale )
    {
        ByApplication dataToReturn = new ByApplication();
        AuditRest audit = null;
        for ( int i = 1; i < auditList.size(); i++ )
        {
            AuditDTO auditDto = auditList.get( i );
            audit = new AuditRest( auditDto.getRealDate(), Long.toString( auditDto.getID() ) );
            dataToReturn.addAudit( audit );
        }
        AuditDTO auditDto = auditList.get( 0 );
        ApplicationRest application =
            buildApplicationRest( Long.toString( app.getTechnicalId() ), app.getName(), auditDto, factorList, locale );
        dataToReturn.setApplication( application );
        return dataToReturn;
    }

    /**
     * Transform data to xstream object for the rest call /audit/id
     * 
     * @param audit The audit
     * @param factorList The list of factor
     * @param locale current locale
     * @return The Xstream object
     */
    public static ByAudit byAudit( AuditDTO audit, List<QualityResultDTO> factorList, Locale locale )
    {
        ByAudit dataToReturn = new ByAudit();
        ApplicationRest application =
            buildApplicationRest( Long.toString( audit.getApplicationId() ), audit.getApplicationName(), audit,
                                  factorList, locale );
        dataToReturn.setApplication( application );
        return dataToReturn;
    }

    /**
     * Transform data given in argument into an application rest object
     * 
     * @param appId The applictaion id
     * @param appName The application name
     * @param auditDto The audit
     * @param factorList The list of factor
     * @param locale The current locale
     * @return An ApplicationRest object
     */
    private static ApplicationRest buildApplicationRest( String appId, String appName, AuditDTO auditDto,
                                                         List<QualityResultDTO> factorList, Locale locale )
    {
        AuditRest audit = new AuditRest( auditDto.getRealDate(), Long.toString( auditDto.getID() ) );
        ApplicationRest application = new ApplicationRest( appId, appName, audit );
        ModuleRest module = new ModuleRest( "", "" );
        for ( QualityResultDTO qualityResultDTO : factorList )
        {
            String moduleId = Long.toString( qualityResultDTO.getProject().getID() );
            if ( !module.getId().equals( moduleId ) )
            {
                module = new ModuleRest( moduleId, qualityResultDTO.getProject().getName() );
                application.addModule( module );
            }
            String name = WebMessages.getString( locale, qualityResultDTO.getRule().getName() );
            FactorRest factor =
                new FactorRest( name, Float.toString( qualityResultDTO.getMeanMark() ) );
            module.addDatas( factor );
        }
        return application;
    }

}
