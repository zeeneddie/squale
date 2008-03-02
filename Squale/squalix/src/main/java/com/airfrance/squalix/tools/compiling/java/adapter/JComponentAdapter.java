/*
 * Créé le 1 août 05, par m400832.
 */
package com.airfrance.squalix.tools.compiling.java.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Pattern <a href=" http://labo-sun.com/index.jsp?actionId=32&docId=344&page=8" target="_new">Adapteur</a>.
 * 
 * @author m400832
 * @version 1.0
 */
public abstract class JComponentAdapter
{

    /** la liste des erreurs */
    protected List mErrors = new ArrayList();

    /**
     * Méthode commune à tous les composants de compilation JAVA. Elle lance l'action sur ce composant (parsing,
     * compiling).
     * 
     * @throws Exception exception en cas d'erreur.
     */
    public abstract void execute()
        throws Exception;

    /**
     * @return la liste des erreurs
     */
    public List getErrors()
    {
        return mErrors;
    }

    /**
     * @param pList la liste des erreurs
     */
    public void setErrors( List pList )
    {
        mErrors = pList;
    }

}
