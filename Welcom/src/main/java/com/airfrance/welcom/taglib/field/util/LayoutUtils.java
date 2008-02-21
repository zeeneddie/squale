package com.airfrance.welcom.taglib.field.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.Globals;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

/**
 * Renseigne l'emplacement des fichiers de configuration,
 * des javascripts et des feuilles de styles
 * Il est possible d'appeler au démarrage de l'application
 * la méthode statique init(ServletContext) qui permet
 * de définir les attributs de cette classe
 * @author: Cédric Torcq
 * @version 0.1
 */
public class LayoutUtils {
    /** bundle*/
    protected static String bundle = Globals.MESSAGES_KEY;
    /** locale */
    protected static String localeKey = Globals.LOCALE_KEY;
    /** message ressource*/
    protected static MessageResources messages = MessageResources.getMessageResources(Constants.Package + ".LocalStrings");
    /** name */
    protected static String name = Constants.BEAN_KEY;

    /** Menu messages */
    protected static MessageResources menuMessages = MessageResources.getMessageResources("com.airfrance.struts.taglib.menu.displayer.taglib.LocalStrings");

    /** Répertoire contenant les javascripts*/
    protected static String javascript_dir = "jslib";

    /** Répertoire contenant les feuilles de styles css*/
    protected static String css_dir = "css";

    /** Répertoire contenant les images*/
    protected static String image_dir = "images";

    /** Feuille de style par défaut*/
    protected static String default_skin = "default.css";

    /** Parmamètres permettant de faire fonctionner les tags de la librairie sans données métier*/
    protected static boolean noErrorMode = false;

    /**
     * copie les properties d'un objet vers un autre 
     * @param dest l'objet destination
     * @param orig l'objet origine
     * @throws JspException exception pouvant etre levee
     */
    public static void copyProperties(final Object dest, final Object orig) throws JspException {
        try {
            PropertyUtils.copyProperties(dest, orig);
        } catch (final InvocationTargetException e) {
            Throwable t = e.getTargetException();

            if (t == null) {
                t = e;
            }

            System.err.println("LayoutUtils.copyProperties: ");
            System.err.println(t);
            throw new JspException("LayoutUtils.copyProperties: " + t.getMessage());
        } catch (final Throwable t) {
            System.err.println("LayoutUtils.copyProperties: ");
            System.err.println(t);
            throw new JspException("LayoutUtils.copyProperties: " + t.getMessage());
        }
    }

    /**
     * Get the property 'property' of the bean named WConstants.BEAN_KEY in the given page context
     * Handle classic exception
     * @param pageContext le page context
     * @param property le nom de la property
     * @throws JspException exception pouvant etre levee
     * @return le bean
     **/
    public static Object getBeanFromPageContext(final PageContext pageContext, final String property) throws JspException {
        final Object bean = pageContext.findAttribute(name);

        if (bean == null) {
            throw new JspException(messages.getMessage("getter.bean", name));
        }

        Object object = bean;

        if (property != null) {
            try {
                object = PropertyUtils.getProperty(bean, property);
            } catch (final IllegalAccessException e) {
                throw new JspException(messages.getMessage("getter.access", property, name));
            } catch (final InvocationTargetException e) {
                final Throwable t = e.getTargetException();
                throw new JspException(messages.getMessage("getter.result", property, t.toString()));
            } catch (final NoSuchMethodException e) {
                throw new JspException(messages.getMessage("getter.method", property, name));
            }
        }

        return object;
    }

    /**
     * Get the property 'property' of the bean named 'name' in the given pageContext.
     * Handle classic Exception
     * @param pageContext le page context
     * @param pName le nom du bean
     * @param property le nom de la property
     * @throws JspException exception pouvant etre levee
     * @return le bean
     **/
    public static Object getBeanFromPageContext(final PageContext pageContext, final String pName, final String property) throws JspException {
        final Object bean = pageContext.findAttribute(pName);

        if (bean == null) {
            throw new JspException(messages.getMessage("getter.bean", pName));
        }

        Object object = bean;

        if (property != null) {
            try {
                object = PropertyUtils.getProperty(bean, property);
            } catch (final IllegalAccessException e) {
                throw new JspException(messages.getMessage("getter.access", property, pName));
            } catch (final InvocationTargetException e) {
                final Throwable t = e.getTargetException();
                throw new JspException(messages.getMessage("getter.result", property, t.toString()));
            } catch (final NoSuchMethodException e) {
                throw new JspException(messages.getMessage("getter.method", property, pName));
            }
        }

        return object;
    }

