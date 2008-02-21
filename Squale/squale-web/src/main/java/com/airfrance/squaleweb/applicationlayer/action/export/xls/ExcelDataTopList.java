package com.airfrance.squaleweb.applicationlayer.action.export.xls;

import java.util.Locale;

import jxl.HeaderFooter;
import jxl.SheetSettings;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.struts.util.MessageResources;

import com.airfrance.squaleweb.applicationlayer.formbean.results.TopListForm;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.welcom.outils.excel.ExcelData;
import com.airfrance.welcom.outils.excel.ExcelGenerateur;
import com.airfrance.welcom.outils.excel.ExcelGenerateurException;
import com.airfrance.welcom.outils.excel.ExcelTable;
import com.airfrance.welcom.outils.excel.ExcelWrapper;

/**
 * Gestion de l'export des tops
 */
public class ExcelDataTopList extends ExcelData {

    /** le bean concerné */
    private TopListForm mBean;

    /** Le nom de l'utilisateur */
    private String mMatricule;

    /**
     * Constructeur
     * @param pLocale : c'est la locale de l'application pour l'internationnalisation
     * @param pMessages :un MessageResources initialisé avec le fichier ressource 
     * contenant les labels à utiliser dans le ficheir excel.
     * @param pBean le bean des tops
     * @param pMatricule le matricule de l'utilsateur
     */
    public ExcelDataTopList(final Locale pLocale, final MessageResources pMessages, TopListForm pBean, String pMatricule) {
        super(pLocale, pMessages);
        mBean = pBean;
        mMatricule = pMatricule;
    }

    /** 
     * @see com.airfrance.welcom.outils.excel.ExcelData#fill(com.airfrance.welcom.outils.excel.ExcelGenerateur)
     * {@inheritDoc}
     */
    public void fill(ExcelGenerateur xlGenerateur) throws ExcelGenerateurException {
        ExcelWrapper monWrapper = (ExcelWrapper) xlGenerateur;
        WritableWorkbook workbook = monWrapper.getWorkbook();
        if (null != mBean.getComponentListForm()) {
            ExcelTable et = new ExcelTable(messages, locale);
            et.setTable(mBean.getComponentListForm());
            /* les en-têtes du tableau */
            // le nom du composant
            et.addHeader("component.name", "fullName");
            // la note
            et.addHeader(mBean.getTre(), "metrics[0]");
            try {
                et.writeTable(workbook);
                // On modifie l'en-tête et pied-de-page
                WritableSheet sheet = workbook.getSheet(0);
                SheetSettings settings = sheet.getSettings();
                String title = (String) WebMessages.getString(locale, "export.pdf.top.title", new String[] { mBean.getProjectName()});
                HeaderFooter head = new HeaderFooter();
                head.getCentre().append(title.replaceAll("''", "'"));
                settings.setHeader(head);
                String footerLeft = (String) WebMessages.getString(locale, "description.name.audit", new String[] { mBean.getAuditDate()});
                HeaderFooter footer = SqualeExportExcelUtils.getFooter(locale, mBean.getApplicationName(), mBean.getProjectName(), footerLeft, mMatricule);
                settings.setFooter(footer);
            } catch (WriteException e) {
                e.printStackTrace();
            }
        }
    }

}
