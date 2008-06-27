package com.airfrance.squalecommon.enterpriselayer.businessobject.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Formule conditionnelle La formule est basée sur un ensemble de conditions évaluées en cascade pour attribuer une
 * note. Chaque note est attribuée si la condition est satisfaite en évaluant les conditions dans l'ordre croissant.
 * Ainsi la note 0 est attribuée si la première condition est satisfaite et ainsi de suite. La meilleure note étant
 * attribuée si aucune des conditions n'est satisfaite.
 * 
 * @hibernate.subclass discriminator-value="ConditionFormula"
 */
public class ConditionFormulaBO
    extends AbstractFormulaBO
    implements Serializable
{
    /** Condition associée à chaque note */
    private List mMarkConditions = new ArrayList();

    /**
     * @return conditions
     * @hibernate.list table="Formula_Conditions" cascade="none" lazy="false"
     * @hibernate.key column="FormulaId"
     * @hibernate.index column="Rank" type="long" length="19"
     * @hibernate.element column="Value" type="string" length="4000" not-null="false" unique="false"
     */
    public List getMarkConditions()
    {
        return mMarkConditions;
    }

    /**
     * @param pStrings conditions
     */
    public void setMarkConditions( List pStrings )
    {
        mMarkConditions = pStrings;
    }

    /**
     * @param pMarkCondition condition
     */
    public void addMarkCondition( String pMarkCondition )
    {
        mMarkConditions.add( pMarkCondition );
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.practice.AbstractFormulaBO#accept(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.practice.FormulaVisitor,
     *      java.lang.Object)
     */
    public Object accept( FormulaVisitor pVisitor, Object pArgument )
    {
        return pVisitor.visit( this, pArgument );
    }

    /**
     * @return le type de la formule
     */
    public String getType()
    {
        return TYPE_CONDITION;
    }

}
