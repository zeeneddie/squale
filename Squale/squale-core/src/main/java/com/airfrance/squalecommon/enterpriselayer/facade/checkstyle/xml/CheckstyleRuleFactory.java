
package com.airfrance.squalecommon.enterpriselayer.facade.checkstyle.xml;

import org.xml.sax.Attributes;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleSetBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de règle interne
 *
 *
 */
public class CheckstyleRuleFactory extends FactoryAdapter{
    
    /**
    *  CheckstyleRuleSets
    */   
   private CheckstyleRuleSetFactory mRuleSetFactory;
   /**
    * Pratique de la règle
    */
   private CheckstyleRulePractice mCheckstyleRuleCategory;         
        
    /**
     * Constructeur
     * @param pRuleSetFactory CheckstyleRuleSet Factory
     * @param pCheckstyleRulePractice pratique      
     */
   public CheckstyleRuleFactory(CheckstyleRuleSetFactory pRuleSetFactory, CheckstyleRulePractice pCheckstyleRulePractice) {
         
     mRuleSetFactory=pRuleSetFactory;
     mCheckstyleRuleCategory = pCheckstyleRulePractice;     
   } 
     
    /** (non-Javadoc)
     * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
     */
    public Object createObject(Attributes pAttributes) throws Exception {
        CheckstyleRuleBO rule=null;
        String name = pAttributes.getValue("name");
        String value = pAttributes.getValue("value");
         //on test si le pattern metadata est en rapport avec le nom de la règle      
        if(name.trim().equals(XmlCheckstyleParsingMessages.getString("checkstyle.rule.name"))){
            
            CheckstyleRuleSetBO ruleSet=mRuleSetFactory.getCheckstyleRuleSets();
            if(null!=ruleSet){
                // nouvelle règle
                rule=(CheckstyleRuleBO)ruleSet.getRules().get(value); 
                if(null==rule){
                    rule=new CheckstyleRuleBO();
                    // On stocke les valeurs de la règle
                    rule.setCategory(mCheckstyleRuleCategory.getPractice());                    
                    rule.setCode(value);
                    rule.setRuleSet(ruleSet);
                    ruleSet.getRules().put(value,rule);
                }
            
            }else{ // on n'a oublié la balise pour la vesion du fichier conf checkstyle 
                throw new Exception(XmlCheckstyleParsingMessages.getString("checkstyle.pattern.forgeted",
                                                                new Object[]{"\n<module>\n\t" +
                                                                   "<metadata>\n"})); 
            }
        }else{
            throw new Exception(XmlCheckstyleParsingMessages.getString("checkstyle.pattern.reserved",
            
                                                                     new Object[]{"\n<module>\n\t" +                                                                                    "<module>\n\t\t" +                                                                                    "<module>\n\t\t\t" +                                                                                    "<metadata>\n"}));
        }
             
       return rule;
    }  
}
