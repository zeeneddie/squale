package com.airfrance.squalecommon.datatransfertobject.transform.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.config.StopTimeDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.StopTimeBO;

/**
 * Transforme une date limite
 */
public class StopTimeTransform
{

    /**
     * Convertit un StopTimeBO en StopTimeDTO
     * 
     * @param pStopTime l'objet à convertir
     * @return le résultat de la conversion
     */
    public static StopTimeDTO bo2dto( StopTimeBO pStopTime )
    {
        StopTimeDTO result = new StopTimeDTO();
        result.setId( pStopTime.getId() );
        result.setDay( pStopTime.getDay() );
        result.setTime( pStopTime.getTime() );
        return result;
    }

    /**
     * Convertit une liste de StopTimeBO en liste de StopTimeDTO
     * 
     * @param pStopTimes la liste des date limites à convertir
     * @return le résultat de la conversion
     */
    public static Collection bo2dto( Collection pStopTimes )
    {
        Collection stopTimesDTO = new ArrayList();
        StopTimeDTO stopTimeDTO;
        StopTimeBO stopTimeBO;
        Iterator it = pStopTimes.iterator();
        while ( it.hasNext() )
        {
            stopTimeBO = (StopTimeBO) it.next();
            stopTimeDTO = bo2dto( stopTimeBO );
            stopTimesDTO.add( stopTimeDTO );
        }
        return stopTimesDTO;
    }

    /**
     * @param pStopTimeDTO la date limite à convertir
     * @return le BO récupérateur de source
     */
    public static StopTimeBO dto2bo( StopTimeDTO pStopTimeDTO )
    {
        StopTimeBO result = new StopTimeBO();
        result.setId( pStopTimeDTO.getId() );
        result.setDay( pStopTimeDTO.getDay() );
        result.setTime( pStopTimeDTO.getTime() );
        return result;
    }
}