    /**
     * Insérez la description de la méthode ici.
     *  Date de création : (02/08/2001 10:26:12)
     * @return java.lang.String
     */
    public static java.lang.String getCss_dir() {
        return css_dir;
    }

    /**
     * 
     * @return la skin par defaut
     */
    public static String getDefaultSkin() {
        return default_skin;
    }

    /**
     * Insérez la description de la méthode ici.
     *  Date de création : (02/08/2001 10:26:12)
     * @return java.lang.String
     */
    public static java.lang.String getImage_dir() {
        return image_dir;
    }

    /**
     * 
     * @return le repertoire ou se trouve les images
     */
    public static String getImageDir() {
        return image_dir;
    }

    /**
     * Build an iterator
     * @param collection collection a iterer
     * @throws JspException exception pouvant etre levee
     * @return un iterator
     */
    public static Iterator getIterator(Object collection) throws JspException {
        Iterator iterator;

        if (collection.getClass().isArray()) {
            collection = Arrays.asList((Object[]) collection);
        }

        if (collection instanceof java.util.Collection) {
            iterator = ((java.util.Collection) collection).iterator();
        } else if (collection instanceof Iterator) {
            iterator = ((Iterator) collection);
        } else if (collection instanceof Map) {
            iterator = ((Map) collection).entrySet().iterator();
        } else {
            throw new JspException(messages.getMessage("optionsTag.iterator", collection.toString()));
        }

        return iterator;
    }

    /**
     * 
     * @param pageContext le pageContext
     * @param pName le name
     * @param property la property
     * @return un iterator
     * @throws JspException exception pouvant etre levee
     */
    public static Iterator getIterator(final PageContext pageContext, final String pName, final String property) throws JspException {
        Iterator iterator;
        Object collection = null;

        if (noErrorMode) {
            final Vector v = new Vector();
            v.add("element 1");
            v.add("element 2");
            v.add("element 3");
            v.add("element 4");
            v.add("element 5");
            v.add("element 6");
            collection = v;
        } else {
            collection = getBeanFromPageContext(pageContext, pName, property);
        }

        if (collection.getClass().isArray()) {
            collection = Arrays.asList((Object[]) collection);
        }

        if (collection instanceof java.util.Collection) {
            iterator = ((java.util.Collection) collection).iterator();
        } else if (collection instanceof Iterator) {
            iterator = ((Iterator) collection);
        } else if (collection instanceof Map) {
            iterator = ((Map) collection).entrySet().iterator();
        } else {
            throw new JspException(messages.getMessage("optionsTag.iterator", collection.toString()));
        }

        return iterator;
    }

    /**
     * Insérez la description de la méthode ici.
     *  Date de création : (02/08/2001 10:26:12)
     * @return java.lang.String
     */
    public static java.lang.String getJavascript_dir() {
        return javascript_dir;
    }

    /**
     * Get the label with the key 'key' from the pageContext messageRessources.
     * Handle classic exception
     * @param pageContext le page context
     * @param key la cle
     * @param args un tableau d'arguments
     * @throws JspException exception pouvant etre levee
     * @return le label
     **/
    public static String getLabel(final PageContext pageContext, final String key, final Object args[]) throws JspException {
        return getLabel(pageContext, bundle, key, args);
    }

    /**
     * Get the label with the key 'key' from the pageContext messageRessources.
     * Handle classic exception
     * @param pageContext le page context
     * @param pBundle le bundle
     * @param key la cle
     * @param args les arguments 
     * @throws JspException exception pouvant etre levee
     * @return le label
     **/
    public static String getLabel(final PageContext pageContext, final String pBundle, final String key, final Object args[]) throws JspException {
        // Acquire the resources object containing our messages
        final MessageResources resources = (MessageResources) pageContext.findAttribute(pBundle);

        if (resources == null) {
            throw new JspException(messages.getMessage("messageTag.resources", pBundle));
        }

        // Calculate the Locale we will be using
        Locale locale = null;

        try {
            locale = (Locale) pageContext.getAttribute(localeKey, PageContext.SESSION_SCOPE);
        } catch (final IllegalStateException e) { // Invalidated session
            locale = null;
        }

        if (locale == null) {
            locale = Locale.getDefault();
        }

        // Retrieve the message string we are looking for
        final String message = resources.getMessage(locale, key, args);

        if (message == null) {
            return key;
        }

        return message;
    }

