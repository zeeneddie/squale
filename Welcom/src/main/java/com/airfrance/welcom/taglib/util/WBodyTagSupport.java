package com.airfrance.welcom.taglib.util;

import javax.servlet.jsp.tagext.TagSupport;

import com.airfrance.welcom.taglib.field.util.TagUtils;


/**
 * 
 * @author M327837
 *
 * Ajoute le support da l'ajour de parametre
 */
public class WBodyTagSupport extends TagSupport {
    

    /**
     * 
     */
    private static final long serialVersionUID = -8307143465885866995L;

    /** 
     * Ajout l'attribut avec sa valeur au stringbuffer
     * @param sb stringbuffer
     * @param name nom
     * @param value valeur
     */
    protected void addParam(StringBuffer sb,String name,String value) {

        TagUtils.addParam(sb, name, value);

    }


}
