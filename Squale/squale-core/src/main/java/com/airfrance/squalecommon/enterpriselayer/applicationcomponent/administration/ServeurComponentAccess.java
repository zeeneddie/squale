package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.administration;

import com.airfrance.squalecommon.enterpriselayer.facade.component.ServeurFacade;

import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;

import java.util.Collection;

/**
 * ComponentApplication du Serveur d'exécution de Squalix
 */
public class ServeurComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * Retourne la liste des serveurs
     * 
     * @return la liste des serveurs
     * @throws JrafEnterpriseException si une erreur survient
     */
    public Collection listeServeurs()
        throws JrafEnterpriseException
    {
        Collection lListeServeurs = ServeurFacade.listeServeurs();
        return lListeServeurs;
    }

}
