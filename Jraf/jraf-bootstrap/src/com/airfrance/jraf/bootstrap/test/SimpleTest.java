/*
 * Créé le 27 mars 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.jraf.bootstrap.test;

import java.sql.Savepoint;

import org.springframework.context.ApplicationContext;

import com.airfrance.jraf.bootstrap.ApplicationContextFactoryInitializer;
import com.airfrance.jraf.bootstrap.initializer.Initializer;


/**
 * @author DIAKITE Tidiani
 * 
 * Cette classe permet d'éffectuer des tests en mode standalone.
 * Précisement, elle permet d'exécuter des exemples dans le cadre 
 * des tests sur des projets hors context serveur d'application.
 **/
public class SimpleTest {

	/**
	 * Constructeur par défaut.
	 */
	public SimpleTest() {
		super();
		// TODO Raccord de constructeur auto-généré
	}
	
	/**
	 * Méthode d'exécution principale.
	 * @param args
	 */
	public static void main(String[] args) {
		String root = "/home/linux/m312645/dinb_comp_java_jraf_v3_0_act_m312645/vobs/dinb_comp_java_jraf/dev/jraf-bootstrap";
	/*		Thread
				.currentThread()
				.getContextClassLoader()
				.getResource("")
				.getPath();*/		
		try {
			//String p = ApplicationContextFactoryHelper.getInstance().getRoot_path();
			//String q = ApplicationContextFactoryHelper.getInstance().getApp_context_file();
			String path = ApplicationContextFactoryHelper.getInstance().getClass().getResource("/").getPath();
			System.out.println(ApplicationContextFactoryHelper.getInstance().getClass().getResource("").getPath());
			//TestInitializerHelper.initFunctionalProviderLocator(p,q);
			ApplicationContextFactoryInitializer.init("application.xml");
			if(ApplicationContextFactoryInitializer.getApplicationContext() != null)
			{
			/*	String file = ((Initializer)ApplicationContextFactoryInitializer.getApplicationContext().getBean("initialize")).getConfigFile();
				System.out.println(path+file);
				TestInitializerHelper.initFunctionalProviderLocator(path, file);
			*/
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}	
	}
}
