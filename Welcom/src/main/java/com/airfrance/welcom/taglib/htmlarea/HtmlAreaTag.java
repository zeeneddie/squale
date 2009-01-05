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
/*
 * Créé le 15 juil. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.htmlarea;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.outils.Access;
import com.airfrance.welcom.outils.Charte;
import com.airfrance.welcom.outils.TrimStringBuffer;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * @author M327836 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class HtmlAreaTag
    extends BodyTagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 4547942713820065322L;

    /** logger */
    private static Log log = LogFactory.getLog( HtmlAreaTag.class );

    /** Key */
    public static final String KEY = "com.airfrance.welcom.taglib.htmlarea.HtmlAreaTag";

    /** Paramètre du tag */
    protected String prefix = WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_COMPRESSION_PREFIX_JS );

    /** Paramètre du tag */
    protected final static String OPEN_SCRIPT = "<script type=\"text/javascript\">\n";

    /** Paramètre du tag */
    protected final static String CLOSE_SCRIPT = "</script>\n";

    /** Paramètre du tag */
    protected final static String OPEN_SCRIPT_DEFER = "<script language=\"JavaScript1.2\" defer>\n";

    /** Paramètre du tag */
    protected final static String HTML_EDITOR = "HTML";

    /** Paramètre du tag */
    protected final static String TEXT_EDITOR = "TEXT";

    /** Paramètre du tag */
    protected final static String CUSTOM_EDITOR = "CUSTOM";

    /** Paramètre du tag */
    private String name;

    /** Paramètre du tag */
    private String property = null;

    /** Paramètre du tag */
    private String scope = null;

    /** Paramètre du tag */
    private String path = null;

    /** Paramètre du tag */
    private String plugin = null;

    /** Paramètre du tag */
    private String cols = null;

    /** Paramètre du tag */
    private String rows = null;

    /** Paramètre du tag */
    private String cssurl = null;

    /** Paramètre du tag */
    private String width = null;

    /** Paramètre du tag */
    private String toolbarkey = null;

    /** Paramètre du tag */
    private boolean debug = false;

    /** Paramètre du tag */
    private String callbacksfile = null;

    /** Paramètre du tag */
    private String editorType = HTML_EDITOR;

    /** Paramètre du tag */
    private boolean forceReadWrite = false;

    /** Paramètre du tag */
    protected Locale localeRequest = Locale.FRENCH;

    /** Paramètre du tag */
    protected Vector pluginList = null;

    /** Paramètre du tag */
    protected MessageResources resources = null;

    /** Paramètre du tag */
    private String tagname;

    /** Paramètre du tag */
    private Hashtable toolbar;

    /** Paramètre du tag */
    private String edName = null;

    /**
     * Constructeur
     */
    public HtmlAreaTag()
    {
        Charte charte = WelcomConfigurator.getCharte();
        cssurl =
            WelcomConfigurator.getMessage( "htmlarea.default.cssurl" + "." + charte.getVersionMajor() + "."
                + charte.getVersionMinor() );
        path = WelcomConfigurator.getMessage( WelcomConfigurator.HTMLAREA_DEFAULT_PATH );
        cols = WelcomConfigurator.getMessage( WelcomConfigurator.HTMLAREA_DEFAULT_COLS );
        rows = WelcomConfigurator.getMessage( WelcomConfigurator.HTMLAREA_DEFAULT_ROWS );
    }

    /**
     * Ouverture du tag
     * 
     * @throws JspException jsp exception
     * @return constante
     */
    public int doStartTag()
        throws JspException
    {
        // Recupere le droit sur la page
        final String pageAccess = (String) pageContext.getAttribute( "access" );

        if ( !( ( ( pageAccess != null ) && pageAccess.equals( Access.READONLY ) && ( forceReadWrite == false ) ) ) )
        {
            resources = (MessageResources) pageContext.getServletContext().getAttribute( Globals.MESSAGES_KEY );
            tagname = name;

            if ( !GenericValidator.isBlankOrNull( property ) )
            {
                if ( property.lastIndexOf( "." ) >= 0 )
                {
                    tagname = property.substring( property.lastIndexOf( "." ) + 1 );
                }
                else
                {
                    tagname = property;
                }
            }

            edName = "ed_" + tagname;
            TrimStringBuffer.setTrim( false );
            initHtmlArea();

            initToolBar();
            initEditor();
        }

        return EVAL_PAGE;
    }

    /**
     * fermeture du tag
     * 
     * @throws JspException jsp exception
     * @return constante
     */
    public int doEndTag()
        throws JspException
    {
        final String pageAccess = (String) pageContext.getAttribute( "access" );

        if ( ( ( pageAccess != null ) && pageAccess.equals( Access.READONLY ) && ( forceReadWrite == false ) ) )
        {
            writeContent();
        }
        else
        {
            final TrimStringBuffer sb = new TrimStringBuffer();

            if ( getBodyContent() != null )
            {
                sb.append( getBodyContent().getString() );
            }

            ResponseUtils.write( pageContext, sb.toString() );
            genEditor();
            writeTextArea();
        }

        release();
        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#release()
     */
    public void release()
    {
        name = null;
        property = null;
        scope = null;
        plugin = null;
        width = null;
        toolbarkey = null;
        debug = false;
        callbacksfile = null;
        editorType = HTML_EDITOR;
        forceReadWrite = false;
        localeRequest = Locale.FRENCH;
        pluginList = null;
        resources = null;
        tagname = null;
        toolbar = null;
        edName = null;
        super.release();
    }

    /**
     * init
     * 
     * @throws JspException jsp exception
     */
    private void initHtmlArea()
        throws JspException
    {
        final TrimStringBuffer sb = new TrimStringBuffer();

        // Recupere la locale de la page
        localeRequest = (Locale) pageContext.getSession().getAttribute( Globals.LOCALE_KEY );

        if ( pageContext.getRequest().getAttribute( KEY ) == null )
        {
            sb.append( OPEN_SCRIPT );
            sb.append( "_editor_url = \"" + path + "\";\n" );
            sb.append( "_editor_lang = \"" + localeRequest.getLanguage() + "\";\n" );
            sb.append( CLOSE_SCRIPT );
            sb.append( "<script type=\"text/javascript\" src=\"" + prefix + path + "htmlarea.js\"></script>\n" );
            sb.append( "<script type=\"text/javascript\" src=\"" + prefix + path + "lang/"
                + localeRequest.getLanguage() + ".js\"></script>\n" );
        }

        if ( !GenericValidator.isBlankOrNull( callbacksfile ) )
        {
            sb.append( "<script type=\"text/javascript\" src=\"js.do?value=" + callbacksfile + "\"></script>\n" );
        }

        ResponseUtils.write( pageContext, sb.toString() );
    }

    /**
     * Init de la barre d'outils
     * 
     * @throws JspException jsp Exception
     */
    private void initToolBar()
        throws JspException
    {
        String bar = null;

        if ( !GenericValidator.isBlankOrNull( toolbarkey ) )
        {
            bar = WelcomConfigurator.getMessage( toolbarkey );
        }

        if ( GenericValidator.isBlankOrNull( bar ) )
        {
            bar = WelcomConfigurator.getMessage( "htmlarea.default.toolbar." + editorType.toLowerCase() );
        }

        final StringTokenizer st = new StringTokenizer( bar, "|" );
        toolbar = new Hashtable();

        int i = 0;

        while ( st.hasMoreTokens() )
        {
            final String subbar = st.nextToken();
            final StringTokenizer st2 = new StringTokenizer( subbar, "," );
            final Vector v = new Vector();

            while ( st2.hasMoreTokens() )
            {
                v.add( st2.nextToken() );
            }

            toolbar.put( "bar" + i, v );
            i++;
        }
    }

    /**
     * init plugin
     * 
     * @param sb string buffer
     * @throws JspException jsp exception
     */
    private void initPlugins( final TrimStringBuffer sb )
        throws JspException
    {

        // Logger
        log.debug( "Type d'éditeur : " + editorType );

        // So on HTML, on charge le plugin CSS
        if ( editorType.equalsIgnoreCase( HTML_EDITOR ) )
        {
            // ajout un plugin CSS et du ;
            if ( GenericValidator.isBlankOrNull( plugin ) )
            {
                plugin = "CSS";
            }
            else
            {
                plugin += ";CSS";
            }
        }
        else if ( editorType.equalsIgnoreCase( TEXT_EDITOR ) )
        {
            // A supprimer plus tard, quand on aura webdav
            if ( GenericValidator.isBlankOrNull( plugin ) )
            {
                plugin = "ContextMenu;DocumentEditor";
            }
            else if ( plugin.indexOf( "DocumentEditor" ) < 0 )
            {
                plugin += ";ContextMenu;DocumentEditor";
            }
        }

        // charge les plugins
        // supplementaires
        if ( !GenericValidator.isBlankOrNull( plugin ) )
        {
            pluginList = new Vector();

            // Retrouve la liste des plugins par seperation ;
            final StringTokenizer st = new StringTokenizer( plugin, ";" );

            // Insere dans la liste de plugin
            while ( st.hasMoreTokens() )
            {
                pluginList.add( st.nextToken() );
            }

            // Ecrit la ligne de chargement de plugin dans la page
            for ( final Iterator iter = pluginList.iterator(); iter.hasNext(); )
            {
                final String plg = (String) iter.next();
                sb.append( "HTMLArea.loadPlugin(\"" + plg + "\");\n" );
            }
        }
    }

    /**
     * init editor
     * 
     * @throws JspException jsp exception
     */
    private void initEditor()
        throws JspException
    {
        final TrimStringBuffer sb = new TrimStringBuffer();
        sb.append( OPEN_SCRIPT );
        initPlugins( sb );
        sb.append( "var " + edName + " = null;" );
        sb.append( "function initEditor" + tagname + "(){" );
        sb.append( edName + " = new HTMLArea('" + tagname + "');\n" );

        if ( editorType.equalsIgnoreCase( TEXT_EDITOR ) )
        {
            sb.append( edName + ".config.pageStyle = \"@import url(" + path + "text.css);\";\n" );
        }

        // customize toolbar
        if ( toolbar != null )
        {
            String bars = edName + ".config.toolbar = [";

            for ( int i = 0; i < toolbar.size(); i++ )
            {
                String buttons = "[";
                final Vector v = (Vector) toolbar.get( "bar" + i );

                for ( final Iterator iter = v.iterator(); iter.hasNext(); )
                {
                    final String element = (String) iter.next();
                    buttons += ( "'" + element + "'," );
                }

                buttons = buttons.substring( 0, buttons.length() - 1 ) + "],";
                bars += buttons;
            }

            bars = bars.substring( 0, bars.length() - 1 ) + "];";
            sb.append( bars + "\n" );
        }

        // register plugins
        if ( pluginList != null )
        {
            for ( final Iterator iter = pluginList.iterator(); iter.hasNext(); )
            {
                final String plg = (String) iter.next();

                if ( plg.equals( "CSS" ) )
                {
                    sb.append( edName + ".config.pageStyle = \"@import url(" + cssurl + ");" );
                    if ( WelcomConfigurator.getCharte() == Charte.V2_002 )
                    {
                        sb.append( "@import url(css.do?value=css/welcom-002.css);" );
                    }
                    if ( WelcomConfigurator.getCharte() == Charte.V3_001 )
                    {
                        sb.append( "@import url(css.do?value=css/welcom-001.css);" );
                    }
                    sb.append( "\";\n" );
                    sb.append( edName );
                    sb.append( ".registerPlugin(" );
                    sb.append( plg );
                    sb.append( ", {\n" );
                    sb.append( "combos : [\n" );
                    sb.append( "{id:'CSS_combo',\n" );
                    sb.append( "label:'Styles',\n" );
                    sb.append( "options: {" );
                    final String styleKey =
                        "htmlarea.default.styles" + "." + WelcomConfigurator.getCharte().getVersionMajor() + "."
                            + WelcomConfigurator.getCharte().getVersionMinor();
                    sb.append( Util.formatQuote( WelcomConfigurator.getMessage( styleKey ) ) );
                    sb.append( "   },\n" );
                    sb.append( "context: 'body'\n" );
                    sb.append( "}]});\n" );
                }
                else if ( plg.equals( "DocumentEditor" ) )
                {
                    sb.append( edName + ".registerPlugin(" + plg + ",'" + edName + "');\n" );
                }
                else
                {
                    sb.append( edName + ".registerPlugin(" + plg + ");\n" );
                }
            }
        }

        saveInitFunction( "initEditor" + tagname + "()" );

        debugFeatures( sb );

        ResponseUtils.write( pageContext, sb.toString() );
    }

    /**
     * Sauve le nom de la fonction init
     * 
     * @param pName name
     */
    private void saveInitFunction( String pName )
    {
        ArrayList list = new ArrayList();
        if ( pageContext.getRequest().getAttribute( KEY ) != null )
        {
            list = (ArrayList) pageContext.getRequest().getAttribute( KEY );
        }
        list.add( pName );
        pageContext.getRequest().setAttribute( KEY, list );
    }

    /**
     * @param sb string buffer
     */
    private void debugFeatures( final TrimStringBuffer sb )
    {
        if ( !debug )
        {
            sb.append( edName + ".config.statusBar = false;\n" );
        }
        else
        {
            sb.append( edName + ".config.toolbar[0].push(['htmlmode']);" );
        }
    }

    /**
     * @throws JspException jsp exception
     */
    private void genEditor()
        throws JspException
    {
        final TrimStringBuffer sb = new TrimStringBuffer();
        sb.append( edName + ".generate();\n" );
        sb.append( "return false;}" );
        sb.append( "HTMLArea.onload = function(){" );
        ArrayList list = (ArrayList) pageContext.getRequest().getAttribute( KEY );
        for ( Iterator iter = list.iterator(); iter.hasNext(); )
        {
            String element = (String) iter.next();
            sb.append( element + ";" );
            // sb.append((String)list.get(list.size()-1)+";");
        }
        sb.append( "}" );
        // sb.append("HTMLArea.onload = initEditor"+tagname+";");
        sb.append( CLOSE_SCRIPT );
        ResponseUtils.write( pageContext, sb.toString() );
    }

    /**
     * @throws JspException jsp exception
     */
    private void writeTextArea()
        throws JspException
    {
        final TrimStringBuffer sb = new TrimStringBuffer();

        sb.append( "<textarea htmlarea=\"true\" id=\"" + tagname + "\" name=\"" + tagname + "\" rows=\"" + rows + "\"" );

        if ( !GenericValidator.isBlankOrNull( width ) )
        {
            sb.append( " style=\"width:" + formatPx( width ) + "\"" );
        }
        else
        {
            sb.append( " cols=\"" + cols + "\"" );
        }

        sb.append( ">\n" );

        final Object o = RequestUtils.lookup( super.pageContext, name, property, scope );

        if ( o == null )
        {
            final JspException e =
                new JspException( "Objet " + name + ", " + property + " introuvable dans le scope : " + scope
                    + " ou n'a pas été initialisé" );
            RequestUtils.saveException( super.pageContext, e );
            throw e;
        }

        if ( !( o instanceof String ) )
        {
            throw new JspException( "Le bean doit être de type String" );
        }

        sb.append( (String) o );
        sb.append( "</textarea>\n" );

        ResponseUtils.write( pageContext, sb.toString() );
    }

    /**
     * @throws JspException jsp exception
     */
    private void writeContent()
        throws JspException
    {
        final TrimStringBuffer sb = new TrimStringBuffer();

        sb.append( "<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\"" );

        if ( !GenericValidator.isBlankOrNull( width ) )
        {
            sb.append( " width=\"" + width + "\"" );
        }

        sb.append( "<tr><td>\n" );

        final Object o = RequestUtils.lookup( super.pageContext, name, property, scope );

        if ( o == null )
        {
            final JspException e =
                new JspException( "Objet " + name + "," + property + " introuvable dans le scope : " + scope );
            RequestUtils.saveException( super.pageContext, e );
            throw e;
        }

        if ( !( o instanceof String ) )
        {
            throw new JspException( "Le bean doit être de type String" );
        }

        sb.append( (String) o );
        sb.append( "</td></tr>\n</table>\n" );

        ResponseUtils.write( pageContext, sb.toString() );
    }

    /**
     * formatage de pixels
     * 
     * @param s chaine à formater
     * @return chaine formatée
     */
    private String formatPx( final String s )
    {
        String s2 = s;

        if ( !s2.substring( s2.length() - 1 ).equals( "%" ) )
        {
            s2 += "px";
        }

        return s2;
    }

    /**
     * @return path
     */
    public String getPath()
    {
        return path;
    }

    /**
     * @param string path
     */
    public void setPath( final String string )
    {
        path = string;
    }

    /**
     * @return plugin
     */
    public String getPlugin()
    {
        return plugin;
    }

    /**
     * @param string plugin
     */
    public void setPlugin( final String string )
    {
        plugin = string;
    }

    /**
     * @return cols
     */
    public String getCols()
    {
        return cols;
    }

    /**
     * @return rows
     */
    public String getRows()
    {
        return rows;
    }

    /**
     * @param string cols
     */
    public void setCols( final String string )
    {
        cols = string;
    }

    /**
     * @param string rows
     */
    public void setRows( final String string )
    {
        rows = string;
    }

    /**
     * @return width
     */
    public String getWidth()
    {
        return width;
    }

    /**
     * @param string width
     */
    public void setWidth( final String string )
    {
        width = string;
    }

    /**
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param string name
     */
    public void setName( final String string )
    {
        name = string;
    }

    /**
     * @return property
     */
    public String getProperty()
    {
        return property;
    }

    /**
     * @return scope
     */
    public String getScope()
    {
        return scope;
    }

    /**
     * @param string property
     */
    public void setProperty( final String string )
    {
        property = string;
    }

    /**
     * @param string scope
     */
    public void setScope( final String string )
    {
        scope = string;
    }

    /**
     * @return toolbarkey
     */
    public String getToolbarkey()
    {
        return toolbarkey;
    }

    /**
     * @param string toolbarkey
     */
    public void setToolbarkey( final String string )
    {
        toolbarkey = string;
    }

    /**
     * @return debug
     */
    public boolean isDebug()
    {
        return debug;
    }

    /**
     * @param b debug
     */
    public void setDebug( final boolean b )
    {
        debug = b;
    }

    /**
     * @return callbacksfile
     */
    public String getCallbacksfile()
    {
        return callbacksfile;
    }

    /**
     * @param string callbacksfile
     */
    public void setCallbacksfile( final String string )
    {
        callbacksfile = string;
    }

    /**
     * @return edName
     */
    public String getEdName()
    {
        return edName;
    }

    /**
     * @param string edName
     */
    public void setEdName( final String string )
    {
        edName = string;
    }

    /**
     * @return editorType
     */
    public String getEditorType()
    {
        return editorType;
    }

    /**
     * @param string editorType
     */
    public void setEditorType( final String string )
    {
        editorType = string;
    }

    /**
     * @return forceReadWrite
     */
    public boolean isForceReadWrite()
    {
        return forceReadWrite;
    }

    /**
     * @param b forceReadWrite
     */
    public void setForceReadWrite( final boolean b )
    {
        forceReadWrite = b;
    }
}