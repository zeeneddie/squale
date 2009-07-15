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
package com.airfrance.squalecommon.datatransfertobject.transform.result;

import java.io.Serializable;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.result.ReferenceFactorDTO;
import com.airfrance.squalecommon.datatransfertobject.result.SqualeReferenceDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.rule.FactorRuleTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.rule.QualityGridTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.SqualeReferenceBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class SqualeReferenceTransform
    implements Serializable
{

    /**
     * Constructeur prive
     */
    private SqualeReferenceTransform()
    {
    }

    /**
     * DTO -> BO pour un SqualeReference
     * 
     * @param pSqualeReferenceDTO SqualeReferenceDTO à transformer
     * @return SqualeReferenceBO
     * @deprecated non utilisé
     */
    /*    TODO BFR --> deprecated method to suppress
    public static SqualeReferenceBO dto2Bo( SqualeReferenceDTO pSqualeReferenceDTO )
    {

        // Initialisation
        SqualeReferenceBO squaleReferenceBO = null;
        pSqualeReferenceDTO = null;

        return squaleReferenceBO;

    }*/

    /**
     * Transforme un objet SqualeReferenceBO en objet SqualeReferenceDTO
     * 
     * @param pSqualeReferenceBO objet metier du referentiel
     * @return SqualeReferenceDTO objet de transfert relatif au referentiel
     */
    public static SqualeReferenceDTO bo2Dto( SqualeReferenceBO pSqualeReferenceBO )
    {

        SqualeReferenceDTO squaleReferenceDTO = new SqualeReferenceDTO();

        // Copie des attributs de pSqualeReferenceBO dans squaleReferenceDTO
        squaleReferenceDTO.setId( pSqualeReferenceBO.getId() );
        squaleReferenceDTO.setLanguage( pSqualeReferenceBO.getLanguage() );
        squaleReferenceDTO.setProgrammingLanguage( pSqualeReferenceBO.getProgrammingLanguage() );
        squaleReferenceDTO.setMethodNumber( pSqualeReferenceBO.getMethodNumber() );
        squaleReferenceDTO.setClassNumber( pSqualeReferenceBO.getClassNumber() );
        squaleReferenceDTO.setApplicationName( pSqualeReferenceBO.getApplicationName() );
        squaleReferenceDTO.setCodeLineNumber( pSqualeReferenceBO.getCodeLineNumber() );
        squaleReferenceDTO.setProjectName( pSqualeReferenceBO.getProjectName() );
        squaleReferenceDTO.setVersion( pSqualeReferenceBO.getVersion() );
        squaleReferenceDTO.setHidden( pSqualeReferenceBO.getHidden() );
        squaleReferenceDTO.setPublic( pSqualeReferenceBO.getPublic() );
        squaleReferenceDTO.setAuditType( pSqualeReferenceBO.getAuditType() );
        squaleReferenceDTO.setDate( pSqualeReferenceBO.getDate() );
        // Conversion de la grille qualité
        squaleReferenceDTO.setGrid( QualityGridTransform.bo2Dto( pSqualeReferenceBO.getQualityGrid() ) );
        // Conversion de chacune des notes associée aux facteurs
        Iterator it = pSqualeReferenceBO.getFactors().keySet().iterator();
        while ( it.hasNext() )
        {
            FactorRuleBO rule = (FactorRuleBO) it.next();
            ReferenceFactorDTO dto = new ReferenceFactorDTO();
            FactorRuleTransform.bo2Dto( dto, rule );
            dto.setValue( (Float) pSqualeReferenceBO.getFactors().get( rule ) );
            squaleReferenceDTO.addFactorValue( dto );
        }
        return squaleReferenceDTO;
    }

}
