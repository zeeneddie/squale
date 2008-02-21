/*
 * Créé le 12 déc. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.RequestProcessor;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.struts.plugin.WelcomContext;
import com.airfrance.welcom.taglib.field.util.LayoutUtils;
import com.airfrance.welcom.taglib.field.util.TagUtils;
import com.airfrance.welcom.taglib.onglet.JSOnglet;
import com.airfrance.welcom.taglib.table.InternalTableUtil;

/**
 * @author M325379
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WRequestProcessor extends RequestProcessor {

    /** Date key */
    public static final String DATE_FORMAT_KEY = "dateformats";

    /** 
     * Ajoute le mise en place du WelcomContext
     * @see org.apache.struts.action.RequestProcessor#process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void process(final HttpServletRequest arg0, final HttpServletResponse arg1) throws IOException, ServletException {

        // Reinitialise le WelcomContext;
        WelcomContext.resetWelcomContextName();

        super.process(arg0, arg1);
    }

    /**
     * effectue un populate welcom
     * @throws ServletException : Exception
     * @param form : fomulaire
     * @param request : request
     * @param response : response
     * @param mapping : mapping
     */
    protected void processPopulate(final HttpServletRequest request, final HttpServletResponse response, final ActionForm form, final ActionMapping mapping) throws ServletException {
        try {
            super.processPopulate(request, response, form, mapping);

            // Recupere tous les parameters
            Hashtable onglets = (Hashtable) request.getSession().getAttribute(JSOnglet.SELECTED_TABS);
            if (onglets == null) {
                onglets = new Hashtable();
            }

            final Enumeration enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()) {
                final String element = (String) enumeration.nextElement();
                if (form != null) {
                    processPopulateDate(request, form, element);
                }
                processPopulateSelectedTabs(request, element, onglets);
            }

            // Stocke les onglets
            request.getSession().setAttribute(JSOnglet.SELECTED_TABS, onglets);

            processPopulateRazCheckBox(request, form);

        } catch (final Exception ex) {
            try {
                try {
                    WActionUtil.checkSessionTimeout(request);
                } catch (final TimeOutException e) {
                    log.error("Timeout : ", e);
                    processException(request, response, e, form, mapping);
                }
                processException(request, response, ex, form, mapping);
            } catch (final IOException ioe) {
                throw new ServletException(ioe);
            }
        }
    }

    /**
      * effectue un populate sur le RZA des check box quand l'autoRaz des checkbox est a false pour les tables
      * @param form : fomulaire
      * @param request : request
      * @parem element : Nom du champs
      */
    private void processPopulateRazCheckBox(final HttpServletRequest request, final ActionForm form) {
        // Persistance des cases a cochés 
        if (Util.isFalse(WelcomConfigurator.getMessage(WelcomConfigurator.OPTIFLUX_AUTORESET_CHECKBOX))) {
            InternalTableUtil.razCheckBoxListe(request, form);
        }
    }

    /**
      * effectue un populate en conction des dates
      * @param request : request
      * @parem element : Nom du champs
      */
    private void processPopulateSelectedTabs(final HttpServletRequest request, final String element, final Hashtable onglets) {

        if (element.indexOf("selectedtab") > 0) {
            final String onglet = request.getParameter(element);

            if (!GenericValidator.isBlankOrNull(onglet)) {
                onglets.put(element, onglet);
            }
        }
    }

    /**
      * effectue un populate en conction des dates
      * @param form : fomulaire
      * @param request : request
      * @parem element : Nom du champs
      */
    private void processPopulateDate(final HttpServletRequest request, final ActionForm form, final String element) {
        if (element.endsWith("WDate") && !element.equals("WDate")) {

            final String field = element.substring(0, element.lastIndexOf("WDate"));

            String formattedDate = TagUtils.getDateHeureFromDateTag(request, field);

            final HashMap formatMaps = (HashMap) request.getSession().getAttribute(DATE_FORMAT_KEY);

            try {
                final Class propertyType = PropertyUtils.getPropertyType(form, field);
                if (propertyType != null) {
                    boolean isTypeDate = propertyType.equals(Date.class);
                    Object emptyValue = null;
                    if (!isTypeDate) {
                        emptyValue = "";
                    }

                    if ((formatMaps != null) && (formatMaps.get(field + "WDate") != null)) {
                        String pattern = (String) formatMaps.get(field + "WDate");

                        if (formatMaps.get(field + "WHour") != null) {
                            pattern = pattern + " " + formatMaps.get(field + "WHour");
                        }

                        if (GenericValidator.isBlankOrNull(formattedDate)) {
                            BeanUtils.setProperty(form, field, emptyValue);
                        } else {
                        	Locale locale = (Locale)request.getSession().getAttribute(Globals.LOCALE_KEY);
                            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern,locale);
                            dateFormat.setLenient(false);

                            try {
                                Date newDate = dateFormat.parse(formattedDate);

                                if (dateFormat.format(newDate).equals(formattedDate)) {
                                    try {
                                        if (isTypeDate) {
                                            LayoutUtils.setProperty(form, field, newDate);
                                        } else {
                                            LayoutUtils.setProperty(form, field, String.valueOf(newDate.getTime()));
                                        }
                                    } catch (SecurityException e1) {
                                        e1.printStackTrace();
                                    }
                                } else {
                                    BeanUtils.setProperty(form, field, emptyValue);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                LayoutUtils.setProperty(form, field, emptyValue);
                            }
                        }
                    } else {
                        if (formattedDate == null) {
                            formattedDate = "";
                        }
                        BeanUtils.setProperty(form, field, formattedDate);
                    }

                }

            } catch (final JspException e1) {
                e1.printStackTrace();
            } catch (final IllegalAccessException e3) {
                e3.printStackTrace();
            } catch (final InvocationTargetException e3) {
                e3.printStackTrace();
            } catch (final NoSuchMethodException e3) {
                e3.printStackTrace();
            }

        }
    }

}
