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
 * Créé le 6 mai 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.struts.util.MessageResources;

import com.airfrance.welcom.outils.jdbc.wrapper.ResultSetUtils;
import com.airfrance.welcom.struts.bean.WComboValue;
import com.airfrance.welcom.struts.bean.WComboValueLabel;

/**
 * @author M327837 Effectue les populate sur le combo ...
 * @link com.airfrance.welcom.outils.jdbc.wrapper.ResultSetUtils
 */
public class WResultSetUtils
    extends ResultSetUtils
{

    /**
     * @param combo : Combo a populer
     * @param resultSet : Données a y ajouter
     * @throws SQLException : probleme SQL
     */
    public static void populateCombo( final WComboValue combo, final ResultSet resultSet )
        throws SQLException
    {
        if ( resultSet != null )
        {
            for ( ; resultSet.next(); combo.setValue( resultSet.getString( 1 ) ) )
            {
                ;
            }
        }
    }

    /**
     * @param combo : Combo a populer
     * @param resultSet : Données a y ajouter
     * @param messages : Message resource pour effectué l'internationnalisation
     * @param locale : Langue dans la quelle il faut traduire
     * @throws SQLException : probleme SQL
     */
    public static void populateCombo( final WComboValueLabel combo, final ResultSet resultSet,
                                      final MessageResources messages, final Locale locale )
        throws SQLException
    {
        if ( resultSet != null )
        {
            for ( ; resultSet.next(); )
            {
                if ( messages == null )
                {
                    if ( resultSet.getMetaData().getColumnCount() == 2 )
                    {
                        combo.addValueLabel( resultSet.getString( 1 ), resultSet.getString( 2 ) );
                    }
                    else
                    {
                        combo.addValueLabel( resultSet.getString( 1 ), resultSet.getString( 1 ) );
                    }
                }
                else
                {
                    if ( resultSet.getMetaData().getColumnCount() == 2 )
                    {
                        combo.addValueLabel( resultSet.getString( 1 ), messages.getMessage( locale,
                                                                                            resultSet.getString( 2 ) ) );
                    }
                    else
                    {
                        combo.addValueLabel( resultSet.getString( 1 ), messages.getMessage( locale,
                                                                                            resultSet.getString( 1 ) ) );
                    }

                    combo.sort();
                }
            }
        }
    }

    /**
     * Effectue un poepulate en retournant directment une liste d'objets
     * 
     * @param c Classe de l'object a intancier
     * @param rs Resulset fournir par la query, effectue le test de nullité, retourne une liste vide dans ce cas
     * @return Liste d'object de la classe
     */
    public static ArrayList populateInArrayList( final Class c, final ResultSet rs )
    {
        final ArrayList vector = new ArrayList();

        try
        {
            if ( rs != null )
            {
                while ( rs.next() )
                {
                    final Object o = c.newInstance();
                    ResultSetUtils.populate( o, rs );
                    vector.add( o );
                }

                rs.close();
            }
        }
        catch ( final Exception e )
        {
            System.err.println( e.getMessage() );
        }

        return vector;
    }

}