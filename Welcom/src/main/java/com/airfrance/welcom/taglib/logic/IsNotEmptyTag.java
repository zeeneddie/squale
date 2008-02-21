// Source File Name:   IsNotEmptyTag.java
package com.airfrance.welcom.taglib.logic;

import javax.servlet.jsp.JspException;

/**
 *  Referenced classes of package com.airfrance.struts.taglib.layout:
 * IsEmptyTag
 *
 */
public class IsNotEmptyTag extends IsEmptyTag {
    /**
     * 
     */
    private static final long serialVersionUID = -8026001995361960875L;

    /**
     * Constructeur
     *
     */
    public IsNotEmptyTag() {
    }

    /**
     * 
     * @see org.apache.struts.taglib.logic.ConditionalTagBase#condition()
     */
    protected boolean condition() throws JspException {
        return condition(false);
    }
}