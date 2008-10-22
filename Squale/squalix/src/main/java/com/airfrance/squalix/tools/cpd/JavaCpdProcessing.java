package com.airfrance.squalix.tools.cpd;


/**
 * Détection de copier/coller en java
 */
public class JavaCpdProcessing
    extends AbstractCpdProcessing
{
    /** Seuil de détection de copier/coller */
    private static final int JAVA_THRESHOLD = 100;

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.cpd.AbstractCpdTask#getTokenThreshold()
     */
    protected int getTokenThreshold()
    {
        return JAVA_THRESHOLD;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.cpd.AbstractCpdProcessing#getExtension()
     */
    protected String[] getExtensions()
    {
        return new String[] { ".java" };
    }

}
