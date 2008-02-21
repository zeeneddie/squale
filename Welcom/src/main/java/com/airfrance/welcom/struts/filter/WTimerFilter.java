package com.airfrance.welcom.struts.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WTimerFilter implements Filter {
    /** logger */
    private static Log log = LogFactory.getLog(WTimerFilter.class);

    /** Config */
    private FilterConfig config;


    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);

        final long stopTime = System.currentTimeMillis();
        log.debug("Time to execute request: " + (stopTime - startTime) + " milliseconds (" + ((HttpServletRequest) request).getRequestURI() + ")");
    }

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(final FilterConfig arg0) throws ServletException {
        config=arg0;
    }

    /**
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }
}