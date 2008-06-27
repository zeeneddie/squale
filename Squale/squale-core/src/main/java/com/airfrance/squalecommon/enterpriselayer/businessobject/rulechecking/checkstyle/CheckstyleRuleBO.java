package com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle;

import java.util.HashSet;
import java.util.Set;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;

/**
 * Règle checkstyle
 * 
 * @hibernate.subclass discriminator-value="Checkstyle"
 */
public class CheckstyleRuleBO
    extends RuleBO
{

    /**
     * Liste des modules
     */
    protected Set mModules = new HashSet();

    /**
     * Access method for the mModules property.
     * 
     * @return the current value of the mModules property
     * @hibernate.set lazy="true" cascade="all" sort="unsorted"
     * @hibernate.key column="RuleId"
     * @hibernate.one-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleModuleBO"
     */

    public Set getModules()
    {
        return mModules;
    }

    /**
     * Sets the value of the mModules property.
     * 
     * @param pModules the new value of the mModules property
     */

    public void setModules( Set pModules )
    {
        mModules = pModules;
    }

    /**
     * rajoute le module à la liste des modules de la règle
     * 
     * @param pModule module checkstyle
     * @throws MissingSeverityException si l'attribut severity est absent du module
     * @throws MismatchSeverityException si l'attribut severity du module est incohérent avec la sévérité de la règle
     */
    public void addModule( CheckstyleModuleBO pModule )
        throws MissingSeverityException, MismatchSeverityException
    {

        if ( pModule.getSeverity() != null )
        {

            if ( ( mSeverity == null ) )
            {// première module à être regroupée sous cette regle interne

                setSeverity( pModule.getSeverity() );
                pModule.setRule( this );

                mModules.add( pModule );

            }
            else
            {
                // on ne peut regrouper que des modules qui ont la même séverité sous un même code
                if ( mSeverity.equals( pModule.getSeverity() ) )
                {

                    setSeverity( pModule.getSeverity() );
                    pModule.setRule( this );
                    mModules.add( pModule );
                }
                else
                {
                    throw new MismatchSeverityException();
                }
            }

        }
        else
        {
            throw new MissingSeverityException();
        }
    }

    /**
     * Exception de sévérité manquante Levée lorsque l'attribut severity est absent
     */
    public class MissingSeverityException
        extends Exception
    {
    }

    /**
     * Exception d'incohérence de sévérité Levée lorsque la sévérité d'un module checkstyle est incompatible avec celle
     * de la règle checkstyle
     */
    public class MismatchSeverityException
        extends Exception
    {
    }
}
