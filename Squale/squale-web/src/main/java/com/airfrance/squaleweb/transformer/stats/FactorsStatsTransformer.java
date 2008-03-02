package com.airfrance.squaleweb.transformer.stats;

import com.airfrance.squalecommon.datatransfertobject.stats.FactorsStatsDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.stats.FactorsStatsForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 */
public class FactorsStatsTransformer
    implements WITransformer
{

    /**
     * @param pObject le tableau d'objet contenant l'objet à transformer
     * @return le form résultat de la transformation
     * @throws WTransformerException en cas d'échec
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        FactorsStatsForm form = new FactorsStatsForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pObject le tableau d'objet contenant l'objet à transformer
     * @param pForm le form résultat
     * @throws WTransformerException en cas d'échec
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        FactorsStatsForm form = (FactorsStatsForm) pForm;
        FactorsStatsDTO dto = (FactorsStatsDTO) pObject[0];
        form.setNbFactorsAccepted( dto.getNbFactorsAccepted() );
        form.setNbFactorsRefused( dto.getNbFactorsRefused() );
        form.setNbFactorsReserved( dto.getNbFactorsReserved() );
        form.setNbTotal( dto.getNbTotal() );
    }

    /**
     * @deprecated n'a pas de sens
     * @param pForm le formulaire
     * @throws WTransformerException si un pb apparait.
     * @return rien mais lance systématiquement une exception
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        throw new WTransformerException( "deprecated" );
    }

    /**
     * @deprecated n'a pas de sens
     * @param pForm le formulaire
     * @param pTab les paramètres
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj( WActionForm pForm, Object[] pTab )
        throws WTransformerException
    {
        throw new WTransformerException( "deprecated" );
    }

}
