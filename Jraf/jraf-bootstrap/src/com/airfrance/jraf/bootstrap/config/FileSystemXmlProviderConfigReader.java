/*
 * Created on Dec 29, 2004
 */
package com.airfrance.jraf.bootstrap.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafConfigException;

/**
 * <p>Title : FileSystemXmlProviderConfigReader.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class FileSystemXmlProviderConfigReader
	extends AbstractProviderConfigReader {

	/** logger */
	private static Log log =
		LogFactory.getLog(FileSystemXmlProviderConfigReader.class);

	/**
	 * Constructeur vide type IOC 2
	 */
	public FileSystemXmlProviderConfigReader() {
	}

	/**
	 * Constructeur avec parametre type IOC 3
	 * @param configFileName
	 */
	public FileSystemXmlProviderConfigReader(String configFileName) {
		setConfigFileName(configFileName);
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.bootstrap.config.IProviderConfigReader#readConfig()
	 */
	public List readConfig() throws JrafConfigException {

		if (getConfigFileName() == null) {
			String message =
				"Probleme de configuration: le fichier de configuration n'est pas renseigne";
			log.fatal(message);
			throw new JrafConfigException(message);
		}

		InputStream lc_stream = null;

		try {
			lc_stream = new FileInputStream(new File(getConfigFileName()));
			return super.readConfig(lc_stream);
		} catch (FileNotFoundException e) {
			try {
				log.fatal(
					"Le fichier '"
						+ getConfigFileName()
						+ "' est introuvable a l'emplacement '"
						+ new File(".").toURL().toExternalForm()
						+ "'",
					e);

				throw new JrafConfigException(
					"Le fichier '"
						+ getConfigFileName()
						+ "' est introuvable a l'emplacement '"
						+ new File(".").toURL().toExternalForm()
						+ "'",
					e);
			} catch (MalformedURLException urlException) {
				throw new JrafConfigException(urlException.getMessage());
			}
		}

	}

}
