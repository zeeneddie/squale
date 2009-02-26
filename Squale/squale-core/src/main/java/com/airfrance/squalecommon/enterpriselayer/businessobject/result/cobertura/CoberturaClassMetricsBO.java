package com.airfrance.squalecommon.enterpriselayer.businessobject.result.cobertura;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * Instance of this class stores values regarding the method level metrics (stored in a
 * {@link Collection}. <br />
 * </p>
 * 
 * @hibernate.subclass discriminator-value="CoberturaClassMetrics"
 */
public class CoberturaClassMetricsBO
extends AbstractCoberturaMetricsBO
{
    /** Collection to store packages informations */
    private List<AbstractCoberturaMetricsBO> methods;
    
    
    /** The default constructor */
    public CoberturaClassMetricsBO()
    {
        super();
        methods = new ArrayList<AbstractCoberturaMetricsBO>();
    }
    
    /**
     * This method adds an instance of {@link CoberturaMethodMetricsBO} to the {@link Collection}. Each time a
     * "method" closing tag is faced by the parser this method is called (please refer to the CoberturaParser class for
     * more information).
     * 
     * @param methodBO the method that has to be added to the Collection
     */
    public void addMethod( CoberturaMethodMetricsBO methodBO )
    {
        methods.add( methodBO );
    }
    
    /**
     * Getter to retrieve the collection of packages
     * 
     * @return the Collection of packages level results
     */
    public List<AbstractCoberturaMetricsBO> getMethods()
    {
        return methods;
    }
}
