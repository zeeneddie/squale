package com.airfrance.squaleweb.applicationlayer.action.export.xls;

import java.util.Locale;

import jxl.HeaderFooter;
import jxl.SheetSettings;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.struts.util.MessageResources;

import com.airfrance.squaleweb.applicationlayer.formbean.results.ComponentResultListForm;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.welcom.outils.excel.ExcelData;
import com.airfrance.welcom.outils.excel.ExcelGenerateur;
import com.airfrance.welcom.outils.excel.ExcelGenerateurException;
import com.airfrance.welcom.outils.excel.ExcelTable;
import com.airfrance.welcom.outils.excel.ExcelWrapper;

/**
 * Data for excel export of components list
 */
public class ExcelDataComponentsResultsList
    extends ExcelData
{

    /** concerned bean */
    private ComponentResultListForm mBean;

    /** User name */
    private String mMatricule;

    /**
     * Constructor
     * 
     * @param pLocale locale
     * @param pMessages resources file
     * @param pBean bean
     * @param pMatricule user name
     */
    public ExcelDataComponentsResultsList( final Locale pLocale, final MessageResources pMessages, ComponentResultListForm pBean,
                             String pMatricule )
    {
        super( pLocale, pMessages );
        mBean = pBean;
        mMatricule = pMatricule;
    }

    /**
     * {@inheritDoc}
     * @see com.airfrance.welcom.outils.excel.ExcelData#fill(com.airfrance.welcom.outils.excel.ExcelGenerateur)
     */
    public void fill( ExcelGenerateur xlGenerateur )
        throws ExcelGenerateurException
    {
        ExcelWrapper monWrapper = (ExcelWrapper) xlGenerateur;
        WritableWorkbook workbook = monWrapper.getWorkbook();
        if ( null != mBean.getComponentListForm() )
        {
            ExcelTable et = new ExcelTable( messages, locale );
            et.setTable( mBean.getComponentListForm().getList() );
            /* Headers */
            // component's name
            et.addHeader( "component.name", "fullName" );
            try
            {
                et.writeTable( workbook );
                // Modify header and footer
                WritableSheet sheet = workbook.getSheet( 0 );
                SheetSettings settings = sheet.getSettings();
                String title =
                    (String) WebMessages.getString( locale, "export.excel.component_with_tres.title",
                                                    new String[] { mBean.getProjectName() } );
                StringBuffer metrics = new StringBuffer("\n");
                for(int i=0; i<mBean.getTreKeys().length; i++) {
                    metrics.append( WebMessages.getString( locale, mBean.getTreKeys()[i]) );
                    metrics.append( " = " );
                    metrics.append( mBean.getTreValues()[i] );
                    metrics.append( "\n" );
                }
                HeaderFooter head = new HeaderFooter();
                head.getCentre().append( title.replaceAll( "''", "'" ) + metrics.toString() );
                settings.setHeader( head );
                String footerLeft =
                    (String) WebMessages.getString( locale, "description.name.audit",
                                                    new String[] { mBean.getAuditDate() } );
                HeaderFooter footer =
                    SqualeExportExcelUtils.getFooter( locale, mBean.getApplicationName(), mBean.getProjectName(),
                                                      footerLeft, mMatricule );
                settings.setFooter( footer );
            }
            catch ( WriteException e )
            {
                e.printStackTrace();
            }
        }
    }

}
