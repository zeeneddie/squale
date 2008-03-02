package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Bean pour la présentation d'un audit en échec
 */
public class ApplicationErrorForm
    extends RootForm
{

    /**
     * L'id de l'audit concerné
     */
    private long mAuditId;

    /**
     * Liste des ProjectErrorForm
     */
    private List mList = new ArrayList();

    /**
     * @return la liste des applications
     */
    public List getList()
    {
        return mList;
    }

    /**
     * @param pList la liste des applications
     */
    public void setList( List pList )
    {
        mList = pList;
    }

    /**
     * @return l'id de l'audit
     */
    public long getAuditId()
    {
        return mAuditId;
    }

    /**
     * @param pAuditId l'id de laudit
     */
    public void setAuditId( long pAuditId )
    {
        mAuditId = pAuditId;
    }

}
