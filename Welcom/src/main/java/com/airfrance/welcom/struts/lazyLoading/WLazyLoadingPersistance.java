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
/*
 * Créé le 18 avr. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.lazyLoading;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WLazyLoadingPersistance
    implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -4016765344131250225L;

    /**
     * Clef de persisance pour le stockage session
     */
    private static final String lazyLoadingKey = "com.airfrance.welcom.LAZYLOADING";

    /**
     * Table de hashage contenant la peristance du lazyLoading
     */
    private final Hashtable htObjectsPerType = new Hashtable();

    /**
     * Constructeur et initisalisation des object de base;
     */
    public WLazyLoadingPersistance()
    {
        init();
    }

    /**
     * Recupere l'object de parsistance pour le LazyLoading
     * 
     * @param request la request
     * @return L'objet de persistance
     */
    public synchronized static WLazyLoadingPersistance find( final HttpServletRequest request )
    {
        final HttpSession session = request.getSession();

        return find( session );
    }

    /**
     * Recupere l'object de parsistance pour le LazyLoading
     * 
     * @param session Session
     * @return L'objet LazyLoading
     */
    public synchronized static WLazyLoadingPersistance find( final HttpSession session )
    {
        if ( session.getAttribute( lazyLoadingKey ) == null )
        {
            session.setAttribute( lazyLoadingKey, new WLazyLoadingPersistance() );
        }

        return (WLazyLoadingPersistance) session.getAttribute( lazyLoadingKey );
    }

    /**
     * Initilisation des type de ressources a statocke
     */
    public void init()
    {
        htObjectsPerType.get( WLazyLoadingType.COMBO );
        htObjectsPerType.get( WLazyLoadingType.DROPDOWNPANEL );
        htObjectsPerType.get( WLazyLoadingType.ONGLET );
    }

    /**
     * ajoute un bout de code a la persiance pour le lazyLoading
     * 
     * @param type : Type de lazy Loading
     * @param key : clef
     * @param data : data de pal page
     */
    public void add( final WLazyLoadingType type, final String key, final String data )
    {
        if ( !htObjectsPerType.containsKey( type ) )
        {

            final int limit =
                Integer.parseInt( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_GLOBAL_LAZYLOADING_HISTORY ) );
            htObjectsPerType.put( type, new WLazyLoadingTypeObject( type, limit ) );
        }

        final WLazyLoadingTypeObject lz = (WLazyLoadingTypeObject) htObjectsPerType.get( type );
        lz.add( key, data );
    }

    /**
     * Recupere le bout de code
     * 
     * @param type : Type de lazy Loading
     * @param key : clef
     * @return la bouton de page
     * @throws WLazyPersistanceExeption : probleme sur lagestion de la persitance
     */
    public String get( final WLazyLoadingType type, final String key )
        throws WLazyPersistanceExeption
    {
        if ( !htObjectsPerType.containsKey( type ) )
        {
            throw new WLazyPersistanceExeption( "Aucun object sous le type :" + type );
        }

        final WLazyLoadingTypeObject lz = (WLazyLoadingTypeObject) htObjectsPerType.get( type );

        return lz.get( key );
    }

    /**
     * Retourne si l'objet est disponible
     * 
     * @param type : Type d'objet (COMBO, DDP ...)
     * @param key : Clef de stockage
     * @return l'objet si disponible
     */
    public boolean contains( final WLazyLoadingType type, final String key )
    {
        return htObjectsPerType.containsKey( type )
            && ( (WLazyLoadingTypeObject) htObjectsPerType.get( type ) ).contains( key );
    }

    /**
     * Persistance d'un type d'object
     * 
     * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
     *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
     */
    private class WLazyLoadingTypeObject
        implements Serializable
    {

        /**
         * 
         */
        private static final long serialVersionUID = 1112877138856356371L;

        /** Type de lazy loading */
        private WLazyLoadingType type;

        /** Taille maximun de la pile */
        private int limitSize = -1;

        /** Liste des objets sauvegardés */
        private final HashMap htObjects = new HashMap();

        /** Liste des objets sauvegardés */
        private final ArrayList vObjects = new ArrayList();

        /**
         * Cree un nouveau objet de typage pour sauvegarde
         * 
         * @param pType : type d'objet (ONGLET, DDP, ...)
         * @param pLimitSize : nombre max d'objet a conserver
         */
        public WLazyLoadingTypeObject( final WLazyLoadingType pType, final int pLimitSize )
        {
            this.type = pType;
            this.limitSize = pLimitSize;
        }

        /**
         * Cree un nouveau objet de typage pour sauvegarde
         * 
         * @param pType : type d'objet (ONGLET, DDP, ...)
         */
        public WLazyLoadingTypeObject( final WLazyLoadingType pType )
        {
            this( pType, -1 );
        }

        /**
         * Memorise un bout de code lazyLoading sous une clef
         * 
         * @param key : clef de stokage
         * @param data : data a stocker
         */
        public void add( final String key, final String data )
        {
            // Suppresion d'un object si deja enregistré sous la clef
            if ( htObjects.containsKey( key ) )
            {
                vObjects.remove( key );
                htObjects.remove( key );
            }

            // Supression des object en memoire ancien
            if ( ( limitSize != -1 ) && ( htObjects.size() > limitSize ) )
            {
                final Object o = vObjects.get( 0 );
                vObjects.remove( o );
                htObjects.remove( o );
            }

            vObjects.add( key );
            htObjects.put( key, data );
        }

        /**
         * Recupere le contenu sous un clef de stockage
         * 
         * @param key : clef de stokage
         * @return : contenu stoké sous la clef
         * @throws WLazyPersistanceExeption Ne trouve de donnés sous cette clefs
         */
        public String get( final String key )
            throws WLazyPersistanceExeption
        {
            if ( !htObjects.containsKey( key ) )
            {
                throw new WLazyPersistanceExeption( "Aucun object sous la clef :" + key + "(" + type + ")" );
            }

            return (String) ( htObjects.get( key ) );
        }

        /**
         * Retourne si la clef est deja renseigné
         * 
         * @param key : clef de stockage
         * @return retourne true si trouve la clef
         */
        public boolean contains( final String key )
        {
            return htObjects.containsKey( key );
        }
    }
}