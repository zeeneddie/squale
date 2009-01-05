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
package com.airfrance.welcom.taglib.field.wrap;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.taglib.field.BaseTextTag;
import com.airfrance.welcom.taglib.field.util.TagUtils;

public class WBaseTextTag
    extends BaseTextTag
    implements IWelcomInputTag
{

    /** Gestion des majuscules (transforme automatiquement en majuscule) */
    protected boolean upperCase = false;

    /** Gestion de la premiere lettre en majuscule */
    protected boolean firstUpperCase = false;

    /** Si le champs est requis */
    protected boolean isRequired = false;

    /** accent */
    protected boolean accent = true;

    /** Active le filtrage de caractere spécuax */
    protected boolean filterSpecialChar = false;

    /** le parametre du tag */
    protected java.lang.String autoComplete = null;

    /** Lance la verification du spell check */
    protected boolean spellChecked = false;

    /** type pour le check */
    protected String javascriptType = "TEXT";

    /**
     * {@inheritDoc}
     */
    protected String prepareEventHandlers()
    {
        updateEvents();
        return super.prepareEventHandlers();
    }

    /**
     * {@inheritDoc}
     */
    protected void prepareOthersAttributes( StringBuffer sb )
    {
        if ( Util.isFalse( getAutoComplete() ) )
        {
            sb.append( " autocomplete=\"off\"" );
        }

        if ( isSpellChecked() )
        {
            sb.append( " spell=\"true\"" );
        }

        // TODO Raccord de méthode auto-généré
        super.prepareOthersAttributes( sb );
    }

    /**
     * Met a jour les evements du tags Verification des caracteres spéciaux Verification classique
     */
    protected void updateEvents()
    {
        updateOnChange();
        updateOnBlur();
    }

    /**
     * Ajoute la fonction javascript de validation sur le onchange
     */
    protected void updateOnChange()
    {
        // Ajoute les validation javascript sur le onchange
        setOnchange( getJavascriptForCheckValue() + ( ( getOnchange() != null ) ? getOnchange() : "" ) );
        if ( GenericValidator.isBlankOrNull( getOnchange() ) )
        {
            setOnchange( null );
        }
    }

    /**
     * Ajoute la fonction javascript de validation sur le onchange
     */
    protected void updateOnBlur()
    {
        // Ajoute les validation javascript sur le onchange
        setOnblur( getJavascriptForCheckSpecialChar() + ( ( getOnblur() != null ) ? getOnblur() : "" ) );
        if ( GenericValidator.isBlankOrNull( getOnblur() ) )
        {
            setOnblur( null );
        }
    }

    /**
     * Recupere la fonction de validation javascript
     * 
     * @return la fonction de validation renseigné
     */
    private String getJavascriptForCheckValue()
    {

        return TagUtils.getJavascriptCheckValue( property, javascriptType, isRequired, isUpperCase(),
                                                 isFirstUpperCase(), accent );

    }

    /**
     * Ajout de la verification des caracteres spéciaux
     * 
     * @param myFieldTag : field
     */
    private String getJavascriptForCheckSpecialChar()
    {
        if ( isFilterSpecialChar() )
        {
            return TagUtils.getJavascriptForCheckSpecialChar( property );
        }
        else
        {
            return "";
        }
    }

    /**
     * @return accent attribut
     */
    public boolean isAccent()
    {
        return accent;
    }

    /**
     * @param accent accent attribut
     */
    public void setAccent( boolean accent )
    {
        this.accent = accent;
    }

    /**
     * @return firstUpperCase attribut
     */
    public boolean isFirstUpperCase()
    {
        return firstUpperCase;
    }

    /**
     * @param firstUpperCase firstUpperCase attribut
     */
    public void setFirstUpperCase( boolean firstUpperCase )
    {
        this.firstUpperCase = firstUpperCase;
    }

    /**
     * @return isRequired attribut
     */
    public boolean isRequired()
    {
        return isRequired;
    }

    /**
     * @param isRequired isRequired attribut
     */
    public void setRequired( boolean isRequired )
    {
        this.isRequired = isRequired;
    }

    /**
     * @return upperCase attribut
     */
    public boolean isUpperCase()
    {
        return upperCase;
    }

    /**
     * @param upperCase upperCase attribut
     */
    public void setUpperCase( boolean upperCase )
    {
        this.upperCase = upperCase;
    }

    /**
     * @return filterSpecialChar attribut
     */
    public boolean isFilterSpecialChar()
    {
        return filterSpecialChar;
    }

    /**
     * @param filterSpecialChar filterSpecialChar attribut
     */
    public void setFilterSpecialChar( boolean filterSpecialChar )
    {
        this.filterSpecialChar = filterSpecialChar;
    }

    /**
     * @return autoComplete attribut
     */
    public java.lang.String getAutoComplete()
    {
        return autoComplete;
    }

    /**
     * @param autoComplete autoComplete attribut
     */
    public void setAutoComplete( java.lang.String autoComplete )
    {
        this.autoComplete = autoComplete;
    }

    /**
     * @return spellChecked attribut
     */
    public boolean isSpellChecked()
    {
        return spellChecked;
    }

    /**
     * @param spellChecked spellChecked attribut
     */
    public void setSpellChecked( boolean spellChecked )
    {
        this.spellChecked = spellChecked;
    }
}
