/*
 * Créé le 2 juin 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.field.wrap;

import javax.servlet.jsp.JspException;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.canvas.CanvasUtil;

/**
 * AutoCompleteTag
 */
public class WBaseAutoCompleteTag
    extends WBaseTextTag
{
    /**
     * 
     */
    private static final long serialVersionUID = 4279121296835714925L;

    // easyComplete
    /** Nom du bean contenant la collection ou nom de la collection si celle-ci est en session */
    protected String easyCompleteName = "";

    /** Nom de la collection contenue dans le bean déclaré dans easyCompleteName */
    protected String easyCompleteProperty = "";

    /** Nom de la propriété des beans contenus dans la collection représentant la valeur à renseigner. */
    protected String easyCompleteBeanValue = "";

    /** Nom de la propriété des beans contenus dans la collection représentant le label à afficher. */
    protected String easyCompleteBeanLabel = "";

    /** Nom de la méthode à appeller pour récupérer la liste des éléments à afficher. */
    protected String easyCompleteCallBackUrl = "";

    /** Nom de la classe css à appliquer à la partie de texte matchée. */
    protected String easyCompleteDecorationClass = "";

    /** parametre du tag */
    private String onitemselection;

    /**
     * {@inheritDoc}
     */
    public int doStartTag()
        throws JspException
    {
        setAutoComplete( "false" );
        addJSEasyComplete();
        return super.doStartTag();
    }

    /**
     * Ajoute l'attribut onintemselection
     */
    protected void prepareOthersAttributes( StringBuffer results )
    {

        // Recupere lea attributs du parent
        super.prepareOthersAttributes( results );

        if ( Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.EASY_COMPLETE_NOTIFIER ) ) )
        {
            setStyleClass( ( getStyleClass() != null ) ? getStyleClass() : "" + " suggest" );
        }

        if ( onitemselection != null )
        {
            results.append( " onitemselection=\"" );
            results.append( onitemselection.replaceAll( "this", "target" ) );
            results.append( "\"" );
        }

    }

    /**
     * {@inheritDoc}
     */
    protected void updateEvents()
    {
        super.updateEvents();
        updateOnKeyUp();
    }

    /**
     * Ajoute le js de l'easycomplete
     * 
     * @throws JspException probleme sur lajout du js de l'easycomplete
     */
    protected void addJSEasyComplete()
        throws JspException
    {
        if ( !GenericValidator.isBlankOrNull( easyCompleteName )
            || !GenericValidator.isBlankOrNull( easyCompleteCallBackUrl ) )
        {
            final String js = (String) pageContext.getRequest().getAttribute( "jsEasyComplete" );

            if ( js == null )
            {
                pageContext.getRequest().setAttribute( "jsEasyComplete", "true" );
                CanvasUtil.addJs( WelcomConfigurator.getMessage( WelcomConfigurator.HEADER_LOCALJS_PATH_KEY )
                    + "auto-complete.js", this, pageContext );
            }
        }
    }

    /**
     * Ajoute la fonction javascript de l'ajout de l'autocompletion sur le onkeyup
     */
    protected void updateOnKeyUp()
    {
        // Ajoute les validation javascript sur le onchange
        setOnkeyup( getJavascriptEasyComplete() + ( ( getOnkeyup() != null ) ? getOnkeyup() : "" ) );
    }

    /**
     * Ecrit le info necessaire pour l'easyComplete
     * 
     * @return fonction javascript pour l'initialisation de l'autocomplete
     */
    private String getJavascriptEasyComplete()
    {
        final StringBuffer buf = new StringBuffer();
        if ( ( !GenericValidator.isBlankOrNull( easyCompleteName ) || !GenericValidator.isBlankOrNull( easyCompleteCallBackUrl ) ) )
        {
            buf.append( "doCompletion(event,this" );

            buf.append( getJavascriptParam( easyCompleteName, null ) );
            buf.append( getJavascriptParam( easyCompleteProperty, null ) );
            buf.append( getJavascriptParam( easyCompleteBeanValue, null ) );
            buf.append( getJavascriptParam( easyCompleteBeanLabel, null ) );
            buf.append( getJavascriptParam(
                                            easyCompleteDecorationClass,
                                            WelcomConfigurator.getMessage( WelcomConfigurator.EASY_COMPLETE_DEFAULT_DECORATION_CLASS ) ) );
            buf.append( getJavascriptParam( easyCompleteCallBackUrl, null ) );

            buf.append( ");" );
        }
        return buf.toString();
    }

    /**
     * retourn le parametre ",value"
     * 
     * @param value valeur
     * @param valueIfValueNull valeur si nulle
     */
    private String getJavascriptParam( final String value, final String valueIfValueNull )
    {
        String bufString = ",null";
        if ( !GenericValidator.isBlankOrNull( value ) )
        {
            bufString = ",'" + value + "'";
        }
        else if ( !GenericValidator.isBlankOrNull( valueIfValueNull ) )
        {
            bufString = ",'" + valueIfValueNull + "'";
        }
        return bufString;
    }

    /**
     * @return onitemselection
     */
    public String getOnitemselection()
    {
        return onitemselection;
    }

    /**
     * @param string onitemselection
     */
    public void setOnitemselection( final String string )
    {
        onitemselection = string;
    }

    /**
     * @return easyCompleteBeanLabel attribut
     */
    public String getEasyCompleteBeanLabel()
    {
        return easyCompleteBeanLabel;
    }

    /**
     * @param easyCompleteBeanLabel easyCompleteBeanLabel attribut
     */
    public void setEasyCompleteBeanLabel( String easyCompleteBeanLabel )
    {
        this.easyCompleteBeanLabel = easyCompleteBeanLabel;
    }

    /**
     * @return easyCompleteBeanValue attribut
     */
    public String getEasyCompleteBeanValue()
    {
        return easyCompleteBeanValue;
    }

    /**
     * @param easyCompleteBeanValue easyCompleteBeanValue attribut
     */
    public void setEasyCompleteBeanValue( String easyCompleteBeanValue )
    {
        this.easyCompleteBeanValue = easyCompleteBeanValue;
    }

    /**
     * @return easyCompleteCallBackUrl attribut
     */
    public String getEasyCompleteCallBackUrl()
    {
        return easyCompleteCallBackUrl;
    }

    /**
     * @param easyCompleteCallBackUrl easyCompleteCallBackUrl attribut
     */
    public void setEasyCompleteCallBackUrl( String easyCompleteCallBackUrl )
    {
        this.easyCompleteCallBackUrl = easyCompleteCallBackUrl;
    }

    /**
     * @return easyCompleteDecorationClass attribut
     */
    public String getEasyCompleteDecorationClass()
    {
        return easyCompleteDecorationClass;
    }

    /**
     * @param easyCompleteDecorationClass easyCompleteDecorationClass attribut
     */
    public void setEasyCompleteDecorationClass( String easyCompleteDecorationClass )
    {
        this.easyCompleteDecorationClass = easyCompleteDecorationClass;
    }

    /**
     * @return easyCompleteName attribut
     */
    public String getEasyCompleteName()
    {
        return easyCompleteName;
    }

    /**
     * @param easyCompleteName easyCompleteName attribut
     */
    public void setEasyCompleteName( String easyCompleteName )
    {
        this.easyCompleteName = easyCompleteName;
    }

    /**
     * @return easyCompleteProperty attribut
     */
    public String getEasyCompleteProperty()
    {
        return easyCompleteProperty;
    }

    /**
     * @param easyCompleteProperty easyCompleteProperty attribut
     */
    public void setEasyCompleteProperty( String easyCompleteProperty )
    {
        this.easyCompleteProperty = easyCompleteProperty;
    }

}