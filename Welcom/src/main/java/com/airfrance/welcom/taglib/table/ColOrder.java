/*
 * Créé le 4 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ColOrder
    extends Col
    implements ICol
{

    // private boolean first = false;
    // private boolean last = false;
    /** ordre ascendant */
    protected String titleUp = "";

    /** ordre descendant */
    protected String titleDown = "";

    /**
     * Constructeur
     */
    public ColOrder()
    {
        setSortable( false );
    }

    /**
     * @see com.airfrance.welcom.taglib.table.ICol#getCurrentValue(int, java.lang.Object, int, java.lang.String,
     *      java.lang.String)
     */
    public String getCurrentValue( final int position, final Object bean, final int idIndex, final String style,
                                   final String styleSelect, final int pageLength )
    {

        final StringBuffer s = new StringBuffer();

        final String formName = getCols().getTable().getFormName();
        if ( position != Cols.FIRST )
        {
            s.append( "<div style=\"position:relative;left:0;top:1\"><a href=\"javascript:tableForward('" );
            s.append( formName );
            s.append( "','" );
            s.append( getHrefUp( idIndex ) );
            s.append( "')\"" );

            if ( !GenericValidator.isBlankOrNull( titleUp ) )
            {
                s.append( " title=\"" );
                s.append( titleUp );
                s.append( "\" " );
            }

            s.append( ">" );
            s.append( "<img border=\"0\" src=\"" );
            s.append( WelcomConfigurator.getMessageWithCfgChartePrefix( ".htmltable.images.order.up" ) );
            s.append( "\">" );
            s.append( "</a></div>" );
        }

        if ( position != Cols.LAST )
        {
            s.append( "<div style=\"position:relative;left:0;top:-1\"><a href=\"javascript:tableForward('" );
            s.append( formName );
            s.append( "','" );
            s.append( getHrefDown( idIndex ) );
            s.append( "')\"" );

            if ( !GenericValidator.isBlankOrNull( titleDown ) )
            {
                s.append( " title=\"" );
                s.append( titleDown );
                s.append( "\" " );
            }

            s.append( ">" );

            s.append( "<img border=\"0\" src=\"" );
            s.append( WelcomConfigurator.getMessageWithCfgChartePrefix( ".htmltable.images.order.down" ) );
            s.append( "\">" );
            s.append( "</a></div>" );
        }

        return s.toString();
    }

    /**
     * @param idIndex l'index
     * @return href en rajoutant le sens
     */
    private String getHrefUp( final int idIndex )
    {
        return getHref( idIndex ) + "&sens=up";
    }

    /**
     * @param idIndex l'index
     * @return href en rajoutant le sens
     */
    private String getHrefDown( final int idIndex )
    {
        return getHref( idIndex ) + "&sens=down";
    }

    /**
     * Retourne la chaine construite avec le href. Si les parametres n'ont pas ete definis, on retourne l'action
     * generique.
     * 
     * @see com.airfrance.welcom.taglib.table.Col#getHref()
     */
    public String getHref( final int position )
    {
        String result = super.getHref();
        if ( result == null )
        {
            final StringBuffer buffer = new StringBuffer();
            buffer.append( Util.SERVEPATH );
            buffer.append( "?action=order" );
            buffer.append( "&collection=" );
            buffer.append( getCols().getTable().getProperty() );
            buffer.append( "&position=" );
            buffer.append( position );
            buffer.append( "&property=" );
            buffer.append( getProperty() );
            buffer.append( "&requestURI=" );
            buffer.append( ( (HttpServletRequest) getPageContext().getRequest() ).getRequestURI() );
            result = buffer.toString();

        }
        return result;
    }

    // /**
    // * @return
    // */
    // public boolean isFirst() {
    // return first;
    // }
    //
    // /**
    // * @return
    // */
    // public boolean isLast() {
    // return last;
    // }
    //
    // /**
    // * @param b
    // */
    // public void setFirst(boolean b) {
    // first = b;
    // }
    //
    // /**
    // * @param b
    // */
    // public void setLast(boolean b) {
    // last = b;
    // }

    /**
     * @return titleDown
     */
    public String getTitleDown()
    {
        return titleDown;
    }

    /**
     * @return titleUp
     */
    public String getTitleUp()
    {
        return titleUp;
    }

    /**
     * @param string le nouveau titleDown
     */
    public void setTitleDown( final String string )
    {
        titleDown = string;
    }

    /**
     * @param string le nouveau titleUp
     */
    public void setTitleUp( final String string )
    {
        titleUp = string;
    }
}