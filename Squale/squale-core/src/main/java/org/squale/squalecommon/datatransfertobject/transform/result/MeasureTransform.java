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
package org.squale.squalecommon.datatransfertobject.transform.result;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.datatransfertobject.DTOMessages;
import org.squale.squalecommon.datatransfertobject.result.ResultsDTO;
import org.squale.squalecommon.datatransfertobject.transform.component.ComponentTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.MetricBO;
import org.squale.squalecommon.util.mapping.Mapping;

/**
 * @author M400843
 */
public class MeasureTransform
    implements Serializable
{

    /** logger */
    private static final Log LOG = LogFactory.getLog( MeasureTransform.class );

    /**
     * Retourne une valeur (un attribut) d'un MeasureBO
     * 
     * @param pMeasure la mesure
     * @param pKeyTRE la clé du TRE que l'on désire
     * @return la valeur (dans un objet) correspondante
     */
    private static Object getValue( MeasureBO pMeasure, String pKeyTRE )
    {
        Object value = null;
        // récupération du getter correspondant au TRE souhaité
        Method getter = Mapping.getMetricGetter( pKeyTRE );
        if ( ( null != getter ) && ( null != pMeasure ) )
        {
            try
            {
                // invocation du getter sur la mesure
                value = getter.invoke( pMeasure, null );
            }
            catch ( IllegalArgumentException e )
            {
                // Exception ayant lieu si les arguments passés sont incorrect,
                // ne devrait pas arriver sur un getter
                String tab[] = { Long.toString( pMeasure.getId() ), pKeyTRE };
                LOG.error( DTOMessages.getString( "transform.measure.IllegalArgumentException.measure_key", tab ), e );
            }
            catch ( IllegalAccessException e )
            {
                // ne devrait pas arriver sur un getter
                String tab[] = { Long.toString( pMeasure.getId() ), pKeyTRE };
                LOG.error( DTOMessages.getString( "transform.measure.IllegalAccessException.measure_key", tab ), e );
            }
            catch ( InvocationTargetException e )
            {
                // ne devrait pas arriver sur un getter
                String tab[] = { Long.toString( pMeasure.getId() ), pKeyTRE };
                LOG.error( DTOMessages.getString( "transform.measure.InvocationTargetException.measure_key", tab ), e );
            }
        }
        return value;
    }

    /**
     * Transforme une mesure en ResultDTO
     * 
     * @param pMeasure la mesure
     * @param pKeyTRE la clé du TRE que l'on désire
     * @return le résultat (ResultDTO) correspondant
     */
    public static ResultsDTO bo2dto( MeasureBO pMeasure, String pKeyTRE )
    {
        ResultsDTO resultDTO;
        Object value;
        // Dans le cas d'une mesure nulle, par exemple une mesure n'existant pas
        // dans un audit précédent, on renvoie une valeur nulle dans le résultat
        if ( pMeasure == null )
        {
            value = null;
        }
        else
        {
            value = getValue( pMeasure, pKeyTRE );
        }
        resultDTO = new ResultsDTO();
        List resultList = new ArrayList();
        resultList.add( value );
        resultDTO.put( null, resultList );
        return resultDTO;
    }

    /**
     * Récupère la bonne instance de mesure dans la collection de mesures
     * 
     * @param pMeasures une collection de mesures
     * @param pTREKey la clé
     * @return une instance de la classe associé à pTREKey si elle est présente dans pMeasures, <code>null</code>
     *         sinon
     */
    private static MeasureBO getMeasure( Collection pMeasures, String pTREKey )
    {
        MeasureBO measure = null;
        Class measureClass = Mapping.getMetricClass( pTREKey );
        Iterator it = pMeasures.iterator();
        while ( it.hasNext() && ( null == measure ) )
        {
            MeasureBO measureToTest = (MeasureBO) it.next();
            if ( measureClass.isInstance( measureToTest ) )
            {
                measure = measureToTest;
            }
        }
        return measure;
    }

    /**
     * Permet d'ajouter un objet en clé et une liste de mesures à un ResultsDTO
     * 
     * @param pResults ResultsDTO ayant en abscisse les clés TRE, si <code>null</code> il est instancié et la première
     *            ligne est affectée
     * @param pMeasures resultsDTO auquel il faut ajouter listes
     * @param pTREKeys listes des clés de mesures
     * @param pObject objet clé de la HashMap de resultsDTO
     * @return ResultsDTO à jour
     */
    public static ResultsDTO bo2dtoByTRE( ResultsDTO pResults, Collection pMeasures, List pTREKeys, Object pObject )
    {
        ResultsDTO results = null;

        // Création de la liste de résultats
        List resultList = new ArrayList();
        Iterator it = pTREKeys.iterator();
        while ( it.hasNext() )
        {
            String key = (String) it.next();
            MeasureBO measure = getMeasure( pMeasures, key );

            resultList.add( getValue( measure, key ) );
        }

        if ( null == pResults )
        {
            results = new ResultsDTO();
            results.put( null, pTREKeys );
        }
        else
        {
            results = pResults;
        }
        results.put( pObject, resultList );

        return results;
    }
    
    /**
     * Transform measures to map
     * key : tre
     * value : score for this tre
     * @param pMeasures measures
     * @param pTREKeys measures keys
     * @return map
     */
    public static Map bo2dtoForMetric(Collection pMeasures, List pTREKeys) {
        Map metricsValues = new HashMap();
        Iterator it = pTREKeys.iterator();
        while ( it.hasNext() )
        {
            String key = (String) it.next();
            MeasureBO measure = getMeasure( pMeasures, key );
            metricsValues.put( key, getValue( measure, key ) );
        }
        return metricsValues;
    }

    /**
     * Permet d'ajouter un objet en clé et une liste de mesures à un ResultsDTO
     * 
     * @param pResults ResultsDTO ayant en abscisse les clés TRE, si <code>null</code> il est instancié et la première
     *            ligne est affectée
     * @param pMeasures resultsDTO auquel il faut ajouter listes
     * @param pObject objet clé de la HashMap de resultsDTO
     * @return ResultsDTO à jour
     */
    public static ResultsDTO bo2dtoByTRE( ResultsDTO pResults, Collection pMeasures, Object pObject )
    {
        ResultsDTO results = null;

        // Création de la liste de résultats
        List resultList = new ArrayList();
        List keyList = new ArrayList();
        Iterator it = pMeasures.iterator();
        // Parcours de chaque mesure
        while ( it.hasNext() )
        {
            MeasureBO measure = (MeasureBO) it.next();
            Map metrics = measure.getMetrics();
            Iterator keys = metrics.keySet().iterator();
            // Parcours de chaque clef de métrique
            // pour les pacer dans le résultat
            while ( keys.hasNext() )
            {
                String key = (String) keys.next();
                String keyMnemo = Mapping.getMeasureName( measure.getClass() ) + "." + key;
                MetricBO metric = (MetricBO) metrics.get( key );
                // si le type est "affichable"
                if ( metric.isPrintable() )
                {
                    // on le stocke dans la hashmap
                    keyList.add( keyMnemo );
                    resultList.add( metric.getValue() );
                }
            }
        }

        if ( null == pResults )
        {
            // Si aucun résultat n'était disponible, on cree
            // un résultat vide ne contenant que les clefs de métrique
            results = new ResultsDTO();
            results.put( null, keyList );
        }
        else
        {
            results = pResults;
        }
        // Ajout des résultats en regard de l'objet
        results.put( pObject, resultList );

        return results;
    }

    /**
     * Permet d'ajouter un objet en clé et une liste de mesures à un ResultsDTO
     * 
     * @param pResults ResultsDTO ayant en abscisse les clés TRE, si <code>null</code> il est instancié et la première
     *            ligne est affectée
     * @param pMeasures liste de mesures
     * @param pAbcisse liste des audits ou des comopsants
     * @param pTREKey clé de mesures
     * @param pObject objet clé de la HashMap de resultsDTO
     * @return ResultsDTO à jour
     */
    public static ResultsDTO bo2dtoByAuditOrComponent( ResultsDTO pResults, List pMeasures, List pAbcisse,
                                                       String pTREKey, Object pObject )
    {
        ResultsDTO results = null;

        // Création de la liste de résultats
        List resultList = new ArrayList();
        Iterator it = pMeasures.iterator();
        while ( it.hasNext() )
        {
            MeasureBO measure = (MeasureBO) it.next();

            resultList.add( getValue( measure, pTREKey ) );
        }

        if ( null == pResults )
        {
            results = new ResultsDTO();
            results.put( null, pAbcisse );
        }
        else
        {
            results = pResults;
        }
        results.put( pObject, resultList );

        return results;
    }

    /**
     * @param pResults ResultsDTO ayant en abscisse les clés TRE (ou null)
     * @param pMeasuresAbcisse Liste contenat des Objet[] {Componsant, Valeur de la measure)
     * @param pObject Audit courant (pour stocker le resultats dans le ResultDTO)
     * @return ResultsDTO à jour
     */
    public static ResultsDTO bo2dtoByAuditOrComponent( ResultsDTO pResults, List pMeasuresAbcisse, Object pObject )
    {
        ResultsDTO results = null;

        // Création de la liste de résultats
        List resultList = new ArrayList();
        List abscisse = new ArrayList();
        Iterator it = pMeasuresAbcisse.iterator();
        while ( it.hasNext() )
        {
            Object[] l = (Object[]) it.next();
            Number measure = (Number) l[1];
            abscisse.add( ComponentTransform.bo2Dto( (AbstractComponentBO) l[0] ) );
            resultList.add( measure );
        }

        if ( null == pResults )
        {
            results = new ResultsDTO();
            results.put( null, abscisse );
        }
        else
        {
            results = pResults;
        }
        results.put( pObject, resultList );

        return results;
    }

}
