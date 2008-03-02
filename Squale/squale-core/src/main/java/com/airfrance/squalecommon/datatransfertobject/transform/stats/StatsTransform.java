package com.airfrance.squalecommon.datatransfertobject.transform.stats;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.stats.AuditsStatsDTO;
import com.airfrance.squalecommon.datatransfertobject.stats.FactorsStatsDTO;
import com.airfrance.squalecommon.datatransfertobject.stats.ProfilStatsDTO;
import com.airfrance.squalecommon.datatransfertobject.stats.SetOfStatsDTO;
import com.airfrance.squalecommon.datatransfertobject.stats.SiteStatsDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.config.ServeurTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.stats.SiteAndProfilStatsDICTBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.stats.SiteStatsDICTBO;

/**
 */
public class StatsTransform
{

    /**
     * Id (fictif) du serveur associé au dto stockant le calcul de totaux
     */
    public final static long STATS_TOTAL_ID = 9999;

    /**
     * bo2dto avec un comportement un peu particulier. Les objets représentant les données stockées en base ne
     * correspondent (volontairement) pas à la représentation souhaitée, le transform effectue donc la mise en forme
     * pour avoir des objets permettant de faciliter l'affichage
     * 
     * @param pSiteAndProfile l'objet contenant les stats pour un site et profil
     * @param pSite l'objet contenant les stats pour un site sans tenir compte du profil
     * @return l'objet DTO contenant toutes les résultats
     */
    public SetOfStatsDTO bo2dto( Collection pSiteAndProfile, Collection pSite )
    {
        SetOfStatsDTO set = new SetOfStatsDTO();
        List siteList = new ArrayList( 0 ), profileList = new ArrayList( 0 );
        Iterator pSiteIt = pSite.iterator(), pSiteAndProfileIt;
        // 2 objets supplémentaires que l'on stocke correspondant au total,
        // afin de faciliter l'affichage
        SiteStatsDTO siteTotal = new SiteStatsDTO();
        ProfilStatsDTO siteAndProfilTotal = new ProfilStatsDTO();
        // les données sur les audits
        int nbAuditFailed = 0, nbAuditPartial = 0, nbAuditSuccessfuls = 0, nbAuditsNotAttempted = 0;
        // les données sur les facteurs
        int nbFactorsRefused = 0, nbFactorsReserved = 0, nbFactorsAccepted = 0;
        // Boucle principale, permet de récupérer toutes les principales informations sauf les LOC
        while ( pSiteIt.hasNext() )
        {
            SiteStatsDICTBO site = (SiteStatsDICTBO) pSiteIt.next();
            // On récupère les données sur les audits
            nbAuditFailed += site.getNbFailedAudits();
            nbAuditPartial += site.getNbPartialAudits();
            nbAuditsNotAttempted += site.getNbNotAttemptedAudit();
            nbAuditSuccessfuls += site.getNbSuccessfulAudits();
            SiteStatsDTO siteDto = new SiteStatsDTO();
            nbFactorsAccepted += site.getNbOfAcceptedFactors();
            nbFactorsRefused += site.getNbOfRefusedFactors();
            nbFactorsReserved += site.getNbOfAcceptedWithReservesFactors();
            // On transforme les statistiques par site
            bo2dto( siteDto, site, pSiteAndProfile.iterator() );
            // Ajoute à la liste final
            siteList.add( siteDto );
        }
        // On affecte les valeurs par profil :
        pSiteAndProfileIt = pSiteAndProfile.iterator();
        while ( pSiteAndProfileIt.hasNext() )
        {
            SiteAndProfilStatsDICTBO siteAndProfil = (SiteAndProfilStatsDICTBO) pSiteAndProfileIt.next();
            String profileName = siteAndProfil.getProfil();
            // on regarde si on a déjà stocké un objet pour ce profil
            ProfilStatsDTO profilDto = null;
            boolean alreadyExists = false;
            for ( int i = 0; i < profileList.size() && !alreadyExists; i++ )
            {
                profilDto = (ProfilStatsDTO) profileList.get( i );
                alreadyExists = profilDto.getProfileName().equals( profileName );
            }
            // il n'existe pas déjà, on l'initialise
            if ( !alreadyExists )
            {
                profilDto = new ProfilStatsDTO();
                profilDto.setProfileName( profileName );
                // On l'ajoute à la liste des profils
                profileList.add( profilDto );
            }
            // On peut lui affecter les valeurs, en faisant un cumul
            profilDto.setLoc( profilDto.getLoc() + siteAndProfil.getNbOfCodesLines() );
            profilDto.setNbProjects( profilDto.getNbProjects() + siteAndProfil.getNbProjects() );
        }
        // rempli les 2 objets totaux complémentaires
        setSiteTotal( siteTotal, siteList );
        setProfilTotal( siteAndProfilTotal, profileList );
        // Rempli les 2 listes du DTO de haut niveau
        set.setListOfSiteStatsDTO( siteList );
        set.setListOfProfilsStatsDTO( profileList );
        // Rempli le form contenant les données sur les audits et l'ajoute à l'objet récapitulant
        AuditsStatsDTO auditsDto = new AuditsStatsDTO();
        auditsDto.setNbFailed( nbAuditFailed );
        auditsDto.setNbPartial( nbAuditPartial );
        auditsDto.setNbNotAttempted( nbAuditsNotAttempted );
        auditsDto.setNbSuccessfuls( nbAuditSuccessfuls );
        auditsDto.setNbTotal( nbAuditFailed + nbAuditsNotAttempted + nbAuditSuccessfuls + nbAuditPartial );
        set.setAuditsStatsDTO( auditsDto );
        // Rempli le form contenant les données sur les audits et l'ajoute à l'objet récapitulant
        FactorsStatsDTO factorsDto = new FactorsStatsDTO();
        factorsDto.setNbFactorsAccepted( nbFactorsAccepted );
        factorsDto.setNbFactorsRefused( nbFactorsRefused );
        factorsDto.setNbFactorsReserved( nbFactorsReserved );
        factorsDto.setNbTotal( nbFactorsAccepted + nbFactorsRefused + nbFactorsReserved );
        set.setFactorsStatsDTO( factorsDto );
        return set;

    }

