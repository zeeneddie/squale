package com.airfrance.welcom.addons.access.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.airfrance.welcom.addons.access.bean.ProfileBean;
import com.airfrance.welcom.addons.config.AddonsConfig;
import com.airfrance.welcom.outils.jdbc.WJdbcMagic;
import com.airfrance.welcom.outils.jdbc.WResultSetUtils;
import com.airfrance.welcom.outils.jdbc.WStatement;
import com.airfrance.welcom.struts.action.WDispatchAction;

/*
 * Créé le 25 mai 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WProfileListeAction extends WDispatchAction {

    /**
     * @see org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public ActionForward unspecified(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {


        WJdbcMagic jdbc = null;

        try {
            jdbc = new WJdbcMagic();
            final WStatement sta = jdbc.getWStatement();
            sta.add("select * from "+AddonsConfig.WEL_PROFILE);
            final ResultSet rs = sta.executeQuery();
            final ArrayList table = WResultSetUtils.populateInArrayList(ProfileBean.class, rs);
            sta.close();

            request.getSession().setAttribute("listProfil", table);

            return mapping.findForward("success");
        } catch (final SQLException sqle) {
            throw new ServletException(sqle.getMessage());
        } finally {
            if (jdbc != null) {
                jdbc.close();
            }
        }

    }

}
