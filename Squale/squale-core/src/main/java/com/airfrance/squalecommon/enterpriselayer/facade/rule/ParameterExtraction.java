package com.airfrance.squalecommon.enterpriselayer.facade.rule;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.AbstractFormulaBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.ConditionFormulaBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FormulaVisitor;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.SimpleFormulaBO;

/**
 * Extraction des paramètres d'une formule Une formule est composée de paramètres avec une syntaxe du type xxx.yyy où
 * xxx est le type de mesure et yyy l'attribut de cette mesure (par exemple mccabe.sumvg)
 */
public class ParameterExtraction
    implements FormulaVisitor
{
    /**
     * Obtention des paramètres d'une formule
     * 
     * @param pFormula formule
     * @return liste des paramètres intervenant dans la formule
     */
    public Collection getFormulaParameters( AbstractFormulaBO pFormula )
    {
        Set result = new HashSet();
        if ( pFormula != null )
        {
            // Extraction des paramètres du trigger
            if ( pFormula.getTriggerCondition() != null )
            {
                extractParameters( pFormula.getMeasureKinds(), pFormula.getTriggerCondition(), result );
            }
            // Extraction des paramètres pour une condition de type
            pFormula.accept( this, result );
        }
        return result;
    }

    /**
     * Extraction des paramètres d'une expression
     * 
     * @param pMeasureKinds types de mesures
     * @param pCondition condition
     * @param pParameters collectiond es paramètres
     */
    private void extractParameters( Collection pMeasureKinds, String pCondition, Collection pParameters )
    {
        StringBuffer buf = new StringBuffer();
        buf.append( "(" );
        Iterator measureKinds = pMeasureKinds.iterator();
        int i = 0;
        while ( measureKinds.hasNext() )
        {
            if ( i > 0 )
            {
                buf.append( '|' );
            }
            buf.append( (String) measureKinds.next() );
            i++;
        }
        // Un paramètre est du type xxx.attribut et pas xxx.methode(
        buf.append( ").([a-zA-Z0-9]+)([^a-zA-Z0-9(]|$)" );
        Pattern pattern = Pattern.compile( buf.toString() );
        Matcher matcher = pattern.matcher( pCondition );
        // On cherche toutes les occurrences de xxx.yyy
        // dans la chaîne de caractères passée en paramètre
        // xxx étant un type de mesure et yyy un attribut de cette mesure
        while ( matcher.find() )
        {
            FormulaParameter parameter = new FormulaParameter();
            parameter.setMeasureKind( matcher.group( 1 ) );
            parameter.setMeasureAttribute( matcher.group( 2 ) );
            pParameters.add( parameter );
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.practice.FormulaVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.practice.ConditionFormulaBO,
     *      java.lang.Object)
     */
    public Object visit( ConditionFormulaBO pConditionFormula, Object pArgument )
    {
        Iterator conditions = pConditionFormula.getMarkConditions().iterator();
        while ( conditions.hasNext() )
        {
            String condition = (String) conditions.next();
            extractParameters( pConditionFormula.getMeasureKinds(), condition, (Collection) pArgument );
        }
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.rule.practice.FormulaVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.rule.practice.SimpleFormulaBO,
     *      java.lang.Object)
     */
    public Object visit( SimpleFormulaBO pSimpleFormula, Object pArgument )
    {
        extractParameters( pSimpleFormula.getMeasureKinds(), pSimpleFormula.getFormula(), (Collection) pArgument );
        return null;
    }

}