    /**
     * Transforme le dto à partir du bo
     * 
     * @param siteDto les statistiques par site sous forme DTO
     * @param site les statistiques par site sous forme DTO
     * @param pSiteAndProfileIt l'iterateur sur les statistiques par profil
     */
    private void bo2dto( SiteStatsDTO siteDto, SiteStatsDICTBO site, Iterator pSiteAndProfileIt )
    {
        if ( site.getServeurBO() != null )
        {
            siteDto.setServeurDTO( ServeurTransform.bo2dto( site.getServeurBO() ) );
        }
        siteDto.setNbAppliToValidate( site.getNbAppliToValidate() );
        siteDto.setNbAppliWithAuditsSuccessful( site.getNbTotalAppliWithSuccesfulAudit() );
        siteDto.setNbAppliWithoutSuccessfulAudits( site.getNbTotalAppliWithAudit() );
        siteDto.setNbValidatedApplis( site.getNbTotalAppliWithoutAudit() );
        // Cette boucle sert seulement pour les LOC et le nombre de projets
        while ( pSiteAndProfileIt.hasNext() )
        {
            SiteAndProfilStatsDICTBO siteAndProfil = (SiteAndProfilStatsDICTBO) pSiteAndProfileIt.next();
            long lSiteId = siteAndProfil.getServeurBO().getServeurId();
            // L'objet actuellement parcouru contient des informations pour le site en question
            // on les récupère et on les ajoute aux données déjà trouvées
            if ( siteDto.getServeurDTO().getServeurId() == lSiteId )
            {
                siteDto.setLoc( siteDto.getLoc() + siteAndProfil.getNbOfCodesLines() );
                siteDto.setNbProjects( siteDto.getNbProjects() + +siteAndProfil.getNbProjects() );
            }
        }

    }

    /**
     * @param siteAndProfilTotal l'objet faisant le total
     * @param profileList la liste des profiles
     */
    private void setProfilTotal( ProfilStatsDTO siteAndProfilTotal, List profileList )
    {
        siteAndProfilTotal.setProfileName( "Total" );
        for ( int i = 0; i < profileList.size(); i++ )
        {
            ProfilStatsDTO current = (ProfilStatsDTO) profileList.get( i );
            siteAndProfilTotal.setLoc( siteAndProfilTotal.getLoc() + current.getLoc() );
            siteAndProfilTotal.setNbProjects( siteAndProfilTotal.getNbProjects() + current.getNbProjects() );
        }
        profileList.add( siteAndProfilTotal );
    }

    /**
     * @param siteTotal l'objet faisant le total
     * @param siteList la liste des sites
     */
    private void setSiteTotal( SiteStatsDTO siteTotal, List siteList )
    {
        siteTotal.getServeurDTO().setServeurId( STATS_TOTAL_ID );
        siteTotal.getServeurDTO().setName( "Total" );
        for ( int i = 0; i < siteList.size(); i++ )
        {
            SiteStatsDTO current = (SiteStatsDTO) siteList.get( i );
            siteTotal.setNbAppliToValidate( siteTotal.getNbAppliToValidate() + current.getNbAppliToValidate() );
            siteTotal.setNbAppliWithAuditsSuccessful( siteTotal.getNbAppliWithAuditsSuccessful()
                + current.getNbAppliWithAuditsSuccessful() );
            siteTotal.setNbAppliWithoutSuccessfulAudits( siteTotal.getNbAppliWithoutSuccessfulAudits()
                + current.getNbAppliWithoutSuccessfulAudits() );
            siteTotal.setLoc( siteTotal.getLoc() + current.getLoc() );
            siteTotal.setNbProjects( siteTotal.getNbProjects() + current.getNbProjects() );
            siteTotal.setNbValidatedApplis( siteTotal.getNbValidatedApplis() + current.getNbValidatedApplis() );
        }
        siteList.add( siteTotal );
    }

    // la version dto2bo n'a pas de sens

}
