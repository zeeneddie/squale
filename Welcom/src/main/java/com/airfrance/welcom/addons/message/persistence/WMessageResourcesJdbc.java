package com.airfrance.welcom.addons.message.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.welcom.addons.message.WIMessageResourcesPersistence;
import com.airfrance.welcom.outils.LocaleUtil;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.outils.jdbc.WJdbcMagic;

/**
 * Inspired by code coming from Dirk Bartkowiak
 */
public class WMessageResourcesJdbc implements WIMessageResourcesPersistence {

	/** logger */
	private static Log log = LogFactory.getLog(WMessageResourcesJdbc.class);

	/** non de la table des messages */
	private static final String TABLE_MSG_LIBELLE = WelcomConfigurator
			.getMessage(WelcomConfigurator.ADDONS_MESSAGE_MANAGER_TABLE_LIBELLES);

	/**
	 * executeQuery
	 * 
	 * simple Method to provide access to jdbc persistence submit a sql query
	 * and returns resultset as vector of String[]
	 * 
	 * @return resulset sous forme de vesteur
	 * @param sqlQuery
	 *            Requete sql
	 */
	public static Vector executeQuery(final String sqlQuery) {
		final Vector resultSet = new Vector();

		// get connection outside of try catch block
		// so we can close the connection safely in finally statement
		WJdbcMagic jdbc = null;
		try {
			jdbc = new WJdbcMagic();
			final Connection myConn = jdbc.getConnection();

			// construct sqlStatement and submit given query
			final Statement sqlStatement = myConn.createStatement();
			final ResultSet res = sqlStatement.executeQuery(sqlQuery);

			// how many columns in return, ask Metadata
			final ResultSetMetaData rmd = res.getMetaData();

			// only read metadata when selecting data
			if (sqlQuery.toLowerCase().startsWith("select")) {
				final int columnCount = rmd.getColumnCount();

				// loop through resultset
				while (res.next()) {
					final String tableRow[] = new String[columnCount];

					// loop through columns and store
					for (int i = 1; i <= columnCount; i++) {
						tableRow[i - 1] = res.getString(i);
					}

					// add row to resultSet
					resultSet.add(tableRow);
				}
			}

			// release jdbc resources
			sqlStatement.close();
			res.close();
		} catch (final Exception e) {
			log.error("Exception: " + e, e);
		} finally {
			// close connection
			if (jdbc != null) {
				jdbc.close();
			}
		}

		// return the complete resultset
		return resultSet;
	}

	/**
	 * Application Persistence needed methods /
	 * 
	 * /**
	 * 
	 * @see com.dbt.strutsMessage.persistence.InterFacePersistence#getAppMessagesByLocale(String)
	 * 
	 * ask database for given locale get all translations from database normally
	 * you would use persistence beans
	 */
	public Vector getAppMessagesByLocale(final String locale) {
		final Vector localeMessages = new Vector();

		final Vector translationResult = executeQuery("SELECT messageKey, message FROM "
				+ TABLE_MSG_LIBELLE + " WHERE locale = '" + locale + "'");

		// check for valid resultset
		if (translationResult.size() > 0) {
			for (int i = 0; i < translationResult.size(); i++) {
				final String dbMessage[] = (String[]) translationResult
						.elementAt(i);

				final Hashtable message = new Hashtable();
				message.put("key", dbMessage[0]);
				message.put("value", dbMessage[1]);

				localeMessages.addElement(message);
			}
		}

		return localeMessages;
	}

	/**
	 * get available locales from persistence
	 * 
	 * @return available locales from persistence
	 */
	public Vector getavailableLocales() {
		return new Vector(LocaleUtil.getAvailableLocales());
	}
}