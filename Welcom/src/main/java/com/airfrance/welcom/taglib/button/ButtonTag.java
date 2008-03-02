package com.airfrance.welcom.taglib.button;

import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.outils.Access;
import com.airfrance.welcom.outils.Charte;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.struts.util.WRequestUtils;
import com.airfrance.welcom.taglib.canvas.CanvasLeftMenuTag;
import com.airfrance.welcom.taglib.formulaire.FormulaireBottomTag;
import com.airfrance.welcom.taglib.html.FormTag;
import com.airfrance.welcom.taglib.onglet.JSOngletBottomTag;
import com.airfrance.welcom.taglib.renderer.RendererFactory;
import com.airfrance.welcom.taglib.table.TableBottomTag;

/**
 * classe du ButtonTag
 */
public class ButtonTag
    extends BodyTagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = -3435140538369187645L;

    /** constante READONLY */
    private final static String READONLY = "READONLY";

    /** constante READWRITE */
    private final static String READWRITE = "READWRITE";

    /** constante TYPE_MENU */
    private final static String TYPE_MENU = "menu";

    /** constante TYPE_FORM */
    private final static String TYPE_FORM = "form";

    /** parametre du tag */
    private String onclick = "";

    /** parametre du tag */
    private String name = "";

    /** parametre du tag */
    private String type = ""; // form (par defaut)ou menu

    /** parametre du tag */
    private String target = "";

    /** parametre du tag */
    private boolean forceReadWrite = false;

    /** parametre du tag */
    private String callMethod = "";

    /** parametre du tag */
    private String messageConfirmationKey = "";

    /** parametre du tag */
    private String toolTipKey = "";

    /** parametre du tag */
    private boolean singleSend = true;

    /** parametre du tag */
    private String causesValidation = "";

    /** parametre du tag */
    private String accessKey = "";

    /** parametre du tag */
    private String overridePageAccess = null;

    /** le current parent */
    private Tag curParent = null;

    /** url du callback pour récupération du message dynamique par le moteur Ajax */
    private String messageConfirmationCallBack = "";

    /** ajout de l'id de l'objet pour le DOM */
    private String styleId = null;

    /** Identifiant le l'objet progressbar associé */
    private String progressbarId;

    /** render */
    private static IButtonRenderer render = (IButtonRenderer) RendererFactory.getRenderer( RendererFactory.BUTTON );

    public int doStartTag()
        throws JspException
    {
        return EVAL_BODY_BUFFERED;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doEndTag()
        throws JspException
    {
        /*
         * // Recupere la locale Locale locale = (Locale)pageContext.getSession().getAttribute(Globals.LOCALE_KEY); if
         * (locale!=null && locale.getLanguage()!=null) localeString = locale.getLanguage(); // Recuperer le fichier des
         * Bundle resources = (MessageResources) pageContext.getServletContext().getAttribute( Globals.MESSAGES_KEY);
         */
        final StringBuffer results = new StringBuffer();

        // Recherche si un parent est du bon type
        for ( curParent = getParent(); ( curParent != null )
            && !( ( curParent instanceof CanvasLeftMenuTag ) || ( curParent instanceof TableBottomTag )
                || ( curParent instanceof JSOngletBottomTag ) || ( curParent instanceof ButtonBarTag ) || ( curParent instanceof FormulaireBottomTag ) ); curParent =
            curParent.getParent() )
        {
            ;
        }

        // Si le type n'est pas spécifier alors le deduit ..
        if ( GenericValidator.isBlankOrNull( type ) )
        {
            if ( curParent instanceof CanvasLeftMenuTag )
            {
                type = TYPE_MENU;
            }
            else
            {
                type = TYPE_FORM;
            }
        }

        if ( curParent instanceof CanvasLeftMenuTag )
        {
            ( (CanvasLeftMenuTag) curParent ).setContainsBoutons( true );
        }

        // Recupere le droit sur la page
        // String pageAccess = (String) pageContext.getAttribute("access");

        final String bouttonBody = getBouttonBody();

        // Encapsule dans le tag
        if ( ( curParent instanceof JSOngletBottomTag ) || ( curParent instanceof TableBottomTag )
            || ( curParent instanceof ButtonBarTag ) || ( curParent instanceof FormulaireBottomTag ) )
        {
            if ( WelcomConfigurator.getCharte() == Charte.V2_002 || WelcomConfigurator.getCharte() == Charte.V2_001 )
            {
                if ( !GenericValidator.isBlankOrNull( bouttonBody ) )
                {
                    results.append( "<td align=\"center\" valign=\"middle\">" );
                    results.append( bouttonBody );
                    results.append( "</td>" );
                }
            }
            else
            {
                results.append( bouttonBody );
            }
        }
        else if ( curParent instanceof CanvasLeftMenuTag )
        {
            // div.menuAction
            if ( WelcomConfigurator.getCharte() == Charte.V2_002 )
            {
                if ( Util.isTrimNonVide( bouttonBody ) )
                {
                    results.append( "<div>" );
                    results.append( bouttonBody );
                    results.append( "</div>" );
                }
            }
            else if ( WelcomConfigurator.getCharte() == Charte.V2_001 )
            {
                results.append( "<tr><td>" );
                results.append( bouttonBody );
                results.append( "</td></tr>" );
                if ( Util.isTrimNonVide( bouttonBody ) )
                {
                    final String pix_grey = WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_PIX_GREY_GIF );
                    results.append( "<tr><td><img src=\"" );
                    results.append( pix_grey );
                    results.append( "\" width=\"140\" height=\"1\" border=\"0\"></td></tr>\n" );
                }
            }
            else
            {
                if ( Util.isTrimNonVide( bouttonBody ) )
                {
                    results.append( "<ul>" );
                    results.append( bouttonBody );
                    results.append( "</ul>" );
                }
            }
        }
        else
        {
            results.append( bouttonBody );
        }

        // Publie
        ResponseUtils.write( pageContext, results.toString() );

        return EVAL_PAGE;
    }

    /**
     * retourne le html genere du bouton
     * 
     * @return le boutonBody
     * @throws JspException exception susceptible d'etre levee
     */
    private String getBouttonBody()
        throws JspException
    {
        // Recupere le droit sur la page
        final StringBuffer results = new StringBuffer();

        // Calcule le forceReadWrite avec les possilité de passer en mode legacy
        boolean myForceReadWrite =
            forceReadWrite
                || ( pageContext.getAttribute( "access" ) == null && Util.isFalse( WelcomConfigurator.getMessage( WelcomConfigurator.ACCESS_KEY_USE_LEGACY ) ) );
        String computedAccess =
            Access.computeTagReadWriteAccess( pageContext, accessKey, myForceReadWrite,
                                              Util.isTrue( overridePageAccess ) );

        if ( type.equals( "form" ) && ( target != null ) && !target.equals( "" ) )
        {
            throw new JspException( "Le formulaire ne peut avoir une target definie" );
        }

        if ( type.equals( "form" ) )
        {
            results.append( getFormButtonBody( computedAccess ) );
        }
        else
        {
            results.append( getMenuButtonBody( computedAccess ) );
        }

        return results.toString();
    }

    /**
     * Retourn le corp du bottom si c'est un bouton de type menu action
     * 
     * @param computedAccess acess avecle quel on genere le bouton
     * @return le bouton generé
     * @throws JspException Pb sur le renderer
     */
    private String getMenuButtonBody( String computedAccess )
        throws JspException
    {
        if ( READWRITE.equals( computedAccess ) )
        {
            String bb = null;
            if ( getBodyContent() != null )
            {
                bb = getBodyContent().getString();
                getBodyContent().clearBody();
            }
            return ( render.drawRenderMenuHRefTag( pageContext, curParent, getName(), bb, target, getOnclick(),
                                                   getHrefOnClick(), getToolTip(), styleId ) );

        }
        else
        {

            return "";
        }

    }

    /**
     * Retourn le corp du bottom si c'est un bouton de type formulaire
     * 
     * @param computedAccess acess avecle quel on genere le bouton
     * @return le bouton generé
     * @throws JspException Pb sur le renderer
     */
    private String getFormButtonBody( String computedAccess )
        throws JspException
    {
        String results = "";

        // Si on passe l'info readonly des profils .... on n'affiche que le bouton recherche
        if ( READWRITE.equals( computedAccess ) )
        {
            String bb = null;
            if ( getBodyContent() != null )
            {
                bb = getBodyContent().getString();
                getBodyContent().clearBody();
            }
            if ( ( GenericValidator.isBlankOrNull( causesValidation ) && checkIfInDefaultCausesValidation( name ) )
                || Util.isTrue( causesValidation ) ) // if (name.equals("rechercher") || name.equals("valider"))
            {
                final FormTag ftag = getFormTag();
                if ( ftag != null )
                {
                    if ( GenericValidator.isBlankOrNull( ftag.getDefaultAction() ) )
                    {
                        ftag.setDefaultAction( callMethod );
                    }
                }
                results =
                    render.drawRenderFormInputTag( pageContext, curParent, getName(), bb, "", getFormOnclick(),
                                                   getToolTip(), styleId );
            }
            else
            {
                // Recherche si on se trouve dans le formulaire
                // Si la page est incluse on n'a pas de tag donc on recherche
                // la trace dans la request laissé par le from de struts
                if ( pageContext.getRequest().getAttribute( "org.apache.struts.taglib.html.FORM" ) != null )
                {
                    results =
                        render.drawRenderFormHRefTag( pageContext, curParent, getName(), bb, "", getFormOnclick(),
                                                      getToolTip(), styleId );
                }
                else
                {
                    String onClic = getHrefOnClick();
                    results =
                        render.drawRenderHRefTag( pageContext, curParent, getName(), bb, "", getOnclick(), onClic,
                                                  getToolTip(), styleId );
                }
            }
        }

        return results;
    }

    /**
     * @return
     */
    private String getHrefOnClick()
    {
        if ( !GenericValidator.isBlankOrNull( progressbarId ) )
        {
            if ( ( getOnclick() != null ) && ( getOnclick().indexOf( "javascript" ) == -1 )
                && ( !getOnclick().equals( "#" ) ) )
            {

                String href = getOnclick();
                onclick = "";

                return "wPBarPreExecTaskLink ('" + progressbarId + "', this, '" + href + "');return false;";
            }
        }

        return null;
    }

    /**
     * @return retourne le formulaire contenant ce bouton
     */
    private FormTag getFormTag()
    {
        Tag tag = getParent();
        while ( tag != null )
        {
            if ( tag instanceof FormTag )
            {
                return (FormTag) tag;
            }
            tag = tag.getParent();
        }
        return null;
    }

    /**
     * construit et retourne le toolTip
     * 
     * @return le tooltip
     * @throws JspException exception pouvant etre levee
     */
    private String getToolTip()
        throws JspException
    {
        final StringBuffer buf = new StringBuffer();
        doToolTip( buf );

        return buf.toString();
    }

    /**
     * construit le toolTip
     * 
     * @param results le stringbuffer dans lequel est mis le toolTip
     * @throws JspException exception pouvant etre levee
     */
    private void doToolTip( final StringBuffer results )
        throws JspException
    {

        String toolTip = WRequestUtils.message( super.pageContext, toolTipKey );

        if ( GenericValidator.isBlankOrNull( toolTip ) )
        {
            toolTip = toolTipKey;
        }

        if ( !GenericValidator.isBlankOrNull( toolTip ) )
        {
            results.append( " title=\"" );
            results.append( toolTip );
            results.append( "\"" );
        }
    }

    /**
     * construit et retourne le onClick
     * 
     * @return le onClick
     * @throws JspException exception pouvant etre levee
     */
    private String getFormOnclick()
        throws JspException
    {
        final StringBuffer buf = new StringBuffer();
        doFormOnclick( buf );

        return buf.toString();
    }

    /**
     * Construit le onClick
     * 
     * @param results le stringbuffer dans lequel est mis le toolTip
     * @throws JspException exception pouvant etre levee
     */
    private void doFormOnclick( final StringBuffer results )
        throws JspException
    {
        String messageConfirmation = WRequestUtils.message( super.pageContext, messageConfirmationKey );

        if ( GenericValidator.isBlankOrNull( messageConfirmation ) )
        {
            messageConfirmation = messageConfirmationKey;
        }

        if ( !GenericValidator.isBlankOrNull( messageConfirmationKey )
            && GenericValidator.isBlankOrNull( messageConfirmationCallBack ) )
        {
            results.append( "setMsg('" + Util.formatJavaScript( messageConfirmation ) + "');" );
        }
        else if ( !GenericValidator.isBlankOrNull( messageConfirmationCallBack ) )
        {
            results.append( "setMsg(getRemoteMessage('" + Util.formatJavaScript( messageConfirmationCallBack ) + "'));" );
        }

        if ( !singleSend )
        {
            results.append( "resetUnique();" );
        }

        results.append( onclick );

        if ( !GenericValidator.isBlankOrNull( onclick ) )
        {
            results.append( ";" );
        }

        if ( !GenericValidator.isBlankOrNull( callMethod ) )
        {

            String formName = "0";

            final FormTag formTag =
                (FormTag) pageContext.getRequest().getAttribute( "org.apache.struts.taglib.html.FORM" );

            if ( formTag != null )
            {
                formName = formTag.getFormName();
            }

            // Ajout de la progressbar
            if ( !GenericValidator.isBlankOrNull( progressbarId ) )
            {
                results.append( "wPBarPreExecTaskForm('" + progressbarId + "');" );
            }

            // Appel à execSubmit
            results.append( "execSubmit(\'" );
            results.append( formName + "\',\'" + callMethod + "\',this" );

            results.append( ");" );
        }
    }

    /**
     * Returns the name.
     * 
     * @return String
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the onClick.
     * 
     * @return String
     */
    public String getOnclick()
    {
        return onclick;
    }

    /**
     * Sets the name.
     * 
     * @param pName The name to set
     */
    public void setName( final String pName )
    {
        name = pName;
    }

    /**
     * Sets the onClick.
     * 
     * @param onClick The onClick to set
     */
    public void setOnclick( final String onClick )
    {
        this.onclick = onClick;
    }

    /**
     * Returns the type.
     * 
     * @return String
     */
    public String getType()
    {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param pType The type to set
     */
    public void setType( final String pType )
    {
        type = pType;
    }

    /**
     * Returns the target.
     * 
     * @return String
     */
    public String getTarget()
    {
        return target;
    }

    /**
     * Sets the target.
     * 
     * @param pTarget The target to set
     */
    public void setTarget( final String pTarget )
    {
        target = pTarget;
    }

    /**
     * Accesseur
     * 
     * @return forceReadWrite
     */
    public boolean isForceReadWrite()
    {
        return forceReadWrite;
    }

    /**
     * Accesseur
     * 
     * @param b forceReadWrite
     */
    public void setForceReadWrite( final boolean b )
    {
        forceReadWrite = b;
    }

    /**
     * Accesseur
     * 
     * @return callMethod
     */
    public String getCallMethod()
    {
        return callMethod;
    }

    /**
     * Accesseur
     * 
     * @param string callMethod
     */
    public void setCallMethod( final String string )
    {
        callMethod = string;
    }

    /**
     * Accesseur
     * 
     * @return messageConfirmationKey
     */
    public String getMessageConfirmationKey()
    {
        return messageConfirmationKey;
    }

    /**
     * Accesseur
     * 
     * @param string messageConfirmationKey
     */
    public void setMessageConfirmationKey( final String string )
    {
        messageConfirmationKey = string;
    }

    /**
     * Accesseur
     * 
     * @return toolTipKey
     */
    public String getToolTipKey()
    {
        return toolTipKey;
    }

    /**
     * Accesseur
     * 
     * @param string toolTipKey
     */
    public void setToolTipKey( final String string )
    {
        toolTipKey = string;
    }

    /**
     * Accesseur
     * 
     * @return singleSend
     */
    public boolean isSingleSend()
    {
        return singleSend;
    }

    /**
     * Accesseur
     * 
     * @param b singleSend
     */
    public void setSingleSend( final boolean b )
    {
        singleSend = b;
    }

    /**
     * Accesseur
     * 
     * @return causesValidation
     */
    public String getCausesValidation()
    {
        return causesValidation;
    }

    /**
     * Accesseur
     * 
     * @param string causesValidation
     */
    public void setCausesValidation( final String string )
    {
        causesValidation = string;
    }

    /**
     * retourne vrai si par defaut on cause la validation
     * 
     * @param buttonName le nom du bouton
     * @return vrai si validation
     */
    public boolean checkIfInDefaultCausesValidation( final String buttonName )
    {
        final String bouttonCausesValidation =
            WelcomConfigurator.getMessageWithCfgChartePrefix( ".bouton.default.causesValidation" );
        final StringTokenizer st = new java.util.StringTokenizer( bouttonCausesValidation, ";", false );

        while ( st.hasMoreElements() )
        {
            final String elements = (String) st.nextElement();

            if ( Util.isEquals( elements.toLowerCase(), buttonName.toLowerCase() ) )
            {
                return true;
            }
        }

        return false;
    }

    /**
     * retourne vrai si par defaut on a les acces read/write
     * 
     * @param buttonName le nom du bouton
     * @return vrai si on force read/write
     */
    public boolean checkIfInDefaultForceReadWrite( final String buttonName )
    {
        final String bouttonForceReadWrite =
            WelcomConfigurator.getMessageWithCfgChartePrefix( ".bouton.default.forceReadWrite" );
        final StringTokenizer st = new java.util.StringTokenizer( bouttonForceReadWrite, ";", false );

        while ( st.hasMoreElements() )
        {
            final String elements = (String) st.nextElement();

            if ( Util.isEquals( elements.toLowerCase(), buttonName.toLowerCase() ) )
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Accesseur
     * 
     * @return accessKey
     */
    public String getAccessKey()
    {
        return accessKey;
    }

    /**
     * Accesseur
     * 
     * @param string accessKey
     */
    public void setAccessKey( final String string )
    {
        accessKey = string;
    }

    /**
     * Accesseur
     * 
     * @return overridePageAccess
     */
    public String getOverridePageAccess()
    {
        return overridePageAccess;
    }

    /**
     * Accesseur
     * 
     * @param string overridePageAccess
     */
    public void setOverridePageAccess( final String string )
    {
        overridePageAccess = string;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#release()
     */
    public void release()
    {
        super.release();
        onclick = "";
        name = "";
        type = ""; // form (par defaut)ou menu
        target = "";
        forceReadWrite = false;
        callMethod = "";
        messageConfirmationKey = "";
        toolTipKey = "";
        singleSend = true;
        causesValidation = "";
        accessKey = "";
        overridePageAccess = null;
        curParent = null;
        styleId = null;
    }

    /**
     * @return messageConfirmationCallBack
     */
    public String getMessageConfirmationCallBack()
    {
        return messageConfirmationCallBack;
    }

    /**
     * @param string messageConfirmationCallBack
     */
    public void setMessageConfirmationCallBack( final String string )
    {
        messageConfirmationCallBack = string;
    }

    /**
     * @return id
     */
    public String getStyleId()
    {
        return styleId;
    }

    /**
     * @param string id
     */
    public void setStyleId( String string )
    {
        styleId = string;
    }

    /**
     * @return
     */
    public String getProgressbarId()
    {
        return progressbarId;
    }

    /**
     * @param string
     */
    public void setProgressbarId( String string )
    {
        progressbarId = string;
    }

}