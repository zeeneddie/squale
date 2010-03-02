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
package org.squale.squalecommon.datatransfertobject.transform.tag;

import java.io.Serializable;

import org.squale.squalecommon.datatransfertobject.tag.TagDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.tag.TagBO;

/**
 * Transforme les composants en bo<->ComponentDTO
 */
public final class TagTransform
    implements Serializable
{

    /**
     * UID
     */
    private static final long serialVersionUID = -7300972719072399802L;

    /**
     * Constructeur prive
     */
    private TagTransform()
    {
    }

    /**
     * DTO -> BO pour un Tag
     * 
     * @param pTagDTO TagDTO à transformer
     * @return TagBO
     * @deprecated A ne pas utiliser
     */
    public static TagBO dto2BoProxy( TagDTO pTagDTO )
    {
        return null;
    }

    /**
     * TagBO -> TagDTO pour un Component
     * 
     * @param pTagBO TagBO
     * @return TagDTO
     */
    public static TagDTO bo2Dto( TagBO pTagBO )
    {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId( pTagBO.getId() );
        tagDTO.setName( pTagBO.getName() );
        tagDTO.setDescription( pTagBO.getDescription() );
        if ( pTagBO.getTagCategoryBO() != null )
        {
            tagDTO.setTagCategoryDTO( TagCategoryTransform.bo2Dto( pTagBO.getTagCategoryBO() ) );
        }

        return tagDTO;
    }

    /**
     * TagDTO -> TagBO
     * 
     * @param pTagDTO TagDTO à transformer
     * @return TagBO
     */
    public static TagBO dto2Bo( TagDTO pTagDTO )
    {

        // Initialisation du retour
        TagBO tagBO = new TagBO();
        tagBO.setId( pTagDTO.getId() );
        tagBO.setName( pTagDTO.getName() );
        tagBO.setDescription( pTagDTO.getDescription() );
        if ( pTagDTO.getTagCategoryDTO() != null )
        {
            tagBO.setTagCategoryBO( TagCategoryTransform.dto2Bo( pTagDTO.getTagCategoryDTO() ) );
        }

        return tagBO;
    }
}
