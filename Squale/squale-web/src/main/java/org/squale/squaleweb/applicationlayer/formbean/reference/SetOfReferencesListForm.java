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
package org.squale.squaleweb.applicationlayer.formbean.reference;

import java.util.ArrayList;
import java.util.List;

import org.squale.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Regroupe l'ensemble des différents ReferenceListForm
 */
public class SetOfReferencesListForm
    extends RootForm
{

    /**
     * Ensemble des listes des références, liste de ReferenceGridForm
     */
    private List mList = new ArrayList( 0 );

    /**
     * @return la liste des grilles de références
     */
    public List getList()
    {
        return mList;
    }

    /**
     * @param pList la liste des grille de références
     */
    public void setList( List pList )
    {
        mList = pList;
    }

    /**
     * Ajoute la référence associée à la grille Si une grille porte le même nom et que la date est avant pGridForm alors
     * on supprime la grille et on ajoute pGridForm
     * 
     * @param pGridForm la grille de référence
     * @param pReferenceForm la référence a ajouter
     */
    public void add( ReferenceGridForm pGridForm, ReferenceForm pReferenceForm )
    {
        // On parcours la liste
        List refs = null;
        ReferenceGridForm current = null;
        boolean found = false;
        int compare = 0;
        for ( int i = 0; i < mList.size() && null == refs; i++ )
        {
            current = (ReferenceGridForm) mList.get( i );
            if ( current.getName().equals( pGridForm.getName() ) )
            {
                refs = (List) current.getReferenceListForm().getList(); // on sauvegarde la valeur associée
                compare = current.getUpdateDate().compareTo( pGridForm.getUpdateDate() );
                // Si une grille du même nom existe et que la date est avant
                // alors on la remplace par la grille en paramètre
                if ( compare < 0 )
                {
                    mList.remove( i ); // on supprime l'entrée afin de la remplacer
                }
                else
                {
                    found = true;
                    if ( compare == 0 )
                    { // ce sont les mêmes grille, on ajoute la référence
                        refs.add( pReferenceForm );
                    }
                }
            }
        }
        // Si on a trouvé aucune entrée portant le même nom, on ajoute la grille
        if ( !found )
        {
            refs = new ArrayList();
            refs.add( pReferenceForm );
            ReferenceListForm refsList = new ReferenceListForm();
            refsList.setList( refs );
            pGridForm.setReferenceListForm( refsList );
            mList.add( pGridForm );
        }
    }

}