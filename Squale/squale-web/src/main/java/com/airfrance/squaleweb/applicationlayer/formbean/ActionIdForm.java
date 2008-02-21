package com.airfrance.squaleweb.applicationlayer.formbean;


/**
 * Formulaire simple doté d'un id
 */
public abstract class ActionIdForm extends RootForm {
    /**
     * @param pId l'id.
     */
    public void setId(long pId) {
        mId = pId;
    }
    /**
     * @return l'id.
     */
    public long getId() {
        return mId;
    }
    /**
     * Id de l'audit.
     */
    protected long mId;

}
