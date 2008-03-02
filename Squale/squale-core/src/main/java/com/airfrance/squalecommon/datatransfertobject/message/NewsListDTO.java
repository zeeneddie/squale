package com.airfrance.squalecommon.datatransfertobject.message;

import java.util.Collection;

/**
 * 
 */
public class NewsListDTO
{

    /** la liste des nouveautés */
    private Collection newsList;

    /**
     * @return la liste des nouveautés
     */
    public Collection getNewsList()
    {
        return newsList;
    }

    /**
     * @param newList la nouvelle liste
     */
    public void setNewsList( Collection newList )
    {
        newsList = newList;
    }

}
