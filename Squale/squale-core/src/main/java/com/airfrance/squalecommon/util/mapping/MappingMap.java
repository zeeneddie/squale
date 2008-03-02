package com.airfrance.squalecommon.util.mapping;

import java.util.Hashtable;
import java.util.Map;

/**
 * Map pour le mapping entre un nom et un nom de classe
 */
public class MappingMap
{
    /** Map avec le nom comme clef et la nom de classe comme valeur */
    private Map mNameMap = new Hashtable();

    /** Map avec le nom de classe commeclef et la nom comme valeur */
    private Map mClassNameMap = new Hashtable();

    /**
     * Ajout d'une entrée
     * 
     * @param pName nom
     * @param pClass nom de la classe
     */
    public void put( String pName, Class pClass )
    {
        mNameMap.put( pName, pClass );
        mClassNameMap.put( pClass, pName );
    }

    /**
     * Obtention de la classe correspondant à un nom
     * 
     * @param pName nom
     * @return classe correspondante
     */
    public Class getClassNameForName( String pName )
    {
        return (Class) mNameMap.get( pName );
    }

    /**
     * Obtention du nom correspondant à un nom de classe
     * 
     * @param pClass nom de la classe
     * @return nom correspondant
     */
    public String getNameForClass( Class pClass )
    {
        return (String) mClassNameMap.get( pClass );
    }
}
