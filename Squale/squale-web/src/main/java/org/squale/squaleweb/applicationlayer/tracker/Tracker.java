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
package org.squale.squaleweb.applicationlayer.tracker;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.squale.squaleweb.util.SqualeWebConstants;

/**
 * Classe gérant le traitement du traceur pseudo-historique
 */
public class Tracker
{

    /** le tracker courant */
    private List mTracker;

    /**
     * Affiche le traceur
     * 
     * @param ts l'objet représentant l'élément courant à ajouter au traceur
     * @param pRequest la requête HTTP.
     * @param pReset indique si on est repassé par un menu auquel cas on redémarre une série historique
     */
    public void updateHistTracker( TrackerStructure ts, HttpServletRequest pRequest, boolean pReset )
    {
        // test pour savoir si le traceur doit etre réinitialisé ou pas,
        // il l'est si on repasse par un menu
        if ( pReset )
        {
            this.reset( pRequest );
        }
        // on récupère le traceur courant
        ArrayList currentTracker = (ArrayList) pRequest.getSession().getAttribute( SqualeWebConstants.TRACKER_HIST );
        mTracker = new ArrayList( 0 );
        if ( null != currentTracker )
        {
            mTracker = currentTracker;
        }
        // Ajout
        // on vérifie qu'on est pas déjà passé par là,
        // si oui on ne fait que réajuster la liste du début jusqu'au chemin courant
        // en effaçant les parcours postérieurs.
        String aux = "";
        int j;
        boolean canExit = false;
        // premier parcours nécessaire pour vérifier qu'on a pas cliqué
        // sur le traceur
        for ( int i = 0; i < mTracker.size() && !canExit; i++ )
        {
            canExit = false;
            aux = ( (String) ( (TrackerStructure) mTracker.get( i ) ).getDisplayName() );
            // on est déjà passé par là (typiquement on a utilisé le traceur)
            // efface les éléments postérieurs en effaçant l'élément courant
            // qui sera de toute façon ajouté à la fin (factorisation de code)
            if ( aux != null && aux.equals( ts.getDisplayName() ) )
            {
                for ( j = i; j < mTracker.size();/* la taille diminue par le remove */)
                {
                    mTracker.remove( j );
                }
                canExit = true;
            }
        }
        // si ça ne vient pas du traceur
        if ( !canExit )
        {
            for ( int i = 0; i < mTracker.size() && !canExit; i++ )
            {
                newTrackerAccessWay( ts, i );
            }
        }
        // réajuste la liste
        ( (ArrayList) mTracker ).trimToSize();
        // on ajoute l'élément courant
        mTracker.add( ts );
        pRequest.getSession().setAttribute( SqualeWebConstants.TRACKER_HIST, mTracker );
    }

    /**
     * une méthode qui met à jour le tracker pour un chemin inédit
     * 
     * @param pTs la Structure du tracker
     * @param pIndex la position de l'élément courant comparé dans le tracker courant
     */
    private void newTrackerAccessWay( TrackerStructure pTs, int pIndex )
    {
        boolean canExit = false;
        // les éléments n'ont pas le type indéfini mais ont le meme type
        if ( pTs.getType() != TrackerStructure.UNDEFINED
            && pTs.getType() == ( (TrackerStructure) mTracker.get( pIndex ) ).getType() )
        {
            // il faut vérifier qu'il y a déjà un élément d'un autre type présent
            // sinon on efface lors de facteurs-criteres-pratiques...
            // car facteur et pratique ont le meme type
            if ( pTs.getType() == TrackerStructure.FACTOR_VIEW )
            {
                for ( int j = mTracker.size() - 1; j > -1; j-- )
                {
                    if ( TrackerStructure.COMPONENT_VIEW == ( (TrackerStructure) mTracker.get( j ) ).getType() )
                    {
                        // on doit vérifier qu'il y a aussi un element de type facteur view déja présent
                        // pour le cas où on passe par les tops
                        for ( int k = j - 1; k > -1 && !canExit; k-- )
                        {
                            if ( TrackerStructure.FACTOR_VIEW == ( (TrackerStructure) mTracker.get( k ) ).getType() )
                            {
                                // si oui, on peut tout effacer
                                while ( j > -1 )
                                {
                                    mTracker.remove( j-- );
                                }
                                canExit = true;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param pRequest la requête http
     */
    public void reset( HttpServletRequest pRequest )
    {
        pRequest.getSession().setAttribute( SqualeWebConstants.TRACKER_RESET, "false" );
        pRequest.getSession().setAttribute( SqualeWebConstants.TRACKER_HIST, new ArrayList() );
        pRequest.getSession().setAttribute( SqualeWebConstants.TRACKER_COMPONENT, new ArrayList() );
    }

}