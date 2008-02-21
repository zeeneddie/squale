/*
 * Created on Dec 28, 2004
 */
package com.airfrance.jraf.provider.accessdelegate.config;

import java.io.InputStream;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafConfigException;

/**
 * <p>Title : ClassPathXmlApplicationComponentConfigReader.java</p>
 * <p>Description : Lecteur de fichier de configuration XML a partir du classpath.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ClassPathXmlApplicationComponentConfigReader
	extends AbstractApplicationComponentConfigReader {

	/** logger */
	private static final Log log =
		LogFactory.getLog(ClassPathXmlApplicationComponentConfigReader.class);

	/**
	 * Constructeur IOC type 2
	 */
	public ClassPathXmlApplicationComponentConfigReader() {
	}

	/**
	 * Constructeur IOC type 3
	 */
	public ClassPathXmlApplicationComponentConfigReader(String fileName) {
		setConfigFileName(fileName);
	}

	/**
	 * Lis le fichier de configuration d'un plugin
	 * @param in_fileName chemin du fichier de configuration 
	 */
	public Map readConfig() throws JrafConfigException {

		InputStream lc_stream = null;

		try {
			lc_stream =
				Thread
					.currentThread()
					.getContextClassLoader()
					.getResourceAsStream(
					getConfigFileName());

			if (lc_stream == null)
				throw new JrafConfigException(
					"Le fichier '"
						+ getConfigFileName()
						+ "' est introuvable dans le classpath");

			return super.readConfig(lc_stream);
		} catch (Exception e) {
			log.fatal(
				"Le fichier '"
					+ getConfigFileName()
					+ "' est introuvable dans le classpath",
				e);
			throw new JrafConfigException(
				"Le fichier specifie est introuvable",
				e);
		}

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.provider.accessdelegate.config.AbstractApplicationComponentConfigReader#setConfigFileName(java.lang.String)
	 */
	public void setConfigFileName(String string) {
		String path = string;
		if (path != null && path.startsWith("/")) {
			path = path.substring(1);
		}
		super.setConfigFileName(path);
	}

}
