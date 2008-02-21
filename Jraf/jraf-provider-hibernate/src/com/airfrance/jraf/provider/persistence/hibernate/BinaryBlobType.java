/*
 * Created on 18 août 06
 *
 */
package com.airfrance.jraf.provider.persistence.hibernate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.usertype.UserType;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;


/**
 * @author M401540
 *
 * Nouveau type Hibernate pour un Blob -> byte[]
 */
public class BinaryBlobType implements UserType {

    
    /** (non-Javadoc)
     * @see org.hibernate.usertype.UserType#sqlTypes()
     */
    public int[] sqlTypes() {
        return new int[] { Types.BLOB }; 
    }

    /** (non-Javadoc)
     * @see org.hibernate.usertype.UserType#returnedClass()
     */
    public Class returnedClass() {
        return byte[].class; 
    }

    /** (non-Javadoc)
     * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
     */
    public boolean equals(Object x, Object y) throws HibernateException {
        return (x == y) 
          || (x != null 
            && y != null 
            && java.util.Arrays.equals((byte[]) x, (byte[]) y)); 
    }

    /** (non-Javadoc)
     * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
     */
    public int hashCode(Object arg0) throws HibernateException {
        return arg0.hashCode();
    }

     /** (non-Javadoc)
     * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], java.lang.Object)
     */
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
        throws HibernateException, SQLException {
            Blob fromBlob = rs.getBlob(names[0]);
            if(fromBlob == null) {
            	return null;
            }
            // un appel à blob.getBytes(1, (int) blob.length());
            // ne marche pas sous Oracle, d'où l'utilisation du Stream 
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                return toByteArrayImpl(fromBlob, baos);
            } catch (IOException e) {
                // lever un exception
                throw new HibernateException(e);
            } finally {
                if (baos != null) {
                    try {
                        //fermeture du flux
                        baos.close();
                    } catch (IOException ex) {
                        // ne rien faire dans le finally ! 
                    }
                }
            }
    }

    /**
     * Utilitaire de conversion Blob -> byte[]. <br />
     * Obligatoire pour Oracle !
     * @param   fromBlob    Blob à lire
     * @param   baos        Byte array pour l'ecriture
     * @return  byte[]      correspondand au Blob
     * @throws SQLException en cas de pb sur le blob
     * @throws IOException  en cas de pb d'ecriture deans le Stream
     * 
     */
    private static byte[] toByteArrayImpl(Blob fromBlob, ByteArrayOutputStream baos) throws SQLException, IOException {
        final int MAXSIZE = 4000;
        byte[] buf = new byte[MAXSIZE];

        InputStream is = fromBlob.getBinaryStream();
        try {
            int dataSize;
            do {
                //lecture du flux
                dataSize = is.read(buf);
                if (dataSize != -1) {
                    //ecriture 
                    baos.write(buf, 0, dataSize);
                }
            } while (dataSize != -1);
        } finally {
            if (is != null) {
                try {
                    //fermeture du flux
                    is.close();
                } catch (IOException ex) {
                    // ne rien faire dans le finally ! 
                }
            }
        }
        return baos.toByteArray();
    }

    /** (non-Javadoc)
     * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int)
     */
    public void nullSafeSet(PreparedStatement st, Object value, int index) 
    throws HibernateException, SQLException { 
        if (value==null) {
            st.setNull(index, Types.BLOB);
        } else {           
            if (((SessionFactoryImplementor) ((PersistenceProviderImpl) PersistenceHelper.getPersistenceProvider()).getSessions()).getDialect().useInputStreamToInsertBlob()) {
                st.setBinaryStream( index, new ByteArrayInputStream((byte[]) value), ((byte [])value).length );            
            } else {
               st.setBlob(index, (Blob) Hibernate.createBlob((byte[]) value));
            }
        }
    } 


    /** (non-Javadoc)
     * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
     */
    public Object deepCopy(Object value) { 
      if (value == null) {
          return null;
      } 

      byte[] bytes = (byte[]) value; 
      byte[] result = new byte[bytes.length]; 
      System.arraycopy(bytes, 0, result, 0, bytes.length); 

      return result; 
    } 


    /** (non-Javadoc)
     * @see org.hibernate.usertype.UserType#isMutable()
     */
    public boolean isMutable() {
        return true;
    }

    /**
     * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, java.lang.Object)
     */

    public Object assemble(Serializable cached, Object owner) {
        return deepCopy(cached);
    }

    /**
     * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
     */

    public Serializable disassemble(Object value) {
        return (Serializable) deepCopy(value);
    }

    /**
     * @see org.hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object, java.lang.Object)
     */

    public Object replace(Object original, Object target, Object owner) {
        return deepCopy(original);
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
