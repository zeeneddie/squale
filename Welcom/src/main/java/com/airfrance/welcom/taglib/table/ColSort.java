/*
 * Créé le 2 avr. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import java.io.Serializable;

/**
 * @author M327837 Infos pour trier une colonne
 */
public class ColSort
    implements Serializable
{

    /** ID pour la serialization */
    static final long serialVersionUID = -6974144058267526814L;

    /** Nom de la colonne a trie */
    private String column;

    /** ordre du trie */
    private SortOrder sort;

    /** Type de trie */
    private String type;

    /** Formatage de la date, s'il y en a une .. */
    private String dateFormat;

    /**
     * @return accesseur
     */
    public String getColumn()
    {
        return column;
    }

    /**
     * @return accesseur
     */
    public SortOrder getSort()
    {
        return sort;
    }

    /**
     * @param string accesseur
     */
    public void setColumn( final String string )
    {
        column = string;
    }

    /**
     * @param order accesseur
     */
    public void setSort( final SortOrder order )
    {
        sort = order;
    }

    /**
     * @return accesseur
     */
    public String getDateFormat()
    {
        return dateFormat;
    }

    /**
     * @return accesseur
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param string accesseur
     */
    public void setDateFormat( final String string )
    {
        dateFormat = string;
    }

    /**
     * @param string accesseur
     */
    public void setType( final String string )
    {
        type = string;
    }
}