package com.airfrance.squaleweb.applicationlayer.formbean.results;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBO;
import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;
import com.airfrance.squaleweb.applicationlayer.formbean.information.PracticeInformationForm;

/**
 * Contient un résultat.
 * 
 * @author M400842
 */
public class ResultForm
    extends RootForm
{

    /** le bean décrivant la pratique */
    private PracticeInformationForm mInfoForm = new PracticeInformationForm();

    /**
     * Identificateur du résultat
     */
    private String mId;

    /**
     * le nom du type de résultat
     */
    private String mName = "";

    /** Note actuelle du facteur */
    private String mCurrentMark = "";

    /** Note précédante du facteur */
    private String mPredeccesorMark = "";

    /**
     * On indique le type de la formule car l'affichage des labels est différent selon le type de formule
     */
    private String mFormulaType;

    /**
     * Clé du tre parent
     */
    private String mTreParent = null;

    /**
     * Id du parent
     */
    private String mParentId = null;

    /**
     * La répartition, utilisée uniquement dans le cas d'une pratique Ce sont des valeurs entières, mais on définit un
     * tableau de double pour le graphique (qui prend obligatoirement en paramètre un tableau de double)
     */
    private double[] mIntRepartition = new double[QualityRuleBO.NUMBER_OF_MARKS];

    /**
     * La répartition, utilisée uniquement dans le cas d'une pratique Ce sont des valeurs entières, mais on définit un
     * tableau de double pour le graphique (qui prend obligatoirement en paramètre un tableau de double)
     */
    private double[] mFloatRepartition = new double[QualityRuleBO.NUMBER_OF_FLOAT_INTERVALS];

    /**
     * Constructeur par défaut
     */
    public ResultForm()
    {
        this( "-1" );
    }

    /**
     * Constructeur
     * 
     * @param pId l'id
     */
    public ResultForm( String pId )
    {
        mId = pId;
    }

    /**
     * @return le nom du type de résultat
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @return la note courante
     */
    public String getCurrentMark()
    {
        return mCurrentMark;
    }

    /**
     * @return la note courante
     */
    public String getPredecessorMark()
    {
        return mPredeccesorMark;
    }

    /**
     * @param pName le nom du type de résultat
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * @param pString la nouvelle note courante
     */
    public void setCurrentMark( String pString )
    {
        if ( pString != null )
        {
            mCurrentMark = pString;
        }
    }

    /**
     * @param pString la nouvelle note de l'audit précédant
     */
    public void setPredecessorMark( String pString )
    {
        if ( pString != null )
        {
            mPredeccesorMark = pString;
        }
    }

    /**
     * @return la répartition.
     */
    public double[] getFloatRepartition()
    {
        return mFloatRepartition;
    }

    /**
     * @return la répartition.
     */
    public double[] getIntRepartition()
    {
        return mIntRepartition;
    }

    /**
     * @param pRepartition la répartition des notes dans le cas d'une pratique.
     */
    public void setIntRepartition( int[] pRepartition )
    {
        for ( int i = 0; i < pRepartition.length; i++ )
        {
            mIntRepartition[i] = new Double( pRepartition[i] ).doubleValue();
        }

    }

    /**
     * @param pRepartition la répartition des notes dans le cas d'une pratique.
     */
    public void setFloatRepartition( int[] pRepartition )
    {
        for ( int i = 0; i < pRepartition.length; i++ )
        {
            mFloatRepartition[i] = new Double( pRepartition[i] ).doubleValue();
        }
    }

    /**
     * @param pRepartition la répartition des notes dans le cas d'une pratique.
     */
    public void setFloatRepartition( Integer[] pRepartition )
    {
        for ( int i = 0; i < mFloatRepartition.length; i++ )
        {
            if ( null != pRepartition[i] )
            {
                mFloatRepartition[i] = pRepartition[i].intValue();
            }
            else
            {
                mFloatRepartition[i] = 0;
            }
        }
    }

    /**
     * @param pRepartition la répartition des notes dans le cas d'une pratique.
     */
    public void setIntRepartition( Integer[] pRepartition )
    {
        for ( int i = 0; i < mIntRepartition.length; i++ )
        {
            if ( null != pRepartition[i] )
            {
                mIntRepartition[i] = pRepartition[i].intValue();
            }
            else
            {
                mIntRepartition[i] = 0;
            }
        }
    }

    /**
     * @return clé du tre parent
     */
    public String getTreParent()
    {
        return mTreParent;
    }

    /**
     * @param pTreParent clé du tre parent
     */
    public void setTreParent( String pTreParent )
    {
        mTreParent = pTreParent;
    }

    /**
     * @return id
     */
    public String getId()
    {
        return mId;
    }

    /**
     * @param pId id
     */
    public void setId( String pId )
    {
        mId = pId;
    }

    /**
     * @return id du parent
     */
    public String getParentId()
    {
        return mParentId;
    }

    /**
     * @param pString id du parent
     */
    public void setParentId( String pString )
    {
        mParentId = pString;
    }

    /**
     * @return si la formule utilisée pour calculer la pratique est de type condtionFormula
     */
    public String getFormulaType()
    {
        return mFormulaType;
    }

    /**
     * @param pFormulaType le type de la formule
     */
    public void setFormulaType( String pFormulaType )
    {
        mFormulaType = pFormulaType;
    }

    /**
     * On compare selon l'id qui est unique
     * 
     * @see java.lang.Object#equals(java.lang.Object) {@inheritDoc}
     */
    public boolean equals( Object obj )
    {
        boolean result = false;
        if ( obj instanceof ResultForm && obj != null )
        {
            ResultForm res = (ResultForm) obj;
            result = res.getId().equals( mId );
        }
        return result;
    }

    /**
     * @see java.lang.Object#hashCode() {@inheritDoc}
     */
    public int hashCode()
    {
        int result;
        if ( getId() != null )
        {
            result = getId().hashCode();
        }
        else
        {
            result = super.hashCode();
        }
        return result;
    }

    /**
     * @return la description de la pratique
     */
    public PracticeInformationForm getInfoForm()
    {
        return mInfoForm;
    }

    /**
     * @param pInfoForm la description de la pratique
     */
    public void setInfoForm( PracticeInformationForm pInfoForm )
    {
        mInfoForm = pInfoForm;
    }

}
