package com.airfrance.welcom.taglib.field.wrap;

import javax.servlet.jsp.JspException;

import com.airfrance.welcom.taglib.field.DateHeureTag;

public class WDateHeureTag
    extends DateHeureTag
    implements IWelcomInputTag
{

    /**
     * Spécifie le mode d'accée du composant
     */
    private String access = READWRITE;

    /**
     * {@inheritDoc}
     */
    protected void doRender( StringBuffer sb )
        throws JspException
    {

        if ( READWRITE.equals( access ) )
        {
            super.doRender( sb );
        }
        else
        {
            doRenderValue( sb, TYPE_WDATE );
            sb.append( " " );
            doRenderValue( sb, TYPE_WHOUR );
            if ( READSEND.equals( access ) )
            {
                doRenderHidden( sb );
            }
        }
    }

    /**
     * Effectue un rendu sou forme caché ...
     * 
     * @param buffer buffer
     * @throws JspException erreur sur le recuperation de la valeur
     */
    protected void doRenderHidden( StringBuffer buffer )
        throws JspException
    {
        buffer.append( "<input type=\"hidden\" name=\"" );
        buffer.append( property + "WDate" );
        buffer.append( "\"" );
        this.doRenderValue( buffer, DateHeureTag.TYPE_WDATE );
        buffer.append( ">" );
        buffer.append( "<input type=\"hidden\" name=\"" );
        buffer.append( property + "WHour" );
        buffer.append( "\"" );
        this.doRenderValue( buffer, DateHeureTag.TYPE_WHOUR );
        buffer.append( ">" );
    }

    /**
     * @return access attribut
     */
    public String getAccess()
    {
        return access;
    }

    /**
     * @param access access attribut
     */
    public void setAccess( String access )
    {
        this.access = access;
    }
}
