package com.airfrance.squalecommon.enterpriselayer.facade.rule;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import com.airfrance.squalecommon.util.mapping.Mapping;

/**
 * Fabrique de mesure
 */
public class MeasureBOFactory {

    /**
     * Création d'une mesure
     * @param pComponentKind type de composant
     * @param pMeasureKind type de mesure
     * @return mesure correspondante ou null s'il n'existe pas une telle mesure
     */
    public MeasureBO createMeasure(String pComponentKind, String pMeasureKind) {
        String mes = pMeasureKind + "." + pComponentKind;
        
        MeasureBO result;
        try {
            result = (MeasureBO) Mapping.getMeasureClass(mes).newInstance();
        } catch (Throwable e) {
            // measure inexistante, on renvoie null qui 
            // sera traité par checkParameters
            result = null ;
        }
        return result;
    }
}
