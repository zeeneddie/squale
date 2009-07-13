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
 * CrÚÚ le 4 mars 05
 *
 * Pour changer le modÞle de ce fichier gÚnÚrÚ, allez Ó :
 * FenÛtre&gt;PrÚfÚrences&gt;Java&gt;GÚnÚration de code&gt;Code et commentaires
 */
package org.squale.welcom.taglib.table;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.squale.welcom.outils.Charte;
import org.squale.welcom.outils.WelcomConfigurator;


/**
 * @author M327837 Pour changer le modÞle de ce commentaire de type gÚnÚrÚ, allez Ó :
 *         FenÛtre&gt;PrÚfÚrences&gt;Java&gt;GÚnÚration de code&gt;Code et commentaires
 */
public class ColSingleSelect
    extends ColDisabled
{
    /** logger */
    private static Log log = LogFactory.getLog( ColSelect.class );

    /** ToolTip */
    protected String toolTip = "";

    /** onclick */
    protected String onclick = "";

    /** parametre du tag */
    private String specialHeaderTitle = "";

    /** bean du formulaire */
    private String formbeanValue = "";

    /** parametre du tag */
    private String propertyValue = "";

    /** id auto generer */
    private static long autoid = 0;

    /** parametre du tag */
    private String value = "";

    /** prefixe auto id */
    private static final String AUTOID_PREFIX = "welcomRadioAutoId";

    /**
     * Retourne la valeur pour le champ input
     * 
     * @param bean le bean
     * @param idIndex l'index
     * @return si une propertyvalue est calculé alors on retoune cette valeur, sinon l'index
     */
    private String getComputedInputValue( final Object bean, final int idIndex )
    {

        String comptedInputValue = Integer.toString( idIndex );

        if ( GenericValidator.isBlankOrNull( value ) && !GenericValidator.isBlankOrNull( propertyValue ) )
        {
            try
            {
                comptedInputValue = BeanUtils.getProperty( bean, propertyValue );
            }
            catch ( final IllegalAccessException e )
            {
                log.error( e, e );
            }
            catch ( final InvocationTargetException e )
            {
                log.error( e, e );
            }
            catch ( final NoSuchMethodException e )
            {
                log.error( e, e );
            }
        }

        return comptedInputValue;
    }

    /**
     * @param bean : Bean
     * @param position la position de la colonne
     * @param idIndex Index
     * @param style Style
     * @param styleSelect Style selectionne
     * @return le html gÚnÚrÚ
     */
    public String getSpecificContent( int position, Object bean, int idIndex, String style, String styleSelect,
                                      final int pageLength )
    {

        final StringBuffer sb = new StringBuffer();

        /** Calcule la value du tag */
        value = getComputedInputValue( bean, idIndex );

        String name = getCols().getTable().getName();
        String wdt = "";
        String st = style;

        if ( isNeedWriteWidth( idIndex ) )
        {
            wdt = " width=\"" + getWidth() + "\"";
        }
        if ( WelcomConfigurator.getCharte() == Charte.V2_002 )
        {
            st = "normal";
        }

        if ( !GenericValidator.isBlankOrNull( getCols().getTable().getProperty() ) )
        {
            name = getCols().getTable().getProperty();
        }

        // Creation du TD
        sb.append( "<td style=\"padding:0 0 0 0;\"" );
        sb.append( wdt );
        sb.append( " classSelect=\"" );
        sb.append( styleSelect );
        sb.append( "\" classDefault=\"" );
        sb.append( style );
        sb.append( "\">" );

        // Creation de la check box
        sb.append( "<input class=\"" );
        sb.append( st );
        sb.append( "\" type=\"radio\" name=\"" );
        sb.append( getProperty() );
        sb.append( "\" value=\"" );
        sb.append( value );
        sb.append( "\" " );
        sb.append( " id=\"" );
        sb.append( getAutoID() );
        sb.append( "\" " );

        sb.append( "onclick=\"checkRadioSingle(this);" );

        if ( !GenericValidator.isBlankOrNull( onclick ) )
        {
            sb.append( onclick );
        }
        sb.append( ";\"" );

        // Si ce test venait à changer, mettre à jour la méthode getLineIsSelected
        // qui reprend le même principe
        if ( formbeanValue.equals( value ) )
        {
            sb.append( " checked " );
        }

        sb.append( "></td>" );

        return sb.toString();
    }

    /**
     * ATTENTION Cette méthode recopie le principe de test utilisé dans getSpecificContent Si elle est modifiée,
     * reporter les modifs sur getSpecificContent
     * 
     * @param bean FormBean de la ligne
     * @param int index de la ligne
     * @return booléen indiquant si la ligne du tableau est sélectionnée
     */
    public boolean getLineIsSelected( Object bean, final int i )
    {
        String beanValue = getComputedInputValue( bean, i );

        if ( formbeanValue.equals( beanValue ) )
        {
            return true;
        }
        else
            return false;
    }

    /**
     * @return id generer automatiquement
     */
    private String getAutoID()
    {

        return AUTOID_PREFIX + getCols().getTable().getName() + getCols().getTable().getProperty() + autoid++;
    }

    /**
     * @return accesseur
     */
    public String getToolTip()
    {
        return toolTip;
    }

    /**
     * @param string accesseur
     */
    public void setToolTip( final String string )
    {
        toolTip = string;
    }

    /**
     * @return onclick
     */
    public String getOnclick()
    {
        return onclick;
    }

    /**
     * @param string onclick
     */
    public void setOnclick( final String string )
    {
        onclick = string;
    }

    /**
     * @return specialHeaderTitle
     */
    public String getSpecialHeaderTitle()
    {
        return specialHeaderTitle;
    }

    /**
     * @param string specialHeaderTitle
     */
    public void setSpecialHeaderTitle( final String string )
    {
        specialHeaderTitle = string;
    }

    /**
     * @return FormbeanValu
     */
    public String getFormbeanValue()
    {
        return formbeanValue;
    }

    /**
     * @param string HeaderNoWrap
     */
    public void setFormbeanValue( String string )
    {
        formbeanValue = string;
    }

    /**
     * @return avlue
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param string value
     */
    public void setValue( String string )
    {
        value = string;
    }

    /**
     * @return PropertyValue
     */
    public String getPropertyValue()
    {
        return propertyValue;
    }

    /**
     * @param string PropertyValue
     */
    public void setPropertyValue( String string )
    {
        propertyValue = string;
    }

}