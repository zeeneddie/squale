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
package com.airfrance.squalecommon.datatransfertobject.transform.tag;

import java.io.Serializable;

import com.airfrance.squalecommon.datatransfertobject.tag.TagCategoryDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.tag.TagCategoryBO;

/**
 * Transforme les composants en bo<->ComponentDTO
 */
public class TagCategoryTransform
    implements Serializable
{

    /**
     * Constructeur prive
     */
    private TagCategoryTransform()
    {
    }

    /**
     * TagBO -> TagDTO pour un Component
     * 
     * @param pTagCategoryBO TagCategoryBO
     * @return TagCategoryDTO
     */
    public static TagCategoryDTO bo2Dto( TagCategoryBO pTagCategoryBO )
    {
        TagCategoryDTO tagCategoryDTO = new TagCategoryDTO();
        tagCategoryDTO.setID( pTagCategoryBO.getId() );
        tagCategoryDTO.setName( pTagCategoryBO.getName() );
        tagCategoryDTO.setDescription( pTagCategoryBO.getDescription() );

        return tagCategoryDTO;
    }

    /**
     * TagCategoryDTO -> TagCategoryBO
     * 
     * @param pTagCategoryDTO TagCategoryDTO à transformer
     * @return TagBO
     */
    public static TagCategoryBO dto2Bo( TagCategoryDTO pTagCategoryDTO )
    {

        // Initialisation du retour
        TagCategoryBO tagCategoryBO = new TagCategoryBO();
        tagCategoryBO.setId( pTagCategoryDTO.getID() );
        tagCategoryBO.setName( pTagCategoryDTO.getName() );
        tagCategoryBO.setDescription( pTagCategoryDTO.getDescription() );

        return tagCategoryBO;
    }
}