    /**
     * 
     * @return true si mode noError
     */
    public static boolean getNoErrorMode() {
        return noErrorMode;
    }

    /**
     * Get the property 'property' of the bean 'bean'
     * Handle classic exception
     * @param bean le bean
     * @param property le nom de la property
     * @throws JspException exception pouvant etre levee
     * @return la property
     **/
    public static Object getProperty(final Object bean, final String property) throws JspException {
        Object object = bean;

        if (noErrorMode) {
            return object;
        }

        if (property != null) {
            try {
                object = PropertyUtils.getProperty(bean, property);
            } catch (final IllegalAccessException e) {
                throw new JspException(messages.getMessage("getter.access", property, name));
            } catch (final InvocationTargetException e) {
                final Throwable t = e.getTargetException();
                throw new JspException(messages.getMessage("getter.result", property, t.toString()));
            } catch (final NoSuchMethodException e) {
                throw new JspException(messages.getMessage("getter.method", property, name));
            }
        }

        return object;
    }

    /**
     * Get the property 'property' of the bean 'bean'
     * Handle classic exception
     * @param bean le bean
     * @param property le nom de la property
     * @param value la value a setter
     * @throws JspException exception pouvant etre levee
     **/
    public static void setProperty(final Object bean, final String property, final Object value) throws JspException {
        if (property != null) {
            try {
                PropertyUtils.setProperty(bean, property, value);
            } catch (final IllegalAccessException e) {
                throw new JspException(messages.getMessage("getter.access", property, name));
            } catch (final InvocationTargetException e) {
                final Throwable t = e.getTargetException();
                throw new JspException(messages.getMessage("getter.result", property, t.toString()));
            } catch (final NoSuchMethodException e) {
                throw new JspException(messages.getMessage("getter.method", property, name));
            }
        }
    }

    /**
     * 
     * @param request la request
     * @return la property frImproveStrutsTaglibLayoutPROPERTY_TO_CHOOSE
     */
    public static String getPropertyToChoose(final ServletRequest request) {
        final String property = request.getParameter("frImproveStrutsTaglibLayoutPROPERTY_TO_CHOOSE");

        if ((property != null) && (property.length() > 0)) {
            return property;
        }

        return null;
    }

    /**
     * 
     * @param session la session
     * @return la skin
     */
    public static String getSkin(final HttpSession session) {
        final String skin = (String) session.getAttribute("LIGHT_SKIN");

        if (skin == null) {
            return "/css/" + default_skin;
        }

        return skin;
    }

    /**
     * 
     * @param session la session
     * @return la skin
     */
    public static String getSkinExtension(final HttpSession session) {
        final String skin = (String) session.getAttribute("LIGHT_SKIN");

        if (skin != null) {
            if (skin.indexOf('_') != -1) {
                return skin.substring(skin.indexOf('_') + 1, skin.indexOf('.'));
            } else {
                return "1";
            }
        } else {
            return "1";
        }
    }

    /**
     * init 
     * @param context le servlet context
     */
    public static void init(final ServletContext context) {
        if ("noerror".equals(context.getInitParameter("struts-layout-mode"))) {
            noErrorMode = true;
        }

        String l_string = context.getInitParameter("struts-layout-config");

        if (l_string != null) {
            javascript_dir = l_string;
        }

        l_string = context.getInitParameter("struts-layout-image");

        if (l_string != null) {
            image_dir = l_string;
        }

        l_string = context.getInitParameter("struts-layout-skin");

        if (l_string != null) {
            default_skin = l_string;
        }

        if (!default_skin.endsWith(".css")) {
            default_skin += ".css";
        }
    }

    /**
     * Set the Struts light skin to use
     * The file in config/'skin'.css will be use.
     * @param session la session
     * @param skin le skin
     **/
    public static void setSkin(final HttpSession session, final String skin) {
        if ((skin != null) && !skin.equals("")) {
            String theSkin = skin;

            if (!theSkin.endsWith(".css")) {
                theSkin += ".css";
            }

            session.setAttribute("LIGHT_SKIN", theSkin);
        } else {
            session.removeAttribute("LIGHT_SKIN");
        }
    }
}