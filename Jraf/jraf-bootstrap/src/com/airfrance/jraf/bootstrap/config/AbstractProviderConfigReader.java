package com.airfrance.jraf.bootstrap.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.FactoryConfigurationError;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.commons.util.DTDEntityResolver;

/**
 * <p>Title : AbstractProviderConfigReader</p>
 * <p>Description : Classe permettant de lire le fichier de configuration des providers</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 */
public abstract class AbstractProviderConfigReader
	extends RuleSetBase
	implements IProviderConfigReader {

	/** logger */
	private static Log log =
		LogFactory.getLog(AbstractProviderConfigReader.class);

	/** dtd location */
	private static final String[] _dtdRegistration =
		{
			"-//Air France Software,Inc.//DTD Jraf Provider Configuration 2.0//EN",
			"com/airfrance/jraf/commons/resources/providers-config.dtd" };

	private String configFileName;

	/**
	 * Lis le fichier de configuration d'un provider
	 * @param in_fileName chemin du fichier de configuration 
	 * @return liste de configuration
	 */
	public List readConfig(InputStream in_stream) throws JrafConfigException {

		// liste de configuration
		List lc_config = new ArrayList();
		Digester lc_digester = new Digester();
		lc_digester.setEntityResolver(new DTDEntityResolver(_dtdRegistration));
		lc_digester.addRuleSet(this);
		lc_digester.push(lc_config);
		try {
			lc_digester.parse(in_stream);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new JrafConfigException(
				"Impossible de lire le fichier de configuration",
				ioe);
		} catch (SAXException saxe) {
			saxe.printStackTrace();
			throw new JrafConfigException(
				"Impossible de parser le fichier de configuration",
				saxe);
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
			throw new JrafConfigException(
				"Impossible de charger le parser xml",
				e);
			
		} finally {
			if (in_stream != null) {
				try {
					in_stream.close();
					lc_digester.clear();
				} catch (IOException ie) {
					// nothing to do	
				}
			}
		}
		// retourne la liste de configuration
		return lc_config;
	}

	/**
	 * Digester configuration
	 * @param in_digester digester
	 */
	public void addRuleInstances(Digester in_digester) {
		// creation d'une configuration d'un provider
		in_digester.addObjectCreate("providers/provider", ProviderConfig.class);
		// fixe les proprietes
		in_digester.addSetProperties("providers/provider", "id", "id");
		in_digester.addSetProperties(
			"providers/provider",
			"class",
			"className");
		in_digester.addCallMethod(
			"providers/provider/init-param",
			"putProperty",
			2);
		in_digester.addCallParam("providers/provider/init-param/param-name", 0);
		in_digester.addCallParam(
			"providers/provider/init-param/param-value",
			1);
		in_digester.addBeanPropertySetter(
			"providers/provider/load-on-startup",
			"loadOnStartup");

		// passe au provider suivant
		in_digester.addSetNext(
			"providers/provider",
			"add",
			ProviderConfig.class.getName());
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.bootstrap.config.IProviderConfigReader#readConfig()
	 */
	public abstract List readConfig() throws JrafConfigException;

	/**
	 * Retourne le chemin du fichier de configuration
	 * @return chemin du fichier de configuration
	 */
	public String getConfigFileName() {
		return configFileName;
	}

	/**
	 * Fixe le chemin du fichier de configuration
	 * @param string chemin du fichier de configuration
	 */
	public void setConfigFileName(String string) {
		configFileName = string;
	}
}
