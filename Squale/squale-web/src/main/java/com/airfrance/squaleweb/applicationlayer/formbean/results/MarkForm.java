package com.airfrance.squaleweb.applicationlayer.formbean.results;

import java.util.LinkedList;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Formulaire de réprésentation d'une note de pratique, avec la liste des composants ayant cette note.
 * 
 * @author M400842
 */
public class MarkForm
    extends RootForm
{

    /** Id de la pratique */
    private String mPracticeId;

    /** Id du facteur */
    private String mFactorId;

    /**
     * Nom de la pratique associée
     */
    private String mPracticeName = "";

    /**
     * Valeur de la note (qui correspond à l'index de répartition) dans le cas d'une note seule
     */
    private int mMarkValue;

    // Si on a un intervalle

    /** la note min de l'intervalle */
    private double mMinMark;

    /** la note max de l'intervalle */
    private double mMaxMark;

    /**
     * @return la note max
     */
    public double getMaxMark()
    {
        return mMaxMark;
    }

    /**
     * @return la note min
     */
    public double getMinMark()
    {
        return mMinMark;
    }

    /**
     * @param pMaxMark la note max
     */
    public void setMaxMark( double pMaxMark )
    {
        mMaxMark = pMaxMark;
    }

    /**
     * @param pMinMark la note min
     */
    public void setMinMark( double pMinMark )
    {
        mMinMark = pMinMark;
    }

    /**
     * Liste des noms des métriques
     */
    private List mTreNames = new LinkedList();

    /**
     * Liste des composants, avec leurs métriques
     */
    private List mComponents = new LinkedList();

    /**
     * Clé de facteur parent de la pratique
     */
    private String mFactorName = null;

    /**
     * @return la liste des composants
     */
    public List getComponents()
    {
        return mComponents;
    }

    /**
     * @return la valeur de la note
     */
    public int getMarkValue()
    {
        return mMarkValue;
    }

    /**
     * @return le nom de la pratique
     */
    public String getPracticeName()
    {
        return mPracticeName;
    }

    /**
     * @return la liste des noms des tre
     */
    public List getTreNames()
    {
        return mTreNames;
    }

    /**
     * @param pComponents la liste des composants
     */
    public void setComponents( List pComponents )
    {
        mComponents = pComponents;
    }

    /**
     * @param pMarkValue la valeur des notes
     */
    public void setMarkValue( int pMarkValue )
    {
        mMarkValue = pMarkValue;
    }

    /**
     * @param pPracticeName le nom de la pratique
     */
    public void setPracticeName( String pPracticeName )
    {
        mPracticeName = pPracticeName;
    }

    /**
     * @param pTreNames la liste des noms des tre
     */
    public void setTreNames( List pTreNames )
    {
        mTreNames = pTreNames;
    }

    /**
     * @return FactorName nom du facteur parent
     */
    public String getFactorName()
    {
        return mFactorName;
    }

    /**
     * @param pFactorName nom du facteur parent
     */
    public void setFactorName( String pFactorName )
    {
        mFactorName = pFactorName;
    }

    /**
     * @return id du facteur
     */
    public String getFactorId()
    {
        return mFactorId;
    }

    /**
     * @return id de la pratique
     */
    public String getPracticeId()
    {
        return mPracticeId;
    }

    /**
     * @param pString id du facteur
     */
    public void setFactorId( String pString )
    {
        mFactorId = pString;
    }

    /**
     * @param pString id de la pratique
     */
    public void setPracticeId( String pString )
    {
        mPracticeId = pString;
    }

}
