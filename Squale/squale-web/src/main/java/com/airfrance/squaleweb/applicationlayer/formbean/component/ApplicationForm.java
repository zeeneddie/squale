package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.airfrance.squaleweb.applicationlayer.formbean.ActionIdFormSelectable;

/**
 * Contient les données indispensables relatives à une application
 * 
 * @author M400842
 */
public class ApplicationForm
    extends ActionIdFormSelectable
{

    /** l'éventuelle justification associée au composant */
    private String justification;

    /** un booléen permettant de savoir si le composant est à exclure du plan d'aciton */
    private boolean excludedFromActionPlan;

    /** indique si le composant a des résultats */
    private boolean mHasResults;

    /** Date de la dernière modification */
    private Date mLastUpdate;

    /** L'utilisateur ayant fait la dernière modification */
    private String mLastUser;

    /**
     * @return true si le composant est exclu du plan d'action
     */
    public boolean getExcludedFromActionPlan()
    {
        return excludedFromActionPlan;
    }

    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest A implémenter sinon on ne peut pas décocher la checkBox
     * @param mapping le mapping
     * @param request la requête
     */
    public void reset( ActionMapping mapping, HttpServletRequest request )
    {
        super.reset( mapping, request );
        // Reinitialisation du checkbox
        excludedFromActionPlan = false;
    }

    /**
     * @return la justification du composant
     */
    public String getJustification()
    {
        return justification;
    }

    /**
     * @param pExcluded le booléen indiquant si il faut exclure le composant ou pas
     */
    public void setExcludedFromActionPlan( boolean pExcluded )
    {
        excludedFromActionPlan = pExcluded;
    }

    /**
     * @param pJustification la nouvelle valeur de la justification
     */
    public void setJustification( String pJustification )
    {
        justification = pJustification;
    }

    
    
    /**
     * Redefinition of the hashCode method
     * {@inheritDoc} 
     * @return return the hash number of the object
     */
    public int hashCode(){
        return super.hashCode();
    }

    
    
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @param obj l'objet à comparer
     * @return true si obj=this, false sinon
     */
    public boolean equals( Object obj )
    {
        boolean result = false;
        if ( obj instanceof ApplicationForm )
        {
            ApplicationForm compare = (ApplicationForm) obj;
            if ( null != this.getApplicationName() )
            {
                result = this.getApplicationName().equals( compare.getApplicationName() );
            }
        }
        return result;
    }
    
    

    /**
     * @return true si le composant a des résultats
     */
    public boolean getHasResults()
    {
        return mHasResults;
    }

    /**
     * @param pHasResults indique si le composant a des résultats
     */
    public void setHasResults( boolean pHasResults )
    {
        mHasResults = pHasResults;
    }

    /**
     * @return la date de la dernière modification
     */
    public Date getLastUpdate()
    {
        return mLastUpdate;
    }

    /**
     * @param pDate la date de la dernière modification
     */
    public void setLastUpdate( Date pDate )
    {
        mLastUpdate = pDate;
    }

    /**
     * @return l'utilisateur ayant fait la dernière modification
     */
    public String getLastUser()
    {
        return mLastUser;
    }

    /**
     * @param pMatricule l'utilisateur ayant fait la dernière modification
     */
    public void setLastUser( String pMatricule )
    {
        mLastUser = pMatricule;
    }

}
