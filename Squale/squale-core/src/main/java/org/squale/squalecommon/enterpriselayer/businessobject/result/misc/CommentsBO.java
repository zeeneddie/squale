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
 * Créé le 6 sept. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.squalecommon.enterpriselayer.businessobject.result.misc;

import org.squale.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.MeasureBO;

/**
 * @author M400843
 * @version 1.1
 * @hibernate.subclass discriminator-value="Comments"
 */
public class CommentsBO
    extends MeasureBO
{

    /**
     * Nombre de lignes de commentaires de la classe, i.e. nombre de lignes de commentaire pur et de commentaire mixte
     * (code + commentaire)
     */
    private final static String CLOC = "cloc";

    /**
     * Nombre total de lignes de code, y compris les commentaires, à l'exclusion des lignes blanches.
     */
    private final static String SLOC = "sloc";

    /**
     * Constructeur publique Initialise les "pseudo" attributs de la HashTable
     */
    public CommentsBO()
    {
        super();
        getMetrics().put( CLOC, new IntegerMetricBO() );
        getMetrics().put( SLOC, new IntegerMetricBO() );
    }

    /**
     * Getter.
     * 
     * @return le nombre de lignes de commentaire de la classe
     */
    public Integer getCloc()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( CLOC ) ).getValue();
    }

    /**
     * Setter.
     * 
     * @param pCloc le nouveau nombre de ligne de commentaire de la classe
     */
    public void setCloc( Integer pCloc )
    {
        ( (IntegerMetricBO) getMetrics().get( CLOC ) ).setValue( pCloc );
    }

    /**
     * Getter.
     * 
     * @return le nombre de lignes de code de la classe
     */
    public Integer getSloc()
    {
        return (Integer) ( (IntegerMetricBO) getMetrics().get( SLOC ) ).getValue();
    }

    /**
     * Setter.
     * 
     * @param pSloc le nouveau nombre de lignes de code de la classe
     */
    public void setSloc( Integer pSloc )
    {
        ( (IntegerMetricBO) getMetrics().get( SLOC ) ).setValue( pSloc );
    }

    /**
     * Getter.<br />
     * CLOC / SLOC * 100
     * 
     * @return le pourcentage global de lignes de commentaire d'un sous-projet.
     */
    public float getCommentPercent()
    {
        return ( getCloc().floatValue() / getSloc().floatValue() ) * 100.f;
    }
}
