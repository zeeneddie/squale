/*
 * Created on Oct 15, 2004
 */
package com.airfrance.jraf.provider.persistence.hibernate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.hibernate.LockMode;
import org.hibernate.Session;
import oracle.sql.BLOB;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.spi.persistence.ISession;

/**
 * <p>Title : BlobHelper.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class BlobHelper {

	/** logger */
	private static final Log log = LogFactory.getLog(BlobHelper.class);

	/**
	 * Creation d'un BLOB Oracle vide
	 * @param s session de persistance
	 * @param bo business object
	 * @param property nom de la propriete
	 * @return 
	 * @throws JrafPersistenceException
	 */
	private final static void createEmptyBlob(
		Session s,
		Object bo,
		String property)
		throws JrafPersistenceException {

		try {
			// on fixe le BLOB vide
			PropertyUtils.setProperty(
				bo,
				property,
				oracle.sql.BLOB.empty_lob());
			// on sauve le BO
			s.save(bo);
			s.flush();
			s.connection().commit();

		} catch (Exception e) {
			// on transforme l'exception
			log.error("Erreur lors de l'ecriture du BLOB vide", e);
			throw new JrafPersistenceException(
				"Erreur lors de l'ecriture du BLOB vide",
				e);
		}
	}

	public final static void writeByte2BLOB(
		ISession session,
		Object bo,
		String property,
		byte[] bytes)
		throws JrafPersistenceException {

		// recuperation de la session hibernate
		Session hSession = ((SessionImpl) session).getSession();

		try {
			// ecriture du BLOB vide
			createEmptyBlob(hSession, bo, property);

			// refresh de l'objet
			hSession.refresh(bo, LockMode.UPGRADE);

			// recuperation du BLOB
			BLOB oracleBLOB = (BLOB) PropertyUtils.getProperty(bo, property);

			// ouverture du stream d'ecriture sur le BLOB
			OutputStream blobOutputStream = oracleBLOB.getBinaryOutputStream();

			blobOutputStream.write(bytes, 0, bytes.length);
			blobOutputStream.flush();
			blobOutputStream.close();
		} catch (Exception e) {
			log.error(
				"Probleme lors de l'ecriture des donnees dans le BLOB",
				e);
			throw new JrafPersistenceException(
				"Probleme lors de l'ecriture des donnees dans le BLOB",
				e);
		}

	}


	/**
	 * Ecriture d'un fichier dans un BLOB
	 * @param session session de persistance
	 * @param bo business object
	 * @param property propriete
	 * @param file fichier
	 * @throws JrafPersistenceException
	 */
	public final static void writeFile2BLOB(
		ISession session,
		Object bo,
		String property,
		File file)
		throws JrafPersistenceException {

		// recuperation de la session hibernate
		Session hSession = ((SessionImpl) session).getSession();

		long fSize = file.length();
		byte[] buffer = null;
		
		try {
			// ecriture du BLOB vide
			createEmptyBlob(hSession, bo, property);

			// refresh de l'objet
			hSession.refresh(bo, LockMode.UPGRADE);

			// recuperation du BLOB
			BLOB oracleBLOB = (BLOB) PropertyUtils.getProperty(bo, property);

			// ouverture du stream d'ecriture sur le BLOB
			OutputStream blobOutputStream = oracleBLOB.getBinaryOutputStream();

			InputStream sampleFileStream = new FileInputStream(file);
			
			
			// cas tres gros fichier
			if (fSize > Integer.MAX_VALUE) {
				buffer = new byte[Integer.MAX_VALUE];
			} else {
				buffer = new byte[(int) file.length()];
			}

			int nread = 0;
			while ((nread = sampleFileStream.read(buffer)) != -1)
				blobOutputStream.write(buffer, 0, nread);
			sampleFileStream.close();
			blobOutputStream.flush();
			blobOutputStream.close();

		} catch (Exception e) {
			log.error(
				"Probleme lors de l'ecriture des donnees dans le BLOB",
				e);
			throw new JrafPersistenceException(
				"Probleme lors de l'ecriture des donnees dans le BLOB",
				e);
		}

	}
	
	public final static byte[] readByteFromBlob(
		ISession session,
		Object bo,
		String property)
		throws JrafPersistenceException {
			BLOB oracleBLOB=null;
			byte[] bytes=null;
			try {
				oracleBLOB = (BLOB) PropertyUtils.getProperty(bo, property);
				long bSize = oracleBLOB.length();
				// cas tres gros fichier
				if (bSize > Integer.MAX_VALUE) {
					bytes = new byte[Integer.MAX_VALUE];
				} else {
					bytes = new byte[(int) bSize];
				}
				long lon = 0;
				InputStream in = oracleBLOB.getBinaryStream();
				lon = in.read(bytes);
				in.close();

			} catch (Exception e) {
				log.error(
					"Probleme lors de la lecture des donnees d'un BLOB",
					e);
				throw new JrafPersistenceException(
					"Probleme lors de la lecture des donnees d'un BLOB",
					e);
			}
			return bytes;
		}
	
	/**
	 * Utilitaire pour transformer des fichiers en byte(]
	 * @param file Fichier a lire
	 * @return fichier sous forme de byte[]
	 * @throws JrafEnterpriseException dans le cas ou le fichier est trop gros ou fichier manquant.
	 */
	static public byte[] fileToByte(File file) throws JrafEnterpriseException {
		byte[] buffer = null;
		long fSize = file.length();

		// cas tres gros fichier
		if (fSize > Integer.MAX_VALUE) {
			throw new JrafEnterpriseException("File too long");
		} else {
			buffer = new byte[(int) fSize];
		}
		try {
			(new FileInputStream(file)).read(buffer);
		} catch (FileNotFoundException e) {
			throw new JrafEnterpriseException(e);
		} catch (IOException e) {
			throw new JrafEnterpriseException(e);
		} 

		return buffer;
    	
	}

}