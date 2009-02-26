package com.airfrance.squalecommon.enterpriselayer.businessobject.result.cobertura;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * Instance of this class stores values regarding the project level metrics such as package level metrics (stored in a
 * {@link Collection}. <br />
 * </p>
 * 
 * @hibernate.subclass discriminator-value="CoberturaProjectMetrics"
 */
public class CoberturaProjectMetricsBO
    extends AbstractCoberturaMetricsBO
{
    /** Collection to store packages informations */
    private List<AbstractCoberturaMetricsBO> packages;

    /** The default constructor */
    public CoberturaProjectMetricsBO()
    {
        super();
        packages = new ArrayList<AbstractCoberturaMetricsBO>();
    }

    /**
     * This method adds an instance of {@link CoberturaPackageMetricsBO} to the {@link Collection}. Each time a
     * "package" closing tag is faced by the parser this method is called (please refer to the CoberturaParser class for
     * more information).
     * 
     * @param packageBO the package that has to be added to the Collection
     */
    public void addPackage( CoberturaPackageMetricsBO packageBO )
    {
        packages.add( packageBO );
    }

    /**
     * Getter to retrieve the collection of packages
     * 
     * @return the Collection of packages level results
     */
    public List<AbstractCoberturaMetricsBO> getPackages()
    {
        return packages;
    }
}