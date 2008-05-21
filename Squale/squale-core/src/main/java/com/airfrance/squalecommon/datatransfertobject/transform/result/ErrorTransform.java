/*
 * Créé le 12 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.datatransfertobject.transform.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.result.ErrorDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.ErrorBO;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ErrorTransform
    implements Serializable
{

    /**
     * Constructeur prive
     */
    private ErrorTransform()
    {
    }

    /**
     * Copie complète de la DTO à la BO DTO -> BO pour un Error
     * 
     * @param pErrorDTO ErrorDTO à transformer
     * @return ErrorBO
     */
    public static ErrorBO dto2Bo( ErrorDTO pErrorDTO )
    {

        // Initialisation du retour et la tache
        ErrorBO errorBO = new ErrorBO();
        errorBO.setTaskName( pErrorDTO.getTaskName() );

        // Initialisation de l'audit
        AuditBO auditBO = new AuditBO();
        auditBO.setId( pErrorDTO.getAuditId() );

        // Initialisation du sous-projet
        ProjectBO projectBO = new ProjectBO();
        projectBO.setId( pErrorDTO.getProjectId() );

        errorBO.setAudit( auditBO );
        errorBO.setProject( projectBO );
        errorBO.setLevel( pErrorDTO.getLevel() );

        return errorBO;

    }

    /**
     * Est utilisé pour remonter une collection d'erreurs aux AC BO -> DTO pour un Error
     * 
     * @param pErrorBO ErrorBO
     * @return ErrorDTO
     */
    public static ErrorDTO bo2Dto( ErrorBO pErrorBO )
    {

        // Initialisation du retour
        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setAuditId( pErrorBO.getAudit().getId() );
        errorDTO.setLevel( pErrorBO.getLevel() );
        errorDTO.setMessageKey( pErrorBO.getMessage() );
        errorDTO.setTaskName( pErrorBO.getTaskName() );
        errorDTO.setProjectId( pErrorBO.getProject().getId() );

        return errorDTO;

    }

    /**
     * Transforms a list of ErrorBO in ErroDTO
     * 
     * @param pErrorsBO list of ErrorBO
     * @return list of ErrorDTO
     */
    public static List bo2Dto( List pErrorsBO )
    {
        List errorsDTO = new ArrayList();
        for ( int i = 0; i < pErrorsBO.size(); i++ )
        {
            errorsDTO.add( bo2Dto( (ErrorBO) pErrorsBO.get( i ) ) );
        }
        return errorsDTO;
    }

}
