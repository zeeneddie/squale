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
 * Créé le 1 avr. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.taglib.table;

import java.util.Comparator;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.squale.welcom.outils.DateUtil;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ColComparator
    implements Comparator
{
    /** Constante */
    public static final String TYPE_STRING = "STRING";

    /** Constante */
    public static final String TYPE_NUMBER = "NUMBER";

    /** Constante */
    public static final String TYPE_DATE = "DATE";

    /** Le tableSort */
    private ListColumnSort tableSort;

    /**
     * Constructeur
     * 
     * @param pTableSort le tableSort
     */
    public ColComparator( final ListColumnSort pTableSort )
    {
        tableSort = pTableSort;
    }

    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare( final Object o1, final Object o2 )
    {
        try
        {
            final Object object1 = PropertyUtils.getProperty( o1, tableSort.getFirst().getColumn() );
            final Object object2 = PropertyUtils.getProperty( o2, tableSort.getFirst().getColumn() );

            final String type = tableSort.getFirst().getType();

            if ( tableSort.getFirst().getSort() == SortOrder.NONE )
            {
                return 0;
            }

            final int ascendingInt = ( tableSort.getFirst().getSort() == SortOrder.ASC ) ? 1 : ( -1 );

            if ( ( object1 instanceof Comparable ) && ( object2 instanceof Comparable ) )
            {
                if ( ( type != null ) && ( type.equalsIgnoreCase( TYPE_STRING ) == false ) )
                {
                    if ( type.equalsIgnoreCase( TYPE_DATE ) )
                    {
                        int ret = 0;
                        Date d1;
                        Date d2;
                        if ( object1 instanceof Date )
                        {
                            d1 = (Date) object1;
                            d2 = (Date) object2;
                        }
                        else
                        {
                            d1 = DateUtil.parseAllDate( (String) object1 );
                            d2 = DateUtil.parseAllDate( (String) object2 );
                        }

                        if ( ( d1 != null ) && ( d2 != null ) )
                        {
                            if ( d1.getTime() == d2.getTime() )
                            {
                                ret = 0;
                            }

                            if ( d1.getTime() < d2.getTime() )
                            {
                                ret = -1 * ascendingInt;
                            }

                            if ( d1.getTime() > d2.getTime() )
                            {
                                ret = 1 * ascendingInt;
                            }
                        }
                        else if ( ( d1 != null ) && ( d2 == null ) )
                        {
                            ret = -1;
                        }
                        else if ( ( d1 == null ) && ( d2 != null ) )
                        {
                            ret = 1;
                        }
                        else
                        {
                            ret = 0;
                        }

                        return ret;
                    }
                    else if ( type.equalsIgnoreCase( TYPE_NUMBER ) && ( object1 instanceof String ) )
                    {
                        int ret = 0;

                        final double i1 = Double.parseDouble( (String) object1 );
                        final double i2 = Double.parseDouble( (String) object2 );

                        if ( i1 == i2 )
                        {
                            ret = 0;
                        }

                        if ( i1 < i2 )
                        {
                            ret = -1 * ascendingInt;
                        }

                        if ( i1 > i2 )
                        {
                            ret = 1 * ascendingInt;
                        }
                        return ret;
                    }
                    else
                    {
                        return ascendingInt * ( (Comparable) object1 ).compareTo( object2 );
                    }
                }
                else
                {
                    return ascendingInt * ( (Comparable) object1 ).compareTo( object2 );
                }
            }
            else if ( ( object1 == null ) && ( object2 == null ) )
            {
                return 0;
            }
            else if ( ( object1 == null ) && ( object2 != null ) )
            {
                return 1 * ascendingInt;
            }
            else if ( ( object1 != null ) && ( object2 == null ) )
            {
                return -1 * ascendingInt;
            }
            else
            {
                // if object are not null and don't implement comparable, compare using string values
                return object1.toString().compareTo( object2.toString() );
            }
        }
        catch ( final Exception e )
        {
            return 0;
        }
    }
}