package com.airfrance.welcom.taglib.html;

import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.outils.Access;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.struts.util.WRequestUtils;

/**
 * OptionTag 
 *
 */
public class OptionTag extends BodyTagSupport {

    /**
     *  
     */
    private static final long serialVersionUID = -571283088885353434L;
    /** Constante*/
    protected static final Locale DEFAULT_LOCALE = Locale.getDefault();
    /** messageRessource*/
    protected static MessageResources messages = MessageResources.getMessageResources("org.apache.struts.taglib.html.LocalStrings");
    /** parametre du tag*/
    protected String text;
    /** parametre du tag*/
    protected String bundle;
    /** parametre du tag*/
    protected boolean disabled;
    /** parametre du tag*/
    protected String key;
    /** parametre du tag*/
    protected String locale;
    /** parametre du tag*/
    private String style;
    /** parametre du tag*/
    private String styleClass;
    /** parametre du tag*/
    protected String value;

    /**
     * Constructeur
     *
     */
    public OptionTag() {
        text = null;
        bundle = "org.apache.struts.action.MESSAGE";
        disabled = false;
        key = null;
        locale = "org.apache.struts.action.LOCALE";
        style = null;
        styleClass = null;
        value = null;
    }

    /**
     * 
     * @return bundle
     */
    public String getBundle() {
        return bundle;
    }

    /**
     * 
     * @param pBundle bundle
     */
    public void setBundle(final String pBundle) {
        bundle = pBundle;
    }

    /**
     * 
     * @return disabled
     */
    public boolean getDisabled() {
        return disabled;
    }

    /**
     * 
     * @param pDisabled disabled
     */
    public void setDisabled(final boolean pDisabled) {
        disabled = pDisabled;
    }

    /**
     * 
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * 
     * @param pKey key
     */
    public void setKey(final String pKey) {
        key = pKey;
    }

    /**
     * 
     * @return locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * 
     * @param pLocale locale
     */
    public void setLocale(final String pLocale) {
        locale = pLocale;
    }

    /**
     * 
     * @return style
     */
    public String getStyle() {
        return style;
    }

    /**
     * 
     * @param pStyle style
     */
    public void setStyle(final String pStyle) {
        style = pStyle;
    }

    /**
     * 
     * @return styleClass
     */
    public String getStyleClass() {
        return styleClass;
    }

    /**
     * 
     * @param pStyleClass styleClass
     */
    public void setStyleClass(final String pStyleClass) {
        styleClass = pStyleClass;
    }

    /**
     * 
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * 
     * @param pValue value
     */
    public void setValue(final String pValue) {
        value = pValue;
    }

    /**
     * 
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
        text = null;

        return EVAL_BODY_BUFFERED;
    }

    /**
     * 
     * @see javax.servlet.jsp.tagext.IterationTag#doAfterBody()
     */
    public int doAfterBody() throws JspException {
        String newText = super.bodyContent.getString();

        if (newText != null) {
            newText = newText.trim();

            if (newText.length() > 0) {
                text = newText;
            }
        }

        return SKIP_BODY;
    }

    /**
     * 
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws JspException {
        final SelectTag selectTag = (SelectTag) super.pageContext.getAttribute("com.airfrance.welcom.taglib.html.SELECT");

        if (selectTag == null) {
            final JspException e = new JspException(messages.getMessage("optionTag.select"));
            RequestUtils.saveException(super.pageContext, e);
            throw e;
        }

        //        String pageAccess = (String) pageContext.getAttribute("access");
        final String pageAccess = selectTag.getResultAccess();
        final StringBuffer results = new StringBuffer();

        if (selectTag.isForceReadWrite() || ((pageAccess != null) && pageAccess.equals(Access.READWRITE)) || (pageAccess == null)) {
            results.append("<option value=\"");
            results.append(value);
            results.append("\"");

            if (disabled) {
                results.append(" disabled=\"disabled\"");
            }

            if (selectTag.isMatched(value)) {
                results.append(" selected=\"selected\"");
            }

            if (style != null) {
                results.append(" style=\"");
                results.append(style);
                results.append("\"");
            }

            if (styleClass != null) {
                results.append(" class=\"");
                results.append(styleClass);
                results.append("\"");
            }

            results.append(">");

            final String newText = text();

            if (newText == null) {
                results.append(value);
            } else {
                results.append(newText);
            }

            results.append("</option>");
        } else {
            if (selectTag.isMatched(value)) {
                results.append("<span class=\"normalBold\">");

                final String newText = text();

                if (newText != null) {
                    results.append(newText);
                } else if (value != null) {
                    results.append(value);
                } else {
                    results.append(WelcomConfigurator.getMessageWithCfgChartePrefix(".default.char.if.empty"));
                }

                results.append("</span>");
            }
        }

        ResponseUtils.write(super.pageContext, results.toString());

        return EVAL_PAGE;
    }

    /**
     * 
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release() {
        super.release();
        bundle = "org.apache.struts.action.MESSAGE";
        disabled = false;
        key = null;
        locale = "org.apache.struts.action.LOCALE";
        style = null;
        styleClass = null;
        text = null;
        value = null;
    }

    /**
     * 
     * @return le text si il n'est pas nul
     * @throws JspException exception pouvant etre levee
     */
    protected String text() throws JspException {
        if (text != null) {
            return text;
        } else {
            return WRequestUtils.message(super.pageContext, key);
        }
    }
}