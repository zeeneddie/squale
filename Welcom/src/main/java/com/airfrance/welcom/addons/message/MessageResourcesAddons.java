package com.airfrance.welcom.addons.message;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.struts.message.WPropertyMessageResources;

/**
 * Inspired by code coming from Dirk Bartkowiak, MessageResources.java, Jul 21,
 * 2003
 */
public class MessageResourcesAddons extends WPropertyMessageResources implements
		java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5806590266875523596L;
	/** logger */
	private static Log log = LogFactory.getLog(MessageResourcesAddons.class);
	/** logger */
	private static Log logWelcom = LogFactory.getLog("Welcom");

	/** Les mes message en fonction de la locale */
	private final HashSet messageLocales = new HashSet();

	/** Liste des message en cache */
	private final HashMap messageCache = new HashMap();

	/** Liste des properties pas defaut */
	private final Properties defaultProperties = new Properties();

	/** Liste des properties de Welcom */
	private final Properties welcomProperties = new Properties();

	/**
	 * @param factory :
	 *            Factory du message ressources
	 * @param messageConfig :
	 *            Configuration information.
	 * @param returnNull :
	 *            retourne null
	 */
	public MessageResourcesAddons(final MessageResourcesFactoryAddons factory,
			final String messageConfig, final boolean returnNull) {
		super(factory, messageConfig, returnNull);
		initDefaultProperties(messageConfig);
		initWelcomProperties();
	}

	/**
	 * Constuctor
	 * 
	 * @param messageResourcesFactory
	 *            MessageResorcesFactory.
	 * @param messageConfig
	 *            Configuration information.
	 */
	public MessageResourcesAddons(
			final MessageResourcesFactoryAddons messageResourcesFactory,
			final String messageConfig) {
		super(messageResourcesFactory, messageConfig);
		initDefaultProperties(messageConfig);
		initWelcomProperties();
	}

	/**
	 * Inilise le default properties
	 * 
	 * @param messageConfig :
	 *            Fichier de resources
	 */
	private void initDefaultProperties(final String messageConfig) {
		String name = messageConfig.replace('.', '/');
		name += ".properties";
		InputStream is = null;

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		if (classLoader == null) {
			classLoader = this.getClass().getClassLoader();
		}
		is = classLoader.getResourceAsStream(name);
		if (is != null) {
			try {
				defaultProperties.load(is);
				logWelcom.info("Lecture du fichier : " + name + " ... OK");
			} catch (final IOException e) {
				logWelcom.info("Lecture du fichier : " + name + " ... ERROR");
				log.error(e, e);
			} finally {
				try {
					is.close();
				} catch (final IOException e) {
					log.error(e, e);
				}
			}
		}
	}

	/**
	 * Initialise du fichier de resources de Welcom
	 * 
	 */
	private void initWelcomProperties() {
		InputStream is = null;
		final String name = "com/airfrance/welcom/resources/DefaultMessages.properties";
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		if (classLoader == null) {
			classLoader = this.getClass().getClassLoader();
		}
		is = classLoader.getResourceAsStream(name);
		if (is != null) {
			try {
				defaultProperties.load(is);
				logWelcom.info("Lecture du fichier : " + name + " ... OK");
			} catch (final IOException e) {
				logWelcom.info("Lecture du fichier : " + name + " ... ERROR");
				log.error(e, e);
			} finally {
				try {
					is.close();
				} catch (final IOException e) {
					log.error(e, e);
				}
			}
		}
	}

	/**
	 * @return Les clef de l'application resources
	 */
	public Enumeration getDefaultPropertiesKeys() {
		return defaultProperties.keys();
	}

	/**
	 * @param key
	 *            la clef a rechercher
	 * @return les propriété par defaut
	 */
	public String getDefaultProperty(final String key) {
		return defaultProperties.getProperty(key);
	}

	/**
	 * Remet a zero les fichiers lors d'un resyncrhonisation
	 * 
	 */
	public synchronized void resetCache() {
		synchronized (messageLocales) {
			messageLocales.clear();
		}

		synchronized (messageCache) {
			messageCache.clear();
		}

		formats = new HashMap();
	}

	/**
	 * Met les locale en cache
	 * 
	 * @param locale :
	 *            la locale courante
	 */
	private synchronized void cacheLocale(final Locale locale) {
		// has some thread cached allready, while we are waiting for
		// for this method?
		if (messageLocales.contains(locale)) {
			return;
		}

		final String askedLocale = super.localeKey(locale);

		// ask Application for Messages associated to this locale
		final Vector Messages = WMessageResourcesControleur
				.getWMessageResourcesControleur().getMessagesByLanguage(
						askedLocale);

		for (int i = 0; i < Messages.size(); i++) {
			final Hashtable tmpHashtable = (Hashtable) Messages.elementAt(i);

			String value = (String) tmpHashtable.get("value");
			final String key = (String) tmpHashtable.get("key");

			if (value == null) {
				value = "";
			}

			// cache message
			messageCache.put(super.messageKey(locale, key), value);
		}

		// store info about cached locale
		messageLocales.add(locale);
	}

	/**
	 * Get the parent Locale.
	 * 
	 * e.g. en_US will return en
	 * 
	 * @param currentLocale :
	 *            Locale courante
	 * @return locale
	 * 
	 */
	private Locale getParentLocale(final Locale currentLocale) {
		Locale theReturnLocale = null;
		if (null == currentLocale) {
			theReturnLocale = null;
		} else if (!currentLocale.getVariant().equals("")) {
			theReturnLocale = new Locale(currentLocale.getLanguage(),
					currentLocale.getCountry());
		} else if (!currentLocale.getCountry().equals("")) {
			theReturnLocale = new Locale(currentLocale.getLanguage(), "");
		}

		return theReturnLocale;
	}

	/**
	 * @see org.apache.struts.util.MessageResources#getMessage(Locale, String)
	 */
	public String getMessage(final Locale currentLocale, final String askedKey) {
		String resultingMessage = null;
		Locale localeParent = currentLocale;

		if (GenericValidator.isBlankOrNull(askedKey)) {
			return askedKey;
		}

		try {
			do {
				if (!messageLocales.contains(localeParent)) {
					// cache parent
					cacheLocale(localeParent);
				}

				resultingMessage = (String) messageCache.get(super.messageKey(
						localeParent, askedKey));

				if (null == localeParent) {
					break;
				}

				localeParent = getParentLocale(localeParent);
			} while (resultingMessage == null);
		} catch (final Exception e) {
			log.error("Erreur a la recuperation en BD des messages ...", e);
		}

		if (resultingMessage == null) {
			// try to get default language
			resultingMessage = defaultProperties.getProperty(askedKey);

			if (resultingMessage == null) {
				// on a pas trouve la valeur par defaut, on regarde dans le
				// fichier DefaultMessage
				resultingMessage = welcomProperties.getProperty(askedKey);
				if (resultingMessage == null) {
					if (!returnNull) {
						resultingMessage = "???" + askedKey + "???";
					}
				}
			}
		}
		return resultingMessage;
	}

}