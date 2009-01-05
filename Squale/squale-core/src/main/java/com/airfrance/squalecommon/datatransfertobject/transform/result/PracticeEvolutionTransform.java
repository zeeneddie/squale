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
package com.airfrance.squalecommon.datatransfertobject.transform.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.result.PracticeEvolutionDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.ComponentTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MarkBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.PracticeResultBO;

/**
 * Transforme des données sous forme BO en PracticeEvolutionDTO
 */
public class PracticeEvolutionTransform
{

    /**
     * @param pTabs la collection de tableau d'objets
     * @return la collection transformée
     */
    public static Collection tabCollectionToDto( Collection pTabs )
    {
        ArrayList results = new ArrayList();
        for ( Iterator it = pTabs.iterator(); it.hasNext(); )
        {
            Object[] currentTab = (Object[]) it.next();
            PracticeEvolutionDTO evolution = tabToDto( currentTab );
            results.add( evolution );
        }
        return results;
    }

    /**
     * @param pMarks les notes sous forme BO
     * @param pIsCurrentAudit indique si il s'agit de nouveaux composants
     * @return la collection transformée
     */
    public static Collection markCollectionToDto( Collection pMarks, boolean pIsCurrentAudit )
    {
        ArrayList results = new ArrayList();
        for ( Iterator it = pMarks.iterator(); it.hasNext(); )
        {
            MarkBO currentMark = (MarkBO) it.next();
            PracticeEvolutionDTO evolution = markBOToDto( currentMark, pIsCurrentAudit );
            results.add( evolution );
        }
        return results;
    }

    /**
     * @param pObject le tableau d'objets nécessaires à la construction du DTO
     * @return le tableau transformé en PracticeEvolutionDTO
     */
    private static PracticeEvolutionDTO tabToDto( Object[] pObject )
    {
        PracticeEvolutionDTO evolution = new PracticeEvolutionDTO();
        int index = 0;
        AbstractComponentBO componentBO = (AbstractComponentBO) pObject[index++];
        ComponentDTO componentDTO = ComponentTransform.bo2Dto( componentBO );
        evolution.setComponent( componentDTO );
        PracticeResultBO practiceBO = (PracticeResultBO) pObject[index++];
        evolution.setPractice( QualityResultTransform.bo2Dto( practiceBO ) );
        Float mark = (Float) pObject[index++];
        evolution.setMark( mark );
        Float previousMark = (Float) pObject[index++];
        evolution.setPreviousMark( previousMark );
        return evolution;
    }

    /**
     * @param pMarkBO la note sous forme BO
     * @param pIsCurrentAudit indique si il s'agit d'un composant appartenant à l'audit courant
     * @return la note transformée en PracticeEvolutionDTO
     */
    private static PracticeEvolutionDTO markBOToDto( MarkBO pMarkBO, boolean pIsCurrentAudit )
    {
        PracticeEvolutionDTO evolution = new PracticeEvolutionDTO();
        ComponentDTO componentDTO = ComponentTransform.bo2Dto( pMarkBO.getComponent() );
        evolution.setComponent( componentDTO );
        evolution.setPractice( QualityResultTransform.bo2Dto( pMarkBO.getPractice() ) );
        if ( pIsCurrentAudit )
        {
            // On change la note courante car il s'agit d'un composant de l'audit courant
            evolution.setMark( new Float( pMarkBO.getValue() ) );
        }
        else
        {
            // On change la note précédente car il s'agit d'un composant de l'audit précédent
            evolution.setPreviousMark( new Float( pMarkBO.getValue() ) );
        }
        return evolution;
    }

}
