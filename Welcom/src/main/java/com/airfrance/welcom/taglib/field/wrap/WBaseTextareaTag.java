package com.airfrance.welcom.taglib.field.wrap;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.taglib.html.TextareaTag;

import com.airfrance.welcom.taglib.field.util.TagUtils;

public class WBaseTextareaTag
    extends TextareaTag
    implements IWelcomInputTag
{

    /** Lance la verification du spell check */
    protected boolean spellChecked = false;

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

    /**
     * {@inheritDoc}
     */
    protected String prepareEventHandlers()
    {
        updateEvents();
        StringBuffer sb = new StringBuffer();
        sb.append( super.prepareEventHandlers() );
        if ( isSpellChecked() )
        {
            sb.append( " spell=\"true\"" );
        }
        return sb.toString();
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

        return TagUtils.getJavascriptCheckValue( property, "TEXTAREA", isRequired, isUpperCase(), isFirstUpperCase(),
                                                 accent );

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

}
