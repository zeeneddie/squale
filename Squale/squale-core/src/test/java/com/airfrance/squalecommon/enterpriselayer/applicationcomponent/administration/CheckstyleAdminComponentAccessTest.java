package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.util.List;

import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.CheckstyleDTO;

/**
 * @author E6400802
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CheckstyleAdminComponentAccessTest extends SqualeTestCase {
    
    
    /**
     * Teste la construction de RulesChecking component par AccessDelegateHelpe
     *
     */
    public void testRulesCheckingAdminComponentAccess(){
        
        
        IApplicationComponent appComponent;
        try {
            appComponent = AccessDelegateHelper.getInstance("CheckstyleAdmin");
            assertNotNull(appComponent);
            
        } catch (Exception e) {
          
            e.printStackTrace();
            fail("unexpected exception");
        }
        
    }
    
    /**
     * Teste l'accès à la méthode GetAllVersions et son resultat
     *
     */
    public void testGetAllVersions(){
        
        IApplicationComponent appComponent;
        try {
            appComponent = AccessDelegateHelper.getInstance("CheckstyleAdmin");
            //appel à la methode getAllVersions
            List version=(List)appComponent.execute("getAllVersions", null);
            assertTrue("nb version",version.size()==0);
            
        } catch (Exception e) {
          
            e.printStackTrace();
            fail("unexpected exception");
        }        
      
    }
    /**
     * 
     * @throws JrafEnterpriseException
     */
    
    public void testParseFile() {
        IApplicationComponent appComponent;
        try {   
            appComponent = AccessDelegateHelper.getInstance("CheckstyleAdmin");
                      
            StringBuffer errors= new StringBuffer();
            Object[] paramIn = new Object[2];
            CheckstyleDTO version=new CheckstyleDTO();
            version.setBytes("n'import quoi".getBytes());
            paramIn[0] =version ;
            paramIn[1] =errors;
            //appel à la methode getAllVersions
            CheckstyleDTO result=(CheckstyleDTO)appComponent.execute("parseFile", paramIn);
            
            assertTrue("Errors",errors.length()>0);
            
        } catch (Exception e) {
          
                e.printStackTrace();
                fail("unexpected exception");
            }     
                 
    }

}
