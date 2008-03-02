/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.airfrance.welcom.taglib.logic;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.logic.ConditionalTagBase;
import org.apache.struts.util.RequestUtils;

import com.airfrance.welcom.taglib.field.util.LayoutUtils;

/**
 * Class IsEmptyTag
 */
public class IsEmptyTag
    extends ConditionalTagBase
{
    /**
     * 
     */
    private static final long serialVersionUID = 4528251686653500057L;

    /**
     * Constructeur
     */
    public IsEmptyTag()
    {
    }

    /**
     * @see org.apache.struts.taglib.logic.ConditionalTagBase#condition()
     */
    protected boolean condition()
        throws JspException
    {
        return condition( true );
    }

    /**
     * @param desired boolean desire
     * @return la condition
     * @throws JspException exception pouvant etre levee
     */
    protected boolean condition( final boolean desired )
        throws JspException
    {
        boolean empty = true;

        if ( super.name != null )
        {
            final Object value = LayoutUtils.getBeanFromPageContext( super.pageContext, super.name, super.property );

            if ( value != null )
            {
                if ( value instanceof String )
                {
                    final String strValue = (String) value;
                    empty = strValue.length() < 1;
                }
                else
                {
                    empty = false;
                }
            }
        }
        else
        {
            final JspException e = new JspException( ConditionalTagBase.messages.getMessage( "logic.selector" ) );
            RequestUtils.saveException( super.pageContext, e );
            throw e;
        }

        return empty == desired;
    }
}