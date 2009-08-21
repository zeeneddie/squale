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
package org.squale.squalecommon.enterpriselayer.businessobject.result;

import java.io.Serializable;

/***
 * Représente un commentaire associé à une note de pratique manuelle
 * 
 * @author xpetitrenaud
 * @version 1.0
 * @hibernate.class table="QualityResult_Comment"
 */
public class QualityResultCommentBO
    implements Serializable
{
    
    /**
     * The serial version number
     */
    private static final long serialVersionUID = 3472340143932962747L;

    /***
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;
    
    /***
     * Commentaires concernant une note manuelle donnée
     */
    private String comments;
    
    /**
     * Note manuelle auquelle est associée le commentaire
     */
    protected QualityResultBO mQualityResult;
    
    /***
     * max comments field length as defined in the database
     */
    public static final int MAXCOMMENTSLENGTH = 4000;

    /***
     * Getter method for the id
     * 
     * @return the object's id 
     * @hibernate.id generator-class="native" type="long" column="QR_CommentId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="qualityres_comment_sequence"
     */
    public long getId()
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
     * @hibernate.property name="comments" column="Comments" type="string" not-null="false" unique="false" update="true"
     *                     insert="true"
     */
    public String getComments()
    {
        return comments;
    }

    /***
     * Setter method for the property comments
     * 
     * @param mComments the new comments
     */
    public void setComments( String mComments )
    {
        this.comments = mComments;
    }

    /***
     * Getter method for the property mQualityResult, the manual mark
     * 
     * @return the manual mark
     * @hibernate.many-to-one column="QualityResultId"
     *                        class="org.squale.squalecommon.enterpriselayer.businessobject.result.QualityResultBO"
     *                        cascade="none" unique="true"
     */
    public QualityResultBO getQualityResult()
    {
        return mQualityResult;
    }

    /***
     * Setter method for the property mQualityResult, the manual mark
     * 
     * @param pQualityResult the new manual mark
     */
    public void setQualityResult( QualityResultBO pQualityResult )
    {
        this.mQualityResult = pQualityResult;
    }
    
    /***
     * Constructeur par défaut
     * Note : pour forcer l'insertion, la valeur de l'id est par défaut de -1
     * conformément à l'unsaved value définie dans le fichier de configuration
     * hibernate
     */
    public QualityResultCommentBO()
    {
        mId = -1;
    }
}