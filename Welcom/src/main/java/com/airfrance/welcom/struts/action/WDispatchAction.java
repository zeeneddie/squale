/*
 * Créé le 13 mai 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;

import com.airfrance.welcom.struts.util.WatchedTask;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public abstract class WDispatchAction extends WAction {
    
    

    // ----------------------------------------------------- Instance Variables


    /**
     * The Class instance of this <code>DispatchAction</code> class.
     */
    protected Class clazz = this.getClass();


    /**
     * Commons Logging instance.
     */
    protected static Log log = LogFactory.getLog(DispatchAction.class);


    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
     MessageResources.getMessageResources
        ("org.apache.struts.actions.LocalStrings");


    /**
     * The set of Method objects we have introspected for this class,
     * keyed by method name.  This collection is populated as different
     * methods are called, so that introspection needs to occur only
     * once per method name.
     */
    protected HashMap methods = new HashMap();


    /**
     * The set of argument type classes for the reflected method call.  These
     * are the same for all calls, so calculate them only once.
     */
    protected Class types[] = {
        ActionMapping.class, ActionForm.class,
        HttpServletRequest.class, HttpServletResponse.class };

    protected Class typesProgressBar[] = {
        ActionMapping.class, ActionForm.class,
        HttpServletRequest.class, HttpServletResponse.class, 
        WatchedTask.class
    };

    // --------------------------------------------------------- Public Methods


    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception Exception if an exception occurs
     * 
     * @return ActionForward
     */
    public ActionForward wExecute(final ActionMapping mapping,
                                 final ActionForm form,
                                 final HttpServletRequest request,
                                 final HttpServletResponse response)
        throws Exception {
            
        // Identify the request parameter containing the method name
        final String parameter = mapping.getParameter();
        if (parameter == null) {
            final String message =
                messages.getMessage("dispatch.handler", mapping.getPath());
            log.error(message);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                               message);
            return (null);
        }

        // Identify the method name to be dispatched to.
        // dispatchMethod() will call unspecified() if name is null
        final String name = request.getParameter(parameter);

        // Invoke the named method, and return the result
        return dispatchMethod(mapping, form, request, response, name);
    }
    
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception Exception if an exception occurs
     * 
     * @return ActionForward
     */
    public ActionForward wExecute(final ActionMapping mapping,
                                 final ActionForm form,
                                 final HttpServletRequest request,
                                 final HttpServletResponse response,
                                 final WatchedTask task)
        throws Exception {
            
        // Identify the request parameter containing the method name
        final String parameter = mapping.getParameter();
        if (parameter == null) {
            final String message =
                messages.getMessage("dispatch.handler", mapping.getPath());
            log.error(message);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                               message);
            return (null);
        }

        // Identify the method name to be dispatched to.
        // dispatchMethod() will call unspecified() if name is null
        final String name = request.getParameter(parameter);

        // Invoke the named method, and return the result
        return dispatchMethod(mapping, form, request, response, name, task);
    }


    /**
     * Method which is dispatched to when there is no value for specified
     * request parameter included in the request.  Subclasses of
     * <code>DispatchAction</code> should override this method if they wish
     * to provide default behavior different than producing an HTTP
     * "Bad Request" error.
     *
     * @see org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected ActionForward unspecified(final ActionMapping mapping,
                                     final ActionForm form,
                                     final HttpServletRequest request,
                                     final HttpServletResponse response)
        throws Exception {

        final String message =
            messages.getMessage("dispatch.parameter", mapping.getPath(),
                                mapping.getParameter());
        log.error(message);
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
        return (null);

    }


    // ----------------------------------------------------- Protected Methods


    /**
     * Dispatch to the specified method.
     * @since Struts 1.1
     * @see org.apache.struts.actions.DispatchAction#dispatchMethod(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, String name)
     */
     protected ActionForward dispatchMethod(final ActionMapping mapping,
                                            final ActionForm form,
                                            final HttpServletRequest request,
                                            final HttpServletResponse response,
                                            final String name) throws Exception {
                                                
        // Make sure we have a valid method name to call.
        // This may be null if the user hacks the query string.
        if (name == null) {
            return this.unspecified(mapping, form, request, response);
        }

        // Identify the method object to be dispatched to
        Method method = null;
        try {
            method = getMethod(name, types);
        } catch (final NoSuchMethodException e) {
            final String message =
                messages.getMessage("dispatch.method", mapping.getPath(),
                                    name);
            log.error(message, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                               message);
            return (null);
        }

        ActionForward forward = null;
        try {
            final Object args[] = { mapping, form, request, response };
            forward = (ActionForward) method.invoke(this, args);
        } catch (final ClassCastException e) {
            final String message =
                messages.getMessage("dispatch.return", mapping.getPath(),
                                    name);
            log.error(message, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                               message);
            return (null);
        } catch (final IllegalAccessException e) {
            final String message =
                messages.getMessage("dispatch.error", mapping.getPath(),
                                    name);
            log.error(message, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                               message);
            return (null);
        } catch (final InvocationTargetException e) {
            // Rethrow the target exception if possible so that the
            // exception handling machinery can deal with it
            final Throwable t = e.getTargetException();
            if (t instanceof Exception) {
                throw ((Exception) t);
            } else {
                final String message =
                    messages.getMessage("dispatch.error", mapping.getPath(),
                                        name);
                log.error(message, e);
                response.sendError
                    (HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
                return (null);
            }
        }

        // Return the returned ActionForward instance
        return (forward);
    }

     /**
      * Dispatch to the specified method.
      * @since Struts 1.1
      * @see org.apache.struts.actions.DispatchAction#dispatchMethod(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, String name)
      */
      protected ActionForward dispatchMethod(final ActionMapping mapping,
                                             final ActionForm form,
                                             final HttpServletRequest request,
                                             final HttpServletResponse response,
                                             final String name,
                                             final WatchedTask task) throws Exception {
                                                 
         // Make sure we have a valid method name to call.
         // This may be null if the user hacks the query string.
         if (name == null || task == null) {
             return this.unspecified(mapping, form, request, response);
         }

         // Identify the method object to be dispatched to
         Method method = null;
         try {
             method = getMethod(name, typesProgressBar);
         } catch (final NoSuchMethodException e) {
             final String message =
                 messages.getMessage("dispatch.method", mapping.getPath(),
                                     name);
             log.error(message, e);
             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                                message);
             return (null);
         }

         ActionForward forward = null;
         try {
             final Object args[] = { mapping, form, request, response, task };
             forward = (ActionForward) method.invoke(this, args);
         } catch (final ClassCastException e) {
             final String message =
                 messages.getMessage("dispatch.return", mapping.getPath(),
                                     name);
             log.error(message, e);
             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                                message);
             return (null);
         } catch (final IllegalAccessException e) {
             final String message =
                 messages.getMessage("dispatch.error", mapping.getPath(),
                                     name);
             log.error(message, e);
             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                                message);
             return (null);
         } catch (final InvocationTargetException e) {
             // Rethrow the target exception if possible so that the
             // exception handling machinery can deal with it
             final Throwable t = e.getTargetException();
             if (t instanceof Exception) {
                 throw ((Exception) t);
             } else {
                 final String message =
                     messages.getMessage("dispatch.error", mapping.getPath(),
                                         name);
                 log.error(message, e);
                 response.sendError
                     (HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
                 return (null);
             }
         }

         // Return the returned ActionForward instance
         return (forward);
     }

    /**
     * Introspect the current class to identify a method of the specified
     * name that accepts the same parameter types as the <code>execute</code>
     * method does.
     *
     * @param name Name of the method to be introspected
     *
     * @exception NoSuchMethodException if no such method can be found
     * @return methode
     **/
    protected Method getMethod(final String name, Class[] argsTypes)
        throws NoSuchMethodException {

        synchronized (methods) {
            Method method = (Method) methods.get(name);
            if (method == null) {
                method = clazz.getMethod(name, argsTypes);
                methods.put(name, method);
            }
            return (method);
        }

    }
    
    
}