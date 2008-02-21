package com.airfrance.jraf.testenv.bd;


import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Project: JRAF 
 * <p>Module: testUnitaireJrafJava
 * <p>Title : BdTool.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 *  Création et suppression d'une BD: Utilise le mapping fourni
 *  par le fichier hibernate.cfg.xml
 */

public class BdTool {

	/** logger */
	private final static Log log = LogFactory.getLog(BdTool.class);

	/** create action */
	final static int CREATE = 1;
	/** drop action */
	final static int DROP = 2;
	/** drop + create action */
	final static int ALL = 3;
	/** generate text action */
	final static int TEXT = 4;

	/**
	 * configuration
	 */
	private Configuration cf = null;
	private String configurationFile = null;
	private String outputFile = null;

	private static SessionFactory sessions = null;
	private static SchemaExport sExport = null;

	/**
	 * Creation de la base de données (Table, indexes, sequences etc.)
	 */
	public void createDB() throws Exception {
		try {
			if (sExport != null)
				sExport.create(false, true);

		} catch (Exception e) {
			log.fatal(e);
			throw e;
		}
	}

	/**
	 * Creation de la base de données (Table, indexes, sequences etc.)
	 */
	public void textDB() throws Exception {
		try {
			if (sExport != null) {
				sExport.setDelimiter(";");
				sExport.setFormat(true);
				if(getOutputFile()!=null) sExport.setOutputFile(getOutputFile());
				sExport.create(true, false);
			}
		} catch (Exception e) {
			log.fatal(e);
			throw e;
		}
	}

	/**
	 * Supprimer la base de données (tables indexes sequences etc.)
	 */
	public void dropDB() throws Exception {
		try {
			if (sExport != null)
				sExport.drop(false, true);
		} catch (Exception e) {
			log.fatal(e);
			throw e;
		}
	}

	protected InputStream getConfigurationInputStream(String resource)
		throws HibernateException {

		log.info("Configuration resource1: " + resource);
		log.info(
			"Configuration resource2: "
				+ Environment.class.getResource("/config-db2").getPath());
		log.info(
			"Configuration resource3: "
				+ Environment.class.getResourceAsStream("/" + resource));
		InputStream stream =
			Environment.class.getResourceAsStream("/" + resource);
		if (stream == null) {
			log.error(resource + " not found");
		}
		return stream;

	}

	/**
	 * Initialisation de la connexion en prévision des traitements
	 * sur la base.
	 */
	public void initSExport() throws Exception {
		try {

			if (getConfigurationFile() == null) {
				throw new RuntimeException("Le fichier de configuration est null.");
			}
			File f = new File(getConfigurationFile());
			log.info(f);
			if (f.exists()) {
				log.info("Configuration  a partir d'un fichier...");
				cf = new Configuration().configure(getConfigurationFile());
			} else {
				log.info("Configuration  a partir d'une url...");
				URL url = this.getClass().getResource(getConfigurationFile());
				cf = new Configuration().configure(url);
			}

			log.info("Configuration effectuee.");
			log.info("Instanciation d'un schema export.");
			sExport = new SchemaExport(cf);
			log.info("schema export instancie.");

		} catch (Exception e) {
			log.fatal(e);
			throw e;
		}
	}

	/**
	 * @param args: Liste des parametres obligatoires
	 * <li>--create: Creation de la base</li>
	 * <li>--drop: Suppression de la base</li>
	 * <li>--config: Chemin d'acces au fichier de configuration hibernate.cfg.xml</li>
	 * <br><b>usage: BdTool [--create|--drop] --config <fileName></b><br>
	 * @return le type de commande a executer (create|drop)
	 */
	public int initMain(String[] args) throws Exception {
		int cmd = -1;

		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("--")) {
				if (args[i].equals("--create")) {
					cmd = BdTool.CREATE;
				} else if (args[i].equals("--drop")) {
					cmd = BdTool.DROP;
				} else if (args[i].equals("--text")) {
					cmd = BdTool.TEXT;
				} else if (args[i].equals("--all")) {
					cmd = BdTool.ALL;
				} else if (args[i].equals("--config")) {
					setConfigurationFile(args[++i]);
				} else if (args[i].equals("--output")) {
					setOutputFile(args[++i]);
				}

			}
		}
		try {
			initSExport();

		} catch (Exception e) {
			log.fatal(e);
			throw e;
		}
		return cmd;
	}
	/**
	 * @return
	 */
	public String getConfigurationFile() {
		return configurationFile;
	}

	/**
	 * @param string
	 */
	public void setConfigurationFile(String string) {
		configurationFile = string;
	}

	public static void main(String[] args) {
		boolean drop = false;
		boolean create = false;
		int cmd = 0;

		if (args.length == 0) {
			args = new String[3];
			args[0] = "--all";
			args[1] = "--config";
			args[2] = "/config/config-comor-default/hibernate.cfg.xml";
		}

		BdTool cnx = new BdTool();
		try {
			cmd = cnx.initMain(args);
			switch (cmd) {
				case BdTool.CREATE :
					log.info("Creation en cours...");
					cnx.createDB();
					log.info("Creation effectuee");
					break;
				case BdTool.TEXT :
					log.info("Creation en cours...");
					cnx.textDB();
					log.info("Creation effectuee");
					break;
				case BdTool.DROP :
					log.info("Drop en cours...");
					cnx.dropDB();
					log.info("Drop effectuee");
					break;
				case BdTool.ALL :
					log.info("drop en cours");
					cnx.dropDB();
					log.info("Drop effectuee");
					log.info("creation en cours");
					cnx.createDB();
					log.info("Creation effectuee");
					break;

				default :
					System.out.println(
						"usage: BdTool  --[create|drop|all] --config <filename>");
			}

		} catch (Exception e) {
			log.fatal(e);
		}
	}
	/**
	 * @return
	 */
	public String getOutputFile() {
		return outputFile;
	}

	/**
	 * @param string
	 */
	public void setOutputFile(String string) {
		outputFile = string;
	}

}
