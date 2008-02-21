package com.airfrance.squalix.tools.cpd;

import java.util.Properties;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;

import net.sourceforge.pmd.cpd.Language;
import net.sourceforge.pmd.cpd.LanguageFactory;

/**
 * Détection de copier/coller en C++
 */
public class CppCpdProcessing extends AbstractCpdProcessing {
    /** Seuil de détection de copier/coller */
    private static final int CPP_THRESHOLD = 100;
    
    /** 
     * {@inheritDoc}
     * @see com.airfrance.squalix.tools.cpd.AbstractCpdTask#getLanguage()
     */
    protected Language getLanguage() {
        LanguageFactory lf = new LanguageFactory();
        Properties p = new Properties();
        p.setProperty("ignore_literals", "false");
        return lf.createLanguage(LanguageFactory.CPP_KEY, p);
    }
    
    /** 
     * {@inheritDoc}
     * @see com.airfrance.squalix.tools.cpd.AbstractCpdTask#getTokenThreshold()
     */
    protected int getTokenThreshold() {
        return CPP_THRESHOLD;
    }
    
    /** 
     * {@inheritDoc}
     * @see com.airfrance.squalix.tools.cpd.AbstractCpdProcessing#getExtension()
     */
    protected String [] getExtensions() {
        return new String[]{".h", ".c", ".cc", ".cpp", ".cxx"};
    }

    /**
     * Obtention des répertoires exclus de la compilation
     * @param pProjectParams paramètres du projet
     * @return répertories exclus sous la forme de ListParameterBO(StringParameterBO)
     */
    protected ListParameterBO getExcludedDirs(MapParameterBO pProjectParams) {
        // Il n'y a pas de répertoires exclus en C++
        return null;
    }

}
