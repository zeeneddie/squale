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
package com.airfrance.squalecommon.enterpriselayer.businessobject.component;

import java.util.ArrayList;
import java.util.Collection;

import com.airfrance.squalecommon.enterpriselayer.businessobject.UnexpectedRelationException;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ProjectParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * Représente un projet intégré à une application Air France
 * 
 * @author m400842
 * @hibernate.subclass lazy="true" discriminator-value="Project"
 */
public class ProjectBO
    extends AbstractComplexComponentBO
{
    /**
     * Le projet est actif.
     */
    public static final int ACTIVATED = 1;

    /**
     * Le projet est supprimé.
     */
    public static final int DELETED = 2;

    /**
     * Le projet est désactivé.
     */
    public static final int DISACTIVATED = 3;

    /**
     * Contient le status du projet (activé, supprimé ou désactivé) Par défaut un projet est actif.
     */
    private int mStatus = ACTIVATED;

    /**
     * Profil de l'application à mettre en adéquation avec la configuration de SQUALIX, permettant de définir un schéma
     * d'analyse. Un profil définit notamment la technologie.
     */
    private ProjectProfileBO mProfile;

    /**
     * Récupérateur de source
     */
    private SourceManagementBO mSourceManager;

    /**
     * Contient les propriétés nécessaires à l'éxecution des différentes tâches.<br>
     * Contient des ProjectParameter
     */
    private MapParameterBO mParameters;

    /**
     * Grille qualité applicable pour ce projet.
     */
    private QualityGridBO mQualityGrid;

    /**
     * Liste des résultats qualité liés au projet et générés au fil des audits.
     */
    private Collection mQualityResults;

    /**
     * Instancie un nouveau composant.
     * 
     * @param pName Nom du composant.
     * @roseuid 42AFF0BF02D1
     */
    public ProjectBO( final String pName )
    {
        super();
        mName = pName;
    }

    /**
     * Constructeur par défaut.
     * 
     * @roseuid 42CBA9A901EC
     */
    public ProjectBO()
    {
        super();
        mParameters = new MapParameterBO();
        mQualityGrid = new QualityGridBO();
        mQualityResults = new ArrayList();
    }

    /**
     * Constructeur complet.
     * 
     * @param pName nom du composant
     * @param pChildren les enfants
     * @param pParent Composant parent
     * @param pProfile Profil du projet (techno, ...)
     * @param pLocation Emplacement du sous-projet
     * @param pParameters paramètre(s) du projet
     * @param pManager le source manager du projet
     * @param pQualityGrid Grille qualité associée
     * @param pQualityResults Résultats liés au projet
     * @throws UnexpectedRelationException si la relation ne peut etre ajouté
     * @roseuid 42CBA9A9021B
     */
    public ProjectBO( String pName, Collection pChildren, AbstractComplexComponentBO pParent,
                      ProjectProfileBO pProfile, SourceManagementBO pManager, MapParameterBO pParameters,
                      String pLocation, QualityGridBO pQualityGrid, Collection pQualityResults )
        throws UnexpectedRelationException
    {
        super( pName, pChildren, pParent );
        mProfile = pProfile;
        mQualityGrid = pQualityGrid;
        mQualityResults = pQualityResults;
        mSourceManager = pManager;
        mParameters = pParameters;
    }

    /**
     * Access method for the mProfile property.
     * 
     * @return the current value of the mProfile property
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO"
     *                        column="ProfileBO" not-null="false" cascade="none" outer-join="auto" update="true"
     *                        insert="true"
     */
    public ProjectProfileBO getProfile()
    {
        return mProfile;
    }

    /**
     * Sets the value of the mProfile property.
     * 
     * @param pProfile the new value of the mProfile property
     * @roseuid 42BACECC020B
     */
    public void setProfile( ProjectProfileBO pProfile )
    {
        mProfile = pProfile;
    }

    /**
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO"
     *                        column="ParametersSet" not-null="false" cascade="none" outer-join="auto" update="true"
     *                        insert="true" 
     * @return la map de parametres
     */
    public MapParameterBO getParameters()
    {
        return mParameters;
    }

    /**
     * @param pMap la nouvelle map de parametres
     */
    public void setParameters( MapParameterBO pMap )
    {
        mParameters = pMap;
    }

    /**
     * Access method for the mQualityGrid property.
     * 
     * @return the current value of the mQualityRules property
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO"
     *                        column="QualityGrid" not-null="false" cascade="none" outer-join="auto" update="true"
     *                        insert="true"
     * @roseuid 42BACECC0238
     */
    public QualityGridBO getQualityGrid()
    {
        return mQualityGrid;
    }

    /**
     * Sets the value of the mQualityGrid property.
     * 
     * @param pQualityGrid the new value of the mQualityGrid property
     * @roseuid 42BACECC0239
     */
    public void setQualityGrid( QualityGridBO pQualityGrid )
    {
        mQualityGrid = pQualityGrid;
    }

    /**
     * Access method for the mQualityResults property.
     * 
     * @return the current value of the mQualityResults property
     * @hibernate.bag lazy="true" cascade="none" inverse="true"
     * @hibernate.key column="ProjectId"
     * @hibernate.one-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.result.QualityResultBO"
     * @roseuid 42BACECC0248
     */
    public Collection getQualityResults()
    {
        return mQualityResults;
    }

    /**
     * Sets the value of the mQualityResults property.
     * 
     * @param pQualityResults the new value of the mQualityResults property
     * @roseuid 42BACECC0249
     */
    public void setQualityResults( Collection pQualityResults )
    {
        mQualityResults = pQualityResults;
    }

    /**
     * @return le nom du source manager
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO"
     *                        column="SourceManager" not-null="false" cascade="none" outer-join="auto" update="true"
     *                        insert="true"
     */
    public SourceManagementBO getSourceManager()
    {
        return mSourceManager;
    }

    /**
     * @param pSourceManager le nouveau source manager
     */
    public void setSourceManager( SourceManagementBO pSourceManager )
    {
        mSourceManager = pSourceManager;
    }

    /**
     * Retourne la valeur du paramètre de nom pName
     * 
     * @param pName le nom du paramètre
     * @return la valeur du paramètre
     */
    public ProjectParameterBO getParameter( String pName )
    {
        return (ProjectParameterBO) getParameters().getParameters().get( pName );
    }

    /**
     * Retourne le statut du projet
     * 
     * @return the mStatus property
     * @hibernate.property name="status" column="Status" type="integer" length="10" unique="false" update="true"
     *                     insert="true"
     */
    public int getStatus()
    {
        return mStatus;
    }

    /**
     * Sets the value of the mStatus property.
     * 
     * @param pStatus le status du projet
     * @roseuid 42CAA72C020E
     */
    public void setStatus( int pStatus )
    {
        mStatus = pStatus;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO#accept(com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor,
     *      java.lang.Object)
     */
    public Object accept( ComponentVisitor pVisitor, Object pArgument )
    {
        return pVisitor.visit( this, pArgument );
    }
}
