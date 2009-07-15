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
package org.squale.squalecommon.datatransfertobject.transform.component.parameters;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.squale.squalecommon.datatransfertobject.component.parameters.ListParameterDTO;
import org.squale.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import org.squale.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;

/**
 * Transforme dto <-> bo pour un MapParameter
 */
public class MapParameterTransform
{

    /**
     * @param pMapParameterDTO le DTO à transformer en BO
     * @return le BO
     */
    public static MapParameterBO dto2Bo( MapParameterDTO pMapParameterDTO )
    {
        MapParameterBO result = new MapParameterBO();
        result.setId( pMapParameterDTO.getId() );
        // On récupère les paramètres associés
        Map paramsDTO = pMapParameterDTO.getParameters();
        // On va remplir la map avec des objets transformés en BO.
        Map paramsBO = new HashMap();
        // On va parcourir toute la map et transformer tous ses éléments. Il faut
        // donc récupérer l'ensemble des clés pour pouvoir ensuite récupérer les éléments
        // associés.
        java.util.Set keys = paramsDTO.keySet();
        for ( Iterator it = keys.iterator(); it.hasNext(); )
        {
            String currentKey = (String) it.next();
            // si l'élément courant est une map, on rappelle récursivement la méthode
            // pour transformer aussi tous ses éléments.
            if ( paramsDTO.get( currentKey ) instanceof MapParameterDTO )
            {
                paramsBO.put( currentKey, MapParameterTransform.dto2Bo( (MapParameterDTO) paramsDTO.get( currentKey ) ) );
            }
            else
            {
                // Si il s'agit d'une liste, on va appeler la méthode qui transforme cette objet
                // ainsi que tous ses éléments en BO.
                if ( paramsDTO.get( currentKey ) instanceof ListParameterDTO )
                {
                    paramsBO.put( currentKey,
                                  ListParameterTransform.dto2Bo( (ListParameterDTO) paramsDTO.get( currentKey ) ) );
                }
                else
                {
                    if ( paramsDTO.get( currentKey ) instanceof StringParameterDTO )
                    {
                        paramsBO.put(
                                      currentKey,
                                      StringParameterTransform.dto2Bo( (StringParameterDTO) paramsDTO.get( currentKey ) ) );
                    }
                }
            }
        }
        result.setParameters( paramsBO );
        return result;
    }

    /**
     * @param pMapParameterBO le BO à transformer en DTO
     * @return le DTO
     */
    public static MapParameterDTO bo2Dto( MapParameterBO pMapParameterBO )
    {
        MapParameterDTO result = new MapParameterDTO();
        result.setId( pMapParameterBO.getId() );
        // On récupère les paramètres associés
        Map paramsBO = pMapParameterBO.getParameters();
        // On va remplir la map avec des objets transformés en DTO.
        Map paramsDTO = new HashMap();
        // On va parcourir toute la map et transformer tous ses éléments. Il faut
        // donc récupérer l'ensemble des clés pour pouvoir ensuite récupérer les éléments
        // associés.
        java.util.Set keys = paramsBO.keySet();
        for ( Iterator it = keys.iterator(); it.hasNext(); )
        {
            String currentKey = (String) it.next();
            // si l'élément courant est une map, on rappelle récursivement la méthode
            // pour transformer aussi tous ses éléments.
            if ( paramsBO.get( currentKey ) instanceof MapParameterBO )
            {
                paramsDTO.put( currentKey, MapParameterTransform.bo2Dto( (MapParameterBO) paramsBO.get( currentKey ) ) );
            }
            else
            {
                // Si il s'agit d'une liste, on va appeler la méthode qui transforme cette objet
                // ainsi que tous ses éléments en DTO.
                if ( paramsBO.get( currentKey ) instanceof ListParameterBO )
                {
                    paramsDTO.put( currentKey,
                                   ListParameterTransform.bo2Dto( (ListParameterBO) paramsBO.get( currentKey ) ) );
                }
                else
                {
                    if ( paramsBO.get( currentKey ) instanceof StringParameterBO )
                    {
                        paramsDTO.put( currentKey,
                                       StringParameterTransform.bo2Dto( (StringParameterBO) paramsBO.get( currentKey ) ) );
                    }
                }
            }
        }
        result.setParameters( paramsDTO );
        return result;
    }
}