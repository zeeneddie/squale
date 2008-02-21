package com.airfrance.welcom.outils.jdbc.wrapper;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * General purpose utility methods related to ResultSets
 *
 * @version $Revision: 1.1 $ $Date: 2001/08/11 $
 */
public class ResultSetUtils {
    /** logger */
    private static Log log = LogFactory.getLog(ResultSetUtils.class);
    

    /** Buffer */
    private static final int BUFFER_SIZE = 4096;

    /**
      * Populate the properties of the specified JavaBean from the next record
      * of the specified ResultSet, based on matching each column name against the
      * corresponding JavaBeans "property setter" methods in the bean's class.
      * Suitable conversion is done for argument types as described under
      * <code>convert()</code>.
      *
      * @param bean      The JavaBean whose properties are to be set
      * @param resultSet The ResultSet whose parameters are to be used
      *                  to populate bean properties
      *
      * @exception SQLException if an exception is thrown while setting
      *            property values or access the ResultSet
      */
    public static void populate(final Object bean, final ResultSet resultSet) throws SQLException {
        // Format pour les dates
        final SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        // Build a list of relevant column properties from this resultSet
        final HashMap properties = new HashMap();

        // Acquire resultSet MetaData
        final ResultSetMetaData metaData = resultSet.getMetaData();
        final int cols = metaData.getColumnCount();

        // Scroll to next record and pump into hashmap
        //if (resultSet.next()) 
        for (int i = 1; i <= cols; i++) {
            if (metaData.getColumnClassName(i).equals("java.sql.Timestamp")) {

                properties.put(metaData.getColumnName(i), getFormattedDateTimeColumn(resultSet.getTimestamp(i), formatDate));

            } else if (metaData.getColumnClassName(i).equals("oracle.sql.CLOB")) {
                
                properties.put(metaData.getColumnName(i), getFormattedClobColumn(resultSet.getAsciiStream(i)));

            } else {

                properties.put(metaData.getColumnName(i), getFormattedStringColumn(resultSet.getString(i)));
            }
        }

        // Set the corresponding properties of our bean
        try {
            populateIgnoreCase(bean, properties);
        } catch (final Exception e) {
            throw new SQLException("BeanUtils.populate threw " + e.toString());
        }
    }

    /**
     * Formate une colonne DateTime SQL pour le populate
     * @param ts : Valeur du Timestamp du resultSet
     * @param formatDate format de la date cf (@link SimpleDateFormat)
     * @return chaine formatté
     */
    private static String getFormattedDateTimeColumn(final Timestamp ts, final SimpleDateFormat formatDate) {
        String dt = "";
        if (ts != null) {
            dt = formatDate.format(new Date(ts.getTime()));
            if (dt == null) {
                dt = "";
            }
        }
        return dt;
    }

    /**
    * Formate une colonne Clob SQL pour le populate
    * @param is : Valeur du ASCIIStream du resultSet
    * @return chaine formatté
    */
    private static String getFormattedClobColumn(final InputStream is) {
       final byte b[] = new byte[BUFFER_SIZE];
       final BufferedInputStream bi = new BufferedInputStream(is);
       final StringBuffer sb = new StringBuffer();

       try {
           while ((bi.read(b)) != -1) {
               sb.append(new String(b));
           }
       } catch (final Exception e) {
           new SQLException("Erreur sur la lecture du CLOB");
       }
       return sb.toString();
    }

    /**
    * Formate une colonne String SQL pour le populate
    * @param st : Valeur du resultSet de la colonne 
    * @return chaine formatté
    */
    private static String getFormattedStringColumn(String st) {
        if (st == null) {
            st = "";
        }
        return st;
    }



