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
package org.squale.squaleweb.util;

import org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;

/**
 * Contient les constantes nécessaires à la vue et au controlleur
 * 
 * @author M400842
 */
public abstract class SqualeWebConstants
{
    /**
     * Liste des profils sur les applications <br>
     * A mettre en relation avec le fichier d'externalisation du projet common.
     */
    public static final String[] PROFILES =
        { ProfileBO.READER_PROFILE_NAME, ProfileBO.AUDITOR_PROFILE_NAME, ProfileBO.MANAGER_PROFILE_NAME };

    /**
     * Contient la clé de référence de l'attribut de session contenant la liste des résultats courants à afficher.
     */
    public static final String RESULTS_KEY = "results";

    /**
     * Contient la clé de référence de l'attribut de session contenant la liste des tops d'un projet
     */
    public static final String TOP_KEY = "top";

    /**
     * Contient la clé de référence de l'attribut de session contenant la liste des notes des pratiques courantes à
     * afficher.
     */
    public static final String PRACTICES_KEY = "practices";

    /**
     * Contient la clé de référence de l'attribut de session contenant la liste des notes des pratiques courantes à
     * afficher dans la vue météo
     */
    public static final String PRACTICES_LIST_KEY = "practices.meteo";

    /**
     * Contient la clé de référence de l'attribut de session contenant la liste des composants enfants à afficher.
     */
    public static final String CHILDREN_KEY = "children";

    /**
     * Contient la clé de référence de l'attribut de session contenant la liste des détails d'une transgression à
     * afficher.
     */
    public static final String ITEMS_KEY = "items";

    /**
     * Clé désignant le traceur pour la navigation dans la hiérarchie de classe
     */
    public static final String TRACKER_COMPONENT = "browsing.tracker.component";

    /**
     * Clé désignant le traceur pour la navigation historique
     */
    public static final String TRACKER_HIST = "browsing.tracker.hist";

    /**
     * Clé indiquant si on vient directement d'une vue composant ou non, sert pour le traceur
     */
    public static final String TRACKER_BOOL = "browsing.tracker.bool";

    /**
     * Clé indiquant si le traceur doit etre réinitialisé
     */
    public static final String TRACKER_RESET = "browsing.tracker.reset";

    /**
     * Clé pour ce qui va s'afficher dans l'onglet "information générale"
     */
    public static final String GEN_INFO = "browsing.tracker.info";

    /**
     * Clé désignant la pratique pour laquelle on affiche les composants
     */
    public static final String TRE_DETAILS_NAME = "browsing.tre.name";

    /**
     * Clé désignant la note de la pratique pour laquelle on affiche les composants
     */
    public static final String TRE_DETAILS_MARK = "browsing.tre.mark";

    /**
     * Contient la clé de référence de l'attribut de session spécifiant si une page à été validée (review.jsp)
     */
    public static final String VALIDATED = "is.validated";

    /**
     * Contient la clé de référence du maker scatterplott
     */
    public static final String BUBBLE_GRAPH = "bubbleGraph.key";

    /**
     * Contient la clé de référence du chart du scatterplott
     */
    public static final String BUBBLE_CHART = "bubbleChart.key";

    /**
     * Contient la clé de référence du GraphMaker du scatterplott
     */
    public static final String BUBBLE_GRAPH_MAKER = "bubbleGraphMaker.key";

    /**
     * Contient la clé indiquant si on a crée un nouveau projet permet de savoir quand on doit envoyer un mail
     */
    public static final String NEW_PROJECT = "new_project";

    /**
     * Clé qui indique si on vient de la page mark.jsp
     */
    public static final String FROM_MARK_PAGE_KEY = "fromMarkPage";

    /**
     * Clé pour retrouver la précédente action dans le cas de bouton retour.
     */
    public static final String RETURN_ACTION_KEY = "returnAction";
    
    /**
     * Clé contenant le langage associé à un projet
     */
    public static final String LANGUAGE = "language";

    /**
     * Hidden constructor for utility class
     */
    private SqualeWebConstants()
    {
        super();
    }

}
