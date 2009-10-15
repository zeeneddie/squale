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
package org.squale.squaleexport.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.bootstrap.initializer.Initializer;

/**
 * 
 *
 */
public final class MapObjToXml
{

    /**
     * Logger
     */
    private static Log mLOGGER;
    
    /**
     * 
     */
    private MapObjToXml()
    {
        // TODO Auto-generated constructor stub
    }
    
    
    /**
     * @param args
     */
    public static void main( String[] args )
    {

        //GregorianCalendar cal = new GregorianCalendar();
        //System.out.println( "launch@"+cal.get( Calendar.HOUR_OF_DAY )+"H"+cal.get( Calendar.MINUTE )+"min "+cal.get( Calendar.SECOND )+"s" );
        String rootPath = System.getProperty( "user.dir" );

        String configFile = "/src/test/config/providers-config.xml";
     
        Initializer init = new Initializer( rootPath, configFile );
        init.initialize();
        // Maintenant que le socle JRAF est initialisé, on peut créer un logger
        mLOGGER = LogFactory.getLog( MapObjToXml.class );
        
        Launch launch= new Launch();
        //cal = new GregorianCalendar();
        //System.out.println( "start@"+cal.get( Calendar.HOUR_OF_DAY )+"H"+cal.get( Calendar.MINUTE )+"min "+cal.get( Calendar.SECOND )+"s" );
        launch.exec();
        //cal = new GregorianCalendar();
        //System.out.println( "finish@"+cal.get( Calendar.HOUR_OF_DAY )+"H"+cal.get( Calendar.MINUTE )+"min "+cal.get( Calendar.SECOND )+"s" );

    }

}
