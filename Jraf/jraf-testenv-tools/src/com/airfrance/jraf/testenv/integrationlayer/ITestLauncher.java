/*
 * Created on Jul 23, 2004
 */
package com.airfrance.jraf.testenv.integrationlayer;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;

/**
 * <p>Title : ITestLauncher.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public interface ITestLauncher  {
	
	/** marque le nom du projet pour le build ant */	
	public final static String TYPE_EXT = "type.ext";

	/** marque l'emplacement de la vue clearcase des projets pour le build ant */	
	public final static String CC_PROJECT_DIR = "cc.project.dir";
	
	
	/**
	 * Lance l'execution des tests
	 * @throws JrafEnterpriseException
	 */
	public abstract void execute() throws JrafEnterpriseException;

}