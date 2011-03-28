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
import java.util.Collection;
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
     * Transform data into an {@link ApplicationRest} with list of audits (successful, partial, failed)
     * 
     * @param application The application
     * @param sucessfulAuditList The list of successful audits
     * @param partialAuditList The list of partial audits
     * @param failedAuditList the list of failed audits
     * @param moduleList The list of modules
     * @param locale The current locale
     * @return The {@link ApplicationRest} object creates with the elements given in argument
     */
    public static ApplicationRest createApplicationRestWithAudits( ComponentDTO application,
                                                               List<AuditDTO> sucessfulAuditList,
                                                               List<AuditDTO> partialAuditList,
                                                               List<AuditDTO> failedAuditList,
                                                               List<ModuleLightDTO> moduleList, Locale locale )
    {
        List<AuditRest> successfulAuditRestList = createAuditRest( sucessfulAuditList, locale );
        List<AuditRest> partialAuditRestList = createAuditRest( partialAuditList, locale );
        List<AuditRest> failedAuditRestList = createAuditRest( failedAuditList, locale );
        ApplicationRest applicationRest =
            createFullApplicationRest( sucessfulAuditList.get( 0 ), application, moduleList, locale );
        applicationRest.setSuccessfulAudits( successfulAuditRestList );
        applicationRest.setPartialAudits( partialAuditRestList );
        applicationRest.setFailedAudits( failedAuditRestList );
        return applicationRest;
    }

    /**
     * This method create a list of {@link AuditRest} object based on the elements given in arguments
     * 
     * @param auditDtoList The list of module
     * @param locale The current locale
     * @return A list of {@link AuditRest} objetc
     */
    private static List<AuditRest> createAuditRest( List<AuditDTO> auditDtoList, Locale locale )
    {
        List<AuditRest> auditRestList = null;
        if ( auditDtoList != null && auditDtoList.size() > 0 )
        {
            auditRestList = new ArrayList<AuditRest>();
            for ( int i = 0; i < auditDtoList.size(); i++ )
            {
                AuditDTO auditDto = auditDtoList.get( i );
                String type = WebMessages.getString( locale, auditDto.getType() );
                AuditRest auditRest =
                    new AuditRest( auditDto.getRealDate(), type, auditDto.getDuration(),
                                   Long.toString( auditDto.getID() ) );
                auditRestList.add( auditRest );
            }
        }
        return auditRestList;
    }

    /**
     * This method creates an application rest based on the component given in argument
     * 
     * @param application The application component
     * @return An application rest object
     */
    public static ApplicationRest createApplicationRest( ComponentDTO application )
    {
        ApplicationRest applicationRest =
            new ApplicationRest( Long.toString( application.getID() ), application.getName(), application.isPublicAppication()  );
        List<TagRest> listTagRest = transformTag( application.getTags() );
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
    public static ApplicationRest createFullApplicationRest( AuditDTO audit, ComponentDTO application,
                                                             List<ModuleLightDTO> moduleList, Locale locale )
    {
        ApplicationRest applicationRest = createApplicationRest( application );
        String type = WebMessages.getString( locale, audit.getType() );
        AuditRest auditRest =
            new AuditRest( audit.getRealDate(), type, audit.getDuration(), Long.toString( audit.getID() ) );
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
            ModuleRest moduleRest =
                new ModuleRest( Long.toString( module.getTechnicalId() ), module.getName(), module.getGridName() );
            List<TagRest> listTagRest = transformTag( module.getTags() );
            moduleRest.setTags( listTagRest );
            for ( QualityResultDTO factorDto : module.getFactor() )
            {
                String name = WebMessages.getString( locale, factorDto.getRule().getName() );
                String value = Double.toString( floor( factorDto.getMeanMark(), 1 ) );
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
    private static List<TagRest> transformTag( Collection<TagDTO> listTagDto )
    {
        List<TagRest> listTag = null;
        if ( listTagDto != null && listTagDto.size() > 0 )
        {
            listTag = new ArrayList<TagRest>();
            for ( TagDTO tagDto : listTagDto )
            {
                listTag.add( new TagRest( tagDto.getName(), tagDto.getTagCategoryDTO().getName() ) );
            }
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
