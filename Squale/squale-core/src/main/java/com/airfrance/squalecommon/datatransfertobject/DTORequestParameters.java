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
package com.airfrance.squalecommon.datatransfertobject;

import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;

/**
 * Classe pour transférer les valeurs des id de projet, d'application et d'audits entre les différentes pages
 */
public class DTORequestParameters
{

    /** le projet - peut etre null */
    private ComponentDTO projectDto;

    /** l'application - peut etre null */
    private ComponentDTO applicationDto;

    /** l'audit courant - peut etre null */
    private AuditDTO currentAuditDto;

    /** l'audit précédent sur le projet - peut etre null */
    private AuditDTO previousAuditDto;

    /**
     * @return l'id de l'application
     */
    public ComponentDTO getApplicationDto()
    {
        return applicationDto;
    }

    /**
     * @return l'id de l'audit courant
     */
    public AuditDTO getCurrentAuditDto()
    {
        return currentAuditDto;
    }

    /**
     * @return l'id de l'audit précédent
     */
    public AuditDTO getPreviousAuditDto()
    {
        return previousAuditDto;
    }

    /**
     * @return l'id du projet
     */
    public ComponentDTO getProjectDto()
    {
        return projectDto;
    }

    /**
     * @param newApplicationDto le nouvel id de l'application
     */
    public void setApplicationDto( ComponentDTO newApplicationDto )
    {
        applicationDto = newApplicationDto;
    }

    /**
     * @param newCurrentAuditDto le nouvel id de l'application
     */
    public void setCurrentAuditDto( AuditDTO newCurrentAuditDto )
    {
        currentAuditDto = newCurrentAuditDto;
    }

    /**
     * @param newPreviousAuditDto le nouvel id de l'application
     */
    public void setPreviousAuditDto( AuditDTO newPreviousAuditDto )
    {
        previousAuditDto = newPreviousAuditDto;
    }

    /**
     * @param newProjectDto le nouvel id de l'application
     */
    public void setProjectDto( ComponentDTO newProjectDto )
    {
        projectDto = newProjectDto;
    }

    /**
     * @return l'id de l'application ou -1 si elle n'est pas définie
     */
    public String getApplicationId()
    {
        String result = "-1";
        if ( applicationDto != null )
        {
            result = "" + applicationDto.getID();
        }
        return result;
    }

    /**
     * @return l'id du projet ou -1 si il n'est pas défini
     */
    public String getProjectId()
    {
        String result = "-1";
        if ( projectDto != null )
        {
            result = "" + projectDto.getID();
        }
        return result;
    }

    /**
     * @return l'id de l'audit courant ou -1 si il n'est pas défini
     */
    public String getCurrentAuditId()
    {
        String result = "-1";
        if ( currentAuditDto != null )
        {
            result = "" + currentAuditDto.getID();
        }
        return result;
    }

}