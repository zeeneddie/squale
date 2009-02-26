package com.airfrance.squalecommon.enterpriselayer.businessobject.result.cobertura;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * Instance of this class stores values regarding the package level metrics (stored in a
 * {@link Collection}. <br />
 * </p>
 * 
 * @hibernate.subclass discriminator-value="CoberturaPackageMetrics"
 */
public class CoberturaPackageMetricsBO
    extends AbstractCoberturaMetricsBO
{
    /** Collection to store packages informations */
    private List<AbstractCoberturaMetricsBO> classes;

    /** The default constructor */
    public CoberturaPackageMetricsBO()
    {
        super();
        classes = new ArrayList<AbstractCoberturaMetricsBO>();
    }
    /**
     * This method adds an instance of {@link CoberturaClassMetricsBO} to the {@link Collection}. Each time a
     * "class" closing tag is faced by the parser this method is called (please refer to the CoberturaParser class for
     * more information).
     * 
     * @param classBO the class that has to be added to the Collection
     */
    public void addClass( CoberturaClassMetricsBO classBO )
    {
        classes.add( classBO );
    }
    
    /**
     * Getter to retrieve the collection of classes
     * 
     * @return the Collection of classes level results
     */
    public List<AbstractCoberturaMetricsBO> getClasses()
    {
        return classes;
    }
}
