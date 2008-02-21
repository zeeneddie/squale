package com.airfrance.squaleweb.util.graph;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;

import org.jfree.data.category.CategoryDataset;

/**
 */
public abstract class AbstractStatsMaker extends AbstractGraphMaker {

    /**
     * @return la hauteur par défaut
     */
    public int getDefaultHeight() {
        return HEIGHT;
    }
    /**
     * @return la largeur par défaut
     */
    public int getDefaultWidth() {
        return WIDTH;
    }

    /** la hauteur du graph */
    private final static int HEIGHT = 400;

    /** la largeur du graph */
    private final static int WIDTH = 980;

    /** les données */
    protected CategoryDataset mDataSet;

    /** La couleur bleue */
    private final static Paint BLUE = new GradientPaint(0.0f, 0.0f, Color.BLUE, 0.0f, 0.0f, Color.BLUE);
    
    /** La couleur bleue */
    private final static Paint CYAN = new GradientPaint(0.0f, 0.0f, Color.CYAN, 0.0f, 0.0f, Color.CYAN);
    
    /** La couleur bleue */
    private final static Paint ORANGE = new GradientPaint(0.0f, 0.0f, Color.ORANGE, 0.0f, 0.0f, Color.ORANGE);
    
    /** La couleur bleue */
    private final static Paint GRAY = new GradientPaint(0.0f, 0.0f, Color.GRAY, 0.0f, 0.0f, Color.GRAY);
    
    /** le tableau regroupant les couleurs*/
    final static protected Paint[] COLORS = new Paint[] { BLUE, CYAN, ORANGE, GRAY };

}
