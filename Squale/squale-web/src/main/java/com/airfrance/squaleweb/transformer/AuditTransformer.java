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
 * Créé le 8 août 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squaleweb.transformer;

import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.AuditForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * @author M400842
 */
public class AuditTransformer
    implements WITransformer
{

    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        AuditForm form = new AuditForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        AuditDTO auditDTO = (AuditDTO) pObject[0];
        AuditForm form = (AuditForm) pForm;
        form.setId( auditDTO.getID() );
        form.setDate( auditDTO.getDate() );
        form.setType( auditDTO.getType() );
        form.setApplicationId( "" + auditDTO.getApplicationId() );
        form.setApplicationName( auditDTO.getApplicationName() );
        form.setStatus( auditDTO.getStatus() );
        form.setStringStatus( auditDTO.getStatus() );
        if ( AuditBO.MILESTONE.equals( auditDTO.getType() ) )
        {
            // Si il s'agit d'un audit de jalon, la date historique
            // et le nom sont forcément renseignés
            form.setHistoricalDate( auditDTO.getHistoricalDate() );
            form.setName( auditDTO.getName() );
        }
        // données admins
        form.setMaxFileSystemSize( auditDTO.getMaxFileSystemSize() );
        form.setDuration( auditDTO.getDuration() );
        if ( auditDTO.getRealBeginningDate() != null )
        {
            form.setRealBeginningDate( auditDTO.getRealBeginningDate() );
        }
        if ( auditDTO.getEndDate() != null )
        {
            form.setEndDate( auditDTO.getEndDate() );
        }
        form.setSqualeVersion( auditDTO.getSqualeVersion() );
        form.setServerName( auditDTO.getServerName() );
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new AuditDTO() };
        formToObj( pForm, obj );
        return obj;
    }

    /**
     * @param pObject l'objet à remplir
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        AuditForm auditForm = (AuditForm) pForm;
        AuditDTO dto = (AuditDTO) pObject[0];
        // Affectation des différents champs
        dto.setID( auditForm.getId() );
        dto.setDate( auditForm.getDate() );
        dto.setName( auditForm.getName() );
        dto.setApplicationId( new Long( auditForm.getApplicationId() ).longValue() );
        dto.setStatus( auditForm.getStatus() );
        dto.setType( auditForm.getType() );
        dto.setSqualeVersion( auditForm.getSqualeVersion() );
        // Si la date historique (cas d'un audit de jalon) est renseignée
        // on l'affecte.
        if ( null != auditForm.getHistoricalDate() )
        {
            dto.setHistoricalDate( auditForm.getHistoricalDate() );
        }
        // données admins
        dto.setMaxFileSystemSize( auditForm.getMaxFileSystemSize() );
        dto.setDuration( auditForm.getDuration() );
        if ( auditForm.getRealBeginningDate() != null )
        {
            dto.setRealBeginningDate( auditForm.getRealBeginningDate() );
        }
        if ( auditForm.getEndDate() != null )
        {
            dto.setEndDate( auditForm.getEndDate() );
        }
    }
}
