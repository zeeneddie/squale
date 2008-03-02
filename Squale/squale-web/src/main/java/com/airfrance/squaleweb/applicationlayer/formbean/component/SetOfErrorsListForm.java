package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Classe possédant l'ensemble des erreurs sous la forme de listes d'erreurs pour une tache donnée
 */
public class SetOfErrorsListForm
    extends RootForm
{

    /** La liste de listes d'erreurs par tache */
    private List listOfErrors = null;

    /** constructeur vide */
    public SetOfErrorsListForm()
    {
        listOfErrors = new ArrayList( 0 );
    }

    /**
     * @return la liste
     */
    public List getListOfErrors()
    {
        return listOfErrors;
    }

    /**
     * @param pListOfErrors la nouvelle liste
     */
    public void setSetOfErrors( List pListOfErrors )
    {
        listOfErrors = pListOfErrors;
    }

}
