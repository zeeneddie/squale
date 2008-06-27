package com.airfrance.squalecommon.enterpriselayer.businessobject.rule;

/**
 * Formule simple Une formule simple s'exprime sous la forme d'une expression qui donne une note sous la forme d'un
 * entier ou d'un flottant. Cette note doit appartenir à l'intervalle 0..3, elle est ajustée si besoin lors de la
 * collecte des résultats
 * 
 * @hibernate.subclass discriminator-value="SimpleFormula"
 */
public class SimpleFormulaBO
    extends AbstractFormulaBO
{
    /** Formule de calcul */
    private String mFormula;

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
     * @return formule
     * @hibernate.property name="formula" column="Formula" type="string" not-null="false" unique="false" length="4000" insert="true" update="true"
     */
    public String getFormula()
    {
        return mFormula;
    }

    /**
     * @param pFormula formule
     */
    public void setFormula( String pFormula )
    {
        mFormula = pFormula;
    }

    /**
     * @return le type de la formule
     */
    public String getType()
    {
        return TYPE_SIMPLE;
    }

}
