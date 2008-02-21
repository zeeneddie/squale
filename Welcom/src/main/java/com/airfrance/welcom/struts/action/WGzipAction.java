/*
 * Créé le 22 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.airfrance.welcom.struts.webServer.WebEngine;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WGzipAction extends Action {
    /** logger */
    private static Log log = LogFactory.getLog(WGzipAction.class);

    /**
     * @see org.apache.struts.action.Action#perform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public synchronized ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
        throws IOException, ServletException {

        try {

            final WebEngine serveurWeb = new WebEngine(servlet, request, response);
            serveurWeb.process(request.getParameter("value"));
            return null;
        } catch (final Exception e) {
            log.error(e, e);
            return null;
        }
    }

}