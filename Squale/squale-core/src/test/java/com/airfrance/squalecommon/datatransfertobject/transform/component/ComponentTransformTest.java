package com.airfrance.squalecommon.datatransfertobject.transform.component;

import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentType;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;

/**
 * @author M400841
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ComponentTransformTest extends SqualeTestCase {

    /** Test non prévu */
    public void testDto2BoProxy(){
        /** TODO non prévu car pas d'update de composants en base */
    }

    /**
     * teste la transformation d'un componentBO en componentDTO
     * @throws Exception en cas d'échec de la transformation
     */
    public void testBo2Dto() throws Exception{
        ISession session = getSession();
        session.beginTransaction();
        // création du profil du projet
        ProjectProfileBO profileBO = getComponentFactory().createProjectProfile(session);
        // Test pour un projet
        ApplicationBO applicationBO = getComponentFactory().createApplication(session);
        // Test pour un projet
        ProjectBO projectBO = getComponentFactory().createProject(session,applicationBO,null);
        //il faut ajouter le profil au projet car la factory ne le fait pas
        projectBO.setProfile(profileBO);
        // Test pour un package
        PackageBO packageBO = getComponentFactory().createPackage(session,projectBO); 
        // Test pour une classe
        ClassBO classBO = getComponentFactory().createClass(session,packageBO); 
        // Test pour une methode
        AbstractComponentBO methodBO = getComponentFactory().createMethod(session,classBO); 
        session.commitTransactionWithoutClose();
        
        
         
        // Initialisation du retour
        ComponentDTO componentDTO = null;
        
        componentDTO = ComponentTransform.bo2Dto(methodBO);
        
        assertEquals(componentDTO.getName(), methodBO.getName());
        assertEquals(componentDTO.getType(), ComponentType.METHOD);
        assertEquals(componentDTO.getID(), methodBO.getId());
        
        componentDTO = ComponentTransform.bo2Dto(classBO);
        assertEquals(componentDTO.getName(), classBO.getName());
        assertEquals(componentDTO.getType(), ComponentType.CLASS);
        assertEquals(componentDTO.getID(), classBO.getId());

        componentDTO = ComponentTransform.bo2Dto(packageBO);
        assertEquals(componentDTO.getName(), packageBO.getName());
        assertEquals(componentDTO.getType(), ComponentType.PACKAGE);
        assertEquals(componentDTO.getID(), packageBO.getId());

        componentDTO = ComponentTransform.bo2Dto(projectBO);
        assertEquals(componentDTO.getName(), projectBO.getName());
        assertEquals(componentDTO.getType(), ComponentType.PROJECT);
        assertEquals(componentDTO.getID(), projectBO.getId());

        componentDTO = ComponentTransform.bo2Dto(applicationBO);
        assertEquals(componentDTO.getName(), applicationBO.getName());
        assertEquals(componentDTO.getType(), ComponentType.APPLICATION);
        assertEquals(componentDTO.getID(), applicationBO.getId());

        
    }

}
