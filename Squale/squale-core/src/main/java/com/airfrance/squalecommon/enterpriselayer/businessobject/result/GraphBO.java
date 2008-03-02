/*
 * Créé le 3 août 05
 *
 */
package com.airfrance.squalecommon.enterpriselayer.businessobject.result;

/**
 * @author m400841
 * @version 1.0
 * @hibernate.subclass discriminator-value="Graph"
 */
public abstract class GraphBO
    extends MeasureBO
{
    /**
     * Image sous forme de chaine de bytes
     */
    private final static String IMAGE = "Image";

    /**
     * Constructeur publique Initialise les "pseudo" attributs de la HashTable
     */
    public GraphBO()
    {
        super();
        getMetrics().put( IMAGE, new BinaryMetricBO() );
    }

    /**
     * @return recupere l'image sous forme de byte[]
     */
    public byte[] getImage()
    {
        return (byte[]) ( (MetricBO) getMetrics().get( IMAGE ) ).getValue();
    }

    /**
     * @param pImage image sous forme de byte[]
     */
    public void setImage( byte[] pImage )
    {
        ( (MetricBO) getMetrics().get( IMAGE ) ).setValue( pImage );
    }

}
