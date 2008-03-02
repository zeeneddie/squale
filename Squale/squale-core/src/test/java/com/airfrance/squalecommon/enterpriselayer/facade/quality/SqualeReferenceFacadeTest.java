package com.airfrance.squalecommon.enterpriselayer.facade.quality;

import java.util.ArrayList;
import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.squalecommon.SqualeTestCase;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class SqualeReferenceFacadeTest
    extends SqualeTestCase
{

    /**
     * test sur la récupération des résultats des projets
     * 
     * @throws JrafEnterpriseException en cas d'échec
     */
    public void testGetProjectResults()
        throws JrafEnterpriseException
    {

        // Initialisation du retour
        Collection references = null;

        // Excution de la méthode
        references = (Collection) SqualeReferenceFacade.getProjectResults( null, null, true, new Long( 0 ) );
        assertNotNull( references );

        // Excution de la méthode
        references = (Collection) SqualeReferenceFacade.getProjectResults( null, null, false, new Long( 0 ) );
        assertNotNull( references );

        // TODO

    }

    /**
     * Test pour void deleteAuditList(Collection)
     * 
     * @throws JrafEnterpriseException en cas d'échec
     */
    public void testDeleteAuditListCollection()
        throws JrafEnterpriseException
    {
        // Initialisation des parametres de la methode
        Collection projectsToRemove =
            new ArrayList( SqualeReferenceFacade.getProjectResults( new Integer( 2 ), new Integer( 0 ), false,
                                                                    new Long( 0 ) ) );

        // Exceution de la méthode
        SqualeReferenceFacade.deleteAuditList( projectsToRemove );
        assertNull( null );

        // TODO tests plus complets a effectuer

    }

    /**
     * Test pour void validateAuditList(Collection)
     * 
     * @throws JrafEnterpriseException en cas d'échec du test
     */
    public void testValidateAuditListCollection()
        throws JrafEnterpriseException
    {
        // Initialisation des parametres de la methode
        Collection projectsToValidate =
            new ArrayList( SqualeReferenceFacade.getProjectResults( new Integer( 2 ), new Integer( 0 ), false,
                                                                    new Long( 0 ) ) );

        // Exceution de la méthode
        SqualeReferenceFacade.validateAuditList( projectsToValidate );
        assertNull( null );

        // TODO tests plus complets a effectuer

    }

}
