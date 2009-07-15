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
package com.airfrance.squaleweb.applicationlayer.formbean.creation;

import java.util.ArrayList;
import java.util.Collection;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;
import com.airfrance.squaleweb.applicationlayer.formbean.tag.TagCategoryForm;
import com.airfrance.squaleweb.applicationlayer.formbean.tag.TagForm;

/**
 * Form bean for a Struts application.
 * 
 * @version 1.0
 * @author
 */
public class CreateTagForm
    extends RootForm 
{

    /** the current tag list */
    private Collection<TagForm> mTags = new ArrayList<TagForm>();
    
    /** the current tag Category list */
    private Collection<TagCategoryForm> mTagCategories = new ArrayList<TagCategoryForm>();
    
    /** the id of the tag (in order to delete it) */
    private String mTagId;
    
    /** the id of the tag category (in order to delete it) */
    private String mTagCategoryId;
    
    /** le nom du Tag */
    private String mName;

    /** La description du Tag */
    private String mDescription;
    
    /** Le nom de la catégorie du Tag dans la af:form lié à l'ajout de tag*/
    private String mCategoryName;
    
    /** le nom de la catégorie de Tag */
    private String mTagCatName;

    /** La description de la catégorie de Tag */
    private String mTagCatDescription;
    
    /** La catégorie du Tag */
    private TagCategoryForm mCategoryForm = new TagCategoryForm();

    /** The index of the chosen tag to modify in the list of the JSP */
    private String mTagIndex;
    
    /** The index of the chosen tag category to modify in the list of the JSP */
    private String mTagCategoryIndex;
    
    /** a boolean to indicate that a Tag is being modified */
    private boolean mTagModified = false;
    
    /** a boolean to indicate that a Tag Category is being modified */
    private boolean mTagCategoryModified = false;
    
    /** a boolean that will verify if the tagCategory that is associated with the created tag exists */
    private boolean mPbTagCategory = false;
    
    /** The name of the categorie wanted for the tag created */
    private String mPbTagCategoryName;
    
    /** a boolean that will verify if the tag has a valid name before creation */
    private boolean mPbTag = false;
    
    /** Le nom de la catégorie du Tag posant problème lors de la création de tags */
    private String mPbTagName;
    
    /** a boolean that will verify if the category has a valid name before creation */
    private boolean mPbCategory = false;
    
    /** The name of the categorie wanted for creation */
    private String mPbCategoryName;
    
    /**
     * Access method for the mName property.
     * @return the name of the Tag
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Sets the value of the mName property.
     * @param pName the name given to the tag
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Access method for the mDescription property.
     * @return the description of the Tag
     */
    public String getDescription()
    {
        return mDescription;
    }

    /**
     * Sets the value of the mDescription property.
     * @param pDescription the description given to the tag
     */
    public void setDescription( String pDescription )
    {
        mDescription = pDescription;
    }
    
    /**
     * Access method for the mCategoryName property.
     * @return the name of the category of the Tag
     */
    public String getCategoryName()
    {
        return mCategoryName;
    }

    /**
     * Sets the value of the mCategoryName property.
     * @param pCategoryName the name of the category of the Tag
     */
    public void setCategoryName( String pCategoryName )
    {
        mCategoryName = pCategoryName;
    }
    
    /**
     * Access method for the mPbTagCategoryName property.
     * @return the name of the category of the Tag
     */
    public String getPbTagCategoryName()
    {
        return mPbTagCategoryName;
    }

    /**
     * Sets the value of the mPbTagCategoryName property.
     * @param pPbTagCategoryName the name of the category of the Tag
     */
    public void setPbTagCategoryName( String pPbTagCategoryName )
    {
        mPbTagCategoryName = pPbTagCategoryName;
    }
    
    /**
     * Access method for the mCategoryForm property.
     * @return the category of the Tag
     */
    public TagCategoryForm getCategoryForm()
    {
        return mCategoryForm;
    }

    /**
     * Sets the value of the mCategoryForm property.
     * @param pCategoryForm the Category given to the tag
     */
    public void setCategoryForm( TagCategoryForm pCategoryForm )
    {
        mCategoryForm = pCategoryForm;
    }
    
    /**
     * Access method for the mTags property.
     * @return the current list of Tags
     */
    public Collection getTags()
    {
        return mTags;
    }

    /**
     * Sets the value of the mTags property.
     * @param pTags the new list of tags
     */
    public void setTags( Collection pTags )
    {
        mTags = pTags;
    }
    
    /**
     * Access method for the mTagCategories property.
     * @return the current list of Tag Categories
     */
    public Collection<TagCategoryForm> getTagCategories()
    {
        return mTagCategories;
    }

    /**
     * Sets the value of the mTagCategories property.
     * @param pTagCategories the new list of tag categories
     */
    public void setTagCategories( Collection<TagCategoryForm> pTagCategories )
    {
        mTagCategories = pTagCategories;
    }
    
    /**
     * Access method for the mTagCatName property.
     * @return the name of the Tag Category
     */
    public String getTagCatName()
    {
        return mTagCatName;
    }

    /**
     * Sets the value of the mTagCatName property.
     * @param pTagCatName the name given to the tag category
     */
    public void setTagCatName( String pTagCatName )
    {
        mTagCatName = pTagCatName;
    }
    
    /**
     * Access method for the mTagCatDescription property.
     * @return the description of the Tag Category
     */
    public String getTagCatDescription()
    {
        return mTagCatDescription;
    }

    /**
     * Sets the value of the mTagCatDescription property.
     * @param pTagCatDescription the description given to the tag category
     */
    public void setTagCatDescription( String pTagCatDescription )
    {
        mTagCatDescription = pTagCatDescription;
    }
    
    /**
     * Access method for the mTagId property.
     * @return the id of the Tag
     */
    public String getTagId()
    {
        return mTagId;
    }

    /**
     * Sets the value of the mTagId property.
     * @param pTagId the id given to the tag
     */
    public void setTagId( String pTagId )
    {
        mTagId = pTagId;
    }
    
    /**
     * Access method for the mTagCategoryId property.
     * @return the id of the tag category
     */
    public String getTagCategoryId()
    {
        return mTagCategoryId;
    }

    /**
     * Sets the value of the mTagCategoryId property.
     * @param pTagCategoryId the id given to the tag category
     */
    public void setTagCategoryId( String pTagCategoryId )
    {
        mTagCategoryId = pTagCategoryId;
    }

    /**
     * Access method for the mTagIndex property.
     * @return the index of the Tag modified
     */
    public String getTagIndex()
    {
        return mTagIndex;
    }

    /**
     * Sets the value of the mTagIndex property.
     * @param pTagIndex the index of the Tag modified
     */
    public void setTagIndex( String pTagIndex )
    {
        mTagIndex = pTagIndex;
    }
    
    /**
     * Access method for the mTagCategoryIndex property.
     * @return the index of the Tag category modified
     */
    public String getTagCategoryIndex()
    {
        return mTagCategoryIndex;
    }

    /**
     * Sets the value of the mTagCategoryIndex property.
     * @param pTagCategoryIndex the index of the Tag category modified
     */
    public void setTagCategoryIndex( String pTagCategoryIndex )
    {
        mTagCategoryIndex = pTagCategoryIndex;
    }
    
    /**
     * Access method for the mTagModified property
     * @return wether a tag is being modified or not 
     */
    public boolean isTagModified()
    {
        return mTagModified;
    }

    /**
     * Sets the value of the mTagModified property
     * @param pTagModified wether a tag will be modified or not
     */
    public void setTagModified( boolean pTagModified )
    {
        mTagModified = pTagModified;
    }
    
    /**
     * Access method for the mTagCategoryModified property
     * @return wether a tag category is being modified or not 
     */
    public boolean isTagCategoryModified()
    {
        return mTagCategoryModified;
    }

    /**
     * Sets the value of the mTagCategoryModified property
     * @param pTagCategoryModified wether a tag category will be modified or not
     */
    public void setTagCategoryModified( boolean pTagCategoryModified )
    {
        mTagCategoryModified = pTagCategoryModified;
    }
    
    /**
     * Access method for the mPbTagCategory property
     * @return wether there is a pb with the association of the chosen tagCategory
     */
    public boolean isPbTagCategory()
    {
        return mPbTagCategory;
    }

    /**
     * Sets the value of the mPbTagCategory property
     * @param pPbTagCategory there is a pb with the association of the chosen tagCategory
     */
    public void setPbTagCategory( boolean pPbTagCategory )
    {
        mPbTagCategory = pPbTagCategory;
    }
    
    /**
     * Access method for the mPbTagName property.
     * @return the name of the Tag that causes problems
     */
    public String getPbTagName()
    {
        return mPbTagName;
    }

    /**
     * Sets the value of the mPbTagName property.
     * @param pPbTagName the name of the Tag that causes problems
     */
    public void setPbTagName( String pPbTagName )
    {
        mPbTagName = pPbTagName;
    }    
    
    /**
     * Access method for the mPbTag property
     * @return wether there is a pb with the chosen tag name
     */
    public boolean isPbTag()
    {
        return mPbTag;
    }

    /**
     * Sets the value of the mPbTag property
     * @param pPbTag there is a pb with the chosen tag name
     */
    public void setPbTag( boolean pPbTag )
    {
        mPbTag = pPbTag;
    }
    
    /**
     * Access method for the mPbCategoryName property.
     * @return the name of the Category that causes problems
     */
    public String getPbCategoryName()
    {
        return mPbCategoryName;
    }

    /**
     * Sets the value of the mPbCategoryName property.
     * @param pPbCategoryName the name of the Category that causes problems
     */
    public void setPbCategoryName( String pPbCategoryName )
    {
        mPbCategoryName = pPbCategoryName;
    }    
    
    /**
     * Access method for the mPbCategory property
     * @return wether there is a pb with the chosen Category name
     */
    public boolean isPbCategory()
    {
        return mPbCategory;
    }

    /**
     * Sets the value of the mPbCategory property
     * @param pPbCategory there is a pb with the chosen Category name
     */
    public void setPbCategory( boolean pPbCategory )
    {
        mPbCategory = pPbCategory;
    }
}
