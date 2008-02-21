
package com.airfrance.squalecommon.enterpriselayer.facade.checkstyle.xml;

import org.xml.sax.Attributes;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleModuleBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de Module checstyle
 */
public class CheckstyleModuleFactory extends FactoryAdapter {
   
     /**
      * Constructeur
      *
      */
    public CheckstyleModuleFactory() {
        
     } 
  
    /** (non-Javadoc)
     * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
     */
    public Object createObject(Attributes pAttributes) {
        CheckstyleModuleBO module=null;
        String name = pAttributes.getValue("name");
          
        if(null!=name){
            module=new CheckstyleModuleBO();
            module.setName(name.trim());
           
        }
        return module;
    }  
}
