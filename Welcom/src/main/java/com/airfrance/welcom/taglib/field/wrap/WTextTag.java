package com.airfrance.welcom.taglib.field.wrap;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.HiddenTag;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.taglib.field.util.LayoutUtils;

public class WTextTag extends WBaseTextTag implements IWelcomInputTag {

    /**
     * Spécifie le mode d'accée du composant
     */
    private String access = READWRITE;
    
    /**
     * {@inheritDoc}
     */
    public int doStartTag() throws JspException {
        
        System.out.println("Access : " + access);

        if (READWRITE.equals(access)) {
            return super.doStartTag();
        } else {
            StringBuffer sb = new StringBuffer();
            doRenderValue(sb);
            ResponseUtils.write(pageContext, sb.toString());
            if (READSEND.equals(access)) {
                HiddenTag hiddenTag = new HiddenTag();
                // initialize the tag
                LayoutUtils.copyProperties(hiddenTag, this);
                hiddenTag.doStartTag();
                hiddenTag.doEndTag();
            }
            return EVAL_PAGE;
        }
    }

    /**
     * @return access attribut
     */
    public String getAccess() {
        return access;
    }

    /**
     * @param access access attribut
     */
    public void setAccess(String access) {
        this.access = access;
    }
    
    
}
