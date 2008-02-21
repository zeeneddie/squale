package com.airfrance.jraf.provider.persistence.hibernate.config;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.airfrance.jraf.commons.exception.JrafConfigException;

public class UrlSystemXmlHibernateConfigReader extends
		AbstractHibernateConfigReader {

	
	/** log */
	private static final Log log =
		LogFactory.getLog(UrlSystemXmlHibernateConfigReader.class);

	/**
	 * Constructeur IOC 2
	 *
	 */
	public UrlSystemXmlHibernateConfigReader() {
	}

	/**
	 * Constructeur IOC 3
	 * @param configFileName
	 */
	public UrlSystemXmlHibernateConfigReader(String configFileName) {
		setConfigFileName(configFileName);
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.provider.persistence.hibernate.config.IHibernateConfigReader#readConfig()
	 */
	public SessionFactory readConfig(Configuration configuration) throws JrafConfigException {
		URL lc_file = null;
		try {
			
			if (getConfigFileName() == null) {
				throw new JrafConfigException("Probleme de lecture du fichier de configuration : son chemin n'est pas renseigne.");
			}
			lc_file = new URL(getConfigFileName());
			return super.readConfig(lc_file, configuration);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JrafConfigException("Probleme de lecture du fichier de configuration : le chemin specifie n'est pas correct.",e);
		}

		//lc_file = new File("C:/Program Files/Apache Software Foundation/Tomcat 5.5/webapps/squaleWeb-3.3/WEB-INF/config/hibernate.cfg.xml");
		//lc_file = new File("/localhost/squaleWeb-3.3/WEB-INF/config/hibernate.cfg.xml");
		
	}

}
