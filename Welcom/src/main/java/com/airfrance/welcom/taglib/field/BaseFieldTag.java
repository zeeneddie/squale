package com.airfrance.welcom.taglib.field;

import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.Charte;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.field.util.LayoutUtils;

/**
 * Base class for the tags dealing with form input The tag renders html code looking like: &lt;tr&gt;&lt;th&gt; input
 * field title &lt;/th&gt;&lt;td&gt; input field &lt;/td&gt;&lt;/tr&gt;
 * 
 * @author: F Madaule
 */
public abstract class BaseFieldTag
    extends LabelledTag
{

    /** Logger */
    private static Log logger = LogFactory.getLog( LabelledTag.class );

    /** Si le champs est requis */
    protected boolean isRequired = false;

    /** Ajout de la proprité styleId */
    protected String styleId = null;

    /** id auto generer */
    private static long autoid = 0;

    /** prefixe auto id */
    private static final String AUTOID_PREFIX = "welcomFieldAutoId";

    /** Constante */
    public static final String CHECKBOX = "CHECKBOX";

    /** Constante */
    public static final String EMAIL = "EMAIL";

    /** Constante */
    public static final String NUMBER = "NUMBER";

    /** Constante */
    public static final String PASSWORD = "PASSWORD";

    /** Constante */
    public static final String READONLY = "READONLY";

    /** Constante */
    public static final String READSEND = "READSEND";

    /** Constante */
    public static final String READWRITE = "READWRITE";

    /** Constante */
    public static final String DATE = "DATE";

    /** Constante */
    public static final String DATEHEURE = "DATEHEURE";

    /** Constante */
    public static final String TEXT = "TEXT";

    /** Constante */
    public static final String TEXTAREA = "TEXTAREA";

    /** Constante */
    public static final String RADIO = "RADIO";

    /** type par defaut */
    protected String type = TEXT;

    /**
     * Append the title of the field to the buffer
     * 
     * @param buffer le stringbuffer
     * @throws JspException exception pouvant etre levee
     */
    protected void beginField( final StringBuffer buffer )
        throws JspException
    {
        doRenderLabel( buffer );

        if ( writeTD )
        {
            buffer.append( "<td" );

            if ( !GenericValidator.isBlankOrNull( styleClass ) )
            {
                buffer.append( " class=\"" + styleClass + "\"" );
            }

            if ( !GenericValidator.isBlankOrNull( colspan ) )
            {
                buffer.append( " colspan=\"" + colspan + "\"" );
            }

            if ( !GenericValidator.isBlankOrNull( width ) )
            {
                buffer.append( " width=\"" + width + "\"" );
            }

            buffer.append( " valign=\"middle\">" );
        }
    }

    /**
     * Gère le label
     * 
     * @param buffer le Stringbuffer
     * @throws JspException exception pouvant etre levee
     */
    private void doRenderLabel( final StringBuffer buffer )
        throws JspException
    {
        if ( hasLabel() )
        {
            if ( writeTD )
            {
                buffer.append( "<td" );

                /*
                 * if (WelcomConfigurator.getCharte()==Charte.V3_001 && "td1".equals(styleClassLabel)) {
                 * logger.info("Supprimer 'styleclass=td1' dans la page JSP"); styleClassLabel="right"; }
                 */

                if ( GenericValidator.isBlankOrNull( styleClassLabel ) )
                {
                    if ( WelcomConfigurator.getCharte() == Charte.V3_001 )
                    {
                        styleClassLabel = "right";
                    }
                    else if ( WelcomConfigurator.getCharte() == Charte.V2_002 )
                    {
                        styleClassLabel = "td1";
                    }
                    else
                    {
                        styleClassLabel = "formtdr";
                    }
                }

                buffer.append( " class=\"" + styleClassLabel + "\"" );

                if ( !GenericValidator.isBlankOrNull( widthLabel ) )
                {
                    buffer.append( " width=\"" + widthLabel + "\"" );
                }

                if ( !GenericValidator.isBlankOrNull( colspanLabel ) )
                {
                    buffer.append( " colspan=\"" + colspanLabel + "\"" );
                }

                buffer.append( ">" );
            }

            // ecrit le libellé
            writeLabel( buffer );

            // ecrit le td final
            if ( writeTD )
            {
                buffer.append( "</td>" );
            }
        }
    }

    /**
     * Retourne vrai si on a a generer un label
     * 
     * @return true si on a a generer un label
     * @throws JspException exception sur la recuperation du lbale
     */
    private boolean hasLabel()
        throws JspException
    {
        return ( super.key != null ) && ( getLabel() != null ) && ( getLabel().length() > 0 );
    }

    /**
     * Ecrit le contenu du label
     * 
     * @param buffer stringbuffer
     * @throws JspException exception
     */
    private void writeLabel( final StringBuffer buffer )
        throws JspException
    {

        // S'il ya une puce
        if ( isLi() )
        {
            buffer.append( "<li>" );
        }

        // Si c'est un bouton radio ou
        // une check box, on rajoute le label
        if ( type.equalsIgnoreCase( CHECKBOX ) || type.equalsIgnoreCase( RADIO ) )
        {

            if ( ( getStyleId() == null ) || getStyleId().equals( "" ) )
            {
                // Génération de l'ID auto ssi aucun ID n'a été défini dans la balise
                setStyleId( getAutoId() );
            }

            buffer.append( "<LABEL FOR=\"" + getStyleId() + "\">" );
            buffer.append( getLabel() );
            buffer.append( "</LABEL>" );
        }
        else
        {
            buffer.append( getLabel() );
        }

        // Ajout de l'étoiel si on est en charte V3
        if ( isRequired && WelcomConfigurator.getCharte() == Charte.V3_001 )
        {
            buffer.append( " *" );
        }
    }

    /**
     * End the field (close the html tags)
     * 
     * @param buffer le stringbuffer
     */
    protected void endField( final StringBuffer buffer )
    {
        if ( writeTD )
        {
            buffer.append( "</td>" );
        }
    }

    /**
     * @see com.airfrance.welcom.taglib.field.LabelledTag#setProperty(java.lang.String)
     */
    public void setProperty( final String pProperty )
    {
        property = pProperty;

        if ( LayoutUtils.getNoErrorMode() )
        {
            this.property = "property";
        }
    }

    /**
     * @return accesseur
     */
    public String getStyleId()
    {
        return styleId;
    }

    /**
     * @param string accesseur
     */
    public void setStyleId( String string )
    {
        styleId = string;
    }

    /**
     * Retourn l'id
     * 
     * @return retourne l'id
     */
    private synchronized String getAutoId()
    {
        return AUTOID_PREFIX + ( autoid++ );
    }
}