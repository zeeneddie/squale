package com.airfrance.squaleweb.applicationlayer.action;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.airfrance.squaleweb.applicationlayer.formbean.component.SplitAuditsListForm;
import com.airfrance.welcom.struts.util.WConstants;

/**
 * Filtre Squale
 * Ce filtre permet de s'assurer que l'on ne sert pas une URL si
 * la session n'est pas correctement renseignée
 * Dans ce cas on redirige vers la racine du site
 */
public class SqualeFilter implements Filter {
    /** URL racine */
    private static final String ROOT_URL = "/index.jsp";
    /** URL de la portlet */
    private static final String PORTLET_URL = "squalePortlet.do";
    /**
    * @see javax.servlet.Filter#void ()
    * {@inheritDoc}
    */
    public void destroy() {

    }

    /**
    * @see javax.servlet.Filter#void (javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
    * {@inheritDoc}
    * @param req la requête
    * @param resp la réponse
    * @param chain la chaîne
    * @throws ServletException si erreur au niveau servlet
    * @throws IOException si erreur de flux
    */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // On vérifie la présence en session des éléments de l'utilisateur
        HttpServletRequest request = (HttpServletRequest) req;
        if ((request.getSession().getAttribute(WConstants.USER_KEY) == null) && (!request.getRequestURL().toString().endsWith(ROOT_URL))) {
            // Dans ce cas on vérifie qu'on ne vient pas de la porlet sinon on fait un traitement spécifique
            if (request.getRequestURL().toString().endsWith(PORTLET_URL) || null != request.getParameter("fromPortlet")) {
                // On met en session l'utilisateur pour SQUALE
                new LoginAction().initUserInSession(request);
                // TODO : voir si userOk = false...
                if (null != request.getParameter("fromPortlet")) {
                    // On met en session les information nécessaires pour la portlet
                    // comme si l'utilisateur était passé par la page d'accueil (cad avec un connexion classique)
                    SplitAuditsListForm splitAuditsListForm = new SplitAuditsListForm();
                    splitAuditsListForm.setAllAudits(true);
                    new IndexAction().initUserSession(splitAuditsListForm, request);
                    request.getSession().setAttribute("splitAuditsListForm", splitAuditsListForm);
                }
                chain.doFilter(req, resp);
            } else {
                request.getRequestDispatcher(ROOT_URL).forward(request, resp);
            }
        } else {
            chain.doFilter(req, resp);
        }
    }

    /**
    * Method init.
    * @param config configuration
    * @throws javax.servlet.ServletException si erreur
    */
    public void init(FilterConfig config) throws ServletException {

    }

}
