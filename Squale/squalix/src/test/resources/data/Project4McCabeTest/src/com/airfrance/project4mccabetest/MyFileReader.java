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
package org.squale.project4mccabetest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;

/**
 * Classe de test permettant de tester l'exécution de la tâche McCabe
 * sous Unix.
 * 
 * @author M400842
 */
public class MyFileReader implements MyNihilistInterface{
    
    /**
     * Logger
     */
    private Logger logger = Logger.getLogger(this.getClass());
    
    /**
     * Read the file at the specified URL.
     * @param pURL the url to read.
     */
    public void readFile(final URL pURL){
        MyJavaBean mjb = new MyJavaBean();
        mjb.setFileName(pURL.getFile());
        try {
            InputStream is = pURL.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int count = 0;
            long startTime = System.currentTimeMillis();
            while(null != br.readLine()){
                count ++;
            }
            mjb.setReadingTime(System.currentTimeMillis() - startTime);
            mjb.setLineNumber(count);            
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.error(mjb.toString());
        doNothing(null);
    }

    /**
     * @see MyNihilistInterface
     */
    public boolean doNothing(final Object pObject) {
        boolean result = false;
        if(null == pObject){
            result = false;
        }else{
            result = false;
        }
        logger.error("J'ai rien fait !!!");
        return result;
    }

}
