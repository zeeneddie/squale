package com.airfrance.squalecommon.datatransfertobject.transform.rulechecking;

import java.io.IOException;

import com.airfrance.squalecommon.datatransfertobject.rulechecking.CheckstyleDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleSetBO;

/**
 * @author S Porto Rico
 */
public class CheckstyleTransform
{

    /**
     * Constructeur prive
     */
    private CheckstyleTransform()
    {

    }

    /**
     * DTO -> BO pour une Version
     * 
     * @param pVersionDTO VersionDTO à transformer
     * @return VersionBO
     * @throws IOException si le fichier n'est pas disponible
     */
    public static CheckstyleRuleSetBO dto2Bo( CheckstyleDTO pVersionDTO )
        throws IOException
    {

        CheckstyleRuleSetBO versionBO = new CheckstyleRuleSetBO();

        // Initialisation des champs de VersionBO
        RuleSetTransform.dto2Bo( pVersionDTO, versionBO );
        versionBO.setValue( pVersionDTO.getBytes() );
        return versionBO;
    }

    /**
     * BO -> DTO pour une Version
     * 
     * @param pVersionBO VersionBO
     * @return VersionDTO
     */
    public static CheckstyleDTO bo2Dto( CheckstyleRuleSetBO pVersionBO )
    {

        // Initialisation
        CheckstyleDTO versionDTO = new CheckstyleDTO();

        // Initialisation des champs de VersionBO
        RuleSetTransform.bo2Dto( pVersionBO, versionDTO );
        versionDTO.setBytes( pVersionBO.getValue() );

        return versionDTO;
    }
}
