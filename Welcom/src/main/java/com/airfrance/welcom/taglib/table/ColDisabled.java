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
 * Créé le 5 mars 07
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.welcom.outils.Util;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public abstract class ColDisabled
    extends Col
{

    /** logger */
    private static Log log = LogFactory.getLog( ColDisabled.class );

    /** parametre du tag */
    private String disabledProperty = "";

    /**
     * Retourne si le tag est desactivé
     * 
     * @param bean Bean
     * @return si le tag est desactivé
     */
    private boolean isDisabledTag( final Object bean )
    {
        // On verifie si on a une disabledProperty
        if ( !disabledProperty.equals( "" ) )
        {
            String resultDisableProperty = "";
            try
            {
                resultDisableProperty = BeanUtils.getProperty( bean, disabledProperty );
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
            if ( Util.isTrue( resultDisableProperty ) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne le contenu spécifique
     * 
     * @param bean : Bean
     * @param position la position de la colonne
     * @param idIndex Index
     * @param style Style
     * @param styleSelect Style selectionne
     * @return le html généré
     */
    public abstract String getSpecificContent( final int position, final Object bean, final int idIndex,
                                               final String style, final String styleSelect, final int pageLength )
        throws JspException;

    /**
     * @param bean : Bean
     * @param position la position de la colonne
     * @param idIndex Index
     * @param style Style
     * @param styleSelect Style selectionne
     * @return le html généré
     */
    public final String getCurrentValue( final int position, final Object bean, final int idIndex, final String style,
                                         final String styleSelect, final int pageLength )
        throws JspException
    {

        final StringBuffer sb = new StringBuffer();

        String wdt = "";

        // si l'objet a été redefinit ou desactivé
        if ( Util.isTrimNonVide( currentValue ) )
        {
            sb.append( getSpecificContent( currentValue, style, isNeedWriteWidth( idIndex ) ) );
        }
        else if ( isDisabledTag( bean ) )
        {
            sb.append( getSpecificContent( "&nbsp;", style, isNeedWriteWidth( idIndex ) ) );
        }
        else
        {
            sb.append( getSpecificContent( position, bean, idIndex, style, styleSelect, pageLength ) );
        }

        return sb.toString();

    }

    /**
     * Retourn le contenu
     * 
     * @param content contenu
     * @param style style
     * @param isNeedWriteWidth s'il doit ajouter la taille
     * @return le contenu avec formattage
     */
    private String getSpecificContent( String content, String style, boolean isNeedWriteWidth )
        throws JspException
    {
        final StringBuffer sb = new StringBuffer();

        // Si le cols n'ecrit pas les td on les rajoutes
        if ( !isWriteTD() )
        {
            sb.append( "<td " );

            // On ecrit la taille si necessaire
            if ( isNeedWriteWidth )
            {
                sb.append( " width=\"" + getWidth() + "\"" );
            }
            sb.append( " class=\"" + style + "\">" );
        }

        sb.append( content );

        if ( !isWriteTD() )
        {
            sb.append( "</td>" );
        }

        return sb.toString();
    }

    /**
     * Retourne s'il est necessaire d'ecrire la taille de la colonne
     * 
     * @param idIndex index
     * @return s'il est necessaire d'ecrire la taille de la colonne
     */
    protected boolean isNeedWriteWidth( final int idIndex )
    {
        return ( getCols().getTable().isDisplayHeader() == false ) && ( idIndex == 1 );
    }

    /**
     * @return disabledProperty
     */
    public String getDisabledProperty()
    {
        return disabledProperty;
    }

    /**
     * @param string disabledProperty
     */
    public void setDisabledProperty( final String string )
    {
        disabledProperty = string;
    }

}
