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
package com.airfrance.squalix.tools.cpd;

import java.util.Iterator;

import net.sourceforge.pmd.cpd.Match;
import net.sourceforge.pmd.cpd.TokenEntry;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.daolayer.rulechecking.ProjectRuleSetDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.CpdTransgressionBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionItemBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.ProjectRuleSetBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;

/**
 * Persistance des données de Cpd Les données sont stockées dans une ruleset dynamique, ce ruleset contient une règle
 * par langage
 */
public class CpdPersistor
{
    /** Transgression */
    private CpdTransgressionBO mTransgression;

    /** RuleSet */
    private ProjectRuleSetBO mRuleSet;

    /** Préfixe de chemin */
    private String mPrefixPath;

    /**
     * Constructeur
     * 
     * @param pProjectBO projet
     * @param pAuditBO audit
     * @param pPrefixPath préfixe de path
     */
    public CpdPersistor( ProjectBO pProjectBO, AuditBO pAuditBO, String pPrefixPath )
    {
        mPrefixPath = pPrefixPath;
        mRuleSet = createRuleSet( pProjectBO );
        mTransgression = createTransgression( pProjectBO, pAuditBO );
    }

    /**
     * Création de la transgression
     * 
     * @param pProjectBO projet
     * @param pAuditBO audit
     * @return transgression
     */
    protected CpdTransgressionBO createTransgression( ProjectBO pProjectBO, AuditBO pAuditBO )
    {
        // Création de la transgression
        CpdTransgressionBO transgression = new CpdTransgressionBO();
        transgression.setAudit( pAuditBO );
        transgression.setComponent( pProjectBO );
        transgression.setRuleSet( mRuleSet );
        transgression.setTaskName( "CpdTask" );
        return transgression;
    }

    /**
     * Création du RuleSet
     * 
     * @param pProject projet
     * @return règle de copy/paste
     */
    protected ProjectRuleSetBO createRuleSet( ProjectBO pProject )
    {
        ProjectRuleSetBO ruleset = new ProjectRuleSetBO();
        ruleset.setProject( pProject );
        return ruleset;
    }

    /**
     * Création d'une règle
     * 
     * @param pLanguage langage
     * @return règle
     */
    protected RuleBO createRule( String pLanguage )
    {
        RuleBO rule = new RuleBO();
        rule.setCategory( "copypaste" );
        rule.setCode( "cpd." + pLanguage );
        rule.setSeverity( "error" );
        rule.setRuleSet( mRuleSet );
        mRuleSet.addRule( rule );
        return rule;
    }

    /**
     * Sauvegarde des résultats
     * 
     * @param pSession session
     * @throws JrafDaoException si erreur
     */
    public void storeResults( ISession pSession )
        throws JrafDaoException
    {
        // Création du RuleSet
        ProjectRuleSetDAOImpl.getInstance().create( pSession, mRuleSet );
        // Sauvegarde des données dans la base
        MeasureDAOImpl.getInstance().create( pSession, mTransgression );
    }

    /**
     * Ajout d'un résultat
     * 
     * @param pLanguage langage
     * @param pMatches détections
     */
    public void addResult( String pLanguage, Iterator pMatches )
    {
        RuleBO rule = createRule( pLanguage );
        int nbOcc = 0;
        int dupLinesNb = 0;
        // On traite chaque détection
        while ( pMatches.hasNext() )
        {
            Match m = (Match) pMatches.next();
            StringBuffer detail = new StringBuffer();
            detail.append( CpdMessages.getString( "copypaste.summary", new Object[] { new Integer( m.getLineCount() ),
                new Integer( m.getTokenCount() ) } ) );
            detail.append( '\n' );
            nbOcc++;
            Iterator occ = m.iterator();
            // Parcours de chaque occurrence de copy/paste
            String itemFileName = "";
            int itemLine = 0;
            while ( occ.hasNext() )
            {
                TokenEntry entry = (TokenEntry) occ.next();
                String fileName = entry.getTokenSrcID();
                // On élimine dans le nom du fichier le nom de la vue
                if ( fileName.startsWith( mPrefixPath ) )
                {
                    fileName = fileName.substring( mPrefixPath.length() );
                }
                detail.append( CpdMessages.getString( "copypaste.occurrenceentry", new Object[] {
                    new Integer( entry.getBeginLine() ), fileName } ) );
                detail.append( '\n' );
                // On affecte le premier fichier et la première ligne trouvée à l'item
                if ( itemFileName.length() == 0 )
                {
                    itemFileName = fileName;
                    itemLine = entry.getBeginLine();
                }
            }
            dupLinesNb += ( m.getMarkCount() - 1 ) * m.getLineCount();
            // On ne fait persister que les 100 premiers items par règle
            // ICI il n'y a qu'une règle définie par langage
            if ( nbOcc <= RuleCheckingTransgressionBO.MAX_DETAILS )
            {
                RuleCheckingTransgressionItemBO item = new RuleCheckingTransgressionItemBO();
                // Taille maximale pour les détails
                final int MAX_LENGTH = 3000;
                // Troncature du message si besoin
                if ( detail.length() > MAX_LENGTH )
                {
                    detail.setLength( MAX_LENGTH - 1 );
                }
                item.setMessage( detail.toString() );
                item.setRule( rule );
                item.setComponentFile( itemFileName );
                item.setLine( itemLine );
                mTransgression.getDetails().add( item );
            }
        }
        // On ajoute une métrique de type Integer pour chaque règle transgressée
        IntegerMetricBO metric = new IntegerMetricBO();
        metric.setName( rule.getCode() );
        metric.setValue( nbOcc );
        metric.setMeasure( mTransgression );
        mTransgression.putMetric( metric );
        // Création de la métrique donnant le nombre total de lignes pour le langage
        mTransgression.setDuplicateLinesForLanguage( pLanguage, new Integer( dupLinesNb ) );
        // Incrément du nombre total de lignes dupliquées
        mTransgression.incrementDuplicateLinesNumber( dupLinesNb );
    }
}
