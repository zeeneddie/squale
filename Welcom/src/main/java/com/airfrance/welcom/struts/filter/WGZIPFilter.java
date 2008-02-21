/*
 * Copyright 2003 Jayson Falkner (jayson@jspinsider.com)
 * This code is from "Servlets and JavaServer pages; the J2EE Web Tier",
 * http://www.jspbook.com. You may freely use the code both commercially
 * and non-commercially. If you like the code, please pick up a copy of
 * the book and help support the authors, development of more free code,
 * and the JSP/Servlet/J2EE community.
 */
package com.airfrance.welcom.struts.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WGZIPFilter implements Filter {
    
    /**
     * La config
     */
    private FilterConfig config;

    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     * */
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
        if (req instanceof HttpServletRequest) {
            final HttpServletRequest request = (HttpServletRequest) req;
            final HttpServletResponse response = (HttpServletResponse) res;

            //si le navigateur accepte la compression, on zip le flux
            final String ae = request.getHeader("accept-encoding");

            if ((ae != null) && (ae.indexOf("gzip") != -1)) {
                final GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(response);
                chain.doFilter(req, wrappedResponse);
                wrappedResponse.finishResponse();

                return;
            }

            chain.doFilter(req, res);
        }
    }

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(final FilterConfig filterConfig) {
        config = filterConfig;
    }

    /**
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        config = null;
    }
}