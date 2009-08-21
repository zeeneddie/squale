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
package org.squale.squalecommon.datatransfertobject.result;

/**
 * DTO pour les commentaires des notes manuelles (ManualMark)
 * @author xpetitrenaud
 *
 */
public class QualityResultCommentDTO
{
    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;
    
    /**
     * Commentaires concernant une note manuelle donnée
     */
    private String comments;
    
    /**
     * Note manuelle auquelle est associée le commentaire
     */
    protected QualityResultDTO mQualityResult;
    
    /***
     * Getter method for the id
     * 
     * @return The technical id of the object
     */
    public Long getId()
    {
        return mId;
    }
    
    /***
     * Setter method for the property mId
     * 
     * @param id the new mId
     */
    public void setId( long id )
    {
        this.mId = id;
    }
    
    /***
     * Getter method for the property comments
     * 
     * @return the comment of the manual mark
     */
    public String getComments()
    {
        return comments;
    }
    
    /***
     * Setter method for the property comments
     * 
     * @param pComments the new comments
     */
    public void setComments( String pComments )
    {
        this.comments = pComments;
    }
    
    /***
     * Getter method for the property mQualityResult, the manual mark
     * 
     * @return the the manual mark
     */
    public QualityResultDTO getQualityResult()
    {
        return mQualityResult;
    }
    
    /***
     * Setter method for the property mQualityResult, the manual mark
     * 
     * @param pQualityResult the new manual mark
     */
    public void setQualityResult( QualityResultDTO pQualityResult )
    {
        this.mQualityResult = pQualityResult;
    }
}