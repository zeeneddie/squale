package com.airfrance.squalix.core;

/**
 * Abstract task specific for the source code termination task
 */
public abstract class AbstractSourceTerminationTask
    extends AbstractTask
{

    /**
     * Indicate if the optimization of the source code recovering is enabled
     * 
     * @return true if the task do the optimization
     */
    public abstract boolean doOptimization();

}
