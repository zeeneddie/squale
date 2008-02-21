package com.airfrance.squaleweb.util.graph;

import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.data.category.CategoryDataset;

/**
 */
public class RepartitionToolTipGenerator implements CategoryToolTipGenerator {

    /**
     * @see org.jfree.chart.labels.CategoryToolTipGenerator#generateToolTip(org.jfree.data.category.CategoryDataset, int, int)
     * {@inheritDoc}
     */
    public String generateToolTip(CategoryDataset pCategory, int pRowIndex, int pColumIndex) {
        String result = "";
        if(pRowIndex < AbstractRepartitionMaker.NB_SERIES_FOR_FLOAT_GRAPH-1){
            result="(" + pCategory.getRowKey(pRowIndex) + "," + pCategory.getRowKey(pRowIndex+1) + ")=" + pCategory.getValue(pRowIndex,pColumIndex)  ;
        }else{
            result="(" + pCategory.getRowKey(pRowIndex) + ",3.0" +  ")=" + pCategory.getValue(pRowIndex,pColumIndex)  ;
        }
        return result;
    }
}
