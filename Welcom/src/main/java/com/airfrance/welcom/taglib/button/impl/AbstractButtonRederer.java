package com.airfrance.welcom.taglib.button.impl;

import javax.servlet.jsp.PageContext;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.struts.util.WRequestUtils;

public class AbstractButtonRederer
{
    /**
     * retourne le label du bouton
     * 
     * @param type le type du bouton
     * @param name nom du bouton
     * @param pageContext contexte
     * @return le label
     */
    protected String getLabel( final PageContext pageContext, final String name, final String rawValue,
                               final String type )
    {

        if ( !GenericValidator.isBlankOrNull( rawValue ) )
        {
            return rawValue;
        }
        else
        {
            final String labelKey = "buttonTag." + type + "." + name;
            String label = WRequestUtils.message( pageContext, labelKey );

            if ( !GenericValidator.isBlankOrNull( label ) && Util.isNonEquals( label, labelKey ) )
            {
                return label;
            }
            else
            {
                label = WRequestUtils.message( pageContext, "buttonTag." + name );
                if ( !GenericValidator.isBlankOrNull( label ) )
                {
                    return label;
                }
                else
                {
                    return labelKey;
                }
            }
        }
    }
}
