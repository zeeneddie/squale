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
//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\com\\airfrance\\squalecommon\\datatransfertobject\\ResultsDTO.java

package com.airfrance.squalecommon.datatransfertobject.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.datatransfertobject.DTOMessages;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;

/**
 */
public class ResultsDTO
    implements Serializable
{

    /** log */
    private static Log LOG = LogFactory.getLog( ResultsDTO.class );

    /**
     * Contient : Une liste pValues qui contient : [ une liste colTypes : [ typeY | typeX1 | typeX2 | ... | typeXn ] |
     * et une à n liste(s) de valeurs (la première valeur etant le type de la ligne) : [ typeY1 | val11 | val21 | ... |
     * valn1 ] | ... | [ typeYm | val1m | val2m | ... | valnm ] ] n > 0, m > 0 (=> (n+1)*(m+1) éléments)
     */
    private Map mResultMap;

    /**
     * Clé : AuditDTO (ou ComponentDTO) Valeur : Tableau de répartitions des pratiques sur des intervalles de pas = 1
     */
    private Map mIntRepartitionPracticeMap;

    /**
     * Clé : AuditDTO (ou ComponentDTO) Valeur : Tableau de répartitions des pratiques sur des intervalles de pas = 0.1
     */
    private Map mFloatRepartitionPracticeMap;

    /**
     * Constructeur par defaut
     * 
     * @roseuid 42CB94450018
     */
    public ResultsDTO()
    {
        Map resultMap = new HashMap();
        setResultMap( resultMap );
    }

    /**
     * Access method for the mResultMap property.
     * 
     * @return the current value of the mResultMap property
     * @roseuid 42CB94450040
     */
    public Map getResultMap()
    {
        return mResultMap;
    }

    /**
     * Sets the value of the mResultMap property.
     * 
     * @param pResultMap the new value of the mResultMap property
     * @roseuid 42CB94450072
     */
    private void setResultMap( Map pResultMap )
    {
        mResultMap = pResultMap;
    }

    /**
     * Permet d'ajouter au resultMap une ligne supplementaire Verifie que le format est le suivant : null en cle --
     * liste d'objets en valeur ( n fois ) objet en cle -- liste de meme taille en valeur
     * 
     * @param pKey clé à ajouter
     * @param pValue valeur associée a la cle
     */
    public void put( Object pKey, List pValue )
    {

        List firstList = (List) mResultMap.get( null );
        if ( firstList != null )
        { // s'il n'y a pas de premier element
            if ( firstList.size() != pValue.size() )
            {
                /* Peut arriver dans les cas de la QualityResultsFacade */
                LOG.warn( DTOMessages.getString( "dto.exception.results.put.sizelist" ) );
            }
            else
            {
                // on ajoute la liste a la HashMap
                mResultMap.put( pKey, pValue );
            }
        }
        else
        {
            if ( pKey != null )
            {
                // si la cle null du premier element n'existe pas
                LOG.warn( DTOMessages.getString( "dto.exception.results.put.nofirstelement" ) );
            }
            // on ajoute la liste a la HashMap
            mResultMap.put( pKey, pValue );
        }

    }

    /**
     * Permet de concatener une collection de resultsDTO
     * 
     * @param pResultsSrc ResultsDTO qui ajoute ses données au pResultsDest
     * @return ResultsDTO unique
     */
    public ResultsDTO add( ResultsDTO pResultsSrc )
    {

        // Chargement des clés de chacune des Maps
        Map resultSrcMap = pResultsSrc.getResultMap();

        Iterator result1Iterator = mResultMap.keySet().iterator();
        Object currentKey = null;

        /*
         * Parcours des cles du premier ResultsDTO et concatenation de la liste (valeur) du deuxieme ResultsDTO
         * correspondant a la cle courante
         */
        while ( result1Iterator.hasNext() )
        {
            currentKey = result1Iterator.next();
            if ( resultSrcMap.get( currentKey ) != null )
            {
                ( (List) mResultMap.get( currentKey ) ).addAll( (List) resultSrcMap.get( currentKey ) );
            }
        }

        /*
         * Verification que tous les element du 2eme ResultsDTO sont presents dans le premier
         */
        // TODO chemin non teste
        if ( !mResultMap.keySet().containsAll( resultSrcMap.keySet() ) )
        {

            Iterator result2Iterator = resultSrcMap.keySet().iterator();
            currentKey = null;

            // Ajout de la premiere liste
            while ( result2Iterator.hasNext() )
            {
                currentKey = result2Iterator.next();
                /*
                 * SI la clé n'existe pas dans le premier ResultsDTO ALORS on ajoute une liste avec des nulls de la
                 * taille des listes du premier ResultsDTO PUIS on concatene la liste de deuxieme ResultsDTO
                 */
                if ( mResultMap.get( currentKey ) == null )
                {

                    List result1ListTemp = new ArrayList( ( (List) mResultMap.get( null ) ).size() );
                    Collections.fill( result1ListTemp, null );
                    result1ListTemp.addAll( (List) resultSrcMap.get( currentKey ) );
                }
            }
        }

        return this;
    }

    /**
     * Access method for the mIntRepartitionPracticeMap property.
     * 
     * @return Map contenant une liste de répartitions des pratiques
     */
    public Map getIntRepartitionPracticeMap()
    {
        return mIntRepartitionPracticeMap;
    }

    /**
     * Sets the value of the mIntRepartitionPracticeMap property.
     * 
     * @param pPracticeMap Map contenant une liste de répartitions des pratiques
     */
    public void setIntRepartitionPracticeMap( Map pPracticeMap )
    {
        mIntRepartitionPracticeMap = pPracticeMap;
    }

    /**
     * Access method for the mIntRepartitionPracticeMap property.
     * 
     * @return Map contenant une liste de répartitions des pratiques
     */
    public Map getFloatRepartitionPracticeMap()
    {
        return mFloatRepartitionPracticeMap;
    }

    /**
     * Sets the value of the mFloatRepartitionPracticeMap property.
     * 
     * @param pPracticeMap Map contenant une liste de répartitions des pratiques
     */
    public void setFloatRepartitionPracticeMap( Map pPracticeMap )
    {
        mFloatRepartitionPracticeMap = pPracticeMap;
    }

    /**
     * @param pProject le projet
     * @return la note courante
     */
    public Float getCurrentMark( ComponentDTO pProject )
    {
        return ( (Float) ( ( (List) getResultMap().get( pProject ) ).get( 0 ) ) );
    }

    /**
     * @param pProject le projet
     * @return la note précédente
     */
    public Float getPreviousMark( ComponentDTO pProject )
    {
        return (Float) ( ( (List) getResultMap().get( pProject ) ).get( 1 ) );
    }

}
