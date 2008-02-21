package com.airfrance.squalecommon.datatransfertobject.config;

import java.util.List;

/**
 * Profile Squalix
 */
public class ProjectProfileDTO extends TasksUserDTO {

    /** true si l'export IDE est possible */
    private boolean mExportIDE;
    
    /** Les grilles disponibles pour ce profil */
    private List mGrids;

    /**
     * @return true si l'export IDE est possible
     */
    public boolean getExportIDE() {
        return mExportIDE;
    }

    /**
     * @param pExportIDE true si l'export IDE est possible
     */
    public void setExportIDE(boolean pExportIDE) {
        mExportIDE = pExportIDE;
    }

    /**
     * @return les grilles
     */
    public List getGrids() {
        return mGrids;
    }

    /**
     * @param pGrids les grilles
     */
    public void setGrids(List pGrids) {
        mGrids = pGrids;
    }

}
