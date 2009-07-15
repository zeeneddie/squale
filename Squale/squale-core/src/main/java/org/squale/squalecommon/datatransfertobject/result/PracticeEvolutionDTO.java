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

import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;

/**
 * Représente l'évolution des pratiques entre deux audits
 */
public class PracticeEvolutionDTO
{

    /* Les constantes pour le filtre */

    /** L'index pour le tableau des filtres */
    public final static int ONLY_UP_OR_DOWN_ID = 0;

    /** L'index pour le tableau des filtres */
    public final static int THRESHOLD_ID = 1;

    /** L'index pour le tableau des filtres */
    public final static int ONLY_PRACTICES_ID = 2;

    /** Indique que le filtre se porte sur les composants qui se sont améliorés */
    public final static String ONLY_DOWN = "onlyDown";

    /** Indique que le filtre se porte sur les composants qui se sont dégradés */
    public final static String ONLY_UP = "onlyUp";

    /** Indique que le filtre se porte sur les composants qui ont été supprimés */
    public final static String DELETED = "deleted";

    /** Le composant concerné */
    private ComponentDTO mComponent;

    /** La pratique concernée */
    private QualityResultDTO mPractice;

    /** La note de l'audit de référence */
    private Float mMark;

    /** La note de l'audit de comparaison */
    private Float mPreviousMark;

    /**
     * @return le composant
     */
    public ComponentDTO getComponent()
    {
        return mComponent;
    }

    /**
     * @return la note courante
     */
    public Float getMark()
    {
        return mMark;
    }

    /**
     * @return la note précédente
     */
    public Float getPreviousMark()
    {
        return mPreviousMark;
    }

    /**
     * @return la pratique
     */
    public QualityResultDTO getPractice()
    {
        return mPractice;
    }

    /**
     * @param pComponent le composant
     */
    public void setComponent( ComponentDTO pComponent )
    {
        mComponent = pComponent;
    }

    /**
     * @param pMark la note courante
     */
    public void setMark( Float pMark )
    {
        mMark = pMark;
    }

    /**
     * @param pPreviousMark la note précédente
     */
    public void setPreviousMark( Float pPreviousMark )
    {
        mPreviousMark = pPreviousMark;
    }

    /**
     * @param pPractice la pratique concernée
     */
    public void setPractice( QualityResultDTO pPractice )
    {
        mPractice = pPractice;
    }

}
