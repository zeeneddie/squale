package com.airfrance.squaleweb.comparator;

import java.util.Comparator;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ProjectForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ComponentForm;

/**
 * Comparateur de ProjectDTO.
 * 
 * @author M400842
 */
public class ComponentComparator
    implements Comparator
{

    /**
     * Compare les composants en utilisant leurs noms.
     * 
     * @param pComp1 la première application
     * @param pComp2 la seconde application
     * @return la comparaison des noms des applications.
     */
    public int compare( Object pComp1, Object pComp2 )
    {
        int result = 0;
        // Suivant le type dynamique de l'objet
        // la comparaison se fait seulement sur leur nom
        // sans distinction majuscule/minuscule
        if ( pComp1 instanceof ComponentDTO )
        {
            result = ( (ComponentDTO) pComp1 ).getName().compareToIgnoreCase( ( (ComponentDTO) pComp2 ).getName() );
        }
        else if ( pComp1 instanceof ApplicationForm )
        {
            result =
                ( (ApplicationForm) pComp1 ).getApplicationName().compareToIgnoreCase(
                                                                                       ( (ApplicationForm) pComp2 ).getApplicationName() );
        }
        else if ( pComp1 instanceof ProjectForm )
        {
            result =
                ( (ProjectForm) pComp1 ).getProjectName().compareToIgnoreCase(
                                                                               ( (ProjectForm) pComp2 ).getProjectName() );
        }
        else if ( pComp1 instanceof ComponentForm )
        {
            result = ( (ComponentForm) pComp1 ).getName().compareToIgnoreCase( ( (ComponentForm) pComp2 ).getName() );
        }
        return result;
    }

}
