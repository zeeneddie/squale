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
package org.squale.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.squalecommon.datatransfertobject.rule.QualityGridConfDTO;
import org.squale.squalecommon.datatransfertobject.rule.QualityGridDTO;
import org.squale.squalecommon.datatransfertobject.rule.QualityRuleDTO;
import org.squale.squalecommon.enterpriselayer.facade.rule.QualityGridFacade;
import org.squale.squalecommon.enterpriselayer.facade.rule.QualityGridImport;

/**
 * Manipulation de grille qualit�
 */
public class QualityGridApplicationComponentAccess
    extends DefaultExecuteComponent
{
    /**
     * Destruction des grilles qualit�
     * 
     * @param pGrids grilles � d�truire
     * @return grilles qualit� utilis�es et ne pouvant donc pas �tre d�truites
     * @throws JrafEnterpriseException si erreur
     */
    public Collection deleteGrids( Collection pGrids )
        throws JrafEnterpriseException
    {
        return QualityGridFacade.deleteGrids( pGrids );
    }

    /**
     * Met � jour une grille qualit� suite � des changements sur le web
     * 
     * @param pGrid la grille
     * @throws JrafEnterpriseException si erreur
     */
    public void updateGrid( QualityGridConfDTO pGrid )
        throws JrafEnterpriseException
    {
        QualityGridFacade.updateGrid( pGrid );
    }

    /**
     * Obtention des grilles qualit�
     * 
     * @param pLastVersionsOnly indique si seulement les derni�res versions
     * @return grilles qualit� existantes
     * @throws JrafEnterpriseException si erreur
     */
    public Collection getGrids( Boolean pLastVersionsOnly )
        throws JrafEnterpriseException
    {
        return QualityGridFacade.getGrids( pLastVersionsOnly.booleanValue() );
    }

    /**
     * Obtention des grilles qualit� sans profil ni audit
     * 
     * @return grilles qualit� existantes sans profil ni audit
     * @throws JrafEnterpriseException si erreur
     */
    public Collection getUnlinkedGrids()
        throws JrafEnterpriseException
    {
        return QualityGridFacade.getUnlinkedGrids();
    }

    /**
     * Permet de r�cup�rer une grille par son identifiant
     * 
     * @param pGridId l'identifiant
     * @return la grille
     * @throws JrafEnterpriseException en cas d'�chec
     */
    public QualityGridDTO loadGridById( Long pGridId )
        throws JrafEnterpriseException
    {
        return QualityGridFacade.loadGridById( pGridId );
    }

    /**
     * Cr�ation d'une grille qualit�
     * 
     * @param pStream flux associ�
     * @param pErrors erreur rencontr�es
     * @return grilles cr�ees
     * @throws JrafEnterpriseException si erreur
     */
    public Collection createGrid( InputStream pStream, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        return QualityGridImport.createGrid( pStream, pErrors );
    }

    /**
     * Importation d'une grille qualit�
     * 
     * @param pStream flux associ�
     * @param pErrors erreur rencontr�es
     * @return grilles pr�sentes dans le fichier
     * @throws JrafEnterpriseException si erreur
     */
    public Collection importGrid( InputStream pStream, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        return QualityGridImport.importGrid( pStream, pErrors );
    }

    /**
     * Obtention d'une grille
     * 
     * @param pQualityGrid grille
     * @return grille correspondante
     * @throws JrafEnterpriseException si erreur
     */
    public QualityGridConfDTO getGrid( QualityGridDTO pQualityGrid )
        throws JrafEnterpriseException
    {
        return QualityGridFacade.get( pQualityGrid );
    }

    /**
     * Obtention d'une r�gle qualit�
     * 
     * @param pQualityRule r�gle qualit� (seul l'id est utilis�)
     * @param deepTransformation le bool�en indiquant si on veut �galement une transformation de la formule ou pas.
     * @return r�gle qualit� correspondante
     * @throws JrafEnterpriseException si erreur
     */
    public QualityRuleDTO getQualityRule( QualityRuleDTO pQualityRule, Boolean deepTransformation )
        throws JrafEnterpriseException
    {
        return QualityGridFacade.getQualityRule( pQualityRule, deepTransformation.booleanValue() );
    }

    /**
     * Obtention d'une r�gle qualit� et des mesures utilis�es
     * 
     * @param pQualityRule r�gle qualit�
     * @param deepTransformation un bool�en indiquant si on veut aussi transformer les formules ou pas.
     * @return un tableau � deux entr�es contenant la r�gle qualit� et ses mesures extraites
     * @throws JrafEnterpriseException si erreur
     */
    public Object[] getQualityRuleAndUsedTres( QualityRuleDTO pQualityRule, Boolean deepTransformation )
        throws JrafEnterpriseException
    {
        return QualityGridFacade.getQualityRuleAndUsedTres( pQualityRule, deepTransformation.booleanValue() );
    }

    /**
     * Obtention des m�triques d'une grille
     * 
     * @param pQualityGrid grille
     * @return liste des m�triques sous la forme de map, chaque entr�e donnant le type de composant et la liste des
     *         m�triques sur ce type de composant
     * @throws JrafEnterpriseException si erreur
     */
    public Map getGridMetrics( QualityGridDTO pQualityGrid )
        throws JrafEnterpriseException
    {
        return QualityGridFacade.getGridMetrics( pQualityGrid );
    }

    /**
     * Met � jour une grille qualit� suite � des changements sur le web
     * 
     * @param pGrid la grille
     * @param pErrors erreur rencontr�es
     * @throws JrafEnterpriseException si erreur
     */
    public void updateGrid( QualityGridConfDTO pGrid, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        QualityGridFacade.updateGrid( pGrid, pErrors );
    }

    /**
     * Constructeur par d�faut
     */
    public QualityGridApplicationComponentAccess()
    {
    }
}
