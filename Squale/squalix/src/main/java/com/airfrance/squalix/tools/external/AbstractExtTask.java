package com.airfrance.squalix.tools.external;

import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskException;



/**
 *
 * classe abstraite de base des taches externes
 * 
 */
public abstract class AbstractExtTask extends AbstractTask{
    
    public abstract void recup()throws TaskException;
}