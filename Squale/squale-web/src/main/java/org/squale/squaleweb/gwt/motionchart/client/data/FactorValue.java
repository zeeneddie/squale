package org.squale.squaleweb.gwt.motionchart.client.data;

import java.io.Serializable;

public class FactorValue
    implements Serializable
{
    private static final long serialVersionUID = 6896331953304641198L;

    private int valuesCount;

    private float valuesSum;

    public void addValue( float factorValue )
    {
        valuesSum += factorValue;
        valuesCount++;
    }

    /**
     * @return the valuesSum
     */
    public float getValue()
    {
        return valuesSum / valuesCount;
    }
}
