package com.airfrance.squaleweb.applicationlayer.formbean.component.parameters;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Classe abstraite pour les paramétres d'un projet
 */
public abstract class AbstractParameterForm
    extends RootForm
{
    /** Indicate if it's the first time this task is setting */
    protected boolean newConf = true;

    /**
     * @return le transformer du bean
     */
    public abstract Class getTransformer();

    /**
     * @return les constantes des paramètres
     */
    public abstract String[] getParametersConstants();

    /**
     * @return si le bean n'est pas validé
     */
    public boolean isValid()
    {
        return ( null == getErrors() ) || getErrors().isEmpty();
    }

    /**
     * @return le nom du bean en session
     */
    public abstract String getNameInSession();

    /**
     * @return le nom de la tâche associée
     */
    public abstract String getTaskName();

    /**
     * @see com.airfrance.welcom.struts.bean.WActionForm#wValidate(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void wValidate( ActionMapping arg0, HttpServletRequest arg1 )
    {
        // On ajoute le nom de la tâche à la requête pour indiquer au dropPanel
        // qu'il faut qu'il soit ouvert
        arg1.setAttribute( "tool", getTaskName() );
        // On ne valide pas les formulaires si on déconfigure la tâche
        String action = (String) arg1.getParameter( "action" );
        // on teste donc sur l'action réalisée
        if ( null == action || !( "removeParameters" ).equals( action ) )
        {
            validateConf( arg0, arg1 );
        }
    }

    /**
     * Valide le formulaire
     * 
     * @param pMapping le mapping
     * @param pRequest la requête
     */
    protected abstract void validateConf( ActionMapping pMapping, HttpServletRequest pRequest );

    /**
     * @return true if it's the first time this task is setting
     */
    public boolean isNewConf()
    {
        return newConf;
    }

    /**
     * @param pNewConf indicate if it's the first time this task is setting
     */
    public void setNewConf( boolean pNewConf )
    {
        newConf = pNewConf;
    }

}
