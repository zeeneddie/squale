package com.airfrance.welcom.taglib.security;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.airfrance.welcom.outils.Access;

/**
 * @author user
 *
 */
public class IsPageReadOnlyTag extends TagSupport {

    /**
     * 
     */
    private static final long serialVersionUID = -4113880035039612215L;

    /**
      * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
      */
    public int doStartTag() throws JspException {
        // Recupere le droit sur la page
        final String pageAccess = (String) pageContext.getAttribute("access");

        if ((pageAccess != null) && pageAccess.equals(Access.READONLY)) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}