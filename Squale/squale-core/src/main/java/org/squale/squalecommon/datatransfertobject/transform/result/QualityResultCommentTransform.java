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

package org.squale.squalecommon.datatransfertobject.transform.result;

import org.squale.squalecommon.datatransfertobject.result.QualityResultCommentDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.QualityResultCommentBO;

/**
 * Transformer QualityResultCommentBO <--> QualityResultCommentDTO
 */
public final class QualityResultCommentTransform
{
    /**
     * Private constructor
     */
    private QualityResultCommentTransform()
    {
        
    }
    /**
     * Transform a QualityResultCommentBO in a QualityResultCommentDTO
     * 
     * @param pQualityCommentBo The BO to transform
     * @return a QualityResultCommentDTO
     */
    public static QualityResultCommentDTO bo2Dto ( QualityResultCommentBO pQualityCommentBo )
    {
        QualityResultCommentDTO qualityResultCommentDTO = new QualityResultCommentDTO();
        qualityResultCommentDTO.setId( pQualityCommentBo.getId() );
        qualityResultCommentDTO.setComments( pQualityCommentBo.getComments() );
        return qualityResultCommentDTO;
    }
}
