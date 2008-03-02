/*
 * Créé le 8 août 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction;
import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;
import com.airfrance.squaleweb.resources.WebMessages;

/**
 * Liste les audits à afficher
 * 
 * @author M400842
 */
public class AuditListForm
    extends RootForm
{
    /** Affichage de tous les audits */
    private boolean mAllAudits = true;

    /** Type d'affichage de all.jsp */
    private String mKind = "all";

    /**
     * Liste des audits
     */
    private List mAudits = new ArrayList();

    /**
     * @return la liste des audits
     */
    public List getAudits()
    {
        return mAudits;
    }

    /**
     * @param pAudits la liste des audits
     */
    public void setAudits( List pAudits )
    {
        mAudits = pAudits;
    }

    /**
     * @param pAllAudits affichage de tous les audits
     */
    public void setAllAudits( boolean pAllAudits )
    {
        mAllAudits = pAllAudits;
    }

    /**
     * @return true si tous les audits sont affichés
     */
    public boolean isAllAudits()
    {
        return mAllAudits;
    }

    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     * @param mapping le mapping
     * @param request la requête
     */
    public void reset( ActionMapping mapping, HttpServletRequest request )
    {
        super.reset( mapping, request );
        // Reinitialisation du checkbox
        mAllAudits = false;
    }

    /**
     * @return type d'audit
     */
    public String getKind()
    {
        return mKind;
    }

    /**
     * @param string type d'audit
     */
    public void setKind( String string )
    {
        mKind = string;
    }

    /**
     * permet d'obtenir la date de l'audit courant sur lequel on travaille sous format affichable.
     * 
     * @param pRequest la requete http
     * @return la date formatée
     */
    public String getDate( HttpServletRequest pRequest )
    {
        String auditDate = WebMessages.getString( pRequest, "project.results.last_audit" );
        if ( pRequest.getSession().getAttribute( BaseDispatchAction.CURRENT_AUDIT_DTO ) != null )
        {
            auditDate =
                ( (AuditDTO) pRequest.getSession().getAttribute( BaseDispatchAction.CURRENT_AUDIT_DTO ) ).getFormattedDate();
        }
        return auditDate;
    }

    /**
     * la liste des audits terminés Sert notamment pour les stats admins
     */
    private List mTerminatedAudits = new ArrayList();

    /**
     * @return la liste des audits terminés
     */
    public List getTerminatedAudits()
    {
        return mTerminatedAudits;
    }

    /**
     * @param pList la liste des audits interrompus ou en cours
     */
    public void setTerminatedAudits( List pList )
    {
        mTerminatedAudits = pList;
    }

    /**
     * la liste des audits interrompus ou en cours Sert notamment pour les stats admins
     */
    private List mShutDownAudits = new ArrayList();

    /**
     * @return la liste des audits interrompus ou en cours
     */
    public List getShutDownAudits()
    {
        return mShutDownAudits;
    }

    /**
     * @param pList la liste des audits interrompus ou en cours
     */
    public void setShutDownAudits( List pList )
    {
        mShutDownAudits = pList;
    }

    /**
     * la liste des audits interrompus ou en cours Sert notamment pour les stats admins
     */
    private List mNotAttemptedAudits = new ArrayList();

    /**
     * @return la liste des audits interrompus ou en cours
     */
    public List getNotAttemptedAudits()
    {
        return mNotAttemptedAudits;
    }

    /**
     * @param pList la liste des audits interrompus ou en cours
     */
    public void setNotAttemptedAudits( List pList )
    {
        mNotAttemptedAudits = pList;
    }

    /**
     * la liste des audits partiels ou en échec Sert pour les admins
     */
    private List mFailedOrPartialAudits = new ArrayList();

    /**
     * @return la liste des audits partiels ou en échec
     */
    public List getFailedOrPartialAudits()
    {
        return mFailedOrPartialAudits;
    }

    /**
     * @param pList la liste des audits partiels ou en échec
     */
    public void setFailedOrPartialAudits( List pList )
    {
        mFailedOrPartialAudits = pList;
    }

}
