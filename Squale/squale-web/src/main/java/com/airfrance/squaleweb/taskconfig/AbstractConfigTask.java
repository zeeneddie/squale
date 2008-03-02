package com.airfrance.squaleweb.taskconfig;

import java.util.Collection;

/**
 * 
 */
public abstract class AbstractConfigTask
{

    /**
     * Nom de la tache
     */
    private String mTaskName;

    /**
     * Titre de la rubrique contenant les infos de configuration de la tache
     */
    private String mHelpKeyTask;

    /**
     * Collection des infos de configuration nécessaire pour effectuer la tache Cette collection contient des objets
     * InfoConfig
     */
    private Collection mInfoConfigTask;

    /**
     * @return La collection des infos de configuration nécessaire
     */
    public Collection getInfoConfigTask()
    {
        return mInfoConfigTask;
    }

    /**
     * @return Le tire de la rubrique
     */
    public String getHelpKeyTask()
    {
        return mHelpKeyTask;
    }

    /**
     * @param pInfoConfigTask Infos de configuration à insérer
     */
    public void setInfoConfigTask( Collection pInfoConfigTask )
    {
        mInfoConfigTask = pInfoConfigTask;
    }

    /**
     * @param pHelpKeyTask Titre à insérer
     */
    public void setHelpKeyTask( String pHelpKeyTask )
    {
        mHelpKeyTask = pHelpKeyTask;
    }

    /**
     * @return retourne le nom de la tache
     */
    public String getTaskName()
    {
        return mTaskName;
    }

    /**
     * @param pTaskName insére le nom de la tache
     */
    public void setTaskName( String pTaskName )
    {
        mTaskName = pTaskName;
    }

    /**
     * Tache abstraite qui servira à initialiser la liste des champs nécessaire dans la JSP pour pouvoir configurer la
     * tache
     */
    public abstract void init();

}
