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
/*
 * Créé le 12 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.datatransfertobject.transform.component;

import java.io.Serializable;

import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class AuditTransform
    implements Serializable
{

    /**
     * Constructeur prive
     */
    private AuditTransform()
    {

    }

    /**
     * DTO -> BO pour un Audit
     * 
     * @param pAuditDTO AuditDTO à transformer
     * @return AuditBO
     */
    public static AuditBO dto2Bo( AuditDTO pAuditDTO )
    {
        AuditBO auditBO = new AuditBO();

        // Initialisation des champs de auditBO

        auditBO.setId( pAuditDTO.getID() );
        auditBO.setName( pAuditDTO.getName() );
        auditBO.setDate( pAuditDTO.getDate() );
        auditBO.setType( pAuditDTO.getType() );
        auditBO.setStatus( pAuditDTO.getStatus() );
        auditBO.setHistoricalDate( pAuditDTO.getHistoricalDate() );
        // données admins
        auditBO.setMaxFileSystemSize( pAuditDTO.getMaxFileSystemSize() );
        auditBO.setDuration( pAuditDTO.getDuration() );
        auditBO.setRealBeginningDate( pAuditDTO.getRealBeginningDate() );
        auditBO.setEndDate( pAuditDTO.getEndDate() );
        auditBO.setSqualeVersion( pAuditDTO.getSqualeVersion() );
        return auditBO;

    }

    /**
     * BO -> DTO pour un Audit
     * 
     * @param pAuditBO AuditBO
     * @param pApplicationId id de l'application
     * @return AuditDTO
     */
    public static AuditDTO bo2Dto( AuditBO pAuditBO, long pApplicationId )
    {

        // Initialisation qui initialise la plupart des champs
        AuditDTO auditDTO = bo2Dto( pAuditBO );
        // seul champ restant à préciser
        auditDTO.setApplicationId( pApplicationId );

        return auditDTO;
    }

    /**
     * On ne spécifie pas l'id de l'audit, il sera mis plus tard car on l'a déjà au moment où on appelle cette méthode
     * BO -> DTO pour un Audit
     * 
     * @param pAuditBO AuditBO
     * @return AuditDTO
     */
    public static AuditDTO bo2Dto( AuditBO pAuditBO )
    {
        // Initialisation
        AuditDTO auditDTO = new AuditDTO();
        // Initialisation des champs de auditBO
        auditDTO.setID( pAuditBO.getId() );
        auditDTO.setName( pAuditBO.getName() );
        auditDTO.setDate( pAuditBO.getDate() );
        auditDTO.setType( pAuditBO.getType() );
        auditDTO.setStatus( pAuditBO.getStatus() );
        auditDTO.setHistoricalDate( pAuditBO.getHistoricalDate() );
        // données admins
        auditDTO.setMaxFileSystemSize( pAuditBO.getMaxFileSystemSize() );
        auditDTO.setDuration( pAuditBO.getDuration() );
        auditDTO.setRealBeginningDate( pAuditBO.getRealBeginningDate() );
        auditDTO.setEndDate( pAuditBO.getEndDate() );
        auditDTO.setSqualeVersion( pAuditBO.getSqualeVersion() );
        return auditDTO;
    }

}
