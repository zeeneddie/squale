package com.airfrance.squaleweb.applicationlayer.action.export.xls;

import java.util.Calendar;
import java.util.Locale;

import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;

import jxl.HeaderFooter;

/**
 * Utilitaire pour les exports XLS
 */
public class SqualeExportExcelUtils {

    /**
     * Pied-de-page générique aux documents XLS :
     * footerLeft                       appli/project            page/total
     * crée le "date" par "matricule"                            http://squale.airfrance.fr
     * @param locale la locale
     * @param appli le nom de l'application
     * @param project le nom du projet
     * @param footerLeft le pied-de-page gauche
     * @param matricule le matricule utilisateur
     * @return le pied-de-page d'un document XLS
     */
    public static HeaderFooter getFooter(Locale locale, String appli, String project, String footerLeft, String matricule) {
        HeaderFooter footer = new HeaderFooter();
        // La date d'aujourd'hui
        String today = SqualeWebActionUtils.getFormattedDate(Calendar.getInstance().getTime(), locale);
        String footerLeft2 = (String) WebMessages.getString(locale, "export.pdf.page.footer", new String[] { today, matricule });
        // A gauche
        footer.getLeft().append(footerLeft);
        footer.getLeft().append("\n");
        footer.getLeft().append(footerLeft2);
        // A droite
        footer.getRight().appendPageNumber();
        footer.getRight().append("/");
        footer.getRight().appendTotalPages();
        footer.getRight().append("\nhttp://squale.airfrance.fr");
        // Au centre
        footer.getCentre().append(appli);
        footer.getCentre().append("/");
        footer.getCentre().append(project);
        footer.getCentre().append("\n");
        return footer;
    }

}
