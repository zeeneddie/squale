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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.component.ModuleLightDTO;
import org.squale.squalecommon.datatransfertobject.result.QualityResultDTO;
import org.squale.squalecommon.datatransfertobject.tag.TagDTO;
import org.squale.squalerest.model.ApplicationRest;
import org.squale.squalerest.model.AuditRest;
import org.squale.squalerest.model.FactorRest;
import org.squale.squalerest.model.ModuleRest;
import org.squale.squalerest.model.TagRest;
import org.squale.squalerest.model.VolumetryRest;
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
    public static Applications applications( List<ComponentDTO> appList )
    {
        Applications dataToReturn = new Applications();
        ApplicationRest application = null;
        for ( ComponentDTO compoDTO : appList )
        {
            application = createApplication( compoDTO );
            dataToReturn.addApplication( application );
        }
        return dataToReturn;
    }

    /**
     * Transform data to xstream object for the rest call /application/id
     * 
     * @param application The application
     * @param auditList The list of audits
     * @param moduleList The list of modules
     * @param locale The current locale
     * @return The Xstream object
     */
    public static ByApplication byApplication( ComponentDTO application, List<AuditDTO> auditList,
                                               List<ModuleLightDTO> moduleList, Locale locale )
    {
        ByApplication dataToReturn = new ByApplication();
        AuditRest audit = null;
        for ( int i = 1; i < auditList.size(); i++ )
        {
            AuditDTO auditDto = auditList.get( i );
            audit = new AuditRest( auditDto.getRealDate(), Long.toString( auditDto.getID() ) );
            dataToReturn.addAudit( audit );
        }
        ApplicationRest applicationRest = createFullApplication( auditList.get( 0 ), application, moduleList, locale );
        dataToReturn.setApplication( applicationRest );
        return dataToReturn;
    }

    /**
     * Transform data to xstream object for the rest call /audit/id
     * 
     * @param audit The audit
     * @param application The application linked to the audit
     * @param moduleList The list of modules
     * @param locale The current locale
     * @return The Xstream object
     */
    public static ByAudit byAudit( AuditDTO audit, ComponentDTO application, List<ModuleLightDTO> moduleList,
                                   Locale locale )
    {
        ByAudit dataToReturn = new ByAudit();
        ApplicationRest applicationRest = createFullApplication( audit, application, moduleList, locale );
        dataToReturn.setApplication( applicationRest );
        return dataToReturn;
    }

    /**
     * This method creates an application rest based on the component given in argument
     * 
     * @param application The application component
     * @return An application rest object
     */
    private static ApplicationRest createApplication( ComponentDTO application )
    {
        ApplicationRest applicationRest =
            new ApplicationRest( Long.toString( application.getID() ), application.getName(), null );
        List<TagRest> listTagRest = new ArrayList<TagRest>();
        if ( application.getTags() != null )
        {
            listTagRest = transformTag( new ArrayList<TagDTO>( application.getTags() ) );
        }
        applicationRest.setTags( listTagRest );
        return applicationRest;
    }

    /**
     * Create the application rest fully filled
     * 
     * @param audit The audit
     * @param application the application
     * @param moduleList The list of module
     * @param locale The current locale
     * @return the application rest Object fully filled
     */
    private static ApplicationRest createFullApplication( AuditDTO audit, ComponentDTO application,
                                                          List<ModuleLightDTO> moduleList, Locale locale )
    {
        ApplicationRest applicationRest = createApplication( application );
        AuditRest auditRest = new AuditRest( audit.getRealDate(), Long.toString( audit.getID() ) );
        applicationRest.setAudit( auditRest );
        createModulesRest( applicationRest, moduleList, locale );
        return applicationRest;
    }

    /**
     * This method create the module rest fully filled
     * 
     * @param application The application
     * @param moduleList The module list
     * @param locale The current locale
     */
    private static void createModulesRest( ApplicationRest application, List<ModuleLightDTO> moduleList, Locale locale )
    {
        for ( ModuleLightDTO module : moduleList )
        {
            ModuleRest moduleRest = new ModuleRest( Long.toString( module.getTechnicalId() ), module.getName() );
            List<TagRest> listTagRest = transformTag( module.getTags() );
            moduleRest.setTags( listTagRest );
            for ( QualityResultDTO factorDto : module.getFactor() )
            {
                String name = WebMessages.getString( locale, factorDto.getRule().getName() );
                String value = Double.toString( floor( factorDto.getMeanMark(), 1 ));
                FactorRest factor = new FactorRest( name, value );
                moduleRest.addDatas( factor );
            }
            Map<String, Integer> volumetryMap = module.getVolumetry();
            for ( Iterator<String> iterator = volumetryMap.keySet().iterator(); iterator.hasNext(); )
            {
                String name = iterator.next();
                VolumetryRest volum =
                    new VolumetryRest( WebMessages.getString( locale, name ),
                                       Integer.toString( volumetryMap.get( name ) ) );
                moduleRest.addVolumetry( volum );
            }
            application.addModule( moduleRest );
        }
    }

    /**
     * This method transform a list of tagDto into a list of tag rest
     * 
     * @param listTagDto The list of tag Dto
     * @return The list of tag rest
     */
    private static List<TagRest> transformTag( List<TagDTO> listTagDto )
    {
        List<TagRest> listTag = new ArrayList<TagRest>();
        for ( TagDTO tagDto : listTagDto )
        {
            listTag.add( new TagRest( tagDto.getName() ) );
        }
        return listTag;
    }
    
    
    /**
     * Rounding a double with n elements after the comma.
     * 
     * @param a The value to round
     * @param n The number of decimal to retain.
     * @return The value round to n decimals
     */
    private static double floor( double a, int n )
    {
        double p = Math.pow( 10.0, n );
        return Math.floor( ( a * p ) + 0.5 ) / p;
    }

}
