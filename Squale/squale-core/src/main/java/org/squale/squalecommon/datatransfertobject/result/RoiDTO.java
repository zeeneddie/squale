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
package org.squale.squalecommon.datatransfertobject.result;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

/**
 * DTO pour le ROI
 */
public class RoiDTO
{

    /**
     * La list contenant les valeurs pour fabriquer le graphe abscisse=le nom de l'appli - date de l'audit ordonnée=la
     * valeur du ROI
     */
    private List mMeasures;

    /** Le nombre de corrections */
    private int mNbCorrections;

    /** Le ROI total */
    private double mTotal;

    /** L'id de l'application. Si l'id est -1 on prend toutes les applications */
    private long mApplicationId = -1;

    /** Formule appliquée pour calculer le ROI à partir du nombre de corrections */
    private String mFormula;

    /**
     * @return l'id de l'application
     */
    public long getApplicationId()
    {
        return mApplicationId;
    }

    /**
     * @param pApplicationId l'id de l'application
     */
    public void setApplicationId( long pApplicationId )
    {
        mApplicationId = pApplicationId;
    }

    /**
     * @return la formule
     */
    public String getFormula()
    {
        return mFormula;
    }

    /**
     * @param pFormula la formule
     */
    public void setFormula( String pFormula )
    {
        mFormula = pFormula;
    }

    /**
     * @param pTotal le ROI total
     */
    public void setTotal( double pTotal )
    {
        mTotal = pTotal;
    }

    /**
     * @return le ROI total
     */
    public double getTotal()
    {

        DecimalFormat df = new DecimalFormat( "########.0" );// un chiffre apres la virgule
        DecimalFormatSymbols dcmlFS = new DecimalFormatSymbols();
        dcmlFS.setDecimalSeparator( '.' );
        df.setDecimalFormatSymbols( dcmlFS );
        return Double.parseDouble( df.format( mTotal ) );
    }

    /**
     * @return le nombre de corrections
     */
    public int getNbCorrections()
    {
        return mNbCorrections;
    }

    /**
     * @param pNbCorrections Le nombre de corrections
     */
    public void setNbCorrections( int pNbCorrections )
    {
        mNbCorrections = pNbCorrections;
    }

    /**
     * @return les mesures
     */
    public List getMeasures()
    {
        return mMeasures;
    }

    /**
     * @param pMeasures les mesures
     */
    public void setMeasures( List pMeasures )
    {
        mMeasures = pMeasures;
    }
}
