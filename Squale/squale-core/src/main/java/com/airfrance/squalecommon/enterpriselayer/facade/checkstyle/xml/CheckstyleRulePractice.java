/*
 * Créé le 2 nov. 06
 *
 * Lors du parcours du fichier de configuration Checkstyle via le Digester,
 * mémorisation sur la balise "metadata" de la pratique associée à la règle
 */
package com.airfrance.squalecommon.enterpriselayer.facade.checkstyle.xml;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/** 
 * Mémorisation de la catégorie lors de l'instanciation
 * @param pAttributes attributs code et valeur
 * @return null
 * @throws Exception erreur de paramétrage
 */
public class CheckstyleRulePractice extends FactoryAdapter{
    /** catégorie de la règle */
    private String practice;
    
    /** Constructeur */
    public CheckstyleRulePractice() {
    }
                    
    /** Mémorisation de la catégorie lors de l'instanciation
     * @param pAttributes code et valeur de la balise metadata
     * @return null
     * @throws Exception balise metadata mal paramétrée
     */
    public Object createObject(Attributes pAttributes) throws Exception {
        String name = pAttributes.getValue("name");
        String value = pAttributes.getValue("value");
        
        //Test si le pattern metadata est en rapport avec le nom de la règle
        if(name.trim().equals(XmlCheckstyleParsingMessages.getString("checkstyle.rule.practice"))) {
            practice=value;
        }else{
            throw new Exception(XmlCheckstyleParsingMessages.getString("checkstyle.pattern.reserved",           
                                                                 new Object[]{"\n<module>\n\t" +
                                                                                "<module>\n\t\t" +
                                                                                "<metadata>\n"}));
        }                                     
        return null;
    }
            
    /**
     * @return category
     */
    public String getPractice() {
        return practice;
    }

}
