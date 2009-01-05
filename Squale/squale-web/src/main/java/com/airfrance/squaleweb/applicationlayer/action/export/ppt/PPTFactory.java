/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.airfrance.squaleweb.applicationlayer.action.export.ppt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

/**
 * Create a powerpoint document into the HTTP response
 */
public final class PPTFactory
{
    /**
     * Private constructor since it's an utility class
     */
    private PPTFactory()
    {
    }

    /** Content Type */
    protected static final String CONTENT_TYPE = "application/ppt";

    /**
     * Generate PPT document
     * 
     * @param pptdata data to write
     * @param response response
     * @param attachementFileName return file name
     * @throws PPTGeneratorException if error
     */
    public static void generatePPTtoHTTPResponse( final PPTData pptdata, final HttpServletResponse response,
                                                  final String attachementFileName)
        throws PPTGeneratorException
    {
        try
        {
            // create slide show with user presentation
            pptdata.modifyPresentation();
            
            
            /* / save changes in a file --> Line not useful for exploitation. May be used for debug 
				FileOutputStream out = new FileOutputStream("C:/slideshow2.ppt" );
				pptdata.getPresentation().write( out );
				out.close();
			*/
            
            response.setContentType( CONTENT_TYPE );
            if ( ( ( attachementFileName != null ) && ( attachementFileName.length() > 0 ) ) )
            {
                response.setHeader( "Content-Disposition", "attachment;filename=" + attachementFileName + ";" );
            }
            // write presentation in http response
            final OutputStream outputStream = response.getOutputStream();
            pptdata.getPresentation().write( outputStream );
            outputStream.flush();
            outputStream.close();
        }
        catch ( IOException ioe )
        {
            throw new PPTGeneratorException( ioe.getMessage() );
        }
    }
}
