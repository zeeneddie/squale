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
package com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking;

import java.io.Serializable;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;

/**
 * Item de transgression
 * 
 * @hibernate.class table="RuleCheckingTransgressionItem" mutable="true" lazy="true"
 */
public class RuleCheckingTransgressionItemBO
    implements Serializable
{

    /** Mot-clé du message qui sera remplacé par le chemin du fichier */
    public static final String PATH_KEY = "#path#";

    /** Mot-clé du message qui sera remplacé par le numéro de ligne */
    public static final String LINE_KEY = "#line#";

    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId = -1;

    /** Le fichier du composant concerné par la transgression */
    private String mComponentFile;

    /** La ligne qui transgresse la règle */
    private int mLine;

    /** Détail de la transgression */
    private String mMessage;

    /** Composant concerné par la transgression */
    private AbstractComponentBO mComponent;

    /** Règle transgressée */
    private RuleBO mRule;

    /** Composant en relation avec la transgression */
    private AbstractComponentBO mComponentInvolved;

    /**
     * Constructeur par défaut
     */
    public RuleCheckingTransgressionItemBO()
    {
        mId = -1;
        mComponentFile = "";
    }

    /**
     * @return l'identifiant (au sens technique) de l'objet
     * @hibernate.id generator-class="native" type="long" column="ItemId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="TransgressionItem_sequence"
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @return la ligne
     * @hibernate.property name="line" column="Line" type="int" length="10" not-null="true" update="true" insert="true"
     */
    public int getLine()
    {
        return mLine;
    }

    /**
     * @return le fichier du composant
     * @hibernate.property name="componentFile" column="Path" length="3000" type="string" update="true" insert="true"
     */
    public String getComponentFile()
    {
        return ( null == mComponentFile ) ? "" : mComponentFile;
    }

    /**
     * @return le détail de la transgession
     * @hibernate.property name="message" column="Message" not-null="true" length="3000" type="string" unique="false"
     *                     update="true" insert="true"
     */
    public String getMessage()
    {
        return mMessage.replaceAll( PATH_KEY, getComponentFile() ).replaceAll( LINE_KEY, "" + getLine() );
    }

    /**
     * @return le composant concerné par la transgression
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO"
     *                        column="ComponentId" cascade="all" outer-join="auto" update="true" insert="true"
     */
    public AbstractComponentBO getComponent()
    {
        return mComponent;
    }

    /**
     * @return la règle transgressée
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO"
     *                        column="RuleId" cascade="all" not-null="true" outer-join="auto" update="true"
     *                        insert="true"
     */
    public RuleBO getRule()
    {
        return mRule;
    }

    /**
     * @return le composant en relation avec la transgression
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO"
     *                        column="ComponentInvolvedId" cascade="all" outer-join="auto" update="true" insert="true"
     */
    public AbstractComponentBO getComponentInvolved()
    {
        return mComponentInvolved;
    }

    /**
     * Modifie mId
     * 
     * @param pId l'identifiant (au sens technique) de l'objet
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Modifie mMessage
     * 
     * @param pMessage le détail de la transgession
     */
    public void setMessage( String pMessage )
    {
        mMessage = pMessage;
    }

    /**
     * Modifie mComponent
     * 
     * @param pComponent le composant concerné par la transgression
     */
    public void setComponent( AbstractComponentBO pComponent )
    {
        mComponent = pComponent;
    }

    /**
     * Modifie mRule
     * 
     * @param pRule la règle transgressée
     */
    public void setRule( RuleBO pRule )
    {
        mRule = pRule;
    }

    /**
     * Modifie mComponentInvolved
     * 
     * @param pComponentInvolved le composant en relation avec la transgression
     */
    public void setComponentInvolved( AbstractComponentBO pComponentInvolved )
    {
        mComponentInvolved = pComponentInvolved;
    }

    /**
     * @param pComponentFile le fichier du composant
     */
    public void setComponentFile( String pComponentFile )
    {
        mComponentFile = pComponentFile;
    }

    /**
     * @param pLine la ligne
     */
    public void setLine( int pLine )
    {
        mLine = pLine;
    }

}
