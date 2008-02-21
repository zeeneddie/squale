/*
 * Créé le 1 sept. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.pdf.stylereport;

import inetsoft.report.lens.DefaultTableLens;
import inetsoft.report.style.TableStyle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

import com.airfrance.welcom.outils.Util;

/**
 * @author M327836
 *
 */
public class PrintableTable {
    /** logger */
    private static Log log = LogFactory.getLog(PrintableTable.class);

    
    /** Table a imprimer */
    private Collection table;

    /** Liste des entete de colonne */
    private Vector header;

    /** Messages */
    protected MessageResources resources = null;

    /** Locale */
    protected Locale localeRequest = Locale.FRENCH;

    /**
     * Constructeur
     */
    public PrintableTable() {
        header = new Vector();
    }

    /**
     * Contruit une table af pour le pdf
     * @param res : Messageresource
     * @param loc : Locale
     */
    public PrintableTable(final MessageResources res, final Locale loc) {
        header = new Vector();

        resources = res;
        localeRequest = loc;
    }

    /**
     * Ajoute une collone dans l'entête
     * @param key :  c'est la clé du libellé de la colone déclaré dans l'Application ressources.properties.
     * @param property :  c'est le nom de la propriété de bean de la collection à aller chercher.
     * @param dateformat : Format de la date si necessaire
     */
    public void addHeader(final String key, final String property, final String dateformat) {
        header.add(new HeaderElement(key, property, dateformat,localeRequest));
    }

    /**
       * Ajoute une collone dans l'entête
       * @param key :  c'est la clé du libellé de la colone déclaré dans l'Application ressources.properties.
       * @param property :  c'est le nom de la propriété de bean de la collection à aller chercher.
       */
    public void addHeader(final String key, final String property) {
        header.add(new HeaderElement(key, property, null,null));
    }

    /**
     * 
     * @return Tableau remplit
     */
    public DefaultTableLens getFormatedTable() {
        final DefaultTableLens tl = new DefaultTableLens();
        final Object oTable[][] = new Object[table.size() + 1][header.size()];

        //header
        int i = 0;

        for (final Enumeration e = header.elements(); e.hasMoreElements();) {
            final HeaderElement elem = (HeaderElement) e.nextElement();
            oTable[0][i] = resources.getMessage(localeRequest, elem.getKey());
            i++;
        }

        //table
        int j = 1;

        for (final Iterator iter = table.iterator(); iter.hasNext();) {
            final Object element = iter.next();

            i = 0;

            for (final Enumeration e = header.elements(); e.hasMoreElements();) {
                final HeaderElement elem = (HeaderElement) e.nextElement();
                String val = null;

                try {
                    val = BeanUtils.getProperty(element, elem.getProperty());
                } catch (final Exception ex) {
                    log.error(ex,ex);
                }

                if (val != null) {
                    if (elem.getDateFormat() != null) {
                        try {
                            oTable[j][i] = elem.getDateFormat().format(Util.formatDtHr.parse(val));
                        } catch (final ParseException pex) {
                            oTable[j][i] = "-";
                        }
                    } else {
                        if (val.equalsIgnoreCase("true")) {
                            val = "ok";
                        }

                        if (val.equalsIgnoreCase("false")) {
                            val = "";
                        }

                        oTable[j][i] = val;
                    }
                } else {
                    oTable[j][i] = "-";
                }
                i++;
            }

            j++;
        }

        tl.setData(oTable);
        tl.setHeaderRowCount(1);

        return tl;
    }

    /**
     * @return Tableau style AF
     */
    public TableStyle getStyledTable() {
        final CharteTableStyle style = new CharteTableStyle();
        style.setTable(getFormatedTable());

        return style;
    }

    /**
     * @return LIste de colonne d'entete
     */
    public Vector getHeader() {
        return header;
    }

    /**
     * @return table de données
     */
    public Collection getTable() {
        return table;
    }

    /**
     * @param collection liste des colonnes
     */
    public void setHeader(final Vector collection) {
        header = collection;
    }

    /**
     * @param collection Donnée a affichés
     */
    public void setTable(final Collection collection) {
        table = collection;
    }

    /**
     * @return MesssgeResources
     */
    public MessageResources getResources() {
        return resources;
    }

    /**
     * @param pResources MessageReources
     */
    public void setResources(final MessageResources pResources) {
        this.resources = pResources;
    }

    /**
     * @return La locale
     */
    public Locale getLocaleRequest() {
        return localeRequest;
    }

    /**
     * @param locale La locale
     */
    public void setLocaleRequest(final Locale locale) {
        localeRequest = locale;
    }

    /**
     *  Generation de l'entete
     * 
     * @author M327837
     *
     * Pour changer le modèle de ce commentaire de type généré, allez à :
     * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
     */
    class HeaderElement {
        
        /** Clef dna le message resources*/
        private String key;
        /** Propriéte du bean a afficher */
        private String property;
        /** Fomrateg de la date */
        private SimpleDateFormat dateFormat = null;

        /**
         * Constructeur 
         *
         */
        public HeaderElement() {
        }

        /**
         * Contruit un header
         * @param k : key
         * @param p : property
         * @param dateformat : formategde la date , @link SimpleDateFormat
         */
        public HeaderElement(final String k, final String p, final String dateformat) {
        	this(k,p,dateformat,null);
        }

        /**
         * Contruit un header
         * @param k : key
         * @param p : property
         * @param dateformat : formategde la date , @link SimpleDateFormat
         * @param locale : locale
         */
        public HeaderElement(final String k, final String p, final String dateformat,final Locale locale) {
            key = k;
            property = p;

            if (dateformat != null) {
            	if (locale!=null) {
            		dateFormat = new SimpleDateFormat(dateformat,locale);
            	} else {
            		dateFormat = new SimpleDateFormat(dateformat);
            	}
            }
        }
        
        /**
         * @return La key
         */
        public String getKey() {
            return key;
        }

        /**
         * @return la property
         */
        public String getProperty() {
            return property;
        }

        /**
         * @param string la key
         */
        public void setKey(final String string) {
            key = string;
        }

        /**
         * @param string la property
         */
        public void setProperty(final String string) {
            property = string;
        }

        /**
         * @return le formattage de la date
         */
        public SimpleDateFormat getDateFormat() {
            return dateFormat;
        }

        /**
         * @param format le formattage de la date
         */
        public void setDateFormat(final SimpleDateFormat format) {
            dateFormat = format;
        }
    }
}