    /**
     * Effectue un populate sans tenir compte de la case
     * @param bean : Bean A populer
     * @param properties : Liste des propertie a modifier
     * @throws IllegalAccessException : Probleme sur l'accés a l'attribut pas le getter
     * @throws InvocationTargetException : Probleme sur le populate
     */
    public static void populateIgnoreCase(final Object bean, final Map properties) throws IllegalAccessException, InvocationTargetException {
        try {
            final Hashtable realName = new Hashtable();

            // Recuperation de la table des correspondances
            final Map map = PropertyUtils.describe(bean);
            Iterator it = map.keySet().iterator();

            while (it.hasNext()) {
                final String element = (String) it.next();
                realName.put(element.toUpperCase(), element);
            }

            // Reaffecte les bons Noms
            final HashMap propertiesRealName = new HashMap();
            it = properties.keySet().iterator();

            while (it.hasNext()) {
                final String element = (String) it.next();

                if (realName.containsKey(element.toUpperCase())) {
                    propertiesRealName.put(realName.get(element.toUpperCase()), properties.get(element));
                }
            }

            BeanUtils.populate(bean, propertiesRealName);
        } catch (final IllegalAccessException e) {
            log.error(e,e);
            BeanUtils.populate(bean, properties);
        } catch (final InvocationTargetException e) {
            log.error(e,e);
            BeanUtils.populate(bean, properties);
        } catch (final NoSuchMethodException e) {
            log.error(e,e);
            BeanUtils.populate(bean, properties);
        }
    }

    /**
      * Populate the properties of the specified JavaBean from the next record
      * of the specified ResultSet, based on matching each column name against the
      * corresponding JavaBeans "property setter" methods in the bean's class.
      * Suitable conversion is done for argument types as described under
      * <code>convert()</code>.
      *
      * @param bean      The JavaBean whose properties are to be set
      * @param resultSet The ResultSet whose parameters are to be used
      *                  to populate bean properties
      * @param formatDate (@link SimpleDateFormat)
      *
      * @exception SQLException if an exception is thrown while setting
      *            property values or access the ResultSet
      */
    public static void populate(final Object bean, final ResultSet resultSet, final SimpleDateFormat formatDate) throws SQLException {
        // Build a list of relevant column properties from this resultSet
        final HashMap properties = new HashMap();

        // Acquire resultSet MetaData
        final ResultSetMetaData metaData = resultSet.getMetaData();
        final int cols = metaData.getColumnCount();

        // Scroll to next record and pump into hashmap
        //if (resultSet.next()) 
        for (int i = 1; i <= cols; i++) {
            if (metaData.getColumnClassName(i).equals("java.sql.Timestamp")) {
                if (resultSet.getTimestamp(i) != null) {
                    properties.put(metaData.getColumnName(i), formatDate.format(new Date(resultSet.getTimestamp(i).getTime())));
                } else {
                    properties.put(metaData.getColumnName(i), "");
                }
            } else {
                properties.put(metaData.getColumnName(i), resultSet.getString(i));
            }
        }

        // Set the corresponding properties of our bean
        try {
            populateIgnoreCase(bean, properties);
        } catch (final Exception e) {
            throw new SQLException("BeanUtils.populate threw " + e.toString());
        }
    }

    /**
      * @return Return a HashMap of the records in a resultSet as a contiguous list.
      *
      * @param resultSet The ResultSet whose parameters are to be used
      *                  to populate bean properties
      *
      * @exception SQLException if an exception is thrown while setting
      *            property values or access the ResultSet
      */
    public static HashMap toMap(final ResultSet resultSet) throws SQLException {
        // Build a list of relevant column properties from this resultSet
        final HashMap properties = new HashMap();

        // Acquire resultSet MetaData
        final ResultSetMetaData metaData = resultSet.getMetaData();
        final int cols = metaData.getColumnCount();

        // Scroll to next record and pump into hashmap
        while (resultSet.next()) {
            for (int i = 1; i <= cols; i++) {
                properties.put(metaData.getColumnName(i), resultSet.getString(i));
            }
        }

        return (properties);
    }

    /**
     * Effectue un poepulate en retournant directment une liste d'objets
     * @param c Classe de l'object a intancier
     * @param rs Resulset fournir par la query, effectue le test de nullité, 
     * retourne une liste vide dans ce cas
     * @return Liste d'object de la classe
     */
    public static Vector populateInVector(final Class c, final ResultSet rs) {
        final Vector vector = new Vector();

        try {
            if (rs != null) {
                while (rs.next()) {
                    final Object o = c.newInstance();
                    ResultSetUtils.populate(o, rs);
                    vector.add(o);
                }

                rs.close();
            }
        } catch (final Exception e) {
            System.err.println(e.getMessage());
        }

        return vector;
    }

}