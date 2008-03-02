package com.airfrance.squaleweb.applicationlayer.formbean.component;

/**
 * Données synthétiques d'un facteur
 */
public class FactorRuleForm
    extends QualityRuleForm
{

    /** L'id de l'objet */
    private long mId;

    /** Criteres */
    private CriteriaListForm mCriteria = new CriteriaListForm();

    /**
     * @return id
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @param pId id
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @return critères
     */
    public CriteriaListForm getCriteria()
    {
        return mCriteria;
    }

    /**
     * Ajout d'un critère
     * 
     * @param pCriterium critère
     */
    public void addCriterium( CriteriumRuleForm pCriterium )
    {
        mCriteria.getList().add( pCriterium );
    }

    /**
     * @param pCriteria modifie les critères
     */
    public void setCriteria( CriteriaListForm pCriteria )
    {
        mCriteria = pCriteria;
    }
}
