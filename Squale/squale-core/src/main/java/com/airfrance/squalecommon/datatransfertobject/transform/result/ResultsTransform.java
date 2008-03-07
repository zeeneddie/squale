/*
 * Créé le 12 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.datatransfertobject.transform.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.result.ResultsDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MarkBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.QualityResultBO;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ResultsTransform
    implements Serializable
{

    /**
     * Constructeur prive
     */
    private ResultsTransform()
    {
    }

    /**
     * DTO -> BO pour un Results
     * 
     * @param pResultsDTO ResultsDTO à transformer
     * @return ResultsBO
     * @deprecated non utilisé car les résultats ne seront pas modifiés par la facade
     */
    /* TODO BEF --> deprecated method to suppress
    public static List dto2Bo( ResultsDTO pResultsDTO )
    {

        // Initialisation
        List list = null; // retour de la methode

        pResultsDTO = null; // traitement du parametre

        // Non implémenté car les résultats ne seront pas modifiés par la facade 

        return list;
    }*/

    /**
     * Permet de convertir la liste d'objets metiers en valeurs
     * 
     * @param pResultsBO liste des valeurs a mettre en valeurs dans la HashMap de ResultsDTO
     * @return ResultsDTO avec HashMap modifié
     */
    public static List bo2Dto( Collection pResultsBO )
    {

        // Initialisation
        List resultsDTO = new ArrayList(); // retour de la methode

        Iterator resultsIterator = pResultsBO.iterator();
        Object currentObject = null;
        Object addedObject = null;
        // Pour chaque résultat de la liste
        while ( resultsIterator.hasNext() )
        {

            currentObject = resultsIterator.next();
            // On regarde son type et on le transforme
            if ( currentObject instanceof MarkBO )
            {
                addedObject = bo2DtoMark( (MarkBO) currentObject );
            }
            else if ( currentObject instanceof MeasureBO )
            {
                addedObject = bo2DtoMeasure( (MeasureBO) currentObject );
            }
            else if ( currentObject instanceof QualityResultBO )
            {
                addedObject = bo2DtoQualityResult( (QualityResultBO) currentObject );
            }
            else
            {
                addedObject = currentObject;
            }

            resultsDTO.add( addedObject );
        }

        return resultsDTO;

    }

    /**
     * Transforme unQualityResultBO en un objet Float correspondant à l'attribut MeanMark
     * 
     * @param pResultBO QualityResultBO
     * @return flottant sous forme d'objet
     */
    private static Float bo2DtoQualityResult( QualityResultBO pResultBO )
    {

        Float mark = null;
        float f = pResultBO.getMeanMark();
        if ( f != -1.0f )
        {
            mark = new Float( f );
        }
        return mark;

    }

    /**
     * @param pResultsBO liste a raiter
     * @return null
     * @deprecated utiliser le MeasureTransform
     */
   
    private static Float bo2DtoMeasure( MeasureBO pResultsBO )
    {
        
        MeasureBO newResultsBO = pResultsBO;

        // Initialisation
        Float value = null; // Initialisation du retour

        newResultsBO = null; // traitement du parametre

        // Non implémenté car externalisé dans MeasureTransform 

        return value;

    }

    /**
     * Transforme un MarkBO en un Float correspondant à l'attribut "value"
     * 
     * @param pResultBO MarkBO associe
     * @return Float
     */
    private static Float bo2DtoMark( MarkBO pResultBO )
    {
        Float value = new Float( pResultBO.getValue() );
        return value;
    }

}
