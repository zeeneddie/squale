package com.airfrance.squaleweb.applicationlayer.formbean.search;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Bean pour la recherche d'un projet
 */
public class SearchProjectForm
    extends RootForm
{

    /** Commencement du nom de l'application associée au projet */
    private String mApplicationBeginningName;

    /** Commencement du nom du projet */
    private String mProjectBeginningName;

    /** Liste des projets trouvés associés à leur dernier audit */
    private Map mProjectForms; // De type ProjectForm -> auditForm

    /**
     * Constructeur par défaut
     */
    public SearchProjectForm()
    {
        mApplicationBeginningName = "";
        mProjectBeginningName = "";
        mProjectForms = null;
    }

    /**
     * @return le début du nom de l'application associée au projet
     */
    public String getApplicationBeginningName()
    {
        return mApplicationBeginningName;
    }

    /**
     * @return le début du nom du projet
     */
    public String getProjectBeginningName()
    {
        return mProjectBeginningName;
    }

    /**
     * @param pApplicationBeginningName le début du nom de l'application associée au projet
     */
    public void setApplicationBeginningName( String pApplicationBeginningName )
    {
        mApplicationBeginningName = pApplicationBeginningName;
    }

    /**
     * @param pProjectBeginningName le début du nom du projet
     */
    public void setProjectBeginningName( String pProjectBeginningName )
    {
        mProjectBeginningName = pProjectBeginningName;
    }

    /**
     * @return la liste des projets trouvés associés à leur dernier audit
     */
    public Map getProjectForms()
    {
        return mProjectForms;
    }

    /**
     * @param pProjectForms la liste des projets trouvés associés à leur dernier audit
     */
    public void setProjectForms( Map pProjectForms )
    {
        mProjectForms = pProjectForms;
    }

    /**
     * @return les projets
     */
    public Set getKeys()
    {
        return mProjectForms.keySet();
    }

    /**
     * @see com.airfrance.welcom.struts.bean.WActionForm#wValidate(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void wValidate( ActionMapping mapping, HttpServletRequest request )
    {
        // Suppression des espaces sur le nom de l'appli
        setApplicationBeginningName( getApplicationBeginningName().trim() );
        if ( !isAValidName( getApplicationBeginningName() ) && getApplicationBeginningName().length() > 0 )
        {
            addError( "applicationBeginningName", new ActionError( "error.name.containsInvalidCharacter" ) );
        }
        // Vérification du nom du projet
        setProjectBeginningName( getProjectBeginningName().trim() );
        if ( !isAValidName( getProjectBeginningName() ) && getProjectBeginningName().length() > 0 )
        {
            addError( "projectBeginningName", new ActionError( "error.name.containsInvalidCharacter" ) );
        }
    }
}
