/*
 * Créé le 1 août 05, par m400832.
 */
package com.airfrance.squalix.tools.compiling.java.adapter;

/**
 * Cette classe représente un "composant" de compilation JAVA, i.e. un parseur ou un compilateur. <br />
 * On utilise le pattern <a href=" http://labo-sun.com/index.jsp?actionId=32&docId=344&page=8" target="_new">Adapteur</a>.
 * 
 * @author m400832
 * @version 1.0
 */
public class JComponent
{

    /**
     * Adapteur des composants de compilation java.
     */
    private JComponentAdapter mComponentAdapter = null;

    /**
     * Constructeur.
     * 
     * @param pComponentAdapter un composant de compilation.
     */
    public JComponent( JComponentAdapter pComponentAdapter )
    {
        mComponentAdapter = pComponentAdapter;
    }

    /**
     * Getter.
     * 
     * @return un composant de compilation.
     */
    public JComponentAdapter getComponent()
    {
        return mComponentAdapter;
    }

}
