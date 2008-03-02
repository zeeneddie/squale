package com.airfrance.squaleweb.applicationlayer.formbean.results;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;
import com.airfrance.squaleweb.util.graph.GraphMaker;

/**
 * Contient une liste de résultats
 * 
 * @author M400842
 */
public class ResultListForm
    extends RootForm
{

    /**
     * Liste des résultats
     */
    private List mList = new ArrayList();

    /** le kiviat de niveau application */
    private GraphMaker kiviat;

    /** le pieChart de niveau application */
    private GraphMaker pieChart;

    /**
     * @return la liste des résultats
     */
    public List getList()
    {
        return mList;
    }

    /**
     * @param pList la liste des résultats
     */
    public void setList( List pList )
    {
        mList = pList;
    }

    /**
     * @return le kiviat
     */
    public GraphMaker getKiviat()
    {
        return kiviat;
    }

    /**
     * @return le pieChart
     */
    public GraphMaker getPieChart()
    {
        return pieChart;
    }

    /**
     * @param pKiviat le nouveau kiviat
     */
    public void setKiviat( GraphMaker pKiviat )
    {
        kiviat = pKiviat;
    }

    /**
     * @param pPieChart le nouveau pieChart
     */
    public void setPieChart( GraphMaker pPieChart )
    {
        pieChart = pPieChart;
    }

}
