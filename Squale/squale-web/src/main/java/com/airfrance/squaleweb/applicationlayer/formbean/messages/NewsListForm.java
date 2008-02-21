package com.airfrance.squaleweb.applicationlayer.formbean.messages;

import java.util.Collection;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * 
 */
public class NewsListForm extends RootForm{

    /** la liste des nouveautés */
    private Collection newsList;

    /**
     * @return la liste des nouveautés
     */
    public Collection getNewsList() {
        return newsList;
    }

    /**
     * @param newList la nouvelle liste
     */
    public void setNewsList(Collection newList) {
        newsList = newList;
    }
    
}
