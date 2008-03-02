package com.airfrance.squalecommon.datatransfertobject.transform.component;

import java.io.Serializable;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentType;

/**
 * @author M400841
 * @deprecated ComponentTransform doit etre utilisé a la place de ApplicationTransform
 */
public class ApplicationTransform
    implements Serializable
{

    /**
     * Constructeur prive
     */
    private ApplicationTransform()
    {
    }

    /**
     * ApplicationDTO -> ApplicationBO
     * 
     * @param pApplicationDTO ApplicationDTO à transformer
     * @return ApplicationBO
     */
    public static ApplicationBO dto2Bo( ComponentDTO pApplicationDTO )
    {

        // Initialisation du retour
        ApplicationBO applicationBO = new ApplicationBO();
        applicationBO.setId( pApplicationDTO.getID() );
        applicationBO.setName( pApplicationDTO.getName() );
        applicationBO.setExcludedFromActionPlan( pApplicationDTO.getExcludedFromActionPlan() );
        applicationBO.setJustification( pApplicationDTO.getJustification() );

        return applicationBO;
    }

    /**
     * ApplicationBO -> ApplicationDTO
     * 
     * @param pApplicationBO ApplicationBO
     * @return ApplicationDTO
     */
    public static ComponentDTO bo2Dto( ApplicationBO pApplicationBO )
    {

        // Initialisation du retour
        ComponentDTO applicationDTO = new ComponentDTO();
        applicationDTO.setID( pApplicationBO.getId() );
        applicationDTO.setName( pApplicationBO.getName() );
        applicationDTO.setType( ComponentType.APPLICATION );
        pApplicationBO.setExcludedFromActionPlan( applicationDTO.getExcludedFromActionPlan() );
        pApplicationBO.setJustification( applicationDTO.getJustification() );

        return applicationDTO;
    }
}
