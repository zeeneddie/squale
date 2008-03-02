package com.airfrance.squaleweb.tagslib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.squaleweb.util.SqualeWebActionUtils;
import com.airfrance.welcom.taglib.field.FieldTag;

/**
 */
public class IteratePathsTag
    extends TagSupport
{

    /** le nom permettant de définir la variable utilisée dans le tag */
    private String name;

    /** Clé pour identifier */
    private String key;

    /** permet de savoir lequel des tableaux de String on veut récupérer */
    private String property;

    /** Le droit d'accès aux champs */
    private boolean disabled;

    /** Le caractère obligatoire du champ */
    private String isRequired = "false";

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag() {@inheritDoc} Méthode de lancement du tag
     */
    public int doStartTag()
        throws JspException
    {
        // Publie
        String[] elementsTab = (String[]) RequestUtils.lookup( pageContext, name, property, null );
        FieldTag field = new FieldTag();
        field.setSize( "60" );
        if ( elementsTab != null )
        {
            // On supprime les chaînes vides du tableau
            elementsTab = SqualeWebActionUtils.cleanValues( elementsTab );
        }
        // cas particulier, aucun champ n'a été rempli
        if ( elementsTab == null || elementsTab.length == 0 )
        {
            elementsTab = new String[] { "" };
        }
        // charge la page dans le tag a exécuter
        field.setPageContext( pageContext );
        // premier champ obligatoire
        field.setIsRequired( isRequired );
        field.setKey( key );
        field.setProperty( property );
        field.setDisabled( disabled );
        field.setStyleClassLabel( "td1" );
        String result = "";
        for ( int i = 0; i < elementsTab.length; i++ )
        {
            if ( i == 1 )
            {
                field.setIsRequired( "false" );
            }
            // positionne la valeur
            field.setValue( elementsTab[i] );
            ResponseUtils.write( pageContext, "<tr class=\"fondClair\">" );
            // lance le tag de welcom
            field.doStartTag();
            field.doEndTag();
            ResponseUtils.write( pageContext, "</tr>" );
        }
        return SKIP_BODY;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag() {@inheritDoc} Méthode de lancement du tag
     */
    public int doEndTag()
        throws JspException
    {
        return EVAL_PAGE;
    }

    /**
     * @return le nom utilisé dans le tag
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param newUsedName le nouveau nom
     */
    public void setName( String newUsedName )
    {
        name = newUsedName;
    }

    /**
     * @return la clé
     */
    public String getKey()
    {
        return key;
    }

    /**
     * @return la propriété
     */
    public String getProperty()
    {
        return property;
    }

    /**
     * @param newKey la nouvelle clé
     */
    public void setKey( String newKey )
    {
        key = newKey;
    }

    /**
     * @param newProperty la nouvelle propriété
     */
    public void setProperty( String newProperty )
    {
        property = newProperty;
    }

    /**
     * @return le caractère obligatoire du champ
     */
    public String getIsRequired()
    {
        return isRequired;
    }

    /**
     * @param newRequirement le nouveau caractère obligatoire du champ
     */
    public void setIsRequired( String newRequirement )
    {
        isRequired = newRequirement;
    }

    /**
     * @return si le champ est readonly
     */
    public boolean isDisabled()
    {
        return disabled;
    }

    /**
     * @param pDisabled indique si le champ est readonly
     */
    public void setDisabled( boolean pDisabled )
    {
        disabled = pDisabled;
    }
}
