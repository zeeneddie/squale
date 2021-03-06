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
package org.squale.squalix.core;

import java.util.HashMap;

/**
 * Cette classe sert � l'�change entre les diff�rents t�ches des param�tres qui ne doivent pas �tre persist�s
 */
public class TaskData
{

    /**
     * La map contenant les param�tres qui ne doivent pas etre persist�s
     */
    private HashMap mMap;

    /**
     * Constructeur
     */
    public TaskData()
    {
        super();
        mMap = new HashMap();
    }

    /**
     * @param pKey la cl� du param�tre
     * @return l'objet associ� � cette cl�
     */
    public Object getData( String pKey )
    {
        return mMap.get( pKey );
    }

    /**
     * Insert dans la map le param�tre
     * 
     * @param pKey la cl� d�finissant le param�tre
     * @param pData la valeur de la cl�
     */
    public void putData( String pKey, Object pData )
    {
        mMap.put( pKey, pData );
    }

    // Liste des constantes qui servent de cl�

    /**
     * Chemin d'acc�s absolu � la vue Cette variable est renseign�e par le SourceManagement, elle est utilis�e par les
     * diff�rentes t�ches qui n�cessitent l'acc�s � des donn�es contenues dans un r�pertoire. Ces t�ches font alors la
     * concat�nation de ce chemin avec le nom de leur r�pertoire. Par exemple, la t�che de compilation va chercher ses
     * sources dans /vobs/squale/src/squaleCommon, l'acc�s � ces sources se fera par concat�nation du chemin d�fini par
     * le sourceManagement. Ce chemin d'acc�s est d�pendant du type de sourcemanagement (clearcase, filesystem, zip ...)
     * Pour clearcase : donne le chemin d'acc�s � la vue statique qui a �t� mont�e par exemple :
     * /app/SQUALE/dev/data/cc_snapshot/squale_v2_0_act_quicktest_testcommon_squaledev/ La t�che de compilation java ira
     * donc chercher ses donn�es dans le r�pertoire
     * /app/SQUALE/dev/data/cc_snapshot/squale_v2_0_act_quicktest_testcommon_squaledev//vobs/squale/src/squaleCommon
     * (Noter le double slash qui est supeflu sur Unix)
     */
    public static final String VIEW_PATH = "view_path";

    /**
     * Classpath java de compilation Il s'agit du classpath utilis� par la t�che de compilation java, par exemple
     * lib1.jar:lib2.jar
     */
    public static final String CLASSPATH = "classpath";

    /**
     * Les r�pertoires contenant les fichiers compil�s
     */
    public static final String CLASSES_DIRS = "classes_dirs";

    /**
     * Le r�pertoire contenant les pages JSP compil�es
     */
    public final static String JSP_TO_JAVA_DIR = "jspToJava_dir";

    /**
     * Le r�pertoire contenant les classes Java des pages JSP compil�es
     */
    public final static String JSP_CLASSES_DIR = "jsp_classes_dir";

    /**
     * Map between classname generated by tomcat and jsp path
     */
    public final static String JSP_MAP_NAMES = "jsp_map_names";
    
    /**
     * Additional directory appended to VIEW_PATH to generate the destination directory for local projects 
     */
    public final static String SUP_PATH = "sup_path";
}
