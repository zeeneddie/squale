package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.homepage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squalecommon.datatransfertobject.config.web.HomepageComponentDTO;
import com.airfrance.squalecommon.enterpriselayer.facade.config.web.HomepageComponentFacade;

/**
 * Application Component for the homepage
 */
public class HomepageApplicationComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * Default constructor
     */
    public HomepageApplicationComponentAccess()
    {

    }

    /**
     * Method for recover the Homepage configuration
     * 
     * @param user The current user 
     * @return The list of HomepageComponent linked to the user
     * @throws JrafEnterpriseException exception occur during the search
     */
    public List<HomepageComponentDTO> getHomepageConfig( UserDTO user )
        throws JrafEnterpriseException
    {
        List<HomepageComponentDTO> componentList = null;

        componentList = HomepageComponentFacade.findComponent( user.getID() );

        return componentList;
    }

    /**
     * Method for save the homepage configuration
     * 
     * @param user the current user
     * @param componentList The new list of HomepageComponent for the user
     * @throws JrafEnterpriseException Exception occur during the record
     */
    public void saveHomepageConfig( UserDTO user, List<HomepageComponentDTO> componentList )
        throws JrafEnterpriseException
    {

        // Search of the already existent HomepageComponent linked to the user
        List<HomepageComponentDTO> existentComponentList;
        existentComponentList = HomepageComponentFacade.findComponent( user.getID() );
        HashMap<String, HomepageComponentDTO> existentComponentMap = new HashMap<String, HomepageComponentDTO>();

        // Set the list into a map, with the component name as key and the component as value
        for ( HomepageComponentDTO existentCompo : existentComponentList )
        {
            existentComponentMap.put( existentCompo.getComponentName(), existentCompo );
        }

        // For each element of the HomepageComponent to save 
        List<HomepageComponentDTO> componentToSave = new ArrayList<HomepageComponentDTO>();
        for ( HomepageComponentDTO compo : componentList )
        {
            // If he already exist we update it else we create it
            if ( existentComponentMap.containsKey( compo.getComponentName() ) )
            {
                HomepageComponentDTO existentComponent = existentComponentMap.get( compo.getComponentName() );
                existentComponent.setComponentPosition( compo.getComponentPosition() );
                existentComponent.setComponentValue( compo.getComponentValue() );
                componentToSave.add( existentComponent );
            }
            else
            {
                compo.setUser( user );
                componentToSave.add( compo );
            }
        }

        // Execute the save or update
        HomepageComponentFacade.saveOrUpdate( componentToSave );

    }

}
